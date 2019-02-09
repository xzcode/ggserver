package xzcode.ggserver.core.message.send;

import io.netty.channel.Channel;

/**
 * 消息发送接口
 * 
 * 
 * @author zai
 * 2019-02-09 14:50:27
 */
public interface ISendMessage {
	
	void send(Object userId, String action, Object message);

	void send(Object userId, String action);

	void send(String action);

	void send(String action, Object message);

}
