package org.x;
/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/

import java.io.InputStream;
import java.sql.ResultSet;

import com.xiangtone.sql.Mysqldb;
import com.xiangtone.util.ConfigManager;
import com.xiangtone.util.MyTools;

/**
*
*
*
*/
public class SMSBinary {
	/**
	*
	*
	*/
	public int serverID;
	public int ringID;
	public String fileName;
	public int section;
	public byte[][] content;
	public int len;

	Mysqldb db;
	ResultSet rs = null;
	String strSql;

	public SMSBinary() {

	}

	/**
	*
	*
	*/

	public byte[][] getBinaryContent(int _ringID, int vcpid) {
		System.out.println("vcpid:" + vcpid);
		switch (vcpid) {
		case 1:
			db = new Mysqldb();
			break;
		case 2:
			String w_dbip_xt = (String) ConfigManager.getInstance().getConfigData("w_dbip_xt", "w_dbip_xt not found!");
			String w_dbport_xt = (String) ConfigManager.getInstance().getConfigData("w_dbport_xt", "w_dbport_xt not found!");
			String w_dbname_xt = (String) ConfigManager.getInstance().getConfigData("w_dbname_xt", "w_dbname_xt not found!");
			String w_dbuser_xt = (String) ConfigManager.getInstance().getConfigData("w_dbuser_xt", "w_dbuser_xt not found!");
			String w_dbpwd_xt = (String) ConfigManager.getInstance().getConfigData("w_dbpwd_xt", "w_dbpwd_xt not found!");
			String r_dbip_xt = (String) ConfigManager.getInstance().getConfigData("r_dbip_xt", "r_dbip_xt not found!");
			String r_dbport_xt = (String) ConfigManager.getInstance().getConfigData("r_dbport_xt", "r_dbport_xt not found!");
			String r_dbname_xt = (String) ConfigManager.getInstance().getConfigData("r_dbname_xt", "r_dbname_xt not found!");
			String r_dbuser_xt = (String) ConfigManager.getInstance().getConfigData("r_dbuser_xt", "r_dbuser_xt not found!");
			String r_dbpwd_xt = (String) ConfigManager.getInstance().getConfigData("r_dbpwd_xt", "r_dbpwd_xt not found!");
			db = new Mysqldb(w_dbip_xt, w_dbport_xt, w_dbname_xt, w_dbuser_xt, w_dbpwd_xt);
			break;
		default:
			db = new Mysqldb();
		}

		byte[] buffer = null;
		byte[][] temp = new byte[20][161];
		try {
			strSql = "select ringid,content,len from sms_binary where ringid = " + _ringID + " order by section";
			System.out.println(strSql);
			rs = db.execQuery(strSql);
			int i = 0;
			while (rs.next()) {
				int nsize = rs.getInt("len");
				InputStream in = rs.getBinaryStream("content");
				buffer = new byte[nsize];
				int len;
				len = in.read(buffer);
				System.out.println("len:" + len);
				System.out.println("out:");
				System.out.println(MyTools.Bytes2HexString(buffer));
				temp[i] = buffer;
				i++;
			}

		} catch (Exception e) {
			System.out.println("SMS.java getRingBin:" + e.toString());

		} finally {
			try {
				db.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return temp;
	}
}
