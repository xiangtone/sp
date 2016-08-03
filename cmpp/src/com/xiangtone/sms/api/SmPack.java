/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package com.xiangtone.sms.api;

public final class SmPack
{

    public SmPack()
    {
        pk_head = new SmHeader();
        buf = new byte[512];
    }

    protected SmHeader pk_head;
    protected byte buf[];
}