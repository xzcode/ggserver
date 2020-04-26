package com.xzcode.ggserver.game.monitor.common.message.resp;

import com.xzcode.ggserver.game.monitor.common.constant.GameMonitorConstant;

import xzcode.ggserver.core.common.message.model.IMessage;

/**
 * 游戏服注册请求
 *
 * @author zai
 * 2020-04-23 12:11:09
 */
public class GameServerRegisterResp  implements IMessage{
	
	public static final String ACTION = GameMonitorConstant.ACTION_ID_PREFIX + "GAME.SERVER.REGISTER.RESP";

	@Override
	public String getActionId() {
		return ACTION;
	}
	
	public GameServerRegisterResp(boolean success) {
		this.success = success;
	}


	/**
	 * 是否注册成功
	 */
	private boolean success;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	
}
