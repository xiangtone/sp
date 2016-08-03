package org.x;
/*
 * Created on 2006-11-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import org.apache.log4j.Logger;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import comsd.commerceware.cmpp.CMPP;
import comsd.commerceware.cmpp.CmppLogin;
import comsd.commerceware.cmpp.ConnDesc;

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
					logger.error("testActive exception msg--Exception:",e);

					logger.info("heartbeat down...");
					p.cmppDisconnectFromIsmg(con);
					cmppcon.destroy();
					try {
						Thread.currentThread().sleep(10 * 1000);
						cmppcon = CMPPSingleConnect.getInstance();
						con = cmppcon.con;
						logger.info("try reconnect");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}

		} catch (Exception e) {
			logger.error("",e);
		}
	}
}
