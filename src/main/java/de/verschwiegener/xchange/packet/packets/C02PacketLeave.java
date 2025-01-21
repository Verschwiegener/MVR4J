package de.verschwiegener.xchange.packet.packets;

import java.util.UUID;

import com.google.gson.JsonObject;

import de.verschwiegener.xchange.XChange;
import de.verschwiegener.xchange.packet.UTF8Packet;
import de.verschwiegener.xchange.util.PacketType;
import de.verschwiegener.xchange.util.Station;
import de.verschwiegener.xchange.util.Util;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * A client sends a MVR_LEAVE when it wants to quit an MVR-xchange Group and
 * does not want to get updates about new MVR files anymore.
 * 
 * @author julius
 *
 */
public class C02PacketLeave extends UTF8Packet {

	public C02PacketLeave() {
		super(PacketType.MVR_LEAVE);
	}

	@Override
	public void parsePacket(JsonObject object, ChannelHandlerContext ctx) {
		Station sourceStation = Util.checkStation(object.get("FromStationUUID").getAsString(), packetType);
		
		if(sourceStation == null)
			return;

		//Remove Station
		XChange.instance.removeStation(sourceStation);

		//Send Return Packet
		sourceStation.getConnection().sendPacket(new S02PacketLeave());

	}

	@Override
	public JsonObject writeJson() {
		JsonObject object = message();

		// Add our UUID
		object.addProperty("FromStationUUID", XChange.instance.station.getUUID().toString());
		return object;
	}

	@Override
	public ByteBuf writePacket() {

		return Util.jsonToByteBuf(writeJson());
	}

}
