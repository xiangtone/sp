// Decompiled by DJ v2.9.9.61 Copyright 2000 Atanas Neshkov  Date: 2003-1-22 10:28:09
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   UnknownPackException.java
package comsd.commerceware.cmpp;

public class UnknownPackException extends Exception {

	public UnknownPackException() {
		details = "unknown packet is received";
	}

	String details;
}