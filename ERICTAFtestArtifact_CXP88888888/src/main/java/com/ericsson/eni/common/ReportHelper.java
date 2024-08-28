package com.ericsson.eni.common;

import com.ericsson.cifwk.taf.TafTestContext;
import com.ericsson.enm.metrics.MetricsBuilder;
import com.ericsson.oss.services.scriptengine.spi.dtos.AbstractDto;
import com.ericsson.oss.services.scriptengine.spi.dtos.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;

public class ReportHelper {
    private static final Logger LOG = LoggerFactory.getLogger(ReportHelper.class);

    public static void reportSuccess(String command, long startOfExecution) {
        long executionTime = System.currentTimeMillis() - startOfExecution;
        LOG.info("Report success metric [{} - {}]", command, executionTime);
        report(command, executionTime, true);
    }

    public static void reportFail(String command, long startOfExecution) {
        long executionTime = System.currentTimeMillis() - startOfExecution;
        LOG.info("Report failure metric [{} - {}]", command, executionTime);
        report(command, executionTime, false);
    }

    private static void report(String command, long executionTime, boolean successful) {
        String executionId = TafTestContext.getContext().getAttribute(ContextKey.EXECUTION_ID);
        long vUser = TafTestContext.getContext().getVUser();
        try {
            MetricsBuilder.forCommand(command)
                    .forJob(vUser)
                    .withExecutionId(executionId)
                    .onElements(0)
                    .at(new Date())
                    .gotResponse(new ResponseDto(new ArrayList<AbstractDto>()))
                    .elementsNotCopied(0)
                    .within((int) executionTime)
                    .withElements(0)
                    .successful(successful)
                    .report();
        }
        catch (Exception e)
        {
            try {
                MetricsBuilder.forCommand(command)
                        .forJob(vUser)
                        .withExecutionId(executionId)
                        .onElements(0)
                        .at(new Date())
                        .gotResponse(new ResponseDto(new ArrayList<AbstractDto>()))
                        .elementsNotCopied(0)
                        .within((int) executionTime)
                        .withElements(0)
                        .successful(successful)
                        .report();
            }
            catch (Exception e1)
            {
                LOG.error(e1.getMessage());
            }
        }
    }
}
