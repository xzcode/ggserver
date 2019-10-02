package xzcode.ggserver.core.session.imp;

import java.net.InetSocketAddress;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import xzcode.ggserver.core.session.GGSession;

/**
 * sesson默认实现
 * 
 * 
 * @author zai
 * 2019-10-02 22:48:34
 */
public class DefaultGGSessionImpl implements GGSession {
	
	
	private Channel channel;
	
	
	public DefaultGGSessionImpl() {}
	

	public DefaultGGSessionImpl(Channel channel) {
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
	

}
