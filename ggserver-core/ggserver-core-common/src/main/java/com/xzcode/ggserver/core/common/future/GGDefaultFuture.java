package com.xzcode.ggserver.core.common.future;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.xzcode.ggserver.core.common.session.GGSession;
import com.xzcode.ggserver.core.common.utils.logger.GGLoggerUtil;

/**
 * 默认future
 *
 * @author zai 2020-04-07 14:26:57
 */
public class GGDefaultFuture implements IGGFuture {

	private Set<IGGFutureListener<IGGFuture>> listeners = new LinkedHashSet<>(2);

	private boolean success;

	private boolean done;

	private boolean cancel;
	
	private GGSession session;

	@Override
	public void addListener(IGGFutureListener<IGGFuture> listener) {
		synchronized (this) {
			if (this.done) {
				triggerListener(listener);
			} else {
				listeners.add(listener);
			}
		}

	}

	@Override
	public boolean isSuccess() {
		return this.success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public boolean isDone() {
		return this.done;
	}

	@Override
	public boolean cancel() {
		return false;
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return false;
	}

	@Override
	public boolean isCancelled() {
		return this.cancel;
	}

	@Override
	public Object get() throws InterruptedException, ExecutionException {
		return null;
	}

	@Override
	public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return null;
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
	public GGSession getSession() {
		return session;
	}

	public void setSession(GGSession session) {
		this.session = session;
	}
	
	public void setDone(boolean done) {
		if (this.done) {
			return;
		}
		this.done = done;
		if (this.done && this.listeners.size() > 0) {
			synchronized (this) {
				triggerListeners();
			}
		}
	}

	private void triggerListeners() {
		for (IGGFutureListener<IGGFuture> listener : listeners) {
			triggerListener(listener);
		}
	}

	private void triggerListener(IGGFutureListener<IGGFuture> listener) {
		try {
			listener.operationComplete(this);
		} catch (Exception e) {
			GGLoggerUtil.getLogger(this).error(e.getMessage(), e);
		}
	}

}
