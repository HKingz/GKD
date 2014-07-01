package com.gkd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
	public boolean loadSystemMapAtStartup;
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

	public String lastElfHistoryOpenDir = new File(".").getAbsolutePath();
	public String lastElfHistoryOpenDir2 = new File(".").getAbsolutePath();
	public String lastMapOpenDir = new File(".").getAbsolutePath();

	public Setting() {
		currentLanguage = "en_US";
		fontsize = 12;
		fontFamily = "Dialog";
		width = 800;
		height = 600;
		divX = 400;
		divY = 200;
		osDebugSplitPane_DividerLocation = 150;

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
		System.out.println(setting.width);
	}
}
