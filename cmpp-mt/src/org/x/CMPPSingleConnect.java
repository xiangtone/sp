package org.x;
/*
 * Created on 2006-11-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import comsd.commerceware.cmpp.*;

import java.lang.*;

import org.apache.log4j.Logger;

import com.xiangtone.util.ConfigManager;
import com.xiangtone.util.MailUtil;

import java.io.*;

public class CMPPSingleConnect {
	private static CMPPSingleConnect cmppcon = null;
	private static Logger logger = Logger.getLogger(CMPPSingleConnect.class);
	private CMPP p = new CMPP();
	public static ConnDesc con = new ConnDesc();
	private CmppeLogin cl = new CmppeLogin();
	public static int count=0;
  	private int maxConnect=Integer.parseInt(ConfigManager.getInstance().getConfigData("max_connect"));

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
//			logger.debug("登陆测试网关:127.0.0.1");
//			p.cmppConnectToIsmg("127.0.0.1", 7891, con);// test
			cl.setIcpid(SMSIsmgInfo.qwIcpID);
			cl.setAuth(SMSIsmgInfo.qwIcpShareKey);
			cl.setVersion((byte) 0x30);
			cl.setTimestamp(1111101020);
			p.cmppLogin(con, cl);
		} catch (Exception e) {
			count++;
  			if(count>=maxConnect){
  				count=0;
  				MailUtil.send("Ismg connect error", ConfigManager.getInstance().getConfigData("send_mail"), ConfigManager.getInstance().getConfigData("mail_to"), "Failed to connect more than "+maxConnect);
  			}
			logger.error("login ismg failed!", e);
			e.printStackTrace();
		}
	}

	synchronized public static void destroy() {
		logger.debug("destory connect instance.......");
		cmppcon = null;
	}
}
