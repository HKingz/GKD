package com.gkd.structurePanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.gkd.instrument.InstrumentCanvas;
import com.mxgraph.canvas.mxICanvas;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphOutline;
import com.mxgraph.util.mxPoint;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;

public class StructurePanel extends JPanel {
	mxGraph graph;
	StructureGraphComponent graphComponent;
	mxGraphOutline graphOutline;

	public StructurePanel() {
		graph = new mxGraph() {
			public void drawState(mxICanvas canvas, mxCellState state, String label) {
				if (getModel().isVertex(state.getCell()) && canvas instanceof InstrumentCanvas) {
					InstrumentCanvas c = (InstrumentCanvas) canvas;
					c.drawVertex(state, label);
				} else {
					// draw edge, at least
					//					super.drawState(canvas, state, label);
					super.drawState(canvas, state, true);
				}
			}

			// Ports are not used as terminals for edges, they are
			// only used to compute the graphical connection point

			public boolean isPort(Object cell) {
				mxGeometry geo = getCellGeometry(cell);

				return (geo != null) ? geo.isRelative() : false;
			}

			// Implements a tooltip that shows the actual
			// source and target of an edge
			public String getToolTipForCell(Object cell) {
				if (model.isEdge(cell)) {
					return convertValueToString(model.getTerminal(cell, true)) + " -> " + convertValueToString(model.getTerminal(cell, false));
				}

				return super.getToolTipForCell(cell);
			}

			public boolean isCellFoldable(Object cell, boolean collapse) {
				return false;
			}
		};
		graphComponent = new StructureGraphComponent(graph);

		graphComponent.setGridVisible(true);
		graphComponent.setGridColor(Color.lightGray);
		graphComponent.setBackground(Color.white);
		graphComponent.getViewport().setOpaque(false);
		graphComponent.setBackground(Color.WHITE);
		graphComponent.setConnectable(false);
		graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				Object cell = graphComponent.getCellAt(e.getX(), e.getY());

				if (cell != null) {
					String label = graph.getLabel(cell);
					System.out.println(label);
				}
			}
		});

		Object parent = graph.getDefaultParent();
		mxCell node1 = (mxCell) graph.insertVertex(parent, null, "first", 0, 0, 100, 100);
		mxCell ports1[] = addPort(node1);
		mxCell node2 = (mxCell) graph.insertVertex(parent, null, "second", 300, 300, 100, 100);
		mxCell ports2[] = addPort(node2);
		graph.insertEdge(parent, null, "", ports1[0], ports2[0], "edgeStyle=entityRelationEdgeStyle;");

		graph.setCellsResizable(false);
		graph.setCellsMovable(true);
		graph.setCellsEditable(false);
		graph.foldCells(false);
		graph.setGridSize(10);

		removeAll();
		setLayout(new BorderLayout());
		add(graphComponent, BorderLayout.CENTER);

		graphOutline = new mxGraphOutline(graphComponent);
		graphOutline.setBackground(Color.white);
		graphOutline.setBorder(new LineBorder(Color.LIGHT_GRAY));

		add(graphOutline, BorderLayout.WEST);
	}

	private mxCell[] addPort(mxCell node) {
		final int PORT_DIAMETER = 0;
		final int PORT_RADIUS = PORT_DIAMETER / 2;

		mxGeometry geo1 = new mxGeometry(0, 0.5, PORT_DIAMETER, PORT_DIAMETER);
		geo1.setOffset(new mxPoint(-PORT_RADIUS, -PORT_RADIUS));
		geo1.setRelative(true);

		mxCell port1 = new mxCell(null, geo1, "shape=ellipse;perimter=ellipsePerimeter");
		port1.setVertex(true);
		graph.addCell(port1, node);

		mxGeometry geo2 = new mxGeometry(1, 0.5, PORT_DIAMETER, PORT_DIAMETER);
		geo2.setOffset(new mxPoint(-PORT_RADIUS, -PORT_RADIUS));
		geo2.setRelative(true);

		mxCell port2 = new mxCell(null, geo2, "shape=ellipse;perimter=ellipsePerimeter");
		port2.setVertex(true);
		graph.addCell(port2, node);
		return new mxCell[] { port1, port2 };
	}
}
