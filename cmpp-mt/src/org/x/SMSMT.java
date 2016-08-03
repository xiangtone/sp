package org.x;

/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;
import org.common.util.ConnectionService;

import com.xiangtone.util.DBForLocal;
import com.xiangtone.util.DBForLog;

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
	// add at 061123
	public String linkID;
	public String msgId;// add at 08-11-27
	public int cpnType;
	//

	public int submitSeq;
	public String submitMsgID;
	public int submitResult;

	public int tpPid;
	public int tpUdhi;
	public int dataCoding;
	public int reportFlag;

	/**
	 * set and get method
	 *
	 */
	public int getMTVcpID() {
		return vcpID;
	}

	public String getMTIsmgID() {
		return ismgID;
	}

	public String getMTSpCode() {
		return spCode;
	}

	public String getMTDestCpn() {
		return destCpn;
	}

	public String getMTFeeCpn() {
		return feeCpn;
	}

	public String getMTServerID() {
		return serverID;
	}

	public String getMTServerName() {
		return serverName;
	}

	public String getMTInfoFee() {
		return infoFee;
	}

	public String getMTFeeCode() {
		return feeCode;
	}

	public String getMTFeeType() {
		return feeType;
	}

	public String getMTSendContent() {
		return sendContent;
	}

	public int getMTMediaType() {
		return mediaType;
	}

	public String getMTSendTime() {
		return sendTime;
	}

	public int getMTSubmitSeq() {
		return submitSeq;
	}

	public String getMTSubmitMsgID() {
		return submitMsgID;
	}

	public int getMTSubmitResult() {
		return submitResult;
	}

	public String getMTMsgId() {
		return msgId;
	}

	public void setMTVcpID(int vcpID) {
		this.vcpID = vcpID;
	}

	public void setMTIsmgID(String ismgID) {
		this.ismgID = ismgID;
	}

	public void setMTSpCode(String spCode) {
		this.spCode = spCode;
	}

	public void setMTDestCpn(String destCpn) {
		this.destCpn = destCpn;
	}

	public void setMTFeeCpn(String feeCpn) {
		this.feeCpn = feeCpn;
	}

	public void setMTServerID(String serverID) {
		this.serverID = serverID;
	}

	public void setMTServerName(String serverName) {
		this.serverName = serverName;
	}

	public void setMTInfoFee(String infoFee) {
		this.infoFee = infoFee;
	}

	public void setMTFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}

	public void setMTFeeType(String feeType) {
		this.feeType = feeType;
	}

	public void setMTSendContent(String sendContent) {
		this.sendContent = sendContent;
	}

	public void setMTMediaType(int mediaType) {
		this.mediaType = mediaType;
	}

	public void setMTSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public void setMTSubmitSeq(int submitSeq) {
		this.submitSeq = submitSeq;
	}

	public void setMTSubmitMsgID(String submitMsgID) {
		this.submitMsgID = submitMsgID;
	}

	public void setMTSubmitResult(int submitResult) {
		this.submitResult = submitResult;
	}

	public void setMTCorpID(String corpid) {
		this.corpID = corpid;
	}

	// add at 061123
	public void setMTLinkID(String linkid) {
		this.linkID = linkid;
	}

	public void setMTCpnType(int cpntype) {
		this.cpnType = cpntype;
	}

	public void setMTMsgID(String msgId) {
		this.msgId = msgId;
	}

	public SMSMT() {
	}

	public void insertMTLog() {
		// if(db == null)
		// {
		// db = new mysqldb();
		// }
		DBForLocal db=new DBForLocal();
		String strSql=null;
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
			// strSql+=",submit_msgid='"+submitMsgID+"'";
			strSql += ",submit_msgid=''";
			strSql += ",submit_result=" + submitResult;
			strSql += ",submit_seq=" + submitSeq;
			logger.debug(strSql);
			db.executeUpdate(strSql);
			// MtsMtHandle mtsMtLog = new MtsMtHandle();
			// mtsMtLog.insertMtlog(strSql);
		} catch (Exception e) {
			logger.error(strSql, e);
		} finally {
			db.close();
		}
	}

	/**
	*
	*
	*/
	public void updateSubmitSeq(String ismgId, int seq, String msgId, int submitResult) {
		DBForLocal db=new DBForLocal();
		String strSql=null;
		try {
			strSql = "update sms_mtlog set submit_seq = 0 ,submit_msgid='" + msgId + "',submit_result=" + submitResult
					+ " where submit_seq = " + seq + " and ismgid ='" + ismgId + "' order by id desc limit 1";
			logger.debug(strSql);
			db.executeUpdate(strSql);
			// MtsMtHandle mtsMtLog = new MtsMtHandle();
			// mtsMtLog.updateSubmitSeq(strSql);

		} catch (Exception e) {
			logger.error(strSql, e);
		} finally {
			db.close();
		}
	}

	public void updateSubmitSeq() {
		try {
			// strSql = "update smsMtlog set submitSeq = 0
			// ,submitMsgid='"+this.submitMsgID+"',submitResult="+this.submitResult+"
			// where submitSeq = "+this.submitSeq+" and ismgid
			// ='"+this.ismgID+"' order by id desc limit 1";
			// db.execUpdate(strSql);
			MtSubmitSeq mtSubmitSeq = new MtSubmitSeq();
			mtSubmitSeq.setSubmitSeq(this.submitSeq);
			mtSubmitSeq.setSubmitMsgID(this.submitMsgID);
			mtSubmitSeq.setSubmitResult(this.submitResult);
			mtSubmitSeq.setIsmgID(this.ismgID);
			mtSubmitSeq.updateSubmitSeq();
			/*
			 * MtsMtHandle mtsMtLog = new MtsMtHandle();
			 * mtsMtLog.setMTSubmitSeq(this.submitSeq);
			 * mtsMtLog.setMTSubmitMsgID(this.submitMsgID);
			 * mtsMtLog.setMTSubmitResult(this.submitResult);
			 * mtsMtLog.setMTIsmgID(this.ismgID); mtsMtLog.updateSubmitSeq();
			 */
		} catch (Exception e) {
			logger.error("updateSubmitSeq", e);
		}
	}

	public void insertMCLog(int cardFlag) {
		// if(db == null)
		// {
		// db = new mysqldb();
		// }
		DBForLocal db=new DBForLocal();
		String strSql=null;
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
			strSql += ",card_flag=" + cardFlag;
			logger.debug(strSql);
			db.close();
		} catch (Exception e) {
			logger.error(strSql, e);
		} finally {
			db.close();
		}
	}
}