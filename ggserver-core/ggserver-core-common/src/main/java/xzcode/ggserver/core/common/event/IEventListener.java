package xzcode.ggserver.core.common.event;

import xzcode.ggserver.core.common.event.model.EventData;

public interface IEventListener<T> {
	
	void onEvent(EventData<T> eventData);
	
}
