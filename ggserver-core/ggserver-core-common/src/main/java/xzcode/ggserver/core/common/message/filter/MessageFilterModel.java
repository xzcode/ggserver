package xzcode.ggserver.core.common.message.filter;

/**
 * 消息过滤器模型
 *
 * @author zai
 * 2018-12-20 10:15:03
 */
public class MessageFilterModel {
	/**
	 * 序号
	 */
	private int order;
	
	/**
	 * 过滤器class
	 */
	private Class<?> filterClazz;
	
	/**
	 * 过滤器
	 */
	private IGGFilter<?> filter;
	


	public MessageFilterModel() {
	}

	public MessageFilterModel(int order, Class<?> filterClazz) {
		this.order = order;
		this.filterClazz = filterClazz;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
	public Class<?> getFilterClazz() {
		return filterClazz;
	}
	
	public void setFilterClazz(Class<?> filterClazz) {
		this.filterClazz = filterClazz;
	}

	public IGGFilter<?> getFilter() {
		return filter;
	}

	public void setFilter(IGGFilter<?> filter) {
		this.filter = filter;
	}
	
	

}
