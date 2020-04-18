package com.xzcode.ggcloud.session.group.common.message.resp;

import com.xzcode.ggcloud.session.group.common.constant.GGSesssionGroupConstant;
import com.xzcode.ggserver.core.common.message.model.IMessage;

/**
 * 客户端认证响应
 *
 * @author zai
 * 2020-04-06 19:01:38
 */
public class AuthResp implements IMessage{
	
	public static final String ACTION_ID = GGSesssionGroupConstant.ACTION_ID_PREFIX + "AUTH.RESP";
	
	@Override
	public String getActionId() {
		return ACTION_ID;
	}
	
	/**
	 * 是否注册成功
	 */
	private boolean success;
	
	/**
	 * 响应码
	 */
	private int code;
	
	/**
	 * 消息
	 */
	private String message;
	

	public AuthResp(boolean success) {
		super();
		this.success = success;
	}
	public AuthResp(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public AuthResp() {
		super();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	
	
	
	
}
