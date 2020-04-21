package com.xzcode.ggserver.game.monitor.client.handler;

import com.xzcode.ggserver.game.monitor.client.config.GameMonitorClientConfig;
import com.xzcode.ggserver.game.monitor.common.message.resp.DiscoveryAddServiceResp;

import xzcode.ggserver.core.common.message.request.Request;
import xzcode.ggserver.core.common.message.request.action.IRequestMessageHandler;

/**
 * 新增服务推送处理器
 * 
 * @author zai
 * 2020-02-10 19:57:04
 */
public class AddServiceRespHandler implements IRequestMessageHandler<DiscoveryAddServiceResp>{
	
	private GameMonitorClientConfig config;
	

	public AddServiceRespHandler(GameMonitorClientConfig config) {
		super();
		this.config = config;
	}


	@Override
	public void handle(Request<DiscoveryAddServiceResp> request) {
		
		DiscoveryAddServiceResp resp = request.getMessage();
		config.getServiceManager().registerService(resp.getServiceInfo());
		
	}

	

}
