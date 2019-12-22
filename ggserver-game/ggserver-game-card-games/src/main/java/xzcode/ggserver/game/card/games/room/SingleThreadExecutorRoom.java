package xzcode.ggserver.game.card.games.room;

import xzcode.ggserver.core.common.executor.ITaskExecutor;
import xzcode.ggserver.game.card.games.house.House;
import xzcode.ggserver.game.card.games.player.RoomPlayer;

/**
 * 单线程执行器房间
 * 
 * @param <P>
 * @param <R>
 * @param <H>
 * @author zai
 * 2019-12-22 17:32:54
 */
public abstract class SingleThreadExecutorRoom
<
P extends RoomPlayer<P, R, H>,
R extends Room<P, R, H>, 
H extends House<P, R, H>
>
extends Room<P, R, H>{	
	
	protected ITaskExecutor executor;

	public SingleThreadExecutorRoom(ITaskExecutor singleThreadExecutor) {
		this.executor = singleThreadExecutor;
	}
	
	public void addOperaction(IRoomOperaction oper) {
		executor.submitTask(oper);
	}
	
	
}

