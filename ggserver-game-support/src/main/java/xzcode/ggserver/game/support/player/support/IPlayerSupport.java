package xzcode.ggserver.game.support.player.support;

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
	 * 获取玩家id
	 * 
	 * @return
	 * @author zzz
	 * 2019-09-22 10:29:29
	 */
	Object getPlayerId();
	
	/**
	 * 发送消息给玩家
	 * 
	 * @param actionId
	 * @param message
	 * @author zzz
	 * 2019-09-22 10:29:42
	 */
	default void send(String actionId, Object message) {
		getGGServer().send(getPlayerId(), actionId, message);
	}
	
	/**
	 * 发送消息给玩家
	 * 
	 * @param actionId
	 * @author zzz
	 * 2019-09-22 10:29:42
	 */
	default void send(String actionId) {
		getGGServer().send(getPlayerId(), actionId);
	}
	
	/**
	 * 断开连接
	 * 
	 * @author zzz
	 * 2019-09-22 10:33:22
	 */
	default void disconnect() {
		getGGServer().disconnect(getPlayerId());
	}
	
	/**
	 * 断开连接
	 * 
	 * @author zzz
	 * 2019-09-22 10:33:22
	 */
	default void disconnect(long delayMs) {
		getGGServer().disconnect(getPlayerId(), delayMs);
	}
	
}
