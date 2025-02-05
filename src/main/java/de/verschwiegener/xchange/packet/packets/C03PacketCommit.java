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
		
		Station station = Util.checkStation(object.get("StationUUID").getAsString(), getPacketType());

		if (station == null)
			return;
		
		if (!checkVersion(object, station))
			return;

		// Handles File Parsing
		handleFile(object, station);

		// Send Return packet
		station.getConnection().sendPacket(new S03PacketCommit());

		// Websocket Server Commit File to all other known Station
		if (XChange.instance.isWebSocketServer()) {
			XChange.instance.getStations().stream().filter(s -> !s.getUUID().equals(station.getUUID()))
					.forEach(target -> target.getConnection().sendPacket(new C03PacketCommit(file, target)));
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
