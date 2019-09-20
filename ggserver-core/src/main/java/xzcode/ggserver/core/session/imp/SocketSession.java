package xzcode.ggserver.core.session.imp;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import xzcode.ggserver.core.session.GGSession;

/**
 * 默认session实现
 * 
 * 
 * @author zai
 * 2017-08-02
 */
public class SocketSession implements GGSession {
	
	private Object registeredUserId;
	
	private Channel channel;
	
	private String host;
	
	private int port;
	
	private boolean isActive = true;
	
	public SocketSession() {}
	
	public void inActive() {
		if (isActive == true) {
			this.isActive = false;
			this.channel = null;
		}
	}


	public SocketSession(Channel channel, String host, int port) {
		super();
		this.channel = channel;
		this.host = host;
		this.port = port;
	}

	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SocketSession [registeredUserId=");
		builder.append(registeredUserId);
		builder.append(", channel=");
		builder.append(channel);
		builder.append(", host=");
		builder.append(host);
		builder.append(", port=");
		builder.append(port);
		builder.append(", isActive=");
		builder.append(isActive);
		builder.append("]");
		return builder.toString();
	}

	public SocketSession(Channel channel) {
		super();
		this.channel = channel;
	}
	
	@Override
	public void register(Object registeredUserId) {
		this.registeredUserId = registeredUserId;
	}
	
	@Override
	public void unregister() {
		this.registeredUserId = null;
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
	public void setChannel(Channel channel) {
		this.channel = channel;
	}


	@Override
	public String getHost() {
		return host;
	}



	public void setHost(String host) {
		this.host = host;
	}



	@Override
	public int getPort() {
		return port;
	}



	public void setPort(int port) {
		this.port = port;
	}
	
	@Override
	public Object getRegisteredUserId() {
		return registeredUserId;
	}
	
	@Override
	public void setRegisteredUserId(Object registeredUserId) {
		this.registeredUserId = registeredUserId;
	}


	public boolean isActive() {
		return isActive;
	}


	public void setActive(boolean isActive) {
		if (isActive == false) {
			this.isActive = isActive;			
		}
	}
	
	
	
	

}
