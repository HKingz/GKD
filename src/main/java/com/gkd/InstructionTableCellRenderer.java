package com.gkd;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

import org.apache.commons.lang3.StringUtils;

import com.gkd.syntaxhighlight.Keywords;
import com.peterswing.CommonLib;

public class InstructionTableCellRenderer extends JLabel implements TableCellRenderer {

	ImageIcon hereIcon = new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/arrow_right_red.png"));
	ImageIcon hereWithBreakpointIcon = new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/arrow_right_red_breakpoint.png"));
	ImageIcon breakpointIcon = new ImageIcon(getClass().getClassLoader().getResource("com/gkd/images/breakpoint/breakpoint.png"));
	ImageIcon breakpointDisableIcon = new ImageIcon(getClass().getClassLoader().getResource("com/gkd/images/breakpoint/breakpointDisable.png"));
	Color darkGreen = new Color(0, 100, 0);
	Color darkBlue = new Color(0, 0, 100);
	Color alterRow = new Color(0xfafafa);
	Border paddingBorder = BorderFactory.createEmptyBorder(0, 100, 0, 0);

	public InstructionTableCellRenderer() {
		this.setOpaque(true);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		try {
			setFont(table.getFont());
			if (isSelected) {
				this.setBackground(table.getSelectionBackground());
			} else {
				if (row % 2 == 0) {
					this.setBackground(Color.white);
				} else {
					this.setBackground(alterRow);
				}
			}
			if (column == 0) {
				if (value.toString().equals("hereO")) {
					this.setIcon(hereWithBreakpointIcon);
				} else if (value.toString().equals("here")) {
					this.setIcon(hereIcon);
				} else if (value.toString().equals("O")) {
					this.setIcon(breakpointIcon);
				} else if (value.toString().equals("X")) {
					this.setIcon(breakpointDisableIcon);
				} else {
					this.setIcon(null);
				}
				this.setBorder(null);
				this.setText(null);
			} else if (column == 1 && value.toString().startsWith("cCode")) {
				String str = ((String) value).replaceAll("cCode : ", "");//.replaceAll("\t", "    ");
				this.setText(str);
				this.setForeground(Color.black);
				this.setBorder(null);
				this.setIcon(null);
			} else if (column == 2) {
				//this.setForeground(Color.black);
				String asmCode = (String) value;
				asmCode = asmCode.replaceAll("<", "&lt;");
				asmCode = asmCode.replaceAll(">", "&gt;");
				if (table.getValueAt(row, 1).toString().contains("cCode")) {
					asmCode = asmCode.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");

					this.setBorder(null);
				} else {
					asmCode = asmCode.replaceAll(Keywords.asmKeywords.toLowerCase(), "<font color=#42a9ec>$0</font>");
					asmCode = asmCode.replaceAll(Keywords.registers.toLowerCase(), "<font color=#31b626>$0</font>");
					if (((InstructionTableModel) table.getModel()).haveCCode) {
						this.setBorder(paddingBorder);
					}
				}
				this.setText("<html><body>&nbsp;" + asmCode + "</body></html>");
				this.setIcon(null);
			} else if (column == 3) {
				this.setText((String) value);
				this.setForeground(Color.black);
				this.setBorder(null);
				this.setIcon(null);
			} else {
				String s = (String) value;
				this.setText(StringUtils.leftPad(CommonLib.string2BigInteger(s).toString(16), 16, '0'));
				this.setBorder(null);
				this.setIcon(null);
			}

			this.setHorizontalAlignment(JLabel.LEFT);
		} catch (Exception ex) {
			ex.printStackTrace();
			setText(ex.getMessage());
		}
		return this;
	}
}
