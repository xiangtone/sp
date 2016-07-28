/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package com.xiangtone.sms.api;

import java.io.*;
/**
*提交消息 设置属性
*
*/


public class sm_submit
{
	
	public ByteCode bc;
	
	public sm_submit()
	{
	 	 bc = new ByteCode(1);
	}
	/**
	*设置VCP_ID 1
	*
	*/
	public void set_vcp_id(String vcp_id) throws Exception
	{
		bc.AddByte(StateCode.VCP_ID);
		bc.AddShort((short)(3+vcp_id.getBytes().length));
		bc.AddBytes(vcp_id.getBytes());	
	}
	// 2
	public void set_server_code(String server_code) throws Exception
	{
		bc.AddByte(StateCode.SERVER_CODE);
		bc.AddShort((short)(3+server_code.getBytes().length));
		bc.AddBytes(server_code.getBytes());
		
	}
	//3
	public void set_media_type(String media_type) throws Exception
	{
		bc.AddByte(StateCode.MEDIA_TYPE);
		bc.AddShort((short)(3+media_type.getBytes().length));
		bc.AddBytes(media_type.getBytes());
	}
	//4
	public void set_server_type(String server_type) throws Exception
	{
		bc.AddByte(StateCode.SERVER_ID);
		bc.AddShort((short)(3+server_type.getBytes().length));
		bc.AddBytes(server_type.getBytes());
		
	}
	//5
	public void set_dest_cpn(String dest_cpn) throws Exception
	{
		bc.AddByte(StateCode.DEST_CPN);
		bc.AddShort((short)(3+dest_cpn.getBytes().length));
		bc.AddBytes(dest_cpn.getBytes());
		
	}
	//6
	public void  set_fee_cpn(String fee_cpn) throws Exception
	{
		bc.AddByte(StateCode.FEE_CPN);
		bc.AddShort((short)(3+fee_cpn.getBytes().length));
		bc.AddBytes(fee_cpn.getBytes());
		
	}
	//7
	public void  set_fee_type(String fee_type) throws Exception
	{
		
		bc.AddByte(StateCode.FEE_TYPE);
		bc.AddShort((short)(3+fee_type.getBytes().length));
		bc.AddBytes(fee_type.getBytes());
		
	}
	//8
	public void set_fee_code(String fee_code) throws Exception
	{
		bc.AddByte(StateCode.FEE_CODE);
		bc.AddShort((short)(3+fee_code.getBytes().length));
		bc.AddBytes(fee_code.getBytes());
		
	}
	//9
	public void set_content(String content) throws Exception
	{
		bc.AddByte(StateCode.CONTENT);
		bc.AddShort((short)(3+content.getBytes().length));
		bc.AddBytes(content.getBytes());
	}
	//10
	public void set_prov_id(String prov_id) throws Exception
	{
		bc.AddByte(StateCode.PROV_ID);
		bc.AddShort((short)(3+prov_id.getBytes().length));
		bc.AddBytes(prov_id.getBytes());
	}
	//11
	public void set_registered_delivery(String registered_delivery)  throws Exception
	{
		bc.AddByte(StateCode.REGISTERED_DELIVERY);
		bc.AddShort((short)(3+registered_delivery.getBytes().length));
		bc.AddBytes(registered_delivery.getBytes());
		
	}
	//12
	public void set_fee_linkid(String linkid) throws Exception
	{
		if(linkid == null){
			linkid = "";
		}
		bc.AddByte(StateCode.LINK_ID);
		bc.AddShort((short)(3+linkid.getBytes().length));
		bc.AddBytes(linkid.getBytes());	
	}
	/*
	public void set_vcp_pwd(String vcp_pwd) throws Exception
	{
		bc.AddByte(StateCode.VCP_PWD);
		bc.AddShort((short)(3+vcp_pwd.getBytes().length));
		bc.AddBytes(vcp_pwd.getBytes());	
	}
	*/
	//13
	public void set_fee_cpntype(int cpntype){
		bc.AddByte(StateCode.FEE_CPNTYPE);
		bc.AddShort((short)(4));
		bc.AddByte((byte)cpntype);
	}
	//14 
	public void set_fee_msgId(String msgId){
			if(msgId == null){
			msgId = "";
		}
		try{
			bc.AddByte(StateCode.MSGID);
			bc.AddShort((short)(3+msgId.getBytes().length));
			bc.AddBytes(msgId.getBytes());	
		}catch(Exception e){
			e.toString();	
		}
		
	}
	/**
	*取得整个字节组
	*
	*/
	public byte[] getBytes()
	{
	 	return bc.getBytes();
	}
	

}
