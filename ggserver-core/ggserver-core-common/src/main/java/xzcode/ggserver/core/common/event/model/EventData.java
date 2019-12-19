package xzcode.ggserver.core.common.event.model;

import io.netty.channel.Channel;
import xzcode.ggserver.core.common.session.GGSession;

public class EventData<T> {
	private GGSession session;
	private Channel channel;
	private T data;
	
	
	public EventData(GGSession session, T data) {
		super();
		this.session = session;
		this.data = data;
	}
	public EventData(GGSession session, T data, Channel channel) {
		this.session = session;
		this.data = data;
		this.channel = channel;
	}
	public GGSession getSession() {
		return session;
	}
	public void setSession(GGSession session) {
		this.session = session;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	public Channel getChannel() {
		return channel;
	}
	
}
