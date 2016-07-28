//2003-06-10
package comsd.commerceware.cmpp;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;

public final class CMPP {
	private final static Logger LOG = Logger.getLogger(CMPP.class);
	mytools tools = new mytools();
	////////////////// 日志记录/////////////
	// Logger myLogger = Logger.getLogger("MsgSendLogger");
	// Logger mySonLogger = Logger.getLogger("myLogger.mySonLogger");
	// PropertyConfigurator.configure("/app/smsgw/smsplatform/CMPP/log4j.properties");

	//////////////////////////////////////
	public CMPP() {

	}

	// 连接网关
	public void cmpp_connect_to_ismg(String host, int port, conn_desc conn) throws IOException {
		Socket s = null;
		try {
			s = new Socket(host, port);
			// System.out.println(s);
			LOG.info(s);
			s.setSoTimeout(0x927c0);
		} catch (IOException e) {
			throw e;
		}
		conn.seq = 1; // 序号为1 代表开始
		conn.sock = s;
	}

	// 断开连接
	public void cmpp_disconnect_from_ismg(conn_desc conn) {
		try {
			conn.sock.close();
		} catch (Exception _ex) {
			return;
		}
	}

	// login ismg
	public void cmpp_login(conn_desc conn, cmppe_login cl) throws IOException, OutOfBoundsException {
		cmppe_head ch = new cmppe_head();

		byte[] buf = new byte[100];
		int body_len = 0;

		DataOutputStream out = null;
		OutOfBoundsException e = new OutOfBoundsException();
		// 加密
		MD5 md5 = new MD5();
		byte md5Byte[] = new byte[40];
		memset(md5Byte, 40);
		String strtime = Integer.toString(cl.icp_timestamp);
		byte[] temp = tools.string2Bytes(strtime);

		int id_len = cl.icp_id.length;

		int auth_len = cl.icp_auth.length;
		System.out.println("auth_len:" + auth_len);

		strcpy(md5Byte, cl.icp_id, 0, id_len);
		strcpy(md5Byte, cl.icp_auth, 15, auth_len);
		strcpy(md5Byte, temp, 15 + auth_len, temp.length);
		int lengthMd5 = 15 + auth_len + temp.length;
		try {
			out = new DataOutputStream(conn.sock.getOutputStream());
			ch.pk_len = 12;
			ch.pk_cmd = 0x00000001;
			ch.pk_seq = conn.seq;
			conn.seq++;
			if (conn.seq == 0x7fffffff)
				conn.seq = 1;
			body_len = tools.strcpy(buf, cl.icp_id, ch.pk_len);// add icp_id

			byte[] bufMd5 = md5.getMD5ofStr(md5Byte, lengthMd5);// get md5 for
																													// icp_auth
			body_len += tools.strcpy(buf, bufMd5, body_len + ch.pk_len); // add
																																		// icp_auth

			body_len += tools.strcpy(buf, cl.icp_version, body_len + ch.pk_len); // add
																																						// icp_version
			body_len += tools.strcpy(buf, cl.icp_timestamp, body_len + ch.pk_len); // add
																																							// icp_timestamp
			// body_len
			// +=tools.strcpy(buf,(short)cl.icp_timestamp,body_len+ch.pk_len); // add
			// icp_timestamp

			ch.pk_len += body_len;
			tools.strcpy(buf, ch.pk_len, 0);
			tools.strcpy(buf, ch.pk_cmd, 4);
			tools.strcpy(buf, ch.pk_seq, 8);
			for (int i = 0; i < ch.pk_len; i++) {
				// System.out.print(buf[i] + ",");
			}
			out.write(buf, 0, ch.pk_len);
			out.flush();
			out = null;
		} catch (IOException e1) {
			out = null;
			throw e1;
		}
	}

