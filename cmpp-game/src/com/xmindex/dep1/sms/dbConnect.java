/*
 * @(#)dbConnect.java 1.0 02/01/10
 *
 * Copyright (c) 2001 XM Index, Inc. All Rights Reserved.
 * 
 * Connect DB with custom driver and connection's string
 *
*/
package com.xmindex.dep1.sms;

import java.sql.*;

public class dbConnect{
	String dbName,sConnStr;
	String sDBDriver = "org.gjt.mm.mysql.Driver";
		
	Connection conn = null;
	ResultSet rs = null;
	
	public dbConnect(String dbName){
		this.dbName = dbName;
	        sConnStr = "jdbc:mysql://127.0.0.1/"+dbName+"?user=play&password=asdf2B#cl.M23cD&useUnicode=true&characterEncoding=gb2312";
		try{
		Class.forName(sDBDriver);
		}
		catch(java.lang.ClassNotFoundException e){
			System.err.println("sql_data():"+e.getMessage());
			}
		}
	public void executeInsert(String sql){
		try{
		conn = DriverManager.getConnection(sConnStr);
		Statement stmt = conn.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,java.sql.ResultSet.CONCUR_READ_ONLY);
		stmt.executeUpdate(sql);
		}
		catch(SQLException ex){
			System.err.println("sql_date.executeUpdate:"+ex.getMessage());
			}
		}
	public ResultSet executeQuery(String sql){
		rs = null;
		try{
		conn = DriverManager.getConnection(sConnStr);
		Statement stmt = conn.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,java.sql.ResultSet.CONCUR_READ_ONLY);
		rs = stmt.executeQuery(sql);
		}
		catch(SQLException ex){
			System.err.println("sql_data.executeQuery:"+ex.getMessage()+sConnStr);
			}
		return rs;
		}
	public void executeDelete(String sql){
		try{
		conn = DriverManager.getConnection(sConnStr);
		Statement stmt = conn.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,java.sql.ResultSet.CONCUR_READ_ONLY);
		stmt.executeUpdate(sql);
		}
		catch(SQLException ex){
			System.err.println("sql_data.executeDelete:"+ex.getMessage());
			}
		}
	}