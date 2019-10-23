package xzcode.ggserver.core.server;

import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.config.IGGConfigSupport;
import xzcode.ggserver.core.common.control.IGGContolSupport;
import xzcode.ggserver.core.common.event.invoker.IEventInvokeSupport;
import xzcode.ggserver.core.common.executor.IExecutorSupport;
import xzcode.ggserver.core.common.message.filter.IGGFilterSupport;
import xzcode.ggserver.core.common.message.receive.IRequestMessageSupport;
import xzcode.ggserver.core.common.message.send.ISendMessageSupport;
import xzcode.ggserver.core.common.session.IGGSessionSupport;
import xzcode.ggserver.core.server.config.GGServerConfig;
import xzcode.ggserver.core.server.starter.IGGServerStarter;
import xzcode.ggserver.core.server.starter.impl.DefaultGGServerStarter;

/**
 * GGServer服务类
 * 
 * @author zzz
 * 2019-10-12 10:49:53
 */
public class GGServer 
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
	
	private GGServerConfig config;
	
	private IGGServerStarter serverStarter;
	
	public void start() {
		this.shutdown();
		this.serverStarter = new DefaultGGServerStarter(config);
		this.serverStarter.start();
	}
	public void shutdown() {
		if (this.serverStarter != null) {
			this.serverStarter.shutdown();
		}
	}

	public GGServer(GGServerConfig serverConfig) {
		this.config = serverConfig;
	}
	public GGConfig getConfig() {
		return config;
	}

}
