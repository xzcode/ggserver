package xzcode.ggserver.core.common.event.impl;

import xzcode.ggserver.core.common.event.IEventListenerGroup;
import xzcode.ggserver.core.common.session.GGSession;

public class DefaultSessionEventManager extends DefaultEventManager {
	
	protected GGSession session;
	

	public DefaultSessionEventManager(GGSession session) {
		this.session = session;
	}

	@Override
	public <T> IEventListenerGroup<T> createEventListenerGroup() {
		return new DefaultSessionEventListenerGroup<>(session);
	}
	
}
