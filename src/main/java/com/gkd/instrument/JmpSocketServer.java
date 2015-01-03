package com.gkd.instrument;

import java.io.DataInputStream;
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

				int physicalAddressSize = in.read();
				int segmentAddressSize = in.read();
				int registerSize = in.read();
				int segmentRegisterSize = in.read();

				int lineNo = 1;

				long fromAddress;
				long toAddress;
				long segmentStart;
				long segmentEnd;

				long eax;
				long ecx;
				long edx;
				long ebx;
				long esp;
				long ebp;
				long esi;
				long edi;

				long es;
				long cs;
				long ss;
				long ds;
				long fs;
				long gs;

				while (!shouldStop) {
					fromAddress = read(in, physicalAddressSize);
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

					synchronized (jmpDataVector) {
						Elf32_Sym symbol = SourceLevelDebugger.symbolTableModel.searchSymbol(fromAddress);
						String fromAddressDescription = symbol == null ? null : symbol.name;
						symbol = SourceLevelDebugger.symbolTableModel.searchSymbol(toAddress);
						String toAddressDescription = symbol == null ? null : symbol.name;

						jmpDataVector.add(new JmpData(lineNo, new Date(), fromAddress, fromAddressDescription, toAddress, toAddressDescription, segmentStart, segmentEnd, eax, ecx,
								edx, ebx, esp, ebp, esi, edi, es, cs, ss, ds, fs, gs));
					}

					fstream.write(lineNo + "-" + dateformat1.format(new Date()) + "-" + Long.toHexString(fromAddress) + "-" + Long.toHexString(toAddress) + "-" + segmentStart
							+ "-" + segmentEnd + "\n");

					lineNo++;
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

	long read(DataInputStream in, int size) throws IOException {
		if (size == 8) {
			return CommonLib.readLong64BitsFromInputStream(in);
		} else if (size == 4) {
			return CommonLib.readLongFromInputStream(in);
		} else if (size == 2) {
			return CommonLib.readShortFromInputStream(in);
		} else {
			return in.readByte();
		}
	}
}
