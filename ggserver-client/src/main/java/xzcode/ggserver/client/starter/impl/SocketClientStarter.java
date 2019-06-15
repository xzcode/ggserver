package xzcode.ggserver.client.starter.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import xzcode.ggserver.client.config.GGClientConfig;
import xzcode.ggserver.client.constant.GGServerTypeConstants;
import xzcode.ggserver.client.handler.SocketChannelInitializer;

/**
 * socket 客户端启动器
 *
 * @author zai
 * 2018-12-20 10:17:44
 */
public class SocketClientStarter {
	
	private static final Logger logger = LoggerFactory.getLogger(SocketClientStarter.class);
	
	private GGClientConfig config;
	
    public SocketClientStarter(GGClientConfig config) {
    	this.config = config;
    }
    
    public void run() {
    	
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
            ChannelFuture future = boot.connect(config.getHost(), config.getPort()).sync(); // (7)
            Channel channel = future.channel();
            config.setChannel(channel);
            future.addListener((f) -> {
            	if (f.isSuccess()) {
            		channel.writeAndFlush(GGServerTypeConstants.TCP.getBytes());
				}
            });
            channel.closeFuture().sync();
        }catch (Exception e) {
        	
        	throw new RuntimeException("GGServer Client start failed !! ", e);
        	
		} finally {
			
            config.getWorkerGroup().shutdownGracefully();
            
        }
    }
    
    /**
     * 关闭socket server
     * 
     * 
     * @author zai
     * 2017-07-27
     */
    public void shutdown() {
    	if (config.getWorkerGroup() != null) {
    		config.getWorkerGroup().shutdownGracefully();			
		}
    	
	}
    
    public void setConfig(GGClientConfig config) {
		this.config = config;
	}
    public GGClientConfig getConfig() {
		return config;
	}
}
