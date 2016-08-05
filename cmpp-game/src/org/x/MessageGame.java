package org.x;

/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;

import java.lang.reflect.*;
import java.net.*;
import com.xiangtone.sms.api.*;
import com.xiangtone.util.*;

public class MessageGame {
	private static Logger logger = Logger.getLogger(MessageGame.class);
	private Properties Props = new Properties();
	private Vector destSet = new Vector(1, 5);
	private Vector feeSet = new Vector(1, 5);
	private Vector msgSet = new Vector(1, 5);
	private Vector costSet = new Vector(1, 5);
	private Vector linkidSet = new Vector(1, 5);// add at 061121
	private Vector cpntypeSet = new Vector(1, 5);
	private Vector ismgSet = new Vector(1, 5);
	private Vector msgIdSet = new Vector(1, 5);// add at 08-11-27
	// public mytools tools = new mytools();

	Socket s = null;
	String ismgid = "00";

	public MessageGame(String ismgid) {
		this.ismgid = ismgid;
		init(ismgid);
	}

	private void init(String ismgStr) {
		try {

			File f = null;
			/*
			 * if(ismgStr.equals("01")) { f = new
			 * File("app_name_bj.properties"); } else if(ismgStr.equals("06")) {
			 * logger.debug("app_name_ln.properties....."); f = new
			 * File("app_name_ln.properties"); } else if(ismgStr.equals("08")) {
			 * f = new File("app_name_hl.properties"); } else
			 * if(ismgStr.equals("15")) { f = new File("app_nameSd.properties");
			 * } else if(ismgStr.equals("19")) { f = new
			 * File("app_nameGd.properties"); } else { f = new
			 * File("app_name.properties"); }
			 */
			logger.debug("appNameFj.properties.....");
			f = new File("appNameFj.properties");

			FileInputStream ins = new FileInputStream(f);
			if (ins != null) {
				Props = new Properties();
				Props.load(ins);
			} else {
				// String strDate = saveLog.formatDate();
				// saveLog.error(strDate+"--API_GET--Can not read the properties
				// file");
				logger.debug("app_name.properties is not exist");
			}
		} catch (Exception e) {
			logger.error("", e);
		}

		return;
	}

	/******************************************************************
	 * 功能：构造函数:: 输入参数说明： 1.内存中队列的句柄；
	 ******************************************************************/
	// public int dispatchGame(String cpn,String itemname,String
	// itemaction,String ismgId,String linkid,String cpntype,Vector
	// destSet,Vector feeSet,Vector msgSet,Vector costSet,Vector ismgSet,Vector
	// linkidSet,Vector cpntypeSet)
	public int dispatchGame(String cpn, String itemname, String itemaction, String ismgId, String linkid, int cpnType,
			String msgId, Vector destSet, Vector feeSet, Vector msgSet, Vector costSet, Vector ismgSet,
			Vector linkidSet, Vector cpnTypeSet, Vector msgIdSet) {
		int feeType = 1;
		int flag = 0;
		destSet.clear();
		feeSet.clear();
		msgSet.clear();
		costSet.clear();
		ismgSet.clear();
		cpnTypeSet.clear();
		msgIdSet.clear();
		// 传入的信息
		itemname = itemname.toUpperCase();
		itemaction = itemaction.toUpperCase();

		// 返回KEY VALUE，取得类名
		String className = Props.getProperty(itemname, "paramName");
		// 取得文件中的对应方法名的串
		String classMethod = Props.getProperty(itemname + ".METHOD", "paramName");
		logger.debug("itemname:" + itemname + "---itemaction:" + itemaction + "--classname:" + className
				+ "---classMethod:" + classMethod);

		// if(className.equals("paramName"))
		// className="ERROR";
		// if(classMethod.equals("paramName"))
		// classMethod="execMain";
		//
		if (!className.equals("paramName") && !classMethod.equals("paramName")) {
			try {
				Class toRun = Class.forName(className);
				// 定义方法名
				Method mainMethod = toRun.getDeclaredMethod(classMethod,
						new Class[] { String.class, String.class, String.class, String.class, Integer.class,
								String.class, Vector.class, Vector.class, Vector.class, Vector.class, Vector.class,
								Vector.class, Vector.class, Vector.class });
				// 激活方法返回结果
				feeType = Integer
						.parseInt(mainMethod
								.invoke(toRun.newInstance(),
										new Object[] { cpn, itemaction, ismgId, linkid, cpnType, msgId, destSet, feeSet,
												msgSet, costSet, ismgSet, linkidSet, cpntypeSet, msgIdSet })
								.toString());

				logger.debug("feetype" + feeType);
			} catch (Exception e) {
				logger.error("", e);
				e.printStackTrace();
			}

		} else {
			logger.debug("派发错误,没有该游戏 ismgid:" + this.ismgid);
			if (this.ismgid.equals("00"))
				return feeType;
			try {
				if (flag == 1)
					return feeType;
				flag++;
				itemname = "ERROR";
				// dispatchGame(cpn,itemname,itemaction,ismgId,destSet,feeSet,msgSet,costSet,ismgSet);
			} catch (Exception eee) {
				logger.error("", eee);
			}

		}
		return feeType;
	}

