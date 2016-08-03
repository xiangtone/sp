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

			logger.debug("创世短信游戏线程开始....listen:" + port);

			VCPServer server = new VCPServer(port);

			server.start();

			// Thread.currentThread().sleep(2000);

			logger.debug("游戏定时触发发送线程开始....");

			// GameServer game = new GameServer();

			// new Thread(game).start();

		} catch (Exception e) {

			logger.error("系统出错,将会退出。",e);

			System.exit(0);

		}

	}

}
