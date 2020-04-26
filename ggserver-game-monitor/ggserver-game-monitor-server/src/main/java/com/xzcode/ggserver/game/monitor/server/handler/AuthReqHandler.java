package com.xzcode.ggserver.game.monitor.server.handler;

import com.xzcode.ggserver.game.monitor.common.message.req.AuthReq;
import com.xzcode.ggserver.game.monitor.common.message.resp.AuthResp;
import com.xzcode.ggserver.game.monitor.server.config.GameMonitorServerConfig;
import com.xzcode.ggserver.game.monitor.server.constant.GameMonitorServerSessionKeys;

import xzcode.ggserver.core.common.message.request.Request;
import xzcode.ggserver.core.common.message.request.action.IRequestMessageHandler;
import xzcode.ggserver.core.common.session.GGSession;

/**
 * 客户端认证处理
 *
 * @author zai
 * 2020-04-26 10:35:45
 */
public class AuthReqHandler implements IRequestMessageHandler<AuthReq>{
	
	private GameMonitorServerConfig config;
	

	public AuthReqHandler(GameMonitorServerConfig config) {
		super();
		this.config = config;
	}
	


	@Override
	public void handle(Request<AuthReq> request) {
		GGSession session = request.getSession();
		AuthReq req = request.getMessage();
		String serverAuthToken = config.getAuthToken();
		//判断认证token是否正确
		if (serverAuthToken != null && !serverAuthToken.isEmpty()) {
			String clientAuthToken = req.getAuthToken();
			if (clientAuthToken != null && !clientAuthToken.isEmpty() && clientAuthToken.equals(serverAuthToken)) {
				session.addAttribute(GameMonitorServerSessionKeys.IS_AUTHED, true);
				session.send(new AuthResp(true));
				return;
			}
		}
		session.send(new AuthResp(false, "Auth Token Is Incorrect"));
	}


	

}
