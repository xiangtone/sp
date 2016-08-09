package org.x;
/*
 * Created on 2006-11-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import org.apache.log4j.Logger;

import comsd.CMPP;
import comsd.CmppLogin;
import comsd.ConnDesc;

public class SMSActiveTest implements Runnable {

	static private Logger logger = Logger.getLogger(SMSActiveTest.class);
	CMPP p = null;
	public ConnDesc con;
	CmppLogin cl = new CmppLogin();
	CMPPSingleConnect cmppcon;

	public SMSActiveTest() {
		p = new CMPP();
		cmppcon = CMPPSingleConnect.getInstance();
		con = cmppcon.con;

	}

	public void run() {
		try {
			int i = 0;
			while (true) {

				logger.debug("send active test ");
				try {
					p.cmppActiveTest(cmppcon.con);
					Thread.currentThread().sleep(5000);
				} catch (Exception e) {
					logger.error("testActive exception msg--Exception:", e);

//					logger.info("heartbeat down...");
					p.cmppDisconnectFromIsmg(con);
					cmppcon.destroy();
					try {
						Thread.currentThread().sleep(35 * 1000);
						cmppcon = CMPPSingleConnect.getInstance();
						con = cmppcon.con;
//						logger.info("try reconnect");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}

		} catch (Exception e) {
			logger.error("", e);
		}
	}
}
