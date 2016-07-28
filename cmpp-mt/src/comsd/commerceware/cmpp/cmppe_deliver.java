// Decompiled by DJ v2.9.9.61 Copyright 2000 Atanas Neshkov  Date: 2003-1-22 10:16:29
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   cmppe_deliver.java

package comsd.commerceware.cmpp;


public final class cmppe_deliver
{

    public cmppe_deliver()
    {
    	msg_id = new byte[8];
        src_addr = new byte[21];
        dst_addr = new byte[21];
        svc_type = new byte[11];
        short_msg = new byte[161];
    }

    protected byte src_addr[];
    protected byte dst_addr[];
    protected byte svc_type[];
    protected byte proto_id;
    protected byte status_rpt;
    protected byte priority;
    protected byte data_coding;
    protected byte sm_len;
    protected byte short_msg[];
    protected byte msg_id[];
}