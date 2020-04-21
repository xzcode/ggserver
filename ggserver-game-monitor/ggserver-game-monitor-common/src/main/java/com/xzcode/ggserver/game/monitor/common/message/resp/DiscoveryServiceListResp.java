package com.xzcode.ggserver.game.monitor.common.message.resp;

import java.util.List;

import com.xzcode.ggserver.game.monitor.common.data.ServiceInfo;

import xzcode.ggserver.core.common.message.model.IMessage;

/**
 * 服务列表推送
 * 
 * @author zai
 * 2020-02-04 11:44:51
 */
public class DiscoveryServiceListResp  implements IMessage{
	
	public static final String ACTION = "GG.MONITOR.SERVICE.LIST.RESP";
	
	@Override
	public String getActionId() {
		return ACTION;
	}
	/**
	 * 服务列表
	 */
	private List<ServiceInfo> serviceList;
	

	public DiscoveryServiceListResp() {
	}

	public DiscoveryServiceListResp(List<ServiceInfo> serviceList) {
		this.serviceList = serviceList;
	}

	public List<ServiceInfo> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<ServiceInfo> serviceList) {
		this.serviceList = serviceList;
	}
	
}
