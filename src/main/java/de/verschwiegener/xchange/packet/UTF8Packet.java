package de.verschwiegener.xchange.packet;

import java.util.UUID;
import java.util.stream.StreamSupport;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import de.verschwiegener.xchange.XChange;
import de.verschwiegener.xchange.packet.packets.S03PacketCommit;
import de.verschwiegener.xchange.util.MVRFile;
import de.verschwiegener.xchange.util.PacketType;
import de.verschwiegener.xchange.util.Station;
import de.verschwiegener.xchange.util.Version;
import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * @author julius
 *
 */
public abstract class UTF8Packet implements Packet {

	protected final PacketType packetType;

	public UTF8Packet(PacketType packetType) {
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
		object.addProperty("Type", packetType.toString());
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
		object.addProperty("Type", packetType.toString());
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
			XChange.instance.listener.xChangeError(packetType.toString(), message);
		}
		
		return ok;
	}
	
	public PacketType getPacketType() {
		return packetType;
	}
	
	protected boolean checkVersion(JsonObject object, Station source) {
		Version stationVersion = new Version(object);
		if (!XChange.instance.station.getVersion().checkVersion(stationVersion)) {
			source.getConnection()
					.sendPacket(new S03PacketCommit(false, "Version is not Compatible With Server, Server Version: "
							+ XChange.instance.station.getVersion().toString()));

			// Send Error
			XChange.instance.listener.xChangeError(packetType.toString(),
					"Station " + object.get("StationUUID").getAsString() + " Version: " + stationVersion
							+ " is not Compatible With Server Version");
			return false;
		}
		return true;
	}
	
	protected void handleFile(JsonObject object, Station station) {
		// Get File and add Station
		MVRFile file = new MVRFile(object);
		file.getStationUUIDs().add(station.getUUID());

		// Get Target Stations, if this instance isn't a target ignore
		JsonArray targetStations = object.get("ForStationsUUID").getAsJsonArray();
		// If target is empty everyone is target
		if (!targetStations.isEmpty()) {
			// TargetStations contains clients uuid
			if (StreamSupport.stream(targetStations.spliterator(), false).filter(element -> UUID
					.fromString(element.getAsString()).equals(XChange.instance.station.getUUID())) == null) {
				return;
			}
		}

		// Register File
		XChange.instance.registerFile(file);
	}

}
