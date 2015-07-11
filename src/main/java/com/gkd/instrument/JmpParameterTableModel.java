package com.gkd.instrument;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.gkd.instrument.callgraph.Parameter;

public class JmpParameterTableModel extends DefaultTableModel {
	String columnNames[] = { "Name", "Type", "Size", "Location", "Value" };
	List<Parameter> parameters = new ArrayList<Parameter>();

	public String getColumnName(int column) {
		return columnNames[column];
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		try {
			return parameters.size();
		} catch (Exception ex) {
			return 0;
		}
	}

	public void setValueAt(Object aValue, int row, int column) {
	}

	public Object getValueAt(int row, int column) {
		if (column == 0) {
			return parameters.get(row).name;
		} else if (column == 1) {
			return parameters.get(row).type;
		} else if (column == 2) {
			return parameters.get(row).size;
		} else if (column == 3) {
			return parameters.get(row).location;
		} else if (column == 4) {
			return "0x" + Long.toHexString(parameters.get(row).value);
		} else {
			return "";
		}
	}

	public boolean isCellEditable(int row, int column) {
		return false;
	}

}
