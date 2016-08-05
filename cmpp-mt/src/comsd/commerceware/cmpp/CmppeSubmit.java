package comsd.commerceware.cmpp;

import java.io.PrintStream;

// Referenced classes of package com.commerceware.cmpp:
//            OutOfBoundsException
public final class CmppeSubmit {

	public CmppeSubmit() {
		msgId = new byte[8];
		serviceId = new byte[10];
		// feeTerminalId = new byte[21];
		feeTerminalId = new byte[32];
		msgSrc = new byte[6];
		feetype = new byte[2];
		feecode = new byte[6];
		validTime = new byte[17];
		atTime = new byte[17];
		srcTerminalId = new byte[21];
		// srcTerminalId = new byte[32];
		destTerminalId = new byte[100][32];
		linkid = new byte[20];
		// reserve = new byte[8];
	}

	public void setMsgid(byte b[]) {
		int j;
		for (j = 0; j < 8; j++) {
			msgId[j] = b[j];
		}
	}

	public void setPktotal(byte b) {
		pkTotal = b;
	}

	public void setPknumber(byte b) {
		pkNumber = b;
	}

	public void setRegistered(byte b) {
		registeredDelivery = b;
	}

	public void setMsglevel(byte b) {
		msgLevel = b;
	}

	public void setServiceid(byte b[]) {
		int j;
		for (j = 0; j < 10; j++)
			serviceId[j] = b[j];

	}

	public void setFeeusertype(byte b) {
		feeUsertype = b;
	}

	public void setFeeterminalid(byte[] b) {
		int j;
		for (j = 0; j < 32; j++)
			feeTerminalId[j] = b[j];

	}

	public void setTppid(byte b) {
		tpPid = b;
	}

	public void setTpudhi(byte b) {
		tpUdhi = b;
	}

	public void setMsgfmt(byte b) {
		msgFmt = b;
	}

	public void setMsgsrc(byte[] b) {
		int j;
		for (j = 0; j < 6; j++)
			msgSrc[j] = b[j];
	}

	public void setFeetype(byte[] b) {
		int j;
		for (j = 0; j < 2; j++)
			feetype[j] = b[j];
	}

	public void setFeecode(byte[] b) {
		int j;
		for (j = 0; j < 6; j++)
			feecode[j] = b[j];
	}

	public void setValidtime(byte[] b) {
		int j;
		for (j = 0; j < 17; j++)
			validTime[j] = b[j];

	}

	public void setAttime(byte[] b) {
		int j;
		for (j = 0; j < 17; j++)
			atTime[j] = b[j];

	}

	public void setSrcterminalid(byte[] b) {
		int j;
		for (j = 0; j < 21; j++)
			srcTerminalId[j] = b[j];

	}

	public void setDestusrtl(byte b) {
		destusrTl = b;
	}

	public void setDestterminalid(byte[][] b) throws OutOfBoundsException {
		try {
			for (int i = 0; i < b.length; i++) {
				if (b[i][0] == 0)
					break;
				int j;
				for (j = 0; j < 32; j++) {
					// System.out.println(b[i].length);
					if (j > b[i].length - 1) {
						destTerminalId[i][j] = 0x0;
					} else {
						destTerminalId[i][j] = b[i][j];
					}
					// if(destTerminalId[i][j] == 0)
					// break;
					// System.out.print(destTerminalId[i][j]+",;;");
				}

			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	public void setMsglength(byte b) {
		msgLength = b;
	}

	public void setMsgcontent(byte[] b) {
		msgContent = new byte[b.length];
		try {
			int j;
			for (j = 0; j < b.length; j++) {
				msgContent[j] = b[j];
			}
		} catch (Exception e) {
			System.out.println("dfdf");
		}
	}

	public void setDestterminaltype(byte b) {
		destTerminalType = b;
	}

	public void setFeeterminaltype(byte b) {
		feeTerminalType = b;
	}

	public void setLinkid(byte[] b) {

		int j;
		for (j = 0; j < 20; j++)
			linkid[j] = b[j];
	}

	/*
	 * protected void printAllField() { try { String s = null; s = new
	 * String(icpId); System.out.println("icpId:" + s); s = null; s = new
	 * String(svcType); System.out.println("svcType:" + s); s = null;
	 * System.out.println("feeType:" + feeType); s = null;
	 * System.out.println("infoFee:" + infoFee); System.out.println("protoId:" +
	 * protoId); s = null; System.out.println("msgMode:" + msgMode);
	 * System.out.println("priority:" + priority); s = null; s = new
	 * String(validate); System.out.println("validate:" + s); s = null; s = new
	 * String(schedule); System.out.println("schedule:" + s);
	 * System.out.println("feeUtype:" + feeUtype); s = null; s = new
	 * String(feeUser); System.out.println("feeUser:" + s); s = null; s = new
	 * String(srcAddr); System.out.println("srcAddr:" + s);
	 * System.out.println("duCount:" + duCount); s = null; for(int i = 0; i <
	 * 100; i++) { if(dstAddr[i][0] == 0) break; s = new String(dstAddr[i]);
	 * System.out.println("dstAddr:" + s); }
	 * 
	 * System.out.println("dataCoding:" + dataCoding);
	 * System.out.println("smLen:" + smLen); s = null; s = new String(shortMsg,
	 * "GB2312"); System.out.println("shortMsg:" + s); } catch(Exception _ex) {
	 * } }
	 */
	protected byte msgId[];
	protected byte pkTotal;
	protected byte pkNumber;
	protected byte registeredDelivery;
	protected byte msgLevel;
	protected byte serviceId[];
	protected byte feeUsertype;
	protected byte feeTerminalId[];
	protected byte tpPid;
	protected byte tpUdhi;
	protected byte msgFmt;
	protected byte msgSrc[];
	protected byte feetype[];
	protected byte feecode[];
	protected byte validTime[];
	protected byte atTime[];
	protected byte srcTerminalId[];
	protected byte destusrTl;
	protected byte destTerminalId[][];
	protected byte msgLength;
	protected byte msgContent[];
	// change at 061123
	protected byte linkid[];
	protected byte feeTerminalType;
	protected byte destTerminalType;
	//
	// protected byte reserve[];
}
