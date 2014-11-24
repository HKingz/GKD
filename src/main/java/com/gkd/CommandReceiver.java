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

	public String getCommandResult() {
		String content = "";
		String str = "";
		Pattern pattern = Pattern.compile("^.*<bochs:[0-9]+>.*", Pattern.DOTALL);
		try {
			int x;
			while ((x = br.read()) != -1) {
				char c = (char) x;
				content += c;
				str += c;
				Matcher matcher = pattern.matcher(content);
				if (matcher.matches()) {
					if (str.lastIndexOf('\n') >= 0) {
						str = str.substring(0, str.lastIndexOf('\n'));
					}
					return str;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("end 2");
		return null;
	}
}