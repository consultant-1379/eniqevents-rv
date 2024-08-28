package com.ericsson.eni.scheduler;

import com.ericsson.cifwk.taf.scenario.TestScenario;
import com.ericsson.cifwk.taf.scenario.TestScenarioRunner;
import com.ericsson.cifwk.taf.scenario.api.TestScenarioBuilder;
import com.ericsson.cifwk.taf.scenario.api.TestScenarioRunnerBuilder;
import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class ScenarioRunScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(ScenarioRunScheduler.class);

    private ScenarioRunScheduler() {
    }

    public static ScheduleBuilder schedule() {
        return new ScheduleBuilder();
    }

    public static class ScheduleBuilder {

        private TestScenarioBuilder scenarioBuilder;
        private TestScenarioRunnerBuilder runnerBuilder;
        private long endTimeMilliseconds;
        private long timeSlotMillis;

        private ScheduleBuilder() {
        }

        public ScheduleBuilder scenario(TestScenarioBuilder scenario) {
            this.scenarioBuilder = scenario;
            return this;
        }

        /**
         * Specify timeframe for one scenario run. If scenario finishes faster than timeslot,
         * then pause for remaining time will be taken
         *
         * @param seconds
         */
        public ScheduleBuilder withTimeSlot(int seconds) {
            this.timeSlotMillis = seconds * 1000;
            return this;
        }

        public ScheduleBuilder withRunner(TestScenarioRunnerBuilder runner) {
            this.runnerBuilder = runner;
            return this;
        }

        public void toRun(int count, TimeUnit unit) {
            LOG.info("Preparing to run scenario for {} {}", count, unit);
            Calendar calendar = Calendar.getInstance();
            if (TimeUnit.DAYS.equals(unit)) {
                calendar.add(Calendar.DATE, count);
            } else if (TimeUnit.HOURS.equals(unit)) {
                calendar.add(Calendar.HOUR, count);
            } else if (TimeUnit.MINUTES.equals(unit)) {
                calendar.add(Calendar.MINUTE, count);
            } else {
                throw new IllegalArgumentException("TimeUnit " + unit + " is not supported");
            }
            endTimeMilliseconds = calendar.getTimeInMillis();

            start();
        }

        private void start() {
            Throwable exception = null;
            do {
                long startTime = System.currentTimeMillis();
                try {
                    TestScenario scenario = scenarioBuilder.build();
                    TestScenarioRunner runner = runnerBuilder.build();
                    runner.start(scenario);
                } catch (Throwable e) {
                    exception = e;
                }

                if (System.currentTimeMillis() < endTimeMilliseconds) {
                    pauseIfRequired(startTime);
                }

            } while (System.currentTimeMillis() < endTimeMilliseconds);

            if (exception != null) {
                Throwables.propagate(exception);
            }
        }

        private void pauseIfRequired(long startTime) {
            long runtimeMillis = System.currentTimeMillis() - startTime;
            if (runtimeMillis < timeSlotMillis) {
                try {
                    LOG.info("Taking pause for {}", timeSlotMillis - runtimeMillis);
                    Thread.sleep(timeSlotMillis - runtimeMillis);
                } catch (InterruptedException e) {
                    LOG.error("Exception during pause period", e.getMessage());
                }
            }
        }
    }
}
