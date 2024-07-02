package de.verschwiegener.xchange.util;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonObject;

import de.verschwiegener.mvr.util.MVRUtil;
import de.verschwiegener.xchange.XChange;
import de.verschwiegener.xchange.packet.packets.C04PacketRequest;

public class MVRFile {

	private int fileSize;
	private UUID uuid;
	private String fileName;

	/**
	 * File Comment from last commit
	 */
	private String comment;

	/**
	 * Location of the File on the Device File System
	 */
	private File fileSystemLocation;

	/**
	 * UUIDs of Stations containing the File
	 */
	private ArrayList<UUID> stationUUID = new ArrayList<UUID>();

	public MVRFile(JsonObject object) {
		fileSize = object.get("FileSize").getAsInt();
		uuid = UUID.fromString(object.get("FileUUID").getAsString());
		fileName = object.get("FileName").getAsString();
		comment = object.get("Comment").getAsString();
	}

	/**
	 * Request File from Peers
	 * 
	 * @param future CompletableFuture, future if request was send without exception
	 */
	public void requestFile(CompletableFuture<Void> future) {
		if (isFilePresent()) {
			future.complete(null);
			return;
		}

		for (UUID uuid : getStationUUID()) {
			Station requestStation = XChange.instance.getStationByUUID(uuid);

			requestStation.getConnection().sendPacket(new C04PacketRequest(this, requestStation))
					.whenComplete((result, ex) -> {
						if (ex != null) {
							future.completeExceptionally(ex);
							return;
						}
						future.complete(null);
					});
		}

	}
	/**
	 * Returns File System Location of this MVR File
	 * @return
	 */
	public File getFilesystemLocation() {
		if (fileSystemLocation == null)
			fileSystemLocation = new File(XChange.instance.mvrWorkingDirectory, fileName);
		return fileSystemLocation;
	}
	
	/**
	 * Creates MVR Code representation to be parsed
	 * 
	 * @return
	 */
	public MVRUtil getAsMVR() {
		return new MVRUtil(fileSystemLocation);
	}

	public ArrayList<UUID> getStationUUID() {
		return stationUUID;
	}
	
	public UUID getUuid() {
		return uuid;
	}

	public String getFileName() {
		return fileName;
	}

	public boolean isFilePresent() {
		return getFilesystemLocation().exists();
	}

	public void writeToJson(JsonObject object) {
		object.addProperty("FileSize", fileSize);
		object.addProperty("FileUUID", uuid.toString());
		object.addProperty("FileName", fileName);
		object.addProperty("Comment", comment);
	}
	
}
