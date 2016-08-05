//2003-06-10
package comsd.commerceware.cmpp;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

import org.apache.log4j.Logger;

public final class CMPP {
	private static Logger logger = Logger.getLogger(CMPP.class);
	MyTools tools = new MyTools();

	public CMPP() {

	}

	// 连接网关
	public void cmppConnectToIsmg(String host, int port, ConnDesc conn) throws IOException {
		Socket s = null;
		try {
			s = new Socket(host, port);
			logger.debug(s);
			s.setSoTimeout(0x927c0);
		} catch (IOException e) {
			logger.error("cmppConnectToIsmg", e);
			throw e;
		}
		conn.seq = 1; // 序号为1 代表开始
		conn.sock = s;
	}

	// 断开连接
	public void cmppDisConnectFromIsmg(ConnDesc conn) {
		try {
			conn.sock.close();
		} catch (Exception e) {
			logger.error("cmppDisconnectFromIsmg", e);
			return;
		}
	}

	// login ismg
	public void cmppLogin(ConnDesc conn, CmppeLogin cl) throws IOException, OutOfBoundsException {
		CmppeHead ch = new CmppeHead();

		byte[] buf = new byte[100];
		int bodyLen = 0;

		DataOutputStream out = null;
		OutOfBoundsException e = new OutOfBoundsException();
		// 加密
		MD5 md5 = new MD5();
		byte md5Byte[] = new byte[40];
		memset(md5Byte, 40);
		String strtime = Integer.toString(cl.icpTimestamp);
		byte[] temp = tools.string2Bytes(strtime);

		int idLen = cl.icpId.length;

		int authLen = cl.icpAuth.length;
		logger.debug("authLen:" + authLen);

		strcpy(md5Byte, cl.icpId, 0, idLen);
		strcpy(md5Byte, cl.icpAuth, 15, authLen);
		strcpy(md5Byte, temp, 15 + authLen, temp.length);
		int lengthMd5 = 15 + authLen + temp.length;
		try {
			out = new DataOutputStream(conn.sock.getOutputStream());
			ch.pkLen = 12;
			ch.pkCmd = 0x00000001;
			ch.pkSeq = conn.seq;
			conn.seq++;
			if (conn.seq == 0x7fffffff)
				conn.seq = 1;
			bodyLen = tools.strcpy(buf, cl.icpId, ch.pkLen);// add icpId

			byte[] bufMd5 = md5.getMD5ofStr(md5Byte, lengthMd5);// get md5 for
																// icpAuth
			bodyLen += tools.strcpy(buf, bufMd5, bodyLen + ch.pkLen); // add
																		// icpAuth

			bodyLen += tools.strcpy(buf, cl.icpVersion, bodyLen + ch.pkLen); // add
																				// icpVersion
			bodyLen += tools.strcpy(buf, cl.icpTimestamp, bodyLen + ch.pkLen); // add
																				// icpTimestamp
			// bodyLen
			// +=tools.strcpy(buf,(short)cl.icpTimestamp,bodyLen+ch.pkLen); //
			// add icpTimestamp

			ch.pkLen += bodyLen;
			tools.strcpy(buf, ch.pkLen, 0);
			tools.strcpy(buf, ch.pkCmd, 4);
			tools.strcpy(buf, ch.pkSeq, 8);
			logger.debug(Arrays.toString(buf));

			out.write(buf, 0, ch.pkLen);
			out.flush();

			out = null;
		} catch (IOException e1) {
			out = null;
			logger.error(e1, e1);
			throw e1;
		}
	}

	protected boolean readCountByte(DataInputStream in, byte buf[], int len) throws IOException {
		try {
			for (int i = 0; i < len; i++)
				buf[i] = in.readByte();

		} catch (IOException e) {
			in = null;
			logger.error("readCountByte", e);
			throw e;
		}
		return true;
	}

	protected void memset(byte b[], int len) {
		for (int i = 0; i < len; i++)
			b[i] = 0;

	}

