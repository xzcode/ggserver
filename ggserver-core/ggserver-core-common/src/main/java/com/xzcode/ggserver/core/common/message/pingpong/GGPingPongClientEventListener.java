package com.xzcode.ggserver.core.common.message.pingpong;

import java.nio.charset.Charset;

import com.xzcode.ggserver.core.common.channel.DefaultChannelAttributeKeys;
import com.xzcode.ggserver.core.common.config.GGConfig;
import com.xzcode.ggserver.core.common.event.EventListener;
import com.xzcode.ggserver.core.common.event.model.EventData;
import com.xzcode.ggserver.core.common.handler.serializer.ISerializer;
import com.xzcode.ggserver.core.common.message.MessageData;
import com.xzcode.ggserver.core.common.message.pingpong.model.GGPing;
import com.xzcode.ggserver.core.common.message.pingpong.model.GGPingPongInfo;
import com.xzcode.ggserver.core.common.message.response.support.IMakePackSupport;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;


public class GGPingPongClientEventListener implements EventListener<Void>, IMakePackSupport{
	
	protected GGConfig config;
	
	protected static final AttributeKey<GGPingPongInfo> PING_PONG_INFO_KEY = AttributeKey.valueOf(DefaultChannelAttributeKeys.PING_INFO);
	
	public GGPingPongClientEventListener(GGConfig config) {
		super();
		this.config = config;
	}

	@Override
	public void onEvent(EventData<Void> eventData) {
		Channel channel = eventData.getChannel();
		channel.writeAndFlush(makePack(new MessageData<>(eventData.getSession(), GGPing.ACTION_ID, null)));
	}

	@Override
	public Charset getCharset() {
		return config.getCharset();
	}

	@Override
	public ISerializer getSerializer() {
		return config.getSerializer();
	}


}
