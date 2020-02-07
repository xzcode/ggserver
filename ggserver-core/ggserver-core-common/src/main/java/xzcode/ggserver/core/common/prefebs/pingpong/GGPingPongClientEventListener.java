package xzcode.ggserver.core.common.prefebs.pingpong;

import java.nio.charset.Charset;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import xzcode.ggserver.core.common.channel.DefaultChannelAttributeKeys;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.event.IEventListener;
import xzcode.ggserver.core.common.event.model.EventData;
import xzcode.ggserver.core.common.handler.serializer.ISerializer;
import xzcode.ggserver.core.common.message.response.Response;
import xzcode.ggserver.core.common.message.response.support.IMakePackSupport;
import xzcode.ggserver.core.common.prefebs.pingpong.model.GGPing;
import xzcode.ggserver.core.common.prefebs.pingpong.model.GGPingPongInfo;


public class GGPingPongClientEventListener implements IEventListener<Void>, IMakePackSupport{
	
	protected GGConfig config;
	
	protected static final AttributeKey<GGPingPongInfo> PING_PONG_INFO_KEY = AttributeKey.valueOf(DefaultChannelAttributeKeys.PING_INFO);
	
	public GGPingPongClientEventListener(GGConfig config) {
		super();
		this.config = config;
	}

	@Override
	public void onEvent(EventData<Void> eventData) {
		Channel channel = eventData.getChannel();
		channel.writeAndFlush(makePack(new Response(eventData.getSession(), null, GGPing.ACTION_ID, null)));
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
