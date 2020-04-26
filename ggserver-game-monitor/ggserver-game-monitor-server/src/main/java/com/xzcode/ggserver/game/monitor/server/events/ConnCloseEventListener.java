package com.xzcode.ggserver.game.monitor.server.events;

import com.xzcode.ggserver.game.monitor.server.config.GameMonitorServerConfig;

import xzcode.ggserver.core.common.event.IEventListener;
import xzcode.ggserver.core.common.event.model.EventData;

public class ConnCloseEventListener implements IEventListener<Void>{
	
	private GameMonitorServerConfig config;

	public ConnCloseEventListener(GameMonitorServerConfig config) {
		super();
		this.config = config;
	}

	public void setConfig(GameMonitorServerConfig config) {
		this.config = config;
	}

	@Override
	public void onEvent(EventData<Void> eventData) {
		//连接关闭
		
		
		
	}

	
}
