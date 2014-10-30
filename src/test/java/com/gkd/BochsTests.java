package com.gkd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BochsTests {
	OutputStreamWriter commandOutputStream;
	BufferedReader br;
	Process p = null;

	public static void main(String args[]) {
		String content = "<bochs:36> ";
		Pattern pattern = Pattern.compile("^.*<bochs:[0-9]+>.*", Pattern.DOTALL);
		Matcher matcher = pattern.matcher(content);
		if (matcher.matches()) {
			System.out.println("yes");
		} else {
			System.out.println("no");
		}
		System.exit(0);
		new BochsTests().test();

		//		ProcessBuilder pb = new ProcessBuilder("/Users/peter/download/bochs-2.6.6-install/bin/bochs", "-q", "-f", "bochsrc.txt");
		//		try {
		//			pb.redirectErrorStream(true);
		//			Process p = pb.start();
		//			System.out.println("s3");
		//			InputStream is = p.getInputStream();
		//			BufferedReader br = new BufferedReader(new InputStreamReader(is), 1024);
		//			
		//
		//			OutputStreamWriter commandOutputStream = new OutputStreamWriter(p.getOutputStream());
		//			commandOutputStream.write("c" + "\n");
		//			commandOutputStream.flush();
		//			
		//			int x;
		//			while ((x = br.read()) != -1) {
		//				System.out.print((char) x);
		//			}
		//
		//			p.waitFor();
		//			System.out.println("s4");
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}
	}

	public void test() {
		ProcessBuilder pb;
		pb = new ProcessBuilder("/Users/peter/download/bochs-2.6.6-install/bin/bochs", "-q", "-f", "bochsrc.txt");

		pb.redirectErrorStream(true);
		try {
			p = pb.start();
			final InputStream is = p.getInputStream();
			br = new BufferedReader(new InputStreamReader(is), 1024);
			commandOutputStream = new OutputStreamWriter(p.getOutputStream());
			//			String result = getCommandResult();
			//			System.out.println("result=" + result);
			//			result = sendCommand("r");
			//			System.out.println("result=" + result);
			//			result = sendCommand("info tab");
			//			System.out.println("result=" + result);
			//			result = sendCommand("disasm cs:0xfff0 0xf000:0x103f0");
			//			System.out.println("result=" + result);
			//			result = sendCommand("pb 0x7c00");
			//			System.out.println("result=" + result);
			//			result = sendCommand("info b");
			//			System.out.println("result=" + result);

			System.out.println("result=" + getCommandResult());
			System.out.println(sendCommand("r"));
			System.out.println(sendCommand("ptime"));
			System.out.println(sendCommand("r"));
			System.out.println(sendCommand("sreg"));
			System.out.println(sendCommand("creg"));
			System.out.println(sendCommand("dreg"));
			System.out.println(sendCommand("fpu"));
			System.out.println(sendCommand("mmx"));
			System.out.println(sendCommand("info eflags"));
			System.out.println(sendCommand("xp /200bx 0"));
			System.out.println(sendCommand("disasm cs:0xfff0 0xf000:0x103f0"));
			System.out.println(sendCommand("info break"));
			System.out.println(sendCommand("info break"));
			System.out.println(sendCommand("info gdt 0 100"));
			System.out.println(sendCommand("info idt 0 200"));
			System.out.println(sendCommand("info ldt 0 200"));
			System.out.println(sendCommand("xp /4096bx 0"));
			System.out.println(sendCommand("print-stack 40"));
			System.out.println(sendCommand("info tab"));
			System.out.println(sendCommand("info break"));
			System.out.println(sendCommand("disasm"));
			//			System.out.println(sendCommand("pb 0x7c00"));
			System.out.println(sendCommand("info b"));
			System.out.println(sendCommand("info eflags"));
			System.out.println(sendCommand("c"));
			Thread.sleep(2000);
			pauseVM();
			System.out.println("-----------");
			System.out.println(sendCommand("r"));

			Thread.sleep(2000);
			System.out.println(sendCommand("c"));
			Thread.sleep(2000);
			pauseVM();
			System.out.println(sendCommand("r"));
			p.waitFor();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//	private void clearBuffer() {
	//		try {
	//			while (br.ready()) {
	//				int temp = br.read();
	//			}
	//		} catch (IOException e) {
	//			e.printStackTrace();
	//		}
	//	}

	public String sendCommand(String command) {
		try {
			System.out.println("command====" + command);
			command = command.toLowerCase().trim();
			//			commandReceiver.clearBuffer();
			Global.lastCommand = command;
			commandOutputStream.write(command + "\n");
			commandOutputStream.flush();
			if (command.equals("6") || command.equals("c")) {
				return null;
			}
			return getCommandResult();
		} catch (IOException e) {
		}
		return null;
	}

	public String getCommandResult() {
		String line = "";
		String str = "";
		Pattern pattern = Pattern.compile("^.*<bochs:[0-9]+>.*");
		try {
			int x;
			while ((x = br.read()) != -1) {
				char c = (char) x;
				//				System.out.print(c);
				line += c;
				str += c;
				if (c == '\n') {
					line = "";
				}
				Matcher matcher = pattern.matcher(line);
				if (matcher.matches()) {
					//					clearBuffer();
					//remove first line
					str = str.substring(str.indexOf('\n') + 1);
					//remove last line
					if (str.lastIndexOf('\n') >= 0) {
						str = str.substring(0, str.lastIndexOf('\n'));
					}
					return str;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public void pauseVM() {
		try {
			System.out.println("p1");
			ProcessBuilder pb;
			pb = new ProcessBuilder("killall", "-2", "bochs");
			pb.redirectErrorStream(false);
			Process p = pb.start();
			p.waitFor();
			System.out.println("p2");
			//			clearBuffer();
			getCommandResult();
			//			getCommandResult();
			//			String line = "killall -2 bochs";
			//			CommandLine cmdLine = CommandLine.parse(line);
			//			DefaultExecutor executor = new DefaultExecutor();
			//			executor.setExitValue(1);
			//			ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
			//			executor.setWatchdog(watchdog);
			//			int exitValue = executor.execute(cmdLine);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
