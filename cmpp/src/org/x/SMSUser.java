package org.x;
/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/

import java.io.*;
import java.util.*;
import java.sql.*;

import com.xiangtone.sql.Mysqldb;
import com.xiangtone.util.FormatSysTime;

/**
* A System Time  format
*
*/
public class SMSUser
{
	/**
	*
	*
	*/
	public String cpn;
	public int cpn_Type;
	public String ismgID;
	public String registerTime;
	public String lastVisitTime;
	public int creditMoney;
	public String corp_id;
	public String corp_spcode;
	
	
	
	public Mysqldb db;
	private String strSql="";
	private ResultSet rs =null;
	/**
	*method of get and set
	*
	*/
	public String getUser_cpn() {return cpn;}
	public String getUser_ismgID(){return ismgID;}
	public String getUser_register_time() { return registerTime;}
	public String getUser_lastVisitTime() { return lastVisitTime;}
	public int    getUser_creditMoney() { return creditMoney;}
	public String getUser_corpID(){return corp_id;}
	public String getUser_corpSpcode(){return corp_spcode;}
	
	
	
	public void setUser_cpn(String strCpn) { this.cpn = strCpn;}
	public void setUser_cpnType(int cpn_type){
		this.cpn_Type = cpn_type;
	}
	public void setUser_ismgID(String strIsmgID) { this.ismgID = strIsmgID;}
	public void setUser_registerTime(String register_time) { this.registerTime=register_time;}
	public void setUser_lastVisitTime(String _last_visit_time){this.lastVisitTime = _last_visit_time;}
	public void setUser_creditMoney(int credit) { this.creditMoney = credit;}
	public void setUser_corpID(String strCorpid){this.corp_id = strCorpid;}
	public void setUser_corpSpcode(String strCorpSpcode){this.corp_spcode = strCorpSpcode;}
	
	
	public SMSUser()
	{
		try
		{
			db = new Mysqldb();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	/**
	*method one 
	*insert user log
	*/ 
	public void insertNewUser()
	{
		try
		{
			//插入
			strSql="insert into sms_user set ";
			strSql+="cpn='"+this.cpn+"'";
			strSql+=",ismgid='"+this.ismgID+"'";
			strSql+=",corp_id='"+this.corp_id+"'";
			strSql+=",corp_spcode='"+this.corp_spcode+"'";
			strSql+=",register_time='"+this.registerTime+"'";
			strSql+=",last_visit_time='"+this.lastVisitTime+"'";
			strSql+=",visit_times=1";
			db.execUpdate(strSql);
	
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
			System.out.println(strSql);
		}
	}
	/**
	*判断该用户是否已经存在
	*
	*/
	public boolean userIsExist()
	{
		boolean flag = false;
		try
		{
			strSql="select * from sms_user where cpn='"+this.cpn+"'";
			System.out.println(strSql);
			rs = db.execQuery(strSql);
			if(rs.next())
			{
				flag = true;
			}
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		rs = null;
		return flag;
	}
	
	/**
	*更新
	*
	*/
	public void updateUserVisitTime(String _cpn)
	{
		strSql = "update sms_user set last_visit_time='"+FormatSysTime.getCurrentTimeA()+"',visit_times =visit_times +1 ";
		strSql+=" where cpn = '"+_cpn+"'";
		try
		{
			db.execUpdate(strSql);
			
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
	}
	public void updateUserVisitTime()
	{
		strSql = "update sms_user set last_visit_time='"+FormatSysTime.getCurrentTimeA()+"',visit_times =visit_times +1 ";
		strSql+=" where cpn = '"+this.cpn+"'";
		try
		{
			db.execUpdate(strSql);
			
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
	}
	/**
	*加钱
	*
	*/
	public boolean increaseCreditMoney(String cpn, int money)
   	{   
   		boolean flag = false;
   		try
   		{
   			strSql = " update sms_user set creditmoney=creditmoney+"+money+" where cpn ='"+cpn+"'";
   			System.out.println(strSql);
   			db.execUpdate(strSql);
   			flag = true;
   		}
   		catch(Exception e)
   		{
   			System.out.println(e.toString());
   		}
   		return flag;
   	}
   	/**
   	*减钱
   	*
   	*/
   	public boolean decreaseCreditMoney(String cpn, int money)
   	{
   		boolean flag = false;
   		try
   		{
   			
   				
   			strSql = "update sms_user set creditmoney=creditmoney-"+money+"  where mobile='"+cpn+"'";
   			System.out.println(strSql);
   			db.execUpdate(strSql);
   			flag = true;
   			
   		}
   		catch(Exception e)
   		{
   			System.out.println(e.toString());
   		}
   		return flag;
   	}
   	
	/**
	*取用户余额
	*
	*
	*/
	public int getCreditMoney(String cpn)
	{
		int balance = 0;
		try
		{
			strSql = "select * from sms_user where cpn='"+cpn+"'";
			rs = db.execQuery(strSql);
			if(rs.next())
			{
				balance = rs.getInt("creditmoney");
			}
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		return balance;	
	 }
	
}