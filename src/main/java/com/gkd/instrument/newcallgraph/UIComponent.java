package com.gkd.instrument.newcallgraph;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import com.mxgraph.swing.handler.mxCellHandler;
import com.mxgraph.view.mxGraph;

public class UIComponent extends JPanel {
	public JPanel panel;
	public JLabel titleLabel;
	public JTable table;
	public CallGraphTableModel model = new CallGraphTableModel();
	private JScrollPane scrollPane;
	private JPanel bottomPanel;
	private JLabel resizeLabel;
	protected Object cell;
	protected mxGraph graph;
	protected CallGraphComponent graphxComponent;

	public UIComponent(final Object cell, CallGraphComponent graphxComponent) {
		this.cell = cell;
		this.graphxComponent = graphxComponent;
		this.graph = graphxComponent.getGraph();
		setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		add(panel, BorderLayout.NORTH);

		titleLabel = new JLabel("");
		panel.add(titleLabel);

		JPanel mainPanel = new JPanel();
		add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		mainPanel.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		//$hide>>$
		table.setModel(model);
		//$hide<<$
		scrollPane.setViewportView(table);

		bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		add(bottomPanel, BorderLayout.SOUTH);

		resizeLabel = new JLabel("New label");
		bottomPanel.add(resizeLabel);

		ResizeHandler resizeHandler = new ResizeHandler();
		resizeLabel.addMouseListener(resizeHandler);
		resizeLabel.addMouseMotionListener(resizeHandler);
	}

	@Override
	public String toString() {
		return "";
	}

	public void setData(Vector<String[]> data) {
		model.data = data;
		model.fireTableDataChanged();
	}

	class ResizeHandler implements MouseListener, MouseMotionListener {
		protected int index;

		public ResizeHandler() {
			this(7);
		}

		public ResizeHandler(int index) {
			this.index = index;
		}

		public void mouseClicked(MouseEvent e) {
			// ignore
		}

		public void mouseEntered(MouseEvent e) {
			// ignore
		}

		public void mouseExited(MouseEvent e) {
			// ignore
		}

		public void mousePressed(MouseEvent e) {
			System.out.println("mousePressed");
			// Selects to create a handler for resizing
			if (!graph.isCellSelected(cell)) {
				graphxComponent.selectCellForEvent(cell, e);
			}

			// Initiates a resize event in the handler
			mxCellHandler handler = graphxComponent.getSelectionCellsHandler().getHandler(cell);
			System.out.println(handler);
			if (handler != null) {
				// Starts the resize at index 7 (bottom right)
				handler.start(SwingUtilities.convertMouseEvent((Component) e.getSource(), e, graphxComponent.getGraphControl()), index);
				e.consume();
			}
		}

		public void mouseReleased(MouseEvent e) {
			graphxComponent.getGraphControl().dispatchEvent(SwingUtilities.convertMouseEvent((Component) e.getSource(), e, graphxComponent.getGraphControl()));
		}

		public void mouseDragged(MouseEvent e) {
			graphxComponent.getGraphControl().dispatchEvent(SwingUtilities.convertMouseEvent((Component) e.getSource(), e, graphxComponent.getGraphControl()));
		}

		public void mouseMoved(MouseEvent e) {
			// ignore
		}
	}
}
