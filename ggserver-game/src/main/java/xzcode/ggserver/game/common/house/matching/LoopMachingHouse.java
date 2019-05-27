package xzcode.ggserver.game.common.house.matching;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.game.common.house.House;
import xzcode.ggserver.game.common.house.listener.IRemoveRoomListener;
import xzcode.ggserver.game.common.house.matching.interfaces.ILoopCheckRemoveAction;
import xzcode.ggserver.game.common.house.matching.interfaces.ILoopCheckTimeoutAction;
import xzcode.ggserver.game.common.house.matching.interfaces.ILoopMatchedAction;
import xzcode.ggserver.game.common.house.matching.interfaces.ILoopMatchingTimeoutListener;
import xzcode.ggserver.game.common.player.CoinsRoomPlayer;
import xzcode.ggserver.game.common.room.MatchingRoom;

/**
 * 自动匹配房间型大厅管理器
 * 
 * @param <R>
 * @param <P>
 * @author zai
 * 2019-02-20 14:33:01
 */
public class LoopMachingHouse
<
	P extends CoinsRoomPlayer<R, H>,
	R extends MatchingRoom< P, R, H>, 
	H extends House<P, R, H>
> 
extends House<P, R, H>{
	
	private static final Logger logger = LoggerFactory.getLogger(LoopMachingHouse.class);
	

	/**
	 * 匹配容器集合
	 */
	protected Map<String, R> matchingRooms = new ConcurrentHashMap<>(); 
	
	/**
	 * 匹配容器集合
	 */
	protected Map<String, R> fullPlayerRooms = new ConcurrentHashMap<>(); 
	
	/**
	 * 匹配超时时间(毫秒)
	 */
	protected long matchingTimeoutMillisec = 3 * 1000;
	
	/**
	 * 超时监听器集合
	 */
	protected List<ILoopMatchingTimeoutListener<R, H>> timeoutListeners = new ArrayList<>();
	
	
	/**
	 * 超时检查行为
	 */
	protected ILoopCheckTimeoutAction<R> checkTimeoutAction;
	
	/**
	 * 可移除房间检查行为
	 */
	protected ILoopCheckRemoveAction<R> checkRemoveAction;

	
	
	public LoopMachingHouse() {
		init();
	}
	
	
	@SuppressWarnings("unchecked")
	public void init() {
		
		
		executor.setMaximumPoolSize(3);
		
		//满员游戏房间检查
		executor.scheduleWithFixedDelay(() -> {
			try {
				Set<Entry<String, R>> es = fullPlayerRooms.entrySet();
				R room = null; 
				for (Entry<String, R> e : es) {
					room = e.getValue();
					synchronized (room) {
						if (!room.isFullPlayers()) {
							fullPlayerRooms.remove(e.getKey());
							matchingRooms.put(e.getKey(), e.getValue());
						}
					}
				}
			} catch (Exception e) {
				logger.error("loop room error!", e);
			}
		}, 10 * 1000, 1, TimeUnit.MILLISECONDS);
		
		//匹配房间检查
		executor.scheduleWithFixedDelay(() -> {
			try {
				Set<Entry<String, R>> es = matchingRooms.entrySet();
				R room = null; 
				for (Entry<String, R> e : es) {
					room = e.getValue();
					if (checkRemoveAction != null) {
						if (checkRemoveAction.checkRemove(room)) {
							synchronized(room) {
								if (checkRemoveAction.checkRemove(room)) {
									this.removeMatchingRoom(room.getRoomNo());
								}
							}
							continue;
						}
					}
					
					//超时行为触发
					if (this.checkTimeoutAction != null && timeoutListeners.size() > 0) {
						if (this.checkTimeoutAction.checkTimeout(room)) {
							synchronized(room) {
								if (this.checkTimeoutAction.checkTimeout(room)) {
									for (ILoopMatchingTimeoutListener<R, H> listener : timeoutListeners) {
										listener.onTimeout(room, (H) this);
									}
								}
							}
						}
					}
				}
			} catch (Exception e) {
				logger.error("loop matching error!", e);
			}
		}, 10 * 1000, 1, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * 对玩家进行匹配
	 * 
	 * @param player
	 * @param matchedAction
	 * @return
	 * @author zai
	 * 2019-04-12 15:08:58
	 */
	public R match(P player, ILoopMatchedAction<P, R, H> matchedAction) {
		R room = null;
		if (matchingRooms.size() > 0) {
			Set<Entry<String, R>> es = matchingRooms.entrySet();
			Iterator<Entry<String, R>> it = es.iterator();
			while (it.hasNext()) {
				Entry<String, R> e = it.next();
				room = e.getValue();
				if (!room.isFullPlayers() && room.getPlayerNum() != 0) {
					synchronized (room) {
						if (!room.isFullPlayers() && room.getPlayerNum() != 0) {
							player.setRoom(room );
							room.addPlayer(player);
							if (matchedAction != null) {
								matchedAction.onMatch(player, room, this);								
							}
							if (room.isFullPlayers()) {
								fullPlayerRooms.put(e.getKey(), e.getValue());
								matchingRooms.remove(e.getKey());
							}
							return room;
						}
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 匹配房间
	 * 
	 * @param player
	 * @return
	 * @author zai
	 * 2019-04-15 11:23:59
	 */
	public R match(P player) {
		return match(player, null);
	}
	
	/**
	 * 添加匹配超时监听器
	 * 
	 * @param listener
	 * @author zai
	 * 2019-04-15 11:43:48
	 */
	public void addMatchingTimeoutListener(ILoopMatchingTimeoutListener<R, H> listener) {
		if (listener != null) {
			this.timeoutListeners.add(listener);			
		}
	}
	
	/**
	 * 清除所有匹配超时监听器
	 * 
	 * @author zai
	 * 2019-04-18 13:58:36
	 */
	public void clearAllMatchingTimeoutListener() {
		this.timeoutListeners.clear();
	}
	
	
	/**
	 * 移除匹配房间
	 * 
	 * @param roomNo
	 * @return
	 * @author zai
	 * 2019-04-15 14:19:40
	 */
	@SuppressWarnings("unchecked")
	public R removeMatchingRoom(String roomNo) {
		R room = null;
		room = matchingRooms.remove(roomNo);
		if (room == null) {
			room = fullPlayerRooms.remove(roomNo);
		}else {
			fullPlayerRooms.remove(roomNo);
		}
		if (room != null) {
			for (IRemoveRoomListener<R, H> listener : reomveListeners) {
				listener.onRemove(room, (H) this);
			}
		}
		return room;
	}

	/**
	 * 添加匹配房间
	 * 
	 * @param room
	 * @author zai
	 * 2019-04-15 11:58:32
	 */
	public void addMatchingRoom(R room) {
		this.matchingRooms.put(room.getRoomNo(), room);
	}
	
	/**
	 * 获取参与匹配的房间
	 * 
	 * @param roomNo
	 * @return
	 * @author zai
	 * 2019-04-17 18:55:32
	 */
	public R getMatchingRoom(String roomNo) {
		R room = matchingRooms.get(roomNo);
		if (room == null) {
			room = fullPlayerRooms.get(roomNo);
		}
		return room;
	}
	
	
	@Override
	public int getTotalRoomsNum() {
		return super.getTotalRoomsNum() + this.matchingRooms.size() + this.fullPlayerRooms.size();
	}
	
	public int getOpenRoomsNum() {
		return super.getTotalRoomsNum();
	}
	public int getMatchingRoomsNum() {
		return this.matchingRooms.size() + this.fullPlayerRooms.size();
	}


	public void setMatchingTimeoutMillisec(long matchingTimeoutMillisec) {
		this.matchingTimeoutMillisec = matchingTimeoutMillisec;
	}
	
	public long getMatchingTimeoutMillisec() {
		return matchingTimeoutMillisec;
	}
	
	public void setCheckTimeoutAction(ILoopCheckTimeoutAction<R> checkTimeoutAction) {
		this.checkTimeoutAction = checkTimeoutAction;
	}
	public ILoopCheckTimeoutAction<R> getCheckTimeoutAction() {
		return checkTimeoutAction;
	}
	
	public void setCheckRemoveAction(ILoopCheckRemoveAction<R> checkRemoveAction) {
		this.checkRemoveAction = checkRemoveAction;
	}
	
	public ILoopCheckRemoveAction<R> getCheckRemoveAction() {
		return checkRemoveAction;
	}
}
