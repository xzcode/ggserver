package xzcode.ggserver.game.support.room.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import xzcode.ggserver.game.support.cardgames.player.RoomPlayer;
import xzcode.ggserver.game.support.interfaces.IGGServerSupport;
import xzcode.ggserver.game.support.interfaces.condition.ICheckCondition;

/**
 * 房间游戏控制器接口
 * 
 * @param <R>
 * @param <P>
 * @author zai
 * 2019-02-16 17:57:18
 * @param <R>
 */
public interface IRoomSupport<P extends RoomPlayer<R>, R>  extends IGGServerSupport {
	
	
	
	/**
	 * 获取所有玩家
	 * 
	 * @return
	 * 
	 * @author zai 2019-02-10 14:18:49
	 */
	Map<Object, P> getPlayers();
	
	/**
	 * 获取参与游戏的玩家
	 * 
	 * @param room
	 * @param condition
	 * @return
	 * @author zai
	 * 2019-06-04 13:52:25
	 */
	default List<P> getInGamePlayers(ICheckCondition<P> condition) {
			Map<Object, P> players = getPlayers();
			List<P> pList = new ArrayList<>(players.size());
			for (Entry<Object, P> entry : players.entrySet()) {
				P player = entry.getValue();
				if (player.isInGame()) {
					if (condition == null) {
						pList.add(entry.getValue());
					}else {
						if (condition.check(entry.getValue())) {
							pList.add(entry.getValue());
						}			
					}
				}
			}
			return pList;
	}

	/**
	 * 根据id获取玩家
	 * 
	 * @return
	 * 
	 * @author zai 2019-02-10 14:34:15
	 */
	default P getPlayer(Object playerId) {
		return getPlayer(playerId);
	}
	
	/**
	 * 根据条件获取玩家
	 * 
	 * @param room
	 * @param condition
	 * @return
	 * @author zai
	 * 2019-02-16 17:48:42
	 */
	default P getPlayer(ICheckCondition<P> condition) {
		for (Entry<Object, P> entry : getPlayers().entrySet()) {
			if (condition.check(entry.getValue())) {
				return entry.getValue();
			}
		}
		return null;
	}
	
	/**
	 * 获取并筛选参与游戏的玩家
	 * 
	 * @param room
	 * @param condition
	 * @return
	 * @author zai
	 * 2019-07-11 09:56:53
	 */
	default P getInGamePlayer(ICheckCondition<P> condition) {
		for (Entry<Object, P> entry : getPlayers().entrySet()) {
			P player = entry.getValue();
			if (player.isInGame() && condition.check(player)) {
				return entry.getValue();
			}
		}
		return null;
	}

	/**
	 * 遍历所有玩家
	 * 
	 * @param room
	 * @param eachPlayer
	 * 
	 * @author zai 2019-02-10 14:19:14
	 */
	default void eachPlayer(IForEachPlayer<P> eachPlayer) {
		for (Entry<Object, P> e : getPlayers().entrySet()) {
			eachPlayer.each(e.getValue());
		}
	}
	
