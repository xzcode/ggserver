package xzcode.ggserver.game.card.games.room;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import xzcode.ggserver.core.server.GGServer;
import xzcode.ggserver.game.card.games.cardgames.player.RoomPlayer;
import xzcode.ggserver.game.card.games.room.listener.IRoomAddPlayerListener;
import xzcode.ggserver.game.card.games.room.listener.IRoomPlayerListener;

/**
 * 游戏房间
 * 
 * 
 * @author zai 2018-05-24
 */
public abstract class Room<P extends RoomPlayer<R>, R>{	
	
	protected GGServer ggServer;
	
	/**
	 * 房间id
	 */
	protected Object roomId;

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
	public abstract int getMaxPalyerNum();	
	
	
	/**
	 * 玩家集合
	 */
	protected Map<Object, P> players = new ConcurrentHashMap<>(4);
	
	
	
	/**
	 * 添加玩家监听器
	 */
	protected List<IRoomAddPlayerListener<P>> afterAddPlayerListeners = new ArrayList<>(1);
	
	/**
	 * 移除玩家监听器
	 */
	protected List<IRoomPlayerListener<P>> afterRemovePlayerListeners = new ArrayList<>(1);
	
	
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
	public P removePlayer(Object playerId) {
		synchronized (this) {
			P player = players.remove(playerId);
			if (player != null ) {
				if (player != null && afterAddPlayerListeners.size() > 0) {
					for (IRoomPlayerListener<P> listener : afterRemovePlayerListeners) {
						listener.onRemove(player);
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
	public void addPlayer(P player) {
		synchronized (this) {
			if (player != null) {
				this.players.put(player.getPlayerId(), player);
				if (afterRemovePlayerListeners.size() > 0) {
					for (IRoomAddPlayerListener<P> listener : afterAddPlayerListeners) {
						listener.onAdd(player);
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
	public void addAfterAddPlayerListener(IRoomAddPlayerListener<P> listener) {
		this.afterAddPlayerListeners.add(listener);
	}
	/**
	 * 添加移除玩家监听器
	 * 
	 * @param listener
	 * @author zai
	 * 2019-04-13 15:32:03
	 */
	public void addAfterRemovePlayerListener(IRoomPlayerListener<P> listener) {
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

	public void setPlayers(Map<Object, P> players) {
		this.players = players;
	}

	public List<IRoomAddPlayerListener<P>> getAfterAddPlayerListeners() {
		return afterAddPlayerListeners;
	}


	public void setAfterAddPlayerListeners(List<IRoomAddPlayerListener<P>> afterAddPlayerListeners) {
		this.afterAddPlayerListeners = afterAddPlayerListeners;
	}


	public List<IRoomPlayerListener<P>> getAfterRemovePlayerListeners() {
		return afterRemovePlayerListeners;
	}


	public void setAfterRemovePlayerListeners(List<IRoomPlayerListener<P>> afterRemovePlayerListeners) {
		this.afterRemovePlayerListeners = afterRemovePlayerListeners;
	}


	
	
	public GGServer getGgServer() {
		return ggServer;
	}


	public void setGgServer(GGServer ggServer) {
		this.ggServer = ggServer;
	}
	
}

