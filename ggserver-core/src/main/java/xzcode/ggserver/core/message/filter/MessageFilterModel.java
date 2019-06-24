package xzcode.ggserver.core.message.filter;

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
	
	private Class<?> filterClazz;
	
	/**
	 * 请求过滤器
	 */
	private GGRequestFilter requestFilter;
	
	/**
	 * 预反序列化过滤器
	 */
	private GGPreDeserializeFilter preDeserializeFilter;
	
	
	/**
	 * 响应过滤器
	 */
	private GGResponseFilter responseFilter;
	
	


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

	public GGRequestFilter getRequestFilter() {
		return requestFilter;
	}

	public void setRequestFilter(GGRequestFilter filter) {
		this.requestFilter = filter;
	}
	
	public Class<?> getFilterClazz() {
		return filterClazz;
	}
	
	public void setFilterClazz(Class<?> filterClazz) {
		this.filterClazz = filterClazz;
	}
	
	public GGResponseFilter getResponseFilter() {
		return responseFilter;
	}
	
	public void setResponseFilter(GGResponseFilter responseFilter) {
		this.responseFilter = responseFilter;
	}

	public GGPreDeserializeFilter getPreDeserializeFilter() {
		return preDeserializeFilter;
	}

	public void setPreDeserializeFilter(GGPreDeserializeFilter preDeserializeFilter) {
		this.preDeserializeFilter = preDeserializeFilter;
	}

	
	
	

}
