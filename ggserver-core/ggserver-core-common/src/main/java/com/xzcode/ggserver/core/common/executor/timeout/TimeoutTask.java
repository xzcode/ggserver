package com.xzcode.ggserver.core.common.executor.timeout;

import com.xzcode.ggserver.core.common.executor.TaskExecutor;
import com.xzcode.ggserver.core.common.future.IGGFuture;

/**
 * 超时等待模型
 * 
 * @author zai
 * 2019-01-21 11:59:37
 */
public class TimeoutTask{
	

	/**
	 * 任务执行器
	 */
	protected TaskExecutor taskExecutor;
	
	
	
	/**
	 * 计时器未来对象
	 */
	protected IGGFuture timeoutFuture;
	
	/**
	 * 超时延迟 ms
	 */
	protected long timeoutDelay;
	
	/**
	 * 任务开始时间ms
	 */
	protected long startTime;
	
	/**
	 * 任务结束事件ms
	 */
	protected long endTime;
	
	
	/**
	 * 任务对象
	 */
	protected Runnable timeoutAction;
	

	
	/**
	 * 默认构造器
	 * @param taskExecutor 任务执行器
	 * @param timeoutDelay 超时延迟ms
	 * @param timeoutAction 任务对象
	 */
	public TimeoutTask(TaskExecutor taskExecutor, long timeoutDelay, Runnable timeoutAction) {
		this.taskExecutor = taskExecutor;
		this.timeoutDelay = timeoutDelay;
		this.timeoutAction = timeoutAction;
	}

	/**
	 * 开始任务
	 * 
	 * @author zai
	 * 2019-04-22 18:06:06
	 */
	public TimeoutTask start() {
		if (timeoutAction == null) {
			return this;
		}
		timeoutFuture = taskExecutor.schedule(timeoutDelay, timeoutAction);
		this.startTime = System.currentTimeMillis();
		this.endTime = this.startTime + timeoutDelay;
		return this;
	}
	
	/**
	 * 是否已超时
	 * @return
	 * 
	 * @author zai
	 * 2019-02-23 15:13:24
	 */
	public boolean isTimeout() {
		if (this.timeoutFuture != null) {
			return this.timeoutFuture.isDone();			
		}
		return true;
	}

	
	public Runnable getTimeoutAction() {
		return timeoutAction;
	}
	
	
	public IGGFuture getTimeoutFuture() {
		return timeoutFuture;
	}
	
	/**
	 * 取消任务
	 * 
	 * @return 是否取消成功
	 * @author zai
	 * 2019-01-21 12:05:01
	 */
	public boolean cancel() {
		if (this.timeoutFuture == null) {
			return true;
		}
		IGGFuture future = this.timeoutFuture;
		this.timeoutFuture = null;
		return future.cancel(false);			
	}
	
	/**
	 * 获取剩余时间
	 * 
	 * @return 返回剩余时间（毫秒）
	 * @author zai
	 * 2019-01-21 13:20:23
	 */
	public long getRemainTime() {
		if (this.timeoutFuture == null) {
			return 0;
		}
		long remain =  this.endTime - System.currentTimeMillis();
		if (remain < 0) {
			remain = 0L;
		}
		return remain;
	}
	
	public long getTimeoutDelay() {
		return timeoutDelay;
	}
	
}
