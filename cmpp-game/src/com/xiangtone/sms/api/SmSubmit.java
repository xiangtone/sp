/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package com.xiangtone.sms.api;

import java.io.*;

/**
 * 提交消息 设置属性
 *
 */

public class SmSubmit {

	public ByteCode bc;

	public SmSubmit() {
		bc = new ByteCode(1);
	}

	/**
	 * 设置VCPID 1
	 *
	 */
	public void setVcpId(String vcpId) throws Exception {
		bc.AddByte(StateCode.VCP_ID);
		bc.AddShort((short) (3 + vcpId.getBytes().length));
		bc.AddBytes(vcpId.getBytes());
	}

	// 2
	public void setServerCode(String serverCode) throws Exception {
		bc.AddByte(StateCode.SERVER_CODE);
		bc.AddShort((short) (3 + serverCode.getBytes().length));
		bc.AddBytes(serverCode.getBytes());

	}

	// 3
	public void setMediaType(String mediaType) throws Exception {
		bc.AddByte(StateCode.MEDIA_TYPE);
		bc.AddShort((short) (3 + mediaType.getBytes().length));
		bc.AddBytes(mediaType.getBytes());
	}

	// 4
	public void setServerType(String serverType) throws Exception {
		bc.AddByte(StateCode.SERVER_ID);
		bc.AddShort((short) (3 + serverType.getBytes().length));
		bc.AddBytes(serverType.getBytes());

	}

	// 5
	public void setDestCpn(String destCpn) throws Exception {
		bc.AddByte(StateCode.DEST_CPN);
		bc.AddShort((short) (3 + destCpn.getBytes().length));
		bc.AddBytes(destCpn.getBytes());

	}

	// 6
	public void setFeeCpn(String feeCpn) throws Exception {
		bc.AddByte(StateCode.FEE_CPN);
		bc.AddShort((short) (3 + feeCpn.getBytes().length));
		bc.AddBytes(feeCpn.getBytes());

	}

	// 7
	public void setFeeType(String feeType) throws Exception {

		bc.AddByte(StateCode.FEE_TYPE);
		bc.AddShort((short) (3 + feeType.getBytes().length));
		bc.AddBytes(feeType.getBytes());

	}

	// 8
	public void setFeeCode(String feeCode) throws Exception {
		bc.AddByte(StateCode.FEE_CODE);
		bc.AddShort((short) (3 + feeCode.getBytes().length));
		bc.AddBytes(feeCode.getBytes());

	}

	// 9
	public void setContent(String content) throws Exception {
		bc.AddByte(StateCode.CONTENT);
		bc.AddShort((short) (3 + content.getBytes().length));
		bc.AddBytes(content.getBytes());
	}

	// 10
	public void setProvId(String provId) throws Exception {
		bc.AddByte(StateCode.PROV_ID);
		bc.AddShort((short) (3 + provId.getBytes().length));
		bc.AddBytes(provId.getBytes());
	}

	// 11
	public void setRegisteredDelivery(String registeredDelivery) throws Exception {
		bc.AddByte(StateCode.REGISTERED_DELIVERY);
		bc.AddShort((short) (3 + registeredDelivery.getBytes().length));
		bc.AddBytes(registeredDelivery.getBytes());

	}

	// 12
	public void setFeeLinkid(String linkid) throws Exception {
		if (linkid == null) {
			linkid = "";
		}
		bc.AddByte(StateCode.LINK_ID);
		bc.AddShort((short) (3 + linkid.getBytes().length));
		bc.AddBytes(linkid.getBytes());
	}

	/*
	 * public void setVcpPwd(String vcpPwd) throws Exception {
	 * bc.AddByte(StateCode.VCPPWD);
	 * bc.AddShort((short)(3+vcpPwd.getBytes().length));
	 * bc.AddBytes(vcpPwd.getBytes()); }
	 */
	// 13
	public void setFeeCpntype(int cpntype) {
		bc.AddByte(StateCode.FEE_CPNTYPE);
		bc.AddShort((short) (4));
		bc.AddByte((byte) cpntype);
	}

	// 14
	public void setFeeMsgId(String msgId) {
		if (msgId == null) {
			msgId = "";
		}
		try {
			bc.AddByte(StateCode.MSGID);
			bc.AddShort((short) (3 + msgId.getBytes().length));
			bc.AddBytes(msgId.getBytes());
		} catch (Exception e) {
			e.toString();
		}

	}

	/**
	 * 取得整个字节组
	 *
	 */
	public byte[] getBytes() {
		return bc.getBytes();
	}

}
