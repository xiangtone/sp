package com.xiangtone.sms.webapi;

import java.net.Socket;

public final class conn_desc
{

    public conn_desc(Socket s)
    {
    	this.sock = s;
    }

    public Socket sock; //Á¬½Ó
}