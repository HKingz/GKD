package com.gkd;

import java.awt.BorderLayout;
import java.math.BigInteger;
import java.util.LinkedHashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.gkd.architecture.DescriptorParser;
import com.gkd.stub.VMController;
import com.peterswing.CommonLib;

public class GDTLDTPanel extends JPanel {
	private JTable byteTable;
	private int gdtNo;
	private JPanel panel3;
	private JPanel panel2;
	private JTabbedPane tabbedPane1;
	private JLabel typeLabel;
	private JTable fieldTable;
	private JScrollPane scrollPane1;
	private int bytes[] = new int[8];
	private long value;
	private long bit[] = new long[64];
	private GKD gkd;
	private int type;
	private BigInteger gdtAddress;
	private JScrollPane scrollPane2;

	public GDTLDTPanel(GKD gkd, int type, BigInteger gdtAddress, int gdtNo) {
		this.gkd = gkd;
		this.type = type;
		this.gdtAddress = gdtAddress;
		this.gdtNo = gdtNo;

		try {
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);

			tabbedPane1 = new JTabbedPane();
			this.add(tabbedPane1, BorderLayout.CENTER);
			tabbedPane1.setTabPlacement(JTabbedPane.LEFT);
			tabbedPane1.setPreferredSize(new java.awt.Dimension(515, 437));

			panel2 = new JPanel();
			tabbedPane1.addTab(MyLanguage.getString("Info"), null, panel2, null);
			BorderLayout jPanel2Layout = new BorderLayout();
			panel2.setLayout(jPanel2Layout);

			scrollPane1 = new JScrollPane();
			panel2.add(scrollPane1, BorderLayout.CENTER);
			scrollPane1.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, false));

			DefaultTableModel jTable1Model = new DefaultTableModel(new String[][] { { "" }, { "" } }, new String[] { "31" });
			for (int x = 30; x >= 0; x--) {
				jTable1Model.addColumn(x);
			}

			byteTable = new JTable();
			scrollPane1.setViewportView(byteTable);
			byteTable.setModel(jTable1Model);
			byteTable.setBounds(12, 12, 562, 50);

			typeLabel = new JLabel();
			panel2.add(typeLabel, BorderLayout.NORTH);
			typeLabel.setText("Type : ");

			panel3 = new JPanel();
			tabbedPane1.addTab(MyLanguage.getString("Field"), null, panel3, null);
			BorderLayout jPanel3Layout = new BorderLayout();
			panel3.setLayout(jPanel3Layout);

			scrollPane2 = new JScrollPane();
			panel3.add(scrollPane2, BorderLayout.CENTER);

			TableModel jTable2Model = new DefaultTableModel(new String[][] {}, new String[] { MyLanguage.getString("Field"), MyLanguage.getString("Value") }) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};

			fieldTable = new JTable();
			scrollPane2.setViewportView(fieldTable);
			fieldTable.setModel(jTable2Model);

			/*String result;
			if (GKDVMStubController.vmType == VMType.Bochs) {
				if (type == 0) {
					GKD.sendBochsCommand("info gdt " + gdtNo);
					String gdtNoHex = String.format("0x%02x", gdtNo);
					result = GKD.commandReceiver.getCommandResult("GDT[" + gdtNoHex + "]");
				} else {
					GKD.sendBochsCommand("info ldt " + gdtNo);
					String gdtNoHex = String.format("0x%02x", gdtNo);
					result = GKD.commandReceiver.getCommandResult("LDT[" + gdtNoHex + "]");
				}
			
				GKD.commandReceiver.clearBuffer();
				GKD.sendBochsCommand("x /8bx " + "0x" + gdtAddress.add(BigInteger.valueOf(gdtNo * 8)).toString(16));
				result = GKD.commandReceiver.getCommandResult(String.format("%08x", gdtAddress.add(BigInteger.valueOf(gdtNo * 8))));
				String lines[] = result.split("\n");
			
				String byteStr[] = lines[0].replaceFirst("^.*>:\t", "").split("\t");
				for (int x = 0; x < 8; x++) {
					bytes[x] = (byte) Long.parseLong(byteStr[x].substring(2), 16);
				}
			} else if (GKDVMStubController.vmType == VMType.Qemu) {
				bytes = GKD.libGDB.virtualMemory(gdtAddress.add(BigInteger.valueOf(gdtNo * 8)), 8);
			}*/
			bytes = VMController.getVM().virtualMemory(gdtAddress.add(BigInteger.valueOf(gdtNo * 8)), 8);

			value = CommonLib.getLong(bytes, 0);

			for (int x = 0; x < 64; x++) {
				bit[x] = CommonLib.getBit(value, x);
			}

			for (int x = 0; x < 32; x++) {
				byteTable.setValueAt(value >> x & 1, 1, 31 - x);
			}

			for (int x = 32; x < 64; x++) {
				byteTable.setValueAt(value >> x & 1, 0, 63 - x);
			}

			// parse descriptor
			if (bit[44] == 1 && bit[43] == 1) {
				typeLabel.setText("Type : Code descriptor, value=0x" + Long.toHexString(value));
				parseCodeDescriptor();
			} else if (bit[44] == 1 && bit[43] == 0) {
				typeLabel.setText("Type : Data descriptor, value=0x" + Long.toHexString(value));
				parseDataDescriptor();
			} else if (bit[44] == 0 && bit[43] == 0 && bit[42] == 0 && bit[41] == 1 && bit[40] == 0) {
				typeLabel.setText("Type : LDT descriptor, value=0x" + Long.toHexString(value) + ", base=0x"
						+ Long.toHexString(CommonLib.getInt(bytes[2], bytes[3], bytes[4], bytes[7])) + ", limit=0x" + Long.toHexString(CommonLib.getShort(bytes[0], bytes[1])));
				parseLDT();
			} else if (bit[44] == 0 && bit[42] == 0 && bit[40] == 1) {
				typeLabel.setText("Type : TSS descriptor, value=0x" + Long.toHexString(value));
				this.removeAll();
				this.setLayout(new BorderLayout());
				this.add(new TSSPanel(gkd, type, gdtAddress, gdtNo, bytes), BorderLayout.CENTER);
			}
			// end parse descriptor
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void parseCodeDescriptor() {
		try {
			DefaultTableModel model = (DefaultTableModel) fieldTable.getModel();
			long base = CommonLib.getLong(bytes[2], bytes[3], bytes[4], bytes[7], 0, 0, 0, 0);
			model.addRow(new String[] { "base", "0x" + Long.toHexString(base) });

			long limit = CommonLib.getLong(bytes[0], bytes[1], bytes[6] & 0xf, 0, 0, 0, 0, 0);
			if (bit[55] == 1) {
				limit *= 4096;
			}
			model.addRow(new String[] { "limit", "0x" + Long.toHexString(limit) });

			model.addRow(new String[] { "G", String.valueOf(bit[55]) });
			model.addRow(new String[] { "D", String.valueOf(bit[54]) });
			model.addRow(new String[] { "AVL", String.valueOf(bit[52]) });
			model.addRow(new String[] { "P", String.valueOf(bit[47]) });
			model.addRow(new String[] { "DPL", String.valueOf(bit[45] + bit[46] << 1) });
			model.addRow(new String[] { "S", String.valueOf(bit[44]) });
			model.addRow(new String[] { "X", String.valueOf(bit[43]) });
			model.addRow(new String[] { "C", String.valueOf(bit[42]) });
			model.addRow(new String[] { "R", String.valueOf(bit[41]) });
			model.addRow(new String[] { "A", String.valueOf(bit[40]) });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void parseDataDescriptor() {
		try {
			DefaultTableModel model = (DefaultTableModel) fieldTable.getModel();
			long base = CommonLib.getLong(bytes[2], bytes[3], bytes[4], bytes[7], 0, 0, 0, 0);
			model.addRow(new String[] { "base", "0x" + Long.toHexString(base) });

			long limit = CommonLib.getLong(bytes[0], bytes[1], bytes[6] & 0xf, 0, 0, 0, 0, 0);
			if (bit[55] == 1) {
				limit *= 4096;
			}
			model.addRow(new String[] { "limit", "0x" + Long.toHexString(limit) });

			model.addRow(new String[] { "G", String.valueOf(bit[55]) });
			model.addRow(new String[] { "B", String.valueOf(bit[54]) });
			model.addRow(new String[] { "AVL", String.valueOf(bit[52]) });
			model.addRow(new String[] { "P", String.valueOf(bit[47]) });
			model.addRow(new String[] { "DPL", String.valueOf(bit[45] + bit[46] << 1) });
			model.addRow(new String[] { "S", String.valueOf(bit[44]) });
			model.addRow(new String[] { "X", String.valueOf(bit[43]) });
			model.addRow(new String[] { "E", String.valueOf(bit[42]) });
			model.addRow(new String[] { "W", String.valueOf(bit[41]) });
			model.addRow(new String[] { "A", String.valueOf(bit[40]) });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void parseLDT() {
		try {
			DefaultTableModel model = (DefaultTableModel) fieldTable.getModel();

			BigInteger base = BigInteger.valueOf(CommonLib.getInt(bytes[2], bytes[3], bytes[4], bytes[7]));
			long limit = CommonLib.getShort(bytes[0], bytes[1]);
			model.addRow(new String[] { "base", "0x" + base.toString(16) });
			model.addRow(new String[] { "limit", "0x" + Long.toHexString(limit) });
			model.addRow(new String[] { "dpl", "0x" + Long.toHexString(bit[46] << 1 + bit[45]) });
			model.addRow(new String[] { "p", "0x" + Long.toHexString(bit[47]) });
			model.addRow(new String[] { "avl", "0x" + Long.toHexString(bit[52]) });
			model.addRow(new String[] { "g", "0x" + Long.toHexString(bit[55]) });

			// parse each descriptor

			JScrollPane pane = new JScrollPane();
			tabbedPane1.addTab(MyLanguage.getString("Descriptor"), null, pane, null);
			JTable table = new JTable();
			DefaultTableModel model2 = new DefaultTableModel(new String[][] {},
					new String[] { "No.", "Type", "Value", "Base", "Limit", "A", "R/W", "C/E", "X", "S", "DPL", "P", "AVL", "D/B", "G" });

			if (limit > 1000) {
				limit = 1000;
			}
			int bytes[] = VMController.getVM().virtualMemory(base, (int) limit + 1);//GKD.getLinearMemory(base, (int) (limit + 1));

			for (int x = 0; x < limit; x += 8) {
				long value = CommonLib.getLong(bytes, x);
				LinkedHashMap<String, String> hm = DescriptorParser.parseDescriptor(value);
				model2.addRow(new String[] { String.valueOf(x), hm.get("Type"), hm.get("Value"), hm.get("Base"), hm.get("Limit"), hm.get("A"), hm.get("R/W"), hm.get("C/E"),
						hm.get("X"), hm.get("S"), hm.get("DPL"), hm.get("P"), hm.get("AVL"), hm.get("D/B"), hm.get("G") });
			}
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.setModel(model2);
			table.getColumnModel().getColumn(2).setPreferredWidth(150);
			for (int x = 5; x <= 14; x++) {
				table.getColumnModel().getColumn(x).setPreferredWidth(50);
			}
			pane.setViewportView(table);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}