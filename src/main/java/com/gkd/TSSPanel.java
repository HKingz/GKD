package com.gkd;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.math.BigInteger;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;

import com.gkd.architecture.IA32PageDirectory;
import com.gkd.stub.VMController;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.peterswing.CommonLib;

import info.clearthought.layout.TableLayout;

public class TSSPanel extends JPanel {

	private JTable table1;
	private int gdtNo;
	private JPanel infoPanel;
	private JTabbedPane tabbedPane1;
	private JTable pageTableTable;
	private JTable pageDirectoryTable;
	private JLabel label1;
	private JTable tssTable;
	private JCheckBox hideIfAddressIsZeroCheckBox;
	private ButtonGroup buttonGroup1;
	private JButton jButton19;
	private JButton refreshAddressTranslateTableButton;
	private JButton jButton18;
	private JButton jButton17;
	private JToolBar jToolBar3;
	private JTable addressTranslateTable2;
	private JScrollPane jScrollPane13;
	private JPanel jPanel22;
	private JTextField addressTextField;
	private JButton refreshAddressTranslateButton;
	private JPanel jPanel21;
	private JRadioButton searchLinearAddressRadioButton;
	private JRadioButton searchVirtualAddressRadioButton;
	private JPanel jPanel20;
	private JPanel addressTranslatePanel;
	private JScrollPane jScrollPane5;
	private JScrollPane jScrollPane4;
	private JScrollPane jScrollPane3;
	private JScrollPane jScrollPane2;
	private JTextField linearAddressTextField;
	private JPanel jPanel3;
	private JSplitPane jSplitPane2;
	private JPanel pageTablePanel;
	private JSplitPane jSplitPane1;
	private JLabel jTypeLabel;
	private JTable jTable2;
	private JScrollPane jScrollPane1;
	private int bytes[];
	private long value;
	private long bit[] = new long[64];
	private GKD gkd;
	private int type;
	private BigInteger gdtAddress;
	private BigInteger ldtr;
	private BigInteger cr3;
	public static Logger logger = Logger.getLogger(TSSPanel.class);

