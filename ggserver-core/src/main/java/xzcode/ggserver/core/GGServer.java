package xzcode.ggserver.core;

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
	
	private GGConfig config;
	

	public GGServer(GGConfig serverConfig) {
		this.config = serverConfig;
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
