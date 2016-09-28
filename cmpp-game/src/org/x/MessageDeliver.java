package org.x;
/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/

import java.io.*;
import java.sql.*;

import org.apache.log4j.Logger;

import com.xiangtone.util.DBForWrite;

public class MessageDeliver {
	private static Logger logger = Logger.getLogger(MessageDeliver.class);
	protected int id; // 序号
	protected int vcpid = 2; // 企业标号
	protected String ismgCode; // 省网关号
	protected String spCode; // 特服务号
	protected String mobileCode; // 源手机号码
	protected String gameCode; // 游戏代号
	protected String actionCode; // 游戏指令
	protected String deliverTime; // 上行时间

	protected ResultSet rs = null;
	protected String strSql;

	protected void setVcpid(int i) {
		this.vcpid = i;
	}

	protected void setIsmgCode(String code) {
		this.ismgCode = code;
	}

	protected void setSpCode(String code) {
		this.spCode = code;
	}

	protected void setMobileCode(String code) {
		this.mobileCode = code;
	}

	protected void setGameCode(String code) {
		this.gameCode = code;
	}

	protected void setActionCode(String code) {
		this.actionCode = code;
	}

	protected void setDeliverTime(String code) {
		this.deliverTime = code;
	}

	public MessageDeliver() {
	}

	protected void insertMOLog() {
		DBForWrite db=new DBForWrite();
		try {
			strSql = "insert into sms_molog set vcpid=" + vcpid + ",ismgid='" + ismgCode + "',spcode='" + spCode + "'";
			strSql += ",cpn='" + mobileCode + "',servername='" + gameCode + "',serveraction='" + actionCode + "'";
			strSql += ",deliverTime='" + deliverTime + "'";
			logger.debug(strSql);
			db.executeUpdate(strSql);
		} catch (Exception e) {
			logger.error(strSql,e);
		}finally{
			db.close();
		}
	}
}
