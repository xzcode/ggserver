package com.xzcode.ggserver.game.monitor.common.message.req;

import com.xzcode.ggserver.game.monitor.common.constant.GameMonitorConstant;

import xzcode.ggserver.core.common.message.model.IMessage;

/**
 * 客户端注册请求
 * 
 * 
 * @author zai
 * 2019-10-04 16:43:22
 */
public class AuthReq implements IMessage {
	
	public static final String ACTION_ID = GameMonitorConstant.ACTION_ID_PREFIX + "AUTH.REQ";
	
	@Override
	public String getActionId() {
		return ACTION_ID;
	}
	
	//认证token
	private String authToken;
	

	public AuthReq() {
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	
	public String getAuthToken() {
		return authToken;
	}
	
}
