package org.x;

import java.sql.PreparedStatement;

import org.apache.log4j.Logger;
import org.common.util.ThreadPool;

import com.xiangtone.util.ConfigManager;
import com.xiangtone.util.DBForLocal;

/**
 * Copyright 2003 Xiamen Xiangtone Co. Ltd. All right reserved.
 */
public class MtSubmitSeq {
	private static Logger logger = Logger.getLogger(MtSubmitSeq.class);
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
	public void updateSubmitSeq() {
		
		ThreadPool.tpx.execute(new Runnable() {
			public void run() {
				String strSql = null;
				DBForLocal db=new DBForLocal();
				PreparedStatement ps=null;
				try {
					strSql = "update sms_mtlog set submit_seq = 0,submit_msgid=?,submit_result=? where submit_seq = ? and ismgid =? order by id desc limit 1";
					logger.info(strSql);
					logger.info("Statement: submit_msgid="+getSubmitMsgID()+", submit_result="+getSubmitResult()+", submit_seq = "+getSubmitSeq()+", ismgid ="+getIsmgID());
					ps=db.iniPreparedStatement(strSql);
					int m=1;
					ps.setString(m++, getSubmitMsgID());
					ps.setLong(m++, getSubmitResult());
					ps.setLong(m++, getSubmitSeq());
					ps.setString(m++, getIsmgID());
					ps.executeUpdate();
				} catch (Exception e) {
					logger.error(strSql, e);
				} finally {
					db.close();
				}
			}
		});

	}
}