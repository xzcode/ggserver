package xzcode.ggserver.core.common.session.event;

public interface IGGSessionEventManager {
	
	
	<T> void emitEvent(String event, T eventData);
	
	<T> void addEventListener(String event, ISessionEventListener<T> listener);

	<T> void removeEventListener(String event, ISessionEventListener<T> listener);

	<T> boolean hasEventListener(String event, ISessionEventListener<T> listener);

	void clearEventListener(String event);

}