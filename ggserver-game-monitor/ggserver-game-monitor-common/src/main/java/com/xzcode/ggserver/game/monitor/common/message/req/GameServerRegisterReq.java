package com.xzcode.ggserver.game.monitor.common.message.req;

import com.xzcode.ggserver.game.monitor.common.constant.GameMonitorConstant;

import xzcode.ggserver.core.common.message.model.IMessage;

/**
 * 游戏服注册请求
 *
 * @author zai
 * 2020-04-23 12:11:09
 */
public class GameServerRegisterReq  implements IMessage{
	
	public static final String ACTION = GameMonitorConstant.ACTION_ID_PREFIX + "GAME.SERVER.REGISTER.REQ";
	public static final GameServerRegisterReq DEFAULT_INSTANT = new GameServerRegisterReq();

	@Override
	public String getActionId() {
		return ACTION;
	}
	
}
