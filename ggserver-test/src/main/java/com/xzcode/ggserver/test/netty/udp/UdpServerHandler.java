package com.xzcode.ggserver.test.netty.udp;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

public class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
		ByteBuf buf = msg.content();
		String str = buf.toString(Charset.forName("utf-8"));
		System.out.println("message from client:" + str);
		Channel channel = ctx.channel();
		//向目标端口发送信息
		channel.writeAndFlush(new DatagramPacket(channel.alloc().buffer().writeBytes("hello2".getBytes(Charset.forName("utf-8"))), new InetSocketAddress("127.0.0.1", 7398)));
	}

}
