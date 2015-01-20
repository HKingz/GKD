package com.gkd.instrument.newcallgraph;

import java.awt.Component;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.JViewport;

import com.gkd.jgraphx_example.editor.JTableRenderer;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;

public class CallGraphComponent extends mxGraphComponent {
	float addressPerPixel = (float) 327.68;
	int pixelPerMarker = 100;
	long markerOffset = 0;
	long markerEnd = 1000000;
	public HashMap<String, Vector<String[]>> tableData = new HashMap<String, Vector<String[]>>();

	public CallGraphComponent(mxGraph graph) {
		super(graph);
	}

	@Override
	public Component[] createComponents(mxCellState state) {
		if (getGraph().getModel().isVertex(state.getCell())) {
			System.out.println(((mxCell) state.getCell()).getAttribute("type"));
			String label = state.getLabel();
			if (label.equals("port")) {
				return null;
			}

			UIComponent c = new UIComponent(state.getCell(), this);
			c.titleLabel.setText(label);
			c.model.data = tableData.get(label);
			c.model.fireTableDataChanged();
			return new Component[] { c };
		}

		return null;
	}

	public int getColumn(mxCellState state, boolean isSource) {
		System.out.println("getColumn");
		if (state != null) {
			if (isSource) {
				System.out.println("sourceRow=" + mxUtils.getInt(state.getStyle(), "sourceRow", -1));
				return mxUtils.getInt(state.getStyle(), "sourceRow", -1);
			} else {
				System.out.println("targetRow=" + mxUtils.getInt(state.getStyle(), "targetRow", -1));
				return mxUtils.getInt(state.getStyle(), "targetRow", -1);
			}
		}

		return -1;
	}

	public int getColumnLocation(mxCellState edge, mxCellState terminal, int column) {
		Component[] c = components.get(terminal.getCell());
		int y = 0;

		if (c != null) {
			for (int i = 0; i < c.length; i++) {
				if (c[i] instanceof JTableRenderer) {
					JTableRenderer vertex = (JTableRenderer) c[i];

					JTable table = vertex.table;
					JViewport viewport = (JViewport) table.getParent();
					double dy = -viewport.getViewPosition().getY();
					y = (int) Math.max(terminal.getY() + 22, terminal.getY() + Math.min(terminal.getHeight() - 20, 30 + dy + column * 16));
				}
			}
		}

		System.out.println("getColumnLocation=" + y);
		return y;
	}
}
