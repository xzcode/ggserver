package xzcode.ggserver.game.support.schedule;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import xzcode.ggserver.core.GGServer;

/**
 * 计划任务暂存器
 * 
 * @author zzz
 * 2019-09-02 18:17:35
 */
public class ScheduleTaskHolder{
	
	
	
	protected Runnable taskAction;
	
	
	/**
	 * 计时器未来对象
	 */
	protected ScheduledFuture<?> future;
	
	/**
	 * ggserver对象
	 */
	protected GGServer gg;
	
	/**
	 * 初始化延迟
	 */
	protected long initialDelay;
	
	/**
	 * 每次执行延迟
	 */
	protected long delay;
	
	/**
	 * 时间单位
	 */
	protected TimeUnit timeUnit;
	
	
	public static ScheduleTaskHolder create(GGServer gg, long initialDelay, long delay, Runnable taskAction) {
		ScheduleTaskHolder taskHolder = new ScheduleTaskHolder();
		taskHolder.setInitialDelay(initialDelay);
		taskHolder.setDelay(delay);
		taskHolder.setTaskAction(taskAction);
		taskHolder.setGg(gg);
		taskHolder.setTimeUnit(TimeUnit.MICROSECONDS);
		return taskHolder;
		
	}
	
	public static ScheduleTaskHolder create(GGServer gg, long initialDelay, long delay, TimeUnit timeUnit, Runnable taskAction) {
		ScheduleTaskHolder taskHolder = new ScheduleTaskHolder();
		taskHolder.setInitialDelay(initialDelay);
		taskHolder.setDelay(delay);
		taskHolder.setTaskAction(taskAction);
		taskHolder.setTimeUnit(timeUnit);
		taskHolder.setGg(gg);
		return taskHolder;
		
	}
	
	/**
	 * 开始任务
	 * 
	 * @author zai
	 * 2019-04-22 18:06:06
	 */
	public ScheduleTaskHolder startTask() {
		this.future = gg.scheduleWithFixedDelay(initialDelay, delay, taskAction, timeUnit).getScheduledFuture();
		return this;
	}
	
	
	/**
	 * 取消任务(非强制)
	 * 
	 * @return
	 * @author zai
	 * 2019-09-02 19:00:32
	 */
	public ScheduleTaskHolder cancleTask() {
		if (this.future != null) {
			this.future.cancel(false);
			this.future = null;
		}
		return this;
	}
	
	/**
	 * 是否已运行过
	 * @return
	 * 
	 * @author zai
	 * 2019-02-23 15:29:36
	 */
	public boolean isRunning() {
		return this.future != null;
	}
	
	
	
	public Runnable getTaskAction() {
		return taskAction;
	}
	public void setTaskAction(Runnable taskAction) {
		this.taskAction = taskAction;
	}
	public GGServer getGg() {
		return gg;
	}
	public void setGg(GGServer gg) {
		this.gg = gg;
	}
	public long getInitialDelay() {
		return initialDelay;
	}
	public void setInitialDelay(long initialDelay) {
		this.initialDelay = initialDelay;
	}
	public long getDelay() {
		return delay;
	}
	public void setDelay(long delay) {
		this.delay = delay;
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}
	
	

	
}
