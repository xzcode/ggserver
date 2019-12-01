package xzcode.ggserver.core.common.future;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import xzcode.ggserver.core.common.utils.logger.GGLoggerUtil;


/**
 * 默认失败future
 * @param <V>
 * 
 * @author zai
 * 2019-12-01 16:28:44
 */
public class GGFailedFuture<V> implements IGGFuture<V> {
	
	public static final GGFailedFuture<?> DEFAULT_FAILED_FUTURE = new GGFailedFuture<>();
	
	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return false;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public boolean isDone() {
		return false;
	}

	@Override
	public V get() throws InterruptedException, ExecutionException {
		return null;
	}

	@Override
	public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return null;
	}

	@Override
	public void addListener(IGGFutureListener<IGGFuture<?>> listener) {
		try {
			listener.operationComplete(DEFAULT_FAILED_FUTURE);
		} catch (Exception e) {
			GGLoggerUtil.getLogger().error("IGGFuture 'operationComplete' Error!", e);
		}
		
	}

	@Override
	public boolean cancel() {
		return false;
	}
	
	

}
