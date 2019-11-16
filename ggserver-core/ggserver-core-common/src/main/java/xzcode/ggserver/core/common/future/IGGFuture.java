package xzcode.ggserver.core.common.future;

import io.netty.util.concurrent.Future;

public interface IGGFuture {

	void setNettyFuture(Future<?> nettyFuture);

	Future<?> getNettyFuture();

	void onComplete(Runnable completeAction);

	boolean isCompleted();
	
	boolean cancel();

	boolean cancel(boolean mayInterruptIfRunning);

}