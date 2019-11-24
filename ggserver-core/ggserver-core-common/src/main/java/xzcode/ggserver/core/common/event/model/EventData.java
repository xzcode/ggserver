package xzcode.ggserver.core.common.event.model;

import xzcode.ggserver.core.common.session.GGSession;

public class EventData<T> {
	private GGSession session;
	private T data;
	
	
	public EventData(GGSession session, T data) {
		super();
		this.session = session;
		this.data = data;
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
	
	
}
