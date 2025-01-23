package de.verschwiegener.xchange.websocket;

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
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

public class WebSocketPacketHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

	private ChannelHandlerContext ctx;
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		this.ctx = ctx;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
		// Handle MVRPackets
		if (frame instanceof TextWebSocketFrame) {
			ByteBuf packet = frame.content();
			JsonObject mainObject = Util.byteBufToJson(packet);
			
			if (mainObject == null)
				return;
			try {
				UTF8Packet p = PacketRegistry.JSON.getPacket(mainObject.get("Type").getAsString());
				p.parsePacket(mainObject, ctx);
			} catch (Exception e) {
				e.printStackTrace();
			}
			packet.clear();
		} else if(frame instanceof BinaryWebSocketFrame) {
			new MVRFilePacket().parsePacket(frame.content());
			
			//Checks if File is complete
			MVRFile file = XChange.instance.currentReceiveFile;
			if(file.getFileSize() == Files.size(Paths.get(file.getFilesystemLocation().getAbsolutePath()))) {
				XChange.instance.currentReceiveFile.setLocal();
				XChange.instance.listener.newMVRFile(XChange.instance.getFileByUUID(XChange.instance.currentReceiveFile.getUuid()));
				XChange.instance.currentReceiveFile = null;
			}
		} else if (frame instanceof CloseWebSocketFrame) {
			ctx.channel().close();
		} else if (frame instanceof PingWebSocketFrame) {
			ctx.channel().writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
		}
	}

}
