package xzcode.ggserver.game.core.room.listener;

/**
 * 添加玩家监听器
 * 
 * @param <R>
 * @param <P>
 * @author zai
 * 2019-07-08 10:26:15
 */
@FunctionalInterface
public interface IRoomAddPlayerListener<P> {
	
	void onAdd(P player);
	
}
