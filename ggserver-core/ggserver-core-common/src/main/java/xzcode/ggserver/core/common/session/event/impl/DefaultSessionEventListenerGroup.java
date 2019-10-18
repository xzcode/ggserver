package xzcode.ggserver.core.common.session.event.impl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.core.common.session.event.ISessionEventListener;
import xzcode.ggserver.core.common.session.event.ISessionEventListenerGroup;

/**
 * 默认会话事件监听组
 * 
 * @param <T>
 * @author zai
 * 2019-10-18 11:56:31
 */
public class DefaultSessionEventListenerGroup<T> implements ISessionEventListenerGroup<T>{
	
	private List<ISessionEventListener<T>> listeners = new CopyOnWriteArrayList<>();
	
	private GGSession session;
	

	public DefaultSessionEventListenerGroup(GGSession session) {
		super();
		this.session = session;
	}

	@Override
	public void addListener(ISessionEventListener<T> listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(ISessionEventListener<T> listener) {
		listeners.remove(listener);
	}

	@Override
	public void onEvent(T e) {
		for (ISessionEventListener<T> li : listeners) {
			li.onEvent(session, e);
		}
	}

	@Override
	public boolean hasListener(ISessionEventListener<T> listener) {
		return listeners.contains(listener);
	}

}
