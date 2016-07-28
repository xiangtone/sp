// Decompiled by DJ v2.9.9.61 Copyright 2000 Atanas Neshkov  Date: 2003-1-22 10:21:36
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   cmppe_login.java

package comsd.commerceware.cmpp;


// Referenced classes of package com.commerceware.cmpp:
//            OutOfBoundsException

public final class cmppe_login
{

    public cmppe_login()
    {
        icp_id = new byte[6];
        icp_auth = new byte[9];
    }

    public void set_icpid(String s)
        throws OutOfBoundsException
    {
        if(s.length() > 6)
        {
            OutOfBoundsException e = new OutOfBoundsException();
            throw e;
        }
        int i;
        for(i = 0; i < s.length(); i++)
            icp_id[i] = (byte)s.charAt(i);
    }

    public void set_auth(String s)
        throws OutOfBoundsException
    {	
    		System.out.println(s);
        if(s.length() > 16)
        {
            OutOfBoundsException e = new OutOfBoundsException();
            throw e;
        }
        int i;
        for(i = 0; i < s.length(); i++)
            icp_auth[i] = (byte)s.charAt(i);
    }

    public void set_version(byte ver)
    {
        icp_version = ver;
    }

    public void set_timestamp(int stamp)
    {
        icp_timestamp = stamp;
    }

    protected byte icp_id[];
    protected byte icp_auth[];
    protected byte icp_version;
    protected int  icp_timestamp;
}
