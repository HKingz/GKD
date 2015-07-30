package com.gkd.helprequest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.border.LineBorder;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

import com.peterswing.advancedswing.enhancedtextarea.EnhancedTextArea;

public class HelpRequestDialog extends javax.swing.JDialog {
	private JButton sendButton;
	private JLabel label3;
	private JLabel label4;
	private JLabel label5;
	private JScrollPane scrollPane1;
	private JTextField nameTextField;
	private EnhancedTextArea infoTextArea;
	private JTextArea messageTextField;
	private JTextField emailTextField;
	private JLabel label2;
	private JLabel label1;

	public HelpRequestDialog(JFrame frame) {
		super(frame);
		initGUI();
	}

	private void initGUI() {
		try {
			GroupLayout thisLayout = new GroupLayout((JComponent) getContentPane());
			getContentPane().setLayout(thisLayout);
			this.setTitle("I need help");
			sendButton = new JButton();
			sendButton.setText("Send help request");
			sendButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					sendButtonActionPerformed(evt);
				}
			});

			label1 = new JLabel();
			label1.setText("<- Press this button, it will send the information to the author, so that he can help you");

			label4 = new JLabel();
			label4.setText("Below are the auto-collected information, will be send to the author");

			infoTextArea = new EnhancedTextArea();

			label5 = new JLabel();
			label5.setText("Name");

			nameTextField = new JTextField();

			label2 = new JLabel();
			label2.setText("Email");

			emailTextField = new JTextField();

			label3 = new JLabel();
			label3.setText("Massage");

			scrollPane1 = new JScrollPane();
			scrollPane1.setBorder(new LineBorder(new java.awt.Color(88, 88, 88), 1, false));
			{
				messageTextField = new JTextArea();
				scrollPane1.setViewportView(messageTextField);
			}

			thisLayout
					.setVerticalGroup(
							thisLayout.createSequentialGroup().addContainerGap()
									.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
											.addComponent(sendButton, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
											.addComponent(label1, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(emailTextField, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(label2, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(label5, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(nameTextField, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
											GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(thisLayout.createParallelGroup().addComponent(scrollPane1, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
							.addGroup(GroupLayout.Alignment.LEADING,
									thisLayout.createSequentialGroup().addGap(8)
											.addComponent(label3, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE).addGap(42)))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(label4, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(infoTextArea, 0, 207, Short.MAX_VALUE).addContainerGap());
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup().addContainerGap().addGroup(thisLayout.createParallelGroup().addGroup(thisLayout.createSequentialGroup()
					.addGroup(thisLayout.createParallelGroup().addComponent(label3, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
							.addGroup(GroupLayout.Alignment.LEADING,
									thisLayout.createSequentialGroup().addComponent(label2, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE).addGap(18)))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(thisLayout.createParallelGroup().addComponent(scrollPane1, GroupLayout.Alignment.LEADING, 0, 560, Short.MAX_VALUE).addGroup(
							GroupLayout.Alignment.LEADING,
							thisLayout.createSequentialGroup().addComponent(emailTextField, GroupLayout.PREFERRED_SIZE, 254, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(label5, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(nameTextField, 0, 239, Short.MAX_VALUE))))
					.addGroup(GroupLayout.Alignment.LEADING,
							thisLayout.createSequentialGroup().addComponent(sendButton, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(label1, 0, 474, Short.MAX_VALUE))
					.addGroup(GroupLayout.Alignment.LEADING,
							thisLayout.createSequentialGroup().addComponent(label4, GroupLayout.PREFERRED_SIZE, 449, GroupLayout.PREFERRED_SIZE).addGap(0, 177, Short.MAX_VALUE))
					.addComponent(infoTextArea, GroupLayout.Alignment.LEADING, 0, 626, Short.MAX_VALUE)).addContainerGap());

			this.setSize(666, 425);
			infoTextArea.setEnabled(false);
			infoTextArea.setBorder(new LineBorder(new java.awt.Color(88, 88, 88), 1, false));
			infoTextArea.getTextArea().setEnabled(false);

			String result = "";
			//			String commands[] = { "r", "info eflags", "xp /10bx 0x1000", "disasm cs:eip", "disasm 0x7c00 0x7cff", "info gdt 0 10", "info idt 0 10", "xp /4096bx 0x1000",
			//					"print-stack 40", "info tab", "info break" };			
			//			for (String c : commands) {
			//				GKD.sendBochsCommand(c);
			//				result += "\n\n" + c + "\n\n" + commandReceiver.getCommandResultUntilEnd();
			//			}
			infoTextArea.setText(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendButtonActionPerformed(ActionEvent evt) {
		String url = "http://webservice1.petersoft.com/NuSoapServer.php";
		Service service = new Service();
		try {
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(url));
			call.setOperationName("peterBochsHelpRequest");
			call.addParameter("Name", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("Email", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("Message", XMLType.XSD_STRING, ParameterMode.IN);
			call.setReturnType(XMLType.XSD_STRING);
			String res = (String) call.invoke(new Object[] { nameTextField.getText(), emailTextField.getText(),
					messageTextField.getText() + "\n-------------------\n" + infoTextArea.getTextArea().getText() });
			if (res.equals("ok")) {
				JOptionPane.showMessageDialog(this, "Message is sent to Peter, thanks");
			} else {
				JOptionPane.showMessageDialog(this, "Send fail, please check network connection");
			}
			return;
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(this, "Send fail");
	}
}
