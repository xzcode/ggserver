package com.xzcode.ggserver.game.monitor.client.config;

import com.xzcode.ggserver.game.monitor.client.GameMonitorClient;
import com.xzcode.ggserver.game.monitor.common.constant.GameMonitorConstant;
import com.xzcode.ggserver.game.monitor.common.data.GameDataManager;

import xzcode.ggserver.core.client.GGClient;
import xzcode.ggserver.core.common.executor.DefaultTaskExecutor;
import xzcode.ggserver.core.common.executor.ITaskExecutor;
import xzcode.ggserver.core.common.session.GGSession;

/**
 * 游戏监控客户端配置
 *
 * @author zai
 * 2020-04-23 14:45:15
 */
public class GameMonitorClientConfig {
	
	//gameMonitorClient对象
	protected GameMonitorClient gameMonitorClient;
	
	protected boolean printPingPongInfo = false;
	
	//GGClient对象
	protected GGClient ggclient;
	
	//任务执行器
	protected ITaskExecutor taskExecutor = new DefaultTaskExecutor("game-monitor-client-", 1);
	
	//GGSession对象
	protected GGSession session;
	
	//是否打印pingpong包信息
	protected boolean 	pingPongEnabled = false;
	
	//服务管理器
	protected GameDataManager serviceManager = new GameDataManager();
	
	//客户端汇报超时时间(秒)
	protected long clientReportInterval = 30L * 1000L;
	
	//重连间隔-秒
	protected long reconnectInterval = 5L * 1000L;
	
	//尝试重新注册周期，ms
	protected long tryRegisterInterval = 10L * 1000L;
	
	//服务端地址
	protected String serverHost = "localhost";
	
	//服务端端口
	protected int serverPort = 18001;
	
	//验证token
	protected String authToken = GameMonitorConstant.DEFAULT_AUTH_TOKEN;

	
	public GGClient getGGclient() {
		return ggclient;
	}

	public void setGGclient(GGClient ggclient) {
		this.ggclient = ggclient;
	}

	public GameDataManager getServiceManager() {
		return serviceManager;
	}

	public void setServiceManager(GameDataManager serviceManager) {
		this.serviceManager = serviceManager;
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

	public String getAuthToken() {
		return authToken;
	}
	
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	
	public void setSession(GGSession session) {
		this.session = session;
	}
	public GGSession getSession() {
		return session;
	}
	
	public GameMonitorClient getGameMonitorClient() {
		return gameMonitorClient;
	}
	
	public void setGameMonitorClient(GameMonitorClient discoveryClient) {
		this.gameMonitorClient = discoveryClient;
	}
	public ITaskExecutor getTaskExecutor() {
		return taskExecutor;
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

	public GGClient getGgclient() {
		return ggclient;
	}

	public void setGgclient(GGClient ggclient) {
		this.ggclient = ggclient;
	}

	public String getServerHost() {
		return serverHost;
	}

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public void setTaskExecutor(ITaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	
	
	
}
