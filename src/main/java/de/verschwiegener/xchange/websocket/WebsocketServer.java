package de.verschwiegener.xchange.websocket;

import java.security.cert.CertificateException;

import javax.net.ssl.SSLException;

import de.verschwiegener.xchange.XChange;
import de.verschwiegener.xchange.XChangeServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebsocketServer implements XChangeServer {

	private final EventLoopGroup bossEventLoopGroup = new NioEventLoopGroup(1);

	public static final EventLoopGroup networkEventLoopGroup = new NioEventLoopGroup(6);

	public static final EventLoopGroup peerEventLoopGroup = new NioEventLoopGroup(1);

	private ChannelFuture bindFuture;

	private final boolean ssl;
	
	private final SslContext sslCtx;
	
	public WebsocketServer(boolean ssl, SslContext sslCtx) {
		this.ssl = ssl;
		this.sslCtx = sslCtx;
	}

	@Override
	public void start() throws InterruptedException, SSLException, CertificateException {
		final ServerBootstrap peerBootstrap = new ServerBootstrap();
		peerBootstrap.group(bossEventLoopGroup, networkEventLoopGroup).channel(NioServerSocketChannel.class)
				/*
				 * .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,
				 * 10000).option(ChannelOption.SO_BACKLOG, 100)
				 * .option(ChannelOption.SO_REUSEADDR, true)
				 */.handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						final ChannelPipeline pipeline = ch.pipeline();

						// SslHandler handler = sslContext.newHandler(ch.alloc());
						if (ssl && sslCtx != null) {
							//Force SSL Connection
							pipeline.addFirst(sslCtx.newHandler(ch.alloc()));
						}
						
						pipeline.addLast(new HttpServerCodec());
						pipeline.addLast(new ChunkedWriteHandler());
						pipeline.addLast(new HttpObjectAggregator(65536));
						pipeline.addLast(new WebSocketServerCompressionHandler());
						pipeline.addLast(new WebSocketServerProtocolHandler("/"));
						pipeline.addLast(peerEventLoopGroup, new WebSocketPacketHandler());
					}
				});

		bindFuture = peerBootstrap.bind(XChange.instance.serverPort).sync();
		bindFuture.channel().closeFuture().sync();
	}

	@Override
	public void shutdown() throws InterruptedException {
		bindFuture.channel().close().sync();
		networkEventLoopGroup.shutdownGracefully().sync();
		bossEventLoopGroup.shutdownGracefully().sync();
		peerEventLoopGroup.shutdownGracefully().sync();
	}

}
