package xzcode.ggserver.core.common.future;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import xzcode.ggserver.core.common.utils.logger.GGLoggerUtil;


/**
 * 基于Netty Future进行外观的实现
 * 
 * @author zai
 * 2019-11-24 17:54:50
 */
public class GGNettyFacadeFuture implements IGGFuture {
	
	private io.netty.util.concurrent.Future<?> nettyFuture;
	
	private List<IGGFutureListener<IGGFuture>> listeners;
	
	
	public GGNettyFacadeFuture() {
		listeners = new ArrayList<>(2);
	}
	
	public GGNettyFacadeFuture(Future<?> nettyFuture) {
		this.setFuture(nettyFuture);
	}

	public void setFuture(Future<?> future) {
		if (this.nettyFuture != null) {
			return;
		}
		this.nettyFuture = (io.netty.util.concurrent.Future<?>) future;
		if (listeners != null && listeners.size() > 0) {
			for (IGGFutureListener<IGGFuture> listener : listeners) {
				nettyFuture.addListener((f) -> {
					listener.operationComplete(this);
				});
			}
		}
	}

	@Override
	public void addListener(IGGFutureListener<IGGFuture> listener) {
			
		try {
			if (nettyFuture == null) {
				listeners.add(listener);
				return;
			}
			nettyFuture.addListener((f) -> {
				listener.operationComplete(this);
			});
		} catch (Exception e) {
			GGLoggerUtil.getLogger().error("IGGFuture 'operationComplete' Error!", e);
		}
		
	}


	@Override
	public boolean isDone() {
		return this.nettyFuture.isDone();
	}

	@Override
	public boolean cancel() {
		if (this.nettyFuture != null) {
			return this.nettyFuture.cancel(false);
		}
		return true;
	}
	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		if (this.nettyFuture != null) {
			return this.nettyFuture.cancel(mayInterruptIfRunning);
		}
		return true;
	}

	@Override
	public boolean isCancelled() {
		return this.nettyFuture.isCancelled();
	}

	@Override
	public Object get() throws InterruptedException, ExecutionException {
		return this.nettyFuture.get();
	}

	@Override
	public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return this.nettyFuture.get(timeout, unit);
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




}
