package com.xzcode.ggserver.game.monitor.common.message.req;

import com.xzcode.ggserver.game.monitor.common.service.ServiceInfo;

import xzcode.ggserver.core.common.message.model.IMessage;

/**
 * 服务更新请求
 * 
 * @author zai
 * 2020-02-04 15:30:56
 */
public class DiscoveryServiceUpdateReq implements IMessage{
	
	public static final String ACTION = "GG.MONITOR.UPDATE.REQ";
	
	@Override
	public String getActionId() {
		return ACTION;
	}
	
	//服务信息
	private ServiceInfo serviceInfo;

	public DiscoveryServiceUpdateReq() {
		
	}

	public DiscoveryServiceUpdateReq(ServiceInfo serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

	public ServiceInfo getServiceInfo() {
		return serviceInfo;
	}

	public void setServiceInfo(ServiceInfo serviceInfo) {
		this.serviceInfo = serviceInfo;
	}
	
}
