package de.verschwiegener.xchange.packet.packets;

import com.google.gson.JsonObject;

import de.verschwiegener.xchange.packet.UTF8Packet;
import de.verschwiegener.xchange.util.Util;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class S04PacketRequest extends UTF8Packet {

	private boolean ok;
	private String message;

	public S04PacketRequest(boolean ok, String message) {
		super("MVR_REQUEST_RET");
		this.ok = ok;
		this.message = message;
	}

	public S04PacketRequest() {
		this(true, "");
	}

	@Override
	public void parsePacket(JsonObject object, ChannelHandlerContext ctx) {
		if(!parseError(object))
			return;
		
		//Needs no logic, Packet just Contains Error Codes

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
