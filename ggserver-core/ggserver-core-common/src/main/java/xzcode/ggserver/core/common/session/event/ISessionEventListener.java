package xzcode.ggserver.core.common.session.event;

import xzcode.ggserver.core.common.session.GGSession;

public interface ISessionEventListener<T> {
	
	void onEvent(GGSession session, T e);
	
}
