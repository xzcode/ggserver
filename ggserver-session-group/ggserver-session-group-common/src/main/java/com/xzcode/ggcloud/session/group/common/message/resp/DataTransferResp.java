package com.xzcode.ggcloud.session.group.common.message.resp;

import com.xzcode.ggcloud.session.group.common.constant.GGSesssionGroupConstant;
import com.xzcode.ggserver.core.common.message.model.IMessage;

/**
 * 数据传输推送
 *
 * @author zai
 * 2020-04-08 10:32:51
 */
public class DataTransferResp implements IMessage {

	public static final String ACTION_ID = GGSesssionGroupConstant.ACTION_ID_PREFIX + "DATA.TRANSFER.RESP";

	@Override
	public String getActionId() {
		return ACTION_ID;
	}

	
	// 传递的会话id
	private String tranferSessionId;

	/* 消息标识 */
	private byte[] action;

	/* 消息体 */
	private byte[] message;

	public DataTransferResp() {

	}

	public String getTranferSessionId() {
		return tranferSessionId;
	}

	public void setTranferSessionId(String tranferSessionId) {
		this.tranferSessionId = tranferSessionId;
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


}
