package com.xzcode.ggserver.core.common.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import com.xzcode.ggserver.core.common.executor.timeout.TimeoutTask;
import com.xzcode.ggserver.core.common.future.IGGFuture;

/**
 * 任务执行器
 * 
 * 
 * @author zai
 * 2019-12-01 15:46:06
 */
public interface TaskExecutor extends Executor{
	
	/**
	 * 直接执行任务
	 * @param runnable
	 * @return
	 * 
	 * @author zai
	 * 2019-12-01 15:55:00
	 */
	IGGFuture submitTask(Runnable runnable);
	
	
	/**
	 * 直接执行任务
	 * @param <V> 回调类型
	 * @param runnable
	 * @return
	 * 
	 * @author zai
	 * 2019-12-01 15:56:05
	 */
	<V> IGGFuture submitTask(Callable<V> callable);
	
	
	/**
	 * 执行计划任务
	 * 
	 * @param delayMs
	 * @param runnable
	 * @return
	 * @author zai
	 * 2019-12-20 19:27:05
	 */
	IGGFuture schedule(long delayMs, Runnable runnable);
	
	/**
	 * 执行计划任务
	 * @param delay 延迟时间
	 * @param timeUnit 时间单位
	 * @param runnable 
	 * @return
	 * 
	 * @author zai
	 * 2019-12-01 15:46:16
	 */
	IGGFuture schedule(long delay, TimeUnit timeUnit, Runnable runnable);
	
	/**
	 * 计划任务
	 * @param <V> callable回调类型
	 * @param delay 延迟时间
	 * @param timeUnit 时间单位
	 * @param callable
	 * @return
	 * 
	 * @author zai
	 * 2019-12-01 15:47:11
	 */
	<V> IGGFuture schedule(long delay, TimeUnit timeUnit, Callable<V> callable);
	
	/**
	 * 等待指定future执行完成后再进行计划任务的执行
	 * @param afterFuture 跟随的任务future
	 * @param delay 延迟时间
	 * @param timeUnit 时间单位
	 * @param runnable 
	 * @return
	 * 
	 * @author zai
	 * 2019-12-01 15:46:16
	 */
	IGGFuture scheduleAfter(IGGFuture afterFuture, long delay, TimeUnit timeUnit, Runnable runnable);
	
	

	/**
	 * 等待指定future执行完成后再进行计划任务的执行
	 * 
	 * @param afterFuture
	 * @param delayMs 延迟时间毫秒
	 * @param runnable
	 * @return
	 * @author zai
	 * 2019-12-25 14:52:04
	 */
	IGGFuture scheduleAfter(IGGFuture afterFuture, long delayMs, Runnable runnable);
	
	
	/**
	 * 等待指定future执行完成后再进行计划任务的执行
	 * @param <V> callable回调类型
	 * @param afterFuture 跟随的任务future
	 * @param delay 延迟时间
	 * @param timeUnit 时间单位
	 * @param runnable 
	 * @return
	 * 
	 * @author zai
	 * 2019-12-01 15:46:16
	 */
	<V> IGGFuture scheduleAfter(IGGFuture afterFuture, long delay, TimeUnit timeUnit, Callable<V> callable);

	/**
	 * 固定延迟计划任务
	 * @param initialDelay 初始化延迟
	 * @param delay 周期间隔时间
	 * @param timeUnit 时间单位
	 * @param runnable
	 * @return
	 * 
	 * @author zai
	 * 2019-12-01 15:48:27
	 */
	IGGFuture scheduleWithFixedDelay(long initialDelay, long delay, TimeUnit timeUnit, Runnable runnable);
	
	/**
	 * 创建超时任务对象
	 * 
	 * @param timeoutDelay
	 * @param timeoutAction
	 * @return
	 * @author zai
	 * 2019-12-29 21:22:50
	 */
	TimeoutTask timeoutTask(long timeoutDelay, Runnable timeoutAction);
	
	
	
	/**
	 * 获取下一个执行器
	 * 
	 * @return
	 * @author zai
	 * 2019-12-22 17:12:55
	 */
	TaskExecutor nextEvecutor();

	
}
