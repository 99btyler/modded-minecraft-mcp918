package mmmcp.util;

import java.util.Random;

public class Timer {

    private double lastTime;

    private final Random random;
    private int currentDelay;

    private final int min;
    private final int max;

    public Timer(int min, int max) {

        reset();

        random = new Random();
        currentDelay = 0;

        this.min = min;
        this.max = max;

    }

    public final boolean hasReached() {

        if ((getCurrentTime() - lastTime) >= currentDelay) {

            reset();
            currentDelay = (random.nextInt(max - min + 1) + min);
            return true;

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
