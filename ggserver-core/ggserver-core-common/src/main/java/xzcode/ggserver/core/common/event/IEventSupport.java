package xzcode.ggserver.core.common.event;

import xzcode.ggserver.core.common.event.model.EventData;

public interface IEventSupport extends IEventManager{
	
	IEventManager getEventManagerImpl();

	@Override
	default <T> void addEventListener(String event, IEventListener<T> listener) {
		getEventManagerImpl().addEventListener(event, listener);
	}

	@Override
	default <T> void removeEventListener(String event, IEventListener<T> listener) {
		getEventManagerImpl().removeEventListener(event, listener);
	}

	@Override
	default <T> boolean hasEventListener(String event, IEventListener<T> listener) {
		return getEventManagerImpl().hasEventListener(event, listener);
	}

	@Override
	default void clearEventListener(String event) {
		getEventManagerImpl().clearEventListener(event);
	}

	default <T> void emitEvent(String event, EventData<T> eventData) {
		getEventManagerImpl().emitEvent(event, eventData);
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
	default <T> IEventListenerGroup<T> createEventListenerGroup() {
		return getEventManagerImpl().createEventListenerGroup();
	}
	
	
	
	
}
