package xzcode.ggserver.game.common.house.matching;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.game.common.holder.matching.MachingPlayerHolder;
import xzcode.ggserver.game.common.house.House;
import xzcode.ggserver.game.common.player.Player;
import xzcode.ggserver.game.common.room.Room;

/**
 * 匹配型大厅管理器
 * 
 * @param <R>
 * @author zai
 * 2019-01-22 19:02:38
 */
public abstract class MachingPlayerHouse
<
P extends Player<R, H>,
R extends Room< P, R, H>, 
H extends House<P, R, H>
> 
extends House<P, R, H>{
	
	private static final Logger logger = LoggerFactory.getLogger(MachingPlayerHouse.class);
	

	/**
	 * 匹配容器集合
	 */
	protected ConcurrentHashMap<Object, MachingPlayerHolder<P>> machingPlayerHolders = new ConcurrentHashMap<>();

	/**
	 * 进行匹配
	 */
	public abstract List<MachingPlayerHolder<P>> match();
	
	
	/**
	 * 添加匹配中玩家容器
	 * 
	 * @param playerHolder
	 * @author zai
	 * 2019-01-24 11:11:28
	 */
	public void addMachingPlayerHolder(MachingPlayerHolder<P> playerHolder) {
		this.machingPlayerHolders.put(playerHolder.getPlayer().getPlayerId(), playerHolder);
	}
	
	public ConcurrentHashMap<Object, MachingPlayerHolder<P>> getMachingPlayerHolders() {
		return machingPlayerHolders;
	}
	
	/**
	 * 获取匹配容器
	 * 
	 * @param userId
	 * @return
	 * @author zai
	 * 2019-01-24 13:30:38
	 */
	public MachingPlayerHolder<P> getMachingHolder(Object userId) {
		return machingPlayerHolders.get(userId);
	}
	
	/**
	 * 取消并移除玩家匹配容器
	 * 
	 * @param userId
	 * @return
	 * @author zai
	 * 2019-01-28 14:47:55
	 */
	public MachingPlayerHolder<P> cancelMatching(Object userId) {
		MachingPlayerHolder<P> machingHolder = getMachingHolder(userId);
		if (machingHolder != null) {
			synchronized (this) {
				machingHolder.cancelTask();
				machingHolder.setCanceled(true);
			}
		}
		return machingPlayerHolders.remove(userId);
		
		
	}
	
	/**
	 * 玩家是否正在匹配中
	 * 
	 * @param userId
	 * @return
	 * @author zai
	 * 2019-01-24 17:37:39
	 */
	public boolean hasMachingHolder(Object userId) {
		return machingPlayerHolders.contains(userId);
	}
	
	
	
}
