package xzcode.ggserver.socket.starter.impl;


import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import xzcode.ggserver.socket.config.GGSocketConfig;
import xzcode.ggserver.socket.config.scanner.GGComponentScanner;
import xzcode.ggserver.socket.executor.factory.EventLoopGroupThreadFactory;
import xzcode.ggserver.socket.handler.WebSocketChannelInitializer;
import xzcode.ggserver.socket.starter.SocketServerStarter;

/**
 * websocket 服务启动器
 *
 * @author zai
 * 2018-12-20 10:17:44
 */
public class WebSocketServerStarter implements SocketServerStarter {
	
	private static final Logger logger = LoggerFactory.getLogger(WebSocketServerStarter.class);
	
	private GGSocketConfig config;
	
	private EventLoopGroup bossGroup;
	
	private EventLoopGroup workerGroup;
	
    private SslContext sslCtx;

    public static SSLEngine sslEngine;
    
    public SSLContext createSSLContext(String type ,String path ,String password) throws Exception {  
        KeyStore ks = KeyStore.getInstance(type); /// "JKS"  
        InputStream ksInputStream = new FileInputStream(path); /// 证书存放地址  
        ks.load(ksInputStream, password.toCharArray());  
        //KeyManagerFactory充当基于密钥内容源的密钥管理器的工厂。  
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());//getDefaultAlgorithm:获取默认的 KeyManagerFactory 算法名称。  
        kmf.init(ks, password.toCharArray());  
        //SSLContext的实例表示安全套接字协议的实现，它充当用于安全套接字工厂或 SSLEngine 的工厂。  
        SSLContext sslContext = SSLContext.getInstance("TLS");  
        sslContext.init(kmf.getKeyManagers(), null, null);  
        return sslContext;  
    } 
    
    public WebSocketServerStarter(GGSocketConfig config) {
    	
    	if (config.isUseSSL()) {
    		//TODO 完成ssl配置
    	}
    	
    	config.setServerType(GGSocketConfig.ServerTypeConstants.WEBSOCKET);
    	this.config = config;
    	
        GGComponentScanner.scan(
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
            
            boot.handler(new LoggingHandler(LogLevel.INFO));
            
            //设置channel类型
            boot.channel(NioServerSocketChannel.class); // (3)
            
            //设置消息处理器
            boot.childHandler(new WebSocketChannelInitializer(
            		config, 
            		sslCtx
            		));
            
            boot.option(ChannelOption.SO_BACKLOG, 128);         // (5)
            boot.childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
    
            // 绑定端口并开始接受连接，此时线程将阻塞不会继续往下执行
            ChannelFuture f = boot.bind(config.getPort()).sync(); // (7)
    
            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        }catch (Exception e) {
        	throw new RuntimeException("Socket server start failed !! ", e);
		} finally {
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
    
    public void setConfig(GGSocketConfig config) {
		this.config = config;
	}
    public GGSocketConfig getConfig() {
		return config;
	}
    
    public static void main(String[] args) throws Exception {
    	
		new WebSocketServerStarter(new GGSocketConfig()).run();
	}
}
