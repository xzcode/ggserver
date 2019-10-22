package xzcode.ggserver.core.common.session.event;

public interface ISessionEventSupport extends IGGSessionEventManager{
	
	IGGSessionEventManager getGGSessionEventManager();

	@Override
	default <T> void addEventListener(String event, ISessionEventListener<T> listener) {
		getGGSessionEventManager().addEventListener(event, listener);
	}

	@Override
	default <T> void removeEventListener(String event, ISessionEventListener<T> listener) {
		getGGSessionEventManager().removeEventListener(event, listener);
	}

	@Override
	default <T> boolean hasEventListener(String event, ISessionEventListener<T> listener) {
		return getGGSessionEventManager().hasEventListener(event, listener);
	}

	@Override
	default void clearEventListener(String event) {
		getGGSessionEventManager().clearEventListener(event);
	}

	@Override
	default <T> void emitEvent(String event, T eventData) {
		getGGSessionEventManager().emitEvent(event, eventData);
	}

	@Override
	default <T> boolean hasEventListener(String event) {
		return getGGSessionEventManager().hasEventListener(event);
	}

	@Override
	default boolean isEmpty() {
		return getGGSessionEventManager().isEmpty();
	}
	
	
	
	
}
