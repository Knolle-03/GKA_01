package de.hawh.ld.GKA01.util;

public class Stopwatch {

    private long startTime = 0;
    private long stopTime = 0;


    public void start() {
        this.startTime = System.currentTimeMillis();
    }


    public void stop() {
        this.stopTime = System.currentTimeMillis();
    }

    public void reset() {
        startTime = 0;
        stopTime = 0;
    }



    public String elapsedTime() {

        if (startTime == 0 || stopTime == 0) throw new IllegalStateException("Stopwatch in illegal State to compute elapsed time");

        long millis = stopTime - startTime;

        int hours = (int) (millis / 3_600_000);
        int hoursRemainder = (int) (millis % 3_600_000);

        int minutes = hoursRemainder / 60_000;
        int minutesRemainder = hoursRemainder % 60_000;

        int seconds = minutesRemainder / 1_000;
        int remainingMillis = minutesRemainder % 1_000;

        return String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, remainingMillis);
    }



}
