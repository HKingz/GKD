package com.gkd;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.gkd.stub.VMController;
import com.gkd.stub.VMType;
import com.peter.tightvncpanel.TightVNC;

public class TestBochsCommandParser {
	public static void main(String args[]) {
		final JFrame frame = new JFrame();
		final JPanel panel = new JPanel();
		frame.add(panel, BorderLayout.CENTER);
		frame.setSize(500, 500);
		frame.setVisible(true);
		new Thread() {
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("connect to vnc");
				TightVNC.initVNCPanel(null, panel, "localhost", 5900, null);
			}
		}.start();
		VMController.vmType = VMType.Bochs;

		if (3 > 2) {
			try {
				ProcessBuilder pb = new ProcessBuilder(("/toolchain/bin/bochs" + " " + "-q -f bochsrc.txt").split(" "));
				pb.redirectErrorStream(true);
				Process p = pb.start();
				final InputStream is = p.getInputStream();
				//				final OutputStream os = p.getOutputStream();

				//			BufferedReader br = new BufferedReader(new InputStreamReader(is), 1024);
				//			BufferedWriter commandOutputStream = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));

				//			new Thread() {
				//				public void run() {
				//					try {
				//						Thread.sleep(2000);
				//					} catch (InterruptedException e) {
				//						e.printStackTrace();
				//					}
				//					try {
				//						System.out.println("r");
				//						os.write('r');
				//						os.write('\n');
				//					} catch (IOException e) {
				//						e.printStackTrace();
				//					}
				//				}
				//			}.start();

				new Thread() {
					public void run() {
						try {
							int x;
							byte[] b = new byte[1];
							while ((x = is.read(b)) != -1) {
								System.out.print((char) b[0]);
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}.start();
				p.waitFor();

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
