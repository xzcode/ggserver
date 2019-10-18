package xzcode.ggserver.core.common.session.event.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.core.common.session.event.IGGSessionEventManager;
import xzcode.ggserver.core.common.session.event.ISessionEventListener;
import xzcode.ggserver.core.common.session.event.ISessionEventListenerGroup;

public class DefaultSessionEventManager implements IGGSessionEventManager {
	
	private GGConfig config;
	
	private GGSession session;
	
	private Map<String, ISessionEventListenerGroup<?>> eventMap;
	
	
	

	public DefaultSessionEventManager(GGConfig config, GGSession session) {
		super();
		this.config = config;
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void emitEvent(String event, T eventData) {
		if (eventMap != null) {
			ISessionEventListenerGroup<T> group = (ISessionEventListenerGroup<T>) eventMap.get(event);
			if (group != null) {
				//异步触发事件
				config.getTaskExecutor().submit(() -> {
					group.onEvent(eventData);
				});
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
			group.removeListener(listener);
		}
		return false;
	}
	
	@Override
	public void clearEventListener(String event) {
		eventMap.remove(event);
	}

	
}
