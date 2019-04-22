package xzcode.ggserver.game.common.room;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import xzcode.ggserver.game.common.player.Player;
import xzcode.ggserver.game.common.room.listener.IAfterAddPlayerListener;
import xzcode.ggserver.game.common.room.listener.IAfterRemovePlayerListener;

/**
 * 游戏房间
 * 
 * 
 * @author zai 2018-05-24
 */
public abstract class Room<P extends Player, R, H>{	
	/**
	 * 房间id
	 */
	protected Object roomId;

	/**
	 * 房间编号
	 */
	protected String roomNo;
	
	/**
	 * 游戏类型
	 */
	private int roomType;
	
	
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
	 * 玩家集合
	 */
	protected Map<Object, P> players = new ConcurrentHashMap<>();
	
	/**
	 * 添加玩家监听器
	 */
	protected List<IAfterAddPlayerListener<R, P>> afterAddPlayerListeners = new ArrayList<>();
	
	/**
	 * 移除玩家监听器
	 */
	protected List<IAfterRemovePlayerListener<R, P>> afterRemovePlayerListeners = new ArrayList<>();
	
	
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
	 * 移除玩家
	 * 
	 * @param playerId
	 * @return
	 * @author zai
	 * 2019-02-20 19:10:19
	 */
	@SuppressWarnings("unchecked")
	public P removePlayer(Object playerId) {
		synchronized (this) {
			P player = players.remove(playerId);
			if (player != null ) {
				if (player != null && afterAddPlayerListeners.size() > 0) {
					for (IAfterRemovePlayerListener<R, P> listener : afterRemovePlayerListeners) {
						listener.onRemove((R) this, player);
					}
				}
			}
			return player;
		}
	}
	
	/**
	 * 添加玩家
	 * 
	 * @param player
	 * @author zai 2019-01-24 19:42:15
	 */
	@SuppressWarnings("unchecked")
	public void addPlayer(P player) {
		synchronized (this) {
			if (player != null) {
				this.players.put(player.getPlayerId(), player);
				if (afterRemovePlayerListeners.size() > 0) {
					for (IAfterAddPlayerListener<R, P> listener : afterAddPlayerListeners) {
						listener.onAdd((R) this, player);
					}
				}
			}
		}
	}
	/**
	 * 添加监听添加玩家事件监听器
	 * 
	 * @param listener
	 * @author zai
	 * 2019-04-13 15:31:37
	 */
	public void addAfterAddPlayerListener(IAfterAddPlayerListener<R, P> listener) {
		this.afterAddPlayerListeners.add(listener);
	}
	/**
	 * 添加移除玩家监听器
	 * 
	 * @param listener
	 * @author zai
	 * 2019-04-13 15:32:03
	 */
	public void addAfterRemovePlayerListener(IAfterRemovePlayerListener<R, P> listener) {
		this.afterRemovePlayerListeners.add(listener);
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


	public int getRoomType() {
		return roomType;
	}


	public void setRoomType(int roomType) {
		this.roomType = roomType;
	}


	public void setPlayers(Map<Object, P> players) {
		this.players = players;
	}
	
	
	
}

