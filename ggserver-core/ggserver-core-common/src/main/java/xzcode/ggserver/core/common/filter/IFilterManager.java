package xzcode.ggserver.core.common.filter;

import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.message.receive.Request;
import xzcode.ggserver.core.common.message.send.Response;
import xzcode.ggserver.core.common.session.GGSession;

public interface IFilterManager extends IFilterSupport{

	
	boolean doBeforeDeserializeFilters(GGSession session, Pack pack);
	
	boolean doRequestFilters(Request request);
	
	boolean doResponseFilters(Response response);

	boolean doAfterSerializeFilters(GGSession session, Pack pack);

}