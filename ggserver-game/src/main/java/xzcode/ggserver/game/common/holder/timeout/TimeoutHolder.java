package xzcode.ggserver.game.common.holder.timeout;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import xzcode.ggserver.core.GGServer;
import xzcode.ggserver.core.executor.task.TimeoutRunnable;
import xzcode.ggserver.game.common.interfaces.timeout.TimeoutAction;

/**
 * 离开房间超时等待模型
 * 
 * @author zai
 * 2019-01-21 11:59:37
 */
public class TimeoutHolder implements TimeoutRunnable{
	
	
	
	protected TimeoutAction timeoutAction;
	
	
	/**
	 * 计时器未来对象
	 */
	protected ScheduledFuture<?> timeoutFuture;
	
	/**
	 * 同步锁
	 */
	protected Object syncLock;	
	
	/**
	 * ggserver对象
	 */
	protected GGServer gg;
	
	protected long timeoutMs;
	
	
	public static TimeoutHolder create(GGServer gg, TimeoutAction timeoutAction, long timeoutMs) {
		TimeoutHolder timeoutHolder = new TimeoutHolder();
		timeoutHolder.onTimeout(timeoutAction);
		timeoutHolder.setGg(gg);
		timeoutHolder.setTimeoutMs(timeoutMs);
		return timeoutHolder;
	}
	public static TimeoutHolder create(GGServer gg, long timeoutMs, Object syncLock, TimeoutAction timeoutAction) {
		TimeoutHolder timeoutHolder = new TimeoutHolder();
		timeoutHolder.onTimeout(timeoutAction);
		timeoutHolder.setGg(gg);
		timeoutHolder.setTimeoutMs(timeoutMs);
		timeoutHolder.setSyncLock(syncLock);
		return timeoutHolder;
	}
	public static TimeoutHolder create(GGServer gg, long timeoutMs, TimeoutAction timeoutAction) {
		TimeoutHolder timeoutHolder = new TimeoutHolder();
		timeoutHolder.onTimeout(timeoutAction);
		timeoutHolder.setGg(gg);
		timeoutHolder.setTimeoutMs(timeoutMs);
		return timeoutHolder;
	}
	
	/**
	 * 开始任务
	 * 
	 * @author zai
	 * 2019-04-22 18:06:06
	 */
	public TimeoutHolder startTask() {
		if (this.syncLock != null) {
			gg.schedule(this.syncLock, this, timeoutMs);
			return this;
		}
		gg.schedule(this, timeoutMs);
		return this;
	}
	
	/**
	 * 是否已运行过
	 * @return
	 * 
	 * @author zai
	 * 2019-02-23 15:29:36
	 */
	public boolean hasRan() {
		return timeoutFuture != null;
	}
	
	
	/**
	 * 检查是否在指定时间内将会超时
	 * @param millisecs
	 * @return
	 * 
	 * @author zai
	 * 2019-02-23 14:37:34
	 */
	public boolean isTimeoutLessThan(long millisecs) {
		if (this.timeoutFuture != null) {
			return this.timeoutFuture.getDelay(TimeUnit.MILLISECONDS) <= millisecs && this.timeoutFuture.getDelay(TimeUnit.MILLISECONDS) > 0;			
		}
		return true;
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
			return this.timeoutFuture.getDelay(TimeUnit.MILLISECONDS) <= 0 ;			
		}
		return true;
	}
	
	/**
	 * 初始化超时信息
	 * 
	 * @param timeoutFuture
	 * @param timeout
	 * @author zai
	 * 2019-01-25 14:42:18
	 */
	public void initTimeout(ScheduledFuture<?> timeoutFuture) {
		this.cancelTask();
		this.timeoutFuture = timeoutFuture;
	}


	public TimeoutHolder(ScheduledFuture<?> timeoutFuture) {
		initTimeout(timeoutFuture);
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
	 * 取消任务
	 * 
	 * @return 是否取消成功
	 * @author zai
	 * 2019-01-21 12:05:01
	 */
	public boolean cancelTask() {
		if (this.timeoutFuture == null) {
			return true;
		}
		ScheduledFuture<?> future = this.timeoutFuture;
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
		return this.timeoutFuture.getDelay(TimeUnit.MILLISECONDS);
	}
	
	public ScheduledFuture<?> getTimeoutFuture() {
		return timeoutFuture;
	}
	public void setTimeoutFuture(ScheduledFuture<?> timeoutFuture) {
		this.timeoutFuture = timeoutFuture;
	}
	
	public long getTimeoutMs() {
		return timeoutMs;
	}
	
	public void setTimeoutMs(long timeoutMs) {
		this.timeoutMs = timeoutMs;
	}
	
	public void setGg(GGServer gg) {
		this.gg = gg;
	}
	
	public GGServer getGg() {
		return gg;
	}
	public Object getSyncLock() {
		return syncLock;
	}
	public void setSyncLock(Object syncLock) {
		this.syncLock = syncLock;
	}
	public void setTimeoutAction(TimeoutAction timeoutAction) {
		this.timeoutAction = timeoutAction;
	}
	
	
	
}
