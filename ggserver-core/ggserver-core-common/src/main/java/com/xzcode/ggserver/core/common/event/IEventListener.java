package com.xzcode.ggserver.core.common.event;

import com.xzcode.ggserver.core.common.event.model.EventData;

public interface IEventListener<T> {
	
	void onEvent(EventData<T> eventData);
	
}
