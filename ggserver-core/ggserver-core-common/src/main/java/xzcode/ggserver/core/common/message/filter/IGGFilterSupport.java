package xzcode.ggserver.core.common.message.filter;

import xzcode.ggserver.core.common.config.IGGConfigSupport;

public interface IGGFilterSupport extends IGGConfigSupport{
	
	
	default void addBeforeDeserializeFilter(int order, GGBeforeDeserializeFilter filter) {
		addFilter(order, filter);
	}
	default void addBeforeDeserializeFilter(GGBeforeDeserializeFilter filter) {
		addBeforeDeserializeFilter(10, filter);
	}
	
	default void addRequestFilter(int order, GGRequestFilter filter) {
		addFilter(order, filter);
	}
	default void addRequestFilter(GGRequestFilter filter) {
		addRequestFilter(10, filter);
	}
	
	default void addResponseFilter(int order, GGResponseFilter filter) {
		addFilter(order, filter);
	}
	default void addResponseFilter(GGResponseFilter filter) {
		addResponseFilter(10, filter);
	}
	
	
	/**
	 * 添加过滤器
	 * 
	 * @param order
	 * @param filter
	 * @author zzz
	 * 2019-10-08 18:36:12
	 */
	default void addFilter(int order, IGGFilter<?> filter) {
		MessageFilterModel filterModel = new MessageFilterModel();
		filterModel.setFilter(filter);
		filterModel.setOrder(order);
		filterModel.setFilterClazz(filter.getClass());
		getConfig().getMessageFilterManager().add(filterModel );
	}
	
	/**
	 * 添加过滤器
	 * @param responseFilter
	 * 
	 * @author zai
	 * 2019-10-04 15:02:07
	 */
	default void addFilter(GGResponseFilter responseFilter) {
		addFilter(10, responseFilter);
	}
	
}
