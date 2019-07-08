package xzcode.ggserver.game.common.house.listener;

/**
 * 房间超时监听器
 * 
 * @param <R>
 * @param <P>
 * @author zai
 * 2019-07-08 10:22:40
 */
@FunctionalInterface
public interface IRoomTimeoutListener<R> {
	
	void onTimeout(R room);
	
}
