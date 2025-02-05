package de.verschwiegener.xchange.util;

import java.io.File;
import java.net.InetSocketAddress;
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
import io.netty.channel.ChannelHandlerContext;

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
	 * Checks if Station exists, if not and we are in WebSocket Mode create a new
	 * one. Only used in MVR_JOIN or MVR_JOIN_RET because that are the only 2 Packets where we a new station would occur
	 * 
	 * @param object
	 * @param packetType
	 * @param ctx
	 * @return
	 */
	public static Station checkStationJoin(JsonObject object, PacketType packetType, ChannelHandlerContext ctx) {
		Station station = XChange.instance.getStationByUUID(UUID.fromString(object.get("StationUUID").getAsString()));

		if (station == null) {
			// If we are in WebSocket Mode a new station can be added
			if (XChange.instance.isWebSocketServer() || XChange.instance.isWebSocketClient()) {
				Station newStation = new Station(object);
				newStation.setConnection(new Connection(((InetSocketAddress) ctx.channel().remoteAddress())));
				
				//We need to directly connect to the station to prevent from sending a new MVR_JOIN Packet
				try {
					newStation.getConnection().connectTo();
				}catch(InterruptedException ie) {
					return null;
				}
				// Set Station Connection
				// newStation.getConnection().setChannel(ctx.channel());
				XChange.instance.addStation(newStation);
				return newStation;
			} else {
				// mDNS Mode
				XChange.instance.listener.xChangeError(packetType.toString(),
						packetType + " Station " + object + " not known");
			}
		}
		return station;
	}
	
	/**
	 * Checks if Station is known
	 * @param object
	 * @param packetType
	 * @return
	 */
	public static Station checkStation(String object, PacketType packetType) {
		UUID uuid = UUID.fromString(object);
		
		//FIX for BlenderDMX WebSocket Server
		//Check if UUID is local station, then ignore packet
		if(uuid.equals(XChange.instance.station.getUUID()))
			return null;
		
		Station station = XChange.instance.getStationByUUID(uuid);
		
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
