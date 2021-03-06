package xzcode.ggserver.core.client;

import xzcode.ggserver.core.client.config.GGClientConfig;
import xzcode.ggserver.core.client.starter.IGGClientStarter;
import xzcode.ggserver.core.client.starter.impl.DefaultClientStarter;
import xzcode.ggserver.core.common.config.IGGConfigSupport;
import xzcode.ggserver.core.common.control.IGGContolSupport;
import xzcode.ggserver.core.common.future.IGGFuture;

/**
 * 客户端
 * 
 * @author zzz
 * 2019-09-24 17:19:54
 */
public class GGClient 
implements 
	IGGConfigSupport<GGClientConfig>,
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
