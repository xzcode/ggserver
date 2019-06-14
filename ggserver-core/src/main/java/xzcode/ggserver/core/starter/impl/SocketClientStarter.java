package xzcode.ggserver.core.starter.impl;


import javax.net.ssl.SSLEngine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import xzcode.ggserver.core.config.GGServerConfig;
import xzcode.ggserver.core.config.scanner.GGComponentScanner;
import xzcode.ggserver.core.handler.SocketChannelInitializer;
import xzcode.ggserver.core.starter.IGGServerClient;

/**
 * socket 服务启动器
 *
 * @author zai
 * 2018-12-20 10:17:44
 */
public class SocketClientStarter implements IGGServerClient {
	
	private static final Logger logger = LoggerFactory.getLogger(WebSocketServerStarter.class);
	
	private GGServerConfig config;
	
    private SslContext sslCtx;

    public static SSLEngine sslEngine;
    
    
    public SocketClientStarter(GGServerConfig config) {
    	
    	config.setServerType(GGServerConfig.ServerTypeConstants.SOCKET);
    	this.config = config;
    	
        GGComponentScanner.scan(
        		config.getComponentObjectManager(),
        		config.getRequestMessageManager(),
        		config.getEventInvokerManager(),
        		config.getMessageFilterManager(),
        		config.getScanPackage()
        		);
    }
    
    public IGGServerClient run() {
    	
        try {
        	
            Bootstrap boot = new Bootstrap(); // (2)
            
            //设置工作线程组
            
            if (logger.isDebugEnabled()) {
            	boot.handler(new LoggingHandler(LogLevel.INFO));				
			}else {
				boot.handler(new LoggingHandler(LogLevel.WARN));
			}
            
            //设置channel类型
            boot.channel(NioSocketChannel.class); // (3)
            
            //设置消息处理器
			boot.handler(new SocketChannelInitializer(config));
            
            boot.group(config.getWorkerGroup());
    
            // 连接服务器
            ChannelFuture f = boot.connect(config.getHost(), config.getPort()).sync(); // (7)
    
            f.channel().closeFuture().sync();
        }catch (Exception e) {
        	
        	throw new RuntimeException("GGServer Client start failed !! ", e);
        	
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
    public IGGServerClient shutdown() {
    	if (config.getWorkerGroup() != null) {
    		config.getWorkerGroup().shutdownGracefully();			
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
    public static void main(String[] args) {
    	GGServerConfig config = new GGServerConfig();
    	config.setPort(9999);
    	config.setHost("localhost");
		SocketClientStarter client = new SocketClientStarter(config);
		client.run();
		config.getSendMessageManager().send("xxxxxxxxx");
		
	}
}
