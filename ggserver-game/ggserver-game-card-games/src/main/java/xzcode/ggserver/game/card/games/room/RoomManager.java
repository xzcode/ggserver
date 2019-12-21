package xzcode.ggserver.game.card.games.room;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import xzcode.ggserver.core.server.IGGServer;
import xzcode.ggserver.game.card.games.house.House;
import xzcode.ggserver.game.card.games.player.RoomPlayer;

/**
 * 房间管理器
 * 
 * @param <R>
 * @param <P>
 * @author zai
 * 2019-12-21 11:50:27
 */
public class RoomManager<P extends RoomPlayer<R>, R extends Room<P, R>, H extends House<P, R>> {
	
	/**
	 * ggserver对象
	 */
	protected IGGServer server;
	
	/**
	 * 进入房间队列
	 */
	protected Queue<Runnable> enterRoomQueue = new ConcurrentLinkedQueue<>();
	
	/**
	 * 大厅集合Map<houseNo, house>
	 */
	protected Map<String, House<P, R>> houses = new ConcurrentHashMap<>(1000);
	
	/**
	 * 玩家集合Map<playerNo, player>
	 */
	protected Map<String, P> players = new ConcurrentHashMap<>(houses.size() * 4);
	
	
	
	public void enterRoom(Runnable enterRoomAction) {
		enterRoomQueue.add(enterRoomAction);
	}
	
	public void addRoom(R room) {
		House<P, R> house = houses.get(room.getHouseNo());
		house.addRoom(room);
	}
	
	public void removeRoom(String roomNo) {
		House<P, R> house = houses.get(roomNo);
		if (house == null) {
			return;
		}
		house.removeRoom(roomNo);
	}
	
	public void removeRoom(R room) {
		House<P, R> house = houses.get(room.getRoomNo());
		if (house == null) {
			return;
		}
		house.removeRoom(room.getRoomNo());
	}
	
	
	public Map<String, House<P, R>> getHouses() {
		return houses;
	}
	
	public Map<String, P> getPlayers() {
		return players;
	}

}
