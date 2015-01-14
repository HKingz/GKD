package com.gkd.instrument;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.peterdwarf.dwarf.CompileUnit;

public class AddressCellRenderer extends JLabel implements TableCellRenderer {
	public boolean showFullPath;

	public AddressCellRenderer() {
		setOpaque(true);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (isSelected) {
			this.setBackground(table.getSelectionBackground());
		} else {
			if (row % 2 == 0) {
				this.setBackground(Color.white);
			} else {
				this.setBackground(new Color(0xf4f4f4));
			}
		}
		Hashtable<String, Object> ht = (Hashtable<String, Object>) value;
		Long address = (Long) ht.get("address");
		CompileUnit cu = (CompileUnit) ht.get("compileUnit");
		String addressDescription = (String) ht.get("addressDescription");

		String filePath;
		if (showFullPath) {
			filePath = cu.DW_AT_name;
		} else {
			filePath = new File(cu.DW_AT_name).getName();
		}
		int hashCode = filePath.hashCode();
		Color color = new Color(hashCode);
		String hex = String.format("#%02x%02x%02x", (int) (color.getRed() * 0.5), (int) (color.getGreen() * 0.5), (int) (color.getBlue() * 0.5));
		setText("<html><body>0x" + Long.toHexString(address) + " <font color=" + hex + ">" + filePath + "</font> <font color=green>" + addressDescription + "</font></body></html>");
		return this;
	}
}
