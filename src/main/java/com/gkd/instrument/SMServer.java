package com.gkd.instrument;

import javax.swing.JTextArea;

import org.apache.log4j.Logger;

import com.gkd.Global;

public class SMServer implements Runnable {
	JTextArea jTextArea;
	boolean shouldStop;
	MySHStub mySHStub = new MySHStub();
	public static Logger logger = Logger.getLogger(SMServer.class);

	public void startServer(JTextArea jTextArea) {
		this.jTextArea = jTextArea;
		jTextArea.setText("");

		shouldStop = false;

		System.loadLibrary("peter");
		new Thread(this).start();
	}

	public void stopServer() {
		shouldStop = true;
	}

	@Override
	public void run() {
		if (Global.debug) {
			logger.debug("SH server started");
		}

		while (!shouldStop) {
			mySHStub.getSharedMemory();
		}

	}

}
