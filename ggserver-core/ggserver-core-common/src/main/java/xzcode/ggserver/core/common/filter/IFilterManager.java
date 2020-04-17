package xzcode.ggserver.core.common.filter;

import xzcode.ggserver.core.common.event.model.EventData;
import xzcode.ggserver.core.common.message.MessageData;
import xzcode.ggserver.core.common.message.Pack;

/**
 * 过滤器管理器统一接口
 * 
 * @author zai
 * 2019-12-25 15:05:30
 */
public interface IFilterManager {
	

	boolean doBeforeDeserializeFilters(Pack pack);

	boolean doRequestFilters(MessageData<?> request);

	boolean doResponseFilters(MessageData<?> response);

	boolean doAfterSerializeFilters(Pack pack);
	
	boolean doEventFilters(EventData<?> eventData);
	
	
	
	

	void addBeforeDeserializeFilter(IBeforeDeserializeFilter filter);

	void addRequestFilter(IRequestFilter filter);

	void addResponseFilter(IResponseFilter filter);
	
	void addAfterSerializeFilter(IAfterSerializeFilter filter);
	
	void addEventFilter(IEventFilter filter);
	
	
	

	void removeBeforeDeserializeFilter(IBeforeDeserializeFilter filter);

	void removeResponseFilter(IResponseFilter filter);

	void removeRequestFilter(IRequestFilter filter);

	void removeAfterSerializeFilter(IAfterSerializeFilter filter);
	
	void removeEventFilter(IEventFilter filter);
	
	

}