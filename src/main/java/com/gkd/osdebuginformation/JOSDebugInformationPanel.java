package com.gkd.osdebuginformation;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.peterswing.advancedswing.jtable.SortableTableModel;

public class JOSDebugInformationPanel extends JPanel {
	private JToolBar toolBar1;
	private JScrollPane scrollPane1;
	public JEditorPane xmlEditorPane;
	private JScrollPane scrollPane2;
	private JTable kernelInterruptTable;
	private JTable kernelModuleTable;
	private JScrollPane scrollPane5;
	private JScrollPane scrollPane4;
	private JTabbedPane tabbedPane1;
	private JTable kernelTable;
	private JScrollPane scrollPane3;
	private JPanel kernelPanel;
	private JTable table1;
	private JPanel oSInfoPanel;
	private JPanel xmlPanel;
	private JPanel mainPanel;
	private JTree functionTree;
	private JScrollPane leftScrollPane;
	private JButton jExcelButton;
	private JSplitPane mainSplitPane;
	OSInfoTableModel osInfoTableModel = new OSInfoTableModel();
	OSInfoTableModel kernelInfoTableModel = new OSInfoTableModel();
	OSInfoKernelModuleTableModel kernelModuleInfoTableModel = new OSInfoKernelModuleTableModel();
	private JTable libraryTable;
	private JScrollPane scrollPane8;
	private JPanel libraryPanel;
	private JTable memoryTable;
	private JScrollPane scrollPane7;
	private JPanel memoryPanel;
	private JTable processTable;
	private JScrollPane scrollPane9;
	private JPanel processPanel;
	OSInfoKernelMemoryAllocatorTableModel kernelMemoryAllocatorTableModel = new OSInfoKernelMemoryAllocatorTableModel();

	private JTable oSInfoKernelMemoryAllocatorTable;

	private JScrollPane scrollPane6;
	OSInfoKernelInterruptTableModel kernelInterruptInfoTableModel = new OSInfoKernelInterruptTableModel();

	OSInfoLibraryTableModel osInfoLibraryTableModel = new OSInfoLibraryTableModel();
	OSInfoProcessTableModel osInfoProcessTableModel = new OSInfoProcessTableModel();

