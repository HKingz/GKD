package com.gkd.instrument.newcallgraph;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class CallGraphTableModel extends DefaultTableModel {
	String columnNames[] = { "Instruction" };
	public Vector<String[]> data;

	public CallGraphTableModel() {
	}

	public String getColumnName(int column) {
		return columnNames[column];
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		if (data == null) {
			return 0;
		} else {
			return data.size();
		}
	}

	public void setValueAt(Object aValue, int row, int column) {
		this.fireTableDataChanged();
	}

	public Object getValueAt(int row, int column) {
		return data.get(row)[1] + " " + data.get(row)[2];
	}

	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
