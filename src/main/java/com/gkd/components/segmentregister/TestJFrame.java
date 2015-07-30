package com.gkd.components.segmentregister;

import java.awt.EventQueue;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class TestJFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestJFrame frame = new TestJFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TestJFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		BochsSegmentRegister bochsSegmentRegister = new BochsSegmentRegister();
		bochsSegmentRegister.setBounds(66, 89, 134, 28);
		contentPane.add(bochsSegmentRegister);

		JCheckBox btnNewButton = new JCheckBox("New button");
		btnNewButton.setBounds(58, 46, 117, 29);
		contentPane.add(btnNewButton);

		SegmentRegister segmentRegister = SegmentRegisterFactory.createSegmentRegister();
		segmentRegister.setBounds(66, 200, 172, 34);
		contentPane.add(segmentRegister);

	}
}
