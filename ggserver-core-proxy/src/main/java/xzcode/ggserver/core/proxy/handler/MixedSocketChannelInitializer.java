package xzcode.ggserver.core.proxy.handler;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import xzcode.ggserver.core.proxy.config.GGProxyConfig;
import xzcode.ggserver.core.proxy.handler.common.InboundCommonHandler;
import xzcode.ggserver.core.proxy.handler.common.OutboundCommonHandler;
import xzcode.ggserver.core.proxy.handler.idle.IdleHandler;
import xzcode.ggserver.core.proxy.handler.tcp.TcpSocketSelectHandler;

/**
 * 混合tcp与websocket 初始化处理器
 * 
 * @author zai
 * 2019-06-15 14:31:58
 */
public class MixedSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
	
	private static final Logger logger = LoggerFactory.getLogger(MixedSocketChannelInitializer.class);
	
	private GGProxyConfig config;
	
	public MixedSocketChannelInitializer() {
	}
	
	public MixedSocketChannelInitializer(GGProxyConfig config) {
		this.config = config;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		
	   	if (config.getIdleCheckEnabled()) {
		   	 //空闲事件触发器
		   	 ch.pipeline().addLast(new IdleStateHandler(config.getReaderIdleTime(), config.getWriterIdleTime(), config.getAllIdleTime(), TimeUnit.MILLISECONDS));
		   	 
		   	 //心跳包处理
		   	 ch.pipeline().addLast(new IdleHandler(this.config));
	   	}
	   	
	   	
	   	ch.pipeline().addLast("TcpSocketSelectHandler", new TcpSocketSelectHandler(config));
	   	
	   	//inbound异常处理
	   	ch.pipeline().addLast("InboundCommonHandler", new InboundCommonHandler(this.config));
        //outbound异常处理
        ch.pipeline().addLast("OutboundCommonHandler", new OutboundCommonHandler());
        
        
		
	}


	public GGProxyConfig getConfig() {
		return config;
	}
	
	public void setConfig(GGProxyConfig config) {
		this.config = config;
	}

}
