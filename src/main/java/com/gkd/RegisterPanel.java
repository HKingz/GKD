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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;

import com.gkd.components.segmentregister.SegmentRegister;
import com.gkd.components.segmentregister.SegmentRegisterFactory;
import com.gkd.stub.VMController;
import com.peterswing.CommonLib;
import com.peterswing.advancedswing.jprogressbardialog.JProgressBarDialog;
import com.gkd.components.segmentregister.SegmentRegisterHeader;

public class RegisterPanel extends JPanel {
	private JLabel eaxLabel;
	public JTextField eaxTextField;
	private JLabel ecxLabel;
	private JLabel edxLabel;
	private JLabel esiLabel;
	private JLabel ssLabel;
	private JLabel gsLabel;
	private JLabel fsLabel;
	public JLabel esLabel;
	public JLabel dsLabel;
	public SegmentRegister csTextField;
	public SegmentRegister gsTextField;
	public SegmentRegister ssTextField;
	public JTextField eipTextField;
	public JTextField cr0TextField;
	public JTextField dr7TextField;
	public JTextField dr6TextField;
	public JTextField dr3TextField;
	public JTextField dr2TextField;
	public JTextField dr1TextField;
	private JLabel dr7Label;
	private JLabel dr6Label;
	private JLabel dr3Label;
	private JLabel dr2Label;
	private JLabel dr1Label;
	public JTextField dr0TextField;
	private JLabel dr0Label;
	private JLabel jLabel25;
	public JTextField trTextField;
	public JTextField idtrTextField;
	public JTextField ldtrTextField;
	public JTextField gdtrTextField;
	private JLabel trLabel;
	private JLabel idtrLabel;
	private JLabel ldtrLabel;
	private JLabel gdtrLabel;
	public JTextField cr4TextField;
	public JTextField cr3TextField;
	public JTextField fdsTextField;
	public JTextField fdpTextField;
	public JTextField fcsTextField;
	public JTextField fipTextField;
	private JLabel fdsLabel;
	private JLabel fdpLabel;
	private JLabel fcsLabel;
	private JLabel fipLabel;
	public JTextField pTimeTextField;
	private JLabel pTimeLabel;
	public JTextField fpuOperandTextField;
	public JTextField fpuTagTextField;
	public JTextField fpuControlTextField;
	public JTextField fpuStatusTextField;
	private JLabel jLabel4;
	private JLabel jLabel3;
	private JLabel jLabel2;
	private JLabel jLabel1;
	public JTextField mmx7TextField;
	public JTextField mmx6TextField;
	public JTextField mmx5TextField;
	public JTextField mmx4TextField;
	public JTextField mmx3TextField;
	public JTextField mmx2TextField;
	public JTextField mmx1TextField;
	public JTextField mmx0TextField;
	private JLabel mm7Label;
	private JLabel mm6Label;
	private JLabel mm5Label;
	private JLabel mm4Label;
	private JLabel mm3Label;
	private JLabel mm2Label;
	private JLabel mm1Label;
	private JLabel mm0Label;
	private JLabel st7Label;
	private JLabel st6Label;
	private JLabel st5Label;
	private JLabel st4Label;
	private JLabel st3Label;
	private JLabel st2Label;
	private JButton exportExcelButton;
	private JButton diskButton;
	public JList stackList;
	public JTextField gdtrLimitTextField;
	public JTextField cr2TextField;
	public JTextField st7TextField;
	public JTextField st6TextField;
	public JTextField st5TextField;
	public JTextField st4TextField;
	public JTextField st3TextField;
	public JTextField st2TextField;
	public JTextField st1TextField;
	public JTextField st0TextField;
	public JLabel cr0DetailLabel2;
	public JLabel cr0DetailLabel;
	public JLabel eflagLabel;
	public JTextField idtrLimitTextField;
	private JLabel jCR4Label;
	private JLabel jCR3Label;
	private JLabel jCR2Label;
	public JLabel jCR0Label;
	public JTextField eflagsTextField;
	private JLabel eflagsLabel;
	private JLabel eIPLabel;
	public SegmentRegister fsTextField;
	public SegmentRegister esTextField;
	public SegmentRegister dsTextField;
	private JLabel csLabel;
	private JPanel mainPanel;
	public JTextField espTextField;
	public JTextField ebpTextField;
	public JTextField ediTextField;
	public JTextField esiTextField;
	private JLabel eSPLabel;
	private JLabel eBPLabel;
	private JLabel eDILabel;
	public JTextField edxTextField;
	public JTextField ecxTextField;
	public JTextField ebxTextField;
	private JLabel eBXLabel;
	GKD gkd;
	BorderLayout thisLayout = new BorderLayout();
	private JLabel st0Label;
	private JLabel st1Label;
	private JPanel panel;
	private JScrollPane stackScrollPane;
	private SegmentRegisterHeader segmentRegisterHeader;
	public JLabel cr4DetailLabel;

