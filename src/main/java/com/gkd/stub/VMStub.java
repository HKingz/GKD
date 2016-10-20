package com.gkd.stub;

import java.math.BigInteger;
import java.util.Hashtable;
import java.util.Vector;

import com.gkd.GKD;

public interface VMStub {
	public void initStub(String para[]);

	public void setVMPath(String path);

	public void setGKDInstance(GKD gkd);

	public void startVM();

	public void stopVM();

	public boolean isRunning();

	public void runVM();

	public void pauseVM();

	public String getVersion();

	public void setVMArguments(String arguments);

	public Vector<String[]> instruction(BigInteger virtualAddress, boolean is32Bit);

	public Hashtable<String, String> registers();

	public int[] physicalMemory(BigInteger addr, int noOfByte);

	public int[] virtualMemory(BigInteger addr, int noOfByte);

	public Vector<Vector<String>> gdt(BigInteger gdtAddress, int noOfByte);

	public Vector<Vector<String>> idt(BigInteger gdtAddress, int noOfByte);

	public Vector<Vector<String>> ldt(BigInteger gdtAddress, int noOfByte);

	public Vector<String[]> pageTable(BigInteger pageDirectoryBaseAddress, boolean pse, boolean pae);

	public Vector<String> stack();

	public Vector<Vector<String>> breakpoint();

	public String disasm(BigInteger eip);

	public Vector<Vector<String>> addressTranslate();

	public void singleStep();

	public void stepOver();

	public void addPhysicalBreakpoint(BigInteger address);

	public void deletePhysicalBreakpoint(BigInteger breakpointNo);

	public void enablePhysicalBreakpoint(BigInteger breakpointNo);

	public void disablePhysicalBreakpoint(BigInteger breakpointNo);

	public void addLinearBreakpoint(BigInteger address);

	public void deleteLinearBreakpoint(BigInteger breakpointNo);

	public void enableLinearBreakpoint(BigInteger breakpointNo);

	public void disableLinearBreakpoint(BigInteger breakpointNo);

	public void addVirtualBreakpoint(BigInteger segment, BigInteger address);

	public String sendVMCommand(String command);

	public void setMemory(BigInteger address, int b);

	public void changeReigsterValue(String register, BigInteger value);

	public void waitVMStop();
}
