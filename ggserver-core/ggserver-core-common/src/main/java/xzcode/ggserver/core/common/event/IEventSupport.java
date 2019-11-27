package xzcode.ggserver.core.common.event;

import xzcode.ggserver.core.common.event.model.EventData;

public interface IEventSupport extends IEventManager{
	
	IEventManager getEventManager();

	@Override
	default <T> void addEventListener(String event, IEventListener<T> listener) {
		getEventManager().addEventListener(event, listener);
	}

	@Override
	default <T> void removeEventListener(String event, IEventListener<T> listener) {
		getEventManager().removeEventListener(event, listener);
	}

	@Override
	default <T> boolean hasEventListener(String event, IEventListener<T> listener) {
		return getEventManager().hasEventListener(event, listener);
	}

	@Override
	default void clearEventListener(String event) {
		getEventManager().clearEventListener(event);
	}

	default void emitEvent(String event, EventData<?> eventData) {
		getEventManager().emitEvent(event, eventData);
	}

	@Override
	default <T> boolean hasEventListener(String event) {
		return getEventManager().hasEventListener(event);
	}

	@Override
	default boolean isEmpty() {
		return getEventManager().isEmpty();
	}

	@Override
	default <T> IEventListenerGroup<T> createEventListenerGroup() {
		return null;
	}
	
	
	
	
}
