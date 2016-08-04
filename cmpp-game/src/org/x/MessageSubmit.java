package org.x;

import org.apache.log4j.Logger;

import com.xiangtone.sms.api.ConnDesc;
import com.xiangtone.sms.api.Message;
import com.xiangtone.sms.api.SmSubmit;
import com.xiangtone.util.ConfigManager;
import com.xiangtone.util.DBForLocal;

public class MessageSubmit {
	private static Logger logger = Logger.getLogger(MessageSubmit.class);

	public MessageSubmit() {
		// sub = new SmSubmit();
		xtsms = new Message();
		conn = new ConnDesc();
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

	public SmSubmit sub; // 提交信息
	public Message xtsms;
	public ConnDesc conn;

	public void setDestCpn(String str) {
		this.destCpn = str;
	}

	public void setFeeCpn(String str) {
		this.feeCpn = str;
	}

	public void setCpnType(int type) {
		this.cpntype = type;
	}// add at 061123

	public void setContent(String str) {
		this.content = str;
	}

	public void setServerID(String str) {
		this.serverID = str;
	}

	public void setVcpID(String str) {
		this.vcpID = str;
	}

	public void setProvID(String str) {
		this.provID = str;
	}

	public void setSpCode(String str) {
		this.spCode = str;
	}

	public void setFeeType(String str) {
		this.feeType = str;
	}

	public void setFeeCode(String str) {
		this.feeCode = str;
	}

	public void setMediaType(String str) {
		this.mediaType = str;
	}

	public void setDelivery(String str) {
		this.delivery = str;
	}

	public void setLinkid(String str) {
		this.linkid = str;
	}

	public void setGameCode(String str) {
		this.gameCode = str;
	}

	public void setSendTime(String str) {
		this.sendTime = str;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public void insertMTlog() {
		String strSql = " insert into sms_mtlog set  spcode='" + spCode + "',ismgid='" + provID + "'";
		strSql += ",destcpn='" + destCpn + "',feecpn='" + feeCpn + "',serverid='" + serverID + "'";
		strSql += ",servername='" + gameCode + "',content='" + content + "'";
		strSql += ",feetype='" + feeType + "',sendtime='" + sendTime + "'";
		DBForLocal db=new DBForLocal();
		try {
			logger.debug(strSql);
			db.iniPreparedStatement(strSql);
			db.executeUpdate(strSql);
		} catch (Exception e) {
			logger.error(strSql, e);
		}finally{
			db.close();
		}
	}

	public void sendResultToSmsPlatform() {
		try {
			sub = new SmSubmit();
			sub.setVcpId(this.vcpID);
			sub.setServerCode(this.spCode);
			sub.setProvId(this.provID);
			sub.setServerType(this.serverID);
			sub.setDestCpn(this.destCpn);
			sub.setFeeType(this.feeType);
			sub.setFeeCpn(this.feeCpn);
			sub.setFeeCpntype(this.cpntype);// add at 061123
			sub.setFeeLinkid(this.linkid);// add at 061123
			sub.setFeeMsgId(this.msgId);// add at 08-11-27
			// sub.set_fee_code("10");
			sub.setMediaType(this.mediaType);
			sub.setContent(this.content);
			sub.setRegisteredDelivery(this.delivery);
			logger.debug("开始连接... 发送MT信息...");
			String smsServerip = (String) ConfigManager.getInstance().getConfigData("sms_serverip",
					"xiangtone_serverip not found!");
			String smsServerport = (String) ConfigManager.getInstance().getConfigData("sms_serverport",
					"xiangtone_serverport not found!");
			logger.debug(smsServerip);
			logger.debug(smsServerport);

			xtsms.connectToServer(smsServerip, Integer.parseInt(smsServerport), conn); // 连接服务器
			logger.debug(conn.sock);
			xtsms.sendSmSubmit(conn, sub); // 提交信息
			xtsms.readPa(conn);// 读取返回
			xtsms.disconnectFromServer(conn);
			logger.debug("提交成功。。");
		} catch (Exception e) {
			logger.error("", e);
		}
	}

}
