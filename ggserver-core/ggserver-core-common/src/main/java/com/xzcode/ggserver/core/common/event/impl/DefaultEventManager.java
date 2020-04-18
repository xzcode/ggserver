package com.xzcode.ggserver.core.common.event.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xzcode.ggserver.core.common.event.EventListener;
import com.xzcode.ggserver.core.common.event.EventListenerGroup;
import com.xzcode.ggserver.core.common.event.EventManager;
import com.xzcode.ggserver.core.common.event.model.EventData;
import com.xzcode.ggserver.core.common.utils.logger.GGLoggerUtil;

/**
 * 默认事件管理器
 * 
 * 
 * @author zai
 * 2019-11-27 21:36:17
 */
public class DefaultEventManager implements EventManager {
	
	
	protected Map<String, EventListenerGroup<?>> eventMap = new ConcurrentHashMap<>(4);



	@SuppressWarnings("unchecked")
	@Override
	public <T> void emitEvent(EventData<T> eventData) {
		EventListenerGroup<T> group = (EventListenerGroup<T>) eventMap.get(eventData.getEvent());
		if (group != null) {
			try {
				group.onEvent(eventData);
			} catch (Exception e) {
				GGLoggerUtil.getLogger(this).error("Emit Event Error!", e);
			}
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> void addEventListener(String event, EventListener<T> listener) {
		EventListenerGroup<T> group = null;
		group = (EventListenerGroup<T>) eventMap.get(event);
		if (group == null) {
			synchronized (this) {
				group = (EventListenerGroup<T>) eventMap.get(event);
				if (group == null) {
					group = createEventListenerGroup();
					eventMap.put(event, group);
				}
			}
		}
		group.addListener((EventListener<T>) listener);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> void removeEventListener(String event, EventListener<T> listener) {
		EventListenerGroup<T> group = (EventListenerGroup<T>) eventMap.get(event);
		if(group != null) {
			group.removeListener(listener);
		}
	}
	@Override
	@SuppressWarnings("unchecked")
	public <T> boolean hasEventListener(String event, EventListener<T> listener) {
		EventListenerGroup<T> group = (EventListenerGroup<T>) eventMap.get(event);
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
		EventListenerGroup<T> group = (EventListenerGroup<T>) eventMap.get(event);
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
	public <T> EventListenerGroup<T> createEventListenerGroup() {
		return new DefaultEventListenerGroup<>();
	}


	
}
