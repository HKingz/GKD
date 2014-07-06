package com.gkd;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JEditorPane;

public class CommandReceiver {
	GKD gkd;
	private final InputStream is;
	BufferedReader br;
	//	public boolean parseEnd = false;
	public boolean shouldShow;
	int timeoutSecond = 5;
	boolean readCommandFinish;
	Vector<String> lines = new Vector<String>();

	public CommandReceiver(InputStream is, GKD gkd) {
		this.is = is;
		this.gkd = gkd;
		br = new BufferedReader(new InputStreamReader(is), 1024);
	}

	private void clearBuffer() {
		try {
			while (br.ready()) {
				int temp = br.read();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getLinesLength() {
		return lines.size();
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
					clearBuffer();
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
}