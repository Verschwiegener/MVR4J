module MVR4J {
	requires org.bouncycastle.pkix;
	requires org.bouncycastle.provider;
	requires org.bouncycastle.util;
	requires io.netty.all;
	requires io.netty.buffer;
	requires io.netty.codec;
	requires io.netty.codec.dns;
	requires io.netty.codec.haproxy;
	requires io.netty.codec.http;
	requires io.netty.codec.http2;
	requires io.netty.codec.memcache;
	requires io.netty.codec.mqtt;
	requires io.netty.codec.redis;
	requires io.netty.codec.smtp;
	requires io.netty.codec.socks;
	requires io.netty.codec.stomp;
	requires io.netty.codec.xml;
	requires io.netty.common;
	requires io.netty.handler;
	requires io.netty.handler.proxy;
	requires io.netty.resolver;
	requires io.netty.resolver.dns;
	requires io.netty.transport;
	requires io.netty.transport.epoll.linux.x86_64;
	requires io.netty.transport.unix.common;
	requires io.netty.transport.rxtx;
	requires io.netty.transport.sctp;
	requires io.netty.transport.udt;
	requires com.google.gson;
	requires java.xml.bind;
	requires javax.jmdns;
	requires org.joml;
	
	
	exports de.verschwiegener.xchange;
	
}