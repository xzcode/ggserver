package xzcode.ggserver.game.card.games.room.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import xzcode.ggserver.core.common.executor.ITaskExecutor;
import xzcode.ggserver.core.common.executor.SingleThreadTaskExecutor;
import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.server.IGGServer;
import xzcode.ggserver.game.card.games.house.House;
import xzcode.ggserver.game.card.games.player.RoomPlayer;
import xzcode.ggserver.game.card.games.player.factory.IPlayerFactory;
import xzcode.ggserver.game.card.games.room.Room;
import xzcode.ggserver.game.card.games.room.factory.IRoomFactory;

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
> implements IRoomGameManager<P, R, H> {
	
	/**
	 * ggserver对象
	 */
	protected IGGServer ggserver;
	
	/**
	 * 大厅集合Map<houseNo, house>
	 */
	protected Map<String, H> houses = new ConcurrentHashMap<>();
	
	/**
	 * 玩家集合Map<playerNo, player>
	 */
	protected Map<String, P> players;
	
	
	/**
	 * 任务执行器
	 */
	protected ITaskExecutor executor;
	
	
	protected IPlayerFactory<P> playerFactory;
	
	protected IRoomFactory<R> roomFactory;
	
	
	/**
	 * 构造器
	 * @param designPlayerSize 预计总玩家数量
	 */
	public RoomGameManager(
			IGGServer ggserver,
			int designPlayerSize,
			IPlayerFactory<P> playerFactory,
			IRoomFactory<R> roomFactory
			) {
		
		this.ggserver = ggserver;
		this.playerFactory = playerFactory;
		this.roomFactory = roomFactory;
		
		players = new ConcurrentHashMap<>(designPlayerSize);
		
		executor = new SingleThreadTaskExecutor("room-game-manager-");
		
	}
	
	
	
	@Override
	public P createPlayer() {
		return playerFactory.createPlayer();
	}
	@Override
	public R createRoom() {
		R room = roomFactory.createRoom();
		//注册玩家离开房间监听器
		//当玩家离开时，同时在游戏管理器移除玩家
		room.addPlayerLeaveListener(player -> {
			removePlayer(player.getPlayerNo());
		});
		
		return room;
	}
	
	@Override
	public IGGFuture submitTask(Runnable runnable) {
		return executor.submitTask(runnable);
	}
	
		
	@Override
	public void removeRoom(String roomNo) {
		House<P, R, H> house = houses.get(roomNo);
		if (house == null) {
			return;
		}
		house.removeRoom(roomNo);
	}
	
	@Override
	public void removeRoom(R room) {
		House<P, R, H> house = houses.get(room.getHouse().getHouseNo());
		if (house == null) {
			return;
		}
		house.removeRoom(room.getRoomNo());
	}
	
	@Override
	public void addRoom(R room) {
		House<P, R, H> house = houses.get(room.getRoomNo());
		if (house == null) {
			return;
		}
		house.addRoom(room);
	}
	
	@Override
	public void addHouse(H house) {
		houses.put(house.getHouseNo(), house);
	}
	
	@Override
	public void removeHouse(H house) {
		houses.remove(house.getHouseNo());
	}
	
	@Override
	public void removeHouse(String houseNO) {
		houses.remove(houseNO);
	}
	
	@Override
	public void addPlayer(P player) {
		players.put(player.getPlayerNo(), player);
	}
	@Override
	public void removePlayer(P player) {
		players.remove(player.getPlayerNo());
	}
	@Override
	public void removePlayer(String playerNo) {
		players.remove(playerNo);
	}
	
	
	
	@Override
	public H getHouse(String houseNo){
		return houses.get(houseNo);
	}
	
	@Override
	public Map<String, H> getHouses() {
		return houses;
	}
	
	@Override
	public Map<String, P> getPlayers() {
		return players;
	}
	
	@Override
	public P getPlayer(String playerNo) {
		return players.get(playerNo);
	}
	
	@Override
	public ITaskExecutor getTaskExecutor() {
		return executor;
	}
	
	public IGGServer getGGserver() {
		return ggserver;
	}

}
