package de.verschwiegener.xchange;

import de.verschwiegener.xchange.util.MVRFile;
import de.verschwiegener.xchange.util.Station;

/**
 * 
 * @author julius
 *
 */
public interface XChangeListener {
	
	
	public void stationAdded(Station station);
	
	public void stationLeave(Station station);
	
	public void newMVRFile(MVRFile file);

}
