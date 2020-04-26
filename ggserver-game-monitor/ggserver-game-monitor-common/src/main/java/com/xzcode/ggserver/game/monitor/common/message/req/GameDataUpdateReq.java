package com.xzcode.ggserver.game.monitor.common.message.req;

import com.xzcode.ggserver.game.monitor.common.constant.GameMonitorConstant;
import com.xzcode.ggserver.game.monitor.common.data.ServiceInfo;

import xzcode.ggserver.core.common.message.model.IMessage;

/**
 * 游戏数据更新请求
 *
 * @author zai 2020-04-23 12:11:20
 */
public class GameDataUpdateReq implements IMessage {

	public static final String ACTION = GameMonitorConstant.ACTION_ID_PREFIX + "GAME.DATA.UPDATE.REQ";

	@Override
	public String getActionId() {
		return ACTION;
	}

	// 服务信息
	private ServiceInfo serviceInfo;

	public GameDataUpdateReq() {

	}

	public GameDataUpdateReq(ServiceInfo serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

	public ServiceInfo getServiceInfo() {
		return serviceInfo;
	}

	public void setServiceInfo(ServiceInfo serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

}
