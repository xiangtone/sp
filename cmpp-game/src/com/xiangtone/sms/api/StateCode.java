/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package com.xiangtone.sms.api;

/**
 * 消息定义
 *
 */
public class StateCode {

	public static final int SM_SUBMIT = 1; // 下行消息 VCP-->XTSMS
	public static final int SM_SUBMIT_ACK = 2; // 下行消息应答 XTSMS-->VCP
	public static final int SM_DELIVER = 3; // 上行消息 XTSMS-->VCP
	public static final int SM_DELIVER_ACK = 4; // 上行消息应答 XTSMS-->VCP
	public static final int SM_REPORT = 5; // 报告消息
	public static final int SM_REPORT_ACK = 6; // 报告消息应答

	// submit attributes
	public static final byte VCP_ID = 1; // 分配给VCP的ID号，用于网关对VSP身份确认
	public static final byte SERVER_CODE = 2; // 特服号
	public static final byte PROV_ID = 3; // 省份
	public static final byte SERVER_ID = 4; // 应用服务类型
	public static final byte DEST_CPN = 5; // 目的手机号码
	public static final byte FEE_CPN = 6; // 费用手机号码
	public static final byte FEE_TYPE = 7; // 资费类别
	public static final byte FEE_CODE = 8; // 费用值（以分为单位）
	public static final byte MEDIA_TYPE = 9; // 媒体类型
	public static final byte CONTENT = 10; // 短信内容
	public static final byte REGISTERED_DELIVERY = 11; // 状态报告
	// public static final byte VCP_PWD = 12; //登入密码
	public static final byte FEE_CPNTYPE = 13;// 费用手机的号码类型 伪码或明码 add @ 061201
	public static final byte LINK_ID = 12;// link id add at 061201
	public static final byte MSGID = 14;// ADD AT 08-11-27

	// deliver attributes
	public static final byte MOBILE_CODE = 1;
	public static final byte GAME_CODE = 2;
	public static final byte ACTION_CODE = 3;
	public static final byte SP_CODE = 4;
	public static final byte ISMG_CODE = 5;

	public static final byte ACK_CODE = 100; // 应答信息中返回
}