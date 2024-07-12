package de.verschwiegener.xchange.packet;

import com.google.gson.JsonObject;

import de.verschwiegener.xchange.XChange;
import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * @author julius
 *
 */
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

	/**
	 * Creates Response Message as JsonObject
	 * @param error
	 * @param message
	 * @return
	 */
	protected JsonObject responseMessage(boolean error, String message) {
		JsonObject object = new JsonObject();
		object.addProperty("Type", packetType);
		object.addProperty("OK", error);
		object.addProperty("Message", message);
		return object;
	}
	/**
	 * Creates Message with PacketType as JsonObject
	 * @return
	 */
	protected JsonObject message() {
		JsonObject object = new JsonObject();
		object.addProperty("Type", packetType);
		return object;
	}
	
	/**
	 * Parses OK and Message Json Attributes and calls xChangeError listener. Returns ok state
	 * @param object JsonObject to parse Error state from
	 * @return OK JsonObject as boolean
	 */
	protected boolean parseError(JsonObject object) {
		boolean ok = object.get("OK").getAsBoolean();
		String message = object.get("Message").getAsString();
		
		if(!ok) {
			XChange.instance.listener.xChangeError(packetType, message);
		}
		
		return ok;
	}
	
	public String getPacketType() {
		return packetType;
	}

}
