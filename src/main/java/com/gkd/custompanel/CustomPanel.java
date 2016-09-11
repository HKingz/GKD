package com.gkd.custompanel;

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

	public CustomPanel(CustomPanelData customPanelData) {
		this.customPanelData = customPanelData;
		setLayout(new MigLayout("", "[grow]", "[][grow]"));

		infoLabel = new JLabel(customPanelData.name + " 0x" + customPanelData.physicalAddress.toString(16));
		add(infoLabel, "cell 0 0");

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 1,grow");

		table = new JTable(model);
		scrollPane.setViewportView(table);

		model=
	}

	public void initData(int[] bytes) {
		// TODO Auto-generated method stub
		
	}

}
