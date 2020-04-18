package com.xzcode.ggserver.core.client.config;

import com.xzcode.ggserver.core.common.config.GGConfig;
import com.xzcode.ggserver.core.common.event.GGEvents;
import com.xzcode.ggserver.core.common.message.pingpong.GGPingPongClientEventListener;
import com.xzcode.ggserver.core.common.message.pingpong.GGPongResponseHandler;
import com.xzcode.ggserver.core.common.message.pingpong.model.GGPong;

import io.netty.bootstrap.Bootstrap;

/**
 * 客户端配置
 * 
 * @author zai 2019-12-12 19:41:19
 */
public class GGClientConfig extends GGConfig {

	private Bootstrap bootstrap = new Bootstrap();

	private String host;

	private int port;

	@Override
	public void init() {

		super.init();

		if (isPingPongEnabled()) {
			requestMessageManager.onMessage(GGPong.ACTION_ID, new GGPongResponseHandler(this));
			eventManager.addEventListener(GGEvents.Idle.ALL, new GGPingPongClientEventListener(this));
		}

	}

	public Bootstrap getBootstrap() {
		return bootstrap;
	}

	public void setBootstrap(Bootstrap bootstrap) {
		this.bootstrap = bootstrap;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
