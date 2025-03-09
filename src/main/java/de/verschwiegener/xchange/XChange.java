package de.verschwiegener.xchange;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import javax.net.ssl.SSLException;

import de.verschwiegener.mvr.util.MVRParser;
import de.verschwiegener.xchange.MDNSService.MDNSServiceData;
import de.verschwiegener.xchange.packet.packets.C01PacketJoin;
import de.verschwiegener.xchange.packet.packets.C02PacketLeave;
import de.verschwiegener.xchange.packet.packets.C03PacketCommit;
import de.verschwiegener.xchange.tcp.TCPServer;
import de.verschwiegener.xchange.util.Connection;
import de.verschwiegener.xchange.util.MVRFile;
import de.verschwiegener.xchange.util.Station;
import de.verschwiegener.xchange.util.Version;
import de.verschwiegener.xchange.websocket.WebsocketServer;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

/**
 * Class Managing all XChange related things
 * 
 * @author julius
 *
 */
public class XChange {

	public static XChange instance;

	/**
	 * Parent Folder into which all received MVR Files get saved
	 */
	public final File mvrWorkingDirectory;

	/**
	 * Describes local Station
	 */
	public final Station station;

	/**
	 * WebSocket Server Station
	 */
	public Station webSocketStation;

	/**
	 * Mode which Protocol is used for MVR-xchange
	 */
	public final ProtocolMode mode;

	/**
	 * Metadata of the File currently being received, is being set in MVR_REQUEST
	 * Packet
	 */
	public MVRFile currentReceiveFile;

	public final int serverPort;

	public XChangeListener listener;

	/**
	 * Websocket Client SSL Context
	 */
	public SslContext sslCtx = null;

	/**
	 * WebSocketServer Address to connect to when launching xChange as WebSocket
	 * Client
	 */
	public URI websocketURI;

	/**
	 * List holding all Discovered UUIDs where the connection is not yet
	 * established, to prevent connecting to the same station twice
	 */
	private ArrayList<UUID> discoveredStations = new ArrayList<UUID>();

	/**
	 * ArrayList to hold all Stations
	 */
	private ArrayList<Station> stations = new ArrayList<Station>();

	/**
	 * ArrayList holding all MVR Files known
	 */
	private ArrayList<MVRFile> files = new ArrayList<MVRFile>();

	private String mvrGroup;

	private XChangeServer server;

	private boolean ssl;

	private File certificate;

	private File privateKey;

	private final String mDnsService = "_mvrxchange._tcp.local.";

	/**
	 * WebSocket Server Constructor
	 * 
	 * @param stationName
	 * @param mvrWorkingDirectory
	 * @param stationUUID
	 */
	public XChange(String stationName, File mvrWorkingDirectory, int port) {
		this(stationName, mvrWorkingDirectory, port, UUID.randomUUID(), "MVR4J");
	}

	/**
	 * WebSocket Server Constructor
	 * 
	 * @param stationName
	 * @param mvrWorkingDirectory
	 * @param stationUUID
	 * @param serverPort
	 * @param provider
	 * @param mvrGroup
	 */
	public XChange(String stationName, File mvrWorkingDirectory, int serverPort, UUID stationUUID, String provider) {
		this(ProtocolMode.WEBSOCKET_SERVER, serverPort, stationName, provider, "Default", stationUUID,
				mvrWorkingDirectory);
	}

	/**
	 * WebSocket Client Constructor
	 * 
	 * @param stationName
	 * @param mvrWorkingDirectory
	 * @param stationUUID
	 * @param address
	 */
	public XChange(String stationName, File mvrWorkingDirectory, URI uri) {
		this(stationName, mvrWorkingDirectory, uri, UUID.randomUUID(), "MVR4J");
	}

