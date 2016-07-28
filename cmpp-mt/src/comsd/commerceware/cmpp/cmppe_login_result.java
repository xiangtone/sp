// Decompiled by DJ v2.9.9.61 Copyright 2000 Atanas Neshkov  Date: 2003-1-22 10:22:02
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   cmppe_login_result.java

package comsd.commerceware.cmpp;


// Referenced classes of package com.commerceware.cmpp:
//            cmppe_result

public class cmppe_login_result extends cmppe_result
{

    public cmppe_login_result()
    {
        auth = new byte[16];
        super.pack_id = 0x80000001;
    }

    public byte auth[];
   // public byte version;
   public byte version;
}