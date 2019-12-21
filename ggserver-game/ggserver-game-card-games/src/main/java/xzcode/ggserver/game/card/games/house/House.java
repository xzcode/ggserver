package xzcode.ggserver.game.card.games.house;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import xzcode.ggserver.game.card.games.player.RoomPlayer;
import xzcode.ggserver.game.card.games.room.Room;

/**
 * 大厅管理器
 * 
 * @param <P>
 * @param <R>
 * @param <H>
 * @author zai
 * 2019-12-21 11:34:05
 */
public abstract class House<P extends RoomPlayer<R>, R extends Room<P, R>> {

	/**
	 * 房间集合
	 */
	protected Map<String, R> rooms = new ConcurrentHashMap<>(1000);
	
	/**
	 * 添加房间
	 * 
	 * @param room
	 * @author zai
	 * 2019-12-21 22:28:08
	 */
	public void addRoom(R room) {
		rooms.put(room.getRoomNo(), room);
	}
	
	/**
	 * 移除房间
	 * 
	 * @param room 房间对象
	 * @author zai
	 * 2019-12-21 22:29:26
	 */
	public void removeRoom(R room) {
		rooms.remove(room.getRoomNo());
	}
	
	/**
	 * 移除房间
	 * 
	 * @param roomNo 房间编号
	 * @author zai
	 * 2019-12-21 22:29:34
	 */
	public void removeRoom(String roomNo) {
		rooms.remove(roomNo);
	}
	
	
}
