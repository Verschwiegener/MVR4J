package de.verschwiegener.xchange.packet.packets;

import java.io.IOException;

import com.google.common.io.Files;

import de.verschwiegener.xchange.XChange;
import de.verschwiegener.xchange.packet.FilePacket;
import io.netty.buffer.ByteBuf;

public class MVRFilePacket extends FilePacket{

	@Override
	public void parsePacket(ByteBuf buffer) {
		try {
			Files.write(buffer.array(), XChange.instance.currentSendFile.getFilesystemLocation());
		} catch (IOException e) {
			e.printStackTrace();
			//TODO Send S04 Error packet
		}
	}

	@Override
	public ByteBuf writePacket() {
		return null;
	}

}
