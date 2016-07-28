/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/

package com.xiangtone.sms.game;
import java.io.*;
import java.util.*;

public class DBConfig
{
	/**
	*
	*
	*/
	

	public static String dbip;
	public static String dbname;
	public static String dbuser;
	public static String dbpwd;
	public static int    dbport;

	
	String configFile;
	
	public DBConfig(String s_configFile)
	{
		
		configFile = s_configFile;
	}
	
	public boolean loadParam()
	{
		Properties props;
		try
		{
			System.out.println("cofig:"+configFile);
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
	    	this.dbip           = props.getProperty("dbip","paramName").trim();
	    	this.dbname         = props.getProperty("dbname","paramName").trim();
	    	this.dbuser         = props.getProperty("dbuser","paramName").trim();
	    	this.dbpwd          = props.getProperty("dbpwd","paramName").trim();
	    	strTmp          = props.getProperty("dbport","paramName").trim();  
	    	try
	    	{
	    		this.dbport = Integer.parseInt(strTmp);
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
    		return false;
    	}
    	
    	return true;
	}
	/**
	*
	*
	*/
	public void printParam()
	{
		prt("dbip",dbip);
		prt("dbport",dbport);
		prt("dbname",dbname);
		prt("dbuser",dbuser);        
		prt("dbpwd",dbpwd);
		
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
           