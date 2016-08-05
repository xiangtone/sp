/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package com.xiangtone.sms.api;

import java.net.Socket;

public final class ConnDesc {

	public ConnDesc() {

	}

	public ConnDesc(Socket s) {
		this.sock = s;
	}

	public Socket sock;
}