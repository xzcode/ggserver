package xzcode.ggserver.core.message.filter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.core.component.GGComponentManager;

/**
 * 消息过滤器集合
 *
 * @author zai
 * 2018-12-20 10:15:31
 */
public class MessageFilterManager {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageFilterManager.class);
	/**
	 * 请求过滤器
	 */
	private final ArrayList<MessageFilterModel> requestFilters = new ArrayList<>(1);
	
	/**
	 * 响应过滤器
	 */
	private final ArrayList<MessageFilterModel> responseFilters = new ArrayList<>(1);
	
	
	/**
	 * 更新组件引用的实例
	 * @param componentObjectMapper
	 * 
	 * @author zai
	 * 2019-02-09 14:24:04
	 */
	public void updateComponentObject(GGComponentManager componentObjectMapper) {
		
		for (MessageFilterModel filterModel : requestFilters) {
			Object object = componentObjectMapper.getComponentObject(filterModel.getFilterClazz());
			if (object instanceof GGRequestFilter ) {
				filterModel.setRequestFilter((GGRequestFilter) object);				
			}else if (object instanceof GGResponseFilter ) {
				filterModel.setResponseFilter((GGResponseFilter) object);	
			}
		}
		
	}
	
	/**
	 * 添加过滤器
	 * @param filterModel
	 * 
	 * @author zai
	 * 2019-02-09 14:24:29
	 */
	public void add(MessageFilterModel filterModel) {
		if (filterModel.getFilterClazz() == GGRequestFilter.class) {
			requestFilters.add(filterModel);
			if (requestFilters.size() > 1) {
				sort(requestFilters);
			}
			requestFilters.trimToSize();
		}else if (filterModel.getFilterClazz() == GGResponseFilter.class ) {
			responseFilters.add(filterModel);
			if (responseFilters.size() > 1) {
				sort(responseFilters);
			}
			responseFilters.trimToSize();
		}
	}
	
	public void sort(List<MessageFilterModel> list) {
		list.sort((o1,o2) -> {
			if (o1.getOrder() > o2.getOrder()) {
				return 1;
			}
			return -1;
		});
	}
	
	/**
	 * 顺序执行请求过滤器
	 * @param action
	 * @param message
	 * @return
	 * 
	 * @author zai
	 * 2017-09-27
	 */
	public boolean doRequestFilters(String action, Object message) {
		GGRequestFilter filter = null;
		for (MessageFilterModel filterModel : requestFilters) {
			filter = filterModel.getRequestFilter();
			if (!filter.doFilter(action, message)) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Message filtered by {}, requestTag:{} .", filter.getClass().getName(),action);					
				}
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 顺序执行响应过滤器
	 * @param action
	 * @param message
	 * @return
	 * 
	 * @author zai
	 * 2017-09-27
	 */
	public boolean doResponseFilters(Object userId, String action, Object message) {
		GGResponseFilter filter = null;
		for (MessageFilterModel filterModel : responseFilters) {
			filter = filterModel.getResponseFilter();
			if (!filter.doFilter(userId, action, message)) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Message filtered by {}, requestTag:{} .", filter.getClass().getName(),action);					
				}
				return false;
			}
		}
		return true;
	}

}
