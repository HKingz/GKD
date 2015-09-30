package com.gkd.sourceleveldebugger;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import com.peterdwarf.elf.Elf32_Sym;

public class SymbolTableCellRenderer extends JLabel implements TableCellRenderer {

	ImageIcon fileIcon = new ImageIcon(getClass().getClassLoader().getResource("com/gkd/images/symbolTree/file.png"));
	ImageIcon functionIcon = new ImageIcon(getClass().getClassLoader().getResource("com/gkd/images/symbolTree/function.png"));
	ImageIcon noTypeIcon = new ImageIcon(getClass().getClassLoader().getResource("com/gkd/images/symbolTree/noType.png"));
	ImageIcon objectIcon = new ImageIcon(getClass().getClassLoader().getResource("com/gkd/images/symbolTree/object.png"));
	ImageIcon sectionIcon = new ImageIcon(getClass().getClassLoader().getResource("com/gkd/images/symbolTree/section.png"));

	public SymbolTableCellRenderer() {
		this.setOpaque(true);
		this.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		setFont(table.getFont());
		if (value instanceof Elf32_Sym) {
			Elf32_Sym symbol = (Elf32_Sym) value;
			int st_type = symbol.st_info & 0xf;
			String type = null;
			if (st_type == 0) {
				type = "NOTYPE";
				setIcon(noTypeIcon);
			} else if (st_type == 1) {
				type = "OBJECT";
				setIcon(objectIcon);
			} else if (st_type == 2) {
				type = "FUNC";
				setIcon(functionIcon);
			} else if (st_type == 3) {
				type = "SECTION";
				setIcon(sectionIcon);
			} else if (st_type == 4) {
				type = "FILE";
				setIcon(fileIcon);
			} else {
				type = "UNKNOWN";
				setIcon(null);
			}
			this.setText(symbol.name);
		} else {
			setIcon(null);
			this.setText((String) value);
		}
		if (isSelected) {
			this.setBackground(table.getSelectionBackground());
		} else {
			if (row % 2 == 0) {
				this.setBackground(Color.white);
			} else {
				this.setBackground(new Color(0xf4f4f4));
			}
		}

		this.setHorizontalAlignment(SwingConstants.LEFT);
		return this;
	}
}
