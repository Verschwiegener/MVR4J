package de.verschwiegener.xchange.packet.packets;

import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.stream.StreamSupport;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import de.verschwiegener.xchange.XChange;
import de.verschwiegener.xchange.packet.UTF8Packet;
import de.verschwiegener.xchange.util.Connection;
import de.verschwiegener.xchange.util.MVRFile;
import de.verschwiegener.xchange.util.PacketType;
import de.verschwiegener.xchange.util.Station;
import de.verschwiegener.xchange.util.Util;
import de.verschwiegener.xchange.util.Version;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class S01PacketJoin extends UTF8Packet {

	private boolean ok;
	private String message;

	public S01PacketJoin(boolean ok, String message) {
		super(PacketType.MVR_JOIN_RET);
		this.ok = ok;
		this.message = message;
	}

	/**
	 * Creates MVR_JOIN_RET Package with given ok value (false equals error) and
	 * message
	 * 
	 * @param ok
	 * @param message
	 */
	public S01PacketJoin() {
		this(true, "");
	}

	@Override
	public void parsePacket(JsonObject object, ChannelHandlerContext ctx) {
		// Check if Packet contains error
		if (!parseError(object))
			return;

		Station station = Util.checkStationJoin(object, getPacketType(), ctx);

		if (station == null)
			return;

		if (!XChange.instance.station.getVersion().checkVersion(station.getVersion())) {
			station.getConnection()
					.sendPacket(new S01PacketJoin(false, "Version is not Compatible With Server, Server Version: "
							+ XChange.instance.station.getVersion().toString()));

			// Send Error
			XChange.instance.listener.xChangeError(packetType.toString(),
					"Station " + object.get("StationUUID").getAsString() + " Version: " + station.getVersion()
							+ " is not Compatible With Server Version");
			return;
		}

		station.updateValues(object);

		JsonArray files = object.get("Commits").getAsJsonArray();
		files.forEach(jsarray -> {
			JsonObject jsobject = (JsonObject) jsarray;

			Station sourceStation = Util.checkStation(jsobject.get("StationUUID").getAsString(), packetType);

			if (sourceStation == null)
				return;

			// Handles File Parsing
			handleFile(jsobject, sourceStation);
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
