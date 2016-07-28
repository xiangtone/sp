// Decompiled by DJ v2.9.9.61 Copyright 2000 Atanas Neshkov  Date: 2003-1-22 10:25:39
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   cmppe_submit_result.java

package comsd.commerceware.cmpp;


// Referenced classes of package com.commerceware.cmpp:
//            cmppe_result, cmppe_us_user

public class cmppe_submit_result extends cmppe_result
{

    public cmppe_submit_result()
    {
        msg_id = new byte[8];
        super.pack_id = 0x80000004;
    }
    
    
	    public static int seq;
        public static byte msg_id[];
        //public static byte result;
        public static int result;
        public static int flag=-1;
}