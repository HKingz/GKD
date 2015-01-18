package com.gkd.jgraphx;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.util.mxPoint;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxGraphView;

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
			//			public void drawState(mxICanvas canvas, mxCellState state, boolean drawLabel) {
			//				if (!getModel().isVertex(state.getCell())) {
			//					super.drawState(canvas, state, true);
			//				}
			//				//				if (getModel().isVertex(state.getCell()) && canvas instanceof JGraphxCanvas) {
			//				//					JGraphxCanvas c = (JGraphxCanvas) canvas;
			//				//					c.drawVertex(state);
			//				//				} else {
			//				//					super.drawState(canvas, state, true);
			//				//				}
			//			}

			public boolean isPort(Object cell) {
				mxGeometry geo = getCellGeometry(cell);

				return (geo != null) ? geo.isRelative() : false;
			}

			//			public boolean isCellFoldable(Object cell, boolean collapse) {
			//				return model.isVertex(cell);
			//			}

			public String getToolTipForCell(Object cell) {
				return super.getToolTipForCell(cell);
			}

			public boolean isCellFoldable(Object cell, boolean collapse) {
				return false;
			}
		};

		mxGraphView graphView = new mxGraphView(graph) {
			public void updateFloatingTerminalPoint(mxCellState edge, mxCellState start, mxCellState end, boolean isSource) {
				System.out.println("updateFloatingTerminalPoint=");
				System.out.println(edge);
				System.out.println(start);
				System.out.println(end);
				System.out.println(isSource);
				double y = start.getY();
				boolean left = start.getX() > end.getX();
				double x = (left) ? start.getX() : start.getX() + start.getWidth();
				double x2 = (left) ? start.getX() - 20 : start.getX() + start.getWidth() + 20;
				System.out.println("\t\t" + x + "," + y);
				//				System.out.println("\t\t" + x2 + "," + y);

				//					int index2 = (isSource) ? 1 : edge.getAbsolutePointCount() - 1;
				//					edge.getAbsolutePoints().add(index2, new mxPoint(x2, y));

				int index = (isSource) ? 0 : edge.getAbsolutePointCount() - 1;
				System.out.println("index=" + index);
				edge.setAbsolutePoint(index, new mxPoint(x, y));
				//								super.updateFloatingTerminalPoint(edge, start, end, isSource);
			}
		};

		graph.setView(graphView);

		graphxComponent = new JGraphxComponent(graph);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 900);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		contentPane.add(graphxComponent, BorderLayout.CENTER);

		graph.setCellsResizable(false);
		graph.setCellsEditable(false);
		graph.foldCells(false);
		graph.setGridSize(10);

		graphxComponent.setGridVisible(true);
		graphxComponent.setGridColor(Color.lightGray);
		graphxComponent.setBackground(Color.white);
		graphxComponent.getViewport().setOpaque(false);
		graphxComponent.setBackground(Color.WHITE);
		graphxComponent.setConnectable(false);

		graphxComponent.getGraphHandler().setCloneEnabled(false);
		graphxComponent.getGraphHandler().setImagePreview(false);

		//		graphxComponent.getGraphControl().addMouseListener(new MouseAdapter() {
		//			public void mouseReleased(MouseEvent e) {
		//				mxCell cell = (mxCell) graphxComponent.getCellAt(e.getX(), e.getY());
		//				if (cell == null) {
		//					return;
		//				}
		//				JComponent value = (JComponent) cell.getValue();
		//			}
		//		});
		com.mxgraph.swing.util.mxGraphTransferable.enableImageSupport = false;

		graph.getModel().beginUpdate();
		addCells(graph.getDefaultParent());
		graph.getModel().endUpdate();

		setLocationRelativeTo(null);
	}

	private void addCells(Object parent) {
		MyJComponent b1 = new MyJComponent();
		mxCell node = (mxCell) graph.insertVertex(parent, null, b1, 50, 50, 400, 200);
		mxCell ports[] = addPort(node);

		MyJComponent l = new MyJComponent();

		mxCell node2 = (mxCell) graph.insertVertex(parent, null, l, 700, 50, 400, 200);
		mxCell ports2[] = addPort(node2);

		Object edge = graph.insertEdge(parent, null, "", node, node2, "edgeStyle=entityRelationEdgeStyle;");
		graph.setSelectionCell(edge);

		//		graph.insertEdge(parent, null, "", ports[0], ports2[0], "edgeStyle=entityRelationEdgeStyle;");
		//		graph.insertEdge(parent, null, "", ports[1], ports2[1], "edgeStyle=entityRelationEdgeStyle;");
		//		graph.insertEdge(parent, null, "", ports[2], ports2[2], "edgeStyle=entityRelationEdgeStyle;");

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
