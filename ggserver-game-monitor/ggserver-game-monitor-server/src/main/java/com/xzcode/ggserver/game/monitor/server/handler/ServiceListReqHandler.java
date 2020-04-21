package com.xzcode.ggserver.game.monitor.server.handler;

import java.util.List;

import com.xzcode.ggserver.game.monitor.common.message.req.DiscoveryServiceListReq;
import com.xzcode.ggserver.game.monitor.common.message.resp.DiscoveryServiceListResp;
import com.xzcode.ggserver.game.monitor.common.service.ServiceInfo;
import com.xzcode.ggserver.game.monitor.server.config.GameMonitorServerConfig;

import xzcode.ggserver.core.common.message.request.Request;
import xzcode.ggserver.core.common.message.request.action.IRequestMessageHandler;
import xzcode.ggserver.core.common.session.GGSession;

/**
 * 客户端注册请求处理
 * 
 * 
 * @author zai
 * 2019-10-04 14:29:53
 */
public class ServiceListReqHandler implements IRequestMessageHandler<DiscoveryServiceListReq>{
	
	private GameMonitorServerConfig config;

	public ServiceListReqHandler(GameMonitorServerConfig config) {
		this.config = config;
	}

	@Override
	public void handle(Request<DiscoveryServiceListReq> request) {
		GGSession session = request.getSession();
		List<ServiceInfo> serviceList = config.getServiceManager().getServiceList();
		DiscoveryServiceListResp resp = new DiscoveryServiceListResp(serviceList);
		session.send(resp);
	}


}