	/**
	 * WebSocket Client Constructor
	 * 
	 * @param stationName
	 * @param mvrWorkingDirectory
	 * @param stationUUID
	 * @param serverPort
	 * @param provider
	 * @param mvrGroup
	 * @param address
	 */
	public XChange(String stationName, File mvrWorkingDirectory, URI uri, UUID stationUUID, String provider) {
		this(ProtocolMode.WEBSOCKET_CLIENT, 4568, stationName, provider, "Default", stationUUID, mvrWorkingDirectory);
		this.websocketURI = uri;
	}

	/**
	 * mDNS Client Constructor
	 * 
	 * @param stationName
	 * @param mvrWOrkingDirectory
	 */
	public XChange(String stationName, File mvrWorkingDirectory) {
		this(stationName, mvrWorkingDirectory, UUID.randomUUID(), "MVR4J", "Default");
	}

	/**
	 * mDNS Client Constructor
	 * 
	 * @param stationName
	 * @param mvrWorkingDirectory
	 * @param stationUUID
	 * @param provider
	 * @param mvrGroup
	 */
	public XChange(String stationName, File mvrWorkingDirectory, UUID stationUUID, String provider, String mvrGroup) {
		this(ProtocolMode.mDNS, 4568, stationName, provider, mvrGroup, stationUUID, mvrWorkingDirectory);
	}

	/**
	 * Create MVR XChange
	 * 
	 * @param mode                Network Protocol to use
	 * @param serverPort          Port the Server should use
	 * @param stationName         Name of the Station and mDNS
	 * @param provider            Name of the XChange Provider
	 * @param mvrGroup            Name of the XChange Group to join
	 * @param stationUUID         UUID of the Station, should be persistent across
	 *                            multiple start-ups of the same software on the
	 *                            same computer
	 * @param mvrWorkingDirectory Parent Folder into which all received MVR Files
	 *                            get saved
	 */
	private XChange(ProtocolMode mode, int serverPort, String stationName, String provider, String mvrGroup,
			UUID stationUUID, File mvrWorkingDirectory) {
		instance = this;

		this.serverPort = serverPort;
		this.mode = mode;
		this.mvrGroup = mvrGroup;
		station = new Station(stationUUID, stationName, provider, Version.MVR_LATEST, null);
		this.mvrWorkingDirectory = mvrWorkingDirectory;
		mvrWorkingDirectory.mkdirs();
	}

