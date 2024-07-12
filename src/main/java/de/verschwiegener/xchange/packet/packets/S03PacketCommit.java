package de.verschwiegener.xchange.packet.packets;

import com.google.gson.JsonObject;

import de.verschwiegener.xchange.packet.UTF8Packet;
import de.verschwiegener.xchange.util.Util;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class S03PacketCommit extends UTF8Packet {

	private boolean ok = true;
	private String message = "";

	public S03PacketCommit() {
		super("MVR_COMMIT_RET");
	}

	public S03PacketCommit(boolean ok, String message) {
		this();
	}

	@Override
	public void parsePacket(JsonObject object, ChannelHandlerContext ctx) {
		if(!parseError(object))
			return;
		
		//TODO parse Packet
	}

	@Override
	public ByteBuf writePacket() {
		return Util.jsonToByteBuf(writeJson());
	}

	@Override
	public JsonObject writeJson() {
		return responseMessage(ok, message);
	}

}
