/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package com.xiangtone.util;

import java.util.*;
import java.sql.*;

public class DBManager
{
	private int flag = 0;
	private String dbDriver = "org.gjt.mm.mysql.Driver";
	private String w_dbConn="";
	private String r_dbConn="";
	private Connection conn=null;
	private ResultSet rs=null;
	private Statement stmt=null;
	
	/** 
	* 本类可能存在的惟一的一个实例 
	*/ 
	private static DBManager m_instance = null;
	
 	//读
 	private DBManager(String w_dbip,String w_dbport,String w_dbname,String w_dbuser,String w_dbpwd,
 	                  String r_dbip,String r_dbport,String r_dbname,String r_dbuser,String r_dbpwd)
	{
		flag = 1;
		try
		{
			Class.forName(dbDriver);
		}
		catch(ClassNotFoundException e)
		{
			System.err.println("Unable to load driver:" + e.getMessage());
  			e.printStackTrace();
  		}
  		w_dbConn="jdbc:mysql://"+w_dbip+":"+w_dbport+"/"+w_dbname+"?";
 		w_dbConn+="user="+ w_dbuser+"&password="+w_dbpwd;
 		w_dbConn+="&useUnicode=true&characterEncoding=GBK";
 		
 		r_dbConn="jdbc:mysql://"+r_dbip+":"+r_dbport+"/"+r_dbname+"?";
 		r_dbConn+="user="+r_dbuser+"&password="+r_dbpwd;
 		r_dbConn+="&useUnicode=true&characterEncoding=GBK";
 	}  
 	/** 
	* 静态工厂方法 
	* @return 返还DBManager 类的单一实例 
	*/ 
	synchronized public static DBManager getInstance(String w_dbip,String w_dbport,String w_dbname,String w_dbuser,String w_dbpwd,
 	                  								 String r_dbip,String r_dbport,String r_dbname,String r_dbuser,String r_dbpwd)
	 
	{ 
		if(m_instance ==null)
		{
			 m_instance=new DBManager(w_dbip,w_dbport, w_dbname,w_dbuser,w_dbpwd,r_dbip,r_dbport,r_dbname,r_dbuser,r_dbpwd); 
		}
		return m_instance; 
	}
	
	/** 
	* 数据库写操作
	* 
	* @param sql 操作SQL语句 
	* @return void 
	*/ 
	final public void executeUpdate(String sql)
	{
		try
    	{
    		conn=DriverManager.getConnection(w_dbConn); 
    		stmt=conn.createStatement();
    	 	stmt.executeUpdate(sql);
     	}
  		catch(SQLException ex)
  		{
   			 System.err.println("aq.executeUpdate:" + ex.getMessage());
    		 System.err.println("aq.executeUpdate:" + sql);
    		 ex.printStackTrace();
    	}
    	finally
    	{
    		if(stmt!=null)
    		{
    			try
    			{
    				stmt.close();
    			}
    			catch(SQLException et)
    			{
    				et.printStackTrace();
    			}
    		}
    		if(conn!=null)
    		{
    			try
    			{
    				conn.close();
    			}
    			catch(SQLException ec)
    			{
    				ec.printStackTrace();
    			}
    		}
    	}//end finally
    }
    
	/** 
	* 数据库写操作
	* 
	* @param sql 操作SQL语句 
	* @return void 
	*/ 
	final public void executeInsert(String sql)
	{
		try
    	{
    		conn=DriverManager.getConnection(w_dbConn); 
    		stmt=conn.createStatement();
    	 	stmt.executeUpdate(sql);
     	}
  		catch(SQLException ex)
  		{
   			 System.err.println("aq.executeInsert:" + ex.getMessage());
    		 System.err.println("aq.executeInsert:" + sql);
    		 ex.printStackTrace();
    	}
    	finally
    	{
    		if(stmt!=null)
    		{
    			try
    			{
    				stmt.close();
    			}
    			catch(SQLException et)
    			{
    				et.printStackTrace();
    			}
    		}
    		if(conn!=null)
    		{
    			try
    			{
    				conn.close();
    			}
    			catch(SQLException ec)
    			{
    				ec.printStackTrace();
    			}
    		}
    	}//end finally
    }
    /** 
	* 数据库读操作
	* 
	* @param sql 操作SQL语句 
	* @return ResultSet 
	*/ 
	final public ResultSet executeQuery(String sql)
	{
		rs = null;
		try
    	{
    		conn = DriverManager.getConnection(r_dbConn); 
    		stmt = conn.createStatement();
    	 	rs = stmt.executeQuery(sql);
    	 	
     	}
  		catch(SQLException ex)
  		{
   			 System.err.println("aq.executeQuery:" + ex.getMessage());
    		 System.err.println("aq.executeQuery:" + sql);
    		 ex.printStackTrace();
    	}
    	finally
    	{
    		if(stmt!=null)
    		{
    			try
    			{
    				stmt.close();
    			}
    			catch(SQLException et)
    			{
    				et.printStackTrace();
    			}
    		}
    		if(conn!=null)
    		{
    			try
    			{
    				conn.close();
    			}
    			catch(SQLException ec)
    			{
    				ec.printStackTrace();
    			}
    		}
    	}//end finally
    	return rs;
    }
	/** 
	* 关闭所有连接
	* 
	* @param sql 操作SQL语句 
	* @return ResultSet 
	*/ 
   public void closeDB() throws Exception 
   {
		if (stmt != null)  
		{
			try
			{
				stmt.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		if(conn !=null)
		{
			try
			{
				stmt.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}	
		}
	}
}