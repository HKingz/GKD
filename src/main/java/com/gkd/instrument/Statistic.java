package com.gkd.instrument;

public class Statistic {
	int noOfCachedRecord;
	int noOfDBRecord;
	int noOfRecordWithSymbol;

	public String toString() {
		return noOfRecordWithSymbol + " (symbol) / " + noOfDBRecord + " (DB) / " + noOfDBRecord + " (cache)";
	}
}
