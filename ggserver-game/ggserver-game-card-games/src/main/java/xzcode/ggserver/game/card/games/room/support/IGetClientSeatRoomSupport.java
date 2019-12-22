package xzcode.ggserver.game.card.games.room.support;

import xzcode.ggserver.game.card.games.house.House;
import xzcode.ggserver.game.card.games.player.RoomPlayer;
import xzcode.ggserver.game.card.games.room.Room;

/**
 * 房间游戏控制器接口
 * 
 * @param <R>
 * @param <P>
 * @author zai
 * 2019-02-16 17:57:18
 * @param <R>
 */
public interface IGetClientSeatRoomSupport
<
P extends RoomPlayer<P, R, H>,
R extends Room<P, R, H>, 
H extends House<P, R, H>
>  extends IRoomSupport<P, R, H> {
	
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
		return getPlayerClientSeatNo(getMaxPlayerNum(), self.getSeat(), target.getSeat());
	}

	

	
}
