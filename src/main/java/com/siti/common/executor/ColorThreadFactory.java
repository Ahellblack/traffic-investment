package com.siti.common.executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ColorThreadFactory implements ThreadFactory {
    private AtomicInteger count = new AtomicInteger(0);
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        String threadName = ColorsExecutor.class.getSimpleName() + count.addAndGet(40);
        t.setName(threadName);
        return t;
    }
}
