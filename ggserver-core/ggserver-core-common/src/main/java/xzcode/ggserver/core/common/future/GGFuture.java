package xzcode.ggserver.core.common.future;


import io.netty.util.concurrent.Future;

/**
 * 
 * 
 * @author zai
 * 2019-10-22 16:35:52
 */
public class GGFuture implements IGGFuture {
	
	private Future<?> nettyFuture;
	
	private boolean completed;
	
	private Runnable completeAction;
	
	public GGFuture() {
	}
	
	public GGFuture(Future<?> nettyFuture) {
		this.setNettyFuture(nettyFuture);
	}

	@Override
	public void setNettyFuture(Future<?> nettyFuture) {
		if (this.nettyFuture != null) {
			return;
		}
		this.nettyFuture = nettyFuture;
		nettyFuture.addListener((e) -> {
			synchronized (this) {
				this.completed = true;
				if (this.completeAction != null) {
					this.completeAction.run();					
				}
			}
			
		});
		
	}

	@Override
	public void onComplete(Runnable completeAction) {
		

		if (!this.completed) {
			synchronized (this) {
				if (!this.completed) {
					this.completeAction = completeAction;					
				}
			}
			return;
		}
		
		if (this.completeAction == null) {
			synchronized (this) {
				if (this.completeAction == null) {
					this.completeAction = completeAction;
					completeAction.run();						
				}
			}
		}
		
	}


	@Override
	public boolean isCompleted() {
		return completed;
	}

	@Override
	public Future<?> getNettyFuture() {
		return this.nettyFuture;
	}



}
