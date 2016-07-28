package com.xiangtone.util;

public class ContactTEL {

	public static String getContactTel(String ismgid) {
		String rtnStr;
		if (ismgid.equals("01")) {
			rtnStr = (String) ConfigManager.getInstance().getConfigData("contact_tel_bj", "contact_tel_bj not found!");
		} else if (ismgid.equals("06")) {
			rtnStr = (String) ConfigManager.getInstance().getConfigData("contact_tel_ln", "contact_tel_ln not found!");
		} else if (ismgid.equals("08")) {
			rtnStr = (String) ConfigManager.getInstance().getConfigData("contact_tel_hl", "contact_tel_hl not fount!");
		} else if (ismgid.equals("15")) {
			rtnStr = (String) ConfigManager.getInstance().getConfigData("contact_tel_sd", "contact_tel_sd not fount!");
		} else if (ismgid.equals("19")) {
			rtnStr = (String) ConfigManager.getInstance().getConfigData("contact_tel_gd", "contact_tel_gd not fount!");
		} else {
			rtnStr = (String) ConfigManager.getInstance().getConfigData("contact_tel", "contact_tel not fount!");
		}
		return rtnStr;
	}
}
