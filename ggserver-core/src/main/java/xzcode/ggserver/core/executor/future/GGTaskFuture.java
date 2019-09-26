package xzcode.ggserver.core.executor.future;


import io.netty.util.concurrent.ScheduledFuture;
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
		setScheduledFuture(scheduledFuture);
	}
	

	public void setScheduledFuture(ScheduledFuture<?> scheduledFuture) {
		this.scheduledFuture = scheduledFuture;
		scheduledFuture.addListener((e) -> {
			if (this.completeAction != null) {
				this.completeAction.run();
			}
		});
	}
	
	
	public GGTaskFuture(GGServer gg, ScheduledFuture<?> scheduledFuture) {
		this.scheduledFuture = scheduledFuture;
	}
	

	public ScheduledFuture<?> getScheduledFuture() {
		return scheduledFuture;
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
