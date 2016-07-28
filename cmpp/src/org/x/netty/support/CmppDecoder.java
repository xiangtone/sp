package org.x.netty.support;

import java.util.List;

import org.apache.log4j.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class CmppDecoder extends ByteToMessageDecoder {

	private final static Logger LOG = Logger.getLogger(CmppDecoder.class);

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in.readableBytes() >= 4) {
			int length = in.readInt();
			in.markReaderIndex();
			if (in.readableBytes() < length - 4) {
				in.resetReaderIndex();
			} else {
				CmppMessage cmppMessaage = new CmppMessage();
				cmppMessaage.setCmd(in.readInt());
				cmppMessaage.setSeq(in.readInt());
				byte[] content = new byte[length - 12];
				in.readBytes(content, 0, length - 12);
				cmppMessaage.setBody(content);
				out.add(cmppMessaage);
			}
		}
	}

}
