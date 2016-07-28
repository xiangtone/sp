package org.x;
/*

 * Created on 2006-11-15

 *

 * TODO To change the template for this generated file go to

 * Window - Preferences - Java - Code Style - Code Templates

 */

import org.apache.log4j.Logger;

import com.xiangtone.util.FormatSysTime;
import com.xiangtone.util.IntByteConvertor;

import comsd.commerceware.cmpp.CMPP;
import comsd.commerceware.cmpp.cmppe_deliver_result;
import comsd.commerceware.cmpp.cmppe_result;
import comsd.commerceware.cmpp.cmppe_submit_result;
import comsd.commerceware.cmpp.conn_desc;

public class SMSRecive implements Runnable {

	CMPP p = new CMPP();

	// conn_desc con = new conn_desc();

	cmppe_deliver_result cd = new cmppe_deliver_result();

	cmppe_submit_result sr = new cmppe_submit_result();

	cmppe_result cr = new cmppe_result();

	public SMSoperate handle;

	public CMPPSingleConnect cmppcon = CMPPSingleConnect.getInstance();;
	conn_desc con = cmppcon.con;
	public static final String ISMGID = "01";

	public SMSRecive()

	{

		handle = new SMSoperate();

		// cmppcon = CMPPSingleConnect.getInstance();

		// con = cmppcon.con;

	}

	public void run() {

		////////////////// ��־��¼/////////////

		// ThreadPoolManager moPoolManger = new ThreadPoolManager(500,this.ISMGID);

		//////////////////////////////////////

		while (true) {

			try {

				// SMSoperate handle = new SMSoperate();

				p.readPa(con);
				Thread.currentThread().sleep(100);
				if (sr.flag == 0) // submit resp

				{

					sr.flag = -1; // ��λ

					String str_resp_msgId = IntByteConvertor.getLong(sr.msg_id, 0) + "";// MyTools.Bytes2HexString(sr.msg_id);MyTools.Bytes2HexString(sr.msg_id);//new
																																							// String(sr.msg_id2)//IntByteConvertor.getLong(sr.msg_id,0)
																																							// +
																																							// "";//MyTools.Bytes2HexString(sr.msg_id);

					int i_resp_result = sr.result;

					int i_resp_seq = sr.seq;

					System.out.println("sr.result:" + i_resp_result);

					System.out.println("sr.seq:" + i_resp_seq);

					System.out.println("sr.msg_id:" + str_resp_msgId);

					handle.receiveSubmitResp(this.ISMGID, (int) i_resp_seq, (String) str_resp_msgId, (int) i_resp_result);

					// handle.receiveSubmitResp(this.ISMGID,(int)i_resp_seq,(String)str_resp_msgId,(int)i_resp_result);

				}

				if (cd.STAT == 0) // ˵������Ϣ������

				{

					cd.STAT = -1;

					System.out.println("����Ϣ������...");

					/////////////////////////////

					// moPoolManger.process1(cd);

					/////////////////////// �����ع��������̳߳� date:2008-11-26 16:09

					String msg_id = IntByteConvertor.getLong(cd.msg_id, 0) + "";// MyTools.Bytes2HexString(cd.get_msg_id());

					String str_ismgid = this.ISMGID;

					String str_spcode = cd.getSPCode();

					String str_cpn = cd.getCpn();

					int cpn_type = cd.getsrcType();// add 061121

					int i_len = cd.getLen();

					int i_fmt = cd.getFmt();

					int i_tp_udhi = cd.get_tp_udhi();

					String str_svc_type = cd.getServerType();

					String link_id = cd.getLinkId();

					System.out.println("content:" + cd.getMessage());

					byte[] str_content = cd.getMessage();

					int i_report_flag = cd.getRegistered_delivery();

					System.out.println("i_report_flag:" + i_report_flag);

					System.out.println("........................");

					System.out.println("........................");

					System.out.println("........................");

					System.out.println("str_spcode:" + str_spcode);

					System.out.println("........................");

					System.out.println("........................");

					System.out.println("........................");

					// myLogger.info(FormatSysTime.getCurrentTimeA() + " new msg--spcode:"
					// + str_spcode +" cpn:" + str_cpn.trim() + " linkId:" + link_id + "
					// content:" + new String(str_content));

					if (i_report_flag == 1)

					{

						System.out.println("״̬������Ϣ....");

						String report_dest_cpn = cd.get_dest_cpn();

						msg_id = IntByteConvertor.getLong(cd.msg_id2, 0) + "";// MyTools.Bytes2HexString(cd.get_msg_id());

						String submit_time = cd.get_submit_time();

						String done_time = cd.get_done_time();

						String stat2 = cd.get_stat();

						System.out.println("stat2:" + stat2);

						// myLogger.info(FormatSysTime.getCurrentTimeA() + "report
						// msg--spcode:" + str_spcode +" cpn:" + report_dest_cpn.trim() + "
						// msgid:" + msg_id + " submit_time:" + submit_time +" done_time:" +
						// done_time + " stat_dev:" + stat2);

						int stat_dev = 0;

						if (stat2.equals("DELIVRD"))

							stat_dev = 0;

						else

							stat_dev = -1;

						handle.receiveReport(this.ISMGID, msg_id, link_id, report_dest_cpn, str_spcode, str_cpn, submit_time,
								done_time, stat_dev, stat2);

						// handle.receiveReport(this.ISMGID,msg_id,report_dest_cpn,str_spcode,str_cpn,submit_time,done_time,stat_dev);

						continue;

					}

					cd.printAll(); // ��ӡmo��Ϣ;

					///////////////////////////////
					///////////////////////////////

					handle.setDeliver_ismgID(str_ismgid);

					handle.setDeliver_msgID(msg_id);

					handle.setDeliver_spCode(str_spcode);

					handle.setDeliver_serverID(str_svc_type);

					handle.setDeliver_fmt(i_fmt);

					handle.setDeliver_srcCpn(str_cpn);

					handle.setDeliver_srcCpnType(cpn_type);

					handle.setDeliver_contentLen(i_len);

					handle.setDeliver_content(str_content);

					handle.setDeliver_linkId(link_id);

					handle.receiveDeliver();

				}

				Thread.currentThread().sleep(100);
			} catch (Exception e) {

				Logger myLogger = Logger.getLogger("MsgSendLogger");

				Logger mySonLogger = Logger.getLogger("myLogger.mySonLogger");

				// PropertyConfigurator.configure("log4j.properties");

				myLogger.info(FormatSysTime.getCurrentTimeA() + " exception msg--Exception:" + e.toString());

				System.out.println(e.toString());

				System.out.println("��������....");

				p.cmpp_disconnect_from_ismg(con);

				cmppcon.destroy();

				cmppcon = null;

				try {

					Thread.currentThread().sleep(30 * 1000);

				} catch (Exception e1) {

				}

				cmppcon = CMPPSingleConnect.getInstance(); // ����

				con = cmppcon.con;

			}

		}

	}

}
