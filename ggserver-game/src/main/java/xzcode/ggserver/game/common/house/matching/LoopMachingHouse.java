package xzcode.ggserver.game.common.house.matching;

import java.lang.reflect.ParameterizedType;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.game.common.house.House;
import xzcode.ggserver.game.common.player.Player;
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
	P extends Player<R, H>,
	R extends MatchingRoom< P, R, H>, 
	H extends House<P, R, H>
> 
extends House<P, R, H>{
	
	private static final Logger logger = LoggerFactory.getLogger(LoopMachingHouse.class);
	
	private Class<P> pClass;
	private Class<R> rClass;
	private Class<H> hClass;
	

	/**
	 * 匹配容器集合
	 */
	protected Map<String, R> matchingRooms = new ConcurrentHashMap<>(); 
	
	/**
	 * 匹配超时时间(毫秒)
	 */
	protected long matchingTimeoutMillisec = 3 * 1000;
	
	/**
	 * 超时行为
	 */
	protected ILoopMatchingTimeoutAction<R> timeoutAction;
	
	/**
	 * 超时检查行为
	 */
	protected ILoopCheckTimeoutAction<R> checkTimeoutAction = new ILoopCheckTimeoutAction<R>() {
		@Override
		public boolean checkTimeout(R room) {
			return room.getPlayerNum() == 1 && room.checkMatchingTimeout();
		}
	} ;

	
	private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
		private AtomicInteger thIndex = new AtomicInteger(0);
		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, LoopMachingHouse.class.getSimpleName() + "-" + getHouseId()+ "-" + thIndex.incrementAndGet());
		}
	});
	
	public LoopMachingHouse() {
		init();
	}
	
	
	public void init() {
		this.pClass = this.getPClass();
		this.rClass = this.getRClass();
		this.hClass = this.getHClass();
		
		executor.setMaximumPoolSize(2);
		
		//满员游戏房间检查
		executor.scheduleWithFixedDelay(() -> {
			try {
				Set<Entry<String, R>> es = rooms.entrySet();
				R room = null; 
				for (Entry<String, R> e : es) {
					room = e.getValue();
					if (!room.isFullPlayers()) {
						matchingRooms.put(e.getKey(), e.getValue());
					}else {
						rooms.put(e.getKey(), e.getValue());
						matchingRooms.remove(e.getKey());
					}
				}
			} catch (Exception e) {
				logger.error("loop room error!", e);
			}
		}, 500, 1, TimeUnit.MILLISECONDS);
		
		//匹配房间检查
		executor.scheduleWithFixedDelay(() -> {
			try {
				Set<Entry<String, R>> es = matchingRooms.entrySet();
				R room = null; 
				for (Entry<String, R> e : es) {
					room = e.getValue();
					if (room.getPlayerNum() == 0) {
						synchronized(room) {
							if (room.getPlayerNum() == 0) {
								this.removeRoom(room.getRoomNo());
							}
						}
						continue;
					}
					if (this.timeoutAction != null && this.checkTimeoutAction != null && this.checkTimeoutAction.checkTimeout(room)) {
						timeoutAction.runAction(room);
					}
					
				}
			} catch (Exception e) {
				logger.error("loop matching error!", e);
			}
		}, 500, 1, TimeUnit.MILLISECONDS);
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
							matchedAction.onMatch(player, room, this);
							if (room.isFullPlayers()) {
								rooms.put(e.getKey(), e.getValue());
								matchingRooms.remove(e.getKey());
							}
							break;
						}
					}
				}
			}
		}
		return room;
	}
	
	
	
	@Override
	public R removeRoom(String roomNo) {
		matchingRooms.remove(roomNo);
		return super.removeRoom(roomNo);
	}

	private R newRoomNewInstance() {
		R room = null;
		try {
			room = rClass.newInstance();
		} catch (Exception e) {
			logger.error("create room failed!!", e);
		}
		return room;
	}
	
	
	public void addMatchingRoom(R room) {
		this.matchingRooms.put(room.getRoomNo(), room);
	}
	
	
	
	
	
	
	private Class<?> getSuperClassGenericsClass(int index){
		return (Class<?>) ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[index];
	}
	
	@SuppressWarnings("unchecked")
	private Class<P> getPClass(){
		return (Class<P>) getSuperClassGenericsClass(0);
	}
	@SuppressWarnings("unchecked")
	private Class<R> getRClass(){
		return (Class<R>) getSuperClassGenericsClass(1);
	}
	@SuppressWarnings("unchecked")
	private Class<H> getHClass(){
		return (Class<H>) getSuperClassGenericsClass(2);
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
	public void setTimeoutAction(ILoopMatchingTimeoutAction<R> timeoutAction) {
		this.timeoutAction = timeoutAction;
	}
	public ILoopMatchingTimeoutAction<R> getTimeoutAction() {
		return timeoutAction;
	}
}
