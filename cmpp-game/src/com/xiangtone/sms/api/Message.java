/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package com.xiangtone.sms.api;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

import org.apache.log4j.Logger;

public class Message {
	private static Logger logger = Logger.getLogger(Message.class);

	public Message() {
	}

	// connect xtsms platform server
	public void connectToServer(String host, int port, ConnDesc conn) throws IOException {
		Socket s = null;
		try {
			s = new Socket(host, port);
			s.setSoTimeout(0x927c0);
			logger.debug(s.toString());
		} catch (IOException e) {
			throw e;
		}
		conn.sock = s;

	}

	// disconnect from xtsms platform server
	public void disconnectFromServer(ConnDesc conn) {
		try {
			conn.sock.getOutputStream().close();
			conn.sock.getInputStream().close();
			conn.sock.close();
			conn.sock = null;
			// conn.sock.close();
		} catch (Exception e) {
			return;
		}
	}

	// send smDeliver
	public void sendSmDeliver(ConnDesc conn, SmDeliver deliver) throws IOException {
		DataOutputStream out = null;
		try {
			out = new DataOutputStream(conn.sock.getOutputStream());

			byte[] buf = deliver.getBytes(); // 信息体 message body
			int bodyLen = buf.length; // 信息体长度
			byte[] header = new byte[8]; // 信息头
			ByteCode bc = new ByteCode(8);
			logger.debug(8 + bodyLen);
			bc.AddInt(8 + bodyLen); // 信息头 add total length
			bc.AddInt(StateCode.SM_DELIVER); // 信息头 add message type
			// bc.AddInt(3);
			bc.AddBytes(buf);// 信息体 add message body
			out.write(bc.getBytes());
			out.flush();

		} catch (IOException e) {
			out = null;
			throw e;
		} catch (Exception e) {
			logger.error("sendSmDeliver", e);
		}

	}

	// send smSubmit
	public void sendSmSubmit(ConnDesc conn, SmSubmit sub) throws IOException {
		DataOutputStream out = null;
		try {
			out = new DataOutputStream(conn.sock.getOutputStream());

			byte[] buf = sub.getBytes(); // 信息体 message body
			int bodyLen = buf.length; // 信息体长度
			byte[] header = new byte[8]; // 信息头
			ByteCode bc = new ByteCode(8);

			bc.AddInt(8 + bodyLen); // 信息头 add total length
			bc.AddInt(StateCode.SM_SUBMIT); // 信息头 add message type
			// bc.AddInt(3);
			bc.AddBytes(buf);// 信息体 add message body
			out.write(bc.getBytes());
			out.flush();

		} catch (IOException e) {
			out = null;
			throw e;
		} catch (Exception e) {
			logger.error("sendSmSubmit", e);
		}

	}

	// read message head
	protected boolean readHead(DataInputStream in, SmPack p) throws IOException {
		try {
			p.pkHead.pkLen = in.readInt();
			p.pkHead.pkCmd = in.readInt();
			logger.debug("readHead ...pkCmd:" + p.pkHead.pkCmd);
		} catch (IOException e) {
			logger.error("readHead", e);
			throw e;
		}
		return true;
	}

	// send message head
	protected void sendHeader(SmHeader ch, DataOutput out) throws IOException {
		try {
			ByteCode bc = new ByteCode(4); // 4 bytes of head

			bc.AddInt32(ch.pkLen);
			bc.AddInt32(ch.pkCmd);

			out.write(bc.getBytes());
		} catch (IOException e) {
			logger.error("send Head Exception", e);
			throw e;
		}
	}

