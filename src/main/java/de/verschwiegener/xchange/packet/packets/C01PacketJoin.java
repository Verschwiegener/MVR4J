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
		
		Station testStation = XChange.instance.getStationByUUID(UUID.fromString(object.get("StationUUID").getAsString()));
		
		//Create Station for WebSocket Server Mode or Station didn't announce itself via mDNS
		if ((XChange.instance.isWebSocketServer() || XChange.instance.isMDNS()) && testStation == null) {
			Station station = new Station(object);
			station.setConnection(new Connection(((InetSocketAddress) ctx.channel().remoteAddress())));
			//station.connect();
			//Set Station Connection
			station.getConnection().setChannel(ctx.channel());
			XChange.instance.addStation(station);
		}

		Station station = Util.checkStation(object.get("StationUUID").getAsString(), packetType);
		if (station == null)
			return;
		
		/**
		 * Fix for Stations that don´t announce their presence via mDNS
		 */
		if(!station.ismDNS() && XChange.instance.isMDNS()) {
			station.getConnection().setChannel(ctx.channel());
		}

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
		files.forEach(jsarray -> {
			JsonObject jsobject = (JsonObject) jsarray;

			Station sourceStation = Util.checkStation(jsobject.get("StationUUID").getAsString(), packetType);

			// Get File and add Station
			MVRFile file = new MVRFile(jsobject);
			file.getStationUUIDs().add(sourceStation.getUUID());

			// Get Target Stations, if this instance isn't a target ignore
			JsonArray targetStations = jsobject.get("ForStationsUUID").getAsJsonArray();
			// If target is empty everyone is target
			if (!targetStations.isEmpty()) {
				// TargetStations contains clients uuid
				boolean isTarget = StreamSupport.stream(targetStations.spliterator(), false).filter(element -> UUID
						.fromString(element.getAsString()).equals(XChange.instance.station.getUUID())) != null;

				// If this Station is not the Target return
				if (!isTarget)
					return;

			}

			// Register File
			XChange.instance.registerFile(file);
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
			//TODO Crashes BlenderDMX Server
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
