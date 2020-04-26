package com.xzcode.ggserver.game.monitor.common.message.req;

import com.xzcode.ggserver.game.monitor.common.constant.GameMonitorConstant;

import xzcode.ggserver.core.common.message.model.IMessage;

/**
 * 房间详情更新请求
 *
 * @author zai
 * 2020-04-23 12:10:43
 */
public class RoomDetailUpdateReq implements IMessage{
	
	public static final String ACTION = GameMonitorConstant.ACTION_ID_PREFIX + "ROOM.DETAIL.UPDATE.REQ";
	
	@Override
	public String getActionId() {
		return ACTION;
	}

	public RoomDetailUpdateReq() {
		
	}
	
}
