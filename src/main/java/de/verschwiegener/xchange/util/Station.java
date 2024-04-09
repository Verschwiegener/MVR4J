package de.verschwiegener.xchange.util;

import java.util.UUID;

import com.google.gson.JsonObject;

/**
 * Wrapper Class for Peers
 */
public class Station {
	
	private final UUID uuid;
	private String name;
	private String provider;
	private Version version;
	
	/**
	 * TCP Connection to Station
	 */
	private Connection connection;
	
	public Station(UUID uuid, String name, String provider, Version version, Connection connection) {
		this.uuid = uuid;
		this.name = name;
		this.provider = provider;
		this.version = version;
		this.connection = connection;
	}
	
	public Station(JsonObject object) {
		uuid = UUID.fromString(object.get("StationUUID").getAsString());
		provider = object.get("Provider").getAsString();
		name = object.get("StationName").getAsString();
		version = new Version(object);
	}
	
	public void writeToJson(JsonObject object) {
		object.addProperty("StationUUID", uuid.toString());
		object.addProperty("Provider", provider);
		object.addProperty("StationName", name);
		version.writeToJson(object);
	}
	
	public void updateValues(JsonObject object) {
		provider = object.get("Provider").getAsString();
		name = object.get("StationName").getAsString();
		version = new Version(object);
	}
	
	public Version getVersion() {
		return version;
	}
	public UUID getUuid() {
		return uuid;
	}
	public String getName() {
		return name;
	}
	
	public Connection getConnection() {
		return connection;
	}
	

}
