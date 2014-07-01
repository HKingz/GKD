package com.gkd;

import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.peterswing.advancedswing.searchtextfield.JSearchTextField;

public class SettingDialog extends JDialog {
	private JCheckBox checkBox1;
	private JCheckBox checkBox2;
	private JButton browseDwarfdumpButton;
	private JButton jBrowseObjdumpButton;
	private JSearchTextField jDwarfdumpTextField;
	private JSearchTextField jObjdumpTextField;
	private JLabel jLabel3;
	private JLabel objdump;
	private JLabel jLabel2;
	private JPanel jThirdPartyProgramPanel;
	private JCheckBox fastStepHistoryCheckBox;
	private JCheckBox logToPetersoftServerCheckBox;
	private JCheckBox jInterruptProfilingCheckBox;
	private JLabel jProfileInfoLabel;
	private JLabel jLabel1;
	private JCheckBox jUpdateGraphCheckBox;
	private JCheckBox jJmpProfilingCheckBox;
	private JCheckBox jCustomZoneCheckBox;
	private JCheckBox jHitZoneCheckBox;
	private JCheckBox jMemoryProfilingCheckBox;
	private JPanel jProfilingPanel;
	private JCheckBox fastStepIDTCheckBox;
	private JCheckBox fastStepLDTCheckBox;
	private JCheckBox fastStepGDTCheckBox;
	private JCheckBox fastStepBreakpointCheckBox;
	private JCheckBox fastStepInstructionCheckBox;
	private JCheckBox fastStepMemoryCheckBox;
	private JCheckBox fastStepRegisterCheckBox;
	private JPanel jPanel2;
	private JLabel jLabel9;
	private JCheckBox gkdIDTCheckBox;
	private JCheckBox gkdLDTCheckBox;
	private JCheckBox gkdGDTCheckBox;
	private JTabbedPane jTabbedPane1;
	private JPanel jPanel3;
	private JCheckBox gkdHistoryCheckBox;
	private JCheckBox gkdAddressTranslateCheckBox;
	private JCheckBox gkdStackCheckBox;
	private JCheckBox gkdPageTableCheckBox;
	private JCheckBox gkdBreakpointCheckBox;
	private JCheckBox gkdInstructionCheckBox;
	private JCheckBox gkdMemoryCheckBox;
	private JCheckBox gkdRegisterCheckBox;
	private JPanel jPanel1;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				SettingDialog inst = new SettingDialog(frame);
				inst.setVisible(true);
			}
		});
	}

	public SettingDialog(JFrame frame) {
		super(frame, true);
		try {
			this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			BorderLayout thisLayout = new BorderLayout();
			getContentPane().setLayout(thisLayout);
			this.setTitle("Setting");

			jTabbedPane1 = new JTabbedPane();
			getContentPane().add(jTabbedPane1, BorderLayout.CENTER);

			jPanel3 = new JPanel();
			TableLayout jPanel3Layout = new TableLayout(new double[][] { { TableLayout.FILL, TableLayout.FILL, TableLayout.FILL, TableLayout.FILL },
					{ TableLayout.FILL, TableLayout.FILL, 99.0, TableLayout.FILL, 70.0, TableLayout.FILL, TableLayout.FILL } });
			jPanel3Layout.setHGap(5);
			jPanel3Layout.setVGap(5);
			jPanel3.setLayout(jPanel3Layout);
			jTabbedPane1.addTab("General", null, jPanel3, null);

			checkBox1 = new JCheckBox();
			jPanel3.add(checkBox1, "0, 0, 3, 0");
			checkBox1.setText(MyLanguage.getString("Load_breakpoint_at_startup"));
			checkBox1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jCheckBox1ActionPerformed(evt);
				}
			});

			checkBox2 = new JCheckBox();
			jPanel3.add(checkBox2, "0, 1, 3, 1");
			checkBox2.setText(MyLanguage.getString("Update_status_after_gkd_command"));
			checkBox2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jCheckBox2ActionPerformed(evt);
				}
			});

			jPanel1 = new JPanel();
			jPanel3.add(jPanel1, "0, 2, 3, 2");
			TableLayout jPanel1Layout = new TableLayout(new double[][] { { 34.0, TableLayout.FILL, TableLayout.FILL, TableLayout.PREFERRED },
					{ 15.0, 15.0, 15.0, TableLayout.PREFERRED } });
			jPanel1Layout.setHGap(10);
			jPanel1Layout.setVGap(5);
			jPanel1.setLayout(jPanel1Layout);

			gkdRegisterCheckBox = new JCheckBox();
			jPanel1.add(gkdRegisterCheckBox, "1, 0");
			gkdRegisterCheckBox.setText(MyLanguage.getString("Register"));
			gkdRegisterCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jInterruptProfilingCheckBoxActionPerformed(evt);
				}
			});

			gkdMemoryCheckBox = new JCheckBox();
			jPanel1.add(gkdMemoryCheckBox, "2, 0");
			gkdMemoryCheckBox.setText("Memory");
			gkdMemoryCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jCheckBox4ActionPerformed(evt);
				}
			});

			gkdInstructionCheckBox = new JCheckBox();
			jPanel1.add(gkdInstructionCheckBox, "3, 0");
			gkdInstructionCheckBox.setText(MyLanguage.getString("Instruction"));
			gkdInstructionCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jCheckBox5ActionPerformed(evt);
				}
			});

			gkdBreakpointCheckBox = new JCheckBox();
			jPanel1.add(gkdBreakpointCheckBox, "1, 1");
			gkdBreakpointCheckBox.setText(MyLanguage.getString("Breakpoint"));
			gkdBreakpointCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jCheckBox6ActionPerformed(evt);
				}
			});

			gkdGDTCheckBox = new JCheckBox();
			jPanel1.add(gkdGDTCheckBox, "2, 1");
			gkdGDTCheckBox.setText(MyLanguage.getString("GDT"));
			gkdGDTCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jCheckBox7ActionPerformed(evt);
				}
			});

			gkdLDTCheckBox = new JCheckBox();
			jPanel1.add(gkdLDTCheckBox, "3, 1");
			gkdLDTCheckBox.setText(MyLanguage.getString("LDT"));
			gkdLDTCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jCheckBox8ActionPerformed(evt);
				}
			});

			gkdIDTCheckBox = new JCheckBox();
			jPanel1.add(gkdIDTCheckBox, "1, 2");
			gkdIDTCheckBox.setText(MyLanguage.getString("IDT"));
			gkdIDTCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jCheckBox9ActionPerformed(evt);
				}
			});

			gkdPageTableCheckBox = new JCheckBox();
			jPanel1.add(gkdPageTableCheckBox, "2, 2");
			gkdPageTableCheckBox.setText(MyLanguage.getString("Page_table"));
			gkdPageTableCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jGKDPageTableCheckBoxActionPerformed(evt);
				}
			});

			gkdStackCheckBox = new JCheckBox();
			jPanel1.add(gkdStackCheckBox, "3, 2");
			gkdStackCheckBox.setText(MyLanguage.getString("Stack"));
			gkdStackCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jGKDStackCheckBoxActionPerformed(evt);
				}
			});

			gkdAddressTranslateCheckBox = new JCheckBox();
			jPanel1.add(gkdAddressTranslateCheckBox, "1, 3, 2, 3");
			gkdAddressTranslateCheckBox.setText(MyLanguage.getString("Address_translate"));
			gkdAddressTranslateCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jGKDAddressTranslateCheckBoxActionPerformed(evt);
				}
			});

			gkdHistoryCheckBox = new JCheckBox();
			jPanel1.add(gkdHistoryCheckBox, "3, 3");
			gkdHistoryCheckBox.setText(MyLanguage.getString("History"));
			gkdHistoryCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jGKDHistoryCheckBoxActionPerformed(evt);
				}
			});

			jLabel9 = new JLabel();
			jPanel3.add(jLabel9, "0, 3, 3, 3");
			jLabel9.setText(MyLanguage.getString("What_panel_will_update_after_fast_step_button"));

			jPanel2 = new JPanel();
			jPanel3.add(jPanel2, "0, 4, 3, 4");
			TableLayout jPanel2Layout = new TableLayout(new double[][] { { 34.0, TableLayout.FILL, TableLayout.FILL, TableLayout.PREFERRED },
					{ 15.0, 15.0, 15.0, TableLayout.FILL } });
			jPanel2Layout.setHGap(10);
			jPanel2Layout.setVGap(5);
			jPanel2.setLayout(jPanel2Layout);

			fastStepRegisterCheckBox = new JCheckBox();
			jPanel2.add(fastStepRegisterCheckBox, "1, 0");
			fastStepRegisterCheckBox.setText(MyLanguage.getString("Register"));
			fastStepRegisterCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					fastStepRegisterCheckBoxActionPerformed(evt);
				}
			});

			fastStepMemoryCheckBox = new JCheckBox();
			jPanel2.add(fastStepMemoryCheckBox, "2, 0");
			fastStepMemoryCheckBox.setText(MyLanguage.getString("Memory"));
			fastStepMemoryCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					fastStepMemoryCheckBoxActionPerformed(evt);
				}
			});

			fastStepInstructionCheckBox = new JCheckBox();
			jPanel2.add(fastStepInstructionCheckBox, "3, 0");
			fastStepInstructionCheckBox.setText(MyLanguage.getString("Instruction"));
			fastStepInstructionCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					fastStepInstructionCheckBoxActionPerformed(evt);
				}
			});

			fastStepBreakpointCheckBox = new JCheckBox();
			jPanel2.add(fastStepBreakpointCheckBox, "1, 1");
			fastStepBreakpointCheckBox.setText(MyLanguage.getString("Breakpoint"));
			fastStepBreakpointCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					fastStepBreakpointCheckBoxActionPerformed(evt);
				}
			});

			fastStepGDTCheckBox = new JCheckBox();
			jPanel2.add(fastStepGDTCheckBox, "2, 1");
			fastStepGDTCheckBox.setText(MyLanguage.getString(MyLanguage.getString("GDT")));
			fastStepGDTCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					fastStepGDTCheckBoxActionPerformed(evt);
				}
			});

			fastStepLDTCheckBox = new JCheckBox();
			jPanel2.add(fastStepLDTCheckBox, "3, 1");
			fastStepLDTCheckBox.setText(MyLanguage.getString(MyLanguage.getString("LDT")));
			fastStepLDTCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					fastStepLDTCheckBoxActionPerformed(evt);
				}
			});

			fastStepIDTCheckBox = new JCheckBox();
			jPanel2.add(fastStepIDTCheckBox, "1, 2");
			fastStepIDTCheckBox.setText(MyLanguage.getString(MyLanguage.getString("IDT")));
			fastStepIDTCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					fastStepIDTCheckBoxActionPerformed(evt);
				}
			});

			fastStepHistoryCheckBox = new JCheckBox();
			jPanel2.add(fastStepHistoryCheckBox, "2, 2");
			fastStepHistoryCheckBox.setText(MyLanguage.getString("History"));
			fastStepHistoryCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					fastHistoryCheckBoxActionPerformed(evt);
				}
			});

			logToPetersoftServerCheckBox = new JCheckBox();
			jPanel3.add(logToPetersoftServerCheckBox, "0, 5, 1, 5");
			logToPetersoftServerCheckBox.setText("Log to Petersoft Server?");
			logToPetersoftServerCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jLogToPetersoftServerCheckBoxActionPerformed(evt);
				}
			});

			jProfilingPanel = new JPanel();
			GroupLayout jProfilingPanelLayout = new GroupLayout((JComponent) jProfilingPanel);
			jProfilingPanel.setLayout(jProfilingPanelLayout);
			jTabbedPane1.addTab("Profiling", null, jProfilingPanel, null);

			jMemoryProfilingCheckBox = new JCheckBox();
			jMemoryProfilingCheckBox.setText("Memory profiling");
			jMemoryProfilingCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jMemoryProfilingCheckBoxActionPerformed(evt);
				}
			});

			jLabel1 = new JLabel();
			jLabel1.setText("You need to restart peter-gkd if you change the following settings");
			jLabel1.setForeground(new java.awt.Color(255, 0, 0));

			jProfileInfoLabel = new JLabel();

			jJmpProfilingCheckBox = new JCheckBox();
			jJmpProfilingCheckBox.setText("Jmp profiling");
			jJmpProfilingCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jJmpProfilingCheckBoxActionPerformed(evt);
				}
			});

			jUpdateGraphCheckBox = new JCheckBox();
			jUpdateGraphCheckBox.setText("Update graph");
			jUpdateGraphCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jUpdateGraphCheckBoxActionPerformed(evt);
				}
			});

			jHitZoneCheckBox = new JCheckBox();
			jHitZoneCheckBox.setText("Hit zone");
			jHitZoneCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jHitZoneCheckBoxActionPerformed(evt);
				}
			});

			jCustomZoneCheckBox = new JCheckBox();
			jCustomZoneCheckBox.setText("Custom zone");
			jCustomZoneCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jCustomZoneCheckBoxActionPerformed(evt);
				}
			});

			jInterruptProfilingCheckBox = new JCheckBox();
			jInterruptProfilingCheckBox.setText("Interrupt profiling");
			jInterruptProfilingCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jInterruptProfilingCheckBoxActionPerformed(evt);
				}
			});

			jProfilingPanelLayout
					.setHorizontalGroup(jProfilingPanelLayout
							.createSequentialGroup()
							.addContainerGap()
							.addGroup(
									jProfilingPanelLayout
											.createParallelGroup()
											.addGroup(
													GroupLayout.Alignment.LEADING,
													jProfilingPanelLayout.createSequentialGroup()
															.addComponent(jInterruptProfilingCheckBox, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)
															.addGap(0, 208, Short.MAX_VALUE))
											.addGroup(
													GroupLayout.Alignment.LEADING,
													jProfilingPanelLayout.createSequentialGroup()
															.addComponent(jUpdateGraphCheckBox, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
															.addGap(0, 268, Short.MAX_VALUE))
											.addGroup(
													GroupLayout.Alignment.LEADING,
													jProfilingPanelLayout.createSequentialGroup()
															.addComponent(jJmpProfilingCheckBox, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
															.addGap(0, 251, Short.MAX_VALUE))
											.addGroup(
													GroupLayout.Alignment.LEADING,
													jProfilingPanelLayout.createSequentialGroup()
															.addComponent(jMemoryProfilingCheckBox, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
															.addGap(0, 199, Short.MAX_VALUE))
											.addComponent(jLabel1, GroupLayout.Alignment.LEADING, 0, 411, Short.MAX_VALUE)
											.addComponent(jProfileInfoLabel, GroupLayout.Alignment.LEADING, 0, 411, Short.MAX_VALUE)
											.addGroup(
													GroupLayout.Alignment.LEADING,
													jProfilingPanelLayout.createSequentialGroup().addGap(19)
															.addComponent(jHitZoneCheckBox, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
															.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
															.addComponent(jCustomZoneCheckBox, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
															.addGap(0, 167, Short.MAX_VALUE))).addContainerGap());
			jProfilingPanelLayout.setVerticalGroup(jProfilingPanelLayout
					.createSequentialGroup()
					.addContainerGap()
					.addComponent(jProfileInfoLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(24)
					.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(jMemoryProfilingCheckBox, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(
							jProfilingPanelLayout
									.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(jHitZoneCheckBox, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(jCustomZoneCheckBox, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
											GroupLayout.PREFERRED_SIZE)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(jJmpProfilingCheckBox, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(jInterruptProfilingCheckBox, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jUpdateGraphCheckBox, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(50, Short.MAX_VALUE));

			jThirdPartyProgramPanel = new JPanel();
			TableLayout jThirdPartyProgramPanelLayout = new TableLayout(new double[][] { { 7.0, TableLayout.FILL, TableLayout.FILL, 159.0, 54.0, 7.0 },
					{ 7.0, 15.0, 20.0, 7.0, 20.0, TableLayout.FILL } });
			jThirdPartyProgramPanelLayout.setHGap(5);
			jThirdPartyProgramPanelLayout.setVGap(5);
			jThirdPartyProgramPanel.setLayout(jThirdPartyProgramPanelLayout);
			jTabbedPane1.addTab("Third party program", null, jThirdPartyProgramPanel, null);
			jThirdPartyProgramPanel.setPreferredSize(new java.awt.Dimension(442, 301));

			jLabel2 = new JLabel();
			jThirdPartyProgramPanel.add(jLabel2, "1, 1, 4, 1");
			jLabel2.setText("Clear the path to use the default path");

			objdump = new JLabel();
			jThirdPartyProgramPanel.add(objdump, "1, 2");
			objdump.setText("objdump");
			objdump.setHorizontalAlignment(SwingConstants.TRAILING);

			jLabel3 = new JLabel();
			jThirdPartyProgramPanel.add(jLabel3, "1, 4");
			jLabel3.setText("dwarfdump");
			jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);

			jObjdumpTextField = new JSearchTextField();
			jThirdPartyProgramPanel.add(jObjdumpTextField, "2, 2, 3, 2");
			jObjdumpTextField.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent evt) {
					jObjdumpTextFieldKeyReleased(evt);
				}
			});

			jDwarfdumpTextField = new JSearchTextField();
			jThirdPartyProgramPanel.add(jDwarfdumpTextField, "2, 4, 3, 4");
			jDwarfdumpTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					jDwarfdumpTextFieldKeyTyped(evt);
				}
			});

			jBrowseObjdumpButton = new JButton();
			jThirdPartyProgramPanel.add(jBrowseObjdumpButton, "4, 2");
			jBrowseObjdumpButton.setText("...");
			jBrowseObjdumpButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jBrowseObjdumpButtonActionPerformed(evt);
				}
			});

			browseDwarfdumpButton = new JButton();
			jThirdPartyProgramPanel.add(browseDwarfdumpButton, "4, 4");
			browseDwarfdumpButton.setText("...");
			browseDwarfdumpButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jBrowseDwarfdumpButtonActionPerformed(evt);
				}
			});

			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent evt) {
					thisWindowClosing(evt);
				}
			});
			jProfileInfoLabel.setText("Memory profile port : " + Global.profilingMemoryPort + " , jmp profile port : " + Global.profilingJmpPort + " , interrupt profile port : "
					+ Global.profilingInterruptPort);

			setSize(450, 350);
			initValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initValue() {
		checkBox1.setSelected(Setting.getInstance().loadBreakpointAtStartup);
		checkBox2.setSelected(Setting.getInstance().updateAfterGKDCommand);

		gkdRegisterCheckBox.setSelected(Setting.getInstance().updateAfterGKDCommand_register);
		gkdMemoryCheckBox.setSelected(Setting.getInstance().updateAfterGKDCommand_memory);
		gkdInstructionCheckBox.setSelected(Setting.getInstance().updateAfterGKDCommand_instruction);
		gkdBreakpointCheckBox.setSelected(Setting.getInstance().updateAfterGKDCommand_breakpoint);
		gkdGDTCheckBox.setSelected(Setting.getInstance().updateAfterGKDCommand_gdt);
		gkdLDTCheckBox.setSelected(Setting.getInstance().updateAfterGKDCommand_ldt);
		gkdIDTCheckBox.setSelected(Setting.getInstance().updateAfterGKDCommand_idt);
		gkdPageTableCheckBox.setSelected(Setting.getInstance().updateAfterGKDCommand_pageTable);
		gkdAddressTranslateCheckBox.setSelected(Setting.getInstance().updateAfterGKDCommand_addressTranslate);
		gkdStackCheckBox.setSelected(Setting.getInstance().updateAfterGKDCommand_stack);
		gkdHistoryCheckBox.setSelected(Setting.getInstance().updateAfterGKDCommand_history);

		fastStepIDTCheckBox.setSelected(Setting.getInstance().updateFastStepCommand_idt);
		fastStepLDTCheckBox.setSelected(Setting.getInstance().updateFastStepCommand_ldt);
		fastStepGDTCheckBox.setSelected(Setting.getInstance().updateFastStepCommand_gdt);
		fastStepBreakpointCheckBox.setSelected(Setting.getInstance().updateFastStepCommand_breakpoint);
		fastStepInstructionCheckBox.setSelected(Setting.getInstance().updateFastStepCommand_instruction);
		fastStepMemoryCheckBox.setSelected(Setting.getInstance().updateFastStepCommand_memory);
		fastStepRegisterCheckBox.setSelected(Setting.getInstance().updateFastStepCommand_register);
		fastStepHistoryCheckBox.setSelected(Setting.getInstance().updateFastStepCommand_history);

		jMemoryProfilingCheckBox.setSelected(Setting.getInstance().memoryProfiling);
		jHitZoneCheckBox.setSelected(Setting.getInstance().hitZone);
		jCustomZoneCheckBox.setSelected(Setting.getInstance().customZone);
		jJmpProfilingCheckBox.setSelected(Setting.getInstance().jmpProfiling);
		jInterruptProfilingCheckBox.setSelected(Setting.getInstance().interruptProfiling);
		jUpdateGraphCheckBox.setSelected(Setting.getInstance().profilingUpdateGraph);

		logToPetersoftServerCheckBox.setSelected(Setting.getInstance().logToPetersoftServer);

		jObjdumpTextField.setText(Setting.getInstance().path_objdump);
		jDwarfdumpTextField.setText(Setting.getInstance().path_dwarfdump);
	}

	private void jCheckBox1ActionPerformed(ActionEvent evt) {
		Setting.getInstance().loadBreakpointAtStartup = checkBox1.isSelected();
	}

	private void jCheckBox2ActionPerformed(ActionEvent evt) {
		Setting.getInstance().updateAfterGKDCommand = checkBox2.isSelected();
	}

	private void thisWindowClosing(WindowEvent evt) {
		Setting.getInstance().save();
	}

	private void jInterruptProfilingCheckBoxActionPerformed(ActionEvent evt) {
		Setting.getInstance().interruptProfiling = jInterruptProfilingCheckBox.isSelected();
	}

	private void jCheckBox4ActionPerformed(ActionEvent evt) {
		Setting.getInstance().updateAfterGKDCommand_memory = gkdMemoryCheckBox.isSelected();
	}

	private void jCheckBox5ActionPerformed(ActionEvent evt) {
		Setting.getInstance().updateAfterGKDCommand_instruction = gkdInstructionCheckBox.isSelected();
	}

	private void jCheckBox6ActionPerformed(ActionEvent evt) {
		Setting.getInstance().updateAfterGKDCommand_breakpoint = gkdBreakpointCheckBox.isSelected();
	}

	private void jCheckBox7ActionPerformed(ActionEvent evt) {
		Setting.getInstance().updateAfterGKDCommand_gdt = gkdGDTCheckBox.isSelected();
	}

	private void jCheckBox8ActionPerformed(ActionEvent evt) {
		Setting.getInstance().updateAfterGKDCommand_ldt = gkdLDTCheckBox.isSelected();
	}

	private void jCheckBox9ActionPerformed(ActionEvent evt) {
		Setting.getInstance().updateAfterGKDCommand_idt = gkdIDTCheckBox.isSelected();
	}

	private void jGKDPageTableCheckBoxActionPerformed(ActionEvent evt) {
		Setting.getInstance().updateAfterGKDCommand_pageTable = gkdPageTableCheckBox.isSelected();
	}

	private void jGKDStackCheckBoxActionPerformed(ActionEvent evt) {
		Setting.getInstance().updateAfterGKDCommand_stack = gkdStackCheckBox.isSelected();
	}

	private void jGKDAddressTranslateCheckBoxActionPerformed(ActionEvent evt) {
		Setting.getInstance().updateAfterGKDCommand_addressTranslate = gkdAddressTranslateCheckBox.isSelected();
	}

	private void jGKDHistoryCheckBoxActionPerformed(ActionEvent evt) {
		Setting.getInstance().updateAfterGKDCommand_history = gkdHistoryCheckBox.isSelected();
	}

	private void fastStepRegisterCheckBoxActionPerformed(ActionEvent evt) {
		Setting.getInstance().updateFastStepCommand_register = fastStepRegisterCheckBox.isSelected();
	}

	private void fastStepMemoryCheckBoxActionPerformed(ActionEvent evt) {
		Setting.getInstance().updateFastStepCommand_memory = fastStepMemoryCheckBox.isSelected();
	}

	private void fastStepInstructionCheckBoxActionPerformed(ActionEvent evt) {
		Setting.getInstance().updateFastStepCommand_instruction = fastStepInstructionCheckBox.isSelected();
	}

	private void fastStepBreakpointCheckBoxActionPerformed(ActionEvent evt) {
		Setting.getInstance().updateFastStepCommand_breakpoint = fastStepBreakpointCheckBox.isSelected();
	}

	private void fastStepGDTCheckBoxActionPerformed(ActionEvent evt) {
		Setting.getInstance().updateFastStepCommand_gdt = fastStepGDTCheckBox.isSelected();
	}

	private void fastStepLDTCheckBoxActionPerformed(ActionEvent evt) {
		Setting.getInstance().updateFastStepCommand_ldt = fastStepLDTCheckBox.isSelected();
	}

	private void fastStepIDTCheckBoxActionPerformed(ActionEvent evt) {
		Setting.getInstance().updateFastStepCommand_idt = fastStepIDTCheckBox.isSelected();
	}

	private void jMemoryProfilingCheckBoxActionPerformed(ActionEvent evt) {
		Setting.getInstance().memoryProfiling = jMemoryProfilingCheckBox.isSelected();
	}

	private void jHitZoneCheckBoxActionPerformed(ActionEvent evt) {
		Setting.getInstance().hitZone = jHitZoneCheckBox.isSelected();
	}

	private void jCustomZoneCheckBoxActionPerformed(ActionEvent evt) {
		Setting.getInstance().customZone = jCustomZoneCheckBox.isSelected();
	}

	private void jJmpProfilingCheckBoxActionPerformed(ActionEvent evt) {
		Setting.getInstance().jmpProfiling = jJmpProfilingCheckBox.isSelected();
	}

	private void jUpdateGraphCheckBoxActionPerformed(ActionEvent evt) {
		Setting.getInstance().profilingUpdateGraph = jUpdateGraphCheckBox.isSelected();
	}

	private void jLogToPetersoftServerCheckBoxActionPerformed(ActionEvent evt) {
		Setting.getInstance().logToPetersoftServer = logToPetersoftServerCheckBox.isSelected();
	}

	private void fastHistoryCheckBoxActionPerformed(ActionEvent evt) {
		Setting.getInstance().updateFastStepCommand_history = fastStepHistoryCheckBox.isSelected();
	}

	private void jObjdumpTextFieldKeyReleased(KeyEvent evt) {
		Setting.getInstance().path_objdump = jObjdumpTextField.getText();
	}

	private void jBrowseObjdumpButtonActionPerformed(ActionEvent evt) {
		JFileChooser jChooser = new JFileChooser();
		int result = jChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = jChooser.getSelectedFile();

			if (file.exists()) {
				jObjdumpTextField.setText(file.getAbsolutePath());
				jObjdumpTextFieldKeyReleased(null);
			}
		}
	}

	private void jBrowseDwarfdumpButtonActionPerformed(ActionEvent evt) {
		JFileChooser jChooser = new JFileChooser();
		int result = jChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = jChooser.getSelectedFile();

			if (file.exists()) {
				jDwarfdumpTextField.setText(file.getAbsolutePath());
				jDwarfdumpTextFieldKeyTyped(null);
			}
		}
	}

	private void jDwarfdumpTextFieldKeyTyped(KeyEvent evt) {
		Setting.getInstance().path_dwarfdump = jObjdumpTextField.getText();
	}
}
