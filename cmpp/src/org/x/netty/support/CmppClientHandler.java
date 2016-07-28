package org.x.netty.support;

import java.net.ConnectException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.x.NettyClient;
import org.x.SMSIsmgInfo;
import org.x.SMSoperate;

import com.xiangtone.util.IntByteConvertor;

import comsd.commerceware.cmpp.CMPP;
import comsd.commerceware.cmpp.CmppLogin;
import comsd.commerceware.cmpp.OutOfBoundsException;
import comsd.commerceware.cmpp.UnknownPackException;
import comsd.commerceware.cmpp.cmppe_deliver_result;
import comsd.commerceware.cmpp.cmppe_submit_result;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

@Sharable
public class CmppClientHandler extends ChannelInboundHandlerAdapter {

	private final static Logger LOG = Logger.getLogger(CmppClientHandler.class);

	private final NettyClient client;
	private long startTime = -1;
	public static final String ISMGID = "01";

	public CmppClientHandler(NettyClient nettyClient) {
		this.client = nettyClient;
	}

	@Override
	public void handlerAdded(ChannelHandlerContext arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void handlerRemoved(ChannelHandlerContext arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		LOG.debug("channelRead");
		CmppMessage cmppMessage = (CmppMessage) msg;
		switch (cmppMessage.getCmd()) {

		case -2147483648:
			LOG.info("get nack pack");
			break;

		case -2147483647:
			LOG.info("------------login resp----------");
			// LOG.info("------------login resp----------: VERSION = " + cl.version);
			// LOG.info("------------login resp----------: STAT = " + ((cmppe_result)
			// (cl)).stat);
			break;

		case -2147483646:
			// LOG.info("------------logout resp----------: STAT = " + cr.stat);
			LOG.info("------------logout resp----------: ");
			break;

		case -2147483644:
			// cmppe_submit_result sr = (cmppe_submit_result) cr;
			// sr.flag = 0;
			// LOG.info("------------submit resp----------: STAT = " + sr.stat + " SEQ
			// = " + sr.seq);
			procSubmitResp(cmppMessage);
			LOG.info("------------submit resp----------:");
			break;

		case 5: // '\005'
			LOG.info("------------deliver---------: STAT = 0");

			cmppe_deliver_result cd = new cmppe_deliver_result();
			try {
				cd.readInBytes(cmppMessage.getBody()); // Ω‚Œˆdeliver–≈œ¢

				cd.seq = cmppMessage.getSeq();
				cd.pack_id = 5;

			} catch (Exception e1) {
				System.out.println(" receive deliver error..............");
				System.out.println(e1.toString());
				e1.printStackTrace();
				throw new UnknownPackException();
			}

			// ctx.writeAndFlush(msg)
			//
			// cmppe_deliver_result cd = (cmppe_deliver_result) cr;
			// cmpp_send_deliver_resp(con, cd);
			LOG.info("----------send deliver -------ok");
			break;
		case -2147483641:
			LOG.info("---------cancel-----------: STAT = ");
			break;

		case -2147483640:
			// myLogger.info(FormatSysTime.getCurrentTimeA() + "---------active
			// resp-----------: STAT " + cr.stat);
			// LOG.info("---------active resp-----------: STAT " +
			// cr.stat);
			break;

		default:
			LOG.info("---------Error packet-----------");
			break;
		}
		LOG.debug(cmppMessage);
	}

	private void procSubmitResp(CmppMessage cmppMessage) {
		cmppe_submit_result sr = new cmppe_submit_result();
		sr.pack_id = 0x80000004;
		sr.seq = cmppMessage.getSeq();
		if (cmppMessage.getBody().length == 12) {
			for (int k = 0; k < 8; k++) {
				sr.msg_id[k] = cmppMessage.getBody()[k];
				sr.result = byteArrayToInt(cmppMessage.getBody(), 8);
			}
		} else {
			sr.msg_id = null;
			sr.result = byteArrayToInt(cmppMessage.getBody(), 0);
		}
		sr.flag = 0;
		SMSoperate handle = new SMSoperate();
		handle.receiveSubmitResp(this.ISMGID, (int) sr.seq, Long.toString(IntByteConvertor.getLong(sr.msg_id, 0)),
				(int) sr.result);
		// sr.result = in.readInt();// change at 061208
		LOG.debug("submit result is:::::" + sr.result);
	}

	public static int byteArrayToInt(byte[] b, int offset) {
		int value = 0;
		for (int i = 0; i < 4; i++) {
			int shift = (4 - 1 - i) * 8;
			value += (b[i + offset] & 0x000000FF) << shift;
		}
		return value;
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent e = (IdleStateEvent) evt;
			if (e.state() == IdleState.READER_IDLE) {
				ctx.close();
			} else if (e.state() == IdleState.WRITER_IDLE) {
				CmppMessage cmppMessage = new CmppMessage();
				cmppMessage.setCmd(8);
				cmppMessage.setSeq(NettyClient.SEQ++);
				byte[] body = new byte[0];
				cmppMessage.setBody(body);
				ctx.writeAndFlush(cmppMessage);
			}
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		if (startTime < 0) {
			startTime = System.currentTimeMillis();
		}
		LOG.debug("active");
		try {
			SMSIsmgInfo smsIsmgInfo = new SMSIsmgInfo();
			CmppLogin cmppLogin = new CmppLogin();
			cmppLogin.set_icpid(SMSIsmgInfo.gd_icpID);
			cmppLogin.set_auth(SMSIsmgInfo.gd_icpShareKey);
			cmppLogin.set_version((byte) 0x30);
			cmppLogin.set_timestamp(1111101020);
			CMPP cmpp = new CMPP();
			cmpp.cmppLoginBytes(cmppLogin);
			ByteBuf firstMessage = Unpooled.copiedBuffer(cmpp.cmppLoginBytes(cmppLogin));
			LOG.debug(cmpp.cmppLoginBytes(cmppLogin));
			// ctx.writeAndFlush(firstMessage);
			CmppMessage loginCmppMessage = new CmppMessage();
			loginCmppMessage.setCmd(0x00000001);
			loginCmppMessage.setSeq(NettyClient.SEQ++);
			loginCmppMessage.setBody(cmpp.cmppLoginBodyBytes(cmppLogin));
			ctx.writeAndFlush(loginCmppMessage);
		} catch (OutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void channelUnregistered(final ChannelHandlerContext ctx) throws Exception {
		LOG.info("Sleeping for: " + NettyClient.RECONNECT_DELAY + 's');

		final EventLoop loop = ctx.channel().eventLoop();
		loop.schedule(new Runnable() {
			@Override
			public void run() {
				LOG.info("Reconnecting...");
				client.configureBootstrap(new Bootstrap(), loop).connect();
			}
		}, NettyClient.RECONNECT_DELAY, TimeUnit.SECONDS);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (cause instanceof ConnectException) {
			startTime = -1;
			println("Failed to connect: " + cause.getMessage());
		}
		cause.printStackTrace();
		ctx.close();
	}

	void println(String msg) {
		if (startTime < 0) {
			LOG.error(String.format("[SERVER IS DOWN] %s%n", msg));
			System.err.format("[SERVER IS DOWN] %s%n", msg);
		} else {
			LOG.error(String.format("[UPTIME: %5ds] %s%n", (System.currentTimeMillis() - startTime) / 1000, msg));
			System.err.format("[UPTIME: %5ds] %s%n", (System.currentTimeMillis() - startTime) / 1000, msg);
		}
	}

}
