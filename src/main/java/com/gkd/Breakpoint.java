package com.gkd;

import java.math.BigInteger;

public class Breakpoint {
	int no;
	String type;
	BigInteger segment;
	BigInteger address;
	String enable;
	int hit;

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public BigInteger getSegment() {
		return segment;
	}

	public void setSegment(BigInteger segment) {
		this.segment = segment;
	}

	public BigInteger getAddress() {
		return address;
	}

	public void setAddress(BigInteger address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}