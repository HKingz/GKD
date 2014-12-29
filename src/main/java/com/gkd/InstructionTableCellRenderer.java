package com.gkd;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
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

	public InstructionTableCellRenderer() {
		this.setOpaque(true);
		//		this.setHorizontalAlignment(JLabel.LEFT);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		try {
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
				this.setText(null);
			} else if (table.getValueAt(row, 1).toString().startsWith("cCode")) {
				if (column == 1) {
					String str = ((String) table.getValueAt(row, 1)).replaceAll("cCode : ", "");
					//					this.setText(String.format("%40s", str));
					this.setText(str);
					this.setForeground(darkGreen);
					this.setIcon(null);
				} else {
					String code = ((String) value);
					if (code != null) {
						code = code.replaceAll("\\t", "    ");
						this.setText(code);
					}
					this.setForeground(darkBlue);
					this.setIcon(null);
				}
			} else {
				this.setForeground(Color.black);
				if (column == 2) {
					String asmCode = (String) value;
					asmCode = asmCode.replaceAll(Keywords.asmKeywords.toLowerCase(), "<font color=blue>$0</font>");
					asmCode = asmCode.replaceAll(Keywords.registers.toLowerCase(), "<font color=green>$0</font>");
					this.setText("<html><body>&nbsp;" + asmCode + "</body></html>");
					this.setIcon(null);
				} else if (column == 3) {
					this.setText((String) value);
					this.setIcon(null);
				} else {
					String s = (String) value;
					if (s.contains(":")) {
						String ss[] = s.split(":");
						if (ss.length > 1) {
							this.setText("<html>" + ss[0] + ":<font color=green>" + ss[1] + "</font></html>");
						} else {
							this.setText(s);
						}
					} else {
						this.setText("0x" + StringUtils.leftPad(CommonLib.string2BigInteger(s).toString(16), 16, '0'));
					}
					this.setIcon(null);
				}
			}

			if (column == 1) {
				this.setHorizontalAlignment(JLabel.CENTER);
			} else {
				this.setHorizontalAlignment(JLabel.LEFT);
			}
		} catch (Exception ex) {
			setText(ex.getMessage());
		}
		return this;
	}
}
