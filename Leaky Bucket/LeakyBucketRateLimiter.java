package com.ratelimitor;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

public class LeakyBucketRateLimiter {
    private final int capacity;
    private final int leakRate;
    private final Queue<Long> bucket;
    private final Timer timer;

    public LeakyBucketRateLimiter(int capacity, int leakRate) {
        this.capacity = capacity;
        this.leakRate = leakRate;
        this.bucket = new LinkedList<>();
        this.timer = new Timer(true);

        // Leak requests periodically
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                synchronized (bucket) {
                    if (!bucket.isEmpty()) {
                        bucket.poll();
                    }
                }
            }
        }, 0, 1000 / leakRate);
    }

    public synchronized boolean allowRequest() {
        if (bucket.size() < capacity) {
            bucket.add(System.currentTimeMillis());
            return true;
        }
        return false;
    }
}
