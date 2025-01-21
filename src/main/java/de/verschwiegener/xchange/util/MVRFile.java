package de.verschwiegener.xchange.util;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonObject;

import de.verschwiegener.mvr.util.MVRParser;
import de.verschwiegener.xchange.XChange;
import de.verschwiegener.xchange.packet.packets.C04PacketRequest;

/**
 * Class containing MVR File Data as well as logic to request the file
 * 
 * @author julius
 *
 */
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

	/**
	 * If the MVR File exists on this machine
	 */
	private boolean isLocal = false;

	/**
	 *  Creates MVRFile with specified fileName
	 * 
	 * @param mvrFile Location of file to be shared
	 * @param fileName String Name the file should have on the receiving machine
	 * @param comment Describes the changes made in this version of the MVR file.
	 */
	public MVRFile(File mvrFile, String fileName, String comment) {
		this.fileSystemLocation = mvrFile;
		this.comment = comment;
		this.fileName = fileName;
		this.fileSize = (int) mvrFile.length();
		uuid = UUID.nameUUIDFromBytes(mvrFile.getName().getBytes());
	}
	
	/**
	 * Creates MVRFile without specified fileName
	 * 
	 * @param mvrFile Location of file to be shared
	 * @param comment Describes the changes made in this version of the MVR file.
	 */
	public MVRFile(File mvrFile, String comment) {
		this.fileSystemLocation = mvrFile;
		this.comment = comment;
		this.fileName = null;
		this.fileSize = (int) mvrFile.length();
		uuid = UUID.nameUUIDFromBytes(mvrFile.getName().getBytes());
	}

	public MVRFile(JsonObject object) {
		fileSize = object.get("FileSize").getAsInt();
		uuid = UUID.fromString(object.get("FileUUID").getAsString());
		fileName = object.get("FileName").getAsString();
		comment = object.get("Comment").getAsString();
	}

	/**
	 * Request File from all known Stations, ignoring ignoreStation
	 * 
	 * @param ignoreStation Station, station to not send File Request to
	 * @return future CompletableFuture, future if request was send without
	 *         exception
	 */
	public void requestFile(UUID ignoreStation) {
		if (existLocal()) {
			return;
		}

		for (UUID uuid : getStationUUIDs()) {
			// Ignore Station
			if (ignoreStation != null && uuid.equals(ignoreStation)) {
				continue;
			}

			Station requestStation = XChange.instance.getStationByUUID(uuid);
			requestStation.getConnection().sendPacket(new C04PacketRequest(this, requestStation));
		}
	}

	/**
	 * Requests File from all Stations
	 * 
	 * @return
	 */
	public void requestFile() {
		requestFile(null);
	}

	/**
	 * Returns File System Location of this MVR File
	 * 
	 * @return
	 */
	public File getFilesystemLocation() {
		if (fileSystemLocation == null) {
			//If FileName is Blank and not set afterwards generate Default FileName
			if(fileName.isBlank()) {
				fileSystemLocation = Util.getDefaultSaveFile();
			}else {
				fileSystemLocation = new File(XChange.instance.mvrWorkingDirectory, fileName);
			}
		}
		//fileSystemLocation.mkdir();
		return fileSystemLocation;
	}

	/**
	 * Creates MVR Code representation to be parsed
	 * 
	 * @return
	 */
	public MVRParser getAsMVR() {
		return new MVRParser(fileSystemLocation);
	}

	/**
	 * If the MVR File exists on machine
	 * 
	 * @return
	 */
	public boolean existLocal() {
		return isLocal;
	}

	public ArrayList<UUID> getStationUUIDs() {
		return stationUUID;
	}

	public UUID getUuid() {
		return uuid;
	}

	public String getFileName() {
		return fileName;
	}
	
	public int getFileSize() {
		return fileSize;
	}
	
	/**
	 * Sets the file name used to store file on disk, only works when fileName is Blank
	 * 
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		if(fileName.isBlank())
			this.fileName = fileName;
	}

	public void setLocal() {
		this.isLocal = true;
	}

	public void writeToJson(JsonObject object) {
		object.addProperty("FileSize", fileSize);
		object.addProperty("FileUUID", uuid.toString());
		if(fileName != null)
			object.addProperty("FileName", fileName);
		object.addProperty("Comment", comment);
	}

}
