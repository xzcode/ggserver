package xzcode.ggserver.game.common.holder.timeout;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import xzcode.ggserver.core.server.GGServer;
import xzcode.ggserver.game.common.interfaces.timeout.ScheduleTaskAction;

/**
 * 计划任务暂存器
 * 
 * @author zzz
 * 2019-09-02 18:17:35
 */
public class ScheduleTaskHolder{
	
	
	
	protected ScheduleTaskAction taskAction;
	
	
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
	
	
	public static ScheduleTaskHolder create(GGServer gg, long initialDelay, long delay, ScheduleTaskAction taskAction) {
		ScheduleTaskHolder taskHolder = new ScheduleTaskHolder();
		taskHolder.setInitialDelay(initialDelay);
		taskHolder.setDelay(delay);
		taskHolder.setTaskAction(taskAction);
		taskHolder.setGg(gg);
		taskHolder.setTimeUnit(TimeUnit.MICROSECONDS);
		return taskHolder;
		
	}
	
	public static ScheduleTaskHolder create(GGServer gg, long initialDelay, long delay, TimeUnit timeUnit, ScheduleTaskAction taskAction) {
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
		this.future = (ScheduledFuture<?>) gg.scheduleWithFixedDelay(initialDelay, delay, timeUnit, taskAction ).getNettyFuture();
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
	
	
	
	public ScheduleTaskAction getTaskAction() {
		return taskAction;
	}
	public void setTaskAction(ScheduleTaskAction taskAction) {
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
