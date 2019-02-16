package xzcode.ggserver.game.common.house;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.game.common.player.Player;
import xzcode.ggserver.game.common.room.Room;

/**
 * 大厅管理器
 * 
 * @param <R>
 * @author zai
 * 2019-01-22 19:02:38
 */
public abstract class House<R extends Room<P>, P extends Player> {
	
	private static final Logger logger = LoggerFactory.getLogger(House.class);
	
	/**
	 * 大厅id
	 */
	private Object houseId;
	
	/**
	 * 游戏id
	 */
	private Object gameId;
	
	/**
	 * 获取最大房间数
	 * 
	 * @return
	 * @author zai
	 * 2019-01-22 19:02:16
	 */
	public abstract int getMaxRoomNum();
	
	
	
	/**
	 * 房间集合
	 */
	protected ConcurrentHashMap<String, R> rooms = new ConcurrentHashMap<>(getMaxRoomNum());
	
	/**
	 * 获取房间
	 * 
	 * @param roomNo
	 * @return
	 * @author zai
	 * 2018-12-27 13:57:56
	 */
	public R getRoom(String roomNo) {
		return rooms.get(roomNo);
	}
	
	
	
	
	/**
	 * 获取最大玩家数
	 * 
	 * @return
	 * @author zai
	 * 2019-01-24 10:44:40
	 */
	/*public abstract int getMaxPlayerNum();*/
	
	
	/**
	 * 玩家集合
	 */
	/*protected ConcurrentHashMap<Object, P> players = new ConcurrentHashMap<>(getMaxPlayerNum());*/
	
	/**
	 * 获取玩家
	 * 
	 * @param userId
	 * @return
	 * @author zai
	 * 2019-01-24 13:40:19
	 */
	/*public P getPlayer(Object userId) {
		return players.get(userId);
	}*/
	
	/**
	 * 玩家是否在大厅内
	 * 
	 * @param player
	 * @return
	 * @author zai
	 * 2019-01-24 15:02:53
	 */
	/*public boolean playerInHouse(P player) {
		return this.players.contains(player.getUserId());
	}
	*/
	/**
	 * 玩家进大厅
	 * 
	 * @param player
	 * @return
	 * @author zai
	 * 2019-01-24 13:48:41
	 */
	/*public boolean enterHouse(P player) {
		//做人数限制
		if (this.getMaxPlayerNum() >= this.players.size()) {
			return false;
		}
		if (this.getMaxRoomNum() > this.rooms.size()) {
			return false;
		}
		if (player == null) {
			return false;
		}
		this.players.putIfAbsent(player.getUserId(), player);
		return true;
	}*/
	
	/**
	 * 离开大厅
	 * 
	 * @param userId
	 * @return
	 * @author zai
	 * 2019-01-24 15:00:57
	 */
	/*public P leaveHouse(Object userId) {
		return this.players.remove(userId);
	}
	*/
	/**
	 * 移除房间
	 * 
	 * @param roomNo
	 * @return
	 * @author zai
	 * 2019-01-04 17:49:01
	 */
	public R removeRoom(String roomNo) {
		return rooms.remove(roomNo);
	}
	
	/**
	 * 添加房间
	 * 
	 * @param roomNo
	 * @param room
	 * @author zai
	 * 2018-12-27 13:57:46
	 */
	public void addRoom(String roomNo, R room) {
		rooms.putIfAbsent(roomNo, room);
	}
	
	/**
	 * 是否存在房间
	 * 
	 * @param roomNo
	 * @author zai
	 * 2018-12-27 13:57:46
	 */
	public boolean contains(String roomNo) {
		return rooms.contains(roomNo);
	}
	
	public Object getHouseId() {
		return houseId;
	}
	
	public void setHouseId(Object houseId) {
		this.houseId = houseId;
	}
	
	public Object getGameId() {
		return gameId;
	}
	
	public void setGameId(Object gameId) {
		this.gameId = gameId;
	}
}
