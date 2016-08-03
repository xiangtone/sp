package org.x;

import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

import com.xiangtone.util.ConfigManager;
import com.xiangtone.util.DBForLocal;

/**
 * Copyright 2003 Xiamen Xiangtone Co. Ltd. All right reserved.
 */
public class MtSubmitSeq {
	private static Logger logger = Logger.getLogger(ConfigManager.class);
	private int submitSeq;
	private String submitMsgID;
	private int submitResult;
	private String ismgID;

	public MtSubmitSeq() {
	}

	public int getSubmitSeq() {
		return submitSeq;
	}

	public void setSubmitSeq(int submitSeq) {
		this.submitSeq = submitSeq;
	}

	public String getSubmitMsgID() {
		return submitMsgID;
	}

	public void setSubmitMsgID(String submitMsgID) {
		this.submitMsgID = submitMsgID;
	}

	public int getSubmitResult() {
		return submitResult;
	}

	public void setSubmitResult(int submitResult) {
		this.submitResult = submitResult;
	}

	public String getIsmgID() {
		return ismgID;
	}

	public void setIsmgID(String ismgID) {
		this.ismgID = ismgID;
	}

//	public void setMTSubmitSeq(int submitSeq) {
//		this.submitSeq = submitSeq;
//	}
//
//	public void setMTSubmitMsgID(String submitMsgID) {
//		this.submitMsgID = submitMsgID;
//	}
//
//	public void setMTSubmitResult(int submitResult) {
//		this.submitResult = submitResult;
//	}
//
//	public void setMTIsmgID(String ismgID) {
//		this.ismgID = ismgID;
//	}
	
	public int updateSubmitSeq() {
		String strSql = null;
		DBForLocal db=new DBForLocal();
		PreparedStatement ps=null;
		try {
			strSql = "update sms_mtlog set submit_seq = 0 ,submit_msgid=?,submit_result=? where submit_seq = ? and ismgid =? order by id desc limit 1";
			logger.debug(strSql);
			ps=db.getPreparedStatement(strSql);
			int m=0;
			ps.setString(m++, getSubmitMsgID());
			ps.setLong(m++, getSubmitResult());
			ps.setLong(m++, getSubmitSeq());
			ps.setString(m++, getIsmgID());
			return ps.executeUpdate();
		} catch (Exception e) {
			logger.error(strSql, e);
		} finally {
			db.close();
		}
		return 0;

	}
}