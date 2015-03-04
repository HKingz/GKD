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
	private String filterType = "all";

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
		Vector<Elf32_Sym> symbols2;
		if (filterType.equals("all")) {
			symbols2 = (Vector<Elf32_Sym>) symbols.clone();
		} else {
			symbols2 = new Vector<Elf32_Sym>();
			for (Elf32_Sym symbol : symbols) {
				int st_type = symbol.st_info & 0xf;
				String type = null;
				if (st_type == 0) {
					type = "type";
				} else if (st_type == 1) {
					type = "object";
				} else if (st_type == 2) {
					type = "function";
				} else if (st_type == 3) {
					type = "section";
				} else if (st_type == 4) {
					type = "file";
				} else {
					type = "unknown";
				}
				System.out.println(filterType + "==" + type);
				if (filterType.equals(type)) {
					symbols2.add(symbol);
				}
			}
		}
		if (searchPattern != null && !searchPattern.equals("")) {
			displaySymbols.clear();

			for (int x = 0; x < symbols2.size(); x++) {
				Elf32_Sym symbol = (Elf32_Sym) symbols2.toArray()[x];
				if ((!exactMatch && symbol.name.toLowerCase().contains(searchPattern.toLowerCase()))
						|| (exactMatch && symbol.name.toLowerCase().equals(searchPattern.toLowerCase())) || Long.toHexString(symbol.st_value).contains(searchPattern)) {
					displaySymbols.add(symbol);
					//				} else if (CommonLib.isNumber(searchPattern) && CommonLib.string2BigInteger(searchPattern).equals(BigInteger.valueOf(symbol.st_value))) {
					//					displaySymbols.add(symbol);
				}
			}
		} else {
			displaySymbols = (Vector<Elf32_Sym>) symbols2.clone();
		}
		fireTableDataChanged();
	}

	public void setSearchFilterType(String filterType) {
		this.filterType = filterType;
		setSearchPattern(searchPattern);
	}

	public void reload() {
		setSearchPattern(searchPattern);
	}

	//	int hashCode = -99999;
	//	Hashtable<Long, Elf32_Sym> ht;
	//	Vector<Long> nullSymbols;

	public Elf32_Sym searchSymbol(long address) {
		//		if (hashCode != symbols.hashCode()) {
		//			ht = new Hashtable<Long, Elf32_Sym>();
		//			nullSymbols = new Vector<Long>();
		//			hashCode = symbols.hashCode();
		//		}
		//		if (nullSymbols.contains(address)) {
		//			return null;
		//		} else if (ht.containsKey(address)) {
		//			return ht.get(address);
		//		}
		for (Elf32_Sym symbol : symbols) {
			if (symbol.st_value == address) {
				//				ht.put(address, symbol);
				return symbol;
			}
		}
		//		nullSymbols.add(address);
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
