package com.gkd.components.segmentregister;

import java.awt.Color;

import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class BochsSegmentRegister extends SegmentRegister {
	private JTextField valueTextField;
	private JTextField limitTextField;
	private JTextField baseTextField;

	public BochsSegmentRegister() {
		setLayout(new MigLayout("insets 0 0 0 0", "[grow][][]", "[]"));

		valueTextField = new JTextField();
		valueTextField.setEditable(false);
		valueTextField.setToolTipText("Value");
		valueTextField.putClientProperty("brighter", true);
		add(valueTextField, "cell 0 0,grow");
		valueTextField.setColumns(10);

		baseTextField = new JTextField();
		valueTextField.setToolTipText("Base");
		baseTextField.putClientProperty("brighter", true);
		add(baseTextField, "cell 1 0,growy");
		baseTextField.setColumns(10);

		limitTextField = new JTextField();
		valueTextField.setToolTipText("Limit");
		limitTextField.putClientProperty("brighter", true);
		add(limitTextField, "flowx,cell 2 0,growy");
		limitTextField.setColumns(10);
	}

	@Override
	public String getText() {
		return valueTextField.getText();
	}

	@Override
	public void setText(String str) {
		valueTextField.setText(str);
	}

	@Override
	public void setBase(String str) {
		baseTextField.setText(str);
	}

	@Override
	public void setLimit(String str) {
		limitTextField.setText(str);
	}

	@Override
	public void setFlags(String str) {

	}

	public String getBase() {
		return baseTextField.getText();
	}

	public String getLimit() {
		return limitTextField.getText();
	}

	public String getFlags() {
		return null;
	}

	public void setForeground(Color color) {
		if (valueTextField != null && baseTextField != null && limitTextField != null) {
			valueTextField.setForeground(color);
			baseTextField.setForeground(color);
			limitTextField.setForeground(color);
		}
	}
}
