package xzcode.ggserver.core.starter;

import xzcode.ggserver.core.config.GGConfig;

public interface IGGClientStarter {
	
	IGGClientStarter connect(String host, int port);
	
	IGGClientStarter connect();
	
	IGGClientStarter disconnect();
	
	void setConfig(GGConfig config);
}
