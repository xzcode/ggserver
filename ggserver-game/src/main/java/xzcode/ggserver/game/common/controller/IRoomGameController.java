package xzcode.ggserver.game.common.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import xzcode.ggserver.game.common.house.House;
import xzcode.ggserver.game.common.interfaces.condition.ICheckCondition;
import xzcode.ggserver.game.common.player.Player;
import xzcode.ggserver.game.common.room.Room;

/**
 * 房间游戏控制器接口
 * 
 * @param <R>
 * @param <P>
 * @author zai
 * 2019-02-16 17:57:18
 */
public interface IRoomGameController
<
P extends Player,
R extends Room< P, R, H>, 
H extends House<P, R, H>
> 
{

	/**
	 * 获取所有玩家
	 * 
	 * @return
	 * 
	 * @author zai 2019-02-10 14:18:49
	 */
	Map<Object, P> getPlayers(R room);
	
	/**
	 * 获取参与游戏的玩家
	 * 
	 * @param room
	 * @param condition
	 * @return
	 * @author zai
	 * 2019-06-04 13:52:25
	 */
	List<P> getInGamePlayers(R room, ICheckCondition<P> condition);

	/**
	 * 根据id获取玩家
	 * 
	 * @return
	 * 
	 * @author zai 2019-02-10 14:34:15
	 */
	P getPlayer(R room, Object playerId);
	
	/**
	 * 根据条件获取玩家
	 * 
	 * @param room
	 * @param condition
	 * @return
	 * @author zai
	 * 2019-02-16 17:48:42
	 */
	P getPlayer(R room, ICheckCondition<P> condition);
	
	/**
	 * 获取并筛选参与游戏的玩家
	 * 
	 * @param room
	 * @param condition
	 * @return
	 * @author zai
	 * 2019-07-11 09:56:53
	 */
	P getInGamePlayer(R room, ICheckCondition<P> condition);

	/**
	 * 遍历所有玩家
	 * 
	 * @param room
	 * @param eachPlayer
	 * 
	 * @author zai 2019-02-10 14:19:14
	 */
	void eachPlayer(R room, ForEachPlayer<P> eachPlayer);
	
	/**
	 * 遍历所有玩家并返回布尔值
	 * 
	 * @param room
	 * @param eachPlayer
	 * @return
	 * @author zai
	 * 2019-02-11 10:56:05
	 */
	boolean boolEachPlayer(R room, BoolForEachPlayer<P> eachPlayer);
	
	
	/**
	 * 遍历所有参与游戏的玩家并返回布尔值
	 * 
	 * @param room
	 * @param eachPlayer
	 * @return
	 * @author zai
	 * 2019-06-13 11:34:19
	 */
	boolean boolEachInGamePlayer(R room, BoolForEachPlayer<P> eachPlayer);

	/**
	 * 广播给所有玩家
	 * 
	 * @param room
	 * @param actionId
	 * @param message
	 * @author zai 2019-01-25 11:06:29
	 */
	void bcToAllPlayer(R room, String actionId, Object message);
	
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
	void bcToAllPlayer(R room, String actionId, Object message, ICheckCondition<P> condition);

	
	/**
	 * 随机获取玩家
	 * 
	 * @return
	 * @author zai
	 * 2019-02-16 17:59:11
	 */
	P getRandomPlayer(R room);

	/**
	 * 随机获取满足条件的玩家
	 * 
	 * @param condition
	 * @return
	 * @author zai
	 * 2019-02-16 18:03:09
	 */
	P getRandomPlayer(R room, ICheckCondition<P> condition);

	
	/**
	 * 获取下一个位置的玩家
	 * @param room
	 * @param curplayer
	 * @param condition
	 * @return
	 */
	P getNextPlayer(R room ,int seatNum ,ICheckCondition<P> condition );
	
	/**
	 * 获取下一个位置的玩家
	 * 
	 * @param room
	 * @param startSeatNum
	 * @return
	 * @author zzz
	 * 2019-09-12 15:58:40
	 */
	P getNextPlayer(R room, int startSeatNum);

	/**
	 * 根据条件获取玩家
	 * 
	 * @param room
	 * @param condition
	 * @return
	 * @author zai
	 * 2019-02-20 19:01:58
	 */
	List<P> getPlayers(R room, ICheckCondition<P> condition);

	/**
	 * 获取玩家列表
	 * 
	 * @param room
	 * @return
	 * @author zai
	 * 2019-02-20 20:36:06
	 */
	List<P> getPlayerList(R room);

	/**
	 * 获取一个座位号
	 * 
	 * @param room
	 * @return
	 * @author zai
	 * 2019-02-21 11:15:34
	 */
	int getSeatNum(R room);
	
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
	void iteratePlayer(R room, PlayerIteration<P> iteration);

	/**
	 * 获取指定条件的玩家数量
	 * 
	 * @param room
	 * @param condition
	 * @return
	 * @author zai
	 * 2019-02-28 15:21:05
	 */
	int countPlayers(R room, ICheckCondition<P> condition);
	
	
	/**
	 * 获取玩家数量
	 * 
	 * @param room
	 * @return
	 * @author zai
	 * 2019-02-28 15:22:16
	 */
	int countPlayers(R room);

	
	/**
	 * 获取玩家客户端座位号号
	 * 
	 * @param maxPlayer 最大玩家数
	 * @param selfSeatNum 当前玩家服务端座位号
	 * @param targetSeatNum 目标玩家服务端座位号
	 * @return
	 * @author zai
	 * 2019-03-12 18:58:35
	 */
	int getPlayerSeatType(int maxPlayer, int selfSeatNum, int targetSeatNum);
	/**
	 * 获取玩家客户端座位号 适合在自己发给自己 但是有转座位号的需求
	 * 
	 * @param maxPlayer
	 * @param selfSeatNum
	 * @return
	 * @author Wmc
	 * 2019-03-15 18:57:09
	 */
	int getPlayerSeatType(int selfSeatNum,int targetSeatNum);
	
	/**
	 * 获取玩家客户端座位号 适合在自己发给自己 但是有转座位号的需求
	 * 
	 * @param maxPlayer
	 * @param selfSeatNum
	 * @return
	 * @author zai
	 * 2019-03-19 11:13:21
	 */
	int getPlayerSelfSeatType(int maxPlayer, int selfSeatNum);
	
	/**
	 * 获取玩家自己的客户端座位号
	 * 
	 * @param selfSeatNum
	 * @return
	 * @author zai
	 * 2019-03-18 18:10:01
	 */
	int getPlayerSelfSeatType(int selfSeatNum);

	/**
	 * 获取自己的客户端座位号
	 * 
	 * @return
	 * @author zai
	 * 2019-03-22 15:53:32
	 */
	int getPlayerSelfSeatType();
	
	/**
	 * 获取玩家客户端座位号
	 * 
	 * @param self 
	 * @param target
	 * @return
	 * @author zai
	 * 2019-04-20 13:18:51
	 */
	int getPlayerSeatType(P self, P target);

	/**
	 * 根据客户端座位号获取玩家
	 * 
	 * @param room
	 * @param selfSeatNum
	 * @param targetSeatType
	 * @return
	 * @author zai
	 * 2019-03-26 20:07:28
	 */
	P getPlayerBySeatType(R room, int selfSeatNum, int targetSeatType);
	
	/**
	 * 根据客户端座位号获取玩家
	 * 
	 * @param room
	 * @param self
	 * @param targetSeatType
	 * @return
	 * @author zai
	 * 2019-04-20 13:23:15
	 */
	P getPlayerBySeatType(R room, P self, int targetSeatType);

	/**
	 * 获取已排序的参与游戏的玩家
	 * 
	 * @param room
	 * @return
	 * @author zai
	 * 2019-03-27 16:26:55
	 */
	List<P> getSortedInGamePlayerList(R room);
	
	/**
	 * 获取已排序的参与游戏的玩家
	 * 
	 * @param room
	 * @param comparator
	 * @return
	 * @author zai
	 * 2019-05-30 12:17:29
	 */
	List<P> getSortedInGamePlayerList(R room, Comparator<P> comparator);

	/**
	 * 遍历每个已准备玩家
	 * 
	 * @param room
	 * @param eachPlayer
	 * @author zai
	 * 2019-05-16 14:19:58
	 */
	void eachInGamePlayer(R room, ForEachPlayer<P> eachPlayer);
	
	/**
	 * 获取指定条件的参与游戏的玩家数量
	 * 
	 * @param room
	 * @param condition
	 * @return
	 * @author zai
	 * 2019-05-21 15:54:04
	 */
	int countInGamePlayers(R room, ICheckCondition<P> condition);
	
	/**
	 * 获取参与游戏的玩家list
	 * 
	 * @param room
	 * @return
	 * @author zai
	 * 2019-05-30 12:03:07
	 */
	List<P> getInGamePlayerList(R room);

	
	/**
	 * 广播给所有玩家
	 * 
	 * @param room
	 * @param actionId
	 * @author zai
	 * 2019-06-26 11:06:07
	 */
	void bcToAllPlayer(R room, String actionId);

	
	/**
	 * 双循环玩家遍历
	 * 
	 * @param room
	 * @param eachPlayer
	 * @author zzz
	 * 2019-09-10 12:16:44
	 */
	void doubleEachPlayer(R room, DoubleEachPlayer<P> eachPlayer);

	/**
	 * 双循环玩家遍历
	 * 
	 * @param room
	 * @param eachPlayer
	 * @author zzz
	 * 2019-09-10 12:28:39
	 */
	void doubleEachInGamePlayer(R room, DoubleEachPlayer<P> eachPlayer);

	
}
