package xzcode.ggserver.core.common.filter.impl;

import java.util.ArrayList;
import java.util.List;

import xzcode.ggserver.core.common.event.model.EventData;
import xzcode.ggserver.core.common.filter.IAfterSerializeFilter;
import xzcode.ggserver.core.common.filter.IBeforeDeserializeFilter;
import xzcode.ggserver.core.common.filter.IEventFilter;
import xzcode.ggserver.core.common.filter.IFilterManager;
import xzcode.ggserver.core.common.filter.IRequestFilter;
import xzcode.ggserver.core.common.filter.IResponseFilter;
import xzcode.ggserver.core.common.message.MessageData;
import xzcode.ggserver.core.common.message.Pack;

/**
 * 默认过滤器管理器
 * 
 * @author zai
 * 2019-12-25 15:08:41
 */
public class DefaultFilterManager implements IFilterManager {

	private List<IBeforeDeserializeFilter> beforeDeserializeFilters = new ArrayList<>();
	private List<IRequestFilter> requestFilters = new ArrayList<>();
	private List<IResponseFilter> responseFilters = new ArrayList<>();
	private List<IAfterSerializeFilter> afterSerializeFilters = new ArrayList<>();
	private List<IEventFilter> eventFilters = new ArrayList<>();

	public DefaultFilterManager() {
		super();
	}

	@Override
	public void addRequestFilter(IRequestFilter filter) {
		requestFilters.add(filter);
	}


	@Override
	public void addResponseFilter(IResponseFilter filter) {

		responseFilters.add(filter);
	}
	
	@Override
	public void addBeforeDeserializeFilter(IBeforeDeserializeFilter filter) {
		beforeDeserializeFilters.add(filter);
	}
	
	@Override
	public void addAfterSerializeFilter(IAfterSerializeFilter filter) {
		afterSerializeFilters.add(filter);
	}
	
	@Override
	public void addEventFilter(IEventFilter filter) {
		eventFilters.add(filter);
		
	}
	
	
	
	
	
	@Override
	public void removeBeforeDeserializeFilter(IBeforeDeserializeFilter filter) {
		beforeDeserializeFilters.remove(filter);
	}

	@Override
	public void removeAfterSerializeFilter(IAfterSerializeFilter filter) {
		afterSerializeFilters.remove(filter);
	}


	@Override
	public void removeResponseFilter(IResponseFilter filter) {
		responseFilters.remove(filter);
	}


	@Override
	public void removeRequestFilter(IRequestFilter filter) {
		requestFilters.remove(filter);
	}
	
	@Override
	public void removeEventFilter(IEventFilter filter) {
		eventFilters.remove(filter);
		
	}

	@Override
	public boolean doBeforeDeserializeFilters(Pack pack) {
		for (IBeforeDeserializeFilter filter : beforeDeserializeFilters) {
			if (!filter.doFilter(pack)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean doAfterSerializeFilters(Pack pack) {
		for (IAfterSerializeFilter filter : afterSerializeFilters) {
			if (!filter.doFilter(pack)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean doRequestFilters(MessageData<?> request) {
		for (IRequestFilter filter : requestFilters) {
			if (!filter.doFilter(request)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean doResponseFilters(MessageData<?> response) {
		for (IResponseFilter filter : responseFilters) {
			if (!filter.doFilter(response)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean doEventFilters(EventData<?> eventData) {
		for (IEventFilter filter : eventFilters) {
			if (!filter.doFilter(eventData)) {
				return false;
			}
		}
		return true;
	}

}
