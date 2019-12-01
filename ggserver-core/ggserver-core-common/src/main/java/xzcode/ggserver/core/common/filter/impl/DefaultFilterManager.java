package xzcode.ggserver.core.common.filter.impl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import xzcode.ggserver.core.common.filter.IAfterSerializeFilter;
import xzcode.ggserver.core.common.filter.IBeforeDeserializeFilter;
import xzcode.ggserver.core.common.filter.IFilterManager;
import xzcode.ggserver.core.common.filter.IRequestFilter;
import xzcode.ggserver.core.common.filter.IResponseFilter;
import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.message.request.Request;
import xzcode.ggserver.core.common.message.response.Response;

/**
 * 默认过滤器管理器
 * 
 * 
 * @author zai
 * 2019-12-01 17:09:34
 */
public class DefaultFilterManager implements IFilterManager {

	private List<IBeforeDeserializeFilter> beforeDeserializeFilters = new CopyOnWriteArrayList<>();;
	private List<IRequestFilter> requestFilters = new CopyOnWriteArrayList<>();;
	private List<IResponseFilter> responseFilters = new CopyOnWriteArrayList<>();;
	private List<IAfterSerializeFilter> afterSerializeFilters = new CopyOnWriteArrayList<>();;

	public DefaultFilterManager() {
		super();
	}

	@Override
	public void addBeforeDeserializeFilter(IBeforeDeserializeFilter filter) {
		beforeDeserializeFilters.add(filter);
	}

	@Override
	public void removeBeforeDeserializeFilter(IBeforeDeserializeFilter filter) {
		beforeDeserializeFilters.remove(filter);
	}

	@Override
	public void addAfterSerializeFilter(IAfterSerializeFilter filter) {
		afterSerializeFilters.add(filter);
	}

	@Override
	public void removeAfterSerializeFilter(IAfterSerializeFilter filter) {
		afterSerializeFilters.remove(filter);
	}

	@Override
	public void addResponseFilter(IResponseFilter filter) {

		responseFilters.add(filter);
	}

	@Override
	public void removeResponseFilter(IResponseFilter filter) {
		responseFilters.remove(filter);
	}

	@Override
	public void addRequestFilter(IRequestFilter filter) {
		requestFilters.add(filter);
	}

	@Override
	public void removeRequestFilter(IRequestFilter filter) {
		requestFilters.remove(filter);
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
		for (IBeforeDeserializeFilter filter : beforeDeserializeFilters) {
			if (!filter.doFilter(pack)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean doRequestFilters(Request<?> request) {
		for (IRequestFilter filter : requestFilters) {
			if (!filter.doFilter(request)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean doResponseFilters(Response response) {
		for (IResponseFilter filter : responseFilters) {
			if (!filter.doFilter(response)) {
				return false;
			}
		}
		return true;
	}

}
