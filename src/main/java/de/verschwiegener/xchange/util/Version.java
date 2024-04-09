package de.verschwiegener.xchange.util;

import com.google.gson.JsonObject;

public class Version {
	
	private final int major, minor;
	
	public Version(int major, int minor) {
		this.major = major;
		this.minor = minor;
	}
	
	public Version(JsonObject object) {
		major = object.get("verMajor").getAsInt();
		minor = object.get("verMinor").getAsInt();
	}
	
	public boolean checkVersion(Version version) {
		return version.major == major &&  version.minor == minor;
	}
	public void writeToJson(JsonObject object) {
		object.addProperty("verMajor", major);
		object.addProperty("verMinor", minor);
	}
	public int getMajor() {
		return major;
	}
	public int getMinor() {
		return minor;
	}
	
	@Override
	public String toString() {
		return major + "/" + minor;
	}
	
}
