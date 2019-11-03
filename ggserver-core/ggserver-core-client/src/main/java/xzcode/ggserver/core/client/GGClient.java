package xzcode.ggserver.core.client;

import xzcode.ggserver.core.client.config.GGClientConfig;
import xzcode.ggserver.core.client.starter.IGGClientStarter;
import xzcode.ggserver.core.client.starter.impl.DefaultClientStarter;
import xzcode.ggserver.core.common.config.IGGConfigSupport;
import xzcode.ggserver.core.common.control.IGGContolSupport;
import xzcode.ggserver.core.common.event.IEventManager;
import xzcode.ggserver.core.common.event.IEventSupport;
import xzcode.ggserver.core.common.executor.IExecutorSupport;
import xzcode.ggserver.core.common.filter.IFilterManager;
import xzcode.ggserver.core.common.filter.IFilterSupport;
import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.common.message.receive.IRequestMessageSupport;
import xzcode.ggserver.core.common.message.send.ISendMessageSupport;
import xzcode.ggserver.core.common.session.IGGSessionSupport;

/**
 * 客户端
 * 
 * @author zzz
 * 2019-09-24 17:19:54
 */
public class GGClient 
implements 
	IGGConfigSupport,
	ISendMessageSupport, 
	IRequestMessageSupport,
	IFilterSupport,
	IExecutorSupport, 
	IGGSessionSupport, 
	IEventSupport,
	IGGContolSupport
{
	
	private GGClientConfig config;
	
	private IGGClientStarter clientStarter;
	
	public IGGFuture connect(String host, int port) {
		return clientStarter.connect(host, port);
	}
	

	public GGClient(GGClientConfig config) {
		this.config = config;
		this.clientStarter = new DefaultClientStarter(config);
	}
	
	public GGClientConfig getConfig() {
		return config;
	}


	@Override
	public IEventManager getEventManager() {
		return config.getEventManager();
	}


	@Override
	public IFilterManager getFilterManager() {
		return config.getFilterManager();
	}

}
