package com.gkd;

import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.apple.eawt.ApplicationEvent;
import com.apple.eawt.ApplicationListener;
import com.gkd.components.segmentregister.SegmentRegister;
import com.gkd.elf.ElfUtil;
import com.gkd.helprequest.HelpRequestDialog;
import com.gkd.instrument.InstrumentPanel;
import com.gkd.logpanel.LogPanel;
import com.gkd.osdebuginformation.JOSDebugInformationPanel;
import com.gkd.osdebuginformation.OSDebugInfoHelper;
import com.gkd.sourceleveldebugger.SourceLevelDebugger3;
import com.gkd.stub.VMController;
import com.gkd.stub.VMType;
import com.gkd.webservice.WebServiceUtil;
import com.peter.tightvncpanel.TightVNC;
import com.peterdwarf.dwarf.Dwarf;
import com.peterdwarf.dwarf.DwarfDebugLineHeader;
import com.peterdwarf.dwarf.DwarfLine;
import com.peterswing.CommonLib;
import com.peterswing.advancedswing.diskpanel.DiskPanel;
import com.peterswing.advancedswing.enhancedtextarea.EnhancedTextArea;
import com.peterswing.advancedswing.jdropdownbutton.JDropDownButton;
import com.peterswing.advancedswing.jmaximizabletabbedpane.JMaximizableTabbedPane;
import com.peterswing.advancedswing.jmaximizabletabbedpane.JMaximizableTabbedPane_BasePanel;
import com.peterswing.advancedswing.jprogressbardialog.JProgressBarDialog;
import com.peterswing.advancedswing.jprogressbardialog.JProgressBarDialogEventListener;
import com.peterswing.advancedswing.jvmdialog.JVMInfoDialog;
import com.peterswing.advancedswing.searchtextfield.JSearchTextField;

@SuppressWarnings("serial")
public class GKD extends JFrame implements WindowListener, ApplicationListener, JProgressBarDialogEventListener {
	private JMenuItem aboutUsMenuItem;
	private JPanel memoryPanel;
	private JDropDownButton stepVMButton;
	private JMenu jMenu5;
	private JScrollPane registerPanelScrollPane;
	private JScrollPane jScrollPane2;
	private JMaximizableTabbedPane jTabbedPane1;
	private HexTable hexTable;
	private JEditorPane vmCommandEditorPane;
	private JSplitPane jSplitPane1;
	private RegisterPanel registerPanel;
	private JMenuItem runBochsMenuItem;
	private JSeparator jSeparator1;
	private JMenuItem stopBochsMenuItem;
	private JMenuItem startVMMenuItem;
	private JMenu bochsMenu;
	private JMenuItem exitMenuItem;
	private JSeparator jSeparator2;
	public JDropDownButton runVMButton;
	private JButton stopVMButton;
	private JButton startVMButton;
	private JToolBar jToolBar1;
	private JPanel jPanel7;
	private JPanel jPanel6;
	private JPanel jPanel5;
	private JMaximizableTabbedPane tabbedPane3;
	private JMenuItem pauseVMMenuItem;
	private JPanel jPanel3;
	public JMaximizableTabbedPane bottomTabbedPane;
	private JButton vmCommandButton;
	private JTextField vmCommandTextField;
	private JPanel jPanel2;
	private JPanel vmPanel;
	public static JTable instructionTable;
	private JScrollPane instructionTableScrollPane;
	private JScrollPane vmCommandScrollPane4;
	public JComboBox<String> memoryAddressComboBox;
	private JButton deleteBreakpointButton;
	private JPanel jPanel12;
	private JTable breakpointTable;
	private JButton disableBreakpointButton;
	private JButton enableBreakpointButton;
	private JButton saveBreakpointButton;
	private JButton addBreakpointButton;
	private JButton jRefreshBreakpointButton;
	private JScrollPane jScrollPane9;
	private JPanel jPanel4;
	private JScrollPane jScrollPane6;
	private JLabel jLabel3;
	private JScrollPane jScrollPane8;
	private JScrollPane jScrollPane7;
	private JTable pageTableTable;
	private JTable pageDirectoryTable;
	private JPanel pagingPanel;
	private JTable gdtTable;
	private JScrollPane jScrollPane3;
	private JMenuItem jupdateVMStatusMenuItem;
	private JButton goMemoryButton;
	private JPanel jPanel9;
	private JMenu jMenu3;
	private JMenuBar jMenuBar1;
	//	private static BufferedWriter commandOutputStream;
	private JSplitPane jSplitPane2;
	private JProgressBar statusProgressBar;
	private JPanel statusPanel;
	private JButton updateBochsButton;
	private JLabel statusLabel;
	private JButton disassembleCurrentIPButton;
	private JComboBox<String> instructionComboBox;
	private JToolBar instructionControlPanel;
	private JPanel instructionPanel;
	private JDropDownButton loadBreakpointButton;
	private int commandHistoryIndex;
	private JScrollPane scrollPane10;
	private ButtonGroup buttonGroup1;
	private JRadioButton hexRadioButton;
	private JRadioButton decRadioButton;
	private JRadioButton octRadioButton1;
	private JRadioButton binaryRadioButton;
	private JTable ldtTable;
	private JScrollPane jScrollPane11;
	private JTable idtTable;
	private JTable addressTranslateTable;
	private JTable addressTranslateTable2;
	private JPanel jPanel22;
	private JPanel jPanel24;
	private JToolBar jPanel26;
	private JPanel vncPanel;
	private JMenuItem disasmFromEIPMinus100MenuItem;
	private JButton nextOverButton;
	private JButton nextButton;
	private JMenuItem licenseMenuItem;
	private JMenuItem stepOverNTimesMenuItem;
	private JMenuItem stepOver100MenuItem;
	private JMenuItem stepOver10MenuItem;
	private JDropDownButton stepOverDropDownButton;
	private JMenuItem runCustomCommandMenuItem;
	private JButton helpButton;
	private EnhancedTextArea bochsoutTextArea;
	private JPanel jPanel31;
	private JMenuItem shortcutHelpMenuItem;
	private JDropDownButton sbaButton;
	private JDropDownButton sbButton;
	private JLabel jLabel9;
	private JLabel jLabel8;
	private JLabel jLabel7;
	private JSpinner showAfterwardSpinner;
	private JMenuItem runBochsAndSkipBreakpointMenuItem;
	private JSearchTextField filterHistoryTableTextField;
	private JLabel jLabel2;
	private JLabel historyTableRepeatedLabel;
	private JButton clearRunningTextAreaButton;
	private JButton clearHistoryTableButton;
	private ButtonGroup buttonGroup4;
	public JMenuItem clearInstructionTableMenuItem;
	private JMenuItem loadSystemMapMenuItem;
	private JMenu systemMenu;
	private JToggleButton sourceLevelDebuggerToggleButton;
	private SourceLevelDebugger3 sourceLevelDebugger;
	public JMenuItem disasmHereMenuItem;
	private JSeparator jSeparator3;
	private JRadioButton mmxRadioButton;
	private JRadioButton fpuRadioButton;
	private JButton previousMemoryButton;
	private JButton nextMemoryPageButton;
	private JComboBox<String> maxRowComboBox;
	private JLabel maximumRowLabel;
	private JCheckBox saveToRunDotTxtCheckBox;
	private JCheckBox autoUpdateEvery20LinesCheckBox;
	private JCheckBox jDisableAutoUpdateCheckBox;
	private JLabel stepCountLabel;
	private JButton pauseButton;
	private EnhancedTextArea jTextArea1;
	private JLabel runningLabel;
	private JPanel runningPanel;
	private JMenuItem stepUntilIPBigChangeMenuItem;
	private JMenuItem jvmMenuItem;
	private JMenuItem stepUntilMovMenuItem;
	private JMenuItem stepUntilIRetMenuItem;
	private JMenuItem stepUntilRetMenuItem;
	private JMenuItem stepUntilCallOrJumpMenuItem;
	private JMenuItem stepNMenuItem;
	private JMenuItem step100MenuItem;
	private JMenuItem step10MenuItem;
	private JPanel jPanel30;
	private JMenuItem helpRequestMenuItem;
	private EnhancedTextArea osLogPanel;
	private JToggleButton osLogToggleButton;
	private JToggleButton registerToggleButton;
	private LogPanel logPanel;
	private JToggleButton profilerToggleButton;
	public static InstrumentPanel instrumentPanel;
	private JOSDebugInformationPanel osDebugInformationPanel1;
	private JLabel osDebugInfoErrorLabel;
	private JTabbedPane tabbedPane5;
	private JPanel osDebugStandardPanel;
	private JButton settingButton;
	private JMenuItem setELFPhysicalBreakpointMenuItem;
	private JMenuItem setELFLinearBreakpointMenuItem;
	private JPopupMenu elfTablePopupMenu;
	public JButton fastStepBochsButton;
	private JMenuItem japaneseMenuItem;
	private JMenuItem koreanMenuItem;
	private JButton instructionUpTenButton;
	private JButton instructionDownButton;
	private JButton instructionUpButton;
	private JMenuItem setLinerBreakpointHereMenuItem;
	private JMenuItem setPhysicalBreakpointHereMenuItem;
	private JPopupMenu searchMemoryTablePopupMenu;
	private JMenuItem disassembleMenuItem;
	private JMenuItem dumpHereMenuItem;
	private JPopupMenu breakpointPopupMenu;
	private JMenuItem setLinearBreakpointMenuItem;
	private JMenuItem setPhysicalBreakpointMenuItem;
	private JPopupMenu instructionPanelPopupMenu;
	private JCheckBox hideIfAddressIsZeroCheckBox;
	private JMaximizableTabbedPane_BasePanel maximizableTabbedPane_BasePanel1;
	private DiskPanel diskPanel;
	private JButton goLinearButton;
	private JButton clearBochsButton;
	public JTextField dumpPageDirectoryAddressTextField;
	private JButton dumpPageTableAtAddressButton;
	private JButton dumpCR3Button;
	private JButton jButton19;
	private JTable programHeaderTable;
	private JScrollPane jScrollPane16;
	private JTable elfSectionTable;
	private JScrollPane jScrollPane15;
	private JTable elfHeaderTable;
	private JScrollPane elfHeaderScrollPane;
	private JMaximizableTabbedPane tabbedPane4;
	private JButton openELFDumpButton;
	private JComboBox<File> elfComboBox;
	private JPanel elfDumpPanel;
	private JLabel latestVersionLabel;
	private JLabel bochsVersionLabel;
	private JCheckBox showELFByteCheckBox;
	private JLabel cpuModeLabel;
	private JPanel jPanel25;
	private JButton loadELFBreakpointButton;
	private JButton saveELFBreakpointButton;
	private JButton disableELFBreakpointButton;
	private JButton enableELFBreakpointButton;
	private JButton refreshELFBreakpointButton;
	private JTable elfTable;
	private JScrollPane jScrollPane14;
	private JComboBox<String> elfFileComboBox;
	private JButton openELFButton;
	private JToolBar elfToolbar;
	private JPanel elfBreakpointPanel;
	private JMenuItem disassemble32MenuItem;
	private JMenuItem pteMenuItem;
	private JMenuItem pdeMenuItem;
	private JMenuItem idtDescriptorMenuItem;
	private JMenuItem idtMenuItem;
	private JMenuItem gdtDescriptorMenuItem;
	private JMenuItem gdtMenuItem;
	private JPopupMenu hexTablePopupMenu;
	private JTextField addressTextField;
	private JPanel jPanel21;
	private ButtonGroup buttonGroup3;
	private JButton jButton18;
	private JButton jButton17;
	private JToolBar jToolBar3;
	private JScrollPane jScrollPane13;
	private JButton refreshAddressTranslateButton;
	private JPanel jPanel20;
	private JRadioButton searchPhysicalAddressRadioButton;
	private JRadioButton searchLinearAddressRadioButton;
	private JRadioButton searchVirtualAddressRadioButton;
	private JPanel addressTranslatePanel;
	private JButton refreshPageTableGraphButton;
	private JCheckBox autoRefreshPageTableGraphCheckBox;
	private JToolBar jToolBar2;
	private JPanel pageTableGraphPanel;
	private JButton pagingGraphButton;
	private JToolBar panel19;
	private JButton gdtGraphButton;
	private JLabel jRunningLabel;
	public JPanel mainPanel;
	private JButton jButton15;
	private JButton diskButton;
	private JButton excelButton2;
	private JButton saveImageButton;
	private JButton excelIDTButton;
	private JPanel jPanel16;
	private JPanel jPanel15;
	private JButton excelGDTButton;
	private JButton saveGDTImageButton;
	private JPanel jPanel14;
	private JRadioButton tblRadioButton;
	private JToolBar jPanel13;
	private JRadioButton regRadioButton;
	private JTable historyTable;
	private JMenuItem dialogMenuItem;
	private JMenuItem arialMenuItem;
	private JMenu fontMenu;
	private JMenu sizeMenu;
	private JScrollPane tableTranslateScrollPane;
	public static CommandLine cmd;
	private JMenuItem font14MenuItem;
	private JMenuItem font12MenuItem;
	private JMenuItem font10MenuItem;
	private JMenuItem font8MenuItem;
	private JMenu topFontMenu;
	private JMenuItem simplifiedChineseMenuItem;
	private JMenuItem traditionalChineseMenuItem;
	private JMenuItem englishMenuItem;
	private JMenu languageMenu;
	private JSplitPane jSplitPane3;
	private JButton saveHistoryTableImageButton;
	private JButton saveHexTableImageButton;
	private JButton saveInstructionTableImageButton;
	private JButton exportHistoryToExcelButton;
	private JButton excelMemoryButton;
	private JButton excelButton;
	private JButton exportToExcelButton;
	private JPanel panel17;
	private JTable searchMemoryTable;
	private JScrollPane jScrollPane12;
	private JPanel searchMemoryControlPanel;
	private JButton searchMemoryButton;
	private JComboBox<String> searchMemoryToComboBox;
	private JLabel toLabel;
	private JComboBox<String> searchMemoryFromComboBox;
	private JLabel fromLabel;
	private JTextField searchMemoryTextField;
	private JLabel hexDecStringLabel;
	private JButton disassembleButton;
	public static ResourceBundle language;
	private JButton refreshAddressTranslateTableButton;

	private BigInteger currentMemoryWindowsAddress;
	public static Logger logger = Logger.getLogger(GKD.class);

	public enum OSType {
		mac, win, linux
	};

	public static OSType os;

	public static String version = "";
	private JButton jButton21;
	public static String bochsrc;
	static boolean preventSetVisibleHang = true;

	boolean breakpointLoadedOnce = false;
	boolean systemMapLoadedOnce = false;
	private JScrollPane jScrollPane17;
	private JEditorPane jEditorPane1;
	private JButton searchObjdumpButton;
	private JTextField searchObjdumpTextField;
	private JToolBar jPanel27;
	private JPanel objdumpPanel;
	private JButton searchRelPltButton;
	private JTextField searchRelPltTextField;
	private JToolBar jToolBar4;
	private JEditorPane jSearchRelPltEditorPane;
	private JScrollPane jScrollPane18;
	private JPanel jPanel28;

	private JEditorPane searchDynamicEditorPane;
	private JScrollPane jScrollPane19;
	private JButton searchDynamicButton;
	private JTextField searchDynamicTextField;
	private JToolBar jToolBar5;
	private JPanel jPanel29;

	public String currentPanel = "jMaximizableTabbedPane_BasePanel1";

	private ButtonGroup buttonGroup2 = new ButtonGroup();
	private JMenuItem loadElfMenuItem = new JMenuItem("Load ELF");
	private String latestVersionURL;
	private boolean saveToRunDotTxt;
	private int skipBreakpointTime;
	private boolean isupdateVMStatusEnd;
	Vector<CustomCommand> customCommandQueue = new Vector<CustomCommand>();
	URL url = getClass().getClassLoader().getResource("com/gkd/images/ajax-loader.gif");
	private JProgressBarDialog progressBarDialog = new JProgressBarDialog();

	TableModel jBreakpointTableModel = new DefaultTableModel(new String[][] {}, new String[] { MyLanguage.getString("No"), MyLanguage.getString("Address_type"),
			"Disp Enb Address", MyLanguage.getString("Hit") }) {
		public boolean isCellEditable(int row, int col) {
			return false;
		}
	};

	public GKD() {
		super();
		progressBarDialog.setTitle("Starting GKD");

		if (os == OSType.mac) {
			com.apple.eawt.Application macApp = com.apple.eawt.Application.getApplication();
			macApp.setDockIconImage(new ImageIcon(GKD.class.getClassLoader().getResource("com/gkd/icons/peter.png")).getImage());
			macApp.addApplicationListener(this);
		}
		// $hide>>$
		Thread loadThread = new Thread("gkd.init()") {
			public void run() {
				// $hide<<$
				init();
				//$hide>>$
				if (Global.debug) {
					logger.debug("setVisible(true)");
				}

				//new Thread(new BochsoutTimer()).start();

				setVisible(true);

				preventSetVisibleHang = false;
				if (Global.debug) {
					logger.debug("end setVisible(true)");
				}
			}
		};
		progressBarDialog.jProgressBar.setMinimum(0);
		progressBarDialog.jProgressBar.setMaximum(100);
		progressBarDialog.jProgressBar.setStringPainted(true);
		progressBarDialog.thread = loadThread;
		progressBarDialog.addCancelEventListener(this);
		progressBarDialog.setVisible(true);
		//$hide<<$
		//		new Thread("preventSetVisibleHang thread") {
		//			public void run() {
		//				try {
		//					Thread.sleep(1000000);
		//					if (preventSetVisibleHang) {
		//						logger.debug("setVisible(true) cause system hang, this probably a swing bug, so force exit, please restart");
		//						System.exit(-1);
		//					}
		//				} catch (InterruptedException e) {
		//					e.printStackTrace();
		//				}
		//			}
		//		}.start();
	}