	protected boolean readHead(DataInputStream in, CmppePack p) throws IOException {
		try {
			p.pkHead.pkLen = in.readInt();
			p.pkHead.pkCmd = in.readInt();
			p.pkHead.pkSeq = in.readInt();

			// System.out.println();
			logger.debug("readHeadPkLen:" + p.pkHead.pkLen+", readHeadPkCmd:" + p.pkHead.pkCmd+", readHeadPkSeq:" + p.pkHead.pkSeq);
		} catch (IOException e) {
			logger.error("readHead", e);
			throw e;

		}
		return true;
	}

	protected boolean sendUnknowncountByte(DataOutputStream out, byte buf[]) throws IOException {
		int i = 0;
		try {
			while (buf[i] != 0 && i < 200) {
				out.writeByte(buf[i]);
				i++;
			}
			out.write(buf[i]);
			out.flush();
		} catch (IOException e) {
			logger.error("sendUnknowncountByte", e);
			throw e;
		}
		return true;
	}

	protected int strcpy(byte d[], byte s[], int from, int maxlen) {
		int i;
		for (i = 0; i < maxlen; i++)
			d[i + from] = s[i];

		// d[i + from] = 0;
		return i;
	}

	protected void sendHeader(byte[] buf, CmppeHead ch) throws IOException {

		logger.debug("send ch.pkLen:" + ch.pkLen+", send ch.pkCmd:" + ch.pkCmd);
		tools.strcpy(buf, ch.pkLen, 0);
		tools.strcpy(buf, ch.pkCmd, 4);
		tools.strcpy(buf, ch.pkSeq, 8);

	}

	protected int testDigitString(byte t[], int maxlen) {
		int i;
		for (i = 0; t[i] != 0 && i < maxlen; i++)
			if (t[i] > 57 || t[i] < 48)
				return -1;

		return i != maxlen ? 1 : -2;
	}

	public void cmppLogout(ConnDesc conn) throws IOException {
		byte[] buf = new byte[100];
		byte[] Tmp = new byte[512];
		DataOutputStream out = null;
		CmppeHead ch = new CmppeHead();
		new CmppePack();
		try {
			out = new DataOutputStream(conn.sock.getOutputStream());
			new DataInputStream(conn.sock.getInputStream());
			ch.pkLen = 16;
			ch.pkCmd = 2;
			ch.pkStat = 0;
			ch.pkSeq = conn.seq;
			// sendHeader(buf, ch);
			conn.seq++;
			if (conn.seq == 0x7fffffff)
				conn.seq = 1;
			out = null;
		} catch (IOException e) {
			out = null;
			logger.error("cmppLogout", e);
			throw e;
		}
	}

	public void cmppCancel(ConnDesc conn, CmppeCancel cc) throws IOException, OutOfBoundsException {
		byte[] buf = new byte[100];
		byte[] Tmp = new byte[512];
		DataOutputStream out = null;
		CmppeHead ch = new CmppeHead();
		new CmppePack();
		try {
			out = new DataOutputStream(conn.sock.getOutputStream());
			ch.pkLen = 12;
			ch.pkCmd = 7;
			ch.pkStat = 0;
			ch.pkSeq = conn.seq;
			int len = cc.msgId.length;

			ch.pkLen += len;
			// sendHeader(buf,ch);
			// sendCountByte(out, cc.msgId, len);

			tools.strcpy(buf, cc.msgId, ch.pkLen);
			tools.strcpy(buf, ch.pkLen, 0);
			tools.strcpy(buf, ch.pkCmd, 4);
			tools.strcpy(buf, ch.pkSeq, 8);

			conn.seq++;
			if (conn.seq == 0x7fffffff)
				conn.seq = 1;
			out = null;
		} catch (IOException e) {
			out = null;
			logger.error("cmppCancel", e);
			throw e;
		}

	}

