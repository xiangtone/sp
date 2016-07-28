package com.xiangtone.sms.webapi;

public class StateCode
{


	public static final String  sm_submit ="0001"; //下行消息 VCP-->XTSMS
	public static final String  sm_submit_ack ="0002"; //下行消息应答 XTSMS-->VCP

	//filed attributes
	public static final String  VCP_ID = "01"; //分配给VCP的ID号，用于网关对VSP身份确认
	public static final String  SERVER_CODE= "02"; //特服号
	public static final String  MEDIA_TYPE = "03";
	public static final String  SERver_TYPE = "04"; //应用服务类型
	public static final String  DEST_CPN= "05"; //目的手机号码
	public static final String  FEE_CPN = "06"; //费用手机号码
	public static final String  FEE_TYPE = "07"; //资费类别
	public static final String  FEE_CODE = "08"; //费用值（以分为单位）
	public static final String  CONTENT= "09"; //短信内容
	public static final String  ACK_CODE = "10"; //应答信息中返回
}