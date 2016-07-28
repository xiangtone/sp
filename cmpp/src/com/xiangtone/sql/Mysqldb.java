/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package com.xiangtone.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.xiangtone.util.ConfigManager;

public class Mysqldb {

	static final private Logger LOG = Logger.getLogger(Mysqldb.class);

	String sDBDriver = "com.mysql.jdbc.Driver";
	Connection conn = null;
	ResultSet rs = null;
	Statement stmt = null;

	public String dbip;
	public int dbport;
	public String dbname;
	public String dbuser;
	public String dbpwd;

	String DBCon = "";

	/**
	*
	*
	*/
	public void setDB_dbip(String _ip) {
		this.dbip = _ip;
	}

	public void setDB_dbport(int _port) {
		this.dbport = _port;
	}

	public void setDB_dbname(String _dbname) {
		this.dbname = _dbname;
	}

	public void setDB_dbuser(String _dbuser) {
		this.dbuser = _dbuser;
	}

	public void setDB_dbpwd(String _dbpwd) {
		this.dbpwd = _dbpwd;
	}

	public Mysqldb(String _dbip, String _dbport, String _dbname, String _dbuser, String _dbpwd) {
		try {
			Class.forName(sDBDriver);
		} catch (java.lang.ClassNotFoundException e) {
			System.err.println("Unable to load driver:" + e.getMessage());
			e.printStackTrace();
		}
		DBCon = "jdbc:mysql://" + _dbip + ":" + _dbport + "/" + _dbname + "?";
		DBCon += "user=" + _dbuser + "&password=" + _dbpwd + "&useUnicode=true&characterEncoding=GBK";

	}

	public Mysqldb() {
		try {
			Class.forName(sDBDriver);
			this.dbip = (String) ConfigManager.getConfigData("w_dbip_public", "w_dbip_public" + " dbip not found!");
			this.dbport = Integer
					.parseInt((String) ConfigManager.getConfigData("w_dbport_public", "w_dbport_public" + " dbport not found!"));
			this.dbname = (String) ConfigManager.getConfigData("w_dbname_public", "w_dbname_public" + " dbname not found!");
			this.dbuser = (String) ConfigManager.getConfigData("w_dbuser_public", "w_dbuser_public" + " dbuser not found!");
			this.dbpwd = (String) ConfigManager.getConfigData("w_dbpwd_public", "w_dbpwd_public" + " dbpwd not found!");
		} catch (java.lang.ClassNotFoundException e) {
			System.err.println("Unable to load driver:" + e.getMessage());
			e.printStackTrace();
		}
		DBCon = "jdbc:mysql://" + this.dbip + ":" + this.dbport + "/" + this.dbname + "?";
		DBCon += "user=" + this.dbuser + "&password=" + this.dbpwd + "&useUnicode=true&characterEncoding=GBK";
	}

	/**
	 * update .insert,delete 等操作
	 *
	 */
	public void executeUpdate(String sql) {
		try {
			conn = DriverManager.getConnection(DBCon);
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException ex) {
			System.err.println("aq.executeInsert:" + ex.getMessage());
			System.err.println("aq.executeInsert:" + sql);
			ex.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException et) {
					et.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException ec) {
					ec.printStackTrace();
				}
			}
		}

	}

	/**
	 * update .insert,delete 等操作
	 *
	 */
	public void execUpdate(String sql) {
		try {
			conn = DriverManager.getConnection(DBCon);
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException ex) {
			System.err.println("aq.executeInsert:" + ex.getMessage());
			System.err.println("aq.executeInsert:" + sql);
			ex.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException et) {
					et.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException ec) {
					ec.printStackTrace();
				}
			}
		}

	}

	/**
	 * select 数据操作
	 *
	 */
	public ResultSet executeQuery(String sql) {
		rs = null;
		try {
			conn = DriverManager.getConnection(DBCon);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			conn.close();
		} catch (SQLException ex) {
			System.err.println("aq.executeQuery:" + ex.getMessage());
			System.err.println("aq.executeQuery: " + sql);
			ex.printStackTrace();
		}
		return rs;
	}

	/**
	 * select 数据操作
	 *
	 */
	public ResultSet execQuery(String sql) {
		rs = null;
		try {
			conn = DriverManager.getConnection(DBCon);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			// conn.close();
		} catch (SQLException ex) {
			System.err.println("aq.executeQuery:" + ex.getMessage());
			System.err.println("aq.executeQuery: " + sql);
			ex.printStackTrace();
		}
		return rs;
	}

	/**
	 * 关闭所有连接
	 *
	 */
	public void close() throws Exception {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}