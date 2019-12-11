package xzcode.ggserver.core.server.impl;

import xzcode.ggserver.core.server.GGServer;
import xzcode.ggserver.core.server.config.GGServerConfig;
import xzcode.ggserver.core.server.starter.IGGServerStarter;
import xzcode.ggserver.core.server.starter.impl.DefaultGGServerStarter;

/**
 * 默认服务器实现
 * 
 * @author zai
 * 2019-12-05 10:40:41
 */
public class DefaultServer implements GGServer<GGServerConfig> {

	private GGServerConfig config;

	private IGGServerStarter serverStarter;
	

	public DefaultServer(GGServerConfig serverConfig) {
		this.config = serverConfig;
	}

	@Override
	public void start() {
		this.shutdown();
		this.serverStarter = new DefaultGGServerStarter(config);
		this.serverStarter.start();
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
