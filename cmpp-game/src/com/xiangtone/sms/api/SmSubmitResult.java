/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package com.xiangtone.sms.api;

import java.util.Arrays;

import org.apache.log4j.Logger;

public class SmSubmitResult extends SmResult {
	private static Logger logger = Logger.getLogger(SmSubmitResult.class);
	public SmSubmitResult() {

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
				logger.debug("---type:" + type);
				logger.debug("---valueLen:  " + valueLen);
				switch (type) {
				case 1:
					vcpId = deByteCode.asciiz(valueLen);
					logger.debug("vcpId:" + vcpId);
					break;
				case 2:
					serverCode = deByteCode.asciiz(valueLen);
					logger.debug("serverCode:" + serverCode);
					break;

				case 3:
					provCode = deByteCode.asciiz(valueLen);
					logger.debug("provId:" + provCode);
					break;
				case 4:
					serverType = deByteCode.asciiz(valueLen);
					logger.debug("serverType:" + serverType);
					break;
				case 5:
					destCpn = deByteCode.asciiz(valueLen);
					logger.debug("destCpn:" + destCpn);
					break;
				case 6:
					feeCpn = deByteCode.asciiz(valueLen);
					logger.debug("feeCpn:" + feeCpn);
					break;
				case 7:
					feeType = deByteCode.asciiz(valueLen);
					logger.debug("feeType:" + feeType);
					break;

				case 8:
					feeCode = deByteCode.asciiz(valueLen);
					logger.debug("feeCode:" + feeCode);
					break;
				case 9:
					mediaType = deByteCode.asciiz(valueLen);
					logger.debug("mediaType:" + mediaType);
					break;
				case 10:
					content = deByteCode.getBytes(valueLen);
					logger.debug("content：" + new String(content));
					break;
				case 11:
					registeredDelivery = deByteCode.asciiz(valueLen);
					logger.debug("registeredDelivery:" + registeredDelivery);

					break;
				case 12:
					vcpPwd = deByteCode.asciiz(valueLen);
					logger.debug("vcpPwd:" + vcpPwd);

				default:
					stat = "01"; // 无效的消息类型
					return;
				}
			}
			stat = "00"; // 成功
		} catch (Exception e) {
			// throw new Exception("decoding error");

			logger.error("",e);
		}
	}

	public static String vcpId;// 1
	public static String serverCode = "08887";// 2
	public static String provCode = "01";// 3
	public static String serverType;// 4
	public static String destCpn;// 5
	public static String feeCpn;// 6
	public static String feeType;// 7
	public static String feeCode;// 8
	public static String mediaType;// 9
	public static byte[] content;// 10
	public static String registeredDelivery;// 11
	public static String vcpPwd;// 12

	public static String stat = "09";
	public DeByteCode deByteCode;

	public String getVcpId() {
		return vcpId;
	}

	public String getServerCode() {
		return this.serverCode;
	}

	public String getMediaType() {
		return this.mediaType;
	}

	public String getFeeType() {
		return this.feeType;
	}

	public String getServerType() {
		return serverType;
	}

	public String getDestCpn() {
		return destCpn;
	}

	public String getFeeCpn() {
		return feeCpn;
	}

	public String getFeeCode() {
		return feeCode;
	}

	public byte[] getContent() {
		return content;
	}

	public String getStat() {
		return stat;
	}

	public String getProvCode() {
		return provCode;
	}

	public String getDelivery() {
		return registeredDelivery;
	}

	public String getVcpPwd() {
		return vcpPwd;
	}
}
