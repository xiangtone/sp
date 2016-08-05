package org.x;

//import java.util.Calendar;//add at 08-01-22
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.xiangtone.util.FormatSysTime;
import com.xiangtone.util.IntByteConvertor;

import comsd.commerceware.cmpp.CMPP;
import comsd.commerceware.cmpp.CmppeSubmit;
import comsd.commerceware.cmpp.ConnDesc;

public class CMPPSendSd implements CMPPSend {
	private static Logger logger = Logger.getLogger(CMPPSendSd.class);
	public SMSMT mt;
	CMPPSingleConnect instance;
	ConnDesc con = new ConnDesc();
	CMPP p = new CMPP();

	public CMPPSendSd(SMSMT mt) {
		this.mt = mt;
		if (instance == null) {
			instance = CMPPSingleConnect.getInstance();
			con = instance.con;
		}
	}

	/**
	 * sendTextSMS
	 *
	 */
	public void sendTextSMS() {
		mt.dataCoding = 15; // text
		mt.reportFlag = 1;
		byte[] submitContent = mt.sendContent.getBytes();
		send(submitContent);

	}

	/**
	 * sendBinaryPicSMS
	 *
	 */
	public void sendBinaryPicSMS() {
		mt.dataCoding = 4; // text
		mt.reportFlag = 1;
		byte[] submitContent = mt.sendContent.getBytes();
		send(submitContent);
		/*
		 * mt.reportFlag = 1; int servermeida =
		 * Integer.parseInt(mt.serverID.substring(0,2)); int picID =
		 * Integer.parseInt(mt.sendContent); switch(servermeida) { case 11: case
		 * 12: case 30: mt.dataCoding =4; mt.tpUdhi = 1; mt.tpPid =1; break;
		 * case 14: mt.dataCoding =245; mt.tpUdhi = 0; mt.tpPid =0; break;
		 * default: mt.dataCoding =4; mt.tpUdhi = 1; mt.tpPid =1; break; }
		 * sendBinarySMS(picID);
		 */
	}

	/**
	 * sendBinarRingSMS
	 *
	 */
	public void sendBinaryRingSMS() {
		mt.reportFlag = 1;
		int servermeida = Integer.parseInt(mt.serverID.substring(0, 2));
		int ringid = Integer.parseInt(mt.sendContent);
		switch (servermeida) {
		case 11:
		case 30:
			mt.dataCoding = 4;
			mt.tpUdhi = 1;
			mt.tpPid = 1;
			break;
		case 12:
		case 14:
			mt.dataCoding = 245;
			mt.tpUdhi = 0;
			mt.tpPid = 0;
			break;
		default:
			mt.dataCoding = 4;
			mt.tpUdhi = 1;
			mt.tpPid = 1;
			break;
		}
		sendBinarySMS(ringid);
	}

	/**
	*
	*
	*/
	private void sendBinarySMS(int ringid) {
		SMSBinary binary = new SMSBinary();
		byte[][] buffer = binary.getBinaryContent(ringid, mt.vcpID);
		String infofee = mt.infoFee;
		String feetype = mt.feeType;
		int flag = 0;
		for (int i = 0; i < buffer.length; i++) {
			if (buffer[i][0] == 0) {
				mt.infoFee = infofee;
				mt.feeType = feetype;
				break;
			}
			send(buffer[i]);
			mt.infoFee = "0";
			mt.feeType = "01";
			mt.feeCode = "BZ";
			flag++;
		}
		if (flag == 0) {
			mt.dataCoding = 15;
			mt.tpUdhi = 0;
			mt.sendContent = "test";
			byte[] errorPicSend = mt.sendContent.getBytes();
			mt.infoFee = "0";
			mt.feeType = "01";
			mt.feeCode = "BZ";
			send(errorPicSend); // 发送文本
		}
	}

	public void sendTextSMCSMS() {
		try {
			mt.dataCoding = 15; // text
			mt.reportFlag = 2; // smc
			byte[] submitContent = mt.sendContent.getBytes();
			send(submitContent);
		} catch (Exception e) {
			logger.error("sendTextSMCSMS", e);
			e.printStackTrace();
		}

	}

