package org.x;
/*
 * Created on 2006-11-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import org.apache.log4j.Logger;

import com.xiangtone.sql.Mysqldb;

public class SMSReport {
	private final static Logger LOG = Logger.getLogger(SMSReport.class);
	String ismgid;
	String msgid;
	String destcpn;
	String spcode;
	String src_cpn;
	String sub_time;
	String done_time;
	String linkId;
	Mysqldb mydb;
	int stat_dev;
	String statDetail;

	// FileWriter fw = null;
	// StringBuffer sb;
	public SMSReport() {
		// String file_name = "";
		// file_name = TimeTools.get_month();//取得系统当月时间用于作为日志的文件。
		mydb = new Mysqldb();
		/*
		 * try{
		 * 
		 * //fw = new FileWriter("/home/smsapp/smsapp/CMPP/log/" +
		 * file_name+".txt",true); //sb = new StringBuffer(); }catch(IOException e){
		 * System.out.println(e.toString()); }
		 */

	}

	public void insertReportLog() {

		String str_sql = "insert into sms_reportlog(ismgid,msg_id,linkid,spcode,dest_cpn,src_cpn,submit_time,done_time,stat,stat_msg) values('"
				+ ismgid + "','" + msgid + "','" + linkId + "','" + spcode + "','" + destcpn + "','" + src_cpn + "','"
				+ sub_time + "','" + done_time + "','" + stat_dev + "','" + statDetail + "')";
		String tempstr_sql = "insert into sms_tempreportlog(ismgid,msg_id,linkid,spcode,dest_cpn,src_cpn,submit_time,done_time,stat,stat_msg) values('"
				+ ismgid + "','" + msgid + "','" + linkId + "','" + spcode + "','" + destcpn + "','" + src_cpn + "','"
				+ sub_time + "','" + done_time + "','" + stat_dev + "','" + statDetail + "')";
		String companystr_sql = "insert into companysms_reportlog(ismgid,msg_id,linkid,spcode,dest_cpn,src_cpn,submit_time,done_time,stat,stat_msg) values('"
				+ ismgid + "','" + msgid + "','" + linkId + "','" + spcode + "','" + destcpn + "','" + src_cpn + "','"
				+ sub_time + "','" + done_time + "','" + stat_dev + "','" + statDetail + "')";

		LOG.debug("insertReportLog:" + str_sql);
		// System.out.println(str_sql);
		// System.out.println(tempstr_sql);

		/*
		 * sb.append("ismg:" + ismgid + "."); sb.append("msgid:" + msgid + ".");
		 * sb.append("destcpn:" + destcpn + "."); sb.append("spcode:" + spcode +
		 * "."); sb.append("srcpn:" + src_cpn + "."); sb.append("subtime:" +
		 * sub_time + "."); sb.append("donetime:" + done_time + ".");
		 * sb.append("stat:" + stat_dev + ".");
		 */
		try {

			mydb.execUpdate(str_sql);
			mydb.execUpdate(tempstr_sql);
			mydb.execUpdate(companystr_sql);
			// System.out.println("start to insert temprept");
			// mydb.execUpdate(tempstr_sql);
			// fw.write(sb.toString());tempstr_sql
			// fw.flush();
			// fw.close();
			mydb.close();
			// ReportHandle tempReportLog = new ReportHandle();
			// tempReportLog.logReport(str_sql);
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			try {
				mydb.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void setReport_ismgID(String _ismgid) {
		this.ismgid = _ismgid;
	}

	public void setReport_msgID(String _smgid) {
		this.msgid = _smgid;
	}

	public void setReport_destCpn(String _destcpn) {
		this.destcpn = _destcpn;
	}

	public void setReport_spCode(String _spcode) {
		this.spcode = _spcode;
	}

	public void setReport_srcCpn(String _strcpn) {
		this.src_cpn = _strcpn;
	}

	public void setReport_submitTime(String _subtime) {
		this.sub_time = _subtime;
	}

	public void setReport_doneTime(String _donetime) {
		this.done_time = _donetime;
	}

	public void setReport_stat(int _statdev) {
		this.stat_dev = _statdev;
	}

	public void setReport_linkId(String linkId) {
		this.linkId = linkId;
	}

	public void setReport_msg(String reportMsg) {
		this.statDetail = reportMsg;
	}
	/*
	 * str_cpn = str_cpn.substring(2,13); smsreport.setReport_ismgID(_ismgID);
	 * smsreport.setReport_msgID(msg_id.trim());
	 * smsreport.setReport_destCpn(dest_cpn);
	 * smsreport.setReport_spCode(str_spcode);
	 * smsreport.setReport_srcCpn(str_cpn);
	 * smsreport.setReport_submitTime(submit_time);
	 * smsreport.setReport_doneTime(done_time);
	 * smsreport.setReport_stat(stat_dev); smsreport.saveReportLog();
	 * smsreport.insertReportLog();
	 */

}
