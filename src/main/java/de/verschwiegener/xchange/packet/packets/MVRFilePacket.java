package de.verschwiegener.xchange.packet.packets;

import java.io.IOException;

import com.google.common.io.Files;

import de.verschwiegener.xchange.XChange;
import de.verschwiegener.xchange.packet.FilePacket;
import io.netty.buffer.ByteBuf;

public class MVRFilePacket extends FilePacket{
	//TODO Support Streaming the MVR File Data directly into file and not save it first into a buffer
	
	private ByteBuf fileData;
	
	public MVRFilePacket(ByteBuf fileData) {
		this.fileData = fileData;
	}
	
	
	public MVRFilePacket() {
	}
	
	@Override
	public void parsePacket(ByteBuf buffer) {
		try {
			/*//Write Buffer data into array
			byte[] data = new byte[buffer.readableBytes()];
			buffer.getBytes(buffer.readerIndex(), data);*/
			Files.write(buffer.array(), XChange.instance.currentReceiveFile.getFilesystemLocation());
			
			/**
			 * Appends Data to File
			 */
			/**FileOutputStream stream = new FileOutputStream(XChange.instance.currentReceiveFile.getFilesystemLocation(), true);
			stream.write(buffer.array());
			stream.flush();
			stream.close();*/
		} catch (IOException e) {
			e.printStackTrace();
			//TODO Send S04 Error packet
		}
	}

	@Override
	public ByteBuf writePacket() {
		return fileData;
	}

}
