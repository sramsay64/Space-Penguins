package com.openthid.util;

public class StringUtils {
	public static char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

	public static String autoElipsis(String text, int maxChars) {
		if (text.length() > maxChars) {
			return text.substring(0, maxChars-2) + "...";
		}
		return text;
	}
}