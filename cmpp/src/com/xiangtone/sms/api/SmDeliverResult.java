/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package com.xiangtone.sms.api;

import java.util.Arrays;

import org.apache.log4j.Logger;

public class SmDeliverResult extends SmResult {
	private static Logger logger = Logger.getLogger(SmDeliverResult.class);
	public SmDeliverResult() {

	}

	public void readInBytes(byte[] b) // throws Exception
	{
		try {

//			logger.debug(Arrays.toString(b));
			deByteCode = new DeByteCode(b);
			while (deByteCode.offset < b.length) {
				byte type = deByteCode.int8();
				short len = deByteCode.int16();
				int valueLen = len - 3;
				logger.debug("---type:" + type);
				logger.debug("---valueLen:  " + valueLen);
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

	public static String mobileCode;
	public static String gameCode;
	public static String actionCode;
	public static String spCode;
	public static String ismgCode;
	public static String stat;
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

	public String get_ismgCode() {
		return this.ismgCode;
	}

}
