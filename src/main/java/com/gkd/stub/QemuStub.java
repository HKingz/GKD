package com.gkd.stub;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.gkd.Disassemble;
import com.gkd.GKD;
import com.gkd.GKDCommonLib;
import com.gkd.Global;
import com.gkd.Setting;
import com.jlibgdb.JLibGDB;
import com.jlibgdb.QemuMonitor;
import com.peterswing.CommonLib;

public class QemuStub implements VMStub {
	private static QemuStub instance = null;
	String path;
	JLibGDB libGDB;
	GKD gkd;
	Process p;
	InputStream qemuInputStream;
	private String arguments;
	public static Logger logger = Logger.getLogger(QemuStub.class);

	public void initStub(String para[]) {
		libGDB = new JLibGDB(para[0], Integer.parseInt(para[1]));
	}

	public static QemuStub getInstance() {
		if (instance == null) {
			instance = new QemuStub();
		}

		return instance;
	}

	@Override
	public void setVMPath(String path) {
		this.path = path;
	}

	@Override
	public void startVM() {
		if (p != null) {
			p.destroy();
		}

		try {
			//String command = "/opt/local/bin/qemu-system-x86_64 -hda hd10meg.img -gdb tcp::1234 -k en-us -S";
			//String command = "/Users/peter/qemu/bin/qemu-system-x86_64 -hda hd10meg.img -s -S -chardev socket,id=qmp,host=0.0.0.0,port=4444,server,nowait -mon chardev=qmp,mode=control";
			//String command = "/Users/peter/qemu/bin/qemu-system-x86_64 -hda hd10meg.img -s -S -chardev socket,id=qmp,host=0.0.0.0,port=4444,server,nowait -mon chardev=qmp,mode=control";

			p = Runtime.getRuntime().exec(path + " " + arguments);
			qemuInputStream = p.getInputStream();
			new Thread("Qemu stub") {
				public void run() {
					try {
						int x;
						while ((x = qemuInputStream.read()) != -1) {
							System.out.print((char) x);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}.start();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void stopVM() {
		try {
			Runtime.getRuntime().exec(Global.stopCommand);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isRunning() {
		return libGDB.isRunning();
	}

	@Override
	public void runVM() {
		libGDB._continue();
	}

	@Override
	public void pauseVM() {
		libGDB.ctrlC();
	}

	@Override
	public void setGKDInstance(GKD gkd) {
		this.gkd = gkd;
	}

	@Override
	public String getVersion() {
		return null;
	}

	@Override
	public void setVMArguments(String arguments) {
		this.arguments = arguments;
	}

	@Override
	public Vector<String[]> instruction(BigInteger virtualAddress, boolean is32Bit) {
		Vector<String[]> r = new Vector<String[]>();
		//		if (address == null) {
		//			eip = eip.and(CommonLib.string2BigInteger("0xffffffffffffffff"));
		//			address = cs.add(eip);
		//		}
		//		jStatusLabel.setText("Updating instruction");
		int bytes[] = physicalMemory(virtualAddress, 200);
		String result = Disassemble.disassemble(bytes, is32Bit, virtualAddress);
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
						String decodedBytes = temp[1].trim().replaceAll("(..)", "$1 ");
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
						r.add(new String[] { String.valueOf(r.size() - 1), "3", r.get(r.size() - 1)[3] + temp[1].trim().replaceAll("(..)", "$1 ").replaceAll("-", "") });
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
		Hashtable<String, BigInteger> ht = libGDB.register();
		Hashtable<String, String> r = new Hashtable<String, String>();
		Enumeration<String> it = ht.keys();
		while (it.hasMoreElements()) {
			String key = it.nextElement();
			r.put(key, ht.get(key).toString());
		}
		BigInteger cr0 = (BigInteger) ht.get("cr0");
		String cr0Detail = "";
		cr0Detail += CommonLib.getBit(cr0, 31) == 1 ? "PG " : "pg ";
		cr0Detail += CommonLib.getBit(cr0, 30) == 1 ? "CD " : "cd ";
		cr0Detail += CommonLib.getBit(cr0, 29) == 1 ? "NW " : "nw ";
		cr0Detail += CommonLib.getBit(cr0, 18) == 1 ? "AM " : "am ";
		cr0Detail += CommonLib.getBit(cr0, 16) == 1 ? "WP" : "wp";
		r.put("cr0Detail", cr0Detail);

		String cr0Detail2 = "";
		cr0Detail2 += CommonLib.getBit(cr0, 5) == 1 ? "NE " : "ne ";
		cr0Detail2 += CommonLib.getBit(cr0, 4) == 1 ? "ET " : "et ";
		cr0Detail2 += CommonLib.getBit(cr0, 3) == 1 ? "TS " : "ts ";
		cr0Detail2 += CommonLib.getBit(cr0, 2) == 1 ? "EM " : "em ";
		cr0Detail2 += CommonLib.getBit(cr0, 1) == 1 ? "MP " : "mp ";
		cr0Detail2 += CommonLib.getBit(cr0, 0) == 1 ? "PE" : "pe";
		r.put("cr0Detail2", cr0Detail2);
		return r;
	}

	@Override
	public int[] physicalMemory(BigInteger addr, int noOfByte) {
		return libGDB.physicalMemory(addr, noOfByte);
	}

	@Override
	public int[] virtualMemory(BigInteger addr, int noOfByte) {
		return libGDB.virtualMemory(addr, noOfByte);
	}

	@Override
	public Vector<Vector<String>> gdt(BigInteger gdtAddress, int noOfByte) {
		return decodeDescriptors(gdtAddress, noOfByte);
	}

	@Override
	public Vector<Vector<String>> idt(BigInteger idtAddress, int noOfByte) {
		return decodeIDTDescriptors(idtAddress, noOfByte);
	}

	@Override
	public Vector<Vector<String>> ldt(BigInteger ldtAddress, int noOfByte) {
		return decodeDescriptors(ldtAddress, noOfByte);
	}

	private Vector<Vector<String>> decodeDescriptors(BigInteger address, int noOfByte) {
		Vector<Vector<String>> r = new Vector<Vector<String>>();
		if (noOfByte <= 0) {
			return r;
		}

		int allBytes[] = libGDB.virtualMemory(address, noOfByte);
		for (int x = 0; x < noOfByte && x < 400; x += 8) {
			try {
				Vector<String> v = new Vector<String>();
				int bytes[] = new int[8];
				System.arraycopy(allBytes, x, bytes, 0, 8);
				long segmentLimit = CommonLib.getLong(bytes[0], bytes[1], bytes[6] & 0xf, 0, 0, 0, 0, 0);
				if (CommonLib.getBit(bytes[6], 6) == 1) {
					segmentLimit *= 4096;
				}
				long base = CommonLib.getLong(bytes[2], bytes[3], bytes[4], bytes[7], 0, 0, 0, 0);
				int type = bytes[5] & 0xf;
				int system = (int) CommonLib.getBit(bytes[5], 4);
				String typeStr = null;
				if (system == 1) {
					if (type == 0) {
						typeStr = "Data Read-Only";
					} else if (type == 1) {
						typeStr = "Date Read-Only, accessed";
					} else if (type == 2) {
						typeStr = "Data Read/Write";
					} else if (type == 3) {
						typeStr = "Data Read/Write, accessed";
					} else if (type == 4) {
						typeStr = "Data Read-Only, expand-down";
					} else if (type == 5) {
						typeStr = "Data Read-Only, expand-down, accessed";
					} else if (type == 6) {
						typeStr = "Data Read/Write, expand-down";
					} else if (type == 7) {
						typeStr = "Data Read/Write, expand-down, accessed";
					} else if (type == 8) {
						typeStr = "Code Execute-Only";
					} else if (type == 9) {
						typeStr = "Code Execute-Only, accessed";
					} else if (type == 0xa) {
						typeStr = "Code Execute/Read";
					} else if (type == 0xb) {
						typeStr = "Code Execute/Read, accessed";
					} else if (type == 0xc) {
						typeStr = "Code Execute-Only, conforming";
					} else if (type == 0xd) {
						typeStr = "Code Execute-Only, conforming, accessed";
					} else if (type == 0xe) {
						typeStr = "Code Execute/Read, conforming";
					} else if (type == 0xf) {
						typeStr = "Code Execute/Read, conforming, accessed";
					}
				} else {
					if (type == 0) {
						typeStr = "Reserved";
					} else if (type == 1) {
						typeStr = "16-bit TSS (Available)";
					} else if (type == 2) {
						typeStr = "LDT";
					} else if (type == 3) {
						typeStr = "16-bit TSS (Busy)";
						segmentLimit += 1;
					} else if (type == 4) {
						typeStr = "16-bit Call Gate";
					} else if (type == 5) {
						typeStr = "Task Gate";
					} else if (type == 6) {
						typeStr = "16-bit Interrupt Gate";
					} else if (type == 7) {
						typeStr = "16-bit Trap Gate";
					} else if (type == 8) {
						typeStr = "Reserved";
					} else if (type == 9) {
						typeStr = "32-bit TSS (Available)";
						segmentLimit += 1;
					} else if (type == 0xa) {
						typeStr = "Reserved";
					} else if (type == 0xb) {
						typeStr = "32-bit TSS (Busy)";
						segmentLimit += 1;
					} else if (type == 0xc) {
						typeStr = "32-bit Call Gate";
					} else if (type == 0xd) {
						typeStr = "Reserved";
					} else if (type == 0xe) {
						typeStr = "32-bit Interrupt Gate";
					} else if (type == 0xf) {
						typeStr = "32-bit Trap Gate";
					}
				}

				v.add("0x" + Integer.toHexString(x / 8));
				v.add(typeStr + ", base=0x" + Long.toHexString(base) + ", limit=0x" + Long.toHexString(segmentLimit));
				r.add(v);
			} catch (Exception ex) {
			}
		}
		return r;
	}

	private Vector<Vector<String>> decodeIDTDescriptors(BigInteger address, int noOfByte) {
		Vector<Vector<String>> r = new Vector<Vector<String>>();

		if (noOfByte <= 0) {
			return r;
		}

		int allBytes[] = libGDB.virtualMemory(address, noOfByte);
		logger.debug("==" + address);
		for (int x = 0; x < noOfByte && x < 400; x += 8) {
			Vector<String> v = new Vector<String>();
			int bytes[] = new int[8];
			System.arraycopy(allBytes, x, bytes, 0, 8);

			long offset = CommonLib.getLong(bytes[0], bytes[1], bytes[6], bytes[7], 0, 0, 0, 0);
			long selector = CommonLib.getLong(bytes[2], bytes[3], 0, 0, 0, 0, 0, 0);
			int type = bytes[5] & 0xf;
			int system = (int) CommonLib.getBit(bytes[5], 4);
			int dpl = (int) ((CommonLib.getInt(bytes[5], 0, 0, 0) << 5) & 0x3);
			int p = (int) CommonLib.getBit(bytes[5], 7);

			String typeStr = null;
			if (type == 0x5) {
				typeStr = "32 bit Task gate";
			} else if (type == 0x6) {
				typeStr = "16-bit interrupt gate";
			} else if (type == 0x7) {
				typeStr = "16-bit trap gate";
			} else if (type == 0xe) {
				typeStr = "32-bit interrupt gate";
			} else if (type == 0xf) {
				typeStr = "Data 32-bit trap gate";
			}

			v.add("0x" + Integer.toHexString(x / 8));
			v.add(typeStr + ", selector=0x" + Long.toHexString(selector) + ", offset=0x" + Long.toHexString(offset) + ", system=" + system + ", dpl=0x" + Long.toHexString(dpl)
					+ ", p=" + p);
			r.add(v);
		}
		return r;
	}

	@Override
	public Vector<String[]> pageTable(BigInteger pageDirectoryBaseAddress, boolean pse, boolean pae) {
		return new Vector<String[]>();
	}

	@Override
	public Vector<String> stack() {
		return new Vector<String>();
	}

	@Override
	public Vector<Vector<String>> breakpoint() {
		Vector<Vector<String>> r = new Vector<Vector<String>>();
		Vector<com.jlibgdb.Breakpoint> breakpoints = libGDB.listBreakpoint();
		int x = 0;
		for (com.jlibgdb.Breakpoint bp : breakpoints) {
			r.add(new Vector<String>(Arrays.asList(new String[] { String.valueOf(x++), "pbreakpoint", "0x" + bp.addr.toString(16), "0x" + Long.toHexString(bp.flag) })));
		}
		return r;
	}

	@Override
	public String disasm(BigInteger eip) {
		int x[] = physicalMemory(eip, 50);
		return Disassemble.disassemble(x, true, eip);
	}

	@Override
	public Vector<Vector<String>> addressTranslate() {
		return new Vector<Vector<String>>();
	}

	@Override
	public void singleStep() {
		libGDB.singleStep();
	}

	@Override
	public void addPhysicalBreakpoint(BigInteger address) {
		libGDB.physicalBreakpoint(address);
	}

	@Override
	public void addLinearBreakpoint(BigInteger address) {

	}

	@Override
	public void addVirtualBreakpoint(BigInteger segment, BigInteger address) {

	}

	@Override
	public String sendVMCommand(String command) {
		String qmpHost = GKDCommonLib.readConfig(GKD.cmd, "/gkd/qmpHost/text()");
		int qmpPort = GKDCommonLib.readConfigInt(GKD.cmd, "/gkd/qmpPort/text()");
		if (qmpHost == null || qmpPort == -1) {
			return null;
		}
		Setting.getInstance().vmCommandHistory.add(command);
		Setting.getInstance().save();
		String r = null;
		if (command.equals("?")) {
			String qmpCommand = "{ \"execute\": \"qmp_capabilities\" }";
			qmpCommand += "{ \"execute\": \"query-commands\" }";
			r = QemuMonitor.sendCommand(qmpCommand, qmpHost, qmpPort);
			r = r.replaceAll("[,\\[\\]]", "\n");
			r = r.replaceFirst("^.*\n", "");
			List<String> list = Arrays.asList(r.split("\n"));
			Collections.sort(list);
			r = "";
			for (String temp : list) {
				if (temp.split(":").length > 1) {
					r += temp.split(":")[1].replaceAll("\"", "").replaceAll("}", "").trim() + "\n";
				}
			}
		} else if (command.equals("gkd g")) {
			r = libGDB.sendSocketCommand("g");
		} else {
			String qmpCommand = "{ \"execute\": \"qmp_capabilities\" }";
			if (command.contains(" ")) {
				qmpCommand += "{ \"execute\": \"" + command.split(" ")[0] + "\", \"arguments\": { \"command-line\": \"" + command.replaceFirst("^[^ ]*", "") + "\" }}";
			} else {
				qmpCommand += "{ \"execute\": \"" + command + "\" }";
			}
			r = QemuMonitor.sendCommand(qmpCommand, qmpHost, qmpPort);
			r = r.replaceAll("\\\\r\\\\n", "\n");
		}
		return r;
	}

	@Override
	public void stepOver() {

	}

	//	@Override
	//	public String getInstruction(BigInteger address, BigInteger csBaseAddress, BigInteger eip, boolean is32Bit) {
	//		Hashtable<String, String> registers = registers();
	//		logger.debug("ip=" + registers.get("ip"));
	//		int bytes[] = virtualMemory(new BigInteger(registers.get("ip")), 40);
	//		String result = Disassemble.disassemble(bytes, false, BigInteger.valueOf(0));
	//		String instruction = result.split("\n")[0].split("  +")[2];
	//		logger.debug("instruction=" + instruction);
	//		return instruction;
	//	}

	@Override
	public void deletePhysicalBreakpoint(BigInteger breakpointNo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enablePhysicalBreakpoint(BigInteger breakpointNo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void disablePhysicalBreakpoint(BigInteger breakpointNo) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

	@Override
	public void changeReigsterValue(String register, BigInteger value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void waitVMStop() {
		while (isRunning())
			;
	}

	@Override
	public void addPhysicalWatchPoint(BigInteger address, boolean isRead) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletePhysicalWatchPoint(BigInteger breakpointNo) {
		// TODO Auto-generated method stub

	}

}
