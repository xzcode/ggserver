package xzcode.ggserver.game.core.player.support;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.game.core.interfaces.IGGServerSupport;
import xzcode.ggserver.game.core.player.Player;

/**
 * 玩家计划任务支持接口
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
	Map<Object, IGGFuture> getScheduleTaskFutures();
	
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
	default void schedule(Object taskKey, long delayMs, TimeUnit timeUnit, Runnable runnable) {
		IGGFuture taskFuture = getGGServer().schedule(delayMs, timeUnit, runnable);
		getScheduleTaskFutures().put(taskKey, taskFuture);
		taskFuture.addListener((e) -> {
			getScheduleTaskFutures().remove(taskKey);
		});
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
		schedule(taskKey, delayMs, TimeUnit.MILLISECONDS, runnable);
	}
	
	/**
	 * 取消计划任务
	 * @param taskKey
	 * 
	 * @author zai
	 * 2019-11-30 12:37:27
	 */
	default void cancelScheduleTask(Object taskKey) {
		IGGFuture taskFuture = getScheduleTaskFutures().get(taskKey);
		if (taskFuture != null) {
			taskFuture.cancel(false);
		}
	}
	
}