	public void send(byte[] submitContent) {

		logger.debug("send sms by qw's gateway---mt.cpn:" + mt.feeCpn +"mt.feeCode:" + mt.feeCode+ "mt.infoFee:" + mt.infoFee);

		try {
			// msgId
			String tempMsgId = "0216134957";// mt.submitMsgID;
			// logger.debug("msgid value is at sd" + tempMsgId);
			// logger.debug(tempMsgId);
			long intMsgId = (new Long(tempMsgId)).longValue();
			logger.debug(intMsgId);
			byte[] msgId = new byte[8];// 初始值为0

			IntByteConvertor.putLong(msgId, intMsgId, 0);

			byte pkTotal = 1;
			byte pkNumber = 1;
			byte registeredDelivery = (byte) 1;// mt.reportFlag;
			byte msgLevel = 5;

			byte[] serviceCode = new byte[10];
			byte[] feecode = mt.feeCode.getBytes();
			System.arraycopy(feecode, 0, serviceCode, 0, feecode.length);
			byte feeUsertype = 3;

			byte[] feeTerminalId = new byte[32];
			byte feeTerminalType;// add at 061123
			String feeCpn = mt.feeCpn;
			if (feeCpn.length() < 13) {
				feeCpn = "86" + mt.feeCpn;
			}
			byte[] feecpn = feeCpn.getBytes();
			System.arraycopy(feecpn, 0, feeTerminalId, 0, feecpn.length);

			byte tpPid = (byte) mt.tpPid;
			byte tpUdhi = (byte) mt.tpUdhi;
			byte msgFmt = (byte) mt.dataCoding;

			byte[] msgSrc = new byte[6];
			byte[] icpID = SMSIsmgInfo.qwIcpID.getBytes();

			System.arraycopy(icpID, 0, msgSrc, 0, icpID.length);

			byte[] feetype = new byte[2];
			byte[] feetypeTemp = mt.feeType.getBytes();
			System.arraycopy(feetypeTemp, 0, feetype, 0, feetypeTemp.length);

			byte[] fee = new byte[6];
			byte[] infofee = mt.infoFee.getBytes();
			System.arraycopy(infofee, 0, fee, 0, infofee.length);

			byte[] validTime = new byte[17];

			byte[] atTime = new byte[17];

			// byte[] srcTerminalId = new byte[21];
			byte[] srcTerminalId = new byte[21];
			if (mt.spCode == null || mt.spCode.equals("")) {
				mt.spCode = SMSIsmgInfo.qwIsmgSpCode;
			}
			byte[] spcode = mt.spCode.getBytes();
			System.arraycopy(spcode, 0, srcTerminalId, 0, spcode.length);

			byte[][] destTerminalId = new byte[1][32];
			byte destTerminalType;
			String destCpn = mt.destCpn;
			if (destCpn.length() < 13) {
				destCpn = "86" + mt.destCpn;
			}
			byte[] destcpn = destCpn.getBytes();
			System.arraycopy(destcpn, 0, destTerminalId[0], 0, destcpn.length);

			byte destusrTl = 1;

			byte[] msgContent = submitContent;
			int smLen = 0;
			smLen = java.lang.reflect.Array.getLength(msgContent);
			byte msgLength = (byte) smLen;
			// byte[] reserve = new byte[8];
			// add at 061123
			byte[] linkid;
			if (mt.linkID == null) {
				linkid = new byte[20];
			} else {
				linkid = new byte[20];
				linkid = mt.linkID.getBytes();
			}

			// System.arraycopy(spcode,0,srcTerminalId,0,spcode.length);
			byte feecpnType = (byte) mt.cpnType;
			CmppeSubmit sub = new CmppeSubmit();// 提交短信对
			sub.setMsgid(msgId);
			sub.setPktotal(pkTotal);
			sub.setPknumber(pkNumber);
			sub.setRegistered(registeredDelivery);
			sub.setMsglevel(msgLevel);
			sub.setServiceid(serviceCode);
			sub.setFeeusertype(feeUsertype);
			sub.setFeeterminalid(feeTerminalId);
			sub.setFeeterminaltype(feecpnType);
			sub.setTppid(tpPid);
			sub.setTpudhi(tpUdhi);
			sub.setMsgfmt(msgFmt);
			sub.setMsgsrc(msgSrc);
			sub.setFeetype(feetype);
			sub.setFeecode(fee);
			sub.setValidtime(validTime);
			sub.setAttime(atTime);
			sub.setSrcterminalid(srcTerminalId);
			sub.setDestusrtl(destusrTl);
			sub.setDestterminalid(destTerminalId);
			sub.setDestterminaltype(feecpnType);// add at 061123
			sub.setFeeterminaltype(feecpnType);
			sub.setMsglength(msgLength);
			sub.setMsgcontent(msgContent);
			sub.setLinkid(linkid);// add at 061123
			// sub.setReserve(reserve);

			//
			if (instance == null) {
				instance.destroy();
				instance = CMPPSingleConnect.getInstance();

			}
			con = instance.con;
			logger.debug(con.sock);
			// int seq = -1;
			// logger.debug("before ");

			int seq = p.cmppSubmit(con, sub);
			mt.submitSeq = seq;
			mt.insertMTLog();
			logger.debug("seq提交成功:" + seq);
			// Thread.currentThread().sleep(100);
			// myLogger.info(FormatSysTime.getCurrentTimeA() + " send
			// msg--spcode:" +
			// mt.spCode + " cpn:" + mt.feeCpn + " linkId:" + mt.linkID + "
			// fee:" +
			// new String(fee) + " serviceCode:" + new String(serviceCode) + "
			// content:" + new String(msgContent));

		} catch (Exception e) {
			logger.error("sendTextSMCSMS", e);
			e.printStackTrace();

		}
	}

}