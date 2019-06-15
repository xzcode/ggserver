package xzcode.ggserver.client.starter;

import xzcode.ggserver.client.config.GGClientConfig;

public interface IGGServerClient {
	
	IGGServerClient run();
	
	IGGServerClient shutdown();
	
	void setConfig(GGClientConfig config);
}
