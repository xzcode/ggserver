package xzcode.ggserver.core.starter.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import xzcode.ggserver.core.config.GGServerConfig;
import xzcode.ggserver.core.config.scanner.GGComponentScanner;
import xzcode.ggserver.core.constant.GGServerTypeConstants;
import xzcode.ggserver.core.handler.MixedSocketChannelInitializer;
import xzcode.ggserver.core.handler.SocketChannelInitializer;
import xzcode.ggserver.core.handler.WebSocketChannelInitializer;
import xzcode.ggserver.core.starter.IGGServerStarter;

/**
 * socket 服务启动器
 *
 * @author zai
 * 2018-12-20 10:17:44
 */
public class DefaultSocketServerStarter implements IGGServerStarter {
	
	private static final Logger logger = LoggerFactory.getLogger(WebSocketServerStarter.class);
	
	private GGServerConfig config;
	
    public DefaultSocketServerStarter(GGServerConfig config) {
    	
    	this.config = config;
    	
        GGComponentScanner.scan(
        		config.getComponentObjectManager(),
        		config.getRequestMessageManager(),
        		config.getEventInvokerManager(),
        		config.getMessageFilterManager(),
        		config.getScanPackage()
        		);
    }
    
    public IGGServerStarter run() {
    	
        try {
        	
            ServerBootstrap boot = new ServerBootstrap(); // (2)
            
            //设置工作线程组
            boot.group(config.getBossGroup(), config.getWorkerGroup());
            
            if (logger.isDebugEnabled()) {
            	boot.handler(new LoggingHandler(LogLevel.INFO));				
			}else {
				boot.handler(new LoggingHandler(LogLevel.WARN));
			}
            
            //设置channel类型
            boot.channel(NioServerSocketChannel.class); // (3)
            
            //设置消息处理器
            if (config.getServerType().equals(GGServerTypeConstants.MIXED)) {
            	boot.childHandler(new MixedSocketChannelInitializer(config));
			}else if (config.getServerType().equals(GGServerTypeConstants.WEBSOCKET)) {
				boot.childHandler(new WebSocketChannelInitializer(config));
			}else if (config.getServerType().equals(GGServerTypeConstants.TCP)) {
				boot.childHandler(new MixedSocketChannelInitializer(config));
			}else {
				throw new RuntimeException("GGServer ServerType Error!!");
			}
            
            
            boot.option(ChannelOption.SO_BACKLOG, 128);         // (5)
            boot.childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
    
            // 绑定端口并开始接受连接，此时线程将阻塞不会继续往下执行
            ChannelFuture future = boot.bind(config.getPort()).sync(); // (7)
            
            future.channel().closeFuture().sync();
        }catch (Exception e) {
        	
        	throw new RuntimeException("GGServer start failed !! ", e);
        	
		} finally {
			
            config.getBossGroup().shutdownGracefully();
            config.getWorkerGroup().shutdownGracefully();
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
