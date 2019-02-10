package xzcode.ggserver.game.common.controller;

public interface IPlayerGameController<P> {
	
	/**
	 * 获取当前玩家
	 * @return
	 * 
	 * @author zai
	 * 2019-02-10 13:57:29
	 */
	P getCurrentPlayer();
	
	/**
	 * 获取当前玩家id
	 * 
	 * @return
	 * @author zai
	 * 2019-01-22 19:31:14
	 */
	Object getCurrentPlayerId();
	
	

}
