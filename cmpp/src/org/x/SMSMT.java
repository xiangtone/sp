package org.x;

/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;

import com.xiangtone.util.DBForLocal;

import java.sql.*;

public class SMSMT {
	private static Logger logger = Logger.getLogger(SMSMT.class);
	public int vcpID;
	public String ismgID;
	public String spCode;

	public String destCpn; // 接收方的手机号
	public String feeCpn; // 发送方的手机号
	public String serverID; // 内容属于那个项目的
	public String serverName;
	public String infoFee; // 费用的值(以分为单位)
	public String feeCode;
	public String feeType; // 收费的方式
	public String sendContent;
	public int mediaType;
	public String sendTime; //

	public String corpID;
	public String linkID;
	public String msgId;
	public int cpnType;

	public int submitSeq;
	public String submitMsgID;
	public int submitResult;

	public int tpPid;
	public int tpUdhi;
	public int dataCoding;
	public int reportFlag;

	private DBForLocal db=new DBForLocal();;
	private PreparedStatement ps=null;
	private String strSql;

	public int getVcpID() {
		return vcpID;
	}

	public String getIsmgID() {
		return ismgID;
	}

	public String getSpCode() {
		return spCode;
	}

	public String getDestCpn() {
		return destCpn;
	}

	public String getFeeCpn() {
		return feeCpn;
	}

	public String getServerID() {
		return serverID;
	}

	public String getServerName() {
		return serverName;
	}

	public String getInfoFee() {
		return infoFee;
	}

	public String getFeeCode() {
		return feeCode;
	}

	public String getFeeType() {
		return feeType;
	}

	public String getSendContent() {
		return sendContent;
	}

	public int getMediaType() {
		return mediaType;
	}

	public String getSendTime() {
		return sendTime;
	}

	public int getSubmitSeq() {
		return submitSeq;
	}

	public String getSubmitMsgID() {
		return submitMsgID;
	}

	public int getSubmitResult() {
		return submitResult;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setVcpID(int vcpID) {
		this.vcpID = vcpID;
	}

	public void setIsmgID(String ismgID) {
		this.ismgID = ismgID;
	}

	public void setSpCode(String spCode) {
		this.spCode = spCode;
	}

	public void setDestCpn(String destCpn) {
		this.destCpn = destCpn;
	}

	public void setFeeCpn(String feeCpn) {
		this.feeCpn = feeCpn;
	}

	public void setServerID(String serverID) {
		this.serverID = serverID;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public void setInfoFee(String infoFee) {
		this.infoFee = infoFee;
	}

	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
	}

	public void setMediaType(int mediaType) {
		this.mediaType = mediaType;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public void setSubmitSeq(int submitSeq) {
		this.submitSeq = submitSeq;
	}

	public void setSubmitMsgID(String submitMsgID) {
		this.submitMsgID = submitMsgID;
	}

	public void setSubmitResult(int submitResult) {
		this.submitResult = submitResult;
	}

	public void setCorpID(String corpid) {
		this.corpID = corpid;
	}

	public void setLinkID(String linkid) {
		this.linkID = linkid;
	}

	public void setCpnType(int cpntype) {
		this.cpnType = cpntype;
	}

	public void setMsgID(String msgId) {
		this.msgId = msgId;
	}

	public SMSMT() {
	}

	public void insertMTLog() {
		try {
			strSql = "insert into sms_mtlog set ";
			strSql += " vcpid=" + vcpID;
			strSql += ",ismgid='" + ismgID + "'";
			strSql += ",comp_msgid='" + submitMsgID + "'";// 用于标识下发的信息的Id
			strSql += ",corpid='" + corpID + "'";
			strSql += ",spcode='" + spCode + "'";
			strSql += ",destcpn='" + destCpn + "'";
			strSql += ",feecpn='" + feeCpn + "'";
			strSql += ",serverid='" + serverID + "'";
			strSql += ",servername='" + serverName + "'";
			strSql += ",infofee='" + infoFee + "'";
			strSql += ",feetype='" + feeType + "'";
			strSql += ",feecode='" + feeCode + "'";
			strSql += ",content='" + sendContent + "'";
			strSql += ",linkid='" + linkID + "'";
			strSql += ",mediatype=" + mediaType;
			strSql += ",sendtime='" + sendTime + "'";
			strSql += ",submit_msgid='" + submitMsgID + "'";
			strSql += ",submit_result=" + submitResult;
			strSql += ",submit_seq=" + submitSeq;
			logger.debug(strSql);
			db.executeUpdate(strSql);
		} catch (SQLException e) {
			logger.error(strSql,e);
		}finally{
			db.close();
		}
	}

	public void updateSubmitSeq(String ismgid, int seq, String msg_id, int submit_result) {
		try {
			strSql = "update sms_mtlog set submit_seq = 0 ,submit_msgid='" + msg_id + "',submit_result=" + submit_result
					+ " where submit_seq = " + seq + " and ismgid ='" + ismgid + "' order by id desc limit 1";
			logger.debug(strSql);
			db.executeUpdate(strSql);
		} catch (SQLException e) {
			logger.error(strSql,e);
		}finally{
			db.close();
		}
	}

	public void updateSubmitSeq() {
		try {
			strSql = "update sms_mtlog set submit_seq = 0 ,submit_msgid='" + this.submitMsgID + "',submit_result="
					+ this.submitResult + " where submit_seq = " + this.submitSeq + " and ismgid ='" + this.ismgID
					+ "' order by id desc limit 1";
			logger.debug(strSql);
			db.executeUpdate(strSql);
		} catch (SQLException e) {
			logger.error(strSql,e);
		}finally{
			db.close();
		}
	}

	public void insertMCLog(int card_flag) {
		try {
			strSql = "insert into sms_mclog set ";
			strSql += " vcpid=" + vcpID;
			strSql += ",ismgid='" + ismgID + "'";
			strSql += ",corpid='" + corpID + "'";
			strSql += ",spcode='" + spCode + "'";
			strSql += ",destcpn='" + destCpn + "'";
			strSql += ",feecpn='" + feeCpn + "'";
			strSql += ",serverid='" + serverID + "'";
			strSql += ",servername='" + serverName + "'";
			strSql += ",infofee='" + infoFee + "'";
			strSql += ",feetype='" + feeType + "'";
			strSql += ",feecode='" + feeCode + "'";
			strSql += ",content='" + sendContent + "'";
			strSql += ",mediatype=" + mediaType;
			strSql += ",sendtime='" + sendTime + "'";
			strSql += ",submit_seq=" + submitSeq;
			strSql += ",card_flag=" + card_flag;
			logger.debug(strSql);
			db.executeUpdate(strSql);
		} catch (SQLException e) {
			logger.error(strSql,e);
		}finally{
			db.close();
		}
	}
}