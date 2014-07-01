package com.gkd;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.gkd.stub.VMController;
import com.peterswing.CommonLib;

public class SourceCodeTableModel extends AbstractTableModel {
	private String currentFile;
	private String[] columnNames = { "", MyLanguage.getString("Line"), MyLanguage.getString("Address"), MyLanguage.getString("Source"), MyLanguage.getString("Bytes") };
	private HashMap<String, List<String>> sourceCodes = new HashMap<String, List<String>>();
	private HashMap<String, HashMap<BigInteger, BigInteger>> debugLineInfo = new HashMap<String, HashMap<BigInteger, BigInteger>>();
	private HashMap<BigInteger, Boolean> breakpoint = new HashMap<BigInteger, Boolean>();
	private boolean isShowBytes = false;
	BigInteger eip;

	public HashMap<String, HashMap<BigInteger, BigInteger>> getDebugLineInfo() {
		return debugLineInfo;
	}

	public HashMap<String, List<String>> getSourceCodes() {
		return sourceCodes;
	}

	public Object getValueAt(int row, int column) {
		try {
			if (column == 0) {
				if (eip == debugLineInfo.get(currentFile).get(new Integer(row))) {
					return "here";
				} else if (breakpoint.containsKey(debugLineInfo.get(currentFile).get(new Integer(row)))) {
					if (breakpoint.get(debugLineInfo.get(currentFile).get(new Integer(row)))) {
						return "O";
					} else {
						return "X";
					}
				} else {
					return "";
				}
			} else if (column == 1) {
				return row + 1;
			} else if (column == 2) {
				return "0x" + debugLineInfo.get(currentFile).get(new Integer(row));
			} else {
				return sourceCodes.get(currentFile).get(row);
			}
		} catch (Exception ex) {
			return "";
		}
	}

	public String getCurrentFile() {
		return currentFile;
	}

	public void setCurrentFile(String currentFile) {
		this.currentFile = currentFile;
		this.fireTableDataChanged();
	}

	public int getColumnCount() {
		if (isShowBytes) {
			return columnNames.length;
		} else {
			return columnNames.length - 1;
		}
	}

	public int getRowCount() {
		try {
			return sourceCodes.get(currentFile).size();
		} catch (Exception ex) {
			return 0;
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

	public void setDebugLine(String lines) {
		String linesArr[] = lines.split("\n");
		debugLineInfo.clear();
		for (int x = 1; x < linesArr.length; x++) {
			String arr[] = linesArr[x].split("===");
			String filename = arr[0].trim();
			String line = arr[1].trim();
			BigInteger address = CommonLib.string2BigInteger("0x" + arr[2].trim());
			if (debugLineInfo.get(filename) == null) {
				HashMap<BigInteger, BigInteger> h = new HashMap<BigInteger, BigInteger>();
				h.put(new BigInteger(line), address);
				debugLineInfo.put(filename, h);
			} else {
				HashMap<BigInteger, BigInteger> h = debugLineInfo.get(filename);
				h.put(new BigInteger(line), address);
				debugLineInfo.put(filename, h);
			}
		}
	}

	public void updateBreakpoint(BigInteger eip) {
		this.eip = eip;
		try {
			breakpoint.clear();

			Vector<Vector<String>> r = VMController.getVM().breakpoint();
			for (Vector<String> s : r) {
				s.add("0"); // hit count
				if (s.size() > 1) {
					s.remove(1);

					if (s.get(1).contains("y")) {
						breakpoint.put(CommonLib.string2BigInteger(s.get(2)), true);
					} else {
						breakpoint.put(CommonLib.string2BigInteger(s.get(2)), false);
					}
				}
			}
			this.fireTableDataChanged();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void showBytes(boolean selected) {
		isShowBytes = selected;
		this.fireTableStructureChanged();
	}

}
