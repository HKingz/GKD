package com.gkd.instrument;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.h2.tools.DeleteDbFiles;

import com.gkd.GKD;
import com.gkd.Global;
import com.gkd.hibernate.HibernateUtil;
import com.gkd.instrument.callgraph.JmpData;
import com.gkd.instrument.callgraph.Parameter;
import com.gkd.sourceleveldebugger.CodeBaseData;
import com.gkd.sourceleveldebugger.SourceLevelDebugger;
import com.peterdwarf.dwarf.CompileUnit;
import com.peterdwarf.dwarf.DebugInfoEntry;
import com.peterdwarf.dwarf.Dwarf;
import com.peterdwarf.dwarf.DwarfDebugLineHeader;
import com.peterdwarf.dwarf.DwarfLib;
import com.peterdwarf.dwarf.DwarfLine;
import com.peterdwarf.dwarf.DwarfParameter;
import com.peterdwarf.elf.Elf32_Sym;
import com.peterswing.CommonLib;

public class JmpSocketServer implements Runnable {
	private int port;
	private boolean shouldStop;
	private ServerSocket serverSocket;
	FileWriter fstream;
	int noOfJmpRecordToFlush = 0;

	public static ConcurrentLinkedQueue<JmpData> jmpDataVector = new ConcurrentLinkedQueue<JmpData>();

	public static Logger logger = Logger.getLogger(JmpSocketServer.class);

	public static Statistic statistic = new Statistic();

	HashMap<Long, Hashtable<String, DwarfParameter>> toAddressCache = new HashMap<Long, Hashtable<String, DwarfParameter>>();
	HashMap<Long, DebugInfoEntry> fromAddressCache = new HashMap<Long, DebugInfoEntry>();

	HashMap<Long, Long> cfaBaseOffsetCache = new HashMap<Long, Long>();

	final int STACK_SIZE = 256;

	public static void main(String args[]) {
		JmpSocketServer jmpSocketServer = new JmpSocketServer();
		jmpSocketServer.startServer(8765, new JmpTableModel());
		jmpSocketServer.stopServer();
	}

