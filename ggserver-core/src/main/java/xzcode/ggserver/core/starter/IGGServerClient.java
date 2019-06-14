package xzcode.ggserver.core.starter;

import xzcode.ggserver.core.config.GGServerConfig;

public interface IGGServerClient {
	
	IGGServerClient run();
	
	IGGServerClient shutdown();
	
	void setConfig(GGServerConfig config);
}
