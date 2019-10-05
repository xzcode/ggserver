package xzcode.ggserver.core.message.filter;

public interface IGGFilterSupport {
	
	MessageFilterManager getMessageFilterManager();
	
	/**
	 * 添加请求过滤器
	 * @param requestFilter 过滤器对象
	 * 
	 * @author zai
	 * 2019-10-04 15:01:05
	 */
	default void addRequestFilter(GGRequestFilter requestFilter) {
		addRequestFilter(10, requestFilter);
	}
	
	/**
	 * 添加请求过滤器
	 * @param order 过滤器排序
	 * @param requestFilter 过滤器对象
	 * 
	 * @author zai
	 * 2019-10-04 15:01:15
	 */
	default void addRequestFilter(int order, GGRequestFilter requestFilter) {
		MessageFilterModel filterModel = new MessageFilterModel();
		filterModel.setRequestFilter(requestFilter);
		filterModel.setOrder(order);
		filterModel.setFilterClazz(requestFilter.getClass());
		getMessageFilterManager().add(filterModel );
	}
	
	/**
	 * 添加响应过滤器
	 * @param responseFilter
	 * 
	 * @author zai
	 * 2019-10-04 15:02:07
	 */
	default void addResponseFilter(GGResponseFilter responseFilter) {
		addResponseFilter(10, responseFilter);
	}
	
	/**
	 * 添加响应过滤器
	 * @param order 过滤器顺序
	 * @param responseFilter
	 * 
	 * @author zai
	 * 2019-10-04 15:02:16
	 */
	default void addResponseFilter(int order, GGResponseFilter responseFilter) {
		MessageFilterModel filterModel = new MessageFilterModel();
		filterModel.setResponseFilter(responseFilter);
		filterModel.setOrder(order);
		filterModel.setFilterClazz(responseFilter.getClass());
		getMessageFilterManager().add(filterModel );
	}
	
}
