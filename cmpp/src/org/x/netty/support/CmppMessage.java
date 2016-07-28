package org.x.netty.support;

import java.util.Arrays;

public class CmppMessage {

	@Override
	public String toString() {
		return "CmppMessage [cmd=" + cmd + ", seq=" + seq + ", body=" + Arrays.toString(body) + "]";
	}

	private int cmd;
	private int seq;
	private byte[] body;

	public int getCmd() {
		return cmd;
	}

	public void setCmd(int cmd) {
		this.cmd = cmd;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

}
