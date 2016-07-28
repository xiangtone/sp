/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package com.xiangtone.sms.game;

import java.util.*;
import java.sql.*;
/**
*
*
*/

public class Mysqldb
{

	String sDBDriver="org.gjt.mm.mysql.Driver";
	Connection conn=null;
	ResultSet rs=null;
	Statement stmt=null;
	
	public String dbip;
	public int dbport=3306;
	public String dbname;
	public String dbuser;
	public String dbpwd;
	
	
	
	String DBCon="jdbc:mysql://localhost/smsgm_my?user=root&password=&useUnicode=true&characterEncoding=gb2312";
	//String DBCon="jdbc:mysql://192.168.1.6/yxbw?user=smsdevp&password=YMj7*(l:-#4E%&useUnicode=true&characterEncoding=gb2312";
	/**
	*
	*
	*/
	public void setDB_dbip(String _ip){this.dbip = _ip;}
	public void setDB_dbport(int _port){this.dbport = _port;}
	public void setDB_dbname(String _dbname){this.dbname = _dbname;}
	public void setDB_dbuser(String _dbuser){this.dbuser = _dbuser;}
	public void setDB_dbpwd(String _dbpwd){this.dbpwd =_dbpwd;}
	
	public Mysqldb()
	{
  		try
  		{
   			 Class.forName(sDBDriver);
        }
  		catch(java.lang.ClassNotFoundException e)
  		{
  			System.err.println("Unable to load driver:" + e.getMessage());
  		}
  		
 	} 
 	
 	public void connectDB()
 	{
 		try
 		{
 			DBCon="jdbc:mysql://"+this.dbip+":"+this.dbport+"/"+this.dbname+"?";
 			DBCon+="user="+this.dbuser+"&password="+this.dbpwd+"&useUnicode=true&characterEncoding=GBK";
            System.out.println("SDBCon:"+DBCon);
            conn=DriverManager.getConnection(DBCon); 
    		stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
    	}
    	catch(java.sql.SQLException e)
  		{
  			System.err.println("Unable to connection:" + e.getMessage());	
  		}	
  	}
	
    	
	/**
	*
	*
	*/
  	public void executeInsert(String sql)
  	{
    	try
    	{
    	 	stmt.executeUpdate(sql);
     	}
  		catch(SQLException ex)
  		{
   			 System.err.println("aq.executeInsert:" + ex.getMessage());
    		System.err.println("aq.executeInsert:" + sql);
    	}
   } 
       
	/**
	*
	*
	*/ 
  public void executeUpdate(String sql)
  {
   	 	try
   	 	{
     		stmt.executeUpdate(sql);
     	}
  		catch(SQLException ex)
  		{
    		System.err.println("aq.executeInsert:" + ex.getMessage());
    		System.err.println("aq.executeInsert:" + sql);
    	}
   } 
	/**
	*
	*
	*/
 	public ResultSet executeQuery(String sql) 
  	{
 		 rs=null;
  		 try 
  		 {

   			rs=stmt.executeQuery(sql);
   		 }
    	 catch(SQLException ex)
    	 {
    		System.err.println("aq.executeQuery:" + ex.getMessage());
    		System.err.println("aq.executeQuery: " + sql);
    	}
    	return rs;
   }
   
	/**
	*
	*
	*/ 
   public void executeDelete(String sql)
   {
   		try 
   		{
     		stmt.executeUpdate(sql);
     	}
     	catch(SQLException ex) 
     	{
    		System.err.println("aq.executeDelete:" + ex.getMessage());
    		System.err.println("aq.executeDelete: " + sql);
    	}
   }
          	
	/**
	*
	*
	*/ 
   public void close() throws Exception 
   {
		if (stmt != null)  
		{
			stmt.close();
			stmt = null;
		}
		conn.close();
		conn = null;	
	}
	
 }   