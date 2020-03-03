package xzcode.ggserver.core.server.starter.impl;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import xzcode.ggserver.core.common.constant.ProtocolTypeConstants;
import xzcode.ggserver.core.common.future.GGNettyFuture;
import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.common.handler.MixedSocketChannelInitializer;
import xzcode.ggserver.core.common.handler.TcpChannelInitializer;
import xzcode.ggserver.core.common.handler.WebSocketChannelInitializer;
import xzcode.ggserver.core.common.utils.logger.GGLoggerUtil;
import xzcode.ggserver.core.server.config.GGServerConfig;
import xzcode.ggserver.core.server.starter.IGGServerStarter;

/**
 * socket 服务启动器
 *
 * @author zai
 * 2018-12-20 10:17:44
 */
public class DefaultGGServerStarter implements IGGServerStarter {
	
	protected static final Logger logger = LoggerFactory.getLogger(DefaultGGServerStarter.class);
	
	protected GGServerConfig config;
	
	protected  Channel serverChannel;
	
    public DefaultGGServerStarter(GGServerConfig config) {
    	
    	this.config = config;
    }
    
    public IGGFuture start() {
    	
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
            if (config.getProtocolType().equals(ProtocolTypeConstants.MIXED)) {
            	boot.childHandler(new MixedSocketChannelInitializer(config));
    		}
            else if (config.getProtocolType().equals(ProtocolTypeConstants.TCP)) {
            	boot.childHandler(new TcpChannelInitializer(config));
            }
            else if (config.getProtocolType().equals(ProtocolTypeConstants.WEBSOCKET)) {
            	boot.childHandler(new WebSocketChannelInitializer(config));
            }
            
            boot.option(ChannelOption.SO_BACKLOG, config.getSoBacklog()); 
            boot.option(ChannelOption.SO_REUSEADDR, true); 
            
            boot.childOption(ChannelOption.SO_REUSEADDR, config.isSoReuseaddr()); 
            boot.childOption(ChannelOption.TCP_NODELAY, true); 
            
            boot.childOption(ChannelOption.SO_KEEPALIVE, true); 
    
            ChannelFuture future = boot.bind(config.getPort());
            
            future.addListener((f) -> {
            	String logoString = getLogoString();
            	if (f.isSuccess()) {
            		GGLoggerUtil.getLogger().warn("{}\n{} started successfully on port {}!\n", logoString, config.getServerName(), config.getPort());
				}else {
					GGLoggerUtil.getLogger().warn("{}\n{} failed to start on port {}!\nError:{}\n", logoString, config.getServerName(), config.getPort(),f.cause());
					
					shutdown();
				}
            });
            
            
            serverChannel = future.channel();
            
            return new GGNettyFuture(future);
            
        }catch (Exception e) {
        	shutdown();
        	throw new RuntimeException("GGServer start failed !! ", e);
        }
    }
    
    private String getLogoString() {
    	StringBuilder sb = new StringBuilder(512);
    	try (
    			InputStream is = this.getClass().getResourceAsStream("/ggserver-logo.txt");
			){
    		byte[] buff = new byte[1024];
    		int len = -1;
    		while ((len = is.read(buff)) != -1) {
				String logoString = new String(buff, 0, len, config.getCharset());
				sb.append(logoString);
			}
		} catch (Exception e) {
			logger.error("GGServer print logo Error!", e);
		}
    	return sb.toString();
    }
    
    /**
     * 关闭socket server
     * 
     * 
     * @author zai
     * 2017-07-27
     */
    public void shutdown() {
    	if (config.getBossGroup() != null) {
    		config.getBossGroup().shutdownGracefully();			
		}
    	if (config.getWorkerGroup() != null) {
    		config.getWorkerGroup().shutdownGracefully();		
		}
	}
    
    public void setConfig(GGServerConfig config) {
		this.config = config;
	}
    public GGServerConfig getConfig() {
		return config;
	}
    
}
