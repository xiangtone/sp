package com.xt.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

public class DBForLog {
	private static Logger myLogger = Logger.getLogger(DBForLog.class);

	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	public DBForLog(){
		try {
			conn=ConnectionService.getInstance().getConnectionForLog();
			stmt = conn.createStatement();
		} catch (SQLException e) {
			myLogger.error("DB",e);
		}
	}

	public void executeQuery(String paramString) throws SQLException {
		this.rs = null;
		this.rs = this.stmt.executeQuery(paramString);
	}

	public void close() {
		if (this.rs != null) {
			try {
				this.rs.close();
			} catch (SQLException localSQLException1) {
				this.myLogger.error("ResultSet close", localSQLException1);
			}
			this.rs = null;
		}
		if (this.stmt != null) {
			try {
				this.stmt.close();
			} catch (SQLException localSQLException2) {
				this.myLogger.error("Statement close", localSQLException2);
			}
			this.stmt = null;
		}
		if (this.conn != null) {
			try {
				this.conn.close();
			} catch (SQLException localSQLException3) {
				this.myLogger.error("Connection close", localSQLException3);
			}
			this.conn = null;
		}
	}

	public ResultSet getRs() {
		return this.rs;
	}

	public static void main(String[] args) {
		String sql="SELECT * FROM `tbl_base_users` WHERE id=1";
		DBForLog db=new DBForLog();
		try {
			db.executeQuery(sql);
			ResultSet rs=db.getRs();
			if(rs.next()){
				myLogger.debug(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}