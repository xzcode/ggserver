package com.xzcode.ggserver.game.monitor.client.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.xzcode.ggserver.game.monitor.client.GameMonitorClient;
import com.xzcode.ggserver.game.monitor.client.registry.RegistryInfo;
import com.xzcode.ggserver.game.monitor.client.registry.RegistryManager;
import com.xzcode.ggserver.game.monitor.common.constant.MonitorConstant;
import com.xzcode.ggserver.game.monitor.common.service.ServiceManager;
import com.xzcode.ggserver.game.monitor.common.util.DiscoveryServiceIdUtil;

import xzcode.ggserver.core.client.GGClient;
import xzcode.ggserver.core.common.executor.DefaultTaskExecutor;
import xzcode.ggserver.core.common.executor.ITaskExecutor;
import xzcode.ggserver.core.common.session.GGSession;

/**
 * 配置
 * 
 * 
 * @author zai
 * 2019-10-04 17:23:47
 */
public class GameMonitorClientConfig {
	
	//discoveryClient对象
	protected GameMonitorClient discoveryClient;
	
	protected boolean 	printPingPongInfo = false;
	
	//GGClient对象
	protected GGClient ggclient;
	
	protected ITaskExecutor taskExecutor = new DefaultTaskExecutor("discovery-client-", 1);
	
	//GGSession对象
	protected GGSession session;
	
	//是否打印pingpong包信息
	protected boolean 	pingPongEnabled = false;
	
	//服务管理器
	protected ServiceManager serviceManager = new ServiceManager();
	
	//注册中心信息
	protected List<RegistryInfo> registries = new ArrayList<>();
	
	//注册中心管理器
	protected RegistryManager registryManager = new RegistryManager(registries);
	
	//客户端汇报超时时间(秒)
	protected long clientReportInterval = 30L * 1000L;
	
	//重连间隔-秒
	protected long reconnectInterval = 5L * 1000L;
	
	//尝试重新注册周期，ms
	protected long tryRegisterInterval = 10L * 1000L;
	
	//验证token
	protected String authToken = MonitorConstant.DEFAULT_AUTH_TOKEN;

	//服务id
	protected String serviceId = DiscoveryServiceIdUtil.newServiceId();
	
	//服务名称
	protected String serviceName = "default_service";
	
	//所在地区
	protected String region = "default";
		
	//所在分区
	protected String zone = "default";
	
	//自定义数据更新次数
	protected AtomicInteger customDataUpdateTimes = new AtomicInteger(0);
	

	/**
	 * 自定义数据
	 */
	private Map<String, String> customData = new ConcurrentHashMap<>(6);
	
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
		customDataUpdateTimes.incrementAndGet();
	}
	
	public Map<String, String> getCustomData() {
		return customData;
	}
	
	public void setCustomData(Map<String, String> extraData) {
		this.customData = extraData;
	}

	public GGClient getGGclient() {
		return ggclient;
	}

	public void setGGclient(GGClient ggclient) {
		this.ggclient = ggclient;
	}

	public ServiceManager getServiceManager() {
		return serviceManager;
	}

	public void setServiceManager(ServiceManager serviceManager) {
		this.serviceManager = serviceManager;
	}

	public List<RegistryInfo> getRegistries() {
		return registries;
	}

	public void setRegistries(List<RegistryInfo> registries) {
		this.registries = registries;
	}

	public RegistryManager getRegistryManager() {
		return registryManager;
	}

	public void setRegistryManager(RegistryManager registryManager) {
		this.registryManager = registryManager;
	}

	public long getClientReportInterval() {
		return clientReportInterval;
	}

	public void setClientReportInterval(long clientReportInterval) {
		this.clientReportInterval = clientReportInterval;
	}

	public long getReconnectInterval() {
		return reconnectInterval;
	}

	public void setReconnectInterval(long reconnectInterval) {
		this.reconnectInterval = reconnectInterval;
	}

	public long getTryRegisterInterval() {
		return tryRegisterInterval;
	}

	public void setTryRegisterInterval(long tryRegisterInterval) {
		this.tryRegisterInterval = tryRegisterInterval;
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
	
	public String getAuthToken() {
		return authToken;
	}
	
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	
	public String getServiceId() {
		return serviceId;
	}
	
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	
	public String getServiceName() {
		return serviceName;
	}
	
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public void setSession(GGSession session) {
		this.session = session;
	}
	public GGSession getSession() {
		return session;
	}
	
	public GameMonitorClient getDiscoveryClient() {
		return discoveryClient;
	}
	
	public void setDiscoveryClient(GameMonitorClient discoveryClient) {
		this.discoveryClient = discoveryClient;
	}
	public ITaskExecutor getTaskExecutor() {
		return taskExecutor;
	}
	
	public AtomicInteger getCustomDataUpdateTimes() {
		return customDataUpdateTimes;
	}
	
	public boolean isPingPongEnabled() {
		return pingPongEnabled;
	}
	
	public void setPingPongEnabled(boolean pingPongEnabled) {
		this.pingPongEnabled = pingPongEnabled;
	}
	
	public boolean isPrintPingPongInfo() {
		return printPingPongInfo;
	}
	
	public void setPrintPingPongInfo(boolean printPingPongInfo) {
		this.printPingPongInfo = printPingPongInfo;
	}
	
}
