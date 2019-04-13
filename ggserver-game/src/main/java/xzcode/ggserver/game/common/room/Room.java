package xzcode.ggserver.game.common.room;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import xzcode.ggserver.game.common.player.Player;

/**
 * 游戏房间
 * 
 * 
 * @author zai 2018-05-24
 */
public abstract class Room<P extends Player<R, H>, R, H>{	
	/**
	 * 房间id
	 */
	protected Object roomId;

	/**
	 * 房间编号
	 */
	protected String roomNo;
	
	/**
	 * 大厅
	 */
	protected H house;

	/**
	 * 房主id
	 */
	protected Object ownerUserId;

	/**
	 * 房主编号
	 */
	protected String ownerUserNo;
	
	/**
	 * 获取最大玩家数
	 * 
	 * @return
	 * @author zai
	 * 2019-02-20 19:07:54
	 */
	public abstract int getMaxPalyerNum();	
	
	/**
	 * 移除玩家
	 * 
	 * @param playerId
	 * @return
	 * @author zai
	 * 2019-02-20 19:10:19
	 */
	public P removePlayer(Object playerId) {
		return players.remove(playerId);
	}

	/**
	 * 玩家集合
	 */
	protected Map<Object, P> players = new ConcurrentHashMap<>();
	
	
	/**
	 * 是否满员
	 * 
	 * @return
	 * @author zai
	 * 2019-02-20 20:24:18
	 */
	public boolean isFullPlayers() {
		return players.size() >= this.getMaxPalyerNum();
	}
	
	/**
	 * 获取当前玩家数
	 * 
	 * @return
	 * @author zai
	 * 2019-02-20 20:24:50
	 */
	public int getPlayerNum() {
		return players.size();
	}
	
	
	public Map<Object, P> getPlayers() {
		return players;
	}

	public P getPlayer(Object playerId) {
		return players.get(playerId);
	}
	/**
	 * 获取房间当前按座位号排序玩家列表
	 * 
	 * @return
	 * @author Wmc
	 * 2019-02-20 15:30:50
	 */
	public List<P> getOrderPlayerList() {
		return players.entrySet().stream().map((entry) -> entry.getValue()).sorted((x, y) -> x.getSeatNum() - y.getSeatNum()).collect(Collectors.toList());
	}
	
	/**
	 * 添加玩家
	 * 
	 * @param player
	 * @author zai 2019-01-24 19:42:15
	 */
	public void addPlayer(P player) {
		this.players.put(player.getPlayerId(), player);
	}

	public Object getRoomId() {
		return roomId;
	}

	public void setRoomId(Object roomId) {
		this.roomId = roomId;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public Object getOwnerUserId() {
		return ownerUserId;
	}

	public void setOwnerUserId(Object ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	public String getOwnerUserNo() {
		return ownerUserNo;
	}

	public void setOwnerUserNo(String ownerUserNo) {
		this.ownerUserNo = ownerUserNo;
	}
	
	public H getHouse() {
		return house;
	}
	
	public void setHouse(H house) {
		this.house = house;
	}

}

