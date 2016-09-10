package com.gkd;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.Vector;

import org.apache.commons.io.IOUtils;

import com.thoughtworks.xstream.XStream;

public class Setting {
	private static Setting setting = null;

	public LinkedHashSet<String> vmCommandHistory = new LinkedHashSet<String>();
	public TreeSet<String> memoryCombo = new TreeSet<String>();
	public LinkedList<Breakpoint> breakpoint = new LinkedList<Breakpoint>();

	public LinkedHashSet<Long> sbAddress = new LinkedHashSet<Long>();
	public LinkedHashSet<Long> sbaAddress = new LinkedHashSet<Long>();

	public String currentLanguage;
	public int fontsize;
	public String fontFamily;
	public int x;
	public int y;
	public int width;
	public int height;
	public int divX;
	public int divY;
	public int jmpSplitPanel_divY;

	public int osDebugSplitPane_DividerLocation;

	public LinkedList<Long> profileMemoryFromAddress = new LinkedList<Long>();
	public LinkedList<Long> profileMemoryToAddress = new LinkedList<Long>();

	public Vector<Long> physicalAddress = new Vector<Long>();

	public Vector<Boolean> tss = new Vector<Boolean>();
	public Vector<Boolean> memoryStart = new Vector<Boolean>();
	public Vector<Boolean> memoryEnd = new Vector<Boolean>();
	public Vector<Boolean> register = new Vector<Boolean>();
	public Vector<Boolean> gdt = new Vector<Boolean>();
	public Vector<Boolean> idt = new Vector<Boolean>();
	public Vector<Boolean> ldt = new Vector<Boolean>();

	public boolean logToPetersoftServer;

	public String path_objdump;
	public String path_dwarfdump;

	public boolean loadBreakpointAtStartup;
	public boolean updateAfterGKDCommand;
	public boolean updateAfterGKDCommand_register;
	public boolean updateAfterGKDCommand_memory;
	public boolean updateAfterGKDCommand_instruction;
	public boolean updateAfterGKDCommand_breakpoint;
	public boolean updateAfterGKDCommand_gdt;
	public boolean updateAfterGKDCommand_ldt;
	public boolean updateAfterGKDCommand_idt;
	public boolean updateAfterGKDCommand_pageTable;
	public boolean updateAfterGKDCommand_stack;
	public boolean updateAfterGKDCommand_addressTranslate;
	public boolean updateAfterGKDCommand_history;

	public boolean updateFastStepCommand_register;
	public boolean updateFastStepCommand_memory;
	public boolean updateFastStepCommand_instruction;
	public boolean updateFastStepCommand_breakpoint;
	public boolean updateFastStepCommand_gdt;
	public boolean updateFastStepCommand_ldt;
	public boolean updateFastStepCommand_idt;
	public boolean updateFastStepCommand_history;

	public boolean memoryProfiling;
	public boolean hitZone;
	public boolean customZone;
	public boolean jmpProfiling;
	public boolean interruptProfiling;
	public boolean profilingUpdateGraph;

	private String lastElfHistoryOpenDir = new File(".").getAbsolutePath();
	private String lastElfHistoryOpenDir2 = new File(".").getAbsolutePath();
	private String lastMapOpenDir = new File(".").getAbsolutePath();
	private String lastLoadElfOpenDir = new File(".").getAbsolutePath();

	public Vector<CustomPanelData> customPanelData;

	class DialogPosition {
		public int x;
		public int y;
		public int width;
		public int height;

		public DialogPosition(int x, int y, int width, int height) {
			super();
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
	}

	public HashMap<String, DialogPosition> dialogPositions = new HashMap<String, DialogPosition>();

	public Setting() {
		currentLanguage = "en_US";
		fontsize = 12;
		fontFamily = "Dialog";
		width = 800;
		height = 600;
		divX = 400;
		divY = 200;
		osDebugSplitPane_DividerLocation = 150;
		jmpSplitPanel_divY = 600;

		updateFastStepCommand_register = true;
		updateFastStepCommand_memory = true;
		updateFastStepCommand_instruction = true;
	}

	public static Setting getInstance() {
		if (setting == null) {
			setting = load();
		}
		return setting;
	}

	public void save() {
		XStream xstream = new XStream();
		xstream.alias("Setting", Setting.class);
		String xml = xstream.toXML(this);
		try {
			IOUtils.write(xml, new FileOutputStream(new File("gkd.xml")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Setting load() {
		try {
			XStream xstream = new XStream();
			xstream.alias("Setting", Setting.class);
			Setting setting = (Setting) xstream.fromXML(new FileInputStream(new File("gkd.xml")));
			if (setting.vmCommandHistory == null) {
				setting.vmCommandHistory = new LinkedHashSet<String>();
			}
			return setting;
		} catch (Exception ex) {
			new File("gkd.xml").delete();
			Setting Setting = new Setting();
			Setting.save();
			return Setting;
		}
	}

	public static void main(String args[]) {
		Setting setting = Setting.getInstance();
	}

	public String getLastElfHistoryOpenDir() {
		return lastElfHistoryOpenDir == null ? "." : lastElfHistoryOpenDir;
	}

	public String getLastElfHistoryOpenDir2() {
		return lastElfHistoryOpenDir2 == null ? "." : lastElfHistoryOpenDir2;
	}

	public String getLastMapOpenDir() {
		return lastMapOpenDir == null ? "." : lastMapOpenDir;
	}

	public String getLastLoadElfOpenDir() {
		return lastLoadElfOpenDir == null ? "." : lastLoadElfOpenDir;
	}

	public void setLastElfHistoryOpenDir(String lastElfHistoryOpenDir) {
		this.lastElfHistoryOpenDir = lastElfHistoryOpenDir;
	}

	public void setLastElfHistoryOpenDir2(String lastElfHistoryOpenDir2) {
		this.lastElfHistoryOpenDir2 = lastElfHistoryOpenDir2;
	}

	public void setLastMapOpenDir(String lastMapOpenDir) {
		this.lastMapOpenDir = lastMapOpenDir;
	}

	public void setLastLoadElfOpenDir(String lastLoadElfOpenDir) {
		this.lastLoadElfOpenDir = lastLoadElfOpenDir;
	}

	public void saveComponentPositionAndSize(String name, Component component) {
		if (dialogPositions == null) {
			dialogPositions = new HashMap<String, DialogPosition>();
		}
		dialogPositions.put(name, new DialogPosition(component.getX(), component.getY(), component.getWidth(), component.getHeight()));
		getInstance().save();
	}

	public void restoreComponentPositionAndSize(String name, Component component) {
		Setting setting = Setting.load();

		if (dialogPositions == null) {
			return;
		}
		DialogPosition p = setting.dialogPositions.get(name);
		if (p != null) {
			component.setLocation(p.x, p.y);
			component.setSize(p.width, p.height);
		}
	}
}
