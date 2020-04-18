package com.xzcode.ggcloud.session.group.client.events;

import com.xzcode.ggcloud.session.group.client.config.SessionGroupClientConfig;
import com.xzcode.ggcloud.session.group.client.session.ServiceClientSession;
import com.xzcode.ggcloud.session.group.common.group.manager.GGSessionGroupManager;
import com.xzcode.ggcloud.session.group.common.message.req.AuthReq;
import com.xzcode.ggserver.core.client.config.GGClientConfig;
import com.xzcode.ggserver.core.common.event.EventListener;
import com.xzcode.ggserver.core.common.event.model.EventData;
import com.xzcode.ggserver.core.common.session.GGSession;
import com.xzcode.ggserver.core.common.session.manager.ISessionManager;

/**
 * 连接打开事件监听
 *
 * @author zai
 * 2020-04-08 11:23:13
 */
public class ConnOpenEventListener implements EventListener<Void>{

	private SessionGroupClientConfig config;
	
	public ConnOpenEventListener(SessionGroupClientConfig config) {
		this.config = config;
	}

	@Override
	public void onEvent(EventData<Void> e) {
		//打开连接，发送认证
		GGSession groupSession = e.getSession();
		groupSession.send(new AuthReq(config.getAuthToken()));
		
		GGSessionGroupManager sessionGroupManager = this.config.getSessionGroupManager();
		sessionGroupManager.addSession(this.config.getSessionGroupId(), groupSession);
		
		
		if (this.config.isEnableServiceClient()) {
			
			GGClientConfig serviceClientConfig = this.config.getServiceClient().getConfig();
			ISessionManager sessionManager = serviceClientConfig.getSessionManager();
			
			ServiceClientSession serviceServerSession = new ServiceClientSession(groupSession.getSessonId(), this.config.getSessionGroupId(), sessionGroupManager, serviceClientConfig);
			GGSession addSessionIfAbsent = sessionManager.addSessionIfAbsent(serviceServerSession);
			if (addSessionIfAbsent != null) {
				serviceServerSession = (ServiceClientSession) addSessionIfAbsent;
			}
			sessionManager.addSessionIfAbsent(serviceServerSession);
		}
		
	}

}
