package org.x;
/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/


import java.io.*;
import java.sql.*;

import com.xiangtone.sql.Mysqldb;

/*
*
*
*/
public class SMSCost
{
	/**
	*
	*
	*/
	public String serverID="8003";
	public String serverCode_IOD="BZ";
	public String serverCode_PUSH="-BZ";
	public String serverName = "BZ";
	public String infoFee="0";
	public String feeType="01";
	public int mediaType=1;
	public String spCode;
	public String memo;
	
	Mysqldb db;
	ResultSet rs = null;
	String strSql;
	/*
	*
	*
	*/
	public String getCost_serverID(){ return serverID;}
	public String getCost_serverCode_IOD(){ return serverCode_IOD;}
	public String getCost_serverCode_PUSH() {return serverCode_PUSH;}
	public String getCost_serverName() {return serverName;}
	public String getCost_infoFee() { return infoFee;}
	public String getCost_feeType() { return feeType;}
	public int getCost_mediaType() { return mediaType;}
	public String getCost_spCode() { return spCode;}
	public String getCost_memo() { return memo;}
	
	public void setCost_serverID(String _serverID){this.serverID = _serverID;}
	
	public SMSCost()
	{
		db = new Mysqldb();
	}
	
	/*
	*
	*
	*/
	public void lookupInfofeeByServerID_IOD(String _serverID)
	{
		try
		{
			strSql="select *  from sms_cost where serverid='"+_serverID+"'";
			rs = db.execQuery(strSql);
			if(rs.next())
			{
				//System.out.println("ddddddd");
				this.serverID = _serverID;
				this.serverCode_IOD =rs.getString("feecode_iod");
				this.serverName = rs.getString("servername");
				this.serverCode_PUSH = rs.getString("feecode_push");
				this.infoFee = rs.getString("infofee");
				this.feeType = rs.getString("feetype");
				this.mediaType = rs.getInt("mediatype");
				this.spCode = rs.getString("spcode");
				this.memo = rs.getString("memo");
				//System.out.println("spcode::"+this.spCode);
			}
			//rs =null;
			//db =null;
		}
		catch(Exception e)
		{
			System.out.println("err:"+e.toString());
			System.out.println(strSql);
		}
	}
	/**
	*
	*
	*/
	public void lookupInfofeeByServerID_PUSH(String _serverID)
	{
		
		try
		{
			strSql="select *  from sms_cost where serverid='"+_serverID+"'";
			rs = db.execQuery(strSql);
			if(rs.next())
			{
				this.serverID = _serverID;
				this.serverCode_IOD =rs.getString("feecode_iod");
				this.serverName = rs.getString("servername");
				this.serverCode_PUSH = rs.getString("feecode_push");
				this.infoFee = rs.getString("infofee");
				this.feeType = rs.getString("feetype");
				this.mediaType = rs.getInt("mediatype");
				this.spCode = rs.getString("spcode");
				this.memo = rs.getString("memo");
			}
			rs =null;
			//db =null;//not do it
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
			System.out.println(strSql);
		}
	}
}
		 
				

	
	
	