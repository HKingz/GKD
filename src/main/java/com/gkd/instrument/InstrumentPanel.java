package com.gkd.instrument;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.LookupPaintScale;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYZDataset;
import org.jzy3d.chart.Chart;
import org.jzy3d.chart.controllers.mouse.ChartMouseController;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.primitives.AbstractDrawable;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

import com.gkd.MyLanguage;
import com.gkd.RegisterPanel;
import com.gkd.Setting;
import com.gkd.TSSPanel;
import com.gkd.instrument.callgraph.CallGraphConfigTableCellEditor;
import com.gkd.instrument.callgraph.CallGraphConfigTableCellRenderer;
import com.gkd.instrument.callgraph.CallGraphConfigTableModel;
import com.gkd.instrument.callgraph.CallGraphRawTableModel;
import com.gkd.instrument.callgraph.JmpData;
import com.gkd.instrument.jfreechart.MyXYBlockRenderer;
import com.gkd.instrument.jfreechart.MyXYToolTipGenerator;
import com.mxgraph.canvas.mxICanvas;
import com.mxgraph.io.mxCodec;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxOrganicLayout;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.mxGraphOutline;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxResources;
import com.mxgraph.util.mxUtils;
import com.mxgraph.util.png.mxPngEncodeParam;
import com.mxgraph.util.png.mxPngImageEncoder;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;
import com.peterswing.CommonLib;
import com.peterswing.advancedswing.combo_color_renderer.ComboBoxRenderer;
import com.peterswing.advancedswing.searchtextfield.JSearchTextField;

public class InstrumentPanel extends JPanel implements ChartChangeListener, ChartMouseListener {
	private JTabbedPane tabbedPane1;
	private JPanel memoryPanel;
	private JButton clearInterruptButton;
	private JButton invisible3dChartButton;
	private JPanel panel15;
	private JPanel panel14;
	private JCheckBox sortCheckBox;
	private JTextArea textArea1;
	private JLabel label17;
	private JScrollPane jScrollPane6;
	private JComboBox chartGirdColorComboBox;
	private JLabel jLabel16;
	private JComboBox chartBackgroundComboBox;
	private JLabel jLabel15;
	private JComboBox speedComboBox;
	private JLabel jLabel14;
	private JComboBox timeframeComboBox;
	private JLabel jLabel13;
	private JPanel jPanel13;
	private JPanel jPanel12;
	private JSplitPane jSplitPane1;
	private JTable interruptTable;
	private JScrollPane interruptTableScrollPane;
	private ChartPanel jInterruptChart;
	private JPanel interruptChartPanel;
	private JTabbedPane interruptTabbedPane;
	private JPanel jPanel11;
	private JLabel jLabel12;
	private JLabel jLabel11;
	private JScrollPane jScrollPane5;
	private RegisterPanel callGraphRegisterPanel;
	private JPanel jPanel10;
	private JPanel jPanel9;
	private JPanel jPanel8;
	private JPanel jPanel7;
	private JPanel jPanel6;
	private JTabbedPane tabbedPane4;
	private JTextField callGraphScaleTextField;
	private JComboBox trackDistanceComboBox;
	private JComboBox trackUnitComboBox;
	private JButton callGraphZoomOutButton;
	private JButton callGraphZoomInButton;
	private JButton deleteButton;
	private JButton addCallGraphButton;
	private JPanel jPanel5;
	private JTable callGraphConfigTable;
	private JScrollPane jScrollPane4;
	private JPanel callGraphConfigPanel;
	private JTextField segmentToTextField;
	private JTextField segmentFromTextField;
	private JTextField segmentEndTextField;
	private JTextField segmentStartTextField;
	private JPanel jPanel4;
	private JLabel segmentToLabel;
	private JLabel segmentFromLabel;
	private JLabel segmentEndLabel;
	private JLabel segmentStartLabel;
	private JPanel callGraphPreviewPanel;
	private JSplitPane callGraphSplitPane;
	private JPanel callGraphDetailPanel;
	private JPanel jPanel3;
	private JTable callGraphRawTable;
	private JScrollPane jScrollPane1;
	private JPanel callGraphTablePanel;
	private JTabbedPane jTabbedPane3;
	private JButton layoutHierarchicalButton;
	private JButton layoutOrganicButton;
	private JButton layoutCircleButton;
	private JButton layoutTreeButton;
	private JButton refreshCallGraphButton;
	private JButton saveGraphButton;
	private JToolBar toolBar1;
	private JPanel callGraphPanel;
	private JTable table1;
	private JCheckBox groupCheckBox;
	private JLabel jLabel10;
	private JComboBox noOfLineComboBox;
	private JPanel jPanel2;
	private JScrollPane jScrollPane3;
	private JPanel jmpPanel;
	private JButton deleteZoneButton;
	private JButton addZoneButton;
	private JScrollPane jScrollPane2;
	private JTable profilingTable;
	private JComboBox jProfilingToComboBox;
	private JLabel jLabel9;
	private JComboBox jProfilingFromComboBox;
	private JLabel jLabel8;
	private JLabel jLabel7;
	private JPanel jMemoryProfilingPanel;
	private JTabbedPane jTabbedPane2;
	private JLabel jLabel4;
	public static JComboBox fromComboBox;
	private JButton zoomInButton;
	private JButton zoomOutButton;
	private ChartPanel memoryChartPanel;
	private JFreeChart jfcMemory;
	private JPanel jMemory3DPanel;
	private JButton zoomOutAutoRangeButton;
	private JLabel jRWCountLabel;
	private JLabel jAddressLabel;
	private JSearchTextField searchTextField;
	private JLabel jLabel3;
	private JLabel jLabel2;
	private JTable hotestAddressTable;
	private JScrollPane hostestAddressScrollPane;
	private JLabel jLabel1;
	public static JComboBox blockSizeComboBox;
	private JLabel jLabel5;
	public static JComboBox toComboBox;
	private JLabel jLabel6;
	private JPanel jPanel1;
	Color background = new Color(250, 250, 250);
	public static Chart memory3dChart;
	JmpTableModel jmpTableModel = new JmpTableModel();
	mxGraph graph;
	CallGraphComponent graphComponent;
	CallGraphConfigTableModel callGraphConfigTableModel = new CallGraphConfigTableModel();
	mxGraphOutline graphOutline;
	private final int MAX_NUMBER_OF_VERTEX = 100;
	JProgressBar jStatusProgressBar;
	JLabel jStatusLabel;
	CallGraphRawTableModel callGraphRawTableModel = new CallGraphRawTableModel();
	JFreeChart interruptChart;
	TimeSeriesCollection interruptDataset;
	Timer interruptTimer;

