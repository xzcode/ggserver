package com.xzcode.socket.core.starter.impl;


import com.xzcode.socket.core.config.SocketServerConfig;
import com.xzcode.socket.core.config.scanner.SocketComponentScanner;
import com.xzcode.socket.core.executor.SocketServerTaskExecutor;
import com.xzcode.socket.core.executor.factory.EventLoopGroupThreadFactory;
import com.xzcode.socket.core.handler.SocketChannelInitializer;
import com.xzcode.socket.core.starter.SocketServerStarter;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class DefaultSocketServerStarter implements SocketServerStarter {
	
	//private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSocketServerStarter.class);
	
	private SocketServerConfig config;
	
	private EventLoopGroup bossGroup;
	
	private EventLoopGroup workerGroup;
	
	private SocketServerTaskExecutor taskExecutor;
	
    
    public DefaultSocketServerStarter(SocketServerConfig config) {
        
    	this.config = config;
        
        this.taskExecutor = new SocketServerTaskExecutor(config);
        
        SocketComponentScanner.scan(
        		config.getComponentObjectManager(),
        		config.getMessageMethodInvokeManager(),
        		config.getEventInvokerManager(),
        		config.getMessageFilterManager(),
        		config.getScanPackage()
        		);
        
    }
    
    public SocketServerStarter run() {
    	
        bossGroup = new NioEventLoopGroup(config.getBossThreadSize(),new EventLoopGroupThreadFactory("Socket Boss Group"));// (1)
        
        workerGroup = new NioEventLoopGroup(config.getBossThreadSize(),new EventLoopGroupThreadFactory("Socket Worker Group"));
        
        try {
        	
            ServerBootstrap boot = new ServerBootstrap(); // (2)
            
            //设置工作线程组
            boot.group(bossGroup, workerGroup);
            //设置channel类型
            boot.channel(NioServerSocketChannel.class); // (3)
            
            //设置消息处理器
            boot.childHandler(new SocketChannelInitializer(
            		config, 
            		taskExecutor
            		));
            
            boot.option(ChannelOption.SO_BACKLOG, 128);         // (5)
            boot.childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
    
            // 绑定端口并开始接受连接，此时线程将阻塞不会继续往下执行
            ChannelFuture f = boot.bind(config.getPort()).sync(); // (7)
    
            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } catch (Exception e) {
        	throw new RuntimeException("Socket server start failed !! ", e);
		}finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
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
    public SocketServerStarter shutdown() {
    	if (workerGroup != null) {
    		workerGroup.shutdownGracefully();			
		}
    	if (bossGroup != null) {
    		bossGroup.shutdownGracefully();			
		}
        return this;
	}
    
    public void setConfig(SocketServerConfig config) {
		this.config = config;
	}
    public SocketServerConfig getConfig() {
		return config;
	}
    
    public static void main(String[] args) throws Exception {
    	
		new DefaultSocketServerStarter(new SocketServerConfig()).run();
	}
}
