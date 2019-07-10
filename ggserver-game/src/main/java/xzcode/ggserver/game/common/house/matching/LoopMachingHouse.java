package xzcode.ggserver.game.common.house.matching;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.game.common.house.House;
import xzcode.ggserver.game.common.house.listener.IRemoveRoomListener;
import xzcode.ggserver.game.common.house.listener.IRoomTimeoutListener;
import xzcode.ggserver.game.common.house.matching.interfaces.ILoopCheckRemoveAction;
import xzcode.ggserver.game.common.house.matching.interfaces.ILoopCheckMatchingTimeoutAction;
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
	protected ILoopCheckMatchingTimeoutAction<R> checkMatchingTimeoutAction;
	
	/**
	 * 可移除房间检查行为
	 */
	protected ILoopCheckRemoveAction<R> checkRemoveAction;
	

	/**
	 * 移除房间监听器集合
	 */
	protected List<IRemoveRoomListener<R, H>> reomveListeners = new ArrayList<>(1);
	
	/**
	 * 房间超时监听器集合
	 */
	protected List<IRoomTimeoutListener<R>> roomTimeoutListeners = new ArrayList<>(1);
	
	/**
	 * 大厅线程任务执行器
	 */
	protected ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
	
	private ScheduledFuture<?> changeThreadNameSF;
	

	/**
	 * 添加移除房间监听器
	 * 
	 * @param listener
	 * @author zai
	 * 2019-04-15 11:39:29
	 */
	public void addRemoveRoomListener(IRemoveRoomListener<R, H> listener) {
		this.reomveListeners.add(listener);
	}
	
	/**
	 * 添加房间超时监听器
	 * 
	 * @param listener
	 * @author zai
	 * 2019-07-08 10:50:41
	 */
	public void addRoomTimeoutListener(IRoomTimeoutListener<R> listener) {
		this.roomTimeoutListeners.add(listener);
	}
	
	
	public LoopMachingHouse() {
		init();
	}
	
	
	@SuppressWarnings("unchecked")
	public void init() {
		
		
		//定义一个定时任务，用来修改当前线程名称
		changeThreadNameSF = executor.scheduleWithFixedDelay(() -> {
			if (houseId != null) {
				Thread.currentThread().setName("game-house-" + houseId + "-");	
				changeThreadNameSF.cancel(false);
			}
		}, 10 * 1000, 5 * 1000, TimeUnit.MILLISECONDS);
		
		//游戏房间检查
		executor.scheduleWithFixedDelay(() -> {
			
			try {
				Set<Entry<String, R>> es = rooms.entrySet();
				for (Entry<String, R> e : es) {
					
					R room = e.getValue();
					//检查房间使用超时
					checkRoomTimeout(room);
					
					//如果房间没有玩家，标记并自动回收
					if (!room.isMarkedToRemove() && room.getPlayerNum() == 0) {
						synchronized(room) {
							if (room.getPlayerNum() == 0) {
								room.setMarkedToRemove(true);
								gg.asyncTask(() -> {
									removeRoom(room.getRoomNo());
								});
							}
						}
					}
					
					//匹配超时行为触发
					if (!room.isFullPlayers()) {//检查房间是否满员
						if (this.checkMatchingTimeoutAction != null && timeoutListeners.size() > 0) {
							if (this.checkMatchingTimeoutAction.checkTimeout(room)) {
								synchronized(room) {
									if (this.checkMatchingTimeoutAction.checkTimeout(room)) {
										for (ILoopMatchingTimeoutListener<R, H> listener : timeoutListeners) {
											listener.onTimeout(room, (H) this);
										}
									}
								}
							}
						}
					}
					
					
				}
			} catch (Exception e) {
				logger.error("loop room error!", e);
			}
			
		}, 500, 10, TimeUnit.MILLISECONDS);
		
		
	}
	
	/**
	 * 检查房间使用超时
	 * 
	 * @param room
	 * @author zai
	 * 2019-07-08 18:08:49
	 */
	public void checkRoomTimeout(R room) {
		//如果房间使用已超时，回收房间
		if (!room.isPlayTimeout() && room.getPlayTimeoutMs() > 0) {
			synchronized (room) {
				if (!room.isPlayTimeout() && room.getPlayTimeoutMs() > 0) {
					if (System.currentTimeMillis() > room.getPlayTimeoutMs()) {
						if (gg != null) {
							//异步执行超时监听器任务
							gg.asyncTask(() -> {
								for (IRoomTimeoutListener<R> listener : roomTimeoutListeners) {
									listener.onTimeout(room);
								}
							});
						}
					}
				}
			}
		}
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
	public R match(P player, int roomType, ILoopMatchedAction<P, R, H> matchedAction) {
		R room = null;
		if (rooms.size() > 0) {
			Set<Entry<String, R>> es = rooms.entrySet();
			Iterator<Entry<String, R>> it = es.iterator();
			while (it.hasNext()) {
				Entry<String, R> e = it.next();
				room = e.getValue();
				if (room.getRoomType() == roomType && !room.isFullPlayers() && room.getPlayerNum() != 0) {
					synchronized (room) {
						if (room.getRoomType() == roomType && !room.isFullPlayers() && room.getPlayerNum() != 0) {
							player.setRoom(room );
							room.addPlayer(player);
							if (matchedAction != null) {
								matchedAction.onMatch(player, room, this);								
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
	public R match(P player, int roomType) {
		return match(player, roomType, null);
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
	
	
	@Override
	public int getTotalRoomsNum() {
		return this.rooms.size();
	}
	
	public void setMatchingTimeoutMillisec(long matchingTimeoutMillisec) {
		this.matchingTimeoutMillisec = matchingTimeoutMillisec;
	}
	
	public long getMatchingTimeoutMillisec() {
		return matchingTimeoutMillisec;
	}
	
	public void setCheckTimeoutAction(ILoopCheckMatchingTimeoutAction<R> checkTimeoutAction) {
		this.checkMatchingTimeoutAction = checkTimeoutAction;
	}
	public ILoopCheckMatchingTimeoutAction<R> getCheckTimeoutAction() {
		return checkMatchingTimeoutAction;
	}
	
	public void setCheckRemoveAction(ILoopCheckRemoveAction<R> checkRemoveAction) {
		this.checkRemoveAction = checkRemoveAction;
	}
	
	public ILoopCheckRemoveAction<R> getCheckRemoveAction() {
		return checkRemoveAction;
	}


}
