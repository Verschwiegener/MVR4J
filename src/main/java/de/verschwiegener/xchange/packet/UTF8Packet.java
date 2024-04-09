package de.verschwiegener.xchange.packet;

import com.google.gson.JsonObject;

import io.netty.channel.ChannelHandlerContext;

public abstract class UTF8Packet implements Packet {

	protected final String packetType;

	public UTF8Packet(String packetType) {
		this.packetType = packetType;
	}
	@Override
	public int getPackageType() {
		return 0;
	}

	public abstract void parsePacket(JsonObject object, ChannelHandlerContext ctx);

	public abstract JsonObject writeJson();

	protected JsonObject responceMessage(boolean error, String message) {
		JsonObject object = new JsonObject();
		object.addProperty("OK", error);
		object.addProperty("Message", message);
		return object;
	}
	protected JsonObject message() {
		JsonObject object = new JsonObject();
		object.addProperty("Type", packetType);
		return object;
	}
	public String getPacketType() {
		return packetType;
	}

}
