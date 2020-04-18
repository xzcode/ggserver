package com.xzcode.ggserver.core.common.filter.impl;

import java.util.ArrayList;
import java.util.List;

import com.xzcode.ggserver.core.common.event.model.EventData;
import com.xzcode.ggserver.core.common.filter.AfterSerializeFilter;
import com.xzcode.ggserver.core.common.filter.BeforeDeserializeFilter;
import com.xzcode.ggserver.core.common.filter.EventFilter;
import com.xzcode.ggserver.core.common.filter.FilterManager;
import com.xzcode.ggserver.core.common.filter.ReceiveMessageFilter;
import com.xzcode.ggserver.core.common.filter.SendMessageFilter;
import com.xzcode.ggserver.core.common.message.MessageData;
import com.xzcode.ggserver.core.common.message.Pack;

/**
 * 默认过滤器管理器
 * 
 * @author zai
 * 2019-12-25 15:08:41
 */
public class DefaultFilterManager implements FilterManager {

	private List<BeforeDeserializeFilter> beforeDeserializeFilters = new ArrayList<>();
	private List<ReceiveMessageFilter> requestFilters = new ArrayList<>();
	private List<SendMessageFilter> responseFilters = new ArrayList<>();
	private List<AfterSerializeFilter> afterSerializeFilters = new ArrayList<>();
	private List<EventFilter> eventFilters = new ArrayList<>();

	public DefaultFilterManager() {
		super();
	}

	@Override
	public void addRequestFilter(ReceiveMessageFilter filter) {
		requestFilters.add(filter);
	}


	@Override
	public void addResponseFilter(SendMessageFilter filter) {

		responseFilters.add(filter);
	}
	
	@Override
	public void addBeforeDeserializeFilter(BeforeDeserializeFilter filter) {
		beforeDeserializeFilters.add(filter);
	}
	
	@Override
	public void addAfterSerializeFilter(AfterSerializeFilter filter) {
		afterSerializeFilters.add(filter);
	}
	
	@Override
	public void addEventFilter(EventFilter filter) {
		eventFilters.add(filter);
		
	}
	
	
	
	
	
	@Override
	public void removeBeforeDeserializeFilter(BeforeDeserializeFilter filter) {
		beforeDeserializeFilters.remove(filter);
	}

	@Override
	public void removeAfterSerializeFilter(AfterSerializeFilter filter) {
		afterSerializeFilters.remove(filter);
	}


	@Override
	public void removeResponseFilter(SendMessageFilter filter) {
		responseFilters.remove(filter);
	}


	@Override
	public void removeRequestFilter(ReceiveMessageFilter filter) {
		requestFilters.remove(filter);
	}
	
	@Override
	public void removeEventFilter(EventFilter filter) {
		eventFilters.remove(filter);
		
	}

	@Override
	public boolean doBeforeDeserializeFilters(Pack pack) {
		for (BeforeDeserializeFilter filter : beforeDeserializeFilters) {
			if (!filter.doFilter(pack)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean doAfterSerializeFilters(Pack pack) {
		for (AfterSerializeFilter filter : afterSerializeFilters) {
			if (!filter.doFilter(pack)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean doRequestFilters(MessageData<?> request) {
		for (ReceiveMessageFilter filter : requestFilters) {
			if (!filter.doFilter(request)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean doResponseFilters(MessageData<?> response) {
		for (SendMessageFilter filter : responseFilters) {
			if (!filter.doFilter(response)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean doEventFilters(EventData<?> eventData) {
		for (EventFilter filter : eventFilters) {
			if (!filter.doFilter(eventData)) {
				return false;
			}
		}
		return true;
	}

}