	// 如果短信长度太长，那么就分隔
	public String[] splitConent(String smContent) {
		int nLen = smContent.length();
		int n1 = (nLen + 69) / 70;
		logger.debug("::::短信条数：" + n1);
		String[] str1 = new String[n1];
		for (int j = 0; j < n1; j++) {
			int j1 = j * 70;
			int j2 = (j + 1) * 70;

			if (j < (n1 - 1))
				str1[j] = smContent.substring(j1, j2);
			else
				str1[j] = smContent.substring(j1);
		}
		return str1;
	}

	// public void multiDispatch(String cpn,String gameCode,String
	// gameAction,String spCode,String ismgCode)
	public void multiDispatch(String cpn, String gameCode, String gameAction, String spCode, String ismgCode,
			String linkid, int cpnType, String msgId) {

		// int tempCpntype = cpnType;
		// int feetype =
		// dispatchGame(cpn,gameCode,gameAction,ismgCode,linkid,tempCpntype,destSet,feeSet,msgSet,costSet,ismgSet,linkidSet,cpntypeSet);
		logger.debug("linkid is:" + linkid);
		int feetype = dispatchGame(cpn, gameCode, gameAction, ismgCode, linkid, cpnType, msgId, destSet, feeSet, msgSet,
				costSet, ismgSet, linkidSet, cpntypeSet, msgIdSet);
		int i1 = destSet.size();
		int i2 = feeSet.size();
		int i3 = msgSet.size();
		int i4 = costSet.size();
		int i6 = linkidSet.size();
		int i7 = cpntypeSet.size();
		int i5 = ismgSet.size();
		int i8 = msgIdSet.size();
		logger.debug(
				"i1:" + i1 + "  i2:" + i2 + "  i3:" + i3 + "  i4:" + i4 + "  i5:" + i5 + "  i6:" + i6 + "  i7:" + i7);
		String strFeeType = (new Integer(feetype)).toString();
		try {
			for (int i = 0; i < i1 && i < i2 && i < i3 && i < i4 && i < i6 && i < i7 && i < i8; i++) {

				String destcpn = (String) destSet.get(i);
				String feecpn = (String) feeSet.get(i);
				String content = (String) msgSet.get(i);
				String serverID = (String) costSet.get(i); // 费用服务id
				String ismgID = ismgCode;
				ismgID = (String) ismgSet.get(i);
				String _msgId = (String) msgIdSet.get(i);
				logger.debug("------msgId value is:" + _msgId);
				int tempCpntype = ((Integer) cpntypeSet.get(i)).intValue();
				/*
				 * logger.debug(":::::::::::::::::::");
				 * logger.debug(":::::::::::::::::::");
				 * logger.debug(":::::::::::::::::::");
				 * logger.debug(":::::::::::::::::::"); logger.debug("destcpn:"
				 * + destcpn); logger.debug("feecpn:" + feecpn);
				 * logger.debug("content:" + content); logger.debug("serverID:"
				 * + serverID); logger.debug("msgId" + _msgId);
				 * logger.debug(":::::::::::::::::::");
				 * logger.debug(":::::::::::::::::::");
				 * logger.debug(":::::::::::::::::::");
				 * logger.debug(":::::::::::::::::::");
				 */
				String vcpID = "1"; // xiangtone vcpid
				String provID = ismgID;
				String mediaType = "1"; // 文本

				String delivery = "0";// 不需要状态报告
				String cpnlinkid = (String) linkidSet.get(i);// add at 061121
				// String tempcpnType = (String)cpntypeSet.get(i);//add at
				// 061121
				int usercpnType = 0;// Integer.parseInt(tempcpnType);//add at
									// 061121
				String str2[] = splitConent(content);
				for (int j = 0; j < str2.length; j++) {
					MessageSubmit ms = new MessageSubmit();
					ms.setDestCpn(destcpn);
					ms.setFeeCpn(feecpn);
					ms.setCpnType(tempCpntype);
					ms.setLinkid(cpnlinkid);
					ms.setContent(str2[j]);
					ms.setServerID(serverID);
					ms.setVcpID(vcpID);
					ms.setProvID(provID);
					ms.setSpCode(spCode);
					ms.setFeeType(strFeeType);
					// ms.setFeeCode(String str)
					ms.setMediaType(mediaType);
					ms.setDelivery(delivery);
					ms.setGameCode(gameCode);
					ms.setMsgId(_msgId);
					ms.setSendTime(FormatSysTime.getCurrentTimeA());
					ms.sendResultToSmsPlatform();
					ms.insertMTlog();
					logger.debug("insert mt log ...OK!!");

				}

			} // end for

		} catch (Exception e) {
			logger.error("MultiDp Switch error!", e);
		}

		finally {
			try {
				if (s != null)
					s.close();
			} catch (IOException e2) {
				logger.error(e2.toString());
			}
		}
	}

}