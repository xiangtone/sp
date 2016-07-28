package org.x;

//import java.util.Calendar;//add at 08-01-22
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.xiangtone.util.FormatSysTime;
import com.xiangtone.util.IntByteConvertor;

import comsd.commerceware.cmpp.CMPP;
import comsd.commerceware.cmpp.cmppe_submit;
import comsd.commerceware.cmpp.conn_desc;

public class CMPPSend_sd implements CMPPSend {
	public SMSMT mt;
	CMPPSingleConnect instance;
	conn_desc con = new conn_desc();
	CMPP p = new CMPP();

	public CMPPSend_sd(SMSMT _mt) {
		this.mt = _mt;
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
		mt.data_coding = 15; // text
		mt.report_flag = 1;
		byte[] submitContent = mt.sendContent.getBytes();
		send(submitContent);

	}

	/**
	 * sendBinaryPicSMS
	 *
	 */
	public void sendBinaryPicSMS() {
		mt.data_coding = 4; // text
		mt.report_flag = 1;
		byte[] submitContent = mt.sendContent.getBytes();
		send(submitContent);
		/*
		 * mt.report_flag = 1; int servermeida =
		 * Integer.parseInt(mt.serverID.substring(0,2)); int picID =
		 * Integer.parseInt(mt.sendContent); switch(servermeida) { case 11: case 12:
		 * case 30: mt.data_coding =4; mt.tp_udhi = 1; mt.tp_pid =1; break; case 14:
		 * mt.data_coding =245; mt.tp_udhi = 0; mt.tp_pid =0; break; default:
		 * mt.data_coding =4; mt.tp_udhi = 1; mt.tp_pid =1; break; }
		 * sendBinarySMS(picID);
		 */
	}

	/**
	 * sendBinarRingSMS
	 *
	 */
	public void sendBinaryRingSMS() {
		mt.report_flag = 1;
		int servermeida = Integer.parseInt(mt.serverID.substring(0, 2));
		int ringid = Integer.parseInt(mt.sendContent);
		switch (servermeida) {
		case 11:
		case 30:
			mt.data_coding = 4;
			mt.tp_udhi = 1;
			mt.tp_pid = 1;
			break;
		case 12:
		case 14:
			mt.data_coding = 245;
			mt.tp_udhi = 0;
			mt.tp_pid = 0;
			break;
		default:
			mt.data_coding = 4;
			mt.tp_udhi = 1;
			mt.tp_pid = 1;
			break;
		}
		sendBinarySMS(ringid);
	}

