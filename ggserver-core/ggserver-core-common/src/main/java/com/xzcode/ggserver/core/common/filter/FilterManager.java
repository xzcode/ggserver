package com.xzcode.ggserver.core.common.filter;

import com.xzcode.ggserver.core.common.event.model.EventData;
import com.xzcode.ggserver.core.common.message.MessageData;
import com.xzcode.ggserver.core.common.message.Pack;

/**
 * 过滤器管理器统一接口
 * 
 * @author zai
 * 2019-12-25 15:05:30
 */
public interface FilterManager {
	

	boolean doBeforeDeserializeFilters(Pack pack);

	boolean doRequestFilters(MessageData<?> request);

	boolean doResponseFilters(MessageData<?> response);

	boolean doAfterSerializeFilters(Pack pack);
	
	boolean doEventFilters(EventData<?> eventData);
	
	
	
	

	void addBeforeDeserializeFilter(BeforeDeserializeFilter filter);

	void addRequestFilter(ReceiveMessageFilter filter);

	void addResponseFilter(SendMessageFilter filter);
	
	void addAfterSerializeFilter(AfterSerializeFilter filter);
	
	void addEventFilter(EventFilter filter);
	
	
	

	void removeBeforeDeserializeFilter(BeforeDeserializeFilter filter);

	void removeResponseFilter(SendMessageFilter filter);

	void removeRequestFilter(ReceiveMessageFilter filter);

	void removeAfterSerializeFilter(AfterSerializeFilter filter);
	
	void removeEventFilter(EventFilter filter);
	
	

}