package com.xzcode.socket.core.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzcode.socket.core.component.SocketComponentObjectManager;
import com.xzcode.socket.core.message.invoker.IMessageInvoker;
import com.xzcode.socket.core.message.invoker.MethodInvoker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 消息过滤器集合
 *
 * @author zai
 * 2018-12-20 10:15:31
 */
public class MessageFilterManager {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageFilterManager.class);
	
	private final List<MessageFilterModel> filters = new ArrayList<>(1);
	
	
	
	public void updateComponentObject(SocketComponentObjectManager componentObjectMapper) {
		for (MessageFilterModel filterModel : filters) {
			filterModel.setFilter((SocketMessageFilter) componentObjectMapper.getSocketComponentObject(filterModel.getFilterClazz()));
		}
	}
	
	
	public void add(MessageFilterModel filterModel) {
		filters.add(filterModel);
		if (filters.size() > 1) {
			sort();
		}
	}
	
	public void sort() {
		filters.sort(new Comparator<MessageFilterModel>() {

			@Override
			public int compare(MessageFilterModel o1, MessageFilterModel o2) {
				if (o1.getOrder() > o2.getOrder()) {
					return 1;
				}
				return -1;
			}
		});
	}
	
	/**
	 * 顺序执行过滤器
	 * @param requestTag
	 * @param message
	 * @return
	 * 
	 * @author zai
	 * 2017-09-27
	 */
	public boolean doFilters(String requestTag, Object message) {
		SocketMessageFilter filter = null;
		for(int i = 0; i < filters.size(); i ++) {
			filter = filters.get(i).getFilter();
			if (!filter.doFilter(requestTag, message)) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Message filtered by {}, requestTag:{} .", filter.getClass().getName(),requestTag);					
				}
				return false;
			}
		}
		return true;
	}

}
