package com.xzcode.ggserver.test.netty.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class UdpServer {
	public static void main(String[] args) {
	    try {
	        Bootstrap b = new Bootstrap();
	        EventLoopGroup group = new NioEventLoopGroup();
	        b.group(group)
	                .channel(NioDatagramChannel.class)
	                .option(ChannelOption.SO_BROADCAST, true)
	                .handler(new UdpServerHandler());
 
	        b.bind(7397).sync().channel().closeFuture().await();
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	}

}
