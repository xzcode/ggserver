package xzcode.ggserver.game.card.games.room.support;

/**
 * 双循环遍历玩家接口
 * 
 * @param <P>
 * @author zai
 * 2019-02-11 10:50:38
 */
public interface IDoubleEachPlayer<P> {

	/**
	 * 每次遍历执行的方法
	 * 
	 * @param player
	 * @author zai
	 * 2019-02-11 10:50:58
	 */
	void each(P playerOuter, P playerInner);
	
}
