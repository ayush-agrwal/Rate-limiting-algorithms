public class Main {
    public static void main(String[] args) {
     
      
      SlidingWindowLogRateLimiter rateLimiter = new SlidingWindowLogRateLimiter(5, 10000);

       
        Runnable requestTask = () -> {
            if (rateLimiter.allowRequest()) {
                System.out.println("Request allowed at " + System.currentTimeMillis());
            } else {
                System.out.println("Request denied at " + System.currentTimeMillis());
            }
        };

        
        for (int i = 0; i < 10; i++) {
            requestTask.run();
            try {
               
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        
        for (int i = 0; i < 10; i++) {
            requestTask.run();
            try {
                // Add a short delay between requests
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
