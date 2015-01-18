package com.gkd.jgraphx;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mxgraph.canvas.mxICanvas;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;

public class TestJGraphx extends JFrame {

	private JPanel contentPane;
	mxGraph graph;
	JGraphxComponent graphxComponent;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestJGraphx frame = new TestJGraphx();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TestJGraphx() {
		graph = new mxGraph() {
			public void drawState(mxICanvas canvas, mxCellState state, boolean drawLabel) {
				if (getModel().isVertex(state.getCell()) && canvas instanceof JGraphxCanvas) {
					JGraphxCanvas c = (JGraphxCanvas) canvas;
					c.drawVertex(state);
				} else {
					super.drawState(canvas, state, true);
				}
			}

			public boolean isPort(Object cell) {
				mxGeometry geo = getCellGeometry(cell);

				return (geo != null) ? geo.isRelative() : false;
			}

			public String getToolTipForCell(Object cell) {
				return super.getToolTipForCell(cell);
			}

			public boolean isCellFoldable(Object cell, boolean collapse) {
				return false;
			}
		};
		graphxComponent = new JGraphxComponent(graph);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		contentPane.add(graphxComponent, BorderLayout.CENTER);

		graph.setCellsDisconnectable(false);
		graphxComponent.setGridVisible(true);
		graphxComponent.setGridColor(Color.lightGray);
		graphxComponent.setBackground(Color.white);
		graphxComponent.getViewport().setOpaque(false);
		graphxComponent.setBackground(Color.WHITE);
		graphxComponent.setConnectable(false);
		graphxComponent.getGraphControl().addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				mxCell cell = (mxCell) graphxComponent.getCellAt(e.getX(), e.getY());
				if (cell == null) {
					return;
				}
				JComponent value = (JComponent) cell.getValue();
				if (value instanceof JCheckBox) {
					JCheckBox c = (JCheckBox) value;
					c.setSelected(!c.isSelected());
					//c.repaint();
					//					e.setSource(c);
					//					c.dispatchEvent(e);
					//					c.repaint();
					//					c.updateUI();
					graphxComponent.repaint();
					System.out.println("ok");
				}
			}
		});

		graph.setCellsResizable(true);
		graph.setCellsMovable(true);
		graph.setCellsEditable(false);
		graph.foldCells(false);
		graph.setGridSize(10);
		com.mxgraph.swing.util.mxGraphTransferable.enableImageSupport = false;

		addCells(graph.getDefaultParent());
	}

	private void addCells(Object parent) {
		mxCell node = (mxCell) graph.insertVertex(parent, null, new JButton("B1"), 200, 200, 50, 50);
		mxCell ports[] = addPort(node);

		mxCell node2 = (mxCell) graph.insertVertex(parent, null, new JCheckBox("C1"), 300, 300, 50, 50);
		mxCell ports2[] = addPort(node2);

		graph.insertEdge(parent, null, "", ports[0], ports2[0], "edgeStyle=entityRelationEdgeStyle;");
		graph.insertEdge(parent, null, "", ports[1], ports2[1], "edgeStyle=entityRelationEdgeStyle;");
		graph.insertEdge(parent, null, "", ports[2], ports2[2], "edgeStyle=entityRelationEdgeStyle;");

	}

	private mxCell[] addPort(mxCell node) {
		final int PORT_DIAMETER = 0;
		final int PORT_RADIUS = PORT_DIAMETER / 2;

		mxGeometry geo1 = new mxGeometry(0, 0.5, PORT_DIAMETER, PORT_DIAMETER);
		//geo1.setOffset(new mxPoint(-PORT_RADIUS, -PORT_RADIUS));
		geo1.setRelative(true);
		mxCell port1 = new mxCell(null, geo1, "shape=ellipse;perimter=ellipsePerimeter");
		port1.setVertex(true);
		graph.addCell(port1, node);

		mxGeometry geo2 = new mxGeometry(1, 0.5, PORT_DIAMETER, PORT_DIAMETER);
		//geo2.setOffset(new mxPoint(-PORT_RADIUS, -PORT_RADIUS));
		geo2.setRelative(true);
		mxCell port2 = new mxCell(null, geo2, "shape=ellipse;perimter=ellipsePerimeter");
		port2.setVertex(true);
		graph.addCell(port2, node);

		mxGeometry geo3 = new mxGeometry(1, 0.6, PORT_DIAMETER, PORT_DIAMETER);
		//geo2.setOffset(new mxPoint(-PORT_RADIUS, -PORT_RADIUS));
		geo3.setRelative(true);
		mxCell port3 = new mxCell(null, geo3, "shape=ellipse;perimter=ellipsePerimeter");
		port2.setVertex(true);
		graph.addCell(port3, node);

		return new mxCell[] { port1, port2, port3 };
	}

}
