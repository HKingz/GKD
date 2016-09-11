package com.gkd;

import javax.swing.table.AbstractTableModel;

public class CustomPanelTableModel extends AbstractTableModel {
	public String columnNames[];
	public String data[][];

	public Object getValueAt(int row, int column) {
		return data[row][column];
	}

	public int getColumnCount() {
		if (columnNames == null) {
			return 0;
		} else {
			return columnNames.length;
		}
	}

	public int getRowCount() {
		if (data == null) {
			return 0;
		} else {
			return data.length;
		}
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public boolean isCellEditable(int row, int col) {
		return false;
	}

	public Class getColumnClass(int columnIndex) {
		return String.class;
	}
}
