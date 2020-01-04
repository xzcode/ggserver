package xzcode.ggserver.core.client.starter.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.Future;
import xzcode.ggserver.core.client.config.GGClientConfig;
import xzcode.ggserver.core.client.pool.DefaultChannelPoolHandler;
import xzcode.ggserver.core.client.starter.IGGClientStarter;
import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.common.handler.MixedSocketChannelInitializer;
import xzcode.ggserver.core.common.handler.TcpChannelInitializer;
import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.core.common.session.impl.DefaultChannelSession;

public class DefaultClientStarter implements IGGClientStarter {
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultClientStarter.class);
	
	private GGClientConfig config;
	
	
	
    public DefaultClientStarter(GGClientConfig config) {
    	this.config = config;
    	init();
    }
    
    public void init() {
    	
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
        boot.handler(new MixedSocketChannelInitializer(config));
        
        boot.option(ChannelOption.SO_BACKLOG, config.getSoBacklog());  
        boot.option(ChannelOption.TCP_NODELAY, true);  
        
      //判断是否开启通道池
        if (config.isChannelPoolEnabled()) {
        	
        	boot.remoteAddress(config.getHost(), config.getPort());
        	
			if (config.getChannelPoolHandler() == null) {
				config.setChannelPoolHandler(new DefaultChannelPoolHandler(this.config));
			}
			if (config.getChannelPool() == null) {
				config.setChannelPool(new FixedChannelPool(boot, config.getChannelPoolHandler(), config.getChannelMaxConnections()));
			}
		}
	}
    
    
    public GGSession connect(String host, int port) {
        try {
        	Bootstrap boot = config.getBootstrap();
            // 连接服务器
            ChannelFuture future = boot.connect(host, port).sync();
            Channel channel = future.channel();
            GGSession session = new DefaultChannelSession(channel, channel.id().asLongText(), config);
            return session;
        }catch (Exception e) {
        	throw new RuntimeException("GGClient connect error !! ", e);
		}
    }
    

	@Override
	public IGGFuture disconnect(GGSession session) {
		return session.disconnect();
	}
	
	public void shutdown() {
		try {
			config.getWorkerGroup().shutdownGracefully().sync();
		} catch (Exception e) {
			logger.error("Shutdown error!", e);
		}
	}
    
    public void setConfig(GGClientConfig config) {
		this.config = config;
	}
    public GGClientConfig getConfig() {
		return config;
	}

    
}
