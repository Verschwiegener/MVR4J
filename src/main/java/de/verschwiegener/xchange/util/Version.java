package de.verschwiegener.xchange.util;

import com.google.gson.JsonObject;

/**
 * 
 * @author julius
 *
 */
public class Version {
	
	public static Version MVR_LATEST = new Version(1, 6);
	
	private final int major, minor;
	
	public Version(int major, int minor) {
		this.major = major;
		this.minor = minor;
	}
	/**
	 * Creates Version out of JsonObject
	 * @param object
	 */
	public Version(JsonObject object) {
		major = object.get("verMajor").getAsInt();
		minor = object.get("verMinor").getAsInt();
	}
	/**
	 * Checks if version equals this Version
	 * 
	 * @param version Version to check against
	 * @return
	 */
	public boolean checkVersion(Version version) {
		return version.major == major &&  version.minor == minor;
	}
	/**
	 * Write Version into JsonObject
	 * @param object
	 */
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
