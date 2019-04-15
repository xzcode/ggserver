package xzcode.ggserver.game.common.house.matching.interfaces;

/**
 * 匹配超时监听接口
 * 
 * @author zai
 * 2019-04-12 15:55:33
 */
public interface ILoopMatchingTimeoutListener<R, H> {
	
	/**
	 * 执行超时动作
	 * 
	 * @param room
	 * @param house
	 * @author zai
	 * 2019-04-15 11:30:43
	 */
	void onTimeout(R room, H house);

}
