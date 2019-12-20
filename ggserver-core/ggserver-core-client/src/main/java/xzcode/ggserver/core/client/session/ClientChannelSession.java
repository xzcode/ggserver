package xzcode.ggserver.core.client.session;

import java.util.concurrent.TimeUnit;

import io.netty.channel.Channel;
import xzcode.ggserver.core.client.config.GGClientConfig;
import xzcode.ggserver.core.client.pool.IChannelPoolSendMessageSupport;
import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.session.impl.DefaultChannelSession;

/**
 * 客户端会后
 * 
 * @author zai
 * 2019-12-15 18:10:39
 */
public class ClientChannelSession extends DefaultChannelSession implements IChannelPoolSendMessageSupport{
	
	protected GGClientConfig config;
	
	public ClientChannelSession(Channel channel, String sessionId, GGClientConfig config) {
		super(channel, sessionId, config);
		this.config = config;
	}
	
	@Override
	public GGClientConfig getConfig() {
		return this.config;
	}

	@Override
	public IGGFuture send(Pack pack, long delay, TimeUnit timeUnit) {
		
		if (!config.isChannelPoolEnabled()) {
			return super.send(pack, delay, timeUnit);
		}
		return poolSend(pack, delay, timeUnit);
	}

}
