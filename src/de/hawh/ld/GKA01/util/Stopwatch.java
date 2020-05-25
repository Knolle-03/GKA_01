package de.hawh.ld.GKA01.util;

import java.util.ArrayList;
import java.util.List;

public class Stopwatch {

    private long startTime = 0;
    private long stopTime = 0;
    private final List<Long> roundTimes = new ArrayList<>();





    public void start() {
        this.startTime = System.currentTimeMillis();
    }


    public void stop() {
        this.stopTime = System.currentTimeMillis();
    }

    public void addToRoundTimes() {
        roundTimes.add(millisElapsed());
    }

    public void reset() {
        startTime = 0;
        stopTime = 0;
    }

    public void resetRounds() {
        roundTimes.clear();
    }

    public String getTotalTime() {
        Long sum = 0L;
        for (Long time : roundTimes) {
            sum += time;
        }

        return elapsedTime(sum);
    }

    public List<String> getRoundTimes() {
        List<String> times = new ArrayList<>();
        for (Long roundTime : roundTimes) {
            times.add(elapsedTime(roundTime));
        }

        return times;
    }


    public long millisElapsed() {
        return stopTime - startTime;
    }

    public String elapsedTime() {
        return elapsedTime(stopTime - startTime);
    }

    public String elapsedTime(long millis) {

        int hours = (int) (millis / 3_600_000);
        int hoursRemainder = (int) (millis % 3_600_000);

        int minutes = hoursRemainder / 60_000;
        int minutesRemainder = hoursRemainder % 60_000;

        int seconds = minutesRemainder / 1_000;
        int remainingMillis = minutesRemainder % 1_000;

        return String.format("%02dh:%02dm:%02ds:%03dms", hours, minutes, seconds, remainingMillis);
    }
}
