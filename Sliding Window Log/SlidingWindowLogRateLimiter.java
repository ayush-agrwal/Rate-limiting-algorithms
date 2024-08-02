public class SlidingWindowLogRateLimiter {
    private final int limit;
    private final long windowSizeInMillis;
    private final Queue<Long> requests;

    public SlidingWindowLogRateLimiter(int limit, long windowSizeInMillis) {
        this.limit = limit;
        this.windowSizeInMillis = windowSizeInMillis;
        this.requests = new LinkedList<>();
    }

    public synchronized boolean allowRequest() {
        long now = System.currentTimeMillis();
        while (!requests.isEmpty() && now - requests.peek() > windowSizeInMillis) {
            requests.poll();
        }

        if (requests.size() < limit) {
            requests.add(now);
            return true;
        }
        return false;
    }
}
