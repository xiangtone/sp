// Decompiled by DJ v2.9.9.61 Copyright 2000 Atanas Neshkov  Date: 2003-1-22 10:23:12
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   cmppe_pack.java
package comsd.commerceware.cmpp;

// Referenced classes of package com.commerceware.cmpp:
//            cmppe_head

public final class CmppePack {

	public CmppePack() {
		pkHead = new CmppeHead();
		buf = new byte[512];
	}

	protected CmppeHead pkHead;
	protected byte buf[];
}