package com.gkd.components.segmentregister;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class SegmentRegisterHeader extends JPanel {
	private JLabel valueTextField;
	private JLabel limitTextField;
	private JLabel baseTextField;

	public SegmentRegisterHeader() {
		setLayout(new MigLayout("insets 0 0 0 0", "[grow][][]", "[]"));

		valueTextField = new JLabel("Value");
		add(valueTextField, "cell 0 0,grow");

		baseTextField = new JLabel("Base");
		add(baseTextField, "cell 1 0,growy");

		limitTextField = new JLabel("Limit");
		add(limitTextField, "flowx,cell 2 0,growy");
	}

}
