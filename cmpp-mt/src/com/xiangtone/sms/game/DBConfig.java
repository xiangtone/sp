/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/

package com.xiangtone.sms.game;
import java.io.*;
import java.util.*;
/**
 *װ�������ļ�����
 *
 *
 */

public class DBConfig
{
	/**
	*
	*
	*/
	public static String dbip_r;     // ���ݿ������IP
	public static String dbname_r;   // ���ݿ���
	public static int    dbport_r;   // �˿�
	public static String dbuser_r;   // Ȩ���û���
	public static String dbpwd_r;    // ����
	
	public static String dbip_w;     // ���ݿ������IP
	public static String dbname_w;   // ���ݿ���
	public static int    dbport_w;   // �˿�
	public static String dbuser_w;   // Ȩ���û���
	public static String dbpwd_w;    // ����
	
	String configFile;             //�����ļ�
	
	public DBConfig(String s_configFile)
	{
		
		configFile = s_configFile;
	}
	/**
	 *װ�ز���
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
	*��ӡ�����ļ��еĲ���
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
           