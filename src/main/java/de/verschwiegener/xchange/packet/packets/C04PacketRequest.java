package de.verschwiegener.xchange.packet.packets;

import java.util.UUID;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import de.verschwiegener.xchange.XChange;
import de.verschwiegener.xchange.packet.Packet;
import de.verschwiegener.xchange.packet.UTF8Packet;
import de.verschwiegener.xchange.util.MVRFile;
import de.verschwiegener.xchange.util.Station;
import de.verschwiegener.xchange.util.Util;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * This packet requests a MVR file from a station. You either can request a
 * specific MVR via its UUID or get the last available MVR File by leaving the
 * field empty. The underlying software will then generate a file based on the
 * current state. This also triggers a MVR_COMMIT message to other connected
 * stations.
 * 
 * @author julius
 *
 */
public class C04PacketRequest extends UTF8Packet {

	private MVRFile file;
	private Station request;

	public C04PacketRequest() {
		super("MVR_REQUEST");
	}

	public C04PacketRequest(MVRFile file, Station request) {
		this();
		this.file = file;
		this.request = request;
	}

	@Override
	public void parsePacket(JsonObject object, ChannelHandlerContext ctx) {
		UUID retrieveUUID = UUID.fromString(object.get("FromStationUUID").getAsString());
		if (!XChange.instance.station.getUuid().equals(retrieveUUID))
			return;

		MVRFile file;
		if (object.get("FileUUID") != null) {
			file = XChange.instance.getFileByUUID(UUID.fromString(object.get("FileUUID").getAsString()));
		} else {
			// TODO get latest File
		}
		// TODO Send MVRFile to requesting station

		Packet packet = new S04PacketRequest();
		ctx.writeAndFlush(Util.packetBuilder(packet.writePacket(), packet.getPackageType()));

	}

	@Override
	public JsonObject writeJson() {
		JsonObject object = message();
		object.addProperty("FileUUID", file.getUuid().toString());

		JsonArray array = new JsonArray();
		array.add(request.getUuid().toString());
		object.add("FromStationUUID", array);
		return object;
	}

	@Override
	public ByteBuf writePacket() {
		// Set CurrentReceiveFile to requested MVR File
		XChange.instance.currentReceiveFile = file;
		return Util.jsonToByteBuf(writeJson());
	}

}
