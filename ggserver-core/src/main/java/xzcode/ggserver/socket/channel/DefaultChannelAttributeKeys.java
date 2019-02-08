package xzcode.ggserver.socket.channel;

import io.netty.util.AttributeKey;
import xzcode.ggserver.socket.session.imp.SocketSession;

/**
 * 默认通道属性key常量
 * 
 * 
 * @author zai
 * 2019-02-08 18:23:16
 */
public interface DefaultChannelAttributeKeys {
	
	/**
	 * 会话key
	 */
	AttributeKey<SocketSession> SESSION = AttributeKey.valueOf("SESSION");
	
}
