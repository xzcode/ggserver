package xzcode.ggserver.core.common.session.filter;

public interface IGGSessionMessageFilterSupport {
	
	
	GGSessionMessageFilterManager getGGSessionMessageFilterManager();
	
	default void addBeforeDeserializeFilter(ISessionBeforeDeserializeFilter filter) {
		getGGSessionMessageFilterManager().addBeforeDeserializeFilter(filter);
	}
	default void addRequestFilter(ISessionRequestFilter filter) {
		getGGSessionMessageFilterManager().addRequestFilter(filter);
	}
	default void addResponseFilter(ISessionReponseFilter filter) {
		getGGSessionMessageFilterManager().addResponseFilter(filter);
	}
	
}
