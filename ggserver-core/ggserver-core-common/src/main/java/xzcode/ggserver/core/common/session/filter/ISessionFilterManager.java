package xzcode.ggserver.core.common.session.filter;

import xzcode.ggserver.core.common.message.PackModel;
import xzcode.ggserver.core.common.message.receive.Request;
import xzcode.ggserver.core.common.message.send.Response;

public interface ISessionFilterManager extends ISessionFilterSupport{

	
	boolean doBeforeDeserializeFilters(PackModel pack);
	
	boolean doRequestFilters(Request request);
	
	boolean doResponseFilters(Response response);

}