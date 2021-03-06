package mmmcp.util;

import java.util.Random;

public class Timer {

    private double lastTime;

    private final int minDelay;
    private final int maxDelay;

    private final Random random;
    private int currentDelay;

    public Timer(int minDelay, int maxDelay) {

        reset();

        this.minDelay = minDelay;
        this.maxDelay = maxDelay;

        random = new Random();
        currentDelay = 0;

    }

    private void reset() {
        lastTime = getCurrentTime();
    }

    public final boolean hasReached() {

        if ((getCurrentTime() - lastTime) >= currentDelay) {

            reset();

            currentDelay = (random.nextInt(maxDelay - minDelay + 1) + minDelay);

            return true;

        } else {
            return false;
        }

    }

    private double getCurrentTime() {
        return (System.nanoTime() / 1000000);
    }

}
