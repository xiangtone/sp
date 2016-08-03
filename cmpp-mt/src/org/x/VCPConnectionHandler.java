package org.x;
/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/

import java.net.Socket;

import org.apache.log4j.Logger;

import com.xiangtone.sms.api.ConnDesc;
import com.xiangtone.sms.api.Message;
import com.xiangtone.sms.api.SmSubmitResult;
import com.xiangtone.util.FormatSysTime;

public class VCPConnectionHandler implements Runnable {
	private static Logger logger = Logger.getLogger(VCPConnectionHandler.class);
	protected Socket socketToHandle;
	protected ConnDesc con;
	protected Message mess;
	protected SmSubmitResult sm;
	protected SMSFactory myFactory;
	// protected SMSMT mt;
	protected SMSCost cost;
	// protected SMSMonth month;
	// protected SMSCard card;
	protected SMSMO mo;

	public VCPConnectionHandler(Socket aSocketToHandle) {
		socketToHandle = aSocketToHandle;
		con = new ConnDesc(socketToHandle);

		// mt = new SMSMT();
		myFactory = new SMSFactory();
		sm = new SmSubmitResult();
		mess = new Message();
		cost = new SMSCost();
		// month =new SMSMonth();
		// card = new SMSCard();
		mo = new SMSMO();
		// strart();
	}

	public void run() {
		try {

			mess.readPa(con); // 读取提交信息
			String stat = sm.getStat();
			logger.debug("stat....:" + stat);
			//
			// stat = "00";
			if (stat.equals("00")) {

				logger.debug("new message");
				String corpId = "00";
				int vcpId = 1;
				vcpId = Integer.parseInt(sm.getVcpId()); // getvcpId();
				String spCode = sm.getServerCode();// 特服号没有
													// //sm.getServerCode();

				int mediaType = Integer.parseInt(sm.getMediaType());
				String destCpn = sm.getDestCpn();
				String feeCpn = sm.getFeeCpn();
				String Infofee = sm.getFeeCode();
				String feeType = sm.getFeeType();
				String sfeeType = feeType;
				String serverId = sm.getServerType();
				String content = new String(sm.getContent());
				String ismgId = sm.getProvCode();
				/////////// add at 061123
				String linkId = sm.getLinkId();
				int feeCpnType = sm.getFeecpnType();
				String msgId = sm.getMsgId();
				logger.debug("linkid is:" + linkId);
				logger.debug("feecpntype:" + feeCpnType);
				logger.debug("msgid is:" + msgId);
				logger.debug("destcpn:" + destCpn);
				logger.debug("feeCpn:" + feeCpn);

				////////////////////////
				if (ismgId == null || ismgId.equals(""))
					ismgId = mo.getImsgID(feeCpn);
				if (msgId == null) {
					msgId = "";
				}
				String ServerCode = "";
				String serverName = "";
				SMSMT mt = new SMSMT();
				cost.lookupInfofeeByServerIDIOD(serverId);
				Infofee = cost.getInfoFee();
				ServerCode = cost.getServerCodeIOD();
				feeType = cost.getFeeType();
				serverName = cost.getServerName();

				mt.setMTVcpID(vcpId);
				mt.setMTIsmgID(ismgId);
				mt.setMTSpCode(spCode);
				mt.setMTCorpID(corpId);
				mt.setMTDestCpn(destCpn);
				mt.setMTFeeCpn(feeCpn);
				mt.setMTServerID(serverId);
				mt.setMTServerName(serverName);
				mt.setMTInfoFee(Infofee);
				mt.setMTFeeCode(ServerCode);
				mt.setMTFeeType(feeType);
				mt.setMTSendContent(content);
				mt.setMTMediaType(mediaType);
				mt.setMTSendTime(FormatSysTime.getCurrentTimeA());
				mt.setMTLinkID(linkId);
				mt.setMTCpnType(feeCpnType);
				mt.setMTSubmitMsgID(msgId);

				/////////////////
				/*
				 * logger.debug("////////////////");
				 * logger.debug("////////////////");
				 * logger.debug("////////////////");
				 * logger.debug("////////////////"); logger.debug("serverId:" +
				 * serverId); logger.debug("cpntype is:" + mt.cpnType);
				 * logger.debug("linkid is:" + mt.linkID); logger.debug(
				 * "content is:" + mt.sendContent); logger.debug("msgId is:" +
				 * mt.submitMsgID); logger.debug("////////////////");
				 * logger.debug("////////////////");
				 * logger.debug("////////////////");
				 * logger.debug("////////////////");
				 */
				//////////////////
				if (msgId.startsWith("auto")) {
					mt.setMTFeeType("01");
					mt.setMTServerID("1000");
					mt.setMTInfoFee("0");
					mt.setMTServerName("AutoHELP");
					mt.setMTFeeCode("HELP");
				}
				// mt.insertMTLog(); 
				///////////////////
				CMPPSend mysms = myFactory.createSMS(ismgId, mt);
				switch (mediaType) {
				case 1:
					mysms.sendTextSMS(); // 发送文本
					break;
				case 2:
					mysms.sendBinaryPicSMS(); // 发送图片
					break;
				case 3:
					mysms.sendBinaryRingSMS();// 发送铃声
					break;

				default:
					mysms.sendTextSMS(); // 发送文本
					break;
				}
				logger.debug("mt.feeType:" + mt.feeType);
				logger.debug("sfeeType:" + feeType);
				logger.debug("feeType:" + feeType);

			} // end if

		} // end try
		catch (Exception e) {
			logger.error("Error handling a client: ", e);
			e.printStackTrace();
		}

	}
}
