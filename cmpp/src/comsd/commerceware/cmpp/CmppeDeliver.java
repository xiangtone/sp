// Decompiled by DJ v2.9.9.61 Copyright 2000 Atanas Neshkov  Date: 2003-1-22 10:16:29
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   cmppe_deliver.java

package comsd.commerceware.cmpp;

public final class CmppeDeliver {

	public CmppeDeliver() {
		msgId = new byte[8];
		srcAddr = new byte[21];
		dstAddr = new byte[21];
		svcType = new byte[11];
		shortMsg = new byte[161];
	}

	protected byte srcAddr[];
	protected byte dstAddr[];
	protected byte svcType[];
	protected byte protoId;
	protected byte statusRpt;
	protected byte priority;
	protected byte dataCoding;
	protected byte smLen;
	protected byte shortMsg[];
	protected byte msgId[];
}