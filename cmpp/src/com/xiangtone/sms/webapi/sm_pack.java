package com.xiangtone.sms.webapi;

public final class sm_pack
{

    public sm_pack()
    {
        pk_head = new sm_header();
        buf = new byte[512];
    }

    protected sm_header pk_head;
    protected byte buf[];
}