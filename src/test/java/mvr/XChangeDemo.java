package mvr;

import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import de.verschwiegener.mvr.util.MVRUtil;
import de.verschwiegener.xchange.ProtocolMode;
import de.verschwiegener.xchange.XChange;
import de.verschwiegener.xchange.XChangeListener;
import de.verschwiegener.xchange.util.MVRFile;
import de.verschwiegener.xchange.util.Station;

public class XChangeDemo {
	
	public static void main(String[] args) throws IOException, InterruptedException, CertificateException {
		//Set MVRExtractFolder, if parsing as MVR is needed
		MVRUtil.mvrExtractFolder = new File("Parent Folder into which mvr files get extracted");
		
		XChange xchange = new XChange(ProtocolMode.TCP, "MVR4J", null);
		xchange.start(new XChangeListener() {
			
			@Override
			public void stationLeave(Station station) {
			}
			
			@Override
			public void stationAdded(Station station) {
				//Connect to Station
				station.connect();
			}
			
			@Override
			public void newMVRFile(MVRFile file) {
				
				//Request file
				CompletableFuture<Void> fileRequest = file.requestFile();
				fileRequest.whenComplete((result, ex) -> {
					
				});
				//Parse File
				MVRUtil mvr = file.getAsMVR();
			}

			@Override
			public void xChangeError(String packet, String message) {
			}
		});
		
		
		xchange.commitFile(new MVRFile(null, "New File"));
		
		xchange.shutdown();
		
	}
	
	
	

}
