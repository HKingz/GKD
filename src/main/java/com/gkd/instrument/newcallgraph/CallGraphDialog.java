package com.gkd.instrument.newcallgraph;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.gkd.GKD;
import com.gkd.Setting;
import com.gkd.instrument.callgraph.JmpData;
import com.gkd.instrument.callgraph.JmpType;

public class CallGraphDialog extends JDialog {
	private final JPanel contentPanel = new JPanel();
	int noOfInstruction;
	Vector<JmpData> jmpData;

	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel("com.peterswing.white.PeterSwingWhiteLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Vector<JmpData> jmpData = new Vector<JmpData>();
		jmpData.add(new JmpData(0, new Date(), 1, "1", 5, "4", JmpType.CALL, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 0));
		jmpData.add(new JmpData(1, new Date(), 4, "4", 15, "15", JmpType.SYSCALL, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 0));

		CallGraphDialog dialog = new CallGraphDialog(null, jmpData, 0);
		dialog.setVisible(true);
		dialog.setLocationRelativeTo(null);
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
