// Decompiled by DJ v2.9.9.61 Copyright 2000 Atanas Neshkov  Date: 2003-1-22 10:23:49
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   cmppe_query.java

package comsd.commerceware.cmpp;

public final class CmppeQuery {

	public CmppeQuery() {
		time = new byte[9];
		code = new byte[11];
	}

	byte time[];
	byte type;
	byte code[];
}