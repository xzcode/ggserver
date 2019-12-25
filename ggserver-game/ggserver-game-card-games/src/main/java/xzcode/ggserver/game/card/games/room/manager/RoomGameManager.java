package xzcode.ggserver.game.card.games.room.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import xzcode.ggserver.core.common.executor.ITaskExecutor;
import xzcode.ggserver.core.common.executor.SingleThreadTaskExecutor;
import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.server.IGGServer;
import xzcode.ggserver.game.card.games.house.House;
import xzcode.ggserver.game.card.games.player.RoomPlayer;
import xzcode.ggserver.game.card.games.room.Room;
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
public abstract class RoomGameManager
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
	protected Map<String, H> houses;
	
	/**
	 * 玩家集合Map<playerNo, player>
	 */
	protected Map<String, P> players;
	
	/**
	 * 任务执行器
	 */
	protected ITaskExecutor executor;
	
	/**
	 * 构造器
	 * @param designPlayerNum 预计总玩家数量
	 */
	public RoomGameManager(int designPlayerNum) {
		
		houses = initHouses();
		
		players = new ConcurrentHashMap<>(designPlayerNum);
		
		executor = new SingleThreadTaskExecutor("room-game-manager-");
		
	}
	
	/**
	 * 初始化大厅集合
	 * 
	 * @return
	 * @author zai
	 * 2019-12-22 16:24:08
	 */
	protected abstract ConcurrentHashMap<String, H> initHouses();
	
	
	/**
	 * 进入房间操作
	 * 
	 * @param enterRoomAction
	 * @author zai
	 * 2019-12-22 16:24:24
	 */
	public void enterRoom(IEnterRoomAction<P, R, H> enterRoomAction) {
		H house = houses.get(enterRoomAction.getHouseNo());
		//查询并进入指定大厅房间
		if (house != null) {
			enterRoomAction.setHouse(house);
			house.enterRoom(enterRoomAction);
		}
	}
	
	public IGGFuture submitTask(Runnable runnable) {
		return executor.submitTask(runnable);
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
	
	public void addPlayer(P player) {
		players.put(player.getPlayerNo(), player);
	}
	public void removePlayer(P player) {
		players.remove(player.getPlayerNo());
	}
	public void removePlayer(String playerNo) {
		players.remove(playerNo);
	}
	
	
	
	public H getHouse(String houseNo){
		return houses.get(houseNo);
	}
	
	public Map<String, H> getHouses() {
		return houses;
	}
	
	public Map<String, P> getPlayers() {
		return players;
	}
	
	public P getPlayer(String playerNo) {
		return players.get(playerNo);
	}
	
	public ITaskExecutor getExecutor() {
		return executor;
	}

}
