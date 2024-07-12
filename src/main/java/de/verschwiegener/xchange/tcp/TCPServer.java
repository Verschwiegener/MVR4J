package de.verschwiegener.xchange.tcp;

import de.verschwiegener.xchange.XChange;
import de.verschwiegener.xchange.XChangeServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * TCP Server receiving all Packets
 * 
 * @author julius
 */
public class TCPServer implements XChangeServer{

	private final EventLoopGroup acceptorEventLoopGroup = new NioEventLoopGroup(1);

	public static final EventLoopGroup networkEventLoopGroup = new NioEventLoopGroup(6);

	public static final EventLoopGroup peerEventLoopGroup = new NioEventLoopGroup(1);
	
	private ChannelFuture bindFuture;

	public TCPServer() {
	}
	
	/**
	 * Starts Netty TCP Server
	 * 
	 * @throws InterruptedException
	 */
	public void start() throws InterruptedException {
		final ServerBootstrap peerBootstrap = new ServerBootstrap();
		peerBootstrap.group(acceptorEventLoopGroup, networkEventLoopGroup).channel(NioServerSocketChannel.class)
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
				.option(ChannelOption.SO_BACKLOG, 100).handler(new LoggingHandler(LogLevel.INFO))
				.childHandler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						final ChannelPipeline pipeline = ch.pipeline();
						pipeline.addLast(peerEventLoopGroup, new NetPacketHandler());
					}
				});

		bindFuture = peerBootstrap.bind(XChange.instance.serverPort).sync();
	}
	
	/**
	 * Shutdown Server and all EventLoopGroups
	 */
	public void shutdown() {
		bindFuture.channel().close();
		networkEventLoopGroup.shutdownGracefully();
		acceptorEventLoopGroup.shutdownGracefully();
		peerEventLoopGroup.shutdownGracefully();
	}
	
	

}
