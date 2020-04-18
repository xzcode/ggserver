package com.xzcode.ggserver.core.common.event;

import com.xzcode.ggserver.core.common.event.model.EventData;

public interface EventListenerGroup<T> {
	
	void addListener(EventListener<T> listener);
	
	void removeListener(EventListener<T> listener);
	
	boolean hasListener(EventListener<T> listener);
	
	void onEvent(EventData<T> eventData);
	
	boolean isEmpty();
	
}
