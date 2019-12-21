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
public class RoomManager<P extends RoomPlayer<R>, R extends Room<P, R>> {
	
	/**
	 * ggserver对象
	 */
	protected IGGServer server;
	
	/**
	 * 进入房间队列
	 */
	protected Queue<Runnable> enterRoomQueue = new ConcurrentLinkedQueue<>();
	
	/**
	 * 大厅集合Map<houseNo,房间集合Map<RoomNo, Room>>
	 */
	protected Map<String, Map<String, R>> houses = new ConcurrentHashMap<>(1000);
	
	/**
	 * 玩家集合Map<playerNo, player>
	 */
	protected Map<String, P> players = new ConcurrentHashMap<>(houses.size() * 4);
	
	
	
	public void enterRoom(Runnable enterRoomAction) {
		enterRoomQueue.add(enterRoomAction);
	}
	
	public void addRoom(R room) {
		Map<String, R> rooms = houses.get(room.getHouseNo());
		rooms.put(room.getRoomNo(), room);
	}
	
	
	public Map<String, Map<String, R>> getHouses() {
		return houses;
	}
	
	public Map<String, P> getPlayers() {
		return players;
	}

}