	public void cmppActiveTest(ConnDesc conn) throws IOException {
		logger.info(conn.sock);
		DataOutputStream out = null;
		CmppeHead ch = new CmppeHead();
		try {
			out = new DataOutputStream(conn.sock.getOutputStream());
			ch.pkLen = 12;
			ch.pkCmd = 8;
			ch.pkSeq = conn.seq;

			byte[] buf = new byte[12];
			int bodyLen = 0;
			tools.strcpy(buf, ch.pkLen + bodyLen, 0);
			tools.strcpy(buf, ch.pkCmd, 4);
			tools.strcpy(buf, ch.pkSeq, 8);
			out.write(buf, 0, 12); // 测试信息体为空
			logger.debug(Arrays.toString(buf));
			out.flush();
//			logger.debug("have send it");
			conn.seq++;
			if (conn.seq == 0x7fffffff)
				conn.seq = 1;
			out = null;
		} catch (IOException e) {
			out = null;
			logger.error("cmppActiveTest", e);
			throw e;
		}
	}

	protected void cmppSendActiveResp(ConnDesc conn, int seq) throws IOException {
		CmppeHead ch = new CmppeHead();

		try {
			DataOutputStream out = new DataOutputStream(conn.sock.getOutputStream());

			ch.pkLen = 12;
			ch.pkCmd = 0x80000008;
			ch.pkSeq = seq;

			int activeRespStat = 0;
			byte[] buf = new byte[100];
			int bodyLen = tools.strcpy(buf, activeRespStat, ch.pkLen);

			tools.strcpy(buf, ch.pkLen + bodyLen, 0);
			tools.strcpy(buf, ch.pkCmd, 4);
			tools.strcpy(buf, ch.pkSeq, 8);

			out.write(buf, 0, 12);
			out.flush();
		} catch (IOException e) {
			logger.error("cmppSendActiveResp", e);
			throw e;
		}
	}

	public int cmppSubmit(ConnDesc conn, CmppeSubmit cs) throws IOException {
		int userSeq = -1;
		byte buf[] = new byte[1024];
		byte[] Tmp = new byte[5];
		CmppeHead ch = new CmppeHead();
		new CmppePack();
		try {
			OutputStream os = conn.sock.getOutputStream();
			int len = 12;
			len += tools.strcpy(buf, cs.msgId, len);
			len += tools.strcpy(buf, cs.pkTotal, len);
			len += tools.strcpy(buf, cs.pkNumber, len);
			len += tools.strcpy(buf, cs.registeredDelivery, len);
			len += tools.strcpy(buf, cs.msgLevel, len);
			len += tools.strcpy(buf, cs.serviceId, len);
			len += tools.strcpy(buf, cs.feeUsertype, len);
			len += tools.strcpy(buf, cs.feeTerminalId, len);
			len += tools.strcpy(buf, cs.feeTerminalType, len);
			len += tools.strcpy(buf, cs.tpPid, len);
			len += tools.strcpy(buf, cs.tpUdhi, len);

			len += tools.strcpy(buf, cs.msgFmt, len);
			len += tools.strcpy(buf, cs.msgSrc, len);
			len += tools.strcpy(buf, cs.feetype, len);
			len += tools.strcpy(buf, cs.feecode, len);
			len += tools.strcpy(buf, cs.validTime, len);
			len += tools.strcpy(buf, cs.atTime, len);
			len += tools.strcpy(buf, cs.srcTerminalId, len);

			len += tools.strcpy(buf, cs.destusrTl, len);
			for (int i = 0; i < cs.destusrTl; i++) {
				len += tools.strcpy(buf, cs.destTerminalId[i], len);
			}
			len += tools.strcpy(buf, cs.destTerminalType, len);
			len += tools.strcpy(buf, cs.msgLength, len);
			len += tools.strcpy(buf, cs.msgContent, len);
			// len +=tools.strcpy(buf,cs.reserve,len);
			len += tools.strcpy(buf, cs.linkid, len);

			ch.pkLen = len;
			ch.pkCmd = 4;
			ch.pkSeq = conn.seq;

			tools.strcpy(buf, ch.pkLen, 0);
			tools.strcpy(buf, ch.pkCmd, 4);
			tools.strcpy(buf, ch.pkSeq, 8);

			// for(int i=0;i<len;i++)
			// System.out.print(buf[i]+",");
			os.write(buf, 0, len); // 写
			os.flush();
			userSeq = conn.seq;
			conn.seq++;
			if (conn.seq == 0x7fffffff)
				conn.seq = 1;

			// System.out.println("the seq is:" + userSeq);

		} catch (IOException e) {
			logger.error("cmppSubmit", e);
			throw e;
		} catch (Exception e) {
			logger.error("cmppSubmit", e);
		}
		return userSeq;
	}

