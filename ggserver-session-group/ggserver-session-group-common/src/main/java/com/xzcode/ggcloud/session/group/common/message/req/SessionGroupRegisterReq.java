package com.xzcode.ggcloud.session.group.common.message.req;

import com.xzcode.ggcloud.session.group.common.constant.GGSesssionGroupConstant;
import com.xzcode.ggserver.core.common.message.model.IMessage;

/**
 * 会话组注册请求
 *
 * @author zai 2020-04-07 16:45:30
 */
public class SessionGroupRegisterReq implements IMessage{
	public static final String ACTION_ID = GGSesssionGroupConstant.ACTION_ID_PREFIX + "REGISTER.REQ";
	
	@Override
	public String getActionId() {
		return ACTION_ID;
	}

	// 会话组id
	private String groupId;

	public SessionGroupRegisterReq() {
	}

	public SessionGroupRegisterReq(String groupId) {
		super();
		this.groupId = groupId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}
