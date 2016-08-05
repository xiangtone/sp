// Decompiled by DJ v2.9.9.61 Copyright 2000 Atanas Neshkov  Date: 2003-1-22 10:25:39
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   cmppe_submit_result.java

package comsd.commerceware.cmpp;

// Referenced classes of package com.commerceware.cmpp:
//            cmppe_result, cmppe_us_user

public class CmppeSubmitResult extends CmppeResult {

	public CmppeSubmitResult() {
		msgId = new byte[8];
		super.packId = 0x80000004;
	}

	public static int seq;
	public static byte msgId[];
	// public static byte result;
	public static int result;
	public static int flag = -1;
}