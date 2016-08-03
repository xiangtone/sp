/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package com.xiangtone.sms.api;

import org.apache.log4j.Logger;

public class SmSubmitResult extends SmResult {
	private static Logger logger = Logger.getLogger(SmSubmitResult.class);

	public SmSubmitResult() {

	}

	public void readInBytes(byte[] b) // throws Exception
	{
		try {
			// logger.debug("b.length"+b.length);
			for (int i = 0; i < b.length; i++)
				// System.out.print(b[i]+",");
				deByteCode = new DeByteCode(b);
			while (deByteCode.offset < b.length) {
				byte type = deByteCode.int8();
				short len = deByteCode.int16();
				int valueLen = len - 3;
				// logger.debug("---type:"+type);
				// logger.debug("---valueLen: "+valueLen);
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
					logger.debug("content:" + new String(content));
					break;
				case 11:
					registeredDelivery = deByteCode.asciiz(valueLen);
					logger.debug("registeredDelivery:" + registeredDelivery);

					break;
				case 13:
					// String tempfeecpnType = deByteCode.int32();
					feecpnType = deByteCode.int8();// Integer.parseInt(tempfeecpnType);
					break;
				case 12:
					linkId = deByteCode.asciiz(valueLen);
					logger.debug("linkid value is:" + linkId);
					break;
				case 14:
					msgId = deByteCode.asciiz(valueLen);
					logger.debug("msgId value is:" + msgId);
					break;
				default:
					stat = "01"; // 无效的消息类型
					return;
				}
			}
			stat = "00"; // 成功
		} catch (Exception e) {
			// throw new Exception("decoding error");

			logger.error("readInBytes", e);

		}
	}

	public static String vcpId;
	public static String serverCode = "05511";
	public static String provCode = "01";

	public static String serverType;
	public static String destCpn;
	public static String feeCpn;
	public static String feeType;
	public static String feeCode;
	public static String mediaType;

	public static byte[] content;

	public static String registeredDelivery;
	public static String stat = "09";
	public DeByteCode deByteCode;
	// add at 061123
	public static int feecpnType;
	public static String linkId;
	public static String msgId;

	public String getVcpId() {
		return vcpId;
	}

	public String getServerCode() {
		return serverCode;
	}

	public String getProvCode() {
		return provCode;
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

	public String getFeeType() {
		return feeType;
	}

	public String getFeeCode() {
		return feeCode;
	}

	public String getMediaType() {
		return mediaType;
	}

	public byte[] getContent() {
		return content;
	}

	public String getRegisteredDelivery() {
		return registeredDelivery;
	}

	public String getStat() {
		return stat;
	}

	public DeByteCode getDeByteCode() {
		return deByteCode;
	}

	public int getFeecpnType() {
		return feecpnType;
	}

	public String getLinkId() {
		return linkId;
	}

	public String getMsgId() {
		return msgId;
	}

}
