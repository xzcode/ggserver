package com.xzcode.ggserver.core.client;

import com.xzcode.ggserver.core.client.config.GGClientConfig;
import com.xzcode.ggserver.core.client.starter.IGGClientStarter;
import com.xzcode.ggserver.core.client.starter.impl.DefaultClientStarter;
import com.xzcode.ggserver.core.common.config.GGConfigSupport;
import com.xzcode.ggserver.core.common.control.IGGContolSupport;
import com.xzcode.ggserver.core.common.future.IGGFuture;

/**
 * 客户端
 * 
 * @author zzz
 * 2019-09-24 17:19:54
 */
public class GGClient 
implements 
	GGConfigSupport<GGClientConfig>,
	IGGContolSupport
{
	
	private GGClientConfig config;
	
	private IGGClientStarter clientStarter;
	
	public IGGFuture connect(String host, int port) {
		return clientStarter.connect(host, port);
	}
	

	public GGClient(GGClientConfig config) {
		this.config = config;
		if (!this.config.isInited()) {
			this.config.init();
		}
		this.clientStarter = new DefaultClientStarter(config);
	}
	
	@Override
	public GGClientConfig getConfig() {
		return config;
	}
	
	public void shutdown() {
		clientStarter.shutdown();
	}

}
