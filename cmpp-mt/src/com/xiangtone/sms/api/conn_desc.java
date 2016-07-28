/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package com.xiangtone.sms.api;

import java.net.Socket;

public final class conn_desc
{

     public conn_desc()
    {
    	
    }
    public conn_desc(Socket s)
    {
    	this.sock = s;
    }

    public Socket sock; //Á¬½Ó
}