package org.x;

import com.xiangtone.sms.api.conn_desc;
import com.xiangtone.sms.api.message;
import com.xiangtone.sms.api.sm_submit;
import com.xiangtone.util.ConfigManager;

public class MessageSubmit {
	public MessageSubmit() {
		// sub = new sm_submit();
		xtsms = new message();
		conn = new conn_desc();
		db = new mysqldb();
	}

	public String destCpn;
	public String feeCpn;
	public String content;
	public String serverID;
	public String vcpID;
	public String spCode;
	public String provID;
	public String feeType;
	public String feeCode;
	public String mediaType;
	public String delivery;
	public String linkid;// add at 061122
	public int cpntype;// add at 061122
	public String gameCode;
	public String sendTime;
	public String msgId;// add at 08-11-27

	public sm_submit sub; // 提交信息
	public message xtsms;
	public conn_desc conn;
	public mysqldb db;

	public void set_destCpn(String str) {
		this.destCpn = str;
	}

	public void set_feeCpn(String str) {
		this.feeCpn = str;
	}

	public void set_cpnType(int type) {
		this.cpntype = type;
	}// add at 061123

	public void set_content(String str) {
		this.content = str;
	}

	public void set_serverID(String str) {
		this.serverID = str;
	}

	public void set_vcpID(String str) {
		this.vcpID = str;
	}

	public void set_provID(String str) {
		this.provID = str;
	}

	public void set_spCode(String str) {
		this.spCode = str;
	}

	public void set_feeType(String str) {
		this.feeType = str;
	}

	public void set_feeCode(String str) {
		this.feeCode = str;
	}

	public void set_mediaType(String str) {
		this.mediaType = str;
	}

	public void set_delivery(String str) {
		this.delivery = str;
	}

	public void set_linkid(String str) {
		this.linkid = str;
	}

	public void set_gameCode(String str) {
		this.gameCode = str;
	}

	public void set_sendTime(String str) {
		this.sendTime = str;
	}

	public void set_msgId(String msgId) {
		this.msgId = msgId;
	}

	public void insertMTlog() {
		try {
			String strSql = " insert into sms_mtlog set  spcode='" + spCode + "',ismgid='" + provID + "'";
			strSql += ",destcpn='" + destCpn + "',feecpn='" + feeCpn + "',serverid='" + serverID + "'";
			strSql += ",servername='" + gameCode + "',content='" + content + "'";
			strSql += ",feetype='" + feeType + "',sendtime='" + sendTime + "'";
			System.out.println(strSql);
			db.executeInsert(strSql);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public void send_result_to_smsPlatform() {
		try {
			sub = new sm_submit();
			sub.set_vcp_id(this.vcpID);
			sub.set_server_code(this.spCode);
			sub.set_prov_id(this.provID);
			sub.set_server_type(this.serverID);
			sub.set_dest_cpn(this.destCpn);
			sub.set_fee_type(this.feeType);
			sub.set_fee_cpn(this.feeCpn);
			sub.set_fee_cpntype(this.cpntype);// add at 061123
			sub.set_fee_linkid(this.linkid);// add at 061123
			sub.set_fee_msgId(this.msgId);// add at 08-11-27
			// sub.set_fee_code("10");
			sub.set_media_type(this.mediaType);
			sub.set_content(this.content);
			sub.set_registered_delivery(this.delivery);
			System.out.println("开始连接... 发送MT信息...");
			String sms_serverip = (String) ConfigManager.getInstance().getConfigData("sms_serverip",
					"xiangtone_serverip not found!");
			String sms_serverport = (String) ConfigManager.getInstance().getConfigData("sms_serverport",
					"xiangtone_serverport not found!");
			System.out.println(sms_serverip);
			System.out.println(sms_serverport);

			xtsms.connect_to_server(sms_serverip, Integer.parseInt(sms_serverport), conn); // 连接服务器
			System.out.println(conn.sock);
			xtsms.send_sm_submit(conn, sub); // 提交信息
			xtsms.readPa(conn);// 读取返回
			xtsms.disconnect_from_server(conn);
			System.out.println("提交成功。。");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}

}
