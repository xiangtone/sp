// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   cmppeDeliverResult.java

package comsd.commerceware.cmpp;

import java.io.PrintStream;

import org.apache.log4j.Logger;

public final class CmppeDeliverResult extends CmppeResult {

	private static Logger logger = Logger.getLogger(CmppeDeliverResult.class);

	public CmppeDeliverResult() {
		msgId = new byte[8];
		// submitTime = new byte[10];
		// doneTime = new byte[10];
		// srcAddr = new byte[21];
		// dstAddr = new byte[21];
		// svcType = new byte[10];
		// shortMsg = new byte[161];
		// reserve = new byte[8];
		super.packId = 5;
	}

	public void readInBytes(byte[] b) throws Exception {
		try {
			logger.debug("the b length is:" + b.length);
			deByteCode = new DeByteCode(b);

			msgId = deByteCode.getBytes(8);

			dstAddr = deByteCode.asciiz(21);
			svcType = deByteCode.asciiz(10);
			logger.debug("dstAddr" + dstAddr);
			// logger.debug("svcType:"+svcType);
			tpPid = deByteCode.int8();
			tpUdhi = deByteCode.int8();
			dataCoding = deByteCode.int8();
			// logger.debug("dataCoding:"+dataCoding);
			// srcAddr = deByteCode.asciiz(21);
			srcAddr = deByteCode.asciiz(32);
			srcType = deByteCode.int8();
			logger.debug("srcAddr:" + srcAddr);
			registeredDelivery = deByteCode.int8();
			logger.debug("registeredDelivery:" + registeredDelivery);
			smLen = deByteCode.int8();
			int bb = smLen;
			if (smLen < 0)
				bb += 256;
			logger.debug("smLen:" + bb);
			if (registeredDelivery == 0) {
				// shortMsg = deByteCode.getBytes(bb);
				shortMsg = deByteCode.getBytes(bb);
				linkid = deByteCode.asciiz(20);
				if (linkid == null) {
					linkid = "";
				}
			} else {
				msgId2 = deByteCode.getBytes(8);
				// logger.debug("msgId2:"+new String(msgId2));

				// for(int i=0;i<msgId2.length;i++)
				// System.out.print(","+msgId2[i]);
				statusFromRpt = deByteCode.getBytes(7);

				submitTime = deByteCode.getBytes(10);
				// logger.debug("submitTime:"+new String(submitTime));
				doneTime = deByteCode.getBytes(10);
				//////////////////////// change at 061116
				// destCpn = deByteCode.getBytes(21);
				destCpn = deByteCode.getBytes(32);
				///////////////////////
				smscSeq = deByteCode.getBytes(4);
				// linkid = deByteCode.asciiz(20);
				linkid = deByteCode.asciiz(20);
				if (linkid == null) {
					linkid = "";
				}
			}
			// logger.debug("deByteCode length is:" + deByteCode.length);
			// logger.debug("message length is:" + shortMsg.length);
			// reserve = deByteCode.asciiz(8);
			// byte[] linkids = deByteCode.getBytes(20);

			logger.debug("linkid is" + linkid);
			STAT = 0;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("readInBytes", e);
			throw new Exception("decoding error" + e.toString());
		}
	}

	public int seq;
	// public int msgId0;
	// public int msgId1;
	public static byte msgId[];
	public static String dstAddr;
	public static String svcType;
	public static byte tpPid;
	public static byte tpUdhi;
	public static byte dataCoding;
	public static String srcAddr;
	public static byte srcType;// add at 061116
	public static byte registeredDelivery;
	public static byte smLen;
	public static byte shortMsg[];
	public String sshortMsg;//
	public static String linkid;// add at 061116
	public String reserve;

	public byte protoId;
	public byte statusRpt;
	public byte priority;

	public DeByteCode deByteCode;

	public static byte msgId2[];
	public static byte statusFromRpt[];
	public static byte submitTime[];
	public static byte doneTime[];
	public static byte destCpn[];
	public byte smscSeq[];

	public static int STAT = -1;

	public byte getTpUdhi() {
		return tpUdhi;
	}

	public byte getTpPid() {
		return tpPid;
	}

	public String getSPCode() {
		return dstAddr;
	}

	public String getCpn() {
		return srcAddr;
	}

	public byte getsrcType() {
		return srcType;
	}

	public byte getFmt() {
		return dataCoding;
	}

	public byte getLen() {
		return smLen;
	}

	public byte[] getMessage() {
		return shortMsg;

	}

	public String getServerType() {
		return svcType;
	}

	public String getLinkId() {
		return linkid;
	}

	public byte getRegisteredDelivery() {
		return registeredDelivery;
	}

	public String getMsgId() {
		return Bytes8ToLong(msgId2);
		// return msgId2;
	}

	public String getStat() {
		return new String(statusFromRpt);
	}

	public String getSubmitTime() {
		return new String(submitTime);
	}

	public String getDoneTime() {
		return new String(doneTime);
	}

	public String getDestCpn() {
		return new String(destCpn);
	}

	public String Bytes8ToLong(byte abyte0[]) {
		for (int i = 0; i < abyte0.length; i++) {
			System.out.print(abyte0[i] + ",");
		}
		long l = (0xff & abyte0[0]) << 56 | (0xff & abyte0[1]) << 48 | (0xff & abyte0[2]) << 40
				| (0xff & abyte0[3]) << 32 | (0xff & abyte0[4]) << 24 | (0xff & abyte0[5]) << 16
				| (0xff & abyte0[6]) << 8 | 0xff & abyte0[7];
		logger.debug("l:" + l);
		return (new Long(l)).toString();
	}

	public void printAll() {
		System.out.println("---dataCoding : " + dataCoding);
		System.out.println("---cpn: " + srcAddr);
		System.out.println("---spcode:" + dstAddr);
		System.out.println("---shortMsg:" + shortMsg);
		System.out.println("---svcType:" + svcType);
		System.out.println("---registeredDelivery:" + registeredDelivery);
		System.out.println("---tpUdhi:" + tpUdhi);
		System.out.println("---linkid:" + linkid);
	}

}
