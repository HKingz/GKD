package com.gkd.custompanel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CustomMotherPanel extends JPanel {
	public JPanel mainPanel;

	public CustomMotherPanel() {
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		add(panel, BorderLayout.NORTH);

		JLabel lblNoOfColumn = new JLabel("No. of colume");
		panel.add(lblNoOfColumn);

		JSpinner spinner = new JSpinner();
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for (Component component : mainPanel.getComponents()) {
					if (component instanceof CustomPanel) {
						CustomPanel customPanel = (CustomPanel) component;
						customPanel.noOfColumn = (int) spinner.getValue();
						customPanel.refresData();
					}
				}
			}
		});
		spinner.setModel(new SpinnerNumberModel(8, 8, 100, 2));
		spinner.setPreferredSize(new Dimension(100, 20));
		panel.add(spinner);

		mainPanel = new JPanel();
		add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

	}

}
