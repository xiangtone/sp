// Decompiled by DJ v2.9.9.61 Copyright 2000 Atanas Neshkov  Date: 2003-1-22 10:27:08
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   DeliverFailException.java

package comsd.commerceware.cmpp;


public class DeliverFailException extends Exception
{

    public DeliverFailException()
    {
        details = "Receive deliver packet exception";
    }

    String details;
}