package com.xzcode.ggserver.game.monitor.server;

import com.xzcode.ggserver.game.monitor.common.message.req.AuthReq;
import com.xzcode.ggserver.game.monitor.common.message.req.DiscoveryServiceListReq;
import com.xzcode.ggserver.game.monitor.common.message.req.DiscoveryServiceUpdateReq;
import com.xzcode.ggserver.game.monitor.server.config.GameMonitorServerConfig;
import com.xzcode.ggserver.game.monitor.server.events.ConnActiveEventListener;
import com.xzcode.ggserver.game.monitor.server.events.ConnCloseEventListener;
import com.xzcode.ggserver.game.monitor.server.handler.AuthReqHandler;

import xzcode.ggserver.core.common.constant.ProtocolTypeConstants;
import xzcode.ggserver.core.common.event.GGEvents;
import xzcode.ggserver.core.common.executor.thread.GGThreadFactory;
import xzcode.ggserver.core.server.IGGServer;
import xzcode.ggserver.core.server.config.GGServerConfig;
import xzcode.ggserver.core.server.impl.GGServer;

public class GameMonitorServer {
	
	private GameMonitorServerConfig config;
	
	
	
	public GameMonitorServer(GameMonitorServerConfig config) {
		super();
		this.config = config;
	}

	public void start() {
		
		GGServerConfig ggConfig = new GGServerConfig();
		ggConfig.setPingPongEnabled(true);
		ggConfig.setPrintPingPongInfo(config.isPrintPingPongInfo());
		ggConfig.setProtocolType(ProtocolTypeConstants.TCP);
		ggConfig.setPort(config.getPort());
		ggConfig.setBossGroupThreadFactory(new GGThreadFactory("monitor-boss-", false));
		ggConfig.setWorkerGroupThreadFactory(new GGThreadFactory("monitor-worker-", false));
		ggConfig.init();
		IGGServer ggServer = new GGServer(ggConfig);
		
		ggServer.addEventListener(GGEvents.Connection.OPENED, new ConnActiveEventListener(config));
		
		ggServer.addEventListener(GGEvents.Connection.CLOSED, new ConnCloseEventListener(config));
		
		ggServer.onMessage(AuthReq.ACTION, new AuthReqHandler(config));
		
		ggServer.start();
		
		config.setServer(ggServer);
		
	}
	
	public void setConfig(GameMonitorServerConfig config) {
		this.config = config;
	}
	
	public GameMonitorServerConfig getConfig() {
		return config;
	}
	
}
