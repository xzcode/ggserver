package xzcode.ggserver.core.common.session;

import java.net.InetSocketAddress;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.future.GGNettyFacadeFuture;
import xzcode.ggserver.core.common.future.IGGFuture;

/**
 * sesson默认实现
 * 
 * 
 * @author zai
 * 2019-10-02 22:48:34
 */
public class DefaultChannelSession implements GGSession {
	
	private GGConfig config;
	private long expireMs;
	private Channel channel;
	
	public DefaultChannelSession(GGConfig config, Channel channel) {
		super();
		this.config = config;
		this.channel = channel;
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
	public IGGFuture<?> disconnect() {
		return new GGNettyFacadeFuture<>(this.channel.close());

	}

	@Override
	public String getHost() {
		return ((InetSocketAddress)channel.remoteAddress()).getHostString();
	}


	@Override
	public int getPort() {
		return ((InetSocketAddress)channel.remoteAddress()).getPort();
	}

	@Override
	public String getSessonId() {
		return channel.id().asLongText();
	}


	@Override
	public GGConfig getConfig() {
		return config;
	}
	
	public void setConfig(GGConfig config) {
		this.config = config;
	}



	@Override
	public boolean isActive() {
		return channel.isActive();
	}


	@Override
	public GGSession getSession() {
		return this;
	}

	@Override
	public Channel getChannel() {
		return channel;
	}

	@Override
	public boolean isExpired() {
		return expireMs < System.currentTimeMillis();
	}


	@Override
	public void updateExpire() {
		this.expireMs = System.currentTimeMillis() + config.getSessionExpireMs();
		
	}


}
