package com.gkd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandReceiver {
	public BufferedReader br;
	public boolean shouldShow;
	int timeoutSecond = 5;

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
					//					System.out.println(line);
					line = "";
				}
				Matcher matcher = pattern.matcher(line);
				if (matcher.matches()) {
					// clearBuffer();
					// remove first line
					str = str.substring(str.indexOf('\n') + 1);
					// remove last line
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
}