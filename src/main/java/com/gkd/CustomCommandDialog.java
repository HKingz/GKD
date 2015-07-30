package com.gkd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import info.clearthought.layout.TableLayout;

public class CustomCommandDialog extends javax.swing.JDialog {
	private JPanel panel1;
	private JButton runButton;
	public JSpinner spinner6;
	public JSpinner spinner5;
	public JSpinner spinner4;
	public JSpinner spinner3;
	public JSpinner spinner2;
	public JSpinner spinner1;
	public JComboBox comboBox6;
	public JComboBox comboBox5;
	public JComboBox comboBox4;
	public JComboBox comboBox3;
	public JComboBox comboBox2;
	public JComboBox comboBox1;
	private JLabel jLabel2;
	private JLabel jLabel1;
	SpinnerNumberModel model1 = new SpinnerNumberModel(1, 1, 100, 1);
	SpinnerNumberModel model2 = new SpinnerNumberModel(1, 1, 100, 1);
	SpinnerNumberModel model3 = new SpinnerNumberModel(1, 1, 100, 1);
	SpinnerNumberModel model4 = new SpinnerNumberModel(1, 1, 100, 1);
	SpinnerNumberModel model5 = new SpinnerNumberModel(1, 1, 100, 1);
	SpinnerNumberModel model6 = new SpinnerNumberModel(1, 1, 100, 1);
	SpinnerNumberModel repeatModel = new SpinnerNumberModel(1, 1, 10000, 1);
	DefaultComboBoxModel comboBoxModel1 = new DefaultComboBoxModel(new String[] { "", "c", "s" });
	public JSpinner repeatSpinner;
	private JLabel jLabel3;
	DefaultComboBoxModel comboBoxModel2 = new DefaultComboBoxModel(new String[] { "", "c", "s" });
	DefaultComboBoxModel comboBoxModel3 = new DefaultComboBoxModel(new String[] { "", "c", "s" });
	DefaultComboBoxModel comboBoxModel4 = new DefaultComboBoxModel(new String[] { "", "c", "s" });
	DefaultComboBoxModel comboBoxModel5 = new DefaultComboBoxModel(new String[] { "", "c", "s" });
	DefaultComboBoxModel comboBoxModel6 = new DefaultComboBoxModel(new String[] { "", "c", "s" });
	public boolean ok;

	public static void main(String s[]) {
		try {
			UIManager.setLookAndFeel("com.peterswing.white.PeterSwingWhiteLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		new CustomCommandDialog(null).setVisible(true);
	}

	public CustomCommandDialog(JFrame frame) {
		super(frame, true);
		GroupLayout thisLayout = new GroupLayout((JComponent) getContentPane());
		getContentPane().setLayout(thisLayout);
		this.setTitle("Run custom commands");
		panel1 = new JPanel();
		TableLayout panel1Layout = new TableLayout(new double[][] { { TableLayout.FILL, TableLayout.FILL },
				{ TableLayout.FILL, TableLayout.FILL, TableLayout.FILL, TableLayout.FILL, TableLayout.FILL, TableLayout.FILL, TableLayout.FILL } });
		panel1Layout.setHGap(5);
		panel1Layout.setVGap(5);
		panel1.setLayout(panel1Layout);

		jLabel1 = new JLabel();
		panel1.add(jLabel1, "0, 0");
		jLabel1.setText("Command");
		jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

		jLabel2 = new JLabel();
		panel1.add(jLabel2, "1, 0");
		jLabel2.setText("Count");
		jLabel2.setHorizontalAlignment(SwingConstants.CENTER);

		comboBox1 = new JComboBox();
		panel1.add(comboBox1, "0, 1");
		comboBox1.setModel(comboBoxModel1);

		comboBox2 = new JComboBox();
		panel1.add(comboBox2, "0, 2");
		comboBox2.setModel(comboBoxModel2);

		comboBox3 = new JComboBox();
		panel1.add(comboBox3, "0, 3");
		comboBox3.setModel(comboBoxModel3);

		comboBox4 = new JComboBox();
		panel1.add(comboBox4, "0, 4");
		comboBox4.setModel(comboBoxModel4);

		comboBox5 = new JComboBox();
		panel1.add(comboBox5, "0, 5");
		comboBox5.setModel(comboBoxModel5);

		comboBox6 = new JComboBox();
		panel1.add(comboBox6, "0, 6");
		comboBox6.setModel(comboBoxModel6);

		spinner1 = new JSpinner();
		panel1.add(spinner1, "1, 1");
		spinner1.setModel(model1);

		spinner2 = new JSpinner();
		panel1.add(spinner2, "1, 2");
		spinner2.setModel(model2);

		spinner3 = new JSpinner();
		panel1.add(spinner3, "1, 3");
		spinner3.setModel(model3);

		spinner4 = new JSpinner();
		panel1.add(spinner4, "1, 4");
		spinner4.setModel(model4);

		spinner5 = new JSpinner();
		panel1.add(spinner5, "1, 5");
		spinner5.setModel(model5);

		spinner6 = new JSpinner();
		panel1.add(spinner6, "1, 6");
		spinner6.setModel(model6);

		runButton = new JButton();
		runButton.setText("Run");
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				runButtonActionPerformed(evt);
			}
		});

		repeatSpinner = new JSpinner();
		repeatSpinner.setModel(repeatModel);
		repeatSpinner.setVerifyInputWhenFocusTarget(false);

		jLabel3 = new JLabel();
		jLabel3.setText("Repeat all above commands");
		jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);

		thisLayout
				.setVerticalGroup(
						thisLayout.createSequentialGroup().addContainerGap().addComponent(panel1, 0, 174, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(repeatSpinner, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
												GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel3, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(runButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				.addContainerGap());
		thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup().addContainerGap()
				.addGroup(thisLayout.createParallelGroup().addComponent(panel1, GroupLayout.Alignment.LEADING, 0, 279, Short.MAX_VALUE).addGroup(GroupLayout.Alignment.LEADING,
						thisLayout.createSequentialGroup().addGap(0, 15, Short.MAX_VALUE).addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(thisLayout.createParallelGroup()
										.addComponent(repeatSpinner, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addGroup(GroupLayout.Alignment.LEADING,
												thisLayout.createSequentialGroup().addPreferredGap(repeatSpinner, runButton, LayoutStyle.ComponentPlacement.INDENT)
														.addComponent(runButton, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)))))
				.addContainerGap());

		this.setSize(301, 281);
		com.peterswing.CommonLib.centerDialog(this);
	}

	private void runButtonActionPerformed(ActionEvent evt) {
		ok = true;

		new Thread("CustomCommandDialog::runButtonActionPerformed()") {
			public void run() {
				try {
					// prevent repeatSpinner.getValue() get the old value
					Thread.currentThread().sleep(10);
					setVisible(false);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}.start();
	}

}
