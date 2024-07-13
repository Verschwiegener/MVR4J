package de.verschwiegener.xchange.packet.packets;

import com.google.gson.JsonObject;

import de.verschwiegener.xchange.XChange;
import de.verschwiegener.xchange.packet.UTF8Packet;
import de.verschwiegener.xchange.util.Util;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class S02PacketLeave extends UTF8Packet{

	
	private boolean ok;
	private String message;
	
	public S02PacketLeave(boolean ok, String message) {
		super("MVR_LEAVE_RET");
	}
	
	public S02PacketLeave() {
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
