package com.gkd.components.segmentregister;

import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class QemuSegmentRegister extends SegmentRegister {
	private JTextField limitTextField;
	private JTextField valueTextField;
	private JTextField baseTextField;
	private JTextField flagsTextField;

	public QemuSegmentRegister() {
		setLayout(new MigLayout("insets 0 0 0 0", "[grow][][][]", "[]"));

		valueTextField = new JTextField();
		add(valueTextField, "cell 0 0,grow");
		valueTextField.setColumns(10);

		baseTextField = new JTextField();
		add(baseTextField, "cell 1 0,growy");
		baseTextField.setColumns(10);

		limitTextField = new JTextField();
		add(limitTextField, "flowx,cell 2 0,growy");
		limitTextField.setColumns(10);

		flagsTextField = new JTextField();
		add(flagsTextField, "cell 3 0");
		flagsTextField.setColumns(10);
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
		flagsTextField.setText(str);
	}

	@Override
	public String getText() {
		return valueTextField.getText();
	}

	public String getBase() {
		return baseTextField.getText();
	}

	public String getLimit() {
		return limitTextField.getText();
	}

	public String getFlags() {
		return flagsTextField.getText();
	}

}
