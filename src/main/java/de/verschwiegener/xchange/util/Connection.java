package de.verschwiegener.xchange.util;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

import de.verschwiegener.xchange.packet.Packet;
import de.verschwiegener.xchange.packet.packets.C01PacketJoin;
import de.verschwiegener.xchange.tcp.NetPacketHandler;
import de.verschwiegener.xchange.tcp.TCPServer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * Netty Client connecting to Peers Server
 * 
 * @author julius
 */
public class Connection {

	private final InetSocketAddress remoteAddress;

	private Channel channel;

	public Connection(InetSocketAddress address) {
		this.remoteAddress = address;
	}

	public CompletableFuture<Void> connectTo() {
		CompletableFuture<Void> connectionFuture = new CompletableFuture<Void>();
		
		final Bootstrap clientBootstrap = new Bootstrap();
		clientBootstrap.group(TCPServer.networkEventLoopGroup).channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						final ChannelPipeline pipeline = ch.pipeline();
						pipeline.addLast(TCPServer.peerEventLoopGroup, new NetPacketHandler());

					}
				});
		final ChannelFuture connectFuture = clientBootstrap.connect(remoteAddress);

		connectFuture.addListener(new ChannelFutureListener() {
			public void operationComplete(ChannelFuture future) throws Exception {
				if (future.isSuccess()) {
					//Connection is established
					channel = future.channel();
					connectionFuture.complete(null);
					sendPacket(new C01PacketJoin());
				} else {
					connectionFuture.completeExceptionally(future.cause());
				}
			}
		});
		return connectionFuture;
	}

	/**
	 * Sends Packet to Station
	 * 
	 * @param packet
	 */
	public CompletableFuture<Void> sendPacket(Packet packet) {
		CompletableFuture<Void> futureToNotify = new CompletableFuture<Void>();
		ChannelFuture future = channel.writeAndFlush(Util.packetBuilder(packet.writePacket(), packet.getPackageType()));
		future.addListener(new GenericFutureListener<Future<? super Void>>() {

			@Override
			public void operationComplete(Future<? super Void> future) throws Exception {
				if (future.isSuccess()) {
					futureToNotify.complete(null);
				} else {
					futureToNotify.completeExceptionally(future.cause());
				}
			}
		});
		return futureToNotify;
	}

	/**
	 * Shuts down Connection to Peer
	 */
	public void shutdown() {
		channel.close();
	}

}
