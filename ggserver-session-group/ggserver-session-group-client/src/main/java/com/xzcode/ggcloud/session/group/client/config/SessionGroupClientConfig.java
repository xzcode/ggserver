package com.xzcode.ggcloud.session.group.client.config;

import java.util.concurrent.ThreadFactory;

import com.xzcode.ggcloud.session.group.client.SessionGroupClient;
import com.xzcode.ggcloud.session.group.common.constant.GGSesssionGroupConstant;
import com.xzcode.ggcloud.session.group.common.group.manager.GGSessionGroupManager;
import com.xzcode.ggserver.core.client.GGClient;
import com.xzcode.ggserver.core.common.utils.RandomIdUtil;
import com.xzcode.ggserver.core.server.GGServer;

import io.netty.channel.EventLoopGroup;

/**
 * 配置
 * 
 * 
 * @author zai 2019-10-04 17:23:47
 */
public class SessionGroupClientConfig {
	
	//会话组客户端
	protected SessionGroupClient sessionGroupClient;

	// 会话客户端
	protected GGClient sessionClient;
	
	//开启业务客户端
	protected boolean enableServiceClient;
	
	//业务客户端
	protected GGClient serviceClient;
	
	//开启业务服务端
	protected boolean enableServiceServer;
	
	//业务客户端
	protected GGServer serviceServer;
	
	//会话组管理器
	protected GGSessionGroupManager sessionGroupManager;

	// 服务器域名
	protected String serverHost = "localhost";

	// 服务器端口
	protected int serverPort = GGSesssionGroupConstant.DEFAULT_SERVER_PORT;

	//工作线程数
	protected int workThreadSize = 8;
	
	//工作线程工厂
	protected ThreadFactory workThreadFactory;
	
	/**
	 * 工作线程组
	 */
	protected EventLoopGroup workEventLoopGroup;
	
	
	// 连接数
	protected int connectionSize = 8;
	
	//重连周期 毫秒
	protected long reconnectInterval = 10L * 1000L;

	protected boolean printPingPongInfo = false;
	
	//是否输出会话组包信息
	protected boolean printSessionGroupPackLog = false;

	// 会话组id
	protected String sessionGroupId = RandomIdUtil.newRandomStringId24();


	// 验证token
	protected String authToken = GGSesssionGroupConstant.DEFAULT_AUTH_TOKEN;


	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}


	public boolean isPrintPingPongInfo() {
		return printPingPongInfo;
	}

	public void setPrintPingPongInfo(boolean printPingPongInfo) {
		this.printPingPongInfo = printPingPongInfo;
	}

	public String getSessionGroupId() {
		return sessionGroupId;
	}

	public void setSessionGroupId(String sessionGroupId) {
		this.sessionGroupId = sessionGroupId;
	}

	public void setSessionClient(GGClient sessionClient) {
		this.sessionClient = sessionClient;
	}

	public GGClient getSessionClient() {
		return sessionClient;
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

	public int getConnectionSize() {
		return connectionSize;
	}

	public void setConnectionSize(int connectionSize) {
		this.connectionSize = connectionSize;
	}
	
	public void setSessionGroupManager(GGSessionGroupManager sessionGroupManager) {
		this.sessionGroupManager = sessionGroupManager;
	}
	
	public GGSessionGroupManager getSessionGroupManager() {
		return sessionGroupManager;
	}
	
	public GGClient getServiceClient() {
		return serviceClient;
	}
	
	public void setServiceClient(GGClient serviceClient) {
		this.serviceClient = serviceClient;
	}
	
	public boolean isEnableServiceClient() {
		return enableServiceClient;
	}
	
	public void setEnableServiceClient(boolean enableServiceClient) {
		this.enableServiceClient = enableServiceClient;
	}
	
	public void setSessionGroupClient(SessionGroupClient sessionGroupClient) {
		this.sessionGroupClient = sessionGroupClient;
	}
	
	public SessionGroupClient getSessionGroupClient() {
		return sessionGroupClient;
	}
	
	public int getWorkThreadSize() {
		return workThreadSize;
	}
	
	public void setWorkThreadSize(int workThreadSize) {
		this.workThreadSize = workThreadSize;
	}
	
	public long getReconnectInterval() {
		return reconnectInterval;
	}
	
	public void setReconnectInterval(long reconnectInterval) {
		this.reconnectInterval = reconnectInterval;
	}

	public ThreadFactory getWorkThreadFactory() {
		return workThreadFactory;
	}

	public void setWorkThreadFactory(ThreadFactory workThreadFactory) {
		this.workThreadFactory = workThreadFactory;
	}
	
	public void setEnableServiceServer(boolean enableServiceServer) {
		this.enableServiceServer = enableServiceServer;
	}
	
	public boolean isEnableServiceServer() {
		return enableServiceServer;
	}
	
	public GGServer getServiceServer() {
		return serviceServer;
	}
	
	public void setServiceServer(GGServer serviceServer) {
		this.serviceServer = serviceServer;
	}
	
	public boolean isPrintSessionGroupPackLog() {
		return printSessionGroupPackLog;
	}
	
	public void setPrintSessionGroupPackLog(boolean printSessionGroupPackLog) {
		this.printSessionGroupPackLog = printSessionGroupPackLog;
	}

	public EventLoopGroup getWorkEventLoopGroup() {
		return workEventLoopGroup;
	}

	public void setWorkEventLoopGroup(EventLoopGroup workEventLoopGroup) {
		this.workEventLoopGroup = workEventLoopGroup;
	}
	
	

}
