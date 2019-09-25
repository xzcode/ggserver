package xzcode.ggserver.core.starter;

import xzcode.ggserver.core.config.GGConfig;

public interface IGGServerStarter {
	
	IGGServerStarter run();
	
	IGGServerStarter shutdown();
	
	void setConfig(GGConfig config);
}
