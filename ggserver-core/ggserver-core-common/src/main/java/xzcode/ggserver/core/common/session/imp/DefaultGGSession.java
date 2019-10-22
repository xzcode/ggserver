package xzcode.ggserver.core.common.session.imp;

import java.net.InetSocketAddress;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.core.common.session.event.IGGSessionEventManager;
import xzcode.ggserver.core.common.session.event.impl.DefaultSessionEventManager;
import xzcode.ggserver.core.common.session.filter.ISessionFilterManager;
import xzcode.ggserver.core.common.session.filter.impl.SessionFilterManager;

/**
 * sesson默认实现
 * 
 * 
 * @author zai
 * 2019-10-02 22:48:34
 */
public class DefaultGGSession implements GGSession {
	
	
	private GGConfig config;
	
	private IGGSessionEventManager eventManager;
	
	private ISessionFilterManager sessionFilterManager;
	
	private Channel channel;
	
	public DefaultGGSession(GGConfig config, Channel channel) {
		super();
		this.config = config;
		this.channel = channel;
		eventManager = new DefaultSessionEventManager(this);
		sessionFilterManager = new SessionFilterManager(this);
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
	public void disconnect() {
		this.channel.close();

	}
	
	@Override
	public Channel getChannel() {
		return channel;
	}
	

	@Override
	public String getHost() {
		return ((InetSocketAddress)channel.remoteAddress()).getHostString();
	}


	@Override
	public int getPort() {
		return ((InetSocketAddress)channel.remoteAddress()).getPort();
	}


	public void setChannel(Channel channel) {
		this.channel = channel;
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
	public ISessionFilterManager getSessionFilterManager() {
		return this.sessionFilterManager;
	}


	@Override
	public IGGSessionEventManager getGGSessionEventManager() {
		return eventManager;
	}


}
