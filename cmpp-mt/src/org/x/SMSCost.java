package org.x;
/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/

import java.io.*;
import java.sql.*;

import org.apache.log4j.Logger;
import org.common.util.ConnectionService;

import com.xiangtone.util.DBForLog;

public class SMSCost {
	private static Logger logger = Logger.getLogger(SMSCost.class);
	public String serverID = "8003";
	public String serverCodeIOD = "BZ";
	public String serverCodePUSH = "-BZ";
	public String serverName = "BZ";
	public String infoFee = "0";
	public String feeType = "01";
	public int mediaType = 1;
	public String spCode;
	public String memo;

	public String getServerID() {
		return serverID;
	}

	public String getServerCodeIOD() {
		return serverCodeIOD;
	}

	public String getServerCodePUSH() {
		return serverCodePUSH;
	}

	public String getServerName() {
		return serverName;
	}

	public String getInfoFee() {
		return infoFee;
	}

	public String getFeeType() {
		return feeType;
	}

	public int getMediaType() {
		return mediaType;
	}

	public String getSpCode() {
		return spCode;
	}

	public String getMemo() {
		return memo;
	}

	public void setCostServerID(String serverID) {
		this.serverID = serverID;
	}

	public SMSCost() {
	}

	public void lookupInfofeeByServerIDIOD(String serverID) {
		String strSql=null;
		DBForLog db=new DBForLog();
		try {
			ResultSet rs = null;
			strSql = "select *  from sms_cost where serverid='" + serverID + "'";
			logger.debug(strSql);
			db.executeQuery(strSql);
			rs = db.getRs();
			if (rs.next()) {
				this.serverID = serverID;
				this.serverCodeIOD = rs.getString("feecode_iod");
				this.serverName = rs.getString("servername");
				this.serverCodePUSH = rs.getString("feecode_push");
				this.infoFee = rs.getString("infofee");
				this.feeType = rs.getString("feetype");
				this.mediaType = rs.getInt("mediatype");
				this.spCode = rs.getString("spcode");
				this.memo = rs.getString("memo");
			}
		} catch (Exception e) {
			logger.error(strSql, e);
		} finally {
			db.close();
		}
	}

	public void lookupInfofeeByServerIDPUSH(String serverID) {
		String strSql = null;
		DBForLog db=new DBForLog();
		try {
			ResultSet rs = null;
			strSql = "select *  from sms_cost where serverid='" + serverID + "'";
			logger.debug(strSql);
			db.executeQuery(strSql);
			rs = db.getRs();
			if (rs.next()) {
				this.serverID = serverID;
				this.serverCodeIOD = rs.getString("feecode_iod");
				this.serverName = rs.getString("servername");
				this.serverCodePUSH = rs.getString("feecode_push");
				this.infoFee = rs.getString("infofee");
				this.feeType = rs.getString("feetype");
				this.mediaType = rs.getInt("mediatype");
				this.spCode = rs.getString("spcode");
				this.memo = rs.getString("memo");
			}
		} catch (Exception e) {
			logger.error(strSql, e);
		} finally {
			db.close();
		}
	}
}
