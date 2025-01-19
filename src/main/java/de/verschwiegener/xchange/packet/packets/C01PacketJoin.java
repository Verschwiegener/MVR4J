package de.verschwiegener.xchange.packet.packets;

import java.net.InetSocketAddress;
import java.util.UUID;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import de.verschwiegener.xchange.ProtocolMode;
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

/**
 * When a MVR-xchange client connects with another MVR-xchange client, the first
 * MVR-xchange client needs to send a MVR_JOIN message.
 * 
 * NOTE A MVR-xchange client can send multiple MVR_JOIN messages to the same
 * server during the same connection to update its name or get the latest MVR
 * file list.
 * 
 * @author julius
 *
 */
public class C01PacketJoin extends UTF8Packet {

	public C01PacketJoin() {
		super(PacketType.MVR_JOIN);
	}

	@Override
	public void parsePacket(JsonObject object, ChannelHandlerContext ctx) {
		System.out.println("Ctx: " + ctx.channel());
		if (XChange.instance.mode == ProtocolMode.WEBSOCKET_SERVER || XChange.instance.mode == ProtocolMode.WEBSOCKET_CLIENT) {
			Station station = new Station(object);
			station.setConnection(new Connection(((InetSocketAddress) ctx.channel().remoteAddress())));
			//Set Station COnnection
			station.getConnection().setChannel(ctx.channel());
			XChange.instance.addStation(station);
		}

		Station station = Util.checkStation(object.get("StationUUID").getAsString(), packetType);
		if (station == null)
			return;

		// Check if Version is Compatible
		Version stationVersion = new Version(object);
		if (!XChange.instance.station.getVersion().checkVersion(stationVersion)) {
			station.getConnection()
					.sendPacket(new S01PacketJoin(false, "Version is not Compatible With Server, Server Version: "
							+ XChange.instance.station.getVersion().toString()));

			// Send Error
			XChange.instance.listener.xChangeError(packetType.toString(),
					"Station " + object.get("StationUUID").getAsString() + " Version: " + stationVersion
							+ " is not Compatible With Server Version");
			return;
		}

		station.updateValues(object);

		JsonArray files = object.get("Commits").getAsJsonArray();
		files.forEach(element -> {
			// TODO parse MVR_COMMIT
		});

		// Send return packet
		station.getConnection().sendPacket(new S01PacketJoin());
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
		return object;
	}

	@Override
	public ByteBuf writePacket() {
		return Util.jsonToByteBuf(writeJson());
	}

}
