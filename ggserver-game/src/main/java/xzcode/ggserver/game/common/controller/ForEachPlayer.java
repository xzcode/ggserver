package xzcode.ggserver.game.common.controller;

import xzcode.ggserver.game.common.player.Player;

/**
 * 遍历玩家接口
 * 
 * @param <P>
 * @author zai
 * 2019-02-11 10:50:38
 */
public interface ForEachPlayer<P extends Player> {

	/**
	 * 每次遍历执行的方法
	 * 
	 * @param player
	 * @author zai
	 * 2019-02-11 10:50:58
	 */
	void each(P player);
	
}