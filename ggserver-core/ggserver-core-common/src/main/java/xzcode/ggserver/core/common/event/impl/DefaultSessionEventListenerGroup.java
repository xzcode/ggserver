package xzcode.ggserver.core.common.event.impl;

import xzcode.ggserver.core.common.event.IEventListener;
import xzcode.ggserver.core.common.event.model.EventData;
import xzcode.ggserver.core.common.session.GGSession;

/**
 * 默认会话事件监听组
 * 
 * @param <T>
 * @author zai
 * 2019-10-18 11:56:31
 */
public class DefaultSessionEventListenerGroup<T> extends DefaultEventListenerGroup<T> {
	
	
	protected GGSession session;
	
	public DefaultSessionEventListenerGroup(GGSession session) {
		super();
		this.session = session;
	}
	
	@Override
	public void onEvent(T e) {
		for (IEventListener<T> li : listeners) {
			li.onEvent(new EventData<T>(session, e));
		}
	}

}
