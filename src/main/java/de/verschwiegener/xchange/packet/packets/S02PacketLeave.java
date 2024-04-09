package de.verschwiegener.xchange.packet.packets;

import com.google.gson.JsonObject;

import de.verschwiegener.xchange.packet.UTF8Packet;
import de.verschwiegener.xchange.util.Util;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class S02PacketLeave extends UTF8Packet{

	
	private boolean ok = true;
	private String message = "";
	
	public S02PacketLeave() {
		super("MVR_LEAVE_RET");
	}
	
	public S02PacketLeave(boolean ok, String message) {
		this();
	}
	
	@Override
	public void parsePacket(JsonObject object, ChannelHandlerContext ctx) {
		boolean ok = object.get("OK").getAsBoolean();
		String message = object.get("Message").getAsString();
		//TODO Call Error API
	}

	@Override
	public ByteBuf writePacket() {
		return Util.jsonToByteBuf(writeJson());
	}

	@Override
	public JsonObject writeJson() {
		return responceMessage(ok, message);
	}

}
