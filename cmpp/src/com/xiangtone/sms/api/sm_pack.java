/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package com.xiangtone.sms.api;

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