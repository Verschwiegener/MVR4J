package de.verschwiegener.xchange.packet;

import io.netty.buffer.ByteBuf;

/**
 * 
 * @author julius
 *
 */
public interface Packet {
	
	
	int getPackageType();
	
	public abstract ByteBuf writePacket();

}
