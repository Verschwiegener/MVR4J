package de.verschwiegener.xchange.websocket;

import java.nio.charset.StandardCharsets;

import com.google.gson.JsonObject;

import de.verschwiegener.xchange.PacketRegistry;
import de.verschwiegener.xchange.packet.UTF8Packet;
import de.verschwiegener.xchange.packet.packets.MVRFilePacket;
import de.verschwiegener.xchange.util.Util;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

public class WebSocketPacketHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

	// MultiPacketBuffer for Packets that are bigger than one tcp packeta
	private ByteBuf[] multiPacketBuffer;
	private ChannelHandlerContext ctx;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		this.ctx = ctx;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
		// Handle MVRPackets
		if (frame instanceof TextWebSocketFrame) {
			System.out.println("Packet: " + frame);
			ByteBuf packet = frame.content();
			/*ByteBuf packet = frame.content();
			int packetHeader = packet.readInt();
			System.out.println("PacketHeader: " + packetHeader);
			if (packetHeader != Util.MVR_PACKAGE_HEADER)
				return;
			System.out.println("Header");
			int packageVersion = packet.readInt();
			if (packageVersion != Util.MVR_PACKAGE_VERSION)
				return;
			System.out.println("Version");

			// Multi Packet Number of packet, unsigned Integer
			int packageNumber = packet.readInt() & 0xff;
			// Multi Packet Count of Packets for one Message, Unsigned Integer
			int packageCount = (packet.readInt() & 0xff) - 1;

			int packetType = packet.readInt();
			long payloadLength = packet.readLong();*/

			// Create new Buffer so Index is 0 for Packet Parsing
			
			
			System.out.println("JSon: " + packet.toString(StandardCharsets.UTF_8));

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
		} else if (frame instanceof CloseWebSocketFrame) {
			//ctx.channel().close();
		} else if (frame instanceof PingWebSocketFrame) {
			ctx.channel().writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
		}
	}

}
