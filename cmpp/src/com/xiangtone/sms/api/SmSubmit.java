/**
 * Title:
 * Description: submit пео╒
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

public class SmSubmit
{
	public ByteCode bc;
	public SmSubmit()
	{
	 	 bc = new ByteCode(1);
	}
	
	
	public void set_vcp_id(String vcp_id) throws Exception
	{
		bc.AddByte(StateCode.VCP_ID);
		bc.AddShort((short)(3+vcp_id.getBytes().length));
		bc.AddBytes(vcp_id.getBytes());	
	}
	public void set_server_code(String server_code) throws Exception
	{
		bc.AddByte(StateCode.SERVER_CODE);
		bc.AddShort((short)(3+server_code.getBytes().length));
		bc.AddBytes(server_code.getBytes());
		
	}
	public void set_media_type(String media_type) throws Exception
	{
		bc.AddByte(StateCode.MEDIA_TYPE);
		bc.AddShort((short)(3+media_type.getBytes().length));
		bc.AddBytes(media_type.getBytes());
	}
	public void set_server_type(String server_type) throws Exception
	{
		bc.AddByte(StateCode.SERVER_ID);
		bc.AddShort((short)(3+server_type.getBytes().length));
		bc.AddBytes(server_type.getBytes());
		
	}
	public void set_dest_cpn(String dest_cpn) throws Exception
	{
		bc.AddByte(StateCode.DEST_CPN);
		bc.AddShort((short)(3+dest_cpn.getBytes().length));
		bc.AddBytes(dest_cpn.getBytes());
		
	}
	public void  set_fee_cpn(String fee_cpn) throws Exception
	{
		
		
		bc.AddByte(StateCode.FEE_CPN);
		bc.AddShort((short)(3+fee_cpn.getBytes().length));
		bc.AddBytes(fee_cpn.getBytes());
		
	}
	public void  set_fee_type(String fee_type) throws Exception
	{
		
		bc.AddByte(StateCode.FEE_TYPE);
		bc.AddShort((short)(3+fee_type.getBytes().length));
		bc.AddBytes(fee_type.getBytes());
		
	}
	public void set_fee_code(String fee_code) throws Exception
	{
		bc.AddByte(StateCode.FEE_CODE);
		bc.AddShort((short)(3+fee_code.getBytes().length));
		bc.AddBytes(fee_code.getBytes());
		
	}
	public void set_content(String content) throws Exception
	{
		bc.AddByte(StateCode.CONTENT);
		bc.AddShort((short)(3+content.getBytes().length));
		bc.AddBytes(content.getBytes());
	}
	public void set_prov_id(String prov_id) throws Exception
	{
		bc.AddByte(StateCode.PROV_ID);
		bc.AddShort((short)(3+prov_id.getBytes().length));
		bc.AddBytes(prov_id.getBytes());
	}
	public void set_registered_delivery(String registered_delivery)  throws Exception
	{
		bc.AddByte(StateCode.REGISTERED_DELIVERY);
		bc.AddShort((short)(3+registered_delivery.getBytes().length));
		bc.AddBytes(registered_delivery.getBytes());
		
	}
	public byte[] getBytes()
	{
	 	return bc.getBytes();
	}
	

}
