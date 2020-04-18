package com.xzcode.ggserver.core.server.impl;

import com.xzcode.ggserver.core.common.future.IGGFuture;
import com.xzcode.ggserver.core.server.GGServer;
import com.xzcode.ggserver.core.server.config.GGServerConfig;
import com.xzcode.ggserver.core.server.starter.IGGServerStarter;
import com.xzcode.ggserver.core.server.starter.impl.DefaultGGServerStarter;

/**
 * 默认服务器实现
 * 
 * @author zai
 * 2019-12-05 10:40:41
 */
public class GGDefaultServer implements GGServer {

	private GGServerConfig config;

	private IGGServerStarter serverStarter;
	

	public GGDefaultServer(GGServerConfig serverConfig) {
		this.config = serverConfig;
	}

	@Override
	public IGGFuture start() {
		this.shutdown();
		this.serverStarter = new DefaultGGServerStarter(config);
		return this.serverStarter.start();
	}

	@Override
	public void shutdown() {
		if (this.serverStarter != null) {
			this.serverStarter.shutdown();
		}
	}

	@Override
	public GGServerConfig getConfig() {
		return config;
	}

}
