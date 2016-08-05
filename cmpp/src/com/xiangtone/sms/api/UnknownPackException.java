/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package com.xiangtone.sms.api;

public class UnknownPackException extends Exception {

	public UnknownPackException() {
		details = "unknown packet is received";
	}

	String details;
}