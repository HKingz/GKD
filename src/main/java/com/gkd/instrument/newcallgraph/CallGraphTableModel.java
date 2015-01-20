package com.gkd.instrument.newcallgraph;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class CallGraphTableModel extends DefaultTableModel {
	String columnNames[] = { "Instruction" };
	public Vector<String[]> data;

	public CallGraphTableModel() {
	}

	public String getColumnName(int column) {
		try {
			return columnNames[column];
		} catch (Exception ex) {
			return "";
		}
	}

	public int getColumnCount() {
		try {
			return columnNames.length;
		} catch (Exception ex) {
			return 0;
		}
	}

	public int getRowCount() {
		try {
			return data.size();
		} catch (Exception ex) {
			return 0;
		}
	}

	public void setValueAt(Object aValue, int row, int column) {
		this.fireTableDataChanged();
	}

	public Object getValueAt(int row, int column) {
		try {
			return data.get(row)[1] + " " + data.get(row)[2];
		} catch (Exception ex) {
			return "";
		}
	}

	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
