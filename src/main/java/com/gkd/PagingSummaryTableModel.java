package com.gkd;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class PagingSummaryTableModel extends DefaultTableModel {
	String columnNames[] = new String[] { "Linear address", "", "Physical address" };

	public Vector<Long> linearAddressesStart = new Vector<Long>();
	public Vector<Long> linearAddressesEnd = new Vector<Long>();
	public Vector<Long> physicalAddressesStart = new Vector<Long>();
	public Vector<Long> physicalAddressesEnd = new Vector<Long>();

	public String getColumnName(int column) {
		return columnNames[column];
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		if (linearAddressesStart != null) {
			return linearAddressesStart.size();
		} else {
			return 0;
		}
	}

	public Object getValueAt(int row, int column) {
		try {
			if (column == 0) {
				return Long.toHexString(linearAddressesStart.get(row)) + " - " + Long.toHexString(linearAddressesEnd.get(row));
			} else if (column == 1) {
				return ">";
			} else {
				return Long.toHexString(physicalAddressesStart.get(row)) + " - " + Long.toHexString(physicalAddressesEnd.get(row));
			}
		} catch (Exception ex) {
			return "";
		}
	}

	public boolean isCellEditable(int row, int column) {
		return false;
	}

}
