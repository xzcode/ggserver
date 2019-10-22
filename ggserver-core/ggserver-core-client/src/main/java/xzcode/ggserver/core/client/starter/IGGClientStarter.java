package xzcode.ggserver.core.client.starter;

import xzcode.ggserver.core.client.config.GGClientConfig;
import xzcode.ggserver.core.common.future.IGGFuture;

public interface IGGClientStarter {
	
	IGGFuture connect(String host, int port);
	
	IGGFuture connect();
	
	IGGFuture disconnect();
	
	void setConfig(GGClientConfig config);
}