	/*
	 * 读取接收到的数据包
	 */
	public void readPa(ConnDesc con) throws Exception {
		CmppeResult cr = null;
		try {
			cr = readResPack(con);
			switch (cr.packId) {

			case -2147483648:
				logger.debug("get nack pack");
				break;

			case -2147483647:
				CmppeLoginResult cl = (CmppeLoginResult) cr;
//				logger.debug("------------login resp----------: VERSION = " + cl.version);
//				logger.debug("------------login resp----------: STAT = " + ((CmppeResult) (cl)).stat);
				break;

			case -2147483646:
//				logger.debug("------------logout resp----------: STAT = " + cr.stat);
				break;

			case -2147483644:
				CmppeSubmitResult sr = (CmppeSubmitResult) cr;
				sr.flag = 0;
//				logger.debug("------------submit resp----------: STAT = " + sr.stat + " SEQ = " + sr.seq);
				break;

			case 5: // '\005'
//				logger.debug("------------deliver---------: STAT = 0");
				CmppeDeliverResult cd = (CmppeDeliverResult) cr;
				cmppSendDeliverResp(con, cd);
//				logger.debug("----------send deliver -------ok");
				break;

			case -2147483641:
//				logger.debug("---------cancel-----------: STAT = " + cr.stat);
				break;

			case -2147483640:
//				logger.debug("---------active resp-----------: STAT " + cr.stat);
				break;

			default:
				logger.debug("---------Error packet-----------");
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("readPa err...", e);
			throw new Exception("readPa err...");
		}
	}

	public CmppeResult readResPack(ConnDesc conn) throws IOException, UnknownPackException, DeliverFailException {
		DataInputStream in = null;
		CmppeResult cr = new CmppeResult();
		CmppeSubmitResult sr = new CmppeSubmitResult();
		new OutOfBoundsException();
		CmppePack pack = new CmppePack();
		new DataOutputStream(conn.sock.getOutputStream());
		in = new DataInputStream(conn.sock.getInputStream());
		do {

			readHead(in, pack);
			if (pack.pkHead.pkCmd != 8)
				break;
			// cmppSendActiveResp(conn, pack.pkHead.pkSeq); //if pkCmd =8 then
			// send activeResp
		} while (true);

		switch (pack.pkHead.pkCmd) {
		case -2147483648:
			cr.packId = 0x80000000;
			cr.stat = pack.pkHead.pkStat;
			return cr;

		case -2147483647: // cmppLoginRep
			CmppeLoginResult cl = new CmppeLoginResult();
			cl.packId = 0x80000001;
			// cl.stat = in.readByte();

			logger.debug("the length is:" + pack.pkHead.pkLen);

			cl.stat = in.readInt(); // 状态 =0(成功)
			if (pack.pkHead.pkLen > 16) {
				for (int k = 0; k < 16; k++)
					cl.auth[k] = in.readByte(); // 认证码

				// cl.version = in.readByte(); //服务器支持的最高版本号 =18
				cl.version = in.readByte();
			}
			return cl;

		case -2147483646:
			readCountByte(in, pack.buf, pack.pkHead.pkLen - 16);
			cr.packId = 0x80000002;
			cr.stat = pack.pkHead.pkStat;
			return cr;

		case -2147483644: // cmppSubmitRep
			sr.packId = 0x80000004;
			sr.seq = pack.pkHead.pkSeq;
			sr.stat = pack.pkHead.pkStat;
			if (pack.pkHead.pkLen - 16 > 0) {
				for (int k = 0; k < 8; k++)
					sr.msgId[k] = in.readByte(); // 认证码
			} else {
				sr.msgId = null;
				return sr;
			}
			sr.result = in.readInt();// change at 061208
			logger.debug("submit result is:::::" + sr.result);

			return sr;

		case 5: // '\005' get mo
			CmppeDeliverResult cd = new CmppeDeliverResult();
			try {
				byte packbuf[] = new byte[pack.pkHead.pkLen - 12];
				in.read(packbuf);
				cd.readInBytes(packbuf); // 解析deliver信息

				cd.seq = pack.pkHead.pkSeq;
				cd.packId = 5;

				return cd;
			} catch (Exception e1) {
				logger.error(" receive deliver error", e1);
				throw new UnknownPackException();
			}

		case -2147483641:
			readCountByte(in, pack.buf, pack.pkHead.pkLen - 16);
			cr.stat = pack.pkHead.pkStat;
			cr.packId = 0x80000007;
			return cr;

		case -2147483640:
			cr.stat = pack.pkHead.pkStat;

			cr.packId = 0x80000008;
			int successId = in.readByte();// change at 2008-11-11 in.readByte()
											// -> in.readInt();
			cr.stat = successId;
			logger.debug("------------successId--------:" + successId);
			return cr;

		}
		UnknownPackException un = new UnknownPackException();
		throw un;
	}

	public void cmppSendDeliverResp(ConnDesc conn, CmppeDeliverResult cd) throws IOException {
		CmppeHead ch = new CmppeHead();
		try {
			DataOutput out = new DataOutputStream(conn.sock.getOutputStream());
			ch.pkCmd = 0x80000005;
			ch.pkLen = 24;
			ch.pkSeq = cd.seq;
			sendHeader(ch, out);

			ByteCode delv = new ByteCode(12);
			delv.AddBytes(cd.msgId);
			// delv.AddInt8((short)cd.stat);
			delv.AddInt32(cd.stat);
			out.write(delv.getBytes());
		} catch (IOException e) {
			logger.error("decode error", e);
			throw e;
		} catch (Exception e) {
			logger.error("decode error", e);
			throw new IOException("decode error");
		}
	}

	protected void sendHeader(CmppeHead ch, DataOutput out) throws IOException {
		try {
			ByteCode bc = new ByteCode(3);

			bc.AddInt32(ch.pkLen);
			bc.AddInt32(ch.pkCmd);
			bc.AddInt32(ch.pkSeq);

			out.write(bc.getBytes());
		} catch (IOException e) {

			logger.error("send Head Exception", e);
			throw e;
		}
	}

	public static final byte CMPPE_MAX_ICP_ID_LEN = 6;
	public static final byte CMPPE_AUTH_LEN = 16;
	public static final byte CMPPE_MAX_SVC_LEN = 10;
	public static final byte CMPPE_DATETIME_LEN = 16;
	public static final byte CMPPE_MAX_DSTS_NUM = 100;
	public static final byte CMPPE_MAX_MSGID_LEN = 64;
	public static final byte CMPPE_MAX_MSISDN_LEN = 20;
	public static final short CMPPE_MAX_SM_LEN = 160;
	public static final byte CMPPE_DATE_TIME_LEN = 14;
	public static final byte CMPPE_MAX_FEE_TYPE = 99;
	public static final int CMPPE_MAX_INFO_FEE = 0xf423f;
	public static final byte CMPPE_MAX_PRIORITY = 9;
	public static final byte CMPPE_QUERY_TIME_LEN = 8;
	public static final byte CMPPE_QUERY_CODE_LEN = 10;
	public static final byte CMPPE_PK_HEAD_SIZE = 16;
	public static final byte CMPPE_PK_LEN_SIZE = 4;
	public static final byte CMPPE_MIN_PK_SIZE = 16;
	public static final short CMPPE_MAX_PK_SIZE = 2477;
	public static final byte CMPPE_MM_NO_REGIST = 0;
	public static final byte CMPPE_MM_REGIST = 1;
	public static final byte CMPPE_MM_CONTROL = 2;
	public static final byte CMPPE_BIND_SEND = 0;
	public static final byte CMPPE_BIND_RECV = 1;
	public static final byte CMPPE_DC_ASCII = 0;
	public static final byte CMPPE_DC_STK = 3;
	public static final byte CMPPE_DC_BIN = 4;
	public static final byte CMPPE_DC_UCS2 = 8;
	public static final byte CMPPE_DC_GB2312 = 15;
	public static final int CMPPE_NACK_RESP = 0x80000000;
	public static final int CMPPE_LOGIN = 1;
	public static final int CMPPE_LOGIN_RESP = 0x80000001;
	public static final int CMPPE_LOGOUT = 2;
	public static final int CMPPE_LOGOUT_RESP = 0x80000002;
	public static final int CMPPE_ROUTE = 3;
	public static final int CMPPE_ROUTE_RESP = 0x80000003;
	public static final int CMPPE_SUBMIT = 4;
	public static final int CMPPE_SUBMIT_RESP = 0x80000004;
	public static final int CMPPE_DELIVER = 5;
	public static final int CMPPE_DELIVER_RESP = 0x80000005;
	public static final int CMPPE_QUERY = 6;
	public static final int CMPPE_QUERY_RESP = 0x80000006;
	public static final int CMPPE_CANCEL = 7;
	public static final int CMPPE_CANCEL_RESP = 0x80000007;
	public static final int CMPPE_ACTIVE = 8;
	public static final int CMPPE_ACTIVE_RESP = 0x80000008;
	public static final int CMPPE_FORWORD = 9;
	public static final int CMPPE_FORWORD_RESP = 0x80000009;
	public static final int CMPPE_SUBMIT_M = 10;
	public static final int CMPPE_SUBMIT_M_RESP = 0x8000000a;
	public static final int CMPPE_RSP_SUCCESS = 0;
	public static final int CMPPE_RSP_OTHER_ERR = 1;
	public static final int CMPPE_RSP_INVAL_MSG_LEN = 2;
	public static final int CMPPE_RSP_UNKNOWN_CMD = 3;
	public static final int CMPPE_RSP_SYNC_ERR = 4;
	public static final int CMPPE_RSP_INVAL_STRUCT = 5;
	public static final int CMPPE_RSP_INVAL_ICP = 16;
	public static final int CMPPE_RSP_INVAL_AUTH = 17;
	public static final int CMPPE_RSP_INVAL_BIND_TYPE = 18;
	public static final int CMPPE_RSP_BINDED = 19;
	public static final int CMPPE_RSP_BIND_EXCEED = 20;
	public static final int CMPPE_RSP_NOT_BIND = 21;
	public static final int CMPPE_RSP_INVAL_MSG_MODE = 32;
	public static final int CMPPE_RSP_INVAL_DATA_CODING = 33;
	public static final int CMPPE_RSP_INVAL_SVC_TYPE = 34;
	public static final int CMPPE_RSP_INVAL_FEE_TYPE = 35;
	public static final int CMPPE_RSP_INVAL_DATETIME = 36;
	public static final int CMPPE_RSP_DSTS_EXCEED = 37;
	public static final int CMPPE_RSP_SMLEN_EXCEED = 38;
	public static final int CMPPE_RSP_INVAL_MSISDN = 38;
	public static final int CMPPE_RSP_INVAL_PARA = 39;
	public static final int CMPPE_RSP_PK_SEQ_REPEAT = 48;
	public static final int CMPPE_RSP_PK_SEQ_EXCEED = 49;
	public static final int CMPPE_RSP_MSG_NOT_FOUND = 50;
	public static final int CMPPE_RSP_LEN_BAD = 136;
	public static final byte CMPPE_NET_TIMEOUT = 120;
}
