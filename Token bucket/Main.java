package com.ratelimitor;

public class Main {
    public static void main(String[] args) {
        TokenBucketRateLimiter rateLimiter = new TokenBucketRateLimiter(10, 1);

        Runnable requestTask = () -> {
            if (rateLimiter.allowRequest()) {
                System.out.println("Request allowed at " + System.currentTimeMillis());
            } else {
                System.out.println("Request denied at " + System.currentTimeMillis());
            }
        };

          for (int i = 0; i < 20; i++) {
            new Thread(requestTask).start();
            try {
                // Add a short delay between thread starts
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
          try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
