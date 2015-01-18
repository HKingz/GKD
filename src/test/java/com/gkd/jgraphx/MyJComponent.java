package com.gkd.jgraphx;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MyJComponent extends JPanel {
	public MyJComponent() {
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);

		JLabel lblNewLabel = new JLabel("New label");
		panel.add(lblNewLabel);

		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("New label");

		lblNewLabel_1.setBackground(Color.pink);
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBounds(58, 69, 115, 92);
		panel_1.add(lblNewLabel_1);

		JLabel label = new JLabel("New label 2");
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
