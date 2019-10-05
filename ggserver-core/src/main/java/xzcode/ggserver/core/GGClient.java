package xzcode.ggserver.core;

import java.util.concurrent.TimeUnit;

import io.netty.channel.nio.NioEventLoopGroup;
import xzcode.ggserver.core.config.GGConfig;
import xzcode.ggserver.core.config.IGGConfigSupport;
import xzcode.ggserver.core.control.IGGContolSupport;
import xzcode.ggserver.core.event.IEventInvokeSupport;
import xzcode.ggserver.core.executor.IExecutorSupport;
import xzcode.ggserver.core.message.receive.IRequestMessageSupport;
import xzcode.ggserver.core.message.send.ISendMessageSupport;
import xzcode.ggserver.core.message.send.SendMessageManager;
import xzcode.ggserver.core.session.IGGSessionSupport;
import xzcode.ggserver.core.starter.IGGClientStarter;
import xzcode.ggserver.core.starter.impl.DefaultGGClientStarter;

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
	
	private GGConfig config;
	
	private IGGClientStarter clientStarter;
	
	public void connect(String host, int port) {
		clientStarter = new DefaultGGClientStarter(config);		
		clientStarter.connect(host, port);
	}
	

	public GGClient(GGConfig config) {
		this.config = config;
		config.setClient(true);
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
