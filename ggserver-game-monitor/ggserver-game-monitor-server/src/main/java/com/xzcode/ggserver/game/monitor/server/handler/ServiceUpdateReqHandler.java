package com.xzcode.ggserver.game.monitor.server.handler;

import com.xzcode.ggserver.game.monitor.common.message.req.DiscoveryServiceUpdateReq;
import com.xzcode.ggserver.game.monitor.common.message.resp.DiscoveryServiceUpdateResp;
import com.xzcode.ggserver.game.monitor.common.service.ServiceInfo;
import com.xzcode.ggserver.game.monitor.common.service.ServiceManager;
import com.xzcode.ggserver.game.monitor.server.config.GameMonitorServerConfig;

import xzcode.ggserver.core.common.message.request.Request;
import xzcode.ggserver.core.common.message.request.action.IRequestMessageHandler;
import xzcode.ggserver.core.common.session.GGSession;

/**
 * 服务更新请求处理
 * 
 * 
 * @author zai
 * 2019-10-04 14:29:53
 */
public class ServiceUpdateReqHandler implements IRequestMessageHandler<DiscoveryServiceUpdateReq>{
	
	private GameMonitorServerConfig config;

	public ServiceUpdateReqHandler(GameMonitorServerConfig config) {
		this.config = config;
	}

	@Override
	public void handle(Request<DiscoveryServiceUpdateReq> request) {
		GGSession session = request.getSession();
		DiscoveryServiceUpdateReq req = request.getMessage();
		ServiceManager serviceManager = config.getServiceManager();
		
		ServiceInfo service = req.getServiceInfo();
		service.setSession(session);
		service.setHost(session.getHost());
		
		serviceManager.updateService(service);
		
		ServiceInfo updated = serviceManager.getService(service.getServiceId());
		DiscoveryServiceUpdateResp resp = new DiscoveryServiceUpdateResp(updated);
		
		//发送给所有服务客户端,服务信息更新
		serviceManager.sendToAllServices(resp);
	}


}
