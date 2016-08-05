package org.x;

import org.apache.log4j.Logger;

import com.xiangtone.util.ConfigManager;

/**
 * 
 * Copyright 2003 Xiamen Xiangtone Co. Ltd.
 * 
 * All right reserved.
 * 
 */

public class XiangtoneGame {
	private static Logger logger = Logger.getLogger(XiangtoneGame.class);
	public static void main(String args[]) {

		try {
			int port = Integer.parseInt((String) ConfigManager.getConfigData("listen_port"));

			logger.debug("����������Ϸ�߳̿�ʼ....listen:" + port);

			VCPServer server = new VCPServer(port);

			server.start();

			// Thread.currentThread().sleep(2000);

			logger.debug("��Ϸ��ʱ���������߳̿�ʼ....");

			// GameServer game = new GameServer();

			// new Thread(game).start();

		} catch (Exception e) {

			logger.error("ϵͳ����,�����˳���",e);

			System.exit(0);

		}

	}

}
