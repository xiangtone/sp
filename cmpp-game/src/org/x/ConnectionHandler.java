package org.x;

/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
import java.io.*;
import java.net.*;

import org.apache.log4j.Logger;

import com.xiangtone.sms.api.*;
import com.xiangtone.util.*;

public class ConnectionHandler implements Runnable {
	private static Logger logger = Logger.getLogger(ConnectionHandler.class);
	protected Socket socketToHandle;
	protected ConnDesc con;
	protected Message mess = new Message();
	protected SmDeliverResult sm = new SmDeliverResult();

	public ConnectionHandler(Socket aSocketToHandle) {
		socketToHandle = aSocketToHandle;
		con = new ConnDesc(socketToHandle);
	}

	public void run() {
		try {

			mess.readPa(con); // 读取提交信息
//			logger.debug("sm.stat:" + sm.stat);
			// sm.stat = "00";
			if (sm.stat.equals("00")) // mo信息上来了
			{

				sm.stat = "01";
				String mobileCode = sm.getMobileCode();

				String gameCode = sm.getGameCode();

				String actionCode = sm.getActionCode();

				String spCode = sm.getSpCode();

				String ismgCode = sm.getIsmgCode();
				String msgId = sm.getMsgId();
				///////// add at 061121
//				logger.debug("mobileCode:" + mobileCode+", gameCode:" + gameCode+", actionCode:" + actionCode+", msgId:" + msgId);
				String linkid = sm.getLinkId();
				int cpnType = sm.getCpntype();
				int vcpid = 1; // xiangtone vcpid =2;
				String deliverTime = FormatSysTime.getCurrentTimeA();
				// String linkid = sm.getLinkId();//add at 061120
				mobileCode = mobileCode.trim();
				gameCode = gameCode.trim().toUpperCase();
				if (actionCode == null)
					actionCode = "";
				actionCode = actionCode.trim().toUpperCase();

//				logger.debug("M:" + mobileCode + "--G:" + gameCode + "--Cmd:" + actionCode);

				// 游戏派发 并发送
//				logger.debug("mobileCode:" + mobileCode+", gameCode:" + gameCode+", actionCode:" + actionCode+", spCode:" + spCode+", ismgCode:" + ismgCode);
				MessageGame sms = new MessageGame(ismgCode);
				sms.multiDispatch(mobileCode, gameCode, actionCode, spCode, ismgCode, linkid, cpnType, msgId);// add

				// 日志处理
				MessageDeliver deliver = new MessageDeliver();
				deliver.setVcpid(vcpid); // 2: 代表翔通
				deliver.setIsmgCode(ismgCode);
				deliver.setSpCode(spCode);
				deliver.setMobileCode(mobileCode);
				deliver.setGameCode(gameCode);
				deliver.setActionCode(actionCode);
				deliver.setDeliverTime(deliverTime);
				deliver.insertMOLog();
				logger.debug("insert mo log...ok!!");
			}

		} catch (Exception e) {
			logger.error("Error handling a client: ",e);
		}
	}
}