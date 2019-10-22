package xzcode.ggserver.core.common.session.event.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.core.common.session.event.IGGSessionEventManager;
import xzcode.ggserver.core.common.session.event.ISessionEventListener;
import xzcode.ggserver.core.common.session.event.ISessionEventListenerGroup;

public class DefaultSessionEventManager implements IGGSessionEventManager {
	
	private GGSession session;
	
	private Map<String, ISessionEventListenerGroup<?>> eventMap;

	public DefaultSessionEventManager(GGSession session) {
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void emitEvent(String event, T eventData) {
		if (eventMap != null) {
			ISessionEventListenerGroup<T> group = (ISessionEventListenerGroup<T>) eventMap.get(event);
			if (group != null) {
				group.onEvent(eventData);
			}
		}
		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> void addEventListener(String event, ISessionEventListener<T> listener) {
		if (eventMap == null) {
			synchronized (this) {
				if (eventMap == null) {
					eventMap = new ConcurrentHashMap<>(4);
				}
			}
			
		}
		ISessionEventListenerGroup<T> group = null;
		group = (ISessionEventListenerGroup<T>) eventMap.get(event);
		if (group == null) {
			synchronized (this) {
				group = (ISessionEventListenerGroup<T>) eventMap.get(event);
				if (group == null) {
					group = new DefaultSessionEventListenerGroup<>(session);
					eventMap.put(event, group);
				}
			}
		}
		group.addListener((ISessionEventListener<T>) listener);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> void removeEventListener(String event, ISessionEventListener<T> listener) {
		ISessionEventListenerGroup<T> group = (ISessionEventListenerGroup<T>) eventMap.get(event);
		if(group != null) {
			group.removeListener(listener);
		}
	}
	@Override
	@SuppressWarnings("unchecked")
	public <T> boolean hasEventListener(String event, ISessionEventListener<T> listener) {
		ISessionEventListenerGroup<T> group = (ISessionEventListenerGroup<T>) eventMap.get(event);
		if(group != null) {
			return group.hasListener(listener);
		}
		return false;
	}
	
	@Override
	public void clearEventListener(String event) {
		eventMap.remove(event);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> boolean hasEventListener(String event) {
		ISessionEventListenerGroup<T> group = (ISessionEventListenerGroup<T>) eventMap.get(event);
		if(group != null) {
			return !group.isEmpty();
		}
		return false;
	}

	@Override
	public boolean isEmpty() {
		return eventMap == null || eventMap.size() == 0;
	}

	
}
