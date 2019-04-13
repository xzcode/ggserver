package xzcode.ggserver.game.common.house.matching;

import xzcode.ggserver.game.common.house.House;
import xzcode.ggserver.game.common.player.Player;
import xzcode.ggserver.game.common.room.MatchingRoom;

/**
 * 匹配后操作
 * 
 * @param <P> 玩家泛型
 * @author zai
 * 2019-01-24 17:48:05
 */
public interface ILoopMatchedAction
<
P extends Player<R, H>,
R extends MatchingRoom< P, R, H>, 
H extends House<P, R, H>
> {
	
	
	void onMatch(P player, R room, LoopMachingHouse<P, R, H> house);

}
