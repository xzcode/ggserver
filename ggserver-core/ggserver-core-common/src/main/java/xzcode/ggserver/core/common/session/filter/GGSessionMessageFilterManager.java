package xzcode.ggserver.core.common.session.filter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import xzcode.ggserver.core.common.message.PackModel;
import xzcode.ggserver.core.common.message.receive.Request;
import xzcode.ggserver.core.common.message.send.Response;

public class GGSessionMessageFilterManager {
	
	private List<ISessionBeforeDeserializeFilter> beforeDeserializeFilters;
	private List<ISessionRequestFilter> requestFilters;
	private List<ISessionReponseFilter> responseFilters;
	
	
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
	
	
	
	
	
	public boolean doBeforeDeserializeFilters(PackModel pack) {
		if (beforeDeserializeFilters == null) {
			return true;
		}
		for (ISessionBeforeDeserializeFilter filter : beforeDeserializeFilters) {
			if (!filter.doFilter(pack)) {
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
			if (!filter.doFilter(request)) {
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
			if (!filter.doFilter(response)) {
				return false;
			}
		}
		return true;
	}
	
}
