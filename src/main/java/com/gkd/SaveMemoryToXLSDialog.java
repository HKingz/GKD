package com.gkd;

import info.clearthought.layout.TableLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.gkd.stub.VMController;
import com.peterswing.CommonLib;

public class SaveMemoryToXLSDialog extends JDialog {
	public JRadioButton currentWindowRadioButton;
	public JRadioButton radioButton1;
	private JLabel toLabel;
	private JProgressBar progressBar1;
	private JButton exportButton;
	public JTextField toTextField;
	public JTextField fromTextField;
	private ButtonGroup buttonGroup1;
	private JLabel label1;
	public boolean ok;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.peterswing.white.PeterSwingWhiteLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				SaveMemoryToXLSDialog inst = new SaveMemoryToXLSDialog(frame);
				inst.setVisible(true);
			}
		});
	}

	public SaveMemoryToXLSDialog(JFrame frame) {
		super(frame, true);
		try {
			TableLayout thisLayout = new TableLayout(new double[][] { { 7.0, 20.0, 49.0, TableLayout.FILL, 36.0, TableLayout.FILL, 7.0 },
					{ TableLayout.FILL, TableLayout.FILL, TableLayout.FILL, TableLayout.FILL, TableLayout.FILL, 3.0 } });
			thisLayout.setHGap(5);
			thisLayout.setVGap(5);
			getContentPane().setLayout(thisLayout);
			this.setTitle("Save memory to excel");

			currentWindowRadioButton = new JRadioButton();
			getContentPane().add(currentWindowRadioButton, "1, 1, 3, 1");
			currentWindowRadioButton.setText("Same as memory window");
			getButtonGroup1().add(currentWindowRadioButton);
			currentWindowRadioButton.setSelected(true);
			currentWindowRadioButton.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					jCurrentWindowRadioButtonMouseClicked(evt);
				}
			});

			label1 = new JLabel();
			getContentPane().add(label1, "1, 0, 5, 0");
			label1.setText("Which range of memory you want to save to xls?");

			radioButton1 = new JRadioButton();
			getContentPane().add(radioButton1, "1, 2, 2, 2");
			radioButton1.setText("From");
			radioButton1.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					jRadioButton1MouseClicked(evt);
				}
			});
			getButtonGroup1().add(radioButton1);
			getContentPane().add(getJFromTextField(), "3, 2");
			getContentPane().add(getJToLabel(), "4, 2");
			getContentPane().add(getJToTextField(), "5, 2");
			getContentPane().add(getJExportButton(), "4, 4, 5, 4");
			getContentPane().add(getJProgressBar1(), "1, 3, 5, 3");

			this.setSize(391, 156);
			CommonLib.centerDialog(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ButtonGroup getButtonGroup1() {
		if (buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
		}
		return buttonGroup1;
	}

	private JTextField getJFromTextField() {
		if (fromTextField == null) {
			fromTextField = new JTextField();
			fromTextField.setEnabled(false);
		}
		return fromTextField;
	}

	private JLabel getJToLabel() {
		if (toLabel == null) {
			toLabel = new JLabel();
			toLabel.setText("To");
			toLabel.setHorizontalAlignment(SwingConstants.CENTER);
			toLabel.setEnabled(false);
		}
		return toLabel;
	}

	private JTextField getJToTextField() {
		if (toTextField == null) {
			toTextField = new JTextField();
			toTextField.setEnabled(false);
		}
		return toTextField;
	}

	private JButton getJExportButton() {
		if (exportButton == null) {
			exportButton = new JButton();
			exportButton.setText("Export to excel");
			exportButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/excel.gif")));
			exportButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jExportButtonActionPerformed(evt);
				}
			});
		}
		return exportButton;
	}

	private void jExportButtonActionPerformed(ActionEvent evt) {
		if (this.currentWindowRadioButton.isSelected()) {
			ok = true;
		} else {
			try {
				int totalByte = (int) (CommonLib.convertFilesize(toTextField.getText()) - CommonLib.convertFilesize(fromTextField.getText()) + 1);
				if (totalByte > 64 * 1024) {
					JOptionPane.showMessageDialog(this, "Cannot dump more than 64KB, because it is too slow");
				} else {
					JFileChooser fc = new JFileChooser();
					int returnVal = fc.showSaveDialog(this);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						if (!file.getName().toLowerCase().endsWith(".xls")) {
							file = new File(file.getAbsolutePath() + ".xls");
						}
						//						GKD.commandReceiver.shouldShow = false;
						int bytes[] = VMController.getVM().physicalMemory(CommonLib.string2BigInteger(fromTextField.getText()), totalByte); //GKD.getMemory(CommonLib.string2BigInteger(fromTextField.getText()), totalByte, true);
						GKDCommonLib.exportTableModelToExcel(file, bytes, fromTextField.getText(), CommonLib.convertFilesize(fromTextField.getText()));
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error, unable to export");
			}
		}
		setVisible(false);
	}

	private JProgressBar getJProgressBar1() {
		if (progressBar1 == null) {
			progressBar1 = new JProgressBar();
			progressBar1.setVisible(false);
		}
		return progressBar1;
	}

	private void jRadioButton1MouseClicked(MouseEvent evt) {
		this.fromTextField.setEnabled(true);
		this.toLabel.setEnabled(true);
		this.toTextField.setEnabled(true);
	}

	private void jCurrentWindowRadioButtonMouseClicked(MouseEvent evt) {
		this.fromTextField.setEnabled(false);
		this.toLabel.setEnabled(false);
		this.toTextField.setEnabled(false);
	}

}
