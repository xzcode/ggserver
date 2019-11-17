package xzcode.ggserver.core.common.filter;

public interface IFilterSupport {
	
	
	IFilterManager getFilterManager();
	
	default void addBeforeDeserializeFilter(IBeforeDeserializeFilter filter) {
		getFilterManager().addBeforeDeserializeFilter(filter);
	}
	default void addRequestFilter(IReceiveFilter filter) {
		getFilterManager().addRequestFilter(filter);
	}
	default void addResponseFilter(ISendFilter filter) {
		getFilterManager().addResponseFilter(filter);
	}
	
	default void removeBeforeDeserializeFilter(IBeforeDeserializeFilter filter) {
		getFilterManager().removeBeforeDeserializeFilter(filter);
	}

	default void removeResponseFilter(ISendFilter filter) {
		getFilterManager().removeResponseFilter(filter);
	}

	default void removeRequestFilter(IReceiveFilter filter) {
		getFilterManager().removeRequestFilter(filter);
	}

	default void addAfterSerializeFilter(IAfterSerializeFilter filter) {
		getFilterManager().addAfterSerializeFilter(filter);
	};

	default void removeAfterSerializeFilter(IAfterSerializeFilter filter) {
		getFilterManager().removeAfterSerializeFilter(filter);
	};
	
}
