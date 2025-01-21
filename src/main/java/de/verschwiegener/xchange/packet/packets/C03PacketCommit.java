package de.verschwiegener.xchange.packet.packets;

import java.util.UUID;
import java.util.stream.StreamSupport;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import de.verschwiegener.xchange.XChange;
import de.verschwiegener.xchange.packet.UTF8Packet;
import de.verschwiegener.xchange.util.MVRFile;
import de.verschwiegener.xchange.util.PacketType;
import de.verschwiegener.xchange.util.Station;
import de.verschwiegener.xchange.util.Util;
import de.verschwiegener.xchange.util.Version;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * The MVR commit message informs all connected stations that there is a new MVR
 * commit. This message only informs the stations about the existence of the new
 * file. Stations needs to request the MVR file with a MVR_REQUEST message.
 * 
 * Each MVR commit represents one revision of the project. Therefore an array of
 * MVR commits, as found in the MVR_JOIN message, represents the working history
 * of the project. It is up to the client how many commits are kept in store at
 * any time.
 * 
 * @author julius
 *
 */
public class C03PacketCommit extends UTF8Packet {

	private MVRFile file;
	private Station[] forStation;
	private Version version;

	public C03PacketCommit() {
		super(PacketType.MVR_COMMIT);
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
	 * Commit Packet
	 * 
	 * @param file MVR File to send
	 * @param forStation Station to send File to
	 */
	public C03PacketCommit(MVRFile file, Station forStation) {
		this(file, (forStation == null) ? new Station[] {} : new Station[] {forStation});
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
		Station sourceStation = Util.checkStation(object.get("StationUUID").getAsString(), packetType);
		if(sourceStation == null)
			return;
		
		//Check if Version is compatible
		Version stationVersion = new Version(object);
		if (!XChange.instance.station.getVersion().checkVersion(stationVersion)) {
			sourceStation.getConnection()
					.sendPacket(new S03PacketCommit(false, "Version is not Compatible With Server, Server Version: "
							+ XChange.instance.station.getVersion().toString()));

			// Send Error
			XChange.instance.listener.xChangeError(packetType.toString(), "Station " + object.get("StationUUID").getAsString()
					+ " Version: " + stationVersion + " is not Compatible With Server Version");
			return;
		}
		
		
		//Get File and add Station
		MVRFile file = new MVRFile(object);
		file.getStationUUIDs().add(sourceStation.getUUID());
		

		//Get Target Stations, if this instance isn't a target ignore
		JsonArray targetStations = object.get("ForStationsUUID").getAsJsonArray();
		// If target is empty everyone is target
		if (!targetStations.isEmpty()) {
			//TargetStations contains clients uuid
			boolean isTarget = StreamSupport.stream(targetStations.spliterator(), false).filter(element -> UUID
					.fromString(element.getAsString()).equals(XChange.instance.station.getUUID())) != null;
			
			//If this Station is not the Target return
			if (!isTarget)
				return;
			
		}

		//Register File
		XChange.instance.registerFile(file);
		
		
		//Send Return packet
		sourceStation.getConnection().sendPacket(new S03PacketCommit());
		
		//Websocket Server Commit File to all other known Station
		if(XChange.instance.isWebSocketServer()) {
			XChange.instance.getStations().forEach(station -> {
				if(!station.getUUID().equals(sourceStation.getUUID())) {
					station.getConnection().sendPacket(new C03PacketCommit(file, station));
				}
			});
		}
	}

	@Override
	public JsonObject writeJson() {
		JsonObject object = message();
		version.writeToJson(object);
		file.writeToJson(object);

		// Add our UUID
		object.addProperty("StationUUID", XChange.instance.station.getUUID().toString());

		// Add Target Stations
		JsonArray array = new JsonArray();
		//If forStation equals null send empty array
		if(forStation != null) {
			for (Station s : forStation) {
				array.add(s.getUUID().toString());
			}
		}
		object.add("ForStationsUUID", array);

		return object;
	}

	@Override
	public ByteBuf writePacket() {
		return Util.jsonToByteBuf(writeJson());
	}

}
