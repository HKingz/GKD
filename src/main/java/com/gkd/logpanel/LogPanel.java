package com.gkd.logpanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;

import com.peterswing.advancedswing.enhancedtextarea.EnhancedTextArea;

public class LogPanel extends javax.swing.JPanel implements LogFileTailerListener {
	private EnhancedTextArea textArea;
	private LogFileTailer tailer;

	public LogPanel() {
		super();
		try {
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			setPreferredSize(new Dimension(400, 300));
			{
				textArea = new EnhancedTextArea();
				this.add(textArea, BorderLayout.CENTER);
				{
					//					lines.setEditable(false);
					//					scrollPane1.setRowHeaderView(lines);
					//					textArea.getDocument().addDocumentListener(new DocumentListener() {
					//						public String getText() {
					//							int caretPosition = textArea.getDocument().getLength();
					//							Element root = textArea.getDocument().getDefaultRootElement();
					//							String text = "1" + System.getProperty("line.separator");
					//							for (int i = 2; i < root.getElementIndex(caretPosition) + 2; i++) {
					//								text += i + System.getProperty("line.separator");
					//							}
					//							return text;
					//						}
					//
					//						@Override
					//						public void changedUpdate(DocumentEvent de) {
					//							lines.setText(getText());
					//						}
					//
					//						@Override
					//						public void insertUpdate(DocumentEvent de) {
					//							lines.setText(getText());
					//						}
					//
					//						@Override
					//						public void removeUpdate(DocumentEvent de) {
					//							lines.setText(getText());
					//						}
					//
					//					});
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		tailer = new LogFileTailer(new File("gkd.log"), 1000, false);
		tailer.addLogFileTailerListener(this);
		tailer.start();
	}

	@Override
	public void newLogFileLine(String line) {
		textArea.setText(textArea.getText() + System.getProperty("line.separator") + line);
		textArea.getTextArea().setCaretPosition(textArea.getTextArea().getDocument().getLength());
	}

}
