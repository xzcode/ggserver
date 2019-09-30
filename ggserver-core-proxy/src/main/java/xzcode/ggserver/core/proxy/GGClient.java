package xzcode.ggserver.core.proxy;

import io.netty.channel.nio.NioEventLoopGroup;
import xzcode.ggserver.core.proxy.config.GGProxyConfig;
import xzcode.ggserver.core.proxy.config.IGGConfigSupport;
import xzcode.ggserver.core.proxy.control.IGGContolSupport;
import xzcode.ggserver.core.proxy.event.IEventInvokeSupport;
import xzcode.ggserver.core.proxy.executor.IExecutorSupport;
import xzcode.ggserver.core.proxy.message.receive.IRequestMessageSupport;
import xzcode.ggserver.core.proxy.message.send.ISendMessageSupport;
import xzcode.ggserver.core.proxy.message.send.SendMessageManager;
import xzcode.ggserver.core.proxy.session.IGGSessonSupport;

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
	
	private GGProxyConfig config;
	

	public GGClient(GGProxyConfig serverConfig) {
		this.config = serverConfig;
	}
	
	public GGProxyConfig getConfig() {
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
