package xzcode.ggserver.core.common.event;

public interface IEventHandler<T> {
	
	void onEvent(T data);
	
}
