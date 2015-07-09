package com.gkd.instrument.callgraph;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "jmpData")
public class JmpData {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "jmpDataId", unique = true, nullable = false)
	public Integer jmpDataId;

	public int lineNo;
	public Date date;
	public long fromAddress;
	public String fromAddressDescription;
	public long toAddress;
	public String toAddressDescription;

	public int what;

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

	public int deep;
	public String fromAddress_DW_AT_name;
	public String toAddress_DW_AT_name;
	public boolean showForDifferentDeep;

	public byte[] stack;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "jmpData")
	public List<Parameter> parameters = new ArrayList<Parameter>();

	public String toAddressSymbol;

	public JmpData() {
	}

	public JmpData(int lineNo, Date date, long fromAddress, String fromAddressDescription, long toAddress, String toAddressDescription, String toAddressSymbol, int what,
			long segmentStart, long segmentEnd, long eax, long ecx, long edx, long ebx, long esp, long ebp, long esi, long edi, long es, long cs, long ss, long ds, long fs,
			long gs, int deep, String fromAddress_DW_AT_name, String toAddress_DW_AT_name, boolean showForDifferentDeep, byte[] stack) {
		this.lineNo = lineNo;
		this.date = date;
		this.fromAddress = fromAddress;
		this.fromAddressDescription = fromAddressDescription;
		this.toAddressSymbol = toAddressSymbol;
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
		this.deep = deep;
		this.fromAddress_DW_AT_name = fromAddress_DW_AT_name;
		this.toAddress_DW_AT_name = toAddress_DW_AT_name;
		this.showForDifferentDeep = showForDifferentDeep;
		this.stack = stack;
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

	public int getWhat() {
		return what;
	}

	public void setWhat(int what) {
		this.what = what;
	}

	public JmpType getWhatEnum() {
		switch (what) {
		case 10:
			return JmpType.JMP;
		case 11:
			return JmpType.JMP_INDIRECT;
		case 12:
			return JmpType.CALL;
		case 13:
			return JmpType.CALL_INDIRECT;
		case 14:
			return JmpType.RET;
		case 15:
			return JmpType.IRET;
		case 16:
			return JmpType.INT;
		case 17:
			return JmpType.SYSCALL;
		case 18:
			return JmpType.SYSRET;
		case 19:
			return JmpType.SYSENTER;
		case 20:
			return JmpType.SYSEXIT;
		default:
			return JmpType.unknown;
		}
	}

	public String getWhatStr() {
		switch (what) {
		case 10:
			return "JMP";
		case 11:
			return "JMP_INDIRECT";
		case 12:
			return "CALL";
		case 13:
			return "CALL_INDIRECT";
		case 14:
			return "RET";
		case 15:
			return "IRET";
		case 16:
			return "INT";
		case 17:
			return "SYSCALL";
		case 18:
			return "SYSRET";
		case 19:
			return "SYSENTER";
		case 20:
			return "SYSEXIT";
		default:
			return "unknown";
		}
	}

	public String toString() {
		return fromAddress + "," + toAddress;
	}

}
