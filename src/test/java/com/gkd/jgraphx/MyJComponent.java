package com.gkd.jgraphx;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MyJComponent extends JPanel {
	public JLabel label;
	public JLabel label2;
	public JPanel panel;

	public MyJComponent() {
		setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		add(panel, BorderLayout.NORTH);

		JLabel lblNewLabel = new JLabel("New label");
		panel.add(lblNewLabel);

		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);

		label2 = new JLabel("New label");

		label2.setBackground(Color.pink);
		label2.setOpaque(true);
		label2.setBounds(58, 69, 115, 92);
		panel_1.add(label2);

		label = new JLabel("New label 2");
		label.setOpaque(true);
		label.setBackground(Color.red);
		label.setBounds(263, 69, 115, 92);
		panel_1.add(label);
	}

	@Override
	public String toString() {
		return "";
	}
}
