package org.x;

import java.util.Random;

import org.apache.log4j.Logger;

import com.xiangtone.util.FormatSysTime;
import com.xiangtone.util.MyTools;

public class SMSoperate {
	private static Logger logger = Logger.getLogger(SMSoperate.class);
	SMSMT smsmt = new SMSMT();
	/*
	 * public SMSMO smsmo; public SMSMT smsmt; public SMSUserSchedule smsus;
	 * public SMSMOtoVCP smsTovcp; public SMSReport smsreport; public SMSUser
	 * user;
	 * 
	 * //changed at 090812 public SMSUserSchedule smsus; public SMSReport
	 * smsreport; public SMSUser user; public SMSMO smsmo; public SMSMT smsmt;
	 * public SMSMOtoVCP smsTovcp;
	 */
	public String deliverIsmgID;
	public String deliverMsgID;
	public String deliverSpCode;
	public String deliverServerID;
	public int deliverFmt;
	public String deliverSrcCpn;
	public int srcCpnType;
	public int deliverContentLen;
	public byte[] deliverContent;
	public String linkid = "";

	public void setDeliverIsmgID(String ismgID) {
		this.deliverIsmgID = ismgID;
	}

	public void setDeliverMsgID(String msgID) {
		this.deliverMsgID = msgID;
	}

	public void setDeliverSpCode(String spCode) {
		this.deliverSpCode = spCode;
	}

	public void setDeliverServerID(String serverID) {
		this.deliverServerID = serverID;
	}

	public void setDeliverFmt(int fmt) {
		this.deliverFmt = fmt;
	}

	public void setDeliverSrcCpn(String srcCpn) {
		try {
			logger.debug(srcCpn.length());
			if (srcCpn.length() > 11)
				srcCpn = srcCpn.substring(2, 13);
		} catch (IndexOutOfBoundsException e) {
			logger.error("srcCpn length error", e);
		}
		this.deliverSrcCpn = srcCpn;

	}

	public void setDeliverSrcCpnType(int srccpntype) {
		this.srcCpnType = srccpntype;
	}

	public void setDeliverContentLen(int len) {
		this.deliverContentLen = len;
	}

	public void setDeliverContent(byte[] content) {
		this.deliverContent = content;
	}

	public void setDeliverLinkId(String linkId) {
		if (linkId.length() < 1) {
			linkId = "";
		}
		this.linkid = linkId;
	}

	public SMSoperate() {
		/*
		 * smsmo = new SMSMO(); smsmt = new SMSMT(); smsus = new
		 * SMSUserSchedule(); smsreport = new SMSReport(); smsTovcp = new
		 * SMSMOtoVCP(); user=new SMSUser();
		 */
		// smsus = new SMSUserSchedule();
		// smsreport = new SMSReport();
		// user=new SMSUser();
		// smsmo = new SMSMO();
		// smsmt = new SMSMT();
		// smsTovcp = new SMSMOtoVCP();
	}

	public void receiveSubmitResp(String IsmgID, int Seq, String MsgID, int SubmitResult) {
		try {
			// SMSMT smsmt = new SMSMT();

			smsmt.setMTIsmgID(IsmgID);
			smsmt.setMTSubmitSeq(Seq);
			smsmt.setMTSubmitMsgID(MsgID.trim());
			smsmt.setMTSubmitResult(SubmitResult);
			// smsmt.updateSubmitSeq(IsmgID,Seq,MsgID,SubmitResult);
			smsmt.updateSubmitSeq();

		} catch (Exception e) {
			logger.error("receiveSubmitResp", e);
		}
	}

	public void receiveReport(String IsmgID, String msgId, String linkId, String destCpn, String strSpcode,
			String strCpn, String submitTime, String doneTime, int statDev, String reportMsg) {
		try {
			SMSReport smsreport = new SMSReport();// changed at 090812

			if (destCpn.length() > 11)
				destCpn = destCpn.substring(2, 13);
			if (strCpn.length() > 11)
				strCpn = strCpn.substring(2, 13);
			smsreport.setIsmgId(IsmgID);
			smsreport.setMsgId(msgId);
			smsreport.setLinkId(linkId.trim());
			smsreport.setDestCpn(destCpn);
			smsreport.setSpCode(strSpcode);
			smsreport.setSrcCpn(strCpn);
			smsreport.setSubTime(submitTime);
			smsreport.setDoneTime(doneTime);
			smsreport.setStatDev(statDev);
			smsreport.setReportMsg(reportMsg);// add at 2009-02-17
			// smsreport.saveReportLog();
			smsreport.insertReportLog();
		} catch (Exception e) {
			logger.error("receiveReport", e);
			e.printStackTrace();
		}
	}

	public void receiveDeliver() {
		try {
			SMSMO smsmo = new SMSMO();
			SMSUserSchedule smsus = new SMSUserSchedule();
			SMSMOtoVCP smsTovcp = new SMSMOtoVCP();

			String strContent = "";
			switch (this.deliverFmt) {
			case 0:
			case 15:
			case 17:
				strContent = new String(this.deliverContent);// .substring(0,this.deliverContentLen);
				break;
			case 8:
				strContent = MyTools.UCS2GB2312(this.deliverContent);
				break;
			default:
				logger.debug("消息编码有误");
				strContent = "消息编码有误" + this.deliverFmt;
				break;
			}
			// change at 2012-08-07
			if (strContent.endsWith(" ")) {
				strContent = strContent.replace(" ", "_");
			}
			//
			strContent = strContent.toUpperCase().trim();
			logger.debug("mo.content:" + strContent);
			logger.debug("spcode :" + deliverSpCode);
			// 处理长号//061116
			smsus.getUserDetail(this.deliverSpCode, strContent);
			// System.out.println("this.deliverSpCode:"+deliverSpCode);
			// 处理长号//061116

			// 记录用户
			String strTime = FormatSysTime.getCurrentTimeA();

			Random r = new Random();
			smsmo.setMOMsgId(this.deliverMsgID);
			smsmo.setMOCpn(this.deliverSrcCpn);
			smsmo.setMoCpnType(this.srcCpnType);
			smsmo.setMOSpCode(smsus.getUSchedSpCode());
			smsmo.setMOServerID(smsus.getUSchedServerID());
			smsmo.setMOServerName(smsus.getUSchedGameCode());
			smsmo.setMOServerAction(smsus.getUSchedActionCode());
			// smsmo.setMOVcpID(smsus.getUSchedVcpID());
			smsmo.setMOVcpID(0);// ((r.nextInt(100)%4));
			smsmo.setMOSpCodeFirst(this.deliverSpCode);
			smsmo.setMOSvcType(this.deliverServerID);
			smsmo.setMOContent(strContent);
			smsmo.setMODeliverTime(strTime);
			smsmo.setMOIsmgID(deliverIsmgID);
			smsmo.setMOCorpID(smsus.getUSchedCorpID());
			smsmo.setMOLinkID(this.linkid);
			logger.debug(smsus.getUSchedSpCode());
			logger.debug("linkid is:" + this.linkid);

			// QueueSingle qs = QueueSingle.getInstance();
			// qs.enq(smsmo);

			smsmo.insertMOLog();

			String stat = smsTovcp.sendMosmsToVcp(smsmo);
			logger.debug("stat:" + stat);

			if (stat.equals("-1")) {
				smsmo.insertErrorMOLog();
			}
			// user.db.close();
			// Thread.currentThread().sleep(200);
		} catch (Exception e) {
			logger.error("receiveDeliver", e);
		}

	}

}
