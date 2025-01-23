package de.verschwiegener.example;

import java.io.File;
import java.util.UUID;

import de.verschwiegener.xchange.XChange;
import de.verschwiegener.xchange.XChangeListener;
import de.verschwiegener.xchange.util.MVRFile;
import de.verschwiegener.xchange.util.Station;

public class MDNSXChange {
	
	
	public static void main(String[] args) {
		// Name of this Station for other XChange Stations
				String stationName = "WebSocketClient";
				// Folder into which all received MVRs will be saved
				File mvrReceiveFolder = new File(new File("").getAbsolutePath() + "/MVRReceive");
				// MDNS XChange Group
				String group = "TestGroup";
				// UUID for receiving station inside the network. This UUID should be persistent
				// across multiple start-ups of the same software on the same computer
				UUID uuid = UUID.randomUUID();
				// The application name providing MVR Import & Export
				String provider = "MVR4J";

				// Create mDNS XChange with random UUID and default provider and group
				XChange xchange = new XChange(stationName, mvrReceiveFolder);

				// Create mDNS XChange with custom UUID, group and provider
				new XChange(stationName, mvrReceiveFolder, uuid, provider, group);

				// Commit File to all other connected Stations
				xchange.commitFile(new MVRFile(new File(""), "File Changes Comment"));

				// Commit File to all other connected Stations with defined fileName
				xchange.commitFile(new MVRFile(new File(""), "File Name for other stations", "File Changes Comment"));

				// Start XChange Server/Client
				xchange.start(new XChangeListener() {

					@Override
					public void xChangeError(String packet, String message) {
					}

					@Override
					public void stationLeave(Station station) {
						// Station announced it leaves the xChange Group
					}

					@Override
					public void stationConnected(Station station) {
						// Connection to station has been established
					}

					@Override
					public void stationDiscovered(Station station) {
						// New mDNS XChange Station has been discovered

						// Connect to selected Station Group
						station.connect();
					}

					@Override
					public void newMVRFileReceived(MVRFile file) {
						// new MVR File was Received

						// Gets MVR File Parser
						file.getAsMVR();

						// Gets MVR File Location
						file.getFilesystemLocation();
					}

					@Override
					public void MVRFileAvailable(MVRFile file) {
						// A connected Station has commited a new MVR File

						// Request File Download
						file.requestFile();
					}
				});

				// Shutdown MVR-xChange
				xchange.shutdown();
	}

}
