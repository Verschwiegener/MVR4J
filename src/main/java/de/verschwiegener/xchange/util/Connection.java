package de.verschwiegener.xchange.util;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

import de.verschwiegener.xchange.XChange;
import de.verschwiegener.xchange.packet.Packet;
import de.verschwiegener.xchange.packet.packets.S04PacketRequest;
import de.verschwiegener.xchange.tcp.NetPacketHandler;
import de.verschwiegener.xchange.tcp.TCPServer;
import de.verschwiegener.xchange.websocket.WebSocketPacketHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * Handles the Connection to a Station
 * 
 * @author julius
 */
public class Connection {

	public static final EventLoopGroup networkEventLoopGroup = new NioEventLoopGroup();

	public static final EventLoopGroup peerEventLoopGroup = new NioEventLoopGroup();
	
	

	private final InetSocketAddress remoteAddress;

	/**
	 * Netty Connection Channel
	 */
	private Channel channel;
	private boolean connected = false;

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
		if (connected) {
			connectionFuture.complete(null);
			return connectionFuture;
		}

		SimpleChannelInboundHandler<?> handler = XChange.instance.isMDNS() ? new NetPacketHandler()
				: new WebSocketPacketHandler(WebSocketClientHandshakerFactory.newHandshaker(
						XChange.instance.websocketURI, WebSocketVersion.V13, null, true, new DefaultHttpHeaders()));	

		
		final Bootstrap clientBootstrap = new Bootstrap();
		clientBootstrap.group(networkEventLoopGroup).channel(NioSocketChannel.class)
				.option(ChannelOption.SO_KEEPALIVE, true).handler(new LoggingHandler(LogLevel.INFO))
				.handler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						final ChannelPipeline pipeline = ch.pipeline();
						if (XChange.instance.isWebSocketClient()) {
							if (XChange.instance.sslCtx != null) {
								pipeline.addLast(XChange.instance.sslCtx.newHandler(ch.alloc(),
										remoteAddress.getHostString(), remoteAddress.getPort()));
							}

							pipeline.addLast(new HttpClientCodec());
							pipeline.addLast(new ChunkedWriteHandler());
							pipeline.addLast(new HttpObjectAggregator(65536));
							pipeline.addLast(WebSocketClientCompressionHandler.INSTANCE);
						}
						pipeline.addLast(peerEventLoopGroup, handler);
					}
				});

		ChannelFuture connectFuture = clientBootstrap.connect(remoteAddress).sync();
		
		if (XChange.instance.isWebSocketClient()) {
			((WebSocketPacketHandler) handler).handshakeFuture().sync();
		}

		channel = connectFuture.channel();

		connectFuture.addListener(new ChannelFutureListener() {
			public void operationComplete(ChannelFuture future) throws Exception {
				if (future.isSuccess()) {
					connected = true;
					connectionFuture.complete(null);
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
		//System.out.println("SendPacket: " + packet.writePacket().toString(StandardCharsets.UTF_8));
		CompletableFuture<Void> futureToNotify = new CompletableFuture<Void>();
		if (!connected) {
			try {
				connectTo();
			}catch(InterruptedException ie) {
				futureToNotify.completeExceptionally(ie);
				return futureToNotify;
			}
		}

		
		Object data = null;
		
		if (XChange.instance.isMDNS()) {
			data = Util.packetBuilder(packet.writePacket(), packet.getPackageType());
		} else {
			if (packet instanceof S04PacketRequest request) {
				data = request.needsBinaryFrame() ? new BinaryWebSocketFrame(packet.writePacket())
						: new TextWebSocketFrame(packet.writePacket());
			} else {
				data = new TextWebSocketFrame(packet.writePacket());
			}
		}
		
		channel.writeAndFlush(data).addListener(new GenericFutureListener<Future<? super Void>>() {

			@Override
			public void operationComplete(Future<? super Void> future) throws Exception {
				if (future.isSuccess()) {
					futureToNotify.complete(null);
				} else {
					future.cause().printStackTrace();
					futureToNotify.completeExceptionally(future.cause());
				}
			}
		});
		return futureToNotify;
	}

	/**
	 * Shuts down Connection to Peer Does not send MVR_LEAVE Packet!
	 */
	public void shutdown() {
		if (channel == null)
			return;
		channel.close();
	}

	/**
	 * Set Connection Channel Used for WebSocket Server Mode where we dont need to
	 * create a new connection to the peer
	 * 
	 * @param channel
	 */
	public void setChannel(Channel channel) {
		shutdown();
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
	
	public static void shutdownNetty() {
		try {
			networkEventLoopGroup.shutdownGracefully().sync();
			peerEventLoopGroup.shutdownGracefully().sync();
		}catch(InterruptedException ie) {	
		}
	}

}
