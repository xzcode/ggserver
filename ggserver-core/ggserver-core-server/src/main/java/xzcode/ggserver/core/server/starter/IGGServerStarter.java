package xzcode.ggserver.core.server.starter;

import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.server.config.GGServerConfig;

public interface IGGServerStarter {
	
	IGGFuture start();
	
	void shutdown();
	
	void setConfig(GGServerConfig config);
}
