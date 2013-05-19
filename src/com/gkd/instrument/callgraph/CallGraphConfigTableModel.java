package com.gkd.instrument.callgraph;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.gkd.Setting;

public class CallGraphConfigTableModel extends AbstractTableModel {
	private String columnNames[] = { "Physical address", "TSS", "Memory start", "Memory end", "Register", "GDT", "IDT", "LDT" };

	Object data[] = { Setting.getInstance().physicalAddress, Setting.getInstance().tss, Setting.getInstance().memoryStart, Setting.getInstance().memoryEnd,
			Setting.getInstance().register, Setting.getInstance().gdt, Setting.getInstance().idt, Setting.getInstance().ldt };

	public boolean needToTellBochsToUpdateZone = false;

	public String getColumnName(int column) {
		return columnNames[column];
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	public int getRowCount() {
		return Setting.getInstance().physicalAddress.size() + 1;
	}

	public void setValueAt(Object aValue, int row, int column) {
		if (row == Setting.getInstance().physicalAddress.size()) {
			long physicalAddress = 0;
			boolean tss = false;
			boolean memoryStart = false;
			boolean memoryEnd = false;
			boolean register = false;
			boolean gdt = false;
			boolean idt = false;
			boolean ldt = false;
			switch (column) {
			case 0:
				physicalAddress = (Long) aValue;
				break;
			case 1:
				tss = (Boolean) aValue;
				break;
			case 2:
				memoryStart = (Boolean) aValue;
				break;
			case 3:
				memoryEnd = (Boolean) aValue;
				break;
			case 4:
				register = (Boolean) aValue;
				break;
			case 5:
				gdt = (Boolean) aValue;
				break;
			case 6:
				idt = (Boolean) aValue;
				break;
			case 7:
				ldt = (Boolean) aValue;
				break;
			}
			this.add(physicalAddress, tss, memoryStart, memoryEnd, register, gdt, idt, ldt);
		} else {
			Vector vector = (Vector) data[column];
			vector.setElementAt(aValue, row);
			Setting.getInstance().save();
		}
	}

	public Object getValueAt(int row, int column) {
		if (row == Setting.getInstance().physicalAddress.size()) {
			if (column == 0) {
				return 0;
			} else {
				return false;
			}
		} else {
			Vector vector = (Vector) data[column];
			return vector.get(row);
		}
	}

	public boolean isCellEditable(int row, int column) {
		return true;
	}

	public void add(long physicalAddress, boolean tss, boolean memoryStart, boolean memoryEnd, boolean register, boolean gdt, boolean idt, boolean ldt) {
		Setting.getInstance().physicalAddress.add(physicalAddress);
		Setting.getInstance().tss.add(tss);
		Setting.getInstance().memoryStart.add(memoryStart);
		Setting.getInstance().memoryEnd.add(memoryEnd);
		Setting.getInstance().register.add(register);
		Setting.getInstance().gdt.add(gdt);
		Setting.getInstance().idt.add(idt);
		Setting.getInstance().ldt.add(ldt);
		this.fireTableDataChanged();
		Setting.getInstance().save();
	}

}
