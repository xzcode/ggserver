package com.xzcode.ggcloud.session.group.client;

import java.nio.charset.Charset;

import com.xzcode.ggcloud.session.group.client.config.SessionGroupClientConfig;
import com.xzcode.ggcloud.session.group.client.events.ConnCloseEventListener;
import com.xzcode.ggcloud.session.group.client.events.ConnOpenEventListener;
import com.xzcode.ggcloud.session.group.client.handler.AnthRespHandler;
import com.xzcode.ggcloud.session.group.client.handler.DataTransferRespHandler;
import com.xzcode.ggcloud.session.group.client.handler.SessionGroupRegisterRespHandler;
import com.xzcode.ggcloud.session.group.common.constant.GGSesssionGroupConstant;
import com.xzcode.ggcloud.session.group.common.group.manager.GGSessionGroupManager;
import com.xzcode.ggcloud.session.group.common.message.resp.AuthResp;
import com.xzcode.ggcloud.session.group.common.message.resp.DataTransferResp;
import com.xzcode.ggcloud.session.group.common.message.resp.SessionGroupRegisterResp;
import com.xzcode.ggcloud.session.group.common.session.SessionGroupSessionFactory;
import com.xzcode.ggserver.core.client.GGClient;
import com.xzcode.ggserver.core.client.config.GGClientConfig;
import com.xzcode.ggserver.core.common.constant.ProtocolTypeConstants;
import com.xzcode.ggserver.core.common.event.GGEvents;
import com.xzcode.ggserver.core.common.event.EventManager;
import com.xzcode.ggserver.core.common.event.EventSupport;
import com.xzcode.ggserver.core.common.executor.thread.GGThreadFactory;
import com.xzcode.ggserver.core.common.handler.serializer.ISerializer;
import com.xzcode.ggserver.core.common.message.response.support.IMakePackSupport;
import com.xzcode.ggserver.core.common.utils.logger.GGLoggerUtil;

/**
 * 会话组客户端
 *
 * @author zai 2020-04-08 11:47:15
 */
public class SessionGroupClient implements EventSupport, IMakePackSupport{

	private SessionGroupClientConfig config;

	public SessionGroupClient(SessionGroupClientConfig config) {
		this.config = config;
		config.setSessionGroupClient(this);
		init();
	}
	
	
	private void init() {
		
		if (this.config.getWorkThreadFactory() == null) {
			this.config.setWorkThreadFactory(new GGThreadFactory("gg-group-cli-", false));
		}
		
		GGClientConfig sessionClientConfig = new GGClientConfig();

		sessionClientConfig.setPingPongEnabled(true);
		sessionClientConfig.setPrintPingPongInfo(this.config.isPrintPingPongInfo());
		sessionClientConfig.setWorkThreadSize(this.config.getWorkThreadSize());
		sessionClientConfig.setWorkerGroupThreadFactory(this.config.getWorkThreadFactory());
		sessionClientConfig.setProtocolType(ProtocolTypeConstants.TCP);
		sessionClientConfig.setSessionFactory(new SessionGroupSessionFactory(sessionClientConfig));
		
		if (this.config.getWorkEventLoopGroup() != null) {
			sessionClientConfig.setWorkerGroup(this.config.getWorkEventLoopGroup());
		}
		
		if (!this.config.isPrintSessionGroupPackLog()) {
			sessionClientConfig.getPackLogger().addPackLogFilter(pack -> {
				String actionString = pack.getActionString();
				return !(actionString.startsWith(GGSesssionGroupConstant.ACTION_ID_PREFIX));
			});
		}

		sessionClientConfig.init();
		
		GGSessionGroupManager sessionGroupManager = new GGSessionGroupManager(sessionClientConfig);
		this.config.setSessionGroupManager(sessionGroupManager);
		

		GGClient sessionClient = new GGClient(sessionClientConfig);
		this.config.setSessionClient(sessionClient);


		sessionClient.onMessage(AuthResp.ACTION_ID, new AnthRespHandler(this.config));
		sessionClient.onMessage(SessionGroupRegisterResp.ACTION_ID, new SessionGroupRegisterRespHandler(this.config));
		sessionClient.onMessage(DataTransferResp.ACTION_ID, new DataTransferRespHandler(this.config));

		sessionClient.addEventListener(GGEvents.Connection.CLOSED, new ConnCloseEventListener(this.config));
		sessionClient.addEventListener(GGEvents.Connection.OPENED, new ConnOpenEventListener(this.config));
		
		
		if (this.config.isEnableServiceClient() && this.config.getServiceClient() == null) {
			
			GGClientConfig serviceClientConfig = new GGClientConfig();
			serviceClientConfig.setWorkerGroup(sessionClientConfig.getWorkerGroup());
			serviceClientConfig.init();
			
			GGClient serviceClient = new GGClient(serviceClientConfig);
			this.config.setServiceClient(serviceClient);
			
		}
	}

	/**
	 * 启动客户端
	 *
	 * @author zai 2020-04-08 11:47:42
	 */
	public void start() {
		this.connect();
	}
	

	/**
	 * 进行连接操作
	 *
	 * @author zai 2020-04-08 11:46:06
	 */
	private void connect() {
		// 根据设置的连接数进行连接初始化
		int connectionSize = config.getConnectionSize();
		for (int i = 0; i < connectionSize; i++) {
			connectOne(config.getServerHost(), config.getServerPort());
		}
	}

	/**
	 * 进行一次连接
	 *
	 * @param host
	 * @param port
	 * @author zai 2020-04-08 11:45:53
	 */
	public void connectOne(String host, int port) {
		GGClient ggclient = config.getSessionClient();
		ggclient.connect(host, port).addListener(f -> {
			if (!f.isSuccess()) {
				// 连接失败，进行进行重连操作
				GGLoggerUtil.getLogger(this).warn("SessionGroupClient Connect Server[{}:{}] Failed!", host, port);
				ggclient.schedule(config.getReconnectInterval(), () -> {
					connectOne(host, port);
				});
				return;
			}
			GGLoggerUtil.getLogger(this).warn("SessionGroupClient Connect Server[{}:{}] Successfully!", host, port);
		});
	}
	
	
	public SessionGroupClientConfig getConfig() {
		return config;
	}
	
	public void setConfig(SessionGroupClientConfig config) {
		this.config = config;
	}


	@Override
	public EventManager getEventManagerImpl() {
		return this.config.getSessionClient();
	}


	@Override
	public Charset getCharset() {
		return this.config.getSessionClient().getCharset();
	}


	@Override
	public ISerializer getSerializer() {
		return this.config.getSessionClient().getSerializer();
	}



}
