package org.x;

import com.xiangtone.sms.api.ConnDesc;
import com.xiangtone.sms.api.Message;
import com.xiangtone.sms.api.SmDeliver;
import com.xiangtone.sms.api.SmDeliverAckResult;
import com.xiangtone.util.ConfigManager;

public class SMSMOtoVCP {

	public Message xtsms;
	public SmDeliver xtdeliver;
	public SmDeliverAckResult xtdeliver_ack;
	public ConnDesc xtconn;
	// public SMSMO smsmo;

	String vcp_ip1 = (String) ConfigManager.getInstance().getConfigData("vcp_ip1", "vcp_ip1 not found");
	String vcp_port1 = (String) ConfigManager.getInstance().getConfigData("vcp_port1", "vcp_port1 not found");
	String vcp_ip2 = (String) ConfigManager.getInstance().getConfigData("vcp_ip2", "vcp_ip2 not found");
	String vcp_port2 = (String) ConfigManager.getInstance().getConfigData("vcp_port2", "vcp_port2 not found");

	// String vcp_ip1="192.168.1.154";
	// int vcp_port1=7100;
	// String vcp_ip2 = "192.168.1.4";
	// String vcp_ip2="192.168.1.154";
	// int vcp_port2=7200;

	public SMSMOtoVCP() {
		xtsms = new Message();
		xtdeliver_ack = new SmDeliverAckResult();
		// xtdeliver = new sm_deliver();
		xtconn = new ConnDesc();
		// smsmo = new smsmo();
	}

	public String send_mosms_to_vcp(SMSMO smsmo) {
		String stat = "-1";
		try {
			System.out.println("send..cpn:" + smsmo.getMO_cpn());
			System.out.println("send..spcode:" + smsmo.getMO_spCode());
			System.out.println("send.serverAction:" + smsmo.getMO_serverAction());
			System.out.println("send.serverAction:" + smsmo.getMO_linkID());

			xtdeliver = new SmDeliver();
			xtdeliver.set_mobileCode(smsmo.getMO_cpn());
			xtdeliver.set_mobileType(smsmo.getMO_cpnType());
			xtdeliver.set_gameCode(smsmo.getMO_serverName());
			xtdeliver.set_actionCode(smsmo.getMO_serverAction());
			xtdeliver.set_spCode(smsmo.getMO_spCode());
			xtdeliver.set_ismgCode(smsmo.getMO_ismgID());
			xtdeliver.set_linkID(smsmo.getMO_linkID());
			xtdeliver.set_MsgId(smsmo.getMO_msgId());

			System.out.println("开始连接...发送mo消息....");

			int vcpID = smsmo.getMO_vcpID();
			System.out.println("派发给的vcpID:" + vcpID);

			switch (vcpID) {
			case 1:
				System.out.println(vcp_ip1);
				xtsms.connect_to_server(vcp_ip1, Integer.parseInt(vcp_port1), xtconn); // 连接服务器
				break;
			case 2:
				System.out.println(vcp_ip2);
				xtsms.connect_to_server(vcp_ip2, Integer.parseInt(vcp_port2), xtconn);
				break;
			default:
				System.out.println(vcp_ip1);
				xtsms.connect_to_server(vcp_ip1, Integer.parseInt(vcp_port1), xtconn); // 连接服务器
				break;
			}

			xtsms.send_sm_deliver(xtconn, xtdeliver); // 提交信息
			xtsms.readPa(xtconn);// 读取返回

			stat = xtdeliver_ack.getAckStat();
			System.out.println("stat:::::" + stat);
			System.out.println("xtconn::::" + xtconn);
			xtsms.disconnect_from_server(xtconn);
		} catch (Exception e) {
			System.out.println("异常：断开连接" + e.toString());
			e.printStackTrace();
		}
		return stat;
	}
}
