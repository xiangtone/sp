// Decompiled by DJ v2.9.9.61 Copyright 2000 Atanas Neshkov  Date: 2003-1-22 10:24:30
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   cmppe_query_result.java

package comsd.commerceware.cmpp;

import java.io.PrintStream;

import org.apache.log4j.Logger;

// Referenced classes of package com.commerceware.cmpp:
//            cmppe_result

public class CmppeQueryResult extends CmppeResult {
	private static Logger logger = Logger.getLogger(CmppeQueryResult.class);

	public CmppeQueryResult() {
		time = new byte[9];
		code = new byte[11];
		super.packId = 0x80000006;
	}

	protected void printAllField() {
		logger.debug("time :" + time);
		logger.debug("type :" + type);
		logger.debug("code :" + code);
		logger.debug("mtTlmsg :" + mtTlmsg);
		logger.debug("mtScs :" + mtScs);
		logger.debug("mtTlusr :" + mtTlusr);
		logger.debug("mtWt :" + mtWt);
		logger.debug("mtFl :" + mtFl);
		logger.debug("moScs :" + moScs);
		logger.debug("moWt :" + moWt);
	}

	public byte time[];
	public byte type;
	public byte code[];
	public int mtTlmsg;
	public int mtTlusr;
	public int mtScs;
	public int mtWt;
	public int mtFl;
	public int moScs;
	public int moWt;
	public int moFl;
}