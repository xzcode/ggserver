package xzcode.ggserver.game.support.house.listener;

/**
 * 添加房间监听器
 * 
 * @param <R>
 * @param <P>
 * @author zai
 * 2019-07-08 10:26:15
 */
@FunctionalInterface
public interface IHouseAddRoomListener<R> {
	
	void onAdd(R room);
	
}
