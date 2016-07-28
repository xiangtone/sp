package org.x;
/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/

import java.io.*;
import java.sql.*;
/**
*
*
*/
public class MessageDeliver
{
	protected int id;             //序号
	protected int vcpid=2;        //企业标号
	protected String ismgCode;    //省网关号
	protected String spCode;      //特服务号
	protected String mobileCode;  //源手机号码
	protected String gameCode;    //游戏代号
	protected String actionCode;  //游戏指令
	protected String deliverTime; //上行时间
	
	protected mysqldb db;
	protected ResultSet rs = null;
	protected String strSql;
	/**
	*
	*
	*/
	protected void set_vcpid(int i) { this.vcpid = i;}
	protected void set_ismgCode(String code) { this.ismgCode = code; }
	protected void set_spCode(String code) {this.spCode = code;}
	protected void set_mobileCode(String code) { this.mobileCode = code; }
	protected void set_gameCode(String code) { this.gameCode = code; }
	protected void set_actionCode(String code) { this.actionCode = code; }
	protected void set_deliverTime(String code) { this.deliverTime = code;}
	/**
	*
	*
	*/
	public MessageDeliver()
	{
		db = new mysqldb();
	}
	/**
	*
	*
	*/
	protected void insertMOLog()
	{
		try
		{
			strSql = "insert into sms_molog set vcpid="+vcpid+",ismgid='"+ismgCode+"',spcode='"+spCode+"'";
			strSql+=",cpn='"+mobileCode+"',servername='"+gameCode+"',serveraction='"+actionCode+"'";
			strSql+=",deliverTime='"+deliverTime+"'";
			System.out.println(strSql);
			db.executeInsert(strSql);
			db.close();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
}
		
	
	