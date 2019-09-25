package xzcode.ggserver.core.starter.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import xzcode.ggserver.core.config.GGConfig;
import xzcode.ggserver.core.config.scanner.GGComponentScanner;
import xzcode.ggserver.core.handler.SocketChannelInitializer;
import xzcode.ggserver.core.starter.IGGClientStarter;

/**
 * socket 服务启动器
 *
 * @author zai
 * 2018-12-20 10:17:44
 */
public class DefaultSocketClientStarter implements IGGClientStarter {
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultSocketClientStarter.class);
	
	private GGConfig config;
	private Channel channel;
	
    public DefaultSocketClientStarter(GGConfig config) {
    	
    	this.config = config;
    	
        GGComponentScanner.scan(
        		config.getComponentObjectManager(),
        		config.getRequestMessageManager(),
        		config.getEventInvokerManager(),
        		config.getMessageFilterManager(),
        		config.getScanPackage()
        		);
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
            
            boot.option(ChannelOption.SO_BACKLOG, 128);         
    
            // 连接服务器
            ChannelFuture future = boot.connect(config.getHost(), config.getPort()).sync();
            channel = future.channel();
            future.addListener((f) -> {
            	if (f.isSuccess()) {
            		if (logger.isInfoEnabled()) {
                    	logger.info("GGClient connected ==> [{}] ", (config.getHost() + ":" + config.getPort()));
                    }
				}
            });
            
            future.channel().closeFuture().sync();
        }catch (Exception e) {
        	
        	throw new RuntimeException("GGClient connect failed !! ", e);
        	
		} finally {
            config.getWorkerGroup().shutdownGracefully();
        }
        return this;
    }
    

	@Override
	public IGGClientStarter disconnect() {
		channel.close();
		if (config.getWorkerGroup() != null) {
    		config.getWorkerGroup().shutdownGracefully();			
		}
		return null;
	}
    
    public void setConfig(GGConfig config) {
		this.config = config;
	}
    public GGConfig getConfig() {
		return config;
	}

    
}