	public static void main(String[] args) {
		WebServiceUtil.log("gkd", "start", null, null, null);

		try {
			UIManager.setLookAndFeel("com.peterswing.white.PeterSwingWhiteLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		CommandLineParser parser = new PosixParser();
		Options options = new Options();
		try {
			options.addOption(OptionBuilder.withDescription("specific config xml").hasArg().withArgName("file").create("f"));
			options.addOption("v", "version", false, "display version info");
			options.addOption("debug", false, "display debug info to stdout");
			cmd = parser.parse(options, args);
		} catch (ParseException e1) {
			e1.printStackTrace();
			System.exit(1);
		}

		if (cmd.hasOption("version") || cmd.hasOption("v")) {
			logger.debug("version : " + Global.version);
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("java -jar GDK.jar [OPTION]", options);
			System.exit(1);
		}

		if (!cmd.hasOption("f")) {
			String errorMessage = "Please specific the config xml by -f <file>";
			System.err.println(errorMessage);
			JOptionPane.showMessageDialog(null, errorMessage);
			System.exit(1);
		}

		if (ArrayUtils.contains(args, "-debug")) {
			Global.debug = true;
			args = (String[]) ArrayUtils.removeElement(args, "-debug");
		} else {
			Global.debug = false;
		}

		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.toLowerCase().contains("windows")) {
			os = OSType.win;
		} else if (osName.toLowerCase().contains("mac")) {
			os = OSType.mac;
		} else {
			os = OSType.linux;
		}

		try {
			if (GKD.class.getProtectionDomain().getCodeSource().getLocation().getFile().endsWith(".jar")) {
				JarFile jarFile = new JarFile(GKD.class.getProtectionDomain().getCodeSource().getLocation().getFile());
				if (System.getProperty("os.name").toLowerCase().contains("linux")) {
					if (System.getProperty("os.arch").contains("64")) {
						if (Global.debug) {
							logger.debug("Loading linux 64 bits jogl");
						}
						CommonLib.writeFile(jarFile.getInputStream(new JarEntry("com/gkd/jogl_dll/linux_amd64/libgluegen-rt.so")), new File("libgluegen-rt.so"));
						CommonLib.writeFile(jarFile.getInputStream(new JarEntry("com/gkd/jogl_dll/linux_amd64/libjogl_awt.so")), new File("libjogl_awt.so"));
						CommonLib.writeFile(jarFile.getInputStream(new JarEntry("com/gkd/jogl_dll/linux_amd64/libjogl_cg.so")), new File("libjogl_cg.so"));
						CommonLib.writeFile(jarFile.getInputStream(new JarEntry("com/gkd/jogl_dll/linux_amd64/libjogl.so")), new File("libjogl.so"));
					} else {
						if (Global.debug) {
							logger.debug("Loading linux 32 bits jogl");
						}
						CommonLib.writeFile(jarFile.getInputStream(new JarEntry("com/gkd/jogl_dll/linux_i586/libgluegen-rt.so")), new File("libgluegen-rt.so"));
						CommonLib.writeFile(jarFile.getInputStream(new JarEntry("com/gkd/jogl_dll/linux_i586/libjogl_awt.so")), new File("libjogl_awt.so"));
						CommonLib.writeFile(jarFile.getInputStream(new JarEntry("com/gkd/jogl_dll/linux_i586/libjogl_cg.so")), new File("libjogl_cg.so"));
						CommonLib.writeFile(jarFile.getInputStream(new JarEntry("com/gkd/jogl_dll/linux_i586/libjogl.so")), new File("libjogl.so"));
					}
					try {
						File f = new File(".");
						Runtime.getRuntime().load(f.getAbsolutePath() + File.separator + "libjogl.so");
						logger.debug("Loading " + f.getAbsolutePath() + File.separator + "libjogl.so");
						Runtime.getRuntime().load(f.getAbsolutePath() + File.separator + "libjogl_awt.so");
						Runtime.getRuntime().load(f.getAbsolutePath() + File.separator + "libjogl_cg.so");
						Runtime.getRuntime().load(f.getAbsolutePath() + File.separator + "libgluegen-rt.so");
					} catch (UnsatisfiedLinkError e) {
						e.printStackTrace();
						System.err.println("Native code library failed to load.\n" + e);
						System.err.println("Solution : Please add \"-Djava.library.path=.\" to start peter-bochs\n" + e);
					}
				} else if (System.getProperty("os.name").toLowerCase().contains("windows")) {
					CommonLib.writeFile(jarFile.getInputStream(new JarEntry("com/gkd/exe/PauseBochs.exe")), new File("PauseBochs.exe"));
					CommonLib.writeFile(jarFile.getInputStream(new JarEntry("com/gkd/exe/StopBochs.exe")), new File("StopBochs.exe"));
					CommonLib.writeFile(jarFile.getInputStream(new JarEntry("com/gkd/exe/ndisasm.exe")), new File("ndisasm.exe"));

					if (System.getProperty("os.arch").contains("64")) {
						if (Global.debug) {
							logger.debug("Loading windows 64 bits jogl");
						}
						CommonLib.writeFile(jarFile.getInputStream(new JarEntry("com/gkd/jogl_dll/windows_amd64/jogl.dll")), new File("jogl.dll"));
						CommonLib.writeFile(jarFile.getInputStream(new JarEntry("com/gkd/jogl_dll/windows_amd64/jogl_awt.dll")), new File("jogl_awt.dll"));
						CommonLib.writeFile(jarFile.getInputStream(new JarEntry("com/gkd/jogl_dll/windows_amd64/jogl_cg.dll")), new File("jogl_cg.dll"));
						CommonLib.writeFile(jarFile.getInputStream(new JarEntry("com/gkd/jogl_dll/windows_amd64/gluegen-rt.dll")), new File("gluegen-rt.dll"));
					} else {
						if (Global.debug) {
							logger.debug("Loading windows 32 bits jogl");
						}
						CommonLib.writeFile(jarFile.getInputStream(new JarEntry("com/gkd/jogl_dll/windows_i586/jogl.dll")), new File("jogl.dll"));
						CommonLib.writeFile(jarFile.getInputStream(new JarEntry("com/gkd/jogl_dll/windows_i586/jogl_awt.dll")), new File("jogl_awt.dll"));
						CommonLib.writeFile(jarFile.getInputStream(new JarEntry("com/gkd/jogl_dll/windows_i586/jogl_cg.dll")), new File("jogl_cg.dll"));
						CommonLib.writeFile(jarFile.getInputStream(new JarEntry("com/gkd/jogl_dll/windows_i586/gluegen-rt.dll")), new File("gluegen-rt.dll"));
					}
					try {
						File f = new File(".");
						System.load(f.getAbsolutePath() + File.separator + "jogl.dll");
						System.load(f.getAbsolutePath() + File.separator + "jogl_awt.dll");
						System.load(f.getAbsolutePath() + File.separator + "jogl_cg.dll");
						System.load(f.getAbsolutePath() + File.separator + "gluegen-rt.dll");
					} catch (UnsatisfiedLinkError e) {
						e.printStackTrace();
						System.err.println("Native code library failed to load.\n" + e);
						System.err.println("Solution : Please add \"-Djava.library.path=.\" to start peter-bochs\n" + e);
					}
				}
				jarFile.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		String temp = GKDCommonLib.readConfig(cmd, "/gkd/vmType/text()");
		if (temp.equals("bochs")) {
			VMController.vmType = VMType.Bochs;
		} else if (temp.equals("qemu")) {
			VMController.vmType = VMType.Qemu;
		}

		if (VMController.vmType == VMType.Bochs) {
			for (String str : GKDCommonLib.readConfig(cmd, "/gkd/vmArguments/text()").split(" ")) {
				if (str.contains("bochsrc") || str.contains(".bxrc")) {
					bochsrc = str;
					break;
				}
			}
		}
		Global.ndisasmPath = GKDCommonLib.readConfig(cmd, "/gkd/ndisasm/text()");

		Global.stopCommand = GKDCommonLib.readConfig(cmd, "/gkd/stopCommand/text()");
		if (VMController.vmType != VMType.Bochs && VMController.vmType != VMType.Qemu) {
			System.err.println("vmtype : \"" + VMController.vmType + "\" not supported");
		}
		if (!new File(cmd.getOptionValue("f")).exists()) {
			System.err.println(cmd.getOptionValue("f") + " not exist");
			System.exit(-1);
		}
		if (VMController.vmType != VMType.Bochs && VMController.vmType != VMType.Qemu) {
			System.err.println("<vmType> only supports qemu and bochs");
		}
		if (VMController.vmType == VMType.Qemu) {
			//libGDB = new JLibGDB("localhost", Integer.parseInt(GKDCommonLib.readConfig(cmd, "/gkd/gkd_server_port/text()")));
			VMController.getVM().initStub(new String[] { "localhost", GKDCommonLib.readConfig(cmd, "/gkd/gkd_server_port/text()") });
		}

		Setting.getInstance().loadBreakpointAtStartup = Boolean.parseBoolean(GKDCommonLib.readConfig(cmd, "/gkd/loadBreakpoint/text()"));

		Global.elfPaths = GKDCommonLib.readConfig(cmd, "/gkd/elf/text()").split(",");

		final GKD gkd = new GKD();
		VMController.getVM().setGKDInstance(gkd);
		VMController.getVM().setVMPath(GKDCommonLib.readConfig(cmd, "/gkd/vm/text()"));
		VMController.getVM().setVMArguments(GKDCommonLib.readConfig(cmd, "/gkd/vmArguments/text()"));
	}

	public void init() {
		progressBarDialog.jProgressBar.setValue(0);
		progressBarDialog.jProgressBar.setString("Loading GUI");
		if (Global.debug) {
			logger.debug(new Date());
		}

		if (Global.debug) {
			logger.debug("initGUI()");
		}

		progressBarDialog.jProgressBar.setValue(10);
		progressBarDialog.jProgressBar.setString("Init GUI");
		initGUI();
		if (Global.debug) {
			logger.debug("end initGUI()");
		}
		if (VMController.vmType == VMType.Qemu) {
			bochsMenu.setVisible(false);
		}

		progressBarDialog.jProgressBar.setValue(80);
		progressBarDialog.jProgressBar.setString("Starting VM");

		VMController.getVM().startVM();

		progressBarDialog.jProgressBar.setValue(90);
		progressBarDialog.jProgressBar.setString("Init font");
		initChineseFont();
		new Thread("checkLatestVersion thread") {
			public void run() {
				HashMap<String, String> map = GKDCommonLib.checkLatestVersion();
				if (Global.debug) {
					logger.debug("finished checkLatestVersion()");
					logger.debug("checkLatestVersion()=" + map.get("latestVersion"));
				}
				if (map != null) {
					if (map.get("latestVersion") != null && map.get("latestVersion").compareTo(Global.version) > 0) {
						latestVersionLabel.setText(MyLanguage.getString("Latest_version_available") + " : " + map.get("latestVersion"));
						latestVersionURL = map.get("downloadURL");
					} else {
						latestVersionLabel.setText("");
					}
				} else {
					latestVersionLabel.setText("");
				}
			}
		}.start();

		if (VMController.vmType == VMType.Bochs) {
			bochsoutTextArea.addTrailListener(new File("."), new File("bochsout.txt"), 0, true);
		}

		progressBarDialog.jProgressBar.setValue(100);
		progressBarDialog.jProgressBar.setString("Fnished");

		if (Global.debug) {
			logger.debug(new Date());
		}
	}

	private void waitUpdateFinish() {
		while (!isupdateVMStatusEnd) {
			try {
				Thread.currentThread();
				Thread.sleep(200);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void initGUI() {
		Date startDate = new Date();
		System.out.println("starting GKD " + new SimpleDateFormat("m:s S").format(startDate));
		try {
			language = Utf8ResourceBundle.getBundle("language_" + Setting.getInstance().currentLanguage);

			// $hide>>$
			if (os == OSType.win) {
				if (!new File("PauseBochs.exe").exists() || !new File("StopBochs.exe").exists()) {
					JOptionPane.showMessageDialog(null, MyLanguage.getString("PauseBochsExe"), MyLanguage.getString("Error"), JOptionPane.ERROR_MESSAGE);
					System.exit(1);
				}
				if (!new File("ndisasm.exe").exists()) {
					JOptionPane.showMessageDialog(null, MyLanguage.getString("NdisasmExe"), MyLanguage.getString("Error"), JOptionPane.ERROR_MESSAGE);
					System.exit(1);
				}
			}
			// $hide<<$
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			progressBarDialog.jProgressBar.setValue(30);
			progressBarDialog.jProgressBar.setString("Init GUI - 1");
			{
				this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				if (Global.isBeta) {
					this.setTitle(MyLanguage.getString("Title") + " " + Global.version + " , This is beta version, if you found a bug, please try older official release");
				} else {
					this.setTitle(MyLanguage.getString("Title") + " " + Global.version);
				}

				this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/peter.png")).getImage());
				this.addWindowListener(new WindowAdapter() {
					public void windowOpened(WindowEvent evt) {
						thisWindowOpened(evt);
					}

					public void windowActivated(WindowEvent evt) {
						thisWindowActivated(evt);
					}

					public void windowClosing(WindowEvent evt) {
						thisWindowClosing(evt);
					}
				});
			}
			progressBarDialog.jProgressBar.setValue(40);
			progressBarDialog.jProgressBar.setString("Init GUI - 2");
			{
				jToolBar1 = new JToolBar();
				getContentPane().add(jToolBar1, BorderLayout.NORTH);
				{
					startVMButton = new JButton();
					jToolBar1.add(startVMButton);
					startVMButton.setText(MyLanguage.getString("Start"));
					startVMButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/accept.png")));
					startVMButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							startVMButtonActionPerformed(evt);
						}
					});
				}
				{
					stopVMButton = new JButton();
					jToolBar1.add(stopVMButton);
					stopVMButton.setText(MyLanguage.getString("Stop"));
					stopVMButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/stop.png")));
					stopVMButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							stopVMButtonActionPerformed(evt);
						}
					});
				}
				{
					runVMButton = new JDropDownButton();
					jToolBar1.add(runVMButton);
					runVMButton.setText(MyLanguage.getString("run"));
					runVMButton.setToolTipText("Start emulation");
					runVMButton.setMaximumSize(new java.awt.Dimension(85, 26));
					runVMButton.add(getRunBochsAndSkipBreakpointMenuItem());
					runVMButton.add(getRunCustomCommandMenuItem());
					runVMButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/resultset_next.png")));
					runVMButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							runVMButtonActionPerformed(evt);
						}
					});
				}
				{
					stepVMButton = new JDropDownButton();
					jToolBar1.add(stepVMButton);
					jToolBar1.add(getStepOverDropDownButton());
					jToolBar1.add(getFastStepBochsButton());
					stepVMButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/step.png")));
					stepVMButton.setText(MyLanguage.getString("Step"));
					stepVMButton.setMaximumSize(new java.awt.Dimension(85, 26));
					stepVMButton.add(getStep10MenuItem());
					stepVMButton.add(getStep100MenuItem());
					stepVMButton.add(getStepNMenuItem());
					stepVMButton.add(getStepUntilCallOrJumpMenuItem());
					stepVMButton.add(getStepUntilRetMenuItem());
					stepVMButton.add(getStepUntilIRetMenuItem());
					stepVMButton.add(getStepUntilMovMenuItem());
					stepVMButton.add(getStepUntilIPBigChangeMenuItem());
					stepVMButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							stepVMButtonActionPerformed(evt);
						}
					});
				}
				{
					nextButton = new JButton();
					nextButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/step.png")));
					nextButton.setText(MyLanguage.getString("Nexti"));
					nextButton.setToolTipText("c/c++ level step-in");
					jToolBar1.add(nextButton);
					jToolBar1.add(getNextOverButton());
					nextButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							nextButtonActionPerformed(evt);
						}
					});
				}
				{
					updateBochsButton = new JButton();
					jToolBar1.add(updateBochsButton);
					jToolBar1.add(getExportToExcelButton());
					jToolBar1.add(getSettingButton());
					jToolBar1.add(getRegisterToggleButton());
					jToolBar1.add(getSourceLevelDebuggerButton());
					jToolBar1.add(getProfilerToggleButton());
					jToolBar1.add(getJOSLogToggleButton());
					updateBochsButton.setEnabled(true);
					updateBochsButton.setText(MyLanguage.getString("Update"));
					updateBochsButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/arrow_refresh.png")));
					updateBochsButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jUpdateBochsButtonActionPerformed(evt);
						}
					});
				}
			}
			progressBarDialog.jProgressBar.setValue(50);
			progressBarDialog.jProgressBar.setString("Init GUI - 3");
			{
				statusPanel = new JPanel();
				BorderLayout jStatusPanelLayout = new BorderLayout();
				statusPanel.setLayout(jStatusPanelLayout);
				getContentPane().add(statusPanel, BorderLayout.SOUTH);
				getContentPane().add(getMainPanel());
				{
					statusProgressBar = new JProgressBar();
					statusProgressBar.setStringPainted(true);
					statusPanel.add(statusProgressBar, BorderLayout.WEST);
					statusPanel.add(getJPanel25(), BorderLayout.CENTER);
				}
			}
			progressBarDialog.jProgressBar.setValue(60);
			progressBarDialog.jProgressBar.setString("Init GUI - 4");
			{
				jMenuBar1 = new JMenuBar();
				setJMenuBar(jMenuBar1);
				{
					jMenu3 = new JMenu();
					jMenuBar1.add(jMenu3);
					jMenu3.setText(MyLanguage.getString("File"));
					{
						jSeparator2 = new JSeparator();
						jMenu3.add(jSeparator2);
					}
					{
						exitMenuItem = new JMenuItem();
						jMenu3.add(exitMenuItem);
						exitMenuItem.setText(MyLanguage.getString("Exit"));
						exitMenuItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								exitMenuItemActionPerformed(evt);
							}
						});
					}
				}

				{
					bochsMenu = new JMenu();
					jMenuBar1.add(bochsMenu);
					jMenuBar1.add(getTopFontMenu());
					jMenuBar1.add(getLanguageMenu());
					jMenuBar1.add(getSystemMenu());
					bochsMenu.setText(MyLanguage.getString("Bochs"));
					{
						startVMMenuItem = new JMenuItem();
						bochsMenu.add(startVMMenuItem);
						startVMMenuItem.setText(MyLanguage.getString("Start"));
						startVMMenuItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								startVMMenuItemActionPerformed(evt);
							}
						});
					}
					{
						stopBochsMenuItem = new JMenuItem();
						bochsMenu.add(stopBochsMenuItem);
						stopBochsMenuItem.setText(MyLanguage.getString("Stop"));
						stopBochsMenuItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								stopVMMenuItemActionPerformed(evt);
							}
						});
					}
					{
						jSeparator1 = new JSeparator();
						bochsMenu.add(jSeparator1);
					}
					{
						runBochsMenuItem = new JMenuItem();
						bochsMenu.add(runBochsMenuItem);
						runBochsMenuItem.setText(MyLanguage.getString("run"));
						runBochsMenuItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								VMController.getVM().runVM();
							}
						});
					}
					{
						pauseVMMenuItem = new JMenuItem();
						bochsMenu.add(pauseVMMenuItem);
						pauseVMMenuItem.setText(MyLanguage.getString("pause"));
						pauseVMMenuItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								pauseVMMenuItemActionPerformed(evt);
							}
						});
					}
					{
						jupdateVMStatusMenuItem = new JMenuItem();
						bochsMenu.add(jupdateVMStatusMenuItem);
						jupdateVMStatusMenuItem.setText(MyLanguage.getString("update_status"));
						jupdateVMStatusMenuItem.setBounds(83, 86, 79, 20);
						jupdateVMStatusMenuItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								jupdateVMStatusMenuItemActionPerformed(evt);
							}
						});
					}
				}
				{
					jMenu5 = new JMenu();
					jMenuBar1.add(jMenu5);
					jMenu5.setText(MyLanguage.getString("Help"));
					{
						aboutUsMenuItem = new JMenuItem();
						jMenu5.add(aboutUsMenuItem);
						jMenu5.add(getHelpRequestMenuItem());
						jMenu5.add(getJVMMenuItem());
						jMenu5.add(getShortcutHelpMenuItem());
						jMenu5.add(getLicenseMenuItem());
						aboutUsMenuItem.setText(MyLanguage.getString("About_us"));
						aboutUsMenuItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								aboutUsMenuItemActionPerformed(evt);
							}
						});
					}
				}
			}
			progressBarDialog.jProgressBar.setValue(70);
			progressBarDialog.jProgressBar.setString("Init GUI - 5");
			if (Setting.getInstance().width == 0 || Setting.getInstance().height == 0) {
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				setSize(screenSize.width * 2 / 3, screenSize.height * 4 / 5);
			} else {
				setSize(Setting.getInstance().width, Setting.getInstance().height);
			}
			int x = Setting.getInstance().x;
			int y = Setting.getInstance().y;
			if (x <= 0 || y <= 0) {
				this.setLocationRelativeTo(null);
			} else {
				setLocation(x, y);
			}

			progressBarDialog.jProgressBar.setValue(71);
			progressBarDialog.jProgressBar.setString("Init GUI - 5.1");

			jSplitPane1.setDividerLocation(Setting.getInstance().divX);
			jSplitPane2.setDividerLocation(Setting.getInstance().divY);

			osDebugInformationPanel1.getMainSplitPane().setDividerLocation(Setting.getInstance().osDebugSplitPane_DividerLocation);
			initGlobalFontSetting(new Font(Setting.getInstance().fontFamily, Font.PLAIN, Setting.getInstance().fontsize));
			instrumentPanel.setThing(statusProgressBar, statusLabel);

			progressBarDialog.jProgressBar.setValue(72);
			progressBarDialog.jProgressBar.setString("Init GUI - 5.2");

			// prevent null jmenuitem
			getInstructionPanelPopupMenu();
			// end prevent null jmenuitem

			progressBarDialog.jProgressBar.setValue(73);
			progressBarDialog.jProgressBar.setString("Init GUI - 5.3");

			KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
				public boolean dispatchKeyEvent(KeyEvent e) {
					if (e.getID() == KeyEvent.KEY_RELEASED) {
						int keycode = e.getKeyCode();
						if (keycode == 112) {
							tabbedPane3.setSelectedIndex(0);
						} else if (keycode == 113) {
							tabbedPane3.setSelectedIndex(1);
						} else if (keycode == 114) {
							tabbedPane3.setSelectedIndex(2);
						} else if (keycode == 115) {
							tabbedPane3.setSelectedIndex(3);
						} else if (keycode == 116) {
							if (startVMButton.isEnabled()) {
								startVMButtonActionPerformed(null);
							}
						} else if (keycode == 117) {
							if (stopVMButton.isEnabled()) {
								stopVMButtonActionPerformed(null);
							}
						} else if (keycode == 118) {
							if (runVMButton.isEnabled()) {
								runVMButtonActionPerformed(null);
							}
						} else if (keycode == 119) {
							if (stepVMButton.isEnabled()) {
								stepVMButtonActionPerformed(null);
							}
						} else if (keycode == 120) {
							if (fastStepBochsButton.isEnabled()) {
								fastStepButtonActionPerformed(null);
							}
						}
					}

					// If the key should not be dispatched to the
					// focused component, set discardEvent to true
					boolean discardEvent = false;
					return discardEvent;
				}
			});
			System.out.println("started GKD " + new SimpleDateFormat("m:s S").format(new Date()));
			System.out.println("used " + (double) (new Date().getTime() - startDate.getTime()) / 1000 + " sec");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(ERROR);
		}
	}

	private void initChineseFont() {
		new Thread("initChineseFont thread") {
			public void run() {
				fontMenu.removeAll();

				Font[] allfonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
				String chinesesample = "\u4e00";
				for (int j = 0; j < allfonts.length && j < 40; j++) {
					if (allfonts[j].canDisplayUpTo(chinesesample) == -1) {
						if (!allfonts[j].getFontName().toLowerCase().contains("-")) {
							JMenuItem jMenuItem = new JMenuItem(allfonts[j].getFontName());
							jMenuItem.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									Setting.getInstance().fontFamily = ((JMenuItem) evt.getSource()).getText();
									initGlobalFontSetting(new Font(Setting.getInstance().fontFamily, Font.PLAIN, Setting.getInstance().fontsize));
								}
							});
							fontMenu.add(jMenuItem);
						}
					}
				}
			}
		}.start();
	}

	private void vmCommandButtonActionPerformed(ActionEvent evt) {
		try {
			String command = vmCommandTextField.getText().trim();
			Global.lastCommand = command;
			Setting.getInstance().vmCommandHistory.add(command);
			Setting.getInstance().save();

			if (command.equals("clear")) {
				this.vmCommandEditorPane.setText("");
			} else if (command.equals("c") && VMController.vmType == VMType.Bochs) {
				VMController.getVM().runVM();
			} else if (command.equals("q") && VMController.vmType == VMType.Bochs) {
				VMController.getVM().stopVM();
			} else {
				String r = VMController.getVM().sendVMCommand(command);
				if (r != null) {
					vmCommandEditorPane.setText(vmCommandEditorPane.getText() + "\n" + r);
				}
			}

			//			if (VMController.vmType == VMType.Bochs) {
			//				if (command.equals("clear")) {
			//					this.vmCommandEditorPane.setText("");
			//				} else if (command.equals("c")) {
			//					runVM();
			//				} else if (command.equals("q")) {
			//					VMController.getVM().stopVM();
			//				} else {
			//					Setting.getInstance().vmCommandHistory.add(command);
			//					Setting.getInstance().save();
			//
			//
			//					sendBochsCommand(command);
			//					if (Setting.getInstance().updateAfterGKDCommand) {
			//						updateVMStatusForBochsCommand(true);
			//					}
			//					commandHistoryIndex = 0;
			//				}
			//			} else if (VMController.vmType == VMType.Qemu) {
			//				String qmpHost = GKDCommonLib.readConfig(cmd, "/gkd/qmpHost/text()");
			//				int qmpPort = GKDCommonLib.readConfigInt(cmd, "/gkd/qmpPort/text()");
			//				if (qmpHost == null || qmpPort == -1) {
			//					return;
			//				}
			//				Setting.getInstance().vmCommandHistory.add(command);
			//				Setting.getInstance().save();
			//				String r = null;
			//				if (command.equals("?")) {
			//					String qmpCommand = "{ \"execute\": \"qmp_capabilities\" }";
			//					qmpCommand += "{ \"execute\": \"query-commands\" }";
			//					r = QemuMonitor.sendCommand(qmpCommand, qmpHost, qmpPort);
			//					r = r.replaceAll("[,\\[\\]]", "\n");
			//					r = r.replaceFirst("^.*\n", "");
			//					List<String> list = Arrays.asList(r.split("\n"));
			//					Collections.sort(list);
			//					r = "";
			//					for (String temp : list) {
			//						if (temp.split(":").length > 1) {
			//							r += temp.split(":")[1].replaceAll("\"", "").replaceAll("}", "").trim() + "\n";
			//						}
			//					}
			//				} else if (command.equals("gkd g")) {
			//					r = libGDB.sendSocketCommand("g");
			//				} else {
			//					String qmpCommand = "{ \"execute\": \"qmp_capabilities\" }";
			//					if (command.contains(" ")) {
			//						qmpCommand += "{ \"execute\": \"" + command.split(" ")[0] + "\", \"arguments\": { \"command-line\": \"" + command.replaceFirst("^[^ ]*", "") + "\" }}";
			//					} else {
			//						qmpCommand += "{ \"execute\": \"" + command + "\" }";
			//					}
			//					r = QemuMonitor.sendCommand(qmpCommand, qmpHost, qmpPort);
			//					r = r.replaceAll("\\\\r\\\\n", "\n");
			//				}
			//				if (r != null) {
			//					vmCommandEditorPane.setText(vmCommandEditorPane.getText() + "\n" + r);
			//				}
			//			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void vmCommandTextFieldKeyTyped(KeyEvent evt) {
		if (evt.getKeyChar() == '\n') {
			vmCommandButtonActionPerformed(null);
			vmCommandTextField.setText("");
		}
	}

	private void startVMMenuItemActionPerformed(ActionEvent evt) {
		VMController.getVM().startVM();
	}

	private void stopVMMenuItemActionPerformed(ActionEvent evt) {
		VMController.getVM().stopVM();
		runVMButton.setText(MyLanguage.getString("run"));
	}

	private void pauseVMMenuItemActionPerformed(ActionEvent evt) {
		skipBreakpointTime = 0;
		//		pauseVM(true, true);
		VMController.getVM().pauseVM();
	}

	private void startVMButtonActionPerformed(ActionEvent evt) {
		VMController.getVM().startVM();
		//		if (GKDVMStubController.vmType == VMType.Bochs) {
		//			if (Global.debug) {
		//				logger.debug("startBochs()");
		//			}
		//			startBochs();
		//			if (Global.debug) {
		//				logger.debug("end startBochs()");
		//			}
		//		} else if (GKDVMStubController.vmType == VMType.Qemu) {
		//			if (Global.debug) {
		//				logger.debug("startQemu()");
		//			}
		//			startQemu();
		//			if (Global.debug) {
		//				logger.debug("end startQemu()");
		//			}
		//		}
	}

	private void stopVMButtonActionPerformed(ActionEvent evt) {
		stopVMMenuItemActionPerformed(null);
	}

	private void runVMButtonActionPerformed(ActionEvent evt) {
		if (runVMButton.getEventSource() != null) {
			if (runVMButton.getEventSource() == runBochsAndSkipBreakpointMenuItem) {
				customCommandQueue.clear();
				String s = JOptionPane.showInputDialog(this, "How many breakpoint you want to skip?");
				if (s == null) {
					return;
				}
				skipBreakpointTime = Integer.parseInt(s);
				VMController.getVM().runVM();

				runVMButton.setText(MyLanguage.getString("pause"));
				runVMButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/resultset_next.png")));
			} else if (runVMButton.getEventSource() == runCustomCommandMenuItem) {
				CustomCommandDialog customCommandDialog = new CustomCommandDialog(this);
				customCommandDialog.setVisible(true);
				customCommandQueue.clear();
				if (customCommandDialog.ok) {
					for (int z = 0; z < (Integer) customCommandDialog.repeatSpinner.getValue(); z++) {
						if (!customCommandDialog.comboBox1.getSelectedItem().toString().equals("")) {
							for (int x = 0; x < (Integer) customCommandDialog.spinner1.getValue(); x++) {
								customCommandQueue.add(new CustomCommand(customCommandDialog.comboBox1.getSelectedItem().toString()));
							}
						}
						if (!customCommandDialog.comboBox2.getSelectedItem().toString().equals("")) {
							for (int x = 0; x < (Integer) customCommandDialog.spinner2.getValue(); x++) {
								customCommandQueue.add(new CustomCommand(customCommandDialog.comboBox2.getSelectedItem().toString()));
							}
						}
						if (!customCommandDialog.comboBox3.getSelectedItem().toString().equals("")) {
							for (int x = 0; x < (Integer) customCommandDialog.spinner3.getValue(); x++) {
								customCommandQueue.add(new CustomCommand(customCommandDialog.comboBox3.getSelectedItem().toString()));
							}
						}
						if (!customCommandDialog.comboBox4.getSelectedItem().toString().equals("")) {
							for (int x = 0; x < (Integer) customCommandDialog.spinner4.getValue(); x++) {
								customCommandQueue.add(new CustomCommand(customCommandDialog.comboBox4.getSelectedItem().toString()));
							}
						}
						if (!customCommandDialog.comboBox5.getSelectedItem().toString().equals("")) {
							for (int x = 0; x < (Integer) customCommandDialog.spinner5.getValue(); x++) {
								customCommandQueue.add(new CustomCommand(customCommandDialog.comboBox5.getSelectedItem().toString()));
							}
						}
						if (!customCommandDialog.comboBox6.getSelectedItem().toString().equals("")) {
							for (int x = 0; x < (Integer) customCommandDialog.spinner6.getValue(); x++) {
								customCommandQueue.add(new CustomCommand(customCommandDialog.comboBox6.getSelectedItem().toString()));
							}
						}
					}

					skipBreakpointTime = 0;
					//					commandReceiver.shouldShow = false;
					//					runVM();
					VMController.getVM().runVM();

					runVMButton.setText(MyLanguage.getString("pause"));
					runVMButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/resultset_next.png")));
				}
			}
		} else {
			customCommandQueue.clear();
			if (runVMButton.getText().equals(MyLanguage.getString("run"))) {
				enableAllButtons(false, true);
				VMController.getVM().runVM();

				runVMButton.setText(MyLanguage.getString("pause"));
				runVMButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/pause.png")));
				new Thread() {
					public void run() {
						VMController.getVM().waitVMStop();
						if (runVMButton.getText().equals(MyLanguage.getString("run"))) {
							updateVMStatus(true);
						}
					}
				}.start();
			} else {
				VMController.getVM().pauseVM();
				updateVMStatus(true);
				waitUpdateFinish();

				runVMButton.setText(MyLanguage.getString("run"));
				runVMButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/resultset_next.png")));
			}
		}
	}

	//	public static void sendBochsCommand(String command) {
	//		try {
	//			command = command.toLowerCase().trim();
	//			commandReceiver.clearBuffer();
	//			Global.lastCommand = command;
	//			commandOutputStream.write(command + "\n");
	//			commandOutputStream.flush();
	//			if (!command.equals("6") && !command.equals("c") && !command.startsWith("pb") && !command.startsWith("vb") && !command.startsWith("lb") && !command.startsWith("bpd")
	//					&& !command.startsWith("bpe") && !command.startsWith("del") && !command.startsWith("set")) {
	//				commandReceiver.waitUntilHaveInput();
	//				return;
	//			}
	//		} catch (IOException e) {
	//		}
	//	}

	private void aboutUsMenuItemActionPerformed(ActionEvent evt) {
		new AboutUsDialog(this).setVisible(true);
	}

	StepThread untilThread;

	private void stepVMButtonActionPerformed(ActionEvent evt) {
		if (stepVMButton.getEventSource() != null) {
			untilThread = new StepThread(stepVMButton.getEventSource());
			if (stepVMButton.getEventSource() == stepNMenuItem) {
				String s = JOptionPane.showInputDialog(this, "Please input the instruction count?");
				if (s == null) {
					return;
				}
				untilThread.instructionCount = Integer.parseInt(s);
			} else if (stepVMButton.getEventSource() == stepUntilIPBigChangeMenuItem) {
				String s = JOptionPane.showInputDialog("Please input the instruction count?");
				if (s == null) {
					return;
				}
				untilThread.ipDelta = CommonLib.string2long(s);
			}

			CardLayout cl = (CardLayout) (mainPanel.getLayout());
			cl.show(mainPanel, "Running Panel");
			new Thread(untilThread, "Step until thread").start();
		} else {
			VMController.getVM().singleStep();
			WebServiceUtil.log("gkd", "step", null, null, null);
			updateVMStatus(true);
		}
	}

	class StepThread implements Runnable {
		Object eventSource;
		public boolean shouldStop;
		public int instructionCount;
		public long ipDelta;
		int noOfLine = 1;

		public StepThread(Object eventSource) {
			this.eventSource = eventSource;
		}

		private String update(String result, DataOutputStream out) {
			BigInteger cs;
			if (registerPanel.csTextField.getBase() == null || registerPanel.csTextField.getBase().equals("")) {
				cs = CommonLib.string2BigInteger(registerPanel.csTextField.getText());
			} else {
				cs = CommonLib.string2BigInteger(registerPanel.csTextField.getBase());
			}
			BigInteger eip = CommonLib.string2BigInteger(registerPanel.eipTextField.getText());

			String instruction = VMController.getVM().instruction(null, cs, eip, is32Bits()).get(0)[2];
			if (saveToRunDotTxt || !jDisableAutoUpdateCheckBox.isSelected()) {
				if (instruction.endsWith("\n")) {
					instruction = instruction.substring(0, instruction.length() - 1);
				}
				if (autoUpdateEvery20LinesCheckBox.isSelected()) {
					result += instruction + "\n";
				} else {
					result = instruction;
				}
			}
			updatePTime(false);
			updateRegister(false);
			updateHistoryTable(instruction);
			if (saveToRunDotTxt) {
				try {
					out.writeBytes(result + "\n");
					out.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (!jDisableAutoUpdateCheckBox.isSelected()) {
				if (maxRowComboBox.getSelectedItem().equals("infinite")) {
					jTextArea1.setMaxRow(-1);
				} else {
					jTextArea1.setMaxRow(Integer.parseInt(maxRowComboBox.getSelectedItem().toString()));
				}
				if (autoUpdateEvery20LinesCheckBox.isSelected()) {
					if (noOfLine >= 20) {
						vmCommandEditorPane.setText(vmCommandEditorPane.getText() + result);
						jTextArea1.newLogFileLine(result);
						result = "";
						noOfLine = 1;
					} else {
						noOfLine++;
					}
				} else {
					vmCommandEditorPane.setText(vmCommandEditorPane.getText() + result);
					jTextArea1.newLogFileLine(result);
					result = "";
				}
			}
			return result;
		}

		public void run() {
			DataOutputStream out = null;
			try {
				enableAllButtons(false, false);
				stepCountLabel.setVisible(false);
				out = new DataOutputStream(new FileOutputStream("run.txt", true));

				if (eventSource != null) {
					BigInteger cs;
					if (registerPanel.csTextField.getBase() == null || registerPanel.csTextField.getBase().equals("")) {
						cs = CommonLib.string2BigInteger(registerPanel.csTextField.getText());
					} else {
						cs = CommonLib.string2BigInteger(registerPanel.csTextField.getBase());
					}
					BigInteger eip = CommonLib.string2BigInteger(registerPanel.eipTextField.getText());

					if (eventSource == step10MenuItem) {
						String result = "";
						for (int x = 1; x <= 10 && !shouldStop; x++) {
							statusLabel.setText("Step " + x + " / 10");
							VMController.getVM().singleStep();
							result = update(result, out);
						}
						updateVMStatus(false);
					} else if (eventSource == step100MenuItem) {
						String result = "";
						for (int x = 1; x <= 100 && !shouldStop; x++) {
							statusLabel.setText("Step " + x + " / 100");
							VMController.getVM().singleStep();
							result = update(result, out);
						}
						updateVMStatus(false);
					} else if (eventSource == stepOver10MenuItem) {
						String result = "";
						for (int x = 1; x <= 10 && !shouldStop; x++) {
							statusLabel.setText("Step over " + x + " / 10");
							VMController.getVM().stepOver();
							result = update(result, out);
						}
						updateVMStatus(false);
					} else if (eventSource == stepOver100MenuItem) {
						String result = "";
						for (int x = 1; x <= 100 && !shouldStop; x++) {
							statusLabel.setText("Step over " + x + " / 100");
							VMController.getVM().stepOver();
							result = update(result, out);
						}
						updateVMStatus(false);
					} else if (eventSource == stepNMenuItem) {
						String result = "";
						stepCountLabel.setVisible(true);
						long lastTime = new Date().getTime();
						int lastCount = 1;
						int speed = 0;
						for (int x = 1; x <= instructionCount && !shouldStop; x++) {
							statusLabel.setText("Step " + x + " / " + instructionCount);
							stepCountLabel.setText("Step " + x + " / " + instructionCount + ", speed : " + speed + " steps/second");

							VMController.getVM().singleStep();

							result = update(result, out);

							if (new Date().getTime() - lastTime >= 1000) {
								speed = x - lastCount;
								lastCount = x;
								lastTime = new Date().getTime();
							}
						}
						vmCommandEditorPane.setText("");
						updateVMStatus(false);
					} else if (eventSource == stepOverNTimesMenuItem) {
						String result = "";
						stepCountLabel.setVisible(true);
						long lastTime = new Date().getTime();
						int lastCount = 1;
						int speed = 0;
						for (int x = 1; x <= instructionCount && !shouldStop; x++) {
							statusLabel.setText("Step over " + x + " / " + instructionCount);
							stepCountLabel.setText("Step over " + x + " / " + instructionCount + ", speed : " + speed + " steps/second");

							//							if (VMController.vmType == VMType.Bochs) {
							VMController.getVM().stepOver();
							//							} else if (VMController.vmType == VMType.Bochs) {
							//								libGDB.singleStep();
							//							}
							result = update(result, out);
							vmCommandEditorPane.setText("");

							if (new Date().getTime() - lastTime >= 1000) {
								speed = x - lastCount;
								lastCount = x;
								lastTime = new Date().getTime();
							}
						}
						updateVMStatus(false);
					} else if (eventSource == stepUntilCallOrJumpMenuItem) {
						boolean notMatch = true;
						do {
							//							sendBochsCommand("s");
							//							String result = commandReceiver.getCommandResult("(").toLowerCase();

							VMController.getVM().singleStep();
							String result = VMController.getVM().instruction(null, cs, eip, is32Bits()).get(0)[2];
							if (result.contains("jmp") || result.contains("je") || result.contains("jne") || result.contains("jg") || result.contains("jge")
									|| result.contains("ja") || result.contains("jae") || result.contains("jl") || result.contains("jle") || result.contains("jb")
									|| result.contains("jbe") || result.contains("jo") || result.contains("jno") || result.contains("jz") || result.contains("jnz")
									|| result.contains("loop") || result.contains("call")) {
								notMatch = false;
							}
						} while (notMatch && !shouldStop);
						updateVMStatus(true);
					} else if (eventSource == stepUntilRetMenuItem) {
						boolean notMatch = true;
						do {
							//							sendBochsCommand("s");
							//							String result = commandReceiver.getCommandResult("(").toLowerCase();
							VMController.getVM().singleStep();
							String result = VMController.getVM().instruction(null, cs, eip, is32Bits()).get(0)[2];
							if (result.contains("ret")) {
								notMatch = false;
							}
						} while (notMatch && !shouldStop);
						updateVMStatus(true);
					} else if (eventSource == stepUntilIRetMenuItem) {
						boolean notMatch = true;
						do {
							//							sendBochsCommand("s");
							//							String result = commandReceiver.getCommandResult("(").toLowerCase();
							VMController.getVM().singleStep();
							String result = VMController.getVM().instruction(null, cs, eip, is32Bits()).get(0)[2];
							if (result.contains("iret")) {
								notMatch = false;
							}
						} while (notMatch && !shouldStop);
						updateVMStatus(true);
					} else if (eventSource == stepUntilMovMenuItem) {
						boolean notMatch = true;
						do {
							//							sendBochsCommand("s");
							//							String result = commandReceiver.getCommandResult("(").toLowerCase();
							VMController.getVM().singleStep();
							String result = VMController.getVM().instruction(null, cs, eip, is32Bits()).get(0)[2];
							if (result.contains("mov")) {
								notMatch = false;
							}
						} while (notMatch && !shouldStop);
						updateVMStatus(true);
					} else if (eventSource == stepUntilIPBigChangeMenuItem) {
						boolean notMatch = true;
						long lastIP = -1;
						int count = 1;
						Date lastTime = new Date();
						stepCountLabel.setVisible(true);

						int noOfLine = 1;
						String result = "";

						do {
							double secondDiff = 0;
							VMController.getVM().singleStep();

							String re = VMController.getVM().instruction(null, cs, eip, is32Bits()).get(0)[2];
							long ip = CommonLib.string2long(re.replaceAll("\\].*$", "").replaceAll("^.*\\[", ""));

							if (saveToRunDotTxt || !jDisableAutoUpdateCheckBox.isSelected()) {
								if (re.endsWith("\n")) {
									re = re.substring(0, re.length() - 1);
								}
								if (autoUpdateEvery20LinesCheckBox.isSelected()) {
									result += re + "\n";
								} else {
									result = re;
								}
							}
							updateRegister(true);
							updateHistoryTable(re);

							if (saveToRunDotTxt) {
								out.writeBytes(result + "\n");
								out.flush();
							}
							if (!jDisableAutoUpdateCheckBox.isSelected()) {
								if (maxRowComboBox.getSelectedItem().equals("infinite")) {
									jTextArea1.setMaxRow(-1);
								} else {
									jTextArea1.setMaxRow(Integer.parseInt(maxRowComboBox.getSelectedItem().toString()));
								}
								if (autoUpdateEvery20LinesCheckBox.isSelected()) {
									if (noOfLine >= 20) {
										vmCommandEditorPane.setText(vmCommandEditorPane.getText() + result);
										jTextArea1.newLogFileLine(result);
										result = "";
										noOfLine = 1;

										secondDiff = (Double.parseDouble(String.valueOf(new Date().getTime())) - lastTime.getTime()) / 1000;
										lastTime = new Date();
									} else {
										noOfLine++;
									}
								} else {
									vmCommandEditorPane.setText(vmCommandEditorPane.getText() + result);
									jTextArea1.newLogFileLine(result);
									result = "";

									secondDiff = (Double.parseDouble(String.valueOf(new Date().getTime())) - lastTime.getTime()) / 1000;
									lastTime = new Date();
								}
							} else {
								secondDiff = (Double.parseDouble(String.valueOf(new Date().getTime())) - lastTime.getTime()) / 1000;
								lastTime = new Date();
							}

							if (lastIP != -1 && Math.abs(ip - lastIP) >= ipDelta) {
								notMatch = false;
							}
							lastIP = ip;

							if (secondDiff > 0) {
								if (!jDisableAutoUpdateCheckBox.isSelected()) {
									if (autoUpdateEvery20LinesCheckBox.isSelected()) {
										stepCountLabel.setText(String.valueOf(count) + " instructions executed, current EIP=0x" + Long.toHexString(ip) + ", "
												+ Math.round(20 / secondDiff) + " instructions executed per second");
										count += 20;
									} else {
										stepCountLabel.setText(String.valueOf(count++) + " instructions executed, current EIP=0x" + Long.toHexString(ip) + ", "
												+ Math.round(1 / secondDiff) + " instructions executed per second");
									}
								} else {
									stepCountLabel.setText(String.valueOf(count++) + " instructions executed, current EIP=0x" + Long.toHexString(ip) + ", "
											+ Math.round(1 / secondDiff) + " instructions executed per second");
								}
							}
							updateRegister(true);
						} while (notMatch && !shouldStop);
						updateVMStatus(true);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (currentPanel.equals("jMaximizableTabbedPane_BasePanel1") || currentPanel.equals("sourceLevelDebugger")) {
					CardLayout cl = (CardLayout) (mainPanel.getLayout());
					cl.show(mainPanel, currentPanel);
				}

				enableAllButtons(true, false);
			}

			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void goMemoryButtonActionPerformed(ActionEvent evt) {
		try {
			updateMemory(true);

			addMemoryAddressComboBox(memoryAddressComboBox.getSelectedItem().toString());

			Setting.getInstance().memoryCombo.add(memoryAddressComboBox.getSelectedItem().toString());
			Setting.getInstance().save();
		} catch (Exception ex) {
			if (Global.debug) {
				ex.printStackTrace();
			}
		}
	}

	private void addMemoryAddressComboBox(String str) {
		for (int x = 0; x < memoryAddressComboBox.getItemCount(); x++) {
			if (memoryAddressComboBox.getItemAt(x).toString().trim().equals(str.trim())) {
				return;
			}
		}
		memoryAddressComboBox.addItem(str.trim());
	}

	private void addInstructionComboBox(String str) {
		for (int x = 0; x < instructionComboBox.getItemCount(); x++) {
			if (this.instructionComboBox.getItemAt(x).toString().trim().equals(str.trim())) {
				return;
			}
		}

		instructionComboBox.addItem(str.trim());
	}

	private void jupdateVMStatusMenuItemActionPerformed(ActionEvent evt) {
		updateVMStatus(true);
	}

	public void updateVMStatus(final boolean updateHistoryTable) {
		logger.debug("updateVMStatus");
		isupdateVMStatusEnd = false;
		WebServiceUtil.log("gkd", "updateVMStatus", null, null, null);
		final JProgressBarDialog d = new JProgressBarDialog(this, true);
		enableAllButtons(false, skipBreakpointTime > 0);
		Thread updateThread = new Thread("updateVMStatus thread") {
			public void run() {
				if (Global.debug) {
					logger.debug("updateVMStatus thread start");
				}

				d.jProgressBar.setString("update ptime");
				if (Global.debug) {
					logger.debug("update ptime");
				}
				updatePTime(true);

				d.jProgressBar.setString("updateRegister");
				if (Global.debug) {
					logger.debug("updateRegister");
				}
				updateRegister(true);

				d.jProgressBar.setString("updateEFlag");
				if (Global.debug) {
					logger.debug("updateEFlag");
				}
				updateEFlags();

				d.jProgressBar.setString("updateMemory");
				if (Global.debug) {
					logger.debug("updateMemory");
				}
				updateMemory(true);

				d.jProgressBar.setString("updateInstruction");
				if (Global.debug) {
					logger.debug("updateInstruction");
				}
				updateInstruction(null);

				d.jProgressBar.setString("updateGDT");
				if (Global.debug) {
					logger.debug("updateGDT");
				}
				updateGDT();

				String cr0 = registerPanel.cr0TextField.getText();
				int cr0Byte0 = CommonLib.string2int(cr0.substring(cr0.length() - 1));
				if ((cr0Byte0 & 1) == 1) {
					d.jProgressBar.setString("updateIDT");
					if (Global.debug) {
						logger.debug("updateIDT");
					}
					updateIDT();

					d.jProgressBar.setString("updateLDT");
					if (Global.debug) {
						logger.debug("updateLDT");
					}
					updateLDT();

					d.jProgressBar.setString("updatePageTable");
					if (Global.debug) {
						logger.debug("updatePageTable");
					}
					updatePageTable(CommonLib.string2BigInteger(registerPanel.cr3TextField.getText()));

					d.jProgressBar.setString("updateStack");
					if (Global.debug) {
						logger.debug("updateStack");
					}
					updateStack();

					d.jProgressBar.setString("updateAddressTranslate");
					if (Global.debug) {
						logger.debug("updateAddressTranslate");
					}
					updateAddressTranslate();
				}

				d.jProgressBar.setString("updateBreakpoint");
				if (Global.debug) {
					logger.debug("updateBreakpoint");
				}
				updateBreakpoint();

				d.jProgressBar.setString("updateBreakpointTableColor");
				if (Global.debug) {
					logger.debug("updateBreakpointTableColor");
				}
				updateBreakpointTableColor();

				if (Global.osDebug.compareTo(BigInteger.valueOf(-1)) != 0) {
					d.jProgressBar.setString("update OS debug informations");
					if (Global.debug) {
						logger.debug("update OS debug informations");
					}
					updateOSDebugInfo();
				}

				d.jProgressBar.setString("update instrument chart");
				if (Global.debug) {
					logger.debug("update instrument chart");
				}
				instrumentPanel.updateChart();

				d.jProgressBar.setString("update call graph");
				if (Global.debug) {
					logger.debug("update call graph");
				}
				instrumentPanel.updateCallGraph();

				if (updateHistoryTable) {
					d.jProgressBar.setString("updateHistoryTable");
					if (Global.debug) {
						logger.debug("updateHistoryTable");
					}

					//					String result = VMController.getVM().disasm(getRealEIP());
					BigInteger cs;
					if (registerPanel.csTextField.getBase() == null || registerPanel.csTextField.getBase().equals("")) {
						cs = CommonLib.string2BigInteger(registerPanel.csTextField.getText());
					} else {
						cs = CommonLib.string2BigInteger(registerPanel.csTextField.getBase());
					}
					BigInteger eip = CommonLib.string2BigInteger(registerPanel.eipTextField.getText());
					String result = VMController.getVM().instruction(null, cs, eip, is32Bits()).get(0)[2];
					updateHistoryTable(result);
				}

				statusLabel.setText("");

				if (breakpointLoadedOnce == false && Setting.getInstance().loadBreakpointAtStartup) {
					loadBreakpointButtonActionPerformed(null);
					breakpointLoadedOnce = true; // since we only have to load once
				}
				if (systemMapLoadedOnce == false && Setting.getInstance().loadSystemMapAtStartup) {
					if (Global.elfPaths != null) {
						sourceLevelDebugger.loadELF(Global.elfPaths);
						systemMapLoadedOnce = true; // since we only have to load once
					}
				}
				jumpToRowInstructionTable(getRealEIP());
				d.jProgressBar.setString("updateVMStatus end");
				d.setVisible(false);

				enableAllButtons(true, skipBreakpointTime > 0 || customCommandQueue.size() > 0);
				isupdateVMStatusEnd = true;
				if (Global.debug) {
					logger.debug("updateVMStatus thread end");
				}
			}
		};

		d.thread = updateThread;
		//updateThread.setDaemon(true);
		d.setTitle("Updating gkd status");
		d.jProgressBar.setIndeterminate(true);
		d.jProgressBar.setStringPainted(true);
		d.jCancelButton.setVisible(false);
		d.setVisible(true);
		//updateThread.start();

		if (Global.debug) {
			logger.debug("updateVMStatus() end");
		}
	}

	protected void updatePTime(boolean updateGUI) {
		//		if (GKDVMStubController.vmType == VMType.Bochs) {
		//			try {
		//				if (updateGUI) {
		//					jStatusLabel.setText("Updating ptime");
		//				}
		//				commandReceiver.shouldShow = false;
		//				sendBochsCommand("ptime");
		//				String result = commandReceiver.getCommandResultUntilEnd();
		//				if (result.contains(":") && result.contains("ptime")) {
		//					registerPanel.jPTimeTextField.setText(result.replaceAll("<.*>", "").split(":")[1].trim());
		//				}
		//			} catch (Exception ex) {
		//				ex.printStackTrace();
		//			}
		//		}
	}

	protected void updateOSDebugInfo() {
		long size = 0;
		try {
			String magicByte = getMemoryStr(Global.osDebug, 8, true);
			CardLayout cl = (CardLayout) (osDebugStandardPanel.getLayout());
			if (magicByte.equals("PETER---")) {
				size = CommonLib.getInt(VMController.getVM().physicalMemory(Global.osDebug.add(BigInteger.valueOf(8)), 4), 0);
				String xml = getMemoryStr(Global.osDebug.add(BigInteger.valueOf(12)), (int) size, true).trim();
				OSDebugInfoHelper.jOSDebugInformationPanel = osDebugInformationPanel1;

				OSDebugInfoHelper.addData(magicByte, size, xml);
				this.osDebugInformationPanel1.xmlEditorPane.setText(xml);
				cl.show(osDebugStandardPanel, "jOSDebugInformationPanel1");
			} else {
				cl.show(osDebugStandardPanel, "OS debug error label");
			}
		} catch (OutOfMemoryError ex) {
			System.gc();
			logger.debug("Size probably too large? size=" + size);
			ex.printStackTrace();
		}
	}

	public void updateVMStatusForBochsCommand(boolean shouldWait) {
		Thread updateThread = new Thread("updateVMStatusForBochsCommand thread") {
			public void run() {
				enableAllButtons(false, false);

				if (Setting.getInstance().updateAfterGKDCommand_register) {
					if (Global.debug) {
						logger.debug("updateRegister");
					}
					updateRegister(true);
					if (Global.debug) {
						logger.debug("updateEFlag");
					}
					updateEFlags();
				}

				if (Setting.getInstance().updateAfterGKDCommand_memory) {
					if (Global.debug) {
						logger.debug("updateMemory");
					}
					updateMemory(true);
				}

				if (Setting.getInstance().updateAfterGKDCommand_instruction) {
					if (Global.debug) {
						logger.debug("updateInstruction");
					}
					updateInstruction(null);
				}

				if (Setting.getInstance().updateAfterGKDCommand_gdt) {
					if (Global.debug) {
						logger.debug("updateGDT");
					}
					updateGDT();
				}

				if (Setting.getInstance().updateAfterGKDCommand_idt) {
					if (Global.debug) {
						logger.debug("updateIDT");
					}
					updateIDT();
				}

				if (Setting.getInstance().updateAfterGKDCommand_ldt) {
					if (Global.debug) {
						logger.debug("updateLDT");
					}
					updateLDT();
				}

				if (Setting.getInstance().updateAfterGKDCommand_pageTable) {
					if (Global.debug) {
						logger.debug("updatePageTable");
					}
					updatePageTable(CommonLib.string2BigInteger(registerPanel.cr3TextField.getText()));
				}

				if (Setting.getInstance().updateAfterGKDCommand_stack) {
					if (Global.debug) {
						logger.debug("updateStack");
					}
					updateStack();
				}

				if (Setting.getInstance().updateAfterGKDCommand_addressTranslate) {
					if (Global.debug) {
						logger.debug("updateAddressTranslate");
					}
					updateAddressTranslate();
				}

				if (Setting.getInstance().updateAfterGKDCommand_history) {
					if (Global.debug) {
						logger.debug("updateHistoryTable");
					}
					updateHistoryTable();
				}

				if (Setting.getInstance().updateAfterGKDCommand_breakpoint) {
					if (Global.debug) {
						logger.debug("updateBreakpointTableColor");
					}
					updateBreakpoint();
					updateBreakpointTableColor();
				}

				statusLabel.setText("");

				enableAllButtons(true, false);

				if (breakpointLoadedOnce == false && Setting.getInstance().loadBreakpointAtStartup) {
					loadBreakpointButtonActionPerformed(null);
					breakpointLoadedOnce = true; // since we only have to load
													// once
				}
			}
		};
		updateThread.start();
		if (shouldWait) {
			try {
				updateThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateBreakpointTableColor() {
		for (int x = 0; x < breakpointTable.getRowCount(); x++) {
			String value = breakpointTable.getValueAt(x, 0).toString();
			BigInteger currentIP = CommonLib.string2BigInteger(registerPanel.eipTextField.getText());
			if (currentIP.equals(CommonLib.string2BigInteger(breakpointTable.getValueAt(x, 2).toString()))) {
				int hit = CommonLib.string2int(breakpointTable.getValueAt(x, 3).toString());
				breakpointTable.setValueAt("-" + value, x, 0);
				breakpointTable.setValueAt(hit + 1, x, 3);
			} else {
				if (value.startsWith("-")) {
					breakpointTable.setValueAt(value.substring(1), x, 0);
				}
			}
		}
	}

	private void updateHistoryTable() {
		updateHistoryTable("");
	}

	private void updateHistoryTable(String instruction) {
		try {
			AllRegisters.time.add(new Date());
			AllRegisters.ptime.add(registerPanel.pTimeTextField.getText());
			AllRegisters.eax.add(CommonLib.string2BigInteger(registerPanel.eaxTextField.getText()));
			AllRegisters.ebx.add(CommonLib.string2BigInteger(registerPanel.ebxTextField.getText()));
			AllRegisters.ecx.add(CommonLib.string2BigInteger(registerPanel.ecxTextField.getText()));
			AllRegisters.edx.add(CommonLib.string2BigInteger(registerPanel.edxTextField.getText()));
			AllRegisters.esi.add(CommonLib.string2BigInteger(registerPanel.esiTextField.getText()));
			AllRegisters.edi.add(CommonLib.string2BigInteger(registerPanel.ediTextField.getText()));
			AllRegisters.ebp.add(CommonLib.string2BigInteger(registerPanel.ebpTextField.getText()));
			AllRegisters.esp.add(CommonLib.string2BigInteger(registerPanel.espTextField.getText()));

			AllRegisters.cs.add(CommonLib.string2BigInteger(registerPanel.csTextField.getText()));
			AllRegisters.eip.add(CommonLib.string2BigInteger(registerPanel.eipTextField.getText()));
			AllRegisters.ds.add(CommonLib.string2BigInteger(registerPanel.dsTextField.getText()));
			AllRegisters.es.add(CommonLib.string2BigInteger(registerPanel.esTextField.getText()));
			AllRegisters.fs.add(CommonLib.string2BigInteger(registerPanel.fsTextField.getText()));
			AllRegisters.gs.add(CommonLib.string2BigInteger(registerPanel.gsTextField.getText()));
			AllRegisters.ss.add(CommonLib.string2BigInteger(registerPanel.ssTextField.getText()));
			AllRegisters.eflags.add(registerPanel.eflagLabel.getText());

			AllRegisters.cr0.add(CommonLib.string2BigInteger(registerPanel.cr0TextField.getText()));
			AllRegisters.cr2.add(CommonLib.string2BigInteger(registerPanel.cr2TextField.getText()));
			AllRegisters.cr3.add(CommonLib.string2BigInteger(registerPanel.cr3TextField.getText()));
			AllRegisters.cr4.add(CommonLib.string2BigInteger(registerPanel.cr4TextField.getText()));

			AllRegisters.gdtr.add(CommonLib.string2BigInteger(registerPanel.gdtrTextField.getText()));
			AllRegisters.idtr.add(CommonLib.string2BigInteger(registerPanel.idtrTextField.getText()));
			AllRegisters.ldtr.add(CommonLib.string2BigInteger(registerPanel.ldtrTextField.getText()));

			AllRegisters.tr.add(CommonLib.string2BigInteger(registerPanel.trTextField.getText()));

			AllRegisters.instructions.add(instruction.trim());
			AllRegisters.cCode.add(getCCodeStr(CommonLib.string2BigInteger(registerPanel.eipTextField.getText())));

			Vector<BigInteger> stack = new Vector<BigInteger>();
			for (int x = 0; x < registerPanel.stackList.getModel().getSize(); x++) {
				stack.add(CommonLib.string2BigInteger(registerPanel.stackList.getModel().getElementAt(x).toString()));
			}
			AllRegisters.stack.add(stack);

			AllRegisters.st0.add(registerPanel.st0TextField.getText());
			AllRegisters.st1.add(registerPanel.st1TextField.getText());
			AllRegisters.st2.add(registerPanel.st2TextField.getText());
			AllRegisters.st3.add(registerPanel.st3TextField.getText());
			AllRegisters.st4.add(registerPanel.st4TextField.getText());
			AllRegisters.st5.add(registerPanel.st5TextField.getText());
			AllRegisters.st6.add(registerPanel.st6TextField.getText());
			AllRegisters.st7.add(registerPanel.st7TextField.getText());
			AllRegisters.fpu_status.add(registerPanel.fpuStatusTextField.getText());
			AllRegisters.fpu_control.add(registerPanel.fpuControlTextField.getText());
			AllRegisters.fpu_tag.add(registerPanel.fpuTagTextField.getText());
			AllRegisters.fpu_operand.add(registerPanel.fpuOperandTextField.getText());
			AllRegisters.fip.add(registerPanel.fipTextField.getText());
			AllRegisters.fcs.add(registerPanel.fcsTextField.getText());
			AllRegisters.fdp.add(registerPanel.fdpTextField.getText());
			AllRegisters.fds.add(registerPanel.fdsTextField.getText());

			AllRegisters.mm0.add(registerPanel.mmx0TextField.getText());
			AllRegisters.mm1.add(registerPanel.mmx1TextField.getText());
			AllRegisters.mm2.add(registerPanel.mmx2TextField.getText());
			AllRegisters.mm3.add(registerPanel.mmx3TextField.getText());
			AllRegisters.mm4.add(registerPanel.mmx4TextField.getText());
			AllRegisters.mm5.add(registerPanel.mmx5TextField.getText());
			AllRegisters.mm6.add(registerPanel.mmx6TextField.getText());
			AllRegisters.mm7.add(registerPanel.mmx7TextField.getText());

			((HistoryTableModel) this.historyTable.getModel()).fireTableDataChanged();
			historyTable.scrollRectToVisible(historyTable.getCellRect(historyTable.getRowCount() - 1, 0, true));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected void updateAddressTranslate() {
		try {
			statusLabel.setText("Updating Address translate");

			Vector<Vector<String>> r = VMController.getVM().addressTranslate();
			DefaultTableModel model = (DefaultTableModel) addressTranslateTable.getModel();
			while (model.getRowCount() > 0) {
				model.removeRow(0);
			}
			for (Vector<String> v : r) {
				model.addRow(v);
			}
			((DefaultTableModel) addressTranslateTable.getModel()).fireTableDataChanged();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void enableAllButtons(boolean b, boolean exceptRunButton) {
		if (!exceptRunButton) {
			runVMButton.setEnabled(b);
		}
		stepVMButton.setEnabled(b);
		stepOverDropDownButton.setEnabled(b);
		nextButton.setEnabled(b);
		fastStepBochsButton.setEnabled(b);
		updateBochsButton.setEnabled(b);
		exportToExcelButton.setEnabled(b);
		settingButton.setEnabled(b);
		registerToggleButton.setEnabled(b);
		sourceLevelDebuggerToggleButton.setEnabled(b);
		profilerToggleButton.setEnabled(b);

		pageDirectoryTable.setEnabled(b);
		pageTableTable.setEnabled(b);

		pauseVMMenuItem.setEnabled(b);
		runBochsMenuItem.setEnabled(b);
		jupdateVMStatusMenuItem.setEnabled(b);
		runBochsAndSkipBreakpointMenuItem.setEnabled(b);
		runCustomCommandMenuItem.setEnabled(b);
		nextOverButton.setEnabled(b);
	}

	public void updatePageTable(BigInteger pageDirectoryBaseAddress) {
		statusLabel.setText("Updating page table");
		PageDirectoryTableModel model = (PageDirectoryTableModel) pageDirectoryTable.getModel();
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}

		Vector<String[]> r = VMController.getVM().pageTable(pageDirectoryBaseAddress, CommonLib.getBit(CommonLib.string2long(registerPanel.cr4TextField.getText()), 4) == 1,
				CommonLib.getBit(CommonLib.string2long(registerPanel.cr4TextField.getText()), 5) == 1);
		for (String s[] : r) {
			model.addRow(s);
		}
		pageDirectoryTable.setModel(model);

	}

	private void updateStack() {
		try {
			statusLabel.setText("Updating stack");
			registerPanel.stackList.removeAll();
			DefaultListModel model = new DefaultListModel();
			Vector<String> r = VMController.getVM().stack();
			for (String s : r) {
				model.addElement(s);
			}
			registerPanel.stackList.setModel(model);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	boolean is32Bits() {
		String cr0 = registerPanel.cr0TextField.getText();
		return (CommonLib.string2int(cr0.substring(cr0.length() - 1)) & 1) == 1;
	}

	public void updateInstruction(BigInteger address) {
		updateInstruction(address, is32Bits());
	}

	public void updateInstruction(BigInteger address, boolean is32Bits) {
		statusLabel.setText("Updating instruction");
		InstructionTableModel model = (InstructionTableModel) instructionTable.getModel();

		BigInteger cs;
		if (registerPanel.csTextField.getBase() == null || registerPanel.csTextField.getBase().equals("")) {
			cs = CommonLib.string2BigInteger(registerPanel.csTextField.getText());
		} else {
			cs = CommonLib.string2BigInteger(registerPanel.csTextField.getBase());
		}
		BigInteger eip = CommonLib.string2BigInteger(registerPanel.eipTextField.getText());

		Vector<String[]> r = VMController.getVM().instruction(address, cs, eip, is32Bits);
		String lastAddress = null;
		for (String[] s : r) {
			if (lastAddress != s[1]) {
				model.addRow(s);
			}
			lastAddress = s[1];
		}
		Collections.sort(model.getData(), new Comparator<String[]>() {
			@Override
			public int compare(String[] o1, String[] o2) {
				String s1 = StringUtils.leftPad(CommonLib.string2BigInteger(o1[1]).toString(16), 16, '0');
				String s2 = StringUtils.leftPad(CommonLib.string2BigInteger(o2[1]).toString(16), 16, '0');
				s1 = s1.replaceAll("cCode : 0x", "");
				s2 = s2.replaceAll("cCode : 0x", "");
				return s1.compareTo(s2);
			}

		});
		//		model.removeNonOrderInstruction();
		model.fireTableDataChanged();

		if (!registerPanel.eipTextField.getText().equals("")) {
			((SourceCodeTableModel) elfTable.getModel()).updateBreakpoint(getRealEIP());
			((InstructionTableModel) instructionTable.getModel()).updateBreakpoint(getRealEIP());
		}
		instructionTable.repaint();
		jumpToRowInstructionTable(getRealEIP());
		statusLabel.setText("");
	}

	public void updateInstructionUsingNasm(BigInteger address) {
		updateInstructionUsingNasm(address, (Integer.parseInt(registerPanel.cr0TextField.getText()) & 1) == 1);
	}

	private void updateInstructionUsingNasm(BigInteger address, boolean is32Bit) {
		try {
			if (address == null) {
				//BigInteger cs = CommonLib.string2BigInteger(this.registerPanel.csTextField.getText());
				BigInteger eip = CommonLib.string2BigInteger(this.registerPanel.eipTextField.getText());
				eip = eip.and(CommonLib.string2BigInteger("0xffffffffffffffff"));
				address = eip;
			}
			statusLabel.setText("Updating instruction");
			int bytes[] = VMController.getVM().physicalMemory(address, 200);
			String result = Disassemble.disassemble(bytes, is32Bit, address);
			String lines[] = result.split("\n");
			if (lines.length > 0) {
				InstructionTableModel model = (InstructionTableModel) instructionTable.getModel();
				statusProgressBar.setMaximum(lines.length - 1);
				for (int x = 0; x < lines.length; x++) {
					statusProgressBar.setValue(x);
					try {
						// load cCode
						//						logger.debug("pcStr=" + pcStr);
						//						if (!pcStr.matches("^[0-9a-fA-F].*")) {
						//							logger.debug("ar=" + pcStr);
						//							System.exit(1);
						//						}
						String temp[] = lines[x].split("  +");
						if (temp.length == 3) {
							String pcStr = temp[0].substring(0, 8).trim();
							String decodedBytes = temp[1].trim();
							String instruction = temp[2].trim();
							BigInteger pc = CommonLib.string2BigInteger("0x" + pcStr);
							if (pc == null) {
								continue;
							}

							String s[] = getCCode(pc, false);
							String lineNo[] = getCCode(pc, true);
							if (s != null && lineNo != null) {
								for (int index = 0; index < s.length; index++) {
									model.addRow(new String[] { "", "cCode : 0x" + pc.toString(16) + " : " + lineNo[index], s[index], "" });
								}
							}
							// end load cCode
							model.addRow(new String[] { "", pc.toString(), instruction, decodedBytes });

							//model.addRow(new String[] { "", "0x" + StringUtils.leftPad(address.add(pc).toString(16), 16, "0"), lines[x].substring(25).trim(),lines[x].substring(8, 8).trim() });
						} else {
							model.replace(model.getRowCount() - 1, 3, model.getRow(model.getRowCount() - 1)[3] + temp[1].trim().replaceAll("-", ""));
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

				model.removeNonOrderInstruction();
				model.fireTableDataChanged();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void jumpToRowInstructionTable(BigInteger eip) {
		logger.debug("jumpToRowInstructionTable=" + eip.toString(16));
		InstructionTableModel model = (InstructionTableModel) instructionTable.getModel();
		int eIPRow = model.findEIPRowNo(eip);
		instructionTable.scrollRectToVisible(instructionTable.getCellRect(eIPRow + 10, 1, true));
		sourceLevelDebugger.instructionTable.scrollRectToVisible(instructionTable.getCellRect(eIPRow + 10, 1, true));
		instructionTable.scrollRectToVisible(instructionTable.getCellRect(eIPRow, 1, true));
		sourceLevelDebugger.instructionTable.scrollRectToVisible(instructionTable.getCellRect(eIPRow, 1, true));
	}

	private String getASMCode(BigInteger pc) {
		for (int x = 0; x < instructionTable.getRowCount(); x++) {
			String col1 = instructionTable.getValueAt(x, 1).toString();
			if (col1.startsWith("cCode")) {
				continue;
			}
			BigInteger address = CommonLib.string2BigInteger(col1.toString());
			if (pc.equals(address)) {
				return instructionTable.getValueAt(x, 2).toString();
			}
		}
		return null;
	}

	public String[] getCCode(BigInteger pc, boolean getFile) {
		for (Dwarf dwarf : sourceLevelDebugger.peterDwarfPanel.dwarfs) {
			try {
				// if (pc.equals(BigInteger.valueOf(0x1600000))) {
				// logger.debug("test");
				// }
				// if (pc.equals(BigInteger.valueOf(0x160000c))) {
				// logger.debug("test");
				// }
				// if (pc.equals(BigInteger.valueOf(0x160000f))) {
				// logger.debug("test");
				// }
				if (pc.equals(BigInteger.valueOf(0x1600943))) {
					logger.debug("test");
				}
				DwarfLine startLine = null;
				DwarfLine endLine = null;
				DwarfDebugLineHeader startHeader = null;

				outerloop: for (DwarfDebugLineHeader header : dwarf.headers) {
					boolean toggle = false;
					for (DwarfLine line : header.lines) {
						if (!toggle && line.address.equals(pc)) {
							startLine = line;
							startHeader = header;
							toggle = true;
							continue;
						}
						if (toggle && !line.address.equals(startLine.address) && line.line_num != startLine.line_num) {
							endLine = line;
							break outerloop;
						}
					}
					startLine = null;
					endLine = null;
				}
				if (startHeader == null || startLine == null) {
					return null;
				}

				File file = startHeader.filenames.get((int) startLine.file_num).file;
				List<String> sourceLines = CacheStructure.fileCaches.get(file);
				if (sourceLines == null) {
					sourceLines = FileUtils.readLines(file);
					CacheStructure.fileCaches.put(file, sourceLines);
				}

				int endLineNo = 0;
				if (endLine == null) {
					endLineNo = sourceLines.size() - startLine.line_num;
				} else {
					endLineNo = endLine.line_num - 1;
				}
				// if (endLineNo - startLine.line_num < 0) {
				// endLineNo = startLine.line_num;
				// }

				// logger.debug(pc.toString(16) + ", " +
				// startLine.line_num + ", " + endLineNo);
				String s[] = new String[endLineNo - startLine.line_num + 1];
				for (int z = startLine.line_num - 1, index = 0; z < endLineNo && z < sourceLines.size(); z++, index++) {
					if (getFile) {
						s[index] = startHeader.filenames.get((int) startLine.file_num).file.getName() + " : " + (z + 1);
					} else {
						String cCode = sourceLines.get(z);
						s[index] = cCode;
					}
				}
				return s;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	private String getCCodeStr(BigInteger pc) {
		String s[] = getCCode(pc, false);
		if (s == null) {
			return "";
		} else {
			String r = "";
			for (String c : s) {
				r += c + "\n";
			}
			return r;
		}
	}

	private void updateGDT() {
		statusLabel.setText("Updating GDT");
		int gdtLimit = Integer.parseInt(this.registerPanel.gdtrLimitTextField.getText().substring(2), 16);
		gdtLimit = (gdtLimit + 1) / 8 - 1;
		if (gdtLimit > 96) {
			gdtLimit = 96;
		}
		BigInteger gdtVirtualAddress = CommonLib.string2BigInteger(registerPanel.gdtrTextField.getText());
		Vector<Vector<String>> r = VMController.getVM().gdt(gdtVirtualAddress, gdtLimit);
		GDTTableModel model = (GDTTableModel) gdtTable.getModel();
		model.clear();
		for (Vector<String> v : r) {
			model.addValue(v);
		}

		((GDTTableModel) gdtTable.getModel()).fireTableDataChanged();
	}

	private void updateIDT() {
		try {
			statusLabel.setText("Updating IDT");
			int idtLimit = Integer.parseInt(this.registerPanel.idtrLimitTextField.getText().substring(2), 16);
			idtLimit = (idtLimit + 1) / 8 - 1;
			if (idtLimit > 96) {
				idtLimit = 96;
			}
			IDTTableModel model = (IDTTableModel) idtTable.getModel();
			model.clear();
			BigInteger idtVirtualAddress = CommonLib.string2BigInteger(registerPanel.idtrTextField.getText());
			Vector<Vector<String>> r = VMController.getVM().idt(idtVirtualAddress, idtLimit);
			for (Vector<String> v : r) {
				model.addValue(v);
			}
			((IDTTableModel) idtTable.getModel()).fireTableDataChanged();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void updateLDT() {
		try {
			statusLabel.setText("Updating LDT");
			if (VMController.vmType == VMType.Bochs) {
				LDTTableModel model = (LDTTableModel) ldtTable.getModel();
				model.clear();
				BigInteger ldtVirtualAddress = CommonLib.string2BigInteger(registerPanel.ldtrTextField.getText());
				Vector<Vector<String>> r = VMController.getVM().idt(ldtVirtualAddress, 200);

				for (Vector<String> v : r) {
					model.addValue(v);
				}

				model.fireTableDataChanged();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void changeText(JTextField textField, BigInteger value) {
		String newValue = "0x" + value.toString(16);
		if (textField.getText().equals(newValue)) {
			textField.setForeground(Color.black);
		} else {
			textField.setForeground(Color.blue);
		}
		textField.setText(newValue);
	}

	private void changeText(SegmentRegister segmentRegister, String value, String base, String limit, String flags) {
		changeText(segmentRegister, CommonLib.string2BigInteger(value), CommonLib.string2BigInteger(base), CommonLib.string2BigInteger(limit), CommonLib.string2BigInteger(flags));
	}

	private void changeText(SegmentRegister segmentRegister, BigInteger value, BigInteger base, BigInteger limit, BigInteger flags) {
		String newValue = "0x" + value.toString(16);
		if (segmentRegister.getText().equals(newValue)) {
			segmentRegister.setForeground(Color.black);
		} else {
			segmentRegister.setForeground(Color.blue);
		}
		segmentRegister.setText(newValue);
		segmentRegister.setBase("0x" + base.toString(16));
		segmentRegister.setLimit("0x" + limit.toString(16));
		segmentRegister.setFlags("0x" + flags.toString(16));
	}

	private void updateEFlags() {

	}

	private void updateRegister(boolean updateGUI) {
		Hashtable<String, String> ht = VMController.getVM().registers();
		if (ht == null) {
			return;
		}
		changeText(this.registerPanel.eaxTextField, CommonLib.string2BigInteger(ht.get("ax")));
		changeText(this.registerPanel.ebxTextField, CommonLib.string2BigInteger(ht.get("bx")));
		changeText(this.registerPanel.ecxTextField, CommonLib.string2BigInteger(ht.get("cx")));
		changeText(this.registerPanel.edxTextField, CommonLib.string2BigInteger(ht.get("dx")));
		changeText(this.registerPanel.esiTextField, CommonLib.string2BigInteger(ht.get("si")));
		changeText(this.registerPanel.ediTextField, CommonLib.string2BigInteger(ht.get("di")));
		changeText(this.registerPanel.ebpTextField, CommonLib.string2BigInteger(ht.get("bp")));
		changeText(this.registerPanel.espTextField, CommonLib.string2BigInteger(ht.get("sp")));
		changeText(this.registerPanel.eipTextField, CommonLib.string2BigInteger(ht.get("ip")));
		changeText(this.registerPanel.eflagsTextField, CommonLib.string2BigInteger(ht.get("eflags")));
		changeText(this.registerPanel.csTextField, ht.get("cs"), ht.get("cs_base"), ht.get("cs_limit"), ht.get("cs_flags"));
		changeText(this.registerPanel.dsTextField, ht.get("ds"), ht.get("ds_base"), ht.get("ds_limit"), ht.get("ds_flags"));
		changeText(this.registerPanel.esTextField, ht.get("es"), ht.get("es_base"), ht.get("es_limit"), ht.get("es_flags"));
		changeText(this.registerPanel.fsTextField, ht.get("fs"), ht.get("fs_base"), ht.get("fs_limit"), ht.get("fs_flags"));
		changeText(this.registerPanel.gsTextField, ht.get("gs"), ht.get("gs_base"), ht.get("gs_limit"), ht.get("gs_flags"));
		changeText(this.registerPanel.ssTextField, ht.get("ss"), ht.get("ss_base"), ht.get("ss_limit"), ht.get("ss_flags"));
		changeText(this.registerPanel.gdtrTextField, CommonLib.string2BigInteger(ht.get("gdtr")));
		changeText(this.registerPanel.gdtrLimitTextField, CommonLib.string2BigInteger(ht.get("gdtr_limit")));
		changeText(this.registerPanel.ldtrTextField, CommonLib.string2BigInteger(ht.get("ldtr")));
		changeText(this.registerPanel.idtrTextField, CommonLib.string2BigInteger(ht.get("idtr")));
		changeText(this.registerPanel.idtrLimitTextField, CommonLib.string2BigInteger(ht.get("idtr_limit")));
		changeText(this.registerPanel.trTextField, CommonLib.string2BigInteger(ht.get("tr")));
		registerPanel.eflagLabel.setText(ht.get("eflagsDetail"));

		cpuModeLabel.setText(ht.get("model"));
		changeText(this.registerPanel.cr0TextField, CommonLib.string2BigInteger(ht.get("cr0")));
		registerPanel.cr0DetailLabel.setText(ht.get("cr0Detail"));
		registerPanel.cr0DetailLabel2.setText(ht.get("cr0Detail2"));
		changeText(this.registerPanel.cr2TextField, CommonLib.string2BigInteger(ht.get("cr2")));
		changeText(this.registerPanel.cr3TextField, CommonLib.string2BigInteger(ht.get("cr3")));
		changeText(this.registerPanel.cr4TextField, CommonLib.string2BigInteger(ht.get("cr4")));
		registerPanel.cr4DetailLabel.setText(ht.get("cr4Detail"));
		changeText(this.registerPanel.dr0TextField, CommonLib.string2BigInteger(ht.get("dr0")));
		changeText(this.registerPanel.dr1TextField, CommonLib.string2BigInteger(ht.get("dr1")));
		changeText(this.registerPanel.dr2TextField, CommonLib.string2BigInteger(ht.get("dr2")));
		changeText(this.registerPanel.dr3TextField, CommonLib.string2BigInteger(ht.get("dr3")));
		changeText(this.registerPanel.dr6TextField, CommonLib.string2BigInteger(ht.get("dr6")));
		changeText(this.registerPanel.dr7TextField, CommonLib.string2BigInteger(ht.get("dr7")));
		changeText(this.registerPanel.st0TextField, CommonLib.string2BigInteger(ht.get("st0")));
		changeText(this.registerPanel.st1TextField, CommonLib.string2BigInteger(ht.get("st1")));
		changeText(this.registerPanel.st2TextField, CommonLib.string2BigInteger(ht.get("st2")));
		changeText(this.registerPanel.st3TextField, CommonLib.string2BigInteger(ht.get("st3")));
		changeText(this.registerPanel.st4TextField, CommonLib.string2BigInteger(ht.get("st4")));
		changeText(this.registerPanel.st5TextField, CommonLib.string2BigInteger(ht.get("st5")));
		changeText(this.registerPanel.st6TextField, CommonLib.string2BigInteger(ht.get("st6")));
		changeText(this.registerPanel.st7TextField, CommonLib.string2BigInteger(ht.get("st7")));
		changeText(this.registerPanel.fpuStatusTextField, CommonLib.string2BigInteger(ht.get("fpuStatus")));
		changeText(this.registerPanel.fpuControlTextField, CommonLib.string2BigInteger(ht.get("fpuControl")));
		changeText(this.registerPanel.fpuTagTextField, CommonLib.string2BigInteger(ht.get("fpuTag")));
		changeText(this.registerPanel.fpuOperandTextField, CommonLib.string2BigInteger(ht.get("fpuOperand")));
		changeText(this.registerPanel.fipTextField, CommonLib.string2BigInteger(ht.get("fip")));
		changeText(this.registerPanel.fcsTextField, CommonLib.string2BigInteger(ht.get("fcs")));
		changeText(this.registerPanel.fdpTextField, CommonLib.string2BigInteger(ht.get("fdp")));
		changeText(this.registerPanel.fdsTextField, CommonLib.string2BigInteger(ht.get("fds")));
		changeText(this.registerPanel.mmx0TextField, CommonLib.string2BigInteger(ht.get("mm0")));
		changeText(this.registerPanel.mmx1TextField, CommonLib.string2BigInteger(ht.get("mm1")));
		changeText(this.registerPanel.mmx2TextField, CommonLib.string2BigInteger(ht.get("mm2")));
		changeText(this.registerPanel.mmx3TextField, CommonLib.string2BigInteger(ht.get("mm3")));
		changeText(this.registerPanel.mmx4TextField, CommonLib.string2BigInteger(ht.get("mm4")));
		changeText(this.registerPanel.mmx5TextField, CommonLib.string2BigInteger(ht.get("mm5")));
		changeText(this.registerPanel.mmx6TextField, CommonLib.string2BigInteger(ht.get("mm6")));
		changeText(this.registerPanel.mmx7TextField, CommonLib.string2BigInteger(ht.get("mm7")));

	}

	private void updateMemory(boolean isPhysicalAddress) {
		try {
			if (this.memoryAddressComboBox.getSelectedItem() != null) {
				int totalByte = 200;
				int bytes[] = new int[0];
				//					commandReceiver.shouldShow = false;

				currentMemoryWindowsAddress = CommonLib.string2BigInteger(this.memoryAddressComboBox.getSelectedItem().toString());
				statusLabel.setText("Updating memory");
				bytes = VMController.getVM().physicalMemory(CommonLib.string2BigInteger(this.memoryAddressComboBox.getSelectedItem().toString()), totalByte);

				statusLabel.setText("");
				hexTable.getModel().setCurrentAddress(CommonLib.string2long(this.memoryAddressComboBox.getSelectedItem().toString()));
				hexTable.getModel().set(bytes);
				hexTable.getModel().fireTableDataChanged();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void exitMenuItemActionPerformed(ActionEvent evt) {
		thisWindowClosing(null);
		System.exit(0);
	}

	private void thisWindowClosing(WindowEvent evt) {
		VMController.getVM().stopVM();

		Setting.getInstance().width = this.getWidth();
		Setting.getInstance().height = this.getHeight();
		Setting.getInstance().x = this.getLocation().x;
		Setting.getInstance().y = this.getLocation().y;
		Setting.getInstance().divX = jSplitPane1.getDividerLocation();
		Setting.getInstance().divY = jSplitPane2.getDividerLocation();
		Setting.getInstance().osDebugSplitPane_DividerLocation = this.osDebugInformationPanel1.getMainSplitPane().getDividerLocation();
		Setting.getInstance().save();
	}

	private void jGDTTableMouseClicked(MouseEvent evt) {
		if (evt.getClickCount() == 2) {
			int gdtNo = CommonLib.string2int((String) gdtTable.getValueAt(gdtTable.getSelectedRow(), 0));
			String tabTitle = "GDT " + String.format("0x%02x", gdtNo);
			for (int x = 0; x < bottomTabbedPane.getTabCount(); x++) {
				if (bottomTabbedPane.getTitleAt(x).equals(tabTitle)) {
					bottomTabbedPane.setSelectedIndex(x);
					return;
				}
			}

			bottomTabbedPane.addTabWithCloseButton(tabTitle, null, new GDTLDTPanel(this, 0, CommonLib.string2BigInteger(this.registerPanel.gdtrTextField.getText()), gdtNo), null);
			bottomTabbedPane.setSelectedIndex(bottomTabbedPane.getTabCount() - 1);
		}
	}

	private void jLDTTableMouseClicked(MouseEvent evt) {
		if (evt.getClickCount() == 2) {
			for (int x = 0; x < bottomTabbedPane.getTabCount(); x++) {
				if (bottomTabbedPane.getTitleAt(x).equals(("LDT " + ldtTable.getSelectedRow() + 1))) {
					bottomTabbedPane.setSelectedIndex(x);
					return;
				}
			}
			JScrollPane temp = new JScrollPane();
			temp.setViewportView(new GDTLDTPanel(this, 1, CommonLib.string2BigInteger(this.registerPanel.ldtrTextField.getText()), ldtTable.getSelectedRow() + 1));
			bottomTabbedPane.addTabWithCloseButton("LDT " + ldtTable.getSelectedRow(), null, temp, null);
			bottomTabbedPane.setSelectedIndex(bottomTabbedPane.getTabCount() - 1);
		}
	}

	private void jUpdateBochsButtonActionPerformed(ActionEvent evt) {
		updateVMStatus(true);
	}

	private void pageDirectoryTableMouseClicked(MouseEvent evt) {
		statusProgressBar.setValue(0);
		String pageTableAddress = pageDirectoryTable.getValueAt(pageDirectoryTable.getSelectedRow(), 1).toString();
		int ps = Integer.parseInt(pageDirectoryTable.getValueAt(pageDirectoryTable.getSelectedRow(), 4).toString());
		if (!CommonLib.isNumber(pageTableAddress)) {
			return;
		}
		System.out.println("pageTableAddress=" + pageTableAddress);
		int bytes[] = VMController.getVM().physicalMemory(CommonLib.string2BigInteger(pageTableAddress), 4096);
		PageTableTableModel model = (PageTableTableModel) pageTableTable.getModel();
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}

		boolean pse = CommonLib.getBit(CommonLib.string2long(registerPanel.cr4TextField.getText()), 4) == 1;
		boolean pae = CommonLib.getBit(CommonLib.string2long(registerPanel.cr4TextField.getText()), 5) == 1;

		for (int x = 0; x <= bytes.length - 4; x += 4) {
			long value = CommonLib.getInt(bytes, x);
			if (!pse && !pae) {
				String base = "0x" + Long.toHexString(CommonLib.getValue(value, 12, 31) << 12);
				String avl = String.valueOf((value >> 9) & 3);
				String g = String.valueOf((value >> 8) & 1);
				String pat = String.valueOf((value >> 7) & 1);
				String d = String.valueOf((value >> 6) & 1);
				String a = String.valueOf((value >> 5) & 1);
				String pcd = String.valueOf((value >> 4) & 1);
				String pwt = String.valueOf((value >> 3) & 1);
				String us = String.valueOf((value >> 2) & 1);
				String wr = String.valueOf((value >> 1) & 1);
				String p = String.valueOf((value >> 0) & 1);
				boolean tempB = model.isShowZeroAddress();
				model.setShowZeroAddress(true);
				model.addRow(new String[] { String.valueOf(x / 4), base, avl, g, pat, d, a, pcd, pwt, us, wr, p });
				model.setShowZeroAddress(tempB);
			} else if (pse && !pae) {
				if (ps == 0) {
					String base = "0x" + Long.toHexString(CommonLib.getValue(value, 12, 31) << 12);
					String avl = String.valueOf((value >> 9) & 3);
					String g = String.valueOf((value >> 8) & 1);
					String pat = String.valueOf((value >> 7) & 1);
					String d = String.valueOf((value >> 6) & 1);
					String a = String.valueOf((value >> 5) & 1);
					String pcd = String.valueOf((value >> 4) & 1);
					String pwt = String.valueOf((value >> 3) & 1);
					String us = String.valueOf((value >> 2) & 1);
					String wr = String.valueOf((value >> 1) & 1);
					String p = String.valueOf((value >> 0) & 1);
					boolean tempB = model.isShowZeroAddress();
					model.setShowZeroAddress(true);
					model.addRow(new String[] { String.valueOf(x / 4), base, avl, g, pat, d, a, pcd, pwt, us, wr, p });
					model.setShowZeroAddress(tempB);
				} else {
					// no page table
				}
			}
		}
		pageTableTable.setModel(model);
	}

	private void pageTableTableMouseClicked(MouseEvent evt) {
		if (evt.getClickCount() == 2) {
			String pageAddress = pageTableTable.getValueAt(pageTableTable.getSelectedRow(), 1).toString();
			this.memoryAddressComboBox.setSelectedItem(pageAddress);
			this.goMemoryButtonActionPerformed(null);
		}
	}

	private void refreshBreakpointButtonActionPerformed(ActionEvent evt) {
		jRefreshBreakpointButton.setEnabled(false);
		updateBreakpoint();
		jRefreshBreakpointButton.setEnabled(true);
	}

	private void updateBreakpoint() {
		try {
			statusLabel.setText("Updating breakpoint");

			DefaultTableModel model = (DefaultTableModel) breakpointTable.getModel();
			while (model.getRowCount() > 0) {
				model.removeRow(0);
			}

			Vector<Vector<String>> r = VMController.getVM().breakpoint();
			for (Vector<String> v : r) {
				model.addRow(v);
			}
			this.refreshELFBreakpointButtonActionPerformed(null);
			statusLabel.setText("");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void disassembleCurrentIPButtonActionPerformed(ActionEvent evt) {
		disassembleCurrentIPButton.setEnabled(false);
		updateInstruction(null);
		updateBreakpointTableColor();
		disassembleCurrentIPButton.setEnabled(true);
	}

	private void addBreakpointButtonActionPerformed(ActionEvent evt) {
		addBreakpointButton.setEnabled(false);
		String type = (String) JOptionPane.showInputDialog(this, null, "Add breakpoint", JOptionPane.QUESTION_MESSAGE, null,
				new Object[] { MyLanguage.getString("Physical_address"), MyLanguage.getString("Linear_address"), MyLanguage.getString("Virtual_address") },
				MyLanguage.getString("Physical_address"));
		if (type != null) {
			String address = JOptionPane.showInputDialog(this, "Please input breakpoint address", "Add breakpoint", JOptionPane.QUESTION_MESSAGE);
			if (address != null) {
				if (type.equals(MyLanguage.getString("Physical_address"))) {
					VMController.getVM().addPhysicalBreakpoint(CommonLib.string2BigInteger(address));
				} else if (type.equals(MyLanguage.getString("Linear_address"))) {
					VMController.getVM().addLinearBreakpoint(CommonLib.string2BigInteger(address));
				} else {
					try {
						VMController.getVM().addVirtualBreakpoint(CommonLib.string2BigInteger(address.split(":")[0]), CommonLib.string2BigInteger(address.split(":")[1]));
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(this, "Virtual address should be in form 0x12:0xabcdef", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				updateBreakpoint();
				updateBreakpointTableColor();
			}
		}
		addBreakpointButton.setEnabled(true);
	}

	private void saveBreakpointButtonActionPerformed(ActionEvent evt) {
		saveBreakpointButton.setEnabled(false);
		LinkedList<Breakpoint> v = Setting.getInstance().breakpoint;
		v.clear();

		for (int x = 0; x < this.breakpointTable.getRowCount(); x++) {
			com.gkd.Breakpoint h = new com.gkd.Breakpoint();
			h.setNo(x);
			h.setType(this.breakpointTable.getValueAt(x, 1).toString());
			h.setEnable(this.breakpointTable.getValueAt(x, 1).toString());
			String breakpointAddress = breakpointTable.getValueAt(x, 2).toString();
			if (breakpointAddress.contains(":")) {
				h.setAddress(CommonLib.string2BigInteger(breakpointAddress.split(":")[0]));
				h.setSegment(CommonLib.string2BigInteger(breakpointAddress.split(":")[1]));
			} else {
				h.setAddress(CommonLib.string2BigInteger(breakpointAddress));
			}
			h.setHit(CommonLib.string2int(this.breakpointTable.getValueAt(x, 3).toString()));
			v.add(h);
		}
		Setting.getInstance().save();
		saveBreakpointButton.setEnabled(true);
	}

	private void loadBreakpointButtonActionPerformed(ActionEvent evt) {
		if (loadBreakpointButton.getEventSource() == loadElfMenuItem) {
			JFileChooser fc = new JFileChooser(new File("."));
			int returnVal = fc.showOpenDialog(this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				sourceLevelDebugger.loadELF(file, null, 0);
			}
		} else {
			loadBreakpointButton.setEnabled(false);
			LinkedList<Breakpoint> vector = Setting.getInstance().breakpoint;
			try {
				for (int x = 0; x < vector.size(); x++) {
					boolean match = false;
					for (int y = 0; y < this.breakpointTable.getRowCount(); y++) {
						if (vector.get(x).getAddress().equals(CommonLib.string2BigInteger(breakpointTable.getValueAt(y, 2).toString().trim()))) {
							match = true;
							break;
						}
					}
					if (!match) {
						if (vector.get(x).getType().contains("pbreakpoint")) {
							//sendCommand("pb " + vector.get(x).getAddress());
							//addPhysicalBreakpoint(vector.get(x).getAddress(), MyLanguage.getString("Physical_address"));
							VMController.getVM().addPhysicalBreakpoint(vector.get(x).getAddress());
						} else if (vector.get(x).getType().contains("lbreakpoint")) {
							//							sendBochsCommand("lb " + vector.get(x).getAddress());
							VMController.getVM().addLinearBreakpoint(vector.get(x).getAddress());
						} else {
							VMController.getVM().addLinearBreakpoint(vector.get(x).getAddress());
						}
						if (VMController.vmType == VMType.Bochs && vector.get(x).getEnable().trim().equals("keep n")) {
							VMController.getVM().disablePhysicalBreakpoint(BigInteger.valueOf(x + 1));
						}
					}
				}
			} catch (Exception e) {
				if (Global.debug) {
					e.printStackTrace();
				}
			}
			updateBreakpoint();
			updateBreakpointTableColor();
			loadBreakpointButton.setEnabled(true);
		}
	}

	private void deleteBreakpointButtonActionPerformed(ActionEvent evt) {
		deleteBreakpointButton.setEnabled(false);
		int rows[] = breakpointTable.getSelectedRows();
		for (int x = 0; x < rows.length; x++) {
			VMController.getVM().deletePhysicalBreakpoint(CommonLib.string2BigInteger(breakpointTable.getValueAt(rows[x], 2).toString()));
		}
		updateBreakpoint();
		updateBreakpointTableColor();
		deleteBreakpointButton.setEnabled(true);
	}

	private void disableBreakpointButtonActionPerformed(ActionEvent evt) {
		disableBreakpointButton.setEnabled(false);
		int rows[] = breakpointTable.getSelectedRows();
		for (int x = 0; x < rows.length; x++) {
			VMController.getVM().disablePhysicalBreakpoint(
					CommonLib.string2BigInteger(breakpointTable.getValueAt(rows[x], 0).toString().replaceAll("^-*", "").trim().split(" ")[0]));
		}
		updateBreakpoint();
		updateBreakpointTableColor();
		disableBreakpointButton.setEnabled(true);
		updateInstruction(null);
	}

	private void enableBreakpointButtonActionPerformed(ActionEvent evt) {
		enableBreakpointButton.setEnabled(false);
		int rows[] = breakpointTable.getSelectedRows();
		for (int x = 0; x < rows.length; x++) {
			VMController.getVM()
					.enablePhysicalBreakpoint(CommonLib.string2BigInteger(breakpointTable.getValueAt(rows[x], 0).toString().replaceAll("^-*", "").trim().split(" ")[0]));
		}
		updateBreakpoint();
		updateBreakpointTableColor();
		enableBreakpointButton.setEnabled(true);
		updateInstruction(null);
	}

	private void vmCommandTextFieldKeyPressed(KeyEvent evt) {
		if (vmCommandTextField.getText().equals("")) {
			commandHistoryIndex = 0;
		}
		HashSet<String> vector = Setting.getInstance().vmCommandHistory;
		if (evt.getKeyCode() == 38) {
			if (commandHistoryIndex < vector.size()) {
				commandHistoryIndex++;
				this.vmCommandTextField.setText(vector.toArray()[vector.size() - commandHistoryIndex].toString());
			}
		} else if (evt.getKeyCode() == 40) {
			if (commandHistoryIndex > 1) {
				commandHistoryIndex--;
				this.vmCommandTextField.setText(vector.toArray()[vector.size() - commandHistoryIndex].toString());
			}
		}
	}

	private ButtonGroup getButtonGroup1() {
		if (buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
		}
		return buttonGroup1;
	}

	private void binaryRadioButtonItemStateChanged(ItemEvent evt) {
		hexTable.getModel().setRadix(2);
		hexTable.getModel().fireTableDataChanged();
	}

	private void octRadioButton1ItemStateChanged(ItemEvent evt) {
		hexTable.getModel().setRadix(8);
		hexTable.getModel().fireTableDataChanged();
	}

	private void decRadioButtonItemStateChanged(ItemEvent evt) {
		hexTable.getModel().setRadix(10);
		hexTable.getModel().fireTableDataChanged();
	}

	private void hexRadioButtonItemStateChanged(ItemEvent evt) {
		hexTable.getModel().setRadix(16);
		hexTable.getModel().fireTableDataChanged();
	}

	private JScrollPane getTableTranslateScrollPane() {
		if (tableTranslateScrollPane == null) {
			tableTranslateScrollPane = new JScrollPane();
			tableTranslateScrollPane.setViewportView(getJPanel30());
		}
		return tableTranslateScrollPane;
	}

	private JTable getAddressTranslateTable() {
		if (addressTranslateTable == null) {
			TableModel jAddressTranslateTableModel = new DefaultTableModel(new String[][] {}, new String[] { MyLanguage.getString("From"), MyLanguage.getString("To") });
			addressTranslateTable = new JTable();
			addressTranslateTable.setModel(jAddressTranslateTableModel);
		}
		return addressTranslateTable;
	}

	private JMenu getTopFontMenu() {
		if (topFontMenu == null) {
			topFontMenu = new JMenu();
			topFontMenu.setText(MyLanguage.getString("Font"));
			topFontMenu.add(getSizeMenu());
			topFontMenu.add(getFontMenu());
		}
		return topFontMenu;
	}

	private JMenuItem getFont8MenuItem() {
		if (font8MenuItem == null) {
			font8MenuItem = new JMenuItem();
			font8MenuItem.setText("8");
			font8MenuItem.setBounds(0, -110, 80, 22);
			font8MenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					font8MenuItemActionPerformed(evt);
				}
			});
		}
		return font8MenuItem;
	}

	private JMenuItem getFont10MenuItem() {
		if (font10MenuItem == null) {
			font10MenuItem = new JMenuItem();
			font10MenuItem.setText("10");
			font10MenuItem.setBounds(0, -88, 80, 22);
			font10MenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					font10MenuItemActionPerformed(evt);
				}
			});
		}
		return font10MenuItem;
	}

	private JMenuItem getFont12MenuItem() {
		if (font12MenuItem == null) {
			font12MenuItem = new JMenuItem();
			font12MenuItem.setText("12");
			font12MenuItem.setBounds(0, -66, 80, 22);
			font12MenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					font12MenuItemActionPerformed(evt);
				}
			});
		}
		return font12MenuItem;
	}

	private JMenuItem getFont14MenuItem() {
		if (font14MenuItem == null) {
			font14MenuItem = new JMenuItem();
			font14MenuItem.setText("14");
			font14MenuItem.setBounds(0, -44, 80, 22);
			font14MenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					font14MenuItemActionPerformed(evt);
				}
			});
		}
		return font14MenuItem;
	}

	private void font14MenuItemActionPerformed(ActionEvent evt) {
		Setting.getInstance().fontsize = 14;
		initGlobalFontSetting(new Font(Setting.getInstance().fontFamily, Font.PLAIN, Setting.getInstance().fontsize));
	}

	private void font12MenuItemActionPerformed(ActionEvent evt) {
		Setting.getInstance().fontsize = 12;
		initGlobalFontSetting(new Font(Setting.getInstance().fontFamily, Font.PLAIN, Setting.getInstance().fontsize));
	}

	private void font10MenuItemActionPerformed(ActionEvent evt) {
		Setting.getInstance().fontsize = 10;
		initGlobalFontSetting(new Font(Setting.getInstance().fontFamily, Font.PLAIN, Setting.getInstance().fontsize));
	}

	private void font8MenuItemActionPerformed(ActionEvent evt) {
		Setting.getInstance().fontsize = 8;
		initGlobalFontSetting(new Font(Setting.getInstance().fontFamily, Font.PLAIN, Setting.getInstance().fontsize));
	}

	public void initGlobalFontSetting(Font fnt) {
		//		FontUIResource fontRes = new FontUIResource(fnt);
		//		for (Enumeration keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
		//			Object key = keys.nextElement();
		//			Object value = UIManager.get(key);
		//			if (value instanceof FontUIResource) {
		//				UIManager.put(key, fontRes);
		//			}
		//		}
		//		SwingUtilities.updateComponentTreeUI(this);
	}

	private JMenu getSizeMenu() {
		if (sizeMenu == null) {
			sizeMenu = new JMenu();
			sizeMenu.setText(MyLanguage.getString("Size"));
			sizeMenu.add(getFont8MenuItem());
			sizeMenu.add(getFont10MenuItem());
			sizeMenu.add(getFont12MenuItem());
			sizeMenu.add(getFont14MenuItem());
		}
		return sizeMenu;
	}

	private JMenu getFontMenu() {
		if (fontMenu == null) {
			fontMenu = new JMenu();
			fontMenu.setText(MyLanguage.getString("Font"));
			fontMenu.add(getArialMenuItem());
			fontMenu.add(getDialogMenuItem());
		}
		return fontMenu;
	}

	private JMenuItem getArialMenuItem() {
		if (arialMenuItem == null) {
			arialMenuItem = new JMenuItem();
			arialMenuItem.setText("Arial");
			arialMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					arialMenuItemActionPerformed(evt);
				}
			});
		}
		return arialMenuItem;
	}

	private JMenuItem getDialogMenuItem() {
		if (dialogMenuItem == null) {
			dialogMenuItem = new JMenuItem();
			dialogMenuItem.setText("Dialog");
			dialogMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					dialogMenuItemActionPerformed(evt);
				}
			});
		}
		return dialogMenuItem;
	}

	private void arialMenuItemActionPerformed(ActionEvent evt) {
		Setting.getInstance().fontFamily = "Arial";
		initGlobalFontSetting(new Font(Setting.getInstance().fontFamily, Font.PLAIN, Setting.getInstance().fontsize));
	}

	private void dialogMenuItemActionPerformed(ActionEvent evt) {
		Setting.getInstance().fontFamily = "Dialog";
		initGlobalFontSetting(new Font(Setting.getInstance().fontFamily, Font.PLAIN, Setting.getInstance().fontsize));
	}

	private JMenu getLanguageMenu() {
		if (languageMenu == null) {
			languageMenu = new JMenu();
			languageMenu.setText(MyLanguage.getString("Language"));
			languageMenu.add(getEnglishMenuItem());
			languageMenu.add(getTraditionalChineseMenuItem());
			languageMenu.add(getSimplifiedChineseMenuItem());
			languageMenu.add(getKoreanMenuItem());
			languageMenu.add(getJapaneseMenuItem());
		}
		return languageMenu;
	}

	private JMenuItem getEnglishMenuItem() {
		if (englishMenuItem == null) {
			englishMenuItem = new JMenuItem();
			englishMenuItem.setText(MyLanguage.getString("English"));
			englishMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					englishMenuItemActionPerformed(evt);
				}
			});
		}
		return englishMenuItem;
	}

	private JMenuItem getTraditionalChineseMenuItem() {
		if (traditionalChineseMenuItem == null) {
			traditionalChineseMenuItem = new JMenuItem();
			traditionalChineseMenuItem.setText(MyLanguage.getString("Traditional_chinese"));
			traditionalChineseMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					traditionalChineseMenuItemActionPerformed(evt);
				}
			});
		}
		return traditionalChineseMenuItem;
	}

	private JMenuItem getSimplifiedChineseMenuItem() {
		if (simplifiedChineseMenuItem == null) {
			simplifiedChineseMenuItem = new JMenuItem();
			simplifiedChineseMenuItem.setText(MyLanguage.getString("Simplified_chinese"));
			simplifiedChineseMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					simplifiedChineseMenuItemActionPerformed(evt);
				}
			});
		}
		return simplifiedChineseMenuItem;
	}

	private void traditionalChineseMenuItemActionPerformed(ActionEvent evt) {
		changeLanguage("zh_TW");
	}

	private void englishMenuItemActionPerformed(ActionEvent evt) {
		changeLanguage("en_US");
	}

	private void simplifiedChineseMenuItemActionPerformed(ActionEvent evt) {
		changeLanguage("zh_CN");
	}

	private void changeLanguage(String language) {
		JOptionPane.showMessageDialog(this, "Please restart");

		Setting.getInstance().currentLanguage = language;
		Setting.getInstance().save();
	}

	private JTable getJHistoryTable() {
		if (historyTable == null) {
			historyTable = new JTable();
			HistoryTableModel model = new HistoryTableModel();
			historyTable.setModel(model);
			final MyTableRowSorter<TableModel> sorter = new MyTableRowSorter<TableModel>(model);
			historyTable.setRowSorter(sorter);
			historyTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			for (int x = 1; x <= 15; x++) {
				historyTable.getColumnModel().getColumn(x).setPreferredWidth(120);
			}
			historyTable.getColumnModel().getColumn(5).setPreferredWidth(800);
		}
		historyTable.setDefaultRenderer(String.class, new HistoryTableCellRenderer());
		historyTable.setIntercellSpacing(new Dimension(0, 0));
		historyTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				historyTableMouseClicked(evt);
			}
		});
		return historyTable;
	}

	private JRadioButton getRegRadioButton() {
		if (regRadioButton == null) {
			regRadioButton = new JRadioButton();
			regRadioButton.setText("reg");
			getButtonGroup2().add(regRadioButton);
			regRadioButton.setSelected(true);
			regRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					regRadioButtonActionPerformed(evt);
				}
			});
		}
		return regRadioButton;
	}

	private JToolBar getJPanel13() {
		if (jPanel13 == null) {
			jPanel13 = new JToolBar();
			FlowLayout jPanel13Layout = new FlowLayout();
			jPanel13Layout.setAlignment(FlowLayout.LEFT);
			{
				jLabel3 = new JLabel();
				jPanel13.add(jLabel3);
				jLabel3.setText(MyLanguage.getString("Pause_history"));
			}
			jPanel13.add(getRegRadioButton());
			jPanel13.add(getTblRadioButton());
			jPanel13.add(getFPURadioButton());
			jPanel13.add(getMMXRadioButton());
			jPanel13.add(getSaveHistoryTableButton());
			jPanel13.add(getExportHistoryToExcelButton());
			jPanel13.add(getClearHistoryTableButton());
			jPanel13.add(getJLabel2());
			jPanel13.add(getHistoryTableRepeatedLabel());
			jPanel13.add(getFilterHistoryTableTextField());
			jPanel13.add(getJLabel9());
			jPanel13.add(getJLabel8());
			jPanel13.add(getJLabel7());
			jPanel13.add(getShowAfterwardSpinner());
		}
		return jPanel13;
	}

	private JRadioButton getTblRadioButton() {
		if (tblRadioButton == null) {
			tblRadioButton = new JRadioButton();
			tblRadioButton.setText("tbl. desc.");
			getButtonGroup2().add(tblRadioButton);
			tblRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					tblRadioButtonActionPerformed(evt);
				}
			});
		}
		return tblRadioButton;
	}

	private ButtonGroup getButtonGroup2() {
		if (buttonGroup2 == null) {
			buttonGroup2 = new ButtonGroup();
		}
		return buttonGroup2;
	}

	private void regRadioButtonActionPerformed(ActionEvent evt) {
		HistoryTableModel model = (HistoryTableModel) this.historyTable.getModel();
		model.setView("reg");
		for (int x = 1; x <= 15; x++) {
			historyTable.getColumnModel().getColumn(x).setPreferredWidth(120);
		}
		historyTable.getColumnModel().getColumn(5).setPreferredWidth(800);
	}

	private void tblRadioButtonActionPerformed(ActionEvent evt) {
		HistoryTableModel model = (HistoryTableModel) this.historyTable.getModel();
		model.setView("tbl");
		for (int x = 1; x < model.getColumnCount(); x++) {
			historyTable.getColumnModel().getColumn(x).setPreferredWidth(120);
		}
		historyTable.getColumnModel().getColumn(6).setPreferredWidth(300);
	}

	private JSplitPane getJSplitPane3() {
		if (jSplitPane3 == null) {
			jSplitPane3 = new JSplitPane();
			jSplitPane3.setDividerLocation(400);
			{
				jScrollPane7 = new JScrollPane();
				jSplitPane3.add(jScrollPane7, JSplitPane.RIGHT);
				{
					pageTableTable = new JTable();
					jScrollPane7.setViewportView(pageTableTable);
					pageTableTable.setModel(new PageTableTableModel());
					pageTableTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					pageTableTable.getColumnModel().getColumn(0).setPreferredWidth(40);
					for (int x = 2; x <= 11; x++) {
						pageTableTable.getColumnModel().getColumn(x).setPreferredWidth(40);
					}
					pageTableTable.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							pageTableTableMouseClicked(evt);
						}
					});
				}
			}
			{
				jScrollPane8 = new JScrollPane();
				jSplitPane3.add(jScrollPane8, JSplitPane.LEFT);
				{
					pageDirectoryTable = new JTable();
					jScrollPane8.setViewportView(pageDirectoryTable);
					pageDirectoryTable.setModel(new PageDirectoryTableModel());
					pageDirectoryTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					pageDirectoryTable.getColumnModel().getColumn(0).setPreferredWidth(40);
					for (int x = 2; x < 11; x++) {
						pageDirectoryTable.getColumnModel().getColumn(x).setPreferredWidth(40);
					}
					pageDirectoryTable.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							pageDirectoryTableMouseClicked(evt);
						}
					});
				}
			}
		}
		return jSplitPane3;
	}

	private JButton getSaveHistoryTableButton() {
		if (saveHistoryTableImageButton == null) {
			saveHistoryTableImageButton = new JButton();
			saveHistoryTableImageButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/disk.png")));
			saveHistoryTableImageButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					saveHistoryTableButtonActionPerformed(evt);
				}
			});
		}
		return saveHistoryTableImageButton;
	}

	private void saveHistoryTableButtonActionPerformed(ActionEvent evt) {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			if (!GKDCommonLib.saveImage(historyTable, file)) {
				JOptionPane.showMessageDialog(this, "Cannot save image.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private JButton getSaveHexTableImageButton() {
		if (saveHexTableImageButton == null) {
			saveHexTableImageButton = new JButton();
			saveHexTableImageButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/disk.png")));
			saveHexTableImageButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					saveHexTableImageButtonActionPerformed(evt);
				}
			});
		}
		return saveHexTableImageButton;
	}

	private void saveHexTableImageButtonActionPerformed(ActionEvent evt) {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			if (!file.getName().toLowerCase().endsWith(".png")) {
				file = new File(file.getAbsolutePath() + ".png");
			}
			if (!GKDCommonLib.saveImage(hexTable, file)) {
				JOptionPane.showMessageDialog(this, "Cannot save image.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private JButton getJButton3() {
		if (saveInstructionTableImageButton == null) {
			saveInstructionTableImageButton = new JButton();
			saveInstructionTableImageButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/disk.png")));
			saveInstructionTableImageButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					saveInstructionTableImageButtonActionPerformed(evt);
				}
			});
		}
		return saveInstructionTableImageButton;
	}

	private void saveInstructionTableImageButtonActionPerformed(ActionEvent evt) {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			if (!GKDCommonLib.saveImage(instructionTable, file)) {
				JOptionPane.showMessageDialog(this, "Cannot save image.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private JButton getExportHistoryToExcelButton() {
		if (exportHistoryToExcelButton == null) {
			exportHistoryToExcelButton = new JButton();
			exportHistoryToExcelButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/excel.gif")));
			exportHistoryToExcelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					exportHistoryToExcelButtonActionPerformed(evt);
				}
			});
		}
		return exportHistoryToExcelButton;
	}

	private void exportHistoryToExcelButtonActionPerformed(ActionEvent evt) {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			if (!file.getName().endsWith(".xlsx")) {
				file = new File(file.getParent() + File.separator + file.getName() + ".xlsx");
			}
			if (file.exists()) {
				int r = JOptionPane.showConfirmDialog(this, "Overwrite " + file.getName() + "?", "Warning", JOptionPane.YES_NO_OPTION);
				if (r == 1) {
					return;
				}
			}
			final JProgressBarDialog d = new JProgressBarDialog(this, "Exporting to XLSX", true);
			d.jProgressBar.setIndeterminate(true);
			d.jProgressBar.setStringPainted(true);

			class MyThread extends Thread {
				File file;

				public MyThread(File file) {
					this.file = file;
				}

				public void run() {
					GKDCommonLib.exportRegisterHistory(file, d);
				}
			}
			d.thread = new MyThread(file);
			d.setVisible(true);
		}
	}

	private JButton getExcelMemoryButton() {
		if (excelMemoryButton == null) {
			excelMemoryButton = new JButton();
			excelMemoryButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/excel.gif")));
			excelMemoryButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					excelMemoryButtonActionPerformed(evt);
				}
			});
		}
		return excelMemoryButton;
	}

	private void excelMemoryButtonActionPerformed(ActionEvent evt) {
		SaveMemoryToXLSDialog d = new SaveMemoryToXLSDialog(this);
		long currentMemoryAddress = CommonLib.string2long(memoryAddressComboBox.getSelectedItem().toString());
		d.fromTextField.setText("0x" + Long.toHexString(currentMemoryAddress));
		d.toTextField.setText("0x" + Long.toHexString(currentMemoryAddress + 64 * 1024));
		d.setVisible(true);
		if (d.ok) {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				if (!file.getName().toLowerCase().endsWith(".xls")) {
					file = new File(file.getAbsolutePath() + ".xls");
				}
				GKDCommonLib.exportTableModelToExcel(file, hexTable.getModel(), memoryAddressComboBox.getSelectedItem().toString());
			}
		}
	}

	private JPanel getJPanel14() {
		if (jPanel14 == null) {
			jPanel14 = new JPanel();
			jPanel14.add(getSaveGDTImageButton());
			jPanel14.add(getExcelGDTButton());
			jPanel14.add(getJGDTGraphButton());
		}
		return jPanel14;
	}

	private JButton getSaveGDTImageButton() {
		if (saveGDTImageButton == null) {
			saveGDTImageButton = new JButton();
			saveGDTImageButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/disk.png")));
			saveGDTImageButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					saveGDTImageButtonActionPerformed(evt);
				}
			});
		}
		return saveGDTImageButton;
	}

	private void saveGDTImageButtonActionPerformed(ActionEvent evt) {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			if (!GKDCommonLib.saveImage(this.gdtTable, file)) {
				JOptionPane.showMessageDialog(this, "Cannot save image.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private JButton getExcelGDTButton() {
		if (excelGDTButton == null) {
			excelGDTButton = new JButton();
			excelGDTButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/excel.gif")));
			excelGDTButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					excelGDTButtonActionPerformed(evt);
				}
			});
		}
		return excelGDTButton;
	}

	private void excelGDTButtonActionPerformed(ActionEvent evt) {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			GKDCommonLib.exportTableModelToExcel(file, this.gdtTable.getModel(), "GDT");
		}
	}

	private JPanel getJPanel15() {
		if (jPanel15 == null) {
			jPanel15 = new JPanel();
			jPanel15.add(getSaveImageButton());
			jPanel15.add(getExcelIDTButton());
		}
		return jPanel15;
	}

	private JPanel getJPanel16() {
		if (jPanel16 == null) {
			jPanel16 = new JPanel();
			jPanel16.add(getDiskButton());
			jPanel16.add(getExcelButton2());
		}
		return jPanel16;
	}

	private JButton getExcelIDTButton() {
		if (excelIDTButton == null) {
			excelIDTButton = new JButton();
			excelIDTButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/excel.gif")));
			excelIDTButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					excelIDTButtonActionPerformed(evt);
				}
			});
		}
		return excelIDTButton;
	}

	private void excelIDTButtonActionPerformed(ActionEvent evt) {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			GKDCommonLib.exportTableModelToExcel(file, this.idtTable.getModel(), "IDT");
		}
	}

	private JButton getSaveImageButton() {
		if (saveImageButton == null) {
			saveImageButton = new JButton();
			saveImageButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/disk.png")));
			saveImageButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					saveImageButtonActionPerformed(evt);
				}
			});
		}
		return saveImageButton;
	}

	private void saveImageButtonActionPerformed(ActionEvent evt) {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			if (!GKDCommonLib.saveImage(this.idtTable, file)) {
				JOptionPane.showMessageDialog(this, "Cannot save image.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private JButton getExcelButton2() {
		if (excelButton2 == null) {
			excelButton2 = new JButton();
			excelButton2.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/excel.gif")));
			excelButton2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					excelButton2ActionPerformed(evt);
				}
			});
		}
		return excelButton2;
	}

	private void excelButton2ActionPerformed(ActionEvent evt) {
		logger.debug("not implement");
	}

	private JButton getDiskButton() {
		if (diskButton == null) {
			diskButton = new JButton();
			diskButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/disk.png")));
			diskButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					diskButtonActionPerformed(evt);
				}
			});
		}
		return diskButton;
	}

	private void diskButtonActionPerformed(ActionEvent evt) {
		logger.debug("jButton11.actionPerformed, event=" + evt);
	}

	private JButton getExcelButton() {
		if (excelButton == null) {
			excelButton = new JButton();
			excelButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/excel.gif")));
			excelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					excelButtonActionPerformed(evt);
				}
			});
		}
		return excelButton;
	}

	private void excelButtonActionPerformed(ActionEvent evt) {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			GKDCommonLib.exportTableModelToExcel(file, GKD.instructionTable.getModel(), "instruction 0x" + this.instructionComboBox.getSelectedItem().toString());
		}
	}

	private JButton getExportToExcelButton() {
		if (exportToExcelButton == null) {
			exportToExcelButton = new JButton();
			exportToExcelButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/excel.gif")));
			exportToExcelButton.setText("Excel");
			exportToExcelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					excelActionPerformed(evt);
				}
			});
		}
		return exportToExcelButton;
	}

	private void excelActionPerformed(ActionEvent evt) {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			final JProgressBarDialog d = new JProgressBarDialog(this, "Exporting to XLS", true);
			d.jProgressBar.setIndeterminate(true);
			d.jProgressBar.setStringPainted(true);

			class MyThread extends Thread {
				File file;
				GKD gkd;

				public MyThread(GKD gkd, File file) {
					this.gkd = gkd;
					this.file = file;
				}

				public void run() {
					XSSFWorkbook wb = new XSSFWorkbook();// Write the output to
															// a file
					GKDCommonLib.exportRegisterHistory(wb, d);
					GKDCommonLib.exportTableModelToExcel(file, gkd.gdtTable.getModel(), "GDT", wb);
					GKDCommonLib.exportTableModelToExcel(file, gkd.idtTable.getModel(), "IDT", wb);
					GKDCommonLib.exportTableModelToExcel(file, GKD.instructionTable.getModel(), "instruction 0x" + gkd.instructionComboBox.getSelectedItem().toString(), wb);
					GKDCommonLib.exportTableModelToExcel(file, gkd.hexTable.getModel(), memoryAddressComboBox.getSelectedItem().toString(), wb);
					FileOutputStream fileOut;
					try {
						fileOut = new FileOutputStream(file);
						wb.write(fileOut);
						fileOut.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			d.thread = new MyThread(this, file);
			d.setVisible(true);
		}
	}

	private JPanel getSearchMemoryPanel() {
		if (panel17 == null) {
			panel17 = new JPanel();
			BorderLayout jPanel17Layout = new BorderLayout();
			panel17.setLayout(jPanel17Layout);
			panel17.add(getSearchMemoryControlPanel(), BorderLayout.NORTH);
			panel17.add(getSearchMemoryScrollPane(), BorderLayout.CENTER);
		}
		return panel17;
	}

	private JPanel getSearchMemoryControlPanel() {
		if (searchMemoryControlPanel == null) {
			searchMemoryControlPanel = new JPanel();
			searchMemoryControlPanel.add(getHexDecStringLabel());
			searchMemoryControlPanel.add(getSearchMemoryTextField());
			searchMemoryControlPanel.add(getFromLabel());
			searchMemoryControlPanel.add(getSearchMemoryFromComboBox());
			searchMemoryControlPanel.add(getToLabel());
			searchMemoryControlPanel.add(getSearchMemoryToComboBox());
			searchMemoryControlPanel.add(getSearchMemoryButton());
			searchMemoryControlPanel.add(getJButton15());
		}
		return searchMemoryControlPanel;
	}

	private JScrollPane getSearchMemoryScrollPane() {
		if (jScrollPane12 == null) {
			jScrollPane12 = new JScrollPane();
			jScrollPane12.setViewportView(getSearchMemoryTable());
		}
		return jScrollPane12;
	}

	private JTable getSearchMemoryTable() {
		if (searchMemoryTable == null) {
			searchMemoryTable = new JTable();
			getSearchMemoryTable().getTableHeader().setReorderingAllowed(false);
			searchMemoryTable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					searchMemoryTableMouseClicked(evt);
				}
			});
			searchMemoryTable.setModel(new SearchTableModel());
		}
		return searchMemoryTable;
	}

	private JLabel getHexDecStringLabel() {
		if (hexDecStringLabel == null) {
			hexDecStringLabel = new JLabel();
			hexDecStringLabel.setText("hex/dec/string");
		}
		return hexDecStringLabel;
	}

	private JTextField getSearchMemoryTextField() {
		if (searchMemoryTextField == null) {
			searchMemoryTextField = new JTextField();
			searchMemoryTextField.setPreferredSize(new java.awt.Dimension(84, 18));
		}
		return searchMemoryTextField;
	}

	private JLabel getFromLabel() {
		if (fromLabel == null) {
			fromLabel = new JLabel();
			fromLabel.setText("from");
		}
		return fromLabel;
	}

	private JComboBox<String> getSearchMemoryFromComboBox() {
		if (searchMemoryFromComboBox == null) {
			ComboBoxModel<String> searchMemoryFromComboBoxModel = new DefaultComboBoxModel<String>(new String[] {});
			searchMemoryFromComboBox = new JComboBox<String>();
			searchMemoryFromComboBox.setModel(searchMemoryFromComboBoxModel);
			searchMemoryFromComboBox.setEditable(true);
			searchMemoryFromComboBox.setPreferredSize(new java.awt.Dimension(120, 22));
		}
		return searchMemoryFromComboBox;
	}

	private JLabel getToLabel() {
		if (toLabel == null) {
			toLabel = new JLabel();
			toLabel.setText("to");
		}
		return toLabel;
	}

	private JComboBox<String> getSearchMemoryToComboBox() {
		if (searchMemoryToComboBox == null) {
			ComboBoxModel<String> jSearchMemoryToComboBoxModel = new DefaultComboBoxModel<String>(new String[] {});
			searchMemoryToComboBox = new JComboBox<String>();
			searchMemoryToComboBox.setModel(jSearchMemoryToComboBoxModel);
			searchMemoryToComboBox.setEditable(true);
			searchMemoryToComboBox.setPreferredSize(new java.awt.Dimension(120, 22));
		}
		return searchMemoryToComboBox;
	}

	private JButton getSearchMemoryButton() {
		if (searchMemoryButton == null) {
			searchMemoryButton = new JButton();
			searchMemoryButton.setText("Search");
			searchMemoryButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					searchMemoryButtonActionPerformed(evt);
				}
			});
		}
		return searchMemoryButton;
	}

	private void searchMemoryButtonActionPerformed(ActionEvent evt) {
		try {
			if (this.searchMemoryToComboBox.getSelectedItem().toString().trim().startsWith("+")) {
				this.searchMemoryToComboBox.setSelectedItem("0x"
						+ Long.toHexString(CommonLib.string2long(this.searchMemoryFromComboBox.getSelectedItem().toString())
								+ CommonLib.string2long(this.searchMemoryToComboBox.getSelectedItem().toString().substring(1))));
			}
			new SearchMemoryDialog(this, this.searchMemoryTable, this.searchMemoryTextField.getText(), CommonLib.string2long(this.searchMemoryFromComboBox.getSelectedItem()
					.toString()), CommonLib.string2long(this.searchMemoryToComboBox.getSelectedItem().toString())).setVisible(true);
		} catch (Exception ex) {

		}
	}

	private JButton getDisassembleButton() {
		if (disassembleButton == null) {
			disassembleButton = new JButton();
			disassembleButton.setText(MyLanguage.getString("Disassemble"));
			disassembleButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					disassembleButtonActionPerformed(evt);
				}
			});
		}
		return disassembleButton;
	}

	private void disassembleButtonActionPerformed(ActionEvent evt) {
		if (this.instructionComboBox.getSelectedItem() == null) {
			return;
		}
		this.addInstructionComboBox(this.instructionComboBox.getSelectedItem().toString());
		disassembleCurrentIPButton.setEnabled(false);
		try {
			InstructionTableModel model = (InstructionTableModel) instructionTable.getModel();
			model.clearData();
			updateInstruction(CommonLib.string2BigInteger(this.instructionComboBox.getSelectedItem().toString()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		updateBreakpointTableColor();
		disassembleCurrentIPButton.setEnabled(true);
	}

	private JButton getJButton15() {
		if (jButton15 == null) {
			jButton15 = new JButton();
			jButton15.setText(MyLanguage.getString("Clear"));
			jButton15.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jButton15ActionPerformed(evt);
				}
			});
		}
		return jButton15;
	}

	private void jButton15ActionPerformed(ActionEvent evt) {
		((SearchTableModel) this.searchMemoryTable.getModel()).removeAll();
	}

	private JPanel getMainPanel() {
		if (mainPanel == null) {
			mainPanel = new JPanel();
			CardLayout jMainPanelLayout = new CardLayout();
			mainPanel.setLayout(jMainPanelLayout);
			{
				progressBarDialog.jProgressBar.setValue(51);
				progressBarDialog.jProgressBar.setString("Init GUI - 3.1");

				mainPanel.add(getMaximizableTabbedPane_BasePanel1(), "jMaximizableTabbedPane_BasePanel1");

				progressBarDialog.jProgressBar.setValue(52);
				progressBarDialog.jProgressBar.setString("Init GUI - 3.2");

				mainPanel.add(getJInstrumentPanel(), "instrumentPanel");

				progressBarDialog.jProgressBar.setValue(53);
				progressBarDialog.jProgressBar.setString("Init GUI - 3.3");

				mainPanel.add(getJRunningLabel(), "Running Label");
				mainPanel.add(getOsLogPanel(), "osLogPanel");
				mainPanel.add(getRunningPanel(), "Running Panel");

				progressBarDialog.jProgressBar.setValue(56);
				progressBarDialog.jProgressBar.setString("Init GUI - 3.4");
				mainPanel.add(getSourceLevelDebugger(), "sourceLevelDebugger");
			}
		}
		return mainPanel;
	}

	private JSplitPane getSplitPane2() {
		//longest task
		jSplitPane2 = new JSplitPane();

		jSplitPane2.setPreferredSize(new java.awt.Dimension(1009, 781));
		jSplitPane2.setOrientation(JSplitPane.VERTICAL_SPLIT);

		jSplitPane1 = new JSplitPane();
		jSplitPane2.add(jSplitPane1, JSplitPane.TOP);
		jSplitPane1.setDividerLocation(400);

		jTabbedPane1 = new JMaximizableTabbedPane();
		jSplitPane1.add(jTabbedPane1, JSplitPane.RIGHT);
		jTabbedPane1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				jTabbedPane1StateChanged(evt);
			}
		});
		instructionPanel = new JPanel();
		BorderLayout jPanel10Layout = new BorderLayout();
		instructionPanel.setLayout(jPanel10Layout);
		jTabbedPane1.addTab(MyLanguage.getString("Instruction"), new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/text_padding_top.png")),
				instructionPanel, null);
		instructionPanel.setPreferredSize(new java.awt.Dimension(604, 452));
		instructionControlPanel = new JToolBar();
		instructionPanel.add(instructionControlPanel, BorderLayout.NORTH);
		ComboBoxModel<String> instructionComboBoxModel = new DefaultComboBoxModel<String>(new String[] {});
		instructionComboBox = new JComboBox<String>();
		instructionComboBox.setMaximumSize(new Dimension(200, 23));
		instructionComboBox.setOpaque(false);
		instructionControlPanel.add(instructionComboBox);
		instructionControlPanel.add(getDisassembleButton());
		instructionComboBox.setModel(instructionComboBoxModel);
		instructionComboBox.setEditable(true);
		instructionComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				instructionComboBoxActionPerformed(evt);
			}
		});
		disassembleCurrentIPButton = new JButton();
		instructionControlPanel.add(disassembleCurrentIPButton);
		instructionControlPanel.add(getInstructionUpTenButton());
		instructionControlPanel.add(getInstructionUpButton());
		instructionControlPanel.add(getInstructionDownButton());
		instructionControlPanel.add(getJButton3());
		instructionControlPanel.add(getExcelButton());
		disassembleCurrentIPButton.setText(MyLanguage.getString("Disassemble") + " cs:eip");
		instructionControlPanel.add(getJumpToInstructionButton());
		disassembleCurrentIPButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				disassembleCurrentIPButtonActionPerformed(evt);
			}
		});
		instructionTableScrollPane = new JScrollPane();
		instructionPanel.add(instructionTableScrollPane, BorderLayout.CENTER);
		instructionTable = new JTable();
		instructionTableScrollPane.setViewportView(instructionTable);
		instructionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		instructionTable.setModel(new InstructionTableModel());
		instructionTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		instructionTable.getTableHeader().setReorderingAllowed(false);
		instructionTable.getColumnModel().getColumn(0).setMaxWidth(20);
		instructionTable.getColumnModel().getColumn(1).setPreferredWidth(150);
		instructionTable.getColumnModel().getColumn(2).setPreferredWidth(200);
		instructionTable.getColumnModel().getColumn(3).setPreferredWidth(200);
		instructionTable.setDefaultRenderer(String.class, new InstructionTableCellRenderer());
		instructionTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				instructionTableMouseClicked(evt);
			}
		});
		jPanel4 = new JPanel();
		jTabbedPane1.addTab(MyLanguage.getString("Breakpoint"), new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/cancel.png")), jPanel4, null);
		BorderLayout jPanel4Layout = new BorderLayout();
		jPanel4.setLayout(jPanel4Layout);

		jScrollPane9 = new JScrollPane();
		jPanel4.add(jScrollPane9, BorderLayout.CENTER);
		breakpointTable = new JTable();
		breakpointTable.getTableHeader().setReorderingAllowed(false);
		jScrollPane9.setViewportView(breakpointTable);
		breakpointTable.setModel(jBreakpointTableModel);
		breakpointTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		breakpointTable.getColumnModel().getColumn(0).setCellRenderer(new BreakpointTableCellRenderer());
		breakpointTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				breakpointTableMouseClicked(evt);
			}
		});
		breakpointTable.getColumnModel().getColumn(2).setPreferredWidth(100);
		breakpointTable.getColumnModel().getColumn(3).setPreferredWidth(20);

		jPanel12 = new JPanel();
		jPanel4.add(jPanel12, BorderLayout.SOUTH);

		addBreakpointButton = new JButton();
		jPanel12.add(addBreakpointButton);
		addBreakpointButton.setText(MyLanguage.getString("Add"));
		addBreakpointButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				addBreakpointButtonActionPerformed(evt);
			}
		});
		deleteBreakpointButton = new JButton();
		jPanel12.add(deleteBreakpointButton);
		deleteBreakpointButton.setText(MyLanguage.getString("Del"));
		deleteBreakpointButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				deleteBreakpointButtonActionPerformed(evt);
			}
		});
		jRefreshBreakpointButton = new JButton();
		jPanel12.add(jRefreshBreakpointButton);
		jRefreshBreakpointButton.setText(MyLanguage.getString("Refresh"));
		jRefreshBreakpointButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				refreshBreakpointButtonActionPerformed(evt);
			}
		});
		enableBreakpointButton = new JButton();
		jPanel12.add(enableBreakpointButton);
		enableBreakpointButton.setText(MyLanguage.getString("Enable"));
		enableBreakpointButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				enableBreakpointButtonActionPerformed(evt);
			}
		});
		disableBreakpointButton = new JButton();
		jPanel12.add(disableBreakpointButton);
		disableBreakpointButton.setText(MyLanguage.getString("Disable"));
		disableBreakpointButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				disableBreakpointButtonActionPerformed(evt);
			}
		});
		saveBreakpointButton = new JButton();
		jPanel12.add(saveBreakpointButton);
		saveBreakpointButton.setText(MyLanguage.getString("Save"));
		saveBreakpointButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				saveBreakpointButtonActionPerformed(evt);
			}
		});
		loadBreakpointButton = new JDropDownButton();
		jPanel12.add(loadBreakpointButton);
		jPanel12.add(getSBButton());
		jPanel12.add(getSBAButton());
		loadBreakpointButton.setText(MyLanguage.getString("Load"));
		loadBreakpointButton.add(loadElfMenuItem);
		loadBreakpointButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				loadBreakpointButtonActionPerformed(evt);
			}
		});
		vmPanel = new JPanel();
		//$hide>>$
		if (VMController.vmType == VMType.Bochs) {
			//$hide><<$
			jTabbedPane1.addTab(MyLanguage.getString("Bochs"), new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/application_xp_terminal.png")),
					vmPanel, null);
			//$hide>>$
		} else if (VMController.vmType == VMType.Qemu) {
			//$hide<<$
			jTabbedPane1.addTab(MyLanguage.getString("Qemu"), new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/application_xp_terminal.png")),
					vmPanel, null);
			//$hide>>$
		}
		//$hide<<$
		jTabbedPane1.addTab("ELF", new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/linux.png")), getJELFBreakpointPanel(), null);
		DiskPanel diskPanel = getDiskPanel();
		if (diskPanel.getFile() != null) {
			jTabbedPane1.addTab(diskPanel.getFile().getName(), new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/package.png")), diskPanel, null);
		}
		BorderLayout jPanel1Layout = new BorderLayout();
		vmPanel.setLayout(jPanel1Layout);
		vmCommandScrollPane4 = new JScrollPane();
		vmCommandEditorPane = new JEditorPane();
		vmCommandScrollPane4.setViewportView(vmCommandEditorPane);
		vmPanel.add(vmCommandScrollPane4, BorderLayout.CENTER);
		jPanel2 = new JPanel();
		TableLayout jPanel2Layout = new TableLayout(new double[][] { { TableLayout.FILL, 411.0, TableLayout.MINIMUM, TableLayout.MINIMUM }, { TableLayout.FILL } });
		jPanel2Layout.setHGap(5);
		jPanel2Layout.setVGap(5);
		jPanel2.setLayout(jPanel2Layout);
		vmPanel.add(jPanel2, BorderLayout.SOUTH);
		vmCommandTextField = new JTextField();
		jPanel2.add(vmCommandTextField, "0, 0, 1, 0");
		vmCommandTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				vmCommandTextFieldKeyPressed(evt);
			}

			public void keyTyped(KeyEvent evt) {
				vmCommandTextFieldKeyTyped(evt);
			}
		});
		vmCommandButton = new JButton();
		jPanel2.add(vmCommandButton, "2, 0");
		jPanel2.add(getClearBochsButton(), "3, 0");
		vmCommandButton.setText("Run");
		vmCommandButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				vmCommandButtonActionPerformed(evt);
			}
		});
		tabbedPane3 = new JMaximizableTabbedPane();
		jSplitPane1.add(tabbedPane3, JSplitPane.LEFT);

		memoryPanel = new JPanel();
		BorderLayout jPanel8Layout = new BorderLayout();
		memoryPanel.setLayout(jPanel8Layout);
		if (GKDCommonLib.readConfigInt(cmd, "/gkd/vncPort/text()") != -1) {
			tabbedPane3.addTab("VNC", null, getVncPanel(), null);
			TightVNC.initVNCPanel(this, getVncPanel(), "localhost", GKDCommonLib.readConfigInt(cmd, "/gkd/vncPort/text()"), null);
		}
		tabbedPane3.addTab(MyLanguage.getString("Memory"), new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/memory.png")), memoryPanel, null);

		jScrollPane2 = new JScrollPane();
		memoryPanel.add(jScrollPane2, BorderLayout.CENTER);
		hexTable = new HexTable();
		hexTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		for (int x = 1; x < 9; x++) {
			hexTable.getColumnModel().getColumn(x).setPreferredWidth(10);
		}
		jScrollPane2.setViewportView(hexTable);
		hexTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		hexTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		hexTable.setCellSelectionEnabled(true);
		hexTable.getTableHeader().setReorderingAllowed(false);
		hexTable.setDefaultRenderer(String.class, new MemoryTableCellRenderer());
		hexTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				jHexTable1MouseClicked(evt);
			}
		});
		jPanel9 = new JPanel();
		memoryPanel.add(jPanel9, BorderLayout.NORTH);

		memoryAddressComboBox = new JComboBox<String>();
		jPanel9.add(memoryAddressComboBox);
		memoryAddressComboBox.setSelectedItem("0x00000000");
		memoryAddressComboBox.setEditable(true);
		memoryAddressComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jMemoryAddressComboBoxActionPerformed(evt);
			}
		});
		new Thread("addMemoryAddressComboBox thread") {
			public void run() {
				TreeSet<String> vector = Setting.getInstance().memoryCombo;

				Iterator<String> iterator = vector.iterator();
				while (iterator.hasNext()) {
					addMemoryAddressComboBox(iterator.next());
				}
			}
		}.start();
		memoryAddressComboBox.setSelectedItem("0x00000000");

		goMemoryButton = new JButton();
		jPanel9.add(goMemoryButton);
		jPanel9.add(getGoLinearButton());
		jPanel9.add(getPreviousMemoryButton());
		jPanel9.add(getNextMemoryPageButton());
		jPanel9.add(getSaveHexTableImageButton());
		jPanel9.add(getExcelMemoryButton());
		goMemoryButton.setText(MyLanguage.getString("Go"));
		goMemoryButton.setToolTipText(MyLanguage.getString("Physical_address"));
		goMemoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				goMemoryButtonActionPerformed(evt);
			}
		});

		binaryRadioButton = new JRadioButton();
		jPanel9.add(binaryRadioButton);
		binaryRadioButton.setText("2");
		binaryRadioButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				binaryRadioButtonItemStateChanged(evt);
			}
		});
		getButtonGroup1().add(binaryRadioButton);
		octRadioButton1 = new JRadioButton();
		jPanel9.add(octRadioButton1);
		octRadioButton1.setText("8");
		octRadioButton1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				octRadioButton1ItemStateChanged(evt);
			}
		});
		getButtonGroup1().add(octRadioButton1);

		decRadioButton = new JRadioButton();
		jPanel9.add(decRadioButton);
		decRadioButton.setText("10");
		decRadioButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				decRadioButtonItemStateChanged(evt);
			}
		});
		getButtonGroup1().add(decRadioButton);

		hexRadioButton = new JRadioButton();
		jPanel9.add(hexRadioButton);
		hexRadioButton.setText("16");
		hexRadioButton.setSelected(true);
		hexRadioButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				hexRadioButtonItemStateChanged(evt);
			}
		});
		getButtonGroup1().add(hexRadioButton);

		jPanel5 = new JPanel();
		tabbedPane3.addTab(MyLanguage.getString("GDT"), new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/gdt.png")), jPanel5, null);
		BorderLayout jPanel5Layout = new BorderLayout();
		jPanel5.setLayout(jPanel5Layout);

		jScrollPane3 = new JScrollPane();
		jPanel5.add(jScrollPane3, BorderLayout.CENTER);
		jPanel5.add(getJPanel14(), BorderLayout.NORTH);

		GDTTableModel jGDTTableModel = new GDTTableModel();
		gdtTable = new JTable();
		gdtTable.setModel(jGDTTableModel);
		jScrollPane3.setViewportView(gdtTable);
		gdtTable.getColumnModel().getColumn(0).setMaxWidth(40);
		gdtTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		gdtTable.getTableHeader().setReorderingAllowed(false);
		gdtTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				jGDTTableMouseClicked(evt);
			}
		});

		jPanel6 = new JPanel();
		BorderLayout jPanel6Layout = new BorderLayout();
		jPanel6.setLayout(jPanel6Layout);
		tabbedPane3.addTab(MyLanguage.getString("IDT"), new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/idt.png")), jPanel6, null);

		scrollPane10 = new JScrollPane();
		jPanel6.add(scrollPane10, BorderLayout.CENTER);
		jPanel6.add(getJPanel15(), BorderLayout.NORTH);

		IDTTableModel jIDTTableModel = new IDTTableModel();
		idtTable = new JTable();
		idtTable.setModel(jIDTTableModel);
		scrollPane10.setViewportView(idtTable);
		idtTable.getColumnModel().getColumn(0).setMaxWidth(40);
		idtTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		idtTable.getTableHeader().setReorderingAllowed(false);
		idtTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				idtTableMouseClicked(evt);
			}
		});

		jPanel7 = new JPanel();
		BorderLayout jPanel7Layout = new BorderLayout();
		jPanel7.setLayout(jPanel7Layout);
		tabbedPane3.addTab(MyLanguage.getString("LDT"), new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/ldt.png")), jPanel7, null);
		tabbedPane3.addTab(MyLanguage.getString("Search_memory"), new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/memory.png")),
				getSearchMemoryPanel(), null);
		tabbedPane3.addTab(VMController.vmType == VMType.Bochs ? "bochsout.txt" : "qemu log",
				new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/script.png")), getJPanel31(), null);

		jScrollPane11 = new JScrollPane();
		jPanel7.add(jScrollPane11, BorderLayout.CENTER);
		jPanel7.add(getJPanel16(), BorderLayout.NORTH);

		LDTTableModel jLDTTableModel = new LDTTableModel();
		ldtTable = new JTable();
		ldtTable.setModel(jLDTTableModel);
		jScrollPane11.setViewportView(ldtTable);
		ldtTable.getColumnModel().getColumn(0).setMaxWidth(40);
		ldtTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		ldtTable.getTableHeader().setReorderingAllowed(false);
		ldtTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				jLDTTableMouseClicked(evt);
			}
		});
		bottomTabbedPane = new JMaximizableTabbedPane();

		jSplitPane2.add(bottomTabbedPane, JSplitPane.BOTTOM);
		registerPanelScrollPane = new JScrollPane();
		registerPanelScrollPane.getVerticalScrollBar().setUnitIncrement(20);
		registerPanelScrollPane.getHorizontalScrollBar().setUnitIncrement(20);
		bottomTabbedPane.addTab(MyLanguage.getString("Register"), new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/text_kerning.png")),
				registerPanelScrollPane, null);

		registerPanel = new RegisterPanel(this);
		registerPanelScrollPane.setViewportView(registerPanel);

		jPanel3 = new JPanel();
		bottomTabbedPane.addTab(MyLanguage.getString("History"), new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/book_addresses.png")), jPanel3,
				null);
		BorderLayout jPanel3Layout = new BorderLayout();
		jPanel3.setLayout(jPanel3Layout);

		jScrollPane6 = new JScrollPane();
		jPanel3.add(jScrollPane6, BorderLayout.CENTER);
		jPanel3.add(getJPanel13(), BorderLayout.NORTH);
		jScrollPane6.setViewportView(getJHistoryTable());

		pagingPanel = new JPanel();
		bottomTabbedPane.addTab(MyLanguage.getString("Paging"), new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/page_copy.png")), pagingPanel,
				null);
		bottomTabbedPane.addTab(MyLanguage.getString("Address_translate"), new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/page_go.png")),
				getJAddressTranslatePanel(), null);
		bottomTabbedPane.addTab("Page table graph (experimental)", new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/page_lightning.png")),
				getJPageTableGraphPanel(), null);
		if (!Global.debug) {
			bottomTabbedPane.removeTabAt(bottomTabbedPane.getTabCount() - 1);
		}
		bottomTabbedPane.addTab(MyLanguage.getString("Table_translate"), new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/page_refresh.png")),
				getTableTranslateScrollPane(), null);
		bottomTabbedPane.addTab(MyLanguage.getString("ELF_dump"), new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/linux.png")),
				getELFDumpScrollPane(), null);
		bottomTabbedPane.addTab("OS debug informations", new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/bug.png")), getOSDebugStandardPanel(),
				null);
		bottomTabbedPane.addTab("Log", new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/script.png")), getLogPanel(), null);
		BorderLayout jPanel11Layout = new BorderLayout();
		pagingPanel.setLayout(jPanel11Layout);
		pagingPanel.add(getJSplitPane3(), BorderLayout.CENTER);
		pagingPanel.add(getJPanel19(), BorderLayout.NORTH);
		return jSplitPane2;
	}

	private JLabel getJRunningLabel() {
		if (jRunningLabel == null) {
			jRunningLabel = new JLabel();

			new Thread("JRunningLabel thread") {
				public void run() {
					if (Setting.getInstance().currentLanguage.equals("zh_TW")) {
						jRunningLabel
								.setText("<html><center>Bochs is running, click the pause button to pause it !!!<br><br><img src=\""
										+ url
										+ "\" /><br><br><a style=\"color: #ffffff;  text-decoration:none\" href=\"http://www.kingofcoders.com\">????????????????????????????????????www.kingofcoders.com</a></center></html>");
					} else if (Setting.getInstance().currentLanguage.equals("zh_CN")) {
						jRunningLabel
								.setText("<html><center>Bochs is running, click the pause button to pause it !!!<br><br><img src=\""
										+ url
										+ "\" /><br><br><img src=\"http://www.kingofcoders.com/images/KOC_logo2.jpg\" /><br><a style=\"color: #ffffff;  text-decoration:none\" href=\"http://www.kingofcoders.com\">??????????????????????????????????????????www.kingofcoders.com</a></center></html>");
					} else {
						jRunningLabel.setText("<html><center>Bochs is running, click the pause button to pause it !!!<br><br><img src=\"" + url + "\" /></center></html>");
					}
				}
			}.start();
			jRunningLabel.setHorizontalAlignment(SwingConstants.CENTER);
			jRunningLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			jRunningLabel.setFont(new java.awt.Font(jRunningLabel.getFont().getFamily(), 0, 20));
			jRunningLabel.setForeground(Color.white);
			jRunningLabel.setBackground(new Color(0, 0, 0, 180));
			jRunningLabel.setOpaque(true);
		}
		return jRunningLabel;
	}

	private JButton getJGDTGraphButton() {
		if (gdtGraphButton == null) {
			gdtGraphButton = new JButton();
			gdtGraphButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/map.png")));
			gdtGraphButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jGDTGraphButtonActionPerformed(evt);
				}
			});
		}
		return gdtGraphButton;
	}

	private void jGDTGraphButtonActionPerformed(ActionEvent evt) {

	}

	private JToolBar getJPanel19() {
		if (panel19 == null) {
			panel19 = new JToolBar();
			FlowLayout jPanel19Layout = new FlowLayout();
			jPanel19Layout.setAlignment(FlowLayout.LEFT);
			panel19.add(getJPagingGraphButton());
			panel19.add(getJButton21x());
			panel19.add(getDumpCR3Button());
			panel19.add(getDumpPageDirectoryAddressTextField());
			panel19.add(getDumpPageTableAtAddressButton());
			panel19.add(getHideIfAddressIsZeroCheckBox());
		}
		return panel19;
	}

	private JButton getJPagingGraphButton() {
		if (pagingGraphButton == null) {
			pagingGraphButton = new JButton();
			pagingGraphButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/disk.png")));
			pagingGraphButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jPagingGraphButtonActionPerformed(evt);
				}
			});
		}
		return pagingGraphButton;
	}

	private void jPagingGraphButtonActionPerformed(ActionEvent evt) {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			if (!GKDCommonLib.saveImage(jSplitPane3, file)) {
				JOptionPane.showMessageDialog(this, "Cannot save image.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private JPanel getJPageTableGraphPanel() {
		if (pageTableGraphPanel == null) {
			pageTableGraphPanel = new JPanel();
			BorderLayout jPageTableGraphPanelLayout = new BorderLayout();
			pageTableGraphPanel.setLayout(jPageTableGraphPanelLayout);
			pageTableGraphPanel.add(getJToolBar2(), BorderLayout.NORTH);
		}
		return pageTableGraphPanel;
	}

	private JToolBar getJToolBar2() {
		if (jToolBar2 == null) {
			jToolBar2 = new JToolBar();
			jToolBar2.add(getJAutoRefreshPageTableGraphCheckBox());
			jToolBar2.add(getJRefreshPageTableGraphButton());
		}
		return jToolBar2;
	}

	private JCheckBox getJAutoRefreshPageTableGraphCheckBox() {
		if (autoRefreshPageTableGraphCheckBox == null) {
			autoRefreshPageTableGraphCheckBox = new JCheckBox();
			autoRefreshPageTableGraphCheckBox.setText("Auto refresh");
		}
		return autoRefreshPageTableGraphCheckBox;
	}

	private JButton getJRefreshPageTableGraphButton() {
		if (refreshPageTableGraphButton == null) {
			refreshPageTableGraphButton = new JButton();
			refreshPageTableGraphButton.setText("Refresh");
		}
		return refreshPageTableGraphButton;
	}

	private JPanel getJAddressTranslatePanel() {
		if (addressTranslatePanel == null) {
			addressTranslatePanel = new JPanel();
			BorderLayout jAddressTranslatePanelLayout = new BorderLayout();
			addressTranslatePanel.setLayout(jAddressTranslatePanelLayout);
			addressTranslatePanel.add(getJPanel20(), BorderLayout.WEST);
			addressTranslatePanel.add(getJPanel22(), BorderLayout.CENTER);
			addressTranslatePanel.add(getJToolBar3(), BorderLayout.NORTH);
		}
		return addressTranslatePanel;
	}

	private JPanel getJPanel20() {
		if (jPanel20 == null) {
			jPanel20 = new JPanel();
			TableLayout jPanel20Layout = new TableLayout(new double[][] { { 8.0, 156.0, 13.0 }, { 25.0, 25.0, 25.0, 22.0, 37.0, TableLayout.FILL } });
			jPanel20Layout.setHGap(5);
			jPanel20Layout.setVGap(5);
			jPanel20.setLayout(jPanel20Layout);
			jPanel20.setPreferredSize(new java.awt.Dimension(189, 629));
			jPanel20.add(getSearchVirtualAddressRadioButton(), "1, 0, 2, 0");
			jPanel20.add(getSearchLinearAddressRadioButton(), "1, 1, 2, 1");
			jPanel20.add(getSearchPhysicalAddressRadioButton(), "1, 2, 2, 2");
			jPanel20.add(getJPanel21(), "1, 4");
			jPanel20.add(getJAddressTextField(), "1, 3");
		}
		return jPanel20;
	}

	private JRadioButton getSearchVirtualAddressRadioButton() {
		if (searchVirtualAddressRadioButton == null) {
			searchVirtualAddressRadioButton = new JRadioButton();
			searchVirtualAddressRadioButton.setText(MyLanguage.getString("Virtual_address"));
			searchVirtualAddressRadioButton.setSelected(true);
			getButtonGroup3().add(searchVirtualAddressRadioButton);
		}
		return searchVirtualAddressRadioButton;
	}

	private JRadioButton getSearchLinearAddressRadioButton() {
		if (searchLinearAddressRadioButton == null) {
			searchLinearAddressRadioButton = new JRadioButton();
			searchLinearAddressRadioButton.setText(MyLanguage.getString("Linear_address"));
			getButtonGroup3().add(searchLinearAddressRadioButton);
		}

		return searchLinearAddressRadioButton;
	}

	private JRadioButton getSearchPhysicalAddressRadioButton() {
		if (searchPhysicalAddressRadioButton == null) {
			searchPhysicalAddressRadioButton = new JRadioButton();
			searchPhysicalAddressRadioButton.setVisible(false);
			searchPhysicalAddressRadioButton.setText(MyLanguage.getString("Physical_address"));
			getButtonGroup3().add(searchPhysicalAddressRadioButton);
		}

		return searchPhysicalAddressRadioButton;
	}

	private JButton getRefreshAddressTranslateButton() {
		if (refreshAddressTranslateButton == null) {
			refreshAddressTranslateButton = new JButton();
			refreshAddressTranslateButton.setText(MyLanguage.getString("Convert"));
			refreshAddressTranslateButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					refreshAddressTranslateButtonActionPerformed(evt);
				}
			});
		}
		return refreshAddressTranslateButton;
	}

	private JPanel getJPanel22() {
		if (jPanel22 == null) {
			jPanel22 = new JPanel();
			BorderLayout jPanel22Layout = new BorderLayout();
			jPanel22.setLayout(jPanel22Layout);
			jPanel22.add(getJScrollPane13(), BorderLayout.CENTER);
		}
		return jPanel22;
	}

	private JTable getJTable1() {
		if (addressTranslateTable2 == null) {
			addressTranslateTable2 = new JTable();
			AddressTranslateTableModel addressTranslateTableModel = new AddressTranslateTableModel();
			addressTranslateTable2.setModel(addressTranslateTableModel);
			addressTranslateTable2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			for (int x = 0; x < addressTranslateTable2.getColumnCount(); x++) {
				addressTranslateTable2.getColumnModel().getColumn(x).setPreferredWidth(100);
			}
		}
		return addressTranslateTable2;
	}

	private JScrollPane getJScrollPane13() {
		if (jScrollPane13 == null) {
			jScrollPane13 = new JScrollPane();
			jScrollPane13.setPreferredSize(new java.awt.Dimension(150, 32));
			jScrollPane13.setViewportView(getJTable1());
		}
		return jScrollPane13;
	}

	private JToolBar getJToolBar3() {
		if (jToolBar3 == null) {
			jToolBar3 = new JToolBar();
			jToolBar3.add(getJButton17());
			jToolBar3.add(getJButton18());
			jToolBar3.add(getJRefreshAddressTranslateTableButton());
			jToolBar3.add(getJButton19());
		}
		return jToolBar3;
	}

	private JButton getJButton17() {
		if (jButton17 == null) {
			jButton17 = new JButton();
			jButton17.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/disk.png")));
			jButton17.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jButton17ActionPerformed(evt);
				}
			});
		}
		return jButton17;
	}

	private JButton getJButton18() {
		if (jButton18 == null) {
			jButton18 = new JButton();
			jButton18.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/excel.gif")));
			jButton18.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jButton18ActionPerformed(evt);
				}
			});
		}
		return jButton18;
	}

	private ButtonGroup getButtonGroup3() {
		if (buttonGroup3 == null) {
			buttonGroup3 = new ButtonGroup();
		}
		return buttonGroup3;
	}

	private JPanel getJPanel21() {
		if (jPanel21 == null) {
			jPanel21 = new JPanel();
			jPanel21.add(getRefreshAddressTranslateButton());
		}
		return jPanel21;
	}

	private JTextField getJAddressTextField() {
		if (addressTextField == null) {
			addressTextField = new JTextField();
			addressTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					addressTextFieldKeyTyped(evt);
				}
			});
		}
		return addressTextField;
	}

	private void refreshAddressTranslateButtonActionPerformed(ActionEvent evt) {
		AddressTranslateTableModel model = (AddressTranslateTableModel) this.addressTranslateTable2.getModel();

		if (searchVirtualAddressRadioButton.isSelected()) {
			if (!this.addressTextField.getText().contains(":") || this.addressTextField.getText().replaceAll("[^:]", "").length() != 1) {
				JOptionPane.showMessageDialog(this, "Error, please input <segment selector>:<offset>\n\ne.g. : 0x10:0x12345678", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			BigInteger segSelector = CommonLib.string2BigInteger(this.addressTextField.getText().split(":")[0]);
			BigInteger address = CommonLib.string2BigInteger(this.addressTextField.getText().split(":")[1]);

			model.searchType.add(1);
			model.searchSegSelector.add(segSelector);
			model.searchAddress.add(address);

			model.virtualAddress.add(address);
			BigInteger segNo = segSelector.shiftRight(3);
			model.segNo.add(segNo);

			// read GDT descriptor
			int descriptor[];
			descriptor = VMController.getVM().physicalMemory(CommonLib.string2BigInteger(this.registerPanel.gdtrTextField.getText()).add(segNo.multiply(BigInteger.valueOf(8))), 8);
			BigInteger baseAddress = CommonLib.getBigInteger(descriptor[2], descriptor[3], descriptor[4], descriptor[7], 0, 0, 0, 0);
			BigInteger linearAddress = baseAddress.add(address);
			model.baseAddress.add(baseAddress);
			model.linearAddress.add(linearAddress);

			boolean pse = CommonLib.getBit(CommonLib.string2long(registerPanel.cr4TextField.getText()), 4) == 1;
			boolean pae = CommonLib.getBit(CommonLib.string2long(registerPanel.cr4TextField.getText()), 5) == 1;

			int bytesAtPhysicalAddress[] = null;
			if (!pae) {
				BigInteger pdNo = CommonLib.getBigInteger(linearAddress, 31, 22);
				model.pdNo.add(pdNo);
				int pdeBytes[];
				pdeBytes = VMController.getVM().physicalMemory(CommonLib.string2BigInteger(this.registerPanel.cr3TextField.getText()).add(pdNo.multiply(BigInteger.valueOf(4))), 4);

				BigInteger pde = CommonLib.getBigInteger(pdeBytes, 0);
				model.pde.add(pde);

				if (!pse) {
					BigInteger ptNo = CommonLib.getBigInteger(linearAddress, 21, 12);
					model.ptNo.add(ptNo);
					BigInteger pageTableBaseAddress = pde.and(CommonLib.string2BigInteger("0xfffff000"));
					int pteBytes[];
					pteBytes = VMController.getVM().physicalMemory(pageTableBaseAddress.add(ptNo.multiply(BigInteger.valueOf(4))), 4);

					BigInteger pte = CommonLib.getBigInteger(pteBytes, 0);
					BigInteger pagePhysicalAddress = pte.and(CommonLib.string2BigInteger("0xfffff000"));
					model.pte.add(pte);

					BigInteger physicalAddress = pagePhysicalAddress.add(CommonLib.getBigInteger(linearAddress, 11, 0));
					model.physicalAddress.add(physicalAddress);

					bytesAtPhysicalAddress = VMController.getVM().physicalMemory(physicalAddress, 8);
				} else {
					model.ptNo.add(BigInteger.valueOf(-1));
					model.pte.add(BigInteger.valueOf(-1));
					BigInteger pageDirectoryBaseAddress = pde.and(CommonLib.string2BigInteger("0xffc00000"));
					BigInteger physicalAddress = pageDirectoryBaseAddress.add(CommonLib.getBigInteger(linearAddress, 21, 0));
					model.physicalAddress.add(physicalAddress);

					bytesAtPhysicalAddress = VMController.getVM().physicalMemory(physicalAddress, 8);
				}
			}
			model.bytes.add(GKDCommonLib.convertToString(bytesAtPhysicalAddress));

			model.fireTableDataChanged();
		} else if (searchLinearAddressRadioButton.isSelected()) {
			BigInteger address = CommonLib.string2BigInteger(this.addressTextField.getText());

			model.searchType.add(2);
			model.searchAddress.add(address);

			BigInteger baseAddress = BigInteger.ZERO;
			BigInteger linearAddress = baseAddress.add(address);
			model.baseAddress.add(baseAddress);
			model.linearAddress.add(linearAddress);

			boolean pse = CommonLib.getBit(CommonLib.string2long(registerPanel.cr4TextField.getText()), 4) == 1;
			boolean pae = CommonLib.getBit(CommonLib.string2long(registerPanel.cr4TextField.getText()), 5) == 1;

			int bytesAtPhysicalAddress[] = null;
			if (!pae) {
				BigInteger pdNo = CommonLib.getBigInteger(linearAddress, 31, 22);
				model.pdNo.add(pdNo);
				int pdeBytes[];
				System.out.println("this.registerPanel.cr3TextField.getText()=" + this.registerPanel.cr3TextField.getText());
				System.out.println("pdNo=" + pdNo);
				pdeBytes = VMController.getVM().physicalMemory(CommonLib.string2BigInteger(this.registerPanel.cr3TextField.getText()).add(pdNo.multiply(BigInteger.valueOf(4))), 4);
				System.out.println("CommonLib.string2BigInteger(this.registerPanel.cr3TextField.getText()).add(pdNo.multiply(BigInteger.valueOf(4)))="
						+ CommonLib.string2BigInteger(this.registerPanel.cr3TextField.getText()).add(pdNo.multiply(BigInteger.valueOf(4))));

				BigInteger pde = CommonLib.getBigInteger(pdeBytes, 0);
				model.pde.add(pde);

				long value = CommonLib.getInt(pdeBytes, 0);
				long ps = (value >> 7) & 1;
				if (!pse || ps == 0) {
					BigInteger ptNo = CommonLib.getBigInteger(linearAddress, 21, 12);
					model.ptNo.add(ptNo);
					BigInteger pageTableBaseAddress = pde.and(CommonLib.string2BigInteger("0xfffff000"));
					System.out.println("pageTableBaseAddress=" + pageTableBaseAddress.toString(16));
					int pteBytes[];
					pteBytes = VMController.getVM().physicalMemory(pageTableBaseAddress.add(ptNo.multiply(BigInteger.valueOf(4))), 4);

					BigInteger pte = CommonLib.getBigInteger(pteBytes, 0);
					BigInteger pagePhysicalAddress = pte.and(CommonLib.string2BigInteger("0xfffff000"));
					model.pte.add(pte);

					BigInteger physicalAddress = pagePhysicalAddress.add(CommonLib.getBigInteger(linearAddress, 11, 0));
					model.physicalAddress.add(physicalAddress);

					bytesAtPhysicalAddress = VMController.getVM().physicalMemory(physicalAddress, 8);
				} else {
					model.ptNo.add(BigInteger.valueOf(-1));
					model.pte.add(BigInteger.valueOf(-1));

					BigInteger pageDirectoryBaseAddress = pde.and(CommonLib.string2BigInteger("0xffc00000"));
					BigInteger physicalAddress = pageDirectoryBaseAddress.add(CommonLib.getBigInteger(linearAddress, 21, 0));
					model.physicalAddress.add(physicalAddress);

					bytesAtPhysicalAddress = VMController.getVM().physicalMemory(physicalAddress, 8);
				}
			}
			model.bytes.add(GKDCommonLib.convertToString(bytesAtPhysicalAddress));

			model.fireTableDataChanged();
		} else if (searchPhysicalAddressRadioButton.isSelected()) {
			for (int x = 0; x < model.getRowCount(); x++) {
				if (model.searchType.get(x).equals(3) && model.searchAddress.get(x).equals(CommonLib.string2long(this.addressTextField.getText()))) {
					return;
				}
			}
			BigInteger addr = CommonLib.string2BigInteger(this.addressTextField.getText());
			model.searchType.add(3);
			model.searchSegSelector.add(BigInteger.ZERO);
			model.searchAddress.add(addr);
			model.virtualAddress.add(BigInteger.ZERO);
			model.segNo.add(BigInteger.ZERO);
			model.linearAddress.add(BigInteger.ZERO);
			model.pdNo.add(BigInteger.ZERO);
			model.ptNo.add(BigInteger.ZERO);
			model.physicalAddress.add(BigInteger.ZERO);
			model.bytes.add("");

			model.fireTableDataChanged();
		}
	}

	private JButton getJRefreshAddressTranslateTableButton() {
		if (refreshAddressTranslateTableButton == null) {
			refreshAddressTranslateTableButton = new JButton();
			refreshAddressTranslateTableButton.setText(MyLanguage.getString("Refresh"));
			refreshAddressTranslateTableButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/arrow_refresh.png")));
			refreshAddressTranslateTableButton.setText("Refresh");
			refreshAddressTranslateTableButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jRefreshAddressTranslateTableButtonActionPerformed(evt);
				}
			});
		}
		return refreshAddressTranslateTableButton;
	}

	private void jRefreshAddressTranslateTableButtonActionPerformed(ActionEvent evt) {
		AddressTranslateTableModel model = (AddressTranslateTableModel) this.addressTranslateTable2.getModel();
		for (int x = 0; x < model.getRowCount(); x++) {
			if (model.searchType.get(x).equals(1)) {
				model.segNo.set(x, model.searchSegSelector.get(x).shiftRight(3));
				model.virtualAddress.set(x, model.searchAddress.get(x));

				BigInteger gdtBase = CommonLib.getBigInteger(
						VMController.getVM().physicalMemory(CommonLib.string2BigInteger(this.registerPanel.cr3TextField.getText()),
								CommonLib.string2int(this.registerPanel.gdtrTextField.getText())), 0);
				gdtBase = gdtBase.add(model.segNo.get(x).multiply(BigInteger.valueOf(8)));
				int bytes[] = new int[8];
				bytes = VMController.getVM().physicalMemory(gdtBase, 8);

				Long gdtDescriptor = CommonLib.getLong(bytes, 0);
				logger.debug(Long.toHexString(gdtDescriptor));
				BigInteger base = CommonLib.getBigInteger(bytes[2], bytes[3], bytes[4], bytes[7], 0, 0, 0, 0);
				logger.debug(base.toString(16));

				model.linearAddress.set(x, base.add(model.searchAddress.get(x)));
			}
		}
		model.fireTableDataChanged();
	}

	private void jHexTable1MouseClicked(MouseEvent evt) {
		if (SwingUtilities.isRightMouseButton(evt)) {
			// select
			Point p = evt.getPoint();
			int rowNumber = hexTable.rowAtPoint(p);
			int columnNumber = hexTable.columnAtPoint(p);
			ListSelectionModel model = hexTable.getSelectionModel();
			model.setSelectionInterval(rowNumber, rowNumber);
			hexTable.getColumnModel().getSelectionModel().setSelectionInterval(columnNumber, columnNumber);
			// end select

			getJHexTablePopupMenu().show(evt.getComponent(), evt.getX(), evt.getY());
		}
	}

	private JPopupMenu getJHexTablePopupMenu() {
		if (hexTablePopupMenu == null) {
			hexTablePopupMenu = new JPopupMenu();
			hexTablePopupMenu.add(getJMenu7());
			hexTablePopupMenu.add(getJMenu8());
			hexTablePopupMenu.add(getJMenuItem4());
			hexTablePopupMenu.add(getJMenuItem5());
			hexTablePopupMenu.add(getJMenuItem6());
			hexTablePopupMenu.add(getJMenuItem7());
			hexTablePopupMenu.add(getJDisassemble32MenuItem());
		}
		return hexTablePopupMenu;
	}

	private JMenuItem getJMenu7() {
		if (gdtMenuItem == null) {
			gdtMenuItem = new JMenuItem();
			gdtMenuItem.setText("GDT table");
			gdtMenuItem.setBounds(0, 21, 115, 21);
			gdtMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jGDTMenuItemActionPerformed(evt);
				}
			});
		}
		return gdtMenuItem;
	}

	private JMenuItem getJMenu8() {
		if (gdtDescriptorMenuItem == null) {
			gdtDescriptorMenuItem = new JMenuItem();
			gdtDescriptorMenuItem.setText("GDT descriptor");
			gdtDescriptorMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jGDTDescriptorMenuItemActionPerformed(evt);
				}
			});
		}
		return gdtDescriptorMenuItem;
	}

	private JMenuItem getJMenuItem4() {
		if (idtMenuItem == null) {
			idtMenuItem = new JMenuItem();
			idtMenuItem.setText("IDT table");
			idtMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jIDTMenuItemActionPerformed(evt);
				}
			});
		}
		return idtMenuItem;
	}

	private JMenuItem getJMenuItem5() {
		if (idtDescriptorMenuItem == null) {
			idtDescriptorMenuItem = new JMenuItem();
			idtDescriptorMenuItem.setText("IDT descriptor");
			idtDescriptorMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jIDTDescriptorMenuItemActionPerformed(evt);
				}
			});
		}
		return idtDescriptorMenuItem;
	}

	private JMenuItem getJMenuItem6() {
		if (pdeMenuItem == null) {
			pdeMenuItem = new JMenuItem();
			pdeMenuItem.setText(MyLanguage.getString("PDE"));
			pdeMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jPDEMenuItemActionPerformed(evt);
				}
			});
		}
		return pdeMenuItem;
	}

	private JMenuItem getJMenuItem7() {
		if (pteMenuItem == null) {
			pteMenuItem = new JMenuItem();
			pteMenuItem.setText(MyLanguage.getString("PTE"));
			pteMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jPTEMenuItemActionPerformed(evt);
				}
			});
		}
		return pteMenuItem;
	}

	private void jGDTMenuItemActionPerformed(ActionEvent evt) {
		new HelperDialog(this, currentMemoryWindowsAddress.add(BigInteger.valueOf(hexTable.getSelectedRow() * 8 + hexTable.getSelectedColumn() - 1)), "GDT").setVisible(true);
	}

	private void jGDTDescriptorMenuItemActionPerformed(ActionEvent evt) {
		new HelperDialog(this, currentMemoryWindowsAddress.add(BigInteger.valueOf(hexTable.getSelectedRow() * 8 + hexTable.getSelectedColumn() - 1)), "GDT Descriptor")
				.setVisible(true);
	}

	private void jIDTMenuItemActionPerformed(ActionEvent evt) {
		new HelperDialog(this, currentMemoryWindowsAddress.add(BigInteger.valueOf(hexTable.getSelectedRow() * 8 + hexTable.getSelectedColumn() - 1)), "IDT").setVisible(true);
	}

	private void jIDTDescriptorMenuItemActionPerformed(ActionEvent evt) {
		new HelperDialog(this, currentMemoryWindowsAddress.add(BigInteger.valueOf(hexTable.getSelectedRow() * 8 + hexTable.getSelectedColumn() - 1)), "IDT Descriptor")
				.setVisible(true);
	}

	private void jPDEMenuItemActionPerformed(ActionEvent evt) {
		new HelperDialog(this, currentMemoryWindowsAddress.add(BigInteger.valueOf(hexTable.getSelectedRow() * 8 + hexTable.getSelectedColumn() - 1)), "PDE").setVisible(true);
	}

	private void jPTEMenuItemActionPerformed(ActionEvent evt) {
		new HelperDialog(this, currentMemoryWindowsAddress.add(BigInteger.valueOf(hexTable.getSelectedRow() * 8 + hexTable.getSelectedColumn() - 1)), "PTE").setVisible(true);
	}

	private void jMemoryAddressComboBoxActionPerformed(ActionEvent evt) {
		if (this.isVisible()) {
			goMemoryButtonActionPerformed(evt);
		}
	}

	private JMenuItem getJDisassemble32MenuItem() {
		if (disassemble32MenuItem == null) {
			disassemble32MenuItem = new JMenuItem();
			disassemble32MenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jDisassemble32MenuItemActionPerformed(evt);
				}
			});
			disassemble32MenuItem.setText(MyLanguage.getString("Disassemble"));
		}
		return disassemble32MenuItem;
	}

	private void jDisassemble32MenuItemActionPerformed(ActionEvent evt) {
		this.instructionComboBox.setSelectedItem(currentMemoryWindowsAddress.add(BigInteger.valueOf(hexTable.getSelectedRow() * 8 + hexTable.getSelectedColumn() - 1)));
		disassembleButtonActionPerformed(null);
		jTabbedPane1.setSelectedIndex(0);
	}

	private JPanel getJELFBreakpointPanel() {
		if (elfBreakpointPanel == null) {
			elfBreakpointPanel = new JPanel();
			BorderLayout jELFBreakpointPanelLayout = new BorderLayout();
			elfBreakpointPanel.setLayout(jELFBreakpointPanelLayout);
			elfBreakpointPanel.add(getELFToolbar(), BorderLayout.NORTH);
			elfBreakpointPanel.add(getJScrollPane14(), BorderLayout.CENTER);
			elfBreakpointPanel.add(getJPanel24(), BorderLayout.SOUTH);
		}
		return elfBreakpointPanel;
	}

	private JToolBar getELFToolbar() {
		if (elfToolbar == null) {
			elfToolbar = new JToolBar();
			elfToolbar.add(getELFFileComboBox());
			elfToolbar.add(getOpenELFButton());
			elfToolbar.add(getShowELFByteCheckBox());
		}
		return elfToolbar;
	}

	private JButton getOpenELFButton() {
		if (openELFButton == null) {
			openELFButton = new JButton();
			openELFButton.setText("Open ELF");
			openELFButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					openELFButtonActionPerformed(evt);
				}
			});
		}
		return openELFButton;
	}

	private JComboBox<String> getELFFileComboBox() {
		if (elfFileComboBox == null) {
			ComboBoxModel<String> elfFileComboBoxModel = new DefaultComboBoxModel<String>();
			elfFileComboBox = new JComboBox<String>();
			elfFileComboBox.setModel(elfFileComboBoxModel);
			elfFileComboBox.setPreferredSize(new java.awt.Dimension(163, 26));
			elfFileComboBox.setMaximumSize(new Dimension(200, 23));
			elfFileComboBox.setOpaque(false);
			elfFileComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					elfFileComboBoxActionPerformed(evt);
				}
			});
		}
		return elfFileComboBox;
	}

	private JScrollPane getJScrollPane14() {
		if (jScrollPane14 == null) {
			jScrollPane14 = new JScrollPane();
			jScrollPane14.setViewportView(getJELFTable());
		}
		return jScrollPane14;
	}

	private JTable getJELFTable() {
		if (elfTable == null) {
			elfTable = new JTable();
			elfTable.setModel(new SourceCodeTableModel());
			elfTable.getColumnModel().getColumn(0).setCellRenderer(new SourceCodeCellRenderer());
			elfTable.getColumnModel().getColumn(3).setCellRenderer(new SourceCodeCellRenderer());
			elfTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			elfTable.getColumnModel().getColumn(0).setPreferredWidth(20);
			elfTable.getColumnModel().getColumn(1).setPreferredWidth(30);
			elfTable.getColumnModel().getColumn(2).setPreferredWidth(80);
			elfTable.getColumnModel().getColumn(3).setPreferredWidth(300);
			elfTable.getTableHeader().setReorderingAllowed(false);
			elfTable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					elfTableMouseClicked(evt);
				}
			});
		}
		return elfTable;
	}

	private void openELFButtonActionPerformed(ActionEvent evt) {
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(Setting.getInstance().lastElfHistoryOpenDir));

		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			openELF(fc.getSelectedFile());
			parseELF(fc.getSelectedFile());
		}
	}

	private void openELF(File file) {
		String lines = ElfUtil.getDebugLine(file);
		String filenames[] = lines.split("\n")[0].split(",");
		SourceCodeTableModel model = (SourceCodeTableModel) elfTable.getModel();
		model.setDebugLine(lines);

		for (int x = 0; x < filenames.length; x++) {
			// find source file
			Collection<File> found = FileUtils.listFiles(file.getParentFile(), FileFilterUtils.nameFileFilter(filenames[x]), TrueFileFilter.INSTANCE);
			if (found.size() == 0) {
				this.elfFileComboBox.addItem(file.getName() + " - " + filenames[x] + " (missing)");
			} else {
				File foundFile = (File) found.toArray()[0];

				// read source code
				try {
					List<String> list = FileUtils.readLines(foundFile);
					model.getSourceCodes().put(foundFile.getName(), list);
				} catch (IOException e) {
					e.printStackTrace();
				}

				this.elfFileComboBox.addItem(file.getName() + " - " + foundFile.getAbsolutePath().substring(file.getParent().length()));
				// end read source code
			}
			// end find source file
		}
		elfFileComboBoxActionPerformed(null);

		model.updateBreakpoint(getRealEIP());

		// save history
		Setting.getInstance().lastElfHistoryOpenDir = file.getParentFile().getAbsolutePath();
		Setting.getInstance().save();
		// end save history
	}

	private void elfFileComboBoxActionPerformed(ActionEvent evt) {
		SourceCodeTableModel model = (SourceCodeTableModel) elfTable.getModel();
		model.setCurrentFile(new File(elfFileComboBox.getSelectedItem().toString().split("-")[1]).getName());
	}

	private JPanel getJPanel24() {
		if (jPanel24 == null) {
			jPanel24 = new JPanel();
			jPanel24.add(getRefreshButton());
			jPanel24.add(getEnableELFBreakpointButton());
			jPanel24.add(getDisableELFBreakpointButton());
			jPanel24.add(getSaveELFBreakpointButton());
			jPanel24.add(getLoadELFBreakpointButton());
		}
		return jPanel24;
	}

	private JButton getRefreshButton() {
		if (refreshELFBreakpointButton == null) {
			refreshELFBreakpointButton = new JButton();
			refreshELFBreakpointButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					refreshELFBreakpointButtonActionPerformed(evt);
				}
			});
			refreshELFBreakpointButton.setText(MyLanguage.getString("Refresh"));
		}
		return refreshELFBreakpointButton;
	}

	private JButton getEnableELFBreakpointButton() {
		if (enableELFBreakpointButton == null) {
			enableELFBreakpointButton = new JButton();
			enableELFBreakpointButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					enableELFBreakpointButtonActionPerformed(evt);
				}
			});
			enableELFBreakpointButton.setText(MyLanguage.getString("Enable"));
		}
		return enableELFBreakpointButton;
	}

	private JButton getDisableELFBreakpointButton() {
		if (disableELFBreakpointButton == null) {
			disableELFBreakpointButton = new JButton();
			disableELFBreakpointButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					disableELFBreakpointButtonActionPerformed(evt);
				}
			});
			disableELFBreakpointButton.setText(MyLanguage.getString("Disable"));
		}
		return disableELFBreakpointButton;
	}

	private JButton getSaveELFBreakpointButton() {
		if (saveELFBreakpointButton == null) {
			saveELFBreakpointButton = new JButton();
			saveELFBreakpointButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					saveELFBreakpointButtonActionPerformed(evt);
				}
			});
			saveELFBreakpointButton.setText(MyLanguage.getString("Save"));
		}
		return saveELFBreakpointButton;
	}

	private JButton getLoadELFBreakpointButton() {
		if (loadELFBreakpointButton == null) {
			loadELFBreakpointButton = new JButton();
			loadELFBreakpointButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					loadELFBreakpointButtonActionPerformed(evt);
				}
			});
			loadELFBreakpointButton.setText(MyLanguage.getString("Load"));
		}
		return loadELFBreakpointButton;
	}

	private void refreshELFBreakpointButtonActionPerformed(ActionEvent evt) {
		if (Global.debug) {
			SourceCodeTableModel model = (SourceCodeTableModel) elfTable.getModel();

			model.updateBreakpoint(getRealEIP());
		}
	}

	public BigInteger getRealEIP() {
		try {
			//			BigInteger eip;
			//			if (CommonLib.getBit(CommonLib.string2long(registerPanel.cr0TextField.getText()), 0) == 1) {
			//				eip = CommonLib.string2BigInteger(registerPanel.eipTextField.getText());
			//			} else {
			//				eip = CommonLib.string2BigInteger(registerPanel.csTextField.getText()).multiply(BigInteger.valueOf(16))
			//						.add(CommonLib.string2BigInteger(registerPanel.eipTextField.getText()));
			//			}
			BigInteger cs;
			if (registerPanel.csTextField.getBase().equals("")) {
				cs = CommonLib.string2BigInteger(registerPanel.csTextField.getText());
			} else {
				cs = CommonLib.string2BigInteger(registerPanel.csTextField.getBase());
			}
			return cs.add(CommonLib.string2BigInteger(registerPanel.eipTextField.getText()));
		} catch (Exception ex) {
			return BigInteger.valueOf(0);
		}
	}

	private void enableELFBreakpointButtonActionPerformed(ActionEvent evt) {
		SourceCodeTableModel model = (SourceCodeTableModel) elfTable.getModel();
		BigInteger address = model.getDebugLineInfo().get(model.getCurrentFile()).get(this.elfTable.getSelectedRow());

		for (int x = 0; x < breakpointTable.getRowCount(); x++) {
			BigInteger addr = CommonLib.string2BigInteger(breakpointTable.getValueAt(x, 2).toString());
			if (addr == address) {
				String breakpointNo = breakpointTable.getValueAt(x, 0).toString().trim().split(" ")[0];
				VMController.getVM().enablePhysicalBreakpoint(CommonLib.string2BigInteger(breakpointNo));

				model.updateBreakpoint(getRealEIP());
				this.updateBreakpoint();
				return;
			}
		}
	}

	private void disableELFBreakpointButtonActionPerformed(ActionEvent evt) {
		SourceCodeTableModel model = (SourceCodeTableModel) elfTable.getModel();
		BigInteger address = model.getDebugLineInfo().get(model.getCurrentFile()).get(this.elfTable.getSelectedRow());

		for (int x = 0; x < breakpointTable.getRowCount(); x++) {
			BigInteger addr = CommonLib.string2BigInteger(breakpointTable.getValueAt(x, 2).toString());
			if (addr == address) {
				String breakpointNo = breakpointTable.getValueAt(x, 0).toString().trim().split(" ")[0];
				VMController.getVM().disablePhysicalBreakpoint(CommonLib.string2BigInteger(breakpointNo));

				model.updateBreakpoint(getRealEIP());
				this.updateBreakpoint();
				return;
			}
		}
	}

	private void saveELFBreakpointButtonActionPerformed(ActionEvent evt) {
		this.saveBreakpointButtonActionPerformed(null);
	}

	private void loadELFBreakpointButtonActionPerformed(ActionEvent evt) {
		this.loadBreakpointButtonActionPerformed(null);
	}

	private void elfTableMouseClicked(MouseEvent evt) {
		if (SwingUtilities.isRightMouseButton(evt)) {
			// select
			Point p = evt.getPoint();
			int rowNumber = elfTable.rowAtPoint(p);
			int columnNumber = elfTable.columnAtPoint(p);
			ListSelectionModel model = elfTable.getSelectionModel();
			model.setSelectionInterval(rowNumber, rowNumber);
			elfTable.getColumnModel().getSelectionModel().setSelectionInterval(columnNumber, columnNumber);
			// end select

			getELFTablePopupMenu().show(evt.getComponent(), evt.getX(), evt.getY());
		}
	}

	private JPanel getJPanel25() {
		if (jPanel25 == null) {
			jPanel25 = new JPanel();
			FlowLayout jPanel25Layout = new FlowLayout();
			jPanel25Layout.setHgap(0);
			jPanel25Layout.setVgap(0);
			jPanel25Layout.setAlignment(FlowLayout.LEFT);
			jPanel25.setLayout(jPanel25Layout);
			{
				statusLabel = new JLabel();
				jPanel25.add(statusLabel);
				statusLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
				statusLabel.setForeground(new java.awt.Color(255, 0, 0));
			}
			jPanel25.add(getCPUModeLabel());
			jPanel25.add(getBochsVersionLabel());
			jPanel25.add(getLatestVersionLabel());
		}
		return jPanel25;
	}

	private JLabel getCPUModeLabel() {
		if (cpuModeLabel == null) {
			cpuModeLabel = new JLabel();
		}
		return cpuModeLabel;
	}

	private JCheckBox getShowELFByteCheckBox() {
		if (showELFByteCheckBox == null) {
			showELFByteCheckBox = new JCheckBox();
			showELFByteCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					showELFByteCheckBoxActionPerformed(evt);
				}
			});
			showELFByteCheckBox.setText(MyLanguage.getString("Bytes"));
		}
		return showELFByteCheckBox;
	}

	private void showELFByteCheckBoxActionPerformed(ActionEvent evt) {
		SourceCodeTableModel model = (SourceCodeTableModel) elfTable.getModel();
		model.showBytes(showELFByteCheckBox.isSelected());
	}

	private JLabel getBochsVersionLabel() {
		if (bochsVersionLabel == null) {
			bochsVersionLabel = new JLabel();
		}
		return bochsVersionLabel;
	}

	private JLabel getLatestVersionLabel() {
		if (latestVersionLabel == null) {
			latestVersionLabel = new JLabel();
			latestVersionLabel.setForeground(Color.green);
			latestVersionLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					latestVersionLabelMouseClicked(evt);
				}
			});
		}
		return latestVersionLabel;
	}

	private void latestVersionLabelMouseClicked(MouseEvent evt) {
		if (!latestVersionLabel.getText().equals("")) {
			try {
				// java.awt.Desktop.getDesktop().browse(new
				// URI(jLatestVersionLabel.getText().split(MyLanguage.getString("Download_url")
				// + " : ")[1]));
				java.awt.Desktop.getDesktop().browse(new URI(latestVersionURL));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void thisWindowActivated(WindowEvent evt) {

	}

	private JPanel getELFDumpScrollPane() {
		if (elfDumpPanel == null) {
			elfDumpPanel = new JPanel();
			BorderLayout jELFDumpPanelLayout = new BorderLayout();
			elfDumpPanel.setLayout(jELFDumpPanelLayout);
			elfDumpPanel.add(getJPanel26(), BorderLayout.NORTH);
			elfDumpPanel.add(getJTabbedPane4(), BorderLayout.CENTER);
		}
		return elfDumpPanel;
	}

	private JToolBar getJPanel26() {
		if (jPanel26 == null) {
			jPanel26 = new JToolBar();
			FlowLayout jPanel26Layout = new FlowLayout();
			jPanel26Layout.setAlignment(FlowLayout.LEFT);
			jPanel26.add(getELFComboBox());
			jPanel26.add(getOpenELFDumpButton());
		}
		return jPanel26;
	}

	private JComboBox<File> getELFComboBox() {
		if (elfComboBox == null) {
			ComboBoxModel<File> elfComboBoxModel = new DefaultComboBoxModel<File>();
			elfComboBox = new JComboBox<File>();
			elfComboBox.setModel(elfComboBoxModel);
			elfComboBox.setMaximumSize(new java.awt.Dimension(400, 30));
			elfComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					elfComboBoxActionPerformed(evt);
				}
			});
		}
		return elfComboBox;
	}

	private JButton getOpenELFDumpButton() {
		if (openELFDumpButton == null) {
			openELFDumpButton = new JButton();
			openELFDumpButton.setText("Open ELF");
			openELFDumpButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					openELFDumpButtonActionPerformed(evt);
				}
			});
		}
		return openELFDumpButton;
	}

	private JTabbedPane getJTabbedPane4() {
		if (tabbedPane4 == null) {
			tabbedPane4 = new JMaximizableTabbedPane();
			tabbedPane4.addTab("Header", null, getELFHeaderScrollPane(), null);
			tabbedPane4.addTab("Section", null, getJScrollPane15(), null);
			tabbedPane4.addTab("Program header", null, getJScrollPane16(), null);
			tabbedPane4.addTab("objdump", null, getObjdumpPanel(), null);
			tabbedPane4.addTab(".rel.plt", null, getJPanel28(), null);
			tabbedPane4.addTab(".dynamic", null, getJPanel29(), null);
		}
		return tabbedPane4;
	}

	private JScrollPane getELFHeaderScrollPane() {
		if (elfHeaderScrollPane == null) {
			elfHeaderScrollPane = new JScrollPane();
			elfHeaderScrollPane.setViewportView(getELFHeaderTable());
		}
		return elfHeaderScrollPane;
	}

	private JTable getELFHeaderTable() {
		if (elfHeaderTable == null) {
			TableModel jELFHeaderTableModel = new DefaultTableModel(null, new String[] { MyLanguage.getString("Field"), MyLanguage.getString("Value") });
			elfHeaderTable = new JTable();
			elfHeaderTable.setModel(jELFHeaderTableModel);
		}
		return elfHeaderTable;
	}

	private void openELFDumpButtonActionPerformed(ActionEvent evt) {
		JFileChooser fc = new JFileChooser();
		// load history
		fc.setCurrentDirectory(new File(Setting.getInstance().lastElfHistoryOpenDir2));

		// end load history
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			elfComboBox.addItem(file);

			parseELF(file);
			openELF(file);
			// save history
			Setting.getInstance().lastElfHistoryOpenDir2 = file.getParentFile().getAbsolutePath();
			Setting.getInstance().save();
			// end save history
		}
	}

	private void parseELF(File elfFile) {
		elfDumpPanel.remove(tabbedPane4);
		tabbedPane4 = null;
		elfDumpPanel.add(getJTabbedPane4(), BorderLayout.CENTER);

		HashMap map = ElfUtil.getELFDetail(elfFile);
		if (map != null) {
			// header
			DefaultTableModel model = (DefaultTableModel) elfHeaderTable.getModel();
			while (model.getRowCount() > 0) {
				model.removeRow(0);
			}
			Set entries = ((HashMap) map.get("header")).entrySet();
			Iterator it = entries.iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();

				Vector<String> v = new Vector<String>();
				v.add(entry.getKey().toString());

				String bytesStr = "";

				if (entry.getValue().getClass() == Short.class) {
					statusLabel.setText("header " + Long.toHexString((Short) entry.getValue()));
					bytesStr += "0x" + Long.toHexString((Short) entry.getValue());
				} else if (entry.getValue().getClass() == Integer.class) {
					bytesStr += "0x" + Long.toHexString((Integer) entry.getValue());
				} else if (entry.getValue().getClass() == Long.class) {
					bytesStr += "0x" + Long.toHexString((Long) entry.getValue());
				} else {
					int b[] = (int[]) entry.getValue();
					for (int x = 0; x < b.length; x++) {
						bytesStr += "0x" + Long.toHexString(b[x]) + " ";
					}
				}

				v.add(bytesStr);
				model.addRow(v);
			}
			// end header

			// section
			model = (DefaultTableModel) elfSectionTable.getModel();
			while (model.getRowCount() > 0) {
				model.removeRow(0);
			}
			int sectionNo = 0;
			while (map.get("section" + sectionNo) != null) {
				entries = ((HashMap) map.get("section" + sectionNo)).entrySet();
				it = entries.iterator();
				Vector<String> v = new Vector<String>();
				while (it.hasNext()) {
					Map.Entry entry = (Map.Entry) it.next();

					String bytesStr = "";
					if (entry.getValue().getClass() == Short.class) {
						statusLabel.setText("section " + Long.toHexString((Short) entry.getValue()));
						bytesStr += "0x" + Long.toHexString((Short) entry.getValue());
					} else if (entry.getValue().getClass() == Integer.class) {
						bytesStr += "0x" + Long.toHexString((Integer) entry.getValue());
					} else if (entry.getValue().getClass() == String.class) {
						bytesStr = (String) entry.getValue();
					} else if (entry.getValue().getClass() == Long.class) {
						bytesStr += "0x" + Long.toHexString((Long) entry.getValue());
					} else {
						int b[] = (int[]) entry.getValue();
						for (int x = 0; x < b.length; x++) {
							bytesStr += "0x" + Long.toHexString(b[x]) + " ";
						}
					}

					v.add(bytesStr);
				}
				model.addRow(v);
				sectionNo++;
			}
			// end section

			// program header
			model = (DefaultTableModel) programHeaderTable.getModel();
			while (model.getRowCount() > 0) {
				model.removeRow(0);
			}
			int programHeaderNo = 0;
			while (map.get("programHeader" + programHeaderNo) != null) {
				entries = ((HashMap) map.get("programHeader" + programHeaderNo)).entrySet();
				it = entries.iterator();
				Vector<String> v = new Vector<String>();
				while (it.hasNext()) {
					Map.Entry entry = (Map.Entry) it.next();

					String bytesStr = "";
					if (entry.getValue().getClass() == Short.class) {
						statusLabel.setText("Program header " + Long.toHexString((Short) entry.getValue()));
						bytesStr += "0x" + Long.toHexString((Short) entry.getValue());
					} else if (entry.getValue().getClass() == Integer.class) {
						bytesStr += "0x" + Long.toHexString((Integer) entry.getValue());
					} else if (entry.getValue().getClass() == Long.class) {
						bytesStr += "0x" + Long.toHexString((Long) entry.getValue());
					} else if (entry.getValue().getClass() == String.class) {
						bytesStr += "0x" + entry.getValue();
					} else {
						int b[] = (int[]) entry.getValue();
						for (int x = 0; x < b.length; x++) {
							bytesStr += "0x" + Long.toHexString(b[x]) + " ";
						}
					}

					v.add(bytesStr);
				}
				model.addRow(v);
				programHeaderNo++;
			}
			// program header

			// symbol table
			int symbolTableNo = 0;
			while (map.get("symbolTable" + symbolTableNo) != null) {
				DefaultTableModel tempTableModel = new DefaultTableModel(null, new String[] { "No.", "st_name", "st_value", "st_size", "st_info", "st_other", "p_st_shndx" });
				JTable tempTable = new JTable();
				HashMap tempMap = (HashMap) map.get("symbolTable" + symbolTableNo);
				Vector<LinkedHashMap> v = (Vector<LinkedHashMap>) tempMap.get("vector");
				for (int x = 0; x < v.size(); x++) {
					Vector<String> tempV = new Vector<String>();
					statusLabel.setText("Symbol table " + x);
					tempV.add("0x" + Long.toHexString((Integer) v.get(x).get("No.")));
					tempV.add((String) v.get(x).get("st_name"));
					tempV.add("0x" + Long.toHexString((Long) v.get(x).get("st_value")));
					tempV.add("0x" + Long.toHexString((Long) v.get(x).get("st_size")));
					tempV.add("0x" + Long.toHexString((Integer) v.get(x).get("st_info")));
					tempV.add("0x" + Long.toHexString((Integer) v.get(x).get("st_other")));
					tempV.add("0x" + Long.toHexString((Integer) v.get(x).get("p_st_shndx")));

					tempTableModel.addRow(tempV);
				}

				tempTable.setModel(tempTableModel);
				JScrollPane tempScrollPane = new JScrollPane();
				tempScrollPane.setViewportView(tempTable);
				tabbedPane4.addTab(tempMap.get("name").toString(), null, tempScrollPane, null);

				symbolTableNo++;
			}
			// end symbol table

			// note
			int noteSectionNo = 0;
			while (map.get("note" + noteSectionNo) != null) {
				DefaultTableModel tempTableModel = new DefaultTableModel(null, new String[] { "No.", "namesz", "descsz", "type", "name", "desc" });
				JTable tempTable = new JTable();
				HashMap<String, Vector<LinkedHashMap<String, Object>>> tempMap = (HashMap) map.get("note" + noteSectionNo);
				Vector<LinkedHashMap<String, Object>> v = tempMap.get("vector");
				for (int x = 0; x < v.size(); x++) {
					Vector<String> tempV = new Vector<String>();
					statusLabel.setText("Note " + x);
					tempV.add("0x" + Long.toHexString((Integer) v.get(x).get("No.")));
					tempV.add("0x" + Long.toHexString((Long) v.get(x).get("namesz")));
					tempV.add("0x" + Long.toHexString((Long) v.get(x).get("descsz")));
					tempV.add("0x" + Long.toHexString((Long) v.get(x).get("type")));
					tempV.add((String) v.get(x).get("name"));
					tempV.add((String) v.get(x).get("desc"));

					tempTableModel.addRow(tempV);
				}

				tempTable.setModel(tempTableModel);
				JScrollPane tempScrollPane = new JScrollPane();
				tempScrollPane.setViewportView(tempTable);
				tabbedPane4.addTab(tempMap.get("name").toString(), null, tempScrollPane, null);

				noteSectionNo++;
			}
			// end note
		}

		try {
			statusLabel.setText("running objdump -DS");
			Process process = Runtime.getRuntime().exec("objdump -DS " + elfFile.getAbsolutePath());
			InputStream input = process.getInputStream();
			String str = "";
			byte b[] = new byte[102400];
			int len;
			while ((len = input.read(b)) > 0) {
				str += new String(b, 0, len);
			}
			jEditorPane1.setText(str);

			statusLabel.setText("readelf -r");
			process = Runtime.getRuntime().exec("readelf -r " + elfFile.getAbsolutePath());
			input = process.getInputStream();
			str = "";
			b = new byte[102400];
			while ((len = input.read(b)) > 0) {
				str += new String(b, 0, len);
			}
			jSearchRelPltEditorPane.setText(str);

			statusLabel.setText("readelf -d");
			process = Runtime.getRuntime().exec("readelf -d " + elfFile.getAbsolutePath());
			input = process.getInputStream();
			str = "";
			b = new byte[102400];
			while ((len = input.read(b)) > 0) {
				str += new String(b, 0, len);
			}
			input.close();
			searchDynamicEditorPane.setText(str);

			statusLabel.setText("");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// end symbol table
	}

	private void elfComboBoxActionPerformed(ActionEvent evt) {
		parseELF((File) elfComboBox.getSelectedItem());
	}

	private void thisWindowOpened(WindowEvent evt) {
		if (Global.debug) {
			logger.debug("updateVMStatus");
		}
		updateVMStatus(true);
		if (Global.debug) {
			logger.debug("updateVMStatus end");
		}
	}

	private JScrollPane getJScrollPane15() {
		if (jScrollPane15 == null) {
			jScrollPane15 = new JScrollPane();
			jScrollPane15.setViewportView(getJSectionTable());
		}
		return jScrollPane15;
	}

	private JTable getJSectionTable() {
		if (elfSectionTable == null) {
			TableModel jSectionTableModel = new DefaultTableModel(null, new String[] { "No.", "sh_name", "sh_type", "sh_flags", "sh_addr", "sh_offset", "sh_size", "sh_link",
					"sh_info", "sh_addralign", "sh_entsize" });
			elfSectionTable = new JTable();
			elfSectionTable.setModel(jSectionTableModel);
			elfSectionTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		}
		return elfSectionTable;
	}

	private JScrollPane getJScrollPane16() {
		if (jScrollPane16 == null) {
			jScrollPane16 = new JScrollPane();
			jScrollPane16.setViewportView(getJProgramHeaderTable());
		}
		return jScrollPane16;
	}

	private JTable getJProgramHeaderTable() {
		if (programHeaderTable == null) {
			TableModel jProgramHeaderTableModel = new DefaultTableModel(null, new String[] { "No.", "p_type", "p_offset", "p_vaddr", "p_filesz", "p_memsz", "p_flags", "p_align" });
			programHeaderTable = new JTable();
			programHeaderTable.setModel(jProgramHeaderTableModel);
			programHeaderTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		}
		return programHeaderTable;
	}

	private JButton getJButton19() {
		if (jButton19 == null) {
			jButton19 = new JButton();
			jButton19.setText("Delete");
			jButton19.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/cross.png")));
			jButton19.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jButton19ActionPerformed(evt);
				}
			});
		}
		return jButton19;
	}

	private void jButton19ActionPerformed(ActionEvent evt) {
		int rows[] = addressTranslateTable2.getSelectedRows();
		AddressTranslateTableModel model = (AddressTranslateTableModel) this.addressTranslateTable2.getModel();
		model.removeRow(rows);
	}

	private JButton getDumpCR3Button() {
		if (dumpCR3Button == null) {
			dumpCR3Button = new JButton();
			dumpCR3Button.setText("Dump CR3");
			dumpCR3Button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					dumpCR3ButtonActionPerformed(evt);
				}
			});
		}
		return dumpCR3Button;
	}

	private JButton getDumpPageTableAtAddressButton() {
		if (dumpPageTableAtAddressButton == null) {
			dumpPageTableAtAddressButton = new JButton();
			dumpPageTableAtAddressButton.setText("Dump at here");
			dumpPageTableAtAddressButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					dumpPageTableAtAddressButtonActionPerformed(evt);
				}
			});
		}
		return dumpPageTableAtAddressButton;
	}

	private JTextField getDumpPageDirectoryAddressTextField() {
		if (dumpPageDirectoryAddressTextField == null) {
			dumpPageDirectoryAddressTextField = new JTextField();
			dumpPageDirectoryAddressTextField.setMaximumSize(new java.awt.Dimension(150, 28));
			dumpPageDirectoryAddressTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					dumpPageDirectoryAddressTextFieldKeyTyped(evt);
				}
			});
		}
		return dumpPageDirectoryAddressTextField;
	}

	private void dumpCR3ButtonActionPerformed(ActionEvent evt) {
		updatePageTable(CommonLib.string2BigInteger(registerPanel.cr3TextField.getText()));
	}

	private void dumpPageTableAtAddressButtonActionPerformed(ActionEvent evt) {
		updatePageTable(CommonLib.string2BigInteger(dumpPageDirectoryAddressTextField.getText()));
	}

	private JButton getJButton21x() {
		if (jButton21 == null) {
			jButton21 = new JButton();
			jButton21.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/excel.gif")));
			jButton21.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jButton21ActionPerformed(evt);
				}
			});
		}
		return jButton21;
	}

	private void jButton21ActionPerformed(ActionEvent evt) {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			GKDCommonLib.exportTableModelToExcel(file, this.pageDirectoryTable.getModel(), this.pageTableTable.getModel(), memoryAddressComboBox.getSelectedItem().toString());
		}
	}

	private void jButton17ActionPerformed(ActionEvent evt) {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			if (!GKDCommonLib.saveImage(addressTranslateTable2, file)) {
				JOptionPane.showMessageDialog(this, "Cannot save image.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void jButton18ActionPerformed(ActionEvent evt) {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			GKDCommonLib.exportTableModelToExcel(file, this.addressTranslateTable2.getModel(), memoryAddressComboBox.getSelectedItem().toString());
		}
	}

	private void dumpPageDirectoryAddressTextFieldKeyTyped(KeyEvent evt) {
		if (evt.getKeyChar() == '\n') {
			dumpPageTableAtAddressButtonActionPerformed(null);
		}
	}

	private void addressTextFieldKeyTyped(KeyEvent evt) {
		if (evt.getKeyChar() == '\n') {
			refreshAddressTranslateButtonActionPerformed(null);
		}
	}

	private JButton getClearBochsButton() {
		if (clearBochsButton == null) {
			clearBochsButton = new JButton();
			clearBochsButton.setText(MyLanguage.getString("Clear"));
			clearBochsButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					clearBochsButtonActionPerformed(evt);
				}
			});
		}
		return clearBochsButton;
	}

	private void clearBochsButtonActionPerformed(ActionEvent evt) {
		this.vmCommandEditorPane.setText("");
	}

	private static String getMemoryStr(BigInteger address, int totalByte, boolean isPhysicalAddress) {
		int bytes[];
		if (isPhysicalAddress) {
			bytes = VMController.getVM().physicalMemory(address, totalByte);
		} else {
			bytes = VMController.getVM().virtualMemory(address, totalByte);
		}

		String str = "";
		for (int x = 0; x < bytes.length; x++) {
			str += (char) bytes[x];
		}
		return str;
	}

	private JButton getGoLinearButton() {
		if (goLinearButton == null) {
			goLinearButton = new JButton();
			goLinearButton.setText("Lin");
			goLinearButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					goLinearButtonActionPerformed(evt);
				}
			});
			goLinearButton.setToolTipText(MyLanguage.getString("Linear_address"));
		}
		return goLinearButton;
	}

	private void goLinearButtonActionPerformed(ActionEvent evt) {
		updateMemory(false);

		addMemoryAddressComboBox(memoryAddressComboBox.getSelectedItem().toString());
		Setting.getInstance().memoryCombo.add(memoryAddressComboBox.getSelectedItem().toString());
		Setting.getInstance().save();
	}

	private DiskPanel getDiskPanel() {
		if (diskPanel == null) {
			diskPanel = new DiskPanel();
			try {
				if (VMController.vmType == VMType.Bochs) {
					String line = GKDCommonLib.findLineInFile(new File(bochsrc), "ata0-master");
					if (line != null) {
						String strs[] = line.split(",");
						for (String str : strs) {
							if (str.toLowerCase().contains("path=")) {
								String filename = str.split("=")[1];
								filename = filename.replaceAll("\"", "");
								File file = new File(filename);
								if (file.exists()) {
									diskPanel.setFile(new File(filename));
								}
								break;
							}
						}
					}
				} else if (VMController.vmType == VMType.Qemu) {
					diskPanel.setFile(new File(GKDCommonLib.readConfig(cmd, "/gkd/hd/text()")));
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return diskPanel;
	}

	private JMaximizableTabbedPane_BasePanel getMaximizableTabbedPane_BasePanel1() {
		if (maximizableTabbedPane_BasePanel1 == null) {
			maximizableTabbedPane_BasePanel1 = new JMaximizableTabbedPane_BasePanel();
			maximizableTabbedPane_BasePanel1.add(getSplitPane2(), "MAIN");
		}
		return maximizableTabbedPane_BasePanel1;
	}

	private JCheckBox getHideIfAddressIsZeroCheckBox() {
		if (hideIfAddressIsZeroCheckBox == null) {
			hideIfAddressIsZeroCheckBox = new JCheckBox();
			hideIfAddressIsZeroCheckBox.setText("Hide if address = 0");
			hideIfAddressIsZeroCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					hideIfAddressIsZeroCheckBoxActionPerformed(evt);
				}
			});
		}
		return hideIfAddressIsZeroCheckBox;
	}

	private void hideIfAddressIsZeroCheckBoxActionPerformed(ActionEvent evt) {
		((PageDirectoryTableModel) pageDirectoryTable.getModel()).setShowZeroAddress(!hideIfAddressIsZeroCheckBox.isSelected());
		((PageTableTableModel) pageTableTable.getModel()).setShowZeroAddress(!hideIfAddressIsZeroCheckBox.isSelected());
	}

	private JPopupMenu getInstructionPanelPopupMenu() {
		if (instructionPanelPopupMenu == null) {
			instructionPanelPopupMenu = new JPopupMenu();
			instructionPanelPopupMenu.add(getSetPhysicalBreakpointMenuItem());
			instructionPanelPopupMenu.add(getSetLinearBreakpointMenuItem());
			instructionPanelPopupMenu.add(getJSeparator3());
			instructionPanelPopupMenu.add(getDisasmHereMenuItem());
			instructionPanelPopupMenu.add(getDisasmFromEIPMinus100MenuItem());
			instructionPanelPopupMenu.add(getClearInstructionTableMenuItem());
		}
		return instructionPanelPopupMenu;
	}

	private JMenuItem getSetPhysicalBreakpointMenuItem() {
		if (setPhysicalBreakpointMenuItem == null) {
			setPhysicalBreakpointMenuItem = new JMenuItem();
			setPhysicalBreakpointMenuItem.setText("Set physical breakpoint here");
			setPhysicalBreakpointMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					setPhysicalBreakpointMenuItemActionPerformed(evt);
				}
			});
		}
		return setPhysicalBreakpointMenuItem;
	}

	private JMenuItem getSetLinearBreakpointMenuItem() {
		if (setLinearBreakpointMenuItem == null) {
			setLinearBreakpointMenuItem = new JMenuItem();
			setLinearBreakpointMenuItem.setText("Set linear breakpoint here");
			setLinearBreakpointMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					setLinearBreakpointMenuItemActionPerformed(evt);
				}
			});
		}
		return setLinearBreakpointMenuItem;
	}

	public void instructionTableMouseClicked(MouseEvent evt) {
		if (SwingUtilities.isRightMouseButton(evt)) {
			JTable jTable = (JTable) evt.getSource();

			if (jTable == instructionTable) {
				Global.clickedWhichInstructionPanel = 0;
			} else {
				Global.clickedWhichInstructionPanel = 1;
			}

			// select
			Point p = evt.getPoint();
			int rowNumber = jTable.rowAtPoint(p);
			int columnNumber = jTable.columnAtPoint(p);
			ListSelectionModel model = jTable.getSelectionModel();
			model.setSelectionInterval(rowNumber, rowNumber);

			jTable.getColumnModel().getSelectionModel().setSelectionInterval(columnNumber, columnNumber);
			// end select

			getInstructionPanelPopupMenu().show(evt.getComponent(), evt.getX(), evt.getY());
		}
	}

	private void setPhysicalBreakpointMenuItemActionPerformed(ActionEvent evt) {
		if (registerToggleButton.isSelected()) {
			InstructionTableModel model = (InstructionTableModel) instructionTable.getModel();
			VMController.getVM().addPhysicalBreakpoint(CommonLib.string2BigInteger(model.getMemoryAddress(instructionTable.getSelectedRow())));
		} else if (this.sourceLevelDebuggerToggleButton.isSelected()) {
			InstructionTableModel model = (InstructionTableModel) sourceLevelDebugger.instructionTable.getModel();
			VMController.getVM().addPhysicalBreakpoint(CommonLib.string2BigInteger(model.getMemoryAddress(sourceLevelDebugger.instructionTable.getSelectedRow())));
		}
		updateBreakpoint();
		updateInstruction(null);
	}

	private void setLinearBreakpointMenuItemActionPerformed(ActionEvent evt) {
		if (registerToggleButton.isSelected()) {
			VMController.getVM().addLinearBreakpoint(CommonLib.string2BigInteger((String) GKD.instructionTable.getValueAt(GKD.instructionTable.getSelectedRow(), 1)));
		} else if (this.sourceLevelDebuggerToggleButton.isSelected()) {
			VMController.getVM().addLinearBreakpoint(
					CommonLib.string2BigInteger((String) this.sourceLevelDebugger.instructionTable.getValueAt(this.sourceLevelDebugger.instructionTable.getSelectedRow(), 1)));
		}
		updateBreakpoint();
		updateInstruction(null);
	}

	private void breakpointTableMouseClicked(MouseEvent evt) {
		if (SwingUtilities.isRightMouseButton(evt)) {
			// select
			Point p = evt.getPoint();
			int rowNumber = breakpointTable.rowAtPoint(p);
			int columnNumber = breakpointTable.columnAtPoint(p);
			ListSelectionModel model = breakpointTable.getSelectionModel();
			model.setSelectionInterval(rowNumber, rowNumber);
			breakpointTable.getColumnModel().getSelectionModel().setSelectionInterval(columnNumber, columnNumber);
			// end select

			getBreakpointPopupMenu().show(evt.getComponent(), evt.getX(), evt.getY());
		}
	}

	private JPopupMenu getBreakpointPopupMenu() {
		if (breakpointPopupMenu == null) {
			breakpointPopupMenu = new JPopupMenu();
			breakpointPopupMenu.add(getDumpHereMenuItem());
			breakpointPopupMenu.add(getDisassembleMenuItem());
		}
		return breakpointPopupMenu;
	}

	private JMenuItem getDumpHereMenuItem() {
		if (dumpHereMenuItem == null) {
			dumpHereMenuItem = new JMenuItem();
			dumpHereMenuItem.setText("Dump here");
			dumpHereMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					dumpHereMenuItemActionPerformed(evt);
				}
			});
		}
		return dumpHereMenuItem;
	}

	private JMenuItem getDisassembleMenuItem() {
		if (disassembleMenuItem == null) {
			disassembleMenuItem = new JMenuItem();
			disassembleMenuItem.setText(MyLanguage.getString("Disassemble"));
			disassembleMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					disassembleMenuItemActionPerformed(evt);
				}
			});
		}
		return disassembleMenuItem;
	}

	private void dumpHereMenuItemActionPerformed(ActionEvent evt) {
		this.memoryAddressComboBox.setSelectedItem(this.breakpointTable.getValueAt(this.breakpointTable.getSelectedRow(), 2));
		if (this.breakpointTable.getValueAt(this.breakpointTable.getSelectedRow(), 0).toString().contains("lb")) {
			goLinearButtonActionPerformed(null);
		} else {
			goMemoryButtonActionPerformed(null);
		}
		tabbedPane3.setSelectedIndex(0);
	}

	private void disassembleMenuItemActionPerformed(ActionEvent evt) {
		this.instructionComboBox.setSelectedItem(this.breakpointTable.getValueAt(this.breakpointTable.getSelectedRow(), 2));
		disassembleButtonActionPerformed(null);
		jTabbedPane1.setSelectedIndex(0);
	}

	private void searchMemoryTableMouseClicked(MouseEvent evt) {
		if (SwingUtilities.isRightMouseButton(evt)) {
			// select
			Point p = evt.getPoint();
			int rowNumber = searchMemoryTable.rowAtPoint(p);
			int columnNumber = searchMemoryTable.columnAtPoint(p);
			ListSelectionModel model = searchMemoryTable.getSelectionModel();
			model.setSelectionInterval(rowNumber, rowNumber);
			searchMemoryTable.getColumnModel().getSelectionModel().setSelectionInterval(columnNumber, columnNumber);
			// end select

			getJSearchMemoryTablePopupMenu().show(evt.getComponent(), evt.getX(), evt.getY());
		}
	}

	private JPopupMenu getJSearchMemoryTablePopupMenu() {
		if (searchMemoryTablePopupMenu == null) {
			searchMemoryTablePopupMenu = new JPopupMenu();
			searchMemoryTablePopupMenu.add(getSetPhysicalBreakpointHereMenuItem());
			searchMemoryTablePopupMenu.add(getSetLinerBreakpointHereMenuItem());
		}
		return searchMemoryTablePopupMenu;
	}

	private JMenuItem getSetPhysicalBreakpointHereMenuItem() {
		if (setPhysicalBreakpointHereMenuItem == null) {
			setPhysicalBreakpointHereMenuItem = new JMenuItem();
			setPhysicalBreakpointHereMenuItem.setText("Set physical breakpoint here");
			setPhysicalBreakpointHereMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					setPhysicalBreakpointHereMenuItemActionPerformed(evt);
				}
			});
		}
		return setPhysicalBreakpointHereMenuItem;
	}

	private JMenuItem getSetLinerBreakpointHereMenuItem() {
		if (setLinerBreakpointHereMenuItem == null) {
			setLinerBreakpointHereMenuItem = new JMenuItem();
			setLinerBreakpointHereMenuItem.setText("Set linear breakpoint here");
			setLinerBreakpointHereMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					setLinerBreakpointHereMenuItemActionPerformed(evt);
				}
			});
		}
		return setLinerBreakpointHereMenuItem;
	}

	private void setPhysicalBreakpointHereMenuItemActionPerformed(ActionEvent evt) {
		logger.debug(this.searchMemoryTable.getValueAt(searchMemoryTable.getSelectedRow(), 0));
		VMController.getVM().addLinearBreakpoint(CommonLib.string2BigInteger((String) searchMemoryTable.getValueAt(this.searchMemoryTable.getSelectedRow(), 0)));
		this.updateBreakpoint();
	}

	private void setLinerBreakpointHereMenuItemActionPerformed(ActionEvent evt) {
		VMController.getVM().addLinearBreakpoint(CommonLib.string2BigInteger((String) searchMemoryTable.getValueAt(this.searchMemoryTable.getSelectedRow(), 0)));
		this.updateBreakpoint();
	}

	private JButton getInstructionUpButton() {
		if (instructionUpButton == null) {
			instructionUpButton = new JButton();
			instructionUpButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/arrow_up1.png")));
			instructionUpButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					instructionUpButtonActionPerformed(evt);
				}
			});
		}
		return instructionUpButton;
	}

	private JButton getInstructionDownButton() {
		if (instructionDownButton == null) {
			instructionDownButton = new JButton();
			instructionDownButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/arrow_down.png")));
			instructionDownButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					instructionDownButtonActionPerformed(evt);
				}
			});
		}
		return instructionDownButton;
	}

	public JEditorPane getBochsEditorPane() {
		return vmCommandEditorPane;
	}

	public void instructionUpButtonActionPerformed(ActionEvent evt) {
		if (GKD.instructionTable.getRowCount() > 0) {
			String firstAddress = "";
			int x = 0;
			do {
				firstAddress = GKD.instructionTable.getValueAt(x, 1).toString().replaceAll("^-*", "").split(":")[0];
				x++;
			} while (!CommonLib.isNumber(firstAddress));
			firstAddress = CommonLib.string2BigInteger(firstAddress).subtract(BigInteger.valueOf(1)).toString(16);

			instructionComboBox.setSelectedItem("0x" + firstAddress);
			updateInstruction(CommonLib.string2BigInteger("0x" + firstAddress));
			updateBreakpointTableColor();
		}
	}

	public void instructionDownButtonActionPerformed(ActionEvent evt) {
		try {
			if (GKD.instructionTable.getRowCount() > 10) {
				String firstAddress = "";
				for (int x = 0, count = 0; count < 10 && x < GKD.instructionTable.getRowCount(); x++) {
					firstAddress = GKD.instructionTable.getValueAt(x, 1).toString().split(":")[0];
					if (CommonLib.isNumber(firstAddress)) {
						count++;
					}
				}

				instructionComboBox.setSelectedItem(firstAddress);
				updateInstruction(CommonLib.string2BigInteger(firstAddress));
				updateBreakpointTableColor();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private JButton getInstructionUpTenButton() {
		if (instructionUpTenButton == null) {
			instructionUpTenButton = new JButton();
			instructionUpTenButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/arrow_up10.png")));
			instructionUpTenButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					instructionUpTenButtonActionPerformed(evt);
				}
			});
		}
		return instructionUpTenButton;
	}

	public void instructionUpTenButtonActionPerformed(ActionEvent evt) {
		String firstAddress = "";
		int x = 0;
		do {
			firstAddress = GKD.instructionTable.getValueAt(x, 1).toString().replaceAll("^-*", "").split(":")[0];
			x++;
		} while (!CommonLib.isNumber(firstAddress));
		firstAddress = CommonLib.string2BigInteger(firstAddress).subtract(BigInteger.valueOf(16)).toString(16);

		this.instructionComboBox.setSelectedItem("0x" + firstAddress);
		updateInstruction(CommonLib.string2BigInteger("0x" + firstAddress));
		updateBreakpointTableColor();
	}

	private JMenuItem getKoreanMenuItem() {
		if (koreanMenuItem == null) {
			koreanMenuItem = new JMenuItem();
			koreanMenuItem.setText("Korean");
			koreanMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					koreanMenuItemActionPerformed(evt);
				}
			});
		}
		return koreanMenuItem;
	}

	private void koreanMenuItemActionPerformed(ActionEvent evt) {
		changeLanguage("KR");
	}

	private JMenuItem getJapaneseMenuItem() {
		if (japaneseMenuItem == null) {
			japaneseMenuItem = new JMenuItem();
			japaneseMenuItem.setText("Japanese");
			japaneseMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					japaneseMenuItemActionPerformed(evt);
				}
			});
		}
		return japaneseMenuItem;
	}

	private void japaneseMenuItemActionPerformed(ActionEvent evt) {
		changeLanguage("JP");
	}

	private void idtTableMouseClicked(MouseEvent evt) {
		if (evt.getClickCount() == 2) {
			for (int x = 0; x < bottomTabbedPane.getTabCount(); x++) {
				if (bottomTabbedPane.getTitleAt(x).equals(("IDT " + String.format("0x%02x", idtTable.getSelectedRow())))) {
					bottomTabbedPane.setSelectedIndex(x);
					return;
				}
			}
			bottomTabbedPane.addTabWithCloseButton("IDT " + String.format("0x%02x", idtTable.getSelectedRow()), null,
					new IDTDescriptorPanel(this, CommonLib.string2BigInteger(this.registerPanel.idtrTextField.getText()), idtTable.getSelectedRow()), null);
			bottomTabbedPane.setSelectedIndex(bottomTabbedPane.getTabCount() - 1);
		}
	}

	private JButton getFastStepBochsButton() {
		if (fastStepBochsButton == null) {
			fastStepBochsButton = new JButton();
			fastStepBochsButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/step.png")));
			fastStepBochsButton.setText(MyLanguage.getString("Fast_Step"));
			fastStepBochsButton
					.setToolTipText("<html><body>A faster step<br><br>It will only update:<br>1) Memory panel<br>2) Instruction panel<br>3) Register panel<br>4) EFlags</body></html>");
			fastStepBochsButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					fastStepButtonActionPerformed(evt);
				}
			});
		}
		return fastStepBochsButton;
	}

	private void fastStepButtonActionPerformed(ActionEvent evt) {
		try {
			VMController.getVM().singleStep();
			WebServiceUtil.log("gkd", "fast step", null, null, null);
			Thread updateThread = new Thread("Fast step update thread") {
				public void run() {
					logger.debug("fastStepButtonActionPerformed");
					enableAllButtons(false, false);

					if (Setting.getInstance().updateFastStepCommand_register) {
						if (Global.debug) {
							logger.debug("updateRegister");
						}
						updateRegister(true);
						if (Global.debug) {
							logger.debug("updateEFlags");
						}
						updateEFlags();
					}

					if (Setting.getInstance().updateFastStepCommand_memory) {
						if (Global.debug) {
							logger.debug("updateMemory");
						}
						updateMemory(true);
					}

					if (Setting.getInstance().updateFastStepCommand_instruction) {
						if (Global.debug) {
							logger.debug("updateInstruction");
						}
						updateInstruction(null);
					}

					if (Setting.getInstance().updateFastStepCommand_breakpoint) {
						if (Global.debug) {
							logger.debug("updateBreakpointTableColor");
						}
						updateBreakpoint();
						updateBreakpointTableColor();
					}

					if (Setting.getInstance().updateFastStepCommand_history) {
						if (Global.debug) {
							logger.debug("updateHistory");
						}
						//						updateHistoryTable(result);
					}

					statusLabel.setText("");

					enableAllButtons(true, false);
				}
			};
			updateThread.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private JPopupMenu getELFTablePopupMenu() {
		if (elfTablePopupMenu == null) {
			elfTablePopupMenu = new JPopupMenu();
			elfTablePopupMenu.add(getSetELFLinearBreakpointMenuItem());
			elfTablePopupMenu.add(getSetELFPhysicalBreakpointMenuItem());
		}
		return elfTablePopupMenu;
	}

	private JMenuItem getSetELFLinearBreakpointMenuItem() {
		if (setELFLinearBreakpointMenuItem == null) {
			setELFLinearBreakpointMenuItem = new JMenuItem();
			setELFLinearBreakpointMenuItem.setText("Set linear breakpoint here");
			setELFLinearBreakpointMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					setELFLinearBreakpointMenuItemActionPerformed(evt);
				}
			});
		}
		return setELFLinearBreakpointMenuItem;
	}

	private JMenuItem getSetELFPhysicalBreakpointMenuItem() {
		if (setELFPhysicalBreakpointMenuItem == null) {
			setELFPhysicalBreakpointMenuItem = new JMenuItem();
			setELFPhysicalBreakpointMenuItem.setText("Set physical breakpoint here");
			setELFPhysicalBreakpointMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					setELFPhysicalBreakpointMenuItemActionPerformed(evt);
				}
			});
		}
		return setELFPhysicalBreakpointMenuItem;
	}

	private void setELFLinearBreakpointMenuItemActionPerformed(ActionEvent evt) {
		SourceCodeTableModel model = (SourceCodeTableModel) elfTable.getModel();
		BigInteger address = model.getDebugLineInfo().get(model.getCurrentFile()).get(this.elfTable.getSelectedRow());
		if (address != null) {
			VMController.getVM().addLinearBreakpoint(address);

			model.updateBreakpoint(getRealEIP());
			this.updateBreakpoint();
		}
	}

	private void setELFPhysicalBreakpointMenuItemActionPerformed(ActionEvent evt) {
		SourceCodeTableModel model = (SourceCodeTableModel) elfTable.getModel();
		BigInteger address = model.getDebugLineInfo().get(model.getCurrentFile()).get(this.elfTable.getSelectedRow());
		if (address != null) {
			VMController.getVM().addLinearBreakpoint(address);

			model.updateBreakpoint(getRealEIP());
			this.updateBreakpoint();
		}
	}

	private JButton getSettingButton() {
		if (settingButton == null) {
			settingButton = new JButton();
			settingButton.setText(MyLanguage.getString("Setting"));
			settingButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/wrench.png")));
			settingButton.setToolTipText("System setting");
			settingButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					settingButtonActionPerformed(evt);
				}
			});
		}
		return settingButton;
	}

	private void settingButtonActionPerformed(ActionEvent evt) {
		SettingDialog jSettingDialog = new SettingDialog(this);
		CommonLib.centerDialog(jSettingDialog);
		jSettingDialog.setVisible(true);
	}

	private JPanel getObjdumpPanel() {
		if (objdumpPanel == null) {
			objdumpPanel = new JPanel();
			BorderLayout objdumpLayout = new BorderLayout();
			objdumpPanel.setLayout(objdumpLayout);
			objdumpPanel.add(getJPanel27(), BorderLayout.NORTH);
			objdumpPanel.add(getJScrollPane17(), BorderLayout.CENTER);
		}
		return objdumpPanel;
	}

	private JToolBar getJPanel27() {
		if (jPanel27 == null) {
			jPanel27 = new JToolBar();
			FlowLayout jPanel27Layout = new FlowLayout();
			jPanel27Layout.setAlignment(FlowLayout.LEFT);
			jPanel27.add(getSearchObjdumpTextField());
			jPanel27.add(getSearchObjdumpButton());
		}
		return jPanel27;
	}

	private JTextField getSearchObjdumpTextField() {
		if (searchObjdumpTextField == null) {
			searchObjdumpTextField = new JTextField();
			searchObjdumpTextField.setMaximumSize(new java.awt.Dimension(100, 25));
			searchObjdumpTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					searchObjdumpTextFieldKeyTyped(evt);
				}
			});
		}
		return searchObjdumpTextField;
	}

	private void searchObjdumpTextFieldKeyTyped(KeyEvent evt) {
		if (evt.getKeyChar() == '\n') {
			searchObjdumpButtonActionPerformed(null);
		}
	}

	private JButton getSearchObjdumpButton() {
		if (searchObjdumpButton == null) {
			searchObjdumpButton = new JButton();
			searchObjdumpButton.setText("Search");
			searchObjdumpButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					searchObjdumpButtonActionPerformed(evt);
				}
			});
		}
		return searchObjdumpButton;
	}

	final Highlighter hilit = new DefaultHighlighter();
	final Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.BLUE);
	private JButton jumpToInstructionButton;

	private void searchObjdumpButtonActionPerformed(ActionEvent evt) {
		if (searchObjdumpTextField.getText().length() > 0) {
			Highlighter h = jEditorPane1.getHighlighter();
			h.removeAllHighlights();
			String text = jEditorPane1.getText().toLowerCase();

			int nextPosition = -1;

			for (int j = 0; j < text.length() - searchObjdumpTextField.getText().length() + 1; j += 1) {
				if (text.substring(j, j + searchObjdumpTextField.getText().length()).equals(searchObjdumpTextField.getText().toLowerCase())) {
					try {
						if (j >= jEditorPane1.getCaretPosition() && nextPosition == -1) {
							h.addHighlight(j, j + searchObjdumpTextField.getText().length(), new DefaultHighlighter.DefaultHighlightPainter(Color.red));
							nextPosition = j + searchObjdumpTextField.getText().length();
						} else {
							h.addHighlight(j, j + searchObjdumpTextField.getText().length(), new DefaultHighlighter.DefaultHighlightPainter(Color.yellow));
						}
					} catch (BadLocationException ble) {
					}
				}
			}
			if (nextPosition != -1) {
				jEditorPane1.setCaretPosition(nextPosition);
			} else {
				jEditorPane1.setCaretPosition(0);
			}
		}
	}

	private JEditorPane getJEditorPane1() {
		if (jEditorPane1 == null) {
			jEditorPane1 = new JEditorPane();
		}
		return jEditorPane1;
	}

	private JScrollPane getJScrollPane17() {
		if (jScrollPane17 == null) {
			jScrollPane17 = new JScrollPane();
			jScrollPane17.setPreferredSize(new java.awt.Dimension(997, 512));
			jScrollPane17.setViewportView(getJEditorPane1());
		}
		return jScrollPane17;
	}

	private JPanel getJPanel28() {
		if (jPanel28 == null) {
			jPanel28 = new JPanel();
			BorderLayout jPanel28Layout = new BorderLayout();
			jPanel28.setLayout(jPanel28Layout);
			jPanel28.add(getJScrollPane18(), BorderLayout.CENTER);
			jPanel28.add(getJToolBar4(), BorderLayout.NORTH);
		}
		return jPanel28;
	}

	private JScrollPane getJScrollPane18() {
		if (jScrollPane18 == null) {
			jScrollPane18 = new JScrollPane();
			jScrollPane18.setPreferredSize(new java.awt.Dimension(993, 533));
			jScrollPane18.setViewportView(getJEditorPane2());
		}
		return jScrollPane18;
	}

	private JEditorPane getJEditorPane2() {
		if (jSearchRelPltEditorPane == null) {
			jSearchRelPltEditorPane = new JEditorPane();
		}
		return jSearchRelPltEditorPane;
	}

	private JToolBar getJToolBar4() {
		if (jToolBar4 == null) {
			jToolBar4 = new JToolBar();
			jToolBar4.add(getSearchRelPltTextField());
			jToolBar4.add(getSearchRelPltButton());
		}
		return jToolBar4;
	}

	private JTextField getSearchRelPltTextField() {
		if (searchRelPltTextField == null) {
			searchRelPltTextField = new JTextField();
			searchRelPltTextField.setMaximumSize(new java.awt.Dimension(100, 25));
			searchRelPltTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					searchRelPltTextFieldKeyTyped(evt);
				}
			});
		}
		return searchRelPltTextField;
	}

	private JButton getSearchRelPltButton() {
		if (searchRelPltButton == null) {
			searchRelPltButton = new JButton();
			searchRelPltButton.setText("Search");
			searchRelPltButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					searchRelPltButtonActionPerformed(evt);
				}
			});
		}
		return searchRelPltButton;
	}

	private void searchRelPltButtonActionPerformed(ActionEvent evt) {
		if (searchRelPltTextField.getText().length() > 0) {
			Highlighter h = jSearchRelPltEditorPane.getHighlighter();
			h.removeAllHighlights();
			String text = jSearchRelPltEditorPane.getText().toLowerCase();

			int nextPosition = -1;

			for (int j = 0; j < text.length() - searchRelPltTextField.getText().length() + 1; j += 1) {
				if (text.substring(j, j + searchRelPltTextField.getText().length()).equals(searchRelPltTextField.getText().toLowerCase())) {
					try {
						if (j >= jSearchRelPltEditorPane.getCaretPosition() && nextPosition == -1) {
							h.addHighlight(j, j + searchRelPltTextField.getText().length(), new DefaultHighlighter.DefaultHighlightPainter(Color.red));
							nextPosition = j + searchRelPltTextField.getText().length();
						} else {
							h.addHighlight(j, j + searchRelPltTextField.getText().length(), new DefaultHighlighter.DefaultHighlightPainter(Color.yellow));
						}
					} catch (BadLocationException ble) {
					}
				}
			}
			if (nextPosition != -1) {
				jSearchRelPltEditorPane.setCaretPosition(nextPosition);
			} else {
				jSearchRelPltEditorPane.setCaretPosition(0);
			}
		}
	}

	private JPanel getJPanel29() {
		if (jPanel29 == null) {
			jPanel29 = new JPanel();
			BorderLayout jPanel29Layout = new BorderLayout();
			jPanel29.setLayout(jPanel29Layout);
			jPanel29.add(getJToolBar5(), BorderLayout.NORTH);
			jPanel29.add(getJScrollPane19(), BorderLayout.CENTER);
		}
		return jPanel29;
	}

	private JToolBar getJToolBar5() {
		if (jToolBar5 == null) {
			jToolBar5 = new JToolBar();
			jToolBar5.add(getSearchDynamicTextField());
			jToolBar5.add(getSearchDynamicButton());
		}
		return jToolBar5;
	}

	private JTextField getSearchDynamicTextField() {
		if (searchDynamicTextField == null) {
			searchDynamicTextField = new JTextField();
			searchDynamicTextField.setMaximumSize(new java.awt.Dimension(100, 25));
			searchDynamicTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent evt) {
					searchDynamicTextFieldKeyTyped(evt);
				}
			});
		}
		return searchDynamicTextField;
	}

	private JButton getSearchDynamicButton() {
		if (searchDynamicButton == null) {
			searchDynamicButton = new JButton();
			searchDynamicButton.setText("Search");
			searchDynamicButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					searchDynamicButtonActionPerformed(evt);
				}
			});
		}
		return searchDynamicButton;
	}

	private void searchDynamicButtonActionPerformed(ActionEvent evt) {
		if (searchDynamicTextField.getText().length() > 0) {
			Highlighter h = searchDynamicEditorPane.getHighlighter();
			h.removeAllHighlights();
			String text = searchDynamicEditorPane.getText().toLowerCase();

			int nextPosition = -1;

			for (int j = 0; j < text.length() - searchDynamicTextField.getText().length() + 1; j += 1) {
				if (text.substring(j, j + searchDynamicTextField.getText().length()).equals(searchDynamicTextField.getText().toLowerCase())) {
					try {
						if (j >= searchDynamicEditorPane.getCaretPosition() && nextPosition == -1) {
							h.addHighlight(j, j + searchDynamicTextField.getText().length(), new DefaultHighlighter.DefaultHighlightPainter(Color.red));
							nextPosition = j + searchDynamicTextField.getText().length();
						} else {
							h.addHighlight(j, j + searchDynamicTextField.getText().length(), new DefaultHighlighter.DefaultHighlightPainter(Color.yellow));
						}
					} catch (BadLocationException ble) {
					}
				}
			}
			if (nextPosition != -1) {
				searchDynamicEditorPane.setCaretPosition(nextPosition);
			} else {
				searchDynamicEditorPane.setCaretPosition(0);
			}
		}
	}

	private JScrollPane getJScrollPane19() {
		if (jScrollPane19 == null) {
			jScrollPane19 = new JScrollPane();
			jScrollPane19.setPreferredSize(new java.awt.Dimension(993, 533));
			jScrollPane19.setViewportView(getSearchDynamicEditorPane());
		}
		return jScrollPane19;
	}

	private JEditorPane getSearchDynamicEditorPane() {
		if (searchDynamicEditorPane == null) {
			searchDynamicEditorPane = new JEditorPane();
		}
		return searchDynamicEditorPane;
	}

	private void searchRelPltTextFieldKeyTyped(KeyEvent evt) {
		if (evt.getKeyChar() == '\n') {
			searchRelPltButtonActionPerformed(null);
		}
	}

	private void searchDynamicTextFieldKeyTyped(KeyEvent evt) {
		if (evt.getKeyChar() == '\n') {
			searchDynamicButtonActionPerformed(null);
		}
	}

	private JPanel getOSDebugStandardPanel() {
		if (osDebugStandardPanel == null) {
			osDebugStandardPanel = new JPanel();
			CardLayout jOSDebugStandardPanelLayout = new CardLayout();
			osDebugStandardPanel.setLayout(jOSDebugStandardPanelLayout);
			osDebugStandardPanel.add(getOSDebugInfoErrorLabel(), "OS debug error label");
			osDebugStandardPanel.add(getJOSDebugInformationPanel1(), "jOSDebugInformationPanel1");
		}
		return osDebugStandardPanel;
	}

	private JTabbedPane getJTabbedPane5() {
		if (tabbedPane5 == null) {
			tabbedPane5 = new JTabbedPane();
		}
		return tabbedPane5;
	}

	private JLabel getOSDebugInfoErrorLabel() {
		if (osDebugInfoErrorLabel == null) {
			osDebugInfoErrorLabel = new JLabel();
			if (Global.osDebug.compareTo(BigInteger.valueOf(-1)) == 0) {
				osDebugInfoErrorLabel.setText("Parameter -osdebug is not specified.");
			} else {
				osDebugInfoErrorLabel.setText("OS debug information not found - wrong magic bytes.");
			}
			osDebugInfoErrorLabel.setHorizontalAlignment(SwingConstants.CENTER);
			osDebugInfoErrorLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			osDebugInfoErrorLabel.setFont(new java.awt.Font(osDebugInfoErrorLabel.getFont().getFamily(), 0, 20));
			osDebugInfoErrorLabel.setForeground(Color.white);
			osDebugInfoErrorLabel.setBackground(new Color(0, 0, 0, 180));
			osDebugInfoErrorLabel.setOpaque(true);
		}
		return osDebugInfoErrorLabel;
	}

	private JOSDebugInformationPanel getJOSDebugInformationPanel1() {
		if (osDebugInformationPanel1 == null) {
			osDebugInformationPanel1 = new JOSDebugInformationPanel();
		}
		return osDebugInformationPanel1;
	}

	private InstrumentPanel getJInstrumentPanel() {
		if (instrumentPanel == null) {
			instrumentPanel = new InstrumentPanel();
		}
		return instrumentPanel;
	}

	private JToggleButton getProfilerToggleButton() {
		if (profilerToggleButton == null) {
			profilerToggleButton = new JToggleButton();
			profilerToggleButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/chart_organisation.png")));
			profilerToggleButton.setText(MyLanguage.getString("Profile_and_Sampling"));
			getButtonGroup4().add(profilerToggleButton);
			profilerToggleButton.setToolTipText("Profile & Sampling");
			profilerToggleButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					profilerToggleButtonActionPerformed(evt);
				}
			});
		}
		return profilerToggleButton;
	}

	private void profilerToggleButtonActionPerformed(ActionEvent evt) {
		CardLayout cl = (CardLayout) (mainPanel.getLayout());
		if (profilerToggleButton.isSelected()) {
			cl.show(mainPanel, "instrumentPanel");
			currentPanel = "instrumentPanel";
		} else {
			cl.show(mainPanel, "jMaximizableTabbedPane_BasePanel1");
			currentPanel = "jMaximizableTabbedPane_BasePanel1";
		}
	}

	private LogPanel getLogPanel() {
		if (logPanel == null) {
			logPanel = new LogPanel();
		}
		return logPanel;
	}

	private JToggleButton getRegisterToggleButton() {
		if (registerToggleButton == null) {
			registerToggleButton = new JToggleButton();
			registerToggleButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/chart_bar.png")));
			getButtonGroup4().add(registerToggleButton);
			registerToggleButton.setSelected(true);
			registerToggleButton.setText(MyLanguage.getString("Register"));
			registerToggleButton.setToolTipText("View all registers");
			registerToggleButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jRegisterToggleButtonActionPerformed(evt);
				}
			});
		}
		return registerToggleButton;
	}

	private void jRegisterToggleButtonActionPerformed(ActionEvent evt) {
		final CardLayout cl = (CardLayout) (mainPanel.getLayout());
		if (registerToggleButton.isSelected()) {
			registerPanelScrollPane.setViewportView(registerPanel);
			cl.show(mainPanel, "jMaximizableTabbedPane_BasePanel1");
			currentPanel = "jMaximizableTabbedPane_BasePanel1";
		}
		// else {
		// cl.show(jMainPanel, "jMaximizableTabbedPane_BasePanel1");
		// currentPanel = "jMaximizableTabbedPane_BasePanel1";
		// SwingUtilities.invokeLater(new Runnable() {
		// public void run() {
		// if
		// (jRunBochsButton.getText().equals(MyLanguage.getString("pause")))
		// {
		// cl.show(jMainPanel, "Running Label");
		// }
		// }
		// });
		// }
	}

	private JToggleButton getJOSLogToggleButton() {
		if (osLogToggleButton == null) {
			osLogToggleButton = new JToggleButton();
			osLogToggleButton.setText("os.log");
			osLogToggleButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/page_red.png")));
			osLogToggleButton.setToolTipText("os.log");
			getButtonGroup4().add(osLogToggleButton);
			osLogToggleButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jOSLogToggleButtonActionPerformed(evt);
				}
			});
		}
		return osLogToggleButton;
	}

	private void jOSLogToggleButtonActionPerformed(ActionEvent evt) {
		CardLayout cl = (CardLayout) (mainPanel.getLayout());
		if (osLogToggleButton.isSelected()) {
			cl.show(mainPanel, "osLogPanel");
			currentPanel = "osLogPanel1";
		} else {
			cl.show(mainPanel, "jMaximizableTabbedPane_BasePanel1");
			currentPanel = "jMaximizableTabbedPane_BasePanel1";
		}
	}

	private EnhancedTextArea getOsLogPanel() {
		if (osLogPanel == null) {
			osLogPanel = new EnhancedTextArea();
			osLogPanel.addTrailListener(new File("."), new File("os.log"));
		}
		return osLogPanel;
	}

	private JMenuItem getHelpRequestMenuItem() {
		if (helpRequestMenuItem == null) {
			helpRequestMenuItem = new JMenuItem();
			helpRequestMenuItem.setText("Help request");
			helpRequestMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					helpRequestMenuItemActionPerformed(evt);
				}
			});
		}
		return helpRequestMenuItem;
	}

	private void helpRequestMenuItemActionPerformed(ActionEvent evt) {
		HelpRequestDialog helpRequestDialog = new HelpRequestDialog(this);
		CommonLib.centerDialog(helpRequestDialog);
		helpRequestDialog.setVisible(true);
	}

	private JPanel getJPanel30() {
		if (jPanel30 == null) {
			jPanel30 = new JPanel();
			BorderLayout jPanel30Layout = new BorderLayout();
			jPanel30.setLayout(jPanel30Layout);
			jPanel30.add(getAddressTranslateTable(), BorderLayout.CENTER);
			jPanel30.add(getJTabbedPane5(), BorderLayout.WEST);
		}
		return jPanel30;
	}

	private JMenuItem getStep10MenuItem() {
		if (step10MenuItem == null) {
			step10MenuItem = new JMenuItem();
			step10MenuItem.setText("Step 10 Instructions");
		}
		return step10MenuItem;
	}

	private JMenuItem getStep100MenuItem() {
		if (step100MenuItem == null) {
			step100MenuItem = new JMenuItem();
			step100MenuItem.setText("Step 100 Instructions");
		}
		return step100MenuItem;
	}

	private JMenuItem getStepNMenuItem() {
		if (stepNMenuItem == null) {
			stepNMenuItem = new JMenuItem();
			stepNMenuItem.setText("Step N Instructions");
		}
		return stepNMenuItem;
	}

	private JMenuItem getStepUntilCallOrJumpMenuItem() {
		if (stepUntilCallOrJumpMenuItem == null) {
			stepUntilCallOrJumpMenuItem = new JMenuItem();
			stepUntilCallOrJumpMenuItem.setText("Until call or jump");
		}
		return stepUntilCallOrJumpMenuItem;
	}

	private JMenuItem getStepUntilRetMenuItem() {
		if (stepUntilRetMenuItem == null) {
			stepUntilRetMenuItem = new JMenuItem();
			stepUntilRetMenuItem.setText("Until ret");
		}
		return stepUntilRetMenuItem;
	}

	private JMenuItem getStepUntilIRetMenuItem() {
		if (stepUntilIRetMenuItem == null) {
			stepUntilIRetMenuItem = new JMenuItem();
			stepUntilIRetMenuItem.setText("Until iret");
		}
		return stepUntilIRetMenuItem;
	}

	private JMenuItem getStepUntilMovMenuItem() {
		if (stepUntilMovMenuItem == null) {
			stepUntilMovMenuItem = new JMenuItem();
			stepUntilMovMenuItem.setText("Until mov");
		}
		return stepUntilMovMenuItem;
	}

	private JMenuItem getJVMMenuItem() {
		if (jvmMenuItem == null) {
			jvmMenuItem = new JMenuItem();
			jvmMenuItem.setText("JVM");
			jvmMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jvmMenuItemActionPerformed(evt);
				}
			});
		}
		return jvmMenuItem;
	}

	private void jvmMenuItemActionPerformed(ActionEvent evt) {
		new JVMInfoDialog(this).setVisible(true);
	}

	private JMenuItem getStepUntilIPBigChangeMenuItem() {
		if (stepUntilIPBigChangeMenuItem == null) {
			stepUntilIPBigChangeMenuItem = new JMenuItem();
			stepUntilIPBigChangeMenuItem.setText("Until IP big change");
		}
		return stepUntilIPBigChangeMenuItem;
	}

	private JPanel getRunningPanel() {
		if (runningPanel == null) {
			runningPanel = new JPanel();
			GroupLayout jRunningPanelLayout = new GroupLayout((JComponent) runningPanel);
			runningPanel.setLayout(jRunningPanelLayout);
			runningPanel.setPreferredSize(new java.awt.Dimension(1073, 758));
			jRunningPanelLayout.setHorizontalGroup(jRunningPanelLayout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
							jRunningPanelLayout
									.createParallelGroup()
									.addGroup(
											GroupLayout.Alignment.LEADING,
											jRunningPanelLayout.createSequentialGroup().addComponent(getPauseButton(), GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
													.addComponent(getMaximumRowLabel(), GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
													.addComponent(getMaxRowComboBox(), GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
													.addComponent(getClearRunningTextAreaButton(), GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(getStepCountLabel(), 0, 749, Short.MAX_VALUE).addGap(48))
									.addComponent(getJTextArea1(), GroupLayout.Alignment.LEADING, 0, 1116, Short.MAX_VALUE)
									.addGroup(
											GroupLayout.Alignment.LEADING,
											jRunningPanelLayout
													.createSequentialGroup()
													.addGap(65)
													.addComponent(getJCheckBox1(), GroupLayout.PREFERRED_SIZE, 335, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
													.addGroup(
															jRunningPanelLayout
																	.createParallelGroup()
																	.addGroup(
																			GroupLayout.Alignment.LEADING,
																			jRunningPanelLayout
																					.createSequentialGroup()
																					.addComponent(getAutoUpdateEvery20LinesCheckBox(), 0, 546, Short.MAX_VALUE)
																					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
																					.addComponent(getSaveToRunDotTxtCheckBox(), GroupLayout.PREFERRED_SIZE, 158,
																							GroupLayout.PREFERRED_SIZE))
																	.addGroup(
																			GroupLayout.Alignment.LEADING,
																			jRunningPanelLayout
																					.createSequentialGroup()
																					.addPreferredGap(getAutoUpdateEvery20LinesCheckBox(), getRunningLabel(),
																							LayoutStyle.ComponentPlacement.INDENT)
																					.addComponent(getRunningLabel(), GroupLayout.PREFERRED_SIZE, 679, GroupLayout.PREFERRED_SIZE)
																					.addGap(0, 25, Short.MAX_VALUE))))));
			jRunningPanelLayout.setVerticalGroup(jRunningPanelLayout
					.createSequentialGroup()
					.addComponent(getRunningLabel(), GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
							jRunningPanelLayout
									.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(getPauseButton(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(getMaximumRowLabel(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
									.addComponent(getMaxRowComboBox(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
									.addComponent(getClearRunningTextAreaButton(), GroupLayout.Alignment.BASELINE, 0, 23, Short.MAX_VALUE)
									.addComponent(getStepCountLabel(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
							jRunningPanelLayout
									.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(getAutoUpdateEvery20LinesCheckBox(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addComponent(getSaveToRunDotTxtCheckBox(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(getJCheckBox1(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(getJTextArea1(), 0, 610, Short.MAX_VALUE).addContainerGap(17, 17));
		}
		return runningPanel;
	}

	private JLabel getRunningLabel() {
		if (runningLabel == null) {
			runningLabel = new JLabel();
			URL url = getClass().getClassLoader().getResource("com/gkd/images/ajax-loader_red.gif");
			if (Setting.getInstance().currentLanguage.equals("zh_TW")) {
				runningLabel
						.setText("<html><center>Bochs is running, click the pause button to pause it !!!<br><br><img src=\""
								+ url
								+ "\" /><br><br><a style=\"color: #000000;  text-decoration:none\" href=\"http://www.kingofcoders.com\">?????????????????????????????????????????????????????????????????????????????????????????????www.kingofcoders.com</a></center></html>");
			} else if (Setting.getInstance().currentLanguage.equals("zh_CN")) {
				runningLabel
						.setText("<html><center>Bochs is running, click the pause button to pause it !!!<br><br><img src=\""
								+ url
								+ "\" /><br><br><img src=\"http://www.kingofcoders.com/images/KOC_logo2.jpg\" /><br><a style=\"color: #000000;  text-decoration:none\" href=\"http://www.kingofcoders.com\">????????????????????????????????????????????????????????????????????????????????????www.kingofcoders.com</a></center></html>");
			} else {
				runningLabel.setText("<html><center>Bochs is running, click the pause button to pause it !!!<br><br><img src=\"" + url + "\" /></center></html>");
			}
			runningLabel.setHorizontalAlignment(SwingConstants.CENTER);
			runningLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			runningLabel.setFont(new java.awt.Font(runningLabel.getFont().getFamily(), 0, 20));
		}
		return runningLabel;
	}

	private EnhancedTextArea getJTextArea1() {
		if (jTextArea1 == null) {
			jTextArea1 = new EnhancedTextArea();
			jTextArea1.setBorder(new LineBorder(new java.awt.Color(0xef, 0xef, 0xef), 1, false));
		}
		return jTextArea1;
	}

	private JButton getPauseButton() {
		if (pauseButton == null) {
			pauseButton = new JButton();
			pauseButton.setText("Pause");
			pauseButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					pauseButtonActionPerformed(evt);
				}
			});
		}
		return pauseButton;
	}

	private void pauseButtonActionPerformed(ActionEvent evt) {
		if (untilThread != null) {
			untilThread.shouldStop = true;
		}
	}

	private JLabel getStepCountLabel() {
		if (stepCountLabel == null) {
			stepCountLabel = new JLabel();
			stepCountLabel.setForeground(new java.awt.Color(222, 0, 5));
			stepCountLabel.setFont(new java.awt.Font(stepCountLabel.getFont().getFamily(), 0, 14));
		}
		return stepCountLabel;
	}

	private JCheckBox getJCheckBox1() {
		if (jDisableAutoUpdateCheckBox == null) {
			jDisableAutoUpdateCheckBox = new JCheckBox();
			jDisableAutoUpdateCheckBox.setText("Disable auto update, so bochs runs faster");
		}
		return jDisableAutoUpdateCheckBox;
	}

	private JCheckBox getAutoUpdateEvery20LinesCheckBox() {
		if (autoUpdateEvery20LinesCheckBox == null) {
			autoUpdateEvery20LinesCheckBox = new JCheckBox();
			autoUpdateEvery20LinesCheckBox.setSelected(true);
			autoUpdateEvery20LinesCheckBox.setText("Update the following instruction box every 20 lines, this make bochs runs faster");
		}
		return autoUpdateEvery20LinesCheckBox;
	}

	private JCheckBox getSaveToRunDotTxtCheckBox() {
		if (saveToRunDotTxtCheckBox == null) {
			saveToRunDotTxtCheckBox = new JCheckBox();
			saveToRunDotTxtCheckBox.setText("Append to run.txt");
			saveToRunDotTxtCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					saveToRunDotTxtCheckBoxActionPerformed(evt);
				}
			});
		}
		return saveToRunDotTxtCheckBox;
	}

	private void saveToRunDotTxtCheckBoxActionPerformed(ActionEvent evt) {
		saveToRunDotTxt = saveToRunDotTxtCheckBox.isSelected();
	}

	private JLabel getMaximumRowLabel() {
		if (maximumRowLabel == null) {
			maximumRowLabel = new JLabel();
			maximumRowLabel.setText("Maximum row");
		}
		return maximumRowLabel;
	}

	private JComboBox<String> getMaxRowComboBox() {
		if (maxRowComboBox == null) {
			ComboBoxModel<String> maxRowComboBoxModel = new DefaultComboBoxModel<String>(new String[] { "infinite", "10", "100", "200", "500", "1000", "2000" });
			maxRowComboBox = new JComboBox<String>();
			maxRowComboBox.setModel(maxRowComboBoxModel);
			maxRowComboBox.setSelectedItem("100");
		}
		return maxRowComboBox;
	}

	private void instructionComboBoxActionPerformed(ActionEvent evt) {
		disassembleButtonActionPerformed(evt);
	}

	private JButton getNextMemoryPageButton() {
		if (nextMemoryPageButton == null) {
			nextMemoryPageButton = new JButton();
			nextMemoryPageButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/resultset_next_grey.png")));
			nextMemoryPageButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					nextMemoryPageButtonActionPerformed(evt);
				}
			});
		}
		return nextMemoryPageButton;
	}

	private JButton getPreviousMemoryButton() {
		if (previousMemoryButton == null) {
			previousMemoryButton = new JButton();
			previousMemoryButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/resultset_previous_grey.png")));
			previousMemoryButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					previousMemoryButtonActionPerformed(evt);
				}
			});
		}
		return previousMemoryButton;
	}

	private void previousMemoryButtonActionPerformed(ActionEvent evt) {
		try {
			long address = CommonLib.string2long(memoryAddressComboBox.getSelectedItem().toString());
			if (address >= 0xc8) {
				memoryAddressComboBox.setSelectedItem("0x" + Long.toHexString(address - 0xc8));
			} else {
				memoryAddressComboBox.setSelectedItem("0x0");
			}
			goMemoryButtonActionPerformed(null);
		} catch (Exception ex) {
			if (Global.debug) {
				ex.printStackTrace();
			}
		}
	}

	private void nextMemoryPageButtonActionPerformed(ActionEvent evt) {
		try {
			long address = CommonLib.string2long(memoryAddressComboBox.getSelectedItem().toString());
			memoryAddressComboBox.setSelectedItem("0x" + Long.toHexString(address + 0xc8));
			goMemoryButtonActionPerformed(null);
		} catch (Exception ex) {
			if (Global.debug) {
				ex.printStackTrace();
			}
		}
	}

	private JRadioButton getFPURadioButton() {
		if (fpuRadioButton == null) {
			fpuRadioButton = new JRadioButton();
			fpuRadioButton.setText("fpu");
			fpuRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					fpuRadioButtonActionPerformed(evt);
				}
			});
			getButtonGroup2().add(fpuRadioButton);
		}
		return fpuRadioButton;
	}

	private JRadioButton getMMXRadioButton() {
		if (mmxRadioButton == null) {
			mmxRadioButton = new JRadioButton();
			mmxRadioButton.setText("mmx");
			mmxRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					mmxRadioButtonActionPerformed(evt);
				}
			});
			getButtonGroup2().add(mmxRadioButton);
		}
		return mmxRadioButton;
	}

	private void mmxRadioButtonActionPerformed(ActionEvent evt) {
		HistoryTableModel model = (HistoryTableModel) this.historyTable.getModel();
		model.setView("mmx");
		for (int x = 0; x < model.getColumnCount(); x++) {
			historyTable.getColumnModel().getColumn(x).setPreferredWidth(200);
		}
	}

	private void fpuRadioButtonActionPerformed(ActionEvent evt) {
		HistoryTableModel model = (HistoryTableModel) this.historyTable.getModel();
		model.setView("fpu");
		for (int x = 1; x < model.getColumnCount(); x++) {
			historyTable.getColumnModel().getColumn(x).setPreferredWidth(200);
		}
		historyTable.getColumnModel().getColumn(9).setPreferredWidth(600);
		historyTable.getColumnModel().getColumn(10).setPreferredWidth(500);
	}

	private void jTabbedPane1StateChanged(ChangeEvent evt) {
		JTabbedPane pane = (JTabbedPane) evt.getSource();
		int sel = pane.getSelectedIndex();
		if (sel == 2) {
			vmCommandTextField.requestFocus();
		}
	}

	private JSeparator getJSeparator3() {
		if (jSeparator3 == null) {
			jSeparator3 = new JSeparator();
		}
		return jSeparator3;
	}

	private JMenuItem getDisasmHereMenuItem() {
		if (disasmHereMenuItem == null) {
			disasmHereMenuItem = new JMenuItem();
			disasmHereMenuItem.setText("Disasm from here");
			disasmHereMenuItem.setEnabled(false);
			disasmHereMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					disasmHereMenuItemActionPerformed(evt);
				}
			});
		}
		return disasmHereMenuItem;
	}

	private void disasmHereMenuItemActionPerformed(ActionEvent evt) {
		String str;
		if (Global.clickedWhichInstructionPanel == 0) {
			str = (String) instructionTable.getValueAt(instructionTable.getSelectedRow(), 1);
		} else {
			str = (String) sourceLevelDebugger.instructionTable.getValueAt(sourceLevelDebugger.instructionTable.getSelectedRow(), 1);
		}

		BigInteger address;
		if (str.startsWith("cCode")) {
			long l = CommonLib.string2long(str.split(":")[1]);
			address = BigInteger.valueOf(l);
		} else {
			long l = CommonLib.string2long(str.split(":")[0]);
			address = BigInteger.valueOf(l);
		}

		updateInstruction(address);
	}

	private SourceLevelDebugger3 getSourceLevelDebugger() {
		if (sourceLevelDebugger == null) {
			sourceLevelDebugger = new SourceLevelDebugger3(this);
		}
		return sourceLevelDebugger;
	}

	private JToggleButton getSourceLevelDebuggerButton() {
		if (sourceLevelDebuggerToggleButton == null) {
			sourceLevelDebuggerToggleButton = new JToggleButton();
			getButtonGroup4().add(sourceLevelDebuggerToggleButton);
			sourceLevelDebuggerToggleButton.setText("C/C++");
			sourceLevelDebuggerToggleButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/page_white_text.png")));
			sourceLevelDebuggerToggleButton.setEnabled(false);
			sourceLevelDebuggerToggleButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					sourceLevelDebuggerToggleButtonActionPerformed(evt);
				}
			});
		}
		return sourceLevelDebuggerToggleButton;
	}

	public void sourceLevelDebuggerToggleButtonActionPerformed(ActionEvent evt) {
		CardLayout cl = (CardLayout) (mainPanel.getLayout());
		if (sourceLevelDebuggerToggleButton.isSelected() || evt == null) {
			sourceLevelDebugger.registerPanelScrollPane.setViewportView(registerPanel);
			cl.show(mainPanel, "sourceLevelDebugger");
			currentPanel = "sourceLevelDebugger";
		} else {
			cl.show(mainPanel, "jMaximizableTabbedPane_BasePanel1");
			currentPanel = "jMaximizableTabbedPane_BasePanel1";
		}

		if (evt == null) {
			sourceLevelDebuggerToggleButton.setSelected(true);
		}
	}

	private JMenu getSystemMenu() {
		if (systemMenu == null) {
			systemMenu = new JMenu();
			systemMenu.setText(MyLanguage.getString("System"));
			systemMenu.add(getLoadSystemMapMenuItem());
		}
		return systemMenu;
	}

	private JMenuItem getLoadSystemMapMenuItem() {
		if (loadSystemMapMenuItem == null) {
			loadSystemMapMenuItem = new JMenuItem();
			loadSystemMapMenuItem.setText(MyLanguage.getString("Load_Elf"));
			loadSystemMapMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					loadSystemMapMenuItemActionPerformed(evt);
				}
			});
		}
		return loadSystemMapMenuItem;
	}

	private void loadSystemMapMenuItemActionPerformed(ActionEvent evt) {
		JFileChooser fc = new JFileChooser(new File("."));
		int returnVal = fc.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			sourceLevelDebugger.loadELF(file, null, 0);
		}
	}

	private JMenuItem getClearInstructionTableMenuItem() {
		if (clearInstructionTableMenuItem == null) {
			clearInstructionTableMenuItem = new JMenuItem();
			clearInstructionTableMenuItem.setText("Clear");
			clearInstructionTableMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					clearInstructionTableMenuItemActionPerformed(evt);
				}
			});
		}
		return clearInstructionTableMenuItem;
	}

	private void clearInstructionTableMenuItemActionPerformed(ActionEvent evt) {
		InstructionTableModel model = (InstructionTableModel) instructionTable.getModel();
		model.clearData();
	}

	private ButtonGroup getButtonGroup4() {
		if (buttonGroup4 == null) {
			buttonGroup4 = new ButtonGroup();
		}
		return buttonGroup4;
	}

	private JButton getClearHistoryTableButton() {
		if (clearHistoryTableButton == null) {
			clearHistoryTableButton = new JButton();
			clearHistoryTableButton.setText("Clear");
			clearHistoryTableButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					clearHistoryTableButtonActionPerformed(evt);
				}
			});
		}
		return clearHistoryTableButton;
	}

	private void clearHistoryTableButtonActionPerformed(ActionEvent evt) {
		((HistoryTableModel) this.historyTable.getModel()).clear();
	}

	private JButton getClearRunningTextAreaButton() {
		if (clearRunningTextAreaButton == null) {
			clearRunningTextAreaButton = new JButton();
			clearRunningTextAreaButton.setText("Clear");
			clearRunningTextAreaButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					clearRunningTextAreaButtonActionPerformed(evt);
				}
			});
		}
		return clearRunningTextAreaButton;
	}

	private void clearRunningTextAreaButtonActionPerformed(ActionEvent evt) {
		jTextArea1.setText("");
	}

	private JLabel getHistoryTableRepeatedLabel() {
		if (historyTableRepeatedLabel == null) {
			historyTableRepeatedLabel = new JLabel();
		}
		return historyTableRepeatedLabel;
	}

	private void historyTableMouseClicked(MouseEvent evt) {
		try {
			String instruction = (String) historyTable.getValueAt(historyTable.getSelectedRow(), 2);
			instruction = instruction.replaceAll("^.*] [0-9]", "").split(":")[2].trim().replaceAll(" .*", "");
			int count = 0;
			for (int x = 0; x <= historyTable.getSelectedRow(); x++) {
				try {
					String i = (String) historyTable.getValueAt(x, 2);
					i = i.replaceAll("^.*]", "").split(":")[2].trim().replaceAll(" .*", "");
					if (instruction.equals(i)) {
						count++;
					}
				} catch (Exception ex) {
				}
			}
			historyTableRepeatedLabel.setText(" " + instruction + " happened " + count + " times ");
		} catch (Exception ex) {
			historyTableRepeatedLabel.setText("");
		}
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setOpaque(true);
			jLabel2.setBorder(new LineBorder(Color.LIGHT_GRAY));
			jLabel2.setBackground(new java.awt.Color(0, 0, 0));
			jLabel2.setMinimumSize(new java.awt.Dimension(2, 15));
			jLabel2.setMaximumSize(new java.awt.Dimension(2, 15));
			jLabel2.setPreferredSize(new java.awt.Dimension(2, 15));
		}
		return jLabel2;
	}

	private JTextField getFilterHistoryTableTextField() {
		if (filterHistoryTableTextField == null) {
			filterHistoryTableTextField = new JSearchTextField();
			filterHistoryTableTextField.setMaximumSize(new java.awt.Dimension(158, 26));
			filterHistoryTableTextField.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent evt) {
					filterHistoryTableTextFieldKeyReleased(evt);
				}
			});
		}
		return filterHistoryTableTextField;
	}

	private void filterHistoryTableTextFieldKeyReleased(KeyEvent evt) {
		MyTableRowSorter<TableModel> sorter = (MyTableRowSorter<TableModel>) historyTable.getRowSorter();
		sorter.showAfterwardCount = (Integer) showAfterwardSpinner.getValue();
		sorter.setRowFilter(RowFilter.regexFilter(filterHistoryTableTextField.getText()));
		// ((MyTableRowSorter<TableModel>)
		// jHistoryTable.getRowSorter()).setRowFilter(genRegexFilter(jFilterHistoryTableTextField.getText()));
	}

	private JMenuItem getRunBochsAndSkipBreakpointMenuItem() {
		if (runBochsAndSkipBreakpointMenuItem == null) {
			runBochsAndSkipBreakpointMenuItem = new JMenuItem();
			runBochsAndSkipBreakpointMenuItem.setText("Run and skip breakpoint for N times");
		}
		return runBochsAndSkipBreakpointMenuItem;
	}

	private JSpinner getShowAfterwardSpinner() {
		if (showAfterwardSpinner == null) {
			SpinnerNumberModel jShowAfterwardSpinnerModel = new SpinnerNumberModel(0, 0, 100, 1);
			showAfterwardSpinner = new JSpinner();
			showAfterwardSpinner.setMaximumSize(new java.awt.Dimension(50, 26));
			showAfterwardSpinner.setModel(jShowAfterwardSpinnerModel);
			showAfterwardSpinner.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent evt) {
					showAfterwardSpinnerStateChanged(evt);
				}
			});
		}
		return showAfterwardSpinner;
	}

	private JLabel getJLabel7() {
		if (jLabel7 == null) {
			jLabel7 = new JLabel();
			jLabel7.setText(" Show afterward");
		}
		return jLabel7;
	}

	private JLabel getJLabel8() {
		if (jLabel8 == null) {
			jLabel8 = new JLabel();
			jLabel8.setBackground(new java.awt.Color(0, 0, 0));
			jLabel8.setMinimumSize(new java.awt.Dimension(2, 15));
			jLabel8.setPreferredSize(new java.awt.Dimension(2, 15));
			jLabel8.setMaximumSize(new java.awt.Dimension(2, 15));
			jLabel8.setOpaque(true);
			jLabel8.setBorder(new LineBorder(Color.LIGHT_GRAY));
		}
		return jLabel8;
	}

	private JLabel getJLabel9() {
		if (jLabel9 == null) {
			jLabel9 = new JLabel();
			jLabel9.setText(" ");
		}
		return jLabel9;
	}

	private void showAfterwardSpinnerStateChanged(ChangeEvent evt) {
		filterHistoryTableTextFieldKeyReleased(null);
	}

	private JDropDownButton getSBButton() {
		if (sbButton == null) {
			sbButton = new JDropDownButton();
			sbButton.setText("SB");
			sbButton.setPreferredSize(new java.awt.Dimension(40, 25));
			loadSBButton();
			sbButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					sbButtonActionPerformed(evt);
				}
			});
		}
		return sbButton;
	}

	void loadSBButton() {
		sbButton.removeAll();
		Object[] a = Setting.getInstance().sbAddress.toArray();
		for (int x = a.length - 1; x >= 0; x--) {
			JMenuItem menu = new JMenuItem();
			menu.setText(String.valueOf(a[x]));
			sbButton.add(menu);
		}
	}

	private JDropDownButton getSBAButton() {
		if (sbaButton == null) {
			sbaButton = new JDropDownButton();
			sbaButton.setText("SBA");
			loadSBAButton();
			sbaButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					sbaButtonActionPerformed(evt);
				}
			});
		}
		return sbaButton;
	}

	void loadSBAButton() {
		sbaButton.removeAll();
		Object[] a = Setting.getInstance().sbaAddress.toArray();
		for (int x = a.length - 1; x >= 0; x--) {
			JMenuItem menu = new JMenuItem();
			menu.setText(String.valueOf(a[x]));
			sbaButton.add(menu);
		}
	}

	private void sbButtonActionPerformed(ActionEvent evt) {
		//		if (jSBButton.getEventSource() != null) {
		//			long l = Long.parseLong(((JMenuItem) jSBButton.getEventSource()).getText());
		//			sendBochsCommand("sb " + l);
		//			Setting.getInstance().sbAddress.add(l);
		//		} else {
		//			String s = JOptionPane.showInputDialog(this, "Please input cycle interval for next stop?");
		//			if (s == null) {
		//				return;
		//			}
		//			try {
		//				long l = Long.parseLong(s);
		//				sendBochsCommand("sb " + l);
		//				Setting.getInstance().sbAddress.add(l);
		//			} catch (Exception ex) {
		//				if (Global.debug) {
		//					ex.printStackTrace();
		//				}
		//			}
		//		}
		//
		//		for (int x = 0; x < Setting.getInstance().sbAddress.size() - 10; x++) {
		//			Setting.getInstance().sbAddress.remove(Setting.getInstance().sbAddress.toArray()[x]);
		//		}
		//		Setting.getInstance().save();
		//		loadSBButton();
	}

	private void sbaButtonActionPerformed(ActionEvent evt) {
		//TODO sba
		//		if (jSBAButton.getEventSource() != null) {
		//			long l = Long.parseLong(((JMenuItem) jSBAButton.getEventSource()).getText());
		//			sendBochsCommand("sba " + l);
		//			Setting.getInstance().sbaAddress.add(l);
		//		} else {
		//			String s = JOptionPane.showInputDialog(this, "Please input cycle interval for next stop?");
		//			if (s == null) {
		//				return;
		//			}
		//			try {
		//				long l = Long.parseLong(s);
		//				sendBochsCommand("sba " + l);
		//				Setting.getInstance().sbaAddress.add(l);
		//			} catch (Exception ex) {
		//				if (Global.debug) {
		//					ex.printStackTrace();
		//				}
		//			}
		//		}

		//		for (int x = 0; x < Setting.getInstance().sbaAddress.size() - 10; x++) {
		//			Setting.getInstance().sbaAddress.remove(Setting.getInstance().sbaAddress.toArray()[x]);
		//		}
		//		Setting.getInstance().save();
		//		loadSBAButton();
	}

	private JMenuItem getShortcutHelpMenuItem() {
		if (shortcutHelpMenuItem == null) {
			shortcutHelpMenuItem = new JMenuItem();
			shortcutHelpMenuItem.setText("Shortcut");
			shortcutHelpMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					shortcutHelpMenuItemActionPerformed(evt);
				}
			});
		}
		return shortcutHelpMenuItem;
	}

	private void shortcutHelpMenuItemActionPerformed(ActionEvent evt) {
		String s = "F1 : Show memory\n" + "F2 : Show GDT\n" + "F3 : Show IDT\n" + "F4 : Show LDT\n" + "F5 : Start bochs\n" + "F6 : Stop bochs\n" + "F7 : Run/Pause bochs\n"
				+ "F8 : Step\n" + "F9 : Fast step\n";
		JOptionPane.showMessageDialog(this, s);
	}

	private JPanel getJPanel31() {
		if (jPanel31 == null) {
			jPanel31 = new JPanel();
			BorderLayout jPanel31Layout = new BorderLayout();
			jPanel31.setLayout(jPanel31Layout);
			jPanel31.add(getBochsoutTextArea(), BorderLayout.CENTER);
		}
		return jPanel31;
	}

	private EnhancedTextArea getBochsoutTextArea() {
		if (bochsoutTextArea == null) {
			bochsoutTextArea = new EnhancedTextArea();
			getBochsoutTextArea().toolBar.add(getHelpButton(), -1);
			getBochsoutTextArea().hideFontComboBox();
		}
		return bochsoutTextArea;
	}

	public String tail2(File file, int lines) {
		try {
			RandomAccessFile fileHandler = new RandomAccessFile(file, "r");
			long fileLength = file.length() - 1;
			StringBuilder sb = new StringBuilder();
			int line = 0;

			for (long filePointer = fileLength; filePointer != -1; filePointer--) {
				fileHandler.seek(filePointer);
				int readByte = fileHandler.readByte();

				if (readByte == 0xA) {
					line = line + 1;
					if (line == lines) {
						if (filePointer == fileLength) {
							continue;
						} else {
							break;
						}
					}
				}
				sb.append((char) readByte);
			}
			fileHandler.close();

			sb.deleteCharAt(sb.length() - 1);
			String lastLine = sb.reverse().toString();
			return lastLine;
		} catch (Exception e) {
			return null;
		}
	}

	private JButton getHelpButton() {
		if (helpButton == null) {
			helpButton = new JButton();
			helpButton.setText("Help");
			helpButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					helpButtonActionPerformed(evt);
				}
			});
		}
		return helpButton;
	}

	private void helpButtonActionPerformed(ActionEvent evt) {
		JOptionPane.showMessageDialog(this, "To enable bochsout.txt, add \"log: bochsout.txt\" to your bochsrc.bxrc");
	}

	private JMenuItem getRunCustomCommandMenuItem() {
		if (runCustomCommandMenuItem == null) {
			runCustomCommandMenuItem = new JMenuItem();
			runCustomCommandMenuItem.setText("Run custom commands");
		}
		return runCustomCommandMenuItem;
	}

	private JDropDownButton getStepOverDropDownButton() {
		if (stepOverDropDownButton == null) {
			stepOverDropDownButton = new JDropDownButton();
			stepOverDropDownButton.setText(MyLanguage.getString("Step_over"));
			stepOverDropDownButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/step_over.png")));
			stepOverDropDownButton.setMaximumSize(new java.awt.Dimension(115, 26));
			stepOverDropDownButton.add(getStepOver10MenuItem());
			stepOverDropDownButton.add(getStepOver100MenuItem());
			stepOverDropDownButton.add(getStepOverNTimesMenuItem());
			stepOverDropDownButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					stepOverDropDownButtonActionPerformed(evt);
				}
			});
		}
		return stepOverDropDownButton;
	}

	private void stepOverDropDownButtonActionPerformed(ActionEvent evt) {
		if (stepOverDropDownButton.getEventSource() != null) {
			untilThread = new StepThread(stepOverDropDownButton.getEventSource());
			if (stepOverDropDownButton.getEventSource() == stepOverNTimesMenuItem) {
				String s = JOptionPane.showInputDialog("Please input the instruction count?");
				if (s == null) {
					return;
				}
				untilThread.instructionCount = Integer.parseInt(s);
			}

			// if (currentPanel.equals("jMaximizableTabbedPane_BasePanel1")) {
			CardLayout cl = (CardLayout) (mainPanel.getLayout());
			cl.show(mainPanel, "Running Panel");
			// }
			new Thread(untilThread, "Step until thread").start();
		} else {
			VMController.getVM().stepOver();
			WebServiceUtil.log("gkd", "step over", null, null, null);
			updateVMStatus(true);
			// updateHistoryTable(re);
		}
	}

	private JMenuItem getStepOver10MenuItem() {
		if (stepOver10MenuItem == null) {
			stepOver10MenuItem = new JMenuItem();
			stepOver10MenuItem.setText(MyLanguage.getString("Step_over_10_times"));
		}
		return stepOver10MenuItem;
	}

	private JMenuItem getStepOver100MenuItem() {
		if (stepOver100MenuItem == null) {
			stepOver100MenuItem = new JMenuItem();
			stepOver100MenuItem.setText(MyLanguage.getString("Step_over_100_times"));
		}
		return stepOver100MenuItem;
	}

	private JMenuItem getStepOverNTimesMenuItem() {
		if (stepOverNTimesMenuItem == null) {
			stepOverNTimesMenuItem = new JMenuItem();
			stepOverNTimesMenuItem.setText(MyLanguage.getString("Step_over_N_times"));
		}
		return stepOverNTimesMenuItem;
	}

	private JMenuItem getLicenseMenuItem() {
		if (licenseMenuItem == null) {
			licenseMenuItem = new JMenuItem();
			licenseMenuItem.setText("License");
			licenseMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					licenseMenuItemActionPerformed(evt);
				}
			});
		}
		return licenseMenuItem;
	}

	private void licenseMenuItemActionPerformed(ActionEvent evt) {
		new LicenseDialog(this).setVisible(true);
	}

	private void nextButtonActionPerformed(ActionEvent evt) {
		BigInteger currentIP = CommonLib.string2BigInteger(registerPanel.eipTextField.getText());
		String nextCCode = null;
		boolean bingo = false;
		BigInteger addr = null;
		for (int x = 0; x < instructionTable.getRowCount() - 1; x++) {
			String addressColumn = (String) instructionTable.getValueAt(x, 1);
			if (addressColumn.startsWith("cCode")) {
				addr = CommonLib.string2BigInteger(addressColumn.split(":")[1]);
			} else {
				addr = CommonLib.string2BigInteger(addressColumn);
			}
			if (bingo && addressColumn.startsWith("cCode") && !addr.equals(currentIP)) {
				nextCCode = addressColumn;
				break;
			}
			if (addr != null && addr.equals(currentIP)) {
				bingo = true;
			}
		}

		if (addr != null) {
			nexti(addr, false);
		}
	}

	private void nexti(BigInteger addr, boolean isStepOver) {
		// logger.debug("going to " + addr.toString(16));
		BigInteger currentEIP = getRealEIP();
		for (int x = 0; x < Global.MAX_NEXTI_INSTRUCTION_COUNT; x++) {
			String asmCode = getASMCode(currentEIP);
			if (isStepOver) {
				VMController.getVM().stepOver();
			} else {
				VMController.getVM().singleStep();
			}
			//			sendBochsCommand(command);
			updateRegister(true);
			waitUpdateFinish();
			currentEIP = getRealEIP();
			if (currentEIP.equals(addr)) {
				break;
			}

			if (asmCode != null && asmCode.contains("call")) {
				logger.debug("bingo la " + asmCode);
				break;
			}
		}

		// updateVMStatus(true);

		updateRegister(true);
		updateEFlags();
		updateMemory(true);
		updateInstruction(null);
		updateBreakpoint();
		updateBreakpointTableColor();
		updateHistoryTable();
		waitUpdateFinish();
		jumpToRowInstructionTable(getRealEIP());
	}

	private JButton getNextOverButton() {
		if (nextOverButton == null) {
			nextOverButton = new JButton();
			nextOverButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/gkd/icons/famfam_icons/step.png")));
			nextOverButton.setText("NextO");
			nextOverButton.setToolTipText("c/c++ level step-over");
			nextOverButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					nextOverButtonActionPerformed(evt);
				}
			});
		}
		return nextOverButton;
	}

	private void nextOverButtonActionPerformed(ActionEvent evt) {
		BigInteger currentIP = CommonLib.string2BigInteger(registerPanel.eipTextField.getText());
		String nextCCode = null;
		boolean bingo = false;
		BigInteger addr = null;
		for (int x = 0; x < instructionTable.getRowCount() - 1; x++) {
			String addressColumn = (String) instructionTable.getValueAt(x, 1);
			if (addressColumn.startsWith("cCode")) {
				addr = CommonLib.string2BigInteger(addressColumn.split(":")[1]);
			} else {
				addr = CommonLib.string2BigInteger(addressColumn);
			}
			if (bingo && addressColumn.startsWith("cCode") && !addr.equals(currentIP)) {
				nextCCode = addressColumn;
				break;
			}
			if (addr != null && addr.equals(currentIP)) {
				bingo = true;
			}
		}

		if (addr != null) {
			nexti(addr, true);
		}
	}

	private JMenuItem getDisasmFromEIPMinus100MenuItem() {
		if (disasmFromEIPMinus100MenuItem == null) {
			disasmFromEIPMinus100MenuItem = new JMenuItem();
			disasmFromEIPMinus100MenuItem.setText("Disasm from EIP-100");
			disasmFromEIPMinus100MenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					disasmFromEIPMinus100MenuItemActionPerformed(evt);
				}
			});
		}
		return disasmFromEIPMinus100MenuItem;
	}

	private void disasmFromEIPMinus100MenuItemActionPerformed(ActionEvent evt) {
		String str;
		if (Global.clickedWhichInstructionPanel == 0) {
			str = (String) instructionTable.getValueAt(instructionTable.getSelectedRow(), 1);
		} else {
			str = (String) sourceLevelDebugger.instructionTable.getValueAt(sourceLevelDebugger.instructionTable.getSelectedRow(), 1);
		}

		BigInteger address;
		if (str.startsWith("cCode")) {
			long l = CommonLib.string2long(str.split(":")[1]);
			address = BigInteger.valueOf(l);
		} else {
			long l = CommonLib.string2long(str.split(":")[0]);
			address = BigInteger.valueOf(l);
		}

		updateInstruction(address.subtract(BigInteger.valueOf(0x100)));
	}

	private JPanel getVncPanel() {
		if (vncPanel == null) {
			vncPanel = new JPanel();
		}
		return vncPanel;
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		handleQuit(null);
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {

	}

	@Override
	public void windowDeactivated(WindowEvent e) {

	}

	@Override
	public void handleAbout(ApplicationEvent event) {
		event.setHandled(true);
		new AboutUsDialog(null).setVisible(true);
	}

	@Override
	public void handleOpenApplication(ApplicationEvent arg0) {
	}

	@Override
	public void handleOpenFile(ApplicationEvent arg0) {
	}

	@Override
	public void handlePreferences(ApplicationEvent arg0) {
	}

	@Override
	public void handlePrintFile(ApplicationEvent arg0) {
	}

	@Override
	public void handleQuit(ApplicationEvent arg0) {
		Setting.getInstance().width = this.getWidth();
		Setting.getInstance().height = this.getHeight();
		Setting.getInstance().x = this.getLocation().x;
		Setting.getInstance().y = this.getLocation().y;
		Setting.getInstance().divX = jSplitPane1.getDividerLocation();
		Setting.getInstance().divY = jSplitPane2.getDividerLocation();
		Setting.getInstance().osDebugSplitPane_DividerLocation = this.osDebugInformationPanel1.getMainSplitPane().getDividerLocation();
		Setting.getInstance().save();

		VMController.getVM().stopVM();
		System.exit(0);
	}

	@Override
	public void handleReOpenApplication(ApplicationEvent arg0) {

	}

	@Override
	public void cancelled() {
		VMController.getVM().stopVM();
		System.exit(0);
	}

	private JButton getJumpToInstructionButton() {
		if (jumpToInstructionButton == null) {
			jumpToInstructionButton = new JButton("");
			jumpToInstructionButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					jumpToRowInstructionTable(getRealEIP());
				}
			});
			jumpToInstructionButton.setIcon(new ImageIcon(GKD.class.getResource("/com/gkd/icons/famfam_icons/eye.png")));
		}
		return jumpToInstructionButton;
	}
}
