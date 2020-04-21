package com.xzcode.ggserver.game.monitor.common.message.resp;

import com.xzcode.ggserver.game.monitor.common.service.ServiceInfo;

import xzcode.ggserver.core.common.message.model.IMessage;

/**
 * 新增服务推送
 * 
 * @author zai
 * 2020-02-10 19:43:35
 */
public class DiscoveryAddServiceResp  implements IMessage{
	
	public static final String ACTION = "GG.MONITOR.ADD.SERVICE.RESP";
	
	@Override
	public String getActionId() {
		return ACTION;
	}
	
	/**
	 * 服务信息
	 */
	private ServiceInfo serviceInfo;
	

	public DiscoveryAddServiceResp() {
		
	}

	public DiscoveryAddServiceResp(ServiceInfo serviceInfo) {
		super();
		this.serviceInfo = serviceInfo;
	}


	public ServiceInfo getServiceInfo() {
		return serviceInfo;
	}
	
	public void setServiceInfo(ServiceInfo serviceInfo) {
		this.serviceInfo = serviceInfo;
	}
	
}
