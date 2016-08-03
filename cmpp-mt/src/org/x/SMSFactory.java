package org.x;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: Disney.com
 * </p>
 * 
 * @author Gavin Wang
 * @version 0.5
 */
public class SMSFactory {
	/**
	*
	*
	*/
	public CMPPSend createSMS(String type, SMSMT mt) {
		/*
		 * if(type.equals("06")) { return new CMPPSend_ln(mt); //辽宁沈阳 } else
		 * if(type.equals("08")) { return new CMPPSend_hl(mt); //黑龙江 } else
		 * if(type.equals("10")) { return new CMPPSend_bj(mt); //江苏移动 } else
		 * if(type.equals("01")) { return new CMPPSend_bj(mt); //北京移动 } else
		 * if(type.equals("15")) { return new CMPPSend_sd(mt); //山东移动 } else
		 * if(type.equals("19")) { return new CMPPSend_gd(mt); //广东移动 } else {
		 * return new CMPPSend_bj(mt); }
		 */
		System.out.println("content is:" + mt.sendContent);
		return new CMPPSendSd(mt);
	}
}