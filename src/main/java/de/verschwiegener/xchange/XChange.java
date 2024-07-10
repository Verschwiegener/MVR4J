package de.verschwiegener.xchange;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

import de.verschwiegener.xchange.MDNSService.MDNSServiceData;
import de.verschwiegener.xchange.packet.packets.C03PacketCommit;
import de.verschwiegener.xchange.tcp.TCPServer;
import de.verschwiegener.xchange.util.Connection;
import de.verschwiegener.xchange.util.MVRFile;
import de.verschwiegener.xchange.util.Station;
import de.verschwiegener.xchange.util.Version;

public class XChange {

	public static XChange instance;

	/**
	 * Directory to save and load MVR Files to and from
	 */
	public File mvrWorkingDirectory;

	/**
	 * Describing local Station
	 */
	public Station station;

	/**
	 * Mode which Protocol is used for MVR-xchange
	 */
	public ProtocolMode mode;

	/**
	 * Metadata of the File currently being received, is being set in MVR_REQUEST Packet
	 */
	public MVRFile currentReceiveFile;
	
	public int serverPort;
	
	public XChangeListener listener;

	/**
	 * ArrayList to hold all Stations
	 */
	private ArrayList<Station> stations = new ArrayList<Station>();

	/**
	 * ArrayList holding all MVR Files known
	 */
	private ArrayList<MVRFile> files = new ArrayList<MVRFile>();

	private String mvrGroup;

	private TCPServer server;
	
	private final String mDnsService = "_mvrxchange._tcp.local.";

	/**
	 * Create MVR XChange, call start mDNS and given ProtocolMode Connection 
	 * 
	 * @param mode
	 * @param stationName
	 * @param mvrWorkingDirectory
	 */
	public XChange(ProtocolMode mode, String stationName, File mvrWorkingDirectory) {
		this(mode, 4568, stationName, "MVR4J", "", UUID.randomUUID(), mvrWorkingDirectory);
	}
	/**
	 * Create MVR XChange, call start mDNS and given ProtocolMode Connection 
	 * 
	 * @param mode
	 * @param serverPort
	 * @param stationName
	 * @param provider
	 * @param mvrGroup
	 * @param stationUUID
	 * @param mvrWorkingDirectory
	 */
	public XChange(ProtocolMode mode, int serverPort, String stationName, String provider, String mvrGroup,
			UUID stationUUID, File mvrWorkingDirectory) {
		instance = this;

		this.serverPort = serverPort;
		this.mode = mode;
		this.mvrGroup = mvrGroup;
		station = new Station(stationUUID, stationName, provider, Version.MVR_LATEST, null);
		this.mvrWorkingDirectory = mvrWorkingDirectory;
	}

	public void start(XChangeListener xchangeListener) throws IOException, InterruptedException {
		this.listener = xchangeListener;
		if (mode == ProtocolMode.TCP) {

			server = new TCPServer();
			server.start();

			MDNSService.registerMDNS(new MDNSServiceData() {

				@Override
				public String getServiceType(String macAddress) {
					return getServiceString();
				}

				@Override
				public int getPort() {
					return serverPort;
				}

				@Override
				public String getName(String macAddress) {
					return station.getName();
				}

				@Override
				public Map<String, String> createProperties(String macAddress) {
					HashMap<String, String> properties = new HashMap<String, String>();
					properties.put("StationName", station.getName());
					properties.put("StationUUID", station.getUuid().toString());
					return properties;
				}
			});

			ServiceListener listener = new ServiceListener() {

				@Override
				public void serviceResolved(ServiceEvent event) {
					ServiceInfo info = event.getInfo();

					if (info.getName().equals(station.getName()))
						return;

					String stationUUID = info.getPropertyString("StationUUID");
					String stationName = info.getPropertyString("StationName");

					if (stationUUID == null || stationName == null)
						return;

					UUID uuid = UUID.fromString(stationUUID);
					// Check if station is known
					if (getStationByUUID(UUID.fromString(stationUUID)) != null)
						return;

					InetAddress address = info.getInet4Addresses()[0];

					CompletableFuture<Void> future = new CompletableFuture<Void>();

					// Connect To Peer
					Connection connection = new Connection(new InetSocketAddress(address, info.getPort()));
					connection.connectTo(future);

					future.whenComplete((result, ex) -> {
						addStation(new Station(uuid, stationName, null, null, connection));
					});

				}

				@Override
				public void serviceRemoved(ServiceEvent event) {
					String stationUUID = event.getInfo().getPropertyString("StationUUID");
					if (stationUUID == null)
						return;
					// Removes Station if mDns Service is Removed
					Station stationToRemove = getStationByUUID(UUID.fromString(stationUUID));
					stationToRemove.getConnection().shutdown();
					removeStation(stationToRemove);
				}

				@Override
				public void serviceAdded(ServiceEvent event) {
				}
			};

			MDNSService.addServiceListener(getServiceString(), listener);
		}
		
	}

