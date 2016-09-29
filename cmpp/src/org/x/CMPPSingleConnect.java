package org.x;
/*
 * Created on 2006-11-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.xiangtone.util.ConfigManager;
import com.xiangtone.util.MailUtil;

import comsd.*;

public class CMPPSingleConnect {
	static private Logger logger = Logger.getLogger(CMPPSingleConnect.class);
	private static CMPPSingleConnect cmppcon = null;
	private CMPP p = new CMPP();
	public static ConnDesc con = new ConnDesc();
	private CmppLogin cl = new CmppLogin();
	public static int count = 0;
	private ConfigManager configManager = ConfigManager.getInstance();
	private int maxConnect = Integer.parseInt(configManager.getConfigData("max_connect"));

	private CMPPSingleConnect() {
		connectIsmg();
	}

	public static synchronized CMPPSingleConnect getInstance() {
		if (cmppcon == null) {
			cmppcon = new CMPPSingleConnect();
		}
		return cmppcon;
	}

	private void connectIsmg() {
		try {
			logger.debug("login gateway:" + SMSIsmgInfo.gdIsmgIp);
			p.cmppConnectToIsmg(SMSIsmgInfo.gdIsmgIp, SMSIsmgInfo.gdIsmgPort, con);
			cl.setIcpid(SMSIsmgInfo.gdIcpID);
			cl.setAuth(SMSIsmgInfo.gdIcpShareKey);
			cl.setVersion((byte) 0x30);
			cl.setTimestamp(1111101020);
			p.cmppLogin(con, cl);
			count = 0;
		} catch (Exception e) {
			if (configManager.getConfigData("mail_io").equals("true")) {
				count++;
				if (count >= maxConnect) {
					count = 0;
					try {
						MailUtil.send("GATEWAY ERROR:form " + InetAddress.getLocalHost().getHostAddress(),
								configManager.getConfigData("mail_form"), configManager.getConfigData("mail_to"),
								"Trying to connect to dateway more than " + maxConnect);
						// MailUtil.send("短信网关连接异常",
						// configManager().getConfigData("mail_form"),
						// configManager().getConfigData("mail_to"),
						// "短信网关尝试重连次数超过"+maxConnect+"次！");
					} catch (Exception e1) {
						logger.error("Mail send error", e1);
					}

				}
			}
			logger.error("err:login ismg failed! --CMPP_receive.java",e);
		}
	}

	synchronized public static void destroy() {
		logger.debug("destory connect instance.......");
		cmppcon = null;
	}

	public static void main(String[] args) {
		logger.debug(ConfigManager.getInstance().getConfigData("mail_to"));
	}
}
