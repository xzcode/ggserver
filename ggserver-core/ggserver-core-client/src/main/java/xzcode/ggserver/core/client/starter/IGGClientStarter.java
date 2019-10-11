package xzcode.ggserver.core.client.starter;

import xzcode.ggserver.core.client.config.GGClientConfig;

public interface IGGClientStarter {
	
	IGGClientStarter connect(String host, int port);
	
	IGGClientStarter connect();
	
	IGGClientStarter disconnect();
	
	void setConfig(GGClientConfig config);
}
