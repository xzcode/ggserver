package xzcode.ggserver.core.common.event;

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

	@Override
	default <T> void emitEvent(String event, T eventData) {
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
