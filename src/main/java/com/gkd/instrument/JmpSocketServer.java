package com.gkd.instrument;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.h2.tools.DeleteDbFiles;

import com.gkd.GKD;
import com.gkd.Global;
import com.gkd.hibernate.HibernateUtil;
import com.gkd.instrument.callgraph.JmpData;
import com.gkd.instrument.callgraph.Parameter;
import com.gkd.sourceleveldebugger.SourceLevelDebugger;
import com.peterdwarf.dwarf.CompileUnit;
import com.peterdwarf.dwarf.DebugInfoEntry;
import com.peterdwarf.dwarf.DwarfLib;
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

	public static void main(String args[]) {
		JmpSocketServer jmpSocketServer = new JmpSocketServer();
		jmpSocketServer.startServer(8765, new JmpTableModel());
		jmpSocketServer.stopServer();
	}

	HashMap<Long, DebugInfoEntry> cache = new HashMap<Long, DebugInfoEntry>();

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
			int rowSize = physicalAddressSize * 2 + whatSize + segmentAddressSize * 2 + registerSize * 8 + segmentRegisterSize * 6;

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

						if (!toNullSymbols.contains(toAddress[x])) {
							Elf32_Sym symbol = SourceLevelDebugger.symbolTableModel.searchSymbol(toAddress[x]);
							if (symbol == null) {
								toNullSymbols.add(toAddress[x]);
							} else {
								toAddressDescription = symbol.name;
							}
						}

						JmpData jmpData = new JmpData(lineNo, new Date(), fromAddress[x], fromAddressDescription, toAddress[x], toAddressDescription, (int) what[x],
								segmentStart[x], segmentEnd[x], eax[x], ecx[x], edx[x], ebx[x], esp[x], ebp[x], esi[x], edi[x], es[x], cs[x], ss[x], ds[x], fs[x], gs[x], deeps[x],
								fromAddress_DW_AT_name, toAddress_DW_AT_name, showForDifferentDeeps[x]);

						DebugInfoEntry debugInfoEntry;
						if (cache.containsKey(toAddress[x])) {
							debugInfoEntry = cache.get(toAddress[x]);
						} else {
							debugInfoEntry = DwarfLib.getSubProgram(GKD.sourceLevelDebugger.peterDwarfPanel.dwarfs, toAddress[x]);
							cache.put(toAddress[x], debugInfoEntry);
						}

						if (debugInfoEntry != null) {
							Vector<DebugInfoEntry> v = debugInfoEntry.getDebugInfoEntryByName("DW_TAG_formal_parameter");
							for (DebugInfoEntry d : v) {
								if (d.debugInfoAbbrevEntries.get("DW_AT_name") != null) {
									jmpData.parameters.add(new Parameter(jmpData, (String) d.debugInfoAbbrevEntries.get("DW_AT_name").value));
								}
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
				//	statistic.noOfCachedRecord += jmpDataVector.size();
				GKD.instrumentStatusLabel.setText("Jump instrumentation : " + JmpSocketServer.statistic);
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

	int read(long arr[], byte bytes[], int offset, int size) throws IOException {
		int totalByteRead = 0;
		for (int x = 0; x < arr.length; x++) {
			long value = read(bytes, offset + (x * size), size);
			totalByteRead += size;
			arr[x] = value;
		}
		return totalByteRead;
	}

	long read(byte bytes[], int offset, int size) throws IOException {
		if (size == 8) {
			return CommonLib.get64BitsInt(bytes, offset);
		} else if (size == 4) {
			return CommonLib.getInt(bytes, offset);
		} else if (size == 2) {
			return CommonLib.getShort(bytes[offset], bytes[offset + 1]);
		} else {
			return bytes[offset];
		}
	}
}
