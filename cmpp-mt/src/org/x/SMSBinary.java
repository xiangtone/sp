package org.x;
/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;
import org.common.util.ConnectionService;

import com.xiangtone.util.*;

public class SMSBinary {
	private static Logger logger = Logger.getLogger(SMSBinary.class);
	public int serverID;
	public int ringID;
	public String fileName;
	public int section;
	public byte[][] content;
	public int len;
	
	String strSql;

	public SMSBinary() {

	}

	public byte[][] getBinaryContent(int ringID, int vcpID) {
		// logger.debug("vcpID:" + vcpID);
		// switch (vcpID) {
		// case 1:
		// db = new Mysqldb();
		// break;
		// case 2:
		// String w_dbip_xt = (String)
		// ConfigManager.getInstance().getConfigItem("w_dbip_xt", "w_dbip_xt not
		// found!");
		// String w_dbport_xt = (String)
		// ConfigManager.getInstance().getConfigItem("w_dbport_xt",
		// "w_dbport_xt not found!");
		// String w_dbname_xt = (String)
		// ConfigManager.getInstance().getConfigItem("w_dbname_xt",
		// "w_dbname_xt not found!");
		// String w_dbuser_xt = (String)
		// ConfigManager.getInstance().getConfigItem("w_dbuser_xt",
		// "w_dbuser_xt not found!");
		// String w_dbpwd_xt = (String)
		// ConfigManager.getInstance().getConfigItem("w_dbpwd_xt",
		// "w_dbpwd_xt not found!");
		// String r_dbip_xt = (String)
		// ConfigManager.getInstance().getConfigItem("r_dbip_xt", "r_dbip_xt not
		// found!");
		// String r_dbport_xt = (String)
		// ConfigManager.getInstance().getConfigItem("r_dbport_xt",
		// "r_dbport_xt not found!");
		// String r_dbname_xt = (String)
		// ConfigManager.getInstance().getConfigItem("r_dbname_xt",
		// "r_dbname_xt not found!");
		// String r_dbuser_xt = (String)
		// ConfigManager.getInstance().getConfigItem("r_dbuser_xt",
		// "r_dbuser_xt not found!");
		// String r_dbpwd_xt = (String)
		// ConfigManager.getInstance().getConfigItem("r_dbpwd_xt",
		// "r_dbpwd_xt not found!");
		// db = new Mysqldb(w_dbip_xt, w_dbport_xt, w_dbname_xt, w_dbuser_xt,
		// w_dbpwd_xt);
		// db = new Mysqldb();
		// break;
		// default:
		// db = new Mysqldb();
		// }
		DBForRead db=new DBForRead();
		ResultSet rs = null;
		byte[] buffer = null;
		byte[][] temp = new byte[20][161];
		try {
			strSql = "select ringid,content,len from sms_binary where ringid = " + ringID + " order by section";
			logger.debug(strSql);
			rs=db.executeQuery(strSql);
			int i = 0;
			while (rs.next()) {
				int nsize = rs.getInt("len");
				InputStream in = rs.getBinaryStream("content");
				buffer = new byte[nsize];
				int len;
				len = in.read(buffer);
				logger.debug("len:" + len);
				logger.debug("out:");
				logger.debug(MyTools.Bytes2HexString(buffer));
				temp[i] = buffer;
				i++;
			}

		} catch (Exception e) {
			logger.error("SMS.java getRingBin:", e);

		} finally {
			db.close();
		}
		return temp;
	}
}
