package xzcode.ggserver.core.common.event.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import xzcode.ggserver.core.common.event.IEventListener;
import xzcode.ggserver.core.common.event.IEventListenerGroup;
import xzcode.ggserver.core.common.event.IEventManager;

public class DefaultEventManager implements IEventManager {
	
	
	protected Map<String, IEventListenerGroup<?>> eventMap;


	@SuppressWarnings("unchecked")
	@Override
	public <T> void emitEvent(String event, T eventData) {
		if (eventMap != null) {
			IEventListenerGroup<T> group = (IEventListenerGroup<T>) eventMap.get(event);
			if (group != null) {
				group.onEvent(eventData);
			}
		}
		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> void addEventListener(String event, IEventListener<T> listener) {
		if (eventMap == null) {
			synchronized (this) {
				if (eventMap == null) {
					eventMap = new ConcurrentHashMap<>(4);
				}
			}
			
		}
		IEventListenerGroup<T> group = null;
		group = (IEventListenerGroup<T>) eventMap.get(event);
		if (group == null) {
			synchronized (this) {
				group = (IEventListenerGroup<T>) eventMap.get(event);
				if (group == null) {
					group = createEventListenerGroup();
					eventMap.put(event, group);
				}
			}
		}
		group.addListener((IEventListener<T>) listener);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> void removeEventListener(String event, IEventListener<T> listener) {
		IEventListenerGroup<T> group = (IEventListenerGroup<T>) eventMap.get(event);
		if(group != null) {
			group.removeListener(listener);
		}
	}
	@Override
	@SuppressWarnings("unchecked")
	public <T> boolean hasEventListener(String event, IEventListener<T> listener) {
		IEventListenerGroup<T> group = (IEventListenerGroup<T>) eventMap.get(event);
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
		IEventListenerGroup<T> group = (IEventListenerGroup<T>) eventMap.get(event);
		if(group != null) {
			return !group.isEmpty();
		}
		return false;
	}

	@Override
	public boolean isEmpty() {
		return eventMap == null || eventMap.size() == 0;
	}

	@Override
	public <T> IEventListenerGroup<T> createEventListenerGroup() {
		return new DefaultEventListenerGroup<>();
	}

	
}
