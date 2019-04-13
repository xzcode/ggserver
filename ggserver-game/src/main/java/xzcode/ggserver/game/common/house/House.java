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
 * @author zai 2019-01-22 19:02:38
 */
public abstract class House< P extends Player, R extends Room<P, R, H>, H> {

	private static final Logger logger = LoggerFactory.getLogger(House.class);

	/**
	 * 大厅id
	 */
	protected Object houseId;

	/**
	 * 游戏id
	 */
	protected Object gameId;


	/**
	 * 房间集合
	 */
	protected ConcurrentHashMap<String, R> rooms = new ConcurrentHashMap<>();

	/**
	 * 获取房间
	 * 
	 * @param roomNo
	 * @return
	 * @author zai 2018-12-27 13:57:56
	 */
	public R getRoom(String roomNo) {
		return rooms.get(roomNo);
	}

	/**
	 * 移除房间
	 * 
	 * @param roomNo
	 * @return
	 * @author zai 2019-01-04 17:49:01
	 */
	public R removeRoom(String roomNo) {
		return rooms.remove(roomNo);
	}

	/**
	 * 添加房间
	 * 
	 * @param roomNo
	 * @param room
	 * @author zai 2018-12-27 13:57:46
	 */
	public R addRoom(String roomNo, R room) {
		return rooms.putIfAbsent(roomNo, room);
	}

	/**
	 * 是否存在房间
	 * 
	 * @param roomNo
	 * @author zai 2018-12-27 13:57:46
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
