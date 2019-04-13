package xzcode.ggserver.game.common.house.matching;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.game.common.holder.matching.MachingRoomHolder;
import xzcode.ggserver.game.common.house.House;
import xzcode.ggserver.game.common.player.RoomPlayer;
import xzcode.ggserver.game.common.room.Room;

/**
 * 自动匹配房间型大厅管理器
 * 
 * @param <R>
 * @param <P>
 * @author zai
 * 2019-02-20 14:33:01
 */
public abstract class MachingRoomHouse<P extends RoomPlayer<R, H>,R extends Room<P, R, H>,  H> extends House<P, R , H>{
	
	private static final Logger logger = LoggerFactory.getLogger(MachingRoomHouse.class);
	

	/**
	 * 匹配容器集合
	 */
	protected ConcurrentHashMap<Object, MachingRoomHolder<R, P, H>> machingRoomHolders = new ConcurrentHashMap<>();

	/**
	 * 进行匹配
	 * 
	 * @return 如果匹配不到房间，返回null
	 * @author zai
	 * 2019-02-20 16:36:58
	 */
	public abstract MachingRoomHolder<R, P, H> match();
	
	
	
	
	/**
	 * 添加匹配中玩家容器
	 * 
	 * @param roomHolder
	 * @author zai
	 * 2019-01-24 11:11:28
	 */
	public boolean addMachingRoomHolder(MachingRoomHolder<R, P, H> roomHolder) {
		synchronized (roomHolder) {
			if (!this.machingRoomHolders.containsKey(roomHolder.getRoom().getRoomNo())) {
				roomHolder.setMatched(false);			
				this.machingRoomHolders.put(roomHolder.getRoom().getRoomNo(), roomHolder);	
				return true;
			}
		}
		return false;
	}
	
	public ConcurrentHashMap<Object, MachingRoomHolder<R, P, H>> getMachingPlayerHolders() {
		return machingRoomHolders;
	}
	
	/**
	 * 获取匹配容器
	 * 
	 * @param roomNo
	 * @return
	 * @author zai
	 * 2019-01-24 13:30:38
	 */
	public MachingRoomHolder<R, P, H> getMachingHolder(Object key) {
		return machingRoomHolders.get(key);
	}
	
	
	/**
	 * 取消并移除玩家匹配容器
	 * 
	 * @param key
	 * @return
	 * @author zai
	 * 2019-01-28 14:47:55
	 */
	public MachingRoomHolder<R, P, H> cancelMatching(Object key) {
		MachingRoomHolder<R, P, H> machingHolder = getMachingHolder(key);
		if (machingHolder != null) {
			synchronized (this) {
				machingHolder.cancelTask();
				machingHolder.setCanceled(true);
			}
		}
		return machingRoomHolders.remove(key);
		
		
	}
	
	/**
	 * 是否正在匹配中
	 * 
	 * @param userId
	 * @return
	 * @author zai
	 * 2019-01-24 17:37:39
	 */
	public boolean hasMachingHolder(Object key) {
		return machingRoomHolders.contains(key);
	}
	
	
	
}
