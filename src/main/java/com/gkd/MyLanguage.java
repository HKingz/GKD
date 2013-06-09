package com.gkd;

public class MyLanguage {
	public static String getString(String str) {
		try {
			return GKD.language.getString(str);
		} catch (Exception ex) {
			return str;
		}
	}
}
