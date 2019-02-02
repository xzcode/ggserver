package com.xzcode.socket.core.filter;

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
	
	private SocketMessageFilter filter;
	
	


	public MessageFilterModel(int order, Class<?> filterClazz) {
		super();
		this.order = order;
		this.filterClazz = filterClazz;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public SocketMessageFilter getFilter() {
		return filter;
	}

	public void setFilter(SocketMessageFilter filter) {
		this.filter = filter;
	}
	
	public Class<?> getFilterClazz() {
		return filterClazz;
	}
	
	public void setFilterClazz(Class<?> filterClazz) {
		this.filterClazz = filterClazz;
	}
	

}
