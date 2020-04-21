package com.xzcode.ggserver.game.monitor.common.data;

import java.util.LinkedHashMap;
import java.util.Map;

import xzcode.ggserver.core.common.session.GGSession;

/**
 * 服务信息
 * 
 * 
 * @author zai
 * 2019-10-04 16:14:45
 */
public class ServiceInfo {
	
	/**
	 * 会话
	 */
	
	protected transient GGSession session;
	
	/**
	 * 服务id
	 */
	protected String serviceId;
	
	/**
	 * 服务名称
	 */
	protected String serviceName;
	
	/**
	 * 服务ip地址
	 */
	protected String host;
	
	//所在地区
	protected String region = "default";
		
	//所在分区
	protected String zone = "default";
	
	/**
	 * 自定义数据
	 */
	protected Map<String, String> customData = new LinkedHashMap<>();;
	
	/**
	 * 服务过期延迟（毫秒）
	 */
	protected long timeoutDelay;
	
	/**
	 * 超时时间戳
	 */
	protected long timeoutTimestamp;
	
	/**
	 * 添加自定义参数
	 * 
	 * @param key
	 * @param value
	 * @author zai
	 * 2020-02-04 11:19:05
	 */
	public void addCustomData(String key, String value) {
		customData.put(key, value);
	}
	
	/**
	 * 刷新超时时间
	 * @param delay
	 * 
	 * @author zai
	 * 2019-10-04 16:39:05
	 */
	public void refreshExpireTime() {
		this.timeoutTimestamp = System.currentTimeMillis() + timeoutDelay;
	}
	
	/**
	 * 是否超时
	 * 
	 * @author zai
	 * 2020-02-03 16:23:42
	 */
	public boolean isTimeout() {
		return timeoutTimestamp < System.currentTimeMillis();
	}
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String ip) {
		this.host = ip;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	
	public Map<String, String> getCustomData() {
		return customData;
	}
	public void setCustomData(Map<String, String> extraData) {
		this.customData = extraData;
	}
	
	public GGSession getSession() {
		return session;
	}
	
	public void setSession(GGSession session) {
		this.session = session;
	}

	public long getTimeoutDelay() {
		return timeoutDelay;
	}

	public void setTimeoutDelay(long timeoutDelay) {
		this.timeoutDelay = timeoutDelay;
	}

	public long getTimeoutTimestamp() {
		return timeoutTimestamp;
	}

	public void setTimeoutTimestamp(long timeoutTimestamp) {
		this.timeoutTimestamp = timeoutTimestamp;
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
