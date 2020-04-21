package com.xzcode.ggserver.game.monitor.client.handler;

import com.xzcode.ggserver.game.monitor.client.config.GameMonitorClientConfig;
import com.xzcode.ggserver.game.monitor.common.message.resp.DiscoveryServiceUnregisterResp;
import com.xzcode.ggserver.game.monitor.common.service.ServiceManager;

import xzcode.ggserver.core.common.message.request.Request;
import xzcode.ggserver.core.common.message.request.action.IRequestMessageHandler;

/**
 * 客户端注册请求处理
 * 
 * 
 * @author zai
 * 2019-10-04 14:29:53
 */
public class ServiceUnregisterRespHandler implements IRequestMessageHandler<DiscoveryServiceUnregisterResp>{
	
	private GameMonitorClientConfig config;
	

	public ServiceUnregisterRespHandler(GameMonitorClientConfig config) {
		super();
		this.config = config;
	}



	@Override
	public void handle(Request<DiscoveryServiceUnregisterResp> request) {
		DiscoveryServiceUnregisterResp resp = request.getMessage();
		String serviceName = resp.getServiceName();
		String serviceId = resp.getServiceId();
		ServiceManager serviceManager = config.getServiceManager();
		serviceManager.removeService(serviceName, serviceId);
	}

	

}
