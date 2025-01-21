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
	 * New Station joined
	 * @param station
	 */
	public void stationAvailable(Station station);
	
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
	public void newMVRFile(MVRFile file);
	
	/**
	 * Error occurred in XChange Transmission
	 * @param message
	 */
	public void xChangeError(String packet, String message);

}
