package com.smxysu3.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCServiceProvider {
	private final static String driverName = "com.mysql.jdbc.Driver";//"org.gjt.mm.mysql.Driver";//"com.mysql.jdbc.Driver";
	private final static String url = "jdbc:mysql://192.168.1.41/smscompanymos?useUnicode=true&characterEncoding=GBK";
	private final static String userName = "smscompanymos";
	private final static String password = "BqAPHe3NG2QSfuyn";
	private static Connection connection;
	static{
		try {
			Class.forName(driverName);
			connection = 	DriverManager.getConnection(url, userName, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static String getDriverName() {
		return driverName;
	}
	
	public static String getUrl() {
		return url;
	}
	
	public static String getUserName() {
		return userName;
	}
	
	public static String getPassword() {
		return password;
	}
	
	public static Connection getConnection() {
		try {
			if(connection.isClosed()){
				connection = DriverManager.getConnection(url, userName, password);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	public static void setConnection(Connection connection) {
		JDBCServiceProvider.connection = connection;
	}
	 
	public static void colseConnection(Connection connection) throws SQLException{
		if(connection!=null){
			if(!connection.isClosed()){
				connection.close();
				connection=null;
			}
		}
	}

}
