package com.gkd;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;

import org.apache.commons.io.FileUtils;

import com.gkd.GKD.OSType;
import com.peterswing.CommonLib;

public class Disassemble {
	public static String disassemble(int bytes[], int bits, BigInteger address) {
		try {
			FileUtils.writeByteArrayToFile(new File("temp"), CommonLib.intArrayToByteArray(bytes));

			ProcessBuilder pb;
			if (GKD.os == OSType.mac || GKD.os == OSType.linux) {
				pb = new ProcessBuilder("ndisasm", "-b", String.valueOf(bits), "-o", address.toString(), "temp");
			} else {
				pb = new ProcessBuilder("ndisasm.exe", "-b", String.valueOf(bits), "-o", address.toString(), "temp");
			}

			pb.redirectErrorStream(true);
			Process p;

			p = pb.start();
			String str = "";
			InputStream is = p.getInputStream();
			final BufferedReader br = new BufferedReader(new InputStreamReader(is), 1024);
			String line;
			while ((line = br.readLine()) != null) {
				str += line + "\n";
			}
			//new File("temp").delete();
			return str;
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
}
