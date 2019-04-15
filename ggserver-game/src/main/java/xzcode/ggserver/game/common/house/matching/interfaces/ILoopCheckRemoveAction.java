package xzcode.ggserver.game.common.house.matching.interfaces;

/**
 * 可移除房间检查接口
 * 
 * @param <R>
 * @param <H>
 * @author zai
 * 2019-04-15 14:26:12
 */
public interface ILoopCheckRemoveAction<R> {
	
	/**
	 * 执行检查
	 * 
	 * @param room
	 * @param house
	 * @author zai
	 * 2019-04-15 14:26:04
	 */
	boolean checkRemove(R room);

}
