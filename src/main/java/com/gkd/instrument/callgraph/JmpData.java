package com.gkd.instrument.callgraph;

public class JmpData {
	public long segmentStart;
	public long segmentEnd;
	public long from;
	public long to;

	public JmpData(long segmentStart, long segmentEnd, long from, long to) {
		this.segmentStart = segmentStart;
		this.segmentEnd = segmentEnd;
		this.from = from;
		this.to = to;
	}

	public boolean contains(String s) {
		s = s.toLowerCase();
		System.out.println(s + " = " + segmentStart);
		if (Long.toHexString(segmentStart).toLowerCase().contains(s)) {
			return true;
		}
		if (Long.toHexString(segmentEnd).toLowerCase().contains(s)) {
			return true;
		}
		if (Long.toHexString(from).toLowerCase().contains(s)) {
			return true;
		}
		if (Long.toHexString(to).toLowerCase().contains(s)) {
			return true;
		}
		return false;
	}
}
