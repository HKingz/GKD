package com.gkd.instrument.newcallgraph;

import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class CallGraphDialogComponent extends JPanel {
	public JPanel panel;
	public JLabel titleLabel;
	public JTable table;
	public CallGraphTableModel model = new CallGraphTableModel();
	private JScrollPane scrollPane;

	public CallGraphDialogComponent() {
		setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		add(panel, BorderLayout.NORTH);

		titleLabel = new JLabel("");
		panel.add(titleLabel);

		JPanel mainPanel = new JPanel();
		add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		mainPanel.add(scrollPane, BorderLayout.NORTH);

		table = new JTable();
		//$hide>>$
		table.setModel(model);
		//$hide<<$
		scrollPane.getViewport().add(table);
	}

	@Override
	public String toString() {
		return "";
	}

	public void setData(Vector<String[]> data) {
		model.data = data;
		model.fireTableDataChanged();
	}
}
