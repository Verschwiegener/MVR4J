package de.verschwiegener.xchange.packet;

import io.netty.buffer.ByteBuf;

/**
 * 
 * @author julius
 * 
 */
public abstract class FilePacket implements Packet{

	
	public abstract void parsePacket(ByteBuf buffer);
	
	@Override
	public int getPackageType() {
		return 1;
	}
	
}
