package xzcode.ggserver.game.card.games.room;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import xzcode.ggserver.core.server.IGGServer;
import xzcode.ggserver.game.card.games.house.House;
import xzcode.ggserver.game.card.games.player.RoomPlayer;
import xzcode.ggserver.game.card.games.room.enter.IEnterRoomAction;

/**
 * 房间管理器
 * 
 * @param <P>
 * @param <R>
 * @param <H>
 * @author zai
 * 2019-12-22 11:30:22
 */
public abstract class RoomManager
<
P extends RoomPlayer<P, R, H>,
R extends Room<P, R, H>, 
H extends House<P, R, H>
> {
	
	/**
	 * ggserver对象
	 */
	protected IGGServer server;
	
	/**
	 * 大厅集合Map<houseNo, house>
	 */
	protected Map<String, House<P, R, H>> houses;
	
	/**
	 * 玩家集合Map<playerNo, player>
	 */
	protected Map<String, P> players = new ConcurrentHashMap<>(houses.size() * 4);
	
	
	public RoomManager() {
		houses = initHouses();
	}
	
	abstract ConcurrentHashMap<String, House<P, R, H>> initHouses();
	
	public void enterRoom(IEnterRoomAction<P, R, H> enterRoomAction) {
		House<P, R, H> house = houses.get(enterRoomAction.getHouseNo());
		if (house != null) {
			house.enterRoom(enterRoomAction);
		}
	}
	
		
	public void removeRoom(String roomNo) {
		House<P, R, H> house = houses.get(roomNo);
		if (house == null) {
			return;
		}
		house.removeRoom(roomNo);
	}
	
	public void removeRoom(R room) {
		House<P, R, H> house = houses.get(room.getRoomNo());
		if (house == null) {
			return;
		}
		house.removeRoom(room.getRoomNo());
	}
	
	public void addRoom(R room) {
		House<P, R, H> house = houses.get(room.getRoomNo());
		if (house == null) {
			return;
		}
		house.addRoom(room);
	}
	
	
	public Map<String, House<P, R, H>> getHouses() {
		return houses;
	}
	
	public Map<String, P> getPlayers() {
		return players;
	}
	
	public P getPlayer(String playerNo) {
		return players.get(playerNo);
	}

}
