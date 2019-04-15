package xzcode.ggserver.game.common.house.matching.interfaces;

/**
 * 超时检查行为
 * 
 * @author zai
 * 2019-04-12 15:55:33
 */
public interface ILoopCheckTimeoutAction<R> {
	
	/**
	 * 超时检查
	 * 
	 * @return
	 * @author zai
	 * 2019-04-12 16:01:51
	 */
	boolean checkTimeout(R room);

}
