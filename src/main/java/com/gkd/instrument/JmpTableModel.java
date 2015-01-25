package com.gkd.instrument;

import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang3.StringUtils;

import com.gkd.GKD;
import com.gkd.instrument.callgraph.JmpData;
import com.gkd.sourceleveldebugger.SourceLevelDebugger;
import com.peterdwarf.elf.Elf32_Sym;

public class JmpTableModel extends DefaultTableModel {
	String columnNames[] = { "No.", "Date", "From", "To", "What", "Segment start", "Segment End", "eax", "ecx", "edx", "ebx", "esp", "ebp", "esi", "edi", "es", "cs", "ss", "ds",
			"fs", "gs" };
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
		JmpData jmpData = data.get(row);
		if (column == 0) {
			return jmpData.lineNo;
		} else if (column == 1) {
			return dateFormat.format(jmpData.date);
		} else if (column == 2) {
			Elf32_Sym symbol = SourceLevelDebugger.symbolTableModel.searchSymbolWithinRange(jmpData.fromAddress);
			String fromAddressDescription = (symbol == null) ? null : symbol.name;

			Hashtable<String, Object> ht = new Hashtable<String, Object>();
			ht.put("address", jmpData.fromAddress);
			ht.put("compileUnit", GKD.sourceLevelDebugger.peterDwarfPanel.getCompileUnit(jmpData.fromAddress));
			ht.put("addressDescription", StringUtils.defaultString(fromAddressDescription));
			ht.put("deep", jmpData.deep);
			return ht;
		} else if (column == 3) {
			Elf32_Sym symbol = SourceLevelDebugger.symbolTableModel.searchSymbol(jmpData.toAddress);
			String toAddressDescription = (symbol == null) ? null : symbol.name;

			Hashtable<String, Object> ht = new Hashtable<String, Object>();
			ht.put("address", jmpData.toAddress);
			ht.put("compileUnit", GKD.sourceLevelDebugger.peterDwarfPanel.getCompileUnit(jmpData.toAddress));
			ht.put("addressDescription", StringUtils.defaultString(toAddressDescription));
			ht.put("deep", jmpData.deep);
			return ht;
		} else if (column == 4) {
			return jmpData.what;
		} else if (column == 5) {
			return "0x" + Long.toHexString(jmpData.segmentStart);
		} else if (column == 5) {
			return "0x" + Long.toHexString(jmpData.segmentStart);
		} else if (column == 6) {
			return "0x" + Long.toHexString(jmpData.segmentEnd);
		} else if (column == 7) {
			return "0x" + Long.toHexString(jmpData.eax);
		} else if (column == 8) {
			return "0x" + Long.toHexString(jmpData.ecx);
		} else if (column == 9) {
			return "0x" + Long.toHexString(jmpData.edx);
		} else if (column == 10) {
			return "0x" + Long.toHexString(jmpData.ebx);
		} else if (column == 11) {
			return "0x" + Long.toHexString(jmpData.esp);
		} else if (column == 12) {
			return "0x" + Long.toHexString(jmpData.ebp);
		} else if (column == 13) {
			return "0x" + Long.toHexString(jmpData.esi);
		} else if (column == 14) {
			return "0x" + Long.toHexString(jmpData.edi);
		} else if (column == 15) {
			return "0x" + Long.toHexString(jmpData.es);
		} else if (column == 16) {
			return "0x" + Long.toHexString(jmpData.cs);
		} else if (column == 17) {
			return "0x" + Long.toHexString(jmpData.ss);
		} else if (column == 18) {
			return "0x" + Long.toHexString(jmpData.ds);
		} else if (column == 19) {
			return "0x" + Long.toHexString(jmpData.fs);
		} else if (column == 20) {
			return "0x" + Long.toHexString(jmpData.gs);
		} else {
			return "";
		}
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
