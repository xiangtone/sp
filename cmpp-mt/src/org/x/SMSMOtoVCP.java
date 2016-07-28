package org.x;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.xiangtone.sms.api.conn_desc;
import com.xiangtone.sms.api.message;
import com.xiangtone.sms.api.sm_deliver;
import com.xiangtone.sms.api.sm_deliver_ack_result;
import com.xiangtone.util.ConfigManager;
import com.xiangtone.util.FormatSysTime;

/**
*
*
*/
public class SMSMOtoVCP {

	/**
	*
	*/
	public message xtsms;
	public sm_deliver xtdeliver;
	public sm_deliver_ack_result xtdeliver_ack;
	public conn_desc xtconn;
	// public SMSMO smsmo;

	String vcp_ip1 = (String) ConfigManager.getInstance().getConfigData("vcp_ip1", "vcp_ip1 not found");
	String vcp_port1 = (String) ConfigManager.getInstance().getConfigData("vcp_port1", "vcp_port1 not found");
	String vcp_port2 = (String) ConfigManager.getInstance().getConfigData("vcp_port2", "vcp_port2 not found");
	String vcp_port3 = (String) ConfigManager.getInstance().getConfigData("vcp_port3", "vcp_port2 not found");
	String vcp_port4 = (String) ConfigManager.getInstance().getConfigData("vcp_port4", "vcp_port2 not found");

	String vcp_ip2 = (String) ConfigManager.getInstance().getConfigData("vcp_ip2", "vcp_ip2 not found");
	String vcpip2_port2 = (String) ConfigManager.getInstance().getConfigData("vcpip2_port2", "vcp_port2 not found");

	// String vcp_ip1="192.168.1.154";
	// int vcp_port1=7100;
	// String vcp_ip2 = "192.168.1.4";
	// String vcp_ip2="192.168.1.154";
	// int vcp_port2=7200;

	public SMSMOtoVCP() {
		xtsms = new message();
		xtdeliver_ack = new sm_deliver_ack_result();
		// xtdeliver = new sm_deliver();
		xtconn = new conn_desc();
		// smsmo = new smsmo();
	}

	public String send_mosms_to_vcp(SMSMO smsmo) {

		String stat = "-1";
		try {

			System.out.println("send..cpn:" + smsmo.getMO_cpn());
			System.out.println("send..spcode:" + smsmo.getMO_spCode());
			System.out.println("send.serverAction:" + smsmo.getMO_serverAction());
			System.out.println("send.serverAction:" + smsmo.getMO_linkID());

			xtdeliver = new sm_deliver();
			xtdeliver.set_mobileCode(smsmo.getMO_cpn());
			xtdeliver.set_mobileType(smsmo.getMO_cpnType());
			xtdeliver.set_gameCode(smsmo.getMO_serverName());
			xtdeliver.set_actionCode(smsmo.getMO_serverAction());
			xtdeliver.set_spCode(smsmo.getMO_spCode());
			xtdeliver.set_ismgCode(smsmo.getMO_ismgID());
			xtdeliver.set_linkID(smsmo.getMO_linkID());
			xtdeliver.set_MsgId(smsmo.getMO_msgId());

			System.out.println("��ʼ����...����mo��Ϣ....");

			int vcpID = smsmo.getMO_vcpID();
			System.out.println("�ɷ�����vcpID:" + vcpID);

			switch (vcpID) {
			case 0:
				System.out.println(vcp_ip1);
				xtsms.connect_to_server(vcp_ip1, Integer.parseInt(vcp_port1), xtconn); // ���ӷ�����
				break;
			case 1:
				System.out.println(vcp_ip1);
				xtsms.connect_to_server(vcp_ip1, Integer.parseInt(vcp_port2), xtconn); // ���ӷ�����
				break;
			case 2:
				System.out.println(vcp_ip1);
				xtsms.connect_to_server(vcp_ip1, Integer.parseInt(vcp_port3), xtconn);
				// xtsms.connect_to_server(vcp_ip2,Integer.parseInt(vcp_port2),xtconn);
				break;
			case 3:
				System.out.println(vcp_ip1);
				xtsms.connect_to_server(vcp_ip1, Integer.parseInt(vcp_port4), xtconn);
				break;
			default:
				System.out.println(vcp_ip1);
				xtsms.connect_to_server(vcp_ip1, Integer.parseInt(vcp_port1), xtconn); // ���ӷ�����
				break;
			/*
			 * case 1: System.out.println(vcp_ip1);
			 * xtsms.connect_to_server(vcp_ip1,Integer.parseInt(vcp_port1),xtconn);
			 * //���ӷ����� break; case 2: System.out.println(vcp_ip2);
			 * xtsms.connect_to_server(vcp_ip2,Integer.parseInt(vcp_port2),xtconn);
			 * break; default: System.out.println(vcp_ip1);
			 * xtsms.connect_to_server(vcp_ip1,Integer.parseInt(vcp_port1),xtconn);
			 * //���ӷ����� break;
			 */
			}

			xtsms.send_sm_deliver(xtconn, xtdeliver); // �ύ��Ϣ
			xtsms.readPa(xtconn);// ��ȡ����

			stat = xtdeliver_ack.getAckStat();
			System.out.println("stat:::::" + stat);
			System.out.println("xtconn::::" + xtconn);
			xtsms.disconnect_from_server(xtconn);
		} catch (Exception e) {
			Logger myLogger = Logger.getLogger("MsgSendLogger");
			Logger mySonLogger = Logger.getLogger("myLogger.mySonLogger");
			PropertyConfigurator.configure("mo2vcplog4j.properties");
			myLogger.info(FormatSysTime.getCurrentTimeA() + " exception mo2vcp--Exception:" + e.toString());
			System.out.println("�쳣���Ͽ�����" + e.toString());
			e.printStackTrace();
		}
		return stat;
	}
}
