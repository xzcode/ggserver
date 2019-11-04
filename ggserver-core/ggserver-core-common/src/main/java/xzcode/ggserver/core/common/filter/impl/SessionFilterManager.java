package xzcode.ggserver.core.common.filter.impl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import xzcode.ggserver.core.common.filter.IBeforeDeserializeFilter;
import xzcode.ggserver.core.common.filter.IFilterManager;
import xzcode.ggserver.core.common.filter.IReponseFilter;
import xzcode.ggserver.core.common.filter.IRequestFilter;
import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.message.receive.Request;
import xzcode.ggserver.core.common.message.send.Response;
import xzcode.ggserver.core.common.session.GGSession;

public class SessionFilterManager implements IFilterManager {
	
	private GGSession session;
	
	private List<IBeforeDeserializeFilter> beforeDeserializeFilters;
	private List<IRequestFilter> requestFilters;
	private List<IReponseFilter> responseFilters;
	
	public SessionFilterManager(GGSession session) {
		super();
		this.session = session;
	}
	@Override
	public void addBeforeDeserializeFilter(IBeforeDeserializeFilter filter) {
		
		if (beforeDeserializeFilters == null) {
			synchronized (this) {
				if (beforeDeserializeFilters == null) {
					beforeDeserializeFilters = new CopyOnWriteArrayList<>();		
				}
			}
		}
		beforeDeserializeFilters.add(filter);
		return;
	}
	@Override
	public void removeBeforeDeserializeFilter(IBeforeDeserializeFilter filter) {
		
		if (beforeDeserializeFilters == null) {
			return;
		}
		beforeDeserializeFilters.remove(filter);
	}
	
	@Override
	public void addResponseFilter(IReponseFilter filter) {
		
		if (responseFilters == null) {
			synchronized (this) {
				if (responseFilters == null) {
					responseFilters = new CopyOnWriteArrayList<>();		
				}
			}
		}
		responseFilters.add( filter);
		return;
	}
	@Override
	public void removeResponseFilter(IReponseFilter filter) {
		if (responseFilters == null) {
			return;
		}
		responseFilters.remove(filter);
	}
	
	@Override
	public void addRequestFilter(IRequestFilter filter) {
		if (requestFilters == null) {
			synchronized (this) {
				if (requestFilters == null) {
					requestFilters = new CopyOnWriteArrayList<>();		
				}
			}
		}
		requestFilters.add( filter);
		return;
	}
	
	@Override
	public void removeRequestFilter(IRequestFilter filter) {
		if (requestFilters == null) {
			return;
		}
		requestFilters.remove(filter);
	}
	
	public boolean doBeforeDeserializeFilters(Pack pack) {
		if (beforeDeserializeFilters == null) {
			return true;
		}
		for (IBeforeDeserializeFilter filter : beforeDeserializeFilters) {
			if (!filter.doFilter(session, pack)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean doRequestFilters(Request request) {
		if (requestFilters == null) {
			return true;
		}
		for (IRequestFilter filter : requestFilters) {
			if (!filter.doFilter(session, request)) {
				return false;
			}
		}
		return true;
	}
	
	
	public boolean doResponseFilters(Response response) {
		if (responseFilters == null) {
			return true;
		}
		for (IReponseFilter filter : responseFilters) {
			if (!filter.doFilter(session, response)) {
				return false;
			}
		}
		return true;
	}
	@Override
	public IFilterManager getFilterManager() {
		return this;
	}
	
}