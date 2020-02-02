package xzcode.ggserver.core.client.config;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.pool.ChannelPool;
import io.netty.channel.pool.ChannelPoolHandler;
import xzcode.ggserver.core.client.session.ClientChannelSessionFactory;
import xzcode.ggserver.core.common.config.GGConfig;

/**
 * 客户端配置
 * 
 * @author zai
 * 2019-12-12 19:41:19
 */
public class GGClientConfig extends GGConfig{
	
	private boolean channelPoolEnabled;
	
	private ChannelPool channelPool;
	
	private ChannelPoolHandler channelPoolHandler;
	
	private int channelPoolMaxSize = 2;
	
	private Bootstrap bootstrap = new Bootstrap();
	
	private String host;
	
	private int port;
	
	@Override
	public void init() {
		
		this.sessionFactory = new ClientChannelSessionFactory(this);
		super.init();
		
		
	}

	public boolean isChannelPoolEnabled() {
		return channelPoolEnabled;
	}

	public void setChannelPoolEnabled(boolean channelPoolEnabled) {
		this.channelPoolEnabled = channelPoolEnabled;
	}

	public ChannelPool getChannelPool() {
		return channelPool;
	}

	public void setChannelPool(ChannelPool channelPool) {
		this.channelPool = channelPool;
	}

	public ChannelPoolHandler getChannelPoolHandler() {
		return channelPoolHandler;
	}

	public void setChannelPoolHandler(ChannelPoolHandler channelPoolHandler) {
		this.channelPoolHandler = channelPoolHandler;
	}

	public int getChannelPoolMaxSize() {
		return channelPoolMaxSize;
	}

	public void setChannelPoolMaxSize(int channelMaxConnections) {
		this.channelPoolMaxSize = channelMaxConnections;
	}

	public Bootstrap getBootstrap() {
		return bootstrap;
	}

	public void setBootstrap(Bootstrap bootstrap) {
		this.bootstrap = bootstrap;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	
	
	
}
