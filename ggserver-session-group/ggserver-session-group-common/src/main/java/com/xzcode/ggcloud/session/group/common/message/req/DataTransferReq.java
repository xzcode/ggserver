package com.xzcode.ggcloud.session.group.common.message.req;

import com.xzcode.ggcloud.session.group.common.constant.GGSesssionGroupConstant;
import com.xzcode.ggserver.core.common.message.model.IMessage;

/**
 * 数据传输请求
 *
 * @author zai 2020-04-08 10:31:48
 */
public class DataTransferReq implements IMessage {

	public static final String ACTION = GGSesssionGroupConstant.ACTION_ID_PREFIX + "DATA.TRANSFER.REQ";

	@Override
	public String getActionId() {
		return ACTION;
	}

	// 传递的会话id
	private String tranferSessionId;

	/* 消息标识 */
	private byte[] action;

	/* 消息体 */
	private byte[] message;

	public DataTransferReq() {

	}

	public byte[] getAction() {
		return action;
	}

	public void setAction(byte[] action) {
		this.action = action;
	}

	public byte[] getMessage() {
		return message;
	}

	public void setMessage(byte[] message) {
		this.message = message;
	}
	
	public String getTranferSessionId() {
		return tranferSessionId;
	}
	
	public void setTranferSessionId(String tranferSessionId) {
		this.tranferSessionId = tranferSessionId;
	}

}
