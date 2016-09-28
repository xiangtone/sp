package org.x;

import com.xiangtone.util.ConfigManager;

/*
 * Created on 2006-11-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
/**
 * @author Administrator
 *
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class TlsmsmtPublic {
	public static void main(String[] args) {
		try {
			SMSIsmgInfo info = new SMSIsmgInfo("config.ini");
			info.loadParam();
			info.printParam();

			SMSActiveTest sdc = new SMSActiveTest();
			new Thread(sdc).start();

			SMSRecive sr = new SMSRecive();
			new Thread(sr).start();

			VCPServer server = new VCPServer(Integer.parseInt(ConfigManager.getConfigData("listen_port")));
			new Thread(server).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
