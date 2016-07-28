// Decompiled by DJ v2.9.9.61 Copyright 2000 Atanas Neshkov  Date: 2003-1-22 10:24:30
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   cmppe_query_result.java

package comsd.commerceware.cmpp;

import java.io.PrintStream;

// Referenced classes of package com.commerceware.cmpp:
//            cmppe_result

public class cmppe_query_result extends cmppe_result
{

    public cmppe_query_result()
    {
        time = new byte[9];
        code = new byte[11];
        super.pack_id = 0x80000006;
    }

    protected void printAllField()
    {
        System.out.println("time :" + time);
        System.out.println("type :" + type);
        System.out.println("code :" + code);
        System.out.println("mt_tlmsg :" + mt_tlmsg);
        System.out.println("mt_scs :" + mt_scs);
        System.out.println("mt_tlusr :" + mt_tlusr);
        System.out.println("mt_wt :" + mt_wt);
        System.out.println("mt_fl :" + mt_fl);
        System.out.println("mo_scs :" + mo_scs);
        System.out.println("mo_wt :" + mo_wt);
    }

    public byte time[];
    public byte type;
    public byte code[];
    public int mt_tlmsg;
    public int mt_tlusr;
    public int mt_scs;
    public int mt_wt;
    public int mt_fl;
    public int mo_scs;
    public int mo_wt;
    public int mo_fl;
}