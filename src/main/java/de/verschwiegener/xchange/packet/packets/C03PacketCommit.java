package de.verschwiegener.xchange.packet.packets;

import java.util.UUID;
import java.util.stream.StreamSupport;

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

public class C03PacketCommit extends UTF8Packet {

	private MVRFile file;
	private Station[] forStation;
	private Version version;

	public C03PacketCommit() {
		super("MVR_COMMIT");
	}

	/**
	 * Commit Packet
	 * 
	 * @param file        MVRFile to send
	 * @param forStations Array of Stations to send File to, if empty send to all
	 */
	public C03PacketCommit(MVRFile file, Station[] forStations) {
		this(file, forStations, XChange.instance.station.getVersion());
	}

	/**
	 * Commit Packet with custom Version (0.0) for first join
	 * 
	 * @param file        MVRFile to send
	 * @param forStations Array of Stations to send File to, if empty send to all
	 * @param version     current MVR File Version, 0.0 for first join
	 */
	public C03PacketCommit(MVRFile file, Station[] forStations, Version version) {
		this();
		this.file = file;
		this.forStation = forStations;
		this.version = version;
	}

	@Override
	public void parsePacket(JsonObject object, ChannelHandlerContext ctx) {
		Version version = new Version(object);

		Station sourceStation = XChange.instance
				.getStationByUUID(UUID.fromString(object.get("StationUUID").getAsString()));
		
		MVRFile file = new MVRFile(object);
		file.getStationUUID().add(sourceStation.getUuid());
		
		XChange.instance.addFile(file);

		if (version.getMajor() == 0 && version.getMinor() == 0)
			return;

		JsonArray targetStations = object.get("ForStationsUUID").getAsJsonArray();
		// If target is empty everyone is target
		if (!targetStations.isEmpty()) {
			// If targetStations contains clients uuid
			boolean isTarget = StreamSupport.stream(targetStations.spliterator(), false).filter(element -> UUID
					.fromString(element.getAsString()).equals(XChange.instance.station.getUuid())) != null;

			if (!isTarget)
				return;
		}
		
		
		XChange.instance.listener.newMVRFile(file);

		sourceStation.getConnection().sendPacket(new S03PacketCommit());
		
		// TODO Call API that a new file is available
	}

	@Override
	public JsonObject writeJson() {
		JsonObject object = message();
		version.writeToJson(object);
		file.writeToJson(object);

		// Add our UUID
		object.addProperty("StationUUID", XChange.instance.station.getUuid().toString());

		// Add Target Stations
		JsonArray array = new JsonArray();
		for (Station s : forStation) {
			array.add(s.getUuid().toString());
		}
		object.add("FromStationUUID", array);

		return object;
	}

	@Override
	public ByteBuf writePacket() {
		return Util.jsonToByteBuf(writeJson());
	}

}
