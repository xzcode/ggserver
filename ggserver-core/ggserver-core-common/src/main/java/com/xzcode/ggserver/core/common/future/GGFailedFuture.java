package com.xzcode.ggserver.core.common.future;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.xzcode.ggserver.core.common.session.GGSession;
import com.xzcode.ggserver.core.common.utils.logger.GGLoggerUtil;


/**
 * 默认失败future
 * @param 
 * 
 * @author zai
 * 2019-12-01 16:28:44
 */
public class GGFailedFuture implements IGGFuture {
	
	public static final GGFailedFuture DEFAULT_FAILED_FUTURE = new GGFailedFuture();
	
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
	public Object get() throws InterruptedException, ExecutionException {
		return null;
	}

	@Override
	public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return null;
	}

	@Override
	public void addListener(IGGFutureListener<IGGFuture> listener) {
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

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Class<T> clazz) {
		try {
			return (T) get();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean isSuccess() {
		return false;
	}

	@Override
	public GGSession getSession() {
		return null;
	}


}
