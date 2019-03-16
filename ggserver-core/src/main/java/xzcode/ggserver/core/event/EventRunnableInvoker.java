package xzcode.ggserver.core.event;

import java.util.ArrayList;
import java.util.List;

/**
 * 可执行事件调用
 * 
 * @author zai
 * 2019-01-02 19:49:46
 */
public class EventRunnableInvoker implements IEventInvoker {
	
	private String eventTag;
	
	private List<Runnable> runnables = new ArrayList<>(1);
	
	@Override
	public void invoke() throws Exception{
		for (Runnable runnable : runnables) {
			runnable.run();
		}
	}
	

	@Override
	public void invoke(Object message) throws Exception {
		// do nothing
		
	}
	

	public String getEventTag() {
		return eventTag;
	}

	public EventRunnableInvoker setEventTag(String eventTag) {
		this.eventTag = eventTag;
		return this;
	}
	
	public void addRunnable(Runnable runnable) {
		this.runnables.add(runnable);
		((ArrayList<Runnable>)this.runnables).trimToSize();;
	}
	public void setRunnables(List<Runnable> runnables) {
		this.runnables = runnables;
	}
	
	public List<Runnable> getRunnables() {
		return runnables;
	}



	
}

