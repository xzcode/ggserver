package xzcode.ggserver.socket.sender;

import io.netty.channel.Channel;
import xzcode.ggserver.socket.utils.json.GGSocketJsonUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息发送器
 * 
 * 
 * @author zai 2017-07-30 23:14:52
 */
public class SocketMessageSender {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(SocketMessageSender.class);
	
	public static void send(Channel channel, SendModel sendModel) {
		if (channel != null && channel.isActive()) {
			channel.writeAndFlush(sendModel);			
		}else {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Channel is inactived! Message will not be send, SendModel:{}", GGSocketJsonUtil.toJson(sendModel));
			}
		}
	}
}
