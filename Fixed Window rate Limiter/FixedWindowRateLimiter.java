package com.ratelimitor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class FixedWindowRateLimiter {
    private final int limit;
    private final int windowSizeInSeconds;
    private final ConcurrentHashMap<Long, AtomicInteger> counters;

    public FixedWindowRateLimiter(int limit, int windowSizeInSeconds) {
        this.limit = limit;
        this.windowSizeInSeconds = windowSizeInSeconds;
        this.counters = new ConcurrentHashMap<>();
    }

    public boolean allowRequest() {
        long currentTime = System.currentTimeMillis() / 1000;
        long windowStart = currentTime - (currentTime % windowSizeInSeconds);

        AtomicInteger counter = counters.computeIfAbsent(windowStart, k -> new AtomicInteger(0));
        if (counter.get() < limit) {
            counter.incrementAndGet();
            return true;
        }
        return false;
    }
}
