package xzcode.ggserver.game.common.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.game.common.house.House;
import xzcode.ggserver.game.common.interfaces.condition.ICheckCondition;
import xzcode.ggserver.game.common.player.Player;
import xzcode.ggserver.game.common.room.Room;

/**
 * 大厅房间游戏游戏控制器
 * 
 * @author zai 2019-01-06 14:34:21
 */
public abstract class HouseRoomGameController<H extends House<R, P>, R extends Room<P>, P extends Player> extends
		GGServerGameController implements IPlayerGameController<P>, IRoomGameController<R, P>, IHouseGameController<H> {

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
	public P getPlayer(R room, ICheckCondition<P> condition) {
		Map<Object, P> players = room.getPlayers();
		P p = null;
		for (Object key : players.keySet()) {
			p = players.get(key);
			if (condition.check(p)) {
				return p;
			}
		}
		return p;
	}
	
	@Override
	public List<P> getPlayerList(R room) {
		Map<Object, P> players = room.getPlayers();
		List<P> pList = new ArrayList<>(players.size());
		for (Object key : players.keySet()) {
			pList.add(players.get(key));
		}
		return pList;
	}
	
	@Override
	public List<P> getPlayers(R room, ICheckCondition<P> condition) {
		Map<Object, P> players = room.getPlayers();
		List<P> pList = new ArrayList<>(players.size());
		P p = null;
		for (Object key : players.keySet()) {
			p = players.get(key);
			if (condition.check(p)) {
				pList.add(p);
			}
		}
		return pList;
	}

	@Override
	public P getRandomPlayer(R room) {
		Map<Object, P> players = room.getPlayers();
		Object[] keys = players.keySet().toArray();
		ThreadLocalRandom random = ThreadLocalRandom.current();
		P p = players.get(random.nextInt(keys.length));
		return p;
	}

	@Override
	public P getRandomPlayer(R room, ICheckCondition<P> condition) {
		Map<Object, P> players = room.getPlayers();
		List<P> plist = new ArrayList<>(players.size());
		for (Object key : players.keySet()) {
			P p = players.get(key);
			if (condition.check(players.get(p))) {
				plist.add(p);
			}
		}
		ThreadLocalRandom random = ThreadLocalRandom.current();
		P p = plist.get(random.nextInt(plist.size()));
		return p;
	}

	/**
	 * 获取指定条件下，下一个玩家 wmc
	 * 2019-02-20 15:30:50
	 */
	@Override
	public P getNextPlayer(R room, P curplayer, ICheckCondition<P> condition) {

		List<P> pList = room.getOrderPlayerList();
		if (pList.size() == 0) {
			return null;
		}
		int maxPlayerOrder = pList.size();// 当前玩家数量
		P p = null;
		boolean isMaxOrderPlayers = curplayer.getSeatNum() == maxPlayerOrder;// 用来作为基准查找下一个玩的本身是否是最后一个玩家
		for (P ps : pList) {
			if (condition.check(ps)) {

				if (isMaxOrderPlayers) {
					p = pList.get(0);
					if (p == null) {
						return null;
					} else {
						return p;
					}
				} else {

					if (ps.getSeatNum() > curplayer.getSeatNum()) {
						return ps;
					}
				}
			}
		}
		return null;
	}

	@Override
	public Object getCurrentPlayerId() {
		return getGGServer().getSession().getRegisteredUserId();
	};

	@Override
	public void bcToAllPlayer(R room, String actionId, Object message) {
		eachPlayer(room, (player) -> {
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
