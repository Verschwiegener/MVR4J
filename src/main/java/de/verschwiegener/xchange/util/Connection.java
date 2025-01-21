package de.verschwiegener.xchange.util;

import java.net.InetSocketAddress;
import java.security.cert.CertificateException;
import java.util.concurrent.CompletableFuture;

import javax.net.ssl.SSLException;

import de.verschwiegener.xchange.ProtocolMode;
import de.verschwiegener.xchange.XChange;
import de.verschwiegener.xchange.packet.Packet;
import de.verschwiegener.xchange.packet.packets.C01PacketJoin;
import de.verschwiegener.xchange.packet.packets.S02PacketLeave;
import de.verschwiegener.xchange.packet.packets.S04PacketRequest;
import de.verschwiegener.xchange.tcp.NetPacketHandler;
import de.verschwiegener.xchange.tcp.TCPServer;
import de.verschwiegener.xchange.websocket.WebSocketPacketHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * Handles the Connection to a Station
 * 
 * @author julius
 */
public class Connection {

	private final InetSocketAddress remoteAddress;
	
	/**
	 * Netty Connection Channel
	 */
	private Channel channel;
	private boolean connected = false;

	private SimpleChannelInboundHandler<?> handler;

	public Connection(InetSocketAddress address) {
		this.remoteAddress = address;
	}

	/**
	 * Connects to Counterpart, used with TCP Mode or as Websocket Client
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public CompletableFuture<Void> connectTo() throws InterruptedException {
		CompletableFuture<Void> connectionFuture = new CompletableFuture<Void>();

		if (XChange.instance.mode == ProtocolMode.mDNS) {
			handler = new NetPacketHandler();
		} else {
			handler = new WebSocketPacketHandler();
		}

		final Bootstrap clientBootstrap = new Bootstrap();
		clientBootstrap.group(TCPServer.networkEventLoopGroup).channel(NioSocketChannel.class)
				.option(ChannelOption.SO_KEEPALIVE, true).handler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						final ChannelPipeline pipeline = ch.pipeline();
						if (XChange.instance.mode == ProtocolMode.WEBSOCKET_CLIENT) {
							// SslHandler handler = sslContext.newHandler(ch.alloc());
							// handler.engine().setEnabledProtocols(new String[] {"TLSv1.2"});
							// pipeline.addLast(handler);

							pipeline.addLast(new HttpServerCodec());
							pipeline.addLast(new HttpObjectAggregator(65536));
							// pipeline.addLast(new WebSocketServerCompressionHandler());
							pipeline.addLast(new ChunkedWriteHandler());
							pipeline.addLast(new WebSocketServerProtocolHandler("/websocket"));
						}
						pipeline.addLast(TCPServer.peerEventLoopGroup, handler);

					}
				});

		ChannelFuture connectFuture = clientBootstrap.connect(remoteAddress);

		channel = connectFuture.channel();

		connectFuture.addListener(new ChannelFutureListener() {
			public void operationComplete(ChannelFuture future) throws Exception {
				if (future.isSuccess()) {
					// Connection is established
					connected = true;
					connectionFuture.complete(null);
					sendPacket(new C01PacketJoin());
				} else {
					channel.close();
					connectionFuture.completeExceptionally(future.cause());
					connected = false;
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
		if (!connected) {
			futureToNotify.completeExceptionally(new Throwable());
			return futureToNotify;
		}

		ChannelFuture future;
		if (XChange.instance.mode == ProtocolMode.mDNS) {
			future = channel.writeAndFlush(Util.packetBuilder(packet.writePacket(), packet.getPackageType()));
		} else {
			if(packet instanceof S04PacketRequest requestPacket) {
				if(requestPacket.needsBinaryFrame()) {
					future = channel.writeAndFlush(new BinaryWebSocketFrame(packet.writePacket()));
				}else {
					future = channel.writeAndFlush(new TextWebSocketFrame(packet.writePacket()));
				}
			}else {
				future = channel.writeAndFlush(new TextWebSocketFrame(packet.writePacket()));
			}
		}

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
		sendPacket(new S02PacketLeave());
		if (channel == null)
			return;
		channel.close();
	}
	
	/**
	 * Set Connection Channel
	 * Used for WebSocket Server Mode where we dont need to create a new connection to the peer
	 * 
	 * @param channel
	 */
	public void setChannel(Channel channel) {
		this.channel = channel;
		// Set Connected so we dont create TCP Connection when we need Websocket
		connected = true;
	}

	public InetSocketAddress getRemoteAddress() {
		return remoteAddress;
	}

	public boolean isConnected() {
		return connected;
	}

}
