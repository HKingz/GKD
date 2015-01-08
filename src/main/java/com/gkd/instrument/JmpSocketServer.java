package com.gkd.instrument;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.gkd.Global;
import com.gkd.instrument.callgraph.JmpData;
import com.gkd.sourceleveldebugger.SourceLevelDebugger;
import com.peterdwarf.elf.Elf32_Sym;
import com.peterswing.CommonLib;

public class JmpSocketServer implements Runnable {
	private int port;
	private JmpTableModel jmpTableModel;
	private boolean shouldStop;
	private ServerSocket serverSocket;
	FileWriter fstream;

	//	public static LinkedHashSet<String> segments = new LinkedHashSet<String>();
	private SimpleDateFormat dateformat1 = new SimpleDateFormat("HH:mm:ss.S");
	public static Vector<JmpData> jmpDataVector = new Vector<JmpData>();

	public static void main(String args[]) {
		new JmpSocketServer().startServer(8765, new JmpTableModel());
	}

	public void startServer(int port, JmpTableModel jmpTableModel) {
		this.port = port;
		this.jmpTableModel = jmpTableModel;
		try {
			fstream = new FileWriter(Global.jmpLog, false);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		shouldStop = false;
		new Thread(this).start();

		while (serverSocket != null && !serverSocket.isBound()) {
			try {
				Thread.currentThread().sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void stopServer() {
		shouldStop = true;
		try {
			serverSocket.close();
		} catch (Exception e) {
		}
	}

	@Override
	public void run() {
		if (Global.debug) {
			System.out.println("Jmp server start at port " + port);
		}

		try {
			serverSocket = new ServerSocket(port);
			while (!shouldStop) {
				Socket clientSocket = serverSocket.accept();
				DataInputStream in = new DataInputStream(clientSocket.getInputStream());
				DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

				int physicalAddressSize = in.read();
				int segmentAddressSize = in.read();
				int registerSize = in.read();
				int segmentRegisterSize = in.read();

				int lineNo = 1;
				int totalSize = physicalAddressSize * 2 + segmentAddressSize * 2 + registerSize * 8 + segmentRegisterSize * 6;

				int noOfJmpRecordToFlush = 50000;
				long fromAddress[] = new long[noOfJmpRecordToFlush];
				long toAddress[] = new long[noOfJmpRecordToFlush];
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

				while (!shouldStop) {
					byte bytes[] = new byte[noOfJmpRecordToFlush * totalSize];
					//					System.out.println(">>" + in.read());
					//					System.out.println(">>" + in.readByte());
					//					System.out.println(">>" + in.readByte());
					//					System.out.println(">>" + in.readByte());

					//					System.out.println("wait start");
					//					String beacon = String.valueOf((char) in.read()) + (char) in.read() + (char) in.read() + (char) in.read() + (char) in.read();
					byte startBytes[] = new byte[5];
					//										in.read(startBytes);
					in.readFully(startBytes);
					String beacon = new String(startBytes);
					if (!beacon.equals("start")) {
						fstream.write("jmp socket - beacon error\n");
						fstream.flush();
						System.exit(-1);
					}

					int byteRead = 0;
					while (byteRead < bytes.length) {
						int b = in.read(bytes, byteRead, bytes.length - byteRead);
						if (b < 0) {
							System.out.println("b<0");
							System.exit(-1);
						}
						byteRead += b;
					}
					//					in.readFully(bytes);

					int offset = 0;
					offset += read(fromAddress, bytes, offset, physicalAddressSize);
					offset += read(toAddress, bytes, offset, physicalAddressSize);

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

					//					int c = in.read();
					//					System.out.println(c + "," + (char) c);
					//					c = in.read();
					//					System.out.println(c + "," + (char) c);
					//					c = in.read();
					//					System.out.println(c + "," + (char) c);
					byte endBytes[] = new byte[3];
					//										in.read(startBytes);
					in.readFully(endBytes);
					//					beacon = String.valueOf((char) in.read()) + (char) in.read() + (char) in.read();
					beacon = new String(endBytes);
					if (!beacon.equals("end")) {
						fstream.write("jmp socket - beacon error\n");
						fstream.flush();
						System.exit(-1);
					}

					/*fromAddress = read(in, physicalAddressSize);
					toAddress = read(in, physicalAddressSize);

					segmentStart = read(in, segmentAddressSize);
					segmentEnd = read(in, segmentAddressSize);

					eax = read(in, registerSize);
					ecx = read(in, registerSize);
					edx = read(in, registerSize);
					ebx = read(in, registerSize);
					esp = read(in, registerSize);
					ebp = read(in, registerSize);
					esi = read(in, registerSize);
					edi = read(in, registerSize);

					es = read(in, segmentRegisterSize);
					cs = read(in, segmentRegisterSize);
					ss = read(in, segmentRegisterSize);
					ds = read(in, segmentRegisterSize);
					fs = read(in, segmentRegisterSize);
					gs = read(in, segmentRegisterSize);
					*/

					synchronized (jmpDataVector) {
						for (int x = 0; x < noOfJmpRecordToFlush; x++) {
							Elf32_Sym symbol = SourceLevelDebugger.symbolTableModel.searchSymbolWithinRange(fromAddress[x]);
							String fromAddressDescription = symbol == null ? null : symbol.name;
							symbol = SourceLevelDebugger.symbolTableModel.searchSymbol(toAddress[x]);
							String toAddressDescription = symbol == null ? null : symbol.name;

							jmpDataVector.add(new JmpData(lineNo, new Date(), fromAddress[x], fromAddressDescription, toAddress[x], toAddressDescription, segmentStart[x],
									segmentEnd[x], eax[x], ecx[x], edx[x], ebx[x], esp[x], ebp[x], esi[x], edi[x], es[x], cs[x], ss[x], ds[x], fs[x], gs[x]));
							if (fromAddress[x] == 0) {
								System.out.println("fuck");
							}
							fstream.write(lineNo + "-" + /*dateformat1.format(new Date()) +*/"-" + Long.toHexString(fromAddress[x]) + "-" + Long.toHexString(toAddress[x]) + "-"
									+ Long.toHexString(segmentStart[x]) + "-" + Long.toHexString(segmentEnd[x]) + "\n");
							fstream.flush();

							lineNo++;
						}
					}

					out.write("done".getBytes());
				}

				in.close();
				clientSocket.close();
			}
			serverSocket.close();
		} catch (BindException ex) {
			JOptionPane.showMessageDialog(null, "You have turn on the profiling feature but the port " + port + " is not available. Program exit", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		} catch (IOException ex2) {

		}
	}

	//	long read(DataInputStream in, int size) throws IOException {
	//		if (size == 8) {
	//			return CommonLib.readLong64BitsFromInputStream(in);
	//		} else if (size == 4) {
	//			return CommonLib.readLongFromInputStream(in);
	//		} else if (size == 2) {
	//			return CommonLib.readShortFromInputStream(in);
	//		} else {
	//			return in.readByte();
	//		}
	//	}

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
