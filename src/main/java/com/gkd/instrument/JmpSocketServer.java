package com.gkd.instrument;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;

import javax.swing.JOptionPane;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.gkd.GKD;
import com.gkd.Global;
import com.gkd.hibernate.HibernateUtil;
import com.gkd.instrument.callgraph.JmpData;
import com.gkd.instrument.callgraph.JmpType;
import com.gkd.sourceleveldebugger.SourceLevelDebugger;
import com.peterdwarf.elf.Elf32_Sym;
import com.peterswing.CommonLib;

public class JmpSocketServer implements Runnable {
	private int port;
	private boolean shouldStop;
	private ServerSocket serverSocket;
	FileWriter fstream;
	int noOfJmpRecordToFlush = 100;

	//	public static LinkedHashSet<String> segments = new LinkedHashSet<String>();
	//	private SimpleDateFormat dateformat1 = new SimpleDateFormat("HH:mm:ss.S");
	//	public static Vector<JmpData> jmpDataVector = new Vector<JmpData>();

	public static Session session = HibernateUtil.openSession();
	Transaction tx;

	public static void main(String args[]) {
		JmpSocketServer jmpSocketServer = new JmpSocketServer();
		jmpSocketServer.startServer(8765, new JmpTableModel());
		jmpSocketServer.stopServer();
	}

	public void startServer(int port, JmpTableModel jmpTableModel) {
		this.port = port;
		try {
			fstream = new FileWriter(Global.jmpLog, false);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		shouldStop = false;
		new Thread(this).start();

		//		while (serverSocket != null && !serverSocket.isBound()) {
		//			try {
		//				Thread.currentThread().sleep(500);
		//			} catch (InterruptedException e) {
		//				e.printStackTrace();
		//			}
		//		}

	}

	public void stopServer() {
		shouldStop = true;
		try {
			serverSocket.close();
			if (session.isOpen()) {
				session.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
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
				int whatSize = in.read();
				int registerSize = in.read();
				int segmentRegisterSize = in.read();

				int lineNo = 1;
				int rowSize = physicalAddressSize * 2 + whatSize + segmentAddressSize * 2 + registerSize * 8 + segmentRegisterSize * 6;

				int noOfRecordRead = 0;
				int deep = 0;

				while (!shouldStop) {
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
						System.err.println("jmp socket error beacon!=start");
						System.exit(-1);
					}

					byte[] tempBytes = new byte[4];
					in.readFully(tempBytes);
					noOfJmpRecordToFlush = ByteBuffer.wrap(tempBytes).order(ByteOrder.LITTLE_ENDIAN).getInt();

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

					int byteRead = 0;
					while (byteRead < bytes.length) {
						int b = in.read(bytes, byteRead, bytes.length - byteRead);
						if (b < 0) {
							System.err.println("b<0");
							System.exit(-1);
						}
						byteRead += b;
					}

					//					in.readFully(bytes);

					noOfRecordRead += noOfJmpRecordToFlush;
					GKD.instrumentStatusLabel.setText("jump : " + String.format("%,d", noOfRecordRead));

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
						System.err.println("jmp socket error beacon!=end");
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

					//					synchronized (jmpDataVector) {
					tx = session.beginTransaction();
					System.out.println("noOfJmpRecordToFlush " + noOfJmpRecordToFlush);
					for (int x = 0; x < noOfJmpRecordToFlush; x++) {
						Elf32_Sym symbol = SourceLevelDebugger.symbolTableModel.searchSymbolWithinRange(fromAddress[x]);
						String fromAddressDescription = symbol == null ? null : symbol.name;
						symbol = SourceLevelDebugger.symbolTableModel.searchSymbol(toAddress[x]);
						String toAddressDescription = symbol == null ? null : symbol.name;

						JmpType w = null;
						switch ((int) what[x]) {
						case 10:
							w = JmpType.JMP;
							break;
						case 11:
							w = JmpType.JMP_INDIRECT;
							break;
						case 12:
							w = JmpType.CALL;
							break;
						case 13:
							w = JmpType.CALL_INDIRECT;
							break;
						case 14:
							w = JmpType.RET;
							break;
						case 15:
							w = JmpType.IRET;
							break;
						case 16:
							w = JmpType.INT;
							break;
						case 17:
							w = JmpType.SYSCALL;
							break;
						case 18:
							w = JmpType.SYSRET;
							break;
						case 19:
							w = JmpType.SYSENTER;
							break;
						case 20:
							w = JmpType.SYSEXIT;
							break;
						default:
							w = JmpType.unknown;
						}

						JmpData jmpData = new JmpData(lineNo, new Date(), fromAddress[x], fromAddressDescription, toAddress[x], toAddressDescription, w, segmentStart[x],
								segmentEnd[x], eax[x], ecx[x], edx[x], ebx[x], esp[x], ebp[x], esi[x], edi[x], es[x], cs[x], ss[x], ds[x], fs[x], gs[x], deep);
						//							jmpDataVector.add(jmpData);
						//							fstream.write(lineNo + "-" + Long.toHexString(fromAddress[x]) + "-" + Long.toHexString(toAddress[x]) + "-" + Long.toHexString(segmentStart[x]) + "-"
						//									+ Long.toHexString(segmentEnd[x]) + "-" + w + "-" + deep + "\n");

						//							session.save(new JmpData(lineNo, new Date(), fromAddress[x], fromAddressDescription, toAddress[x], toAddressDescription, w, segmentStart[x],
						//									segmentEnd[x], eax[x], ecx[x], edx[x], ebx[x], esp[x], ebp[x], esi[x], edi[x], es[x], cs[x], ss[x], ds[x], fs[x], gs[x], deep));
						session.save(jmpData);
						if (lineNo % 100000 == 0) {
							System.out.println("        >>> added " + lineNo);
						}

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

						lineNo++;
					}
					System.out.println("add to db " + lineNo);
					if (session.isOpen() && session.isConnected()) {
						tx.commit();
					}
					System.out.println("commited to db " + lineNo);

					out.write("done".getBytes());
					out.flush();

				} // end while

				in.close();
				clientSocket.close();
			}
			serverSocket.close();
		} catch (BindException ex) {
			JOptionPane.showMessageDialog(null, "You have turn on the profiling feature but the port " + port + " is not available. Program exit", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		} catch (IOException ex2) {
			ex2.printStackTrace();
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
