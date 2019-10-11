package xzcode.ggserver.core.common.handler;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.handler.idle.IdleHandler;

/**
 * 混合tcp与websocket 初始化处理器
 * 
 * @author zai
 * 2019-06-15 14:31:58
 */
public class MixedSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
	
	private static final Logger logger = LoggerFactory.getLogger(MixedSocketChannelInitializer.class);
	
	private GGConfig config;
	
	public MixedSocketChannelInitializer() {
	}
	
	public MixedSocketChannelInitializer(GGConfig config) {
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
	   	
	   	
	   	ch.pipeline().addLast(new SocketSelectHandler(config));
	   	
        
        
		
	}


	public GGConfig getConfig() {
		return config;
	}
	
	public void setConfig(GGConfig config) {
		this.config = config;
	}

}
