package de.verschwiegener.xchange.packet.packets;

import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.stream.StreamSupport;

import com.google.gson.JsonArray;
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

		Station station = Util.checkStationJoin(object, getPacketType(), ctx);

		if (station == null)
			return;

		/**
		 * Fix for Stations that don´t announce their presence via mDNS
		 */
		// Maybe not needed anymore
		/*
		 * if(!station.ismDNS() && XChange.instance.isMDNS()) {
		 * station.getConnection().setChannel(ctx.channel()); }
		 */

		// Check Server Version is valid
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

		// Update Station Name, Provider and Version
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
			array.add(new C03PacketCommit(file, null, XChange.instance.station.getVersion()).writeJson());
		}
		object.add("Commits", array);
		return object;
	}

	@Override
	public ByteBuf writePacket() {
		return Util.jsonToByteBuf(writeJson());
	}

}
