package de.verschwiegener.xchange.websocket;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.JsonObject;

import de.verschwiegener.xchange.PacketRegistry;
import de.verschwiegener.xchange.XChange;
import de.verschwiegener.xchange.packet.UTF8Packet;
import de.verschwiegener.xchange.packet.packets.MVRFilePacket;
import de.verschwiegener.xchange.util.MVRFile;
import de.verschwiegener.xchange.util.Util;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketHandshakeException;

public class WebSocketPacketHandler extends SimpleChannelInboundHandler<Object> {

	private final WebSocketClientHandshaker handshaker;
	private ChannelPromise handshakeFuture;

	public WebSocketPacketHandler(WebSocketClientHandshaker handshaker) {
		this.handshaker = handshaker;
	}

	public WebSocketPacketHandler() {
		handshaker = null;
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) {
		if(handshaker != null)
			handshakeFuture = ctx.newPromise();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		if(handshaker != null)
			handshaker.handshake(ctx.channel());
	}

	public ChannelFuture handshakeFuture() {
		return handshakeFuture;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		if(handshaker != null) {
			if (!handshakeFuture.isDone()) {
				handshakeFuture.setFailure(cause);
			}
		}
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("REad: " + msg);
		if(handshaker != null) {
			Channel ch = ctx.channel();
			if (!handshaker.isHandshakeComplete()) {
				try {
					handshaker.finishHandshake(ch, (FullHttpResponse) msg);
					System.out.println("WebSocket Client connected!");
					handshakeFuture.setSuccess();
				} catch (WebSocketHandshakeException e) {
					System.out.println("WebSocket Client failed to connect");
					handshakeFuture.setFailure(e);
				}
				return;
			}
		}

		WebSocketFrame frame = (WebSocketFrame) msg;
		// Handle MVRPackets
		if (frame instanceof TextWebSocketFrame) {
			ByteBuf packet = frame.content();
			JsonObject mainObject = Util.byteBufToJson(packet);

			System.out.println("JSon: " + packet.toString(StandardCharsets.UTF_8));

			if (mainObject == null)
				return;
			try {
				UTF8Packet p = PacketRegistry.JSON.getPacket(mainObject.get("Type").getAsString());
				p.parsePacket(mainObject, ctx);
			} catch (Exception e) {
				e.printStackTrace();
			}
			packet.clear();
		} else if (frame instanceof BinaryWebSocketFrame) {
			new MVRFilePacket().parsePacket(frame.content());

			// Checks if File is complete
			MVRFile file = XChange.instance.currentReceiveFile;
			if (file.getFileSize() == Files.size(Paths.get(file.getFilesystemLocation().getAbsolutePath()))) {
				XChange.instance.currentReceiveFile.setLocal();
				XChange.instance.listener.newMVRFileReceived(
						XChange.instance.getFileByUUID(XChange.instance.currentReceiveFile.getUuid()));
				XChange.instance.currentReceiveFile = null;
			}
		} else if (frame instanceof CloseWebSocketFrame) {
			System.out.println("Close WebSocket Frame: " + ((CloseWebSocketFrame)frame).reasonText());
			ctx.channel().close();
		} else if (frame instanceof PingWebSocketFrame) {
			ctx.channel().writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
		}
	}

}
