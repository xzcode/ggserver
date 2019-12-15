package xzcode.ggserver.core.client.starter;

import xzcode.ggserver.core.client.config.GGClientConfig;
import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.common.session.GGSession;

public interface IGGClientStarter {
	
	GGSession connect(String host, int port);
	
	IGGFuture disconnect(GGSession session);
	
	void setConfig(GGClientConfig config);
	
	void shutdown();
}
