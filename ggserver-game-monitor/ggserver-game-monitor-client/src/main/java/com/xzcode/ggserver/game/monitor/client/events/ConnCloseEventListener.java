package com.xzcode.ggserver.game.monitor.client.events;

import com.xzcode.ggserver.game.monitor.client.config.GameMonitorClientConfig;
import com.xzcode.ggserver.game.monitor.common.service.ServiceManager;

import xzcode.ggserver.core.common.event.IEventListener;
import xzcode.ggserver.core.common.event.model.EventData;

public class ConnCloseEventListener implements IEventListener<Void>{
	
	private GameMonitorClientConfig config;
	
	public ConnCloseEventListener(GameMonitorClientConfig config) {
		super();
		this.config = config;
	}


	@Override
	public void onEvent(EventData<Void> eventData) {
		config.getRegistryManager().getRegistriedInfo().setActive(false);
		ServiceManager serviceManager = config.getServiceManager();
		serviceManager.clearAllServices();
		//断开连接，触发重连
		config.getDiscoveryClient().connect();
	}


	
}
