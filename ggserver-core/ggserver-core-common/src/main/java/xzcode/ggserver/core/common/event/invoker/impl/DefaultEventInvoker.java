package xzcode.ggserver.core.common.event.invoker.impl;

import xzcode.ggserver.core.common.event.IEventHandler;
import xzcode.ggserver.core.common.event.invoker.IEventInvoker;

/**
 * 可执行事件调用
 * 
 * @author zai
 * 2019-01-02 19:49:46
 */
public class DefaultEventInvoker implements IEventInvoker {
	
	private String event;
	
	@SuppressWarnings("rawtypes")
	private IEventHandler action;
	

	
	@SuppressWarnings("rawtypes")
	public void setAction(IEventHandler action) {
		this.action = action;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public void invoke(Object message) throws Exception {
		action.onEvent(message);
	}
	

	public String getEvent() {
		return event;
	}

	public DefaultEventInvoker setEventTag(String eventTag) {
		this.event = eventTag;
		return this;
	}


	@Override
	public IEventHandler<?> getEventHandler() {
		return action;
	}
	


	
}

