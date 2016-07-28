/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package com.xiangtone.sms.api;

import java.io.*;
public class sm_deliver
{
	public ByteCode bc;
	public sm_deliver()
	{
	 	 bc = new ByteCode(1);
	}
	
	//set mobile_code
	public void set_mobileCode(String mobileCode) throws Exception
	{
		bc.AddByte(StateCode.MOBILE_CODE);
		bc.AddShort((short)(3+mobileCode.getBytes().length));
		bc.AddBytes(mobileCode.getBytes());	
	}
	// set sp_code
	public void set_spCode(String spCode) throws Exception
	{
		bc.AddByte(StateCode.SP_CODE);
		bc.AddShort((short)(3+spCode.getBytes().length));
		bc.AddBytes(spCode.getBytes());
		
	}
	// set game_code
	public void set_gameCode(String gameCode) throws Exception
	{
		bc.AddByte(StateCode.GAME_CODE);
		bc.AddShort((short)(3+gameCode.getBytes().length));
		bc.AddBytes(gameCode.getBytes());
	}
	//set game_action
	public void set_actionCode(String gameAction) throws Exception
	{
		bc.AddByte(StateCode.ACTION_CODE);
		bc.AddShort((short)(3+gameAction.getBytes().length));
		bc.AddBytes(gameAction.getBytes());
		
	}
	//set ismg_code
	public void set_ismgCode(String ismgCode) throws Exception
	{
		bc.AddByte(StateCode.ISMG_CODE);
		bc.AddShort((short)(3+ismgCode.getBytes().length));
		bc.AddBytes(ismgCode.getBytes());
		
	}
	// get total byte
	public byte[] getBytes()
	{
	 	return bc.getBytes();
	}
	

}
