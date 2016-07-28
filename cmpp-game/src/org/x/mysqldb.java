package org.x;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.xiangtone.util.ConfigManager;

public class mysqldb {

	String sDBDriver = "org.gjt.mm.mysql.Driver";
	Connection conn = null;

	ResultSet rs = null;

	Statement stmt = null;

	String sDBCon = "";
	String strDate = "";

	public mysqldb(String dbip1, int dbport1, String dbname1, String dbuser1, String dbpwd1) {
		try {

			Class.forName(sDBDriver);
		} catch (java.lang.ClassNotFoundException e) {
			System.out.println(e.toString());
		}

		sDBCon = "jdbc:mysql://" + dbip1 + ":" + dbport1 + "/" + dbname1 + "?user=" + dbuser1 + "&password=" + dbpwd1;
		sDBCon += "&useUnicode=true&characterEncoding=GBK";

	}

	// 系统默认的方法
	public mysqldb() {
		try {

			Class.forName(sDBDriver);
		} catch (java.lang.ClassNotFoundException e) {
			System.out.println(e.toString());
		}

		String xiangtone_dbip = (String) ConfigManager.getInstance().getConfigData("xiangtone_dbip",
				"xiangtone_dbip not found!");
		String xiangtone_dbport = (String) ConfigManager.getInstance().getConfigData("xiangtone_dbport",
				"xiangtone_dbport not found!");
		String xiangtone_dbname = (String) ConfigManager.getInstance().getConfigData("xiangtone_dbname",
				"xiangtone_dbname not found!");
		String xiangtone_dbuser = (String) ConfigManager.getInstance().getConfigData("xiangtone_dbuser",
				"xiangtone_dbuser not found!");
		String xiangtone_dbpwd = (String) ConfigManager.getInstance().getConfigData("xiangtone_dbpwd",
				"xiangtone_dbpwd not found!");

		sDBCon = "jdbc:mysql://" + xiangtone_dbip + ":" + xiangtone_dbport + "/" + xiangtone_dbname;
		sDBCon += "?user=" + xiangtone_dbuser + "&password=" + xiangtone_dbpwd + "&useUnicode=true&characterEncoding=GBK";
		System.out.println(sDBCon);

	}

	/**
	*
	*
	*/

	public void executeUpdate(String sql) {

		try {
			conn = DriverManager.getConnection(sDBCon);
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			conn.close();
		} catch (SQLException ex) {
			System.out.println(ex.toString());
		}

		return;
	}

	public void executeInsert(String sql) {

		try {
			conn = DriverManager.getConnection(sDBCon);
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			conn.close();
		} catch (SQLException ex) {
			System.out.println(ex.toString());
		}

		return;
	}

	public ResultSet executeQuery(String sql) {
		rs = null;
		try {
			conn = DriverManager.getConnection(sDBCon);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			conn.close();

		} catch (SQLException ex) {
			System.out.println("error:" + sql);
			System.out.println(ex.toString());
			System.out.println("execQuery:" + stmt);
		}
		return rs;
	}

	public void setBin(String sql, byte[] ndata, int len) {
		if (len > 140)
			len = 140;
		java.sql.PreparedStatement pstm = null;
		try {
			pstm = conn.prepareStatement(sql);
			ByteArrayInputStream in = new ByteArrayInputStream(ndata);
			pstm.setBinaryStream(1, in, len);
			int rows = pstm.executeUpdate();
			System.out.println("rows:" + rows);
			pstm.close();
		} catch (SQLException ex) {
			System.err.println("aq.executeDelete:" + ex.getMessage());
			System.err.println("aq.executeDelete: " + sql);
		}
	}

	public void close() throws Exception {
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}

		if (conn != null) {
			conn.close();
			conn = null;
		}

	}

	public String str_replace(String str) {
		char ch = '\'';
		String strResult = "";
		int n1 = -1, n2 = 0;
		while (true) {
			n1 = str.indexOf(ch, n2);
			// System.out.println("n1:"+n1);
			if (n1 == -1) {
				strResult += str.substring(n2);
				break;
			} else {
				strResult += str.substring(n2, n1) + '\\' + ch;
			}
			n2 = n1 + 1;
		}

		// if( strResult.substring(strResult.length()-1).equals("\\") )
		// strResult+="\\";

		return strResult;
	}

}
