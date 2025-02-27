package de.verschwiegener.xchange;

import de.verschwiegener.xchange.util.MVRFile;
import de.verschwiegener.xchange.util.Station;

/**
 * 
 * @author julius
 *
 */
public interface XChangeListener {
	
	/**
	 * New Station discovered for connection 
	 * Only for mDNS!
	 * @param station
	 */
	public void stationDiscovered(Station station);
	
	/**
	 * Station Connection has been established, station is ready to receive data
	 * @param station
	 */
	public void stationConnected(Station station);
	
	/**
	 * Station Left
	 * @param station
	 */
	public void stationLeave(Station station);
	
	/**
	 * New MVR File available
	 * @param file
	 */
	public void MVRFileAvailable(MVRFile file);
	
	/**
	 * MVR File is received and can be used
	 * 
	 * @param file
	 */
	public void newMVRFileReceived(MVRFile file);
	
	/**
	 * Error occurred in XChange Transmission
	 * @param message
	 */
	public void xChangeError(String packet, String message);

}
