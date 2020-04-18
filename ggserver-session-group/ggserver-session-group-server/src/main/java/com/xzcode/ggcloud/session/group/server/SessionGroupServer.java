package com.xzcode.ggcloud.session.group.server;

import com.xzcode.ggcloud.session.group.common.constant.GGSesssionGroupConstant;
import com.xzcode.ggcloud.session.group.common.group.manager.GGSessionGroupManager;
import com.xzcode.ggcloud.session.group.common.message.req.AuthReq;
import com.xzcode.ggcloud.session.group.common.message.req.DataTransferReq;
import com.xzcode.ggcloud.session.group.common.message.req.SessionGroupRegisterReq;
import com.xzcode.ggcloud.session.group.common.session.SessionGroupSessionFactory;
import com.xzcode.ggcloud.session.group.server.config.SessionGroupServerConfig;
import com.xzcode.ggcloud.session.group.server.events.ConnActiveEventListener;
import com.xzcode.ggcloud.session.group.server.events.ConnCloseEventListener;
import com.xzcode.ggcloud.session.group.server.handler.AuthReqHandler;
import com.xzcode.ggcloud.session.group.server.handler.DataTransferReqHandler;
import com.xzcode.ggcloud.session.group.server.handler.SessionGroupRegisterReqHandler;
import com.xzcode.ggserver.core.common.constant.ProtocolTypeConstants;
import com.xzcode.ggserver.core.common.event.GGEvents;
import com.xzcode.ggserver.core.common.executor.thread.GGThreadFactory;
import com.xzcode.ggserver.core.common.future.IGGFuture;
import com.xzcode.ggserver.core.server.GGServer;
import com.xzcode.ggserver.core.server.config.GGServerConfig;
import com.xzcode.ggserver.core.server.impl.GGDefaultServer;

/**
 * 会话组服务器启动类
 *
 * @author zai
 * 2020-04-08 15:21:21
 */
public class SessionGroupServer {
	
	private SessionGroupServerConfig config;
	
	
	
	public SessionGroupServer(SessionGroupServerConfig config) {
		super();
		this.config = config;
		init();
	}

	public void init() {
		
		GGThreadFactory bossThreadFactory = new GGThreadFactory("gg-group-boss-", false);
		if (this.config.getWorkThreadFactory() == null) {
			this.config.setWorkThreadFactory(new GGThreadFactory("gg-group-worker-", false));
		}
		
		GGServerConfig sessionServerConfig = new GGServerConfig();
		sessionServerConfig.setPingPongEnabled(true);
		sessionServerConfig.setPrintPingPongInfo(this.config.isPrintPingPongInfo());
		sessionServerConfig.setProtocolType(ProtocolTypeConstants.TCP);
		sessionServerConfig.setPort(this.config.getPort());
		sessionServerConfig.setSessionFactory(new SessionGroupSessionFactory(sessionServerConfig));
		sessionServerConfig.setBossGroupThreadFactory(bossThreadFactory);
		sessionServerConfig.setWorkerGroupThreadFactory(this.config.getWorkThreadFactory());
		sessionServerConfig.setWorkThreadSize(this.config.getWorkThreadSize());
		
		if (!this.config.isPrintSessionGroupPackLog()) {
			sessionServerConfig.getPackLogger().addPackLogFilter(pack -> {
				String actionString = pack.getActionString();
				return !(actionString.startsWith(GGSesssionGroupConstant.ACTION_ID_PREFIX));
			});
		}
		sessionServerConfig.init();
		
		GGSessionGroupManager sessionGroupManager = new GGSessionGroupManager(sessionServerConfig);
		this.config.setSessionGroupManager(sessionGroupManager);
		
		GGServer sessionServer = new GGDefaultServer(sessionServerConfig);
		sessionServer.addEventListener(GGEvents.Connection.OPENED, new ConnActiveEventListener(config));
		sessionServer.addEventListener(GGEvents.Connection.CLOSED, new ConnCloseEventListener(config));
		sessionServer.onMessage(AuthReq.ACTION, new AuthReqHandler(config));
		sessionServer.onMessage(SessionGroupRegisterReq.ACTION_ID, new SessionGroupRegisterReqHandler(config));
		sessionServer.onMessage(DataTransferReq.ACTION, new DataTransferReqHandler(config));
		
		this.config.setSessionServer(sessionServer);
		
		
		
		
		GGServerConfig serviceServerConfig = new GGServerConfig();
		serviceServerConfig.setBossGroup(sessionServerConfig.getBossGroup());
		serviceServerConfig.setWorkerGroup(sessionServerConfig.getWorkerGroup());
		serviceServerConfig.init();
		
		GGServer serviceServer = new GGDefaultServer(serviceServerConfig);
		this.config.setServiceServer(serviceServer);
		
	}
	
	public IGGFuture start() {
		return this.config.getSessionServer().start();
	}
	
	public void setConfig(SessionGroupServerConfig config) {
		this.config = config;
	}
	
	public SessionGroupServerConfig getConfig() {
		return config;
	}
	
}
