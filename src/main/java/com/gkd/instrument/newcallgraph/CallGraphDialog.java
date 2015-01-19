package com.gkd.instrument.newcallgraph;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gkd.GKD;
import com.gkd.Setting;
import com.gkd.instrument.callgraph.JmpData;

public class CallGraphDialog extends JDialog {
	private final JPanel contentPanel = new JPanel();
	int noOfInstruction;
	Vector<JmpData> jmpData;

	public static void main(String args[]) {
		new CallGraphDialog(null, null, 0).setVisible(true);
	}

	public CallGraphDialog(JFrame frame, Vector<JmpData> jmpData, int noOfInstruction) {
		super(frame, true);
		this.jmpData = jmpData;
		this.noOfInstruction = noOfInstruction;
		setBounds(100, 100, 1000, 850);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			CallGraphPanel callGraphPanel = new CallGraphPanel((GKD) frame);
			contentPanel.add(callGraphPanel, BorderLayout.CENTER);
			callGraphPanel.initGraph(jmpData, noOfInstruction);
		}
		setLocationRelativeTo(null);
		Setting.getInstance().restoreComponentPositionAndSize("callGraphDialog", this);

		this.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent evt) {
			}

			public void windowActivated(WindowEvent evt) {
			}

			public void windowClosing(WindowEvent evt) {
				Setting.getInstance().saveComponentPositionAndSize("callGraphDialog", CallGraphDialog.this);
			}
		});
	}

}
