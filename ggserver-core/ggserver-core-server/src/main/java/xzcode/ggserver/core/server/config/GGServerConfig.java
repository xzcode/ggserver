package xzcode.ggserver.core.server.config;

import java.util.concurrent.ThreadFactory;

import io.netty.channel.nio.NioEventLoopGroup;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.executor.factory.EventLoopGroupThreadFactory;

public class GGServerConfig extends GGConfig{

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
	
	
}
