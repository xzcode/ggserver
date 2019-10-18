package xzcode.ggserver.core.common.session.filter;

public interface ISessionFilterSupport {
	
	
	ISessionFilterManager getSessionFilterManager();
	
	default void addBeforeDeserializeFilter(ISessionBeforeDeserializeFilter filter) {
		getSessionFilterManager().addBeforeDeserializeFilter(filter);
	}
	default void addRequestFilter(ISessionRequestFilter filter) {
		getSessionFilterManager().addRequestFilter(filter);
	}
	default void addResponseFilter(ISessionReponseFilter filter) {
		getSessionFilterManager().addResponseFilter(filter);
	}
	
	

	default void removeBeforeDeserializeFilter(ISessionBeforeDeserializeFilter filter) {
		getSessionFilterManager().removeBeforeDeserializeFilter(filter);
	}

	default void removeResponseFilter(ISessionReponseFilter filter) {
		getSessionFilterManager().removeResponseFilter(filter);
	}

	default void removeRequestFilter(ISessionRequestFilter filter) {
		getSessionFilterManager().removeRequestFilter(filter);
	}
	
}
