package com.gkd;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.peterswing.advancedswing.jprogressbardialog.JProgressBarDialog;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class RegisterPanel extends javax.swing.JPanel {
	private JLabel jEAXLabel;
	public JTextField eaxTextField;
	private JLabel jECXLabel;
	private JLabel jEDXLabel;
	private JLabel jESILabel;
	private JLabel jSSLabel;
	private JLabel jGSLabel;
	private JLabel jFSLabel;
	public JLabel jESLabel;
	public JLabel jDSLabel;
	public JTextField csTextField;
	public JTextField gsTextField;
	public JTextField jSSTextField;
	public JTextField eipTextField;
	public JTextField cr0TextField;
	public JTextField dr7TextField;
	public JTextField dr6TextField;
	public JTextField dr3TextField;
	public JTextField dr2TextField;
	private JSeparator jSeparator1;
	public JTextField dr1TextField;
	private JLabel jDR7Label;
	private JLabel jDR6Label;
	private JLabel jDR3Label;
	private JLabel jDR2Label;
	private JLabel jDR1Label;
	public JTextField dr0TextField;
	private JLabel jDR0Label;
	private JLabel jLabel25;
	public JTextField jTRTextField;
	public JTextField jIDTRTextField;
	public JTextField jLDTRTextField;
	public JTextField gdtrTextField;
	private JLabel jTRLabel;
	private JLabel jIDTRLabel;
	private JLabel jLDTRLabel;
	private JLabel jGDTRLabel;
	public JTextField cr4TextField;
	public JTextField cr3TextField;
	private JLabel jST1Label;
	public JTextField jFDSTextField;
	public JTextField jFDPTextField;
	public JTextField jFCSTextField;
	public JTextField jFIPTextField;
	private JLabel jFDSLabel;
	private JLabel jFDPLabel;
	private JLabel jFCSLabel;
	private JLabel jFIPLabel;
	public JTextField jPTimeTextField;
	private JLabel jPTimeLabel;
	public JTextField jFPUOperandTextField;
	public JTextField jFPUTagTextField;
	public JTextField jFPUControlTextField;
	public JTextField jFPUStatusTextField;
	private JLabel jLabel4;
	private JLabel jLabel3;
	private JLabel jLabel2;
	private JLabel jLabel1;
	private JSeparator jSeparator2;
	public JTextField jMMX7TextField;
	public JTextField jMMX6TextField;
	public JTextField jMMX5TextField;
	public JTextField jMMX4TextField;
	public JTextField jMMX3TextField;
	public JTextField jMMX2TextField;
	public JTextField jMMX1TextField;
	public JTextField jMMX0TextField;
	private JLabel jMM7Label;
	private JLabel jMM6Label;
	private JLabel jMM5Label;
	private JLabel jMM4Label;
	private JLabel jST7Label;
	private JLabel jST6Label;
	private JLabel jST5Label;
	private JLabel jST4Label;
	private JLabel jST3Label;
	private JLabel jST2Label;
	private JLabel jST0Label;
	private JPanel jPanel1;
	private JButton jExportExcelButton;
	private JButton jButton1;
	public JList jStackList;
	public JTextField gdtrLimitTextField;
	public JTextField cr2TextField;
	private JLabel jMM3Label;
	private JLabel jMM2Label;
	private JLabel jMM1Label;
	private JLabel jMM0Label;
	public JTextField jST7TextField;
	public JTextField jST6TextField;
	public JTextField jST5TextField;
	public JTextField jST4TextField;
	public JTextField jST3TextField;
	public JTextField jST2TextField;
	public JTextField jST1TextField;
	public JTextField jST0TextField;
	public JLabel jCR0DetailLabel2;
	public JLabel jCR0DetailLabel;
	public JLabel jEFlagLabel2;
	public JLabel jEFlagLabel;
	public JTextField jIDTRLimitTextField;
	private JLabel jCR4Label;
	private JLabel jCR3Label;
	private JLabel jCR2Label;
	public JLabel jCR0Label;
	public JTextField eflagsTextField;
	private JLabel jEFlagsLabel;
	private JLabel jEIPLabel;
	public JTextField fsTextField;
	public JTextField esTextField;
	public JTextField dsTextField;
	private JLabel jCSLabel;
	private JPanel jPanel99;
	public JTextField espTextField;
	public JTextField ebpTextField;
	public JTextField ediTextField;
	public JTextField esiTextField;
	private JLabel jESPLabel;
	private JLabel jEBPLabel;
	private JLabel jEDILabel;
	public JTextField edxTextField;
	public JTextField ecxTextField;
	public JTextField ebxTextField;
	private JLabel jEBXLabel;
	GKD peterBochsDebugger;
	BorderLayout thisLayout = new BorderLayout();
	FormLayout jPanel2Layout = new FormLayout(
			"max(p;15dlu), 24dlu, max(p;15dlu), 72dlu, 5dlu, max(p;15dlu), 67dlu, 5dlu, max(p;15dlu), 68dlu, 5dlu, 28dlu, 5dlu, max(p;15dlu), 72dlu, 5dlu, 32dlu, 5dlu, max(p;15dlu), 69dlu, 5dlu, 64dlu", 
			"max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu)");

	/**
	 * Auto-generated main method to display this JPanel inside a new JFrame.
	 */

	public RegisterPanel() {
		super();
		initGUI();
	}

	public RegisterPanel(GKD peterBochsDebugger) {
		super();
		this.peterBochsDebugger = peterBochsDebugger;
		initGUI();
		// ToolTipManager.sharedInstance().setInitialDelay(0);
	}

	private void initGUI() {
		try {
			BoxLayout thisLayout = new BoxLayout(this, javax.swing.BoxLayout.X_AXIS);
			this.setLayout(thisLayout);
			this.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
			this.setPreferredSize(new java.awt.Dimension(1522, 770));
			{
				jPanel99 = new JPanel();
				this.add(jPanel99, BorderLayout.CENTER);
				jPanel99.setLayout(jPanel2Layout);
				jPanel99.setPreferredSize(new java.awt.Dimension(914, 694));
				{
					jCSLabel = new JLabel();
					jPanel99.add(jCSLabel, new CellConstraints("2, 1, 1, 1, default, default"));
					jCSLabel.setText("cs");
					jCSLabel.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jCSLabelMouseClicked(evt);
						}
					});
				}
				{
					csTextField = new JTextField();
					jPanel99.add(csTextField, new CellConstraints("3, 1, 2, 1, default, default"));
					csTextField.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent evt) {
							jCSTextFieldKeyTyped(evt);
						}
					});
				}
				{
					jDSLabel = new JLabel();
					jPanel99.add(jDSLabel, new CellConstraints("2, 3, 1, 1, default, default"));
					jDSLabel.setText("ds");
					jDSLabel.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jDSLabelMouseClicked(evt);
						}
					});
				}
				{
					jESLabel = new JLabel();
					jPanel99.add(jESLabel, new CellConstraints("2, 4, 1, 1, default, default"));
					jESLabel.setText("es");
					jESLabel.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jESLabelMouseClicked(evt);
						}
					});
				}
				{
					jFSLabel = new JLabel();
					jPanel99.add(jFSLabel, new CellConstraints("2, 5, 1, 1, default, default"));
					jFSLabel.setText("fs");
					jFSLabel.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jFSLabelMouseClicked(evt);
						}
					});
				}
				{
					jGSLabel = new JLabel();
					jPanel99.add(jGSLabel, new CellConstraints("2, 6, 1, 1, default, default"));
					jGSLabel.setText("gs");
					jGSLabel.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jGSLabelMouseClicked(evt);
						}
					});
				}
				{
					jSSLabel = new JLabel();
					jPanel99.add(jSSLabel, new CellConstraints("2, 7, 1, 1, default, default"));
					jSSLabel.setText("ss");
					jSSLabel.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jSSLabelMouseClicked(evt);
						}
					});
				}
				{
					dsTextField = new JTextField();
					jPanel99.add(dsTextField, new CellConstraints("3, 3, 2, 1, default, default"));
					dsTextField.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent evt) {
							jDSTextFieldKeyTyped(evt);
						}
					});
				}
				{
					esTextField = new JTextField();
					jPanel99.add(esTextField, new CellConstraints("3, 4, 2, 1, default, default"));
					esTextField.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent evt) {
							jESTextFieldKeyTyped(evt);
						}
					});
				}
				{
					fsTextField = new JTextField();
					jPanel99.add(fsTextField, new CellConstraints("3, 5, 2, 1, default, default"));
					fsTextField.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent evt) {
							jFSTextFieldKeyTyped(evt);
						}
					});
				}
				{
					gsTextField = new JTextField();
					jPanel99.add(gsTextField, new CellConstraints("3, 6, 2, 1, default, default"));
					gsTextField.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent evt) {
							jGSTextFieldKeyTyped(evt);
						}
					});
				}
				{
					jSSTextField = new JTextField();
					jPanel99.add(jSSTextField, new CellConstraints("3, 7, 2, 1, default, default"));
					jSSTextField.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent evt) {
							jSSTextFieldKeyTyped(evt);
						}
					});
				}
				{
					jEIPLabel = new JLabel();
					jPanel99.add(jEIPLabel, new CellConstraints("2, 2, 1, 1, default, default"));
					jEIPLabel.setText("eip");
					jEIPLabel.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jEIPLabelMouseClicked(evt);
						}
					});
				}
				{
					eipTextField = new JTextField();
					jPanel99.add(eipTextField, new CellConstraints("3, 2, 2, 1, default, default"));
					eipTextField.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent evt) {
							jEIPTextFieldKeyTyped(evt);
						}
					});
				}
				{
					jEFlagsLabel = new JLabel();
					jPanel99.add(jEFlagsLabel, new CellConstraints("2, 8, 1, 1, default, default"));
					jEFlagsLabel.setText("eflags");
					jEFlagsLabel.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jEFlagsLabelMouseClicked(evt);
						}
					});
				}
				{
					eflagsTextField = new JTextField();
					jPanel99.add(eflagsTextField, new CellConstraints("3, 8, 2, 1, default, default"));
				}
				{
					jEFlagLabel = new JLabel();
					jPanel99.add(jEFlagLabel, new CellConstraints("2, 9, 3, 1, default, default"));
				}
				{
					jEFlagLabel2 = new JLabel();
					jPanel99.add(jEFlagLabel2, new CellConstraints("2, 10, 5, 1, default, default"));
				}
				{
					jEAXLabel = new JLabel();
					jPanel99.add(jEAXLabel, new CellConstraints("6, 1, 1, 1, default, default"));
					jEAXLabel.setText("eax");
					jEAXLabel.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jEAXLabelMouseClicked(evt);
						}
					});
				}
				{
					jEBXLabel = new JLabel();
					jPanel99.add(jEBXLabel, new CellConstraints("6, 2, 1, 1, default, default"));
					jEBXLabel.setText("ebx");
					jEBXLabel.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jEBXLabelMouseClicked(evt);
						}
					});
				}
				{
					jECXLabel = new JLabel();
					jPanel99.add(jECXLabel, new CellConstraints("6, 3, 1, 1, default, default"));
					jECXLabel.setText("ecx");
					jECXLabel.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jECXLabelMouseClicked(evt);
						}
					});
				}
				{
					jEDXLabel = new JLabel();
					jPanel99.add(jEDXLabel, new CellConstraints("6, 4, 1, 1, default, default"));
					jEDXLabel.setText("edx");
					jEDXLabel.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jEDXLabelMouseClicked(evt);
						}
					});
				}
				{
					jESILabel = new JLabel();
					jPanel99.add(jESILabel, new CellConstraints("6, 5, 1, 1, default, default"));
					jESILabel.setText("esi");
					jESILabel.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jESILabelMouseClicked(evt);
						}
					});
				}
				{
					jEDILabel = new JLabel();
					jPanel99.add(jEDILabel, new CellConstraints("6, 6, 1, 1, default, default"));
					jEDILabel.setText("edi");
					jEDILabel.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jEDILabel6MouseClicked(evt);
						}
					});
				}
				{
					jEBPLabel = new JLabel();
					jPanel99.add(jEBPLabel, new CellConstraints("6, 7, 1, 1, default, default"));
					jEBPLabel.setText("ebp");
					jEBPLabel.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jEBPLabelMouseClicked(evt);
						}
					});
				}
				{
					jESPLabel = new JLabel();
					jPanel99.add(jESPLabel, new CellConstraints("6, 8, 1, 1, default, default"));
					jESPLabel.setText("esp");
					jESPLabel.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jESPLabelMouseClicked(evt);
						}
					});
				}
				{
					eaxTextField = new JTextField();
					jPanel99.add(eaxTextField, new CellConstraints("7, 1, 1, 1, default, default"));
					eaxTextField.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent evt) {
							jEAXTextFieldKeyTyped(evt);
						}
					});
				}
				{
					ebxTextField = new JTextField();
					jPanel99.add(ebxTextField, new CellConstraints("7, 2, 1, 1, default, default"));
					ebxTextField.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent evt) {
							jEBXTextFieldKeyTyped(evt);
						}
					});
				}
				{
					ecxTextField = new JTextField();
					jPanel99.add(ecxTextField, new CellConstraints("7, 3, 1, 1, default, default"));
					ecxTextField.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent evt) {
							jECXTextFieldKeyTyped(evt);
						}
					});
				}
				{
					edxTextField = new JTextField();
					jPanel99.add(edxTextField, new CellConstraints("7, 4, 1, 1, default, default"));
					edxTextField.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent evt) {
							jEDXTextFieldKeyTyped(evt);
						}
					});
				}
				{
					esiTextField = new JTextField();
					jPanel99.add(esiTextField, new CellConstraints("7, 5, 1, 1, default, default"));
					esiTextField.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent evt) {
							jESITextFieldKeyTyped(evt);
						}
					});
				}
				{
					ediTextField = new JTextField();
					jPanel99.add(ediTextField, new CellConstraints("7, 6, 1, 1, default, default"));
					ediTextField.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent evt) {
							jEDITextFieldKeyTyped(evt);
						}
					});
				}
				{
					ebpTextField = new JTextField();
					jPanel99.add(ebpTextField, new CellConstraints("7, 7, 1, 1, default, default"));
					ebpTextField.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent evt) {
							jEBPTextFieldKeyTyped(evt);
						}
					});
				}
				{
					espTextField = new JTextField();
					jPanel99.add(espTextField, new CellConstraints("7, 8, 1, 1, default, default"));
					espTextField.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent evt) {
							jESPTextFieldKeyTyped(evt);
						}
					});
				}
				{
					jCR0Label = new JLabel();
					jPanel99.add(jCR0Label, new CellConstraints("9, 1, 1, 1, default, default"));
					jCR0Label.setText("cr0");
					jCR0Label.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jCR0LabelMouseClicked(evt);
						}
					});
				}
				{
					cr0TextField = new JTextField();
					jPanel99.add(cr0TextField, new CellConstraints("10, 1, 3, 1, default, default"));
					cr0TextField.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent evt) {
							jCR0TextFieldKeyTyped(evt);
						}
					});
				}
				{
					jCR2Label = new JLabel();
					jPanel99.add(jCR2Label, new CellConstraints("9, 4, 1, 1, default, default"));
					jCR2Label.setText("cr2");
					jCR2Label.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jCR2LabelMouseClicked(evt);
						}
					});
				}
				{
					cr2TextField = new JTextField();
					jPanel99.add(cr2TextField, new CellConstraints("10, 4, 3, 1, default, default"));
					cr2TextField.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent evt) {
							jCR2TextFieldKeyTyped(evt);
						}
					});
				}
				{
					jCR3Label = new JLabel();
					jPanel99.add(jCR3Label, new CellConstraints("9, 5, 1, 1, default, default"));
					jCR3Label.setText("cr3");
					jCR3Label.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jCR3LabelMouseClicked(evt);
						}
					});
				}
				{
					cr3TextField = new JTextField();
					jPanel99.add(cr3TextField, new CellConstraints("10, 5, 3, 1, default, default"));
					cr3TextField.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent evt) {
							jCR3TextFieldKeyTyped(evt);
						}
					});
				}
				{
					jCR4Label = new JLabel();
					jPanel99.add(jCR4Label, new CellConstraints("9, 6, 1, 1, default, default"));
					jCR4Label.setText("cr4");
					jCR4Label.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jCR4LabelMouseClicked(evt);
						}
					});
				}
				{
					cr4TextField = new JTextField();
					jPanel99.add(cr4TextField, new CellConstraints("10, 6, 3, 1, default, default"));
					cr4TextField.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent evt) {
							jCR4TextFieldKeyTyped(evt);
						}
					});
				}
				{
					jSeparator1 = new JSeparator();
					jPanel99.add(jSeparator1, new CellConstraints("9, 7, 4, 1, default, default"));
				}
				{
					gdtrTextField = new JTextField();
					jPanel99.add(gdtrTextField, new CellConstraints("15, 1, 1, 1, default, default"));
					gdtrTextField.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent evt) {
							jGDTRTextFieldKeyTyped(evt);
						}
					});
				}
				{
					jGDTRLabel = new JLabel();
					jPanel99.add(jGDTRLabel, new CellConstraints("14, 1, 1, 1, default, default"));
					jGDTRLabel.setText("gdtr");
					jGDTRLabel.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jGDTRLabelMouseClicked(evt);
						}
					});
				}
				{
					gdtrLimitTextField = new JTextField();
					jPanel99.add(gdtrLimitTextField, new CellConstraints("17, 1, 1, 1, default, default"));
				}
				{
					jLDTRLabel = new JLabel();
					jPanel99.add(jLDTRLabel, new CellConstraints("14, 2, 1, 1, default, default"));
					jLDTRLabel.setText("ldtr");
					jLDTRLabel.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jLDTRLabelMouseClicked(evt);
						}
					});
				}
				{
					jLDTRTextField = new JTextField();
					jPanel99.add(jLDTRTextField, new CellConstraints("15, 2, 1, 1, default, default"));
					jLDTRTextField.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent evt) {
							jLDTRTextFieldKeyTyped(evt);
						}
					});
				}
				{
					jIDTRLabel = new JLabel();
					jPanel99.add(jIDTRLabel, new CellConstraints("14, 3, 1, 1, default, default"));
					jIDTRLabel.setText("idtr");
					jIDTRLabel.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jIDTRLabelMouseClicked(evt);
						}
					});
				}
				{
					jIDTRTextField = new JTextField();
					jPanel99.add(jIDTRTextField, new CellConstraints("15, 3, 1, 1, default, default"));
					jIDTRTextField.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent evt) {
							jIDTRTextFieldKeyTyped(evt);
						}
					});
				}
				{
					jIDTRLimitTextField = new JTextField();
					jPanel99.add(jIDTRLimitTextField, new CellConstraints("17, 3, 1, 1, default, default"));
				}
				{
					jTRLabel = new JLabel();
					jPanel99.add(jTRLabel, new CellConstraints("14, 4, 1, 1, default, default"));
					jTRLabel.setText("tr");
					jTRLabel.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jTRLabelMouseClicked(evt);
						}
					});
				}
				{
					jTRTextField = new JTextField();
					jPanel99.add(jTRTextField, new CellConstraints("15, 4, 3, 1, default, default"));
					jTRTextField.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent evt) {
							jTRTextFieldKeyTyped(evt);
						}
					});
				}
				{
					jCR0DetailLabel = new JLabel();
					jPanel99.add(jCR0DetailLabel, new CellConstraints("9, 2, 4, 1, default, default"));
				}
				{
					jCR0DetailLabel2 = new JLabel();
					jPanel99.add(jCR0DetailLabel2, new CellConstraints("9, 3, 4, 1, default, default"));
				}
				{
					jDR0Label = new JLabel();
					jPanel99.add(jDR0Label, new CellConstraints("19, 1, 1, 1, default, default"));
					jDR0Label.setText("DR0");
					jDR0Label.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jDR0LabelMouseClicked(evt);
						}
					});
				}
				{
					jDR1Label = new JLabel();
					jPanel99.add(jDR1Label, new CellConstraints("19, 2, 1, 1, default, default"));
					jDR1Label.setText("DR1");
					jDR1Label.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jDR1LabelMouseClicked(evt);
						}
					});
				}
				{
					jDR2Label = new JLabel();
					jPanel99.add(jDR2Label, new CellConstraints("19, 3, 1, 1, default, default"));
					jDR2Label.setText("DR2");
					jDR2Label.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jDR2LabelMouseClicked(evt);
						}
					});
				}
				{
					jDR3Label = new JLabel();
					jPanel99.add(jDR3Label, new CellConstraints("19, 4, 1, 1, default, default"));
					jDR3Label.setText("DR3");
					jDR3Label.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jDR3LabelMouseClicked(evt);
						}
					});
				}
				{
					jDR6Label = new JLabel();
					jPanel99.add(jDR6Label, new CellConstraints("19, 5, 1, 1, default, default"));
					jDR6Label.setText("DR6");
					jDR6Label.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jDR6LabelMouseClicked(evt);
						}
					});
				}
				{
					jDR7Label = new JLabel();
					jPanel99.add(jDR7Label, new CellConstraints("19, 6, 1, 1, default, default"));
					jDR7Label.setText("DR7");
					jDR7Label.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jDR7LabelMouseClicked(evt);
						}
					});
				}
				{
					dr0TextField = new JTextField();
					jPanel99.add(dr0TextField, new CellConstraints("20, 1, 1, 1, default, default"));
					dr0TextField.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent evt) {
							jDR0TextFieldKeyTyped(evt);
						}
					});
				}
				{
					dr1TextField = new JTextField();
					jPanel99.add(dr1TextField, new CellConstraints("20, 2, 1, 1, default, default"));
					dr1TextField.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent evt) {
							jDR1TextFieldKeyTyped(evt);
						}
					});
				}
				{
					dr2TextField = new JTextField();
					jPanel99.add(dr2TextField, new CellConstraints("20, 3, 1, 1, default, default"));
					dr2TextField.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent evt) {
							jDR2TextFieldKeyTyped(evt);
						}
					});
				}
				{
					dr3TextField = new JTextField();
					jPanel99.add(dr3TextField, new CellConstraints("20, 4, 1, 1, default, default"));
					dr3TextField.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent evt) {
							jDR3TextFieldKeyTyped(evt);
						}
					});
				}
				{
					dr6TextField = new JTextField();
					jPanel99.add(dr6TextField, new CellConstraints("20, 5, 1, 1, default, default"));
					dr6TextField.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent evt) {
							jDR6TextFieldKeyTyped(evt);
						}
					});
				}
				{
					dr7TextField = new JTextField();
					jPanel99.add(dr7TextField, new CellConstraints("20, 6, 1, 1, default, default"));
					dr7TextField.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent evt) {
							jDR7TextFieldKeyTyped(evt);
						}
					});
				}
				{
					jLabel25 = new JLabel();
					jPanel99.add(jLabel25, new CellConstraints("22, 1, 1, 1, default, default"));
					jLabel25.setText(MyLanguage.getString("Stack"));
				}
				{
					jPanel1 = new JPanel();
					BoxLayout jPanel1Layout = new BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS);
					jPanel99.add(jPanel1, new CellConstraints("1, 1, 1, 4, default, default"));
					jPanel1.setLayout(jPanel1Layout);
					{
						jButton1 = new JButton();
						jPanel1.add(jButton1);
						jButton1.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/disk.png")));
						jButton1.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								jButton1ActionPerformed(evt);
							}
						});
					}
					{
						jExportExcelButton = new JButton();
						jPanel1.add(jExportExcelButton);
						jExportExcelButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/excel.gif")));
						jExportExcelButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								jExportExcelButtonActionPerformed(evt);
							}
						});
					}
				}
				{
					jST0Label = new JLabel();
					jPanel99.add(jST0Label, new CellConstraints("2, 12, 1, 1, default, default"));
					jST0Label.setText("ST0");
				}
				{
					jST1Label = new JLabel();
					jPanel99.add(jST1Label, new CellConstraints("2, 13, 1, 1, default, default"));
					jST1Label.setText("ST1");
				}
				{
					jST2Label = new JLabel();
					jPanel99.add(jST2Label, new CellConstraints("2, 14, 1, 1, default, default"));
					jST2Label.setText("ST2");
				}
				{
					jST3Label = new JLabel();
					jPanel99.add(jST3Label, new CellConstraints("2, 15, 1, 1, default, default"));
					jST3Label.setText("ST3");
				}
				{
					jST4Label = new JLabel();
					jPanel99.add(jST4Label, new CellConstraints("2, 16, 1, 1, default, default"));
					jST4Label.setText("ST4");
				}
				{
					jST5Label = new JLabel();
					jPanel99.add(jST5Label, new CellConstraints("2, 17, 1, 1, default, default"));
					jST5Label.setText("ST5");
				}
				{
					jST6Label = new JLabel();
					jPanel99.add(jST6Label, new CellConstraints("2, 18, 1, 1, default, default"));
					jST6Label.setText("ST6");
				}
				{
					jST7Label = new JLabel();
					jPanel99.add(jST7Label, new CellConstraints("2, 19, 1, 1, default, default"));
					jST7Label.setText("ST7");
				}
				{
					jST0TextField = new JTextField();
					jPanel99.add(jST0TextField, new CellConstraints("4, 12, 4, 1, default, default"));
				}
				{
					jST1TextField = new JTextField();
					jPanel99.add(jST1TextField, new CellConstraints("4, 13, 4, 1, default, default"));
				}
				{
					jST2TextField = new JTextField();
					jPanel99.add(jST2TextField, new CellConstraints("4, 14, 4, 1, default, default"));
				}
				{
					jST3TextField = new JTextField();
					jPanel99.add(jST3TextField, new CellConstraints("4, 15, 4, 1, default, default"));
				}
				{
					jST4TextField = new JTextField();
					jPanel99.add(jST4TextField, new CellConstraints("4, 16, 4, 1, default, default"));
				}
				{
					jST5TextField = new JTextField();
					jPanel99.add(jST5TextField, new CellConstraints("4, 17, 4, 1, default, default"));
				}
				{
					jST6TextField = new JTextField();
					jPanel99.add(jST6TextField, new CellConstraints("4, 18, 4, 1, default, default"));
				}
				{
					jST7TextField = new JTextField();
					jPanel99.add(jST7TextField, new CellConstraints("4, 19, 4, 1, default, default"));
				}
				{
					jMM0Label = new JLabel();
					jPanel99.add(jMM0Label, new CellConstraints("9, 12, 1, 1, default, default"));
					jMM0Label.setText("MM0");
				}
				{
					jMM1Label = new JLabel();
					jPanel99.add(jMM1Label, new CellConstraints("9, 13, 1, 1, default, default"));
					jMM1Label.setText("MM1");
				}
				{
					jMM2Label = new JLabel();
					jPanel99.add(jMM2Label, new CellConstraints("9, 14, 1, 1, default, default"));
					jMM2Label.setText("MM2");
				}
				{
					jMM3Label = new JLabel();
					jPanel99.add(jMM3Label, new CellConstraints("9, 15, 1, 1, default, default"));
					jMM3Label.setText("MM3");
				}
				{
					jMM4Label = new JLabel();
					jPanel99.add(jMM4Label, new CellConstraints("9, 16, 1, 1, default, default"));
					jMM4Label.setText("MM4");
				}
				{
					jMM5Label = new JLabel();
					jPanel99.add(jMM5Label, new CellConstraints("9, 17, 1, 1, default, default"));
					jMM5Label.setText("MM5");
				}
				{
					jMM6Label = new JLabel();
					jPanel99.add(jMM6Label, new CellConstraints("9, 18, 1, 1, default, default"));
					jMM6Label.setText("MM6");
				}
				{
					jMM7Label = new JLabel();
					jPanel99.add(jMM7Label, new CellConstraints("9, 19, 1, 1, default, default"));
					jMM7Label.setText("MM7");
				}
				{
					jMMX0TextField = new JTextField();
					jPanel99.add(jMMX0TextField, new CellConstraints("10, 12, 6, 1, default, default"));
				}
				{
					jMMX1TextField = new JTextField();
					jPanel99.add(jMMX1TextField, new CellConstraints("10, 13, 6, 1, default, default"));
				}
				{
					jMMX2TextField = new JTextField();
					jPanel99.add(jMMX2TextField, new CellConstraints("10, 14, 6, 1, default, default"));
				}
				{
					jMMX3TextField = new JTextField();
					jPanel99.add(jMMX3TextField, new CellConstraints("10, 15, 6, 1, default, default"));
				}
				{
					jMMX4TextField = new JTextField();
					jPanel99.add(jMMX4TextField, new CellConstraints("10, 16, 6, 1, default, default"));
				}
				{
					jMMX5TextField = new JTextField();
					jPanel99.add(jMMX5TextField, new CellConstraints("10, 17, 6, 1, default, default"));
				}
				{
					jMMX6TextField = new JTextField();
					jPanel99.add(jMMX6TextField, new CellConstraints("10, 18, 6, 1, default, default"));
				}
				{
					jMMX7TextField = new JTextField();
					jPanel99.add(jMMX7TextField, new CellConstraints("10, 19, 6, 1, default, default"));
				}
				{
					jSeparator2 = new JSeparator();
					jPanel99.add(jSeparator2, new CellConstraints("2, 11, 6, 1, default, default"));
				}
				{
					jLabel1 = new JLabel();
					jPanel99.add(jLabel1, new CellConstraints("2, 20, 2, 1, default, default"));
					jLabel1.setText("Status");
				}
				{
					jLabel2 = new JLabel();
					jPanel99.add(jLabel2, new CellConstraints("2, 21, 2, 1, default, default"));
					jLabel2.setText("Control");
				}
				{
					jLabel3 = new JLabel();
					jPanel99.add(jLabel3, new CellConstraints("2, 22, 2, 1, default, default"));
					jLabel3.setText("Tag");
				}
				{
					jLabel4 = new JLabel();
					jPanel99.add(jLabel4, new CellConstraints("2, 23, 2, 1, default, default"));
					jLabel4.setText("Operand");
				}
				{
					jFPUStatusTextField = new JTextField();
					jPanel99.add(jFPUStatusTextField, new CellConstraints("4, 20, 12, 1, default, default"));
				}
				{
					jFPUControlTextField = new JTextField();
					jPanel99.add(jFPUControlTextField, new CellConstraints("4, 21, 12, 1, default, default"));
				}
				{
					jFPUTagTextField = new JTextField();
					jPanel99.add(jFPUTagTextField, new CellConstraints("4, 22, 4, 1, default, default"));
				}
				{
					jFPUOperandTextField = new JTextField();
					jPanel99.add(jFPUOperandTextField, new CellConstraints("4, 23, 4, 1, default, default"));
				}
				{
					jPTimeLabel = new JLabel();
					jPanel99.add(jPTimeLabel, new CellConstraints("9, 8, 1, 1, default, default"));
					jPTimeLabel.setText("ptime");
				}
				{
					jPTimeTextField = new JTextField();
					jPanel99.add(jPTimeTextField, new CellConstraints("10, 8, 1, 1, default, default"));
				}
				{
					jFIPLabel = new JLabel();
					jPanel99.add(jFIPLabel, new CellConstraints("2, 24, 1, 1, default, default"));
					jFIPLabel.setText("fip");
				}
				{
					jFCSLabel = new JLabel();
					jPanel99.add(jFCSLabel, new CellConstraints("2, 25, 1, 1, default, default"));
					jFCSLabel.setText("fcs");
				}
				{
					jFDPLabel = new JLabel();
					jPanel99.add(jFDPLabel, new CellConstraints("2, 26, 1, 1, default, default"));
					jFDPLabel.setText("fdp");
				}
				{
					jFDSLabel = new JLabel();
					jPanel99.add(jFDSLabel, new CellConstraints("2, 27, 1, 1, default, default"));
					jFDSLabel.setText("fds");
				}
				{
					jFIPTextField = new JTextField();
					jPanel99.add(jFIPTextField, new CellConstraints("4, 24, 1, 1, default, default"));
				}
				{
					jFCSTextField = new JTextField();
					jPanel99.add(jFCSTextField, new CellConstraints("4, 25, 1, 1, default, default"));
				}
				{
					jFDPTextField = new JTextField();
					jPanel99.add(jFDPTextField, new CellConstraints("4, 26, 1, 1, default, default"));
				}
				{
					jFDSTextField = new JTextField();
					jPanel99.add(jFDSTextField, new CellConstraints("4, 27, 1, 1, default, default"));
				}
				{
					jStackList = new JList();
					jPanel99.add(jStackList, new CellConstraints("22, 2, 1, 30, default, default"));
				}
			}

			for (final Component component : jPanel99.getComponents()) {
				if (component.getClass() == JTextField.class) {
					JTextField t = (JTextField) component;
					t.setToolTipText("Press enter to set the value");
					component.addFocusListener(new FocusAdapter() {
						public void focusGained(FocusEvent evt) {
							textFieldFocusGained((JComponent) component, evt);
						}
					});
					component.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent evt) {
							textFieldKeyTyped(evt);
						}
					});
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jButton1ActionPerformed(ActionEvent evt) {
		final JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(peterBochsDebugger);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			if (!GKDCommonLib.saveImage(this.getParent(), file)) {
				JOptionPane.showMessageDialog(this, "Cannot save image", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void jExportExcelButtonActionPerformed(ActionEvent evt) {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(peterBochsDebugger);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			if (!file.getName().endsWith(".xls")) {
				file = new File(file.getParent() + File.separator + file.getName() + ".xls");
			}
			if (file.exists()) {
				int r = JOptionPane.showConfirmDialog(this, "Overwrite " + file.getName() + "?", "Warning", JOptionPane.YES_NO_OPTION);
				if (r == 1) {
					return;
				}
			}
			final JProgressBarDialog d = new JProgressBarDialog(peterBochsDebugger, "Exporting to XLS", true);
			d.jProgressBar.setIndeterminate(true);
			d.jProgressBar.setStringPainted(true);

			class MyThread extends Thread {
				File file;

				public MyThread(File file) {
					this.file = file;
				}

				public void run() {
					GKDCommonLib.exportRegisterHistory(file, d);
				}
			}
			d.thread = new MyThread(file);
			d.setVisible(true);
		}
	}

	private void jCSLabelMouseClicked(MouseEvent evt) {
		peterBochsDebugger.jMemoryAddressComboBox.setSelectedItem(this.csTextField.getText());
	}

	private void jEIPLabelMouseClicked(MouseEvent evt) {
		peterBochsDebugger.jMemoryAddressComboBox.setSelectedItem(this.eipTextField.getText());
	}

	private void jDSLabelMouseClicked(MouseEvent evt) {
		peterBochsDebugger.jMemoryAddressComboBox.setSelectedItem(this.dsTextField.getText());
	}

	private void jESLabelMouseClicked(MouseEvent evt) {
		peterBochsDebugger.jMemoryAddressComboBox.setSelectedItem(this.esTextField.getText());
	}

	private void jFSLabelMouseClicked(MouseEvent evt) {
		peterBochsDebugger.jMemoryAddressComboBox.setSelectedItem(this.fsTextField.getText());
	}

	private void jGSLabelMouseClicked(MouseEvent evt) {
		peterBochsDebugger.jMemoryAddressComboBox.setSelectedItem(this.gsTextField.getText());
	}

	private void jSSLabelMouseClicked(MouseEvent evt) {
		peterBochsDebugger.jMemoryAddressComboBox.setSelectedItem(this.jSSTextField.getText());
	}

	private void jEFlagsLabelMouseClicked(MouseEvent evt) {
		peterBochsDebugger.jMemoryAddressComboBox.setSelectedItem(this.eflagsTextField.getText());
	}

	private void jEAXLabelMouseClicked(MouseEvent evt) {
		peterBochsDebugger.jMemoryAddressComboBox.setSelectedItem(this.eaxTextField.getText());
	}

	private void jEBXLabelMouseClicked(MouseEvent evt) {
		peterBochsDebugger.jMemoryAddressComboBox.setSelectedItem(this.ebxTextField.getText());
	}

	private void jECXLabelMouseClicked(MouseEvent evt) {
		peterBochsDebugger.jMemoryAddressComboBox.setSelectedItem(this.ecxTextField.getText());
	}

	private void jEDXLabelMouseClicked(MouseEvent evt) {
		peterBochsDebugger.jMemoryAddressComboBox.setSelectedItem(this.edxTextField.getText());
	}

	private void jESILabelMouseClicked(MouseEvent evt) {
		peterBochsDebugger.jMemoryAddressComboBox.setSelectedItem(this.esiTextField.getText());
	}

	private void jEDILabel6MouseClicked(MouseEvent evt) {
		peterBochsDebugger.jMemoryAddressComboBox.setSelectedItem(this.ediTextField.getText());
	}

	private void jEBPLabelMouseClicked(MouseEvent evt) {
		peterBochsDebugger.jMemoryAddressComboBox.setSelectedItem(this.ebpTextField.getText());
	}

	private void jESPLabelMouseClicked(MouseEvent evt) {
		peterBochsDebugger.jMemoryAddressComboBox.setSelectedItem(this.espTextField.getText());
	}

	private void jGDTRLabelMouseClicked(MouseEvent evt) {
		peterBochsDebugger.jMemoryAddressComboBox.setSelectedItem(this.gdtrTextField.getText());
	}

	private void jLDTRLabelMouseClicked(MouseEvent evt) {
		peterBochsDebugger.jMemoryAddressComboBox.setSelectedItem(this.jLDTRTextField.getText());
	}

	private void jIDTRLabelMouseClicked(MouseEvent evt) {
		peterBochsDebugger.jMemoryAddressComboBox.setSelectedItem(this.jIDTRTextField.getText());
	}

	private void jTRLabelMouseClicked(MouseEvent evt) {
		peterBochsDebugger.jMemoryAddressComboBox.setSelectedItem(this.jTRTextField.getText());
	}

	private void jCR0LabelMouseClicked(MouseEvent evt) {
		peterBochsDebugger.jMemoryAddressComboBox.setSelectedItem(this.cr0TextField.getText());
	}

	private void jCR2LabelMouseClicked(MouseEvent evt) {
		peterBochsDebugger.jMemoryAddressComboBox.setSelectedItem(this.cr2TextField.getText());
	}

	private void jCR3LabelMouseClicked(MouseEvent evt) {
		peterBochsDebugger.jMemoryAddressComboBox.setSelectedItem(this.cr3TextField.getText());
	}

	private void jCR4LabelMouseClicked(MouseEvent evt) {
		peterBochsDebugger.jMemoryAddressComboBox.setSelectedItem(this.cr4TextField.getText());
	}

	private void jDR0LabelMouseClicked(MouseEvent evt) {
		peterBochsDebugger.jMemoryAddressComboBox.setSelectedItem(this.dr0TextField.getText());
	}

	private void jDR1LabelMouseClicked(MouseEvent evt) {
		peterBochsDebugger.jMemoryAddressComboBox.setSelectedItem(this.dr1TextField.getText());
	}

	private void jDR2LabelMouseClicked(MouseEvent evt) {
		peterBochsDebugger.jMemoryAddressComboBox.setSelectedItem(this.dr2TextField.getText());
	}

	private void jDR3LabelMouseClicked(MouseEvent evt) {
		peterBochsDebugger.jMemoryAddressComboBox.setSelectedItem(this.dr3TextField.getText());
	}

	private void jDR6LabelMouseClicked(MouseEvent evt) {
		peterBochsDebugger.jMemoryAddressComboBox.setSelectedItem(this.dr6TextField.getText());
	}

	private void jDR7LabelMouseClicked(MouseEvent evt) {
		peterBochsDebugger.jMemoryAddressComboBox.setSelectedItem(this.dr7TextField.getText());
	}

	private void textFieldKeyTyped(KeyEvent evt) {
		if (evt.getKeyChar() == '\n') {

		}
	}

	private void textFieldFocusGained(JComponent component, FocusEvent evt) {
		Action action = component.getActionMap().get("postTip");
		if (action == null) {
			return;
		}
		ActionEvent ae = new ActionEvent(component, ActionEvent.ACTION_PERFORMED, "postTip", EventQueue.getMostRecentEventTime(), 0);
		action.actionPerformed(ae);
	}

	private void jCSTextFieldKeyTyped(KeyEvent evt) {
		GKD.sendCommand("set cs=" + csTextField.getText());
	}

	private void jEIPTextFieldKeyTyped(KeyEvent evt) {
		GKD.sendCommand("set eip=" + eipTextField.getText());
	}

	private void jDSTextFieldKeyTyped(KeyEvent evt) {
		GKD.sendCommand("set ds=" + dsTextField.getText());
	}

	private void jESTextFieldKeyTyped(KeyEvent evt) {
		GKD.sendCommand("set es=" + esTextField.getText());
	}

	private void jFSTextFieldKeyTyped(KeyEvent evt) {
		GKD.sendCommand("set fs=" + fsTextField.getText());
	}

	private void jGSTextFieldKeyTyped(KeyEvent evt) {
		GKD.sendCommand("set gs=" + gsTextField.getText());
	}

	private void jSSTextFieldKeyTyped(KeyEvent evt) {
		GKD.sendCommand("set ss=" + jSSTextField.getText());
	}

	private void jEAXTextFieldKeyTyped(KeyEvent evt) {
		GKD.sendCommand("set eax=" + eaxTextField.getText());
	}

	private void jEBXTextFieldKeyTyped(KeyEvent evt) {
		GKD.sendCommand("set ebx=" + ebxTextField.getText());
	}

	private void jECXTextFieldKeyTyped(KeyEvent evt) {
		GKD.sendCommand("set ecx=" + ecxTextField.getText());
	}

	private void jEDXTextFieldKeyTyped(KeyEvent evt) {
		GKD.sendCommand("set edx=" + edxTextField.getText());
	}

	private void jESITextFieldKeyTyped(KeyEvent evt) {
		GKD.sendCommand("set esi=" + esiTextField.getText());
	}

	private void jEDITextFieldKeyTyped(KeyEvent evt) {
		GKD.sendCommand("set edi=" + ediTextField.getText());
	}

	private void jEBPTextFieldKeyTyped(KeyEvent evt) {
		GKD.sendCommand("set ebp=" + ebpTextField.getText());
	}

	private void jESPTextFieldKeyTyped(KeyEvent evt) {
		GKD.sendCommand("set esp=" + espTextField.getText());
	}

	private void jCR0TextFieldKeyTyped(KeyEvent evt) {
		GKD.sendCommand("set cr0=" + cr0TextField.getText());
	}

	private void jCR2TextFieldKeyTyped(KeyEvent evt) {
		GKD.sendCommand("set cr2=" + cr2TextField.getText());
	}

	private void jCR3TextFieldKeyTyped(KeyEvent evt) {
		GKD.sendCommand("set cr3=" + cr3TextField.getText());
	}

	private void jCR4TextFieldKeyTyped(KeyEvent evt) {
		GKD.sendCommand("set cr4=" + cr4TextField.getText());
	}

	private void jGDTRTextFieldKeyTyped(KeyEvent evt) {
		GKD.sendCommand("set gdtr=" + gdtrTextField.getText());
	}

	private void jLDTRTextFieldKeyTyped(KeyEvent evt) {
		GKD.sendCommand("set ldtr=" + jLDTRTextField.getText());
	}

	private void jIDTRTextFieldKeyTyped(KeyEvent evt) {
		GKD.sendCommand("set idtr=" + jIDTRTextField.getText());
	}

	private void jTRTextFieldKeyTyped(KeyEvent evt) {
		GKD.sendCommand("set tr=" + jTRTextField.getText());
	}

	private void jDR0TextFieldKeyTyped(KeyEvent evt) {
		GKD.sendCommand("set dr0=" + dr0TextField.getText());
	}

	private void jDR1TextFieldKeyTyped(KeyEvent evt) {
		GKD.sendCommand("set dr1=" + dr1TextField.getText());
	}

	private void jDR2TextFieldKeyTyped(KeyEvent evt) {
		GKD.sendCommand("set dr2=" + dr2TextField.getText());
	}

	private void jDR3TextFieldKeyTyped(KeyEvent evt) {
		GKD.sendCommand("set dr3=" + dr3TextField.getText());
	}

	private void jDR6TextFieldKeyTyped(KeyEvent evt) {
		GKD.sendCommand("set dr6=" + dr6TextField.getText());
	}

	private void jDR7TextFieldKeyTyped(KeyEvent evt) {
		GKD.sendCommand("set dr7=" + dr7TextField.getText());
	}
}
