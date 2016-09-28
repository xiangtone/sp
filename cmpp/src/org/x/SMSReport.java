package org.x;
/*
 * Created on 2006-11-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import org.apache.log4j.Logger;

import com.xiangtone.util.DBForLocal;

public class SMSReport {
	private final static Logger LOG = Logger.getLogger(SMSReport.class);
	String ismgId;
	String msgId;
	String destCpn;
	String spCode;
	String srcCpn;
	String subTime;
	String doneTime;
	String linkId;
	int statDev;
	String statDetail;
	DBForLocal db=new DBForLocal();
	// FileWriter fw = null;
	// StringBuffer sb;
	public SMSReport() {
		// String file_name = "";
		// file_name = TimeTools.get_month();//取得系统当月时间用于作为日志的文件。
		/*
		 * try{
		 * 
		 * //fw = new FileWriter("/home/smsapp/smsapp/CMPP/log/" +
		 * file_name+".txt",true); //sb = new StringBuffer(); }catch(IOException e){
		 * System.out.println(e.toString()); }
		 */

	}

	public void insertReportLog() {

		String strSql = "insert into sms_reportlog(ismgid,msg_id,linkid,spcode,dest_cpn,src_cpn,submit_time,done_time,stat,stat_msg) values('"
				+ ismgId + "','" + msgId + "','" + linkId + "','" + spCode + "','" + destCpn + "','" + srcCpn + "','"
				+ subTime + "','" + doneTime + "','" + statDev + "','" + statDetail + "')";
		String tempStrSql = "insert into sms_tempreportlog(ismgid,msg_id,linkid,spcode,dest_cpn,src_cpn,submit_time,done_time,stat,stat_msg) values('"
				+ ismgId + "','" + msgId + "','" + linkId + "','" + spCode + "','" + destCpn + "','" + srcCpn + "','"
				+ subTime + "','" + doneTime + "','" + statDev + "','" + statDetail + "')";
		String companyStrSql = "insert into companysms_reportlog(ismgid,msg_id,linkid,spcode,dest_cpn,src_cpn,submit_time,done_time,stat,stat_msg) values('"
				+ ismgId + "','" + msgId + "','" + linkId + "','" + spCode + "','" + destCpn + "','" + srcCpn + "','"
				+ subTime + "','" + doneTime + "','" + statDev + "','" + statDetail + "')";

		/*
		 * sb.append("ismg:" + ismgid + "."); sb.append("msgid:" + msgid + ".");
		 * sb.append("destcpn:" + destcpn + "."); sb.append("spcode:" + spcode +
		 * "."); sb.append("srcpn:" + src_cpn + "."); sb.append("subtime:" +
		 * sub_time + "."); sb.append("donetime:" + done_time + ".");
		 * sb.append("stat:" + stat_dev + ".");
		 */
		try {
			LOG.info(strSql);
			db.executeUpdate(strSql);
			LOG.info(tempStrSql);
			db.executeUpdate(tempStrSql);
			LOG.info(companyStrSql);
			db.executeUpdate(companyStrSql);
			// System.out.println("start to insert temprept");
			// mydb.execUpdate(tempstr_sql);
			// fw.write(sb.toString());tempstr_sql
			// fw.flush();
			// fw.close();
			// ReportHandle tempReportLog = new ReportHandle();
			// tempReportLog.logReport(str_sql);
		} catch (Exception e) {
			LOG.error("",e);
		} finally {
			db.close();
		}

	}

	public void setIsmgID(String ismgid) {
		this.ismgId = ismgid;
	}

	public void setMsgID(String smgid) {
		this.msgId = smgid;
	}

	public void setDestCpn(String destcpn) {
		this.destCpn = destcpn;
	}

	public void setSpCode(String spcode) {
		this.spCode = spcode;
	}

	public void setSrcCpn(String strcpn) {
		this.srcCpn = strcpn;
	}

	public void setSubmitTime(String subtime) {
		this.subTime = subtime;
	}

	public void setDoneTime(String donetime) {
		this.doneTime = donetime;
	}

	public void setStat(int statdev) {
		this.statDev = statdev;
	}

	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}

	public void setMsg(String reportMsg) {
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
