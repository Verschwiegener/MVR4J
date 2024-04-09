package de.verschwiegener.xchange;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import de.verschwiegener.xchange.packet.UTF8Packet;
import de.verschwiegener.xchange.packet.packets.C01PacketJoin;
import de.verschwiegener.xchange.packet.packets.C02PacketLeave;
import de.verschwiegener.xchange.packet.packets.C03PacketCommit;
import de.verschwiegener.xchange.packet.packets.C04PacketRequest;
import de.verschwiegener.xchange.packet.packets.S01PacketJoin;
import de.verschwiegener.xchange.packet.packets.S02PacketLeave;
import de.verschwiegener.xchange.packet.packets.S03PacketCommit;
import de.verschwiegener.xchange.packet.packets.S04PacketRequest;

public enum PacketRegistry {

	JSON() {
		{
			registerPacket(new C01PacketJoin());
			registerPacket(new C02PacketLeave());
			registerPacket(new C03PacketCommit());
			registerPacket(new C04PacketRequest());
			registerPacket(new S01PacketJoin());
			registerPacket(new S02PacketLeave());
			registerPacket(new S03PacketCommit());
			registerPacket(new S04PacketRequest());
		}
	};

	private final ArrayList<UTF8Packet> packets;

	PacketRegistry() {
		packets = new ArrayList<UTF8Packet>();
	}
	
	protected void registerPacket(UTF8Packet packet) {
		packets.add(packet);
	}

	/**
	 * Returns new Packet by JSONPacketType, or new MVRFilePacket if JSONPacketType
	 * is null
	 * 
	 * @param JSONPacketType
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public UTF8Packet getPacket(String JSONPacketType) throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		UTF8Packet p = JSON.packets.stream().filter(packet -> packet.getPacketType().equals(JSONPacketType))
				.findFirst().orElse(null);
		return p.getClass().getDeclaredConstructor().newInstance();
	}

}
