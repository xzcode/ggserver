package xzcode.ggserver.game.support.house;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.core.GGServer;
import xzcode.ggserver.game.support.player.Player;
import xzcode.ggserver.game.support.room.Room;

/**
 * 大厅管理器
 * 
 * @param <R>
 * @author zai 2019-01-22 19:02:38
 */
public abstract class House< P extends Player, R extends Room<P>, H> {
	
	private static final Logger logger = LoggerFactory.getLogger(House.class);
	/**
	 * ggserver对象
	 */
	protected GGServer ggServer;

	/**
	 * 玩家集合
	 */
	protected ConcurrentHashMap<Object, P> players = new ConcurrentHashMap<>(2000);


	/**
	 * 房间集合
	 */
	protected ConcurrentHashMap<Object, R> rooms = new ConcurrentHashMap<>(1000);
	
	/**
	 * 获取房间
	 * 
	 * @param roomNo
	 * @return
	 * @author zai 2018-12-27 13:57:56
	 */
	public R getRoom(Object roomKey) {
		return rooms.get(roomKey);
	}

	/**
	 * 移除房间
	 * 
	 * @param roomNo
	 * @return
	 * @author zai 2019-01-04 17:49:01
	 */
	public R removeRoom(Object roomKey) {
		return rooms.remove(roomKey);
	}

	/**
	 * 添加房间
	 * 
	 * @param roomNo
	 * @param room
	 * @author zai 2018-12-27 13:57:46
	 */
	public R addRoom(Object roomKey, R room) {
		return rooms.putIfAbsent(roomKey, room);
	}

	/**
	 * 是否存在房间
	 * 
	 * @param roomNo
	 * @author zai 2018-12-27 13:57:46
	 */
	public boolean containsRoom(Object roomKey) {
		return rooms.containsKey(roomKey);
	}
	
	/**
	 * 获取所有房间数量
	 * 
	 * @return
	 * @author zai
	 * 2019-04-16 12:24:39
	 */
	public int getTotalRoomsNum() {
		return rooms.size();
	}
	
	/**
	 * 添加玩家
	 * 
	 * @param player
	 * @author zai
	 * 2019-04-26 15:25:41
	 */
	public void addPlayer(Object playerKey, P player) {
		this.players.put(playerKey, player);
	}
	
	/**
	 * 移除玩家
	 * 
	 * @param player
	 * @author zai
	 * 2019-04-26 15:25:47
	 */
	public P removePlayer(Object playerKey) {
		return this.players.remove(playerKey);
	}
	
	/**
	 * 获取玩家数
	 * 
	 * @author zai
	 * 2019-04-26 15:30:31
	 */
	public int getPlayerNum() {
		return this.players.size();
	}


	public GGServer getGgServer() {
		return ggServer;
	}
	public void setGgServer(GGServer ggServer) {
		this.ggServer = ggServer;
	}
	
	
}
