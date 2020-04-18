package com.xzcode.ggserver.core.common.filter;

import com.xzcode.ggserver.core.common.event.model.EventData;
import com.xzcode.ggserver.core.common.message.MessageData;
import com.xzcode.ggserver.core.common.message.Pack;

/**
 * 过滤器支持接口
 * 
 * 
 * @author zai
 * 2019-12-01 16:56:07
 */
public interface FilterSupport extends FilterManager{
	
	/**
	 * 获取过滤器管理器
	 * @return
	 * 
	 * @author zai
	 * 2019-12-01 16:56:15
	 */
	FilterManager getFilterManager();
	
	@Override
	default boolean doBeforeDeserializeFilters(Pack pack) {
		return getFilterManager().doBeforeDeserializeFilters(pack);
	}

	@Override
	default boolean doRequestFilters(MessageData<?> request) {
		return getFilterManager().doRequestFilters(request);
	}

	@Override
	default boolean doResponseFilters(MessageData<?> messageData) {
		return getFilterManager().doResponseFilters(messageData);
	}

	@Override
	default boolean doAfterSerializeFilters(Pack pack) {
		return getFilterManager().doAfterSerializeFilters(pack);
	}

	default void addBeforeDeserializeFilter(BeforeDeserializeFilter filter) {
		getFilterManager().addBeforeDeserializeFilter(filter);
	}
	default void addRequestFilter(ReceiveMessageFilter filter) {
		getFilterManager().addRequestFilter(filter);
	}
	default void addResponseFilter(SendMessageFilter filter) {
		getFilterManager().addResponseFilter(filter);
	}
	
	default void removeBeforeDeserializeFilter(BeforeDeserializeFilter filter) {
		getFilterManager().removeBeforeDeserializeFilter(filter);
	}

	default void removeResponseFilter(SendMessageFilter filter) {
		getFilterManager().removeResponseFilter(filter);
	}

	default void removeRequestFilter(ReceiveMessageFilter filter) {
		getFilterManager().removeRequestFilter(filter);
	}

	default void addAfterSerializeFilter(AfterSerializeFilter filter) {
		getFilterManager().addAfterSerializeFilter(filter);
	};

	default void removeAfterSerializeFilter(AfterSerializeFilter filter) {
		getFilterManager().removeAfterSerializeFilter(filter);
	}

	@Override
	default boolean doEventFilters(EventData<?> eventData) {
		return getFilterManager().doEventFilters(eventData);
	}

	@Override
	default void addEventFilter(EventFilter filter) {
		getFilterManager().addEventFilter(filter);
	}

	@Override
	default void removeEventFilter(EventFilter filter) {
		getFilterManager().removeEventFilter(filter);
	};
	
	
	
}
