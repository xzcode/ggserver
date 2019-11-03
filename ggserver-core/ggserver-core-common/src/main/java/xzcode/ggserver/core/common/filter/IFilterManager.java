package xzcode.ggserver.core.common.filter;

import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.message.receive.Request;
import xzcode.ggserver.core.common.message.send.Response;

public interface IFilterManager extends IFilterSupport{

	
	boolean doBeforeDeserializeFilters(Pack pack);
	
	boolean doRequestFilters(Request request);
	
	boolean doResponseFilters(Response response);

}