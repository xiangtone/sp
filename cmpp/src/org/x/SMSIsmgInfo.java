package org.x;
/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class SMSIsmgInfo {
	private static Logger logger = Logger.getLogger(SMSIsmgInfo.class);
	public static String fjIsmgSpCode;
	public static String fjIsmgIp;
	public static int fjIsmgPort;
	public static String fjIcpID;
	public static String fjIcpShareKey;

	public static String gdIsmgSpCode;
	public static String gdIsmgIp;
	public static int gdIsmgPort;
	public static String gdIcpID;
	public static String gdIcpShareKey;

	public static String qwIsmgSpCode;
	public static String qwIsmgIp;
	public static int qwIsmgPort;
	public static String qwIcpID;
	public static String qwIcpShareKey;
	
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
				logger.debug("Can not read the properties file:" + configFile);
				return false;
			}
			////////////// fj ismg&sp information
			String strTmp = "";
			strTmp = props.getProperty("fj_icpID", "paramName").trim();
			this.fjIsmgIp = props.getProperty("fj_ismg_port", "paramName").trim();
			this.fjIsmgSpCode = props.getProperty("fj_ismg_spCode", "paraName").trim();
			this.fjIcpID = props.getProperty("fj_icpID", "paramName").trim();
			this.fjIcpShareKey = props.getProperty("fj_icpShareKey", "paramName").trim();
			try {
				this.fjIsmgPort = Integer.parseInt(strTmp);
			} catch (Exception e) {
				logger.error("ICPSocket conver to int error!",e);
			}
			////////////// gd ismg&sp information
			// String strTmp = "";

			strTmp = props.getProperty("gd_ismg_port", "paramName").trim();
			this.gdIsmgIp = props.getProperty("gd_ismg_ip", "paramName").trim();
			this.gdIsmgSpCode = props.getProperty("gd_ismg_spCode", "paraName").trim();
			this.gdIcpID = props.getProperty("gd_icpID", "paramName").trim();
			this.gdIcpShareKey = props.getProperty("gd_icpShareKey", "paramName").trim();
			try {
				this.gdIsmgPort = Integer.parseInt(strTmp);
				logger.debug("the pwd is:" + this.gdIcpShareKey);
				logger.debug("the port is:" + this.gdIsmgPort);
			} catch (Exception e) {
				logger.error("ICPSocket conver to int error!",e);
			}

		} catch (Exception e) {
			logger.error("read profile.ini file error!", e);
			return false;
		}
		return true;
	}

	public void printParam() {
		prt("gdIsmgIp:", gdIsmgIp);
		prt("gdIsmgSpCode:", gdIsmgSpCode);
		prt("gdIsmgProt:", gdIsmgPort);
		prt("gdIcpID:", gdIcpID);
		prt("gdIcpShareKey:", gdIcpShareKey);
	}

	private void prt(String name, String content) {
		logger.debug(name + content);
	}

	private void prt(String name, int num) {
		logger.debug(name + num);
	}

	public static String getIsmg_spCode(String ismgid) {
		// 用于有多个网关连接得到的网关的spcode,企业代码
		String spcode = "10665008";
		if (ismgid.equals("01")) {
			spcode = fjIsmgSpCode;
		} else if (ismgid.equals("02")) {
			spcode = gdIsmgSpCode;
		}
		return spcode;
	}

}
