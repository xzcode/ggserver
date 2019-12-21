package xzcode.ggserver.game.card.games.house.listener;

/**
 * 移除房间监听器
 * 
 * @param <R>
 * @param <H>
 * @author zai
 * 2019-04-15 11:36:39
 */
public interface IRemoveRoomListener<R, H> {
	
	/**
	 * 执行移除动作
	 * 
	 * @param room
	 * @param house
	 * @author zai
	 * 2019-04-15 11:36:56
	 */
	void onRemove(R room, H house);

}
