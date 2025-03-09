package de.verschwiegener.xchange.tcp;

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

	// ByteBuf[] multiPacketBuffer = new ByteBuf[1];
	ByteBuf multiPacketBuffer = Unpooled.directBuffer();

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf packet) throws Exception {
		if (packet.readInt() != Util.MVR_PACKAGE_HEADER)
			return;
		int packageVersion = packet.readInt();
		// Quick Fix for PA because their version is 0
		if (packageVersion != 0) {
			if (packageVersion != Util.MVR_PACKAGE_VERSION)
				return;
		}

		// Number that defines what number this package in the complete message has.
		// unsigned Integer
		int packageNumber = packet.readInt() & 0xff;
		// Number that defines how many packages the current message consists of.
		// Unsigned Integer
		int packageCount = packet.readInt() & 0xff;

		// if(packageCount != multiPacketBuffer.length) {
		// multiPacketBuffer = new ByteBuf[packageCount];
		// }

		int packetType = packet.readInt();
		long payloadLength = packet.readLong();

		multiPacketBuffer.writeBytes(packet, (int) payloadLength);

		// PackageCount is 0 based
		if ((packageCount - 1) == packageNumber) {

			if (packetType == 1)
				new MVRFilePacket().parsePacket(multiPacketBuffer);
			else {
				// System.out.println("JSon: " +
				// multiPacketBuffer.toString(StandardCharsets.UTF_8));
				JsonObject mainObject = Util.byteBufToJson(multiPacketBuffer);
				if (mainObject == null)
					return;
				try {
					UTF8Packet p = PacketRegistry.JSON.getPacket(mainObject.get("Type").getAsString());
					System.out.println("Got Packet: " + p + ctx.channel().remoteAddress());
					p.parsePacket(mainObject, ctx);
				} catch (Exception e) {
					e.printStackTrace();

				}
			}
			System.out.println("Done");
			multiPacketBuffer.clear();
			ctx.channel().close();
		}
	}
}