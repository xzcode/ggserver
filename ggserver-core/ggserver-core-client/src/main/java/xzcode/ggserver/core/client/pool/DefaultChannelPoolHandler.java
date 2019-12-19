package xzcode.ggserver.core.client.pool;

import io.netty.channel.Channel;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.util.AttributeKey;
import xzcode.ggserver.core.client.config.GGClientConfig;
import xzcode.ggserver.core.common.channel.DefaultChannelAttributeKeys;
import xzcode.ggserver.core.common.handler.TcpChannelInitializer;

/**
 * 默认通道池处理器
 * 
 * @author zai
 * 2019-12-12 19:36:15
 */
public class DefaultChannelPoolHandler implements ChannelPoolHandler {
	
	protected GGClientConfig config;
	
	protected static final AttributeKey<String> PROTOCOL_TYPE_KEY = AttributeKey.valueOf(DefaultChannelAttributeKeys.PROTOCOL_TYPE);

	public DefaultChannelPoolHandler(GGClientConfig config) {
		super();
		this.config = config;
	}

	@Override
	public void channelReleased(Channel ch) throws Exception {
		
	}

	@Override
	public void channelAcquired(Channel ch) throws Exception {
		
	}

	@Override
	public void channelCreated(Channel ch) throws Exception {
		ch.pipeline().addLast(new TcpChannelInitializer(this.config));
		
	}

}
