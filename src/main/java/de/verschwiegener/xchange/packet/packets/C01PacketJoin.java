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

public class C01PacketJoin extends UTF8Packet {

	public C01PacketJoin() {
		super("MVR_JOIN");
	}

	@Override
	public void parsePacket(JsonObject object, ChannelHandlerContext ctx) {
		Station station = XChange.instance.getStationByUUID(UUID.fromString(object.get("StationUUID").getAsString()));
		if (!XChange.instance.station.getVersion().checkVersion(new Version(object))) {
			station.getConnection()
					.sendPacket(new S01PacketJoin(false, "Version is not Compatible With Server, Server Version: "
							+ XChange.instance.station.getVersion().toString()));
			return;
		}

		if (station == null)
			return;

		station.updateValues(object);

		JsonArray files = object.get("Files").getAsJsonArray();
		files.forEach(element -> {
			// TODO parse MVR_COMMIT
		});

		station.getConnection().sendPacket(new S01PacketJoin());

		// TODO Call Some Kind of API to signal to the user that the station is new(if
		// XChange.instance.getStaion(station) is null)
	}

	@Override
	public JsonObject writeJson() {
		JsonObject object = message();
		XChange.instance.station.writeToJson(object);

		// Create Commits Array with all local Files
		JsonArray array = new JsonArray();
		for (MVRFile file : XChange.instance.getFiles()) {
			array.add(new C03PacketCommit(file, null, new Version(0, 0)).writeJson());
		}
		object.add("Commits", array);

		System.out.println("Write C01");
		return object;
	}

	@Override
	public ByteBuf writePacket() {
		return Util.jsonToByteBuf(writeJson());
	}

}
