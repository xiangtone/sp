package com.xiangtone.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

public class ConnectionService {
	private static Logger myLogger = Logger.getLogger(ConnectionService.class);
	private static final String DB_READ = "read";
	private static ConnectionService instance = new ConnectionService();

	private ConnectionService() {
	}

	static {
		getInstance();
	}

	public static ConnectionService getInstance() {
		return instance;
	}

	private DataSource dsLog = setupDataSource(DB_READ);

	public synchronized Connection getConnectionForLog() {
		try {
			return dsLog.getConnection();
		} catch (SQLException ex) {
			myLogger.error("Connection", ex);
		}
		return null;
	}

	public static DataSource setupDataSource(String db) {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl(ConfigManager.getConfigData(db + ".url"));
		ds.setUsername(ConfigManager.getConfigData(db + ".user"));
		ds.setPassword(ConfigManager.getConfigData(db + ".password"));
		ds.setInitialSize(Integer.valueOf(ConfigManager.getConfigData(db + ".initialSize") != null
				&& ConfigManager.getConfigData(db + ".initialSize").length() > 0
						? ConfigManager.getConfigData(db + ".initialSize") : "2"));
		ds.setMaxActive(Integer.valueOf(ConfigManager.getConfigData(db + ".maxActive") != null
				&& ConfigManager.getConfigData(db + ".maxActive").length() > 0 ? ConfigManager.getConfigData(db + ".maxActive")
						: "10"));
		ds.setMaxIdle(Integer.valueOf(ConfigManager.getConfigData(db + ".maxIdle") != null
				&& ConfigManager.getConfigData(db + ".maxIdle").length() > 0 ? ConfigManager.getConfigData(db + ".maxIdle")
						: "5"));
		ds.setMinIdle(Integer.valueOf(ConfigManager.getConfigData(db + ".minIdle") != null
				&& ConfigManager.getConfigData(db + "..minIdle").length() > 0 ? ConfigManager.getConfigData(db + "..minIdle")
						: "2"));
		ds.setMaxWait(Long.valueOf(ConfigManager.getConfigData(db + ".maxWait") != null
				&& ConfigManager.getConfigData(db + "..maxWait").length() > 0 ? ConfigManager.getConfigData(db + "..maxWait")
						: "5000"));
		ds.setRemoveAbandoned(true);
		ds.setRemoveAbandonedTimeout(60);
		ds.setLogAbandoned(true);
		ds.setMinEvictableIdleTimeMillis(30 * 1000);
		ds.setTimeBetweenEvictionRunsMillis(10 * 1000);
		return ds;
	}

	public static void printDataSourceStats(DataSource ds) throws SQLException {
		BasicDataSource bds = (BasicDataSource) ds;
		System.out.println("NumActive: " + bds.getNumActive());
		System.out.println("NumIdle: " + bds.getNumIdle());
	}

	public static void shutdownDataSource(DataSource ds) throws SQLException {
		BasicDataSource bds = (BasicDataSource) ds;
		bds.close();
	}

	public static void main(String[] args) {
		System.out.println(ConfigManager.getConfigData("log.url"));

		ConnectionService.getInstance().getConnectionForLog();

		System.out.println(ConfigManager.getConfigData("local.url"));
	}
}
