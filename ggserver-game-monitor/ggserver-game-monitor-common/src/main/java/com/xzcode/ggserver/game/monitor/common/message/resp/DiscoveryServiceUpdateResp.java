package com.xzcode.ggserver.game.monitor.common.message.resp;

import com.xzcode.ggserver.game.monitor.common.data.ServiceInfo;

import xzcode.ggserver.core.common.message.model.IMessage;

/**
 * 服务更新推送
 * 
 * @author zai
 * 2020-02-04 11:34:37
 */
public class DiscoveryServiceUpdateResp  implements IMessage{
	
	public static final String ACTION = "GG.MONITOR.SERVICE.UPDATE.RESP";
	
	@Override
	public String getActionId() {
		return ACTION;
	}
	
	/**
	 * 服务信息
	 */
	private ServiceInfo serviceInfo;
	

	public DiscoveryServiceUpdateResp() {
		
	}

	public DiscoveryServiceUpdateResp(ServiceInfo serviceInfo) {
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