	/**
	 * Starts XChange Server
	 * 
	 * @param xchangeListener
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws CertificateException
	 */
	public void start(XChangeListener xchangeListener) {
		System.out.println("Start: " + station.getUUID());
		this.listener = xchangeListener;
		switch (mode) {
		case mDNS: {
			// Throws Interrupt Exception
			server = new TCPServer();
			try {
				server.start();
			} catch (InterruptedException | SSLException | CertificateException e) {
				try {
					server.shutdown();
				} catch (InterruptedException e2) {
				}
				e.printStackTrace();
				listener.xChangeError("SERVER_STARTUP", "Could not start " + mode + " Server");
				return;
			}

			// Throws IOException
			try {
				MDNSService.registerMDNS(new MDNSServiceData() {

					@Override
					public String getServiceType(String macAddress) {
						return mDnsService;
					}

					@Override
					public int getPort() {
						return serverPort;
					}

					@Override
					public String getName(String macAddress) {
						//TODO FIx MDNS Name and XChange Group
						//return station.getName().replace(" ", "_") + "." + mvrGroup;
						//return station.getName();
						return mvrGroup;
					}

					@Override
					public Map<String, String> createProperties(String macAddress) {
						HashMap<String, String> properties = new HashMap<String, String>();
						properties.put("StationName", station.getName());
						properties.put("StationUUID", station.getUUID().toString());
						return properties;
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
				listener.xChangeError("MDNS_STARTUP", "Could not register mDNS Entry");
				return;
			}

			MDNSService.addServiceListener(mDnsService, createServiceListener());
			break;
		}
		case WEBSOCKET_CLIENT: {
			String host;
			int port;
			try {
				// Parse WebSocket String
				String scheme = websocketURI.getScheme() == null ? "ws" : websocketURI.getScheme();
				host = websocketURI.getHost() == null ? "127.0.0.1" : websocketURI.getHost();
				if (websocketURI.getPort() == -1) {
					if ("ws".equalsIgnoreCase(scheme)) {
						port = 80;
					} else if ("wss".equalsIgnoreCase(scheme)) {
						port = 443;
					} else {
						port = -1;
					}
				} else {
					port = websocketURI.getPort();
				}
				if (!"ws".equalsIgnoreCase(scheme) && !"wss".equalsIgnoreCase(scheme)) {
					listener.xChangeError("SERVER_STARTUP", "Invalid Address: " + websocketURI);
					return;
				}
				final boolean ssl = "wss".equalsIgnoreCase(scheme);
				if (ssl) {
					// TODO Secure SSLContext
					sslCtx = SslContextBuilder.forClient().build();
				}
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

			webSocketStation = new Station();
			// Connect to WebSocket Server
			Connection connection = new Connection(new InetSocketAddress(host, port));
			CompletableFuture<Void> future = null;
			try {
				future = connection.connectTo();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			future.whenComplete((result, ex) -> {
				if (ex != null) {
					ex.printStackTrace();
					return;
				}
				connection.sendPacket(new C01PacketJoin());
				webSocketStation.setConnection(connection);
				// Can´t call stationConnect listener because as a WebSocket Client we first
				// only have a connection and not a full station because we only get the UUID
				// stationName etc from the first MVR_JOIN_RET
			});
			break;
		}
		case WEBSOCKET_SERVER: {
			// WebSocket Server
			try {
				// Create Server SSL Context
				SslContext sslContext = null;
				if (ssl) {
					if (privateKey == null || certificate == null) {
						SelfSignedCertificate ssc = new SelfSignedCertificate();
						sslContext = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
					} else {
						sslContext = SslContextBuilder.forServer(certificate, privateKey).build();
					}
				}

				server = new WebsocketServer(ssl, sslContext);

				server.start();
			} catch (InterruptedException | SSLException | CertificateException e) {
				e.printStackTrace();
				try {
					server.shutdown();
				} catch (InterruptedException e2) {
				}
				listener.xChangeError("SERVER_STARTUP", "Could not start " + mode + " Server");
				return;
			}
			break;
		}
		}
	}

	/**
	 * Shuts down MVR XChange
	 * 
	 * @throws IOException
	 */
	public void shutdown() {
		try {
			MDNSService.unregisterAllServices();
			MDNSService.shutdown();
		} catch (IOException e) {
			e.printStackTrace();
		}
		stations.forEach(station -> {
			station.getConnection().sendPacket(new C02PacketLeave());
			station.getConnection().shutdown();
		});
		Connection.shutdownNetty();
		if (server != null) {
			try {
				server.shutdown();
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * 
	 * Adds new Station, overwrites old one if it exist
	 * 
	 * @param station
	 * @implNote This method is for internal use only and may change in future
	 *           versions.
	 */
	public void addStation(Station station) {
		// Remove from Discovered UUIDs
		discoveredStations
				.remove(discoveredStations.stream().filter(d -> d.equals(station.getUUID())).findFirst().orElse(null));
		Station stationOld = getStationByUUID(station.getUUID());
		if (stationOld != null) {
			stations.remove(stationOld);
		}

		stations.add(station);
		listener.stationConnected(station);
	}

	/**
	 * Shuts down Station Connection, Removes it, send MVR_LEAVE and Calls
	 * stationLeave Listener
	 * 
	 * @param station
	 */
	public void removeStation(Station station) {
		station.getConnection().sendPacket(new C02PacketLeave());
		removeStationInternal(station);
	}

	/**
	 * For internal use only!!!
	 * 
	 * Removes station from internal Lists
	 * 
	 * @param station
	 */
	public void removeStationInternal(Station station) {
		station.getConnection().shutdown();
		stations.remove(station);

		listener.stationLeave(station);

		// Remove Station from File Station List
		for (MVRFile file : files) {
			if (file.getStationUUIDs().contains(station.getUUID())) {
				file.getStationUUIDs().remove(station.getUUID());
			}
		}
	}

	/**
	 * Return Station by UUID, null if non found
	 * 
	 * @param uuid UUID of Station
	 * @return
	 * @implNote This method is for internal use only and may change in future
	 *           versions.
	 */
	public Station getStationByUUID(UUID uuid) {
		// In WebSocket Mode every Message goes to webSocketStation
		if (isWebSocketClient())
			return webSocketStation;
		return stations.stream().filter(station -> station.getUUID().equals(uuid)).findFirst().orElse(null);
	}

	/**
	 * 
	 * Returns MVRFile by UUID, null if non found
	 * 
	 * @param uuid UUID of the MVRFile
	 * @implNote This method is for internal use only and may change in future
	 *           versions.
	 */
	public MVRFile getFileByUUID(UUID uuid) {
		return files.stream().filter(files -> files.getUuid().equals(uuid)).findFirst().orElse(null);
	}

	/**
	 * Commits new Local File to all Connected XChange Clients
	 * 
	 * @param file local MVRFile File to commit to other Stations
	 */
	public void commitFile(MVRFile file) {
		file.setLocal();
		files.add(file);
		stations.forEach(station -> station.getConnection().sendPacket(new C03PacketCommit(file, new Station[] {})));
	}

	/**
	 * 
	 * Checks if File from other Stations is already known, adds and calls
	 * MVRFileAvailable listener if it is
	 * 
	 * @param file MVRFile commited by Stations
	 * @implNote This method is for internal use only and may change in future
	 *           versions.
	 */
	public void registerFile(MVRFile file) {
		MVRFile existingFile = getFileByUUID(file.getUuid());
		// If File Exists add Station UUIDs to existing file, to keep track which
		// stations have the file
		if (existingFile != null) {
			existingFile.getStationUUIDs().addAll(file.getStationUUIDs());
			return;
		}
		files.add(file);
		listener.MVRFileAvailable(file);
	}

	public ArrayList<MVRFile> getFiles() {
		return files;
	}

	public ArrayList<Station> getStations() {
		return stations;
	}

	public boolean isWebSocketServer() {
		return mode == ProtocolMode.WEBSOCKET_SERVER;
	}

	public boolean isWebSocketClient() {
		return mode == ProtocolMode.WEBSOCKET_CLIENT;
	}

	public boolean isMDNS() {
		return mode == ProtocolMode.mDNS;
	}

	/**
	 * Setup SSl Communication for WebSocket Server
	 * 
	 * @param certificate File certificate in PEM format
	 * @param privateKey  File private Key in PEM format
	 */
	public void setupSSL(File certificate, File privateKey) {
		this.certificate = certificate;
		this.privateKey = privateKey;
		ssl = true;
	}

	private ServiceListener createServiceListener() {
		return new ServiceListener() {

			@Override
			public void serviceResolved(ServiceEvent event) {
				//System.out.println("Resolved: " + event);
				ServiceInfo info = event.getInfo();

				String stationUUID = info.getPropertyString("StationUUID");
				String stationName = info.getPropertyString("StationName");

				// Check if Station is in Group
				if (!event.getName().equals(mvrGroup)) {
					String testGroup = event.getName().substring(stationName.length() + 1);
					if (!testGroup.equals(mvrGroup)) {
						return;
					}
				}

				if (stationUUID == null || stationName == null)
					return;

				UUID uuid = UUID.fromString(stationUUID);
				// Check if station is known, or is this instance
				if (getStationByUUID(uuid) != null || uuid.compareTo(station.getUUID()) == 0)
					return;
				

				// UUID has been discovered
				if (discoveredStations.stream().filter(du -> du.equals(uuid)).findFirst().orElse(null) != null)
					return;

				discoveredStations.add(uuid);

				InetAddress address = null;

				for (InetAddress ipv4 : info.getInet4Addresses()) {
					if (!ipv4.isLoopbackAddress())
						address = ipv4;
				}

				// If mDNS Entry does not have a valid IPv4 Address
				if (address == null)
					return;

				// Create Connection
				Connection connection = new Connection(new InetSocketAddress(address, info.getPort()));

				Station s = new Station(uuid, stationName, null, null, connection);
				// Call Listener
				XChange.instance.listener.stationDiscovered(s);
			}

			@Override
			public void serviceRemoved(ServiceEvent event) {
				String stationUUID = event.getInfo().getPropertyString("StationUUID");
				if (stationUUID == null)
					return;

				// Potential Station to remove, if not connected
				Station stationToRemove = getStationByUUID(UUID.fromString(stationUUID));

				// Remove Station only if no connection to Station exists, otherwise it would be
				// removed when the Station Leaves
				if (stationToRemove.getConnection().isConnected()) {
					removeStation(stationToRemove);
				}
			}

			@Override
			public void serviceAdded(ServiceEvent event) {
				//System.out.println("Event: " + event);
				/*ServiceInfo info = event.getInfo();
				System.out.println("Added XChange: " + info);
				System.out.println("Info: " + info.getDomain() + " / " + info.getName() + " / " + info.getKey() + " / "
						+ info.getPort() + " / " + info.getQualifiedName() + " / " + info.getServer() + " / "
						+ Arrays.toString(info.getHostAddresses()));

				/*
				 * System.err.println("Info: " + event.getDNS().getServiceInfo(event.getType(),
				 * event.getName()));
				 * 
				 * System.out.println("Byte0: " + info.getTextBytes()[0]);
				 * 
				 * System.out.println("Bytes_ " + new String(info.getTextBytes(),
				 * StandardCharsets.UTF_8));
				 * 
				 * System.err.println("Type: " + event.getInfo().getPort());
				 */

				/*info.getPropertyNames().asIterator().forEachRemaining(key -> {
					// System.out.println("Key: " + key + " / " + info.getPropertyString(key));
				});*/
			}
		};
	}

	public static void main(String[] args) throws IOException, InterruptedException, CertificateException {

		MVRParser.mvrExtractFolder = new File(new File("").getAbsolutePath() + "/MVRExport");

		File mvrWorkingDirectory = new File(new File("").getAbsolutePath() + "/MVRReceive");

		// XChange xchange = new XChange("MVR4J TestClient", mvrWorkingDirectory,
		// UUID.randomUUID(), "MVR4J", "Default");

		URI uri = null;
		try {
			uri = new URI("");
		} catch (Exception e) {
			e.printStackTrace();
		}

		//XChange xchange = new XChange("MVR4J", mvrWorkingDirectory, uri);

		XChange xchange = new XChange("MVR4J", mvrWorkingDirectory);
		
		xchange.commitFile(new MVRFile(new File(new File("").getAbsolutePath() +
		 "/basic_gdtf.mvr"), "Test Demostage"));
		 xchange.commitFile(
		 new MVRFile(new File(new File("").getAbsolutePath() + "/DemoStage_MVR.mvr"),
		 "MA Demostage"));

		xchange.start(new XChangeListener() {

			@Override
			public void stationDiscovered(Station station) {
				// Connect to TCP Mode Client
				System.err.println("Discovered: " + station.getName());
				station.connect();
			}

			@Override
			public void stationConnected(Station station) {
				System.out.println("Connected to Station: " + station.getName() + " / " + station.getUUID());
				xchange.commitFile(
						new MVRFile(new File(new File("").getAbsolutePath() + "/basic_gdtf.mvr"), "Test Demostage"));
			}

			@Override
			public void stationLeave(Station station) {
			}

			@Override
			public void MVRFileAvailable(MVRFile file) {
				System.out.println("File Available");
				file.requestFile();
			}

			@Override
			public void xChangeError(String packet, String message) {
				System.err.println("Error: " + packet + " / " + message);
			}

			@Override
			public void newMVRFileReceived(MVRFile file) {
				System.out.println("New File: " + file);
			}

		});

		//Thread.sleep(50000);
		//System.out.println("Shutdown");
		//xchange.shutdown();
	}

}
