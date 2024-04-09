package de.verschwiegener.xchange.packet;

import io.netty.buffer.ByteBuf;

public interface Packet {
	
	
	int getPackageType();
	
	public abstract ByteBuf writePacket();

}
