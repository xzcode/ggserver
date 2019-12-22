package xzcode.ggserver.core.common.session.impl;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import xzcode.ggserver.core.common.config.GGConfig;

/**
 * sesson默认实现
 * 
 * 
 * @author zai
 * 2019-10-02 22:48:34
 */
public class DefaultChannelSession extends AbstractSession<GGConfig> {
	
	private Channel channel;
	
	public DefaultChannelSession(Channel channel, String sessionId, GGConfig config) {
		super(sessionId, config);
		this.channel = channel;
		updateExpire();
	}

	@Override
	public void addAttribute(String key, Object value) {
		channel.attr(AttributeKey.valueOf(key)).set(value);
	}

	@Override
	public Object getAttribute(String key) {
		return channel.attr(AttributeKey.valueOf(key)).get();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAttribute(String key, Class<T> t) {
		return (T) channel.attr(AttributeKey.valueOf(key)).get();
	}

	@Override
	public Object reomveAttribute(String key) {
		return channel.attr(AttributeKey.valueOf(key)).getAndSet(null);
	}


	@Override
	public Channel getChannel() {
		return channel;
	}

	@Override
	public void setChannel(Channel channel) {
		this.channel = channel;
	}


	
}
