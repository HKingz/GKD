package com.gkd.instrument.callgraph;

import java.util.Date;

public class JmpData {
	public int lineNo;
	public Date date;
	public long fromAddress;
	public String fromAddressDescription;
	public long toAddress;
	public String toAddressDescription;

	public enum JmpType {
		unknown, BX_INSTR_IS_JMP, BX_INSTR_IS_JMP_INDIRECT, BX_INSTR_IS_CALL, BX_INSTR_IS_CALL_INDIRECT, BX_INSTR_IS_RET, BX_INSTR_IS_IRET, BX_INSTR_IS_INT, BX_INSTR_IS_SYSCALL, BX_INSTR_IS_SYSRET, BX_INSTR_IS_SYSENTER, BX_INSTR_IS_SYSEXIT,
	}

	public JmpType what;

	public long segmentStart;
	public long segmentEnd;

	public long eax;
	public long ecx;
	public long edx;
	public long ebx;
	public long esp;
	public long ebp;
	public long esi;
	public long edi;

	public long es;
	public long cs;
	public long ss;
	public long ds;
	public long fs;
	public long gs;

	public JmpData(int lineNo, Date date, long fromAddress, String fromAddressDescription, long toAddress, String toAddressDescription, JmpType what, long segmentStart,
			long segmentEnd, long eax, long ecx, long edx, long ebx, long esp, long ebp, long esi, long edi, long es, long cs, long ss, long ds, long fs, long gs) {
		this.lineNo = lineNo;
		this.date = date;
		this.fromAddress = fromAddress;
		this.fromAddressDescription = fromAddressDescription;
		this.toAddress = toAddress;
		this.toAddressDescription = toAddressDescription;
		this.what = what;
		this.segmentStart = segmentStart;
		this.segmentEnd = segmentEnd;
		this.eax = eax;
		this.ecx = ecx;
		this.edx = edx;
		this.ebx = ebx;
		this.esp = esp;
		this.ebp = ebp;
		this.esi = esi;
		this.edi = edi;
		this.es = es;
		this.cs = cs;
		this.ss = ss;
		this.ds = ds;
		this.fs = fs;
		this.gs = gs;
	}

	public boolean contains(String s) {
		if (Long.toHexString(segmentStart).toLowerCase().contains(s)) {
			return true;
		}
		if (Long.toHexString(segmentEnd).toLowerCase().contains(s)) {
			return true;
		}
		if (Long.toHexString(fromAddress).toLowerCase().contains(s)) {
			return true;
		}
		if (Long.toHexString(toAddress).toLowerCase().contains(s)) {
			return true;
		}
		if (fromAddressDescription != null && fromAddressDescription.toLowerCase().contains(s)) {
			return true;
		}
		if (toAddressDescription != null && toAddressDescription.toLowerCase().contains(s)) {
			return true;
		}
		return false;
	}

	public String toString() {
		return fromAddress + "," + toAddress;
	}
}
