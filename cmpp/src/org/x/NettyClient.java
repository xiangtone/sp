package org.x;

import org.apache.log4j.Logger;
import org.x.netty.support.CmppClientHandler;
import org.x.netty.support.CmppDecoder;
import org.x.netty.support.CmppEncoder;

import com.xiangtone.util.ConfigManager;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class NettyClient {

	private static final Logger LOG = Logger.getLogger(NettyClient.class.getName());
	private final String host;
	private final int port;
	public static int SEQ = 1;
	// Sleep 5 seconds before a reconnection attempt.
	public static final int RECONNECT_DELAY = 5;

	// Reconnect when the server sends nothing for 10 seconds.
	private static final int READ_TIMEOUT = 100;
	private static final int WRITE_TIMEOUT = 7;

	private final CmppClientHandler handler = new CmppClientHandler(this);

	public NettyClient(final String host, final int port) {
		this.host = host;
		this.port = port;
	}

	public void run() {
		configureBootstrap(new Bootstrap()).connect();
	}

	private Bootstrap configureBootstrap(Bootstrap b) {
		return configureBootstrap(b, new NioEventLoopGroup());
	}

	public Bootstrap configureBootstrap(Bootstrap b, EventLoopGroup g) {
		b.group(g).channel(NioSocketChannel.class).remoteAddress(host, port).option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new IdleStateHandler(READ_TIMEOUT, WRITE_TIMEOUT, 0));
						ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
						// ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(10240,
						// 0, 4, 0, 4));
						ch.pipeline().addLast(new CmppEncoder());
						ch.pipeline().addLast(new CmppDecoder());
						// ch.pipeline().addLast(new HeartBeatHandler());
						ch.pipeline().addLast(handler);
					}
				});

		return b;
	}

	// public void run() throws Exception {
	// // Configure the client.
	// EventLoopGroup group = new NioEventLoopGroup();
	// try {
	// Bootstrap b = new Bootstrap();
	//
	// // Start the client.
	// ChannelFuture f = b.connect(host, port).sync();
	//
	// // Wait until the connection is closed.
	// f.channel().closeFuture().sync();
	// } finally {
	// // Shut down the event loop to terminate all threads.
	// group.shutdownGracefully();
	// }
	// }

	public static void main(String[] args) throws Exception {
		LOG.info("init");

		SMSIsmgInfo info = new SMSIsmgInfo("config.ini");
		info.loadParam();
		info.printParam();

		String host = ConfigManager.getConfigData("gd_ismg_ip");

		int port = Integer.parseInt(ConfigManager.getConfigData("gd_ismg_port"));

		LOG.info(host + ":" + port);

		new NettyClient(host, port).run();

		LOG.info("done");
	}

}
