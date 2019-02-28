package xzcode.ggserver.core.handler;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import xzcode.ggserver.core.config.GGServerConfig;
import xzcode.ggserver.core.executor.GGServerTaskExecutor;
import xzcode.ggserver.core.handler.common.InboundCommonHandler;
import xzcode.ggserver.core.handler.common.OutboundCommonHandler;
import xzcode.ggserver.core.handler.idle.IdleHandler;
import xzcode.ggserver.core.handler.tcp.DecodeHandler;
import xzcode.ggserver.core.handler.tcp.EncodeHandler;

/**
 * 默认channel初始化处理器
 * 
 * 
 * @author zai
 * 2017-08-02
 */
public class SocketChannelInitializer extends ChannelInitializer<SocketChannel> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SocketChannelInitializer.class);


	private GGServerConfig config;
	
	
	public SocketChannelInitializer() {
	}
	

	public SocketChannelInitializer(GGServerConfig config) {
		this.config = config;
	}
	
	

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		
		LOGGER.debug("Init Channel:{}", ch);
		
		//Inbound 是顺序执行
   	 
	   	 
	   	 
	   	if (config.getIdleCheckEnabled()) {
	   		
		   	 //空闲事件触发器
		   	 ch.pipeline().addLast(new IdleStateHandler(config.getReaderIdleTime(), config.getWriterIdleTime(), config.getAllIdleTime(), TimeUnit.MILLISECONDS));
		   	 
		   	 //心跳包处理
		   	 ch.pipeline().addLast(new IdleHandler(config));
		   	 
	   	}
	   	
   	 
	   	 //消息解码器
        ch.pipeline().addLast(new DecodeHandler(this.config));
        
        //inbound异常处理
        ch.pipeline().addLast(new InboundCommonHandler(this.config));
        
        
        //Outbound 是反顺序执行
        
        //消息编码器
        ch.pipeline().addLast(new EncodeHandler(this.config));
        //outbound异常处理
        ch.pipeline().addLast(new OutboundCommonHandler());
	}
	

	public GGServerConfig getConfig() {
		return config;
	}
	
	public void setConfig(GGServerConfig config) {
		this.config = config;
	}
	

}