package com.xzcode.ggserver.core.common.event;

import com.xzcode.ggserver.core.common.event.model.EventData;

/**
 * 事件管理器接口
 * 
 * 
 * @author zai
 * 2019-11-27 21:49:33
 */
public interface EventManager {
	
	/**
	 * 发射事件
	 * 
	 * @param event
	 * @param eventData
	 * @author zai
	 * 2019-12-05 10:50:36
	 */
	<T> void emitEvent(EventData<T> eventData);
	
	/**
	 * 添加事件监听
	 * 
	 * @param event
	 * @param listener
	 * @author zai
	 * 2019-12-05 10:50:46
	 */
	<T> void addEventListener(String event, EventListener<T> listener);

	/**
	 * 移除事件监听
	 * 
	 * @param event
	 * @param listener
	 * @author zai
	 * 2019-12-05 10:50:55
	 */
	<T> void removeEventListener(String event, EventListener<T> listener);

	/**
	 * 是否存在事件监听
	 * 
	 * @param event
	 * @param listener
	 * @return
	 * @author zai
	 * 2019-12-05 10:51:04
	 */
	<T> boolean hasEventListener(String event, EventListener<T> listener);
	
	/**
	 * 是否存在事件监听
	 * 
	 * @param event
	 * @return
	 * @author zai
	 * 2019-12-05 10:51:14
	 */
	<T> boolean hasEventListener(String event);

	/**
	 * 清除事件监听
	 * 
	 * @param event
	 * @author zai
	 * 2019-12-05 10:51:22
	 */
	void clearEventListener(String event);
	
	/**
	 * 是否没有事件监听
	 * 
	 * @return
	 * @author zai
	 * 2019-12-05 10:51:29
	 */
	boolean isEmpty();
	
	/**
	 * 创建事件监听组
	 * 
	 * @return
	 * @author zai
	 * 2019-12-05 10:51:45
	 */
	<T> EventListenerGroup<T> createEventListenerGroup();

}