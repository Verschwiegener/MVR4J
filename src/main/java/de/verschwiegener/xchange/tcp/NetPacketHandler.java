package de.verschwiegener.xchange.tcp;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import com.google.gson.JsonObject;

import de.verschwiegener.xchange.PacketRegistry;
import de.verschwiegener.xchange.packet.UTF8Packet;
import de.verschwiegener.xchange.packet.packets.MVRFilePacket;
import de.verschwiegener.xchange.util.Util;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Netty PacketHandler decoding and parsing incoming Packets from all Peers
 * 
 * @author julius
 */
public class NetPacketHandler extends SimpleChannelInboundHandler<ByteBuf> {
	
	private ChannelHandlerContext ctx;
	
	ByteBuf[] multiPacketBuffer = new ByteBuf[1];

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		this.ctx = ctx;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf packet) throws Exception {
		if (packet.readInt() != Util.MVR_PACKAGE_HEADER)
			return;
		int packageVersion = packet.readInt();
		if (packageVersion != Util.MVR_PACKAGE_VERSION)
			return;
		
		// Number that defines what number this package in the complete message has. unsigned Integer
		int packageNumber = packet.readInt() & 0xff;
		// Number that defines how many packages the current message consists of. Unsigned Integer
		int packageCount = (packet.readInt() & 0xff) - 1;
		
		if(packageCount != multiPacketBuffer.length) {
			multiPacketBuffer = new ByteBuf[packageCount];
		}
		
		int packetType = packet.readInt();
		long payloadLength = packet.readLong();
		
		multiPacketBuffer[packageNumber] = packet;
		
		
		if (packageCount == packageNumber) {
			//TODO Test MultiPacket Code
			ByteBuf data = Unpooled.buffer(0);
			for(int i = 0; i < multiPacketBuffer.length;i++) {
				data.writeBytes(multiPacketBuffer[i]);
				multiPacketBuffer[i].clear();
			}
			if(packetType == 1)
				new MVRFilePacket().parsePacket(data);
			else {
				JsonObject mainObject = Util.byteBufToJson(data);
				if(mainObject == null)
					return;
				try {
					UTF8Packet p = PacketRegistry.JSON.getPacket(mainObject.get("Type").getAsString());
					p.parsePacket(mainObject, ctx);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			packet.clear();
		}
	}
	
	public ChannelHandlerContext getCtx() {
		return ctx;
	}
}