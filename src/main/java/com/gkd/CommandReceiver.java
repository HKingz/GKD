package com.gkd;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Vector;

import javax.swing.JEditorPane;

public class CommandReceiver implements Runnable {
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
	}

	public void clearBuffer() {
		synchronized (lines) {
			lines.clear();
		}
	}

	public int getLinesLength() {
		return lines.size();
	}

	public void waitUntilNoInput() {
		int count = lines.size();
		while (true) {
			synchronized (lines) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (count == 0 || count == lines.size()) {
					return;
				}
				count = lines.size();
			}
		}
	}

	public String getCommandAllResult() {
		String str = "";
		synchronized (lines) {
			for (String s : lines) {
				str += s + "\n";
			}
		}
		return str;
	}

	public String getCommandResultUntilEnd() {
		String str = "";

		synchronized (lines) {
			for (String s : lines) {
				str += s + "\n";
			}
		}
		return str;
	}

	public String getCommandResult(String startPattern, String endPattern) {
		long startTime = new Date().getTime();

		String str = "";
		boolean startCapture = false;

		while (true) {
			synchronized (lines) {
				//System.out.println(" >>>" + lines.size());
				if (lines.size() > 0) {
					if (startCapture) {
						if (lines.get(0).contains(endPattern)) {
							str += lines.get(0) + "\n";
							lines.remove(0);
							startCapture = false;
							return str;
						} else {
							str += lines.get(0) + "\n";
							lines.remove(0);
						}
					} else {
						if (lines.get(0).contains(startPattern)) {
							str += lines.get(0) + "\n";
							lines.remove(0);
							if (startPattern.equals(endPattern)) {
								return str;
							}
							startCapture = true;
						} else {
							lines.remove(0);
						}
					}
					startTime = new Date().getTime();
				}
				long diff = new Date().getTime() - startTime;
				if (diff / 1000 >= timeoutSecond) {
					return null;
				}
			}
		}
	}

	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(is), 1024);
			String line;
			JEditorPane bochsEditorPane = null;
			if (gkd != null) {
				bochsEditorPane = gkd.getBochsEditorPane();
			}
			while ((line = br.readLine()) != null) {
				if (shouldShow && bochsEditorPane != null) {
					bochsEditorPane.setText(bochsEditorPane.getText() + "\n" + line);
					bochsEditorPane.scrollRectToVisible(new Rectangle(0, bochsEditorPane.getHeight() - 1, 1, bochsEditorPane.getHeight() - 1));
				}
				if (!line.equals("")) {
					//System.out.println(line);
					lines.add(line);
				}
			}
		} catch (IOException e) {
			// e.printStackTrace();
		}
	}

	public void waitUntilHaveLine(int i) {
		long startTime = new Date().getTime();

		while (lines.size() < i) {
			//			try {
			//				Thread.currentThread().sleep(20);
			//			} catch (InterruptedException e) {
			//			}
			long diff = new Date().getTime() - startTime;
			if (diff / 1000 >= timeoutSecond) {
				return;
			}
		}
	}
}