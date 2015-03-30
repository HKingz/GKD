package com.gkd.instrument;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.apache.commons.lang3.StringUtils;

import com.gkd.instrument.callgraph.Parameter;

public class AddressCellRenderer extends JLabel implements TableCellRenderer {
	public boolean showFullPath;
	static Color colors[] = { Color.red, Color.blue, new Color(255, 0, 255), new Color(0, 128, 128), new Color(210, 105, 30), new Color(250, 128, 114), new Color(255, 140, 0),
			new Color(30, 144, 255), new Color(50, 205, 50), new Color(0, 191, 255) };

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
		if (value instanceof Hashtable) {
			Hashtable<String, Object> ht = (Hashtable<String, Object>) value;
			Long address = (Long) ht.get("address");
			String DW_AT_name = (String) ht.get("DW_AT_name");
			String filePath = "";
			if (showFullPath) {
				filePath = DW_AT_name;
			} else {
				filePath = new File(DW_AT_name).getName();
			}
			String addressDescription = (String) ht.get("addressDescription");
			int deep = (int) ht.get("deep");

			int hashCode = Math.abs(filePath.hashCode());
			//Color color = new Color(hashCode);
			Color color = colors[hashCode % colors.length];
			//		String hex = String.format("#%02x%02x%02x", (int) (color.getRed() * 0.5), (int) (color.getGreen() * 0.5), (int) (color.getBlue() * 0.5));
			String hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());

			String spacer = StringUtils.repeat("&nbsp;", deep);

			setText("<html><body>" + spacer + "0x" + Long.toHexString(address) + " <font color=" + hex + ">" + filePath + "</font> <font color=" + hex + ">" + addressDescription
					+ "</font></body></html>");
		} else if (value instanceof List) {
			List<Parameter> parameters = (List<Parameter>) value;
			String html = "";
			for (Parameter parameter : parameters) {
				html += parameter.name + " <font color=\"blue\">" + parameter.type + "</font> <font color=\"orange\">" + parameter.location + "</font>,";
			}
			setText("<html><body>" + html + "</body></html>");
		}
		//setBackground(color);
		return this;
	}
}
