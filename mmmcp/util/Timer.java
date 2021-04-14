package mmmcp.util;

import java.util.Random;

public class Timer {

    private double time = getCurrentTime();
    private int delay = 0;

    private final int minDelay;
    private final int maxDelay;

    public Timer(int minDelay, int maxDelay) {

        if (minDelay > maxDelay) {
            final int i = minDelay;
            minDelay = maxDelay;
            maxDelay = i;
        }

        this.minDelay = minDelay;
        this.maxDelay = maxDelay;

    }

    public final boolean hasReachedDelay() {

        if ((getCurrentTime() - time) >= delay) {

            time = getCurrentTime();
            delay = (new Random().nextInt(maxDelay - minDelay + 1) + minDelay);

            return true;

        } else {
            return false;
        }

    }

    private double getCurrentTime() {
        return (System.nanoTime() / 1000000);
    }

}