	/**
	 * Shuts down MVR XChange
	 * 
	 * @throws IOException
	 */
	public void shutdown() throws IOException {
		stations.forEach(station -> station.getConnection().shutdown());
		server.shutdown();
		MDNSService.unregisterAllServices();
		MDNSService.shutdown();
	}

	/**
	 * Adds new Station, overwrites old one if it exist
	 * 
	 * @param station
	 */
	public void addStation(Station station) {
		if(stations.contains(station))
			return;
		stations.add(station);
		listener.stationAdded(station);
	}

	/**
	 * Shuts down Station Connection and removes it
	 * 
	 * @param station
	 */
	public void removeStation(Station station) {
		station.getConnection().shutdown();
		stations.remove(station);
	}

	public Station getStationByUUID(UUID uuid) {
		return stations.stream().filter(station -> station.getUuid().equals(uuid)).findFirst().orElse(null);
	}

	/**
	 * Returns MVRFile by UUID
	 * 
	 * @param uuid
	 * @return
	 */
	public MVRFile getFileByUUID(UUID uuid) {
		return files.stream().filter(files -> files.getUuid().equals(uuid)).findFirst().orElse(null);
	}

	/**
	 * Returns MVRFile By FileName
	 * 
	 * @param name
	 * @return
	 */
	public MVRFile getFileByName(String name) {
		return files.stream().filter(files -> files.getFileName().equals(name)).findFirst().orElse(null);
	}
	
	/**
	 * Commits new File to all Connected XChange Clients
	 * 
	 * @param file
	 */
	public void commitFile(MVRFile file) {
		files.add(file);
		stations.forEach(station -> station.getConnection().sendPacket(new C03PacketCommit(file, new Station[] {})));
	}

	/**
	 * Checks if File is already known, if not add to known files
	 * 
	 * @param file
	 */
	public void registerFile(MVRFile file) {
		MVRFile testFile = getFileByUUID(file.getUuid());
		if (testFile != null) {
			testFile.getStationUUID().addAll(file.getStationUUID());
			return;
		}
		files.add(file);
		listener.newMVRFile(file);
	}

	private String getServiceString() {
		return (mvrGroup == null || mvrGroup.isEmpty()) ? mDnsService : mvrGroup + "." + mDnsService;
	}

	public ArrayList<MVRFile> getFiles() {
		return files;
	}
	
	public ArrayList<Station> getStations() {
		return stations;
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		XChange xchange = new XChange(ProtocolMode.TCP, "Test45", null);

		xchange.start(new XChangeListener() {
			
			@Override
			public void stationLeave(Station station) {
				
			}
			
			@Override
			public void stationAdded(Station station) {
				
			}
			
			@Override
			public void newMVRFile(MVRFile file) {
				
			}
		});
		

//		Thread.sleep(10000);

//		xchange2.start();

//		Thread.sleep(10000);

//		xchange3.start();

		//Thread.sleep(10000);

		//xchange.shutdown();
		// xchange2.shutdown();
		// xchange3.shutdown();
	}

}
