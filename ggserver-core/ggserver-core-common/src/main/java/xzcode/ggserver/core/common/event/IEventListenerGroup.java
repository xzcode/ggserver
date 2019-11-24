package xzcode.ggserver.core.common.event;

public interface IEventListenerGroup<T> {
	
	void addListener(IEventListener<T> listener);
	
	void removeListener(IEventListener<T> listener);
	
	boolean hasListener(IEventListener<T> listener);
	
	void onEvent(T e);
	
	boolean isEmpty();
	
}
