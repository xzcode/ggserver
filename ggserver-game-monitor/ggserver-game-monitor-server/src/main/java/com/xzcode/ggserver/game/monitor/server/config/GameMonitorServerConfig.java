package com.xzcode.ggserver.game.monitor.server.config;

import com.xzcode.ggserver.game.monitor.common.constant.GameMonitorConstant;
import com.xzcode.ggserver.game.monitor.common.data.GameDataManager;

import xzcode.ggserver.core.server.IGGServer;

/**
 * 配置
 * 
 * 
 * @author zai
 * 2019-10-04 17:23:47
 */
public class GameMonitorServerConfig {
	
	//ggserver对象
	private IGGServer server;
	
	//是否打印pingpong包信息
	protected boolean 	printPingPongInfo = false;
	
	//服务管理器
	private GameDataManager serviceManager = new GameDataManager();
	
	//服务端口
	private int port = 19394;
	
	//认证token
	private String authToken = GameMonitorConstant.DEFAULT_AUTH_TOKEN;
	
	//客户端汇报周期(毫秒)
	private long clientReportInterval = 30L * 1000L;
	
	//服务失效时间(毫秒)
	private long serviceTimeoutDelay;
	
	//所在地区
	private String region = "default";
	
	//所在分区
	private String zone = "default";
	
	

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public long getClientReportInterval() {
		return clientReportInterval;
	}

	public void setClientReportInterval(long clientReportTimeout) {
		this.clientReportInterval = clientReportTimeout;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public IGGServer getServer() {
		return server;
	}
	
	public void setServer(IGGServer server) {
		this.server = server;
	}
	
	public GameDataManager getServiceManager() {
		return serviceManager;
	}
	
	public void setServiceManager(GameDataManager serviceManager) {
		this.serviceManager = serviceManager;
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
	public long getServiceTimeoutDelay() {
		return serviceTimeoutDelay;
	}
	
	public void setServiceTimeoutDelay(long serviceTimeout) {
		this.serviceTimeoutDelay = serviceTimeout;
	}
	
	public boolean isPrintPingPongInfo() {
		return printPingPongInfo;
	}
	
	public void setPrintPingPongInfo(boolean printPingPongInfo) {
		this.printPingPongInfo = printPingPongInfo;
	}
	

}
