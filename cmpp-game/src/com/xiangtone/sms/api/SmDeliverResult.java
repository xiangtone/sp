/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package com.xiangtone.sms.api;

import java.util.Arrays;

import org.apache.log4j.Logger;

/**
 * deliver result
 *
 */

public class SmDeliverResult extends SmResult {
	private static Logger logger = Logger.getLogger(SmDeliverAckResult.class);
	public SmDeliverResult() {

	}

	public void readInBytes(byte[] b) // throws Exception
	{
		try {
			logger.debug("readInBytes"+Arrays.toString(b));
			deByteCode = new DeByteCode(b);
			while (deByteCode.offset < b.length) {
				byte type = deByteCode.int8();
				short len = deByteCode.int16();
				int valueLen = len - 3;
				switch (type) {

				case 1:
					mobileCode = deByteCode.asciiz(valueLen);
					logger.debug("mobileCode:" + mobileCode);
					break;
				case 2:
					gameCode = deByteCode.asciiz(valueLen);
					logger.debug("gameCode:" + gameCode);
					break;
				case 3:
					actionCode = deByteCode.asciiz(valueLen);
					logger.debug("actionCode:" + actionCode);
					break;
				case 4:
					spCode = deByteCode.asciiz(valueLen);
					logger.debug("spCode:" + spCode);
					break;
				case 5:
					ismgCode = deByteCode.asciiz(valueLen);
					logger.debug("ismgCode:" + ismgCode);
					break;
				case 12:
					linkId = deByteCode.asciiz(valueLen);
					logger.debug("linkId:" + linkId);
					break;
				case 13:
					cpnType = deByteCode.int8();
					logger.debug("cpnType:" + cpnType);
					break;
				case 14:
					msgId = deByteCode.asciiz(valueLen);
					logger.debug("msgId:" + msgId);
					break;
				default:
					stat = "01"; // 无效的消息类型
					return;
				}
			}
			stat = "00"; // 成功
		} catch (Exception e) {
			logger.error("",e);
		}
	}

	public static String mobileCode; // 手机号码
	public static String gameCode; // 游戏代号
	public static String actionCode;// 指令
	public static String spCode; // 特服务号
	public static String ismgCode; // 手机号码归属省份
	public static String linkId;// 信息的linkId add at 061121
	public static int cpnType;// 手机号码的类型 add at 061121
	public static String stat; // 状态
	public static String msgId;// 短信的信息码 add at 08-11-27
	public DeByteCode deByteCode;

	public String getMobileCode() {
		return this.mobileCode;
	}

	public String getGameCode() {
		return this.gameCode;
	}

	public String getActionCode() {
		return this.actionCode;
	}

	public String getSpCode() {
		return this.spCode;
	}

	public String getIsmgCode() {
		return this.ismgCode;
	}

	public String getLinkId() {
		return linkId;
	}

	public int getCpntype() {
		return cpnType;
	}

	public String getMsgId() {
		return msgId;
	}

}