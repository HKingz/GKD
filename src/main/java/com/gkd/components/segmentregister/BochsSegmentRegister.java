package com.gkd.components.segmentregister;

import java.awt.BorderLayout;

import javax.swing.JTextField;

public class BochsSegmentRegister extends SegmentRegister {
	private JTextField textField;

	public BochsSegmentRegister() {
		setLayout(new BorderLayout(0, 0));

		textField = new JTextField();
		add(textField, BorderLayout.CENTER);
		textField.setColumns(10);
	}

	@Override
	public String getText() {
		return textField.getText();
	}

	@Override
	public void setText(String str) {
		textField.setText(str);
	}

	@Override
	public void setBase(String str) {

	}

	@Override
	public void setLimit(String str) {

	}

	@Override
	public void setFlags(String str) {

	}

	public String getBase() {
		return null;
	}

	public String getLimit() {
		return null;
	}

	public String getFlags() {
		return null;
	}
}
