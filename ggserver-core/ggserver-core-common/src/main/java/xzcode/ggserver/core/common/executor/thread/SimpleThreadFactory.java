package xzcode.ggserver.core.common.executor.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleThreadFactory implements ThreadFactory {
    private final String threadNamePrefix;
    private static final AtomicInteger threadIdx = new AtomicInteger();
    private final boolean daemon;

    public SimpleThreadFactory(final String threadNamePrefix, final boolean daemon) {
        this.threadNamePrefix = threadNamePrefix;
        this.daemon = daemon;
    }

    @Override
    public Thread newThread(final Runnable r) {
        final Thread t = new Thread(r, threadNamePrefix + threadIdx.getAndIncrement());
        t.setDaemon(daemon);
        return t;
    }
}
