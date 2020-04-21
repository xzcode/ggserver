package com.xzcode.ggserver.game.monitor.client.registry;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 注册中心管理器
 * 
 * 
 * @author zai
 * 2019-10-05 13:49:56
 */
public class RegistryManager {
	
	
	//注册中心信息
	private List<RegistryInfo> registryInfos;
	
	/**
	 * 所有注册中心都已失效
	 */
	private boolean allRegistriesDown;
	

	//已注册的信息
	protected RegistryInfo registriedInfo;
	
	
	public RegistryManager(List<RegistryInfo> registryInfos) {
		this.registryInfos = registryInfos;
	}
	
	
	
	/**
	 * 随机获取一个注册中心
	 * @return
	 * 
	 * @author zai
	 * 2019-10-05 13:49:47
	 */
	public RegistryInfo getRandomRegistry() {
		if (registryInfos.isEmpty()) {
			return null;
		}
		if (registryInfos.size() == 1) {
			this.registriedInfo = registryInfos.get(0);
		}
		this.registriedInfo = registryInfos.get(ThreadLocalRandom.current().nextInt(registryInfos.size()));
		return this.registriedInfo;
	}
	
	/**
	 * 检查是否所有注册中心均已失效
	 * @return
	 * 
	 * @author zai
	 * 2019-11-24 16:31:22
	 */
	public boolean isAllRegistriesDown() {
		return allRegistriesDown;
	}
	
	public RegistryInfo getRegistriedInfo() {
		return registriedInfo;
	}
	
	public void setRegistriedInfo(RegistryInfo registriedInfo) {
		this.registriedInfo = registriedInfo;
	}
}
