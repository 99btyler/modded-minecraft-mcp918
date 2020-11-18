package mmmcp.util;

import java.util.Random;

public class Timer {

    private double lastTime;

    private final Random random;
    private int currentDelay;

    private final int min;
    private final int max;

    public Timer(int min, int max) {

        // Set lastTime
        reset();

        random = new Random();
        currentDelay = 0;
        // Because currentDelay is 0, the first hasReached() will be instantaneous

        this.min = min;
        this.max = max;

    }

    public final boolean hasReached() {

        if ((getCurrentTime() - lastTime) >= currentDelay) {

            // New lastTime
            reset();

            // New random currentDelay (between min and max)
            currentDelay = (random.nextInt(max - min + 1) + min);

            // Confirm hasReached()
            return true;
            // Timer automatically starts using the new lastTime and currentDelay

        } else {
            return false;
        }

    }

    private void reset() {
        lastTime = getCurrentTime();
    }

    private double getCurrentTime() {
        return (System.nanoTime() / 1000000);
    }

}
