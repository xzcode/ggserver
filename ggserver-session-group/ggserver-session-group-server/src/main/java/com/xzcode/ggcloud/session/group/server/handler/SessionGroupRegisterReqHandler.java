package com.xzcode.ggcloud.session.group.server.handler;

import com.xzcode.ggcloud.session.group.common.group.manager.GGSessionGroupManager;
import com.xzcode.ggcloud.session.group.common.message.req.SessionGroupRegisterReq;
import com.xzcode.ggcloud.session.group.common.message.resp.SessionGroupRegisterResp;
import com.xzcode.ggcloud.session.group.server.config.SessionGroupServerConfig;
import com.xzcode.ggcloud.session.group.server.constant.SessionGroupServerSessionKeys;
import com.xzcode.ggserver.core.common.message.MessageData;
import com.xzcode.ggserver.core.common.message.request.action.MessageDataHandler;
import com.xzcode.ggserver.core.common.session.GGSession;

/**
 * 内置ping处理器
 * 
 * @author zai
 * 2020-01-16 17:04:11
 */
public class SessionGroupRegisterReqHandler implements MessageDataHandler<SessionGroupRegisterReq> {
	
	protected SessionGroupServerConfig config;
	
	public SessionGroupRegisterReqHandler(SessionGroupServerConfig config) {
		this.config = config;
	}

	@Override
	public void handle(MessageData<SessionGroupRegisterReq> request) {
		GGSession session = request.getSession();
		SessionGroupRegisterReq req = request.getMessage();
		String groupId = req.getGroupId();
		GGSessionGroupManager sessionGroupManager = config.getSessionGroupManager();
		
		session.addAttribute(SessionGroupServerSessionKeys.GROUP_SESSION_GROUP_ID, groupId);
		session.setReady(true);
		sessionGroupManager.addSession(groupId, session);
		
		session.send(new SessionGroupRegisterResp(true));
		
	}



}
