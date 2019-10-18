package xzcode.ggserver.core.common.session.filter.impl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import xzcode.ggserver.core.common.message.PackModel;
import xzcode.ggserver.core.common.message.receive.Request;
import xzcode.ggserver.core.common.message.send.Response;
import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.core.common.session.filter.ISessionBeforeDeserializeFilter;
import xzcode.ggserver.core.common.session.filter.ISessionFilterManager;
import xzcode.ggserver.core.common.session.filter.ISessionReponseFilter;
import xzcode.ggserver.core.common.session.filter.ISessionRequestFilter;

public class SessionFilterManager implements ISessionFilterManager {
	
	private GGSession session;
	
	private List<ISessionBeforeDeserializeFilter> beforeDeserializeFilters;
	private List<ISessionRequestFilter> requestFilters;
	private List<ISessionReponseFilter> responseFilters;
	
	public SessionFilterManager(GGSession session) {
		super();
		this.session = session;
	}
	@Override
	public void addBeforeDeserializeFilter(ISessionBeforeDeserializeFilter filter) {
		
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
	public void removeBeforeDeserializeFilter(ISessionBeforeDeserializeFilter filter) {
		
		if (beforeDeserializeFilters == null) {
			return;
		}
		beforeDeserializeFilters.remove(filter);
	}
	
	@Override
	public void addResponseFilter(ISessionReponseFilter filter) {
		
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
	public void removeResponseFilter(ISessionReponseFilter filter) {
		if (responseFilters == null) {
			return;
		}
		responseFilters.remove(filter);
	}
	
	@Override
	public void addRequestFilter(ISessionRequestFilter filter) {
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
	public void removeRequestFilter(ISessionRequestFilter filter) {
		if (requestFilters == null) {
			return;
		}
		requestFilters.remove(filter);
	}
	
	public boolean doBeforeDeserializeFilters(PackModel pack) {
		if (beforeDeserializeFilters == null) {
			return true;
		}
		for (ISessionBeforeDeserializeFilter filter : beforeDeserializeFilters) {
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
		for (ISessionRequestFilter filter : requestFilters) {
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
		for (ISessionReponseFilter filter : responseFilters) {
			if (!filter.doFilter(session, response)) {
				return false;
			}
		}
		return true;
	}
	@Override
	public ISessionFilterManager getSessionFilterManager() {
		return this;
	}
	
}
