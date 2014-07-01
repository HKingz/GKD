package com.gkd;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.gkd.stub.VMController;
import com.peterswing.CommonLib;

public class SearchMemoryDialog extends JDialog {
	private JProgressBar progressBar1;
	private JPanel panel1;
	private JButton button1;
	JTable table;
	String pattern;
	long from;
	long to;
	private JTextField textField1;
	private JLabel label1;
	private JPanel panel2;
	private JLabel jAddressLabel;
	int patternByte[];
	boolean shouldStop;
	Thread t;
	SearchThread s = new SearchThread();

	public SearchMemoryDialog(JFrame frame, JTable jTable, String pattern, long from, long to) {
		super(frame, true);
		this.table = jTable;
		this.pattern = pattern.trim().toLowerCase();
		this.from = from;
		this.to = to;

		try {
			progressBar1 = new JProgressBar();
			getContentPane().add(progressBar1, BorderLayout.CENTER);

			panel1 = new JPanel();
			getContentPane().add(panel1, BorderLayout.SOUTH);

			jAddressLabel = new JLabel();
			panel1.add(jAddressLabel);

			button1 = new JButton();
			panel1.add(button1);
			button1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					button1ActionPerformed(evt);
				}
			});
			button1.setText(MyLanguage.getString("Cancel"));

			panel2 = new JPanel();
			BorderLayout jPanel2Layout = new BorderLayout();
			panel2.setLayout(jPanel2Layout);
			getContentPane().add(panel2, BorderLayout.NORTH);
			panel2.setPreferredSize(new java.awt.Dimension(290, 35));

			textField1 = new JTextField();
			panel2.add(textField1, BorderLayout.CENTER);

			label1 = new JLabel();
			panel2.add(label1, BorderLayout.NORTH);
			label1.setText(MyLanguage.getString("Searching_these_bytes"));

			setSize(350, 130);
			CommonLib.centerDialog(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setTitle(MyLanguage.getString("Search") + " " + pattern + " " + MyLanguage.getString("From") + " 0x" + Long.toHexString(from) + " " + MyLanguage.getString("To") + " 0x"
				+ Long.toHexString(to));
		t = new Thread(s);
		t.start();
	}

	class SearchThread implements Runnable {
		public void run() {
			if (pattern.startsWith("0x")) {
				patternByte = CommonLib.hexStringToByteArray(pattern.substring(2));
			} else if (CommonLib.isNumeric(pattern)) {
				patternByte = CommonLib.integerStringToByteArray(pattern);
			} else {
				patternByte = CommonLib.stringToByteArray(pattern);
			}

			for (int x = 0; x < patternByte.length; x++) {
				textField1.setText(textField1.getText() + String.format("0x%02x", patternByte[x]) + " ");
			}

			progressBar1.setMaximum(100);
			int totalByte = 200;
			for (long addr = from; addr <= to; addr += (totalByte - patternByte.length + 1)) {
				jAddressLabel.setText("0x" + Long.toHexString(addr));
				//				GKD.commandReceiver.clearBuffer();
				//				GKD.sendBochsCommand("xp /" + totalByte + "bx " + addr);
				float totalByte2 = totalByte - 1;
				totalByte2 = totalByte2 / 8;
				int totalByte3 = (int) Math.floor(totalByte2);
				String realEndAddressStr;
				String realStartAddressStr;
				long realStartAddress = addr;
				//				realStartAddressStr = String.format("%08x", realStartAddress);
				long realEndAddress = realStartAddress + totalByte3 * 8;
				//				realEndAddressStr = String.format("%08x", realEndAddress);

				//				String result =  GKD.commandReceiver.getCommandResult(realStartAddressStr, realEndAddressStr, null);
				//				String[] lines = result.split("\n");
				int bytes[] = VMController.getVM().physicalMemory(BigInteger.valueOf(realStartAddress), totalByte);

				// search
				for (int x = 0; x < bytes.length - patternByte.length; x++) {
					int temp[] = new int[patternByte.length];
					for (int z = 0; z < temp.length; z++) {
						temp[z] = bytes[x + z];
					}
					if (Arrays.equals(patternByte, temp)) {
						// System.out.println("match " + (addr + x));
						((SearchTableModel) table.getModel()).addRow(addr + x, patternByte);
					}
				}
				// end search

				if (shouldStop) {
					return;
				}

				progressBar1.setValue((int) ((addr - from) * 100 / (to - from)));
			}
			progressBar1.setValue(100);
			button1.setText(MyLanguage.getString("Finished"));
		}
	}

	private void button1ActionPerformed(ActionEvent evt) {
		shouldStop = true;
		while (t.isAlive()) {
		}
		setVisible(false);
	}
}
