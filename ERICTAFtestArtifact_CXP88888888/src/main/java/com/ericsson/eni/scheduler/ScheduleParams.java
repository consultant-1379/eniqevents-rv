package com.ericsson.eni.scheduler;

import java.util.concurrent.TimeUnit;

/**
 * Class holds information for scheduling scenario run
 */
public class ScheduleParams {
    private TimeUnit timeUnit;
    private int unitCount;

    /**
     * @param timeUnit  TimeUnit.MINUTES, TimeUnit.HOURS, TimeUnit.DAYS
     * @param unitCount count of how much time units
     */
    public ScheduleParams(TimeUnit timeUnit, int unitCount) {
        this.timeUnit = timeUnit;
        this.unitCount = unitCount;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public int getUnitCount() {
        return unitCount;
    }

    @Override
    public String toString() {
        return "ScheduleParams{" +
                "timeUnit=" + timeUnit +
                ", unitCount=" + unitCount +
                '}';
    }
}
