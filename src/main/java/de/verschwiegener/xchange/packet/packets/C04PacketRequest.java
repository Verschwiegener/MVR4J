package de.verschwiegener.xchange.packet.packets;

import java.io.RandomAccessFile;
import java.util.UUID;
import java.util.stream.Stream;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import de.verschwiegener.xchange.XChange;
import de.verschwiegener.xchange.packet.Packet;
import de.verschwiegener.xchange.packet.UTF8Packet;
import de.verschwiegener.xchange.util.MVRFile;
import de.verschwiegener.xchange.util.PacketType;
import de.verschwiegener.xchange.util.Station;
import de.verschwiegener.xchange.util.Util;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

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
		super(PacketType.MVR_REQUEST);
	}

	public C04PacketRequest(MVRFile file, Station request) {
		this();
		this.file = file;
		this.request = request;
	}

	@Override
	public void parsePacket(JsonObject object, ChannelHandlerContext ctx) {
		JsonArray retriveUUIDs = object.get("FromStationUUID").getAsJsonArray();

		if (!retriveUUIDs.isEmpty()) {
			// If File should not be retrieved from this Station ignore
			JsonElement e = retriveUUIDs.asList().stream().filter(j -> XChange.instance.station.getUUID().toString().equals(j.getAsString()))
					.findFirst().orElse(null);
			if(e == null)
				return;
		}

		// Get File
		MVRFile file = null;
		if (object.get("FileUUID") != null && !object.get("FileUUID").getAsString().isEmpty()) {
			file = XChange.instance.getFileByUUID(UUID.fromString(object.get("FileUUID").getAsString()));
		} else {
			// Get Latest File if FileUUID is empty
			file = XChange.instance.getFiles().get(XChange.instance.getFiles().size() - 1);
		}

		// TODO When new MVR Version is out check for Version and send File via S04
		// Packet
		// Because the MVR_REQUEST Packet does not have a StationUUID Attribute in MVR
		// 1.6 we need to send it directly to the Context

		// File was not found or this Station has no Files, Send File Not available
		// Packet
		if (file == null || !file.existLocal()) {
			if (XChange.instance.isWebSocketServer()) {
				file.requestFile();
			} else {
				Packet packet = new S04PacketRequest(false, "The MVR is not available on this client");
				ctx.writeAndFlush(Util.packetBuilder(packet.writePacket(), packet.getPackageType()));
			}
			return;
		}

		ByteBuf data;
		// Send MVRFile to requesting station
		try {
			RandomAccessFile stream = new RandomAccessFile(file.getFilesystemLocation(), "r");
			// Read to Buffer
			byte[] array = new byte[(int) stream.length()];
			stream.read(array);
			stream.close();
			data = Unpooled.wrappedBuffer(array);
		} catch (Exception e) {
			e.printStackTrace();
			// Send File Not available Packet
			Packet packet = new S04PacketRequest(false, "The MVR is not available on this client");
			ctx.writeAndFlush(Util.packetBuilder(packet.writePacket(), packet.getPackageType()));

			// Call Error Listener
			XChange.instance.listener.xChangeError(packetType.toString(), "Could not send File: " + file.getFileName());
			return;
		}

		// Send Data
		if (XChange.instance.isMDNS()) {
			ctx.writeAndFlush(Util.packetBuilder(data, 1, 1, 0));
		} else {
			ctx.writeAndFlush(new BinaryWebSocketFrame(data));
		}
	}

	@Override
	public JsonObject writeJson() {
		JsonObject object = message();
		object.addProperty("FileUUID", file.getUuid().toString());

		JsonArray array = new JsonArray();
		array.add(request.getUUID().toString());
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
