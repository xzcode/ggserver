package xzcode.ggserver.game.common.house;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xzcode.ggserver.game.common.house.listener.IRemoveRoomListener;
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
	

	protected Class<P> pClass;
	protected Class<R> rClass;
	protected Class<H> hClass;

	/**
	 * 大厅id
	 */
	protected Long houseId;

	/**
	 * 游戏id
	 */
	protected Long gameId;
	
	protected ConcurrentHashMap<Long, P> players = new ConcurrentHashMap<>();


	/**
	 * 房间集合
	 */
	protected ConcurrentHashMap<String, R> rooms = new ConcurrentHashMap<>();
	
	
	/**
	 * 移除房间监听器集合
	 */
	protected List<IRemoveRoomListener<R, H>> reomveListeners = new ArrayList<>();
	
	/**
	 * 大厅线程任务执行器
	 */
	protected ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
		private AtomicInteger thIndex = new AtomicInteger(0);
		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, "house-" + getHouseId() + "-" + thIndex.incrementAndGet());
		}
	});
	
	
	public House() {
		this.pClass = this.getPClass();
		this.rClass = this.getRClass();
		this.hClass = this.getHClass();
		
		//游戏房间检查
		executor.scheduleWithFixedDelay(() -> {
			try {
				Set<Entry<String, R>> es = rooms.entrySet();
				R room = null; 
				for (Entry<String, R> e : es) {
					room = e.getValue();
					//如果房间没有玩家，自动回收
					if (room.getPlayerNum() == 0) {
						synchronized(room) {
							if (room.getPlayerNum() == 0) {
								this.removeRoom(e.getKey());
							}
						}
						continue;
					}
				}
			} catch (Exception e) {
				logger.error("loop room error!", e);
			}
		}, 500, 1, TimeUnit.MILLISECONDS);
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
	@SuppressWarnings("unchecked")
	public R removeRoom(String roomNo) {
		R room = rooms.remove(roomNo);
		if (room != null) {
			for (IRemoveRoomListener<R, H> listener : reomveListeners) {
				listener.onRemove(room, (H) this);
			}
		}
		return room;
	}

	/**
	 * 添加房间
	 * 
	 * @param roomNo
	 * @param room
	 * @author zai 2018-12-27 13:57:46
	 */
	public R addRoom(R room) {
		return rooms.putIfAbsent(room.getRoomNo(), room);
	}

	/**
	 * 是否存在房间
	 * 
	 * @param roomNo
	 * @author zai 2018-12-27 13:57:46
	 */
	public boolean containsRoom(String roomNo) {
		return rooms.contains(roomNo);
	}
	
	/**
	 * 获取所有房间数量
	 * 
	 * @return
	 * @author zai
	 * 2019-04-16 12:24:39
	 */
	public int getTotalRoomsNum() {
		return rooms.size();
	}
	
	/**
	 * 添加玩家
	 * 
	 * @param player
	 * @author zai
	 * 2019-04-26 15:25:41
	 */
	public void addPlayer(P player) {
		this.players.put(player.getPlayerId(), player);
	}
	
	/**
	 * 移除玩家
	 * 
	 * @param player
	 * @author zai
	 * 2019-04-26 15:25:47
	 */
	public void removePlayer(P player) {
		this.players.remove(player.getPlayerId());
	}
	
	/**
	 * 获取玩家数
	 * 
	 * @author zai
	 * 2019-04-26 15:30:31
	 */
	public int getPlayerNum() {
		return this.players.size();
	}

	public Long getHouseId() {
		return houseId;
	}

	public void setHouseId(Long houseId) {
		this.houseId = houseId;
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}
	
	
}
