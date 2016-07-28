package org.x;
/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class SMSIsmgInfo {
	/**
	*
	*
	*/

	/////
	public static String fj_ismg_spCode;
	public static String fj_ismg_ip;
	public static int fj_ismg_port;
	public static String fj_icpID;
	public static String fj_icpShareKey;

	public static String gd_ismg_spCode;
	public static String gd_ismg_ip;
	public static int gd_ismg_port;
	public static String gd_icpID;
	public static String gd_icpShareKey;

	public static String qw_ismg_spCode;
	public static String qw_ismg_ip;
	public static int qw_ismg_port;
	public static String qw_icpID;
	public static String qw_icpShareKey;
	/*
	
	*/
	String configFile;

	public SMSIsmgInfo(String s_configFile) {
		configFile = s_configFile;
	}

	public SMSIsmgInfo() {
		configFile = "config.ini";
	}

	public boolean loadParam() {
		Properties props;
		try {
			InputStream in = getClass().getResourceAsStream(configFile);
			if (in == null) {
				in = ClassLoader.getSystemResourceAsStream(configFile);
			}
			if (in == null) {
				File file = new File(System.getProperty("user.dir") + "/" + configFile);
				if (file.exists()) {
					in = new FileInputStream(System.getProperty("user.dir") + "/" + configFile);
				}
			}
			if (in == null) {
				String filePath = Thread.currentThread().getContextClassLoader().getResource("").toString().replaceAll("file:",
						"") + configFile;
				if (filePath.indexOf(":") == 2)
					filePath = filePath.substring(1, filePath.length());
				File file = new File(filePath);
				if (file.exists()) {
					in = new FileInputStream(filePath);
				}
			}
			if (in != null) {
				props = new Properties();
				props.load(in);
			} else {
				System.out.println("Can not read the properties file:" + configFile);
				return false;
			}
			////////////// fj ismg&sp information
			String strTmp = "";
			strTmp = props.getProperty("fj_ismg_port", "paramName").trim();
			this.fj_ismg_ip = props.getProperty("fj_ismg_ip", "paramName").trim();
			this.fj_ismg_spCode = props.getProperty("fj_ismg_spCode", "paraName").trim();
			this.fj_icpID = props.getProperty("fj_icpID", "paramName").trim();
			this.fj_icpShareKey = props.getProperty("fj_icpShareKey", "paramName").trim();
			try {
				this.fj_ismg_port = Integer.parseInt(strTmp);
			} catch (Exception e) {
				System.out.println("ICPSocket conver to int error!!!!!!!");
			}
			////////////// gd ismg&sp information
			// String strTmp = "";

			strTmp = props.getProperty("gd_ismg_port", "paramName").trim();
			this.gd_ismg_ip = props.getProperty("gd_ismg_ip", "paramName").trim();
			this.gd_ismg_spCode = props.getProperty("gd_ismg_spCode", "paraName").trim();
			this.gd_icpID = props.getProperty("gd_icpID", "paramName").trim();
			this.gd_icpShareKey = props.getProperty("gd_icpShareKey", "paramName").trim();
			try {
				this.gd_ismg_port = Integer.parseInt(strTmp);
				System.out.println("the pwd is::::::::::" + this.gd_icpShareKey);
				System.out.println("the port is:" + this.gd_ismg_port);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("ICPSocket conver to int error!!");
			}

		} catch (Exception e) {
			System.out.println("read profile.ini file error!" + e);
			return false;
		}
		return true;
	}

	public void printParam() {
		/*
		 * prt("fj_ismg_ip:",fj_ismg_ip); prt("fj_ismg_spcode:",fj_ismg_spCode);
		 * prt("fj_ismg_prot:",fj_ismg_port); prt("fj_icpID:",fj_icpID);
		 * prt("fj_icpShareKey:",fj_icpShareKey);
		 */
		prt("gd_ismg_ip:", gd_ismg_ip);
		prt("gd_ismg_spcode:", gd_ismg_spCode);
		prt("gd_ismg_prot:", gd_ismg_port);
		prt("gd_icpID:", gd_icpID);
		prt("gd_icpShareKey:", gd_icpShareKey);
	}

	private void prt(String name, String content) {
		System.out.println(name + content);
	}

	private void prt(String name, int num) {
		System.out.println(name + num);
	}

	public static String getIsmg_spCode(String ismgid) {
		// 用于有多个网关连接得到的网关的spcode,企业代码
		String spcode = "10665008";
		if (ismgid.equals("01")) {
			spcode = fj_ismg_spCode;
		} else if (ismgid.equals("02")) {
			spcode = gd_ismg_spCode;
		}
		return spcode;
	}
	/**
	*
	*
	*/

}
