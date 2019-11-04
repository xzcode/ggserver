package xzcode.ggserver.core.server.config;

import java.util.concurrent.ThreadFactory;

import io.netty.channel.nio.NioEventLoopGroup;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.executor.factory.EventLoopGroupThreadFactory;

public class GGServerConfig extends GGConfig{
	
	protected String	host = "0.0.0.0";

	protected int 		port = 9999;

	protected NioEventLoopGroup bossGroup;
	
	protected ThreadFactory bossGroupThreadFactory;
	
	
	@Override
	public void init() {
		
		super.init();
		
		if (bossGroupThreadFactory == null) {
			bossGroupThreadFactory = new EventLoopGroupThreadFactory("netty-boss");
		}
		if (bossGroup == null) {
			bossGroup = new NioEventLoopGroup(getBossThreadSize(),bossGroupThreadFactory);				
		}
		
	}
	public NioEventLoopGroup getBossGroup() {
		return bossGroup;
	}
	public void setBossGroup(NioEventLoopGroup bossGroup) {
		this.bossGroup = bossGroup;
	}
	public ThreadFactory getBossGroupThreadFactory() {
		return bossGroupThreadFactory;
	}
	public void setBossGroupThreadFactory(ThreadFactory bossGroupThreadFactory) {
		this.bossGroupThreadFactory = bossGroupThreadFactory;
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