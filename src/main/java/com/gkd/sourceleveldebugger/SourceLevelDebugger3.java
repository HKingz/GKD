package com.gkd.sourceleveldebugger;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.TreeSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.TreePath;

import org.apache.commons.io.IOUtils;

import com.gkd.GKD;
import com.gkd.GKDCommonLib;
import com.gkd.Global;
import com.gkd.InstructionTableCellRenderer;
import com.gkd.InstructionTableModel;
import com.gkd.MyLanguage;
import com.gkd.instrument.CallGraphComponent;
import com.gkd.instrument.PeterSwingCanvas;
import com.mxgraph.canvas.mxICanvas;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;
import com.peterdwarf.dwarf.Dwarf;
import com.peterdwarf.dwarf.DwarfDebugLineHeader;
import com.peterdwarf.dwarf.DwarfHeaderFilename;
import com.peterdwarf.elf.Elf32_Sym;
import com.peterdwarf.gui.PeterDwarfPanel;
import com.peterswing.CommonLib;
import com.peterswing.FilterTreeModel;
import com.peterswing.advancedswing.jmaximizabletabbedpane.JMaximizableTabbedPane;
import com.peterswing.advancedswing.jmaximizabletabbedpane.JMaximizableTabbedPane_BasePanel;
import com.peterswing.advancedswing.jprogressbardialog.JProgressBarDialogEventListener;
import com.peterswing.advancedswing.onoffbutton.OnOffButton;
import com.peterswing.advancedswing.searchtextfield.JSearchTextField;

public class SourceLevelDebugger3 extends JMaximizableTabbedPane_BasePanel implements JProgressBarDialogEventListener {
	private JSplitPane jMainSplitPane;
	private JPanel panel1;
	private JPanel panel6;
	private JSplitPane splitPane3;
	private JPanel panel4;
	private JScrollPane scrollPane1;
	private JButton disassembleCSEIPButton;
	private JButton disassembleButton;
	private JComboBox instructionComboBox;
	public JScrollPane registerPanelScrollPane;
	private JTabbedPane infoTabbedPane;
	public JTable instructionTable;
	private JScrollPane instructionTableScrollPane;
	private JButton excelButton;
	private JButton jDiskButton;
	private JButton instructionDownButton;
	private JButton instructionUpButton;
	private JButton instructionUpTenButton;
	private JPanel jDwarfPanel;
	public PeterDwarfPanel peterDwarfPanel;
	private JPanel instructionControlPanel;
	private JPanel jASMPanel;
	private JPanel symbolTablePanel;
	private JSearchTextField searchProjectTextField;
	private JToolBar projectToolBar;
	private JMaximizableTabbedPane jMainTabbedPane;
	private JTree projectTree;
	private JPanel panel3;
	private JMaximizableTabbedPane jTabbedPane1;

	private GKD gkd;
	private File elfFile;
	private ProjectTreeNode root;
	private JLabel errorLabel;
	private JCheckBox symbolExactCheckBox;
	private JSearchTextField searchTextField;
	private JSearchTextField searchSymbolTextField;
	private JToolBar jToolBar1;
	private JTable symbolTable;
	private JScrollPane scrollPane11;
	private JCheckBox exactMatchCheckBox;

	FilterTreeModel projectFilterTreeModel = new FilterTreeModel(new ProjectTreeModel(null));
	SymbolTableModel symbolTableModel = new SymbolTableModel();
	private JButton btnSearch;
	private JPanel panel2;
	private JButton refreshCallGrapphButton;
	private JToolBar callGraphToolBar;
	private JPanel callGraphPanel;
	private JTextField searchCodeBaseTextField;
	private JButton refreshCodeBaseButton;
	private JToolBar jToolBar2;
	private JTable codeBaseTable;
	private JScrollPane codeBaseScrollPane;
	private JPanel codeBasePanel;
	private OnOffButton onOffButton;
	TableRowSorter<TableModel> sorter;
	mxGraph graph;
	CallGraphComponent graphComponent;

