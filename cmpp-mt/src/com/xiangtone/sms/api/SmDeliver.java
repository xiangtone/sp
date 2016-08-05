/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package com.xiangtone.sms.api;

import java.io.*;

import org.apache.log4j.Logger;

public class SmDeliver {
	public ByteCode bc;
	private static Logger logger = Logger.getLogger(SmDeliver.class);
	public SmDeliver() {
		bc = new ByteCode(1);
	}

	// set mobile_code
	public void setMobileCode(String mobileCode) throws Exception {
		bc.AddByte(StateCode.MOBILE_CODE);
		bc.AddShort((short) (3 + mobileCode.getBytes().length));
		bc.AddBytes(mobileCode.getBytes());
	}

	// set mobile_type add at 061121
	public void setMobileType(int type) {
		bc.AddByte(StateCode.FEE_CPNTYPE);
		bc.AddShort((short) (4));
		bc.AddByte((byte) type);
	}

	// set sp_code
	public void setSpCode(String spCode) throws Exception {
		logger.debug("smDeliver: " + spCode);
		bc.AddByte(StateCode.SP_CODE);
		bc.AddShort((short) (3 + spCode.getBytes().length));
		bc.AddBytes(spCode.getBytes());

	}

	// set game_code
	public void setGameCode(String gameCode) throws Exception {
		bc.AddByte(StateCode.GAME_CODE);
		bc.AddShort((short) (3 + gameCode.getBytes().length));
		bc.AddBytes(gameCode.getBytes());
	}

	// set game_action
	public void setActionCode(String gameAction) throws Exception {
		logger.debug("smDeliverAction:" + gameAction);
		bc.AddByte(StateCode.ACTION_CODE);
		bc.AddShort((short) (3 + gameAction.getBytes().length));
		bc.AddBytes(gameAction.getBytes());

	}

	// set ismg_code
	public void setIsmgCode(String ismgCode) throws Exception {
		bc.AddByte(StateCode.ISMG_CODE);
		bc.AddShort((short) (3 + ismgCode.getBytes().length));
		bc.AddBytes(ismgCode.getBytes());
	}

	// set linkid //add at 061120
	public void setLinkID(String linkid) throws Exception {
		logger.debug("linkid is:" + linkid);
		if (linkid == null) {// change at 061204
			byte[] _linkid = new byte[20];
			bc.AddByte(StateCode.LINK_ID);
			bc.AddShort((short) (3 + _linkid.length));
			bc.AddBytes(_linkid);

		} else {
			bc.AddByte(StateCode.LINK_ID);
			bc.AddShort((short) (3 + linkid.getBytes().length));
			bc.AddBytes(linkid.getBytes());
		}

	}

	// add at 2008-11-27
	public void setMsgId(String msgId) {
		try {
			bc.AddByte(StateCode.MSGID);
			bc.AddShort((short) (3 + msgId.getBytes().length));
			bc.AddBytes(msgId.getBytes());
		} catch (Exception e) {
			logger.error("setMsgId", e);
			e.toString();
		}

	}

	// get total byte
	public byte[] getBytes() {
		return bc.getBytes();
	}

}
