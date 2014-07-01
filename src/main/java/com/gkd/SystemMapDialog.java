package com.gkd;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.peterswing.CommonLib;

public class SystemMapDialog extends JDialog {
	private JScrollPane scrollPane1;
	private JCheckBox showSelectedOnlyCheckBox;
	private JButton cancelButton;
	private JButton setButton;
	private JPanel panel1;
	private JTable table1;
	File file;
	SystemMapTableModel model = new SystemMapTableModel();

	public SystemMapDialog(JFrame frame, File file) {
		super(frame, true);
		this.file = file;

		model.load(file);
		try {
			this.setTitle("Load System.map");

			scrollPane1 = new JScrollPane();
			getContentPane().add(scrollPane1, BorderLayout.CENTER);

			table1 = new JTable();
			scrollPane1.setViewportView(table1);
			table1.setModel(model);
			table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table1.getColumnModel().getColumn(0).setPreferredWidth(50);
			table1.getColumnModel().getColumn(1).setPreferredWidth(100);
			table1.getColumnModel().getColumn(2).setPreferredWidth(100);
			table1.getColumnModel().getColumn(3).setPreferredWidth(400);

			panel1 = new JPanel();
			getContentPane().add(panel1, BorderLayout.SOUTH);

			showSelectedOnlyCheckBox = new JCheckBox();
			panel1.add(showSelectedOnlyCheckBox);
			showSelectedOnlyCheckBox.setText("Show selected only");

			setButton = new JButton();
			panel1.add(setButton);
			setButton.setText("Set breakpoint");
			setButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					setButtonActionPerformed(evt);
				}
			});

			cancelButton = new JButton();
			panel1.add(cancelButton);
			cancelButton.setText("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					cancelButtonActionPerformed(evt);
				}
			});

			this.setSize(700, 700);
			CommonLib.centerDialog(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setButtonActionPerformed(ActionEvent evt) {
		System.out.println("jSetButton.actionPerformed, event=" + evt);
	}

	private void cancelButtonActionPerformed(ActionEvent evt) {
		setVisible(false);
	}

}
