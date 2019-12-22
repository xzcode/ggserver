package xzcode.ggserver.game.card.games.room;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.game.card.games.player.RoomPlayer;

/**
 * 游戏房间
 * 
 * 
 * @author zai 2018-05-24
 */
public abstract class OperQueuedRoom<P extends RoomPlayer<R, H>,R, H> extends Room<P, R, H>{	
	
	/**
	 * 操作队列
	 */
	protected Queue<Runnable> operactionsQueue = new ConcurrentLinkedDeque<>();
	
	
	
	protected IGGFuture operactionTaskFuture;
	
	/**
	 * 添加操作到队列
	 * @param oper
	 * 
	 * @author zai
	 * 2019-12-01 11:54:46
	 */
	public void addOperactionToQueue(Runnable oper) {
		this.operactionsQueue.add(oper);
	}
	
	protected void startOperactionTask() {
		
		operactionTaskFuture = server.scheduleWithFixedDelay(1, 100, TimeUnit.MILLISECONDS, () -> {
			Runnable oper = operactionsQueue.poll();
			if (oper != null) {
				oper.run();
			}
		});
	}
	
}

