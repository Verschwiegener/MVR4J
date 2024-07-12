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

	private ByteBuf buffer = Unpooled.buffer();

	HashMap<Integer, ByteBuf> multipacketBuffer = new HashMap<Integer, ByteBuf>();
	
	private ChannelHandlerContext ctx;

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
		
		// Multi Packet Number of packet, unsigned Integer
		int packageNumber = packet.readInt() & 0xff;
		// Multi Packet Count of Packets for one Message, Unsigned Integer
		int packageCount = (packet.readInt() & 0xff) - 1;
		
		int packetType = packet.readInt();
		long payloadLength = packet.readLong();
		
		buffer.writeBytes(packet, (int) payloadLength);
		
		if (packageCount == packageNumber) {
			if(packetType == 1)
				new MVRFilePacket().parsePacket(buffer);
			else {
				
				System.out.println("JSon: " + buffer.toString(StandardCharsets.UTF_8));
				
				JsonObject mainObject = Util.byteBufToJson(buffer);
				if(mainObject == null)
					return;
				try {
					UTF8Packet p = PacketRegistry.JSON.getPacket(mainObject.get("Type").getAsString());
					p.parsePacket(mainObject, ctx);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			buffer.clear();
		}
	}
	
	public ChannelHandlerContext getCtx() {
		return ctx;
	}
}