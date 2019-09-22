package xzcode.ggserver.game.support.player.support;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import xzcode.ggserver.core.executor.future.GGTaskFuture;
import xzcode.ggserver.game.support.interfaces.IGGServerSupport;
import xzcode.ggserver.game.support.player.Player;

/**
 * 玩家支持接口
 * 
 * @author zzz
 * 2019-09-22 10:23:19
 */
public interface IPlayerSchduleTaskSupport<P extends Player> extends IGGServerSupport{
	
	/**
	 * 获取计划任务集合
	 * 
	 * @return
	 * @author zzz
	 * 2019-09-22 11:42:55
	 */
	Map<Object, GGTaskFuture> getScheduleTaskFutures();
	
	/**
	 * 执行计划任务
	 * 
	 * @param taskKey 任务key
	 * @param syncLock 同步锁，非必要可以填写null
	 * @param delayMs 时间
	 * @param timeUnit 时间单位
	 * @param runnable 任务对象
	 * @author zzz
	 * 2019-09-22 11:48:29
	 */
	default void schedule(Object taskKey,Object syncLock, long delayMs, TimeUnit timeUnit, Runnable runnable) {
		GGTaskFuture taskFuture = getGGServer().schedule(syncLock, delayMs, timeUnit, runnable);
		getScheduleTaskFutures().put(taskKey, taskFuture);
		taskFuture.getScheduledFuture().addListener((e) -> {
			getScheduleTaskFutures().remove(taskKey);
		});
	}
	
	/**
	 * 执行计划任务
	 * 
	 * @param taskKey 任务key
	 * @param syncLock 同步锁，非必要可以填写null
	 * @param delayMs 时间，毫秒 ms
	 * @param runnable 任务对象
	 * @author zzz
	 * 2019-09-22 11:48:29
	 */
	default void schedule(Object taskKey,Object syncLock, long delayMs, Runnable runnable) {
		schedule(taskKey, syncLock, delayMs, TimeUnit.MILLISECONDS, runnable);
	}
	
	/**
	 * 执行计划任务
	 * 
	 * @param taskKey 任务key
	 * @param delayMs 时间，毫秒 ms
	 * @param runnable 任务对象
	 * @author zzz
	 * 2019-09-22 11:48:29
	 */
	default void schedule(Object taskKey, long delayMs, Runnable runnable) {
		schedule(taskKey, null, delayMs, TimeUnit.MILLISECONDS, runnable);
	}
	
	default void cancelScheduleTask(Object taskKey) {
		GGTaskFuture taskFuture = getScheduleTaskFutures().get(taskKey);
		if (taskFuture != null) {
			taskFuture.getScheduledFuture().cancel(false);
		}
	}
	
}
