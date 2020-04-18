package com.xzcode.ggserver.test.netty.udp;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class UdpClient {
	public static void main(String[] args) {
		EventLoopGroup group = new NioEventLoopGroup();
	    try {
	        Bootstrap b = new Bootstrap();
	        b.group(group).channel(NioDatagramChannel.class)
	                .handler(new UdpClientHandler());
	        Channel ch = b.bind(7398).sync().channel();
	        //向目标端口发送信息
	        ch.writeAndFlush(new DatagramPacket(ch.alloc().buffer().writeBytes("hello".getBytes(Charset.forName("utf-8"))), new InetSocketAddress("127.0.0.1", 7397)));
	        ch.closeFuture().await();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        group.shutdownGracefully();
	    }
	}
}
