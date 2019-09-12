package xzcode.ggserver.core.executor.future;

import java.util.concurrent.ScheduledFuture;

import xzcode.ggserver.core.GGServer;

/**
 * 
 * @author zzz
 * 2019-09-09 18:06:25
 */
public class GGTaskFuture {
	
	private ScheduledFuture<?> scheduledFuture;
	
	private boolean completed;
	
	private Runnable completeAction;
	
	
	public GGTaskFuture() {
	}
	

	public GGTaskFuture(ScheduledFuture<?> scheduledFuture) {
		this.scheduledFuture = scheduledFuture;
	}
	
	
	public GGTaskFuture(GGServer gg, ScheduledFuture<?> scheduledFuture) {
		this.scheduledFuture = scheduledFuture;
	}


	/**
	 * 当任务执行完毕执行
	 * 
	 * @param run
	 * @author zzz
	 * 2019-09-09 18:06:38
	 */
	public void onComplete(Runnable completeAction) {
		
			synchronized (this) {
				this.completeAction = completeAction;
				if (completed) {
					completeAction.run();
				}
			}
	}
	
	
	/**
	 * 设置完成状态
	 * 
	 * @param completed
	 * @author zzz
	 * 2019-09-10 10:45:20
	 */
	public void setCompleted(boolean completed) {
		synchronized (this) {
			this.completed = completed;
			if (this.completed && this.completeAction != null) {
				completeAction.run();
			}
		}
	}
	

	public ScheduledFuture<?> getScheduledFuture() {
		return scheduledFuture;
	}

	public void setScheduledFuture(ScheduledFuture<?> scheduledFuture) {
		this.scheduledFuture = scheduledFuture;
	}

	public Runnable getCompleteAction() {
		return completeAction;
	}
	
	public void setCompleteAction(Runnable completeAction) {
		this.completeAction = completeAction;
	}
	public boolean isCompleted() {
		return completed;
	}


}
