package com.gkd.instrument;

public class MySHStub {
	public native String getSharedMemory();

	public static void main(String args[]) {
		System.loadLibrary("PeterBochs");
		MySHStub mySHStub = new MySHStub();
		while (true) {
			String s = mySHStub.getSharedMemory();
			// logger.debug("return=" + mySHStub.getSharedMemory());
		}
	}
}
