package com.xzcode.ggserver.core.common.event;

import com.xzcode.ggserver.core.common.event.model.EventData;

public interface IEventListenerGroup<T> {
	
	void addListener(IEventListener<T> listener);
	
	void removeListener(IEventListener<T> listener);
	
	boolean hasListener(IEventListener<T> listener);
	
	void onEvent(EventData<T> eventData);
	
	boolean isEmpty();
	
}
