/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/

package com.xiangtone.sms.game;
import java.io.*;
import java.util.*;
/**
 *装载配置文件的类
 *
 *
 */

public class DBConfig
{
	/**
	*
	*
	*/
	public static String dbip_r;     // 数据库服务器IP
	public static String dbname_r;   // 数据库名
	public static int    dbport_r;   // 端口
	public static String dbuser_r;   // 权限用户名
	public static String dbpwd_r;    // 密码
	
	public static String dbip_w;     // 数据库服务器IP
	public static String dbname_w;   // 数据库名
	public static int    dbport_w;   // 端口
	public static String dbuser_w;   // 权限用户名
	public static String dbpwd_w;    // 密码
	
	String configFile;             //配置文件
	
	public DBConfig(String s_configFile)
	{
		
		configFile = s_configFile;
	}
	/**
	 *装载参数
	 *
	 */
	public boolean loadParam()
	{
		Properties props;
		try
		{
   	    	InputStream ins = getClass().getResourceAsStream(configFile);
	    	if(ins!=null)
	    	{                                                    
   		    	props = new Properties();                             
		    	props.load(ins);
	    	}
	    	else
	    	{	
	        	System.err.println("Can not read the properties file. " );
	        	return false;
	    	}
	    	
	    	//read Param
	    	
	    	String strTmp = "";
	    	this.dbip_r           = props.getProperty("dbip_r","paramName").trim();
	    	this.dbname_r         = props.getProperty("dbname_r","paramName").trim();
	    	this.dbuser_r         = props.getProperty("dbuser_r","paramName").trim();
	    	this.dbpwd_r          = props.getProperty("dbpwd_r","paramName").trim();
	    	strTmp          = props.getProperty("dbport_r","paramName").trim();  
	    	try
	    	{
	    		this.dbport_r = Integer.parseInt(strTmp);
	    	}
	    	catch(Exception e)
	    	{
	    		System.out.println("dbport convert to int error!");
	    		 return false;
	        }    
	        
	        
	        this.dbip_w           = props.getProperty("dbip_w","paramName").trim();
	    	this.dbname_w         = props.getProperty("dbname_w","paramName").trim();
	    	this.dbuser_w         = props.getProperty("dbuser_w","paramName").trim();
	    	this.dbpwd_w          = props.getProperty("dbpwd_w","paramName").trim();
	    	strTmp          = props.getProperty("dbport_w","paramName").trim();  
	    	try
	    	{
	    		this.dbport_w= Integer.parseInt(strTmp);
	    	}
	    	catch(Exception e)
	    	{
	    		System.out.println("dbport convert to int error!");
	    		 return false;
	        }   
    	}
    	catch(Exception e)
    	{
    		System.out.println("read profile.ini file error!"+e);
    		e.printStackTrace();
    		return false;
    	}
    	
    	return true;
	}
	/**
	*打印配置文件中的参数
	*
	*/
	public void printParam()
	{
		prt("dbip_r",dbip_r);
		prt("dbport_r",dbport_r);
		prt("dbname_r",dbname_r);
		prt("dbuser_r",dbuser_r);        
		prt("dbpwd_r",dbpwd_r);
		
		prt("dbip_w",dbip_w);
		prt("dbport_w",dbport_w);
		prt("dbname_w",dbname_w);
		prt("dbuser_w",dbuser_w);        
		prt("dbpwd_w",dbpwd_w);
		
	}           
	private void prt(String str1,String str2)
	{                       
		System.out.println(str1+":"+str2);
	}
	private void prt(String str1,int str2)
	{                       
		System.out.println(str1+":"+str2);
	}       
}               
           