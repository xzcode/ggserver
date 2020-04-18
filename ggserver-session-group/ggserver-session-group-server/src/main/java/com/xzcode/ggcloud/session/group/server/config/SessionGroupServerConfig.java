package com.xzcode.ggcloud.session.group.server.config;

import java.util.concurrent.ThreadFactory;

import com.xzcode.ggcloud.session.group.common.constant.GGSesssionGroupConstant;
import com.xzcode.ggcloud.session.group.common.group.manager.GGSessionGroupManager;
import com.xzcode.ggserver.core.server.GGServer;

import io.netty.channel.EventLoopGroup;

/**
 * 总配置
 *
 * @author zai
 * 2020-04-08 11:53:31
 */
public class SessionGroupServerConfig {
	
	//sessionServer对象
	private GGServer sessionServer;
	
	//业务服务端对象
	private GGServer serviceServer;
	
	//是否开启业务服务端
	private boolean enableServiceServer = false;
	
	//是否打印pingpong包信息
	protected boolean printPingPongInfo = false;
	
	//会话组管理器
	private GGSessionGroupManager sessionGroupManager;
	
	//工作线程数
	protected int workThreadSize = 8;
	
	
	//服务端口
	private int port = GGSesssionGroupConstant.DEFAULT_SERVER_PORT;
	
	//认证token
	private String authToken = GGSesssionGroupConstant.DEFAULT_AUTH_TOKEN;
	
	//工作线程工厂
	protected ThreadFactory workThreadFactory;
	
	//工作线程组
	protected EventLoopGroup workEventLoopGroup;
	
	
	//是否输出会话组包信息
	protected boolean printSessionGroupPackLog = false;

	
	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public GGServer getSessionServer() {
		return sessionServer;
	}
	
	public void setSessionServer(GGServer sessionServer) {
		this.sessionServer = sessionServer;
	}
	
	public GGSessionGroupManager getSessionGroupManager() {
		return sessionGroupManager;
	}
	
	public void setSessionGroupManager(GGSessionGroupManager sessionGroupManager) {
		this.sessionGroupManager = sessionGroupManager;
	}

	public boolean isPrintPingPongInfo() {
		return printPingPongInfo;
	}
	
	public void setPrintPingPongInfo(boolean printPingPongInfo) {
		this.printPingPongInfo = printPingPongInfo;
	}
	
	
	public GGServer getServiceServer() {
		return serviceServer;
	}
	
	public void setServiceServer(GGServer serviceServer) {
		this.serviceServer = serviceServer;
	}

	public boolean isEnableServiceServer() {
		return enableServiceServer;
	}

	public void setEnableServiceServer(boolean enableServiceService) {
		this.enableServiceServer = enableServiceService;
	}

	public int getWorkThreadSize() {
		return workThreadSize;
	}

	public void setWorkThreadSize(int workThreadSize) {
		this.workThreadSize = workThreadSize;
	}

	public ThreadFactory getWorkThreadFactory() {
		return workThreadFactory;
	}
	
	public void setWorkThreadFactory(ThreadFactory workThreadFactory) {
		this.workThreadFactory = workThreadFactory;
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
