package xzcode.ggserver.core.common.message.pingpong;

import java.nio.charset.Charset;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import xzcode.ggserver.core.common.channel.DefaultChannelAttributeKeys;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.handler.serializer.ISerializer;
import xzcode.ggserver.core.common.message.MessageData;
import xzcode.ggserver.core.common.message.pingpong.model.GGPing;
import xzcode.ggserver.core.common.message.pingpong.model.GGPingPongInfo;
import xzcode.ggserver.core.common.message.pingpong.model.GGPong;
import xzcode.ggserver.core.common.message.request.action.MessageDataHandler;
import xzcode.ggserver.core.common.message.response.support.IMakePackSupport;

/**
 * 内置ping处理器
 * 
 * @author zai
 * 2020-01-16 17:04:11
 */
public class GGPingRequestHandler implements MessageDataHandler<GGPing> , IMakePackSupport{
	
	protected GGConfig config;
	
	protected static final AttributeKey<GGPingPongInfo> PING_PONG_INFO_KEY = AttributeKey.valueOf(DefaultChannelAttributeKeys.PING_INFO);
	
	public GGPingRequestHandler(GGConfig config) {
		this.config = config;
	}

	@Override
	public void handle(MessageData<GGPing> request) {
		Channel channel = request.getChannel();
		channel.writeAndFlush(makePack(new MessageData<>(request.getSession(), GGPong.ACTION_ID, null)));
		
		GGPingPongInfo pingPongInfo = channel.attr(PING_PONG_INFO_KEY).get();
		if (pingPongInfo == null) {
			pingPongInfo = new GGPingPongInfo(config.getPingPongLostTimes(), config.getPingPongMaxLoseTimes());
			channel.attr(PING_PONG_INFO_KEY).set(pingPongInfo);
		}
		pingPongInfo.heartBeatLostTimesReset();
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
