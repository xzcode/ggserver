package xzcode.ggserver.core.client.starter.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import xzcode.ggserver.core.client.config.GGClientConfig;
import xzcode.ggserver.core.client.starter.IGGClientStarter;
import xzcode.ggserver.core.common.future.GGFuture;
import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.common.handler.SocketChannelInitializer;

public class DefaultClientStarter implements IGGClientStarter {
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultClientStarter.class);
	
	private GGClientConfig config;
	private Channel channel;
	
    public DefaultClientStarter(GGClientConfig config) {
    	this.config = config;
    }
    

	@Override
	public IGGFuture connect() {
		return connect(config.getHost(), config.getPort());
	}
    
    public IGGFuture connect(String host, int port) {
    	
        try {
        	
        	config.setHost(host);
        	config.setPort(port);
        	
        	Bootstrap boot = new Bootstrap();
            
            //设置工作线程组
            boot.group(config.getWorkerGroup());
            
            if (logger.isDebugEnabled()) {
            	boot.handler(new LoggingHandler(LogLevel.INFO));				
			}else {
				boot.handler(new LoggingHandler(LogLevel.WARN));
			}
            
            //设置channel类型
            boot.channel(NioSocketChannel.class); 
            
            //设置消息处理器
            boot.handler(new SocketChannelInitializer(config));
            
            boot.option(ChannelOption.SO_BACKLOG, config.getSoBacklog());         
    
            // 连接服务器
            ChannelFuture future = boot.connect(config.getHost(), config.getPort());
            channel = future.channel();
            
            
            //监听关闭事件
            channel.closeFuture().addListener((e) -> {
            	if (config.isAutoShutdown()) {
    				config.getWorkerGroup().shutdownGracefully();				
    			}
            });
            return new GGFuture(future);
        }catch (Exception e) {
        	throw new RuntimeException("GGClient connect failed !! ", e);
		}
    }
    

	@Override
	public IGGFuture disconnect() {
		ChannelFuture channelFuture = channel.close();
		
		if (config.getWorkerGroup() != null && config.isAutoShutdown()) {
    		config.getWorkerGroup().shutdownGracefully();			
		}
		return new GGFuture(channelFuture);
	}
    
    public void setConfig(GGClientConfig config) {
		this.config = config;
	}
    public GGClientConfig getConfig() {
		return config;
	}

    
}
