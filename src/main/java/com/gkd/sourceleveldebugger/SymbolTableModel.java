package com.gkd.sourceleveldebugger;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.gkd.MyLanguage;
import com.peterdwarf.elf.Elf32_Sym;

public class SymbolTableModel extends AbstractTableModel {
	private String[] columnNames = { MyLanguage.getString("Name"), "Value" };
	public Vector<Elf32_Sym> synbols;
	Vector<Elf32_Sym> symbols;
	private String searchPattern;
	public boolean exactMatch;

	public SymbolTableModel() {
	}

	public Object getValueAt(int row, int column) {
		try {
			if (synbols == null) {
				setSearchPattern(searchPattern);
			}
			Elf32_Sym symbol = (Elf32_Sym) synbols.toArray()[row];
			if (column == 0) {
				// MUST return Elf32_Sym, the double-click and tooltip NEED an Elf32_Sym object
				return symbol;
			} else {
				return "0x" + Long.toHexString(symbol.st_value);
			}
		} catch (Exception ex) {
			return "";
		}
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		if (synbols == null) {
			setSearchPattern(searchPattern);
		}
		if (synbols == null) {
			return 0;
		} else if (searchPattern == null) {
			return synbols.size();
		} else {
			return synbols.size();
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

	public void setSearchPattern(String searchPattern) {
		this.searchPattern = searchPattern;
		if (symbols == null) {
			return;
		}
		if (searchPattern != null && !searchPattern.equals("")) {
			synbols.clear();

			for (int x = 0; x < symbols.size(); x++) {
				Elf32_Sym symbol = (Elf32_Sym) symbols.toArray()[x];
				if ((!exactMatch && symbol.name.toLowerCase().contains(searchPattern.toLowerCase()))
						|| (exactMatch && symbol.name.toLowerCase().equals(searchPattern.toLowerCase())) || Long.toHexString(symbol.st_value).contains(searchPattern)) {
					synbols.add(symbol);
					//				} else if (CommonLib.isNumber(searchPattern) && CommonLib.string2BigInteger(searchPattern).equals(BigInteger.valueOf(symbol.st_value))) {
					//					displaySymbols.add(symbol);
				}
			}
		} else {
			synbols = (Vector<Elf32_Sym>) symbols.clone();
		}
		fireTableDataChanged();
	}

	public void reload() {
		setSearchPattern(searchPattern);
	}

	public Elf32_Sym searchSymbol(long address) {
		for (Elf32_Sym symbol : symbols) {
			if (symbol.st_value == address) {
				return symbol;
			}
		}
		return null;
	}
}
