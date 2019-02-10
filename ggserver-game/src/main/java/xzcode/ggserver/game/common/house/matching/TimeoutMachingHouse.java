package xzcode.ggserver.game.common.house.matching;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.game.common.holder.matching.MachingPlayerHolder;
import xzcode.ggserver.game.common.player.Player;
import xzcode.ggserver.game.common.room.Room;

/**
 * 匹配客超时型大厅管理器
 * 
 * @param <R>
 * @author zai
 * 2019-01-22 19:02:38
 */
public abstract class TimeoutMachingHouse< R extends Room<P>, P extends Player> extends MachingHouse<R, P>{
	
	private static final Logger logger = LoggerFactory.getLogger(TimeoutMachingHouse.class);
	
	
	
}
