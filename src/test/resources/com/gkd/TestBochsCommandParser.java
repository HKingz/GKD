package com.gkd;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

		try {
			ProcessBuilder pb = new ProcessBuilder(("/toolchain/bin/bochs" + " " + "-q -f bochsrc.txt").split(" "));
			pb.redirectErrorStream(true);
			Process p = pb.start();
			final InputStream is = p.getInputStream();
			final BufferedWriter commandOutputStream = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));

			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(is), 1024);
				int x;
				StringBuffer content = new StringBuffer(4096);
				Pattern pattern = Pattern.compile("^.*<bochs:[0-9]+>.*", Pattern.DOTALL);
				while ((x = br.read()) != -1) {
					char c = (char) x;
					content.append(c);
					Matcher matcher = pattern.matcher(content);
					if (matcher.matches()) {
						System.out.println("match");
						break;
					}
				}

				//commandOutputStream.write("show \"cpu0\"" + "\n");
				commandOutputStream.write("r" + "\n");
				commandOutputStream.flush();

				System.out.println("send r");

				content = new StringBuffer(4096);
				String line = "";
				while ((x = is.read()) != -1) {
					char c = (char) x;
					content.append(c);
					line += c;
					if (content.length() % 10000 == 0) {
						System.out.println(content.length());
					}
					Matcher matcher = pattern.matcher(line);
					if (matcher.matches()) {
						System.out.println(">"+content);
						break;
					}
					if (c == '\n') {
						line = "";
					}
				}
				
				commandOutputStream.write("r" + "\n");
				commandOutputStream.flush();

				System.out.println("send r");

				content = new StringBuffer(4096);
				 line = "";
				while ((x = is.read()) != -1) {
					char c = (char) x;
					content.append(c);
					line += c;
					if (content.length() % 10000 == 0) {
						System.out.println(content.length());
					}
					Matcher matcher = pattern.matcher(line);
					if (matcher.matches()) {
						System.out.println(">"+content);
						break;
					}
					if (c == '\n') {
						line = "";
					}
				}
				System.out.println("end=" + content.length());
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			p.waitFor();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
