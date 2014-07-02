package com.gkd.stub;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.gkd.CommandReceiver;
import com.gkd.GKD;
import com.gkd.GKD.OSType;
import com.gkd.Disassemble;
import com.gkd.Global;
import com.gkd.MyLanguage;
import com.gkd.Setting;
import com.gkd.instrument.InterruptSocketServerController;
import com.gkd.instrument.JmpSocketServerController;
import com.gkd.instrument.MemorySocketServerController;
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
		/*
		try {
			path = "/Users/peter/install/bin/bochs";
			arguments = "-f bochsrc.txt -q";
			ProcessBuilder pb = new ProcessBuilder((path + " " + arguments).split(" "));
			pb.redirectErrorStream(true);
			p = pb.start();
			InputStream is = p.getInputStream();
			commandReceiver = new CommandReceiver(is, gkd);
			new Thread(commandReceiver, "commandReceiver thread").start();
			commandOutputStream = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));

			Date date1 = new Date();
			while (commandReceiver.getLinesLength() < 9) {
				Thread.sleep(100);
				if (new Date().getTime() - date1.getTime() > 4000) {
					break;
				}
			}
			String versionLines[] = commandReceiver.getCommandAllResult().split("\n");
			for (String line : versionLines) {
				if (line.contains("Bochs x86 Emulator")) {
					version = line.trim();
				}
				if (line.contains("Peter-bochs instrument")) {
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
				}
			}
			for (int x = 0; x < 2; x++) {
				//				sendBochsCommand("r");
				String result = sendBochsCommand("r", "eax:", "eflags");//commandReceiver.getCommandResult("ax:", "eflags", null);
				System.out.println(result);

				//				sendBochsCommand("sreg");
				result = sendBochsCommand("sreg", "es:", "idtr:");//commandReceiver.getCommandResult("s:", "idtr:", null);
				System.out.println(result);

				//				sendBochsCommand("creg");
				result = sendBochsCommand("creg", "CR0=", "EFER=");// commandReceiver.getCommandResult("CR0", "CR4", null);
				System.out.println(result);

				sendBochsCommand("dreg", "DR0=", "DR7=");
				//				result = sendBochsCommand("dreg");//commandReceiver.getCommandResult("DR0", "DR7", null);
				System.out.println(result);

				//				sendBochsCommand("fpu");
				result = sendBochsCommand("fpu", "status  word", "FP7 ST7");//commandReceiver.getCommandResult("status", "FP7", null);
				System.out.println(result);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		*/
	}

	public void initStub(String para[]) {

	}

	private String sendBochsCommand(String command, String startPattern, String endPattern) {
		try {
			logger.debug("sendBochsCommand " + command);
			command = command.toLowerCase().trim();
			//			commandReceiver.waitUntilNoInput();
			//			commandReceiver.clearBuffer();
			Global.lastCommand = command;
			commandOutputStream.write(command + "\n");
			commandOutputStream.flush();
			if (!command.equals("6") && !command.equals("c") && !command.startsWith("pb") && !command.startsWith("vb") && !command.startsWith("lb") && !command.startsWith("bpd")
					&& !command.startsWith("bpe") && !command.startsWith("del") && !command.startsWith("set")) {
				commandReceiver.waitUntilHaveLine(1);
				if (startPattern == null || endPattern == null) {
					return null;
				} else {
					return commandReceiver.getCommandResult(startPattern, endPattern);
				}
			}
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
			ProcessBuilder pb = new ProcessBuilder((path + " " + arguments).split(" "));
			pb.redirectErrorStream(true);
			p = pb.start();
			InputStream is = p.getInputStream();
			commandReceiver = new CommandReceiver(is, gkd);
			new Thread(commandReceiver, "commandReceiver thread").start();
			commandOutputStream = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));

			Date date1 = new Date();
			while (commandReceiver.getLinesLength() < 9) {
				Thread.sleep(100);
				if (new Date().getTime() - date1.getTime() > 4000) {
					break;
				}
			}
			String versionLines[] = commandReceiver.getCommandResultUntilEnd().split("\n");
			for (String line : versionLines) {
				if (line.contains("Bochs x86 Emulator")) {
					version = line.trim();
				}
				if (line.contains("Peter-bochs instrument")) {
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
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void stopVM() {
		logger.debug("stopVM");
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
		commandReceiver.clearBuffer();
		sendBochsCommand("c", null, null);
	}

	@Override
	public void pauseVM() {
		logger.debug("pauseVM");
		try {
			commandReceiver.clearBuffer();
			commandReceiver.waitUntilNoInput();
			if (GKD.os == OSType.mac || GKD.os == OSType.linux) {
				ProcessBuilder pb = new ProcessBuilder("killall", "-2", "bochs");
				pb.start();
			} else {
				ProcessBuilder pb = new ProcessBuilder("PauseBochs.exe");
				pb.start();
			}
		} catch (Exception ex) {
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
	public Vector<String[]> instruction(BigInteger address, BigInteger csBaseAddress, BigInteger eip, boolean is32Bit) {
		logger.debug("updateInstruction " + address + ", " + csBaseAddress + ", " + eip);
		Vector<String[]> r = new Vector<String[]>();
		final int maximumLine = 400;
		//String command;
		int bits = is32Bit ? 32 : 16;
		if (address == null) {
			eip = eip.and(CommonLib.string2BigInteger("0xffffffffffffffff"));
			address = csBaseAddress.add(eip);
			//command = "disasm -b " + bits + " " + address + " " + address.add(BigInteger.valueOf(0x400));
			//command = "disasm -b " + bits + " cs:0x" + eip.toString(16) + " 0x" + csOrBaseAddress.toString(16) + ":0x" + eip.add(BigInteger.valueOf(0x400)).toString(16);
		} else {
			//command = "disasm -b " + bits + " " + address + " " + address.add(BigInteger.valueOf(0x400));
		}

		int bytes[] = physicalMemory(address, 200);
		String result = Disassemble.disassemble(bytes, is32Bit, address);

		String lines[] = result.split("\n");
		if (lines.length > 0) {
			//			InstructionTableModel model = (InstructionTableModel) instructionTable.getModel();
			//			jStatusProgressBar.setMaximum(lines.length - 1);
			for (int x = 0; x < lines.length; x++) {
				//				jStatusProgressBar.setValue(x);
				try {
					// load cCode

					//						logger.info("pcStr=" + pcStr);
					//						if (!pcStr.matches("^[0-9a-fA-F].*")) {
					//							logger.info("fuck ar=" + pcStr);
					//							System.exit(1);
					//						}
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
								r.add(new String[] { "", "cCode : 0x" + pc.toString(16) + " : " + lineNo[index], s[index], "" });
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

		/*
		//commandReceiver.clearBuffer();
		//commandReceiver.shouldShow = false;
		//sendBochsCommand(command, null, null);
		//commandReceiver.waitUntilHaveLine(10);
		//String result = commandReceiver.getCommandResultUntilEnd();
		String lines[] = result.split("\n");
		if (lines.length > 0) {
			//			InstructionTableModel model = (InstructionTableModel) instructionTable.getModel();
			//			jStatusProgressBar.setMaximum(lines.length - 1);
			for (int x = 1; x < lines.length && x < maximumLine; x++) {
				//				jStatusProgressBar.setValue(x);
				try {
					lines[x] = lines[x].replaceFirst("\\<.*\\>", "");
					String strs[] = lines[x].split(":");
					int secondColon = lines[x].indexOf(":", lines[x].indexOf(":") + 1);

					// load cCode
					String pcStr = strs[0].trim();
					BigInteger pc = CommonLib.string2BigInteger("0x" + pcStr);
					if (pc == null) {
						continue;
					}
					String s[] = gkd.getCCode(pc, false);
					String lineNo[] = gkd.getCCode(pc, true);
					if (s != null && lineNo != null) {
						for (int index = 0; index < s.length; index++) {
							//							model.addRow(new String[] { "", "cCode : 0x" + pc.toString(16) + " : " + lineNo[index], s[index], "" });
							r.add(new String[] { "", "cCode : 0x" + pc.toString(16) + " : " + lineNo[index], s[index], "" });
						}
					}
					// end load cCode
					//					model.addRow(new String[] { "", "0x" + pc.toString(16), lines[x].substring(secondColon + 1).trim().split(";")[0].trim(), lines[x].split(";")[1] });
					r.add(new String[] { "", "0x" + pc.toString(16), lines[x].substring(secondColon + 1).trim().split(";")[0].trim(), lines[x].split(";")[1] });
				} catch (Exception ex) {
					//ex.printStackTrace();
					logger.error("Error line : " + lines[x]);
				}
			}

			//			model.removeNonOrderInstruction();
			//			model.fireTableDataChanged();
		}*/
		return r;
	}

	@Override
	public Hashtable<String, String> registers() {
		Hashtable<String, String> ht = new Hashtable<String, String>();
		try {
			commandReceiver.shouldShow = false;
			//			sendBochsCommand("r");
			String result = sendBochsCommand("r", "ax:", "eflags");//commandReceiver.getCommandResult("ax:", "eflags", null);
			result = result.replaceAll("r", "\nr");
			String lines[] = result.split("\n");

			for (String line : lines) {
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
				String result = sendBochsCommand("sreg", "00000000000i", "Next");// commandReceiver.getCommandResult("s:", "idtr:", null);
				// logger.debug(result);
				String[] lines = result.split("\n");

				for (String line : lines) {
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
				//				sendBochsCommand("sreg");
				String result = sendBochsCommand("sreg", "es:", "idtr:");//commandReceiver.getCommandResult("s:", "idtr:", null);
				// logger.debug(result);
				String[] lines = result.split("\n");

				for (String line : lines) {
					line = line.replaceFirst("<.*>", "");
					String str[] = line.split(" ");

					if (line.matches(".*cs:.*")) {
						ht.put("cs", line.split(":")[1].split(",")[0]);
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
			// commandReceiver.setCommandNoOfLine(Integer.parseInt(bochsCommandLength.get(0).get("cregs").toString()));
			//			sendBochsCommand("creg");
			String result = sendBochsCommand("creg", "CR0=", "EFER=");// commandReceiver.getCommandResult("CR0", "CR4", null);
			String[] lines = result.split("\n");

			for (String line : lines) {
				if (line.matches(".*CR0=.*")) {
					line = line.replaceFirst("^.*CR0", "CR0");
					ht.put("cr0", line.split(" ")[0].split("=")[1].replace(":", ""));

					if (CommonLib.getBit(CommonLib.string2long(ht.get("cr0")), 0) == 1) {
						ht.put("mode", MyLanguage.getString("Protected_mode") + "     ");
					} else {
						ht.put("mode", MyLanguage.getString("Real_mode") + "     ");
					}
					String arr[] = line.split(":")[1].split(" ");

					//					registerPanel.cr0DetailLabel.setText("");
					ht.put("cr0Detail", "");
					//					registerPanel.cr0DetailLabel2.setText(" ");
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
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		try {
			if (version.contains("2.4.1")) {
			} else {
				// commandReceiver.setCommandNoOfLine(Integer.parseInt(bochsCommandLength.get(0).get("cregs").toString()));
				//				sendBochsCommand("dreg");
				String result = sendBochsCommand("dreg", "DR0=", "DR7=");// commandReceiver.getCommandResult("DR0", "DR7", null);
				String[] lines = result.split("\n");

				for (String line : lines) {
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
			//			sendBochsCommand("fpu");
			String result = sendBochsCommand("fpu", "status  word", "FP7 ST7");//commandReceiver.getCommandResult("status", "FP7", null);
			String[] lines = result.split("\n");

			for (String line : lines) {
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
			//			sendBochsCommand("mmx");
			String result = sendBochsCommand("mmx", "MM[0]", "MM[7]");//commandReceiver.getCommandResult("MM[0]", "MM[7]", null);
			String[] lines = result.split("\n");

			for (String line : lines) {
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
			commandReceiver.clearBuffer();
			commandReceiver.shouldShow = false;

			int bytes[] = new int[totalByte];
			if (totalByte > 0) {
				float totalByte2 = totalByte - 1;
				totalByte2 = totalByte2 / 8;
				int totalByte3 = (int) Math.floor(totalByte2);
				String realEndAddressStr;
				String realStartAddressStr;
				BigInteger realStartAddress = address;
				realStartAddressStr = String.format("%08x", realStartAddress);
				BigInteger realEndAddress = realStartAddress.add(BigInteger.valueOf(totalByte3 * 8));
				realEndAddressStr = String.format("%08x", realEndAddress);

				String result;
				if (isPhysicalAddress) {
					result = sendBochsCommand("xp /" + totalByte + "bx " + address, realStartAddressStr, realEndAddressStr);
				} else {
					result = sendBochsCommand("x /" + totalByte + "bx " + address, realStartAddressStr, realEndAddressStr);
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
		commandReceiver.shouldShow = false;
		//		String limitStr = String.format("0x%02x", noOfByte);
		String result = sendBochsCommand("info gdt", "GDT", "you can");
		//		String result = commandReceiver.getCommandResult("GDT[0x00]", "GDT[" + limitStr + "]", null);
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
		commandReceiver.clearBuffer();
		commandReceiver.shouldShow = false;
		//		String limitStr = String.format("0x%02x", noOfByte);
		String result = sendBochsCommand("info idt", "IDT", "you can");

		//String result = commandReceiver.getCommandResult(, "limit=0)");

		if (result != null) {
			String lines[] = result.split("\n");
			for (int x = 0; x < lines.length; x++) {
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
		sendBochsCommand("info ldt", "LDT", "you can");
		commandReceiver.waitUntilHaveLine(200);
		String result = commandReceiver.getCommandResultUntilEnd();
		//String result = commandReceiver.getCommandResultUntilEnd();
		String lines[] = result.split("\n");
		for (int x = 1; x < lines.length; x++) {
			Vector<String> v = new Vector<String>();
			v.add(lines[x].replaceFirst("^.*\\[", "").replaceFirst("].*$", ""));
			v.add(lines[x].replaceFirst("^.*]=", ""));
			r.add(v);
		}
		return r;
	}

	@Override
	public Vector<String[]> pageTable(BigInteger pageDirectoryBaseAddress) {
		Vector<String[]> r = new Vector<String[]>();
		commandReceiver.clearBuffer();
		commandReceiver.shouldShow = false;
		// commandReceiver.setCommandNoOfLine(512);
		//		String result = sendBochsCommand("xp /4096bx " + pageDirectoryBaseAddress);
		float totalByte2 = 4096 - 1;
		totalByte2 = totalByte2 / 8;
		int totalByte3 = (int) Math.floor(totalByte2);
		String realEndAddressStr;
		String realStartAddressStr;
		BigInteger realStartAddress = pageDirectoryBaseAddress;
		realStartAddressStr = realStartAddress.toString(16);
		BigInteger realEndAddress = realStartAddress.add(BigInteger.valueOf(totalByte3 * 8));
		realEndAddressStr = String.format("%08x", realEndAddress);
		String result = sendBochsCommand("xp /4096bx " + pageDirectoryBaseAddress, realStartAddressStr, realEndAddressStr);
		if (result != null) {
			String[] lines = result.split("\n");

			for (int y = 0; y < lines.length; y++) {
				String[] b = lines[y].replaceFirst("^.*:", "").trim().split("\t");

				for (int z = 0; z < 2; z++) {
					try {
						int bytes[] = new int[4];
						for (int x = 0; x < 4; x++) {
							bytes[x] = CommonLib.string2BigInteger(b[x + z * 4].substring(2).trim()).intValue();
						}
						long value = CommonLib.getInt(bytes, 0);
						// "No.", "PT base", "AVL", "G",
						// "D", "A", "PCD", "PWT",
						// "U/S", "W/R", "P"

						long baseL = value & 0xfffff000;
						// if (baseL != 0) {
						String base = "0x" + Long.toHexString(baseL);
						String avl = String.valueOf((value >> 9) & 3);
						String g = String.valueOf((value >> 8) & 1);
						String d = String.valueOf((value >> 6) & 1);
						String a = String.valueOf((value >> 5) & 1);
						String pcd = String.valueOf((value >> 4) & 1);
						String pwt = String.valueOf((value >> 3) & 1);
						String us = String.valueOf((value >> 2) & 1);
						String wr = String.valueOf((value >> 1) & 1);
						String p = String.valueOf((value >> 0) & 1);

						//						ia32_pageDirectories.add(new IA32PageDirectory(base, avl, g, d, a, pcd, pwt, us, wr, p));

						r.add(new String[] { String.valueOf(y * 2 + z), base, avl, g, d, a, pcd, pwt, us, wr, p });
						// }
					} catch (Exception ex) {
					}
				}
			}
		}
		return r;
	}

	@Override
	public Vector<String> stack() {
		Vector<String> r = new Vector<String>();
		try {
			commandReceiver.clearBuffer();
			commandReceiver.shouldShow = false;
			sendBochsCommand("print-stack 40", null, null);
			commandReceiver.waitUntilHaveLine(40);
			String result = commandReceiver.getCommandResultUntilEnd();
			//			String result = commandReceiver.getCommandResultUntilHaveLines(40);
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
		sendBochsCommand("info break", null, null);
		commandReceiver.waitUntilHaveLine(1);
		String result = commandReceiver.getCommandResultUntilEnd();
		//		String result = commandReceiver.getCommandResultUntilEnd();
		String[] lines = result.split("\n");

		for (int x = 1; x < lines.length; x++) {
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
		sendBochsCommand("disasm " + eip, null, null);
		commandReceiver.waitUntilHaveLine(1);
		return commandReceiver.getCommandResultUntilEnd();
	}

	@Override
	public Vector<Vector<String>> addressTranslate() {
		Vector<Vector<String>> r = new Vector<Vector<String>>();
		commandReceiver.clearBuffer();
		commandReceiver.shouldShow = false;
		sendBochsCommand("info tab", null, null);
		commandReceiver.waitUntilHaveLine(1);
		String result = commandReceiver.getCommandResultUntilEnd();

		//String result = commandReceiver.getCommandResultUntilEnd();
		String[] lines = result.split("\n");
		for (int x = 1; x < lines.length; x++) {
			Vector<String> strs = new Vector<String>(Arrays.asList(lines[x].trim().split("->")));
			r.add(strs);
		}
		return r;
	}

	@Override
	public void singleStep() {
		sendBochsCommand("s", null, null);
	}

	@Override
	public void stepOver() {
		sendBochsCommand("next", null, null);
	}

	@Override
	public void addPhysicalBreakpoint(BigInteger address) {
		sendBochsCommand("pb " + address, null, null);
	}

	@Override
	public void addLinearBreakpoint(BigInteger address) {
		sendBochsCommand("lb " + address, null, null);
	}

	@Override
	public void addVirtualBreakpoint(BigInteger segment, BigInteger address) {
		sendBochsCommand("vb " + segment + ":" + address, null, null);
	}

	@Override
	public String sendVMCommand(String command) {
		try {
			command = command.toLowerCase().trim();
			commandReceiver.clearBuffer();
			commandOutputStream.write(command + "\n");
			commandOutputStream.flush();
			commandReceiver.waitUntilHaveLine(1);
			return commandReceiver.getCommandResultUntilEnd();

			//			if (!command.equals("6") && !command.equals("c") && !command.startsWith("pb") && !command.startsWith("vb") && !command.startsWith("lb") && !command.startsWith("bpd")
			//					&& !command.startsWith("bpe") && !command.startsWith("del") && !command.startsWith("set")) {
			//				commandReceiver.waitUntilHaveInput();
			//				return;
			//			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deletePhysicalBreakpoint(BigInteger breakpointNo) {
		sendBochsCommand("del " + breakpointNo, null, null);
	}

	@Override
	public void enablePhysicalBreakpoint(BigInteger breakpointNo) {
		sendBochsCommand("bpe " + breakpointNo, null, null);
	}

	@Override
	public void disablePhysicalBreakpoint(BigInteger breakpointNo) {
		sendBochsCommand("bpd " + breakpointNo, null, null);
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
		sendBochsCommand("setpmem " + address + " 1 " + b, null, null);
	}

	@Override
	public void changeReigsterValue(String register, BigInteger value) {
		sendBochsCommand("set " + register + "=" + value.toString(), null, null);
	}

	@Override
	public void waitVMStop() {
		while (commandReceiver.getLinesLength() == 0) {
			try {

				Thread.sleep(200);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