	protected boolean read_count_byte(DataInputStream in, byte buf[], int len) throws IOException {
		try {
			for (int i = 0; i < len; i++)
				buf[i] = in.readByte();

		} catch (IOException e) {
			in = null;
			throw e;
		}
		return true;
	}

	protected void memset(byte b[], int len) {
		for (int i = 0; i < len; i++)
			b[i] = 0;

	}

	protected boolean readHead(DataInputStream in, cmppe_pack p) throws IOException {
		try {
			p.pk_head.pk_len = in.readInt();
			p.pk_head.pk_cmd = in.readInt();
			p.pk_head.pk_seq = in.readInt();

			// System.out.println();
			// System.out.println("read resp message .....");
			// System.out.println("readHead_pk_len:" + p.pk_head.pk_len);
			// System.out.println("readHead_pk_cmd:" + p.pk_head.pk_cmd);
			// System.out.println("readHead_pk_seq:" + p.pk_head.pk_seq);
			LOG.info("read resp message readHead_pk_seq:" + p.pk_head.pk_seq);
		} catch (IOException e) {
			throw e;
		}
		return true;
	}

	protected boolean send_unknowncount_byte(DataOutputStream out, byte buf[]) throws IOException {
		int i = 0;
		try {
			while (buf[i] != 0 && i < 200) {
				out.writeByte(buf[i]);
				i++;
			}
			out.write(buf[i]);
			out.flush();
		} catch (IOException e) {

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

	protected void sendHeader(byte[] buf, cmppe_head ch) throws IOException {

		System.out.println("send ch.pk_len:" + ch.pk_len);
		System.out.println("send ch.pk_cmd:" + ch.pk_cmd);
		tools.strcpy(buf, ch.pk_len, 0);
		tools.strcpy(buf, ch.pk_cmd, 4);
		tools.strcpy(buf, ch.pk_seq, 8);

	}

	protected int testDigitString(byte t[], int maxlen) {
		int i;
		for (i = 0; t[i] != 0 && i < maxlen; i++)
			if (t[i] > 57 || t[i] < 48)
				return -1;

		return i != maxlen ? 1 : -2;
	}

	public void cmpp_logout(conn_desc conn) throws IOException {
		byte[] buf = new byte[100];
		byte[] _tmp = new byte[512];
		DataOutputStream out = null;
		cmppe_head ch = new cmppe_head();
		new cmppe_pack();
		try {
			out = new DataOutputStream(conn.sock.getOutputStream());
			new DataInputStream(conn.sock.getInputStream());
			ch.pk_len = 16;
			ch.pk_cmd = 2;
			ch.pk_stat = 0;
			ch.pk_seq = conn.seq;
			// sendHeader(buf, ch);
			conn.seq++;
			if (conn.seq == 0x7fffffff)
				conn.seq = 1;
			out = null;
		} catch (IOException e) {
			out = null;
			throw e;
		}
	}

	public void cmpp_cancel(conn_desc conn, cmppe_cancel cc) throws IOException, OutOfBoundsException {
		byte[] buf = new byte[100];
		byte[] _tmp = new byte[512];
		DataOutputStream out = null;
		cmppe_head ch = new cmppe_head();
		new cmppe_pack();
		try {
			out = new DataOutputStream(conn.sock.getOutputStream());
			ch.pk_len = 12;
			ch.pk_cmd = 7;
			ch.pk_stat = 0;
			ch.pk_seq = conn.seq;
			int len = cc.msg_id.length;

			ch.pk_len += len;
			// sendHeader(buf,ch);
			// send_count_byte(out, cc.msg_id, len);

			tools.strcpy(buf, cc.msg_id, ch.pk_len);
			tools.strcpy(buf, ch.pk_len, 0);
			tools.strcpy(buf, ch.pk_cmd, 4);
			tools.strcpy(buf, ch.pk_seq, 8);

			conn.seq++;
			if (conn.seq == 0x7fffffff)
				conn.seq = 1;
			out = null;
		} catch (IOException e) {
			out = null;
			throw e;
		}
	}

	public void cmpp_active_test(conn_desc conn) throws IOException {
		//////////////////////
		// System.out.println("conn socket::::::::::::");
		// System.out.println(conn.sock);
		// System.out.println("conn socket::::::::::::");
		/////////////////////
		DataOutputStream out = null;
		cmppe_head ch = new cmppe_head();
		try {
			out = new DataOutputStream(conn.sock.getOutputStream());
			ch.pk_len = 12;
			ch.pk_cmd = 8;
			ch.pk_seq = conn.seq;

			byte[] buf = new byte[12];
			int body_len = 0;
			tools.strcpy(buf, ch.pk_len + body_len, 0);
			tools.strcpy(buf, ch.pk_cmd, 4);
			tools.strcpy(buf, ch.pk_seq, 8);
			out.write(buf, 0, 12); // 测试信息体为空
			for (int i = 0; i < 12; i++) {
				// System.out.print(buf[i] + ",");
			}
			// System.out.println("seq:"+conn.seq);
			// System.out.println("sock:"+conn.sock);
			out.flush();
			conn.seq++;
			if (conn.seq == 0x7fffffff)
				conn.seq = 1;
			out = null;
		} catch (IOException e) {
			out = null;
			throw e;
		}
	}

	protected void cmpp_send_active_resp(conn_desc conn, int seq) throws IOException {
		cmppe_head ch = new cmppe_head();

		try {
			DataOutputStream out = new DataOutputStream(conn.sock.getOutputStream());

			ch.pk_len = 12;
			ch.pk_cmd = 0x80000008;
			ch.pk_seq = seq;

			int active_resp_stat = 0;
			byte[] buf = new byte[100];
			int body_len = tools.strcpy(buf, active_resp_stat, ch.pk_len);

			tools.strcpy(buf, ch.pk_len + body_len, 0);
			tools.strcpy(buf, ch.pk_cmd, 4);
			tools.strcpy(buf, ch.pk_seq, 8);

			out.write(buf, 0, 12);
			out.flush();
		} catch (IOException e) {
			throw e;
		}
	}

	public int cmpp_submit(conn_desc conn, cmppe_submit cs) throws IOException {
		int user_seq = -1;
		byte buf[] = new byte[1024];
		byte[] _tmp = new byte[5];
		cmppe_head ch = new cmppe_head();
		new cmppe_pack();
		try {
			OutputStream os = conn.sock.getOutputStream();
			int len = 12;
			len += tools.strcpy(buf, cs.msg_id, len);
			len += tools.strcpy(buf, cs.pk_total, len);
			len += tools.strcpy(buf, cs.pk_number, len);
			len += tools.strcpy(buf, cs.registered_delivery, len);
			len += tools.strcpy(buf, cs.msg_level, len);
			len += tools.strcpy(buf, cs.service_id, len);
			len += tools.strcpy(buf, cs.fee_usertype, len);
			len += tools.strcpy(buf, cs.fee_terminal_id, len);
			len += tools.strcpy(buf, cs.fee_terminal_type, len);
			len += tools.strcpy(buf, cs.tp_pid, len);
			len += tools.strcpy(buf, cs.tp_udhi, len);

			len += tools.strcpy(buf, cs.msg_fmt, len);
			len += tools.strcpy(buf, cs.msg_src, len);
			len += tools.strcpy(buf, cs.feetype, len);
			len += tools.strcpy(buf, cs.feecode, len);
			len += tools.strcpy(buf, cs.valid_time, len);
			len += tools.strcpy(buf, cs.at_time, len);
			len += tools.strcpy(buf, cs.src_terminal_id, len);

			len += tools.strcpy(buf, cs.destusr_tl, len);
			for (int i = 0; i < cs.destusr_tl; i++) {
				len += tools.strcpy(buf, cs.dest_terminal_id[i], len);
			}
			len += tools.strcpy(buf, cs.dest_terminal_type, len);
			len += tools.strcpy(buf, cs.msg_length, len);
			len += tools.strcpy(buf, cs.msg_content, len);
			// len +=tools.strcpy(buf,cs.reserve,len);
			len += tools.strcpy(buf, cs.linkid, len);

			ch.pk_len = len;
			ch.pk_cmd = 4;
			ch.pk_seq = conn.seq;

			tools.strcpy(buf, ch.pk_len, 0);
			tools.strcpy(buf, ch.pk_cmd, 4);
			tools.strcpy(buf, ch.pk_seq, 8);

			// for(int i=0;i<len;i++)
			// System.out.print(buf[i]+",");
			os.write(buf, 0, len); // 写
			os.flush();
			user_seq = conn.seq;
			conn.seq++;
			if (conn.seq == 0x7fffffff)
				conn.seq = 1;

			// System.out.println("the seq is:" + user_seq);

		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user_seq;
	}

	public void readPa(conn_desc con) throws Exception {
		cmppe_result cr = null;
		try {
			cr = readResPack(con);
			switch (cr.pack_id) {

			case -2147483648:
				System.out.println("get nack pack");
				break;

			case -2147483647:
				cmppe_login_result cl = (cmppe_login_result) cr;
				System.out.println("------------login resp----------: VERSION = " + cl.version);
				System.out.println("------------login resp----------: STAT = " + ((cmppe_result) (cl)).stat);
				break;

			case -2147483646:
				System.out.println("------------logout resp----------: STAT = " + cr.stat);
				break;

			case -2147483644:
				cmppe_submit_result sr = (cmppe_submit_result) cr;
				sr.flag = 0;
				System.out.println("------------submit resp----------: STAT = " + sr.stat + " SEQ = " + sr.seq);
				break;

			case 5: // '\005'
				System.out.println("------------deliver---------: STAT = 0");
				cmppe_deliver_result cd = (cmppe_deliver_result) cr;
				cmpp_send_deliver_resp(con, cd);
				System.out.println("----------send deliver -------ok");
				break;

			case -2147483641:
				System.out.println("---------cancel-----------: STAT = " + cr.stat);
				break;

			case -2147483640:
				// myLogger.info(FormatSysTime.getCurrentTimeA() + "---------active
				// resp-----------: STAT " + cr.stat);
				// System.out.println("---------active resp-----------: STAT " +
				// cr.stat);
				break;

			default:
				LOG.error("---------Error packet-----------");
				// System.out.println("---------Error packet-----------");
				break;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.out.println("have a exception");
			throw new Exception("readPa err...");
		}
	}

	public cmppe_result readResPack(conn_desc conn) throws IOException, UnknownPackException, DeliverFailException {
		DataInputStream in = null;
		cmppe_result cr = new cmppe_result();
		cmppe_submit_result sr = new cmppe_submit_result();
		new OutOfBoundsException();
		cmppe_pack pack = new cmppe_pack();
		new DataOutputStream(conn.sock.getOutputStream());
		in = new DataInputStream(conn.sock.getInputStream());
		do {

			readHead(in, pack);
			if (pack.pk_head.pk_cmd != 8) {
				break;
			} else {
				LOG.debug("receive gateway active");
			}
			// cmpp_send_active_resp(conn, pack.pk_head.pk_seq); //if pk_cmd =8 then
			// send active_resp
		} while (true);

		switch (pack.pk_head.pk_cmd) {
		case -2147483648:
			cr.pack_id = 0x80000000;
			cr.stat = pack.pk_head.pk_stat;
			return cr;

		case -2147483647: // cmpp_login_rep
			cmppe_login_result cl = new cmppe_login_result();
			cl.pack_id = 0x80000001;
			// cl.stat = in.readByte();
			///////////////////
			// System.out.println("::::::::::::::::::::");
			// System.out.println("::::::::::::::::::::");
			// System.out.println("::::::::::::::::::::");
			// System.out.println("the length is:" + pack.pk_head.pk_len);
			// System.out.println("::::::::::::::::::::");
			// System.out.println("::::::::::::::::::::");
			// System.out.println("::::::::::::::::::::");
			///////////////////

			cl.stat = in.readInt(); // 状态 =0(成功)
			if (pack.pk_head.pk_len > 16) {
				for (int k = 0; k < 16; k++)
					cl.auth[k] = in.readByte(); // 认证码

				// cl.version = in.readByte(); //服务器支持的最高版本号 =18
				cl.version = in.readByte();
			}
			return cl;

		case -2147483646:
			read_count_byte(in, pack.buf, pack.pk_head.pk_len - 16);
			cr.pack_id = 0x80000002;
			cr.stat = pack.pk_head.pk_stat;
			return cr;

		case -2147483644: // cmpp_submit_rep
			sr.pack_id = 0x80000004;
			sr.seq = pack.pk_head.pk_seq;
			sr.stat = pack.pk_head.pk_stat;
			if (pack.pk_head.pk_len - 16 > 0) {
				for (int k = 0; k < 8; k++)
					sr.msg_id[k] = in.readByte(); // 认证码
			} else {
				sr.msg_id = null;
				return sr;
			}
			sr.result = in.readInt();// change at 061208
			System.out.println("submit result is:::::" + sr.result);

			return sr;

		case 5: // '\005' get mo
			System.out.println("------- Case 5 -------");
			cmppe_deliver_result cd = new cmppe_deliver_result();
			try {
				byte packbuf[] = new byte[pack.pk_head.pk_len - 12];
				in.read(packbuf);
				cd.readInBytes(packbuf); // 解析deliver信息

				cd.seq = pack.pk_head.pk_seq;
				cd.pack_id = 5;

				return cd;
			} catch (Exception e1) {
				System.out.println(" receive deliver error..............");
				System.out.println(e1.toString());
				throw new UnknownPackException();
			}

		case -2147483641:
			read_count_byte(in, pack.buf, pack.pk_head.pk_len - 16);
			cr.stat = pack.pk_head.pk_stat;
			cr.pack_id = 0x80000007;
			return cr;

		case -2147483640:
			cr.stat = pack.pk_head.pk_stat;

			cr.pack_id = 0x80000008;
			int success_id = in.readByte();// change at 2008-11-11 in.readByte() ->
																			// in.readInt();
			cr.stat = success_id;
			// System.out.println("------------success_id--------:" + success_id);
			return cr;

		}
		UnknownPackException un = new UnknownPackException();
		throw un;
	}

	public void cmpp_send_deliver_resp(conn_desc conn, cmppe_deliver_result cd) throws IOException {
		cmppe_head ch = new cmppe_head();
		try {
			DataOutput out = new DataOutputStream(conn.sock.getOutputStream());
			ch.pk_cmd = 0x80000005;
			ch.pk_len = 24;
			ch.pk_seq = cd.seq;
			sendHeader(ch, out);

			ByteCode delv = new ByteCode(2);
			delv.AddBytes(cd.msg_id);
			// delv.AddInt8((short)cd.stat);
			delv.AddInt32(cd.stat);
			out.write(delv.getBytes());
		} catch (IOException e) {
			throw e;
		} catch (Exception e1) {
			throw new IOException("decode error");
		}
	}

	protected void sendHeader(cmppe_head ch, DataOutput out) throws IOException {
		try {
			ByteCode bc = new ByteCode(3);

			bc.AddInt32(ch.pk_len);
			bc.AddInt32(ch.pk_cmd);
			bc.AddInt32(ch.pk_seq);

			out.write(bc.getBytes());
		} catch (IOException e) {

			System.out.println("send Head Exception" + e.getMessage());
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
