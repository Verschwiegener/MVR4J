package de.verschwiegener.xchange;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Map;
import java.util.function.Predicate;

import javax.jmdns.JmDNS;
import javax.jmdns.JmmDNS;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
/**
 * MDNS Wrapper Class
 * 
 * @author julius
 *
 */
public class MDNSService {

	private static JmDNS mDns;

	static {
		try {
			mDns = JmDNS.create(getInetAddress());
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*try {
			mDns = JmmDNS.Factory.getInstance();
		}catch(Exception e) {
			
		}*/
	}

	public static ServiceInfo registerMDNS(MDNSServiceData data) throws IOException {
		InetAddress netInterface = getInetAddress();

		byte[] hardwareAddress = NetworkInterface.getByInetAddress(netInterface).getHardwareAddress();

		String macAddress = hardwareAddressBytesToString(hardwareAddress);
		
		ServiceInfo mDNSService = ServiceInfo.create(data.getServiceType(macAddress), data.getName(macAddress), "",
				data.getPort(), 0, 0, data.createProperties(macAddress));
		mDns.registerService(mDNSService);

		return mDNSService;
	}
	
	private static InetAddress getInetAddress() throws SocketException {
		return NetworkInterface.networkInterfaces().filter(networkInterfaceFilter())
				.flatMap(NetworkInterface::inetAddresses).filter(inetAddressFilter()).findFirst().orElse(null);
	}

	/**
	 * Unregisters all mDns Services
	 */
	public static void unregisterAllServices() {
		mDns.unregisterAllServices();
	}

	/**
	 * Unregisters mDns Service
	 * 
	 * @param info
	 */
	public static void unregisterService(ServiceInfo info) {
		mDns.unregisterService(info);
	}

	/**
	 * Adds Service Listener which gets called when new Devices are Discovered
	 * 
	 * @param service
	 * @param listener
	 */
	public static void addServiceListener(String service, ServiceListener listener) {
		mDns.addServiceListener(service, listener);
	}

	/**
	 * Removes Service Listener from mDns
	 * 
	 * @param service
	 * @param listener
	 */
	public static void removeServiceListener(String service, ServiceListener listener) {
		mDns.removeServiceListener(service, listener);
	}

	public static void shutdown() throws IOException {
		mDns.close();
	}

	public interface MDNSServiceData {

		public String getServiceType(String macAddress);

		public String getName(String macAddress);

		public int getPort();

		public Map<String, String> createProperties(String macAddress);

	}

	private static Predicate<NetworkInterface> networkInterfaceFilter() {
		return networkInterface -> {
			try {
				return !networkInterface.isLoopback() && !networkInterface.isPointToPoint() && networkInterface.isUp();
			} catch (SocketException e) {
				return false;
			}
		};
	}

	private static Predicate<InetAddress> inetAddressFilter() {
		return inetAddress -> inetAddress instanceof Inet4Address /* || inetAddress instanceof Inet6Address */;
	}

	private static String hardwareAddressBytesToString(byte[] mac) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mac.length; i++) {
			sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? ":" : ""));
		}
		return sb.toString().toUpperCase();
	}

}
