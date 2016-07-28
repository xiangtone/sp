package com.xiangtone.sms.webapi;
import java.io.*;
import java.net.*;

public class ConnectionHandler implements Runnable 
{
    protected Socket socketToHandle;
    protected conn_desc con ;
    protected message mess = new message();
    public ConnectionHandler(Socket aSocketToHandle) 
    {
        socketToHandle = aSocketToHandle;
        con = new conn_desc(socketToHandle);
    }
    public void run() {
        try {
        	
        	mess.readPa(con); //读取提交信息
            
        } catch (Exception e) {
            System.out.println("Error handling a client: " + e);
        }
    }
}
