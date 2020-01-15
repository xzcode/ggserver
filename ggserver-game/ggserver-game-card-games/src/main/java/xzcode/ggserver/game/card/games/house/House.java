package xzcode.ggserver.game.card.games.house;

import java.util.Map;
import java.util.Map.Entry;
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
public abstract class House
<
P extends RoomPlayer<P, R, H>,
R extends Room<P, R, H>, 
H extends House<P, R, H>
> 
{
	
	/**
	 * 大厅编号
	 */
	protected String houseNo;
	
	/**
	 * 大厅名称
	 */
	protected String houseName;
	
	

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
	
	/**
	 * 获取房间
	 * 
	 * @param roomNo
	 * @return
	 * @author zai
	 * 2019-12-26 10:54:33
	 */
	public R getRoom(String roomNo) {
		return rooms.get(roomNo);
	}
	
	/**
	 * 获取一个房间
	 * 
	 * @return
	 * @author zai
	 * 2020-01-15 11:47:57
	 */
	public R getOneRoom() {
		for (Entry<String, R> entry : rooms.entrySet()) {
			return entry.getValue();
		}
		return null;
	}
	
	public String getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public Map<String, R> getRooms() {
		return rooms;
	}

	public void setRooms(Map<String, R> rooms) {
		this.rooms = rooms;
	}
	
	
	
	
	
}
