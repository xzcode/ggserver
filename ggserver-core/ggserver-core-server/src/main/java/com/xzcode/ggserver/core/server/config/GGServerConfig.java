package com.xzcode.ggserver.core.server.config;

import java.util.concurrent.ThreadFactory;

import com.xzcode.ggserver.core.common.config.GGConfig;
import com.xzcode.ggserver.core.common.event.GGEvents;
import com.xzcode.ggserver.core.common.executor.thread.GGThreadFactory;
import com.xzcode.ggserver.core.common.message.pingpong.GGPingPongServerEventListener;
import com.xzcode.ggserver.core.common.message.pingpong.GGPingRequestHandler;
import com.xzcode.ggserver.core.common.message.pingpong.model.GGPing;

import io.netty.channel.nio.NioEventLoopGroup;

public class GGServerConfig extends GGConfig{
	
	protected String	serverName = "GGServer";
	
	protected String	host = "0.0.0.0";
	
	protected int 		port = 9999;
	
	protected NioEventLoopGroup bossGroup;
	
	protected ThreadFactory bossGroupThreadFactory;
	
	
	@Override
	public void init() {
		
		if (bossGroupThreadFactory == null) {
			bossGroupThreadFactory = new GGThreadFactory("gg-boss-", false);
		}
		
		if (bossGroup == null) {
			bossGroup = new NioEventLoopGroup(getBossThreadSize(),bossGroupThreadFactory);				
		}
		
		super.init();
		

		if (isPingPongEnabled()) {
			requestMessageManager.onMessage(GGPing.ACTION_ID, new GGPingRequestHandler(this));			
			eventManager.addEventListener(GGEvents.Idle.ALL, new GGPingPongServerEventListener(this));
		}
		
	}
	public NioEventLoopGroup getBossGroup() {
		return bossGroup;
	}
	public void setBossGroup(NioEventLoopGroup bossGroup) {
		this.bossGroup = bossGroup;
	}
	public ThreadFactory getBossGroupThreadFactory() {
		return bossGroupThreadFactory;
	}
	public void setBossGroupThreadFactory(ThreadFactory bossGroupThreadFactory) {
		this.bossGroupThreadFactory = bossGroupThreadFactory;
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	
}
