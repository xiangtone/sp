package smsunitl.provsendwitch;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.xiangtone.util.DBForMT;

import java.util.ArrayList;
import java.io.*;
import java.sql.*;
import smsunitl.configfile.ConfigManager;

public class WhiteCpnsOperate {
	private static Logger logger = Logger.getLogger(WhiteCpnsOperate.class);
	public static List whiteCpnsList;
	public static long lastModify;
	private List tempWhiteCpnsList;
	private String cpn = "";// 手机号码
	// String dbIp = "xiangtone_dbip";
	// String dbName = "mts";
	// String dbUser = "xiangtone_dbuser";
	// String dbPwd = "xiangtone_dbpwd";
	// String dbPort = "xiangtone_dbport";
	public DBForMT mydb = null;
	public boolean whiteCpnFlag = false;// 判断是否是白号码标识

	public void init() {
		if (whiteCpnsList == null) {
			// whiteCpnsList = new ArrayList();
			String whiteCpns = ConfigManager.getInstance().getConfigData("whitecpns", "0000");
			// logger.debug(whiteCpns);
			String[] tempCpns = whiteCpns.split(",");
			this.whiteCpnsList = Arrays.asList(tempCpns);
			String fileName = "tlconfig.ini";
			// System.getProperty("user.dir")+File.separator+"config.ini";
			File f = new File(
					Thread.currentThread().getContextClassLoader().getResource("").toString().replaceAll("file:", "")
							+ fileName);
			lastModify = f.lastModified();
		}
		/*
		 * else{ logger.debug("not null"); }
		 */
		tempWhiteCpnsList = this.whiteCpnsList;
	}

	public boolean checkModify() {
		boolean checkFlag = false;
		String fileName = "tlconfig.ini";
		// System.getProperty("user.dir")+File.separator+"config.ini";
		File f = new File(
				Thread.currentThread().getContextClassLoader().getResource("").toString().replaceAll("file:", "")
						+ fileName);
		if (f.lastModified() > this.lastModify) {
			checkFlag = true;
		}
		return checkFlag;
		// if(f.lastModified())
	}

	// add at 2012-05-02
	// 用于判断是否是白号码的操作
	public void checkWhiteCpn() {
		// boolean whiteCpnFlag = false;
		this.mydb = new DBForMT();
		String strSql = "select id from whitecpn where cpn='" + this.cpn + "'";
		try {
			logger.debug(strSql);
			ResultSet rs = this.mydb.executeQuery(strSql);
			if (rs.next()) {
				this.whiteCpnFlag = true;
			} else {
				this.whiteCpnFlag = false;
			}
		} catch (Exception e) {
			logger.error(strSql, e);
			this.whiteCpnFlag = false;
		} finally {
			this.mydb.close();
		}
	}

	public List getWhiteCpnsList() {
		return this.tempWhiteCpnsList;
	}

	public void setCpn(String cpn) {
		this.cpn = cpn;
	}

	public boolean getWhiteCpnFlag() {
		return this.whiteCpnFlag;
	}
}
