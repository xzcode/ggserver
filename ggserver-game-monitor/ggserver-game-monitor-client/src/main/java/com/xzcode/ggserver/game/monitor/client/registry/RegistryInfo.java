package com.xzcode.ggserver.game.monitor.client.registry;

/**
 * 注册中心信息
 * 
 * 
 * @author zai
 * 2019-11-24 16:27:42
 */
public class RegistryInfo {
	
	//域名或ip
	private String domain;
	//端口
	private int port;
	//认证token
	private String authToken;
	//所在分区
	private String zone;
	//所在地区
	private String region;
	//活动状态
	private boolean active;
	
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getAutoToken() {
		return authToken;
	}
	public void setAutoToken(String autoToken) {
		this.authToken = autoToken;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	
}
