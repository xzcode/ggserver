package xzcode.ggserver.core.common.session.imp;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import xzcode.ggserver.core.common.channel.group.IChannelGroup;
import xzcode.ggserver.core.common.channel.group.IChannelGroupManager;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.event.IEventManager;
import xzcode.ggserver.core.common.event.impl.DefaultSessionEventManager;
import xzcode.ggserver.core.common.filter.IFilterManager;
import xzcode.ggserver.core.common.filter.impl.SessionFilterManager;
import xzcode.ggserver.core.common.future.GGFuture;
import xzcode.ggserver.core.common.session.GGSession;

/**
 * sesson默认实现
 * 
 * 
 * @author zai
 * 2019-10-02 22:48:34
 */
public class GroupedChannelSession implements GGSession {
	
	private GGConfig config;
	
	private IEventManager eventManager;
	
	private IFilterManager filterManager;
	
	private IChannelGroup channelGroup;
	
	private Map<String, Object> attrMap = new ConcurrentHashMap<>(6);
	
	public GroupedChannelSession(GGConfig config, IChannelGroup channelGroup) {
		super();
		this.config = config;
		this.channelGroup = channelGroup;
		eventManager = new DefaultSessionEventManager(this);
		filterManager = new SessionFilterManager(this);
	}

	@Override
	public IFilterManager getFilterManager() {
		return this.filterManager;
	}

	@Override
	public IEventManager getEventManager() {
		return eventManager;
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
	public GGFuture disconnect() {
		return new GGFuture(this.channel.close());

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
	public Channel getSendMessageChannel() {
		return channelGroup.getRandomChannel();
	}


}
