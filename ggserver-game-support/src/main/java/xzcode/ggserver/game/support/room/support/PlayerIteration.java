package xzcode.ggserver.game.support.room.support;

import java.util.Iterator;
import java.util.Map.Entry;

import xzcode.ggserver.game.support.player.Player;

/**
 * 遍历玩家接口
 * 
 * @param <P>
 * @author zai
 * 2019-02-11 10:50:38
 */
public interface PlayerIteration<P extends Player> {

	/**
	 * 迭代
	 * @param entry
	 * @param it
	 * 
	 * @author zai
	 * 2019-02-23 16:01:43
	 */
	void it(P player, Entry<Object, P> entry, Iterator<Entry<Object, P>> it);
	
}
