package com.xzcode.ggserver.core.common.message.pingpong;

import com.xzcode.ggserver.core.common.channel.DefaultChannelAttributeKeys;
import com.xzcode.ggserver.core.common.config.GGConfig;
import com.xzcode.ggserver.core.common.event.EventListener;
import com.xzcode.ggserver.core.common.event.model.EventData;
import com.xzcode.ggserver.core.common.message.pingpong.model.GGPingPongInfo;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;


public class GGPingPongServerEventListener implements EventListener<Void>{
	
	protected GGConfig config;
	
	protected static final AttributeKey<GGPingPongInfo> PING_PONG_INFO_KEY = AttributeKey.valueOf(DefaultChannelAttributeKeys.PING_INFO);
	
	public GGPingPongServerEventListener(GGConfig config) {
		super();
		this.config = config;
	}

	@Override
	public void onEvent(EventData<Void> eventData) {
		Channel channel = eventData.getChannel();
		GGPingPongInfo pingPongInfo = channel.attr(PING_PONG_INFO_KEY).get();
		if (pingPongInfo == null) {
			pingPongInfo = new GGPingPongInfo(config.getPingPongLostTimes(), config.getPingPongMaxLoseTimes());
			channel.attr(PING_PONG_INFO_KEY).set(pingPongInfo);
		}
		pingPongInfo.heartBeatLostTimesIncrease();
		
		//超过心跳丢失次数，断开连接
		if (pingPongInfo.isHeartBeatLost()) {
			channel.disconnect();
			return;
		}
	}


}
