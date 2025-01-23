module MVR4J {
	requires org.bouncycastle.pkix;
	requires org.bouncycastle.provider;
	requires org.bouncycastle.util;
	requires io.netty.all;
	requires io.netty.buffer;
	requires io.netty.codec;
	requires io.netty.codec.http;
	requires io.netty.common;
	requires io.netty.handler;
	requires io.netty.transport;
	requires com.google.gson;
	requires java.xml.bind;
	requires javax.jmdns;
	requires org.joml;
	
	
	exports de.verschwiegener.xchange;
	exports de.verschwiegener.xchange.util;
	exports de.verschwiegener.mvr;
	exports de.verschwiegener.mvr.auxData;
	exports de.verschwiegener.mvr.layer;
	exports de.verschwiegener.mvr.layer.type;
	exports de.verschwiegener.mvr.nodes;
	exports de.verschwiegener.mvr.nodes.enums;
	exports de.verschwiegener.mvr.util;
	exports de.verschwiegener.example;
	
}