package org.x;

import org.apache.log4j.Logger;

import com.xiangtone.sms.api.ConnDesc;
import com.xiangtone.sms.api.Message;
import com.xiangtone.sms.api.SmDeliver;
import com.xiangtone.sms.api.SmDeliverAckResult;
import com.xiangtone.util.ConfigManager;

public class SMSMOtoVCP {
	private static Logger logger = Logger.getLogger(SMSMOtoVCP.class);
	public Message xtsms;
	public SmDeliver xtdeliver;
	public SmDeliverAckResult xtDeliverAck;
	public ConnDesc xtconn;

	String vcpIp1 = (String) ConfigManager.getInstance().getConfigData("vcp_ip1", "vcp_ip1 not found");
	String vcpPort1 = (String) ConfigManager.getInstance().getConfigData("vcp_port1", "vcp_port1 not found");
	String vcpIp2 = (String) ConfigManager.getInstance().getConfigData("vcp_ip2", "vcp_ip2 not found");
	String vcpPort2 = (String) ConfigManager.getInstance().getConfigData("vcp_port2", "vcp_port2 not found");

	public SMSMOtoVCP() {
		xtsms = new Message();
		xtDeliverAck = new SmDeliverAckResult();
		// xtdeliver = new sm_deliver();
		xtconn = new ConnDesc();
		// smsmo = new smsmo();
	}

	public String sendMoSmsToVcp(SMSMO smsmo) {
		String stat = "-1";
		try {
//			logger.debug("send cpn:" + smsmo.getCpn()+", send spcode:" + smsmo.getSpCode()+", send serverAction:" + smsmo.getServerAction()+", send serverAction:" + smsmo.getLinkID());

			xtdeliver = new SmDeliver();
			xtdeliver.setMobileCode(smsmo.getCpn());
			xtdeliver.setMobileType(smsmo.getCpnType());
			xtdeliver.setGameCode(smsmo.getServerName());
			xtdeliver.setActionCode(smsmo.getServerAction());
			xtdeliver.setSpCode(smsmo.getSpCode());
			xtdeliver.setIsmgCode(smsmo.getIsmgID());
			xtdeliver.setLinkID(smsmo.getLinkID());
			xtdeliver.setMsgId(smsmo.getMsgId());

//			logger.debug("开始连接...发送mo消息....");

			int vcpID = smsmo.getVcpID();
//			logger.debug("派发给的vcpID:" + vcpID);

			switch (vcpID) {
			case 1:
//				logger.debug(vcpIp1);
				xtsms.connectToServer(vcpIp1, Integer.parseInt(vcpPort1), xtconn); // 连接服务器
				break;
			case 2:
//				logger.debug(vcpIp2);
				xtsms.connectToServer(vcpIp2, Integer.parseInt(vcpPort2), xtconn);
				break;
			default:
//				logger.debug(vcpIp1);
				xtsms.connectToServer(vcpIp1, Integer.parseInt(vcpPort1), xtconn); // 连接服务器
				break;
			}

			xtsms.sendSmDeliver(xtconn, xtdeliver); // 提交信息
			xtsms.readPa(xtconn);// 读取返回

			stat = xtDeliverAck.getAckStat();
//			logger.debug("stat:" + stat+", xtconn:" + xtconn);
		} catch (Exception e) {
			logger.error("",e);
		}finally{
			xtsms.disconnectFromServer(xtconn);
		}
		return stat;
	}
}
