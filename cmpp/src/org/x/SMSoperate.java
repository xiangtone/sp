package org.x;

import org.apache.log4j.Logger;

import com.xiangtone.util.FormatSysTime;
import com.xiangtone.util.MyTools;

public class SMSoperate {
	private static Logger logger = Logger.getLogger(SMSoperate.class);
	/*
	 * 
	 * public SMSMO smsmo;
	 * 
	 * public SMSMT smsmt;
	 * 
	 * public SMSUserSchedule smsus;
	 * 
	 * public SMSMOtoVCP smsTovcp;
	 * 
	 * public SMSReport smsreport;
	 * 
	 * public SMSUser user;
	 * 
	 * 
	 * 
	 */

	public SMSUserSchedule smsus;

	// public SMSReport smsreport;

	public SMSUser user;

	// public SMSMO smsmo;

	// public SMSMT smsmt;

	public SMSMOtoVCP smsTovcp;

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

			if (srcCpn.trim().length() > 11)

				srcCpn = srcCpn.substring(2, 13);

		}

		catch (IndexOutOfBoundsException e) {

			logger.error("", e);

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

	public void setDeliverLinkId(String linkid) {

		if (linkid.length() < 1) {

			linkid = "";

		}

		this.linkid = linkid;

	}

	public SMSoperate() {

		/*
		 * 
		 * smsmo = new SMSMO();
		 * 
		 * smsmt = new SMSMT();
		 * 
		 * smsus = new SMSUserSchedule();
		 * 
		 * smsreport = new SMSReport();
		 * 
		 * smsTovcp = new SMSMOtoVCP();
		 * 
		 * user=new SMSUser();
		 * 
		 */

		smsus = new SMSUserSchedule();

		// smsreport = new SMSReport();//change at 090804

		// user=new SMSUser();

		// smsmo = new SMSMO();

		// smsmt = new SMSMT();//change at 090804

		smsTovcp = new SMSMOtoVCP();

	}

	/**
	 * 
	 * �����ύ�󷵻ص����
	 *
	 * 
	 * 
	 */

	public void receiveSubmitResp(String ismgID, int seq, String msgID, int submitResult) {

		try {

			SMSMT smsmt = new SMSMT();

			smsmt.setIsmgID(ismgID);

			smsmt.setSubmitSeq(seq);

			smsmt.setSubmitMsgID(msgID.trim());

			smsmt.setSubmitResult(submitResult);

			// smsmt.updateSubmitSeq(IsmgID,Seq,MsgID,SubmitResult);

			smsmt.updateSubmitSeq();

		}

		catch (Exception e) {

			logger.error("", e);
		}

	}

	/**
	 * 
	 * ����report
	 *
	 * 
	 * 
	 */

	public void receiveReport(String ismgID, String msgId, String linkId,

			String destCpn, String strSpcode, String strCpn,

			String submitTime, String doneTime, int statDev, String reportMsg) {

		try {
			SMSReport smsreport = new SMSReport();// change at 090804
			if (destCpn.trim().length() > 11)

				destCpn = destCpn.substring(2, 13);

			if (strCpn.trim().length() > 11)

				strCpn = strCpn.substring(2, 13);

			smsreport.setIsmgID(ismgID);

			smsreport.setMsgID(msgId);

			smsreport.setLinkId(linkId.trim());

			smsreport.setDestCpn(destCpn);

			smsreport.setSpCode(strSpcode);

			smsreport.setSrcCpn(strCpn);

			smsreport.setSubmitTime(submitTime);

			smsreport.setDoneTime(doneTime);

			smsreport.setStat(statDev);

			smsreport.setMsg(reportMsg);// add at 2009-02-17

			// smsreport.saveReportLog();

			smsreport.insertReportLog();

		}

		catch (Exception e) {

			logger.error("", e);

		}

	}

	public void receiveDeliver() {
		SMSMO smsmo = new SMSMO();// change at 091105
		try {

			String strContent = "";

			switch (this.deliverFmt) // ���ݲ�ͬ�������Ϣ����
			{

			case 0:
			case 15:
			case 17:
				strContent = new String(this.deliverContent);// .substring(0,this.deliverContentLen);

				break;

			case 8:

				strContent = MyTools.UCS2GB2312(this.deliverContent);

				break;

			default:

				logger.debug("��Ϣ��������");

				strContent = "��Ϣ��������" + this.deliverFmt;

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

			// ������//061116

			smsus.getUserDetail(this.deliverSpCode, strContent);

			// logger.debug("this.deliverSpCode:"+deliverSpCode);

			// ������//061116

			// ��¼�û�

			String strTime = FormatSysTime.getCurrentTimeA();

			/*
			 * user.setUserCpn(this.deliverSrcCpn);
			 * 
			 * user.setUserCpnType(this.srcCpnType);//�û��ֻ����������뻹��α�롣
			 * 
			 * user.setUserIsmgID(this.deliverIsmgID);
			 * 
			 * user.setUserCorpID(smsus.getUSchedCorpID());
			 * 
			 * user.setUserCorpSpcode(smsus.getUSchedSpCode());
			 * 
			 * user.setUserRegisterTime(strTime);
			 * 
			 * user.setUserLastVisitTime(strTime);
			 * 
			 * if(user.userIsExist())
			 * 
			 * {
			 * 
			 * user.updateUserVisitTime();
			 * 
			 * }
			 * 
			 * else
			 * 
			 * {
			 * 
			 * user.insertNewUser();
			 * 
			 * 
			 * 
			 * }
			 */// changed at 090804

			// ����mo�������Ϣ

			smsmo.setMsgId(this.deliverMsgID);

			smsmo.setCpn(this.deliverSrcCpn);

			smsmo.setCpntype(this.srcCpnType);

			smsmo.setSpCode(smsus.getUSchedSpCode());

			smsmo.setServerID(smsus.getUSchedServerID());

			smsmo.setServerName(smsus.getUSchedGameCode());

			smsmo.setServerAction(smsus.getUSchedActionCode());

			smsmo.setVcpID(smsus.getUSchedVcpID());

			smsmo.setSpCodeFirst(this.deliverSpCode);

			smsmo.setSvcType(this.deliverServerID);

			smsmo.setContent(strContent);

			smsmo.setDeliverTime(strTime);

			smsmo.setIsmgID(deliverIsmgID);

			smsmo.setCorpID(smsus.getUSchedCorpID());

			smsmo.setLinkID(this.linkid);

			logger.debug(smsus.getUSchedSpCode());

			logger.info("linkid is:" + this.linkid);

			// ������ģ��

			// QueueSingle qs = QueueSingle.getInstance();

			// qs.enq(smsmo);

			// ����������־

			smsmo.insertMOLog();

			// �ɷ�������

			String stat = smsTovcp.sendMoSmsToVcp(smsmo);

			logger.debug("�ɷ������ͽ��:" + stat);

			// ������Ͳ��ɹ�,Ӧ�ý�һ������

			if (stat.equals("-1")) {

				smsmo.insertErrorMOLog();

			}

		}

		catch (Exception e) {

			logger.error("", e);

		}

	}

}
