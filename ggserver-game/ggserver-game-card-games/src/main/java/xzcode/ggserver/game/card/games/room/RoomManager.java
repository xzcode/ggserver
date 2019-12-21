package xzcode.ggserver.game.card.games.room;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import xzcode.ggserver.core.server.IGGServer;
import xzcode.ggserver.game.card.games.player.RoomPlayer;

/**
 * 房间管理器
 * 
 * @param <R>
 * @param <P>
 * @author zai
 * 2019-12-21 11:50:27
 */
public class RoomManager<R extends Room<P, R>, P extends RoomPlayer<R>> {
	
	/**
	 * ggserver对象
	 */
	protected IGGServer server;
	
	/**
	 * 进入房间队列
	 */
	protected Queue<Runnable> enterRoomQueue = new ConcurrentLinkedQueue<>();
	
	/**
	 * 房间集合
	 */
	protected Map<String, Map<String, R>> rooms = new ConcurrentHashMap<>(1000);
	
	/**
	 * 玩家集合
	 */
	protected Map<String, P> players = new ConcurrentHashMap<>(rooms.size() * 4);
	
	
	
	public void enterRoom(Runnable enterRoomAction) {
		enterRoomQueue.add(enterRoomAction);
	}
	

}
