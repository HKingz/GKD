package com.gkd.sourceleveldebugger;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.gkd.MyLanguage;
import com.peterdwarf.elf.Elf32_Sym;

public class SymbolTableModel extends AbstractTableModel {
	private String[] columnNames = { MyLanguage.getString("Name"), "Value" };
	public Vector<Elf32_Sym> displaySymbols;
	Vector<Elf32_Sym> symbols;
	private String searchPattern;
	public boolean exactMatch;

	public SymbolTableModel() {
	}

	public Object getValueAt(int row, int column) {
		try {
			if (displaySymbols == null) {
				setSearchPattern(searchPattern);
			}
			Elf32_Sym symbol = (Elf32_Sym) displaySymbols.toArray()[row];
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
		if (displaySymbols == null) {
			setSearchPattern(searchPattern);
		}
		if (displaySymbols == null) {
			return 0;
		} else if (searchPattern == null) {
			return displaySymbols.size();
		} else {
			return displaySymbols.size();
		}
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public boolean isCellEditable(int row, int col) {
		return false;
	}

	public Class getColumnClass(int columnIndex) {
		if (columnIndex == 0) {
			return Elf32_Sym.class;
		}
		return String.class;
	}

	public void setSearchPattern(String searchPattern) {
		this.searchPattern = searchPattern;
		if (symbols == null) {
			return;
		}
		if (searchPattern != null && !searchPattern.equals("")) {
			displaySymbols.clear();

			for (int x = 0; x < symbols.size(); x++) {
				Elf32_Sym symbol = (Elf32_Sym) symbols.toArray()[x];
				if ((!exactMatch && symbol.name.toLowerCase().contains(searchPattern.toLowerCase()))
						|| (exactMatch && symbol.name.toLowerCase().equals(searchPattern.toLowerCase())) || Long.toHexString(symbol.st_value).contains(searchPattern)) {
					displaySymbols.add(symbol);
					//				} else if (CommonLib.isNumber(searchPattern) && CommonLib.string2BigInteger(searchPattern).equals(BigInteger.valueOf(symbol.st_value))) {
					//					displaySymbols.add(symbol);
				}
			}
		} else {
			displaySymbols = (Vector<Elf32_Sym>) symbols.clone();
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

	public Elf32_Sym searchSymbolWithinRange(long address) {
		for (Elf32_Sym symbol : symbols) {
			if (symbol.checkWithinRange(address)) {
				return symbol;
			}
		}
		return null;
	}
}
