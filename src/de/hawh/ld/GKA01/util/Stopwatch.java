package de.hawh.ld.GKA01.util;

public class Stopwatch {

    private long startTime = 0;
    private long stopTime = 0;
    Stopwatch.State state = State.READY;

    private enum State {
        READY, RUNNING, RAN
    }


    public void start() {
        if (state == State.RAN) {
            System.out.println("Stopwatch already stores a time. Use reset() to start over.");
            return;
        } else if (state == State.RUNNING) {
            System.out.println("Stopwatch already running. Use stop() and reset() to start over.");
            return;
        }
        this.startTime = System.currentTimeMillis();
        this.state = State.RUNNING;
    }


    public void stop() {
        if (state == State.READY) {
            System.out.println("Stopwatch has not been started. Use start() to start the stopwatch");
            return;
        } else if (state == State.RAN) {
            System.out.println("Stopwatch has already been stopped.");
            return;
        }
        this.stopTime = System.currentTimeMillis();
        this.state = State.RAN;
    }

    public void reset() {
        startTime = 0;
        stopTime = 0;
        this.state = State.READY;
    }


    public long millisElapsed() {
        return stopTime - startTime;
    }



    public String elapsedTime() {
        return elapsedTime(stopTime - startTime);
    }

    public String elapsedTime(long millis) {
//
//        if (state == State.RUNNING) {
//            System.out.println("Stopwatch is still running use stop() and then elapsedTime().");
//            return null;
//        } else if(state == State.READY) {
//            System.out.println("Stopwatch has not been started.\nUse start() and stop() to measure time\nand elapsedTime() to get the time.");
//            return null;
//        }


        int hours = (int) (millis / 3_600_000);
        int hoursRemainder = (int) (millis % 3_600_000);

        int minutes = hoursRemainder / 60_000;
        int minutesRemainder = hoursRemainder % 60_000;

        int seconds = minutesRemainder / 1_000;
        int remainingMillis = minutesRemainder % 1_000;

        return String.format("%02dh:%02dm:%02ds:%03dms", hours, minutes, seconds, remainingMillis);
    }
}
