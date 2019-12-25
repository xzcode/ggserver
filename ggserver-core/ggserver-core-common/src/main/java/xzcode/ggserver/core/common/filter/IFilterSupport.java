package xzcode.ggserver.core.common.filter;

import xzcode.ggserver.core.common.event.model.EventData;
import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.message.request.Request;
import xzcode.ggserver.core.common.message.response.Response;

/**
 * 过滤器支持接口
 * 
 * 
 * @author zai
 * 2019-12-01 16:56:07
 */
public interface IFilterSupport extends IFilterManager{
	
	/**
	 * 获取过滤器管理器
	 * @return
	 * 
	 * @author zai
	 * 2019-12-01 16:56:15
	 */
	IFilterManager getFilterManager();
	
	@Override
	default boolean doBeforeDeserializeFilters(Pack pack) {
		return getFilterManager().doBeforeDeserializeFilters(pack);
	}

	@Override
	default boolean doRequestFilters(Request<?> request) {
		return getFilterManager().doRequestFilters(request);
	}

	@Override
	default boolean doResponseFilters(Response response) {
		return getFilterManager().doResponseFilters(response);
	}

	@Override
	default boolean doAfterSerializeFilters(Pack pack) {
		return getFilterManager().doAfterSerializeFilters(pack);
	}

	default void addBeforeDeserializeFilter(IBeforeDeserializeFilter filter) {
		getFilterManager().addBeforeDeserializeFilter(filter);
	}
	default void addRequestFilter(IRequestFilter filter) {
		getFilterManager().addRequestFilter(filter);
	}
	default void addResponseFilter(IResponseFilter filter) {
		getFilterManager().addResponseFilter(filter);
	}
	
	default void removeBeforeDeserializeFilter(IBeforeDeserializeFilter filter) {
		getFilterManager().removeBeforeDeserializeFilter(filter);
	}

	default void removeResponseFilter(IResponseFilter filter) {
		getFilterManager().removeResponseFilter(filter);
	}

	default void removeRequestFilter(IRequestFilter filter) {
		getFilterManager().removeRequestFilter(filter);
	}

	default void addAfterSerializeFilter(IAfterSerializeFilter filter) {
		getFilterManager().addAfterSerializeFilter(filter);
	};

	default void removeAfterSerializeFilter(IAfterSerializeFilter filter) {
		getFilterManager().removeAfterSerializeFilter(filter);
	}

	@Override
	default boolean doEventFilters(EventData<?> eventData) {
		return getFilterManager().doEventFilters(eventData);
	}

	@Override
	default void addEventFilter(IEventFilter filter) {
		getFilterManager().addEventFilter(filter);
	}

	@Override
	default void removeEventFilter(IEventFilter filter) {
		getFilterManager().removeEventFilter(filter);
	};
	
	
	
}
