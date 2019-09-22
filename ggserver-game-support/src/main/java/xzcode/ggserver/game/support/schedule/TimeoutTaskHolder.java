package xzcode.ggserver.game.support.schedule;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import xzcode.ggserver.core.GGServer;
import xzcode.ggserver.core.executor.future.GGTaskFuture;

/**
 * 超时等待模型
 * 
 * @author zai
 * 2019-01-21 11:59:37
 */
public class TimeoutTaskHolder{
	
	
	
	protected Runnable timeoutAction;
	
	
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
	
	protected long startTimeMs;
	
	
	public static TimeoutTaskHolder create(GGServer gg, Runnable timeoutAction, long timeoutMs) {
		TimeoutTaskHolder timeoutHolder = new TimeoutTaskHolder();
		timeoutHolder.onTimeout(timeoutAction);
		timeoutHolder.setGg(gg);
		timeoutHolder.setTimeoutMs(timeoutMs);
		return timeoutHolder;
	}
	public static TimeoutTaskHolder create(GGServer gg, long timeoutMs, Object syncLock, Runnable timeoutAction) {
		TimeoutTaskHolder timeoutHolder = new TimeoutTaskHolder();
		timeoutHolder.onTimeout(timeoutAction);
		timeoutHolder.setGg(gg);
		timeoutHolder.setTimeoutMs(timeoutMs);
		timeoutHolder.setSyncLock(syncLock);
		return timeoutHolder;
	}
	public static TimeoutTaskHolder create(GGServer gg, long timeoutMs, Runnable timeoutAction) {
		TimeoutTaskHolder timeoutHolder = new TimeoutTaskHolder();
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
	public TimeoutTaskHolder startTask() {
		GGTaskFuture taskFuture;
		if (this.syncLock != null) {
			taskFuture = gg.schedule(this.syncLock, timeoutMs, timeoutAction);
		}else {
			taskFuture = gg.schedule(timeoutMs, timeoutAction);
		}
		this.timeoutFuture = taskFuture.getScheduledFuture();
		this.startTimeMs = System.currentTimeMillis();
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


	public TimeoutTaskHolder(ScheduledFuture<?> timeoutFuture) {
		initTimeout(timeoutFuture);
	}


	public TimeoutTaskHolder() {
		super();
	}
	
	public Runnable getTimeoutAction() {
		return timeoutAction;
	}
	


	/**
	 * 注册超时行为
	 * 
	 * @param timeoutAction
	 * @author zai
	 * 2019-01-29 11:20:36
	 */
	public void onTimeout(Runnable timeoutAction) {
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
		if (this.timeoutFuture == null) {
			return 0;
		}
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
	public void setTimeoutAction(Runnable timeoutAction) {
		this.timeoutAction = timeoutAction;
	}
	public long getStartTimeMs() {
		return startTimeMs;
	}
	public void setStartTimeMs(long startTimeMs) {
		this.startTimeMs = startTimeMs;
	}
	
	
	
}
