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
import xzcode.ggserver.core.common.config.scanner.GGComponentScanner;
import xzcode.ggserver.core.common.handler.SocketChannelInitializer;

public class DefaultGGClientStarter implements IGGClientStarter {
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultGGClientStarter.class);
	
	private GGClientConfig config;
	private Channel channel;
	
    public DefaultGGClientStarter(GGClientConfig config) {
    	
    	this.config = config;
    	if (config.getScanPackage() != null && config.getScanPackage().length > 0) {
    		GGComponentScanner.scan(config);
		}
    }
    

	@Override
	public IGGClientStarter connect() {
		return connect(config.getHost(), config.getPort());
	}
    
    public IGGClientStarter connect(String host, int port) {
    	
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
            ChannelFuture future = boot.connect(config.getHost(), config.getPort()).sync();
            channel = future.channel();
            future.addListener((f) -> {
            	if (f.isSuccess()) {
            		if (logger.isInfoEnabled()) {
                    	logger.info("GGClient connected --> [{}] ", (config.getHost() + ":" + config.getPort()));
                    }
				}
            });
            
            //监听关闭事件
            future.channel().closeFuture().addListener((e) -> {
            	if (config.isAutoShutdown()) {
    				config.getWorkerGroup().shutdownGracefully();				
    			}
            });
        }catch (Exception e) {
        	
        	throw new RuntimeException("GGClient connect failed !! ", e);
        	
		} finally {
			if (config.isAutoShutdown()) {
				config.getWorkerGroup().shutdownGracefully();				
			}
        }
        return this;
    }
    

	@Override
	public IGGClientStarter disconnect() {
		channel.close();
		if (config.getWorkerGroup() != null && config.isAutoShutdown()) {
    		config.getWorkerGroup().shutdownGracefully();			
		}
		return null;
	}
    
    public void setConfig(GGClientConfig config) {
		this.config = config;
	}
    public GGClientConfig getConfig() {
		return config;
	}

    
}
