package com.xzcode.ggcloud.session.group.client.events;

import com.xzcode.ggcloud.session.group.client.config.SessionGroupClientConfig;
import com.xzcode.ggserver.core.common.event.EventListener;
import com.xzcode.ggserver.core.common.event.model.EventData;
import com.xzcode.ggserver.core.common.session.manager.ISessionManager;

public class ConnCloseEventListener implements EventListener<Void>{
	
	private SessionGroupClientConfig config;
	
	public ConnCloseEventListener(SessionGroupClientConfig config) {
		super();
		this.config = config;
	}


	@Override
	public void onEvent(EventData<Void> eventData) {
		
		ISessionManager sessionManager = this.config.getServiceClient().getSessionManager();
		sessionManager.remove(eventData.getSession().getSessonId());
		
		//断开连接后，创建新连接
		this.config.getSessionGroupClient().connectOne(config.getServerHost(), config.getServerPort());
	}


	
}
