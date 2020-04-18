package com.xzcode.ggserver.core.client.starter;

import com.xzcode.ggserver.core.client.config.GGClientConfig;
import com.xzcode.ggserver.core.common.future.IGGFuture;
import com.xzcode.ggserver.core.common.session.GGSession;

public interface IGGClientStarter {
	
	IGGFuture connect(String host, int port);
	
	IGGFuture disconnect(GGSession session);
	
	void setConfig(GGClientConfig config);
	
	void shutdown();
}
