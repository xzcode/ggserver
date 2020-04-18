package com.xzcode.ggcloud.session.group.common.message.resp;

import com.xzcode.ggcloud.session.group.common.constant.GGSesssionGroupConstant;
import com.xzcode.ggserver.core.common.message.model.IMessage;

/**
 * 会话组注册响应
 *
 * @author zai
 * 2020-04-07 16:51:05
 */
public class SessionGroupRegisterResp implements IMessage{
	
	public static final String ACTION_ID = GGSesssionGroupConstant.ACTION_ID_PREFIX + "REGISTER.RESP";
	
	@Override
	public String getActionId() {
		return ACTION_ID;
	}
	
	// 是否成功
	private boolean success;


	public SessionGroupRegisterResp() {
		super();
	}


	public SessionGroupRegisterResp(boolean success) {
		super();
		this.success = success;
	}


	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	
	
	
}
