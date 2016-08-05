package org.x;

/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
import java.io.*;
import java.util.*;
import com.xiangtone.sms.api.*;
import com.xiangtone.util.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
*
*
*/
public class SMSMOtoVCP {

	private static Logger logger = Logger.getLogger(SMSMOtoVCP.class);
	public Message xtsms;
	public SmDeliver xtdeliver;
	public SmDeliverAckResult xtdeliverAck;
	public ConnDesc xtconn;
	// public SMSMO smsmo;

	String vcpIp1 = (String) ConfigManager.getInstance().getConfigItem("vcp_ip1", "vcp_ip1 not found");
	String vcpPort1 = (String) ConfigManager.getInstance().getConfigItem("vcp_port1", "vcp_port1 not found");
	String vcpPort2 = (String) ConfigManager.getInstance().getConfigItem("vcp_port2", "vcp_port2 not found");
	String vcpPort3 = (String) ConfigManager.getInstance().getConfigItem("vcp_port3", "vcp_port2 not found");
	String vcpPort4 = (String) ConfigManager.getInstance().getConfigItem("vcp_port4", "vcp_port2 not found");

	String vcpIp2 = (String) ConfigManager.getInstance().getConfigItem("vcp_ip2", "vcp_ip2 not found");
	String vcpip2port2 = (String) ConfigManager.getInstance().getConfigItem("vcpip2_port2", "vcp_port2 not found");

	// String vcp_ip1="192.168.1.154";
	// int vcp_port1=7100;
	// String vcp_ip2 = "192.168.1.4";
	// String vcp_ip2="192.168.1.154";
	// int vcp_port2=7200;

	public SMSMOtoVCP() {
		xtsms = new Message();
		xtdeliverAck = new SmDeliverAckResult();
		// xtdeliver = new sm_deliver();
		xtconn = new ConnDesc();
		// smsmo = new smsmo();
	}

	public String sendMosmsToVcp(SMSMO smsmo) {

		String stat = "-1";
		try {

			logger.debug("send cpn:" + smsmo.getMOCpn());
			logger.debug("send spcode:" + smsmo.getMOSpCode());
			logger.debug("send serverAction:" + smsmo.getMOServerAction());
			logger.debug("send LinkID:" + smsmo.getMOLinkID());

			xtdeliver = new SmDeliver();
			xtdeliver.setMobileCode(smsmo.getMOCpn());
			xtdeliver.setMobileType(smsmo.getMOCpnType());
			xtdeliver.setGameCode(smsmo.getMOServerName());
			xtdeliver.setActionCode(smsmo.getMOServerAction());
			xtdeliver.setSpCode(smsmo.getMOSpCode());
			xtdeliver.setIsmgCode(smsmo.getMOIsmgID());
			xtdeliver.setLinkID(smsmo.getMOLinkID());
			xtdeliver.setMsgId(smsmo.getMOMsgId());

			logger.debug("开始连接...发送mo消息....");

			int vcpID = smsmo.getMOVcpID();
			logger.debug("派发给的vcpID:" + vcpID);

			switch (vcpID) {
			case 0:
				logger.debug(vcpIp1);
				xtsms.connectToServer(vcpIp1, Integer.parseInt(vcpPort1), xtconn); // 连接服务器
				break;
			case 1:
				logger.debug(vcpIp1);
				xtsms.connectToServer(vcpIp1, Integer.parseInt(vcpPort2), xtconn); // 连接服务器
				break;
			case 2:
				logger.debug(vcpIp1);
				xtsms.connectToServer(vcpIp1, Integer.parseInt(vcpPort3), xtconn);
				// xtsms.connectToServer(vcpIp2,Integer.parseInt(vcp_port2),xtconn);
				break;
			case 3:
				logger.debug(vcpIp1);
				xtsms.connectToServer(vcpIp1, Integer.parseInt(vcpPort4), xtconn);
				break;
			default:
				logger.debug(vcpIp1);
				xtsms.connectToServer(vcpIp1, Integer.parseInt(vcpPort1), xtconn); // 连接服务器
				break;
			/*
			 * case 1: System.out.println(vcpIp1);
			 * xtsms.connectToServer(vcpIp1,Integer.parseInt(vcp_port1),
			 * xtconn); // 连接服务器 break; case 2: System.out.println(vcpIp2);
			 * xtsms.connectToServer(vcpIp2,Integer.parseInt(vcp_port2),
			 * xtconn); break; default: System.out.println(vcpIp1);
			 * xtsms.connectToServer(vcpIp1,Integer.parseInt(vcp_port1),
			 * xtconn); // 连接服务器 break;
			 */
			}

			xtsms.sendSmDeliver(xtconn, xtdeliver); // 提交信息
			xtsms.readPa(xtconn);// 读取返回

			stat = xtdeliverAck.getAckStat();
			logger.debug("stat:" + stat);
			logger.debug("xtconn:" + xtconn);
			xtsms.disConnectFromServer(xtconn);
		} catch (Exception e) {
			// Logger myLogger = Logger.getLogger("MsgSendLogger");
			// Logger mySonLogger = Logger.getLogger("myLogger.mySonLogger");
			// PropertyConfigurator.configure("mo2vcplog4j.properties");
			// myLogger.info(FormatSysTime.getCurrentTimeA() + " exception
			// mo2vcp--Exception:" + e.toString());
			logger.error("断开连接", e);
			e.printStackTrace();
		}
		return stat;
	}
}
