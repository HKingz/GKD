package com.gkd.instrument.newcallgraph;

import java.awt.Component;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.JViewport;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxGraphView;

public class CallGraphComponent extends mxGraphComponent {
	public HashMap<String, Vector<String[]>> tableData = new HashMap<String, Vector<String[]>>();

	public CallGraphComponent(mxGraph graph) {
		super(graph);

		mxGraphView graphView = new mxGraphView(graph) {
			public void updateFloatingTerminalPoint_old(mxCellState edge, mxCellState start, mxCellState end, boolean isSource) {
				double y = start.getY() + start.getHeight() / 2;

				boolean left = start.getX() > end.getX();
				mxCell cell = (mxCell) start.getCell();
				UIComponent c = (UIComponent) cell.getValue();

				double x = (left) ? start.getX() : start.getX() + start.getWidth();
				double x2 = (left) ? start.getX() - 20 : start.getX() + start.getWidth() + 20;

				int index2 = (isSource) ? 1 : edge.getAbsolutePointCount() - 1;
				edge.getAbsolutePoints().add(index2, new mxPoint(x2, y));

				int index = (isSource) ? 0 : edge.getAbsolutePointCount() - 1;
				edge.setAbsolutePoint(index, new mxPoint(x, y));
			}

			public void updateFloatingTerminalPoint(mxCellState edge, mxCellState start, mxCellState end, boolean isSource) {
				System.out.println("updateFloatingTerminalPoint=" + edge + "," + start + "," + end + "," + isSource);
				int col = getColumn(edge, isSource);

				if (col >= 0) {
					double y = getColumnLocation(edge, start, col);
					boolean left = start.getX() > end.getX();

					if (isSource) {
						double diff = Math.abs(start.getCenterX() - end.getCenterX()) - start.getWidth() / 2 - end.getWidth() / 2;

						if (diff < 40) {
							left = !left;
						}
					}

					double x = (left) ? start.getX() : start.getX() + start.getWidth();
					double x2 = (left) ? start.getX() - 20 : start.getX() + start.getWidth() + 20;
					System.out.println("\t\t" + x + "," + y);
					//					System.out.println("\t\t" + x2 + "," + y);

					int index2 = (isSource) ? 1 : edge.getAbsolutePointCount() - 1;
					edge.getAbsolutePoints().add(index2, new mxPoint(x2, y));

					int index = (isSource) ? 0 : edge.getAbsolutePointCount() - 1;
					System.out.println("index=" + index);
					edge.setAbsolutePoint(index, new mxPoint(x, y));
				} else {
					//					super.updateFloatingTerminalPoint(edge, start, end, isSource);
				}
			}
		};

		graph.setView(graphView);
	}

	public Component[] createComponents(mxCellState state) {
		System.out.println("components=" + components);
		System.out.println("---- createComponents");
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
		System.out.println("components 3=" + components);
		System.out.println("getColumn " + state);
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
		System.out.println("components 2=" + components);
		Component[] c = components.get(terminal.getCell());
		System.out.println("c=" + c);
		int y = 0;

		if (c != null) {
			for (int i = 0; i < c.length; i++) {
				if (c[i] instanceof UIComponent) {
					UIComponent vertex = (UIComponent) c[i];

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
