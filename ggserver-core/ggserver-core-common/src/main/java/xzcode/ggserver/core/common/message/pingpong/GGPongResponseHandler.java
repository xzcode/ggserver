package xzcode.ggserver.core.common.message.pingpong;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import xzcode.ggserver.core.common.channel.DefaultChannelAttributeKeys;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.message.MessageData;
import xzcode.ggserver.core.common.message.pingpong.model.GGPingPongInfo;
import xzcode.ggserver.core.common.message.pingpong.model.GGPong;
import xzcode.ggserver.core.common.message.request.action.MessageDataHandler;

/**
 * 内置pingpong处理器
 * 
 * @author zai
 * 2020-01-16 17:04:11
 */
public class GGPongResponseHandler implements MessageDataHandler<GGPong>{
	
	protected GGConfig config;
	
	protected static final AttributeKey<GGPingPongInfo> PING_PONG_INFO_KEY = AttributeKey.valueOf(DefaultChannelAttributeKeys.PING_INFO);
	
	
	public GGPongResponseHandler(GGConfig config) {
		this.config = config;
	}



	@Override
	public void handle(MessageData<GGPong> request) {
		Channel channel = request.getChannel();
		GGPingPongInfo gGPingPongInfo = channel.attr(PING_PONG_INFO_KEY).get();
		if (gGPingPongInfo == null) {
			gGPingPongInfo = new GGPingPongInfo(config.getPingPongLostTimes(), config.getPingPongMaxLoseTimes());
			channel.attr(PING_PONG_INFO_KEY).set(gGPingPongInfo);
		}
		gGPingPongInfo.heartBeatLostTimesReset();
	}
	


}
