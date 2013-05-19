package com.gkd.osdebuginformation;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class OSInfoTableModel extends DefaultTableModel {
	String columnNames[] = { "Field", "Value" };
	Vector<Vector<String>> data = new Vector<Vector<String>>();

	public Vector<Vector<String>> getData() {
		return data;
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
		}
		return data.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return data.get(rowIndex).get(columnIndex);
	}

	public void addRow(String key, String field) {
		Vector<String> v = new Vector<String>();
		v.add(key);
		v.add(field);
		data.add(v);
		this.fireTableDataChanged();
	}

}
