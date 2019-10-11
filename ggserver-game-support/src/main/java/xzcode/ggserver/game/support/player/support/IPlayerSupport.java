package xzcode.ggserver.game.support.player.support;

import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.game.support.interfaces.IGGServerSupport;
import xzcode.ggserver.game.support.player.Player;

/**
 * 玩家支持接口
 * 
 * @author zzz
 * 2019-09-22 10:23:19
 */
public interface IPlayerSupport<P extends Player> extends IGGServerSupport{
	
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
		getGGServer().send(getSesson(), actionId, message);
	}
	
	/**
	 * 发送消息给玩家
	 * 
	 * @param actionId
	 * @author zzz
	 * 2019-09-22 10:29:42
	 */
	default void send(String actionId) {
		getGGServer().send(getSesson(), actionId);
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
