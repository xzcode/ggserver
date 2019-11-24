package xzcode.ggserver.core.common.event;

import xzcode.ggserver.core.common.event.model.EventData;

public interface IEventManager {
	
	
	void emitEvent(String event, EventData<?> eventData);
	
	<T> void addEventListener(String event, IEventListener<T> listener);

	<T> void removeEventListener(String event, IEventListener<T> listener);

	<T> boolean hasEventListener(String event, IEventListener<T> listener);
	
	<T> boolean hasEventListener(String event);

	void clearEventListener(String event);
	
	boolean isEmpty();
	
	<T> IEventListenerGroup<T> createEventListenerGroup();

}