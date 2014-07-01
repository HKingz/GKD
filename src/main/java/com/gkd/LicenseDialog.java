package com.gkd;

import java.awt.BorderLayout;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.peterswing.CommonLib;

public class LicenseDialog extends javax.swing.JDialog {
	private JScrollPane scrollPane1;
	private JEditorPane editorPane1;

	public LicenseDialog(JFrame frame) {
		super(frame, true);
		try {
			this.setTitle("License");

			scrollPane1 = new JScrollPane();
			getContentPane().add(scrollPane1, BorderLayout.CENTER);

			editorPane1 = new JEditorPane();
			URL url = getClass().getClassLoader().getResource("com/gkd/license.html");
			editorPane1.setPage(url);
			scrollPane1.setViewportView(editorPane1);
			this.setSize(577, 463);
			CommonLib.centerDialog(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
