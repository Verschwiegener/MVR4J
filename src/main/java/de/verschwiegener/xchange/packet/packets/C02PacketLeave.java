package de.verschwiegener.xchange.packet.packets;

import java.util.UUID;

import com.google.gson.JsonObject;

import de.verschwiegener.xchange.XChange;
import de.verschwiegener.xchange.packet.UTF8Packet;
import de.verschwiegener.xchange.util.Station;
import de.verschwiegener.xchange.util.Util;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class C02PacketLeave extends UTF8Packet {

	public C02PacketLeave() {
		super("MVR_LEAVE");
	}

	@Override
	public void parsePacket(JsonObject object, ChannelHandlerContext ctx) {
		Station sourceStation = XChange.instance
				.getStationByUUID(UUID.fromString(object.get("FromStationUUID").getAsString()));

		XChange.instance.removeStation(sourceStation);
		XChange.instance.listener.stationLeave(sourceStation);
		
		sourceStation.getConnection().sendPacket(new S02PacketLeave());

	}

	@Override
	public JsonObject writeJson() {
		JsonObject object = message();

		// Add our UUID
		object.addProperty("FromStationUUID", XChange.instance.station.getUuid().toString());
		return object;
	}

	@Override
	public ByteBuf writePacket() {

		return Util.jsonToByteBuf(writeJson());
	}

}
