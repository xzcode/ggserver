package xzcode.ggserver.core.client;

import io.netty.channel.nio.NioEventLoopGroup;
import xzcode.ggserver.core.client.config.GGClientConfig;
import xzcode.ggserver.core.client.starter.IGGClientStarter;
import xzcode.ggserver.core.client.starter.impl.DefaultGGClientStarter;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.config.IGGConfigSupport;
import xzcode.ggserver.core.common.control.IGGContolSupport;
import xzcode.ggserver.core.common.event.invoker.IEventInvokeSupport;
import xzcode.ggserver.core.common.executor.IExecutorSupport;
import xzcode.ggserver.core.common.message.receive.IRequestMessageSupport;
import xzcode.ggserver.core.common.message.send.ISendMessageSupport;
import xzcode.ggserver.core.common.message.send.SendMessageManager;
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
	

	@Override
	public SendMessageManager getSendMessageManager() {
		return this.config.getSendMessageManager();
	}
	@Override
	public NioEventLoopGroup getTaskExecutor() {
		return this.config.getTaskExecutor();
	}
	
}
