package com.gkd.instrument.newcallgraph;

import java.awt.Component;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.JViewport;

import org.apache.log4j.Logger;

import com.gkd.sourceleveldebugger.SourceLevelDebugger;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxGraphView;

public class CallGraphComponent extends mxGraphComponent {
	public static Logger logger = Logger.getLogger(CallGraphComponent.class);
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
				logger.debug("updateFloatingTerminalPoint=" + edge + "," + start + "," + end + "," + isSource);
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
					logger.debug("\t\t" + x + "," + y);
					//					logger.debug("\t\t" + x2 + "," + y);

					int index2 = (isSource) ? 1 : edge.getAbsolutePointCount() - 1;
					edge.getAbsolutePoints().add(index2, new mxPoint(x2, y));

					int index = (isSource) ? 0 : edge.getAbsolutePointCount() - 1;
					logger.debug("index=" + index);
					edge.setAbsolutePoint(index, new mxPoint(x, y));
				} else {
					//					super.updateFloatingTerminalPoint(edge, start, end, isSource);
				}
			}
		};

		graph.setView(graphView);
	}

	public Component[] createComponents(mxCellState state) {
		logger.debug("components=" + components);
		logger.debug("---- createComponents");
		if (getGraph().getModel().isVertex(state.getCell())) {
			logger.debug(((mxCell) state.getCell()).getAttribute("type"));
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
		logger.debug("components 3=" + components);
		logger.debug("getColumn " + state);
		if (state != null) {
			if (isSource) {
				logger.debug("sourceRow=" + mxUtils.getInt(state.getStyle(), "sourceRow", -1));
				return mxUtils.getInt(state.getStyle(), "sourceRow", -1);
			} else {
				logger.debug("targetRow=" + mxUtils.getInt(state.getStyle(), "targetRow", -1));
				return mxUtils.getInt(state.getStyle(), "targetRow", -1);
			}
		}

		return -1;
	}

	public int getColumnLocation(mxCellState edge, mxCellState terminal, int column) {
		logger.debug("components 2=" + components);
		Component[] c = components.get(terminal.getCell());
		logger.debug("c=" + c);
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

		logger.debug("getColumnLocation=" + y);
		return y;
	}
}
