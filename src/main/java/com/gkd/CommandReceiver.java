package com.gkd;

import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class CommandReceiver {
	public InputStream is;
	int timeoutSecond = 5;
	public static Logger logger = Logger.getLogger(CommandReceiver.class);

	public CommandReceiver(InputStream is) {
		this.is = is;
	}

	public String getCommandResult() {
		Pattern pattern = Pattern.compile("^.*<bochs:[0-9]+>.*", Pattern.DOTALL);
		try {
			int x;
			StringBuffer content = new StringBuffer(40960);

			String line = "";
			char c;
			while ((x = is.read()) != -1) {
				c = (char) x;
				//				System.out.print(c);
				//				System.out.flush();
				content.append(c);
				line += c;
				Matcher matcher = pattern.matcher(line);
				if (matcher.matches()) {
					return content.toString();
				}
				if (c == '\n') {
					line = "";
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}