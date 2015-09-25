package com.gkd.stub;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.gkd.CommandReceiver;
import com.gkd.Disassemble;
import com.gkd.GKD;
import com.gkd.GKD.OSType;
import com.gkd.GKDCommonLib;
import com.gkd.Global;
import com.gkd.MyLanguage;
import com.gkd.Setting;
import com.gkd.instrument.InterruptSocketServerController;
import com.gkd.instrument.JmpSocketServerController;
import com.gkd.instrument.MemorySocketServerController;
import com.peter.tightvncpanel.TightVNC;
import com.peterswing.CommonLib;

public class BochsStub implements VMStub {
	private static BochsStub instance = null;
	String path;
	Process p;
	CommandReceiver commandReceiver;
	GKD gkd;
	BufferedWriter commandOutputStream;
	Logger logger = Logger.getLogger(BochsStub.class);
	String version;
	String arguments;
	int timeout = 10000;

	public static void main(String args[]) {
		new BochsStub();
	}

	public BochsStub() {
	}

	public void initStub(String para[]) {

	}

	private String sendBochsCommand(String command) {
		try {
			logger.debug("sendBochsCommand " + command);
			command = command.toLowerCase().trim();
			Global.lastCommand = command;
			commandOutputStream.write(command + "\n");
			commandOutputStream.flush();
			if (command.equals("6") || command.equals("c")) {
				return null;
			}

			return commandReceiver.getCommandResult();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public void setGKDInstance(GKD gkd) {
		this.gkd = gkd;
	}

	public static BochsStub getInstance() {
		if (instance == null) {
			instance = new BochsStub();
		}

		return instance;
	}

	@Override
	public void setVMPath(String path) {
		if (!new File(path).exists()) {
			System.err.println(path + " not exist");
			System.exit(-1);
		}
		this.path = path;
	}

	@Override
	public void startVM() {
		try {
			logger.debug("startVM");

			// profiling server has to start before run the bochs
			if (Setting.getInstance().memoryProfiling) {
				if (Global.debug) {
					logger.debug("Memory profiling port " + Global.profilingMemoryPort);
				}
				MemorySocketServerController.start(Global.profilingMemoryPort, null);
			}
			if (Setting.getInstance().jmpProfiling) {
				if (Global.debug) {
					logger.debug("Jump profiling port " + Global.profilingJmpPort);
				}
				JmpSocketServerController.start(Global.profilingJmpPort, GKD.instrumentPanel.getJmpTableModel());
			}
			if (Setting.getInstance().interruptProfiling) {
				if (Global.debug) {
					logger.debug("Interrupt profiling port " + Global.profilingInterruptPort);
				}
				InterruptSocketServerController.start(Global.profilingInterruptPort);
			}

			ProcessBuilder pb = new ProcessBuilder((path + " " + arguments).split(" "));
			pb.redirectErrorStream(true);
			p = pb.start();
			commandReceiver = new CommandReceiver(p.getInputStream());
			commandOutputStream = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));

			if (GKDCommonLib.readConfigInt(gkd.cmd, "/gkd/vncPort/text()") != -1) {
				gkd.tabbedPane3.addTab("VNC", null, gkd.getVncPanel(), null);
				TightVNC.initVNCPanel(gkd, gkd.getVncPanel(), "localhost", GKDCommonLib.readConfigInt(gkd.cmd, "/gkd/vncPort/text()"), null, false);
			}

			String versionLines[] = commandReceiver.getCommandResult().split("\n");
			for (String line : versionLines) {
				line = line.trim(); // string::matches will break is string contains \r
				if (line.contains("Bochs x86 Emulator")) {
					version = line.trim();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void stopVM() {
		logger.debug("stopVM");
		if (Setting.getInstance().memoryProfiling) {
			MemorySocketServerController.stop();
		}
		if (Setting.getInstance().jmpProfiling) {
			JmpSocketServerController.stop();
		}
		if (Setting.getInstance().interruptProfiling) {
			InterruptSocketServerController.stop();
		}
		if (p != null) {
			p.destroy();
		}
	}

	@Override
	public boolean isRunning() {
		return false;
	}

	@Override
	public void runVM() {
		logger.debug("runVM");
		sendBochsCommand("c");
	}

	@Override
	public void pauseVM() {
		logger.debug("pauseVM");
		try {
			ProcessBuilder pb;
			Process p;
			if (GKD.os == OSType.mac || GKD.os == OSType.linux) {
				pb = new ProcessBuilder("killall", "-2", "bochs");
				p = pb.start();
			} else {
				pb = new ProcessBuilder("PauseBochs.exe");
				p = pb.start();
			}
			p.waitFor();
			logger.debug("pauseVM end");
			//			commandReceiver.getCommandResult();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
		}
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public void setVMArguments(String arguments) {
		this.arguments = arguments;
	}

	@Override
	public Vector<String[]> instruction(BigInteger physicalAddress, boolean is32Bit) {
		logger.debug("updateInstruction " + physicalAddress);
		Vector<String[]> r = new Vector<String[]>();
		//		if (csBaseAddress == null) {
		//			eip = eip.and(CommonLib.string2BigInteger("0xffffffffffffffff"));
		//			csBaseAddress = cs.add(eip);
		//		} else {
		//			eip = eip.and(CommonLib.string2BigInteger("0xffffffffffffffff"));
		//			csBaseAddress = csBaseAddress.add(eip);
		//		}
		//		logger.debug("csBaseAddress=" + csBaseAddress.toString());

		int bytes[] = physicalMemory(physicalAddress, 200);
		String result = Disassemble.disassemble(bytes, is32Bit, physicalAddress);

		String lines[] = result.split("\n");
		if (lines.length > 0) {
			for (int x = 0; x < lines.length; x++) {
				try {
					// load cCode
					String temp[] = lines[x].split("  +");
					if (temp.length == 3) {
						String pcStr = temp[0].substring(0, 8).trim();
						String decodedBytes = temp[1].trim();
						String instruction = temp[2].trim();
						BigInteger pc = CommonLib.string2BigInteger("0x" + pcStr);
						if (pc == null) {
							continue;
						}
						String s[] = gkd.getCCode(pc, false);
						String lineNo[] = gkd.getCCode(pc, true);
						if (s != null && lineNo != null) {
							for (int index = 0; index < s.length; index++) {
								r.add(new String[] { "", "cCode : " + StringUtils.leftPad(CommonLib.string2BigInteger(pc.toString()).toString(16), 16, '0') + " : " + lineNo[index],
										s[index], "" });
							}
						}
						// end load cCode
						r.add(new String[] { "", pc.toString(), instruction, decodedBytes });

						//model.addRow(new String[] { "", "0x" + StringUtils.leftPad(address.add(pc).toString(16), 16, "0"), lines[x].substring(25).trim(),lines[x].substring(8, 8).trim() });
					} else {
						r.add(new String[] { String.valueOf(r.size() - 1), "3", r.get(r.size() - 1)[3] + temp[1].trim().replaceAll("-", "") });
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			//			model.removeNonOrderInstruction();
			//			model.fireTableDataChanged();
		}
		return r;
	}

	@Override
	public Hashtable<String, String> registers() {
		Hashtable<String, String> ht = new Hashtable<String, String>();
		try {
			String result = sendBochsCommand("r");
			result = result.replaceAll("r", "\nr");
			String lines[] = result.split("\n");

			for (String line : lines) {
				line = line.trim(); // string::matches will break is string contains \r
				if (line.matches(".*.ax:.*")) {
					ht.put("ax", line.replaceAll(":", "").replaceAll("^.*ax", "").split(" ")[1].replaceAll("_", ""));
				} else if (line.matches(".*.bx:.*")) {
					ht.put("bx", line.replaceAll(":", "").replaceAll("^.*bx", "").split(" ")[1].replaceAll("_", ""));
				} else if (line.matches(".*.cx:.*")) {
					ht.put("cx", line.replaceAll(":", "").replaceAll("^.*cx", "").split(" ")[1].replaceAll("_", ""));
				} else if (line.matches(".*.dx:.*")) {
					ht.put("dx", line.replaceAll(":", "").replaceAll("^.*dx", "").split(" ")[1].replaceAll("_", ""));
				} else if (line.matches(".*.si:.*")) {
					ht.put("si", line.replaceAll(":", "").replaceAll("^.*si", "").split(" ")[1].replaceAll("_", ""));
				} else if (line.matches(".*.di:.*")) {
					ht.put("di", line.replaceAll(":", "").replaceAll("^.*di", "").split(" ")[1].replaceAll("_", ""));
				} else if (line.matches(".*.bp:.*")) {
					ht.put("bp", line.replaceAll(":", "").replaceAll("^.*bp", "").split(" ")[1].replaceAll("_", ""));
				} else if (line.matches(".*.sp:.*")) {
					ht.put("sp", line.replaceAll(":", "").replaceAll("^.*sp", "").split(" ")[1].replaceAll("_", ""));
				} else if (line.matches(".*.ip:.*")) {
					ht.put("ip", line.replaceAll(":", "").replaceAll("^.*ip", "").split(" ")[1].replaceAll("_", ""));
				} else if (line.matches(".*eflags .*")) {
					ht.put("eflags", line.replaceAll(":", "").replaceAll("^.*eflags", "").split(" ")[1].replaceAll("_", ""));

					BigInteger eflags = CommonLib.string2BigInteger(ht.get("eflags"));
					String eflagsDetail = "";
					if (CommonLib.getBit(eflags, 21) == 1) {
						eflagsDetail += "ID ";
					} else {
						eflagsDetail += "id ";
					}
					if (CommonLib.getBit(eflags, 20) == 1) {
						eflagsDetail += "VIP ";
					} else {
						eflagsDetail += "vip ";
					}
					if (CommonLib.getBit(eflags, 19) == 1) {
						eflagsDetail += "VIF ";
					} else {
						eflagsDetail += "vif ";
					}
					if (CommonLib.getBit(eflags, 18) == 1) {
						eflagsDetail += "AC ";
					} else {
						eflagsDetail += "ac ";
					}
					if (CommonLib.getBit(eflags, 17) == 1) {
						eflagsDetail += "VM ";
					} else {
						eflagsDetail += "vm ";
					}
					if (CommonLib.getBit(eflags, 16) == 1) {
						eflagsDetail += "RF ";
					} else {
						eflagsDetail += "rf ";
					}
					if (CommonLib.getBit(eflags, 14) == 1) {
						eflagsDetail += "NT ";
					} else {
						eflagsDetail += "nt ";
					}
					eflagsDetail += "IOPL=" + CommonLib.getBit(eflags, 13) + CommonLib.getBit(eflags, 12) + " ";
					if (CommonLib.getBit(eflags, 11) == 1) {
						eflagsDetail += "OF ";
					} else {
						eflagsDetail += "of ";
					}
					if (CommonLib.getBit(eflags, 10) == 1) {
						eflagsDetail += "DF ";
					} else {
						eflagsDetail += "df ";
					}
					if (CommonLib.getBit(eflags, 9) == 1) {
						eflagsDetail += "IF ";
					} else {
						eflagsDetail += "if ";
					}
					if (CommonLib.getBit(eflags, 8) == 1) {
						eflagsDetail += "TF ";
					} else {
						eflagsDetail += "tf ";
					}
					if (CommonLib.getBit(eflags, 7) == 1) {
						eflagsDetail += "SF ";
					} else {
						eflagsDetail += "sf ";
					}
					if (CommonLib.getBit(eflags, 6) == 1) {
						eflagsDetail += "ZF ";
					} else {
						eflagsDetail += "zf ";
					}
					if (CommonLib.getBit(eflags, 4) == 1) {
						eflagsDetail += "AF ";
					} else {
						eflagsDetail += "af ";
					}
					if (CommonLib.getBit(eflags, 2) == 1) {
						eflagsDetail += "PF ";
					} else {
						eflagsDetail += "pf ";
					}
					if (CommonLib.getBit(eflags, 0) == 1) {
						eflagsDetail += "CF ";
					} else {
						eflagsDetail += "cf ";
					}

					ht.put("eflagsDetail", eflagsDetail);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (version.contains("2.4.1")) {
			try {
				//				sendBochsCommand("sreg");
				String result = sendBochsCommand("sreg");// commandReceiver.getCommandResult("s:", "idtr:", null);
				// logger.debug(result);
				String[] lines = result.split("\n");

				for (String line : lines) {
					line = line.trim(); // string::matches will break is string contains \r
					line = line.replaceFirst("<.*>", "");
					String str[] = line.split(" ");

					if (line.matches(".*cs:.*")) {
						ht.put("cs", line.split("=")[1].split(",")[0]);
					} else if (line.matches(".*ds:.*")) {
						ht.put("ds", line.split("=")[1].split(",")[0]);
					} else if (line.matches(".*es:.*")) {
						ht.put("es", line.split("=")[1].split(",")[0]);
					} else if (line.matches(".*fs:.*")) {
						ht.put("fs", line.split("=")[1].split(",")[0]);
					} else if (line.matches(".*gs:.*")) {
						ht.put("gs", line.split("=")[1].split(",")[0]);
					} else if (line.matches(".*ss:.*")) {
						ht.put("ss", line.split("=")[1].split(",")[0]);
					} else

					if (line.matches(".*gdtr:.*")) {
						ht.put("gdtr", line.split("=")[1].split(",")[0]);
						ht.put("gdtr_limit", str[1].split("=")[1]);
					} else if (line.matches(".*ldtr.*")) {
						ht.put("ldtr", line.split("=")[1].split(",")[0]);
					} else if (line.matches(".*idtr:.*")) {
						ht.put("idtr", line.split("=")[1].split(",")[0]);
						ht.put("idtr_limit", str[1].split("=")[1]);
					} else if (line.matches(".*tr:.*")) {
						ht.put("tr", line.split("=")[1].split(",")[0]);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			try {
				String result = sendBochsCommand("sreg");
				// logger.debug(result);
				String[] lines = result.split("\n");

				for (int x = 0; x < lines.length; x++) {
					String line = lines[x];
					line = line.trim(); // string::matches will break is string contains \r
					line = line.replaceFirst("<.*>", "");
					String str[] = line.split(" ");

					if (line.matches(".*cs:.*")) {
						ht.put("cs", line.split(":")[1].split(",")[0]);
						x++;
						line = lines[x];
						ht.put("cs_base", line.split(",")[1].split("=")[1].trim());
					} else if (line.matches(".*ds:.*")) {
						ht.put("ds", line.split(":")[1].split(",")[0]);
					} else if (line.matches(".*es:.*")) {
						ht.put("es", line.split(":")[1].split(",")[0]);
					} else if (line.matches(".*fs:.*")) {
						ht.put("fs", line.split(":")[1].split(",")[0]);
					} else if (line.matches(".*gs:.*")) {
						ht.put("gs", line.split(":")[1].split(",")[0]);
					} else if (line.matches(".*ss:.*")) {
						ht.put("ss", line.split(":")[1].split(",")[0]);
					}

					if (line.matches(".*gdtr:.*")) {
						ht.put("gdtr", line.split("=")[1].split(",")[0]);
						ht.put("gdtr_limit", str[1].split("=")[1]);
					} else if (line.matches(".*ldtr.*")) {
						ht.put("ldtr", line.split("=")[1].split(",")[0]);
					} else if (line.matches(".*idtr:.*")) {
						ht.put("idtr", line.split("=")[1].split(",")[0]);
						ht.put("idtr_limit", str[1].split("=")[1]);
					} else if (line.matches(".*tr:.*")) {
						ht.put("tr", line.split(":")[1].split(",")[0]);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		try {
			String result = sendBochsCommand("creg");
			String[] lines = result.split("\n");

			for (String line : lines) {
				line = line.trim(); // string::matches will break is string contains \r
				line = line.replaceFirst("^.*CR0", "CR0");
				if (line.matches(".*CR0=.*")) {
					ht.put("cr0", line.split(" ")[0].split("=")[1].replace(":", ""));

					if (CommonLib.getBit(CommonLib.string2long(ht.get("cr0")), 0) == 1) {
						ht.put("mode", MyLanguage.getString("Protected_mode") + "     ");
					} else {
						ht.put("mode", MyLanguage.getString("Real_mode") + "     ");
					}

					String arr[] = line.split(":")[1].trim().split(" ");
					ht.put("cr0Detail", "");
					ht.put("cr0Detail2", "");
					for (int z = 0; z < 7; z++) {
						ht.put("cr0Detail", ht.get("cr0Detail") + arr[z] + " ");
					}
					for (int z = 7; z < arr.length; z++) {
						ht.put("cr0Detail2", ht.get("cr0Detail2") + arr[z] + " ");
					}
				} else if (line.matches(".*CR2=.*")) {
					ht.put("cr2", line.split(" ")[2].split("=")[1]);
				} else if (line.matches(".*CR3=.*")) {
					ht.put("cr3", line.split(" ")[0].split("=")[1]);
				} else if (line.matches(".*CR4=.*")) {
					ht.put("cr4", line.split(" ")[0].split("=")[1].replace(":", ""));

					String arr[] = line.split(":")[1].trim().split(" ");
					ht.put("cr4Detail", "<html>");
					for (int z = 0; z < arr.length; z++) {
						ht.put("cr4Detail", ht.get("cr4Detail") + arr[z] + " ");
						if (z == 7) {
							ht.put("cr4Detail", ht.get("cr4Detail") + "<br>");
						}
					}
					ht.put("cr4Detail", ht.get("cr4Detail") + "</html>");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		try {
			if (version.contains("2.4.1")) {
			} else {
				String result = sendBochsCommand("dreg");
				String[] lines = result.split("\n");

				for (String line : lines) {
					line = line.trim(); // string::matches will break is string contains \r
					if (line.matches(".*DR0=0x.*")) {
						ht.put("dr0", line.split("=")[1].split(":")[0]);
					} else if (line.matches(".*DR1=0x.*")) {
						ht.put("dr1", line.split("=")[1].split(":")[0]);
					} else if (line.matches(".*DR2=0x.*")) {
						ht.put("dr2", line.split("=")[1].split(":")[0]);
					} else if (line.matches(".*DR3=0x.*")) {
						ht.put("dr3", line.split("=")[1].split(":")[0]);
					} else if (line.matches(".*DR6=0x.*")) {
						ht.put("dr6", line.split("=")[1].split(":")[0]);
					} else if (line.matches(".*DR7=0x.*")) {
						ht.put("dr7", line.split("=")[1].split(":")[0]);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		try {
			// fpu
			String result = sendBochsCommand("fpu");
			String[] lines = result.split("\n");

			for (String line : lines) {
				line = line.trim(); // string::matches will break is string contains \r
				if (line.matches(".*ST0.*")) {
					ht.put("st0", line.replaceAll("^.*raw", "").split(":")[0]);
				} else if (line.matches(".*ST1.*")) {
					ht.put("st1", line.replaceAll("^.*raw", "").split(":")[0]);
				} else if (line.matches(".*ST2.*")) {
					ht.put("st2", line.replaceAll("^.*raw", "").split(":")[0]);
				} else if (line.matches(".*ST3.*")) {
					ht.put("st3", line.replaceAll("^.*raw", "").split(":")[0]);
				} else if (line.matches(".*ST4.*")) {
					ht.put("st4", line.replaceAll("^.*raw", "").split(":")[0]);
				} else if (line.matches(".*ST5.*")) {
					ht.put("st5", line.replaceAll("^.*raw", "").split(":")[0]);
				} else if (line.matches(".*ST6.*")) {
					ht.put("st6", line.replaceAll("^.*raw", "").split(":")[0]);
				} else if (line.matches(".*ST7.*")) {
					ht.put("st7", line.replaceAll("^.*raw", "").split(":")[0]);
				} else if (line.matches(".*status.*")) {
					ht.put("fpuStatus", line.replaceAll("^.*word:", "").trim().split(":")[0]);
				} else if (line.matches(".*control.*")) {
					ht.put("fpuControl", line.replaceAll("^.*control word:", "").trim().split(":")[0]);
				} else if (line.matches(".*tag.*")) {
					ht.put("fpuTag", line.replaceAll("^.*tag word:", "").trim().split(":")[0]);
				} else if (line.matches(".*operand.*")) {
					ht.put("fpuOperand", line.replaceAll("^.*operand:", "").trim().split(":")[0]);
				} else if (line.matches("fip.*")) {
					ht.put("fip", line.split(":")[1].trim());
				} else if (line.matches("fcs.*")) {
					ht.put("fcs", line.split(":")[1].trim());
				} else if (line.matches("fdp.*")) {
					ht.put("fdp", line.split(":")[1].trim());
				} else if (line.matches("fds.*")) {
					ht.put("fds", line.split(":")[1].trim());
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		try {
			// mmx
			String result = sendBochsCommand("mmx");
			String[] lines = result.split("\n");

			for (String line : lines) {
				line = line.trim(); // string::matches will break is string contains \r
				if (line.matches(".*MM\\[0\\].*")) {
					ht.put("mm0", line.replaceAll("^.*]:", "").trim().replaceAll("_", ""));
				} else if (line.matches(".*MM\\[1\\].*")) {
					ht.put("mm1", line.replaceAll("^.*]:", "").trim().replaceAll("_", ""));
				} else if (line.matches(".*MM\\[2\\].*")) {
					ht.put("mm2", line.replaceAll("^.*]:", "").trim().replaceAll("_", ""));
				} else if (line.matches(".*MM\\[3\\].*")) {
					ht.put("mm3", line.replaceAll("^.*]:", "").trim().replaceAll("_", ""));
				} else if (line.matches(".*MM\\[4\\].*")) {
					ht.put("mm4", line.replaceAll("^.*]:", "").trim().replaceAll("_", ""));
				} else if (line.matches(".*MM\\[5\\].*")) {
					ht.put("mm5", line.replaceAll("^.*]:", "").trim().replaceAll("_", ""));
				} else if (line.matches(".*MM\\[6\\].*")) {
					ht.put("mm6", line.replaceAll("^.*]:", "").trim().replaceAll("_", ""));
				} else if (line.matches(".*MM\\[7\\].*")) {
					ht.put("mm7", line.replaceAll("^.*]:", "").trim().replaceAll("_", ""));
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ht;
	}

	@Override
	public int[] physicalMemory(BigInteger addr, int noOfByte) {
		return getMemory(addr, noOfByte, true);
	}

	@Override
	public int[] virtualMemory(BigInteger addr, int noOfByte) {
		return getMemory(addr, noOfByte, false);
	}

	public int[] getMemory(BigInteger address, int totalByte, boolean isPhysicalAddress) {
		try {
			int bytes[] = new int[totalByte];
			if (totalByte > 0) {
				//				float totalByte2 = totalByte - 1;
				//				totalByte2 = totalByte2 / 8;
				//				int totalByte3 = (int) Math.floor(totalByte2);
				//				String realEndAddressStr;
				//				String realStartAddressStr;
				//				BigInteger realStartAddress = address;
				//				realStartAddressStr = String.format("%08x", realStartAddress);
				//				BigInteger realEndAddress = realStartAddress.add(BigInteger.valueOf(totalByte3 * 8));
				//				realEndAddressStr = String.format("%08x", realEndAddress);

				String result;
				if (isPhysicalAddress) {
					result = sendBochsCommand("xp /" + totalByte + "bx " + address);
				} else {
					result = sendBochsCommand("x /" + totalByte + "bx " + address);
				}

				if (result != null) {
					String[] lines = result.split("\n");
					int offset = 0;
					// logger.debug(result);
					for (int y = 0; y < lines.length; y++) {
						String[] b = lines[y].replaceFirst("^.*:", "").split("\t");
						// logger.debug(lines[y]);

						for (int x = 1; x < b.length && offset < totalByte; x++) {
							// logger.debug(offset + "==" + x);
							bytes[offset] = (int) CommonLib.string2long(b[x]);
							offset++;
						}
					}
				}
			}
			return bytes;
		} catch (OutOfMemoryError ex) {
			System.gc();
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public Vector<Vector<String>> gdt(BigInteger gdtAddress, int noOfByte) {
		Vector<Vector<String>> r = new Vector<Vector<String>>();
		String result = sendBochsCommand("info gdt 0 " + noOfByte / 8);
		if (result != null) {
			String lines[] = result.split("\n");
			for (int x = 1; x < lines.length; x++) {
				try {
					Vector<String> v = new Vector<String>();
					v.add(lines[x].replaceFirst("^.*\\[", "").replaceFirst("].*$", ""));
					v.add(lines[x].replaceFirst("^.*]=", ""));
					r.add(v);
				} catch (Exception ex) {
				}
			}
		}
		return r;
	}

	@Override
	public Vector<Vector<String>> idt(BigInteger gdtAddress, int noOfByte) {
		Vector<Vector<String>> r = new Vector<Vector<String>>();
		String result = sendBochsCommand("info idt 0 " + noOfByte / 8);

		if (result != null) {
			String lines[] = result.split("\n");
			for (int x = 0; x < lines.length; x++) {
				if (!lines[x].contains("[")) {
					continue;
				}
				Vector<String> v = new Vector<String>();
				v.add(lines[x].replaceFirst("^.*\\[", "").replaceFirst("].*$", ""));
				v.add(lines[x].replaceFirst("^.*]=", ""));
				r.add(v);
			}
		}
		return r;
	}

	@Override
	public Vector<Vector<String>> ldt(BigInteger gdtAddress, int noOfByte) {
		Vector<Vector<String>> r = new Vector<Vector<String>>();
		String result = sendBochsCommand("info ldt 0 " + noOfByte / 8);
		String lines[] = result.split("\n");
		for (int x = 1; x < lines.length; x++) {
			if (!lines[x].contains("[")) {
				continue;
			}
			Vector<String> v = new Vector<String>();
			v.add(lines[x].replaceFirst("^.*\\[", "").replaceFirst("].*$", ""));
			v.add(lines[x].replaceFirst("^.*]=", ""));
			r.add(v);
		}
		return r;
	}

	@Override
	public Vector<String[]> pageTable(BigInteger pageDirectoryBaseAddress, boolean pse, boolean pae) {
		Vector<String[]> r = new Vector<String[]>();
		int[] bytes = physicalMemory(pageDirectoryBaseAddress, 4096);
		if (bytes != null) {
			for (int x = 0; x < bytes.length; x += 4) {
				// "No.", "PT base", "AVL", "G",
				// "D", "A", "PCD", "PWT",
				// "U/S", "W/R", "P"
				if (!pae) {
					if (!pse) {
						// normal 4k
						long value = CommonLib.getInt(bytes, x);
						long baseL = value & 0xfffff000;
						String base = "0x" + Long.toHexString(baseL);
						String avl = String.valueOf((value >> 9) & 3);
						String g = String.valueOf((value >> 8) & 1);
						String ps = String.valueOf((value >> 7) & 1);
						String d = String.valueOf((value >> 6) & 1);
						String a = String.valueOf((value >> 5) & 1);
						String pcd = String.valueOf((value >> 4) & 1);
						String pwt = String.valueOf((value >> 3) & 1);
						String us = String.valueOf((value >> 2) & 1);
						String wr = String.valueOf((value >> 1) & 1);
						String p = String.valueOf((value >> 0) & 1);

						r.add(new String[] { String.valueOf(x / 4), base, avl, g, ps, d, a, pcd, pwt, us, wr, p });
					} else {
						long value = CommonLib.getInt(bytes, x);
						String avl = String.valueOf((value >> 9) & 3);
						String g = String.valueOf((value >> 8) & 1);
						String ps = String.valueOf((value >> 7) & 1);
						String d = String.valueOf((value >> 6) & 1);
						String a = String.valueOf((value >> 5) & 1);
						String pcd = String.valueOf((value >> 4) & 1);
						String pwt = String.valueOf((value >> 3) & 1);
						String us = String.valueOf((value >> 2) & 1);
						String wr = String.valueOf((value >> 1) & 1);
						String p = String.valueOf((value >> 0) & 1);
						String base = "";
						if (ps.equals("1")) {
							long baseL = value & 0xffc00000;
							base = "0x" + Long.toHexString(baseL);
						} else {
							long baseL = value & 0xfffff000;
							base = "0x" + Long.toHexString(baseL);
						}
						//						logger.debug((x / 4) + "=" + value);
						r.add(new String[] { String.valueOf(x / 4), base, avl, g, ps, d, a, pcd, pwt, us, wr, p });
					}
				} else {
					logger.error("Not support pae");
				}
			}
		}
		return r;
	}

	@Override
	public Vector<String> stack() {
		Vector<String> r = new Vector<String>();
		try {
			String result = sendBochsCommand("print-stack 40");
			String[] lines = result.split("\n");
			for (int y = 1; y < lines.length; y++) {
				try {
					String[] b = lines[y].split("[\\[\\]]");
					r.add(b[1]);
				} catch (Exception ex2) {
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return r;
	}

	@Override
	public Vector<Vector<String>> breakpoint() {
		Vector<Vector<String>> r = new Vector<Vector<String>>();
		String result = sendBochsCommand("info break");
		String[] lines = result.split("\n");

		for (int x = 0; x < lines.length; x++) {
			if (lines[x].contains("breakpoint")) {
				Vector<String> strs = new Vector<String>(Arrays.asList(lines[x].trim().split(" \\s")));
				strs.add("0"); // hit count
				if (strs.size() > 1) {
					strs.remove(1);
					r.add(strs);
				}
			}
		}
		return r;
	}

	@Override
	public String disasm(BigInteger eip) {
		return sendBochsCommand("disasm " + eip);
	}

	@Override
	public Vector<Vector<String>> addressTranslate() {
		Vector<Vector<String>> r = new Vector<Vector<String>>();
		String result = sendBochsCommand("info tab");
		String[] lines = result.split("\n");
		for (int x = 1; x < lines.length; x++) {
			Vector<String> strs = new Vector<String>(Arrays.asList(lines[x].trim().split("->")));
			r.add(strs);
		}
		return r;
	}

	@Override
	public void singleStep() {
		sendBochsCommand("s");
	}

	@Override
	public void stepOver() {
		sendBochsCommand("next");
	}

	@Override
	public void addPhysicalBreakpoint(BigInteger address) {
		sendBochsCommand("pb " + address);
	}

	@Override
	public void addLinearBreakpoint(BigInteger address) {
		sendBochsCommand("lb " + address);
	}

	@Override
	public void addVirtualBreakpoint(BigInteger segment, BigInteger address) {
		sendBochsCommand("vb " + segment + ":" + address);
	}

	@Override
	public String sendVMCommand(String command) {
		try {
			command = command.toLowerCase().trim();
			commandOutputStream.write(command + "\n");
			commandOutputStream.flush();
			return commandReceiver.getCommandResult();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deletePhysicalBreakpoint(BigInteger breakpointNo) {
		sendBochsCommand("del " + breakpointNo);
	}

	@Override
	public void enablePhysicalBreakpoint(BigInteger breakpointNo) {
		sendBochsCommand("bpe " + breakpointNo);
	}

	@Override
	public void disablePhysicalBreakpoint(BigInteger breakpointNo) {
		sendBochsCommand("bpd " + breakpointNo);
	}

	@Override
	public void deleteLinearBreakpoint(BigInteger breakpointNo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enableLinearBreakpoint(BigInteger breakpointNo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void disableLinearBreakpoint(BigInteger breakpointNo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMemory(BigInteger address, int b) {
		sendBochsCommand("setpmem " + address + " 1 " + b);
	}

	@Override
	public void changeReigsterValue(String register, BigInteger value) {
		sendBochsCommand("set " + register + "=" + value.toString());
	}

	@Override
	public void waitVMStop() {
		commandReceiver.getCommandResult();
	}

}
