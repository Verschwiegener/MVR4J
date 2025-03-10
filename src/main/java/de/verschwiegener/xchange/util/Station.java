package de.verschwiegener.xchange.util;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonObject;

import de.verschwiegener.xchange.XChange;
import de.verschwiegener.xchange.packet.packets.C01PacketJoin;

/**
 * Wrapper Class for Peers
 * 
 * @author julius
 */
public class Station {

	private UUID uuid;
	private String name;
	private String provider;
	private Version version;

	/**
	 * TCP Connection to Station
	 */
	private Connection connection;

	/**
	 * Manually create Station
	 * 
	 * @param uuid       UUID of the XChange Station, UUID should be persistent
	 *                   across multiple start-ups of the same software on the same
	 *                   computer
	 * @param name       Name of the XChange Station
	 * @param provider   Name of the XChange Provider of the Station
	 * @param version    MVR Version of the XChange Station
	 * @param connection Connection to the XChange Station
	 */
	public Station(UUID uuid, String name, String provider, Version version, Connection connection) {
		this.uuid = uuid;
		this.name = name;
		this.provider = provider;
		this.version = version;
		this.connection = connection;
	}

	/**
	 * Creates Station by loading data out of Json
	 * 
	 * @param object
	 */
	public Station(JsonObject object) {
		uuid = UUID.fromString(object.get("StationUUID").getAsString());
		provider = object.get("Provider").getAsString();
		name = object.get("StationName").getAsString();
		version = new Version(object);
	}

	/**
	 * Creates Empty Station
	 */
	public Station() {

	}

	/**
	 * Writes Station Data to Json
	 * 
	 * @param object JsonObjectto write to
	 */
	public void writeToJson(JsonObject object) {
		object.addProperty("StationUUID", uuid.toString());
		object.addProperty("Provider", provider);
		object.addProperty("StationName", name);
		version.writeToJson(object);
	}

	/**
	 * Update Provider, Name and Version with new Data
	 * 
	 * @param object
	 */
	public void updateValues(JsonObject object) {
		provider = object.get("Provider").getAsString();
		name = object.get("StationName").getAsString();
		version = new Version(object);
	}

	public Version getVersion() {
		return version;
	}

	public UUID getUUID() {
		return uuid;
	}

	public String getName() {
		return name;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Checks if station has uuid
	 * 
	 * @return
	 */
	public boolean isValid() {
		return uuid != null;
	}

	/**
	 * Connect to peer
	 */
	public void connect() {
		// Check if a Connection can be established
		CompletableFuture<Void> future = null;
		try {
			future = connection.connectTo();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		future.whenComplete((result, ex) -> {
			if (ex != null) {
				XChange.instance.listener.xChangeError("Initial Connection",
						"Could not Connect to Station:" + uuid.toString() + "IP: "
								+ connection.getRemoteAddress().getHostString() + " at Port: "
								+ connection.getRemoteAddress().getPort());
				return;
			}
			// send Join Packet
			connection.sendPacket(new C01PacketJoin());
			// Add Station when connection is established
			XChange.instance.addStation(this);
		});

	}

}
