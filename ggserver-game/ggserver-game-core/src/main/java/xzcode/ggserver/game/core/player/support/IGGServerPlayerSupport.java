package xzcode.ggserver.game.core.player.support;

import java.util.concurrent.TimeUnit;

import xzcode.ggserver.core.common.message.response.Response;
import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.game.core.interfaces.IGGServerSupport;
import xzcode.ggserver.game.core.player.Player;

/**
 * 玩家支持接口
 * 
 * @author zzz
 * 2019-09-22 10:23:19
 */
public interface IGGServerPlayerSupport<P extends Player> extends IGGServerSupport{
	
	/**
	 * 获取session
	 * @return
	 * 
	 * @author zai
	 * 2019-10-02 23:56:40
	 */
	GGSession getSesson();
	
	/**
	 * 发送消息给玩家
	 * 
	 * @param actionId
	 * @param message
	 * @author zzz
	 * 2019-09-22 10:29:42
	 */
	default void send(String actionId, Object message) {
		getGGServer().send(new Response(getSesson(), null, actionId, message), 0, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * 发送消息给玩家
	 * 
	 * @param actionId
	 * @author zzz
	 * 2019-09-22 10:29:42
	 */
	default void send(String actionId) {
		getGGServer().send(new Response(getSesson(), null, actionId, null), 0, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * 断开连接
	 * 
	 * @author zzz
	 * 2019-09-22 10:33:22
	 */
	default void disconnect() {
		getGGServer().disconnect(getSesson());
	}
	
	/**
	 * 断开连接
	 * 
	 * @author zzz
	 * 2019-09-22 10:33:22
	 */
	default void disconnect(long delayMs) {
		getGGServer().disconnect(getSesson(), delayMs);
	}
	
}
