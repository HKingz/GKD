package com.gkd;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.gkd.GKD.OSType;
import com.peterswing.CommonLib;

public class Disassemble {
	static Logger logger = Logger.getLogger(Disassemble.class);

	public static String disassemble(int bytes[], boolean is32Bit, BigInteger address) {
		try {
			if (Global.debug) {
				logger.info("disassemble");
			}
			FileUtils.writeByteArrayToFile(new File("temp"), CommonLib.intArrayToByteArray(bytes));

			ProcessBuilder pb;
			int bits = is32Bit ? 32 : 16;

			if (Global.ndisasmPath == null || Global.ndisasmPath.equals("")) {
				if (GKD.os == OSType.mac || GKD.os == OSType.linux) {
					pb = new ProcessBuilder("ndisasm", "-b", String.valueOf(bits), "-o", address.toString(), "temp");
				} else {
					pb = new ProcessBuilder("ndisasm.exe", "-b", String.valueOf(bits), "-o", address.toString(), "temp");
				}
			} else {
				pb = new ProcessBuilder(Global.ndisasmPath, "-b", String.valueOf(bits), "-o", address.toString(), "temp");
			}
			pb.redirectErrorStream(true);
			Process p;

			p = pb.start();
			String str = "";
			InputStream is = p.getInputStream();
			final BufferedReader br = new BufferedReader(new InputStreamReader(is), 1);
			String line;
			while ((line = br.readLine()) != null) {
				str += line + "\n";
			}
			return str;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			FileUtils.deleteQuietly(new File("temp"));
		}
	}
}
