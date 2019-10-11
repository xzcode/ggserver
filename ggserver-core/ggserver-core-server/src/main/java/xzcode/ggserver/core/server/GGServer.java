package xzcode.ggserver.core.server;

import io.netty.channel.nio.NioEventLoopGroup;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.config.IGGConfigSupport;
import xzcode.ggserver.core.common.control.IGGContolSupport;
import xzcode.ggserver.core.common.event.invoker.IEventInvokeSupport;
import xzcode.ggserver.core.common.executor.IExecutorSupport;
import xzcode.ggserver.core.common.message.receive.IRequestMessageSupport;
import xzcode.ggserver.core.common.message.send.ISendMessageSupport;
import xzcode.ggserver.core.common.message.send.SendMessageManager;
import xzcode.ggserver.core.common.session.IGGSessionSupport;
import xzcode.ggserver.core.server.config.GGServerConfig;
import xzcode.ggserver.core.server.starter.IGGServerStarter;
import xzcode.ggserver.core.server.starter.impl.DefaultGGServerStarter;

/**
 * socket服务工具
 * 
 * 
 * @author zai 2017-08-04
 */
public class GGServer 
implements 
	IGGConfigSupport,
	ISendMessageSupport, 
	IRequestMessageSupport,
	IExecutorSupport, 
	IGGSessionSupport, 
	IEventInvokeSupport,
	IGGContolSupport
{
	
	private GGServerConfig config;
	
	private IGGServerStarter serverStarter;
	
	public void init() {
		if (serverStarter == null) {
			serverStarter = new DefaultGGServerStarter(config);
		}
	}
	
	public void start() {
		this.serverStarter.run();
	}
	

	public GGServer(GGServerConfig serverConfig) {
		this.config = serverConfig;
		init();
	}
	public GGConfig getConfig() {
		return config;
	}

	@Override
	public SendMessageManager getSendMessageManager() {
		return this.config.getSendMessageManager();
	}
	@Override
	public NioEventLoopGroup getTaskExecutor() {
		return this.config.getTaskExecutor();
	}
	
	

}
