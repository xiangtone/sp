package org.x;
/*
 * Created on 2006-11-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import java.lang.*;
import java.net.InetAddress;

import org.apache.log4j.Logger;

import com.xiangtone.util.ConfigManager;
import com.xiangtone.util.MailUtil;

import comsd.*;

import java.io.*;

public class CMPPSingleConnect {
	private static CMPPSingleConnect cmppcon = null;
	private static Logger logger = Logger.getLogger(CMPPSingleConnect.class);
	private CMPP p = new CMPP();
	public static ConnDesc con = new ConnDesc();
	private CmppeLogin cl = new CmppeLogin();
	public static int count=0;
	private ConfigManager configManager = ConfigManager.getInstance();
  	private int maxConnect=Integer.parseInt(configManager.getConfigData("max_connect"));

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
			logger.debug("login ismg:" + SMSIsmgInfo.qwIsmgIp);
			p.cmppConnectToIsmg(SMSIsmgInfo.qwIsmgIp,SMSIsmgInfo.qwIsmgPort,con);
//			logger.debug("鐧婚檰娴嬭瘯缃戝叧:127.0.0.1");
//			p.cmppConnectToIsmg("127.0.0.1", 7891, con);// test
			cl.setIcpid(SMSIsmgInfo.qwIcpID);
			cl.setAuth(SMSIsmgInfo.qwIcpShareKey);
			cl.setVersion((byte) 0x30);
			cl.setTimestamp(1111101020);
			p.cmppLogin(con, cl);
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
			logger.error("login ismg failed!", e);
		}
	}

	synchronized public static void destroy() {
		logger.debug("destory connect instance.......");
		cmppcon = null;
	}
}
