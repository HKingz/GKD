package com.gkd.mbr;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.peterswing.CommonLib;

public class ReadMBR {

	public static void main(String[] args) {
		try {
			InputStream in = new FileInputStream("/Users/peter/Desktop/test");

			in.skip(0x1be);
			for (int x = 0; x < 4; x++) {
				byte partition[] = new byte[16];
				IOUtils.read(in, partition, 0, 16);
				printPartitionTable(partition);
			}
			IOUtils.closeQuietly(in);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void printPartitionTable(byte partition[]) {
		System.out.println("status         : " + partition[0]);
		System.out.println("chs (first)    : " + hex(partition[1]) + " " + hex(partition[2]) + " " + hex(partition[3]));
		System.out.println("partition type : " + hex(partition[4]));
		System.out.println("chs (last)     : " + hex(partition[5]) + " " + hex(partition[6]) + " " + hex(partition[7]));
		System.out.println("lba (first)    : " + hex(partition[8]) + " " + hex(partition[9]) + " " + hex(partition[10]) + " " + hex(partition[11]));
		System.out.println("no. of sector  : " + hex(partition[12]) + " " + hex(partition[13]) + " " + hex(partition[14]) + " " + hex(partition[15]));
		System.out.println("size           : " + CommonLib.convertFilesize(CommonLib.getInt(new byte[] { partition[12], partition[13], partition[14], partition[15] }, 0) * 512));
		System.out.println();
	}

	public static String hex(int x) {
		return Integer.toHexString(x & 0xff);
	}
}
