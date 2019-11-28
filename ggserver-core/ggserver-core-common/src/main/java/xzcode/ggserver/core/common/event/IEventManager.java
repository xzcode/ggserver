package xzcode.ggserver.core.common.event;

import xzcode.ggserver.core.common.event.model.EventData;

/**
 * 事件管理器接口
 * 
 * 
 * @author zai
 * 2019-11-27 21:49:33
 */
public interface IEventManager {
	
	
	<T> void emitEvent(String event, EventData<T> eventData);
	
	<T> void addEventListener(String event, IEventListener<T> listener);

	<T> void removeEventListener(String event, IEventListener<T> listener);

	<T> boolean hasEventListener(String event, IEventListener<T> listener);
	
	<T> boolean hasEventListener(String event);

	void clearEventListener(String event);
	
	boolean isEmpty();
	
	<T> IEventListenerGroup<T> createEventListenerGroup();

}