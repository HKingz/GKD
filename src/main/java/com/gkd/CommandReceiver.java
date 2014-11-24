package com.gkd;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class CommandReceiver {
	public BufferedReader br;
	public boolean shouldShow;
	int timeoutSecond = 5;
	public static Logger logger = Logger.getLogger(CommandReceiver.class);

	public CommandReceiver(InputStream is) {
		br = new BufferedReader(new InputStreamReader(is), 1024);
	}

	// public void clearBuffer() {
	// try {
	// while (br.ready()) {
	// int temp = br.read();
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

	public String getCommandResult() {
		String content = "";
		String str = "";
		Pattern pattern = Pattern.compile("^.*<bochs:[0-9]+>.*", Pattern.DOTALL);
		try {
			int x;
			while ((x = br.read()) != -1) {
				char c = (char) x;
				System.out.print(c);
				System.out.flush();
				content += c;
				str += c;
				//				if (c == '\n') {
				//					System.out.println("\n>>>" + content);
				//					content = "";
				//				}

				//				System.out.println("++++" + content + "---");
				//				System.out.flush();
				Matcher matcher = pattern.matcher(content);
				if (matcher.matches()) {
					// clearBuffer();
					// remove first line
					//str = str.substring(str.indexOf('\n') + 1);
					// remove last line
					if (str.lastIndexOf('\n') >= 0) {
						str = str.substring(0, str.lastIndexOf('\n'));
					}
					return str;
				}
			}

			System.out.println("end 3");

			//			String line;
			//			while ((line = br.readLine()) != null) {
			//				str += line + "\n";
			//				System.out.println(">>>" + line);
			//				Matcher matcher = pattern.matcher(line);
			//				if (matcher.matches()) {
			//					return str;
			//				}
			//			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("end 2");
		return null;
	}
}