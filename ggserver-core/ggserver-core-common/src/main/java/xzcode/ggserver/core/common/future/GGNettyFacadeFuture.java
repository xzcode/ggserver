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
 * @param <V>
 * 
 * @author zai
 * 2019-11-24 17:54:50
 */
public class GGNettyFacadeFuture<V> implements IGGFuture<V> {
	
	private io.netty.util.concurrent.Future<V> nettyFuture;
	
	
	private List<IGGFutureListener<IGGFuture<?>>> futureListeners;
	
	public GGNettyFacadeFuture() {
	}
	
	public GGNettyFacadeFuture(Future<V> nettyFuture) {
		this.setFuture(nettyFuture);
	}

	@SuppressWarnings("unchecked")
	public void setFuture(Future<?> future) {
		if (this.nettyFuture != null) {
			return;
		}
		this.nettyFuture = (io.netty.util.concurrent.Future<V>) future;
		if (this.futureListeners != null) {
			nettyFuture.addListener((f) -> {
				for (IGGFutureListener<IGGFuture<?>> listener : futureListeners) {
					listener.operationComplete(new GGNettyFacadeFuture<V>((Future<V>) f));
				}
			});
		}
	}

	@Override
	public void addListener(IGGFutureListener<IGGFuture<?>> listener) {
			
		try {
			if (this.futureListeners == null) {
				this.futureListeners = new ArrayList<>();
			}
	
			if (!this.isDone()) {
				this.futureListeners.add(listener);		
				return;
			}
			this.futureListeners.add(listener);	
			listener.operationComplete(new GGNettyFacadeFuture<V>(this.nettyFuture));
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
	public V get() throws InterruptedException, ExecutionException {
		return this.nettyFuture.get();
	}

	@Override
	public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return this.nettyFuture.get(timeout, unit);
	}




}
