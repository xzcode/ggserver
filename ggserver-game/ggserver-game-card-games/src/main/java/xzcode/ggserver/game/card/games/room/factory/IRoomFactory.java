package xzcode.ggserver.game.card.games.room.factory;

/**
 * 房间工厂
 * 
 * @param <R>
 * @author zai
 * 2019-12-25 19:57:56
 */
public interface IRoomFactory<R> {
	
	/**
	 * 创建房间
	 * 
	 * @return
	 * @author zai
	 * 2019-12-25 19:58:03
	 */
	R createRoom();
}