	// read respack
	public void readPa(ConnDesc conn) {

		SmResult sr = null;

		try {
			sr = readResPack(conn);

			switch (sr.packCmd) {
			case 1: // StateCode.smSubmit
				SmSubmitResult ssr = (SmSubmitResult) sr;
				logger.debug("----receiver vcp ------stat=" + ssr.stat);
				logger.debug("----receiver vcp submit message------");
				smSendSubmitAck(conn, ssr.stat);
				break;
			case 2: // StatCode.smSubmitAck
				SmSubmitAckResult ssra = (SmSubmitAckResult) sr;
				logger.debug("--------" + ssra.stat);
				break;
			case 3:
				SmDeliverResult sdr = (SmDeliverResult) sr;
				logger.debug(" ------receiver platform-----stat =" + sdr.stat);
				smSendDeliverAck(conn, sdr.stat);
				break;
			case 4:
				SmDeliverAckResult sdar = (SmDeliverAckResult) sr;
				logger.debug("---------deliverAck-----:" + sdar.stat);
				break;

			default:
				logger.debug("---------Error packet-----------");
				break;
			}
		} catch (Exception e) {
			logger.error("readPa", e);
			e.printStackTrace();
		} finally {
			try {
				if (conn.sock != null)
					conn.sock.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// send smSubmitAck
	public void smSendSubmitAck(ConnDesc conn, String ackCode) throws Exception {
		SmHeader ch = new SmHeader();
		try {
			DataOutput out = new DataOutputStream(conn.sock.getOutputStream());
			ch.pkCmd = 2;// smSubmitAck
			int len1 = ackCode.getBytes().length;
			ch.pkLen = 8 + 3 + len1;
			sendHeader(ch, out);

			ByteCode ack = new ByteCode(3);
			ack.AddByte(StateCode.ACK_CODE); // add Type
			ack.AddInt16((short) (3 + len1)); // add length
			ack.addAsciiz(ackCode, len1); // add value string
			byte[] b = ack.getBytes();
			logger.debug(Arrays.toString(b));
			// for(int i=0;i<b.length;i++)
			// System.out.print(b[i]+",");
			out.write(ack.getBytes());

		} catch (Exception e) {
			logger.error("smSendSubmitAck",e);
			throw e;
		}

	}

	// send smDeliverAck
	public void smSendDeliverAck(ConnDesc conn, String ackCode) throws Exception {
		SmHeader ch = new SmHeader();
		try {
			DataOutput out = new DataOutputStream(conn.sock.getOutputStream());
			ch.pkCmd = 4;// smDeliverAck
			int len1 = ackCode.getBytes().length;
			ch.pkLen = 8 + 3 + len1;
			sendHeader(ch, out);

			ByteCode ack = new ByteCode(3);
			ack.AddByte(StateCode.ACK_CODE); // add Type
			ack.AddInt16((short) (3 + len1)); // add length
			ack.addAsciiz(ackCode, len1); // add value string
			byte[] b = ack.getBytes();
			logger.debug(Arrays.toString(b));
			// for(int i=0;i<b.length;i++)
			// System.out.print(b[i]+",");
			out.write(ack.getBytes());

		} catch (Exception e) {
			logger.error("smSendDeliverAck",e);
			throw e;
		}

	}

	public SmResult readResPack(ConnDesc conn) throws IOException, UnknownPackException {
		DataInputStream in = null;
		SmResult sr = new SmResult();
		SmPack pack = new SmPack();

		in = new DataInputStream(conn.sock.getInputStream());

		readHead(in, pack); // read header
		logger.debug("totalLen:" + pack.pkHead.pkLen);
		byte packbuf[] = new byte[pack.pkHead.pkLen - 8];
		logger.debug(Arrays.toString(packbuf));
		in.read(packbuf); // read body message
		//////////////////// add at 061206
		/*
		 * for(int k = 0;k < packbuf.length;k++){ System.out.print(packbuf[k] +
		 * " "); }
		 */
		//////////////////////////////////
		switch (pack.pkHead.pkCmd) {

		case 1:
			logger.debug("------- Case 1 -------");
			SmSubmitResult ssr = new SmSubmitResult();
			try {
				ssr.readInBytes(packbuf); // 处理信息体
				ssr.packCmd = 1;
				return ssr;
			} catch (Exception e1) {
				throw new UnknownPackException();
			}
			// break;
		case 2:
			logger.debug("-------case 2--------");
			SmSubmitAckResult ssra = new SmSubmitAckResult();
			try {
				ssra.readInBytes(packbuf);
				ssra.packCmd = 2;
				return ssra;
			} catch (Exception e) {
				throw new UnknownPackException();
			}

		case 3:// 平台上行信息
			logger.debug("-------Case 3 --------");
			SmDeliverResult sdr = new SmDeliverResult();
			try {
				sdr.readInBytes(packbuf);
				sdr.packCmd = 3;
				return sdr;
			} catch (Exception e2) {
				throw new UnknownPackException();
			}
			// break;
		case 4:
			logger.debug("-----case 4------");
			SmDeliverAckResult adar = new SmDeliverAckResult();
			try {
				adar.readInBytes(packbuf);
				adar.packCmd = 4;
				return adar;
			} catch (Exception e) {
				throw new UnknownPackException();
			}

		default:
			// throw new UnknowPackException();
			break;

		}
		UnknownPackException un = new UnknownPackException();
		throw un;
	}

}