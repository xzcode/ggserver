package xzcode.ggserver.core.common.session.event;

public interface ISessionEventListenerGroup<T> {
	
	void addListener(ISessionEventListener<T> listener);
	
	void removeListener(ISessionEventListener<T> listener);
	
	boolean hasListener(ISessionEventListener<T> listener);
	
	void onEvent(T e);
	
}
