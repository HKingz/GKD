package com.gkd.instrument.newcallgraph;

import java.awt.Component;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;

public class CallGraphComponent extends mxGraphComponent {
	float addressPerPixel = (float) 327.68;
	int pixelPerMarker = 100;
	long markerOffset = 0;
	long markerEnd = 1000000;

	public CallGraphComponent(mxGraph graph) {
		super(graph);
	}

	//	@Override
	//	public mxInteractiveCanvas createCanvas() {
	//		return new JGraphxCanvas(this);
	//	}

	// public Component[] createComponents(mxCellState state) {
	// if (getGraph().getModel().isVertex(state.getCell())) {
	// System.out.println(state.getCell());
	// // return new Component[] { new JTableRenderer(state.getCell(),
	// // this) };
	// return new Component[] { new JButton(((mxCell)
	// state.getCell()).getValue().toString()) };
	// }
	// return null;
	// }

	//	@Override
	//	protected void paintBackground(Graphics g) {
	//		double scale = this.getGraph().getView().getScale();
	//		super.paintGrid(g);
	//
	//		if (scale >= 0.5) {
	//			Graphics2D g2 = (Graphics2D) g;
	//			FontMetrics fm = g2.getFontMetrics();
	//
	//			int minX = (int) (50 * scale);
	//			int minY = (int) (20 * scale);
	//			int tricker = 10;
	//
	//			g2.setColor(Color.darkGray);
	//
	//			// address line
	//			int actualWidth = this.getViewport().getComponent(0).getWidth();
	//			int actualHeight = this.getViewport().getComponent(0).getHeight();
	//			g2.drawLine(0, minY, actualWidth, minY);
	//			int wordWidth = fm.stringWidth("This is JGraphxComponent");
	//			g2.drawString("This is JGraphxComponent", minX, minY - 10);
	//
	//			int width = (int) ((markerEnd - markerOffset) / addressPerPixel);
	//			for (int x = 0; x <= width; x += pixelPerMarker) {
	//				float positionX = x;
	//				float scaledPositionX = (float) (positionX * scale);
	//				scaledPositionX += minX;
	//				g2.drawLine((int) scaledPositionX, (int) (minY - (tricker * scale)), (int) scaledPositionX, (int) (minY + (tricker * scale)));
	//				g2.drawString("0x" + Long.toHexString((long) (x * addressPerPixel) + markerOffset), (int) scaledPositionX, minY);
	//			}
	//			// end address line
	//
	//		}
	//	}

	@Override
	public Component[] createComponents(mxCellState state) {
		//		if (getGraph().getModel().isVertex(state.getCell())) {
		//			mxCell cell = (mxCell) state.getCell();
		//			JComponent value = (JComponent) cell.getValue();
		//			if (value == null) {
		//				return null;
		//			} else {
		//				return new Component[] { value };
		//			}
		//		}
		if (getGraph().getModel().isVertex(state.getCell())) {
			String label = state.getLabel();
			return new Component[] { new UIComponent(state.getCell(), this) };
		}

		return null;
	}
}
