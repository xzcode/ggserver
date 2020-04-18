package com.xzcode.ggserver.core.common.executor.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.xzcode.ggserver.core.common.executor.TaskExecutor;

import io.netty.util.concurrent.FastThreadLocalThread;

public class GGThreadFactory implements ThreadFactory {
    private final String threadNamePrefix;
    private final AtomicInteger THREAD_IDX = new AtomicInteger();
    private final boolean daemon;
    private TaskExecutor taskExecutor;

    public GGThreadFactory(final String threadNamePrefix, final boolean daemon) {
        this.threadNamePrefix = threadNamePrefix;
        this.daemon = daemon;
    }
    public GGThreadFactory(final String threadNamePrefix, final boolean daemon, TaskExecutor bindThreadLocalTaskExecutor) {
    	this.threadNamePrefix = threadNamePrefix;
    	this.daemon = daemon;
    	this.taskExecutor = bindThreadLocalTaskExecutor;
    	
    }

    @Override
    public Thread newThread(final Runnable r) {
        final Thread t = new FastThreadLocalThread(r, threadNamePrefix + THREAD_IDX.getAndIncrement());
        t.setDaemon(daemon);
        return t;
    }
}
