package de.verschwiegener.xchange.util;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import de.verschwiegener.xchange.XChange;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 
 * @author julius
 *
 */
public class Util {
	
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	
	public static final int MVR_PACKAGE_HEADER = 778682;
	public static final int MVR_PACKAGE_VERSION = 1;
	
	public static JsonObject byteBufToJson(ByteBuf buffer) {
		return (JsonObject) GSON.fromJson(buffer.toString(StandardCharsets.UTF_8),
				JsonElement.class);
	}
	
	public static ByteBuf jsonToByteBuf(JsonObject object) {
		ByteBuf buffer = Unpooled.buffer();
		try {
			buffer.writeBytes(GSON.toJson(object).getBytes(StandardCharsets.UTF_8));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return buffer;
	}
	
	public static ByteBuf packetBuilder(ByteBuf data, int packageType) {
		return packetBuilder(data, packageType, 1, 0);
	}
	
	public static ByteBuf packetBuilder(ByteBuf data, int packageType, int packageCount, int packageNumber) {
		ByteBuf buffer = Unpooled.buffer(28);
		buffer.writeInt(MVR_PACKAGE_HEADER);
		buffer.writeInt(MVR_PACKAGE_VERSION);
		buffer.writeInt(packageNumber);
		buffer.writeInt(packageCount);
		buffer.writeInt(packageType);
		buffer.writeLong(data.readableBytes());
		buffer.writeBytes(data);
		
		data.clear();
		return buffer;
	}
	
	/**
	 * Checks if Station is known
	 * @param object
	 * @param packetType
	 * @return
	 */
	public static Station checkStation(String object, PacketType packetType) {
		Station station = XChange.instance.getStationByUUID(UUID.fromString(object));
		
		//Check if Station exists
		if (station == null) {
			XChange.instance.listener.xChangeError(packetType.toString(),
					packetType + " Station " + object + " not known");
			return null;
		}
		return station;
	}
	
	/**
	 * Returns the Default File
	 * 
	 * @return
	 */
	public static File getDefaultSaveFile() {
		String captureTime = (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date());
		File ssFile = new File(XChange.instance.mvrWorkingDirectory, captureTime + ".mvr");
		// Check File for existing ones
		int iterator = 0;
		while (ssFile.exists()) {
			iterator++;
			ssFile = new File(XChange.instance.mvrWorkingDirectory, captureTime + "_" + iterator + ".mvr");
		}
		return ssFile;
	}

}
