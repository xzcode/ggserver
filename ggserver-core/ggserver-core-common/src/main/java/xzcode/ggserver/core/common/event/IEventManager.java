package xzcode.ggserver.core.common.event;

public interface IEventManager {
	
	
	<T> void emitEvent(String event, T eventData);
	
	<T> void addEventListener(String event, IEventListener<T> listener);

	<T> void removeEventListener(String event, IEventListener<T> listener);

	<T> boolean hasEventListener(String event, IEventListener<T> listener);
	
	<T> boolean hasEventListener(String event);

	void clearEventListener(String event);
	
	boolean isEmpty();
	
	<T> IEventListenerGroup<T> createEventListenerGroup();

}