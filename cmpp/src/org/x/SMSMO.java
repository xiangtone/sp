package org.x;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>All right reserved</p>
 * <p>Company: Disney.com</p>
 * @author Gavin Wang
 * @version 0.5
 */
import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;

import com.xiangtone.util.DBForLocal;
import com.xiangtone.util.DBForLog;

import java.sql.*;

/**
 * A class respresenting a set of packet and byte couters. It is bservable to
 * allw it to be watched, but only comment of a class
 */
public class SMSMO {
	private static Logger logger = Logger.getLogger(SMSMO.class);
	protected String cpn; // 用户手机号
	protected int cpnType;
	protected String spCode; // 特别服务号(长号)
	protected String spCodeFirst; // 特服务号
	protected String svcType; // 业务类型
	protected String content; // 短信内容
	protected String serverName; // content分解0 ,业务代号
	protected String serverID; // 服务id
	protected String serverAction = ""; // content分解1 ,业务指令
	protected String deliverTime; // 接收时间
	protected String ismgID; // 省网关标志(01北京06辽宁08黑龙江15山东19广东
	protected int vcpID;
	protected int len;
	protected int tpUdhi = 0;
	protected int fmt = 0;
	protected String msgId = "";
	private DBForLog dblog = new DBForLog();
	private DBForLocal dblocal = new DBForLocal();
	private PreparedStatement ps = null;
	private String strSql = "";
	private ResultSet rs = null;

	protected String corpID;
	protected String linkID;

	public String getCpn() {
		return cpn;
	}

	public int getCpnType() {
		return cpnType;
	}

	public String getSpCode() {
		return spCode;
	}

	public String getSvcType() {
		return svcType;
	}

	public String getContent() {
		return content;
	}

	public String getServerName() {
		return serverName;
	}

	public String getServerID() {
		return serverID;
	}

	public String getServerAction() {
		return serverAction;
	}

	public String getDeliverTime() {
		return deliverTime;
	}

	public String getIsmgID() {
		return ismgID;
	}

	public int getVcpID() {
		return vcpID;
	}

	public String getCorpID() {
		return corpID;
	}

	public String getLinkID() {
		return linkID;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setCpn(String cpn) {
		this.cpn = cpn;
	}

	public void setCpntype(int cpntype) {
		this.cpnType = cpntype;
	}

	public void setSpCode(String spcode) {
		this.spCode = spcode;
	}

	public void setSpCodeFirst(String spCodeFirst) {
		this.spCodeFirst = spCodeFirst;
	}

	public void setSvcType(String svcType) {
		this.svcType = svcType;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public void setServerID(String serverID) {
		this.serverID = serverID;
	}

	public void setServerAction(String serverAction) {
		this.serverAction = serverAction;
	}

	public void setDeliverTime(String deliverTime) {
		this.deliverTime = deliverTime;
	}

	public void setIsmgID(String ismgID) {
		this.ismgID = ismgID;
	}

	public void setVcpID(int vcpID) {
		this.vcpID = vcpID;
	}

	public void setCorpID(String corpID) {
		this.corpID = corpID;
	}

	public void setLinkID(String linkid) {
		this.linkID = linkid;
	}

	///////////// add setMsgId
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	/**
	 * Construct Function
	 *
	 */
	public SMSMO() {
	}

	/**
	 * the first methods of class insert mo log
	 */
	public void insertMOLog() {
		try {
			strSql = "insert into sms_molog set ";
			strSql += " vcpid=" + vcpID;
			strSql += ",ismgid='" + ismgID + "'";
			strSql += ",corpid='" + corpID + "'";
			strSql += ",spcode_first='" + spCodeFirst + "'";
			strSql += ",content='" + content + "'";
			strSql += ",spcode='" + spCode + "'";
			strSql += ",msgid='" + msgId + "'";
			strSql += ",cpn='" + cpn + "'";
			strSql += ",serverid='" + serverID + "'";
			strSql += ",servername='" + serverName + "'";
			strSql += ",serveraction='" + serverAction + "'";
			strSql += ",delivertime='" + deliverTime + "'";
			strSql += ",linkid='" + linkID + "'";
			logger.info(strSql);
			dblocal.executeUpdate(strSql);
		} catch (SQLException e) {
			logger.error(strSql,e);
		}finally {
			dblocal.close();
		}
	}

	public void insertErrorMOLog() {
		try {
			strSql = "insert into sms_molog_error set ";
			strSql += " vcpid=" + vcpID;
			strSql += ",ismgid='" + ismgID + "'";
			strSql += ",corpid='" + corpID + "'";
			strSql += ",spcode_first='" + spCodeFirst + "'";
			strSql += ",content='" + content + "'";
			strSql += ",spcode='" + spCode + "'";
			strSql += ",cpn='" + cpn + "'";
			strSql += ",serverid='" + serverID + "'";
			strSql += ",servername='" + serverName + "'";
			strSql += ",serveraction='" + serverAction + "'";
			strSql += ",delivertime='" + deliverTime + "'";
			logger.debug(strSql);
			dblocal.executeUpdate(strSql);
		}catch (SQLException e) {
			logger.error(strSql,e);
		}finally {
			dblocal.close();
		}
	}

	public String getMO_corpID(String cpn) {
		try {
			strSql = "select corp_id from sms_user where cpn='" + cpn + "'";
			logger.debug(strSql);
			rs = dblog.executeQuery(strSql);
			if (rs.next()) {
				String corp_id = rs.getString("corp_id");
				return corp_id;
			}
		}catch (SQLException e) {
			logger.error(strSql,e);
		}finally {
			dblog.close();
		}
		return "00";
	}

	public String getGameID(int vcpid, String servername) {
		try {
			strSql = "select gameid from sms_gamelist where vcpid=" + vcpid + " and gamename='" + servername + "'";
			logger.debug(strSql);
			rs = dblog.executeQuery(strSql);
			if (rs.next()) {
				String gameid = rs.getString("gameid");
				return gameid;
			}
		} catch (SQLException e) {
			logger.error(strSql,e);
		}finally {
			dblog.close();
		}
		return "";
	}

	public String getImsgID(String scpn) {
		try {
			strSql = " select ismgid from sms_user where cpn='" + scpn + "'";
			logger.debug(strSql);
			rs = dblog.executeQuery(strSql);
			if (rs.next()) {
				String ismgid = rs.getString("ismgid");
				return ismgid;
			}
		}catch (SQLException e) {
			logger.error(strSql,e);
		}finally {
			dblog.close();
		}
		return "04";
	}
}