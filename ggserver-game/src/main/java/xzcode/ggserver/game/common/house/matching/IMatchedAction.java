package xzcode.ggserver.game.common.house.matching;

/**
 * 匹配后操作
 * 
 * @param <P> 玩家泛型
 * @author zai
 * 2019-01-24 17:48:05
 */
public interface IMatchedAction {
	
	
	void onMatch(MachingPlayerHouse<?, ?> house);

}
