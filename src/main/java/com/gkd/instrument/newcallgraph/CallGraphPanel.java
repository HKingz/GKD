package com.gkd.instrument.newcallgraph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

import com.gkd.instrument.callgraph.JmpData;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphOutline;
import com.mxgraph.util.mxPoint;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxGraphView;

public class CallGraphPanel extends JPanel {
	mxGraph graph;
	CallGraphComponent graphxComponent;
	JPanel rightPanel;
	JPanel leftPanel = new JPanel();
	mxGraphOutline graphOutline;

	public CallGraphPanel() {
		setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerLocation(250);
		add(splitPane, BorderLayout.CENTER);

		splitPane.setLeftComponent(leftPanel);

		rightPanel = new JPanel();
		splitPane.setRightComponent(rightPanel);
		rightPanel.setLayout(new BorderLayout(0, 0));

		JPanel toolbarPanel = new JPanel();
		add(toolbarPanel, BorderLayout.NORTH);
		toolbarPanel.setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		toolbarPanel.add(toolBar);

		JButton btnSaveImage = new JButton("Save image");
		btnSaveImage.setIcon(new ImageIcon(CallGraphPanel.class.getResource("/com/gkd/icons/famfam_icons/disk.png")));
		toolBar.add(btnSaveImage);

		initCallGraph();
	}

	private void initCallGraph() {
		graph = new mxGraph() {
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

		mxGraphView graphView = new mxGraphView(graph) {
			public void updateFloatingTerminalPoint(mxCellState edge, mxCellState start, mxCellState end, boolean isSource) {
				//				System.out.println("updateFloatingTerminalPoint=");
				//				System.out.println(edge);
				//				System.out.println(start);
				//				System.out.println(end);
				//				System.out.println(isSource);
				double y = start.getY() + start.getHeight() / 2;

				boolean left = start.getX() > end.getX();
				//								double x = (left) ? start.getX() : start.getX() + start.getWidth();
				//				double x2 = (left) ? start.getX() - 20 : start.getX() + start.getWidth() + 20;
				//				System.out.println("\t\t" + x2 + "," + y);

				//					int index2 = (isSource) ? 1 : edge.getAbsolutePointCount() - 1;
				//					edge.getAbsolutePoints().add(index2, new mxPoint(x2, y));

				mxCell cell = (mxCell) start.getCell();
				MyJComponent c = (MyJComponent) cell.getValue();

				//				int index = (isSource) ? 0 : edge.getAbsolutePointCount() - 1;
				//				System.out.println("y=" + y);
				//				if (isSource) {
				//					try {
				//						//x = c.label2.getLocationOnScreen().x;
				//						//						x = start.getX() + start.getWidth();
				//						y = c.label2.getLocation().y;
				//						System.out.println("y2=" + y);
				//					} catch (Exception ex) {
				//
				//					}
				//					System.out.println("\t\t >> " + x + "," + y);
				//					edge.setAbsolutePoint(index, new mxPoint(x, y));
				//				} else {
				//					System.out.println("\t\t" + x + "," + y);
				//					edge.setAbsolutePoint(index, new mxPoint(x, y));
				//				}

				double x = (left) ? start.getX() : start.getX() + start.getWidth();
				double x2 = (left) ? start.getX() - 20 : start.getX() + start.getWidth() + 20;
				System.out.println("\t\t" + x + "," + y);
				//					System.out.println("\t\t" + x2 + "," + y);

				int index2 = (isSource) ? 1 : edge.getAbsolutePointCount() - 1;
				edge.getAbsolutePoints().add(index2, new mxPoint(x2, y));

				int index = (isSource) ? 0 : edge.getAbsolutePointCount() - 1;
				System.out.println("index=" + index);
				edge.setAbsolutePoint(index, new mxPoint(x, y));

				//								super.updateFloatingTerminalPoint(edge, start, end, isSource);
			}
		};

		graph.setView(graphView);

		graphxComponent = new CallGraphComponent(graph);
		leftPanel.setLayout(new BorderLayout(0, 0));
		graphOutline = new mxGraphOutline(graphxComponent);

		leftPanel.add(graphOutline);

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

		rightPanel.add(graphxComponent, BorderLayout.CENTER);

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

	public void initGraph(Vector<JmpData> jmpData, int noOfInstruction) {
		graph.getModel().beginUpdate();
		addCells(graph.getDefaultParent());
		graph.getModel().endUpdate();
	}

}
