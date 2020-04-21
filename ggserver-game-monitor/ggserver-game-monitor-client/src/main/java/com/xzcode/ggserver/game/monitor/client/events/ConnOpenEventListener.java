package com.xzcode.ggserver.game.monitor.client.events;

import com.xzcode.ggserver.game.monitor.client.config.GameMonitorClientConfig;
import com.xzcode.ggserver.game.monitor.common.message.req.AuthReq;
import com.xzcode.ggserver.game.monitor.common.service.ServiceInfo;

import xzcode.ggserver.core.common.event.IEventListener;
import xzcode.ggserver.core.common.event.model.EventData;
import xzcode.ggserver.core.common.session.GGSession;

public class ConnOpenEventListener implements IEventListener<Void>{

	private GameMonitorClientConfig config;
	
	public ConnOpenEventListener(GameMonitorClientConfig config) {
		this.config = config;
	}

	@Override
	public void onEvent(EventData<Void> e) {
		config.getRegistryManager().getRegistriedInfo().setActive(true);
		
		//打开连接，发送注册请求
		//发送注册请求
		GGSession session = e.getSession();
		config.setSession(session);
		
		AuthReq req = new AuthReq();
		req.setAuthToken(config.getAuthToken());
		ServiceInfo serviceInfo = new ServiceInfo();
		
		serviceInfo.setRegion(config.getRegion());
		serviceInfo.setZone(config.getZone());
		serviceInfo.setServiceId(config.getServiceId());
		serviceInfo.setServiceName(config.getServiceName());
		serviceInfo.setCustomData(config.getCustomData());
		
		req.setServiceInfo(serviceInfo);
		
		session.send(req);
	}

}
