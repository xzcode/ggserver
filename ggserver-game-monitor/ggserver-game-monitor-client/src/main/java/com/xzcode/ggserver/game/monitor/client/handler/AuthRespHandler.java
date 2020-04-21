package com.xzcode.ggserver.game.monitor.client.handler;

import java.util.List;

import com.xzcode.ggserver.game.monitor.client.GameMonitorClient;
import com.xzcode.ggserver.game.monitor.client.config.GameMonitorClientConfig;
import com.xzcode.ggserver.game.monitor.client.listener.IClientRegisterSuccessListener;
import com.xzcode.ggserver.game.monitor.common.message.req.DiscoveryServiceListReq;
import com.xzcode.ggserver.game.monitor.common.message.resp.AuthResp;

import xzcode.ggserver.core.common.message.request.Request;
import xzcode.ggserver.core.common.message.request.action.IRequestMessageHandler;

/**
 * 客户端注册请求处理
 * 
 * 
 * @author zai
 * 2019-10-04 14:29:53
 */
public class AuthRespHandler implements IRequestMessageHandler<AuthResp>{
	
	private GameMonitorClientConfig config;
	

	public AuthRespHandler(GameMonitorClientConfig config) {
		super();
		this.config = config;
	}



	@Override
	public void handle(Request<AuthResp> request) {
		AuthResp resp = request.getMessage();
		if (resp.isSuccess()) {
			config.getSession().send(DiscoveryServiceListReq.DEFAULT_INSTANT);
			GameMonitorClient discoveryClient = config.getDiscoveryClient();
			List<IClientRegisterSuccessListener> registerSuccessListeners = discoveryClient.getRegisterSuccessListeners();
			for (IClientRegisterSuccessListener listener : registerSuccessListeners) {
				listener.onRegisterSuccess();
			}
		}
	}

	

}
