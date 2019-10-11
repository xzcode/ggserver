package xzcode.ggserver.core.server.starter.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import xzcode.ggserver.core.common.config.scanner.GGComponentScanner;
import xzcode.ggserver.core.common.handler.MixedSocketChannelInitializer;
import xzcode.ggserver.core.server.config.GGServerConfig;
import xzcode.ggserver.core.server.starter.IGGServerStarter;

/**
 * socket 服务启动器
 *
 * @author zai
 * 2018-12-20 10:17:44
 */
public class DefaultGGServerStarter implements IGGServerStarter {
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultGGServerStarter.class);
	
	private GGServerConfig config;
	
    public DefaultGGServerStarter(GGServerConfig config) {
    	
    	this.config = config;
    	if (config.getScanPackage() != null && config.getScanPackage().length > 0) {
    		GGComponentScanner.scan(
    				config.getComponentObjectManager(),
    				config.getRequestMessageManager(),
    				config.getEventInvokerManager(),
    				config.getMessageFilterManager(),
    				config.getScanPackage()
    				);
		}
    }
    
    public IGGServerStarter run() {
    	
        try {
        	
            ServerBootstrap boot = new ServerBootstrap(); 
            
            //设置工作线程组
            boot.group(config.getBossGroup(), config.getWorkerGroup());
            
            if (logger.isDebugEnabled()) {
            	boot.handler(new LoggingHandler(LogLevel.INFO));				
			}else {
				boot.handler(new LoggingHandler(LogLevel.WARN));
			}
            
            //设置channel类型
            boot.channel(NioServerSocketChannel.class);
            
            //设置消息处理器
            boot.childHandler(new MixedSocketChannelInitializer(config));
            
            
            boot.option(ChannelOption.SO_BACKLOG, config.getSoBacklog()); 
            
            boot.childOption(ChannelOption.SO_KEEPALIVE, true); 
    
            ChannelFuture future = boot.bind(config.getPort()).sync(); 
            
            future.channel().closeFuture().sync();
            
        }catch (Exception e) {
        	
        	throw new RuntimeException("GGServer start failed !! ", e);
        	
		} finally {
			if (config.isAutoShutdown()) {
				config.getBossGroup().shutdownGracefully();
				config.getWorkerGroup().shutdownGracefully();
			}
        }
        return this;
    }
    
    /**
     * 关闭socket server
     * 
     * 
     * @author zai
     * 2017-07-27
     */
    public IGGServerStarter shutdown() {
    	if (config.getBossGroup() != null) {
    		config.getBossGroup().shutdownGracefully();			
		}
    	if (config.getWorkerGroup() != null) {
    		config.getWorkerGroup().shutdownGracefully();			
		}
        return this;
	}
    
    public void setConfig(GGServerConfig config) {
		this.config = config;
	}
    public GGServerConfig getConfig() {
		return config;
	}
    
}
