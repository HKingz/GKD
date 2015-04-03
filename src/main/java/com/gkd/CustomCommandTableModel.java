package com.gkd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

public class CustomCommandTableModel extends DefaultTableModel {
	String columnNames[] = { "Command", "Count" };
	Vector<String> command = new Vector<String>();
	Vector<Integer> count = new Vector<Integer>();
	JButton button = new JButton("Add");
	public static Logger logger = Logger.getLogger(CustomCommandTableModel.class);

	public CustomCommandTableModel() {
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(button), "Button clicked for row");
			}
		});
		button.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				logger.debug("mouseClicked");
			}

			@Override
			public void mousePressed(MouseEvent e) {
				logger.debug("mousePressed");
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				logger.debug("mouseReleased");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				logger.debug("mouseEntered");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				logger.debug("mouseExited");
			}

		});
		button.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				logger.debug("mouseMoved");
			}
		});
	}

	public String getColumnName(int column) {
		return columnNames[column];
	}

	public int getColumnCount() {
		if (columnNames == null) {
			return 0;
		}
		return columnNames.length;
	}

	public int getRowCount() {
		if (command == null) {
			return 1;
		}
		return command.size() + 1;
	}

	public void setValueAt(Object aValue, int row, int column) {
		this.fireTableDataChanged();
	}

	public Object getValueAt(final int row, int column) {
		if (row < getRowCount() - 1) {
			if (column == 0) {
				return command.get(row);
			} else if (column == 1) {
				return count.get(row);
			} else {
				return "";
			}
		} else {
			return button;
		}
	}

	public boolean isCellEditable(int row, int column) {
		return false;
	}

	public Class getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}
}