	/**
	 * 遍历所有玩家并返回布尔值
	 * 
	 * @param room
	 * @param eachPlayer
	 * @return
	 * @author zai
	 * 2019-02-11 10:56:05
	 */
	default boolean boolEachPlayer(IBoolForEachPlayer<P> eachPlayer) {
		for (Entry<Object, P> entry : getPlayers().entrySet()) {
			if (!eachPlayer.each(entry.getValue())) {
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * 遍历所有参与游戏的玩家并返回布尔值
	 * 
	 * @param room
	 * @param eachPlayer
	 * @return
	 * @author zai
	 * 2019-06-13 11:34:19
	 */
	default boolean boolEachInGamePlayer(IBoolForEachPlayer<P> eachPlayer) {
			for (Entry<Object, P> entry : getPlayers().entrySet()) {
				if (!entry.getValue().isInGame()) {
					continue;
				}
				if (!eachPlayer.each(entry.getValue())) {
					return false;
				}
			}
			return true;
	}

	/**
	 * 广播给所有玩家
	 * 
	 * @param room
	 * @param actionId
	 * @param message
	 * @author zai 2019-01-25 11:06:29
	 */
	default void bcToAllPlayer(String actionId, Object message) {
		eachPlayer((player) -> {
			getGGServer().send(player.getPlayerId(), actionId, message);
		});
	}
	
	

	/**
	 * 广播给所有满足条件的玩家
	 * @param room
	 * @param actionId
	 * @param message
	 * @param condition
	 * 
	 * @author zai
	 * 2019-02-23 16:49:17
	 */
	default void bcToAllPlayer(String actionId, Object message, ICheckCondition<P> condition) {
		eachPlayer((player) -> {
			if (condition.check(player)) {
				getGGServer().send(player.getPlayerId(), actionId, message);				
			}
		});
	}

	
	/**
	 * 随机获取玩家
	 * 
	 * @return
	 * @author zai
	 * 2019-02-16 17:59:11
	 */
	@SuppressWarnings("unchecked")
	default P getRandomPlayer() {
		Map<Object, P> players = getPlayers();
		if (players.size() <= 0) {
			return null;
		}
		return ((Entry<Object, P>)players.entrySet().toArray()[ThreadLocalRandom.current().nextInt(players.size())]).getValue();
	}

	/**
	 * 随机获取满足条件的玩家
	 * 
	 * @param condition
	 * @return
	 * @author zai
	 * 2019-02-16 18:03:09
	 */
	default P getRandomPlayer(ICheckCondition<P> condition) {
			Map<Object, P> players = getPlayers();
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

	
	/**
	 * 获取下一个位置的玩家
	 * @param room
	 * @param curplayer
	 * @param condition
	 * @return
	 */
	default P getNextPlayer(int startSeatNo, ICheckCondition<P> condition) {
			//获取排序后的玩家
			List<P> pList = getSortedInGamePlayerList();
			//排除起始座位号
			if (condition != null) {
				pList = pList.stream().filter(o -> o.getSeatNo() != startSeatNo && condition.check(o)).collect(Collectors.toList());
			}else {
				pList = pList.stream().filter(o -> o.getSeatNo() != startSeatNo).collect(Collectors.toList());
			}
			
			if (pList.size() == 0) {
				return null;
			}
			
			for (P p : pList) {
				if (p.getSeatNo() > startSeatNo) {
					return p;
				}
			}
			
			for (P p : pList) {
				if (p.getSeatNo() < startSeatNo) {
					return p;
				}
			}
			
	
			return null;
		
	}
	
	/**
	 * 获取下一个位置的玩家
	 * 
	 * @param room
	 * @param startSeatNo
	 * @return
	 * @author zzz
	 * 2019-09-12 15:58:40
	 */
	default P getNextPlayer(int startSeatNo) {
		return getNextPlayer(startSeatNo, null);
		
	}

	/**
	 * 根据条件获取玩家
	 * 
	 * @param room
	 * @param condition
	 * @return
	 * @author zai
	 * 2019-02-20 19:01:58
	 */
	default List<P> getPlayers(ICheckCondition<P> condition) {
			Map<Object, P> players = getPlayers();
			List<P> pList = new ArrayList<>(players.size());
			for (Entry<Object, P> entry : players.entrySet()) {
				if (condition == null) {
					pList.add(entry.getValue());
				}else {
					if (condition.check(entry.getValue())) {
						pList.add(entry.getValue());
					}			
				}
			}
			return pList;
	}

	/**
	 * 获取玩家列表
	 * 
	 * @param room
	 * @return
	 * @author zai
	 * 2019-02-20 20:36:06
	 */
	default List<P> getPlayerList() {
			Map<Object, P> players = getPlayers();
			List<P> pList = new ArrayList<>(players.size());
			for (Entry<Object, P> entry : players.entrySet()) {
				pList.add(entry.getValue());
			}
			return pList;
	}

	/**
	 * 获取一个座位号
	 * 
	 * @param room
	 * @return
	 * @author zai
	 * 2019-02-21 11:15:34
	 */
	default int getSeatNo() {
		
		int maxPlayerNum = getMaxPlayerNum();
		List<Integer> nums = new ArrayList<>(maxPlayerNum);
		for (int i = 1; i <= maxPlayerNum; i++) {
			nums.add(i);
		}
			Map<Object, P> players = getPlayers();
			for (Entry<Object, P> entry : players.entrySet()) {
				nums.remove(new Integer(entry.getValue().getSeatNo()));
			}
			return nums.get(0);
	};
	
	/**
	 * 获取最大玩家数
	 * 
	 * @return
	 * @author zai
	 * 2019-02-21 11:07:05
	 */
	int getMaxPlayerNum();

	/**
	 * 玩家迭代
	 * @param room
	 * @param iteration
	 * 
	 * @author zai
	 * 2019-02-23 16:03:00
	 */
	default void iteratePlayer(IPlayerIteration<P> iteration) {
		Iterator<Entry<Object, P>> it = getPlayers().entrySet().iterator();
		while (it.hasNext()) {
			Entry<Object, P> next = it.next();
			iteration.it(next.getValue(), next, it);			
		}
	}

	/**
	 * 获取指定条件的玩家数量
	 * 
	 * @param room
	 * @param condition
	 * @return
	 * @author zai
	 * 2019-02-28 15:21:05
	 */
	default int countPlayers(ICheckCondition<P> condition) {
			int i = 0;
			for (Entry<Object, P> entry : getPlayers().entrySet()) {
				if (condition.check(entry.getValue())) {
					i++;
				}
			}
			return i;
	}
	
	
	/**
	 * 获取玩家数量
	 * 
	 * @param room
	 * @return
	 * @author zai
	 * 2019-02-28 15:22:16
	 */
	default int countPlayers() {
		return getPlayers().size();
	}

	
	/**
	 * 获取玩家客户端座位号号
	 * 
	 * @param maxPlayer 最大玩家数
	 * @param selfSeatNo 当前玩家服务端座位号
	 * @param targetSeatNo 目标玩家服务端座位号
	 * @return
	 * @author zai
	 * 2019-03-12 18:58:35
	 */
	default int getPlayerClientSeatNo(int maxPlayer, int selfSeatNo,int targetSeatNo) {
		return ((maxPlayer - selfSeatNo + targetSeatNo) % maxPlayer +1);
	}
	/**
	 * 获取玩家客户端座位号 适合在自己发给自己 但是有转座位号的需求
	 * 
	 * @param maxPlayer
	 * @param selfSeatNo
	 * @return
	 * @author Wmc
	 * 2019-03-15 18:57:09
	 */
	default int getPlayerClientSeatNo(int selfSeatNo,int targetSeatNo) {
		return getPlayerClientSeatNo(getMaxPlayerNum(), selfSeatNo, targetSeatNo);
	}
	
	/**
	 * 获取玩家客户端座位号 适合在自己发给自己 但是有转座位号的需求
	 * 
	 * @param maxPlayer
	 * @param selfSeatNo
	 * @return
	 * @author zai
	 * 2019-03-19 11:13:21
	 */
	default int getSelfClientSeatNo(int maxPlayer, int selfSeatNo) {
		return getPlayerClientSeatNo(maxPlayer,selfSeatNo,selfSeatNo);
	}
	
	/**
	 * 获取玩家自己的客户端座位号
	 * 
	 * @param selfSeatNo
	 * @return
	 * @author zai
	 * 2019-03-18 18:10:01
	 */
	default int getSelfClientSeatNo(int selfSeatNo) {
		return getPlayerClientSeatNo(getMaxPlayerNum(), selfSeatNo,selfSeatNo);
	}

	/**
	 * 获取自己的客户端座位号
	 * 
	 * @return
	 * @author zai
	 * 2019-03-22 15:53:32
	 */
	default int getSelfClientSeatNo() {
		return 1;
	}
	
	/**
	 * 获取玩家客户端座位号
	 * 
	 * @param self 
	 * @param target
	 * @return
	 * @author zai
	 * 2019-04-20 13:18:51
	 */
	default int getClientSeatNo(P self, P target) {
		return getPlayerClientSeatNo(getMaxPlayerNum(), self.getSeatNo(), target.getSeatNo());
	}

	/**
	 * 根据客户端座位号获取玩家
	 * 
	 * @param room
	 * @param selfSeatNo
	 * @param targetClientSeatNo
	 * @return
	 * @author zai
	 * 2019-03-26 20:07:28
	 */
	default P getPlayerByClientSeatNo(int selfSeatNo, int targetClientSeatNo) {
		return getPlayer(player -> {
			return getPlayerClientSeatNo(selfSeatNo, player.getSeatNo()) == targetClientSeatNo;
		}); 
	};
	
	/**
	 * 根据客户端座位号获取玩家
	 * 
	 * @param room
	 * @param self
	 * @param targetClientSeatNo
	 * @return
	 * @author zai
	 * 2019-04-20 13:23:15
	 */
	default P getPlayerByClientSeatNo(P self, int targetClientSeatNo) {
		return getPlayerByClientSeatNo(self.getSeatNo(), targetClientSeatNo);
	};

	/**
	 * 获取已排序的参与游戏的玩家
	 * 
	 * @param room
	 * @return
	 * @author zai
	 * 2019-03-27 16:26:55
	 */
	default List<P> getSortedInGamePlayerList() {
		List<P> inGamePlayers = getPlayers(player -> {
			return player.isInGame();
		});
		Collections.sort(inGamePlayers, (x, y) -> x.getSeatNo() - y.getSeatNo());
		return inGamePlayers;
	}
	
	/**
	 * 获取已排序的参与游戏的玩家
	 * 
	 * @param room
	 * @param comparator
	 * @return
	 * @author zai
	 * 2019-05-30 12:17:29
	 */
	default List<P> getSortedInGamePlayerList(Comparator<P> comparator) {
			List<P> inGamePlayers = getPlayers(player -> {
				return player.isInGame();
			});
			Collections.sort(inGamePlayers, comparator);
			return inGamePlayers;
	}

	/**
	 * 遍历每个已准备玩家
	 * 
	 * @param room
	 * @param eachPlayer
	 * @author zai
	 * 2019-05-16 14:19:58
	 */
	default void eachInGamePlayer(IForEachPlayer<P> eachPlayer) {
		P player = null;
		for (Entry<Object, P> e : getPlayers().entrySet()) {
			player = e.getValue();
			if (player.isInGame()) {
				eachPlayer.each(e.getValue());				
			}
		}
	}
	
	/**
	 * 获取指定条件的参与游戏的玩家数量
	 * 
	 * @param room
	 * @param condition
	 * @return
	 * @author zai
	 * 2019-05-21 15:54:04
	 */
	default int countInGamePlayers(ICheckCondition<P> condition) {
		int i = 0;
		P player = null;
		for (Entry<Object, P> entry : getPlayers().entrySet()) {
			player = entry.getValue();
			if (player.isInGame() && condition.check(player)) {
				i++;
			}
		}
		return i;
	}
	
	/**
	 * 获取参与游戏的玩家list
	 * 
	 * @param room
	 * @return
	 * @author zai
	 * 2019-05-30 12:03:07
	 */
	default List<P> getInGamePlayerList() {
		Map<Object, P> players = getPlayers();
		List<P> pList = new ArrayList<>(players.size());
		for (Entry<Object, P> entry : players.entrySet()) {
			P player = entry.getValue();
			if (player.isInGame()) {
				pList.add(player);					
			}
		}
		return pList;
	}

	
	/**
	 * 广播给所有玩家
	 * 
	 * @param room
	 * @param actionId
	 * @author zai
	 * 2019-06-26 11:06:07
	 */
	default void bcToAllPlayer(String actionId) {
		eachPlayer(player -> {
			getGGServer().send(player.getPlayerId(), actionId, null);
		});
	}

	
	/**
	 * 双循环玩家遍历
	 * 
	 * @param room
	 * @param eachPlayer
	 * @author zzz
	 * 2019-09-10 12:16:44
	 */
	default void doubleEachPlayer(IDoubleEachPlayer<P> eachPlayer) {
		Set<Entry<Object, P>> entrySet = getPlayers().entrySet();
		for (Entry<Object, P> e : entrySet) {
			for (Entry<Object, P> e2 : entrySet) {
				eachPlayer.each(e.getValue(), e2.getValue());
			}
		}
	}

	/**
	 * 双循环玩家遍历
	 * 
	 * @param room
	 * @param eachPlayer
	 * @author zzz
	 * 2019-09-10 12:28:39
	 */
	default void doubleEachInGamePlayer(IDoubleEachPlayer<P> eachPlayer) {
		P player = null;
		P player2 = null;
		Set<Entry<Object, P>> entrySet = getPlayers().entrySet();
		for (Entry<Object, P> e : entrySet) {
			player = e.getValue();
			if (player.isInGame()) {
				for (Entry<Object, P> e2 : entrySet) {
					player2 = e2.getValue();
					if (player2.isInGame()) {
						eachPlayer.each(player, player2);							
					}
				}
			}
		}
	}

	
}
