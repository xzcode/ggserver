package xzcode.ggserver.core.event;

public interface IEventHandler<T> {
	
	void onEvent(T data);
	
}
