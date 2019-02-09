package xzcode.ggserver.core.starter;

import xzcode.ggserver.core.config.GGServerConfig;

public interface IGGServerStarter {
	
	IGGServerStarter run();
	
	IGGServerStarter shutdown();
	
	void setConfig(GGServerConfig config);
}
