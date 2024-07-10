package de.verschwiegener.xchange.util;

import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
			//e.printStackTrace();
		}
		return buffer;
	}
	
	public static ByteBuf packetBuilder(ByteBuf data, int packageType) {
		ByteBuf buffer = Unpooled.buffer(28);
		buffer.writeInt(MVR_PACKAGE_HEADER);
		buffer.writeInt(MVR_PACKAGE_VERSION);
		buffer.writeInt(0);
		buffer.writeInt(0);
		buffer.writeInt(packageType);
		buffer.writeLong(data.readableBytes());
		buffer.writeBytes(data);
		data.clear();
		
		buffer.resetReaderIndex();
		return buffer;
		
	}

}
