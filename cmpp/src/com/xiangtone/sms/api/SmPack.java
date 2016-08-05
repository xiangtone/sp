/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package com.xiangtone.sms.api;

public final class SmPack
{

    public SmPack()
    {
        pkHead = new SmHeader();
        buf = new byte[512];
    }

    protected SmHeader pkHead;
    protected byte buf[];
}