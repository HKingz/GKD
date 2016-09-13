package com.gkd.custompanel;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.gkd.CustomPanelData;
import com.gkd.CustomPanelTableModel;

import net.miginfocom.swing.MigLayout;

public class CustomPanel extends JPanel {
	public JLabel infoLabel;
	public JTable table;
	public CustomPanelData customPanelData;
	CustomPanelTableModel model = new CustomPanelTableModel();
	public int noOfColumn = 8;
	int[] bytes;

	public CustomPanel(CustomPanelData customPanelData) {
		this.customPanelData = customPanelData;
		setLayout(new MigLayout("", "[grow]", "[][grow]"));

		infoLabel = new JLabel(customPanelData.name + " 0x" + customPanelData.physicalAddress.toString(16));
		add(infoLabel, "cell 0 0");

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 1,grow");

		table = new JTable(model);
		scrollPane.setViewportView(table);

	}

	public void refresData() {
		initData(bytes);
	}

	public void initData(int[] bytes) {
		this.bytes = bytes;
		model.columnNames = new String[customPanelData.columnNames.length
				* (noOfColumn / customPanelData.columnNames.length)];
		for (int x = 0; x < model.columnNames.length; x++) {
			model.columnNames[x] = customPanelData.columnNames[x % customPanelData.columnNames.length];
		}
		int offset = 0;
		int x = 0;
		int definitions[] = customPanelData.definitions;
		ArrayList<String> data = new ArrayList<String>();
		while (offset < bytes.length) {
			int d = definitions[x % definitions.length];
			long value = 0;
			int o = 0;
			for (int z = 0; z < d; z++) {
				if (offset + z < bytes.length) {
					value += bytes[offset + z] << o;
					o += 8;
				}
			}
			data.add("0x" + Long.toHexString(value));
			offset += d;
			x++;
		}

		int row = 0;
		int col = 0;
		System.out.println(data.size());
		System.out.println(model.getColumnCount());
		System.out.println((float) data.size() / model.getColumnCount());
		model.data = new String[(int) Math.ceil((float) data.size() / model.getColumnCount())][model.getColumnCount()];
		System.out.println(model.getRowCount() + " , " + model.getColumnCount());
		for (String d : data) {
			model.data[row][col] = d;
			col++;
			if (col == model.getColumnCount()) {
				col = 0;
				row++;
			}
		}
		model.fireTableStructureChanged();
	}

}
