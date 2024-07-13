package de.verschwiegener.xchange.packet.packets;

import java.util.UUID;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import de.verschwiegener.xchange.XChange;
import de.verschwiegener.xchange.packet.UTF8Packet;
import de.verschwiegener.xchange.util.MVRFile;
import de.verschwiegener.xchange.util.Station;
import de.verschwiegener.xchange.util.Util;
import de.verschwiegener.xchange.util.Version;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class S01PacketJoin extends UTF8Packet{

	private boolean ok;
	private String message;
	
	public S01PacketJoin(boolean ok, String message) {
		super("MVR_JOIN_RET");
		this.ok = ok;
		this.message = message;
	}
	
	/**
	 * Creates MVR_JOIN_RET Package with given ok value (false equals error) and message
	 * @param ok
	 * @param message
	 */
	public S01PacketJoin() {
		this(true, "");
	}
	
	
	
	@Override
	public void parsePacket(JsonObject object, ChannelHandlerContext ctx) {
		//Check if Packet contains error
		if(!parseError(object))
			return;
		
		//Get Station
		Station station = XChange.instance.getStationByUUID(UUID.fromString(object.get("StationUUID").getAsString()));
		
		if(station == null) {
			XChange.instance.listener.xChangeError(packetType, packetType + " Station " + object.get("StationUUID").getAsString() + " not known");
			return;
		}
		
		JsonArray files = object.get("Commits").getAsJsonArray();
		files.forEach(element -> {
			// TODO parse MVR_COMMIT
		});
		
	}

	@Override
	public ByteBuf writePacket() {
		return Util.jsonToByteBuf(writeJson());
	}

	@Override
	public JsonObject writeJson() {
		JsonObject object = responseMessage(ok, message);
		XChange.instance.station.writeToJson(object);

		// Create Commits Array with all local Files
		JsonArray array = new JsonArray();
		for (MVRFile file : XChange.instance.getFiles()) {
			array.add(new C03PacketCommit(file, null, new Version(0, 0)).writeJson());
		}
		object.add("Commits", array);
		
		
		return object;
	}

}
