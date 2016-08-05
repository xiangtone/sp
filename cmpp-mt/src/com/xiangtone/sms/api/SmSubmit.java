/**
 * Title:
 * Description: submit ��Ϣ
 * Copyright:    Copyright (c) 2001
 * Company: 
 * Modified timed : 2002-7-11 16:07
 * @author Gavin wang (wangyg@xmindex.com.cn)
 * @version 1.0
 */
/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package com.xiangtone.sms.api;

import java.io.*;

public class SmSubmit {
	public ByteCode bc;

	public SmSubmit() {
		bc = new ByteCode(1);
	}

	public void setVcpId(String vcpId) throws Exception {
		bc.AddByte(StateCode.VCP_ID);
		bc.AddShort((short) (3 + vcpId.getBytes().length));
		bc.AddBytes(vcpId.getBytes());
	}

	public void setServerCode(String serverCode) throws Exception {
		bc.AddByte(StateCode.SERVER_CODE);
		bc.AddShort((short) (3 + serverCode.getBytes().length));
		bc.AddBytes(serverCode.getBytes());

	}

	public void setMediaType(String mediaType) throws Exception {
		bc.AddByte(StateCode.MEDIA_TYPE);
		bc.AddShort((short) (3 + mediaType.getBytes().length));
		bc.AddBytes(mediaType.getBytes());
	}

	public void setServerType(String serverType) throws Exception {
		bc.AddByte(StateCode.SERVER_ID);
		bc.AddShort((short) (3 + serverType.getBytes().length));
		bc.AddBytes(serverType.getBytes());

	}

	public void setDestCpn(String destCpn) throws Exception {
		bc.AddByte(StateCode.DEST_CPN);
		bc.AddShort((short) (3 + destCpn.getBytes().length));
		bc.AddBytes(destCpn.getBytes());

	}

	public void setFeeCpn(String feeCpn) throws Exception {

		bc.AddByte(StateCode.FEE_CPN);
		bc.AddShort((short) (3 + feeCpn.getBytes().length));
		bc.AddBytes(feeCpn.getBytes());

	}

	public void setFeeType(String feeType) throws Exception {

		bc.AddByte(StateCode.FEE_TYPE);
		bc.AddShort((short) (3 + feeType.getBytes().length));
		bc.AddBytes(feeType.getBytes());

	}

	public void setFeeCode(String feeCode) throws Exception {
		bc.AddByte(StateCode.FEE_CODE);
		bc.AddShort((short) (3 + feeCode.getBytes().length));
		bc.AddBytes(feeCode.getBytes());

	}

	public void setContent(String content) throws Exception {
		bc.AddByte(StateCode.CONTENT);
		bc.AddShort((short) (3 + content.getBytes().length));
		bc.AddBytes(content.getBytes());
	}

	public void setProvId(String provId) throws Exception {
		bc.AddByte(StateCode.PROV_ID);
		bc.AddShort((short) (3 + provId.getBytes().length));
		bc.AddBytes(provId.getBytes());
	}

	public void setRegisteredDelivery(String registeredDelivery) throws Exception {
		bc.AddByte(StateCode.REGISTERED_DELIVERY);
		bc.AddShort((short) (3 + registeredDelivery.getBytes().length));
		bc.AddBytes(registeredDelivery.getBytes());

	}

	public byte[] getBytes() {
		return bc.getBytes();
	}

}