	public JOSDebugInformationPanel() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			{
				toolBar1 = new JToolBar();
				this.add(toolBar1, BorderLayout.NORTH);
				{
					jExcelButton = new JButton();
					toolBar1.add(jExcelButton);
					jExcelButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/excel.gif")));
				}
			}
			{
				mainSplitPane = new JSplitPane();
				this.add(mainSplitPane, BorderLayout.CENTER);
				{
					leftScrollPane = new JScrollPane();
					mainSplitPane.add(leftScrollPane, JSplitPane.LEFT);
					{
						FunctionTreeNode root = new FunctionTreeNode("os", "os");
						root.add(new FunctionTreeNode("kernel", "kernel"));
						root.add(new FunctionTreeNode("memory", "memory"));
						root.add(new FunctionTreeNode("library", "library"));
						root.add(new FunctionTreeNode("process", "process"));
						root.add(new FunctionTreeNode("device", "device"));
						root.add(new FunctionTreeNode("fs", "fs"));
						root.add(new FunctionTreeNode("network", "network"));
						root.add(new FunctionTreeNode("table", "table"));
						root.add(new FunctionTreeNode("xml", "xml"));

						functionTree = new JTree(root);
						functionTree.setCellRenderer(new FunctionTreeRenderer());
						functionTree.setShowsRootHandles(true);
						leftScrollPane.setViewportView(functionTree);
						functionTree.addTreeSelectionListener(new TreeSelectionListener() {
							public void valueChanged(TreeSelectionEvent evt) {
								functionTreeValueChanged(evt);
							}
						});
					}
				}
				{
					mainPanel = new JPanel();
					CardLayout mainPanelLayout = new CardLayout();
					mainPanel.setLayout(mainPanelLayout);
					mainSplitPane.add(mainPanel, JSplitPane.RIGHT);
					{
						xmlPanel = new JPanel();
						BorderLayout xmlPanelLayout = new BorderLayout();
						xmlPanel.setLayout(xmlPanelLayout);
						mainPanel.add(xmlPanel, "xmlPanel");
						{
							scrollPane1 = new JScrollPane();
							xmlPanel.add(scrollPane1, BorderLayout.CENTER);
							{
								xmlEditorPane = new JEditorPane();
								scrollPane1.setViewportView(xmlEditorPane);
								xmlEditorPane.setText("");
							}
						}
					}
					{
						oSInfoPanel = new JPanel();
						BorderLayout oSInfoPanelLayout = new BorderLayout();
						oSInfoPanel.setLayout(oSInfoPanelLayout);
						mainPanel.add(oSInfoPanel, "oSInfoPanel");
						{
							scrollPane2 = new JScrollPane();
							oSInfoPanel.add(scrollPane2, BorderLayout.CENTER);
							{
								table1 = new JTable();
								scrollPane2.setViewportView(table1);
								table1.setModel(new SortableTableModel(osInfoTableModel));
							}
						}
					}
					{
						kernelPanel = new JPanel();
						BorderLayout kernelPanelLayout = new BorderLayout();
						kernelPanel.setLayout(kernelPanelLayout);
						mainPanel.add(kernelPanel, "kernelPanel");
						{
							tabbedPane1 = new JTabbedPane();
							kernelPanel.add(tabbedPane1, BorderLayout.CENTER);
							tabbedPane1.setPreferredSize(new java.awt.Dimension(453, 422));
							tabbedPane1.setTabPlacement(JTabbedPane.LEFT);
							{
								scrollPane3 = new JScrollPane();
								tabbedPane1.addTab("basic info", null, scrollPane3, null);
								{
									kernelTable = new JTable();
									scrollPane3.setViewportView(kernelTable);
									kernelTable.setModel(kernelInfoTableModel);
								}
							}
							{
								scrollPane4 = new JScrollPane();
								tabbedPane1.addTab("module", null, scrollPane4, null);
								{
									kernelModuleTable = new JTable();
									scrollPane4.setViewportView(kernelModuleTable);
									kernelModuleTable.setModel(kernelModuleInfoTableModel);
								}
							}
							{
								scrollPane5 = new JScrollPane();
								tabbedPane1.addTab("interrupt", null, scrollPane5, null);
								{
									kernelInterruptTable = new JTable();
									scrollPane5.setViewportView(kernelInterruptTable);
									kernelInterruptTable.setModel(kernelInterruptInfoTableModel);
								}
							}
							{
								scrollPane6 = new JScrollPane();
								tabbedPane1.addTab("memory allocator", null, scrollPane6, null);
								{
									oSInfoKernelMemoryAllocatorTable = new JTable();
									scrollPane6.setViewportView(oSInfoKernelMemoryAllocatorTable);
									oSInfoKernelMemoryAllocatorTable.setModel(kernelMemoryAllocatorTableModel);
									oSInfoKernelMemoryAllocatorTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
									oSInfoKernelMemoryAllocatorTable.getColumnModel().getColumn(0).setPreferredWidth(150);
									oSInfoKernelMemoryAllocatorTable.getColumnModel().getColumn(1).setPreferredWidth(450);
								}
							}
						}
					}
					{
						memoryPanel = new JPanel();
						BorderLayout memoryPanelLayout = new BorderLayout();
						memoryPanel.setLayout(memoryPanelLayout);
						mainPanel.add(memoryPanel, "panel1");
						{
							scrollPane7 = new JScrollPane();
							memoryPanel.add(scrollPane7, BorderLayout.CENTER);
							{
								TableModel memoryTableModel = new DefaultTableModel(new String[][] { { "One", "Two" }, { "Three", "Four" } }, new String[] { "Column 1",
										"Column 2" });
								memoryTable = new JTable();
								scrollPane7.setViewportView(memoryTable);
								memoryTable.setModel(memoryTableModel);
							}
						}
					}
					{
						libraryPanel = new JPanel();
						BorderLayout libraryPanelLayout = new BorderLayout();
						libraryPanel.setLayout(libraryPanelLayout);
						mainPanel.add(libraryPanel, "libraryPanel");
						{
							scrollPane8 = new JScrollPane();
							libraryPanel.add(scrollPane8, BorderLayout.CENTER);
							{
								libraryTable = new JTable();
								scrollPane8.setViewportView(libraryTable);
								libraryTable.setModel(osInfoLibraryTableModel);
							}
						}
					}
					{
						processPanel = new JPanel();
						BorderLayout processPanelLayout = new BorderLayout();
						processPanel.setLayout(processPanelLayout);
						mainPanel.add(processPanel, "processPanel");
						{
							scrollPane9 = new JScrollPane();
							processPanel.add(scrollPane9, BorderLayout.CENTER);
							{
								processTable = new JTable();
								scrollPane9.setViewportView(processTable);
								processTable.setModel(osInfoProcessTableModel);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public OSInfoProcessTableModel getOsInfoProcessTableModel() {
		return osInfoProcessTableModel;
	}

	public void setOsInfoProcessTableModel(OSInfoProcessTableModel osInfoProcessTableModel) {
		this.osInfoProcessTableModel = osInfoProcessTableModel;
	}

	public OSInfoLibraryTableModel getOsInfoLibraryTableModel() {
		return osInfoLibraryTableModel;
	}

	public void setOsInfoLibraryTableModel(OSInfoLibraryTableModel osInfoLibraryTableModel) {
		this.osInfoLibraryTableModel = osInfoLibraryTableModel;
	}

	public OSInfoKernelMemoryAllocatorTableModel getKernelMemoryAllocatorTableModel() {
		return kernelMemoryAllocatorTableModel;
	}

	public void setKernelMemoryAllocatorTableModel(OSInfoKernelMemoryAllocatorTableModel kernelMemoryAllocatorTableModel) {
		this.kernelMemoryAllocatorTableModel = kernelMemoryAllocatorTableModel;
	}

	public OSInfoKernelModuleTableModel getKernelModuleInfoTableModel() {
		return kernelModuleInfoTableModel;
	}

	public void setKernelModuleInfoTableModel(OSInfoKernelModuleTableModel kernelModuleInfoTableModel) {
		this.kernelModuleInfoTableModel = kernelModuleInfoTableModel;
	}

	public OSInfoKernelInterruptTableModel getKernelInterruptInfoTableModel() {
		return kernelInterruptInfoTableModel;
	}

	public void setKernelInterruptInfoTableModel(OSInfoKernelInterruptTableModel kernelInterruptInfoTableModel) {
		this.kernelInterruptInfoTableModel = kernelInterruptInfoTableModel;
	}

	public OSInfoTableModel getKernelInfoTableModel() {
		return kernelInfoTableModel;
	}

	public void setKernelInfoTableModel(OSInfoTableModel kernelInfoTableModel) {
		this.kernelInfoTableModel = kernelInfoTableModel;
	}

	public OSInfoTableModel getOsInfoTableModel() {
		return osInfoTableModel;
	}

	public void setOsInfoTableModel(OSInfoTableModel osInfoTableModel) {
		this.osInfoTableModel = osInfoTableModel;
	}

	public JSplitPane getMainSplitPane() {
		return mainSplitPane;
	}

	public void setmainSplitPane(JSplitPane mainSplitPane) {
		this.mainSplitPane = mainSplitPane;
	}

	private void functionTreeValueChanged(TreeSelectionEvent evt) {
		FunctionTreeNode node = (FunctionTreeNode) functionTree.getLastSelectedPathComponent();
		CardLayout cl = (CardLayout) (mainPanel.getLayout());
		if (node.toString().equals("os")) {
			cl.show(mainPanel, "oSInfoPanel");
		} else if (node.toString().equals("xml")) {
			cl.show(mainPanel, "xmlPanel");
		} else if (node.toString().equals("kernel")) {
			cl.show(mainPanel, "kernelPanel");
		} else if (node.toString().equals("library")) {
			cl.show(mainPanel, "libraryPanel");
		} else if (node.toString().equals("process")) {
			cl.show(mainPanel, "processPanel");
		}
	}

}
