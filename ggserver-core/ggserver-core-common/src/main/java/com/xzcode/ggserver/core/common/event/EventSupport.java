package com.xzcode.ggserver.core.common.event;

import com.xzcode.ggserver.core.common.event.model.EventData;

/**
 * 事件支持
 * 
 * @author zai 2019-12-05 10:50:19
 */
public interface EventSupport extends EventManager {

	EventManager getEventManagerImpl();

	@Override
	default <T> void addEventListener(String event, EventListener<T> listener) {
		getEventManagerImpl().addEventListener(event, listener);
	}

	@Override
	default <T> void removeEventListener(String event, EventListener<T> listener) {
		getEventManagerImpl().removeEventListener(event, listener);
	}

	@Override
	default <T> boolean hasEventListener(String event, EventListener<T> listener) {
		return getEventManagerImpl().hasEventListener(event, listener);
	}

	@Override
	default void clearEventListener(String event) {
		getEventManagerImpl().clearEventListener(event);
	}

	default <T> void emitEvent(EventData<T> eventData) {
		getEventManagerImpl().emitEvent(eventData);
	}

	@Override
	default <T> boolean hasEventListener(String event) {
		return getEventManagerImpl().hasEventListener(event);
	}

	@Override
	default boolean isEmpty() {
		return getEventManagerImpl().isEmpty();
	}

	@Override
	default <T> EventListenerGroup<T> createEventListenerGroup() {
		return getEventManagerImpl().createEventListenerGroup();
	}

}
