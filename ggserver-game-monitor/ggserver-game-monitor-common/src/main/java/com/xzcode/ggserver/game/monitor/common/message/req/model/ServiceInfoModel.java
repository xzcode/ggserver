package com.xzcode.ggserver.game.monitor.common.message.req.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 服务信息模型
 * 
 * 
 * @author zai
 * 2019-10-04 16:14:45
 */
public class ServiceInfoModel {
	
	/**
	 * 服务名称
	 */
	private String serviceName;
	
	/**
	 * 服务id
	 */
	private String serviceId;
	
	/**
	 * 服务ip地址
	 */
	private String ip;
	
	/**
	 * 服务ip地址
	 */
	private Integer port;
	
	/**
	 * 所在地区
	 */
	protected String region;
		
	/**
	 * 所在分区
	 */
	protected String zone;
	
	/**
	 * 负载参考值
	 */
	private Long loadingCapacity;
	
	/**
	 * 额外数据
	 */
	private Map<String, String> extraData;
	
	/**
	 * 添加额外参数
	 * 
	 * @param key
	 * @param value
	 * @author zai
	 * 2020-02-04 11:19:05
	 */
	public void addExtraData(String key, String value) {
		if (extraData == null) {
			extraData = new LinkedHashMap<>();
		}
		extraData.put(key, value);
	}
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public long getLoadingCapacity() {
		return loadingCapacity;
	}

	public void setLoadingCapacity(long loadingCapacity) {
		this.loadingCapacity = loadingCapacity;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public Map<String, String> getExtraData() {
		return extraData;
	}
	
	public void setExtraData(Map<String, String> extraData) {
		this.extraData = extraData;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}
	
	
	
}
