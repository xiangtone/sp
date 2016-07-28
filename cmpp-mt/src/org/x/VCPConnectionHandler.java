package org.x;
/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/

import java.net.Socket;

import com.xiangtone.sms.api.conn_desc;
import com.xiangtone.sms.api.message;
import com.xiangtone.sms.api.sm_submit_result;
import com.xiangtone.util.FormatSysTime;

public class VCPConnectionHandler implements Runnable {
	/**
	*
	*
	*/
	protected Socket socketToHandle;
	protected conn_desc con;
	protected message mess;
	protected sm_submit_result sm;
	protected SMSFactory myFactory;
	// protected SMSMT mt;
	protected SMSCost cost;
	// protected SMSMonth month;
	// protected SMSCard card;
	protected SMSMO mo;

	/**
	*
	*
	*/
	public VCPConnectionHandler(Socket aSocketToHandle) {
		socketToHandle = aSocketToHandle;
		con = new conn_desc(socketToHandle);

		// mt = new SMSMT();
		myFactory = new SMSFactory();
		sm = new sm_submit_result();
		mess = new message();
		cost = new SMSCost();
		// month =new SMSMonth();
		// card = new SMSCard();
		mo = new SMSMO();
		// strart();
	}

	/**
	*
	*
	*/
	public void run() {
		try {

			mess.readPa(con); // ��ȡ�ύ��Ϣ
			String stat = sm.get_stat();
			System.out.println("stat....:" + stat);
			//
			// stat = "00";
			if (stat.equals("00")) {

				////////////////////////////////////////
				System.out.println(":::::::::::::");
				System.out.println(":::::::::::::");
				System.out.println(":::::::::::::");
				System.out.println("new message");
				System.out.println(":::::::::::::");
				System.out.println(":::::::::::::");
				System.out.println(":::::::::::::");
				///////////////////////////////////////
				String _corpid = "00";
				int _vcpid = 1;
				_vcpid = Integer.parseInt(sm.get_vcp_id()); // get_vcpid();
				String _spcode = sm.get_server_code();// �ط���û��
																							// //sm.get_server_code();

				int _mediatype = Integer.parseInt(sm.get_media_type());
				String _destcpn = sm.get_dest_cpn();
				String _feecpn = sm.get_fee_cpn();
				String _infofee = sm.get_fee_code();
				String _feetype = sm.get_fee_type();
				String s_feetype = _feetype;
				String _serverid = sm.get_server_type();
				String _content = new String(sm.get_content());
				String _ismgid = sm.get_prov_code();
				/////////// add at 061123
				String _linkid = sm.get_linkid();
				int _feecpntype = sm.get_feecpn_type();
				String msgId = sm.get_msgId();
				System.out.println("linkid is:" + _linkid);
				System.out.println("feecpntype:" + _feecpntype);
				System.out.println("msgid is:" + msgId);
				System.out.println("destcpn:" + _destcpn);
				System.out.println("_feecpn:" + _feecpn);

				////////////////////////
				if (_ismgid == null || _ismgid.equals(""))
					_ismgid = mo.getImsgID(_feecpn);
				if (msgId == null) {
					msgId = "";
				}
				String _servercode = "";
				String _servername = "";
				SMSMT mt = new SMSMT();
				cost.lookupInfofeeByServerID_IOD(_serverid);
				_infofee = cost.getCost_infoFee();
				_servercode = cost.getCost_serverCode_IOD();
				_feetype = cost.getCost_feeType();
				_servername = cost.getCost_serverName();

				mt.setMT_vcpID(_vcpid);
				mt.setMT_ismgID(_ismgid);
				mt.setMT_spCode(_spcode);
				mt.setMT_corpID(_corpid);
				mt.setMT_destCpn(_destcpn);
				mt.setMT_feeCpn(_feecpn);
				mt.setMT_serverID(_serverid);
				mt.setMT_serverName(_servername);
				mt.setMT_infoFee(_infofee);
				mt.setMT_feeCode(_servercode);
				mt.setMT_feeType(_feetype);
				mt.setMT_sendContent(_content);
				mt.setMT_mediaType(_mediatype);
				mt.setMT_sendTime(FormatSysTime.getCurrentTimeA());
				mt.setMT_linkID(_linkid);
				mt.setMT_cpnType(_feecpntype);
				mt.setMT_submitMsgID(msgId);

				/////////////////
				/*
				 * System.out.println("////////////////");
				 * System.out.println("////////////////");
				 * System.out.println("////////////////");
				 * System.out.println("////////////////");
				 * System.out.println("_serverid:" + _serverid);
				 * System.out.println("cpntype is:" + mt.cpnType);
				 * System.out.println("linkid is:" + mt.linkID);
				 * System.out.println("content is:" + mt.sendContent);
				 * System.out.println("msgId is:" + mt.submitMsgID);
				 * System.out.println("////////////////");
				 * System.out.println("////////////////");
				 * System.out.println("////////////////");
				 * System.out.println("////////////////");
				 */
				//////////////////
				if (msgId.startsWith("auto")) {
					mt.setMT_feeType("01");
					mt.setMT_serverID("1000");
					mt.setMT_infoFee("0");
					mt.setMT_serverName("AutoHELP");
					mt.setMT_feeCode("HELP");
				}
				// mt.insertMTLog(); //������־
				///////////////////
				CMPPSend mysms = myFactory.createSMS(_ismgid, mt);
				switch (_mediatype) {
				case 1:
					mysms.sendTextSMS(); // �����ı�
					break;
				case 2:
					mysms.sendBinaryPicSMS(); // ����ͼƬ
					break;
				case 3:
					mysms.sendBinaryRingSMS();
					break;

				default:
					mysms.sendTextSMS(); // �����ı�
					break;
				}
				System.out.println("*************************mt.feeType:" + mt.feeType);
				System.out.println("*************************s_feetype:" + _feetype);
				System.out.println("*************************_feetype:" + _feetype);

			} // end if

		} // end try
		catch (Exception e) {
			System.out.println("Error handling a client: " + e);
			e.printStackTrace();
		}

	}
	/*
	 * public static void main(String[] args){ try{ SMSIsmgInfo info = new
	 * SMSIsmgInfo("config.ini"); info.loadParam(); info.printParam();
	 * SMSActiveTest sdc = new SMSActiveTest(); new Thread(sdc).start(); SMSRecive
	 * sr = new SMSRecive(); new Thread(sr).start(); }catch(Exception e){
	 * System.out.println(">>>>>>ƽ̨����"); e.printStackTrace(); } VCPServer
	 * vcpserver = new VCPServer(8001); vcpserver.start(); //VCPConnectionHandler
	 * vcphanlder = new VCPConnectionHandler(); }
	 */
}
