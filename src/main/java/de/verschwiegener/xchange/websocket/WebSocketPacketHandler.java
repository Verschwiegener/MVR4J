package de.verschwiegener.xchange.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

public class WebSocketPacketHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
		// Handle text frames
		System.out.println("Frame: " + frame);
		if (frame instanceof TextWebSocketFrame) {
			String request = ((TextWebSocketFrame) frame).text();
			System.out.println("Received: " + request);
			ctx.channel().writeAndFlush(new TextWebSocketFrame("Server received: " + request));
		} else if (frame instanceof CloseWebSocketFrame) {
			ctx.channel().close();
		} else if (frame instanceof PingWebSocketFrame) {
			ctx.channel().writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
