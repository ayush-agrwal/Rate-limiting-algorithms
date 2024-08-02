package com.ratelimitor;

public class Main {
    public static void main(String[] args) {
          LeakyBucketRateLimiter rateLimiter = new LeakyBucketRateLimiter(10, 1);

        
        Runnable requestTask = () -> {
            if (rateLimiter.allowRequest()) {
                System.out.println("Request allowed at " + System.currentTimeMillis());
            } else {
                System.out.println("Request denied at " + System.currentTimeMillis());
            }
        };

       
        for (int i = 0; i < 2000; i++) {
            new Thread(requestTask).start();
            try {
               
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
