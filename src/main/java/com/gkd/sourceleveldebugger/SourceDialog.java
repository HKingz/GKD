package com.gkd.sourceleveldebugger;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.peterswing.advancedswing.enhancedtextarea.EnhancedTextArea;

public class SourceDialog extends javax.swing.JDialog {
	public EnhancedTextArea enhancedTextArea1;

	public SourceDialog(JFrame frame, String title) {
		super(frame, title, true);
		try {
			{
				enhancedTextArea1 = new EnhancedTextArea();
				getContentPane().add(enhancedTextArea1, BorderLayout.CENTER);
				enhancedTextArea1.setText("enhancedTextArea1");
			}
			this.setSize(784, 570);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
