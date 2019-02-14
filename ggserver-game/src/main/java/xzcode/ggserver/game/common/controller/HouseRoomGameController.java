package xzcode.ggserver.game.common.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xzcode.ggserver.game.common.house.House;
import xzcode.ggserver.game.common.player.Player;
import xzcode.ggserver.game.common.room.Room;

/**
 * 基础游戏控制器
 * 
 * @author zai 2019-01-06 14:34:21
 */
public abstract class HouseRoomGameController<H extends House<R, P>, R extends Room<P>, P extends Player>
		extends GGServerGameController
		implements IPlayerGameController<P>, IRoomGameController<R,P>, IHouseGameController<H> {

	private static final Logger logger = LoggerFactory.getLogger(HouseRoomGameController.class);
	
	
	
	@Override
	public void eachPlayer(R room, ForEachPlayer<P> eachPlayer) {
		Map<Object, P> players = room.getPlayers();
		for (Object key : players.keySet()) {
			P p = players.get(key);
			eachPlayer.each(p);
		}
	}
	
	@Override
	public boolean boolEachPlayer(R room, BoolForEachPlayer<P> eachPlayer) {
		Map<Object, P> players = room.getPlayers();
		for (Object key : players.keySet()) {
			P p = players.get(key);
			if (!eachPlayer.each(p)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public Object getCurrentPlayerId() {
		return getGGServer().getSession().getRegisteredUserId();
	};
	
	@Override
	public void bcToAllPlayer(R room, String actionId, Object message) {
		eachPlayer(room, (player)->{
			getGGServer().send(player.getPlayerId(), actionId, message);				
		});
	}

	@Override
	public Map<Object, P> getPlayers(R room) {
		
		return room.getPlayers();
	}

	@Override
	public P getPlayer(R room, Object playerId) {
		return room.getPlayer(playerId);
	}
	
	
}
