package mvr;

import java.io.IOException;

import de.verschwiegener.xchange.ProtocolMode;
import de.verschwiegener.xchange.XChange;
import de.verschwiegener.xchange.XChangeListener;
import de.verschwiegener.xchange.util.MVRFile;
import de.verschwiegener.xchange.util.Station;

public class XChangeDemo {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		XChange xchange = new XChange(ProtocolMode.TCP, "MVR4J", null);
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
		
		xchange.commitFile(new MVRFile(null, "New File"));
		
		xchange.shutdown();
		
	}
	
	
	

}