	public SourceLevelDebugger3(GKD gkd) {
		this.gkd = gkd;
		try {
			this.setPreferredSize(new java.awt.Dimension(975, 563));
			{
				jMainSplitPane = new JSplitPane();
				this.add(getJErrorLabel(), "errorLabel");
				this.add(jMainSplitPane, "MAIN");
				jMainSplitPane.setDividerLocation(200);
				{
					panel4 = new JPanel();
					jMainSplitPane.add(panel4, JSplitPane.RIGHT);
					BorderLayout panel4Layout = new BorderLayout();
					panel4.setLayout(panel4Layout);
					{
						splitPane3 = new JSplitPane();
						panel4.add(splitPane3, BorderLayout.CENTER);
						splitPane3.setOrientation(JSplitPane.VERTICAL_SPLIT);
						splitPane3.setDividerLocation(500);
						{
							panel6 = new JPanel();
							splitPane3.add(panel6, JSplitPane.TOP);
							splitPane3.add(getJInfoTabbedPane(), JSplitPane.BOTTOM);
							BorderLayout panel6Layout = new BorderLayout();
							panel6.setLayout(panel6Layout);
							{
								jMainTabbedPane = new JMaximizableTabbedPane();
								panel6.add(jMainTabbedPane, BorderLayout.CENTER);
								{
									jASMPanel = new JPanel();
									jMainTabbedPane.addTab(MyLanguage.getString("ASM/C"), null, jASMPanel, null);
									jMainTabbedPane.addTab("Dwarf", null, getJDwarfPanel(), null);
									jMainTabbedPane.addTab("Code base", null, getCodeBasePanel(), null);
									jMainTabbedPane.addTab("Call Graph", null, getCallGraphPanel(), null);
									BorderLayout jASMPanelLayout = new BorderLayout();
									jASMPanel.setLayout(jASMPanelLayout);
									{
										instructionTableScrollPane = new JScrollPane();
										instructionTableScrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
											boolean isRunning;

											public void adjustmentValueChanged(AdjustmentEvent evt) {
												/*	JScrollBar vbar = (JScrollBar) evt.getSource();

													if (evt.getValueIsAdjusting()) {
														return;
													}
													if ((vbar.getValue() + vbar.getVisibleAmount()) == vbar.getMaximum()) {
														if (!isRunning) {
															try {
																isRunning = true;
																final CardLayout cl = (CardLayout) (gkd.jMainPanel.getLayout());
																cl.show(gkd.jMainPanel, "Running Label");
																//															new Thread("update instruction thread") {
																//																public void run() {
																//																	long address = Long.parseLong(instructionTable.getValueAt(instructionTable.getRowCount() - 1, 1).toString()
																//																			.substring(2), 16);
																//																	gkd.updateInstruction(address, true);
																//																	gkd.updateBreakpointTableColor();
																////																	cl.show(gkd.jMainPanel, gkd.currentPanel);
																//
																//																	isRunning = false;
																//																}
																//															}.start();

															} catch (Exception ex) {
															}
														}

													}*/
											}

										});
										jASMPanel.add(instructionTableScrollPane, BorderLayout.CENTER);
										{
											instructionTable = new JTable();
											instructionTableScrollPane.setViewportView(instructionTable);
											instructionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
											instructionTable.setModel(GKD.instructionTable.getModel());
											instructionTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
											instructionTable.getTableHeader().setReorderingAllowed(false);
											instructionTable.getColumnModel().getColumn(0).setMaxWidth(20);
											instructionTable.getColumnModel().getColumn(1).setPreferredWidth(40);
											instructionTable.getColumnModel().getColumn(2).setPreferredWidth(200);
											instructionTable.getColumnModel().getColumn(3).setPreferredWidth(40);
											instructionTable.setShowGrid(false);
											instructionTable.setDefaultRenderer(String.class, new InstructionTableCellRenderer());
											instructionTable.addMouseListener(new MouseAdapter() {
												public void mouseClicked(MouseEvent evt) {
													instructionTableMouseClicked(evt);
												}
											});
										}
									}
									{
										instructionControlPanel = new JPanel();
										jASMPanel.add(instructionControlPanel, BorderLayout.NORTH);
										{
											instructionComboBox = new JComboBox();
											instructionControlPanel.add(instructionComboBox);
											instructionComboBox.setEditable(true);
											instructionComboBox.addActionListener(new ActionListener() {
												public void actionPerformed(ActionEvent evt) {
													instructionComboBoxActionPerformed(evt);
												}
											});
										}
										{
											disassembleButton = new JButton();
											instructionControlPanel.add(disassembleButton);
											disassembleButton.setText(MyLanguage.getString("Disassemble"));
											disassembleButton.addActionListener(new ActionListener() {
												public void actionPerformed(ActionEvent evt) {
													disassembleButtonActionPerformed(evt);
												}
											});
										}
										{
											disassembleCSEIPButton = new JButton();
											instructionControlPanel.add(disassembleCSEIPButton);
											disassembleCSEIPButton.setText(MyLanguage.getString("Disassemble") + " cs:eip");
											disassembleCSEIPButton.setEnabled(true);
											disassembleCSEIPButton.addActionListener(new ActionListener() {
												public void actionPerformed(ActionEvent evt) {
													disassembleCSEIPButtonActionPerformed(evt);
												}
											});
										}
										{
											instructionUpTenButton = new JButton();
											instructionControlPanel.add(instructionUpTenButton);
											instructionUpTenButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/arrow_up10.png")));
											instructionUpTenButton.addActionListener(new ActionListener() {
												public void actionPerformed(ActionEvent evt) {
													instructionUpTenButtonActionPerformed(evt);
												}
											});
										}
										{
											instructionUpButton = new JButton();
											instructionControlPanel.add(instructionUpButton);
											instructionUpButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/arrow_up1.png")));
											instructionUpButton.addActionListener(new ActionListener() {
												public void actionPerformed(ActionEvent evt) {
													instructionUpButtonActionPerformed(evt);
												}
											});
										}
										{
											instructionDownButton = new JButton();
											instructionControlPanel.add(instructionDownButton);
											instructionDownButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/arrow_down.png")));
											instructionDownButton.addActionListener(new ActionListener() {
												public void actionPerformed(ActionEvent evt) {
													instructionDownButtonActionPerformed(evt);
												}
											});
										}
										{
											jDiskButton = new JButton();
											instructionControlPanel.add(jDiskButton);
											jDiskButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/disk.png")));
											jDiskButton.addActionListener(new ActionListener() {
												public void actionPerformed(ActionEvent evt) {
													jButton3ActionPerformed(evt);
												}
											});
										}
										{
											excelButton = new JButton();
											instructionControlPanel.add(excelButton);
											instructionControlPanel.add(getJSearchTextField());
											excelButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/excel.gif")));
											{
												btnSearch = new JButton("Search");
												instructionControlPanel.add(btnSearch);
												btnSearch.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent evt) {
														btnSearchActionPerformed(evt);
													}
												});
											}
											{
												onOffButton = new OnOffButton();
												onOffButton.setSelected(true);
												onOffButton.addItemListener(new ItemListener() {
													public void itemStateChanged(ItemEvent e) {
														InstructionTableModel model = (InstructionTableModel) GKD.instructionTable.getModel();
														if (e.getStateChange() == ItemEvent.SELECTED) {
															model.showAsmLevel = true;
														} else {
															model.showAsmLevel = false;
														}
														model.fireTableDataChanged();
													}
												});
												onOffButton.setPreferredSize(new Dimension(53, 18));
												instructionControlPanel.add(onOffButton);
											}
											excelButton.addActionListener(new ActionListener() {
												public void actionPerformed(ActionEvent evt) {
													jButton12ActionPerformed(evt);
												}
											});
										}
									}
								}
							}
						}
					}
				}
				{
					panel1 = new JPanel();
					jMainSplitPane.add(panel1, JSplitPane.LEFT);
					BorderLayout panel1Layout = new BorderLayout();
					panel1.setLayout(panel1Layout);
					{
						jTabbedPane1 = new JMaximizableTabbedPane();
						panel1.add(jTabbedPane1, BorderLayout.CENTER);
						jTabbedPane1.addChangeListener(new ChangeListener() {
							public void stateChanged(ChangeEvent evt) {
								jTabbedPane1StateChanged(evt);
							}
						});
						{
							panel3 = new JPanel();
							BorderLayout panel3Layout = new BorderLayout();
							panel3.setLayout(panel3Layout);
							jTabbedPane1.addTab("Symbol", null, getSymbolTablePanel(), null);
							jTabbedPane1.addTab(MyLanguage.getString("Project"), null, panel3, null);
							{
								scrollPane1 = new JScrollPane();
								panel3.add(scrollPane1, BorderLayout.CENTER);
								panel3.add(getJProjectToolBar(), BorderLayout.NORTH);
								{
									projectTree = new JTree();
									scrollPane1.setViewportView(projectTree);
									projectTree.setModel(projectFilterTreeModel);
									projectTree.setShowsRootHandles(true);
									projectTree.setCellRenderer(new ProjectTreeRenderer());
									projectTree.addMouseListener(new MouseAdapter() {
										public void mouseClicked(MouseEvent evt) {
											projectTreeMouseClicked(evt);
										}
									});
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void projectTreeMouseClicked(MouseEvent evt) {
		//				* handleProjectTreeClick(jFileTree.getSelectionPath());
	}

	void handleProjectTreeClick(TreePath treePath) {
		/*
		try {
			if (treePath != null) {
				final ObjectFileTreeNode selectedTreeNode = (ObjectFileTreeNode) jFileTree.getSelectionPath().getLastPathComponent();
				if (selectedTreeNode != null) {
					if (selectedTreeNode.file == null) {
						return;
					}
					if (selectedTreeNode.file.length() >= 1024 * 1024 * 10) {
						int n = JOptionPane.showConfirmDialog(application, "File too large, continue?", "Warning", JOptionPane.YES_NO_OPTION);
						if (n == JOptionPane.NO_OPTION) {
							return;
						}
					}
					final JProgressBarDialog dialog = new JProgressBarDialog(application, "Dumping object/library", true);
					dialog.progressBar.setIndeterminate(true);
					dialog.progressBar.setStringPainted(true);
					// dialog.setSize(new Dimension(750, 150));
					dialog.addCancelEventListener(this);
					CommonLib.centerDialog(dialog);

					Thread thread = new Thread() {
						public void run() {
							try {
								String objectFile = selectedTreeNode.file.getAbsolutePath();
								Process process = Runtime.getRuntime().exec("objdump -dlS " + objectFile);
								InputStream input = process.getInputStream();

								String str = "";
								byte b[] = new byte[10240000];
								int len;
								int x = 0;
								cSourceTextArea.setText("");
								while ((len = input.read(b)) > 0) {
									str += new String(b, 0, len);
									x += len;
									dialog.progressBar.setString("objdump -dlS " + objectFile + ", " + String.valueOf(x));
								}
								input.close();
								dialog.progressBar.setString("Setting the result to textarea, please be patient");
								cSourceTextArea.pager.setVisible(true);
								cSourceTextArea.pageSize = 10000;
								cSourceTextArea.loadLargeFile(str);
								cSourceTextArea.refreshPage();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					};
					dialog.thread = thread;
					dialog.setVisible(true);
				}
			}
		} catch (Exception e) {
		}
		*/
	}

	private void instructionComboBoxActionPerformed(ActionEvent evt) {
		disassembleButtonActionPerformed(evt);
	}

	private void disassembleButtonActionPerformed(ActionEvent evt) {
		this.addInstructionComboBox(this.instructionComboBox.getSelectedItem().toString());
		disassembleCSEIPButton.setEnabled(false);
		try {
			gkd.updateInstruction(CommonLib.string2BigInteger(this.instructionComboBox.getSelectedItem().toString()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		gkd.updateBreakpointTableColor();
		disassembleCSEIPButton.setEnabled(true);
	}

	private void addInstructionComboBox(String str) {
		for (int x = 0; x < instructionComboBox.getItemCount(); x++) {
			if (this.instructionComboBox.getItemAt(x).toString().trim().equals(str.trim())) {
				return;
			}
		}

		instructionComboBox.addItem(str.trim());
	}

	private void disassembleCSEIPButtonActionPerformed(ActionEvent evt) {
		disassembleCSEIPButton.setEnabled(false);
		gkd.updateInstruction(null);
		gkd.updateBreakpointTableColor();
		gkd.jumpToRowInstructionTable(gkd.getRealEIP());
		disassembleCSEIPButton.setEnabled(true);
	}

	private void instructionUpTenButtonActionPerformed(ActionEvent evt) {
		gkd.instructionUpTenButtonActionPerformed(evt);
	}

	private void instructionUpButtonActionPerformed(ActionEvent evt) {
		gkd.instructionUpButtonActionPerformed(evt);
	}

	private void instructionDownButtonActionPerformed(ActionEvent evt) {
		gkd.instructionDownButtonActionPerformed(evt);
	}

	private void jButton3ActionPerformed(ActionEvent evt) {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			if (!GKDCommonLib.saveImage(instructionTable, file)) {
				JOptionPane.showMessageDialog(this, "Cannot save image.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void jButton12ActionPerformed(ActionEvent evt) {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			GKDCommonLib.exportTableModelToExcel(file, this.instructionTable.getModel(), "instruction 0x" + this.instructionComboBox.getSelectedItem().toString());
		}
	}

	private void instructionTableMouseClicked(MouseEvent evt) {
		gkd.instructionTableMouseClicked(evt);
	}

	public void loadELF(String elfPaths[]) {
		final JDialog dialog = new JDialog(gkd, true);
		JLabel jLabel = new JLabel();
		jLabel.setHorizontalAlignment(JTextField.CENTER);
		dialog.setContentPane(jLabel);
		dialog.setSize(500, 100);
		dialog.setLocationRelativeTo(gkd);
		new Thread(new Runnable() {
			@Override
			public void run() {
				dialog.setVisible(true);
			}
		}).start();

		//$hide>>$
		if (Global.debug) {
			System.out.println("load elf");
		}

		for (String elfPath : elfPaths) {
			File file;
			long memoryOffset = 0;

			if (elfPath.contains("=")) {
				memoryOffset = CommonLib.string2long(elfPath.split("=")[1]);
				file = new File(elfPath.split("=")[0]);
			} else {
				file = new File(elfPath);
			}
			loadELF(file, dialog, memoryOffset);
		}

		if (Global.debug) {
			System.out.println("load elf end");
		}
		//$hide<<$

		dialog.setVisible(false);
	}

	public void loadELF(File file, JDialog dialog, long memoryOffset) {
		this.elfFile = file;
		if (!elfFile.isFile()) {
			JOptionPane.showMessageDialog(this, elfFile.getAbsolutePath() + " is not a file !!!");
			return;
		} else if (!file.exists()) {
			JOptionPane.showMessageDialog(this, elfFile.getAbsolutePath() + " not exist !!!");
			return;
		}

		gkd.enableAllButtons(false, false);
		peterDwarfPanel.init(elfFile, memoryOffset, dialog);
		gkd.disasmHereMenuItem.setEnabled(true);
		gkd.clearInstructionTableMenuItem.setEnabled(true);
		gkd.sourceLevelDebuggerToggleButtonActionPerformed(null);
		com.peterswing.CommonLib.expandAll(projectTree, true);

		initProjectTree();
		initSymbolTable();

		errorLabel.setVisible(false);
		show("MAIN");
		gkd.enableAllButtons(true, false);
	}

	private void initProjectTree() {
		//$hide>>$
		if (Global.debug) {
			System.out.println("--initProjectTree");
		}

		root = new ProjectTreeNode(elfFile);
		((FilterTreeModel) projectTree.getModel()).setRoot(root);

		TreeSet<File> allSourceFiles = new TreeSet<File>();
		for (Dwarf dwarf : peterDwarfPanel.dwarfs) {
			for (DwarfDebugLineHeader header : dwarf.headers) {
				for (DwarfHeaderFilename filename : header.filenames) {
					allSourceFiles.add(filename.file);
				}
			}
		}
		for (File file : allSourceFiles) {
			ProjectTreeNode subnode = new ProjectTreeNode(file);
			root.children.add(subnode);
		}
		((FilterTreeModel) projectTree.getModel()).reload();

		if (Global.debug) {
			System.out.println("--initProjectTree end");
		}
		//$hide<<$
	}

	private void initSymbolTable() {
		//$hide>>$
		if (Global.debug) {
			System.out.println("--initSymbolTable");
		}

		TreeSet<Elf32_Sym> allSymbols = new TreeSet<Elf32_Sym>();
		for (Dwarf dwarf : peterDwarfPanel.dwarfs) {
			for (Elf32_Sym symbol : dwarf.symbols) {
				if (symbol.name.length() > 0) {
					allSymbols.add(symbol);
				}
			}
		}
		symbolTableModel.symbols = allSymbols;
		symbolTableModel.fireTableDataChanged();

		if (Global.debug) {
			System.out.println("--initSymbolTable end");
		}
		//$hide<<$
	}

	private void initMemoryMap() {
		if (Global.debug) {
			System.out.println("--initMemoryMap");
		}
		if (Global.debug) {
			System.out.println("--initMemoryMap end");
		}
	}

	@Override
	public void cancelled() {
		projectTree.setEnabled(true);
	}

	private JToolBar getJProjectToolBar() {
		if (projectToolBar == null) {
			projectToolBar = new JToolBar();
			projectToolBar.add(getJSearchProjectTextField());
			projectToolBar.add(getJExactMatchCheckBox());
		}
		return projectToolBar;
	}

	private JSearchTextField getJSearchProjectTextField() {
		if (searchProjectTextField == null) {
			searchProjectTextField = new JSearchTextField();
			searchProjectTextField.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent evt) {
					searchProjectTextFieldKeyReleased(evt);
				}
			});
		}
		return searchProjectTextField;
	}

	private JPanel getSymbolTablePanel() {
		if (symbolTablePanel == null) {
			symbolTablePanel = new JPanel();
			BorderLayout jFunctionListPanelLayout = new BorderLayout();
			symbolTablePanel.setLayout(jFunctionListPanelLayout);
			symbolTablePanel.add(getJScrollPane11(), BorderLayout.CENTER);
			symbolTablePanel.add(getJToolBar1(), BorderLayout.NORTH);
		}
		return symbolTablePanel;
	}

	private void searchProjectTextFieldKeyReleased(KeyEvent evt) {
		if (searchProjectTextField.getText() != null) {
			projectFilterTreeModel.filter = searchProjectTextField.getText();
			projectFilterTreeModel.reload();
			CommonLib.expandAll(projectTree, true);
		}
	}

	private JCheckBox getJExactMatchCheckBox() {
		if (exactMatchCheckBox == null) {
			exactMatchCheckBox = new JCheckBox();
			exactMatchCheckBox.setText("exact");
			exactMatchCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					exactMatchCheckBoxActionPerformed(evt);
				}
			});
		}
		return exactMatchCheckBox;
	}

	private void exactMatchCheckBoxActionPerformed(ActionEvent evt) {
		projectFilterTreeModel.exactMatch = exactMatchCheckBox.isSelected();
		searchProjectTextFieldKeyReleased(null);
	}

	private JScrollPane getJScrollPane11() {
		if (scrollPane11 == null) {
			scrollPane11 = new JScrollPane();
			scrollPane11.setViewportView(getSymbolTable());
		}
		return scrollPane11;
	}

	private JTable getSymbolTable() {
		if (symbolTable == null) {
			symbolTable = new JTable() {
				public String getToolTipText(MouseEvent event) {
					Point p = event.getPoint();
					int row = rowAtPoint(p);
					int col = columnAtPoint(p);
					if (row == -1 || col == -1) {
						return null;
					}
					Elf32_Sym symbol = (Elf32_Sym) getValueAt(row, col);
					String str = "<html><table>";
					str += "<tr><td>name</td><td>:</td><td>" + symbol.name + "</td></tr>";
					str += "<tr><td>st_name</td><td>:</td><td>" + symbol.st_name + "</td></tr>";
					str += "<tr><td>st_value</td><td>:</td><td>0x" + Long.toHexString(symbol.st_value) + "</td></tr>";
					str += "<tr><td>st_size</td><td>:</td><td>" + symbol.st_size + "</td></tr>";
					str += "<tr><td>st_info</td><td>:</td><td>" + symbol.st_info + "</td></tr>";
					str += "<tr><td>st_other</td><td>:</td><td>" + symbol.st_other + "</td></tr>";
					str += "<tr><td>st_shndx</td><td>:</td><td>" + symbol.st_shndx + "</td></tr>";
					str += "</table></html>";
					return str;
				}
			};
			symbolTable.setModel(symbolTableModel);
			symbolTable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					symbolTableMouseClicked(evt);
				}
			});
		}
		return symbolTable;
	}

	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.add(getSearchSymbolTextField());
			jToolBar1.add(getSymbolExactCheckBox());
		}
		return jToolBar1;
	}

	private JSearchTextField getSearchSymbolTextField() {
		if (searchSymbolTextField == null) {
			searchSymbolTextField = new JSearchTextField();
			searchSymbolTextField.setText("");
			searchSymbolTextField.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent evt) {
					searchSymbolTextFieldKeyReleased(evt);
				}
			});
		}
		return searchSymbolTextField;
	}

	private JCheckBox getSymbolExactCheckBox() {
		if (symbolExactCheckBox == null) {
			symbolExactCheckBox = new JCheckBox();
			symbolExactCheckBox.setText("exact");
			symbolExactCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					symbolExactCheckBoxActionPerformed(evt);
				}
			});
		}
		return symbolExactCheckBox;
	}

	private void searchSymbolTextFieldKeyReleased(KeyEvent evt) {
		if (searchSymbolTextField.getText() != null) {
			symbolTableModel.setSearchPattern(searchSymbolTextField.getText());
		}
	}

	private void symbolExactCheckBoxActionPerformed(ActionEvent evt) {
		symbolTableModel.exactMatch = symbolExactCheckBox.isSelected();
		searchSymbolTextFieldKeyReleased(null);
	}

	private void jTabbedPane1StateChanged(ChangeEvent evt) {
		JTabbedPane pane = (JTabbedPane) evt.getSource();

		// Get current tab
		int sel = pane.getSelectedIndex();

		if (sel == 1) {
			symbolTableModel.reload();
		}
	}

	private void symbolTableMouseClicked(MouseEvent evt) {
		if (evt.getClickCount() == 2) {
			Elf32_Sym symbol = (Elf32_Sym) symbolTable.getValueAt(symbolTable.getSelectedRow(), 0);
			if (symbol != null) {
				long address = symbol.st_value;

				instructionComboBox.setSelectedItem("0x" + Long.toHexString(address));
				jMainTabbedPane.setSelectedIndex(0);

				gkd.jumpToRowInstructionTable(BigInteger.valueOf(address));
			}
		}
	}

	private JLabel getJErrorLabel() {
		if (errorLabel == null) {
			errorLabel = new JLabel();
			errorLabel.setText("<html>Source level debug is disabled before you load the map file,<br>by clicking menu \"system\" -> \"load elf\"</html>");
			errorLabel.setBackground(new java.awt.Color(0, 0, 0, 200));
			errorLabel.setForeground(new java.awt.Color(255, 255, 255));
			errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
			errorLabel.setFont(new java.awt.Font("Dialog", 0, 26));
			errorLabel.setOpaque(true);
		}
		return errorLabel;
	}

	private JPanel getJDwarfPanel() {
		if (jDwarfPanel == null) {
			jDwarfPanel = new JPanel();
			BorderLayout jDwarfPanelLayout = new BorderLayout();
			jDwarfPanel.setLayout(jDwarfPanelLayout);
			jDwarfPanel.add(getPeterDwarfPanel(), BorderLayout.CENTER);
		}
		return jDwarfPanel;
	}

	private PeterDwarfPanel getPeterDwarfPanel() {
		if (peterDwarfPanel == null) {
			peterDwarfPanel = new PeterDwarfPanel();
		}
		return peterDwarfPanel;
	}

	private JTabbedPane getJInfoTabbedPane() {
		if (infoTabbedPane == null) {
			infoTabbedPane = new JTabbedPane();
			infoTabbedPane.addTab(MyLanguage.getString("Register"), null, getRegisterPanelScrollPane(), null);
		}
		return infoTabbedPane;
	}

	private JScrollPane getRegisterPanelScrollPane() {
		if (registerPanelScrollPane == null) {
			registerPanelScrollPane = new JScrollPane();
		}
		return registerPanelScrollPane;
	}

	private JSearchTextField getJSearchTextField() {
		if (searchTextField == null) {
			searchTextField = new JSearchTextField();
			searchTextField.setPreferredSize(new java.awt.Dimension(163, 25));
			searchTextField.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent evt) {
					searchTextFieldKeyReleased(evt);
				}
			});
		}
		return searchTextField;
	}

	private JPanel getCodeBasePanel() {
		if (codeBasePanel == null) {
			codeBasePanel = new JPanel();
			BorderLayout codeBasePanelLayout = new BorderLayout();
			codeBasePanel.setLayout(codeBasePanelLayout);
			codeBasePanel.add(getCodeBaseScrollPane(), BorderLayout.CENTER);
			codeBasePanel.add(getJToolBar2(), BorderLayout.NORTH);
		}
		return codeBasePanel;
	}

	private JScrollPane getCodeBaseScrollPane() {
		if (codeBaseScrollPane == null) {
			codeBaseScrollPane = new JScrollPane();
			codeBaseScrollPane.setViewportView(getCodeBaseTable());
		}
		return codeBaseScrollPane;
	}

	private JTable getCodeBaseTable() {
		if (codeBaseTable == null) {
			CodeBaseTableModel codeBaseTableModel = new CodeBaseTableModel(peterDwarfPanel);
			codeBaseTable = new JTable();
			codeBaseTable.setModel(codeBaseTableModel);
			sorter = new TableRowSorter<TableModel>(codeBaseTableModel);
			codeBaseTable.setRowSorter(sorter);
			codeBaseTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			codeBaseTable.getColumnModel().getColumn(0).setPreferredWidth(100);
			codeBaseTable.getColumnModel().getColumn(1).setPreferredWidth(100);
			codeBaseTable.getColumnModel().getColumn(2).setPreferredWidth(400);
			codeBaseTable.getColumnModel().getColumn(3).setPreferredWidth(1500);
			codeBaseTable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					codeBaseTableMouseClicked(evt);
				}
			});
		}
		return codeBaseTable;
	}

	private JToolBar getJToolBar2() {
		if (jToolBar2 == null) {
			jToolBar2 = new JToolBar();
			jToolBar2.add(getRefreshCodeBaseButton());
			jToolBar2.add(getSearchCodeBaseTextField());
		}
		return jToolBar2;
	}

	private JButton getRefreshCodeBaseButton() {
		if (refreshCodeBaseButton == null) {
			refreshCodeBaseButton = new JButton();
			refreshCodeBaseButton.setText("Refresh");
			refreshCodeBaseButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					refreshCodeBaseButtonActionPerformed(evt);
				}
			});
		}
		return refreshCodeBaseButton;
	}

	private void refreshCodeBaseButtonActionPerformed(ActionEvent evt) {
		CodeBaseTableModel codeBaseTableModel = (CodeBaseTableModel) codeBaseTable.getModel();
		codeBaseTableModel.refresh();
		codeBaseTableModel.fireTableDataChanged();
	}

	private JTextField getSearchCodeBaseTextField() {
		if (searchCodeBaseTextField == null) {
			searchCodeBaseTextField = new JTextField();
			searchCodeBaseTextField.setPreferredSize(new java.awt.Dimension(288, 26));
			searchCodeBaseTextField.setMaximumSize(new java.awt.Dimension(150, 22));
			searchCodeBaseTextField.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent evt) {
					searchCodeBaseTextFieldKeyReleased(evt);
				}
			});
		}
		return searchCodeBaseTextField;
	}

	private void searchCodeBaseTextFieldKeyReleased(KeyEvent evt) {
		sorter.setRowFilter(RowFilter.regexFilter(searchCodeBaseTextField.getText()));
	}

	private void codeBaseTableMouseClicked(MouseEvent evt) {
		if (evt.getClickCount() == 2) {
			File file = (File) codeBaseTable.getValueAt(codeBaseTable.getSelectedRow(), 3);
			try {
				InputStream in = new FileInputStream(file.getAbsolutePath());
				SourceDialog dialog = new SourceDialog(gkd, file.getAbsolutePath());
				dialog.enhancedTextArea1.setText(IOUtils.toString(in));
				IOUtils.closeQuietly(in);
				dialog.setLocationRelativeTo(gkd);
				dialog.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	private JPanel getCallGraphPanel() {
		if (callGraphPanel == null) {
			callGraphPanel = new JPanel();
			BorderLayout callGraphPanelLayout = new BorderLayout();
			callGraphPanel.setLayout(callGraphPanelLayout);
			callGraphPanel.add(getCallGraphToolBar(), BorderLayout.NORTH);
			callGraphPanel.add(getJPanel2(), BorderLayout.CENTER);
		}
		return callGraphPanel;
	}

	private JToolBar getCallGraphToolBar() {
		if (callGraphToolBar == null) {
			callGraphToolBar = new JToolBar();
			callGraphToolBar.add(getRefreshCallGrapphButton());
		}
		return callGraphToolBar;
	}

	private JButton getRefreshCallGrapphButton() {
		if (refreshCallGrapphButton == null) {
			refreshCallGrapphButton = new JButton();
			refreshCallGrapphButton.setText("Refresh");
			refreshCallGrapphButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					refreshCallGrapphButtonActionPerformed(evt);
				}
			});
		}
		return refreshCallGrapphButton;
	}

	private JPanel getJPanel2() {
		if (panel2 == null) {
			panel2 = new JPanel();
		}
		return panel2;
	}

	private void refreshCallGrapphButtonActionPerformed(ActionEvent evt) {
		graph = new mxGraph() {
			public void drawState(mxICanvas canvas, mxCellState state, String label) {
				if (getModel().isVertex(state.getCell()) && canvas instanceof PeterSwingCanvas) {
					PeterSwingCanvas c = (PeterSwingCanvas) canvas;
					c.drawVertex(state, label);
				} else {
					// draw edge, at least
					//					super.drawState(canvas, state, label);
					super.drawState(canvas, state, true);
				}
			}

			// Ports are not used as terminals for edges, they are
			// only used to compute the graphical connection point

			public boolean isPort(Object cell) {
				mxGeometry geo = getCellGeometry(cell);

				return (geo != null) ? geo.isRelative() : false;
			}

			// Implements a tooltip that shows the actual
			// source and target of an edge
			public String getToolTipForCell(Object cell) {
				if (model.isEdge(cell)) {
					return convertValueToString(model.getTerminal(cell, true)) + " -> " + convertValueToString(model.getTerminal(cell, false));
				}

				return super.getToolTipForCell(cell);
			}

			public boolean isCellFoldable(Object cell, boolean collapse) {
				return false;
			}
		};
		graphComponent = new CallGraphComponent(graph);
		Object parent = graph.getDefaultParent();

		//		addCells(parent);
		graph.setCellsDisconnectable(false);

		graphComponent.setGridVisible(true);
		graphComponent.setGridColor(Color.lightGray);
		graphComponent.setBackground(Color.white);
		graphComponent.getViewport().setOpaque(false);
		graphComponent.setBackground(Color.WHITE);
		graphComponent.setConnectable(false);
		graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				Object cell = graphComponent.getCellAt(e.getX(), e.getY());

				if (cell != null) {
					String label = graph.getLabel(cell);
					if (label.contains("->")) {
						//						cellClientEvent(label);
					}
				}
			}
		});

		graph.setCellsResizable(false);
		graph.setCellsMovable(false);
		graph.setCellsEditable(false);
		graph.foldCells(false);
		graph.setGridSize(10);
		callGraphPanel.removeAll();
		callGraphPanel.add(graphComponent, BorderLayout.CENTER);

		//		mxGraphOutline graphOutline = new mxGraphOutline(graphComponent);
		//		graphOutline.setBackground(Color.white);
		//		graphOutline.setBorder(new LineBorder(Color.LIGHT_GRAY));
		//		callGraphPanel.removeAll();
		//		callGraphPanel.add(graphOutline, BorderLayout.CENTER);
		//		callGraphPanel.setPreferredSize(new Dimension(100, 100));
	}

	private void btnSearchActionPerformed(ActionEvent evt) {
		jumpToInstruction(searchTextField.getText(), -1);
	}

	private void searchTextFieldKeyReleased(KeyEvent evt) {
		jumpToInstruction(searchTextField.getText(), -1);
	}

	private void jumpToInstruction(String s, int startRow) {
		s = s.toLowerCase();
		if (startRow == -1) {
			if (instructionTable.getSelectedRow() == 0) {
				startRow = instructionTable.getSelectedRow();
			} else {
				startRow = instructionTable.getSelectedRow() + 1;
			}
		}
		for (int x = startRow; x < instructionTable.getRowCount(); x++) {
			if (instructionTable.getValueAt(x, 1).toString().toLowerCase().contains(s) || instructionTable.getValueAt(x, 2).toString().toLowerCase().contains(s)) {
				instructionTable.setRowSelectionInterval(x, x);
				instructionTable.scrollRectToVisible(instructionTable.getCellRect(x + 10, 1, true));
				instructionTable.scrollRectToVisible(instructionTable.getCellRect(x + 10, 1, true));
				instructionTable.scrollRectToVisible(instructionTable.getCellRect(x, 1, true));
				instructionTable.scrollRectToVisible(instructionTable.getCellRect(x, 1, true));
				break;
			}
		}
	}
}