	public void startServer(int port, JmpTableModel jmpTableModel) {
		DeleteDbFiles.execute(new File(".").getAbsolutePath(), "jmpDB", true);
		HibernateUtil.getSessionFactory();

		this.port = port;
		try {
			fstream = new FileWriter(Global.jmpLog, false);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		shouldStop = false;
		new Thread(new DBThread()).start();
		new Thread(this).start();
	}

	public void stopServer() {
		shouldStop = true;
		try {
			if (serverSocket != null) {
				serverSocket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		logger.debug("Jmp server start at port " + port);

		try {
			serverSocket = new ServerSocket(port);

			Socket clientSocket = serverSocket.accept();
			DataInputStream in = new DataInputStream(clientSocket.getInputStream());
			DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

			int physicalAddressSize = in.read();
			int segmentAddressSize = in.read();
			int whatSize = in.read();
			int registerSize = in.read();
			int segmentRegisterSize = in.read();

			int lineNo = 1;
			int rowSize = physicalAddressSize * 2 + whatSize + segmentAddressSize * 2 + registerSize * 8 + segmentRegisterSize * 6 + STACK_SIZE;

			int deep = 0;
			logger.debug("client connected, port=" + clientSocket.getPort());

			while (!shouldStop) {
				byte startBytes[] = new byte[5];
				in.readFully(startBytes);
				String beacon = new String(startBytes);
				if (!beacon.equals("start")) {
					fstream.write("jmp socket - beacon error\n");
					fstream.flush();
					System.err.println("jmp socket error beacon!=start");
					System.exit(-1);
				}

				byte[] tempBytes = new byte[4];
				in.readFully(tempBytes);
				noOfJmpRecordToFlush = ByteBuffer.wrap(tempBytes).order(ByteOrder.LITTLE_ENDIAN).getInt();
				//logger.debug("jmp server incoming record = " + noOfJmpRecordToFlush);

				long fromAddress[] = new long[noOfJmpRecordToFlush];
				long toAddress[] = new long[noOfJmpRecordToFlush];
				long what[] = new long[noOfJmpRecordToFlush];
				long segmentStart[] = new long[noOfJmpRecordToFlush];
				long segmentEnd[] = new long[noOfJmpRecordToFlush];

				long eax[] = new long[noOfJmpRecordToFlush];
				long ecx[] = new long[noOfJmpRecordToFlush];
				long edx[] = new long[noOfJmpRecordToFlush];
				long ebx[] = new long[noOfJmpRecordToFlush];
				long esp[] = new long[noOfJmpRecordToFlush];
				long ebp[] = new long[noOfJmpRecordToFlush];
				long esi[] = new long[noOfJmpRecordToFlush];
				long edi[] = new long[noOfJmpRecordToFlush];

				long es[] = new long[noOfJmpRecordToFlush];
				long cs[] = new long[noOfJmpRecordToFlush];
				long ss[] = new long[noOfJmpRecordToFlush];
				long ds[] = new long[noOfJmpRecordToFlush];
				long fs[] = new long[noOfJmpRecordToFlush];
				long gs[] = new long[noOfJmpRecordToFlush];

				byte stack[][] = new byte[noOfJmpRecordToFlush][STACK_SIZE];

				byte bytes[] = new byte[noOfJmpRecordToFlush * rowSize];

				int deeps[] = new int[noOfJmpRecordToFlush];
				boolean showForDifferentDeeps[] = new boolean[noOfJmpRecordToFlush];

				int byteRead = 0;
				while (byteRead < bytes.length) {
					int b = in.read(bytes, byteRead, bytes.length - byteRead);
					if (b < 0) {
						System.err.println("b<0");
						System.exit(-1);
					}
					byteRead += b;
				}

				int offset = 0;
				offset += read(fromAddress, bytes, offset, physicalAddressSize);
				offset += read(toAddress, bytes, offset, physicalAddressSize);

				offset += read(what, bytes, offset, whatSize);

				offset += read(segmentStart, bytes, offset, segmentAddressSize);
				offset += read(segmentEnd, bytes, offset, segmentAddressSize);

				offset += read(eax, bytes, offset, registerSize);
				offset += read(ecx, bytes, offset, registerSize);
				offset += read(edx, bytes, offset, registerSize);
				offset += read(ebx, bytes, offset, registerSize);
				offset += read(esp, bytes, offset, registerSize);
				offset += read(ebp, bytes, offset, registerSize);
				offset += read(esi, bytes, offset, registerSize);
				offset += read(edi, bytes, offset, registerSize);

				offset += read(es, bytes, offset, segmentRegisterSize);
				offset += read(cs, bytes, offset, segmentRegisterSize);
				offset += read(ss, bytes, offset, segmentRegisterSize);
				offset += read(ds, bytes, offset, segmentRegisterSize);
				offset += read(fs, bytes, offset, segmentRegisterSize);
				offset += read(gs, bytes, offset, segmentRegisterSize);

				offset += read(stack, bytes, STACK_SIZE, offset, 1);

				byte endBytes[] = new byte[3];
				in.readFully(endBytes);

				beacon = new String(endBytes);
				if (!beacon.equals("end")) {
					fstream.write("jmp socket - beacon error\n");
					fstream.flush();
					System.err.println("jmp socket error beacon!=end");
					System.exit(-1);
				}

				for (int x = 0; x < noOfJmpRecordToFlush; x++) {
					deeps[x] = deep;
					switch ((int) what[x]) {
					case 12:
						deep++;
						break;
					case 13:
						deep++;
						break;
					case 14:
						deep--;
						break;
					case 15:
						deep--;
						break;
					case 16:
						deep++;
						break;
					case 17:
						deep++;
						break;
					case 18:
						deep--;
						break;
					case 19:
						deep++;
						break;
					case 20:
						deep--;
						break;
					}

					if (deep != deeps[x]) {
						showForDifferentDeeps[x] = true;
					} else {
						showForDifferentDeeps[x] = false;
					}
				}
				Vector<Long> fromNullSymbols = new Vector<Long>();
				Vector<Long> toNullSymbols = new Vector<Long>();

				Vector<CodeBaseData> data = new Vector<CodeBaseData>();
				initCodeDaseData(data);

				for (int x = 0; x < noOfJmpRecordToFlush; x++) {
					try {
						CompileUnit fromCU = GKD.sourceLevelDebugger.peterDwarfPanel.getCompileUnit(fromAddress[x]);
						CompileUnit toCU = GKD.sourceLevelDebugger.peterDwarfPanel.getCompileUnit(toAddress[x]);

						String fromAddress_DW_AT_name = null;
						String toAddress_DW_AT_name = null;

						if (fromCU != null) {
							fromAddress_DW_AT_name = fromCU.DW_AT_name;
						}
						if (toCU != null) {
							toAddress_DW_AT_name = toCU.DW_AT_name;
						}

						String fromAddressDescription = null;
						String toAddressDescription = null;

						if (!fromNullSymbols.contains(fromAddress[x])) {
							Elf32_Sym symbol = SourceLevelDebugger.symbolTableModel.searchSymbol(fromAddress[x]);
							if (symbol == null) {
								fromNullSymbols.add(fromAddress[x]);
							} else {
								fromAddressDescription = symbol.name;
							}
						}

						for (CodeBaseData d : data) {
							if (d.PC.compareTo(BigInteger.valueOf(fromAddress[x])) >= 0) {
								if (fromAddressDescription == null) {
									fromAddressDescription = "";
								}
								fromAddressDescription += " : " + d.lineNo;
								break;
							}
						}

						String toAddressSymbol = null;
						if (!toNullSymbols.contains(toAddress[x])) {
							Elf32_Sym symbol = SourceLevelDebugger.symbolTableModel.searchSymbol(toAddress[x]);
							if (symbol == null) {
								toNullSymbols.add(toAddress[x]);
							} else {
								toAddressDescription = symbol.name;
								toAddressSymbol = symbol.name;
							}
						}

						for (CodeBaseData d : data) {
							if (d.PC.compareTo(BigInteger.valueOf(toAddress[x])) >= 0) {
								if (toAddressDescription == null) {
									toAddressDescription = "";
								}
								toAddressDescription += " : " + d.lineNo;
								break;
							}
						}

						JmpData jmpData = new JmpData(lineNo, new Date(), fromAddress[x], fromAddressDescription, toAddress[x], toAddressDescription, toAddressSymbol,
								(int) what[x], segmentStart[x], segmentEnd[x], eax[x], ecx[x], edx[x], ebx[x], esp[x], ebp[x], esi[x], edi[x], es[x], cs[x], ss[x], ds[x], fs[x],
								gs[x], deeps[x], fromAddress_DW_AT_name, toAddress_DW_AT_name, showForDifferentDeeps[x], stack[x]);

						Hashtable<String, DwarfParameter> parameters;

						if (toAddressCache.containsKey(toAddress[x])) {
							parameters = toAddressCache.get(toAddress[x]);
						} else {
							parameters = DwarfLib.getParameters(GKD.sourceLevelDebugger.peterDwarfPanel.dwarfs, toAddress[x]);
							toAddressCache.put(toAddress[x], parameters);
						}

						//CIE
						long cfsBaseOffset = -1;

						if (cfaBaseOffsetCache.containsKey(toAddress[x])) {
							cfsBaseOffset = cfaBaseOffsetCache.get(toAddress[x]);
						} else {
							for (int z = 0; z < GKD.sourceLevelDebugger.peterDwarfPanel.dwarfs.get(0).ehFrames.get(0).fieDetailsKeys.size(); z++) {
								if (GKD.sourceLevelDebugger.peterDwarfPanel.dwarfs.get(0).ehFrames.get(0).fieDetailsKeys.get(z).equals("DW_CFA_def_cfa")) {
									cfsBaseOffset = (long) GKD.sourceLevelDebugger.peterDwarfPanel.dwarfs.get(0).ehFrames.get(0).fieDetails.get(z)[2];
								}
							}

							cfaBaseOffsetCache.put(toAddress[x], cfsBaseOffset);
						}
						// shit, i hardcoded dwarfs.get(0), fix later
						//System.out.println("cfsBaseOffset=" + cfsBaseOffset);
						//CIE end

						if (parameters != null) {
							for (String parameterName : parameters.keySet()) {
								DwarfParameter parameter = parameters.get(parameterName);
								//								System.out.println("write " + parameter.offset);
								//System.out.println("\tParameter=" + parameter);
								//								out.writeByte(0);
								//								out.writeLong(Long.reverseBytes(parameter.offset));

								//								tempBytes = new byte[8];
								//								in.readFully(tempBytes);
								//								long value = ByteBuffer.wrap(tempBytes).order(ByteOrder.LITTLE_ENDIAN).getLong();

								long value = stack[x][(int) parameter.offset];
								jmpData.parameters.add(new Parameter(jmpData, parameter.name, parameter.type, String.valueOf(parameter.offset), value));
							}
						}

						jmpDataVector.add(jmpData);
						if (lineNo % 100000 == 0) {
							logger.debug("processed " + lineNo);
							GKD.instrumentStatusLabel.setText("Jump instrumentation : " + JmpSocketServer.statistic);
						}
						if (lineNo % 1000 == 0) {
							JmpSocketServer.statistic.noOfCachedRecord = jmpDataVector.size();
						}
						lineNo++;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				//statistic.noOfCachedRecord += jmpDataVector.size();
				GKD.instrumentStatusLabel.setText("Jump instrumentation : " + JmpSocketServer.statistic);

				//				out.writeByte(1);
				out.write("done".getBytes());

				out.flush();

				System.gc();
			} // end while

			in.close();
			clientSocket.close();
			serverSocket.close();
		} catch (BindException ex) {
			JOptionPane.showMessageDialog(null, "You have turn on the profiling feature but the port " + port + " is not available. Program exit", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		} catch (SocketException ex) {
			logger.info("Jmp socket closed");
		} catch (IOException ex2) {
			ex2.printStackTrace();
		}
	}

	private void initCodeDaseData(Vector<CodeBaseData> data) {
		for (Dwarf dwarf : GKD.sourceLevelDebugger.peterDwarfPanel.dwarfs) {
			for (CompileUnit cu : dwarf.compileUnits) {
				DwarfDebugLineHeader header = cu.dwarfDebugLineHeader;
				loop1: for (int xx = 0; xx < header.lines.size(); xx++) {
					try {
						DwarfLine line = header.lines.get(xx);
						File file = cu.dwarfDebugLineHeader.filenames.get((int) line.file_num).file;
						if (!file.exists()) {
							break loop1;
						}
						List<String> sourceLines = FileUtils.readLines(file);

						int endLineNo = 0;
						if (xx == header.lines.size() - 1 || line.file_num != header.lines.get(xx + 1).file_num) {
							endLineNo = sourceLines.size() - line.line_num;
						} else {
							endLineNo = header.lines.get(xx + 1).line_num - 1;
						}
						if (endLineNo - line.line_num < 0) {
							endLineNo = line.line_num;
						}
						String s[] = new String[endLineNo - line.line_num + 1];
						for (int z = line.line_num - 1, index = 0; z < endLineNo && z < sourceLines.size(); z++, index++) {
							String cCode = sourceLines.get(z);
							s[index] = cCode;
						}

						CodeBaseData d = new CodeBaseData();
						d.file = file;
						d.PC = line.address;
						d.codeLines = s;
						d.lineNo = line.line_num;
						data.add(d);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		Collections.sort(data, new Comparator<CodeBaseData>() {
			@Override
			public int compare(CodeBaseData o1, CodeBaseData o2) {
				return o1.PC.compareTo(o2.PC);
			}
		});
	}

	int read(long dest[], byte src[], int offset, int size) throws IOException {
		int totalByteRead = 0;
		for (int x = 0; x < dest.length; x++) {
			long value = read(src, offset + (x * size), size);
			totalByteRead += size;
			dest[x] = value;
		}
		return totalByteRead;
	}

	int read(byte dest[][], byte src[], int secondLevelLength, int offset, int size) throws IOException {
		int totalByteRead = 0;
		for (int x = 0; x < dest.length; x++) {
			for (int y = 0; y < secondLevelLength; y++) {
				byte value = (byte) read(src, offset + (x * size), size);
				totalByteRead += size;
				dest[x][y] = value;
			}
		}
		return totalByteRead;
	}

	long read(byte src[], int offset, int size) throws IOException {
		if (size == 8) {
			return CommonLib.get64BitsInt(src, offset);
		} else if (size == 4) {
			return CommonLib.getInt(src, offset);
		} else if (size == 2) {
			return CommonLib.getShort(src[offset], src[offset + 1]);
		} else {
			return src[offset];
		}
	}
}
