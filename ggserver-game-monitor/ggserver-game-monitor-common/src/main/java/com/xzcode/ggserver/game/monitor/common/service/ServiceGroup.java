package com.xzcode.ggserver.game.monitor.common.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务信息组
 * 
 * 
 * @author zai
 * 2019-10-04 16:14:45
 */
public class ServiceGroup {
	
	private Map<String, ServiceInfo> services = new ConcurrentHashMap<>();
	
	
	public void addServiceInfo(ServiceInfo serviceInfo) {
		services.putIfAbsent(serviceInfo.getServiceId(), serviceInfo);
	}
	
	public ServiceInfo removeServiceInfo(String serviceId) {
		return services.remove(serviceId);
	}
	
	public ServiceInfo getServiceInfo(String serviceId) {
		return services.get(serviceId);
	}
	
	public Map<String, ServiceInfo> getServices() {
		return services;
	}
	
}
