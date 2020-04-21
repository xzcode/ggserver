package com.xzcode.ggserver.game.monitor.common.message.req;

import xzcode.ggserver.core.common.message.model.IMessage;

public class DiscoveryServiceListReq  implements IMessage{
	
	public static final String ACTION = "GG.MONITOR.LIST.REQ";
	public static final DiscoveryServiceListReq DEFAULT_INSTANT = new DiscoveryServiceListReq();

	@Override
	public String getActionId() {
		return ACTION;
	}
	
}
