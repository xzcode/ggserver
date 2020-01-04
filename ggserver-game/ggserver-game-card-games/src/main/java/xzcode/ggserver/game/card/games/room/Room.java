package xzcode.ggserver.game.card.games.room;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import xzcode.ggserver.core.server.IGGServer;
import xzcode.ggserver.game.card.games.house.House;
import xzcode.ggserver.game.card.games.player.RoomPlayer;
import xzcode.ggserver.game.card.games.room.listener.IPlayerEnterListener;
import xzcode.ggserver.game.card.games.room.listener.IPlayerLeaveListener;

/**
 * 游戏房间
 * 
 * 
 * @author zai 2018-05-24
 */
public abstract class Room
<
P extends RoomPlayer<P, R, H>,
R extends Room<P, R, H>, 
H extends House<P, R, H>
>{	
	
	
	/**
	 * ggserver对象
	 */
	protected IGGServer ggserver;
	
	/**
	 * 大厅
	 */
	protected H house;
	
	/**
	 * 房间编号
	 */
	protected String roomNo;
	
	
	/**
	 * 获取最大玩家数
	 * 
	 * @return
	 * @author zai
	 * 2019-02-20 19:07:54
	 */
	public abstract int getMaxPlayerNum();	
	
	
	/**
	 * 玩家集合
	 */
	protected Map<Object, P> players = new ConcurrentHashMap<>(4);
	
	
	
	/**
	 * 添加玩家监听器
	 */
	protected List<IPlayerEnterListener<P>> playerEnterListeners = new ArrayList<>(1);
	
	/**
	 * 移除玩家监听器
	 */
	protected List<IPlayerLeaveListener<P>> playerLeaveListeners = new ArrayList<>(1);
	
	
	/**
	 * 是否满员
	 * 
	 * @return
	 * @author zai
	 * 2019-02-20 20:24:18
	 */
	public boolean isFullPlayers() {
		return players.size() >= this.getMaxPlayerNum();
	}
	
	
	/**
	 * 移除玩家
	 * 
	 * @param playerId
	 * @return
	 * @author zai
	 * 2019-02-20 19:10:19
	 */
	public P removePlayer(P player) {
		P remove = players.remove(player.getPlayerNo());
		if (remove != null) {
			if (player != null && playerLeaveListeners.size() > 0) {
				for (IPlayerLeaveListener<P> listener : playerLeaveListeners) {
					listener.leave(player);
				}
			}
		}
		return remove;
	}
	
	/**
	 * 添加玩家
	 * 
	 * @param player
	 * @author zai 2019-01-24 19:42:15
	 */
	public void addPlayer(P player) {
		if (player != null) {
			this.players.put(player.getPlayerNo(), player);
			if (playerLeaveListeners.size() > 0) {
				for (IPlayerEnterListener<P> listener : playerEnterListeners) {
					listener.enter(player);
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
	public void addPlayerEnterListener(IPlayerEnterListener<P> listener) {
		this.playerEnterListeners.add(listener);
	}
	/**
	 * 添加移除玩家监听器
	 * 
	 * @param listener
	 * @author zai
	 * 2019-04-13 15:32:03
	 */
	public void addPlayerLeaveListener(IPlayerLeaveListener<P> listener) {
		this.playerLeaveListeners.add(listener);
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

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public void setPlayers(Map<Object, P> players) {
		this.players = players;
	}

	public List<IPlayerEnterListener<P>> getAfterAddPlayerListeners() {
		return playerEnterListeners;
	}


	public void setAfterAddPlayerListeners(List<IPlayerEnterListener<P>> afterAddPlayerListeners) {
		this.playerEnterListeners = afterAddPlayerListeners;
	}


	public List<IPlayerLeaveListener<P>> getAfterRemovePlayerListeners() {
		return playerLeaveListeners;
	}


	public void setAfterRemovePlayerListeners(List<IPlayerLeaveListener<P>> afterRemovePlayerListeners) {
		this.playerLeaveListeners = afterRemovePlayerListeners;
	}


	
	
	public IGGServer getGGserver() {
		return ggserver;
	}


	public void setGGserver(IGGServer server) {
		this.ggserver = server;
	}
	
	public H getHouse() {
		return house;
	}
	
	public void setHouse(H house) {
		this.house = house;
	}
	
}

