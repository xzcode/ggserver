package xzcode.ggserver.core.common.filter;

public interface IFilterSupport {
	
	
	IFilterManager getFilterManager();
	
	default void addBeforeDeserializeFilter(IBeforeDeserializeFilter filter) {
		getFilterManager().addBeforeDeserializeFilter(filter);
	}
	default void addRequestFilter(IRequestFilter filter) {
		getFilterManager().addRequestFilter(filter);
	}
	default void addResponseFilter(IReponseFilter filter) {
		getFilterManager().addResponseFilter(filter);
	}
	
	

	default void removeBeforeDeserializeFilter(IBeforeDeserializeFilter filter) {
		getFilterManager().removeBeforeDeserializeFilter(filter);
	}

	default void removeResponseFilter(IReponseFilter filter) {
		getFilterManager().removeResponseFilter(filter);
	}

	default void removeRequestFilter(IRequestFilter filter) {
		getFilterManager().removeRequestFilter(filter);
	}
	
}
