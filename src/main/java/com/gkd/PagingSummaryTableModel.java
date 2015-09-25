package com.gkd;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class PagingSummaryTableModel extends DefaultTableModel {
	String columnNames[] = new String[] { "Linear address", "", "Physical address" };

	public Vector<String> linearAddresses = new Vector<String>();
	public Vector<String> physicalAddresses = new Vector<String>();

	public String getColumnName(int column) {
		return columnNames[column];
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		if (linearAddresses != null) {
			return linearAddresses.size();
		} else {
			return 0;
		}
	}

	public Object getValueAt(int row, int column) {
		try {
			if (column == 0) {
				return linearAddresses.get(row);
			} else if (column == 1) {
				return ">";
			} else {
				return physicalAddresses.get(row);
			}
		} catch (Exception ex) {
			return "";
		}
	}

	public boolean isCellEditable(int row, int column) {
		return false;
	}

}
