package com.ericsson.eni.scheduler;

import com.ericsson.cifwk.taf.data.DataHandler;

import java.util.concurrent.TimeUnit;

public class ScheduleParamResolver {

    /**
     * Resolve schedule params from properties for specified flow name
     *
     * @param flowName
     * @return
     */
    public static ScheduleParams get(String flowName) {
        int unitCount = Integer.valueOf(DataHandler.getAttribute(flowName + ".count").toString());
        TimeUnit timeUnit = TimeUnit.valueOf(DataHandler.getAttribute(flowName + ".units").toString());
        return new ScheduleParams(timeUnit, unitCount);
    }
}
