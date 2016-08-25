package org.x;
/*

 * Created on 2006-11-15

 *

 * TODO To change the template for this generated file go to

 * Window - Preferences - Java - Code Style - Code Templates

 */

import org.apache.log4j.Logger;

import com.xiangtone.util.IntByteConvertor;

import comsd.CMPP;
import comsd.CmppeDeliverResult;
import comsd.CmppeResult;
import comsd.CmppeSubmitResult;
import comsd.ConnDesc;

public class SMSRecive implements Runnable {

	private static Logger logger = Logger.getLogger(SMSRecive.class);

	CMPP p = new CMPP();

	// connDesc con = new connDesc();

	CmppeDeliverResult cd = new CmppeDeliverResult();

	CmppeSubmitResult sr = new CmppeSubmitResult();

	CmppeResult cr = new CmppeResult();

	public SMSoperate handle;

	public CMPPSingleConnect cmppcon = CMPPSingleConnect.getInstance();;
	ConnDesc con = cmppcon.con;
	public static final String ISMGID = "01";

	public SMSRecive()

	{

		handle = new SMSoperate();

		// cmppcon = CMPPSingleConnect.getInstance();

		// con = cmppcon.con;

	}

	public void run() {

		// ThreadPoolManager moPoolManger = new
		// ThreadPoolManager(500,this.ISMGID);

		while (true) {

			try {

				// SMSoperate handle = new SMSoperate();

				p.readPa(con);
				Thread.currentThread().sleep(10);
				if (sr.flag == 0) // submit resp

				{

					sr.flag = -1; // 复位

					String strRespMsgId = IntByteConvertor.getLong(sr.msgId, 0) + "";// MyTools.Bytes2HexString(sr.msgId);MyTools.Bytes2HexString(sr.msgId);//new
					// String(sr.msgId2)//IntByteConvertor.getLong(sr.msgId,0)
					// +
					// "";//MyTools.Bytes2HexString(sr.msgId);

					int iRespResult = sr.result;

					int iRespSeq = sr.seq;

//					logger.debug("sr.result:" + iRespResult);
//
//					logger.debug("sr.seq:" + iRespSeq);
//
//					logger.debug("sr.msgId:" + strRespMsgId);

					handle.receiveSubmitResp(this.ISMGID, (int) iRespSeq, (String) strRespMsgId, (int) iRespResult);

					// handle.receiveSubmitResp(this.ISMGID,(int)iRespSeq,(String)strRespMsgId,(int)iRespResult);

				}

				if (cd.STAT == 0) // 说明有消息上来了

				{

					cd.STAT = -1;

					logger.debug("有消息上来了...");

					/////////////////////////////

					// moPoolManger.process1(cd);

					/////////////////////// 进行重构，加入线程池 date:2008-11-26 16:09

					String msgId = IntByteConvertor.getLong(cd.msgId, 0) + "";// MyTools.Bytes2HexString(cd.getMsgId());

					String strIsmgid = this.ISMGID;

					String strSpcode = cd.getSPCode();

					String strCpn = cd.getCpn();

					int cpnType = cd.getsrcType();// add 061121

					int iLen = cd.getLen();

					int iFmt = cd.getFmt();

					int iTpUdhi = cd.getTpUdhi();

					String strSvcType = cd.getServerType();

					String linkId = cd.getLinkId();

//					logger.debug("content:" + cd.getMessage());

					byte[] strContent = cd.getMessage();

					int iReportFlag = cd.getRegisteredDelivery();

//					logger.debug("iReportFlag:" + iReportFlag);
//
//					logger.debug("strSpcode:" + strSpcode);

					// myLogger.info(FormatSysTime.getCurrentTimeA() + " new
					// msg--spcode:"
					// + strSpcode +" cpn:" + strCpn.trim() + " linkId:" +
					// linkId + "
					// content:" + new String(strContent));

					if (iReportFlag == 1)

					{

//						logger.debug("状态报告信息....");

						String reportDestCpn = cd.getDestCpn();

						msgId = IntByteConvertor.getLong(cd.msgId2, 0) + "";// MyTools.Bytes2HexString(cd.getMsgId());

						String submitTime = cd.getSubmitTime();

						String doneTime = cd.getDoneTime();

						String stat2 = cd.getStat();

//						logger.debug("stat2:" + stat2);

						int statDev = 0;

						if (stat2.equals("DELIVRD"))

							statDev = 0;

						else

							statDev = -1;

						try {
							handle.receiveReport(this.ISMGID, msgId, linkId, reportDestCpn, strSpcode, strCpn, submitTime, doneTime,
									statDev, stat2);
						} catch (Exception e) {
							logger.error("", e);
						}

						// handle.receiveReport(this.ISMGID,msgId,reportDestCpn,strSpcode,strCpn,submitTime,doneTime,statDev);

						continue;

					}

					cd.printAll(); // 打印mo消息;

					handle.setDeliverIsmgID(strIsmgid);

					handle.setDeliverMsgID(msgId);

					handle.setDeliverSpCode(strSpcode);

					handle.setDeliverServerID(strSvcType);

					handle.setDeliverFmt(iFmt);

					handle.setDeliverSrcCpn(strCpn);

					handle.setDeliverSrcCpnType(cpnType);

					handle.setDeliverContentLen(iLen);

					handle.setDeliverContent(strContent);

					handle.setDeliverLinkId(linkId);

					handle.receiveDeliver();

				}

				Thread.currentThread().sleep(100);
			} catch (Exception e) {

				logger.error("SmsRecive 重新连接....", e);

				p.cmppDisconnectFromIsmg(con);

				cmppcon.destroy();

				cmppcon = null;

				try {

					Thread.currentThread().sleep(30 * 1000);

				} catch (Exception e1) {

				}

				cmppcon = CMPPSingleConnect.getInstance(); // 重连

				con = cmppcon.con;

			}

		}

	}

}
