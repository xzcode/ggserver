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
import xzcode.ggserver.core.session.IGGSessonSupport;

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
	IGGSessonSupport, 
	IEventInvokeSupport,
	IGGContolSupport
{
	
	private GGConfig config;
	

	public GGClient(GGConfig serverConfig) {
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
