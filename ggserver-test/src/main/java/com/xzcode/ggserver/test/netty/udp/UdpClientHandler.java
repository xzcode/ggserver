package com.xzcode.ggserver.test.netty.udp;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

public class UdpClientHandler extends SimpleChannelInboundHandler<DatagramPacket>{

	
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
		ByteBuf buf = msg.content();
		String str = buf.toString(Charset.forName("utf-8"));
		System.out.println("message from server:" + str);
		
	}

}
