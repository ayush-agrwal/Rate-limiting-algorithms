package com.ratelimitor;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TokenBucketRateLimiter {
    private final int capacity;
    private final int refillRate;
    private final AtomicInteger tokens;
    private final ScheduledExecutorService scheduler;

    public TokenBucketRateLimiter(int capacity, int refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate;
        this.tokens = new AtomicInteger(capacity);
        this.scheduler = Executors.newScheduledThreadPool(1);

       
        scheduler.scheduleAtFixedRate(() -> {
            int newTokens = Math.min(capacity, tokens.get() + refillRate);
            tokens.set(newTokens);
        }, 0, 1, TimeUnit.SECONDS);
    }

    public boolean allowRequest() {
        return tokens.getAndUpdate(current -> current > 0 ? current - 1 : 0) > 0;
    }
    
    
    
}
