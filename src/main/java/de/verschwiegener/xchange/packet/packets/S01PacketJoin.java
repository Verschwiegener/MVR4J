package de.verschwiegener.xchange.packet.packets;

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

	private boolean ok = true;
	private String message = "";
	
	public S01PacketJoin() {
		super("MVR_JOIN_RET");
	}
	
	/**
	 * Creates MVR_JOIN_RET Package with given ok value (false equals error) and message
	 * @param ok
	 * @param message
	 */
	public S01PacketJoin(boolean ok, String message) {
		this();
		this.ok = ok;
		this.message = message;
	}
	
	
	
	@Override
	public void parsePacket(JsonObject object, ChannelHandlerContext ctx) {
		boolean ok = object.get("OK").getAsBoolean();
		String message = object.get("Message").getAsString();
		//TODO Call Error API
		
		//Add new Station that the Client knows who the sender is
		Station station = new Station(object);
		
		JsonArray files = object.get("Files").getAsJsonArray();
		files.forEach(element -> {
			// TODO parse MVR_COMMIT
		});

		XChange.instance.addStation(station);
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
		object.add("Commits", object);
		
		
		
		return null;
	}

}
