package com.gkd.instrument;

public class Statistic {
	int noOfCachedRecord;
	int noOfDBRecord;
	int noOfRecordWithSymbol;

	public String toString() {
		return String.format("%,d", noOfRecordWithSymbol) + " symbol / " + String.format("%,d", noOfDBRecord) + " DB / " + String.format("%,d", noOfCachedRecord) + " cache";
	}
}
