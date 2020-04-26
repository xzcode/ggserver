package com.xzcode.ggserver.game.monitor.server;

import com.xzcode.ggserver.game.monitor.common.message.req.AuthReq;
import com.xzcode.ggserver.game.monitor.common.message.req.GameServerRegisterReq;
import com.xzcode.ggserver.game.monitor.common.message.req.GameDataUpdateReq;
import com.xzcode.ggserver.game.monitor.server.config.GameMonitorServerConfig;
import com.xzcode.ggserver.game.monitor.server.constant.GameMonitorServerSessionKeys;
import com.xzcode.ggserver.game.monitor.server.events.ConnActiveEventListener;
import com.xzcode.ggserver.game.monitor.server.events.ConnCloseEventListener;
import com.xzcode.ggserver.game.monitor.server.handler.AuthReqHandler;

import xzcode.ggserver.core.common.constant.ProtocolTypeConstants;
import xzcode.ggserver.core.common.event.GGEvents;
import xzcode.ggserver.core.common.executor.thread.GGThreadFactory;
import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.core.common.utils.logger.GGLoggerUtil;
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
		
		//添加认证过滤器
		ggServer.addRequestFilter(messageData -> {
			if (messageData.getAction().equals(AuthReq.ACTION_ID)) {
				return true;
			}
			
			GGSession session = messageData.getSession();
			Boolean auth = session.getAttribute(GameMonitorServerSessionKeys.IS_AUTHED, Boolean.class);
			if (auth != null && auth) {
				return true;
			}
			GGLoggerUtil.getLogger(this).warn("Session Not Authed!");
			return false;
		});
		
		//注册认证处理器
		ggServer.onMessage(AuthReq.ACTION_ID, new AuthReqHandler(config));
		
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
