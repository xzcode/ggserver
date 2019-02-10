package xzcode.ggserver.game.common.holder.timeout;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import xzcode.ggserver.core.executor.task.TimeoutRunnable;
import xzcode.ggserver.game.common.interfaces.timeout.TimeoutAction;

/**
 * 离开房间超时等待模型
 * 
 * @author zai
 * 2019-01-21 11:59:37
 */
public abstract class TimeoutHolder implements TimeoutRunnable{
	
	protected TimeoutAction timeoutAction;
	
	
	/**
	 * 计时器未来对象
	 */
	protected ScheduledFuture<?> timeoutFuture;
	
	
	/**
	 * 初始化超时信息
	 * 
	 * @param timeoutFuture
	 * @param timeout
	 * @author zai
	 * 2019-01-25 14:42:18
	 */
	public void initTimeout(ScheduledFuture<?> timeoutFuture) {
		this.timeoutFuture = timeoutFuture;
	}
	



	public TimeoutHolder(ScheduledFuture<?> timeoutFuture) {
		super();
		this.timeoutFuture = timeoutFuture;
	}


	public TimeoutHolder() {
		super();
	}
	
	public TimeoutAction getTimeoutAction() {
		return timeoutAction;
	}
	
	@Override
	public void run() {
		this.getTimeoutAction().run();
	}


	/**
	 * 注册超时行为
	 * 
	 * @param timeoutAction
	 * @author zai
	 * 2019-01-29 11:20:36
	 */
	public void onTimeout(TimeoutAction timeoutAction) {
		this.timeoutAction = timeoutAction;
		
	}


	/**
	 * 检查是否超时
	 * 
	 * @return
	 * @author zai
	 * 2019-01-28 12:00:04
	 */
	public boolean checkTimeout() {
		return this.timeoutFuture.getDelay(TimeUnit.MILLISECONDS) <= 0;
	}



	/**
	 * 取消任务
	 * 
	 * @return 是否取消成功
	 * @author zai
	 * 2019-01-21 12:05:01
	 */
	public boolean cancelTask() {
		if (this.timeoutFuture != null) {
			return this.timeoutFuture.cancel(false);			
		}
		return true;
	}
	
	/**
	 * 获取剩余时间
	 * 
	 * @return
	 * @author zai
	 * 2019-01-21 13:20:23
	 */
	public long getRemainTime() {
		return this.timeoutFuture.getDelay(TimeUnit.MILLISECONDS);
	}
	
	public ScheduledFuture<?> getTimeoutFuture() {
		return timeoutFuture;
	}
	public void setTimeoutFuture(ScheduledFuture<?> timeoutFuture) {
		this.timeoutFuture = timeoutFuture;
	}
	
}