	public InstrumentPanel() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			setLayout(thisLayout);
			setPreferredSize(new java.awt.Dimension(870, 609));
			{
				tabbedPane1 = new JTabbedPane();
				add(tabbedPane1, BorderLayout.CENTER);
				tabbedPane1.setTabPlacement(JTabbedPane.LEFT);
				tabbedPane1.setPreferredSize(new java.awt.Dimension(661, 419));
				{
					memoryPanel = new JPanel();
					GroupLayout jMemoryPanelLayout = new GroupLayout((JComponent) memoryPanel);
					memoryPanel.setLayout(jMemoryPanelLayout);
					tabbedPane1.addTab("Memory", null, memoryPanel, null);
					tabbedPane1.addTab("Profiling", null, getJMemoryProfilingPanel(), null);
					tabbedPane1.addTab("Jmp", null, getJmpPanel(), null);
					tabbedPane1.addTab("Call graph", null, getJTabbedPane3(), null);
					tabbedPane1.addTab("Interrupt", null, getJPanel11(), null);
					jMemoryPanelLayout
							.setVerticalGroup(jMemoryPanelLayout
									.createSequentialGroup()
									.addContainerGap()
									.addGroup(
											jMemoryPanelLayout
													.createParallelGroup(GroupLayout.Alignment.BASELINE)
													.addComponent(getFromComboBox(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
													.addComponent(getJLabel4x(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
															GroupLayout.PREFERRED_SIZE)
													.addComponent(getJLabel6(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
															GroupLayout.PREFERRED_SIZE)
													.addComponent(getToComboBox(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
													.addComponent(getJLabel5x(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
															GroupLayout.PREFERRED_SIZE)
													.addComponent(getBlockSizeComboBox(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 24,
															GroupLayout.PREFERRED_SIZE)
													.addComponent(getJLabel3(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
															GroupLayout.PREFERRED_SIZE)
													.addComponent(getSearchTextField(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(getJTabbedPane2(), 0, 224, Short.MAX_VALUE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(
											jMemoryPanelLayout
													.createParallelGroup(GroupLayout.Alignment.BASELINE)
													.addComponent(getJLabel1(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
															GroupLayout.PREFERRED_SIZE)
													.addComponent(getJLabel2(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
															GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(
											jMemoryPanelLayout
													.createParallelGroup()
													.addComponent(getHostestAddressScrollPane(), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 110,
															GroupLayout.PREFERRED_SIZE)
													.addGroup(
															GroupLayout.Alignment.LEADING,
															jMemoryPanelLayout.createSequentialGroup()
																	.addComponent(getJLabel4(), GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
																	.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
																	.addComponent(getJLabel5(), GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
																	.addGap(74))).addComponent(getJPanel1(), GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
									.addContainerGap());
					jMemoryPanelLayout.linkSize(SwingConstants.VERTICAL, new Component[] { getSearchTextField(), getFromComboBox(), getToComboBox(), getBlockSizeComboBox() });
					jMemoryPanelLayout
							.setHorizontalGroup(jMemoryPanelLayout
									.createSequentialGroup()
									.addContainerGap()
									.addGroup(
											jMemoryPanelLayout
													.createParallelGroup()
													.addGroup(
															jMemoryPanelLayout
																	.createSequentialGroup()
																	.addGroup(
																			jMemoryPanelLayout
																					.createParallelGroup()
																					.addGroup(
																							GroupLayout.Alignment.LEADING,
																							jMemoryPanelLayout
																									.createSequentialGroup()
																									.addComponent(getJLabel3(), GroupLayout.PREFERRED_SIZE, 70,
																											GroupLayout.PREFERRED_SIZE)
																									.addComponent(getSearchTextField(), GroupLayout.PREFERRED_SIZE, 151,
																											GroupLayout.PREFERRED_SIZE))
																					.addGroup(
																							GroupLayout.Alignment.LEADING,
																							jMemoryPanelLayout
																									.createSequentialGroup()
																									.addComponent(getJLabel2(), GroupLayout.PREFERRED_SIZE, 96,
																											GroupLayout.PREFERRED_SIZE).addGap(125))
																					.addGroup(
																							GroupLayout.Alignment.LEADING,
																							jMemoryPanelLayout
																									.createSequentialGroup()
																									.addComponent(getJPanel1(), GroupLayout.PREFERRED_SIZE, 182,
																											GroupLayout.PREFERRED_SIZE).addGap(39))
																					.addGroup(
																							jMemoryPanelLayout
																									.createSequentialGroup()
																									.addPreferredGap(getJLabel2(), getJLabel4(),
																											LayoutStyle.ComponentPlacement.INDENT)
																									.addGroup(
																											jMemoryPanelLayout
																													.createParallelGroup()
																													.addComponent(getJLabel4(), GroupLayout.Alignment.LEADING,
																															GroupLayout.PREFERRED_SIZE, 160,
																															GroupLayout.PREFERRED_SIZE)
																													.addComponent(getJLabel5(), GroupLayout.Alignment.LEADING,
																															GroupLayout.PREFERRED_SIZE, 160,
																															GroupLayout.PREFERRED_SIZE)).addGap(49)))
																	.addGroup(
																			jMemoryPanelLayout
																					.createParallelGroup()
																					.addComponent(getHostestAddressScrollPane(), GroupLayout.Alignment.LEADING, 0, 498,
																							Short.MAX_VALUE)
																					.addGroup(
																							GroupLayout.Alignment.LEADING,
																							jMemoryPanelLayout
																									.createSequentialGroup()
																									.addGroup(
																											jMemoryPanelLayout
																													.createParallelGroup()
																													.addGroup(
																															GroupLayout.Alignment.LEADING,
																															jMemoryPanelLayout
																																	.createSequentialGroup()
																																	.addPreferredGap(getJLabel1(), getJLabel4x(),
																																			LayoutStyle.ComponentPlacement.INDENT)
																																	.addComponent(getJLabel4x(),
																																			GroupLayout.PREFERRED_SIZE,
																																			GroupLayout.PREFERRED_SIZE,
																																			GroupLayout.PREFERRED_SIZE)
																																	.addPreferredGap(
																																			LayoutStyle.ComponentPlacement.RELATED)
																																	.addComponent(getFromComboBox(),
																																			GroupLayout.PREFERRED_SIZE, 139,
																																			GroupLayout.PREFERRED_SIZE))
																													.addGroup(
																															GroupLayout.Alignment.LEADING,
																															jMemoryPanelLayout
																																	.createSequentialGroup()
																																	.addComponent(getJLabel1(),
																																			GroupLayout.PREFERRED_SIZE, 112,
																																			GroupLayout.PREFERRED_SIZE).addGap(80)))
																									.addComponent(getJLabel6(), GroupLayout.PREFERRED_SIZE, 21,
																											GroupLayout.PREFERRED_SIZE)
																									.addComponent(getToComboBox(), GroupLayout.PREFERRED_SIZE, 120,
																											GroupLayout.PREFERRED_SIZE)
																									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 0,
																											GroupLayout.PREFERRED_SIZE)
																									.addComponent(getJLabel5x(), GroupLayout.PREFERRED_SIZE, 69,
																											GroupLayout.PREFERRED_SIZE)
																									.addComponent(getBlockSizeComboBox(), GroupLayout.PREFERRED_SIZE, 75,
																											GroupLayout.PREFERRED_SIZE).addGap(0, 9, Short.MAX_VALUE))))
													.addComponent(getJTabbedPane2(), GroupLayout.Alignment.LEADING, 0, 719, Short.MAX_VALUE)).addContainerGap());
					jMemoryPanelLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] { getFromComboBox(), getToComboBox() });
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ToolTipManager.sharedInstance().setInitialDelay(0);
	}

	private static JFreeChart createChart(XYZDataset dataset) {
		NumberAxis xAxis = new NumberAxis("address (" + blockSizeComboBox.getSelectedItem() + ")");
		xAxis.setLowerMargin(0.0);
		xAxis.setUpperMargin(0.0);
		xAxis.setAxisLinePaint(Color.cyan);
		xAxis.setTickMarkPaint(Color.cyan);
		// xAxis.setAutoTickUnitSelection(false);
		// xAxis.setTickUnit(new NumberTickUnit(1));
		// NumberFormat nf = NumberFormat.getPercentInstance();
		// xAxis.setNumberFormatOverride(nf);
		// xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		NumberAxis yAxis = new NumberAxis("address (" + blockSizeComboBox.getSelectedItem() + " KB)");
		// yAxis.setAutoRangeIncludesZero(true);
		// yAxis.setInverted(false);
		yAxis.setLowerMargin(0.0);
		yAxis.setUpperMargin(0.0);
		yAxis.setAxisLinePaint(Color.pink);
		yAxis.setTickMarkPaint(Color.pink);
		// yAxis.setTickUnit(new NumberTickUnit(10));
		// yAxis.setTickLabelFont(new Font("Dialog", 0, 7));
		// yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		MyXYBlockRenderer renderer = new MyXYBlockRenderer();
		XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);

		// plot.setBackgroundPaint(Color.white);
		// plot.setDomainGridlineStroke(new BasicStroke(1));
		plot.setDomainGridlinePaint(Color.white);

		// plot.setRangeGridlineStroke(new BasicStroke(1));
		plot.setRangeGridlinePaint(Color.white);

		// JFreeChart chart = new JFreeChart("Memory read/write hot zone", new
		// Font("Serif", Font.PLAIN, 12), plot, true);
		JFreeChart chart = new JFreeChart("Memory read/write hot zone", plot);
		chart.removeLegend();
		chart.setBackgroundPaint(Color.white);

		return chart;
	}

	private static JFreeChart createEmptyChart(XYZDataset dataset) {
		NumberAxis xAxis = new NumberAxis("address");
		xAxis.setLowerMargin(0.0);
		xAxis.setUpperMargin(0.0);
		NumberAxis yAxis = new NumberAxis("address");
		yAxis.setAutoRangeIncludesZero(false);
		yAxis.setInverted(false);
		yAxis.setLowerMargin(0.0);
		yAxis.setUpperMargin(0.0);
		// yAxis.setAxisLinePaint(Color.pink);
		// yAxis.setTickMarkPaint(Color.white);
		yAxis.setTickLabelFont(new Font("Dialog", 0, 7));
		yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		MyXYBlockRenderer renderer = new MyXYBlockRenderer();
		XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);

		// plot.setBackgroundPaint(Color.white);
		// plot.setDomainGridlineStroke(new BasicStroke(1));
		plot.setDomainGridlinePaint(Color.white);

		// plot.setRangeGridlineStroke(new BasicStroke(1));
		plot.setRangeGridlinePaint(Color.white);

		JFreeChart chart = new JFreeChart("Memory read/write hot zone", new Font("Serif", Font.PLAIN, 12), plot, true);
		chart.removeLegend();
		chart.setBackgroundPaint(Color.white);

		return chart;
	}

	private static XYZDataset createEmptyDataset() {
		DefaultXYZDataset dataset = new DefaultXYZDataset();
		return dataset;
	}

	private int findLargest(int data[]) {
		int largest = Integer.MIN_VALUE;
		for (int x = 0; x < data.length; x++) {
			if (data[x] > largest) {
				largest = data[x];
			}
		}
		return largest;
	}

	private static XYZDataset createDataset() {
		long rowCount = Data.getRowCount(CommonLib.convertFilesize((String) fromComboBox.getSelectedItem()), CommonLib.convertFilesize((String) toComboBox.getSelectedItem()),
				CommonLib.convertFilesize((String) blockSizeComboBox.getSelectedItem()));
		long columnCount = Data.getColumnCount(CommonLib.convertFilesize((String) fromComboBox.getSelectedItem()),
				CommonLib.convertFilesize((String) toComboBox.getSelectedItem()), CommonLib.convertFilesize((String) blockSizeComboBox.getSelectedItem()));
		double[] xvalues = new double[(int) (columnCount * rowCount)];
		double[] yvalues = new double[(int) (columnCount * rowCount)];
		double[] zvalues = new double[(int) (columnCount * rowCount)];
		double[][] data = new double[][] { xvalues, yvalues, zvalues };

		// set the default z-value to zero throughout the data array.
		for (int y = 0; y < rowCount; y++) {
			for (int x = 0; x < columnCount; x++) {
				setValue(data, x, y, 0.0);
			}
		}

		int dataB[] = Data.getChartData(CommonLib.convertFilesize((String) fromComboBox.getSelectedItem()), CommonLib.convertFilesize((String) toComboBox.getSelectedItem()),
				CommonLib.convertFilesize((String) blockSizeComboBox.getSelectedItem()));
		for (int index = 0; index < dataB.length; index++) {
			int y = (int) (index / columnCount);
			int x = (int) (index - (y * columnCount));
			setValue(data, x, y, dataB[index]);
		}

		DefaultXYZDataset dataset = new DefaultXYZDataset();
		dataset.addSeries("read/write count", data);
		return dataset;
	}

	private static void setValue(double[][] data, int x, int y, double value) {
		long columnCount = Data.getColumnCount(CommonLib.convertFilesize((String) fromComboBox.getSelectedItem()),
				CommonLib.convertFilesize((String) toComboBox.getSelectedItem()), CommonLib.convertFilesize((String) blockSizeComboBox.getSelectedItem()));
		data[0][(int) (y * columnCount + x)] = x;
		data[1][(int) (y * columnCount + x)] = y;
		data[2][(int) (y * columnCount + x)] = value;
	}

	public void updateChart() {
		update2DChart();
		// update3DChart();
	}

	public void update2DChart() {
		// jfcMemory.getCategoryPlot().setDataset(createMemoryDataset());
		jfcMemory.getXYPlot().setDataset(createDataset());
		MyXYBlockRenderer renderer = (MyXYBlockRenderer) jfcMemory.getXYPlot().getRenderer();
		int largest = findLargest(Data.getChartData(CommonLib.convertFilesize((String) fromComboBox.getSelectedItem()),
				CommonLib.convertFilesize((String) toComboBox.getSelectedItem()), CommonLib.convertFilesize((String) blockSizeComboBox.getSelectedItem())));
		if (largest == 0) {
			largest = 1;
		}
		LookupPaintScale paintScale = new LookupPaintScale(0, largest, background);
		if (largest > 1) {
			// int mean =
			// medianWithoutZero(Data.getChartData(CommonLib.convertFilesize((String)
			// jFromComboBox.getSelectedItem()),
			// CommonLib.convertFilesize((String) jToComboBox
			// .getSelectedItem())));
			int m[] = Data.getChartData(CommonLib.convertFilesize((String) fromComboBox.getSelectedItem()), CommonLib.convertFilesize((String) toComboBox.getSelectedItem()),
					CommonLib.convertFilesize((String) blockSizeComboBox.getSelectedItem()));
			TreeSet<Integer> data = new TreeSet<Integer>();
			for (int x = 0; x < m.length; x++) {
				if (m[x] > 0) {
					data.add(m[x]);
				}
			}

			// paintScale.add(0, Color.white);
			ArrayList<Color> allColors = allColors();
			Object iData[] = data.toArray();
			paintScale.add(1, allColors.get(0));
			for (int x = 1; x < iData.length - 1; x++) {
				paintScale.add((int) (Integer) iData[x], allColors.get(allColors.size() / iData.length * x));
			}
			paintScale.add((int) (Integer) iData[iData.length - 1], allColors.get(allColors.size() - 1));
		}
		renderer.setPaintScale(paintScale);
		renderer.setBaseToolTipGenerator(new MyXYToolTipGenerator());
		jfcMemory.getXYPlot().setForegroundAlpha(1f);
		zoomOutAutoRangeButtonActionPerformed(null);
	}

	public void update3DChart() {
		try {
			long rowCount = Data.getRowCount(CommonLib.convertFilesize((String) fromComboBox.getSelectedItem()), CommonLib.convertFilesize((String) toComboBox.getSelectedItem()),
					CommonLib.convertFilesize((String) blockSizeComboBox.getSelectedItem()));
			long columnCount = Data.getColumnCount(CommonLib.convertFilesize((String) fromComboBox.getSelectedItem()),
					CommonLib.convertFilesize((String) toComboBox.getSelectedItem()), CommonLib.convertFilesize((String) blockSizeComboBox.getSelectedItem()));

			List<Coord3d> coords = new ArrayList<Coord3d>();

			int dataB[] = Data.getChartData(CommonLib.convertFilesize((String) fromComboBox.getSelectedItem()), CommonLib.convertFilesize((String) toComboBox.getSelectedItem()),
					CommonLib.convertFilesize((String) blockSizeComboBox.getSelectedItem()));

			// Random r = new Random();
			for (int x = 0; x < columnCount; x++) {
				for (int y = 0; y < rowCount; y++) {
					int index = (int) (x + y * columnCount);
					if (index < dataB.length) {
						coords.add(new Coord3d(x, y, dataB[index]));
					} else {
						coords.add(new Coord3d(x, y, 0));
					}
					// coords.add(new Coord3d(x, y, r.nextInt(100)));
				}
			}

			// Create the object to represent the function over the given range.
			final Shape surface = (Shape) Builder.buildDelaunay(coords);
			surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new org.jzy3d.colors.Color(1, 1, 1, .5f)));
			surface.setFaceDisplayed(true);
			surface.setWireframeDisplayed(true);
			surface.setWireframeColor(org.jzy3d.colors.Color.BLACK);

			for (Iterator<AbstractDrawable> it = memory3dChart.getScene().getGraph().getAll().iterator(); it.hasNext();) {
				it.next();
				it.remove();
			}
			memory3dChart.getScene().getGraph().add(surface);
		} catch (Exception ex) {
		}
	}

	public ArrayList<Color> allColors() {
		ArrayList<Color> allColors = new ArrayList<Color>();
		for (int b = 0; b <= 255; b++) {
			allColors.add(new Color(b, 0, 255 - b));
		}
		// for (int b = 0; b <= 255; b++) {
		// allColors.add(new Color(0, 255, b));
		// }
		// for (int g = 255; g >= 0; g--) {
		// allColors.add(new Color(255, g, 255));
		// }
		// for (int r = 0; r <= 255; r++) {
		// allColors.add(new Color(r, 0, 255));
		// }
		// for (int b = 255; b >= 0; b--) {
		// allColors.add(new Color(255, 0, b));
		// }
		return allColors;
	}

	public static int median(int[] m) {
		int middle = m.length / 2; // subscript of middle element
		if (m.length % 2 == 1) {
			// Odd number of elements -- return the middle one.
			return m[middle];
		} else {
			// Even number -- return average of middle two
			// Must cast the numbers to double before dividing.
			return (m[middle - 1] + m[middle]) / 2;
		}
	}

	public static int medianWithoutZero(int[] m) {
		ArrayList<Integer> data = new ArrayList<Integer>();
		for (int x = 0; x < m.length; x++) {
			if (m[x] > 0) {
				data.add(m[x]);
			}
		}
		int middle = data.size() / 2; // subscript of middle element
		if (data.size() % 2 == 1) {
			// Odd number of elements -- return the middle one.
			return data.get(middle);
		} else {
			// Even number -- return average of middle two
			// Must cast the numbers to double before dividing.
			return (data.get(middle - 1) + data.get(middle)) / 2;
		}
	}

	private JPanel getJMemory3DPanel() {
		if (jMemory3DPanel == null) {
			jMemory3DPanel = new JPanel();
			GroupLayout jMemory3DPanelLayout = new GroupLayout((JComponent) jMemory3DPanel);
			jMemory3DPanel.setLayout(jMemory3DPanelLayout);
			jMemory3DPanel.setPreferredSize(new java.awt.Dimension(890, 242));
			jMemory3DPanel.setVisible(false);

			try {
				memory3dChart = createEmptyMemory3DChart();
				ChartMouseController mouse = new ChartMouseController();
				// mouse.addControllerEventListener(new
				// ControllerEventListener() {
				//
				// public void controllerEventFired(ControllerEvent e) {
				// if (e.getType() == ControllerType.ROTATE) {
				// // System.out.println("Mouse[ROTATE]:" +
				// // (Coord3d)e.getValue());
				//
				// }
				//
				// }
				//
				// });
				memory3dChart.addController(mouse);

				jMemory3DPanelLayout.setHorizontalGroup(jMemory3DPanelLayout.createSequentialGroup().addContainerGap()
						.addComponent((Component) memory3dChart.getCanvas(), 0, 220, Short.MAX_VALUE).addContainerGap());
				jMemory3DPanelLayout.setVerticalGroup(jMemory3DPanelLayout.createSequentialGroup().addContainerGap()
						.addComponent((Component) memory3dChart.getCanvas(), 0, 116, Short.MAX_VALUE).addContainerGap());

				// GLCapabilities glCapabilities = new GLCapabilities();
				// glCapabilities.setHardwareAccelerated(true);

			} catch (UnsatisfiedLinkError e) {
				jMemory3DPanel = new JPanel();
				jMemory3DPanel.add(new JLabel("Error : no gluegen-rt in java.library.path, to fix it, please add -Djava.library.path=<directory that contains libgluegen-rt.so>"));
			}
		}
		return jMemory3DPanel;
	}

	public static Chart createEmptyMemory3DChart() {
		memory3dChart = new Chart(Quality.Fastest);
		return memory3dChart;
	}

	private JButton getZoomOutButton() {
		if (zoomOutButton == null) {
			zoomOutButton = new JButton();
			zoomOutButton.setText("out");
			zoomOutButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/zoom_out.png")));
			zoomOutButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					zoomOutButtonActionPerformed(evt);
				}
			});
		}
		return zoomOutButton;
	}

	private void zoomOutButtonActionPerformed(ActionEvent evt) {
		memoryChartPanel.zoomOutBoth(2, 2);
	}

	private JButton getZoomInButton() {
		if (zoomInButton == null) {
			zoomInButton = new JButton();
			zoomInButton.setText("in");
			zoomInButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/zoom_in.png")));
			zoomInButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					zoomInButtonActionPerformed(evt);
				}
			});
		}
		return zoomInButton;
	}

	private void zoomInButtonActionPerformed(ActionEvent evt) {
		memoryChartPanel.zoomInBoth(2, 2);
	}

	private JButton getZoomOutAutoRangeButton() {
		if (zoomOutAutoRangeButton == null) {
			zoomOutAutoRangeButton = new JButton();
			zoomOutAutoRangeButton.setText("fit");
			zoomOutAutoRangeButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/zoom.png")));
			zoomOutAutoRangeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					zoomOutAutoRangeButtonActionPerformed(evt);
				}
			});
		}
		return zoomOutAutoRangeButton;
	}

	private void zoomOutAutoRangeButtonActionPerformed(ActionEvent evt) {
		memoryChartPanel.restoreAutoBounds();
	}

	@Override
	public void chartChanged(ChartChangeEvent event) {

	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Hotest address:");
		}
		return jLabel1;
	}

	private JScrollPane getHostestAddressScrollPane() {
		if (hostestAddressScrollPane == null) {
			hostestAddressScrollPane = new JScrollPane();
			hostestAddressScrollPane.setViewportView(getHotestAddressTable());
		}
		return hostestAddressScrollPane;
	}

	private JTable getHotestAddressTable() {
		if (hotestAddressTable == null) {
			DefaultTableModel jHotestAddressTableModel = new DefaultTableModel(new String[][] {}, new String[] { "Address", "Hit count" });
			hotestAddressTable = new JTable();
			hotestAddressTable.setModel(jHotestAddressTableModel);
		}
		return hotestAddressTable;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("Informations:");
		}
		return jLabel2;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("Mem addr :");
		}
		return jLabel3;
	}

	private JSearchTextField getSearchTextField() {
		if (searchTextField == null) {
			searchTextField = new JSearchTextField();
			searchTextField.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent evt) {
					searchTextFieldKeyPressed(evt);
				}
			});
		}
		return searchTextField;
	}

	private JLabel getJLabel4() {
		if (jAddressLabel == null) {
			jAddressLabel = new JLabel();
			jAddressLabel.setText("Address = ");
		}
		return jAddressLabel;
	}

	private JLabel getJLabel5() {
		if (jRWCountLabel == null) {
			jRWCountLabel = new JLabel();
			jRWCountLabel.setText("Read write count =");
		}
		return jRWCountLabel;
	}

	@Override
	public void chartMouseClicked(ChartMouseEvent event) {
		try {
			// System.out.println(event.getTrigger().getX());
			JFreeChart chart = event.getChart();
			XYPlot xyplot = chart.getXYPlot();
			MyXYBlockRenderer renderer = (MyXYBlockRenderer) xyplot.getRenderer();

			XYZDataset dataset = (XYZDataset) xyplot.getDataset();
			XYItemEntity entity = (XYItemEntity) event.getEntity();
			int series = entity.getSeriesIndex();
			int item = entity.getItem();

			int i = event.getTrigger().getX();
			int j = event.getTrigger().getY();
			Point2D point2d = memoryChartPanel.translateScreenToJava2D(new Point(i, j));
			ChartRenderingInfo chartrenderinginfo = memoryChartPanel.getChartRenderingInfo();
			Rectangle2D rectangle2d = chartrenderinginfo.getPlotInfo().getDataArea();
			double x = xyplot.getDomainAxis().java2DToValue(point2d.getX(), rectangle2d, xyplot.getDomainAxisEdge());
			double y = xyplot.getRangeAxis().java2DToValue(point2d.getY(), rectangle2d, xyplot.getRangeAxisEdge());
			int realX = (int) Math.round(x);
			int realY = (int) Math.round(y);
			renderer.setSelectedXY(realX, realY);
			long blockSize = CommonLib.convertFilesize((String) blockSizeComboBox.getSelectedItem());
			long columnCount = Data.getColumnCount(CommonLib.convertFilesize((String) fromComboBox.getSelectedItem()),
					CommonLib.convertFilesize((String) toComboBox.getSelectedItem()), blockSize);
			Long address = ((realY * columnCount) + realX) * blockSize;

			updateHotestTable(address, blockSize);

			this.jAddressLabel.setText("Address=0x" + Long.toHexString(address));
			this.jRWCountLabel.setText("R/W count=" + (int) dataset.getZValue(series, item));
		} catch (Exception ex) {

		}
	}

	private void updateHotestTable(long address, long blockSize) {
		DefaultTableModel jHotestAddressTableModel = (DefaultTableModel) hotestAddressTable.getModel();
		while (jHotestAddressTableModel.getRowCount() > 0) {
			jHotestAddressTableModel.removeRow(0);
		}
		HashMap<String, Integer> map = Data.getHotestAddressCount(address, blockSize);
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			jHotestAddressTableModel.addRow(new String[] { entry.getKey().toString(), entry.getValue().toString() });
		}
	}

	@Override
	public void chartMouseMoved(ChartMouseEvent event) {
		// System.out.println("chartMouseMoved");

	}

	private JComboBox getFromComboBox() {
		if (fromComboBox == null) {
			ComboBoxModel jFromComboBoxModel = new DefaultComboBoxModel(new String[] { "0MB" });
			fromComboBox = new JComboBox();
			fromComboBox.setModel(jFromComboBoxModel);
			fromComboBox.setEditable(true);
			fromComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					fromComboBoxActionPerformed(evt);
				}
			});
		}
		return fromComboBox;
	}

	private JLabel getJLabel4x() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("From");
		}
		return jLabel4;
	}

	private JLabel getJLabel5x() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("Block Size");
		}
		return jLabel5;
	}

	private JComboBox getBlockSizeComboBox() {
		if (blockSizeComboBox == null) {
			ComboBoxModel jToComboBoxModel = new DefaultComboBoxModel(new String[] { "100MB", "10MB", "1MB", "100KB", "32KB" });
			blockSizeComboBox = new JComboBox();
			blockSizeComboBox.setModel(jToComboBoxModel);
			blockSizeComboBox.setEditable(true);
			blockSizeComboBox.setSelectedItem("100KB");
			blockSizeComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					blockSizeComboBoxActionPerformed(evt);
				}
			});
		}
		return blockSizeComboBox;
	}

	private void fromComboBoxActionPerformed(ActionEvent evt) {
		updateChart();
	}

	private void blockSizeComboBoxActionPerformed(ActionEvent evt) {
		updateChart();
	}

	private JLabel getJLabel6() {
		if (jLabel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setText("To");
			jLabel6.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabel6;
	}

	private JComboBox getToComboBox() {
		if (toComboBox == null) {
			ComboBoxModel jToComboBoxModel = new DefaultComboBoxModel(new String[] { "1GB", "100MB", "10MB", "1MB" });
			toComboBox = new JComboBox();
			toComboBox.setModel(jToComboBoxModel);
			toComboBox.setEditable(true);
			jToComboBoxModel.setSelectedItem("100MB");
			toComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					toComboBoxActionPerformed(evt);
				}
			});
		}
		return toComboBox;
	}

	private void toComboBoxActionPerformed(ActionEvent evt) {
		updateChart();
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			FlowLayout jPanel1Layout = new FlowLayout();
			jPanel1Layout.setHgap(0);
			jPanel1Layout.setVgap(0);
			jPanel1Layout.setAlignment(FlowLayout.LEFT);
			jPanel1.setLayout(jPanel1Layout);
			jPanel1.add(getZoomOutAutoRangeButton());
			jPanel1.add(getZoomOutButton());
			jPanel1.add(getZoomInButton());
		}
		return jPanel1;
	}

	private void searchTextFieldKeyPressed(KeyEvent evt) {
		if ((evt.getKeyCode() == KeyEvent.VK_ENTER)) {
			ValueAxis xAxis = this.jfcMemory.getXYPlot().getDomainAxis();
			// Double max = jfcMemory.getMax(jfcMemory.getXYPlot().getDataset(),
			// xAxis.getRange());

			long columnCount = Data.getColumnCount(CommonLib.convertFilesize((String) fromComboBox.getSelectedItem()),
					CommonLib.convertFilesize((String) toComboBox.getSelectedItem()), CommonLib.convertFilesize((String) blockSizeComboBox.getSelectedItem()));
			long address = CommonLib.convertFilesize(searchTextField.getText());
			long blockSize = CommonLib.convertFilesize((String) blockSizeComboBox.getSelectedItem());
			long blockNo = address / blockSize;
			long x = blockNo % columnCount;
			long y = blockNo / columnCount;
			MyXYBlockRenderer renderer = (MyXYBlockRenderer) jfcMemory.getXYPlot().getRenderer();
			renderer.setRealX((int) x);
			renderer.setRealY((int) y);
			updateHotestTable(address, blockSize);
			jfcMemory.fireChartChanged();
			// ((DefaultXYZDataset)
			// jfcMemory.getXYPlot().getDataset()).addSeries(null, null);
			// jMemoryChartPanel.repaint();
		}
	}

	private JTabbedPane getJTabbedPane2() {
		if (jTabbedPane2 == null) {
			jTabbedPane2 = new JTabbedPane();

			jfcMemory = createEmptyChart(createEmptyDataset());
			memoryChartPanel = new ChartPanel(jfcMemory);
			jTabbedPane2.addTab("Chart", null, memoryChartPanel, null);
			jTabbedPane2.addTab("3D Chart", null, getJPanel14(), null);
			memoryChartPanel.setDisplayToolTips(true);
			jfcMemory.addChangeListener(this);
			memoryChartPanel.addChartMouseListener(this);
		}
		return jTabbedPane2;
	}

	private JPanel getJMemoryProfilingPanel() {
		if (jMemoryProfilingPanel == null) {
			jMemoryProfilingPanel = new JPanel();
			GroupLayout jMemoryProfilingPanelLayout = new GroupLayout((JComponent) jMemoryProfilingPanel);
			jMemoryProfilingPanel.setLayout(jMemoryProfilingPanelLayout);
			jMemoryProfilingPanelLayout
					.setVerticalGroup(jMemoryProfilingPanelLayout
							.createSequentialGroup()
							.addContainerGap()
							.addComponent(getJLabel7(), GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(
									jMemoryProfilingPanelLayout
											.createParallelGroup(GroupLayout.Alignment.BASELINE)
											.addComponent(getJProfilingFromComboBox(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(getJLabel8(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(getJLabel9(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(getJProfilingToComboBox(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(getJAddZoneButton(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
													GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(getJScrollPane2(), GroupLayout.PREFERRED_SIZE, 254, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addGroup(
									jMemoryProfilingPanelLayout
											.createParallelGroup(GroupLayout.Alignment.BASELINE)
											.addComponent(getJSortCheckBox(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(getJLabel17(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
													GroupLayout.PREFERRED_SIZE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(getJScrollPane6(), GroupLayout.PREFERRED_SIZE, 214, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(getJDeleteZoneButton(), GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
			jMemoryProfilingPanelLayout.linkSize(SwingConstants.VERTICAL, new Component[] { getJProfilingFromComboBox(), getJProfilingToComboBox() });
			jMemoryProfilingPanelLayout.setHorizontalGroup(jMemoryProfilingPanelLayout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
							jMemoryProfilingPanelLayout
									.createParallelGroup()
									.addGroup(
											GroupLayout.Alignment.LEADING,
											jMemoryProfilingPanelLayout.createSequentialGroup()
													.addComponent(getJLabel7(), GroupLayout.PREFERRED_SIZE, 608, GroupLayout.PREFERRED_SIZE).addGap(0, 156, Short.MAX_VALUE))
									.addGroup(
											GroupLayout.Alignment.LEADING,
											jMemoryProfilingPanelLayout
													.createSequentialGroup()
													.addGroup(
															jMemoryProfilingPanelLayout
																	.createParallelGroup()
																	.addGroup(
																			GroupLayout.Alignment.LEADING,
																			jMemoryProfilingPanelLayout
																					.createSequentialGroup()
																					.addGroup(
																							jMemoryProfilingPanelLayout
																									.createParallelGroup()
																									.addComponent(getJLabel17(), GroupLayout.Alignment.LEADING,
																											GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
																									.addGroup(
																											GroupLayout.Alignment.LEADING,
																											jMemoryProfilingPanelLayout
																													.createSequentialGroup()
																													.addComponent(getJDeleteZoneButton(),
																															GroupLayout.PREFERRED_SIZE, 62,
																															GroupLayout.PREFERRED_SIZE).addGap(46)))
																					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
																					.addComponent(getJSortCheckBox(), GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE))
																	.addGroup(
																			GroupLayout.Alignment.LEADING,
																			jMemoryProfilingPanelLayout
																					.createSequentialGroup()
																					.addComponent(getJLabel8(), GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
																					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
																					.addComponent(getJProfilingFromComboBox(), GroupLayout.PREFERRED_SIZE,
																							GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE).addGap(29)))
													.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 74, GroupLayout.PREFERRED_SIZE)
													.addComponent(getJLabel9(), GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
													.addComponent(getJProfilingToComboBox(), GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
													.addComponent(getJAddZoneButton(), GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE).addGap(0, 275, Short.MAX_VALUE))
									.addComponent(getJScrollPane2(), GroupLayout.Alignment.LEADING, 0, 764, Short.MAX_VALUE)
									.addComponent(getJScrollPane6(), GroupLayout.Alignment.LEADING, 0, 764, Short.MAX_VALUE)).addContainerGap());
			jMemoryProfilingPanelLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] { getJProfilingFromComboBox(), getJProfilingToComboBox() });
		}
		return jMemoryProfilingPanel;
	}

	private JLabel getJLabel7() {
		if (jLabel7 == null) {
			jLabel7 = new JLabel();
			jLabel7.setText("Enter the memory zone that you want to profile");
		}
		return jLabel7;
	}

	private JLabel getJLabel8() {
		if (jLabel8 == null) {
			jLabel8 = new JLabel();
			jLabel8.setText("From");
		}
		return jLabel8;
	}

	private JComboBox getJProfilingFromComboBox() {
		if (jProfilingFromComboBox == null) {
			ComboBoxModel jComboBox1Model = new DefaultComboBoxModel(new String[] {});
			jProfilingFromComboBox = new JComboBox();
			jProfilingFromComboBox.setModel(jComboBox1Model);
			jProfilingFromComboBox.setEditable(true);

			new Thread() {
				public void run() {
					LinkedList<Long> vector = Setting.getInstance().profileMemoryFromAddress;
					Iterator<Long> iterator = vector.iterator();
					while (iterator.hasNext()) {
						addProfileMemoryFromComboBox(iterator.next());
					}
				}
			}.start();
		}
		return jProfilingFromComboBox;
	}

	private JLabel getJLabel9() {
		if (jLabel9 == null) {
			jLabel9 = new JLabel();
			jLabel9.setText("To");
			jLabel9.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabel9;
	}

	private JComboBox getJProfilingToComboBox() {
		if (jProfilingToComboBox == null) {
			ComboBoxModel jComboBox2Model = new DefaultComboBoxModel(new String[] {});
			jProfilingToComboBox = new JComboBox();
			jProfilingToComboBox.setModel(jComboBox2Model);
			jProfilingToComboBox.setEditable(true);

			new Thread() {
				public void run() {
					LinkedList<Long> vector = Setting.getInstance().profileMemoryToAddress;
					Iterator<Long> iterator = vector.iterator();
					while (iterator.hasNext()) {
						addProfileMemoryToComboBox(iterator.next());
					}
				}
			}.start();
		}
		return jProfilingToComboBox;
	}

	private JTable getJProfilingTable() {
		if (profilingTable == null) {
			profilingTable = new JTable();
			ProfilingTableModel profilingTableModel = new ProfilingTableModel();
			profilingTable.setModel(profilingTableModel);
			profilingTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			profilingTable.getColumnModel().getColumn(4).setPreferredWidth(3000);
			profilingTable.getTableHeader().setReorderingAllowed(false);
			profilingTable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					jProfilingTableMouseClicked(evt);
				}
			});
			Data.memoryProfilingZone = profilingTableModel;

			try {
				LinkedList<Long> fromVector = Setting.getInstance().profileMemoryFromAddress;
				LinkedList<Long> toVector = Setting.getInstance().profileMemoryToAddress;
				Iterator<Long> fromIterator = fromVector.iterator();
				Iterator<Long> toIterator = toVector.iterator();
				while (fromIterator.hasNext()) {
					((ProfilingTableModel) this.profilingTable.getModel()).addZone(fromIterator.next(), toIterator.next());
				}
			} catch (Exception ex) {

			}
		}
		return profilingTable;
	}

	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getJProfilingTable());
		}
		return jScrollPane2;
	}

	private JButton getJAddZoneButton() {
		if (addZoneButton == null) {
			addZoneButton = new JButton();
			addZoneButton.setText("Add");
			addZoneButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jAddZoneButtonActionPerformed(evt);
				}
			});
		}
		return addZoneButton;
	}

	private void jAddZoneButtonActionPerformed(ActionEvent evt) {
		try {
			long from = CommonLib.convertFilesize(jProfilingFromComboBox.getSelectedItem().toString());
			long to = CommonLib.convertFilesize(jProfilingToComboBox.getSelectedItem().toString());
			if (from > to) {
				long x = to;
				to = from;
				from = x;
			}
			((ProfilingTableModel) this.profilingTable.getModel()).addZone(from, to);
			jProfilingFromComboBox.setSelectedItem("");
			jProfilingToComboBox.setSelectedItem("");

			Setting.getInstance().profileMemoryFromAddress.add(from);
			Setting.getInstance().profileMemoryToAddress.add(to);
			Setting.getInstance().save();

			Data.memoryProfilingZone.needToTellBochsToUpdateZone = true;
		} catch (Exception ex) {

		}
	}

	private void addProfileMemoryFromComboBox(Long l) {
		for (int x = 0; x < jProfilingFromComboBox.getItemCount(); x++) {
			if (jProfilingFromComboBox.getItemAt(x).toString().trim().equals(l.toString().trim())) {
				return;
			}
		}
		jProfilingFromComboBox.addItem("0x" + Long.toHexString(l));
	}

	private void addProfileMemoryToComboBox(Long l) {
		for (int x = 0; x < jProfilingToComboBox.getItemCount(); x++) {
			if (jProfilingToComboBox.getItemAt(x).toString().trim().equals(l.toString().trim())) {
				return;
			}
		}
		jProfilingToComboBox.addItem("0x" + Long.toHexString(l));
	}

	private JButton getJDeleteZoneButton() {
		if (deleteZoneButton == null) {
			deleteZoneButton = new JButton();
			deleteZoneButton.setText("Delete");
			deleteZoneButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jDeleteZoneButtonActionPerformed(evt);
				}
			});
		}
		return deleteZoneButton;
	}

	private void jDeleteZoneButtonActionPerformed(ActionEvent evt) {
		Setting.getInstance().profileMemoryToAddress.remove(profilingTable.getSelectedRow());
		Setting.getInstance().profileMemoryFromAddress.remove(profilingTable.getSelectedRow());

		((ProfilingTableModel) this.profilingTable.getModel()).removeAll();

		LinkedList<Long> fromVector = Setting.getInstance().profileMemoryFromAddress;
		LinkedList<Long> toVector = Setting.getInstance().profileMemoryToAddress;
		Iterator<Long> fromIterator = fromVector.iterator();
		Iterator<Long> toIterator = toVector.iterator();
		while (fromIterator.hasNext()) {
			((ProfilingTableModel) this.profilingTable.getModel()).addZone(fromIterator.next(), toIterator.next());
		}
	}

	private JPanel getJmpPanel() {
		if (jmpPanel == null) {
			jmpPanel = new JPanel();
			BorderLayout jmpPanelLayout = new BorderLayout();
			jmpPanel.setLayout(jmpPanelLayout);
			jmpPanel.add(getJScrollPane3(), BorderLayout.CENTER);
			jmpPanel.add(getJPanel2(), BorderLayout.NORTH);
		}
		return jmpPanel;
	}

	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getJTable1());
		}
		return jScrollPane3;
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			FlowLayout jPanel2Layout = new FlowLayout();
			jPanel2Layout.setAlignment(FlowLayout.LEFT);
			jPanel2.setLayout(jPanel2Layout);
			jPanel2.add(getJLabel10());
			jPanel2.add(getJNoOfLineComboBox());
			jPanel2.add(getJGroupCheckBox());
		}
		return jPanel2;
	}

	private JComboBox getJNoOfLineComboBox() {
		if (noOfLineComboBox == null) {
			ComboBoxModel jNoOfLineComboBoxModel = new DefaultComboBoxModel(new String[] { "20", "50", "100", "200" });
			noOfLineComboBox = new JComboBox();
			noOfLineComboBox.setModel(jNoOfLineComboBoxModel);
			noOfLineComboBox.setEditable(true);
			noOfLineComboBox.setPreferredSize(new java.awt.Dimension(153, 22));
			noOfLineComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jNoOfLineComboBoxActionPerformed(evt);
				}
			});
		}
		return noOfLineComboBox;
	}

	private JLabel getJLabel10() {
		if (jLabel10 == null) {
			jLabel10 = new JLabel();
			jLabel10.setText("No of line");
		}
		return jLabel10;
	}

	private void jNoOfLineComboBoxActionPerformed(ActionEvent evt) {
		jmpTableModel.setRowCount(Integer.parseInt(noOfLineComboBox.getSelectedItem().toString()));
	}

	private JCheckBox getJGroupCheckBox() {
		if (groupCheckBox == null) {
			groupCheckBox = new JCheckBox();
			groupCheckBox.setText("Group");
			groupCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jGroupCheckBoxActionPerformed(evt);
				}
			});
		}
		return groupCheckBox;
	}

	private void jGroupCheckBoxActionPerformed(ActionEvent evt) {
		jmpTableModel.setGroup(groupCheckBox.isSelected());
	}

	private JTable getJTable1() {
		if (table1 == null) {
			table1 = new JTable();
			table1.setModel(jmpTableModel);
			table1.getTableHeader().setReorderingAllowed(false);
			table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			for (int x = 0; x < table1.getColumnCount(); x++) {
				table1.getColumnModel().getColumn(x).setPreferredWidth(100);
			}
		}
		return table1;
	}

	public JmpTableModel getJmpTableModel() {
		return jmpTableModel;
	}

	private JPanel getJCallGraphPanel() {
		if (callGraphPanel == null) {
			callGraphPanel = new JPanel();
			BorderLayout jCallGraphPanelLayout = new BorderLayout();
			callGraphPanel.setLayout(jCallGraphPanelLayout);
			callGraphPanel.add(getJToolBar1(), BorderLayout.NORTH);
			callGraphPanel.add(getJCallGraphSplitPane(), BorderLayout.CENTER);
		}
		return callGraphPanel;
	}

	private JToolBar getJToolBar1() {
		if (toolBar1 == null) {
			toolBar1 = new JToolBar();
			toolBar1.add(getJSaveGraphButton());
			toolBar1.add(getJRefreshCallGraphButton());
			toolBar1.add(getJLayoutTreeButton());
			toolBar1.add(getJLayoutCircleButton());
			toolBar1.add(getJLayoutOrganicButton());
			toolBar1.add(getJLayoutHierarchicalButton());
			toolBar1.add(getJCallGraphZoomInButton());
			toolBar1.add(getJCallGraphScaleTextField());
			toolBar1.add(getJCallGraphZoomOutButton());
			toolBar1.add(getJLabel11());
			toolBar1.add(getJTrackUnitComboBox());
			toolBar1.add(getJLabel12());
			toolBar1.add(getJTrackDistanceComboBox());
		}
		return toolBar1;
	}

	private JButton getJSaveGraphButton() {
		if (saveGraphButton == null) {
			saveGraphButton = new JButton();
			saveGraphButton.setText("Save");
			saveGraphButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jSaveGraphButtonActionPerformed(evt);
				}
			});
		}
		return saveGraphButton;
	}

	public void updateCallGraph() {
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
		graphComponent = new CallGraphComponent(graph);
		Object parent = graph.getDefaultParent();

		addCells(parent);
		graph.setCellsDisconnectable(false);

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
					if (label.contains("->")) {
						cellClientEvent(label);
					}
				}
			}
		});

		graph.setCellsResizable(false);
		graph.setCellsMovable(false);
		graph.setCellsEditable(false);
		graph.foldCells(false);
		graph.setGridSize(10);
		jPanel3.removeAll();
		jPanel3.add(graphComponent, BorderLayout.CENTER);

		graphOutline = new mxGraphOutline(graphComponent);
		graphOutline.setBackground(Color.white);
		graphOutline.setBorder(new LineBorder(Color.LIGHT_GRAY));
		callGraphPreviewPanel.removeAll();
		callGraphPreviewPanel.add(graphOutline, BorderLayout.CENTER);
		callGraphPreviewPanel.setPreferredSize(new Dimension(100, 100));
	}

	private void cellClientEvent(String label) {
		String str[] = label.split("->");
		this.segmentStartTextField.setText(str[0]);
		this.segmentEndTextField.setText(str[1]);
	}

	private void setMarkerMaxAndMinSize() {
		long smallestSegmentStart = Long.MAX_VALUE;
		long largestSegmentEnd = Long.MIN_VALUE;
		for (int x = JmpSocketServer.jmpDataVector.size() - 1, counter = 0; x >= 0 && counter <= MAX_NUMBER_OF_VERTEX; x--, counter++) {
			JmpData jumpData = JmpSocketServer.jmpDataVector.get(x);
			if (jumpData.segmentStart < smallestSegmentStart) {
				smallestSegmentStart = jumpData.segmentStart;
			}
			if (jumpData.segmentEnd > largestSegmentEnd) {
				largestSegmentEnd = jumpData.segmentEnd;
			}
		}
		graphComponent.markerOffset = smallestSegmentStart;
		graphComponent.markerEnd = largestSegmentEnd;
	}

	private void addCells(Object parent) {
		setMarkerMaxAndMinSize();

		final int minX = 50;
		final int minY = 20;
		final int cellHeight = 20;

		int count = graph.getModel().getChildCount(parent);
		for (int x = 0; x < count; x++) {
			graph.getModel().remove(graph.getModel().getChildAt(parent, 0));
		}
		callGraphRawTableModel.removeAll();
		graph.getModel().beginUpdate();
		try {
			mxCell lastPort = null;
			jStatusProgressBar.setMaximum(MAX_NUMBER_OF_VERTEX);
			for (int x = JmpSocketServer.jmpDataVector.size() - 1, counter = 0; x >= 0 && counter <= MAX_NUMBER_OF_VERTEX; x--, counter++) {
				jStatusLabel.setText("Updating call graph " + x + "/" + JmpSocketServer.jmpDataVector.size());
				jStatusProgressBar.setValue(counter);
				JmpData jumpData = JmpSocketServer.jmpDataVector.get(x);
				callGraphRawTableModel.add(jumpData);
				int positionX = (int) ((jumpData.segmentStart - graphComponent.markerOffset) / graphComponent.addressPerPixel);
				positionX += minX;
				mxCell node = (mxCell) graph.insertVertex(parent, null, "0x" + Long.toHexString(jumpData.segmentStart) + " -> " + "0x" + Long.toHexString(jumpData.segmentEnd),
						positionX, minY + (counter * 30), (jumpData.segmentEnd - jumpData.segmentStart) / graphComponent.addressPerPixel, cellHeight);

				mxCell ports[] = addPort(node);

				if (lastPort != null) {
					// graph.insertEdge(parent, null, x, lastPort, ports[0],
					// "edgeStyle=elbowEdgeStyle;elbow=horizontal;"
					// +
					// "exitX=1;exitY=0.5;exitPerimeter=1;entryX=0;entryY=0;entryPerimeter=1;");
					graph.insertEdge(parent, null, "", lastPort, ports[0], "edgeStyle=entityRelationEdgeStyle;");
				}
				lastPort = ports[1];
			}
			jStatusProgressBar.setValue(jStatusProgressBar.getMaximum());
		} finally {
			graph.getModel().endUpdate();
		}
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

	// public Vector<Object> getAllCells(Object parent) {
	// Vector<Object> vector = new Vector<Object>();
	// for (int x = 0; x < graph.getModel().getChildCount(parent); x++) {
	// mxCell cell = (mxCell) graph.getModel().getChildAt(parent, x);
	// if (cell.isVertex()) {
	// vector.add(cell);
	// }
	// }
	// return vector;
	// }

	private JButton getJRefreshCallGraphButton() {
		if (refreshCallGraphButton == null) {
			refreshCallGraphButton = new JButton();
			refreshCallGraphButton.setText("Refresh");
			refreshCallGraphButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jRefreshCallGraphButtonActionPerformed(evt);
				}
			});
		}
		return refreshCallGraphButton;
	}

	private void jRefreshCallGraphButtonActionPerformed(ActionEvent evt) {
		this.updateCallGraph();
	}

	private JButton getJLayoutTreeButton() {
		if (layoutTreeButton == null) {
			layoutTreeButton = new JButton();
			layoutTreeButton.setText("Tree");
			layoutTreeButton.setVisible(false);
			layoutTreeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jLayoutTreeButtonActionPerformed(evt);
				}
			});
		}
		return layoutTreeButton;
	}

	private JButton getJLayoutCircleButton() {
		if (layoutCircleButton == null) {
			layoutCircleButton = new JButton();
			layoutCircleButton.setText("Circle");
			layoutCircleButton.setVisible(false);
			layoutCircleButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jLayoutCircleButtonActionPerformed(evt);
				}
			});
		}
		return layoutCircleButton;
	}

	private JButton getJLayoutOrganicButton() {
		if (layoutOrganicButton == null) {
			layoutOrganicButton = new JButton();
			layoutOrganicButton.setText("Organic");
			layoutOrganicButton.setVisible(false);
			layoutOrganicButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jLayoutOrganicButtonActionPerformed(evt);
				}
			});
		}
		return layoutOrganicButton;
	}

	private JButton getJLayoutHierarchicalButton() {
		if (layoutHierarchicalButton == null) {
			layoutHierarchicalButton = new JButton();
			layoutHierarchicalButton.setText("Hierarchical");
			layoutHierarchicalButton.setVisible(false);
			layoutHierarchicalButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jLayoutHierarchicalButtonActionPerformed(evt);
				}
			});
		}
		return layoutHierarchicalButton;
	}

	private void jLayoutTreeButtonActionPerformed(ActionEvent evt) {
		mxCompactTreeLayout layout = new mxCompactTreeLayout(graph);
		if (layout != null) {
			Object cell = graphComponent.getGraph().getSelectionCell();
			if (cell == null || graphComponent.getGraph().getModel().getChildCount(cell) == 0) {
				cell = graphComponent.getGraph().getDefaultParent();
			}
			layout.execute(cell);
		}
	}

	private void jLayoutCircleButtonActionPerformed(ActionEvent evt) {
		mxCircleLayout layout = new mxCircleLayout(graph);
		if (layout != null) {
			Object cell = graphComponent.getGraph().getSelectionCell();
			if (cell == null || graphComponent.getGraph().getModel().getChildCount(cell) == 0) {
				cell = graphComponent.getGraph().getDefaultParent();
			}
			layout.execute(cell);
		}
	}

	private void jLayoutOrganicButtonActionPerformed(ActionEvent evt) {
		mxOrganicLayout layout = new mxOrganicLayout(graph);
		if (layout != null) {
			Object cell = graphComponent.getGraph().getSelectionCell();
			if (cell == null || graphComponent.getGraph().getModel().getChildCount(cell) == 0) {
				cell = graphComponent.getGraph().getDefaultParent();
			}
			layout.execute(cell);
		}
	}

	private void jLayoutHierarchicalButtonActionPerformed(ActionEvent evt) {
		mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
		if (layout != null) {
			Object cell = graphComponent.getGraph().getSelectionCell();
			if (cell == null || graphComponent.getGraph().getModel().getChildCount(cell) == 0) {
				cell = graphComponent.getGraph().getDefaultParent();
			}
			layout.execute(cell);
		}
	}

	private JTabbedPane getJTabbedPane3() {
		if (jTabbedPane3 == null) {
			jTabbedPane3 = new JTabbedPane();
			jTabbedPane3.setPreferredSize(new java.awt.Dimension(730, 443));
			jTabbedPane3.addTab("Config", null, getJCallGraphConfigPanel(), null);
			jTabbedPane3.addTab("Call graph", null, getJCallGraphPanel(), null);
			jTabbedPane3.addTab("Raw data", null, getJCallGraphTablePanel(), null);
		}
		return jTabbedPane3;
	}

	private JPanel getJCallGraphTablePanel() {
		if (callGraphTablePanel == null) {
			callGraphTablePanel = new JPanel();
			BorderLayout jCallGraphTablePanelLayout = new BorderLayout();
			callGraphTablePanel.setLayout(jCallGraphTablePanelLayout);
			callGraphTablePanel.add(getJScrollPane1x(), BorderLayout.CENTER);
		}
		return callGraphTablePanel;
	}

	private JScrollPane getJScrollPane1x() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJCallGraphRawTable());
		}
		return jScrollPane1;
	}

	private JTable getJCallGraphRawTable() {
		if (callGraphRawTable == null) {
			callGraphRawTable = new JTable();
			callGraphRawTable.setModel(callGraphRawTableModel);
			callGraphRawTable.getTableHeader().setReorderingAllowed(false);
			callGraphRawTable.getTableHeader().setReorderingAllowed(false);
		}
		return callGraphRawTable;
	}

	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			BorderLayout jPanel3Layout = new BorderLayout();
			jPanel3.setLayout(jPanel3Layout);
		}
		return jPanel3;
	}

	private JPanel getJCallGraphDetailPanel() {
		if (callGraphDetailPanel == null) {
			callGraphDetailPanel = new JPanel();
			BorderLayout jCallGraphDetailPanelLayout = new BorderLayout();
			callGraphDetailPanel.setLayout(jCallGraphDetailPanelLayout);
			callGraphDetailPanel.setPreferredSize(new java.awt.Dimension(760, 184));
			callGraphDetailPanel.add(getJCallGraphPreviewPanel(), BorderLayout.WEST);
			callGraphDetailPanel.add(getJTabbedPane4(), BorderLayout.CENTER);
		}
		return callGraphDetailPanel;
	}

	private JSplitPane getJCallGraphSplitPane() {
		if (callGraphSplitPane == null) {
			callGraphSplitPane = new JSplitPane();
			callGraphSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			callGraphSplitPane.setDividerLocation(300);
			callGraphSplitPane.add(getJPanel3(), JSplitPane.TOP);
			callGraphSplitPane.add(getJCallGraphDetailPanel(), JSplitPane.BOTTOM);
		}
		return callGraphSplitPane;
	}

	private JPanel getJCallGraphPreviewPanel() {
		if (callGraphPreviewPanel == null) {
			callGraphPreviewPanel = new JPanel();
			BorderLayout jCallGraphPreviewPanelLayout = new BorderLayout();
			callGraphPreviewPanel.setLayout(jCallGraphPreviewPanelLayout);
		}
		return callGraphPreviewPanel;
	}

	private JLabel getJSegmentStartLabel() {
		if (segmentStartLabel == null) {
			segmentStartLabel = new JLabel();
			segmentStartLabel.setText("Segment start");
			segmentStartLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return segmentStartLabel;
	}

	private JLabel getJSegmentEndLabel() {
		if (segmentEndLabel == null) {
			segmentEndLabel = new JLabel();
			segmentEndLabel.setText("Segment end");
			segmentEndLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return segmentEndLabel;
	}

	private JLabel getJSegmentFromLabel() {
		if (segmentFromLabel == null) {
			segmentFromLabel = new JLabel();
			segmentFromLabel.setText("Segment jump from");
			segmentFromLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return segmentFromLabel;
	}

	private JLabel getJSegmentToLabel() {
		if (segmentToLabel == null) {
			segmentToLabel = new JLabel();
			segmentToLabel.setText("Segment jump to");
			segmentToLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return segmentToLabel;
	}

	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			GroupLayout jPanel4Layout = new GroupLayout((JComponent) jPanel4);
			jPanel4.setLayout(jPanel4Layout);
			jPanel4Layout.setHorizontalGroup(jPanel4Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
							jPanel4Layout.createParallelGroup()
									.addComponent(getJSegmentStartLabel(), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
									.addComponent(getJSegmentEndLabel(), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
									.addComponent(getJSegmentFromLabel(), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
									.addComponent(getJSegmentToLabel(), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
							jPanel4Layout
									.createParallelGroup()
									.addGroup(
											jPanel4Layout.createSequentialGroup().addComponent(getJSegmentStartTextField(), GroupLayout.PREFERRED_SIZE, 158,
													GroupLayout.PREFERRED_SIZE))
									.addGroup(
											jPanel4Layout.createSequentialGroup().addComponent(getJSegmentEndTextField(), GroupLayout.PREFERRED_SIZE, 158,
													GroupLayout.PREFERRED_SIZE))
									.addGroup(
											jPanel4Layout.createSequentialGroup().addComponent(getJSegmentFromTextField(), GroupLayout.PREFERRED_SIZE, 158,
													GroupLayout.PREFERRED_SIZE))
									.addGroup(
											jPanel4Layout.createSequentialGroup().addComponent(getJSegmentToTextField(), GroupLayout.PREFERRED_SIZE, 158,
													GroupLayout.PREFERRED_SIZE))).addContainerGap(324, Short.MAX_VALUE));
			jPanel4Layout.setVerticalGroup(jPanel4Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
							jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(getJSegmentStartTextField(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
									.addComponent(getJSegmentStartLabel(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
							jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(getJSegmentEndTextField(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
									.addComponent(getJSegmentEndLabel(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
							jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(getJSegmentFromTextField(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
									.addComponent(getJSegmentFromLabel(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
							jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(getJSegmentToTextField(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
									.addComponent(getJSegmentToLabel(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(21, 21));
		}
		return jPanel4;
	}

	private JTextField getJSegmentStartTextField() {
		if (segmentStartTextField == null) {
			segmentStartTextField = new JTextField();
		}
		return segmentStartTextField;
	}

	private JTextField getJSegmentEndTextField() {
		if (segmentEndTextField == null) {
			segmentEndTextField = new JTextField();
		}
		return segmentEndTextField;
	}

	private JTextField getJSegmentFromTextField() {
		if (segmentFromTextField == null) {
			segmentFromTextField = new JTextField();
		}
		return segmentFromTextField;
	}

	private JTextField getJSegmentToTextField() {
		if (segmentToTextField == null) {
			segmentToTextField = new JTextField();
		}
		return segmentToTextField;
	}

	private void jSaveGraphButtonActionPerformed(ActionEvent evt) {
		JFileChooser chooser = new JFileChooser();
		chooser.showSaveDialog(this);

		File file = chooser.getSelectedFile();
		if (file != null && !file.getPath().equals("")) {
			try {
				saveXmlPng(graphComponent, file, Color.white);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Cannot save file");
			}
		}
	}

	protected void saveXmlPng(mxGraphComponent graphComponent, File file, Color bg) throws IOException {
		mxGraph graph = graphComponent.getGraph();

		// Creates the image for the PNG file
		BufferedImage image = mxCellRenderer.createBufferedImage(graph, null, 1, bg, graphComponent.isAntiAlias(), null, graphComponent.getCanvas());

		// Creates the URL-encoded XML data
		mxCodec codec = new mxCodec();
		String xml = URLEncoder.encode(mxUtils.getXml(codec.encode(graph.getModel())), "UTF-8");
		mxPngEncodeParam param = mxPngEncodeParam.getDefaultEncodeParam(image);
		param.setCompressedText(new String[] { "mxGraphModel", xml });

		// Saves as a PNG file
		FileOutputStream outputStream = new FileOutputStream(file);
		try {
			mxPngImageEncoder encoder = new mxPngImageEncoder(outputStream, param);

			if (image != null) {
				encoder.encode(image);
			} else {
				JOptionPane.showMessageDialog(graphComponent, mxResources.get("noImageData"));
			}
		} finally {
			outputStream.close();
		}
	}

	public void setThing(JProgressBar jStatusProgressBar, JLabel jStatusLabel) {
		this.jStatusProgressBar = jStatusProgressBar;
		this.jStatusLabel = jStatusLabel;
	}

	private JPanel getJCallGraphConfigPanel() {
		if (callGraphConfigPanel == null) {
			callGraphConfigPanel = new JPanel();
			BorderLayout jCallGraphConfigPanelLayout = new BorderLayout();
			callGraphConfigPanel.setLayout(jCallGraphConfigPanelLayout);
			callGraphConfigPanel.add(getJScrollPane4(), BorderLayout.CENTER);
			callGraphConfigPanel.add(getJPanel5(), BorderLayout.SOUTH);
		}
		return callGraphConfigPanel;
	}

	private JScrollPane getJScrollPane4() {
		if (jScrollPane4 == null) {
			jScrollPane4 = new JScrollPane();
			jScrollPane4.setViewportView(getJCallGraphConfigTable());
		}
		return jScrollPane4;
	}

	private JTable getJCallGraphConfigTable() {
		if (callGraphConfigTable == null) {
			callGraphConfigTable = new JTable();
			callGraphConfigTable.setModel(callGraphConfigTableModel);
			callGraphConfigTable.getTableHeader().setReorderingAllowed(false);
			callGraphConfigTable.getTableHeader().setReorderingAllowed(false);
			callGraphConfigTable.setDefaultRenderer(Boolean.class, new CallGraphConfigTableCellRenderer());
			callGraphConfigTable.setDefaultEditor(Boolean.class, new CallGraphConfigTableCellEditor());
		}
		return callGraphConfigTable;
	}

	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			FlowLayout jPanel5Layout = new FlowLayout();
			jPanel5Layout.setAlignment(FlowLayout.LEFT);
			jPanel5.setLayout(jPanel5Layout);
			jPanel5.add(getJAddCallGraphButton());
			jPanel5.add(getJDeleteButton());
		}
		return jPanel5;
	}

	private JButton getJAddCallGraphButton() {
		if (addCallGraphButton == null) {
			addCallGraphButton = new JButton();
			addCallGraphButton.setText("Add");
		}
		return addCallGraphButton;
	}

	private JButton getJDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new JButton();
			deleteButton.setText("Delete");
		}
		return deleteButton;
	}

	private JButton getJCallGraphZoomInButton() {
		if (callGraphZoomInButton == null) {
			callGraphZoomInButton = new JButton();
			callGraphZoomInButton.setText("+");
			callGraphZoomInButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jCallGraphZoomInButtonActionPerformed(evt);
				}
			});
		}
		return callGraphZoomInButton;
	}

	private JButton getJCallGraphZoomOutButton() {
		if (callGraphZoomOutButton == null) {
			callGraphZoomOutButton = new JButton();
			callGraphZoomOutButton.setText("-");
			callGraphZoomOutButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jCallGraphZoomOutButtonActionPerformed(evt);
				}
			});
		}
		return callGraphZoomOutButton;
	}

	private JComboBox getJTrackUnitComboBox() {
		if (trackUnitComboBox == null) {
			ComboBoxModel jTrackUnitComboBoxModel = new DefaultComboBoxModel(new String[] { "1", "2", "4", "8", "0x10", "0x20", "0x40", "0x80", "0x100", "0x200", "0x400", "0x800",
					"0x1000", "0x2000", "0x4000", "0x8000" });
			trackUnitComboBox = new JComboBox();
			trackUnitComboBox.setModel(jTrackUnitComboBoxModel);
			trackUnitComboBox.setMaximumSize(new java.awt.Dimension(100, 25));
			trackUnitComboBox.setEditable(true);
			trackUnitComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jTrackUnitComboBoxActionPerformed(evt);
				}
			});
		}
		return trackUnitComboBox;
	}

	private JComboBox getJTrackDistanceComboBox() {
		if (trackDistanceComboBox == null) {
			ComboBoxModel jTrackDistanceComboBoxModel = new DefaultComboBoxModel(new String[] { "10", "20", "40", "80", "100", "200", "400", "800" });
			trackDistanceComboBox = new JComboBox();
			trackDistanceComboBox.setModel(jTrackDistanceComboBoxModel);
			trackDistanceComboBox.setPreferredSize(new java.awt.Dimension(30, 22));
			trackDistanceComboBox.setMaximumSize(new java.awt.Dimension(100, 25));
			trackDistanceComboBox.setEditable(true);
			trackDistanceComboBox.setSelectedItem(100);
			trackDistanceComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jTrackDistanceComboBoxActionPerformed(evt);
				}
			});
		}
		return trackDistanceComboBox;
	}

	private void jCallGraphZoomInButtonActionPerformed(ActionEvent evt) {
		int scale = Integer.parseInt(callGraphScaleTextField.getText().replaceAll("%", ""));
		scale += 20;
		graphComponent.zoomTo((double) scale / 100, true);
		callGraphScaleTextField.setText(scale + "%");
	}

	private void jCallGraphZoomOutButtonActionPerformed(ActionEvent evt) {
		int scale = Integer.parseInt(callGraphScaleTextField.getText().replaceAll("%", ""));
		scale -= 20;
		graphComponent.zoomTo((double) scale / 100, true);
		callGraphScaleTextField.setText(scale + "%");
	}

	private void jTrackUnitComboBoxActionPerformed(ActionEvent evt) {
		graphComponent.addressPerPixel = CommonLib.string2BigInteger(trackUnitComboBox.getSelectedItem().toString()).floatValue();
		addCells(graph.getDefaultParent());
		graphComponent.repaint();
	}

	private void jTrackDistanceComboBoxActionPerformed(ActionEvent evt) {
		graphComponent.pixelPerMarker = CommonLib.string2BigInteger(trackDistanceComboBox.getSelectedItem().toString()).intValue();
		addCells(graph.getDefaultParent());
		graphComponent.repaint();
	}

	private JTextField getJCallGraphScaleTextField() {
		if (callGraphScaleTextField == null) {
			callGraphScaleTextField = new JTextField();
			callGraphScaleTextField.setText("100%");
			callGraphScaleTextField.setMaximumSize(new java.awt.Dimension(50, 25));
			callGraphScaleTextField.addFocusListener(new FocusAdapter() {
				public void focusLost(FocusEvent evt) {
					jCallGraphScaleTextFieldFocusLost(evt);
				}
			});
			callGraphScaleTextField.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent evt) {
					jCallGraphScaleTextFieldKeyPressed(evt);
				}
			});
		}
		return callGraphScaleTextField;
	}

	private void jCallGraphScaleTextFieldKeyPressed(KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
			jCallGraphScaleTextFieldFocusLost(null);
		}
	}

	private void jCallGraphScaleTextFieldFocusLost(FocusEvent evt) {
		int scale = Integer.parseInt(callGraphScaleTextField.getText().replaceAll("%", ""));
		graphComponent.zoomTo((double) scale / 100, true);
	}

	private JTabbedPane getJTabbedPane4() {
		if (tabbedPane4 == null) {
			tabbedPane4 = new JTabbedPane();
			tabbedPane4.setPreferredSize(new java.awt.Dimension(660, 184));
			tabbedPane4.addTab("Information", null, getJPanel4(), null);
			tabbedPane4.addTab("Register", null, getJPanel6(), null);
			tabbedPane4.addTab("TSS", null, getJPanel7(), null);
			tabbedPane4.addTab("GDT", null, getJPanel8(), null);
			tabbedPane4.addTab("IDT", null, getJPanel9(), null);
			tabbedPane4.addTab("LDT", null, getJPanel10(), null);
		}
		return tabbedPane4;
	}

	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			jPanel6 = new JPanel();
			BorderLayout jPanel6Layout = new BorderLayout();
			jPanel6.setLayout(jPanel6Layout);
			jPanel6.add(getJScrollPane5(), BorderLayout.CENTER);
		}
		return jPanel6;
	}

	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			jPanel7 = new JPanel();
			BorderLayout jPanel7Layout = new BorderLayout();
			jPanel7.setLayout(jPanel7Layout);
			jPanel7.add(new TSSPanel(null, 0, BigInteger.valueOf(0), 0, null), BorderLayout.CENTER);
		}
		return jPanel7;
	}

	private JPanel getJPanel8() {
		if (jPanel8 == null) {
			jPanel8 = new JPanel();
		}
		return jPanel8;
	}

	private JPanel getJPanel9() {
		if (jPanel9 == null) {
			jPanel9 = new JPanel();
		}
		return jPanel9;
	}

	private JPanel getJPanel10() {
		if (jPanel10 == null) {
			jPanel10 = new JPanel();
		}
		return jPanel10;
	}

	private RegisterPanel getJCallGraphRegisterPanel() {
		if (callGraphRegisterPanel == null) {
			callGraphRegisterPanel = new RegisterPanel();
		}
		return callGraphRegisterPanel;
	}

	private JScrollPane getJScrollPane5() {
		if (jScrollPane5 == null) {
			jScrollPane5 = new JScrollPane();
			jScrollPane5.setPreferredSize(new java.awt.Dimension(655, 157));
			jScrollPane5.setViewportView(getJCallGraphRegisterPanel());
		}
		return jScrollPane5;
	}

	private JLabel getJLabel11() {
		if (jLabel11 == null) {
			jLabel11 = new JLabel();
			jLabel11.setText(" Unit : ");
		}
		return jLabel11;
	}

	private JLabel getJLabel12() {
		if (jLabel12 == null) {
			jLabel12 = new JLabel();
			jLabel12.setText(" Track distance");
		}
		return jLabel12;
	}

	private JPanel getJPanel11() {
		if (jPanel11 == null) {
			jPanel11 = new JPanel();
			BorderLayout jPanel11Layout = new BorderLayout();
			jPanel11.setLayout(jPanel11Layout);
			jPanel11.add(getJInterruptTabbedPane(), BorderLayout.CENTER);
		}
		return jPanel11;
	}

	private JTabbedPane getJInterruptTabbedPane() {
		if (interruptTabbedPane == null) {
			interruptTabbedPane = new JTabbedPane();
			interruptTabbedPane.addTab("Chart", null, getJPanel12(), null);
		}
		return interruptTabbedPane;
	}

	private JPanel getJInterruptChartPanel() {
		if (interruptChartPanel == null) {
			interruptChartPanel = new JPanel();
			BorderLayout jInterruptChartPanelLayout = new BorderLayout();
			interruptChartPanel.setLayout(jInterruptChartPanelLayout);
			interruptChartPanel.add(getJInterruptChart(), BorderLayout.CENTER);
		}
		return interruptChartPanel;
	}

	private ChartPanel getJInterruptChart() {
		if (jInterruptChart == null) {
			interruptDataset = createInterruptChartDataset();
			interruptChart = createInterruptChart(interruptDataset);
			jInterruptChart = new ChartPanel(interruptChart);

			runTimer();
		}
		return jInterruptChart;
	}

	Hashtable<Integer, TimeSeries> allSeries = new Hashtable<Integer, TimeSeries>();

	private void runTimer() {
		if (interruptTimer != null) {
			interruptTimer.cancel();
		}

		interruptTimer = new Timer();
		interruptTimer.schedule(new TimerTask() {
			Hashtable<Long, Integer> oldInterruptRecords;

			public void run() {
				try {
					synchronized (allSeries) {
						ArrayList<Long> list = Collections.list(InterruptSocketServer.interruptRecords.keys());
						Date d = new Date();
						// int noOfFrame = getTimeframe() / getSpeed();
						oldInterruptRecords = (Hashtable<Long, Integer>) InterruptSocketServer.interruptRecords.clone();
						((InterruptTableModel) interruptTable.getModel()).fireTableDataChanged();

						for (int x = 0; x < list.size(); x++) {
							long interuptNoL = list.get(x);
							int interruptNo = (int) interuptNoL;
							if (allSeries.get(interruptNo) != null) {
								TimeSeries series = allSeries.get(interruptNo);
								List<TimeSeriesDataItem> items = series.getItems();
								for (int z = items.size() - 1; z >= 0; z--) {
									RegularTimePeriod pd = items.get(z).getPeriod();
									Calendar cal1 = Calendar.getInstance();
									cal1.add(Calendar.MILLISECOND, -1 * getTimeframe());
									Calendar cal2 = Calendar.getInstance();
									cal2.setTime(pd.getEnd());
									if (cal1.after(cal2)) {
										series.delete(pd);
									}
								}
								series.add(new Millisecond(d), InterruptSocketServer.interruptRecords.get(interuptNoL) - oldInterruptRecords.get(interuptNoL));
								//							InterruptSocketServer.interruptRecords.put(interuptNoL, 0);
							} else {
								TimeSeries newSeries = new TimeSeries("Int 0x" + Integer.toHexString(interruptNo));
								interruptDataset.addSeries(newSeries);
								allSeries.put(interruptNo, newSeries);
							}
						}

						interruptChart.fireChartChanged();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}, 0, getSpeed());
	}

	private TimeSeriesCollection createInterruptChartDataset() {
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		return dataset;
	}

	private JFreeChart createInterruptChart(final XYDataset dataset) {
		final JFreeChart chart = ChartFactory.createTimeSeriesChart("Interrupt Chart", "Time", "Count", dataset, true, true, false);

		chart.setBackgroundPaint(Color.white);

		final XYPlot plot = chart.getXYPlot();
		// plot.setOutlinePaint(null);
		plot.setBackgroundPaint(Color.black);
		plot.setDomainGridlinePaint(Color.green);
		plot.setRangeGridlinePaint(Color.green);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(false);

		final DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("s.SS"));
		return chart;
	}

	private JScrollPane getJInterruptTableScrollPane() {
		if (interruptTableScrollPane == null) {
			interruptTableScrollPane = new JScrollPane();
			interruptTableScrollPane.setViewportView(getJInterruptTable());
		}
		return interruptTableScrollPane;
	}

	private JTable getJInterruptTable() {
		if (interruptTable == null) {
			interruptTable = new JTable();
			interruptTable.setModel(new InterruptTableModel());
		}
		return interruptTable;
	}

	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane1.setDividerLocation(400);
			jSplitPane1.add(getJInterruptTableScrollPane(), JSplitPane.BOTTOM);
			jSplitPane1.add(getJInterruptChartPanel(), JSplitPane.TOP);
		}
		return jSplitPane1;
	}

	private JPanel getJPanel12() {
		if (jPanel12 == null) {
			jPanel12 = new JPanel();
			BorderLayout jPanel12Layout = new BorderLayout();
			jPanel12.setLayout(jPanel12Layout);
			jPanel12.setPreferredSize(new java.awt.Dimension(787, 577));
			jPanel12.add(getJSplitPane1(), BorderLayout.CENTER);
			jPanel12.add(getJPanel13(), BorderLayout.NORTH);
		}
		return jPanel12;
	}

	private JPanel getJPanel13() {
		if (jPanel13 == null) {
			jPanel13 = new JPanel();
			FlowLayout jPanel13Layout = new FlowLayout();
			jPanel13Layout.setAlignment(FlowLayout.LEFT);
			jPanel13.setLayout(jPanel13Layout);
			jPanel13.add(getJLabel13());
			jPanel13.add(getJTimeframeComboBox());
			jPanel13.add(getJLabel14());
			jPanel13.add(getJSpeedComboBox());
			jPanel13.add(getJLabel15());
			jPanel13.add(getJChartBackgroundComboBox());
			jPanel13.add(getJLabel16());
			jPanel13.add(getJChartGirdColorComboBox());
			jPanel13.add(getJClearInterruptButton());
		}
		return jPanel13;
	}

	private JLabel getJLabel13() {
		if (jLabel13 == null) {
			jLabel13 = new JLabel();
			jLabel13.setText("Timeframe");
		}
		return jLabel13;
	}

	private JComboBox getJTimeframeComboBox() {
		if (timeframeComboBox == null) {
			ComboBoxModel jTimeframeComboBoxModel = new DefaultComboBoxModel(new String[] { "5s", "10s", "30s", "1m", "5m", "10m" });
			timeframeComboBox = new JComboBox();
			timeframeComboBox.setModel(jTimeframeComboBoxModel);
			timeframeComboBox.setPreferredSize(new java.awt.Dimension(84, 22));
			timeframeComboBox.setSelectedItem("10s");
			timeframeComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jTimeframeComboBoxActionPerformed(evt);
				}
			});
		}
		return timeframeComboBox;
	}

	private JLabel getJLabel14() {
		if (jLabel14 == null) {
			jLabel14 = new JLabel();
			jLabel14.setText("Speed");
		}
		return jLabel14;
	}

	private JComboBox getJSpeedComboBox() {
		if (speedComboBox == null) {
			ComboBoxModel jSpeedComboBoxModel = new DefaultComboBoxModel(new String[] { "100ms", "200ms", "250ms", "500ms", "1s", "2s", "5s", "10s" });
			speedComboBox = new JComboBox();
			speedComboBox.setModel(jSpeedComboBoxModel);
			speedComboBox.setPreferredSize(new java.awt.Dimension(97, 22));
			speedComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jSpeedComboBoxActionPerformed(evt);
				}
			});
		}
		return speedComboBox;
	}

	private int getTimeframe() {
		String str = timeframeComboBox.getSelectedItem().toString();
		if (str.contains("m")) {
			return Integer.parseInt(str.replaceAll("m", "")) * 60 * 1000;
		} else if (str.contains("s")) {
			return Integer.parseInt(str.replaceAll("s", "")) * 1000;
		} else {
			return 0;
		}
	}

	private int getSpeed() {
		try {
			String str = speedComboBox.getSelectedItem().toString();
			if (str.contains("m")) {
				return Integer.parseInt(str.replaceAll("ms", ""));
			} else if (str.contains("s")) {
				return Integer.parseInt(str.replaceAll("s", "")) * 1000;
			} else {
				return 100;
			}
		} catch (Exception ex) {
			return 100;
		}
	}

	private void jTimeframeComboBoxActionPerformed(ActionEvent evt) {
		runTimer();
	}

	private void jSpeedComboBoxActionPerformed(ActionEvent evt) {
		runTimer();
	}

	private JLabel getJLabel15() {
		if (jLabel15 == null) {
			jLabel15 = new JLabel();
			jLabel15.setText("Background");
		}
		return jLabel15;
	}

	private JComboBox getJChartBackgroundComboBox() {
		if (chartBackgroundComboBox == null) {
			ComboBoxModel jChartBackgroundComboBoxModel = new DefaultComboBoxModel(new Color[] { Color.black, Color.gray, Color.lightGray, Color.blue, Color.white });
			chartBackgroundComboBox = new JComboBox();
			chartBackgroundComboBox.setModel(jChartBackgroundComboBoxModel);
			chartBackgroundComboBox.setRenderer(new ComboBoxRenderer());
			chartBackgroundComboBox.setPreferredSize(new java.awt.Dimension(67, 22));
			chartBackgroundComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jChartBackgroundComboBoxActionPerformed(evt);
				}
			});
		}
		return chartBackgroundComboBox;
	}

	private void jChartBackgroundComboBoxActionPerformed(ActionEvent evt) {
		interruptChart.getPlot().setBackgroundPaint((Color) chartBackgroundComboBox.getSelectedItem());
	}

	private JLabel getJLabel16() {
		if (jLabel16 == null) {
			jLabel16 = new JLabel();
			jLabel16.setText("Grid color");
		}
		return jLabel16;
	}

	private JComboBox getJChartGirdColorComboBox() {
		if (chartGirdColorComboBox == null) {
			ComboBoxModel jChartGirdColorComboBoxModel = new DefaultComboBoxModel(new Color[] { Color.green, Color.lightGray, Color.white, Color.black, Color.yellow, Color.red,
					Color.blue });
			chartGirdColorComboBox = new JComboBox();
			chartGirdColorComboBox.setModel(jChartGirdColorComboBoxModel);
			chartGirdColorComboBox.setPreferredSize(new java.awt.Dimension(71, 22));
			chartGirdColorComboBox.setRenderer(new ComboBoxRenderer());
			chartGirdColorComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jChartGirdColorComboBoxActionPerformed(evt);
				}
			});
		}
		return chartGirdColorComboBox;
	}

	private void jChartGirdColorComboBoxActionPerformed(ActionEvent evt) {
		((XYPlot) interruptChart.getPlot()).setDomainGridlinePaint((Color) chartGirdColorComboBox.getSelectedItem());
		((XYPlot) interruptChart.getPlot()).setRangeGridlinePaint((Color) chartGirdColorComboBox.getSelectedItem());
	}

	private JScrollPane getJScrollPane6() {
		if (jScrollPane6 == null) {
			jScrollPane6 = new JScrollPane();
			jScrollPane6.setViewportView(getJTextArea1());
		}
		return jScrollPane6;
	}

	private JLabel getJLabel17() {
		if (label17 == null) {
			label17 = new JLabel();
			label17.setText("Information :");
		}
		return label17;
	}

	private JTextArea getJTextArea1() {
		if (textArea1 == null) {
			textArea1 = new JTextArea();
		}
		return textArea1;
	}

	private void jProfilingTableMouseClicked(MouseEvent evt) {
		loadInformation(sortCheckBox.isSelected(), profilingTable.getValueAt(profilingTable.getSelectedRow(), 4).toString());
	}

	private JCheckBox getJSortCheckBox() {
		if (sortCheckBox == null) {
			sortCheckBox = new JCheckBox();
			sortCheckBox.setText("Sort");
			sortCheckBox.setSelected(true);
			sortCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jSortCheckBoxActionPerformed(evt);
				}
			});
		}
		return sortCheckBox;
	}

	private void loadInformation(boolean sort, String str) {
		String address[] = str.split(":");
		HashSet<BigInteger> c = new HashSet<BigInteger>();
		for (int x = 0; x < address.length; x++) {
			try {
				c.add(CommonLib.string2BigInteger(address[x]));
			} catch (Exception ex) {

			}
		}
		Vector<BigInteger> v = new Vector<BigInteger>();

		Iterator<BigInteger> it = c.iterator();
		while (it.hasNext()) {
			BigInteger element = it.next();
			v.add(element);
		}

		if (sort) {
			Collections.sort(v);
		}
		textArea1.setText("");
		Iterator<BigInteger> it2 = v.iterator();
		while (it2.hasNext()) {
			BigInteger element = it2.next();
			textArea1.setText(textArea1.getText() + "\n0x" + element.toString(16));
		}
	}

	private void jSortCheckBoxActionPerformed(ActionEvent evt) {
		loadInformation(sortCheckBox.isSelected(), profilingTable.getValueAt(profilingTable.getSelectedRow(), 4).toString());
	}

	private JPanel getJPanel14() {
		if (panel14 == null) {
			panel14 = new JPanel();
			BorderLayout jPanel14Layout = new BorderLayout();
			panel14.setLayout(jPanel14Layout);
			panel14.setPreferredSize(new java.awt.Dimension(759, 358));
			panel14.add(getJMemory3DPanel(), BorderLayout.CENTER);
			panel14.add(getJPanel15(), BorderLayout.SOUTH);
		}
		return panel14;
	}

	private JPanel getJPanel15() {
		if (panel15 == null) {
			panel15 = new JPanel();
			FlowLayout jPanel15Layout = new FlowLayout();
			jPanel15Layout.setAlignment(FlowLayout.LEFT);
			panel15.setLayout(jPanel15Layout);
			panel15.add(getJInvisible3dChartButton());
		}
		return panel15;
	}

	private JButton getJInvisible3dChartButton() {
		if (invisible3dChartButton == null) {
			invisible3dChartButton = new JButton();
			invisible3dChartButton.setText(MyLanguage.getString("Visible"));
			invisible3dChartButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jInvisible3dChartButtonActionPerformed(evt);
				}
			});
		}
		return invisible3dChartButton;
	}

	private void jInvisible3dChartButtonActionPerformed(ActionEvent evt) {
		if (invisible3dChartButton.getText().equals(MyLanguage.getString("Visible"))) {
			invisible3dChartButton.setText(MyLanguage.getString("Invisible"));
			jMemory3DPanel.setVisible(true);
		} else {
			invisible3dChartButton.setText(MyLanguage.getString("Visible"));
			jMemory3DPanel.setVisible(false);
			jMemory3DPanel.repaint();
		}
		jMemory3DPanel.revalidate();
	}

	private JButton getJClearInterruptButton() {
		if (clearInterruptButton == null) {
			clearInterruptButton = new JButton();
			clearInterruptButton.setText("Clear");
			clearInterruptButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jClearInterruptButtonActionPerformed(evt);
				}
			});
		}
		return clearInterruptButton;
	}

	private void jClearInterruptButtonActionPerformed(ActionEvent evt) {
		InterruptSocketServer.interruptRecords.clear();
	}
}
