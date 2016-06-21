package com.gkd.instrument;

import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang3.StringUtils;

import com.gkd.instrument.callgraph.JmpData;
import com.peterswing.CommonLib;

public class JmpTableModel extends DefaultTableModel {

	String columnNames[] = {"No.", "Date", "From", "To", "Exception", "Error Code", "What", "Parameter", "Segment start", "Segment End", "eax", "ecx", "edx", "ebx", "esp", "ebp", "esi", "edi", "es", "cs",
		"ss", "ds", "fs", "gs", "stack", "stack base"};
	public Vector<JmpData> data = new Vector<JmpData>();
	private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.S");

	public String getColumnName(int column) {
		return columnNames[column];
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		try {
			return data.size();
		} catch (Exception ex) {
			return 0;
		}
	}

	public void setValueAt(Object aValue, int row, int column) {
	}

	public Object getValueAt(int row, int column) {
		//		try {
		JmpData jmpData = data.get(row);
		if (column == 0) {
			return jmpData.lineNo;
		} else if (column == 1) {
			return jmpData.date == null ? null : dateFormat.format(jmpData.date);
		} else if (column == 2) {
			Hashtable<String, Object> ht = new Hashtable<String, Object>();
			ht.put("address", jmpData.fromAddress);
			ht.put("DW_AT_name", StringUtils.defaultString(jmpData.fromAddress_DW_AT_name));
			ht.put("addressDescription", StringUtils.defaultString(jmpData.fromAddressDescription));
			ht.put("deep", jmpData.deep);
			return ht;
		} else if (column == 3) {
			Hashtable<String, Object> ht = new Hashtable<String, Object>();
			ht.put("address", jmpData.toAddress);
			ht.put("DW_AT_name", StringUtils.defaultString(jmpData.toAddress_DW_AT_name));
			ht.put("addressDescription", StringUtils.defaultString(jmpData.toAddressDescription));
			ht.put("deep", jmpData.deep);
			return ht;
		} else if (column == 4) {
			switch ((int) jmpData.exceptionNo) {
				case 0x00:
					return "#0 Division by zero";
				case 0x01:
					return "#1 Debugger";
				case 0x02:
					return "#2 NMI";
				case 0x03:
					return "#3 Breakpoint";
				case 0x04:
					return "#4 Overflow";
				case 0x05:
					return "#5 Bounds";
				case 0x06:
					return "#6 Invalid Opcode";
				case 0x07:
					return "#7 Coprocessor not available";
				case 0x08:
					return "#8 Double fault";
				case 0x09:
					return "#9 Coprocessor Segment Overrun (386 or earlier only)";
				case 0x0A:
					return "#A Invalid Task State Segment";
				case 0x0B:
					return "#B Segment not present";
				case 0x0C:
					return "#C Stack Fault";
				case 0x0D:
					return "#D General protection fault";
				case 0x0E:
					return "#E Page fault";
				case 0x0F:
					return "#F reserved";
				case 0x10:
					return "#10 Math Fault";
				case 0x11:
					return "#11 Alignment Check";
				case 0x12:
					return "#12 Machine Check";
				case 0x13:
					return "#13SIMD Floating-Point Exception";
				default:
					return "UNKNOWN : " + jmpData.fromAddress;
			}
		} else if (column == 5) {
			return jmpData.errorCode;
		} else if (column == 6) {
			return jmpData.getWhatStr();
		} else if (column == 7) {
			return jmpData.parameters;
		} else if (column == 8) {
			return "0x" + Long.toHexString(jmpData.segmentStart);
		} else if (column == 9) {
			return "0x" + Long.toHexString(jmpData.segmentEnd);
		} else if (column == 10) {
			return "0x" + Long.toHexString(jmpData.eax);
		} else if (column == 11) {
			return "0x" + Long.toHexString(jmpData.ecx);
		} else if (column == 12) {
			return "0x" + Long.toHexString(jmpData.edx);
		} else if (column == 13) {
			return "0x" + Long.toHexString(jmpData.ebx);
		} else if (column == 14) {
			return "0x" + Long.toHexString(jmpData.esp);
		} else if (column == 15) {
			return "0x" + Long.toHexString(jmpData.ebp);
		} else if (column == 16) {
			return "0x" + Long.toHexString(jmpData.esi);
		} else if (column == 17) {
			return "0x" + Long.toHexString(jmpData.edi);
		} else if (column == 18) {
			return "0x" + Long.toHexString(jmpData.es);
		} else if (column == 19) {
			return "0x" + Long.toHexString(jmpData.cs);
		} else if (column == 20) {
			return "0x" + Long.toHexString(jmpData.ss);
		} else if (column == 21) {
			return "0x" + Long.toHexString(jmpData.ds);
		} else if (column == 22) {
			return "0x" + Long.toHexString(jmpData.fs);
		} else if (column == 23) {
			return "0x" + Long.toHexString(jmpData.gs);
		} else if (column == 24) {
			return CommonLib.arrayToHexString(jmpData.stack);
		} else if (column == 25) {
			return "0x" + Long.toHexString(jmpData.stackBase);
		} else {
			return "";
		}
		//		} catch (Exception ex) {
		//			ex.printStackTrace();
		//			return "";
		//		}
	}

	public boolean isCellEditable(int row, int column) {
		return false;
	}

	public void add(JmpData jmpDate) {
		data.add(jmpDate);
	}

	public void addAll(Vector<JmpData> d) {
		data.addAll(d);
	}

	public void removeAll() {
		data.clear();
		this.fireTableDataChanged();
	}
}
