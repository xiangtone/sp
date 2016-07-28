package com.xiangtone.sms.webapi;


public class UnknownPackException extends Exception
{

    public UnknownPackException()
    {
        details = "unknown packet is received";
    }

    String details;
}