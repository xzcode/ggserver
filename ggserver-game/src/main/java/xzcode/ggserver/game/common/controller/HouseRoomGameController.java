package xzcode.ggserver.game.common.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
		GGServerGameController 
		implements 
		IPlayerGameController<P>, 
		IRoomGameController<R, P>, 
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
	public P getNextPlayer(R room, int seatNum, ICheckCondition<P> condition) {

		List<P> pList = room.getOrderPlayerList();
		if (pList.size() == 0) {
			return null;
		}
		int maxPlayerOrder = room.getMaxPalyerNum();// 当前玩家数量
		List<P>aimPlayerList = new ArrayList<>();
		for (P ps : pList) {
			if (condition.check(ps) && ps.getSeatNum() <= maxPlayerOrder&&ps.getSeatNum()>0) {
				aimPlayerList.add(ps);
			}
		}
		if (aimPlayerList.size()==0) {
			return null;
		}
		aimPlayerList.sort((x,y)->
		{
			int r = -2;
			if (x.getSeatNum() > seatNum) {
				r = y.getSeatNum() < seatNum ? -1 : 0;
			}else {
				r = y.getSeatNum() < seatNum ? 0 : 1;
			}
			if (r==0) {
				r = x.getSeatNum()-y.getSeatNum();
			}
			return r;
		});
		return aimPlayerList.get(0);
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

}
