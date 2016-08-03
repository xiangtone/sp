package com.xiangtone.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class DBForLog {
	private static Logger logger = Logger.getLogger(DBForLog.class);

	private Connection connection = null;
	private PreparedStatement preparedStatement= null;
	private ResultSet resultSet = null;

	public DBForLog(){
		connection=ConnectionService.getInstance().getConnectionForLog();
	}
	
	public PreparedStatement getPreparedStatement(String paramString) {
		if(connection==null){
			connection=ConnectionService.getInstance().getConnectionForLog();
		}
		try {
			preparedStatement = connection.prepareStatement(paramString);
		} catch (SQLException e) {
			logger.error("",e);
		}
		return preparedStatement;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void close() {
		if (this.resultSet != null) {
			try {
				this.resultSet.close();
			} catch (SQLException localSQLException1) {
				this.logger.error("ResultSet close", localSQLException1);
			}
			this.resultSet = null;
		}
		if (this.preparedStatement != null) {
			try {
				this.preparedStatement.close();
			} catch (SQLException localSQLException2) {
				this.logger.error("Statement close", localSQLException2);
			}
			this.preparedStatement = null;
		}
		if (this.connection != null) {
			try {
				this.connection.close();
			} catch (SQLException localSQLException3) {
				this.logger.error("Connection close", localSQLException3);
			}
			this.connection = null;
		}
	}

	public static void main(String[] args) {
		String sql="SELECT * FROM `tbl_base_users` WHERE id=1";
		DBForLog db=new DBForLog();
		try {
//			db.getPreparedStatement(sql).executeQuery();
			db.close();
			db.executeQuery(sql);
			ResultSet rs=db.getRs();
			if(rs.next()){
				logger.debug(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void executeQuery(String strSql) {
		if(connection==null){
			connection=ConnectionService.getInstance().getConnectionForLog();
		}
		try {
			preparedStatement=connection.prepareStatement(strSql);
			resultSet=preparedStatement.executeQuery();
		} catch (SQLException e) {
			logger.error(strSql,e);
		}
	}

	public ResultSet getRs() {
		return resultSet;
	}
	
}