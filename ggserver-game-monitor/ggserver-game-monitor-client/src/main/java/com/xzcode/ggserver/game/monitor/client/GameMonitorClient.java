package com.xzcode.ggserver.game.monitor.client;

import java.util.ArrayList;
import java.util.List;

import com.xzcode.ggserver.game.monitor.client.config.GameMonitorClientConfig;
import com.xzcode.ggserver.game.monitor.client.events.ConnCloseEventListener;
import com.xzcode.ggserver.game.monitor.client.events.ConnOpenEventListener;
import com.xzcode.ggserver.game.monitor.client.handler.AuthRespHandler;
import com.xzcode.ggserver.game.monitor.client.listener.IClientRegisterSuccessListener;
import com.xzcode.ggserver.game.monitor.common.message.req.GameServerRegisterReq;
import com.xzcode.ggserver.game.monitor.common.message.resp.AuthResp;

import xzcode.ggserver.core.client.GGClient;
import xzcode.ggserver.core.client.config.GGClientConfig;
import xzcode.ggserver.core.common.constant.ProtocolTypeConstants;
import xzcode.ggserver.core.common.event.GGEvents;
import xzcode.ggserver.core.common.executor.thread.GGThreadFactory;
import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.core.common.utils.logger.GGLoggerUtil;

public class GameMonitorClient {

	private GameMonitorClientConfig config;

	protected List<IClientRegisterSuccessListener> registerSuccessListeners = new ArrayList<>();

	public GameMonitorClient(GameMonitorClientConfig config) {
		this.config = config;
		this.config.setGameMonitorClient(this);
	}

	public void start() {
		GGClientConfig ggConfig = new GGClientConfig();
		ggConfig.setPingPongEnabled(true);
		ggConfig.setPrintPingPongInfo(config.isPrintPingPongInfo());
		ggConfig.setTaskExecutor(config.getTaskExecutor());
		ggConfig.setProtocolType(ProtocolTypeConstants.TCP);
		ggConfig.setWorkerGroupThreadFactory(new GGThreadFactory("monitor-worker-", false));
		ggConfig.init();

		GGClient ggClient = new GGClient(ggConfig);
		config.setGGclient(ggClient);

		ggClient.onMessage(AuthResp.ACTION, new AuthRespHandler(config));

		ggClient.addEventListener(GGEvents.Connection.CLOSED, new ConnCloseEventListener(config));
		ggClient.addEventListener(GGEvents.Connection.OPENED, new ConnOpenEventListener(config));

		connect();

	}

	public void connect() {
		GGClient ggClient = config.getGGclient();
		String host = config.getServerHost();
		int port = config.getServerPort();
		ggClient.connect(host, port).addListener(f -> {
			if (!f.isSuccess()) {
				// 连接失败，进行进行重连操作
				GGLoggerUtil.getLogger(this).info("Game Monitor Client Connect Server[{}:{}] Failed!", host, port);
				ggClient.schedule(config.getTryRegisterInterval(), () -> {
					connect();
				});
				return;
			}
			GGLoggerUtil.getLogger(this).info("Game Monitor Client Connect Server[{}:{}] Successfully!", host, port);
		});
	}

	public void syncServiceInfos() {
		GGSession session = config.getSession();
		if (session != null) {
			session.send(GameServerRegisterReq.DEFAULT_INSTANT);
		}
	}

	public void addRegisterSuccessListener(IClientRegisterSuccessListener listener) {
		this.registerSuccessListeners.add(listener);
	}

	public List<IClientRegisterSuccessListener> getRegisterSuccessListeners() {
		return registerSuccessListeners;
	}

	public GameMonitorClientConfig getConfig() {
		return config;
	}

}