	/**
	*
	*
	*/
	private void sendBinarySMS(int _ringid) {
		SMSBinary binary = new SMSBinary();
		byte[][] buffer = binary.getBinaryContent(_ringid, mt.vcpID);
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
			mt.data_coding = 15;
			mt.tp_udhi = 0;
			mt.sendContent = "test";
			byte[] errorPicSend = mt.sendContent.getBytes();
			mt.infoFee = "0";
			mt.feeType = "01";
			mt.feeCode = "BZ";
			send(errorPicSend); // �����ı�
		}
	}

	public void sendTextSMCSMS() {
		try {
			mt.data_coding = 15; // text
			mt.report_flag = 2; // smc
			byte[] submitContent = mt.sendContent.getBytes();
			send(submitContent);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void send(byte[] submitContent) {
		////////////////// ��־��¼/////////////
		/*
		 * Logger myLogger = Logger.getLogger("MsgSendLogger"); Logger mySonLogger =
		 * Logger.getLogger("myLogger.mySonLogger");
		 * PropertyConfigurator.configure("log4j.properties");
		 */
		////////////////////////////////////////////////////////////

		System.out.println("----send sms by qw's gateway------");
		System.out.println("mt.cpn:" + mt.feeCpn);
		System.out.println("mt.feeCode:" + mt.feeCode);
		System.out.println("mt.infoFee:" + mt.infoFee);
		System.out.println("-----------------------------------");

		try {
			// msgId
			String temp_msgId = "0216134957";// mt.submitMsgID;
			// System.out.println("msgid value is at sd" + temp_msgId);
			// System.out.println(temp_msgId);
			long intMsgId = (new Long(temp_msgId)).longValue();
			System.out.println(intMsgId);
			byte[] msg_id = new byte[8];// ��ʼֵΪ0

			IntByteConvertor.putLong(msg_id, intMsgId, 0);

			byte pk_total = 1;
			byte pk_number = 1;
			byte registered_delivery = (byte) 1;// mt.report_flag;
			byte msg_level = 5;

			byte[] service_code = new byte[10];
			byte[] feecode = mt.feeCode.getBytes();
			System.arraycopy(feecode, 0, service_code, 0, feecode.length);
			byte fee_usertype = 3;

			byte[] fee_terminal_id = new byte[32];
			byte fee_terminal_type;// add at 061123
			String fee_cpn = mt.feeCpn;
			if (fee_cpn.length() < 13) {
				fee_cpn = "86" + mt.feeCpn;
			}
			byte[] feecpn = fee_cpn.getBytes();
			System.arraycopy(feecpn, 0, fee_terminal_id, 0, feecpn.length);

			byte tp_pid = (byte) mt.tp_pid;
			byte tp_udhi = (byte) mt.tp_udhi;
			byte msg_fmt = (byte) mt.data_coding;

			byte[] msg_src = new byte[6];
			byte[] icpID = SMSIsmgInfo.qw_icpID.getBytes();

			System.arraycopy(icpID, 0, msg_src, 0, icpID.length);

			byte[] feetype = new byte[2];
			byte[] feetypeTemp = mt.feeType.getBytes();
			System.arraycopy(feetypeTemp, 0, feetype, 0, feetypeTemp.length);

			byte[] fee = new byte[6];
			byte[] infofee = mt.infoFee.getBytes();
			System.arraycopy(infofee, 0, fee, 0, infofee.length);

			byte[] valid_time = new byte[17];

			byte[] at_time = new byte[17];

			// byte[] src_terminal_id = new byte[21];
			byte[] src_terminal_id = new byte[21];
			if (mt.spCode == null || mt.spCode.equals("")) {
				mt.spCode = SMSIsmgInfo.qw_ismg_spCode;
			}
			byte[] spcode = mt.spCode.getBytes();
			System.arraycopy(spcode, 0, src_terminal_id, 0, spcode.length);

			byte[][] dest_terminal_id = new byte[1][32];
			byte dest_terminal_type;
			String dest_cpn = mt.destCpn;
			if (dest_cpn.length() < 13) {
				dest_cpn = "86" + mt.destCpn;
			}
			byte[] destcpn = dest_cpn.getBytes();
			System.arraycopy(destcpn, 0, dest_terminal_id[0], 0, destcpn.length);

			byte destusr_tl = 1;

			byte[] msg_content = submitContent;
			int sm_len = 0;
			sm_len = java.lang.reflect.Array.getLength(msg_content);
			byte msg_length = (byte) sm_len;
			// byte[] reserve = new byte[8];
			// add at 061123
			byte[] linkid;
			if (mt.linkID == null) {
				linkid = new byte[20];
			} else {
				linkid = new byte[20];
				linkid = mt.linkID.getBytes();
			}

			// System.arraycopy(spcode,0,src_terminal_id,0,spcode.length);
			byte feecpn_type = (byte) mt.cpnType;
			cmppe_submit sub = new cmppe_submit();// �ύ���Ŷ�
			sub.set_msgid(msg_id);
			sub.set_pktotal(pk_total);
			sub.set_pknumber(pk_number);
			sub.set_registered(registered_delivery);
			sub.set_msglevel(msg_level);
			sub.set_serviceid(service_code);
			sub.set_feeusertype(fee_usertype);
			sub.set_feeterminalid(fee_terminal_id);
			sub.set_feeterminaltype(feecpn_type);
			sub.set_tppid(tp_pid);
			sub.set_tpudhi(tp_udhi);
			sub.set_msgfmt(msg_fmt);
			sub.set_msgsrc(msg_src);
			sub.set_feetype(feetype);
			sub.set_feecode(fee);
			sub.set_validtime(valid_time);
			sub.set_attime(at_time);
			sub.set_srcterminalid(src_terminal_id);
			sub.set_destusrtl(destusr_tl);
			sub.set_destterminalid(dest_terminal_id);
			sub.set_destterminaltype(feecpn_type);// add at 061123
			sub.set_feeterminaltype(feecpn_type);
			sub.set_msglength(msg_length);
			sub.set_msgcontent(msg_content);
			sub.set_linkid(linkid);// add at 061123
			// sub.set_reserve(reserve);

			//
			if (instance == null) {
				instance.destroy();
				instance = CMPPSingleConnect.getInstance();

			}
			con = instance.con;
			System.out.println(con.sock);
			// int seq = -1;
			// System.out.println("before ");

			int seq = p.cmpp_submit(con, sub);
			mt.submitSeq = seq;
			mt.insertMTLog();
			System.out.println("seq:" + seq);
			System.out.println("�ύ�ɹ�!");
			// Thread.currentThread().sleep(100);
			// myLogger.info(FormatSysTime.getCurrentTimeA() + " send msg--spcode:" +
			// mt.spCode + " cpn:" + mt.feeCpn + " linkId:" + mt.linkID + " fee:" +
			// new String(fee) + " service_code:" + new String(service_code) + "
			// content:" + new String(msg_content));

		} catch (Exception e) {
			Logger myLogger = Logger.getLogger("MsgSendLogger");
			Logger mySonLogger = Logger.getLogger("myLogger.mySonLogger");
			PropertyConfigurator.configure("log4j.properties");
			System.out.println("SMS_sd.java :");
			// System.out.println(e.toString());
			myLogger.info(FormatSysTime.getCurrentTimeA() + " wq send exception:" + e.toString());
			e.printStackTrace();

		}
	}

}