package org.x.netty.support;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class CmppEncoder extends MessageToByteEncoder<CmppMessage> {

	@Override
	protected void encode(ChannelHandlerContext ctx, CmppMessage msg, ByteBuf out) throws Exception {
		out.writeInt(msg.getBody().length + 12);
		out.writeInt(msg.getCmd());
		out.writeInt(msg.getSeq());
		out.writeBytes(msg.getBody());
	}

}
