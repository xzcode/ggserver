package xzcode.ggserver.core.client;

import xzcode.ggserver.core.client.config.GGClientConfig;
import xzcode.ggserver.core.client.starter.IGGClientStarter;
import xzcode.ggserver.core.client.starter.impl.DefaultGGClientStarter;
import xzcode.ggserver.core.common.config.IGGConfigSupport;
import xzcode.ggserver.core.common.control.IGGContolSupport;
import xzcode.ggserver.core.common.event.invoker.IEventInvokeSupport;
import xzcode.ggserver.core.common.executor.IExecutorSupport;
import xzcode.ggserver.core.common.message.filter.IGGFilterSupport;
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
	IGGFilterSupport,
	IExecutorSupport, 
	IGGSessionSupport, 
	IEventInvokeSupport,
	IGGContolSupport
{
	
	private GGClientConfig config;
	
	private IGGClientStarter clientStarter;
	
	public void connect(String host, int port) {
		clientStarter = new DefaultGGClientStarter(config);		
		clientStarter.connect(host, port);
	}
	

	public GGClient(GGClientConfig config) {
		this.config = config;
	}
	
	public GGClientConfig getConfig() {
		return config;
	}

}
