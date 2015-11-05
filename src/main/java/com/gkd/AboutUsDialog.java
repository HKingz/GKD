package com.gkd;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.peterswing.CommonLib;

public class AboutUsDialog extends javax.swing.JDialog {
	private JTextArea textArea1;
	private JLabel label1;
	private JScrollPane jScrollPane1;

	public AboutUsDialog(JFrame frame) {
		super(frame, true);
		try {
			getContentPane().setLayout(null);
			this.setTitle("About us");
			getContentPane().setBackground(new java.awt.Color(255, 255, 255));
			this.setResizable(false);
			{
				jScrollPane1 = new JScrollPane();
				getContentPane().add(jScrollPane1);
				jScrollPane1.setBounds(6, 314, 400, 130);
				{
					textArea1 = new JTextArea();
					jScrollPane1.setViewportView(textArea1);
					String str = "";
					str += "Website : https://github.com/mcheung63/GKD\n";
					str += "Blog : http://peter.kingofcoders.com\n";
					str += "Contact : Peter (mcheung63@hotmail.com)\n";
					str += "Version: " + PropertyUtil.getProperty("version");
					textArea1.setText(str);
					textArea1.setEditable(false);
					textArea1.setMargin(new java.awt.Insets(20, 20, 20, 20));
					textArea1.setBackground(new java.awt.Color(255, 255, 255));
				}
			}
			label1 = new JLabel();
			getContentPane().add(label1);
			textArea1.setBounds(12, 263, 360, 95);
			label1.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/gdk-logo.png")));
			label1.setBounds(0, 12, 384, 356);

			this.setSize(420, 470);
			CommonLib.centerDialog(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
