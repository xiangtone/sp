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

			strTmp = props.getProperty("qw_ismg_port", "paramName").trim();
			this.qw_ismg_ip = props.getProperty("qw_ismg_ip", "paramName").trim();
			this.qw_ismg_spCode = props.getProperty("qw_ismg_spCode", "paraName").trim();
			this.qw_icpID = props.getProperty("qw_icpID", "paramName").trim();
			this.qw_icpShareKey = props.getProperty("qw_icpShareKey", "paramName").trim();
			try {
				this.qw_ismg_port = Integer.parseInt(strTmp);
			} catch (Exception e) {
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
		prt("qw_ismg_ip:", qw_ismg_ip);
		prt("qw_ismg_spcode:", qw_ismg_spCode);
		prt("qw_ismg_prot:", qw_ismg_port);
		prt("qw_icpID:", qw_icpID);
		prt("qw_icpShareKey:", qw_icpShareKey);
	}

	private void prt(String name, String content) {
		System.out.println(name + content);
	}

	private void prt(String name, int num) {
		System.out.println(name + num);
	}

	public static String getIsmg_spCode(String ismgid) {
		// �����ж���������ӵõ������ص�spcode,��ҵ����
		String spcode = "05511";
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
