package com.xt.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

public class DBForLocal {
	private static Logger myLogger = Logger.getLogger(DBForLocal.class);

	private Connection conn = null;
	private Statement stmt = null;

	public DBForLocal(){
		try {
			conn=ConnectionService.getInstance().getConnectionForLocal();
			stmt = conn.createStatement();
		} catch (SQLException e) {
			myLogger.error("DB",e);
		}
	}

	public int executeUpdate(String paramString) throws SQLException {
		return this.stmt.executeUpdate(paramString);
	}

	public void close() {
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
	
}