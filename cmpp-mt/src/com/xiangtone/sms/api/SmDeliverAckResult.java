/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package com.xiangtone.sms.api;

import org.apache.log4j.Logger;

public class SmDeliverAckResult extends SmResult {
	private static Logger logger = Logger.getLogger(SmDeliverAckResult.class);
	public SmDeliverAckResult() {

	}

	public void readInBytes(byte[] b) // throws Exception
	{
		try {
			deByteCode = new DeByteCode(b);
			while (deByteCode.offset < b.length) {
				byte type = deByteCode.int8();
				short len = deByteCode.int16();
				int valueLen = len - 3;
				logger.debug("type:" + type);
				switch (type) {
				case 100:
					errorCode = deByteCode.asciiz(valueLen);
					stat = errorCode;
					logger.debug("stat:" + stat);
					break;

				default:
					stat = "-1"; // 服务返回错误
					return;
				}
			}
		} catch (Exception e) {
			logger.error("readInBytes",e);
		}

	}

	public DeByteCode deByteCode;
	public String stat = "00";
	public String errorCode;

	public String getAckStat() {
		return stat;
	}
}