	public TSSPanel(GKD gkd, int type, BigInteger gdtAddress, int gdtNo, int bytes[]) {
		this.gkd = gkd;
		this.type = type;
		this.gdtAddress = gdtAddress;
		this.gdtNo = gdtNo;
		this.bytes = bytes;

		try {
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);

			tabbedPane1 = new JTabbedPane();
			this.add(tabbedPane1, BorderLayout.CENTER);
			tabbedPane1.setTabPlacement(JTabbedPane.LEFT);

			jSplitPane1 = new JSplitPane();
			tabbedPane1.addTab("TSS", null, jSplitPane1, null);
			jSplitPane1.setDividerLocation(450);

			jScrollPane2 = new JScrollPane();
			jSplitPane1.add(jScrollPane2, JSplitPane.LEFT);
			jScrollPane2.setPreferredSize(new java.awt.Dimension(449, 600));

			TableModel jTable2Model = new DefaultTableModel(new String[][]{}, new String[]{MyLanguage.getString("Field"), MyLanguage.getString("Value")});
			jTable2 = new JTable();
			jScrollPane2.setViewportView(jTable2);
			jTable2.setModel(jTable2Model);

			jScrollPane3 = new JScrollPane();
			jSplitPane1.add(jScrollPane3, JSplitPane.RIGHT);
			jScrollPane3.setPreferredSize(new java.awt.Dimension(457, 600));

			TableModel tssTableModel = new DefaultTableModel(new String[][]{},
					new String[]{MyLanguage.getString("Offset"), MyLanguage.getString("Field"), MyLanguage.getString("Value"), ""});
			tssTable = new JTable();
			jScrollPane3.setViewportView(tssTable);
			tssTable.setModel(tssTableModel);
			tssTable.getColumn("").setCellRenderer(new ButtonRenderer());
			tssTable.getColumn("").setCellEditor(new ButtonEditor(new JCheckBox()));

			infoPanel = new JPanel();
			tabbedPane1.addTab("Info", null, infoPanel, null);
			BorderLayout jPanel1Layout = new BorderLayout();
			infoPanel.setLayout(jPanel1Layout);

			jScrollPane1 = new JScrollPane();
			infoPanel.add(jScrollPane1, BorderLayout.CENTER);
			jScrollPane1.setBounds(12, 38, 667, 60);
			jScrollPane1.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, false));

			DefaultTableModel jTable1Model = new DefaultTableModel(new String[][]{{""}, {""}}, new String[]{"31"});
			for (int x = 30; x >= 0; x--) {
				jTable1Model.addColumn(x);
			}

			table1 = new JTable();
			jScrollPane1.setViewportView(table1);
			table1.setModel(jTable1Model);
			table1.setBounds(12, 12, 562, 50);

			jTypeLabel = new JLabel();
			infoPanel.add(jTypeLabel, BorderLayout.NORTH);
			jTypeLabel.setText("Type : ");
			jTypeLabel.setBounds(12, 12, 576, 14);

			pageTablePanel = new JPanel();
			tabbedPane1.addTab("Page table", null, pageTablePanel, null);
			BorderLayout jPanel2Layout = new BorderLayout();
			pageTablePanel.setLayout(jPanel2Layout);

			jPanel3 = new JPanel();
			FormLayout jPanel3Layout = new FormLayout("max(p;5dlu), max(p;5dlu), 80dlu, max(p;5dlu)", "max(p;5dlu), max(p;5dlu), max(p;5dlu), max(p;5dlu)");
			jPanel3.setLayout(jPanel3Layout);
			pageTablePanel.add(jPanel3, BorderLayout.NORTH);
			jPanel3.setPreferredSize(new java.awt.Dimension(915, 27));

			label1 = new JLabel();
			jPanel3.add(label1, new CellConstraints("2, 1, 1, 1, default, default"));
			label1.setText(MyLanguage.getString("Linear_address"));
			label1.setBounds(691, 12, 143, 14);

			linearAddressTextField = new JTextField();
			jPanel3.add(linearAddressTextField, new CellConstraints("3, 1, 1, 1, default, default"));
			jPanel3.add(getHideIfAddressIsZeroCheckBox(), new CellConstraints("4, 1, 1, 1, default, default"));

			jSplitPane2 = new JSplitPane();
			pageTablePanel.add(jSplitPane2, BorderLayout.CENTER);
			jSplitPane2.setDividerLocation(400);

			jScrollPane4 = new JScrollPane();
			jSplitPane2.add(jScrollPane4, JSplitPane.LEFT);
			jScrollPane4.setPreferredSize(new java.awt.Dimension(399, 573));

			PageDirectoryTableModel jPageDirectoryTableModel = new PageDirectoryTableModel();
			pageDirectoryTable = new JTable();
			pageDirectoryTable.getTableHeader().setReorderingAllowed(false);
			jScrollPane4.setViewportView(pageDirectoryTable);
			pageDirectoryTable.setModel(jPageDirectoryTableModel);
			pageDirectoryTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			pageDirectoryTable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					pageDirectoryTableMouseClicked(evt);
				}
			});

			jScrollPane5 = new JScrollPane();
			jSplitPane2.add(jScrollPane5, JSplitPane.RIGHT);
			jScrollPane5.setPreferredSize(new java.awt.Dimension(507, 573));

			PageTableTableModel jPageTableTableModel = new PageTableTableModel();
			pageTableTable = new JTable();
			pageTableTable.getTableHeader().setReorderingAllowed(false);
			jScrollPane5.setViewportView(pageTableTable);
			pageTableTable.setModel(jPageTableTableModel);
			pageTableTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			addressTranslatePanel = new JPanel();
			tabbedPane1.addTab("Address  translate", null, addressTranslatePanel, null);
			BorderLayout jAddressTranslatePanelLayout = new BorderLayout();
			addressTranslatePanel.setLayout(jAddressTranslatePanelLayout);

			jPanel20 = new JPanel();
			TableLayout jPanel20Layout = new TableLayout(new double[][]{{8.0, 156.0, 13.0}, {25.0, 25.0, 25.0, 22.0, 37.0, TableLayout.FILL}});
			jPanel20Layout.setHGap(5);
			jPanel20Layout.setVGap(5);
			addressTranslatePanel.add(jPanel20, BorderLayout.WEST);
			jPanel20.setPreferredSize(new java.awt.Dimension(189, 629));
			jPanel20.setLayout(jPanel20Layout);

			searchVirtualAddressRadioButton = new JRadioButton();
			searchVirtualAddressRadioButton.setText(MyLanguage.getString("Virtual_address"));
			jPanel20.add(searchVirtualAddressRadioButton, "1, 0, 2, 0");
			searchVirtualAddressRadioButton.setSelected(true);
			getButtonGroup1().add(searchVirtualAddressRadioButton);

			searchLinearAddressRadioButton = new JRadioButton();
			searchLinearAddressRadioButton.setText(MyLanguage.getString("Linear_address"));
			jPanel20.add(searchLinearAddressRadioButton, "1, 1, 2, 1");
			getButtonGroup1().add(searchLinearAddressRadioButton);

			jPanel21 = new JPanel();
			jPanel20.add(jPanel21, "1, 4");

			refreshAddressTranslateButton = new JButton();
			refreshAddressTranslateButton.setText(MyLanguage.getString("Convert"));
			jPanel21.add(refreshAddressTranslateButton);
			refreshAddressTranslateButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					refreshAddressTranslateButtonActionPerformed(evt);
				}
			});

			addressTextField = new JTextField();
			jPanel20.add(addressTextField, "1, 3");
			addressTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					addressTextFieldKeyTyped(evt);
				}
			});

			jPanel22 = new JPanel();
			BorderLayout jPanel22Layout = new BorderLayout();
			addressTranslatePanel.add(jPanel22, BorderLayout.CENTER);
			jPanel22.setLayout(jPanel22Layout);

			jScrollPane13 = new JScrollPane();
			jPanel22.add(jScrollPane13, BorderLayout.CENTER);
			jScrollPane13.setPreferredSize(new java.awt.Dimension(150, 32));

			AddressTranslateTableModel addressTranslateTableModel = new AddressTranslateTableModel();
			addressTranslateTable2 = new JTable();
			jScrollPane13.setViewportView(addressTranslateTable2);
			addressTranslateTable2.setModel(addressTranslateTableModel);
			addressTranslateTable2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			jToolBar3 = new JToolBar();
			addressTranslatePanel.add(jToolBar3, BorderLayout.NORTH);

			jButton17 = new JButton();
			jToolBar3.add(jButton17);
			jButton17.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/disk.png")));
			jButton17.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					button17ActionPerformed(evt);
				}
			});

			jButton18 = new JButton();
			jToolBar3.add(jButton18);
			jButton18.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/excel.gif")));
			jButton18.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					button18ActionPerformed(evt);
				}
			});

			refreshAddressTranslateTableButton = new JButton();
			jToolBar3.add(refreshAddressTranslateTableButton);
			refreshAddressTranslateTableButton.setText("Refresh");
			refreshAddressTranslateTableButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/arrow_refresh.png")));
			refreshAddressTranslateTableButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					refreshAddressTranslateTableButtonActionPerformed(evt);
				}
			});

			jButton19 = new JButton();
			jToolBar3.add(jButton19);
			jButton19.setText("Delete");
			jButton19.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/cross.png")));
			jButton19.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jButton19ActionPerformed(evt);
				}
			});

			if (bytes != null) {
				value = CommonLib.getLong(bytes, 0);

				for (int x = 0; x < 64; x++) {
					bit[x] = CommonLib.getBit(value, x);
				}

				for (int x = 0; x < 32; x++) {
					table1.setValueAt(value >> x & 1, 1, 31 - x);
				}

				for (int x = 32; x < 64; x++) {
					table1.setValueAt(value >> x & 1, 0, 63 - x);
					table1.setPreferredSize(new java.awt.Dimension(669, 32));
				}

				jTypeLabel.setText("Type : TSS descriptor, value=0x" + Long.toHexString(value));
				parseTSSDescriptor();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void parseTSSDescriptor() {
		try {
			DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
			long base = CommonLib.getLong(bytes[2], bytes[3], bytes[4], bytes[7], 0, 0, 0, 0);
			model.addRow(new String[]{"base", "0x" + Long.toHexString(base)});

			long limit = CommonLib.getLong(bytes[0], bytes[1], bytes[6] & 0xf, 0, 0, 0, 0, 0);
			if (bit[55] == 1) {
				limit *= 4096;
			}
			limit += 1; //Real TSS limit = TSS limit in descriptor + 1
			model.addRow(new String[]{"limit", "0x" + Long.toHexString(limit)});

			model.addRow(new String[]{"G", String.valueOf(bit[55])});
			model.addRow(new String[]{"AVL", String.valueOf(bit[52])});
			model.addRow(new String[]{"P", String.valueOf(bit[47])});
			model.addRow(new String[]{"DPL", String.valueOf(bit[45] + bit[46] << 1)});
			model.addRow(new String[]{"S", String.valueOf(bit[44])});
			model.addRow(new String[]{"D", String.valueOf(bit[43])});
			model.addRow(new String[]{"G", String.valueOf(bit[42])});
			model.addRow(new String[]{"B", String.valueOf(bit[41])});
			model.addRow(new String[]{"V", String.valueOf(bit[40])});

			// TSS
			//			float totalByte2 = limit - 1;
			//			totalByte2 = totalByte2 / 8;
			//			int totalByte3 = (int) Math.floor(totalByte2);
			//			String realEndAddressStr;
			//			String realStartAddressStr;
			//			long realStartAddress = base;
			//			realStartAddressStr = String.format("%08x", realStartAddress);
			//			long realEndAddress = realStartAddress + totalByte3 * 8;
			//			realEndAddressStr = String.format("%08x", realEndAddress);
			int tssByte[] = VMController.getVM().virtualMemory(BigInteger.valueOf(base), (int) limit);

			if (tssByte == null) {
				return;
			}

			//long tssValue = CommonLib.getLong(b, 0);
			DefaultTableModel tssModel = (DefaultTableModel) tssTable.getModel();
			tssModel.addRow(new String[]{"0", "link", "0x" + CommonLib.getBigInteger(tssByte[0], tssByte[1], 0, 0, 0, 0, 0, 0).toString(16)});
			tssModel.addRow(new String[]{"4", "esp0", "0x" + CommonLib.getBigInteger(tssByte[4], tssByte[5], tssByte[6], tssByte[7], 0, 0, 0, 0).toString(16)});
			tssModel.addRow(new String[]{"8", "ss0", "0x" + CommonLib.getBigInteger(tssByte[8], tssByte[9], 0, 0, 0, 0, 0, 0).toString(16)});
			tssModel.addRow(new String[]{"0xc", "esp1", "0x" + CommonLib.getBigInteger(tssByte[0xc], tssByte[0xd], tssByte[0xe], tssByte[0xf], 0, 0, 0, 0).toString(16)});
			tssModel.addRow(new String[]{"0x10", "ss1", "0x" + CommonLib.getBigInteger(tssByte[0x10], tssByte[0x11], 0, 0, 0, 0, 0, 0).toString(16)});
			tssModel.addRow(new String[]{"0x14", "esp2", "0x" + CommonLib.getBigInteger(tssByte[0x14], tssByte[0x15], tssByte[0x16], tssByte[0x17], 0, 0, 0, 0).toString(16)});
			tssModel.addRow(new String[]{"0x18", "ss2", "0x" + CommonLib.getBigInteger(tssByte[0x18], tssByte[0x19], 0, 0, 0, 0, 0, 0).toString(16)});
			cr3 = CommonLib.getBigInteger(tssByte[0x1c], tssByte[0x1d], tssByte[0x1e], tssByte[0x1f], 0, 0, 0, 0);
			tssModel.addRow(new String[]{"0x1c", "cr3", "0x" + cr3.toString(16), "dump"});
			tssModel.addRow(new String[]{"0x20", "eip", "0x" + CommonLib.getBigInteger(tssByte[0x20], tssByte[0x21], tssByte[0x22], tssByte[0x23], 0, 0, 0, 0).toString(16)});
			tssModel.addRow(new String[]{"0x24", "eflags", "0x" + CommonLib.getBigInteger(tssByte[0x24], tssByte[0x25], tssByte[0x26], tssByte[0x27], 0, 0, 0, 0).toString(16)});
			tssModel.addRow(new String[]{"0x28", "eax", "0x" + CommonLib.getBigInteger(tssByte[0x28], tssByte[0x29], tssByte[0x2a], tssByte[0x2b], 0, 0, 0, 0).toString(16)});
			tssModel.addRow(new String[]{"0x2c", "ecx", "0x" + CommonLib.getBigInteger(tssByte[0x2c], tssByte[0x2d], tssByte[0x2e], tssByte[0x2f], 0, 0, 0, 0).toString(16)});
			tssModel.addRow(new String[]{"0x30", "edx", "0x" + CommonLib.getBigInteger(tssByte[0x30], tssByte[0x31], tssByte[0x32], tssByte[0x33], 0, 0, 0, 0).toString(16)});
			tssModel.addRow(new String[]{"0x34", "ebx", "0x" + CommonLib.getBigInteger(tssByte[0x34], tssByte[0x35], tssByte[0x36], tssByte[0x37], 0, 0, 0, 0).toString(16)});
			tssModel.addRow(new String[]{"0x38", "esp", "0x" + CommonLib.getBigInteger(tssByte[0x38], tssByte[0x39], tssByte[0x3a], tssByte[0x3b], 0, 0, 0, 0).toString(16)});
			tssModel.addRow(new String[]{"0x3c", "ebp", "0x" + CommonLib.getBigInteger(tssByte[0x3c], tssByte[0x3d], tssByte[0x3e], tssByte[0x3f], 0, 0, 0, 0).toString(16)});
			tssModel.addRow(new String[]{"0x40", "esi", "0x" + CommonLib.getBigInteger(tssByte[0x40], tssByte[0x41], tssByte[0x42], tssByte[0x43], 0, 0, 0, 0).toString(16)});
			tssModel.addRow(new String[]{"0x44", "edi", "0x" + CommonLib.getBigInteger(tssByte[0x44], tssByte[0x45], tssByte[0x45], tssByte[0x47], 0, 0, 0, 0).toString(16)});

			tssModel.addRow(new String[]{"0x48", "es", "0x" + CommonLib.getBigInteger(tssByte[0x48], tssByte[0x49], 0, 0, 0, 0, 0, 0).toString(16)});
			tssModel.addRow(new String[]{"0x4c", "cs", "0x" + CommonLib.getBigInteger(tssByte[0x4c], tssByte[0x4d], 0, 0, 0, 0, 0, 0).toString(16)});
			tssModel.addRow(new String[]{"0x50", "ss", "0x" + CommonLib.getBigInteger(tssByte[0x50], tssByte[0x51], 0, 0, 0, 0, 0, 0).toString(16)});
			tssModel.addRow(new String[]{"0x54", "ds", "0x" + CommonLib.getBigInteger(tssByte[0x54], tssByte[0x55], 0, 0, 0, 0, 0, 0).toString(16)});
			tssModel.addRow(new String[]{"0x58", "fs", "0x" + CommonLib.getBigInteger(tssByte[0x58], tssByte[0x59], 0, 0, 0, 0, 0, 0).toString(16)});
			tssModel.addRow(new String[]{"0x5c", "gs", "0x" + CommonLib.getBigInteger(tssByte[0x5c], tssByte[0x5d], 0, 0, 0, 0, 0, 0).toString(16)});
			tssModel.addRow(new String[]{"0x60", "ldtr", "0x" + CommonLib.getBigInteger(tssByte[0x60], tssByte[0x61], 0, 0, 0, 0, 0, 0).toString(16)});
			ldtr = CommonLib.getBigInteger(tssByte[0x60], tssByte[0x61], 0, 0, 0, 0, 0, 0);
			tssModel.addRow(new String[]{"0x64", "T", "0x" + Long.toHexString(CommonLib.getBit(tssByte[0x64], 0))});
			tssModel.addRow(new String[]{"0x66", "iobp offset", "0x" + CommonLib.getBigInteger(tssByte[0x66], tssByte[0x67], 0, 0, 0, 0, 0, 0).toString(16)});

			updatePageTable(cr3);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void updatePageTable(BigInteger pageDirectoryBaseAddress) {
		this.linearAddressTextField.setText("0x" + pageDirectoryBaseAddress.toString(16));
		Vector<IA32PageDirectory> ia32_pageDirectories = new Vector<IA32PageDirectory>();
		try {
			//			float totalByte2 = 4096 - 1;
			//			totalByte2 = totalByte2 / 8;
			//			int totalByte3 = (int) Math.floor(totalByte2);
			//			String realEndAddressStr;
			//			String realStartAddressStr;
			//			BigInteger realStartAddress = pageDirectoryBaseAddress;
			//			realStartAddressStr = String.format("%08x", realStartAddress);
			//			BigInteger realEndAddress = realStartAddress.add(BigInteger.valueOf(totalByte3 * 8));
			//			realEndAddressStr = String.format("%08x", realEndAddress);

			DefaultTableModel model = (DefaultTableModel) pageDirectoryTable.getModel();
			while (model.getRowCount() > 0) {
				model.removeRow(0);
			}

			int bytes[] = VMController.getVM().physicalMemory(pageDirectoryBaseAddress, 4096);

			if (bytes == null) {
				return;
			}

			for (int x = 0; x < bytes.length; x += 4) {
				long value = CommonLib.getInt(bytes, x);

				long baseL = value & 0xfffff000;
				String base = "0x" + Long.toHexString(baseL);
				String avl = String.valueOf((value >> 9) & 3);
				String g = String.valueOf((value >> 8) & 1);
				String d = String.valueOf((value >> 6) & 1);
				String a = String.valueOf((value >> 5) & 1);
				String pcd = String.valueOf((value >> 4) & 1);
				String pwt = String.valueOf((value >> 3) & 1);
				String us = String.valueOf((value >> 2) & 1);
				String wr = String.valueOf((value >> 1) & 1);
				String p = String.valueOf((value >> 0) & 1);

				ia32_pageDirectories.add(new IA32PageDirectory(base, avl, g, d, a, pcd, pwt, us, wr, p));

				model.addRow(new String[]{String.valueOf(x / 4), base, avl, g, d, a, pcd, pwt, us, wr, p});
			}

			pageDirectoryTable.setModel(model);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void pageDirectoryTableMouseClicked(MouseEvent evt) {
		if (evt.getClickCount() == 2) {
			String pageTableAddress = pageDirectoryTable.getValueAt(pageDirectoryTable.getSelectedRow(), 1).toString();

			//			float totalByte2 = 4096 - 1;
			//			totalByte2 = totalByte2 / 8;
			//			int totalByte3 = (int) Math.floor(totalByte2);
			//			String realEndAddressStr;
			//			String realStartAddressStr;
			String baseAddress = pageTableAddress;
			BigInteger realStartAddress = CommonLib.string2BigInteger(baseAddress);
			//
			//			realStartAddressStr = String.format("%08x", realStartAddress);
			//			BigInteger realEndAddress = realStartAddress.add(BigInteger.valueOf(totalByte3 * 8));
			//			realEndAddressStr = String.format("%08x", realEndAddress);

			DefaultTableModel model = (DefaultTableModel) pageTableTable.getModel();
			while (model.getRowCount() > 0) {
				model.removeRow(0);
			}

			int bytes[] = VMController.getVM().physicalMemory(realStartAddress, 4096);

			if (bytes == null) {
				return;
			}

			for (int x = 0; x < bytes.length; x += 4) {
				long value = CommonLib.getInt(bytes, x);

				long baseL = value & 0xfffff000;
				String base = "0x" + Long.toHexString(baseL);
				String avl = String.valueOf((value >> 9) & 3);
				String g = String.valueOf((value >> 8) & 1);
				String d = String.valueOf((value >> 6) & 1);
				String a = String.valueOf((value >> 5) & 1);
				String pcd = String.valueOf((value >> 4) & 1);
				String pwt = String.valueOf((value >> 3) & 1);
				String us = String.valueOf((value >> 2) & 1);
				String wr = String.valueOf((value >> 1) & 1);
				String p = String.valueOf((value >> 0) & 1);

				model.addRow(new String[]{String.valueOf(x / 4), base, avl, g, d, a, pcd, pwt, us, wr, p});
			}

			pageTableTable.setModel(model);
		}
	}

	class ButtonRenderer extends JButton implements TableCellRenderer {

		public ButtonRenderer() {
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			setFont(table.getFont());
			if (table.getValueAt(row, 3) == null || table.getValueAt(row, 3).equals("")) {
				return null;
			}
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(UIManager.getColor("Button.background"));
			} else {
				setForeground(table.getForeground());
				setBackground(UIManager.getColor("Button.background"));
			}

			setText((value == null) ? "" : value.toString());
			return this;
		}
	}

	class ButtonEditor extends DefaultCellEditor {

		protected JButton button;
		private boolean isPushed;
		BigInteger cr3;

		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
				}
			});
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			if (table.getValueAt(row, 3) == null || table.getValueAt(row, 3).equals("")) {
				return null;
			}
			if (isSelected) {
				button.setForeground(table.getSelectionForeground());
				button.setBackground(table.getSelectionBackground());
			} else {
				button.setForeground(table.getForeground());
				button.setBackground(table.getBackground());
			}

			cr3 = CommonLib.string2BigInteger(table.getValueAt(row, 2).toString());

			button.setText((value == null) ? "" : value.toString());
			isPushed = true;
			return button;
		}

		public Object getCellEditorValue() {
			if (isPushed) {
				gkd.dumpPageDirectoryAddressTextField.setText("0x" + cr3.toString(16));
				gkd.dumpPagingSummaryPageDirectoryAddressTextField.setText("0x" + cr3.toString(16));
				gkd.updatePageTable(cr3);
				gkd.bottomTabbedPane.setSelectedIndex(2);
			}
			isPushed = false;
			return button.getText();
		}

		public boolean stopCellEditing() {
			isPushed = false;
			return super.stopCellEditing();
		}

		protected void fireEditingStopped() {
			super.fireEditingStopped();
		}
	}

	private void refreshAddressTranslateButtonActionPerformed(ActionEvent evt) {
		AddressTranslateTableModel model = (AddressTranslateTableModel) this.addressTranslateTable2.getModel();

		if (searchVirtualAddressRadioButton.isSelected()) {
			if (!this.addressTextField.getText().contains(":") || this.addressTextField.getText().replaceAll("[^:]", "").length() != 1) {
				JOptionPane.showMessageDialog(this, "Error, please input <segment selector>:<offset>\n\ne.g. : 0x10:0x12345678", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			BigInteger segSelector = CommonLib.string2BigInteger(this.addressTextField.getText().split(":")[0]);
			BigInteger address = CommonLib.string2BigInteger(this.addressTextField.getText().split(":")[1]);

			// for (int x = 0; x < model.getRowCount(); x++) {
			// if (model.searchType.get(x).equals(1) &&
			// model.searchSegSelector.get(x).equals(segSelector) &&
			// model.searchAddress.get(x).equals(address)) {
			// return;
			// }
			// }
			model.searchType.add(1);
			model.searchSegSelector.add(segSelector);
			model.searchAddress.add(address);

			model.virtualAddress.add(address);
			BigInteger segNo = segSelector.shiftRight(3);
			model.segNo.add(segNo);

			// read GDT descriptor
			int descriptor[] = VMController.getVM().physicalMemory(ldtr.add(segNo.multiply(BigInteger.valueOf(8))), 8);
			BigInteger baseAddress = CommonLib.getBigInteger(descriptor[2], descriptor[3], descriptor[4], descriptor[7], 0, 0, 0, 0);
			BigInteger linearAddress = baseAddress.add(address);
			model.baseAddress.add(baseAddress);
			model.linearAddress.add(linearAddress);

			BigInteger pdNo = CommonLib.getBigInteger(linearAddress, 31, 22);
			model.pdNo.add(pdNo);
			int pdeBytes[] = VMController.getVM().physicalMemory(cr3.add(pdNo.multiply(BigInteger.valueOf(4))), 4);
			BigInteger pde = CommonLib.getBigInteger(pdeBytes, 0);
			model.pde.add(pde);

			BigInteger ptNo = CommonLib.getBigInteger(linearAddress, 21, 12);
			model.ptNo.add(ptNo);
			BigInteger pageTableBaseAddress = pde.and(BigInteger.valueOf(0xfffff000));
			int pteBytes[] = VMController.getVM().physicalMemory(pageTableBaseAddress.add(ptNo.multiply(BigInteger.valueOf(4))), 4);
			BigInteger pte = CommonLib.getBigInteger(pteBytes, 0);
			BigInteger pagePhysicalAddress = pte.and(BigInteger.valueOf(0xfffff000));
			model.pte.add(pte);

			BigInteger physicalAddress = pagePhysicalAddress.add(CommonLib.getBigInteger(linearAddress, 11, 0));
			model.physicalAddress.add(physicalAddress);
			int bytesAtPhysicalAddress[] = VMController.getVM().physicalMemory(physicalAddress, 8);
			model.bytes.add(GKDCommonLib.convertToString(bytesAtPhysicalAddress));

			model.fireTableDataChanged();
		} else if (searchLinearAddressRadioButton.isSelected()) {
			BigInteger address = CommonLib.string2BigInteger(this.addressTextField.getText());

			model.searchType.add(2);
			model.searchAddress.add(address);

			BigInteger baseAddress = BigInteger.valueOf(0);
			BigInteger linearAddress = baseAddress.add(address);
			model.baseAddress.add(baseAddress);
			model.linearAddress.add(linearAddress);

			BigInteger pdNo = CommonLib.getBigInteger(linearAddress, 31, 22);
			model.pdNo.add(pdNo);
			int pdeBytes[] = VMController.getVM().physicalMemory(cr3.add(pdNo.multiply(BigInteger.valueOf(4))), 4);
			BigInteger pde = CommonLib.getBigInteger(pdeBytes, 0);
			model.pde.add(pde);

			BigInteger ptNo = CommonLib.getBigInteger(linearAddress, 21, 12);
			model.ptNo.add(ptNo);
			BigInteger pageTableBaseAddress = pde.and(BigInteger.valueOf(0xfffff000));
			int pteBytes[] = VMController.getVM().physicalMemory(pageTableBaseAddress.add(ptNo.multiply(BigInteger.valueOf(4))), 4);
			BigInteger pte = CommonLib.getBigInteger(pteBytes, 0);
			BigInteger pagePhysicalAddress = pte.and(BigInteger.valueOf(0xfffff000));
			model.pte.add(pte);

			BigInteger physicalAddress = pagePhysicalAddress.add(CommonLib.getBigInteger(linearAddress, 11, 0));
			model.physicalAddress.add(physicalAddress);
			int bytesAtPhysicalAddress[] = VMController.getVM().physicalMemory(physicalAddress, 8);
			model.bytes.add(GKDCommonLib.convertToString(bytesAtPhysicalAddress));

			model.fireTableDataChanged();
		}
	}

	private void addressTextFieldKeyTyped(KeyEvent evt) {
		if (evt.getKeyChar() == '\n') {
			refreshAddressTranslateButtonActionPerformed(null);
		}
	}

	private void button17ActionPerformed(ActionEvent evt) {
		final JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			if (!GKDCommonLib.saveImage(addressTranslateTable2, file)) {
				JOptionPane.showMessageDialog(this, "Cannot save image.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void button18ActionPerformed(ActionEvent evt) {
		final JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			GKDCommonLib.exportTableModelToExcel(file, this.addressTranslateTable2.getModel(), String.valueOf(cr3));
		}
	}

	private void refreshAddressTranslateTableButtonActionPerformed(ActionEvent evt) {
		AddressTranslateTableModel model = (AddressTranslateTableModel) this.addressTranslateTable2.getModel();
		for (int x = 0; x < model.getRowCount(); x++) {
			if (model.searchType.get(x).equals(1)) {
				model.segNo.set(x, model.searchSegSelector.get(x).shiftRight(3));
				model.virtualAddress.set(x, model.searchAddress.get(x));

				BigInteger gdtBase = CommonLib.getBigInteger(VMController.getVM().physicalMemory(cr3, 8), 0);
				logger.debug("gdtBase=" + gdtBase.toString(16));
				gdtBase = gdtBase.add(model.segNo.get(x).multiply(BigInteger.valueOf(8)));
				int bytes[] = VMController.getVM().physicalMemory(gdtBase, 8);

				Long gdtDescriptor = CommonLib.getLong(bytes, 0);
				logger.debug(Long.toHexString(gdtDescriptor));
				BigInteger base = CommonLib.getBigInteger(bytes[2], bytes[3], bytes[4], bytes[7], 0, 0, 0, 0);
				logger.debug(base.toString(16));

				model.linearAddress.set(x, base.add(model.searchAddress.get(x)));
			}
		}
		model.fireTableDataChanged();
	}

	private void jButton19ActionPerformed(ActionEvent evt) {
		int rows[] = addressTranslateTable2.getSelectedRows();
		AddressTranslateTableModel model = (AddressTranslateTableModel) this.addressTranslateTable2.getModel();
		model.removeRow(rows);
	}

	private ButtonGroup getButtonGroup1() {
		if (buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
		}
		return buttonGroup1;
	}

	private JCheckBox getHideIfAddressIsZeroCheckBox() {
		if (hideIfAddressIsZeroCheckBox == null) {
			hideIfAddressIsZeroCheckBox = new JCheckBox();
			hideIfAddressIsZeroCheckBox.setText("Hide if address = 0");
			hideIfAddressIsZeroCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					hideIfAddressIsZeroCheckBoxActionPerformed(evt);
				}
			});
		}
		return hideIfAddressIsZeroCheckBox;
	}

	private void hideIfAddressIsZeroCheckBoxActionPerformed(ActionEvent evt) {
		((PageDirectoryTableModel) pageDirectoryTable.getModel()).setShowZeroAddress(!hideIfAddressIsZeroCheckBox.isSelected());
		((PageTableTableModel) pageTableTable.getModel()).setShowZeroAddress(!hideIfAddressIsZeroCheckBox.isSelected());
	}
}
