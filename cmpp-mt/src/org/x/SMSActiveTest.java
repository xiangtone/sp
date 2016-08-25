package org.x;

import java.lang.*;
import java.io.*;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.xiangtone.util.FormatSysTime;

import comsd.*;

public class SMSActiveTest implements Runnable {
	CMPP p = null;// new CMPP();
	public ConnDesc con;// = new conn_desc();
	CmppeLogin cl = new CmppeLogin();
	CMPPSingleConnect cmppcon;// = CMPPSingleConnect.getInstance();
	private static Logger logger = Logger.getLogger(SMSActiveTest.class);

	public SMSActiveTest() {
		p = new CMPP();
		cmppcon = CMPPSingleConnect.getInstance();
		con = cmppcon.con;

	}

	public void run() {
		try {
			int i = 0;
			while (true) {
				System.out.println("send active test");
				try {
					p.cmppActiveTest(cmppcon.con);
					Thread.currentThread().sleep(5000);
				} catch (Exception e) {
					logger.error("testActive exception msg--Exception:", e);
					logger.debug("Try to active again");
					p.cmppDisConnectFromIsmg(con);
					cmppcon.destroy();

					// cmppcon =null;
					try {
						Thread.currentThread().sleep(10 * 1000);
						cmppcon = CMPPSingleConnect.getInstance(); 
						con = cmppcon.con;

					} catch (Exception e1) {
						logger.error("Failed to active:", e);
					}
					// cmppcon = CMPPSingleConnect.getInstance(); 
				}

			}

		} catch (Exception e) {
			logger.error("SMSActiveTest", e);
		}
	}
}