	public RegisterPanel() {
		super();
		try {
			this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			setLayout(new BorderLayout());

			mainPanel = new JPanel();
			this.add(mainPanel, BorderLayout.CENTER);
			mainPanel.setLayout(new MigLayout("insets 0", "[][48px][grow][10px][30px][120px][10px][36px][100px][10px][30px][120px][10px][64px,grow][10px][30px][120px]",
					"[][][][][][][][][][][][][][][grow][][][21.00][][][][][28px][]"));

			segmentRegisterHeader = new SegmentRegisterHeader();
			mainPanel.add(segmentRegisterHeader, "cell 2 1,grow");

			csLabel = new JLabel();
			mainPanel.add(csLabel, "cell 1 2,growx,aligny center");
			csLabel.setText("cs");
			csLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					jCSLabelMouseClicked(evt);
				}
			});

			csTextField = SegmentRegisterFactory.createSegmentRegister();
			csTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					csTextFieldKeyTyped(evt);
				}
			});
			mainPanel.add(csTextField, "cell 2 2,growx,aligny center");

			panel = new JPanel();
			mainPanel.add(panel, "cell 0 2 1 3,grow");
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

			diskButton = new JButton();
			panel.add(diskButton);
			diskButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/disk.png")));

			exportExcelButton = new JButton();
			panel.add(exportExcelButton);
			exportExcelButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/excel.gif")));
			exportExcelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					exportExcelButtonActionPerformed(evt);
				}
			});
			diskButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					diskButtonActionPerformed(evt);
				}
			});

			dsLabel = new JLabel();
			mainPanel.add(dsLabel, "cell 1 4,growx,aligny center");
			dsLabel.setText("ds");
			dsLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					dsLabelMouseClicked(evt);
				}
			});

			esLabel = new JLabel();
			mainPanel.add(esLabel, "cell 1 5,growx,aligny center");
			esLabel.setText("es");
			esLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					esLabelMouseClicked(evt);
				}
			});

			fsLabel = new JLabel();
			mainPanel.add(fsLabel, "cell 1 6,growx,aligny center");
			fsLabel.setText("fs");
			fsLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					fsLabelMouseClicked(evt);
				}
			});

			pTimeLabel = new JLabel();
			mainPanel.add(pTimeLabel, "cell 10 6,alignx left,aligny center");
			pTimeLabel.setText("ptime");

			pTimeTextField = new JTextField();
			pTimeTextField.putClientProperty("NoBorder", true);
			mainPanel.add(pTimeTextField, "cell 11 6 3 1,growx,aligny center");

			gsLabel = new JLabel();
			mainPanel.add(gsLabel, "cell 1 7,growx,aligny center");
			gsLabel.setText("gs");
			gsLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					gsLabelMouseClicked(evt);
				}
			});

			eSPLabel = new JLabel();
			mainPanel.add(eSPLabel, "cell 10 7,growx,aligny center");
			eSPLabel.setText("esp");
			eSPLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					espLabelMouseClicked(evt);
				}
			});

			espTextField = new JTextField();
			espTextField.putClientProperty("NoBorder", true);
			mainPanel.add(espTextField, "cell 11 7 3 1,growx,aligny center");
			espTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					espTextFieldKeyTyped(evt);
				}
			});

			ssLabel = new JLabel();
			mainPanel.add(ssLabel, "cell 1 8,growx,aligny center");
			ssLabel.setText("ss");
			ssLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					ssLabelMouseClicked(evt);
				}
			});

			dsTextField = SegmentRegisterFactory.createSegmentRegister();
			mainPanel.add(dsTextField, "cell 2 4,growx,aligny center");
			dsTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					dsTextFieldKeyTyped(evt);
				}
			});

			esTextField = SegmentRegisterFactory.createSegmentRegister();
			mainPanel.add(esTextField, "cell 2 5,growx,aligny center");
			esTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					esTextFieldKeyTyped(evt);
				}
			});

			fsTextField = SegmentRegisterFactory.createSegmentRegister();
			mainPanel.add(fsTextField, "cell 2 6,growx,aligny center");
			fsTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					fsTextFieldKeyTyped(evt);
				}
			});

			gsTextField = SegmentRegisterFactory.createSegmentRegister();
			mainPanel.add(gsTextField, "cell 2 7,growx,aligny center");
			gsTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					gsTextFieldKeyTyped(evt);
				}
			});

			ssTextField = SegmentRegisterFactory.createSegmentRegister();
			mainPanel.add(ssTextField, "cell 2 8,growx,aligny center");
			ssTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					ssTextFieldKeyTyped(evt);
				}
			});

			eIPLabel = new JLabel();
			mainPanel.add(eIPLabel, "cell 1 3,growx,aligny center");
			eIPLabel.setText("eip");
			eIPLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					eIPLabelMouseClicked(evt);
				}
			});

			eipTextField = new JTextField();
			eipTextField.putClientProperty("NoBorder", true);
			mainPanel.add(eipTextField, "cell 2 3,growx,aligny center");
			eipTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					eipTextFieldKeyTyped(evt);
				}
			});

			cr4DetailLabel = new JLabel();
			mainPanel.add(cr4DetailLabel, "cell 7 8 5 1,growx");

			jLabel25 = new JLabel();
			mainPanel.add(jLabel25, "cell 13 8,growx,aligny center");
			jLabel25.setText(MyLanguage.getString("Stack"));

			eflagsLabel = new JLabel();
			mainPanel.add(eflagsLabel, "cell 1 9,growx,aligny center");
			eflagsLabel.setText("eflags");
			eflagsLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					eFlagsLabelMouseClicked(evt);
				}
			});

			eflagsTextField = new JTextField();
			eflagsTextField.putClientProperty("NoBorder", true);
			mainPanel.add(eflagsTextField, "cell 2 9,growx,aligny center");

			eflagLabel = new JLabel("-");
			mainPanel.add(eflagLabel, "cell 4 9 8 1,growx,aligny center");

			stackList = new JList();
			stackList.putClientProperty("NoBorder", true);
			stackList.setBorder(new LineBorder(new java.awt.Color(200, 200, 200), 1, false));

			st0Label = new JLabel();
			st0Label.setText("ST0");
			mainPanel.add(st0Label, "cell 1 10");

			st0TextField = new JTextField();
			st0TextField.putClientProperty("NoBorder", true);
			mainPanel.add(st0TextField, "cell 2 10 4 1,growx,aligny center");

			mm0Label = new JLabel();
			mainPanel.add(mm0Label, "cell 7 10,growx,aligny center");
			mm0Label.setText("MM0");

			mmx0TextField = new JTextField();
			mmx0TextField.putClientProperty("NoBorder", true);
			mainPanel.add(mmx0TextField, "cell 8 10 4 1,growx,aligny center");

			st1Label = new JLabel();
			st1Label.setText("ST1");
			mainPanel.add(st1Label, "flowx,cell 1 11");

			st1TextField = new JTextField();
			st1TextField.putClientProperty("NoBorder", true);
			mainPanel.add(st1TextField, "cell 2 11 4 1,growx,aligny center");

			mm1Label = new JLabel();
			mainPanel.add(mm1Label, "cell 7 11,growx,aligny center");
			mm1Label.setText("MM1");

			mmx1TextField = new JTextField();
			mmx1TextField.putClientProperty("NoBorder", true);
			mainPanel.add(mmx1TextField, "cell 8 11 4 1,growx,aligny center");

			st2Label = new JLabel();
			mainPanel.add(st2Label, "cell 1 12,growx,aligny center");
			st2Label.setText("ST2");

			st2TextField = new JTextField();
			st2TextField.putClientProperty("NoBorder", true);
			mainPanel.add(st2TextField, "cell 2 12 4 1,growx,aligny center");

			mm2Label = new JLabel();
			mainPanel.add(mm2Label, "cell 7 12,growx,aligny center");
			mm2Label.setText("MM2");

			mmx2TextField = new JTextField();
			mmx2TextField.putClientProperty("NoBorder", true);
			mainPanel.add(mmx2TextField, "cell 8 12 4 1,growx,aligny center");

			st3Label = new JLabel();
			mainPanel.add(st3Label, "cell 1 13,growx,aligny center");
			st3Label.setText("ST3");

			st3TextField = new JTextField();
			st3TextField.putClientProperty("NoBorder", true);
			mainPanel.add(st3TextField, "cell 2 13 4 1,growx,aligny center");

			mm3Label = new JLabel();
			mainPanel.add(mm3Label, "cell 7 13,growx,aligny center");
			mm3Label.setText("MM3");

			mmx3TextField = new JTextField();
			mmx3TextField.putClientProperty("NoBorder", true);
			mainPanel.add(mmx3TextField, "cell 8 13 4 1,growx,aligny center");

			st4Label = new JLabel();
			mainPanel.add(st4Label, "cell 1 14,growx,aligny center");
			st4Label.setText("ST4");

			st4TextField = new JTextField();
			st4TextField.putClientProperty("NoBorder", true);
			mainPanel.add(st4TextField, "cell 2 14 4 1,growx,aligny center");

			mm4Label = new JLabel();
			mainPanel.add(mm4Label, "cell 7 14,growx,aligny center");
			mm4Label.setText("MM4");

			mmx4TextField = new JTextField();
			mmx4TextField.putClientProperty("NoBorder", true);
			mainPanel.add(mmx4TextField, "cell 8 14 4 1,growx,aligny center");

			stackScrollPane = new JScrollPane();
			stackScrollPane.setViewportView(stackList);
			mainPanel.add(stackScrollPane, "cell 13 9 4 13,grow");

			st5Label = new JLabel();
			mainPanel.add(st5Label, "cell 1 15,growx,aligny center");
			st5Label.setText("ST5");

			st5TextField = new JTextField();
			st5TextField.putClientProperty("NoBorder", true);
			mainPanel.add(st5TextField, "cell 2 15 4 1,growx,aligny center");

			mm5Label = new JLabel();
			mainPanel.add(mm5Label, "cell 7 15,growx,aligny center");
			mm5Label.setText("MM5");

			mmx5TextField = new JTextField();
			mmx5TextField.putClientProperty("NoBorder", true);
			mainPanel.add(mmx5TextField, "cell 8 15 4 1,growx,aligny center");

			st6Label = new JLabel();
			mainPanel.add(st6Label, "cell 1 16,growx,aligny center");
			st6Label.setText("ST6");

			st6TextField = new JTextField();
			st6TextField.putClientProperty("NoBorder", true);
			mainPanel.add(st6TextField, "cell 2 16 4 1,growx,aligny center");

			mm6Label = new JLabel();
			mainPanel.add(mm6Label, "cell 7 16,growx,aligny center");
			mm6Label.setText("MM6");

			mmx6TextField = new JTextField();
			mmx6TextField.putClientProperty("NoBorder", true);
			mainPanel.add(mmx6TextField, "cell 8 16 4 1,growx,aligny center");

			st7Label = new JLabel();
			mainPanel.add(st7Label, "cell 1 17,growx,aligny center");
			st7Label.setText("ST7");

			st7TextField = new JTextField();
			st7TextField.putClientProperty("NoBorder", true);
			mainPanel.add(st7TextField, "cell 2 17 4 1,growx,aligny center");

			mm7Label = new JLabel();
			mainPanel.add(mm7Label, "cell 7 17,growx,aligny center");
			mm7Label.setText("MM7");

			mmx7TextField = new JTextField();
			mmx7TextField.putClientProperty("NoBorder", true);
			mainPanel.add(mmx7TextField, "cell 8 17 4 1,growx,aligny center");

			jLabel1 = new JLabel();
			mainPanel.add(jLabel1, "cell 1 18,growx,aligny center");
			jLabel1.setText("Status");

			fpuStatusTextField = new JTextField();
			fpuStatusTextField.putClientProperty("NoBorder", true);
			mainPanel.add(fpuStatusTextField, "cell 2 18 4 1,growx,aligny center");

			fcsLabel = new JLabel();
			mainPanel.add(fcsLabel, "cell 7 18,growx,aligny center");
			fcsLabel.setText("fcs");

			fcsTextField = new JTextField();
			fcsTextField.putClientProperty("NoBorder", true);
			mainPanel.add(fcsTextField, "cell 8 18 4 1,growx,aligny center");

			jLabel2 = new JLabel();
			mainPanel.add(jLabel2, "cell 1 19,growx,aligny center");
			jLabel2.setText("Control");

			fpuControlTextField = new JTextField();
			fpuControlTextField.putClientProperty("NoBorder", true);
			mainPanel.add(fpuControlTextField, "cell 2 19 4 1,growx,aligny center");

			fdpLabel = new JLabel();
			mainPanel.add(fdpLabel, "cell 7 19,growx,aligny center");
			fdpLabel.setText("fdp");

			fdpTextField = new JTextField();
			fdpTextField.putClientProperty("NoBorder", true);
			mainPanel.add(fdpTextField, "cell 8 19 4 1,growx,aligny center");

			jLabel3 = new JLabel();
			mainPanel.add(jLabel3, "cell 1 20,growx,aligny center");
			jLabel3.setText("Tag");

			fpuTagTextField = new JTextField();
			fpuTagTextField.putClientProperty("NoBorder", true);
			mainPanel.add(fpuTagTextField, "cell 2 20 4 1,growx,aligny center");

			fdsLabel = new JLabel();
			mainPanel.add(fdsLabel, "cell 7 20,growx,aligny center");
			fdsLabel.setText("fds");

			fdsTextField = new JTextField();
			fdsTextField.putClientProperty("NoBorder", true);
			mainPanel.add(fdsTextField, "cell 8 20 4 1,growx,aligny center");

			jLabel4 = new JLabel();
			mainPanel.add(jLabel4, "cell 1 21,growx,aligny center");
			jLabel4.setText("Operand");

			fpuOperandTextField = new JTextField();
			fpuOperandTextField.putClientProperty("NoBorder", true);
			mainPanel.add(fpuOperandTextField, "cell 2 21 4 1,growx,aligny center");

			eaxLabel = new JLabel();
			mainPanel.add(eaxLabel, "cell 4 2,growx,aligny center");
			eaxLabel.setText("eax");
			eaxLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					eaxLabelMouseClicked(evt);
				}
			});

			eBXLabel = new JLabel();
			mainPanel.add(eBXLabel, "cell 4 3,growx,aligny center");
			eBXLabel.setText("ebx");
			eBXLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					ebxLabelMouseClicked(evt);
				}
			});

			ecxLabel = new JLabel();
			mainPanel.add(ecxLabel, "cell 4 4,growx,aligny center");
			ecxLabel.setText("ecx");
			ecxLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					ecxLabelMouseClicked(evt);
				}
			});

			edxLabel = new JLabel();
			mainPanel.add(edxLabel, "cell 4 5,growx,aligny center");
			edxLabel.setText("edx");
			edxLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					edxLabelMouseClicked(evt);
				}
			});

			esiLabel = new JLabel();
			mainPanel.add(esiLabel, "cell 4 6,growx,aligny center");
			esiLabel.setText("esi");
			esiLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					esiLabelMouseClicked(evt);
				}
			});

			eDILabel = new JLabel();
			mainPanel.add(eDILabel, "cell 4 7,growx,aligny center");
			eDILabel.setText("edi");
			eDILabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					ediLabel6MouseClicked(evt);
				}
			});

			eBPLabel = new JLabel();
			mainPanel.add(eBPLabel, "cell 4 8,growx,aligny center");
			eBPLabel.setText("ebp");
			eBPLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					eBPLabelMouseClicked(evt);
				}
			});

			eaxTextField = new JTextField();
			eaxTextField.putClientProperty("NoBorder", true);
			mainPanel.add(eaxTextField, "cell 5 2,growx,aligny center");
			eaxTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					eaxTextFieldKeyTyped(evt);
				}
			});

			ebxTextField = new JTextField();
			ebxTextField.putClientProperty("NoBorder", true);
			mainPanel.add(ebxTextField, "cell 5 3,growx,aligny center");
			ebxTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					ebxTextFieldKeyTyped(evt);
				}
			});

			ecxTextField = new JTextField();
			ecxTextField.putClientProperty("NoBorder", true);
			mainPanel.add(ecxTextField, "cell 5 4,growx,aligny center");
			ecxTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					ecxTextFieldKeyTyped(evt);
				}
			});

			edxTextField = new JTextField();
			edxTextField.putClientProperty("NoBorder", true);
			mainPanel.add(edxTextField, "cell 5 5,growx,aligny center");
			edxTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					edxTextFieldKeyTyped(evt);
				}
			});

			esiTextField = new JTextField();
			esiTextField.putClientProperty("NoBorder", true);
			mainPanel.add(esiTextField, "cell 5 6,growx,aligny center");
			esiTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					esiTextFieldKeyTyped(evt);
				}
			});

			ediTextField = new JTextField();
			ediTextField.putClientProperty("NoBorder", true);
			mainPanel.add(ediTextField, "cell 5 7,growx,aligny center");
			ediTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					ediTextFieldKeyTyped(evt);
				}
			});

			ebpTextField = new JTextField();
			ebpTextField.putClientProperty("NoBorder", true);
			mainPanel.add(ebpTextField, "cell 5 8,growx,aligny center");
			ebpTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					ebpTextFieldKeyTyped(evt);
				}
			});

			jCR0Label = new JLabel();
			mainPanel.add(jCR0Label, "cell 7 2,growx,aligny center");
			jCR0Label.setText("cr0");
			jCR0Label.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					cr0LabelMouseClicked(evt);
				}
			});

			cr0TextField = new JTextField();
			cr0TextField.putClientProperty("NoBorder", true);
			mainPanel.add(cr0TextField, "cell 8 2,growx,aligny center");
			cr0TextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					cr0TextFieldKeyTyped(evt);
				}
			});

			jCR2Label = new JLabel();
			mainPanel.add(jCR2Label, "cell 7 5,growx,aligny center");
			jCR2Label.setText("cr2");
			jCR2Label.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					jCR2LabelMouseClicked(evt);
				}
			});

			cr2TextField = new JTextField();
			cr2TextField.putClientProperty("NoBorder", true);
			mainPanel.add(cr2TextField, "cell 8 5,growx,aligny center");
			cr2TextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					cr2TextFieldKeyTyped(evt);
				}
			});

			jCR3Label = new JLabel();
			mainPanel.add(jCR3Label, "cell 7 6,growx,aligny center");
			jCR3Label.setText("cr3");
			jCR3Label.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					cr3LabelMouseClicked(evt);
				}
			});

			cr3TextField = new JTextField();
			cr3TextField.putClientProperty("NoBorder", true);
			mainPanel.add(cr3TextField, "cell 8 6,growx,aligny center");
			cr3TextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					cr3TextFieldKeyTyped(evt);
				}
			});

			jCR4Label = new JLabel();
			mainPanel.add(jCR4Label, "cell 7 7,growx,aligny center");
			jCR4Label.setText("cr4");
			jCR4Label.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					cr4LabelMouseClicked(evt);
				}
			});

			cr4TextField = new JTextField();
			cr4TextField.putClientProperty("NoBorder", true);
			mainPanel.add(cr4TextField, "cell 8 7,growx,aligny center");
			cr4TextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					cr4TextFieldKeyTyped(evt);
				}
			});

			gdtrTextField = new JTextField();
			gdtrTextField.putClientProperty("NoBorder", true);
			mainPanel.add(gdtrTextField, "cell 11 2,growx,aligny center");
			gdtrTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					gdtrTextFieldKeyTyped(evt);
				}
			});

			gdtrLabel = new JLabel();
			mainPanel.add(gdtrLabel, "cell 10 2,growx,aligny center");
			gdtrLabel.setText("gdtr");
			gdtrLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					jGDTRLabelMouseClicked(evt);
				}
			});

			gdtrLimitTextField = new JTextField();
			gdtrLimitTextField.putClientProperty("NoBorder", true);
			mainPanel.add(gdtrLimitTextField, "cell 13 2,growx,aligny center");

			ldtrLabel = new JLabel();
			mainPanel.add(ldtrLabel, "cell 10 3,growx,aligny center");
			ldtrLabel.setText("ldtr");
			ldtrLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					jLDTRLabelMouseClicked(evt);
				}
			});

			ldtrTextField = new JTextField();
			ldtrTextField.putClientProperty("NoBorder", true);
			mainPanel.add(ldtrTextField, "cell 11 3,growx,aligny center");
			ldtrTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					ldtrTextFieldKeyTyped(evt);
				}
			});

			idtrLabel = new JLabel();
			mainPanel.add(idtrLabel, "cell 10 4,growx,aligny center");
			idtrLabel.setText("idtr");
			idtrLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					idtrLabelMouseClicked(evt);
				}
			});

			idtrTextField = new JTextField();
			idtrTextField.putClientProperty("NoBorder", true);
			mainPanel.add(idtrTextField, "cell 11 4,growx,aligny center");
			idtrTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					idtrTextFieldKeyTyped(evt);
				}
			});

			idtrLimitTextField = new JTextField();
			idtrLimitTextField.putClientProperty("NoBorder", true);
			mainPanel.add(idtrLimitTextField, "cell 13 4,growx,aligny center");

			trLabel = new JLabel();
			mainPanel.add(trLabel, "cell 10 5,growx,aligny center");
			trLabel.setText("tr");
			trLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					trLabelMouseClicked(evt);
				}
			});

			trTextField = new JTextField();
			trTextField.putClientProperty("NoBorder", true);
			mainPanel.add(trTextField, "cell 11 5 3 1,growx,aligny center");
			trTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					trTextFieldKeyTyped(evt);
				}
			});

			cr0DetailLabel = new JLabel("");
			mainPanel.add(cr0DetailLabel, "cell 7 3 2 1,growx,aligny center");

			cr0DetailLabel2 = new JLabel();
			mainPanel.add(cr0DetailLabel2, "cell 7 4 2 1,growx,aligny center");

			dr0Label = new JLabel();
			mainPanel.add(dr0Label, "cell 15 2,growx,aligny center");
			dr0Label.setText("DR0");
			dr0Label.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					dr0LabelMouseClicked(evt);
				}
			});

			dr1Label = new JLabel();
			mainPanel.add(dr1Label, "cell 15 3,growx,aligny center");
			dr1Label.setText("DR1");
			dr1Label.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					dr1LabelMouseClicked(evt);
				}
			});

			dr2Label = new JLabel();
			mainPanel.add(dr2Label, "cell 15 4,growx,aligny center");
			dr2Label.setText("DR2");
			dr2Label.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					dr2LabelMouseClicked(evt);
				}
			});

			dr3Label = new JLabel();
			mainPanel.add(dr3Label, "cell 15 5,growx,aligny center");
			dr3Label.setText("DR3");
			dr3Label.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					dr3LabelMouseClicked(evt);
				}
			});

			dr6Label = new JLabel();
			mainPanel.add(dr6Label, "cell 15 6,growx,aligny center");
			dr6Label.setText("DR6");
			dr6Label.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					dr6LabelMouseClicked(evt);
				}
			});

			dr7Label = new JLabel();
			mainPanel.add(dr7Label, "cell 15 7,growx,aligny center");
			dr7Label.setText("DR7");
			dr7Label.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					dr7LabelMouseClicked(evt);
				}
			});

			dr0TextField = new JTextField();
			dr0TextField.putClientProperty("NoBorder", true);
			mainPanel.add(dr0TextField, "cell 16 2,growx,aligny center");
			dr0TextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					dr0TextFieldKeyTyped(evt);
				}
			});

			dr1TextField = new JTextField();
			dr1TextField.putClientProperty("NoBorder", true);
			mainPanel.add(dr1TextField, "cell 16 3,growx,aligny center");
			dr1TextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					dr1TextFieldKeyTyped(evt);
				}
			});

			dr2TextField = new JTextField();
			dr2TextField.putClientProperty("NoBorder", true);
			mainPanel.add(dr2TextField, "cell 16 4,growx,aligny center");
			dr2TextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					dr2TextFieldKeyTyped(evt);
				}
			});

			dr3TextField = new JTextField();
			dr3TextField.putClientProperty("NoBorder", true);
			mainPanel.add(dr3TextField, "cell 16 5,growx,aligny center");
			dr3TextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					dr3TextFieldKeyTyped(evt);
				}
			});

			dr6TextField = new JTextField();
			dr6TextField.putClientProperty("NoBorder", true);
			mainPanel.add(dr6TextField, "cell 16 6,growx,aligny center");
			dr6TextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					dr6TextFieldKeyTyped(evt);
				}
			});

			dr7TextField = new JTextField();
			dr7TextField.putClientProperty("NoBorder", true);
			mainPanel.add(dr7TextField, "cell 16 7,growx,aligny center");
			dr7TextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					dr7TextFieldKeyTyped(evt);
				}
			});

			fipLabel = new JLabel();
			mainPanel.add(fipLabel, "cell 7 21,growx,aligny center");
			fipLabel.setText("fip");

			fipTextField = new JTextField();
			fipTextField.putClientProperty("NoBorder", true);
			mainPanel.add(fipTextField, "cell 8 21 4 1,growx,aligny center");

			for (final Component component : mainPanel.getComponents()) {
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

	public RegisterPanel(GKD gkd) {
		this();
		this.gkd = gkd;

	}

	private void diskButtonActionPerformed(ActionEvent evt) {
		final JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(gkd);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			if (!GKDCommonLib.saveImage(this.getParent(), file)) {
				JOptionPane.showMessageDialog(this, "Cannot save image", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void exportExcelButtonActionPerformed(ActionEvent evt) {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(gkd);
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
			final JProgressBarDialog d = new JProgressBarDialog(gkd, "Exporting to XLS", true);
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
		gkd.memoryAddressComboBox.setSelectedItem(this.csTextField.getText());
	}

	private void eIPLabelMouseClicked(MouseEvent evt) {
		gkd.memoryAddressComboBox.setSelectedItem(this.eipTextField.getText());
	}

	private void dsLabelMouseClicked(MouseEvent evt) {
		gkd.memoryAddressComboBox.setSelectedItem(this.dsTextField.getText());
	}

	private void esLabelMouseClicked(MouseEvent evt) {
		gkd.memoryAddressComboBox.setSelectedItem(this.esTextField.getText());
	}

	private void fsLabelMouseClicked(MouseEvent evt) {
		gkd.memoryAddressComboBox.setSelectedItem(this.fsTextField.getText());
	}

	private void gsLabelMouseClicked(MouseEvent evt) {
		gkd.memoryAddressComboBox.setSelectedItem(this.gsTextField.getText());
	}

	private void ssLabelMouseClicked(MouseEvent evt) {
		gkd.memoryAddressComboBox.setSelectedItem(this.ssTextField.getText());
	}

	private void eFlagsLabelMouseClicked(MouseEvent evt) {
		gkd.memoryAddressComboBox.setSelectedItem(this.eflagsTextField.getText());
	}

	private void eaxLabelMouseClicked(MouseEvent evt) {
		gkd.memoryAddressComboBox.setSelectedItem(this.eaxTextField.getText());
	}

	private void ebxLabelMouseClicked(MouseEvent evt) {
		gkd.memoryAddressComboBox.setSelectedItem(this.ebxTextField.getText());
	}

	private void ecxLabelMouseClicked(MouseEvent evt) {
		gkd.memoryAddressComboBox.setSelectedItem(this.ecxTextField.getText());
	}

	private void edxLabelMouseClicked(MouseEvent evt) {
		gkd.memoryAddressComboBox.setSelectedItem(this.edxTextField.getText());
	}

	private void esiLabelMouseClicked(MouseEvent evt) {
		gkd.memoryAddressComboBox.setSelectedItem(this.esiTextField.getText());
	}

	private void ediLabel6MouseClicked(MouseEvent evt) {
		gkd.memoryAddressComboBox.setSelectedItem(this.ediTextField.getText());
	}

	private void eBPLabelMouseClicked(MouseEvent evt) {
		gkd.memoryAddressComboBox.setSelectedItem(this.ebpTextField.getText());
	}

	private void espLabelMouseClicked(MouseEvent evt) {
		gkd.memoryAddressComboBox.setSelectedItem(this.espTextField.getText());
	}

	private void jGDTRLabelMouseClicked(MouseEvent evt) {
		gkd.memoryAddressComboBox.setSelectedItem(this.gdtrTextField.getText());
	}

	private void jLDTRLabelMouseClicked(MouseEvent evt) {
		gkd.memoryAddressComboBox.setSelectedItem(this.ldtrTextField.getText());
	}

	private void idtrLabelMouseClicked(MouseEvent evt) {
		gkd.memoryAddressComboBox.setSelectedItem(this.idtrTextField.getText());
	}

	private void trLabelMouseClicked(MouseEvent evt) {
		gkd.memoryAddressComboBox.setSelectedItem(this.trTextField.getText());
	}

	private void cr0LabelMouseClicked(MouseEvent evt) {
		gkd.memoryAddressComboBox.setSelectedItem(this.cr0TextField.getText());
	}

	private void jCR2LabelMouseClicked(MouseEvent evt) {
		gkd.memoryAddressComboBox.setSelectedItem(this.cr2TextField.getText());
	}

	private void cr3LabelMouseClicked(MouseEvent evt) {
		gkd.memoryAddressComboBox.setSelectedItem(this.cr3TextField.getText());
	}

	private void cr4LabelMouseClicked(MouseEvent evt) {
		gkd.memoryAddressComboBox.setSelectedItem(this.cr4TextField.getText());
	}

	private void dr0LabelMouseClicked(MouseEvent evt) {
		gkd.memoryAddressComboBox.setSelectedItem(this.dr0TextField.getText());
	}

	private void dr1LabelMouseClicked(MouseEvent evt) {
		gkd.memoryAddressComboBox.setSelectedItem(this.dr1TextField.getText());
	}

	private void dr2LabelMouseClicked(MouseEvent evt) {
		gkd.memoryAddressComboBox.setSelectedItem(this.dr2TextField.getText());
	}

	private void dr3LabelMouseClicked(MouseEvent evt) {
		gkd.memoryAddressComboBox.setSelectedItem(this.dr3TextField.getText());
	}

	private void dr6LabelMouseClicked(MouseEvent evt) {
		gkd.memoryAddressComboBox.setSelectedItem(this.dr6TextField.getText());
	}

	private void dr7LabelMouseClicked(MouseEvent evt) {
		gkd.memoryAddressComboBox.setSelectedItem(this.dr7TextField.getText());
	}

	private void textFieldKeyTyped(KeyEvent evt) {
		if (evt.getKeyChar() == '\n') {
			System.out.println("haven't implement yet");
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

	private void csTextFieldKeyTyped(KeyEvent evt) {
		VMController.getVM().changeReigsterValue("cs", CommonLib.string2BigInteger(csTextField.getText()));
	}

	private void eipTextFieldKeyTyped(KeyEvent evt) {
		VMController.getVM().changeReigsterValue("eip", CommonLib.string2BigInteger(eipTextField.getText()));
	}

	private void dsTextFieldKeyTyped(KeyEvent evt) {
		VMController.getVM().changeReigsterValue("ds", CommonLib.string2BigInteger(dsTextField.getText()));
	}

	private void esTextFieldKeyTyped(KeyEvent evt) {
		VMController.getVM().changeReigsterValue("es", CommonLib.string2BigInteger(esTextField.getText()));
	}

	private void fsTextFieldKeyTyped(KeyEvent evt) {
		VMController.getVM().changeReigsterValue("fs", CommonLib.string2BigInteger(fsTextField.getText()));
	}

	private void gsTextFieldKeyTyped(KeyEvent evt) {
		VMController.getVM().changeReigsterValue("gs", CommonLib.string2BigInteger(gsTextField.getText()));
	}

	private void ssTextFieldKeyTyped(KeyEvent evt) {
		VMController.getVM().changeReigsterValue("ss", CommonLib.string2BigInteger(ssTextField.getText()));
	}

	private void eaxTextFieldKeyTyped(KeyEvent evt) {
		VMController.getVM().changeReigsterValue("eax", CommonLib.string2BigInteger(eaxTextField.getText()));
	}

	private void ebxTextFieldKeyTyped(KeyEvent evt) {
		VMController.getVM().changeReigsterValue("ebx", CommonLib.string2BigInteger(ebxTextField.getText()));
	}

	private void ecxTextFieldKeyTyped(KeyEvent evt) {
		VMController.getVM().changeReigsterValue("ecx", CommonLib.string2BigInteger(ecxTextField.getText()));
	}

	private void edxTextFieldKeyTyped(KeyEvent evt) {
		VMController.getVM().changeReigsterValue("edx", CommonLib.string2BigInteger(edxTextField.getText()));
	}

	private void esiTextFieldKeyTyped(KeyEvent evt) {
		VMController.getVM().changeReigsterValue("esi", CommonLib.string2BigInteger(esiTextField.getText()));
	}

	private void ediTextFieldKeyTyped(KeyEvent evt) {
		VMController.getVM().changeReigsterValue("edi", CommonLib.string2BigInteger(ediTextField.getText()));
	}

	private void ebpTextFieldKeyTyped(KeyEvent evt) {
		VMController.getVM().changeReigsterValue("ebp", CommonLib.string2BigInteger(ebpTextField.getText()));
	}

	private void espTextFieldKeyTyped(KeyEvent evt) {
		VMController.getVM().changeReigsterValue("esp", CommonLib.string2BigInteger(espTextField.getText()));
	}

	private void cr0TextFieldKeyTyped(KeyEvent evt) {
		VMController.getVM().changeReigsterValue("cr0", CommonLib.string2BigInteger(cr0TextField.getText()));
	}

	private void cr2TextFieldKeyTyped(KeyEvent evt) {
		VMController.getVM().changeReigsterValue("cr2", CommonLib.string2BigInteger(cr2TextField.getText()));
	}

	private void cr3TextFieldKeyTyped(KeyEvent evt) {
		VMController.getVM().changeReigsterValue("cr3", CommonLib.string2BigInteger(cr3TextField.getText()));
	}

	private void cr4TextFieldKeyTyped(KeyEvent evt) {
		VMController.getVM().changeReigsterValue("cr4", CommonLib.string2BigInteger(cr4TextField.getText()));
	}

	private void gdtrTextFieldKeyTyped(KeyEvent evt) {
		VMController.getVM().changeReigsterValue("gdtr", CommonLib.string2BigInteger(gdtrTextField.getText()));
	}

	private void ldtrTextFieldKeyTyped(KeyEvent evt) {
		VMController.getVM().changeReigsterValue("ldtr", CommonLib.string2BigInteger(ldtrTextField.getText()));
	}

	private void idtrTextFieldKeyTyped(KeyEvent evt) {
		VMController.getVM().changeReigsterValue("idtr", CommonLib.string2BigInteger(idtrTextField.getText()));
	}

	private void trTextFieldKeyTyped(KeyEvent evt) {
		VMController.getVM().changeReigsterValue("tr", CommonLib.string2BigInteger(trTextField.getText()));
	}

	private void dr0TextFieldKeyTyped(KeyEvent evt) {
		VMController.getVM().changeReigsterValue("dr0", CommonLib.string2BigInteger(dr0TextField.getText()));
	}

	private void dr1TextFieldKeyTyped(KeyEvent evt) {
		VMController.getVM().changeReigsterValue("dr1", CommonLib.string2BigInteger(dr1TextField.getText()));
	}

	private void dr2TextFieldKeyTyped(KeyEvent evt) {
		VMController.getVM().changeReigsterValue("dr2", CommonLib.string2BigInteger(dr2TextField.getText()));
	}

	private void dr3TextFieldKeyTyped(KeyEvent evt) {
		VMController.getVM().changeReigsterValue("dr3", CommonLib.string2BigInteger(dr3TextField.getText()));
	}

	private void dr6TextFieldKeyTyped(KeyEvent evt) {
		VMController.getVM().changeReigsterValue("dr6", CommonLib.string2BigInteger(dr6TextField.getText()));
	}

	private void dr7TextFieldKeyTyped(KeyEvent evt) {
		VMController.getVM().changeReigsterValue("dr7", CommonLib.string2BigInteger(dr7TextField.getText()));
	}
}
