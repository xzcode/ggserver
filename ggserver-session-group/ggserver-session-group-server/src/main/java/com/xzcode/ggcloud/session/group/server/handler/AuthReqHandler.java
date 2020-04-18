package com.xzcode.ggcloud.session.group.server.handler;

import com.xzcode.ggcloud.session.group.common.message.req.AuthReq;
import com.xzcode.ggcloud.session.group.common.message.resp.AuthResp;
import com.xzcode.ggcloud.session.group.server.config.SessionGroupServerConfig;
import com.xzcode.ggserver.core.common.message.MessageData;
import com.xzcode.ggserver.core.common.message.request.action.MessageDataHandler;
import com.xzcode.ggserver.core.common.session.GGSession;

/**
 * 客户端认证请求
 *
 * @author zai 2020-04-07 10:57:11
 */
public class AuthReqHandler implements MessageDataHandler<AuthReq> {

	private SessionGroupServerConfig config;

	public AuthReqHandler(SessionGroupServerConfig config) {
		super();
		this.config = config;
	}

	@Override
	public void handle(MessageData<AuthReq> request) {
		GGSession session = request.getSession();
		AuthReq req = request.getMessage();
		if (this.config.getAuthToken().equals(req.getAuthToken())) {
			session.send(new AuthResp(true));
		}
	}

}
