package xzcode.ggserver.game.common.house.matching;

/**
 * 超时行为
 * 
 * @author zai
 * 2019-04-12 15:55:33
 */
public interface ILoopMatchingTimeoutAction<R> {
	
	/**
	 * 执行超时行为
	 * 
	 * @param room
	 * @author zai
	 * 2019-04-12 17:04:52
	 */
	void runAction(R room);

}
