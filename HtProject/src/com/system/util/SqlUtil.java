package com.system.util;

public class SqlUtil {

	public static String sqlEncode(String txt) {
		if (StringUtil.isNullOrEmpty(txt))
			return "";
		return txt.replace("\\", "\\\\").replace("\'", "\\\'");
	}
}
