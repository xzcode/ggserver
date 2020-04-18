package com.xzcode.ggserver.core.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzcode.ggserver.core.common.config.GGConfig;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * 混合tcp与websocket 初始化处理器
 * 
 * @author zai
 * 2019-06-15 14:31:58
 */
public class MixedSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
	
	private static final Logger logger = LoggerFactory.getLogger(MixedSocketChannelInitializer.class);
	
	private GGConfig config;
	
	public MixedSocketChannelInitializer() {
	}
	
	public MixedSocketChannelInitializer(GGConfig config) {
		this.config = config;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
	   	ch.pipeline().addLast(new SocketSelectHandler(config));
	}


	public GGConfig getConfig() {
		return config;
	}
	
	public void setConfig(GGConfig config) {
		this.config = config;
	}

}
