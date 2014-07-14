package com.gkd.components.segmentregister;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

public class SegmentRegister extends JPanel {
	int x = 0;

	public SegmentRegister() {
		// the following code give will show a textfield in windowbuilder, it is better than show nothing
		if (this.getClass() == SegmentRegister.class) {
			setLayout(new BorderLayout());
			add(new JButton("SegmentRegister"), BorderLayout.CENTER);
		}
		setMaximumSize(new Dimension(400, 20));
	}

	public void setText(String str) {
		throw new UnsupportedOperationException("Must implement");
	}

	public void setBase(String str) {
		throw new UnsupportedOperationException("Must implement");
	}

	public void setLimit(String str) {
		throw new UnsupportedOperationException("Must implement");
	}

	public void setFlags(String str) {
		throw new UnsupportedOperationException("Must implement");
	}

	public String getText() {
		throw new UnsupportedOperationException("Must implement");
	}

	public String getBase() {
		throw new UnsupportedOperationException("Must implement");
	}

	public String getLimit() {
		throw new UnsupportedOperationException("Must implement");
	}

	public String getFlags() {
		throw new UnsupportedOperationException("Must implement");
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(10000, 1000);
	}

}
