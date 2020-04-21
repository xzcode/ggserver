package com.xzcode.ggserver.game.monitor.client.handler;

import java.util.List;

import com.xzcode.ggserver.game.monitor.client.config.GameMonitorClientConfig;
import com.xzcode.ggserver.game.monitor.common.message.resp.DiscoveryServiceListResp;
import com.xzcode.ggserver.game.monitor.common.service.ServiceInfo;
import com.xzcode.ggserver.game.monitor.common.service.ServiceManager;

import xzcode.ggserver.core.common.message.request.Request;
import xzcode.ggserver.core.common.message.request.action.IRequestMessageHandler;

/**
 * 服务列表推送处理
 * 
 * 
 * @author zai
 * 2019-10-04 14:29:53
 */
public class ServiceListRespHandler implements IRequestMessageHandler<DiscoveryServiceListResp>{
	
	private GameMonitorClientConfig config;
	

	public ServiceListRespHandler(GameMonitorClientConfig config) {
		super();
		this.config = config;
	}


	@Override
	public void handle(Request<DiscoveryServiceListResp> request) {
		
		DiscoveryServiceListResp resp = request.getMessage();
		//检查获取服务集合,内容属性存在null值问题
		List<ServiceInfo> serviceList = resp.getServiceList();
		
		if (serviceList == null) {
			return;
		}
		
		ServiceManager serviceManager = config.getServiceManager();
		
		for (ServiceInfo serviceInfo : serviceList) {
			serviceManager.registerService(serviceInfo);
		}
		
	}

	

}
