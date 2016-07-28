package org.x;

import java.sql.ResultSet;

import com.xiangtone.sql.Mysqldb;

/**
 * A class respresenting a set of packet and byte couters. It is bservable to
 * allw it to be watched, but only comment of a class
 */
public class SMSMO {
	/**
	 * Packet counters;
	 * 
	 */
	protected String cpn;
	protected int cpnType;
	protected String spCode; // �ر�����(����)
	protected String spCode_first;
	protected String svcType; // ҵ������
	protected String content; // ��������
	protected String serverName;
	protected String serverID; // ����id
	protected String serverAction = ""; // content�ֽ�1 ,ҵ��ָ��
	protected String deliverTime; // ����ʱ��
	protected String ismgID; // ʡ���ر�־(01����06����08������15ɽ��19�㶫
	protected int vcpID;
	protected int len;
	protected int tp_udhi = 0;
	protected int fmt = 0;
	protected String msgId = "";
	public Mysqldb db;
	private String strSql = "";
	private ResultSet rs = null;
	protected String corpID;
	protected String linkID;

	/**
	 * method of set and get
	 *
	 */
	public String getMO_cpn() {
		return cpn;
	}

	public int getMO_cpnType() {
		return cpnType;
	}

	public String getMO_spCode() {
		return spCode;
	}

	public String getMO_svcType() {
		return svcType;
	}

	public String getMO_content() {
		return content;
	}

	public String getMO_serverName() {
		return serverName;
	}

	public String getMO_serverID() {
		return serverID;
	}

	public String getMO_serverAction() {
		return serverAction;
	}

	public String getMO_deliverTime() {
		return deliverTime;
	}

	public String getMO_ismgID() {
		return ismgID;
	}

	public int getMO_vcpID() {
		return vcpID;
	}

	public String getMO_corpID() {
		return corpID;
	}

	public String getMO_linkID() {
		return linkID;
	}

	public String getMO_msgId() {
		return msgId;
	}

	public void setMO_cpn(String cpn) {
		this.cpn = cpn;
	}

	public void setMo_cpntype(int _cpntype) {
		this.cpnType = _cpntype;
	}

	public void setMO_spCode(String spcode) {
		this.spCode = spcode;
	}

	public void setMO_spCode_first(String spCode_first) {
		this.spCode_first = spCode_first;
	}

	public void setMO_svcType(String svcType) {
		this.svcType = svcType;
	}

	public void setMO_content(String content) {
		this.content = content;
	}

	public void setMO_serverName(String serverName) {
		this.serverName = serverName;
	}

	public void setMO_serverID(String serverID) {
		this.serverID = serverID;
	}

	public void setMO_serverAction(String serverAction) {
		this.serverAction = serverAction;
	}

	public void setMO_deliverTime(String deliverTime) {
		this.deliverTime = deliverTime;
	}

	public void setMO_ismgID(String ismgID) {
		this.ismgID = ismgID;
	}

	public void setMO_vcpID(int vcpID) {
		this.vcpID = vcpID;
	}

	public void setMO_corpID(String _corpID) {
		this.corpID = _corpID;
	}

	public void setMO_linkID(String linkid) {
		this.linkID = linkid;
	}

	///////////// add setMsgId
	public void setMO_msgId(String msgId) {
		this.msgId = msgId;
	}

	/**
	 * Construct Function
	 *
	 */
	public SMSMO() {
		db = new Mysqldb();
	}

	/**
	 * the first methods of class insert mo log
	 */
	public void insertMOLog() {
		try {
			strSql = "insert into sms_molog set ";
			strSql += " vcpid=" + vcpID;
			strSql += ",ismgid='" + ismgID + "'";
			strSql += ",corpid='" + corpID + "'";
			strSql += ",spcode_first='" + spCode_first + "'";
			strSql += ",content='" + content + "'";
			strSql += ",spcode='" + spCode + "'";
			strSql += ",msgid='" + msgId + "'";
			strSql += ",cpn='" + cpn + "'";
			strSql += ",serverid='" + serverID + "'";
			strSql += ",servername='" + serverName + "'";
			strSql += ",serveraction='" + serverAction + "'";
			strSql += ",delivertime='" + deliverTime + "'";
			strSql += ",linkid='" + linkID + "'";
			db.execUpdate(strSql);
		} catch (Exception e) {
			System.out.println(e.toString());
			System.out.println(strSql);
			e.printStackTrace();
		}
	}

	/**
	*
	*
	*/
	public void insertErrorMOLog() {
		try {
			strSql = "insert into sms_molog_error set ";
			strSql += " vcpid=" + vcpID;
			strSql += ",ismgid='" + ismgID + "'";
			strSql += ",corpid='" + corpID + "'";
			strSql += ",spcode_first='" + spCode_first + "'";
			strSql += ",content='" + content + "'";
			strSql += ",spcode='" + spCode + "'";
			strSql += ",cpn='" + cpn + "'";
			strSql += ",serverid='" + serverID + "'";
			strSql += ",servername='" + serverName + "'";
			strSql += ",serveraction='" + serverAction + "'";
			strSql += ",delivertime='" + deliverTime + "'";
			db.execUpdate(strSql);
		} catch (Exception e) {
			System.out.println(e.toString());
			System.out.println(strSql);
			e.printStackTrace();
		}
	}

	public String getMO_corpID(String cpn) {
		try {
			strSql = "select corp_id from sms_user where cpn='" + cpn + "'";
			rs = db.execQuery(strSql);
			if (rs.next()) {
				String corp_id = rs.getString("corp_id");
				return corp_id;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "00";
	}

	/**
	 * ȡ��gameid
	 *
	 */
	public String getGameID(int vcpid, String servername) {
		try {
			strSql = "select gameid from sms_gamelist where vcpid=" + vcpid + " and gamename='" + servername + "'";
			rs = db.execQuery(strSql);
			if (rs.next()) {
				String gameid = rs.getString("gameid");
				return gameid;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 *
	 *
	 */

	public String getImsgID(String scpn) {
		try {
			strSql = " select ismgid from sms_user where cpn='" + scpn + "'";
			rs = db.execQuery(strSql);
			if (rs.next()) {
				String ismgid = rs.getString("ismgid");
				return ismgid;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "01";
	}

}