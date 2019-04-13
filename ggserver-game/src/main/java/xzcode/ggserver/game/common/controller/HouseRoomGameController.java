package xzcode.ggserver.game.common.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.game.common.house.House;
import xzcode.ggserver.game.common.interfaces.condition.ICheckCondition;
import xzcode.ggserver.game.common.player.RoomPlayer;
import xzcode.ggserver.game.common.room.Room;

/**
 * 大厅房间游戏游戏控制器
 * 
 * @author zai 2019-01-06 14:34:21
 */
public abstract class HouseRoomGameController
<
P extends RoomPlayer<R, H>,
R extends Room< P, R, H>, 
H extends House<P, R, H>
> 
extends
		GGServerGameController 
		implements 
		IPlayerGameController<P>, 
		IRoomGameController<P, R, H>, 
		IHouseGameController<H> {

	private static final Logger logger = LoggerFactory.getLogger(HouseRoomGameController.class);

	@Override
	public void eachPlayer(R room, ForEachPlayer<P> eachPlayer) {
		for (Entry<Object, P> e : room.getPlayers().entrySet()) {
			eachPlayer.each(e.getValue());
		}
	}
	
	@Override
	public void iteratePlayer(R room, PlayerIteration<P> iteration) {
		Iterator<Entry<Object, P>> it = room.getPlayers().entrySet().iterator();
		while (it.hasNext()) {
			Entry<Object, P> next = it.next();
			iteration.it(next.getValue(), next, it);			
		}
	}

	@Override
	public boolean boolEachPlayer(R room, BoolForEachPlayer<P> eachPlayer) {
		for (Entry<Object, P> entry : room.getPlayers().entrySet()) {
			if (!eachPlayer.each(entry.getValue())) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public int countPlayers(R room, ICheckCondition<P> condition) {
		int i = 0;
		for (Entry<Object, P> entry : room.getPlayers().entrySet()) {
			if (condition.check(entry.getValue())) {
				i++;
			}
		}
		return i;
	}
	
	@Override
	public int countPlayers(R room) {
		return room.getPlayers().size();
	}

	@Override
	public P getPlayer(R room, ICheckCondition<P> condition) {
		for (Entry<Object, P> entry : room.getPlayers().entrySet()) {
			if (condition.check(entry.getValue())) {
				return entry.getValue();
			}
		}
		return null;
	}
	
	@Override
	public List<P> getPlayerList(R room) {
		Map<Object, P> players = room.getPlayers();
		List<P> pList = new ArrayList<>(players.size());
		for (Entry<Object, P> entry : players.entrySet()) {
			pList.add(entry.getValue());
		}
		return pList;
	}
	
	@Override
	public List<P> getPlayers(R room, ICheckCondition<P> condition) {
		Map<Object, P> players = room.getPlayers();
		List<P> pList = new ArrayList<>(players.size());
		for (Entry<Object, P> entry : players.entrySet()) {
			if (condition.check(entry.getValue())) {
				pList.add(entry.getValue());
			}
		}
		return pList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public P getRandomPlayer(R room) {
		if (room.getPlayerNum() <= 0) {
			return null;
		}
		return ((Entry<Object, P>)room.getPlayers().entrySet().toArray()[ThreadLocalRandom.current().nextInt(room.getPlayerNum())]).getValue();
	}

	
	@Override
	public P getRandomPlayer(R room, ICheckCondition<P> condition) {
		Map<Object, P> players = room.getPlayers();
		List<P> plist = new ArrayList<>(players.size());
		for (Entry<Object, P> entry : players.entrySet()) {
			if (condition.check(entry.getValue())) {
				plist.add(entry.getValue());
			}
		}
		if (players.size() > 1) {
			return plist.get(ThreadLocalRandom.current().nextInt(plist.size()));
		}
		return plist.get(0);
		
	}
	
	@Override
	public List<P> getSortedInGamePlayerList(R room) {
		List<P> inGamePlayers = getPlayers(room, player -> {
			return player.isInGame();
		});
		Collections.sort(inGamePlayers, (x, y) -> x.getSeatNum() - y.getSeatNum());
		return inGamePlayers;
	}

	@Override
	public P getNextPlayer(R room, int startSeatNum, ICheckCondition<P> condition) {

		//获取排序后的玩家
		List<P> pList = getSortedInGamePlayerList(room);
		//排除起始座位号
		pList = pList.stream().filter(o -> o.getSeatNum() != startSeatNum && condition.check(o)).collect(Collectors.toList());
		
		if (pList.size() == 0) {
			return null;
		}
		
		for (P p : pList) {
			if (p.getSeatNum() > startSeatNum) {
				return p;
			}
		}
		
		for (P p : pList) {
			if (p.getSeatNum() < startSeatNum) {
				return p;
			}
		}
		

		return null;
		
		
	}
	
	@Override
	public int getSeatNum(R room) {
		int maxPlayerNum = getMaxPlayerNum();
		List<Integer> nums = new ArrayList<>(maxPlayerNum);
		for (int i = 1; i <= maxPlayerNum; i++) {
			nums.add(i);
		}
		synchronized (room) {
			Map<Object, P> players = getPlayers(room);
			for (Entry<Object, P> entry : players.entrySet()) {
				nums.remove(new Integer(entry.getValue().getSeatNum()));
			}
			return nums.get(0);
		}
	};
	
	

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
	public void bcToAllPlayer(R room, String actionId, Object message, ICheckCondition<P> condition) {
		eachPlayer(room, (player) -> {
			if (condition.check(player)) {
				getGGServer().send(player.getPlayerId(), actionId, message);				
			}
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
	
	@Override
	public int getPlayerSeatType(int maxPlayer, int selfSeatNum,int targetSeatNum) {
		return ((maxPlayer - selfSeatNum + targetSeatNum) % maxPlayer +1);
	}
	
	@Override
	public int getPlayerSeatType(int selfSeatNum,int targetSeatNum) {
		return getPlayerSeatType(getMaxPlayerNum(), selfSeatNum, targetSeatNum);
	}

	@Override
	public int getPlayerSelfSeatType(int maxPlayer, int selfSeatNum) {
		return getPlayerSeatType(maxPlayer,selfSeatNum,selfSeatNum);
	}
	
	@Override
	public int getPlayerSelfSeatType(int selfSeatNum) {
		return getPlayerSeatType(getMaxPlayerNum(), selfSeatNum,selfSeatNum);
	}
	@Override
	public int getPlayerSelfSeatType() {
		return 1;
	}
	
	@Override
	public P getPlayerBySeatType(R room, int selfSeatNum, int targetSeatType) {
		return getPlayer(room, player -> {
			return getPlayerSeatType(selfSeatNum, player.getSeatNum()) == targetSeatType;
		}); 
	};
	
}
