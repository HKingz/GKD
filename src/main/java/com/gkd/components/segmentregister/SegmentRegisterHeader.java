package com.gkd.components.segmentregister;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class SegmentRegisterHeader extends JPanel {
	private JLabel valueLabel;
	private JLabel limitLabel;
	private JLabel baseLabel;

	public SegmentRegisterHeader() {
		setLayout(new MigLayout("insets 0 0 0 0", "[grow][70px][70px]", "[]"));

		valueLabel = new JLabel("Value");
		Font font = new Font(valueLabel.getFont().getFamily(), Font.PLAIN, 8);
		valueLabel.setFont(font);
		add(valueLabel, "cell 0 0,growx,alignx left");

		baseLabel = new JLabel("Base");
		baseLabel.setFont(font);
		add(baseLabel, "cell 1 0,growx,alignx left");

		limitLabel = new JLabel("Limit");
		limitLabel.setFont(font);
		add(limitLabel, "cell 2 0,growx,alignx left");
	}

}
