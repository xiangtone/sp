// Decompiled by DJ v2.9.9.61 Copyright 2000 Atanas Neshkov  Date: 2003-1-22 10:21:36
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   cmppe_login.java

package comsd.commerceware.cmpp;

import org.apache.log4j.Logger;

// Referenced classes of package com.commerceware.cmpp:
//            OutOfBoundsException

public final class CmppLogin {
	private static Logger logger = Logger.getLogger(CmppLogin.class);
	public CmppLogin() {
		icpId = new byte[6];
		icpAuth = new byte[9];
	}

	public void setIcpid(String s) throws OutOfBoundsException {
		if (s.length() > 6) {
			OutOfBoundsException e = new OutOfBoundsException();
			throw e;
		}
		int i;
		for (i = 0; i < s.length(); i++)
			icpId[i] = (byte) s.charAt(i);
	}

	public void setAuth(String s) throws OutOfBoundsException {
		logger.debug(s);
		if (s.length() > 16) {
			OutOfBoundsException e = new OutOfBoundsException();
			throw e;
		}
		int i;
		for (i = 0; i < s.length(); i++)
			icpAuth[i] = (byte) s.charAt(i);
	}

	public void setVersion(byte ver) {
		icpVersion = ver;
	}

	public void setTimestamp(int stamp) {
		icpTimestamp = stamp;
	}

	protected byte icpId[];
	protected byte icpAuth[];
	protected byte icpVersion;
	protected int icpTimestamp;
}
