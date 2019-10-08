package xzcode.ggserver.core.message.filter;

public interface IGGFilterSupport {
	
	MessageFilterManager getMessageFilterManager();
	
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
		getMessageFilterManager().add(filterModel );
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
