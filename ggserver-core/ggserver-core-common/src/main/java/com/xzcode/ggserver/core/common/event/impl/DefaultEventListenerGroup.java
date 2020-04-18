package com.xzcode.ggserver.core.common.event.impl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.xzcode.ggserver.core.common.event.EventListener;
import com.xzcode.ggserver.core.common.event.EventListenerGroup;
import com.xzcode.ggserver.core.common.event.model.EventData;
import com.xzcode.ggserver.core.common.utils.logger.GGLoggerUtil;

/**
 * 默认事件监听组
 * 
 * @param <T>
 * @author zai
 * 2019-10-18 11:56:31
 */
public class DefaultEventListenerGroup<T> implements EventListenerGroup<T>{
	
	protected List<EventListener<T>> listeners = new CopyOnWriteArrayList<>();
	
	

	@Override
	public void addListener(EventListener<T> listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(EventListener<T> listener) {
		listeners.remove(listener);
	}

	

	@Override
	public boolean hasListener(EventListener<T> listener) {
		return listeners.contains(listener);
	}

	@Override
	public boolean isEmpty() {
		return listeners.isEmpty();
	}

	@Override
	public void onEvent(EventData<T> eventData) {
		for (EventListener<T> li : listeners) {
			try {
				li.onEvent(eventData);				
			} catch (Exception e) {
				GGLoggerUtil.getLogger(this.getClass()).error("Invoke Event Error!", e);
			}
		}
		
	}

}
