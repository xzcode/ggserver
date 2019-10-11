package xzcode.ggserver.core.server.starter;

import xzcode.ggserver.core.server.config.GGServerConfig;

public interface IGGServerStarter {
	
	IGGServerStarter run();
	
	IGGServerStarter shutdown();
	
	void setConfig(GGServerConfig config);
}
